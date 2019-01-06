package RueckertOnlineBanking.entity;
import RueckertOnlineBanking.entity.util.GeneratedIdEntity;

import javax.persistence.Entity;
import java.util.Objects;
import java.util.Random;

@Entity
public class Account extends GeneratedIdEntity {

    private String iban;
    private String bic;
    private int bankCode;
    private long accountNumber;
    private double credit;
    //@ManyToOne
    //private Customer customer;


    public Account() {
        super.id = getId();
        this.bankCode = this.instantiateBankCode();
        this.accountNumber = this.generateAccountNumber();
        this.bic = "RUOBDE01"; // BIC stands for "Rueckert Online Banking DE 01".
        this.iban = this.generateIban();
        this.credit = 0.0;
        //this.customer = customer;
    }

    private int instantiateBankCode() {
        // Bank Code consists of 8 digits.
        // 713 = Location in Bavaria.
        // 1 = 'Other' kind of credit institute.
        // 9472 = Specific numbers of this bank.
        return 71319472;
    }

    private long generateAccountNumber() {
        // Account Number consists of 10 digits.
        String accountNumberString = "";
        for(int i = 0; i < 10; i++) {
            // .nextInt() is exclusive the top number, so add 1 to make it inclusive.
            Random randomNum = new Random();
            int generatedDigit = randomNum.nextInt(10);
            // The first digit should not be 0.
            if(generatedDigit == 0 && i == 0){
                generatedDigit = 1;
            }
            accountNumberString += Integer.toString(generatedDigit);
        }

        // TODO: In DB nachschauen, obs Kontonummer schon gibt! Wenn ja, neue erstellen!

        return Long.parseLong(accountNumberString);
    }

    private String generateIban() {
        // generate an iban from a known bank code and account number.
        long checksum = this.generateChecksum();

        String iban = "DE" + String.valueOf(checksum) + String.valueOf(this.bankCode) + String.valueOf(this.accountNumber);
        return iban;
    }

    private long generateChecksum() {
        // ISO 7064 Modulo 97 algorithm for creating the checksum.
        // For details, visit: https://www.sparkonto.org/manuelles-berechnen-der-iban-pruefziffer-sepa/

        // For DE, 1314 will be set directly.
        String start = Integer.toString(this.bankCode) + String.valueOf(this.accountNumber) + "131400";

        Long block1 = Long.parseLong(start.substring(0, 9));
        Long block2 = Long.parseLong(start.substring(9, 16));
        Long block3 = Long.parseLong(start.substring(16, 23));
        Long block4 = Long.parseLong(start.substring(23));

        Long difference1 = block1 - ((block1/97)*97);
        if(String.valueOf(difference1).length() == 1) {
            String difference1Text = String.valueOf(difference1);
            String concatenate1 = "0" + difference1Text;
            difference1 = Long.getLong(concatenate1);
        }

        String a = String.valueOf(difference1);
        if(a == null){
            System.out.println("a ist null");
            a = "0";
        }
        String b = String.valueOf(block2);
        String block2Concatenated = a + b;


        if(block2Concatenated.startsWith("null")){
            String tmpNum = block2Concatenated.substring(4);
            block2Concatenated = "0" + tmpNum;
        }

        Long c  = Long.parseLong(block2Concatenated);
        Long d  = ((Long.parseLong(block2Concatenated)/97)*97);
        Long difference2 = c - d;

        if(String.valueOf(difference2).length() == 1) {
            String difference2Text = String.valueOf(difference2);
            String concatenate2 = "0" + difference2Text;
            difference2 = Long.getLong(concatenate2);
        }


        a = String.valueOf(difference2);
        if(a == null){
            System.out.println("a ist null");
            a = "0";
        }
        b = String.valueOf(block3);
        String block3Concatenated = a + b;

        if(block3Concatenated.startsWith("null")){
            String tmpNum = block3Concatenated.substring(4);
            block3Concatenated = "0" + tmpNum;
        }

        c = Long.parseLong(block3Concatenated);
        d = ((Long.parseLong(block3Concatenated)/97)*97);
        Long difference3 = c - d;
        System.out.println("Difference 3: " + difference3 + " 1:" + c + " 2: " + d);




        if(String.valueOf(difference3).length() == 1) {
            String difference3Text = String.valueOf(difference3);
            String concatenate3 = "0" + difference3Text;
            difference3 = Long.getLong(concatenate3);
        }


        a = String.valueOf(difference3);
        if(a == null){
            System.out.println("a ist null");
            a = "0";
        }
        b = String.valueOf(block4);
        String block4Concatenated = a + b;
        System.out.println("Diff4Concatenated: " + block4Concatenated + " 1: " + a + " 2: " + b);


        if(block4Concatenated.startsWith("null")){
            String tmpNum = block4Concatenated.substring(4);
            block4Concatenated = "0" + tmpNum;
        }

        c = Long.parseLong(block4Concatenated);
        d = ((Long.parseLong(block4Concatenated)/97)*97);
        Long difference4 = c - d;

        if(String.valueOf(difference4).length() == 1) {
            String difference4Text = String.valueOf(difference4);
            String concatenate4 = "0" + difference4Text;
            difference4 = Long.getLong(concatenate4);
        }

        if(difference4 == null || difference4 == 0L){
            return 98;
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
        return
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

    public int getBankCode() {
        return bankCode;
    }

    public void setBankCode(int bankCode) {
        this.bankCode = bankCode;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }


}
