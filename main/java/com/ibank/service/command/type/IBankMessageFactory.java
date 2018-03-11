package com.ibank.service.command.type;

public class IBankMessageFactory extends AbstractMessageFactory {

    /**
     * The Spring's context Id.
     */
    public static final String ID = "iBankMessageFactory";


    /* (non-Javadoc)
     * @see ru.psit.gateway.commands.AbstractMessageFactory#getSystem()
     */
    @Override
    public SystemType getSystem() {
        return SystemType.BLIZKO;
    }

    /**
     * @param requestMessageType incoming message.
     * @return responce message.
     */
    //TODO:rewrite a creating msg of response. use reflection
    public MessageType create_MsgIBankClientRs(MessageType requestMessageType) {
        // create response
        MsgIBankClientRs msgIBankClientRs = getObjectFactory().createMsgIBankClientRs();

        // response message type
        MessageType responseMessageType = newMessageType(requestMessageType, msgIBankClientRs);

        // type
        responseMessageType.setMessageType(MessageTypeType.CLIENT);
        responseMessageType.setActionType(requestMessageType.getActionType());

        // return
        // logMessage(msgIBankClientRs);
        return responseMessageType;
    }

    /**
     * @param requestMessageType incoming message.
     * @return responce message.
     */
    //TODO:rewrite a creating msg of response. use reflection
    public MessageType create_MsgIBankTransferAddRs(MessageType requestMessageType) {
        // create response
        MsgIBankTransferAddRs msgIBankTransferAddRs = getObjectFactory().createMsgIBankTransferAddRs();

        // response message type
        MessageType responseMessageType = newMessageType(requestMessageType, msgIBankTransferAddRs);

        // type
        responseMessageType.setMessageType(MessageTypeType.TRANSFER);
        responseMessageType.setActionType(requestMessageType.getActionType());

        // return
        // logMessage(msgIBankTransferAddRs);
        return responseMessageType;
    }

    //TODO:rewrite a creating msg of response. use reflection
    public MessageType create_MsgIBankTransferBlokRs(MessageType requestMessageType) {
        // create response
        MsgIBankTransferBlokRs msgIBankTransferBlokRs = getObjectFactory().createMsgIBankTransferBlokRs();

        // response message type
        MessageType responseMessageType = newMessageType(requestMessageType, msgIBankTransferBlokRs);

        // type
        responseMessageType.setMessageType(MessageTypeType.TRANSFER);
        responseMessageType.setActionType(requestMessageType.getActionType());

        // return
        // logMessage(msgIBankTransferBlokRs);
        return responseMessageType;
    }

    //TODO:rewrite a creating msg of response. use reflection
    public MessageType create_MsgIBankTransferDeblokRs(MessageType requestMessageType) {
        // create response
        MsgIBankTransferDeblokRs msgIBankTransferDeblokRs = getObjectFactory().createMsgIBankTransferDeblokRs();

        // response message type
        MessageType responseMessageType = newMessageType(requestMessageType, msgIBankTransferDeblokRs);

        // type
        responseMessageType.setMessageType(MessageTypeType.TRANSFER);
        responseMessageType.setActionType(requestMessageType.getActionType());

        // return
        // logMessage(msgIBankTransferDeblokRs);
        return responseMessageType;
    }

    //TODO:rewrite a creating msg of response. use reflection
    public MessageType create_MsgIBankTransferSearchRs(MessageType requestMessageType) {
        // create response
        MsgIBankTransferSearchRs msgIBankTransferSearchRs = getObjectFactory().createMsgIBankTransferSearchRs();

        // response message type
        MessageType responseMessageType = newMessageType(requestMessageType, msgIBankTransferSearchRs);

        // type
        responseMessageType.setMessageType(MessageTypeType.TRANSFER);
        responseMessageType.setActionType(requestMessageType.getActionType());

        // return
        // logMessage(msgIBankTransferSearchRs);
        return responseMessageType;
    }

