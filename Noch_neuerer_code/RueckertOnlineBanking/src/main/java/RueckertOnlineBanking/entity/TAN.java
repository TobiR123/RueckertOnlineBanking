package RueckertOnlineBanking.entity;


import RueckertOnlineBanking.entity.util.GeneratedIdEntity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.concurrent.ThreadLocalRandom;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class TAN extends GeneratedIdEntity {

    private int tanNumber;

    @XmlTransient
    @OneToOne
    private Transaction transaction;

    public TAN() {
        super.id = getId();
        this.tanNumber = this.generateTan();
    }

    private int generateTan() {
        // Generate a TAN number that consists of 6 digits.
        String actualTAN = "";
        for (int i = 0; i < 6; i++) {
            // Generate numbers between 1 and 10. The 0 is excluded so that no leading 0s can appear.
            int digit = ThreadLocalRandom.current().nextInt(1, 10);
            actualTAN = actualTAN + String.valueOf(digit);
        }
        return Integer.parseInt(actualTAN);
    }

    @Override
    public String toString() {
        String result;
        if (this.transaction != null) {
            result = "TAN-Number: " +
                    this.tanNumber +
                    "transaction: " +
                    this.transaction.getId();
        } else {
            result = "TAN-Number: " +
                    this.tanNumber +
                    "transaction: null";
        }

        return result;
    }

    public Long getId() {
        return super.getId();
    }

    public int getTanNumber() {
        return tanNumber;
    }

    public void setTanNumber(int tanNumber) {
        this.tanNumber = tanNumber;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

}
