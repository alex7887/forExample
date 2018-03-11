package com.ibank.service.command;

import com.ibank.service.command.type.IBankMessageFactory;

public abstract class IBankCommand extends Command  {

    /**
     * The logger.
     */
    private static final Log LOG = LogFactory.getLog(IBankCommand.class);
    public static final String BANK_CLIENT_DOCUMENT_SUFFIX = "*";

    /**
     * The message factory for requests and responces.
     */
    private IBankMessageFactory messageFactory;

    /**
     * The reference to XML2SQL converter.
     */
    private Xml2SqlConverter xml2SqlConverter;

    /**
     * The reference to SQL2XML converter.
     */
    private Sql2XmlConverter sql2XmlConverter;

    /**
     * The reference to property holder.
     */
    private PropertiesHolder propertiesHolder;

    private Map<String, AbstractServiceFactory> providerFactories =new HashMap<String, AbstractServiceFactory>();

    /**
     * Creates IBank command.
     * @param messageType
     */
    public IBankCommand(MessageType messageType) {
        super(messageType);
    }

    /**
     * @return the messageFactory
     */
    public IBankMessageFactory getMessageFactory() {
        return messageFactory;
    }

    /**
     * @param messageFactory the messageFactory to set
     */
    @Required
    public void setMessageFactory(IBankMessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }

    /**
     * @return the xml2SqlConverter
     */
    public Xml2SqlConverter getXml2SqlConverter() {
        return xml2SqlConverter;
    }

    /**
     * @param xml2SqlConverter the xml2SqlConverter to set
     */
    @Required
    public void setXml2SqlConverter(Xml2SqlConverter xml2SqlConverter) {
        this.xml2SqlConverter = xml2SqlConverter;
    }

    /**
     * @return the sql2XmlConverter
     */
    public Sql2XmlConverter getSql2XmlConverter() {
        return sql2XmlConverter;
    }

    /**
     * @param sql2XmlConverter the sql2XmlConverter to set
     */
    @Required
    public void setSql2XmlConverter(Sql2XmlConverter sql2XmlConverter) {
        this.sql2XmlConverter = sql2XmlConverter;
    }

    /**
     * @return the propertiesHolder
     */
    public PropertiesHolder getPropertiesHolder() {
        return propertiesHolder;
    }

    /**
     * @param propertiesHolder the propertiesHolder to set
     */
    @Required
    public void setPropertiesHolder(PropertiesHolder propertiesHolder) {
        this.propertiesHolder = propertiesHolder;
    }

    /**
     * Checks client in IBank for being terrorist.
     * @param clienInfoType
     * @return
     * @throws GateWayException
     */
    public void checkClient(ClientInfoType clienInfoType) throws GateWayException {
        // get values
        Long custId = getMessageType().getCustId();
        // check client in table of terrorists
        CLIENT_T client_t = getXml2SqlConverter().create_CLIENT_T(clienInfoType, null, null, null, null);

        validateDCTP(client_t.getPASP());
        // get them
        PERSINFO_T persinfo_t = client_t.getPersinfo();
        PASP_T pasp_t = client_t.getPASP();
        Date bith = client_t.getBIRTHDATE();
        // get function
        CHECK_CLIENT function = (CHECK_CLIENT)getStoredFunctionFactory().createFunction(CHECK_CLIENT.class);
        Integer result = function.execute(persinfo_t, pasp_t, bith, custId);
        if(result.intValue() != CHECK_CLIENT.SUCCESS) {
            LOG.error(getMessageSource().getMessage(GateWayMessageSource.ERROR_CHECK_CLIENT));
            throw new GateWayException(getMessageSource().getMessage(GateWayMessageSource.ERROR_CHECK_CLIENT));
        }
    }

    /**
     * Check agent point for access to work.
     * @param messageType
     * @return
     * @throws GateWayException
     */
    public void checkPoint(MessageType messageType, AgentType agentType) throws GateWayException {
        // convert values
        Long nCustId = messageType.getCustId();
        Long nClientId = agentType.getClientID();
        Long nRootCustID = messageType.getRootCustId();
        // checkpoint
        checkPoint(nCustId, nClientId, nRootCustID);
    }

