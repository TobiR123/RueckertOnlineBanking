package RueckertOnlineBanking.entity;


import RueckertOnlineBanking.entity.util.GeneratedIdEntity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Entity
public class TAN extends GeneratedIdEntity {

    private int tanNumber;

    @OneToOne
    private Transaction transaction;

    public TAN() {
        super.id = getId();
        this.tanNumber = this.generateTan();
    }

    private int generateTan() {
        // Generate a TAN number that consists of 6 digits.
        String actualTAN = "";
        for(int i = 0; i < 6; i++) {
            // Generate numbers between 1 and 10. The 0 is excluded so that no leading 0s can appear.
            int digit =  ThreadLocalRandom.current().nextInt(1, 10);
            actualTAN = actualTAN + String.valueOf(digit);
        }
        return Integer.parseInt(actualTAN);
    }

    @Override
    public String toString() {
        return "TAN-Number: " +
                this.tanNumber +
                "transaction: " +
                this.transaction;
    }

    @Override
    public boolean equals(Object o){
        if( o == null) {
            return false;
        }
        else if(getClass() != o.getClass()) {
            return false;
        }
        final TAN other = (TAN) o;
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
