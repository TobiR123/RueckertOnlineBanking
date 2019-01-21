package RueckertOnlineBanking.service;

import richterMoneyTransport.TransportOrder;

import java.util.Date;

public interface MoneyTransportServiceIF {

    TransportOrder placeOrder(TransportOrder transportOrder);

    TransportOrder callMoneyTransportCompany(RueckertOnlineBanking.entity.Customer receiver, double amount, Date deliveryDate);

}
