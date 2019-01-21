package RueckertOnlineBanking.entity;

import javax.persistence.Embeddable;
import java.util.concurrent.ThreadLocalRandom;

@Embeddable
public class PIN {

    private int pinNumber;

    public PIN() {
        this.pinNumber = this.generatePin();
    }

    public PIN(int pinNumber) {
        this.pinNumber = pinNumber;
    }

    public int generatePin() {
        // Generate a PIN number that consists of 6 digits.
        String actualPin = "";
        for (int i = 0; i < 6; i++) {
            // Generate numbers between 1 and 10. The 0 is excluded so that no leading 0s can appear.
            int digit = ThreadLocalRandom.current().nextInt(1, 10);
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
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        }
        final PIN other = (PIN) o;
        // Compare PIN numbers.
        if (this.pinNumber == other.pinNumber) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(this.pinNumber).hashCode();
    }

    ///// GETTER AND SETTER /////
    public int getPinNumber() {
        return this.pinNumber;
    }

    public void setPinNumber(int pinNumber) {
        this.pinNumber = pinNumber;
    }

}