    //TODO:rewrite a creating msg of response. use reflection
    public MessageType create_MsgIBankTransferPayRs(MessageType requestMessageType) {
        // create response
        MsgIBankTransferPayRs msgIBankTransferPayRs = getObjectFactory().createMsgIBankTransferPayRs();

        // response message type
        MessageType responseMessageType = newMessageType(requestMessageType, msgIBankTransferPayRs);

        // type
        responseMessageType.setMessageType(MessageTypeType.TRANSFER);
        responseMessageType.setActionType(requestMessageType.getActionType());

        // return
        // logMessage(msgIBankTransferPayRs);
        return responseMessageType;
    }

    //TODO:rewrite a creating msg of response. use reflection
    public MessageType create_MsgIBankTransferCommissionCalculateRs(
            MessageType requestMessageType) {
        // create response
        MsgIBankTransferCommissionCalculateRs msgIBankTransferCommissionCalculateRs =
                getObjectFactory().createMsgIBankTransferCommissionCalculateRs();

        // response message type
        MessageType responseMessageType = newMessageType(requestMessageType, msgIBankTransferCommissionCalculateRs);

        // type
        responseMessageType.setMessageType(MessageTypeType.TRANSFER);
        responseMessageType.setActionType(requestMessageType.getActionType());

        // return
        // logMessage(msgIBankTransferCommissionCalculateRs);
        return responseMessageType;
    }

    //TODO:rewrite a creating msg of response. use reflection
    public MessageType create_MsgIBankClientSearchRs(MessageType requestMessageType) {
        // create response
        MsgIBankClientSearchRs msgIBankClientSearchRs =
                getObjectFactory().createMsgIBankClientSearchRs();

        // response message type
        MessageType responseMessageType = newMessageType(requestMessageType, msgIBankClientSearchRs);

        // type
        responseMessageType.setMessageType(MessageTypeType.CLIENT);
        responseMessageType.setActionType(requestMessageType.getActionType());

        // return
        // logMessage(msgIBankClientSearchRs);
        return responseMessageType;
    }

    //TODO:rewrite a creating msg of response. use reflection
    public MessageType create_MsgIBankDirectoryCommissionRs(MessageType requestMessageType) {
        // create response
        MsgIBankDirectoryCommissionRs msgIBankDirectoryCommissionRs =
                getObjectFactory().createMsgIBankDirectoryCommissionRs();

        // response message type
        MessageType responseMessageType = newMessageType(requestMessageType, msgIBankDirectoryCommissionRs);

        // type
        responseMessageType.setMessageType(MessageTypeType.DIRECTORY);
        responseMessageType.setActionType(requestMessageType.getActionType());

        // return
        // logMessage(msgIBankDirectoryCommissionRs);
        return responseMessageType;
    }

    //TODO:rewrite a creating msg of response. use reflection
    public MessageType create_MsgIBankDirectoryCityRs(MessageType requestMessageType) {
        // create response
        MsgIBankDirectoryCityRs msgIBankDirectoryCityRs =
                getObjectFactory().createMsgIBankDirectoryCityRs();

        // response message type
        MessageType responseMessageType = newMessageType(requestMessageType, msgIBankDirectoryCityRs);

        // type
        responseMessageType.setMessageType(MessageTypeType.DIRECTORY);
        responseMessageType.setActionType(requestMessageType.getActionType());

        // return
        // logMessage(msgIBankDirectoryCityRs);
        return responseMessageType;
    }

    //TODO:rewrite a creating msg of response. use reflection
    public MessageType create_MsgIBankDirectoryCountryRs(MessageType requestMessageType) {
        // create response
        MsgIBankDirectoryCountryRs msgIBankDirectoryCountryRs =
                getObjectFactory().createMsgIBankDirectoryCountryRs();

        // response message type
        MessageType responseMessageType = newMessageType(requestMessageType, msgIBankDirectoryCountryRs);

        // type
        responseMessageType.setMessageType(MessageTypeType.DIRECTORY);
        responseMessageType.setActionType(requestMessageType.getActionType());

        // return
        // logMessage(msgIBankDirectoryCountryRs);
        return responseMessageType;
    }

