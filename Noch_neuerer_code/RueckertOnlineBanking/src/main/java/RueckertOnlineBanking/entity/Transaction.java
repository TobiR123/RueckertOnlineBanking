package RueckertOnlineBanking.entity;


import RueckertOnlineBanking.entity.util.GeneratedIdEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Transaction extends GeneratedIdEntity {


    @XmlTransient
    @ManyToOne
    private Account sender;
    @XmlTransient
    @ManyToOne
    private Account receiver;
    private double amount;
    private String description;
    private Date timestamp;
    @XmlTransient
    @OneToOne(mappedBy = "transaction")
    private TAN tan;

    public Transaction() {
        super.id = getId();
    }

    public Transaction(Account sender, Account receiver, double amount, String description, TAN tan) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.description = description;
        this.tan = tan;
        this.timestamp = new Date();
    }

    @Override
    public String toString() {
        return "Sender: " +
                this.sender +
                " Receiver: " +
                this.receiver +
                " Amount: " +
                this.amount +
                " Description: " +
                this.description +
                " TAN: " +
                this.tan +
                " Timestamp: " +
                this.timestamp;
    }

    public Long getId() {
        return super.getId();
    }

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public Account getReceiver() {
        return receiver;
    }

    public void setReceiver(Account receiver) {
        this.receiver = receiver;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public TAN getTan() {
        return tan;
    }

    public void setTan(TAN tan) {
        this.tan = tan;
    }

}
