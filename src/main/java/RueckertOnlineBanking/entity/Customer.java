package RueckertOnlineBanking.entity;

import RueckertOnlineBanking.entity.util.GeneratedIdEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
public class Customer extends GeneratedIdEntity {

    private String firstname;
    private String lastname;
    //@OneToMany(cascade= CascadeType.PERSIST, fetch = FetchType.EAGER)
    @OneToOne
    private EMailAddress eMailAddress;
    private int phoneNumber;
    private Date dateOfBirth;
    @OneToOne
    private Address address;
    @OneToOne
    private PIN pinNumber;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade= CascadeType.PERSIST)
    private List<TAN> tanNumbers;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade= CascadeType.PERSIST)
    private List<Account> accounts;



    public Customer(){
        super.id = getId();
        this.accounts = new ArrayList<>();
        this.tanNumbers = new ArrayList<>();
    }

    public Customer(String firstname, String lastname, EMailAddress emailAddress, int phoneNumber, Date dateOfBirth, Address address){
        super.id = getId();
        this.firstname = firstname;
        this.lastname = lastname;
        this.eMailAddress = emailAddress;
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
                this.eMailAddress +
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
    public int getPhoneNumber() {

        return this.phoneNumber;
    }
    public EMailAddress geteMailAddress(){
        return this.eMailAddress;
    }
    public void seteMailAddress(EMailAddress eMailAddress) {
        this.eMailAddress = eMailAddress;
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
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void removeAccount(Account account) {

        this.accounts.remove(account);
    }

}