    //TODO:rewrite a creating msg of response. use reflection
    public MessageType create_MsgIBankDirectoryFeaturesRs(MessageType requestMessageType) {
        // create response
        MsgIBankDirectoryFeaturesRs msgIBankDirectoryFeaturesRs =
                getObjectFactory().createMsgIBankDirectoryFeaturesRs();

        // response message type
        MessageType responseMessageType = newMessageType(requestMessageType, msgIBankDirectoryFeaturesRs);

        // type
        responseMessageType.setMessageType(MessageTypeType.DIRECTORY);
        responseMessageType.setActionType(requestMessageType.getActionType());

        // return
        // logMessage(msgIBankDirectoryFeaturesRs);
        return responseMessageType;
    }

    //TODO:rewrite a creating msg of response. use reflection
    public MessageType create_MsgIBankDirectoryLimitsRs(MessageType requestMessageType) {
        // create response
        MsgIBankDirectoryLimitsRs msgIBankDirectoryLimitsRs =
                getObjectFactory().createMsgIBankDirectoryLimitsRs();

        // response message type
        MessageType responseMessageType = newMessageType(requestMessageType, msgIBankDirectoryLimitsRs);

        // type
        responseMessageType.setMessageType(MessageTypeType.DIRECTORY);
        responseMessageType.setActionType(requestMessageType.getActionType());

        // return
        // logMessage(msgIBankDirectoryLimitsRs);
        return responseMessageType;
    }

    //TODO:rewrite a creating msg of response. use reflection
    public MessageType create_MsgIBankDirectoryAgentRs(MessageType requestMessageType) {
        // create response
        MsgIBankDirectoryAgentRs msgIBankDirectoryAgentRs =
                getObjectFactory().createMsgIBankDirectoryAgentRs();

        // response message type
        MessageType responseMessageType = newMessageType(requestMessageType, msgIBankDirectoryAgentRs);

        // type
        responseMessageType.setMessageType(MessageTypeType.DIRECTORY);
        responseMessageType.setActionType(requestMessageType.getActionType());

        // return
        // logMessage(msgIBankDirectoryAgentRs);
        return responseMessageType;
    }

    //TODO:rewrite a creating msg of response. use reflection
    public MessageType create_MsgIBankTransferBlokForChangeRs(MessageType requestMessageType) {
        // create response
        MsgIBankTransferBlokForChangeRs msgIBankTransferBlokForChangeRs =
                getObjectFactory().createMsgIBankTransferBlokForChangeRs();

        // response message type
        MessageType responseMessageType = newMessageType(requestMessageType, msgIBankTransferBlokForChangeRs);

        // type
        responseMessageType.setMessageType(MessageTypeType.TRANSFER);
        responseMessageType.setActionType(requestMessageType.getActionType());

        // return
        // logMessage(msgIBankTransferBlokForChangeRs);
        return responseMessageType;
    }

    //TODO:rewrite a creating msg of response. use reflection
    public MessageType create_MsgIBankTransferPayReversalRs(MessageType requestMessageType) {
        // create response
        MsgIBankTransferPayReversalRs msgIBankTransferPayReversalRs =
                getObjectFactory().createMsgIBankTransferPayReversalRs();

        // response message type
        MessageType responseMessageType = newMessageType(requestMessageType, msgIBankTransferPayReversalRs);

        // type
        responseMessageType.setMessageType(MessageTypeType.TRANSFER);
        responseMessageType.setActionType(requestMessageType.getActionType());

        // return
        // logMessage(msgIBankTransferPayReversalRs);
        return responseMessageType;
    }