    /**
     * Check agent point for access to work.
     * @param messageType
     * @return
     * @throws GateWayException
     */
    public void checkPoint(MessageType messageType, long agentClientId) throws GateWayException {
        // convert values
        Long nCustId = messageType.getCustId();
        Long nClientId = agentClientId;
        Long nRootCustID = messageType.getRootCustId();
        // checkpoint
        checkPoint(nCustId, nClientId, nRootCustID);
    }

    /**
     * Check agent point for access to work.
     * @param custId
     * @param clientId
     * @param rootCustID
     * @return
     * @throws GateWayException
     */
    public void checkPoint(Long custId, Long clientId, Long rootCustID) throws GateWayException {
        // get stored fucntion
        CHECK_POINT function = (CHECK_POINT)getStoredFunctionFactory().createFunction(CHECK_POINT.class);
        // execute
        Number result = function.execute(custId, clientId, rootCustID);
        if(result.intValue() != CHECK_POINT.SUCCESS) {
            LOG.error(getMessageSource().getMessage(GateWayMessageSource.ERROR_CHECK_POINT));
            throw new GateWayException(getMessageSource().getMessage(GateWayMessageSource.ERROR_CHECK_POINT));
        }
    }

    /**
     * @param USER_ID
     * @param PDOC
     * @param PNUM
     * @param PSER
     * @param NAMF
     * @return
     */
    public Long findClient(Long USER_ID, String PDOC, String PNUM, String PSER, String NAMF) {
        // create function
        FIND_CLIENT function = (FIND_CLIENT)getStoredFunctionFactory().createFunction(FIND_CLIENT.class);
        // call it
        Long result = function.execute(USER_ID, PDOC, PNUM, PSER, NAMF);
        LOG.debug("Found client : " + result);
        return result;
    }

    /**
     * @param custId our coustomer id.
     * @return client id from internal bank system for getting commission.
     */
    public Integer getCommissionClientId(Long custId) {
        // get stored fucntion
        GET_AGENTNUMBER_BY_COMMISSION function = (GET_AGENTNUMBER_BY_COMMISSION)
                getStoredFunctionFactory().createFunction(GET_AGENTNUMBER_BY_COMMISSION.class);
        // call function
        return function.execute(custId);
    }

    /**
     * @param custId our coustomer id.
     * @return client id from internal bank system for getting commission.
     */
    public Integer getCommissionClientTRTypeId(Long custId) {
        // get stored fucntion
        GET_AGENTNUMBER_BY_COMM_TRTYPE function = (GET_AGENTNUMBER_BY_COMM_TRTYPE)
                getStoredFunctionFactory().createFunction(GET_AGENTNUMBER_BY_COMM_TRTYPE.class);
        // call function
        return function.execute(custId);
    }

    /**
     * Checks the amount of days between two dates.
     * @param startDateTime
     * @param endDateTime
     * @throws GateWayException
     */
    public void checkAmountOfDays(Timestamp startDateTime,
                                  Timestamp endDateTime) throws GateWayException {
        // check days
        int daysLimit = getPropertiesHolder().getMaximumCountOfDayInReport();
        if(DateUtils.numberOfDays(startDateTime, endDateTime) > daysLimit)
        {
            LOG.error(getMessageSource().getMessage(GateWayMessageSource.AMOUNT_DAYS_GRATER_THEN_LIMIT) + " : " + daysLimit);
            throw new GateWayException(getMessageSource().getMessage(GateWayMessageSource.AMOUNT_DAYS_GRATER_THEN_LIMIT) + " : " + daysLimit);
        }
    }




    /**
     * @param providerFactories the providerFactories to set
     */
    public void setProviderFactories(Map<String, AbstractServiceFactory> providerFactories) {
        this.providerFactories = providerFactories;
    }

    /**
     * @return the providerFactories
     */
    public Map<String, AbstractServiceFactory> getProviderFactories() {
        return providerFactories;
    }


}
