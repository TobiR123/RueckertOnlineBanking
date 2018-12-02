package RueckertOnlineBanking.entity;

import java.util.concurrent.ThreadLocalRandom;

import RueckertOnlineBanking.entity.util.GeneratedIdEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class Account extends GeneratedIdEntity {

    private String iban;
    private String bic;
    private int bankCode;
    private int accountNumber;
    private double credit;
    @ManyToOne
    private Customer customer;

    public Account() {
        super.id = getId();
    }

    public Account(Customer customer) {
        super.id = getId();
        this.bankCode = this.instantiateBankCode();
        this.accountNumber = this.generateAccountNumber();
        this.bic = "RUOBDE01"; // BIC stands for "Rueckert Online Banking DE 01".
        this.iban = this.generateIban();
    }

    private int instantiateBankCode() {
        // Bank Code consists of 8 digits.
        // 713 = Location in Bavaria.
        // 1 = 'Other' kind of credit institute.
        // 9472 = Specific numbers of this bank.
        return 71319472;
    }

    private int generateAccountNumber() {
        // Account Number consists of 10 digits.
        String accountNumberString = "";
        for(int i = 0; i < 10; i++) {
            // .nextInt() is exclusive the top number, so add 1 to make it inclusive.
            int generatedDigit = ThreadLocalRandom.current().nextInt(0, 10);
            accountNumberString = accountNumberString + Integer.toString(generatedDigit);
        }

        // TODO: In DB nachschauen, obs Kontonummer schon gibt! Wenn ja, neue erstellen!

        return Integer.getInteger(accountNumberString);
    }

    private String generateIban() {
        // generate an iban from a known bank code and account number.
        long checksum = this.generateChecksum();
        return "DE" + String.valueOf(checksum) + String.valueOf(this.bankCode) + String.valueOf(this.accountNumber);
    }

    private long generateChecksum() {
        // ISO 7064 Modulo 97 algorithm for creating the checksum.
        // For details, visit: https://www.sparkonto.org/manuelles-berechnen-der-iban-pruefziffer-sepa/

        // For DE, 1314 will be set directly.
        String start = Integer.toString(this.bankCode) + Integer.toString(this.accountNumber) + "131400";

        Long block1 = Long.parseLong(start.substring(0, 9));
        Long block2 = Long.parseLong(start.substring(9, 16));
        Long block3 = Long.parseLong(start.substring(16, 23));
        Long block4 = Long.parseLong(start.substring(23));

        Long difference1 = block1 - ((block1/97)*97);
        if(String.valueOf(difference1).length() == 1) {
            difference1 = Long.getLong("0" + String.valueOf(difference1));
        }

        String block2Concatenated = String.valueOf(difference1) + String.valueOf(block2);

        Long difference2 = Long.getLong(block2Concatenated) - ((Long.getLong(block2Concatenated)/97)*97);
        if(String.valueOf(difference2).length() == 1) {
            difference2 = Long.getLong("0" + String.valueOf(difference2));
        }

        String block3Concatenated = String.valueOf(difference2) + String.valueOf(block3);

        Long difference3 = Long.getLong(block3Concatenated) - ((Long.getLong(block3Concatenated)/97)*97);
        if(String.valueOf(difference3).length() == 1) {
            difference3 = Long.getLong("0" + String.valueOf(difference3));
        }

        String block4Concatenated = String.valueOf(difference3) + String.valueOf(block4);

        Long difference4 = Long.getLong(block4Concatenated) - ((Long.getLong(block4Concatenated)/97)*97);
        if(String.valueOf(difference4).length() == 1) {
            difference4 = Long.getLong("0" + String.valueOf(difference4));
        }

        // Return checksum.
        return 98 - difference4;

    }


    @Override
    public boolean equals(Object o){
        if( o == null) {
            return false;
        }
        else if(getClass() != o.getClass()) {
            return false;
        }
        final Account other = (Account) o;
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

    @Override
    public String toString() {
        return "Customer: " +
                this.customer.toString() +
                "iban: " +
                this.iban +
                "bic: " +
                this.bic +
                "bank code: " +
                this.bankCode +
                "account number: " +
                this.accountNumber +
                "credit: " +
                this.credit;
    }

    ///// GETTER AND SETTER /////

    public Long getId() {
        return super.getId();
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