    //TODO:rewrite a creating msg of response. use reflection
    public MessageType create_MsgIBankReportTransferRs(MessageType requestMessageType) {
        // create response
        MsgIBankReportTransferRs msgIBankReportTransferRs =
                getObjectFactory().createMsgIBankReportTransferRs();

        // response message type
        MessageType responseMessageType = newMessageType(requestMessageType, msgIBankReportTransferRs);

        // type
        responseMessageType.setMessageType(MessageTypeType.REPORT);
        responseMessageType.setActionType(requestMessageType.getActionType());

        // return
        // logMessage(msgIBankReportTransferRs);
        return responseMessageType;
    }

    //TODO:rewrite a creating msg of response. use reflection
    public MessageType create_MsgIBankReportPayTransferRs(MessageType requestMessageType) {
        // create response
        MsgIBankReportPayTransferRs msgIBankReportPayTransferRs =
                getObjectFactory().createMsgIBankReportPayTransferRs();

        // response message type
        MessageType responseMessageType = newMessageType(requestMessageType, msgIBankReportPayTransferRs);

        // type
        responseMessageType.setMessageType(MessageTypeType.REPORT);
        responseMessageType.setActionType(requestMessageType.getActionType());

        // return
        // logMessage(msgIBankReportPayTransferRs);
        return responseMessageType;
    }

    //TODO:rewrite a creating msg of response. use reflection
    public MessageType create_MsgIBankReportFinancicalRs(MessageType requestMessageType) {
        // create response
        MsgIBankReportFinancicalRs msgIBankReportFinancicalRs =
                getObjectFactory().createMsgIBankReportFinancicalRs();

        // response message type
        MessageType responseMessageType = newMessageType(requestMessageType, msgIBankReportFinancicalRs);

        // type
        responseMessageType.setMessageType(MessageTypeType.REPORT);
        responseMessageType.setActionType(requestMessageType.getActionType());

        // return
        // logMessage(msgIBankReportFinancicalRs);
        return responseMessageType;
    }

    //TODO:rewrite a creating msg of response. use reflection
    public MessageType create_MsgIBankTransferArhivRs(MessageType requestMessageType) {
        // create response
        MsgIBankTransferArhivRs msgIBankTransferArhivRs =
                getObjectFactory().createMsgIBankTransferArhivRs();

        // response message type
        MessageType responseMessageType = newMessageType(requestMessageType, msgIBankTransferArhivRs);

        // type
        responseMessageType.setMessageType(MessageTypeType.ARHIV);
        responseMessageType.setActionType(requestMessageType.getActionType());

        // return
        // logMessage(msgIBankTransferArhivRs);
        return responseMessageType;
    }

    //TODO:rewrite a creating msg of response. use reflection
    public MessageType create_MsgIBankTransferChangeRs(MessageType requestMessageType) {
        // create response
        MsgIBankTransferChangeRs msgIBankTransferChangeRs =
                getObjectFactory().createMsgIBankTransferChangeRs();

        // response message type
        MessageType responseMessageType = newMessageType(requestMessageType, msgIBankTransferChangeRs);

        // type
        responseMessageType.setMessageType(MessageTypeType.TRANSFER);
        responseMessageType.setActionType(requestMessageType.getActionType());

        // return
        // logMessage(msgIBankTransferChangeRs);
        return responseMessageType;
    }

    //TODO:rewrite a creating msg of response. use reflection
    public MessageType create_MsgIBankTransferDeleteRs(MessageType requestMessageType) {
        // create response
        MsgIBankTransferDeleteRs msgIBankTransferDeleteRs =
                getObjectFactory().createMsgIBankTransferDeleteRs();

        // response message type
        MessageType responseMessageType = newMessageType(requestMessageType, msgIBankTransferDeleteRs);

        // type
        responseMessageType.setMessageType(MessageTypeType.TRANSFER);
        responseMessageType.setActionType(requestMessageType.getActionType());

        // return
        // logMessage(msgIBankTransferDeleteRs);
        return responseMessageType;
    }

