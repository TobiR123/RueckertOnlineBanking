package RueckertOnlineBanking.entity;


import RueckertOnlineBanking.entity.util.GeneratedIdEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Transaction extends GeneratedIdEntity {



    @ManyToOne
    private Account sender;
    @ManyToOne
    private Account receiver;
    private double amount;
    private String description;
    private Date timestamp;
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

        checkLiquidityOfSender();
    }

    private boolean checkLiquidityOfSender() {
        if(sender.getCredit() - this.amount < 0) {
            return false;
        }
        return true;
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

    @Override
    public boolean equals(Object o){
        if( o == null) {
            return false;
        }
        else if(getClass() != o.getClass()) {
            return false;
        }
        final Transaction other = (Transaction) o;
        if(!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        if(this.id == null) {
            return 0;
        } else {
            return this.id.hashCode();
        }
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
