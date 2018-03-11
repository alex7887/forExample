package com.ibank.service.command;

public abstract class Command {


    /**
     * The logger.
     */
    private static final Log LOG = LogFactory.getLog(Command.class);

    /**
     * The message source.
     */
    private GateWayMessageSource messageSource;

    /**
     * The factory of stored procedures.
     */
    private StoredFunctionFactory storedFunctionFactory;

    /**
     * The incoming request.
     */
    private MessageType messageType;

    /**
     * The reference to JAXB element factory.
     */
    private JAXBElementFactory elementFactory;

    /**
     * The reference to validator factory.
     */
    private AgentValidatorFactory agentValidatorFactory;

    /**
     * @param messageType incoming message type.
     */
    public Command(MessageType messageType) {
        super();
        this.messageType = messageType;
    }

    /**
     * Executes command.
     * @return response message.
     */
    public abstract JAXBElement<MessageType> execute();

    /**
     * @return the storedFunctionFactory
     */
    public StoredFunctionFactory getStoredFunctionFactory() {
        return storedFunctionFactory;
    }

    /**
     * @param storedFunctionFactory the storedFunctionFactory to set
     */
    public void setStoredFunctionFactory(StoredFunctionFactory storedFunctionFactory) {
        this.storedFunctionFactory = storedFunctionFactory;
    }

    /**
     * @return the messageType
     */
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * @param messageType the messageType to set
     */
    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    /**
     * @return the messageSource
     */
    public GateWayMessageSource getMessageSource() {
        return messageSource;
    }

    /**
     * @param messageSource the messageSource to set
     */
    public void setMessageSource(GateWayMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * @return the agentValidatorFactory
     */
    public AgentValidatorFactory getAgentValidatorFactory() {
        return agentValidatorFactory;
    }

    /**
     * @param agentValidatorFactory the agentValidatorFactory to set
     */
    @Required
    public void setAgentValidatorFactory(AgentValidatorFactory agentValidatorFactory) {
        this.agentValidatorFactory = agentValidatorFactory;
    }

    /**
     * Handles request and fills response.
     * @param request request.
     * @param response response to fill.
     */
    public void handle(Object request, Object response) {
        try {
            // validate agent request
            AgentValidator agentValidator = (AgentValidator)getAgentValidatorFactory().createValidator(getMessageType());
            if (agentValidator != null)
                agentValidator.validate(getMessageType());
            // handle request
            handleRequest(request, response);
        }
        // gateway exceptions
        catch(GateWayException exception) {
            LOG.error("error in handle request.", exception);
            // bad result
            setResult(response, ResultType.ERROR);
            setDescription(response, GateWayMessageSource.GATEWAY_ERROR_PREFIX + exception.getMessage());
        }
        // database errors
        catch(UncategorizedSQLException unSqlException) {
            LOG.error("data access error in handle request.", unSqlException);
            // bad result
            setResult(response, ResultType.ERROR);
            String message = StringUtils.parse(unSqlException.getSQLException());
            setDescription(response, GateWayMessageSource.IBANK_ERROR_PREFIX + message);
        }
    }

    /**
     * Implementation of request handling.
     * @param request request.
     * @param response response to fill.
     * @throws GateWayException possible exception.
     * @throws SQLException
     * @throws SQLException
     */
    public abstract void handleRequest(Object request, Object response) throws GateWayException;

    /**
     * Sets result in response.
     * @param response object of response.
     * @param value value to set.
     */
    private void setResult(Object response, ResultType value) {
        try {
            ReflectionUtils.invokeMethod(response, "setResult",
                    new Class[] { ResultType.class },
                    new Object[] { value });
        } catch (Exception e) {
            LOG.error("Can't set result.", e);
            throw new IllegalStateException("Can't set result.", e);
        }
    }

    /**
     * Sets description in response.
     * @param response object of response.
     * @param value value to set.
     */
    private void setDescription(Object response, String value) {
        try {
            ReflectionUtils.invokeMethod(response, "setDescription",
                    new Class[] { String.class },
                    new Object[] { value });
        } catch (Exception e) {
            LOG.error("Can't set description.", e);
            throw new IllegalStateException("Can't set description.", e);
        }
    }

    /**
     * @return the elementFactory
     */
    public JAXBElementFactory getElementFactory() {
        return elementFactory;
    }

    /**
     * @param elementFactory the elementFactory to set
     */
    public void setElementFactory(JAXBElementFactory elementFactory) {
        this.elementFactory = elementFactory;
    }
}