    /**
     * @param requestMessageType incoming message.
     * @return responce message.
     */
    //TODO:rewrite a creating msg of response. use reflection
    public MessageType create_MsgIBankClientTemplateAddRs(MessageType requestMessageType) {
        // create response
        MsgIBankClientTemplateAddRs msgIBankClientTemplateAddRs = getObjectFactory().createMsgIBankClientTemplateAddRs();

        // response message type
        MessageType responseMessageType = newMessageType(requestMessageType, msgIBankClientTemplateAddRs);

        // type
        responseMessageType.setMessageType(MessageTypeType.TRANSFER);
        responseMessageType.setActionType(requestMessageType.getActionType());

        // return
        // logMessage(msgIBankClientTemplateDelRs);
        return responseMessageType;
    }


    /**
     * @param requestMessageType incoming message.
     * @return responce message.
     */
    //TODO:rewrite a creating msg of response. use reflection
    public MessageType create_MsgIBankClientTemplateDelRs(MessageType requestMessageType) {
        // create response
        MsgIBankClientTemplateDelRs msgIBankClientTemplateDelRs = getObjectFactory().createMsgIBankClientTemplateDelRs();

        // response message type
        MessageType responseMessageType = newMessageType(requestMessageType, msgIBankClientTemplateDelRs);

        // type
        responseMessageType.setMessageType(MessageTypeType.TRANSFER);
        responseMessageType.setActionType(requestMessageType.getActionType());

        // return
        // logMessage(msgIBankClientTemplateDelRs);
        return responseMessageType;
    }

    /**
     * @param requestMessageType incoming message.
     * @return responce message.
     */
    //TODO:rewrite a creating msg of response. use reflection
    public MessageType create_MsgIBankAddressTransferListRs(MessageType requestMessageType) {
        // create response
        MsgIBankAddressTransferListRs msgIBankAddressTransferListRs = getObjectFactory().createMsgIBankAddressTransferListRs();

        // response message type
        MessageType responseMessageType = newMessageType(requestMessageType, msgIBankAddressTransferListRs);

        // type
        responseMessageType.setMessageType(MessageTypeType.TRANSFER);
        responseMessageType.setActionType(requestMessageType.getActionType());

        // return
        // logMessage(msgIBankClientTemplateDelRs);
        return responseMessageType;
    }



    /**
     * @param requestMessageType incoming message.
     * @return responce message.
     */
    //TODO:rewrite a creating msg of response. use reflection
    public MessageType create_MsgIBankReportTransferListRs(MessageType requestMessageType) {
        // create response
        MsgIBankReportTransferListRs msgIBankReportTransferListRs = getObjectFactory().createMsgIBankReportTransferListRs();

        // response message type
        MessageType responseMessageType = newMessageType(requestMessageType, msgIBankReportTransferListRs);

        // type
        responseMessageType.setMessageType(MessageTypeType.REPORT);
        responseMessageType.setActionType(requestMessageType.getActionType());


        return responseMessageType;
    }



    /**
     * @param requestMessageType incoming message.
     * @return responce message.
     */

    //TODO:rewrite a creating msg of response. use reflection
    public MessageType create_MsgIBankTransferConfirmRs(MessageType requestMessageType) {
        // create response
        MsgIBankTransferConfirmRs msgIBankTransferConfirmRs = getObjectFactory().createMsgIBankTransferConfirmRs();

        // response message type
        MessageType responseMessageType = newMessageType(requestMessageType, msgIBankTransferConfirmRs);

        // type
        responseMessageType.setMessageType(MessageTypeType.TRANSFER);
        responseMessageType.setActionType(requestMessageType.getActionType());

        // return
        // logMessage(msgIBankClientTemplateDelRs);
        return responseMessageType;
    }


}
