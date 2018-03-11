package com.ibank.service.command.type;

public class MsgIBankTransferSearchRqCommand extends IBankTransferCommand {

    /**
     * The logger.
     */
    private static final Log LOG = LogFactory.getLog(MsgIBankTransferSearchRqCommand.class);

    /**
     * @param messageType
     */
    public MsgIBankTransferSearchRqCommand(MessageType messageType) {
        super(messageType);
    }


    /* (non-Javadoc)
     * @see ru.psit.gateway.commands.Command#execute()
     */
    @Override
    public JAXBElement<MessageType> execute() {
        // start

        // get request
        MsgIBankTransferSearchRq msgIBankTransferSearchRq = (MsgIBankTransferSearchRq)getMessageFactory().
                getDocumentChoice(getMessageType());

        // make response
        MessageType responseMessageType = getMessageFactory().create_MsgIBankTransferSearchRs(getMessageType());
        MsgIBankTransferSearchRs msgIBankTransferSearchRs = (MsgIBankTransferSearchRs)getMessageFactory().
                getDocumentChoice(responseMessageType);

        // fill response data
        // result
        msgIBankTransferSearchRs.setResult(ResultType.OK);
        // handle request
        handle(msgIBankTransferSearchRq, msgIBankTransferSearchRs);

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
        MsgIBankTransferSearchRq msgIBankTransferSearchRq = (MsgIBankTransferSearchRq)request;
        MsgIBankTransferSearchRs msgIBankTransferSearchRs = (MsgIBankTransferSearchRs)response;

        JAXBElement<MessageType>   elementResponse = checkExternalProvider(msgIBankTransferSearchRq.getTransfer().getTransferType(), getMessageType());
        //JAXBElement<MessageType>   elementResponse = null;
        if(elementResponse!=null){
            MsgIBankTransferSearchRs forsajMsgIBankTransferSearchRs =(MsgIBankTransferSearchRs)getMessageFactory().getDocumentChoice(elementResponse.getValue());
            msgIBankTransferSearchRs.setDescription(forsajMsgIBankTransferSearchRs.getDescription());
            msgIBankTransferSearchRs.setResult(forsajMsgIBankTransferSearchRs.getResult());
            msgIBankTransferSearchRs.getPayAmount().addAll(forsajMsgIBankTransferSearchRs.getPayAmount());
            msgIBankTransferSearchRs.setTransfer(forsajMsgIBankTransferSearchRs.getTransfer());

        }else {

            // transfer
            TransferType transferType = msgIBankTransferSearchRq.getTransfer();

            // get KNTR
            String KNTR = transferType.getCheckTransferNumber();
            if(KNTR != null) {
                // search by KNTR
                SEARCHTRANSFER.SearchResult searchResult = searchTransferByKNTR(KNTR);
                TRANSFER_T transfer_t = searchResult.getTransfer_t();
                if(transfer_t == null || transfer_t.isNull()) {
                    LOG.error(getMessageSource().getMessage(GateWayMessageSource.NO_TRANSFER_FOUND_BY_KNTR));
                    throw new GateWayException(getMessageSource().getMessage(GateWayMessageSource.NO_TRANSFER_FOUND_BY_KNTR));
                }
                LOG.debug("TRANSFER_T found by KNTR : " + transfer_t);
                // check status for payment and check CustID of sender or payment agent
                if(transfer_t.getTrSTATYS() == TRANSFER_T_Statuses.ALREADY_PAYED &&
                        !(transfer_t.getSenderAGENT().getCUSTID()== getMessageType().getCustId() ||
                                transfer_t.getPayMENTAGENT().getCUSTID()!= getMessageType().getCustId()))
                {
                    //if paument is pay
                    String message = getMessageSource().getMessage(GateWayMessageSource.TRANSFER_ALREADY_PAYED1)
                            + SqlDateFactory.getDate(transfer_t.getTrPAYMENTDATE())
                            + " " + getMessageSource().getSuffix(GateWayMessageSource.TRANSFER_ALREADY_PAYED2)
                            + transfer_t.getPayMENTAGENT().getCUSTID();
                    LOG.error(message);
                    throw new GateWayException(message);
                }
                //check custId of block transfer document with custId message
                //BlockeCustId =null, если перевод редактировался в др. системе
                if(searchResult.getBlockeCustId()!=null)
                {
                    if(searchResult.getBlockeCustId()!= getMessageType().getCustId())
                    {
                        if(transfer_t.getTrSTATYS() == TRANSFER_T_Statuses.TRANSFER_BLOCKED)
                        {
                            LOG.error(getMessageSource().getMessage(GateWayMessageSource.TRANSFER_BLOCKED) + searchResult.getBlockeCustId());
                            throw new GateWayException(getMessageSource().getMessage(GateWayMessageSource.TRANSFER_BLOCKED) + searchResult.getBlockeCustId());
                        }
                        else if(transfer_t.getTrSTATYS() == TRANSFER_T_Statuses.TRANSFER_BLOCKED_FOR_CHANGE)
                        {
                            LOG.error(getMessageSource().getMessage(GateWayMessageSource.TRANSFER_BLOCKED_FOR_CHANGE) + searchResult.getBlockeCustId());
                            throw new GateWayException(getMessageSource().getMessage(GateWayMessageSource.TRANSFER_BLOCKED_FOR_CHANGE)+ searchResult.getBlockeCustId());
                        }
                    }
                }

                // fill response transfer
                TransferType responseTransferType = getSql2XmlConverter().create_TransferType(transfer_t);
                msgIBankTransferSearchRs.setTransfer(responseTransferType);
            } else {
                // transfer
                TRANSFER_T transfer_t = getXml2SqlConverter().create_TRANSFER_T(transferType);
                Integer count = searchCountByTrasfer(transfer_t);
                msgIBankTransferSearchRs.setDescription(getMessageSource().getMessage(GateWayMessageSource.FOUND_TRANSFERS) +" : " + count);
            }
        }
    }
}
