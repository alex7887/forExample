package com.ibank.service.command.type;

import com.ibank.service.command.IBankCommand;

public class IBankCommandFactory extends AbstractCommandFactory implements org.springframework.beans.factory.InitializingBean {

    /**
     * The Spring's context Id.
     */
    public static final String ID = "iBankCommandFactory";

    /**
     * The logger.
     */
    private static final Log LOG = LogFactory.getLog(IBankCommandFactory.class);

    /**
     * The reference to message factory.
     */
    private IBankMessageFactory iBankMessageFactory;

    /**
     * The reference to XML2SQL converter.
     */
    private Xml2SqlConverter xml2SqlConverter;

    /**
     * The reference to SQL2XML converter.
     */
    private Sql2XmlConverter sql2XmlConverter;

    private Map<String, AbstractServiceFactory> providerFactories =new HashMap<String, AbstractServiceFactory>();

    private DOMDocumentFactory documentFactory;
    private SchemaFactory schemaFactory;
    private XmlParametersHolder xmlParametersHolder;
    private HttpClientFactory httpClientFactory;


    /**
     * @return the iBankMessageFactory
     */
    public IBankMessageFactory getiBankIBank() {
        return iBankMessageFactory;
    }

    /**
     * @param iBankMessageFactory
     *            the iBankMessageFactory to set
     */
    @Required
    public void setIBankMessageFactory(
            IBankMessageFactory iBankMessageFactory) {
        this.iBankMessageFactory = iBankMessageFactory;
    }

    /**
     * @return the xml2SqlConverter
     */
    public Xml2SqlConverter getXml2SqlConverter() {
        return xml2SqlConverter;
    }

    /**
     * @param xml2SqlConverter
     *            the xml2SqlConverter to set
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
     * @param sql2XmlConverter
     *            the sql2XmlConverter to set
     */
    @Required
    public void setSql2XmlConverter(Sql2XmlConverter sql2XmlConverter) {
        this.sql2XmlConverter = sql2XmlConverter;
    }

    /**
     * Creates command for IBank request.
     *
     * @param messageType
     *            incoming message type.
     * @return command for execution.
     * @throws GateWayException
     *             internal error for sending back.
     */
    public IBankCommand createCommand(MessageType messageType)	throws GateWayException {
        //create command
        IBankCommand iBankCommand = (IBankCommand) super
                .createCommand(messageType);

        // initialize
        iBankCommand.setMessageFactory(iBankMessageFactory);
        iBankCommand.setAgentValidatorFactory(getAgentValidatorFactory());
        iBankCommand.setSql2XmlConverter(this.getSql2XmlConverter());
        iBankCommand.setXml2SqlConverter(this.getXml2SqlConverter());
        iBankCommand.setStoredFunctionFactory(this.getStoredFunctionFactory());
        iBankCommand.setMessageSource(getMessageSource());
        iBankCommand.setPropertiesHolder(this.getPropertiesHolder());
        //new
        iBankCommand.setProviderFactories(this.getProviderFactories());


        return iBankCommand;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        //System.out.println("this will be called after all the setters are done.");
        for(String key :getProviderFactories().keySet()){
            Object object=getProviderFactories().get(key);
            if(object instanceof ForsajServiceFactory){
                ((ForsajServiceFactory)object).setDocumentFactory(documentFactory);

                ((ForsajServiceFactory)object).setElementFactory(getElementFactory());
                ((ForsajServiceFactory)object).setHttpClientFactory(getHttpClientFactory());
                ((ForsajServiceFactory)object).setMessageSource(getMessageSource());
                ((ForsajServiceFactory)object).setXmlParametersHolder(xmlParametersHolder);
            }
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

    /**
     * @param documentFactory the documentFactory to set
     */
    public void setDocumentFactory(DOMDocumentFactory documentFactory) {
        this.documentFactory = documentFactory;
    }

    /**
     * @return the documentFactory
     */
    public DOMDocumentFactory getDocumentFactory() {
        return documentFactory;
    }

    /**
     * @param schemaFactory the schemaFactory to set
     */
    public void setSchemaFactory(SchemaFactory schemaFactory) {
        this.schemaFactory = schemaFactory;
    }

    /**
     * @return the schemaFactory
     */
    public SchemaFactory getSchemaFactory() {
        return schemaFactory;
    }

    /**
     * @param xmlParametersHolder the xmlParametersHolder to set
     */
    public void setXmlParametersHolder(XmlParametersHolder xmlParametersHolder) {
        this.xmlParametersHolder = xmlParametersHolder;
    }

    /**
     * @return the xmlParametersHolder
     */
    public XmlParametersHolder getXmlParametersHolder() {
        return xmlParametersHolder;
    }

    /**
     * @param httpClientFactory the httpClientFactory to set
     */
    public void setHttpClientFactory(HttpClientFactory httpClientFactory) {
        this.httpClientFactory = httpClientFactory;
    }

    /**
     * @return the httpClientFactory
     */
    public HttpClientFactory getHttpClientFactory() {
        return httpClientFactory;
    }
}
