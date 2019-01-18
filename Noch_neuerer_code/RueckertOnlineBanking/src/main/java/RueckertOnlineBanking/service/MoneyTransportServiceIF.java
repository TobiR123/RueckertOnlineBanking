package RueckertOnlineBanking.service;

import richterMoneyTransport.TransportOrder;

import javax.jws.WebParam;

public interface MoneyTransportServiceIF {

    TransportOrder placeOrder(TransportOrder transportOrder);
    TransportOrder callMoneyTransportCompany(RueckertOnlineBanking.entity.Customer receiver, double amount);

}
