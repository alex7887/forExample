package com.ibank.service.command;

public abstract class AuditableTransferCommand extends IBankCommand{

    /**
     * The reference to auditor.
     */
    private Auditor auditor;

    /**
     * Creates ibank command.
     * @param messageType
     */
    public AuditableIBankTransferCommand(MessageType messageType) {
        super(messageType);
    }

    /**
     * @return the auditor.
     */
    public synchronized Auditor getAuditor() {
        if(auditor == null)
            auditor = new Auditor(getStoredFunctionFactory(), getMessageSource());
        return auditor;
    }
}
