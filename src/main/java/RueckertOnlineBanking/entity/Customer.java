package RueckertOnlineBanking.entity;

import RueckertOnlineBanking.entity.util.GeneratedIdEntity;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Customer extends GeneratedIdEntity {

    private String firstname;
    private String lastname;
    @OneToMany(cascade= CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<EMailAddress> eMailAddresses;
    private int phoneNumber;
    private Date dateOfBirth;
    @OneToOne
    private Address address;
    @OneToOne
    private PIN pinNumber;
    @OneToMany
    private List<TAN> tanNumbers;
    @OneToMany
    private List<Account> accounts;



    public Customer(){
        super.id = getId();
        this.eMailAddresses = new ArrayList<>();
        this.accounts = new ArrayList<>();
        this.tanNumbers = new ArrayList<>();
    }

    public Customer(String firstname, String lastname, EMailAddress emailAddress, int phoneNumber, Date dateOfBirth, Address address){
        super.id = getId();
        this.firstname = firstname;
        this.lastname = lastname;
        this.eMailAddresses = new ArrayList<>();
        this.eMailAddresses.add(emailAddress);
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.accounts = new ArrayList<>();

        this.tanNumbers = new ArrayList<>();
    }

    @Override
    public String toString()
    {
        return "Firstname: " +
                this.firstname +
                " Lastname: " +
                this.lastname +
                " E-Mail Addresses: " +
                this.printEmailAddresses() +
                " Phone Number: " +
                this.phoneNumber +
                " Date of Birth: " +
                this.dateOfBirth +
                " Address: " +
                this.address +
                " Accounts: " +
                this.accounts +
                " PIN: " +
                this.pinNumber +
                " TANS: " +
                this.tanNumbers;
    }

    @Override
    public boolean equals(Object o){
        if( o == null) {
            return false;
        }
        else if(getClass() != o.getClass()) {
            return false;
        }
        final Customer other = (Customer) o;
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

    private String printEmailAddresses() {
        String result = "";
        for(int i = 0; i < this.eMailAddresses.size(); i++) {
            result += eMailAddresses.get(i).toString();
        }
        return result;
    }

    ///// GETTERS AND SETTERS /////
    public Long getId() {
        return super.getId();
    }

    public String getFirstname() {

        return this.firstname;
    }
    public void setFirstname(String firstname) {

        this.firstname = firstname;
    }
    public String getLastname() {

        return this.lastname;
    }
    public void setLastname(String lastname){

        this.lastname = lastname;
    }
    public List<EMailAddress> getEmailAddresses() {

        return this.eMailAddresses;
    }
    public void addEmailAddress(EMailAddress eMailAddress)
    {

        this.eMailAddresses.add(eMailAddress);
    }
    public void removeEmailAddress(EMailAddress eMailAddress) {

        this.eMailAddresses.remove(eMailAddress);
    }
    public List<EMailAddress> geteMailAddresses() {
        return this.eMailAddresses;
    }
    public int getPhoneNumber() {

        return this.phoneNumber;
    }
    public void setPhoneNumber(int phoneNumber) {

        this.phoneNumber = phoneNumber;
    }
    public Date getDateOfBirth() {

        return this.dateOfBirth;
    }
    public void setDateOfBirth(Date dateOfBirth) {

        this.dateOfBirth = dateOfBirth;
    }
    public void setDateOfBirth(String dateOfBirth) throws ParseException {
        this.dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth);
    }
    public Address getAddress() {

        return this.address;
    }
    public void setAddress(Address address) {

        this.address = address;
    }
    public PIN getPinNumber() {

        return this.pinNumber;
    }
    public void setPinNumber(PIN pinNumber){

        this.pinNumber = pinNumber;
    }
    public List<TAN> getTanNumbers() {

        return this.tanNumbers;
    }
    public String getTanNumbersAsString() {
        String result = "";
        for(int i = 0; i < this.tanNumbers.size(); i++) {
            result += " TAN" + i + ":" + String.valueOf(this.tanNumbers.get(i).getTanNumber());
        }
        return result;
    }
    public void addTanNumber(TAN tanNumber) {

        this.tanNumbers.add(tanNumber);
    }
    public void removeTanNumber(TAN tanNumber){

        this.tanNumbers.remove(tanNumber);
    }
    public List<Account> getAccounts() {

        return this.accounts;
    }
    public void addAccount(Account account) {

        this.accounts.add(account);
    }
    public void removeAccount(Account account) {

        this.accounts.remove(account);
    }

}
