package RueckertOnlineBanking.service;
import richterMoneyTransport.Address;
import richterMoneyTransport.TransportOrder;
import richterMoneyTransport.TransportOrderService;
import richterMoneyTransport.TransportOrderServiceService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.jms.*;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Alternative
@RequestScoped
public class MoneyTransportServiceRemote implements  MoneyTransportServiceIF{

    private TransportOrderServiceService transportOrderService;
    private TransportOrderService stub;

    @PostConstruct
    public void init() {
        this.transportOrderService = new TransportOrderServiceService();
        this.stub = this.transportOrderService.getTransportOrderServicePort();
    }

    @Override
    // Own method to prepare all needed objects for the order placement.
    public TransportOrder callMoneyTransportCompany(RueckertOnlineBanking.entity.Customer receiver, double amount) {

        // The sender is the owner of the online banking system.
        richterMoneyTransport.Customer sender = new richterMoneyTransport.Customer();
        sender.setName("Tobias Rueckert");
        richterMoneyTransport.Address senderAddress = new Address();
        senderAddress.setStreet("Salzweg");
        senderAddress.setHousenumber("3");
        senderAddress.setZipCode(92318);
        senderAddress.setCity("Neumarkt in der Oberpfalz");
        sender.setAddress(senderAddress);
        sender.setEmailAddress("tobias.rueckert@st.oth-regensburg.de");

        richterMoneyTransport.Customer recipient = new richterMoneyTransport.Customer();
        recipient.setName(receiver.getFirstname() + receiver.getLastname());
        richterMoneyTransport.Address recipientAddress = new richterMoneyTransport.Address();
        recipientAddress.setStreet(receiver.getAddress().getStreet());
        recipientAddress.setHousenumber(receiver.getAddress().getHouseNumber());
        recipientAddress.setZipCode(receiver.getAddress().getPostcode());
        recipientAddress.setCity(receiver.getAddress().getPlace());
        recipient.setAddress(recipientAddress);
        recipient.setEmailAddress(receiver.geteMailAddress().getMailAddress());


        TransportOrder newTransportOrder = new TransportOrder();
        newTransportOrder.setConsignor(sender);
        newTransportOrder.setRecipient(recipient);
        newTransportOrder.setAmount(amount);

        GregorianCalendar c = new GregorianCalendar();
        c.setTime(new Date());
        try {
            XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            newTransportOrder.setExecutionDay(date2);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        } finally {
            return this.placeOrder(newTransportOrder);
        }
    }


    @Override
    public TransportOrder placeOrder(TransportOrder transportOrder) {
        // Call the remote method from richter money transport project.
        try {
            return stub.placeOrder(transportOrder);
        } catch(Exception e){
            // TODO: THROW CUSTOM EXCEPTION AND CATCH IT IN THE MODEL ...
            // IRGENDWIE DIE EXCEPTION VERARBEITEN! Auf Fehlerseite weiterleiten usw.
            // VOM RETURN OBJEKT DAS WICHTIGSTE ALS PRIMITIVER DATENTYP IM CUSTOMER SPEICHERN; Z.B. die orderId als String im Customer!
            return null;
        }
    }
}