package com.ibank.service.command.type;

public class MsgIBankTransferPayRqCommand extends IBankTransferCommand {

    /**
     * The logger.
     */
    private static final Log LOG = LogFactory.getLog(MsgIBankTransferPayRqCommand.class);

    public static final String BANK_CLIENT_DOCUMENT_SUFFIX = "*";



    /**
     * @param messageType
     */
    public MsgIBankTransferPayRqCommand(MessageType messageType) {
        super(messageType);
    }

    /* (non-Javadoc)
     * @see ru.psit.gateway.commands.Command#execute()
     */
    @Override
    public JAXBElement<MessageType> execute() {
        // start
        LOG.info("start handling request");
        // get request
        MsgIBankTransferPayRq msgIBankTransferPayRq = (MsgIBankTransferPayRq)getMessageFactory().
                getDocumentChoice(getMessageType());

        // make response
        MessageType responseMessageType = getMessageFactory().create_MsgIBankTransferPayRs(getMessageType());
        MsgIBankTransferPayRs msgIBankTransferPayRs = (MsgIBankTransferPayRs)getMessageFactory().
                getDocumentChoice(responseMessageType);

        // fill response data
        // result
        msgIBankTransferPayRs.setResult(ResultType.OK);
        // handle request
        handle(msgIBankTransferPayRq, msgIBankTransferPayRs);

        // result response
        return this.getElementFactory().createElement(responseMessageType);
    }

