package RueckertOnlineBanking.entity;


import RueckertOnlineBanking.entity.util.GeneratedIdEntity;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Entity
//@Embeddable
public class PIN extends GeneratedIdEntity {

    private int pinNumber;

    public PIN() {
        super.id = getId();
//        this.pinNumber = this.generatePin();
        //this.pinNumber = this.generatePin();
    }

    public PIN(int pinNumber) {
        super.id = getId();
        this.pinNumber = pinNumber;
    }

    public int generatePin() {
        // Generate a PIN number that consists of 6 digits.
        String actualPin = "";
        for(int i = 0; i < 6; i++) {
            // Generate numbers between 1 and 10. The 0 is excluded so that no leading 0s can appear.
            int digit =  ThreadLocalRandom.current().nextInt(1, 10);
            actualPin = actualPin + String.valueOf(digit);
        }
        this.pinNumber = Integer.parseInt(actualPin);
        return this.pinNumber;
    }

    @Override
    public String toString() {
        return "PIN-Number: " +
                this.pinNumber;
    }

    @Override
    public boolean equals(Object o){
        if( o == null) {
            return false;
        }
        else if(getClass() != o.getClass()) {
            return false;
        }
        final PIN other = (PIN)o;
        if(!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        if(this.id == null) {
            return 0;
        }
        else {
            return this.id.hashCode();
        }
    }

    ///// GETTER AND SETTER /////
    public int getPinNumber() {
        return this.pinNumber;
    }

    public void setPinNumber(int pinNumber) {
        this.pinNumber = pinNumber;
    }

    public Long getId() {
        return super.getId();
    }

}
