
package richterMoneyTransport;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für placeOrder complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="placeOrder"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="transportOrder" type="{http://service.richter.de/}transportOrder" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "placeOrder", propOrder = {
    "transportOrder"
})
public class PlaceOrder {

    protected TransportOrder transportOrder;

    /**
     * Ruft den Wert der transportOrder-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TransportOrder }
     *     
     */
    public TransportOrder getTransportOrder() {
        return transportOrder;
    }

    /**
     * Legt den Wert der transportOrder-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportOrder }
     *     
     */
    public void setTransportOrder(TransportOrder value) {
        this.transportOrder = value;
    }

}