    /* (non-Javadoc)
     * @see ru.psit.gateway.commands.Command#handleRequest(java.lang.Object, java.lang.Object)
     */
    @Override
    public void handleRequest(Object request, Object response)
            throws GateWayException {
        // converts
        MsgIBankTransferPayRq msgIBankTransferPayRq = (MsgIBankTransferPayRq)request;
        MsgIBankTransferPayRs msgIBankTransferPayRs = (MsgIBankTransferPayRs)response;

        // transfer
        TransferType transferType = msgIBankTransferPayRq.getTransfer();

        // check point (our)
        checkPoint(getMessageType(), transferType.getPaymentAgent());

        JAXBElement<MessageType>   elementResponse = checkExternalProvider(msgIBankTransferPayRq.getTransfer().getTransferType(), getMessageType());
        //JAXBElement<MessageType>   elementResponse = null;
        if(elementResponse!=null){
            MsgIBankTransferPayRs forsajMsgIBankTransferPayRs =(MsgIBankTransferPayRs)getMessageFactory().getDocumentChoice(elementResponse.getValue());

            msgIBankTransferPayRs.setDescription(forsajMsgIBankTransferPayRs.getDescription());
            msgIBankTransferPayRs.setPayAmount(forsajMsgIBankTransferPayRs.getPayAmount());
            msgIBankTransferPayRs.setResult(forsajMsgIBankTransferPayRs.getResult());
            msgIBankTransferPayRs.setTransfer(forsajMsgIBankTransferPayRs.getTransfer());
        }else {
            // check clients
            //validatePDOCLength(transferType.getSenderTransfer().getPersonForm().getIdentityPaper());

            checkClient(transferType.getSenderTransfer());
            checkClient(transferType.getTrueReceiverTransfer());

            // check currency
            String currency3 = transferType.getAmountTransfer().getCurrency();
            // code like 810 for RUR
            Integer currency = getXml2SqlConverter().getCurrency(currency3);

            // get transfer by KNTR
            String KNTR = transferType.getCheckTransferNumber();

            // search by KNTR
            //TRANSFER_T transfer_t = searchTransferByKNTR(KNTR).getTransfer_t();
            SEARCHTRANSFER.SearchResult searchResult = searchTransferByKNTR(KNTR);
            TRANSFER_T transfer_t = searchResult.getTransfer_t();
            LOG.debug("TRANSFER_T found by KNTR : " + transfer_t);


            if(transfer_t == null || transfer_t.isNull()) {
                LOG.error(getMessageSource().getMessage(GateWayMessageSource.NO_TRANSFER_FOUND_BY_KNTR));
                throw new GateWayException(getMessageSource().getMessage(GateWayMessageSource.NO_TRANSFER_FOUND_BY_KNTR));
            }
            // check status of transfer, it should be blocked
            if(transfer_t.getTrSTATYS().intValue() != TRANSFER_T_Statuses.TRANSFER_BLOCKED) {
                LOG.error(getMessageSource().getMessage(GateWayMessageSource.TRANSFER_NOT_BLOCKED_YET));
                throw new GateWayException(getMessageSource().getMessage(GateWayMessageSource.TRANSFER_NOT_BLOCKED_YET));
            }

            // get amount
            Double amount = DataConverter.bigDecimal2Double(transferType.getAmountTransfer().getAmountT());

            // counts commissions and currentcy rate

            AmountCommissionLimitHolder amountCommissionLimitHolder = paymentCommissionExtend(
                    getMessageType().getCustId(), amount, currency,  transferType, transfer_t.getTrSUM().getRATE(), transfer_t.getTransferTYPE());


            // update commission
            transfer_t.getTrSUM().setRATEPM(amountCommissionLimitHolder.getCommissionAgentPayment());


            // resident
            Boolean ISRESIDENT_BY_S_CNTR = getXml2SqlConverter().isResident(transferType.getSenderTransfer());
            Boolean ISRESIDENT_BY_R_CNTR = getXml2SqlConverter().isResident(transferType.getTrueReceiverTransfer());


            // get CNTR and check
            Integer S_CNTR = getAgentCNTR(transfer_t.getSenderAGENT().getCUSTID(), transfer_t.getSenderAGENT().getPOINID());
            Integer R_CNTR = getAgentCNTR(getMessageType().getCustId(), transferType.getPaymentAgent().getPointID());

            // check
            boolean iCheck = isTransferOneSelf(transferType.getSenderTransfer(), transferType.getTrueReceiverTransfer());

            // check if senderagent != paymentagent
            // else it is rollback of transfer
            if(!transfer_t.getSenderAGENT().getCUSTID().equals(transferType.getPaymentAgent().getCustID())) {
                // check country
                checkCountryForPay(S_CNTR, R_CNTR, currency, ISRESIDENT_BY_S_CNTR, ISRESIDENT_BY_R_CNTR, iCheck);
            }

            // check amount of transfer a day for system and limits for country
            Double alreadyPayedAmount = checkPaymentLimitsForReceiver(transferType.getTrueReceiverTransfer(), R_CNTR,
                    amount, currency, ISRESIDENT_BY_R_CNTR);

            // check limit of transfer receiver for country
            checkReceiveLimitForCountry(R_CNTR, amount, currency, ISRESIDENT_BY_R_CNTR, alreadyPayedAmount);

            // check summ and currency in XML and SQL
            checkSummAndCurrency(transfer_t, amount, currency);
            LOG.debug("end checkSummAndCurrency");
            // pay transfer
            createPayment(transferType, transfer_t, currency, amountCommissionLimitHolder);

            // fill response transfer and get updated transfer type
            TransferType responseTransferType = getSql2XmlConverter().create_TransferType(
                    searchTransferByKNTR(KNTR).getTransfer_t());
            LOG.debug("end responseTransferType "+responseTransferType);
            msgIBankTransferPayRs.setTransfer(responseTransferType);

        }
    }

