package RueckertOnlineBanking.service;

import RueckertOnlineBanking.loggerFactory.LoggerFactory;
import richterMoneyTransport.TransportOrder;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

@Alternative
@RequestScoped
public class MockMoneyTransportService implements MoneyTransportServiceIF {

    @Inject
    private LoggerFactory loggerFactory;
    private Logger logger;

    @PostConstruct
    public void init() {
        try {
            this.logger = this.loggerFactory.create();
        } catch (Exception e) {
            // Throw exception to one level above.
            throw e;
        }
    }

    @Override
    public TransportOrder callMoneyTransportCompany(RueckertOnlineBanking.entity.Customer receiver, double amount, Date deliveryDate) {
        // Since this is only the fake money transport service, we only fill the attributes that are needed to store the customer transport order entity in the database.
        // Because the sender, receiver, etc. will not be stored in db, we do not have to set them here.
        richterMoneyTransport.TransportOrder newTransportOrder = new richterMoneyTransport.TransportOrder();
        newTransportOrder.setAmount(amount);

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(deliveryDate);

        try {
            XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
            newTransportOrder.setExecutionDay(date);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }

        this.logger.log(Level.INFO, "Successful created dummy transport order in mock service.");
        return this.placeOrder(newTransportOrder);
    }


    @Override
    public TransportOrder placeOrder(TransportOrder transportOrder) {
        // generate dummy tracking number for transport order record.
        long dummyId = this.generateDummyId();
        transportOrder.setId(dummyId);
        return transportOrder;
    }

    private long generateDummyId() {
        String actualDummyId = "";
        for (int i = 0; i < 3; i++) {
            // Generate numbers between 1 and 10. The 0 is excluded so that no leading 0s can appear.
            long digit = ThreadLocalRandom.current().nextLong(1, 10);
            actualDummyId = actualDummyId + String.valueOf(digit);
        }
        return Long.parseLong(actualDummyId);
    }
}
