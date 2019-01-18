
package richterMoneyTransport;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java-Klasse für transportOrder complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="transportOrder"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://service.richter.de/}generatedIdEntity"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="consignor" type="{http://service.richter.de/}customer" minOccurs="0"/&gt;
 *         &lt;element name="recipient" type="{http://service.richter.de/}customer" minOccurs="0"/&gt;
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="executionDay" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="status" type="{http://service.richter.de/}transportStatus" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "transportOrder", propOrder = {
    "consignor",
    "recipient",
    "amount",
    "executionDay",
    "status"
})
public class TransportOrder
    extends GeneratedIdEntity
{

    protected Customer consignor;
    protected Customer recipient;
    protected double amount;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar executionDay;
    protected TransportStatus status;

    /**
     * Ruft den Wert der consignor-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Customer }
     *     
     */
    public Customer getConsignor() {
        return consignor;
    }

    /**
     * Legt den Wert der consignor-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Customer }
     *     
     */
    public void setConsignor(Customer value) {
        this.consignor = value;
    }

    /**
     * Ruft den Wert der recipient-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Customer }
     *     
     */
    public Customer getRecipient() {
        return recipient;
    }

    /**
     * Legt den Wert der recipient-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Customer }
     *     
     */
    public void setRecipient(Customer value) {
        this.recipient = value;
    }

    /**
     * Ruft den Wert der amount-Eigenschaft ab.
     * 
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Legt den Wert der amount-Eigenschaft fest.
     * 
     */
    public void setAmount(double value) {
        this.amount = value;
    }

    /**
     * Ruft den Wert der executionDay-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExecutionDay() {
        return executionDay;
    }

    /**
     * Legt den Wert der executionDay-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExecutionDay(XMLGregorianCalendar value) {
        this.executionDay = value;
    }

    /**
     * Ruft den Wert der status-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TransportStatus }
     *     
     */
    public TransportStatus getStatus() {
        return status;
    }

    /**
     * Legt den Wert der status-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportStatus }
     *     
     */
    public void setStatus(TransportStatus value) {
        this.status = value;
    }

}