    /**
     * @param transferType
     * @param currency
     * @throws SQLException
     * @throws SQLException
     */
    protected void createPayment(TransferType transferType, TRANSFER_T transfer_t,
                                 Integer currency, AmountCommissionLimitHolder amountCommissionLimitHolder)
            throws GateWayException {
        // get values
        Long custId = getMessageType().getCustId();

        // SQL transfer
        fillPaymentTRANSFER_T(transfer_t, transferType, currency);

        if(!calculateLimit(custId, amountCommissionLimitHolder.getAgentLimit(),
                amountCommissionLimitHolder.getAgentLimitComission())) {
            LOG.error(getMessageSource().getMessage(GateWayMessageSource.CANT_CALCULATE_AGENT_LIMIT));
            throw new  GateWayException(getMessageSource().getMessage(GateWayMessageSource.CANT_CALCULATE_AGENT_LIMIT));
        }
        try{
            // get stored fucntion
            CREATETRPARENT function = (CREATETRPARENT)getStoredFunctionFactory().createFunction(CREATETRPARENT.class);

            validatePDOCLength(transfer_t.getSenderCLIENT().getPASP());
            // execute
            Number result = function.execute(transfer_t, custId);
            // check result
            if(result.intValue() != CREATETRPARENT.SUCCESS) {
                LOG.error(getMessageSource().getMessage(GateWayMessageSource.CANT_CREATE_PAYMENT));
                throw new GateWayException(getMessageSource().getMessage(GateWayMessageSource.CANT_CREATE_PAYMENT));
            }
        } catch(Throwable exception) {
            LOG.info("Error Pay CREATETRPARENT = "+exception.getMessage());

            // limit update
            if(!calculateLimit(custId, -amountCommissionLimitHolder.getAgentLimit(),
                    amountCommissionLimitHolder.getAgentLimitComission())) {
                LOG.error(getMessageSource().getMessage(GateWayMessageSource.CANT_CALCULATE_AGENT_LIMIT));
                throw new GateWayException(getMessageSource().getMessage(GateWayMessageSource.CANT_CALCULATE_AGENT_LIMIT));
            }
            if(exception instanceof GateWayException) {
                throw (GateWayException)exception;
            }
            LOG.info("Error Pay CREATETRPARENT 2 = "+exception.getMessage());
            throw  (UncategorizedSQLException)exception;
        }



    }

    /**
     * Checks received XML and transfer in bank.
     * @throws GateWayException
     */
    private void checkSummAndCurrency(TRANSFER_T transfer_t, Double amount, Integer currency) throws GateWayException {
        // check summs
        TRANFSUMM_T tranfsumm_t = transfer_t.getTrSUM();
        // check currency
        if(tranfsumm_t.getCURN().intValue() != currency) {
            LOG.error(getMessageSource().getMessage(GateWayMessageSource.CURRENCY_IN_REQUEST_AND_TRANSFER_DIFFERENT));
            throw new GateWayException(getMessageSource().getMessage(GateWayMessageSource.CURRENCY_IN_REQUEST_AND_TRANSFER_DIFFERENT));
        }
        // check summ or amount
        if(tranfsumm_t.getAMOUNT().doubleValue() != amount) {
            LOG.error(getMessageSource().getMessage(GateWayMessageSource.SUMM_IN_REQUEST_AND_TRANSFER_DIFFERENT));
            throw new GateWayException(getMessageSource().getMessage(GateWayMessageSource.SUMM_IN_REQUEST_AND_TRANSFER_DIFFERENT));
        }
    }

    /**
     *
     * @param transfer_t
     * @param transferType
     * @param currency
     * @throws GateWayException
     */
    private void fillPaymentTRANSFER_T(TRANSFER_T transfer_t, TransferType transferType, Integer currency) throws GateWayException {
        // fill from XML
        transfer_t.setTrPARENTREF(transferType.getTransferParentDocReference());
        transfer_t.setTrPAYMENTDATE(SqlDateFactory.getCurrentDateTime());

        // for payment only
        AGENT_T agent_t2 = getXml2SqlConverter().create_AGENT_T(transferType.getPaymentAgent());
        transfer_t.setPayMENTAGENT(agent_t2);

        transfer_t.setTruRECCLIENT(getXml2SqlConverter().create_CLIENT_T(
                transferType.getTrueReceiverTransfer(),
                agent_t2.getCUSTID(), null, null, null
        ));
    }


    /**
     * убираем звездочку, если значение поле паспорт со звездочкой равен 21 символу, т.к. звездочку в данном случае
     * не стоит сохранять а данное поле в БД равно длине 20 символов
     * @param pasp_t
     */
    private void validatePDOCLength(PASP_T pasp_t)
    {
        //transfer_t.getSenderCLIENT().getPASP().getPDOC()
        if(pasp_t!=null)
        {
            if(pasp_t.getPDOC()!=null)
                if(StringUtils.hasText(pasp_t.getPDOC()) && pasp_t.getPDOC().endsWith(BANK_CLIENT_DOCUMENT_SUFFIX))
                {
                    if(pasp_t.getPDOC().length()==21)
                    {
                        pasp_t.setPDOC(pasp_t.getPDOC().substring(0, 20));
                    }
                }

        }

    }
}
