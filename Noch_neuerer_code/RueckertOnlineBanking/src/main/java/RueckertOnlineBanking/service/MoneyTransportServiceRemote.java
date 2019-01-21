package RueckertOnlineBanking.service;

import RueckertOnlineBanking.loggerFactory.LoggerFactory;
import richterMoneyTransport.Address;
import richterMoneyTransport.TransportOrder;
import richterMoneyTransport.TransportOrderService;
import richterMoneyTransport.TransportOrderServiceService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

@Alternative
@RequestScoped
public class MoneyTransportServiceRemote implements MoneyTransportServiceIF {

    private TransportOrderServiceService transportOrderService;
    private TransportOrderService stub;

    @Inject
    private LoggerFactory loggerFactory;
    private Logger logger;

    @PostConstruct
    public void init() {
        try {
            this.transportOrderService = new TransportOrderServiceService();
            this.stub = this.transportOrderService.getTransportOrderServicePort();
            this.logger = this.loggerFactory.create();
        } catch (Exception e) {
            // Throw exception to one level above.
            throw e;
        }
    }

    @Override
    // Own method to prepare all needed objects for the order placement.
    public TransportOrder callMoneyTransportCompany(RueckertOnlineBanking.entity.Customer receiver, double amount, Date deliveryDate) {
        // The sender is the owner of the online banking system.
        richterMoneyTransport.Customer sender = new richterMoneyTransport.Customer();
        sender.setName("Rückert Online Banking");
        richterMoneyTransport.Address senderAddress = new Address();
        senderAddress.setStreet("Straubinger Strasse");
        senderAddress.setHousenumber("3");
        senderAddress.setZipCode(92318);
        senderAddress.setCity("Neumarkt in der Oberpfalz");
        sender.setAddress(senderAddress);
        sender.setEmailAddress("rueckertonlinebanking@sampleProvider.de");

        // The receiver is the currently logged in customer.
        richterMoneyTransport.Customer recipient = new richterMoneyTransport.Customer();
        recipient.setName(receiver.getFirstname() + " " + receiver.getLastname());
        richterMoneyTransport.Address recipientAddress = new richterMoneyTransport.Address();
        recipientAddress.setStreet(receiver.getAddress().getStreet());
        recipientAddress.setHousenumber(receiver.getAddress().getHouseNumber());
        recipientAddress.setZipCode(receiver.getAddress().getPostcode());
        recipientAddress.setCity(receiver.getAddress().getPlace());
        recipient.setAddress(recipientAddress);
        recipient.setEmailAddress(receiver.geteMailAddress().getMailAddress());

        // Build a new transport order.
        TransportOrder newTransportOrder = new TransportOrder();
        newTransportOrder.setConsignor(sender);
        newTransportOrder.setRecipient(recipient);
        newTransportOrder.setAmount(amount);

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(deliveryDate);

        richterMoneyTransport.TransportOrder createdOrder = new TransportOrder();
        try {
            XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
            newTransportOrder.setExecutionDay(date);

            // Call the remote method from richter money transport.
            createdOrder = this.placeOrder(newTransportOrder);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            this.logger.log(Level.SEVERE, "Something went wrong while calling richtermoneytransport ...");
            throw e;
        }
        return createdOrder;
    }


    @Override
    public TransportOrder placeOrder(TransportOrder transportOrder) {
        // Call the remote method from richter money transport project.
        try {
            return stub.placeOrder(transportOrder);
        } catch (Exception e) {
            throw e;
        }
    }
}