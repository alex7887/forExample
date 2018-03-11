package com.ibank.service.external.forsaj;

public class ForsajService extends AbstractService {

    /**
     * The logger.
     */
    private static final Log LOG = LogFactory.getLog(ForsajService.class);

    public ForsajService(MessageType messageType) {
        super(messageType);
        // TODO Auto-generated constructor stub
    }

    private ForsajServiceParameters forsajServiceParameters;

    private HttpClient httpClient;
    private JAXBElementFactory elementFactory;
    private DOMDocumentFactory documentFactory;
    private XmlParametersHolder xmlParametersHolder;
    private Schema schema;

    @Override
    public Object execute() throws GateWayException {

        String response = null;

        JAXBElement<MessageType> element = this.getElementFactory()
                .createElement(getMessageType());

        LOG.info("getMessageType="+getMessageType());

        StringWriter stringWriter = new StringWriter();
        Document document = null;
        try {
            document = getDocumentFactory().newDocument(element,
                    getXmlParametersHolder().getJaxbContextPath(), getSchema());
        } catch (JAXBException exception) {
            String message = GateWayMessageSource.GATEWAY_ERROR_PREFIX
                    + getMessageSource()
                    .getMessage(GateWayMessageSource.ERROR_DURING_CONVERTING_JAXB_TO_DOM);
            LOG.error(message, exception);
            throw new GateWayException(message, exception);
            // throw new XMLCreatingException(message, exception, element);
        }
        try {

            getDocumentFactory().writeDocument(document, stringWriter);

        } catch (Exception exception) {
            String message = GateWayMessageSource.GATEWAY_ERROR_PREFIX
                    + getMessageSource()
                    .getMessage(
                            GateWayMessageSource.ERROR_DURING_CONVERTING_DOM_TO_XML);
            LOG.error(message, exception);
            throw new GateWayException(message, exception);
        }

        String request = stringWriter.toString();


        response = httpClient.post(request, forsajServiceParameters
                .getParameters(), forsajServiceParameters.getUrlSuffix());

        JAXBElement<MessageType> elementResponse =handlerMessage(response);
        LOG.debug("Response  parsing XML: " + elementResponse.toString());
        return elementResponse;
    }


    private JAXBElement<MessageType> handlerMessage(String response) throws GateWayException{
        // XML parsing
        LOG.debug("start parsing XML: " + response);
        Document document = null;
        try {
            // get DOM document from InputStream
            document = documentFactory.newDocument(response);
        } catch(Exception exception) {

            if(response.contains("<xml")){
                String message = getMessageSource().getMessage(GateWayMessageSource.BAD_XML_FORMAT);
                LOG.error(message, exception);
                throw new GateWayException(message, exception);
            }
            else {
                LOG.error(getMessageSource().getMessage(GateWayMessageSource.ERROR_BAD_RESPONSE_FROM_FORSAJ)+ response);
                throw new GateWayException(getMessageSource().getMessage(GateWayMessageSource.ERROR_BAD_RESPONSE_FROM_FORSAJ)+ response);
            }

        }

// convert from DOM to JAXB
        JAXBElement<MessageType> element = null;
        try {
            element = elementFactory.newElement(document, getXmlParametersHolder().getJaxbContextPath(), getSchema());


        } catch(JAXBException exception) {
            String message = GateWayMessageSource.GATEWAY_ERROR_PREFIX + getMessageSource().getMessage(GateWayMessageSource.ERROR_DURING_CONVERTING_DOM_TO_JAXB);
            LOG.error(message, exception);
            throw new GateWayException(message, exception);
        }
        return element;

    }

    /**
     * @param forsajServiceParameters
     *            the forsajServiceParameters to set
     */
    public void setForsajServiceParameters(
            ForsajServiceParameters forsajServiceParameters) {
        this.forsajServiceParameters = forsajServiceParameters;
    }

    /**
     * @return the forsajServiceParameters
     */
    public ForsajServiceParameters getForsajServiceParameters() {
        return forsajServiceParameters;
    }

    /**
     * @param httpClient
     *            the httpClient to set
     */
    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * @return the httpClient
     */
    public HttpClient getHttpClient() {
        return httpClient;
    }

    /**
     * @param elementFactory
     *            the elementFactory to set
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
     * @param documentFactory
     *            the documentFactory to set
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
     */
    public void setSchema(Schema schema) {
        this.schema = schema;
    }


    /**
     * @return the schema
     */
    public Schema getSchema() {
        return schema;
    }

}
