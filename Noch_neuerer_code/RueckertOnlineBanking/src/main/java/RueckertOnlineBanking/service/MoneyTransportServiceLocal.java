package RueckertOnlineBanking.service;

import RueckertOnlineBanking.entity.Customer;
import richterMoneyTransport.TransportOrder;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;

@Alternative
@RequestScoped
public class MoneyTransportServiceLocal implements MoneyTransportServiceIF {
    @Override
    public TransportOrder placeOrder(TransportOrder transportOrder) {
        return null;
    }

    @Override
    public TransportOrder callMoneyTransportCompany(Customer receiver, double amount) {
        return null;
    }
}
