package RueckertOnlineBanking.entity;

import RueckertOnlineBanking.entity.util.GeneratedIdEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Customer extends GeneratedIdEntity {

    private String firstname;
    private String lastname;
    @OneToMany
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
    }

    public Customer(String firstname, String lastname, EMailAddress emailAddress, int phoneNumber, Date dateOfBirth, Address address, Account account){
        super.id = getId();
        this.firstname = firstname;
        this.lastname = lastname;
        this.eMailAddresses = new ArrayList<EMailAddress>();
        this.eMailAddresses.add(emailAddress);
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.accounts = new ArrayList<Account>();
        this.accounts.add(account);

        this.pinNumber = new PIN();
        this.tanNumbers = new ArrayList<TAN>();
        this.fillTanNumbersList();
        this.sendTanNumbersListToEmail();
    }

    public Customer(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    private void fillTanNumbersList() {
        // Method to fill list of tans with 20 generated numbers.
        for(int i = 0; i < 20; i++) {
            this.tanNumbers.add(new TAN());
        }
    }

    private void sendTanNumbersListToEmail() {
        // TODO:
    }

    @Override
    public String toString()
    {
        return "Firstname: " +
                this.firstname +
                " Lastname: " +
                this.lastname +
                " E-Mail Addresses: " +
                this.eMailAddresses +
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
