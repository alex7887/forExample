package com.ibank.service.external;

public class AbstractServiceFactory {


    /**
     * The message source.
     */
    private GateWayMessageSource messageSource;

    /**
     * @return an instance of service.
     * @throws GateWayException
     */
    public abstract AbstractService createService(Object request) throws GateWayException;

    /**
     * @param messageSource the messageSource to set
     */
    public void setMessageSource(GateWayMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * @return the messageSource
     */
    public GateWayMessageSource getMessageSource() {
        return messageSource;
    }

}
