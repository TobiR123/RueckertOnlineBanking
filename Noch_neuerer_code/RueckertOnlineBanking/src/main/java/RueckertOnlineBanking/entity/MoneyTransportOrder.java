package RueckertOnlineBanking.entity;

import RueckertOnlineBanking.entity.util.GeneratedIdEntity;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class MoneyTransportOrder extends GeneratedIdEntity {
/*
This class represents the local equivalent of the TransportOrder Entity located in richtermoneytransport package.
Therefore, only the most important attributes are saved in this entity, to be able to save them to a customer and show them in the web application.
*/

    // ##### ATTRIBUTES ##### //
    private long transportOrderNumber;
    private double amount;
    private Date deliveryDate;

    public MoneyTransportOrder() {
    }

    public MoneyTransportOrder(long transportOrderNumber, double amount, Date deliveryDate) {
        this.transportOrderNumber = transportOrderNumber;
        this.amount = amount;
        this.deliveryDate = deliveryDate;
    }


    // ##### GETTER AND SETTER ##### //
    public long getTransportOrderNumber() {
        return transportOrderNumber;
    }

    public void setTransportOrderNumber(long transportOrderNumber) {
        this.transportOrderNumber = transportOrderNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }


}
