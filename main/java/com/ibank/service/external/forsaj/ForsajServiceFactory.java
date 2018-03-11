package com.ibank.service.external.forsaj;

public class ForsajServiceFactory extends AbstractServiceFactory{

    private static final Log LOG = LogFactory.getLog(ForsajServiceFactory.class);

    private HttpClientFactory httpClientFactory;
    private ForsajServiceParameters forsajServiceParameters;
    private ForsajServiceConnectionParameters forsajServiceConnectionParameters;
    private JAXBElementFactory elementFactory;
    private DOMDocumentFactory documentFactory;
    private XmlParametersHolder xmlParametersHolder;
    //private Schema schema;



    @Override
    public AbstractService createService(Object request)
            throws GateWayException {

        ForsajService forsajService =new ForsajService((MessageType)request);
        //LOG.info("SERVICE FACTORY="+request);
        forsajService.setMessageSource(getMessageSource());

        String urlForsaj = forsajServiceParameters.getUrl();

        //deltaService.setPropertiesHolder(propertiesHolder);
        forsajService.setHttpClient(httpClientFactory.getClient(urlForsaj, forsajServiceConnectionParameters.getTimeOut(), forsajServiceConnectionParameters.getRetry(),  null, forsajServiceConnectionParameters.getCharset(), forsajServiceParameters.getPort()));
        forsajService.setForsajServiceParameters(forsajServiceParameters);
        forsajService.setElementFactory(elementFactory);
        forsajService.setDocumentFactory(documentFactory);
        forsajService.setXmlParametersHolder(xmlParametersHolder);
        //forsajService.setSchema(schema);
        //LOG.info("SERVICE FACTORY 2="+forsajService.getMessageType());

        //forsajService.setCharset(getCharset());

        return forsajService;
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

    /**
     * @param forsajServiceParameters the forsajServiceParameters to set
     */
    public void setForsajServiceParameters(ForsajServiceParameters forsajServiceParameters) {
        this.forsajServiceParameters = forsajServiceParameters;
    }

    /**
     * @return the forsajServiceParameters
     */
    public ForsajServiceParameters getForsajServiceParameters() {
        return forsajServiceParameters;
    }

    /**
     * @param forsajServiceConnectionParameters the forsajServiceConnectionParameters to set
     */
    public void setForsajServiceConnectionParameters(
            ForsajServiceConnectionParameters forsajServiceConnectionParameters) {
        this.forsajServiceConnectionParameters = forsajServiceConnectionParameters;
    }

    /**
     * @return the forsajServiceConnectionParameters
     */
    public ForsajServiceConnectionParameters getForsajServiceConnectionParameters() {
        return forsajServiceConnectionParameters;
    }

    /**
     * @param elementFactory the elementFactory to set
     */
    public void setElementFactory(JAXBElementFactory elementFactory) {
        this.elementFactory = elementFactory;
    }

    /**
     * @return the elementFactory
     */
    public JAXBElementFactory getElementFactory() {
        return elementFactory;
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
     * @param schema the schema to set
     *//*
	public void setSchema(Schema schema) {
		this.schema = schema;
	}

	*//**
     * @return the schema
     *//*
	public Schema getSchema() {
		return schema;
	}
*/
}
