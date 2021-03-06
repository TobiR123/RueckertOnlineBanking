
package richterMoneyTransport;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.1-SNAPSHOT
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "TransportOrderServiceService", targetNamespace = "http://service.richter.de/", wsdlLocation = "http://im-lamport:8080/richtermoneytransport-0.1/TransportOrderService?wsdl")
public class TransportOrderServiceService
    extends Service
{

    private final static URL TRANSPORTORDERSERVICESERVICE_WSDL_LOCATION;
    private final static WebServiceException TRANSPORTORDERSERVICESERVICE_EXCEPTION;
    private final static QName TRANSPORTORDERSERVICESERVICE_QNAME = new QName("http://service.richter.de/", "TransportOrderServiceService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://im-lamport:8080/richtermoneytransport-0.1/TransportOrderService?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        TRANSPORTORDERSERVICESERVICE_WSDL_LOCATION = url;
        TRANSPORTORDERSERVICESERVICE_EXCEPTION = e;
    }

    public TransportOrderServiceService() {
        super(__getWsdlLocation(), TRANSPORTORDERSERVICESERVICE_QNAME);
    }

    public TransportOrderServiceService(WebServiceFeature... features) {
        super(__getWsdlLocation(), TRANSPORTORDERSERVICESERVICE_QNAME, features);
    }

    public TransportOrderServiceService(URL wsdlLocation) {
        super(wsdlLocation, TRANSPORTORDERSERVICESERVICE_QNAME);
    }

    public TransportOrderServiceService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, TRANSPORTORDERSERVICESERVICE_QNAME, features);
    }

    public TransportOrderServiceService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public TransportOrderServiceService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns TransportOrderService
     */
    @WebEndpoint(name = "TransportOrderServicePort")
    public TransportOrderService getTransportOrderServicePort() {
        return super.getPort(new QName("http://service.richter.de/", "TransportOrderServicePort"), TransportOrderService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns TransportOrderService
     */
    @WebEndpoint(name = "TransportOrderServicePort")
    public TransportOrderService getTransportOrderServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://service.richter.de/", "TransportOrderServicePort"), TransportOrderService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (TRANSPORTORDERSERVICESERVICE_EXCEPTION!= null) {
            throw TRANSPORTORDERSERVICESERVICE_EXCEPTION;
        }
        return TRANSPORTORDERSERVICESERVICE_WSDL_LOCATION;
    }

}
