package com.ibank.service.external;

public abstract class AbstractService {


    /**
     * The message source.
     */
    private GateWayMessageSource messageSource;

    /**
     * The request message type.
     */
    private MessageType messageType;


    /**
     * Creates service and binds message with service.
     * @param messageType message type.
     */
    public AbstractService(MessageType messageType) {
        super();
        this.messageType=messageType;

    }


    /**
     * @param messageSource the messageSource to set
     */
    @Required
    public void setMessageSource(GateWayMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * @return the messageSource
     */
    public GateWayMessageSource getMessageSource() {
        return messageSource;
    }


    /**
     * Executes request to service.
     * @param parameters pameters for service.
     */
    public abstract Object execute() throws GateWayException;


    /**
     * @param messageType the messageType to set
     */
    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }


    /**
     * @return the messageType
     */
    public MessageType getMessageType() {
        return messageType;
    }



}
