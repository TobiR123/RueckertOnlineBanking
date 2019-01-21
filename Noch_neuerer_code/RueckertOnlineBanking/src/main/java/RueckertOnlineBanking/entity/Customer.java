package RueckertOnlineBanking.entity;

import RueckertOnlineBanking.entity.util.GeneratedIdEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Customer extends GeneratedIdEntity {

    private String firstname;
    private String lastname;
    @OneToOne
    private EMailAddress eMailAddress;
    @XmlTransient
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<String> phoneNumbers;
    private Date dateOfBirth;
    @OneToOne(orphanRemoval = true)
    private Address address;
    @XmlTransient
    private PIN pinNumber;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<TAN> tanNumbers;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Account> accounts;
    @XmlTransient
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<MoneyTransportOrder> moneyTransportOrders;


    public Customer() {
        super.id = getId();
        this.accounts = new ArrayList<>();
        this.tanNumbers = new ArrayList<>();
        this.phoneNumbers = new ArrayList<>();
        this.eMailAddress = new EMailAddress();
        this.moneyTransportOrders = new ArrayList<>();
    }

    public Customer(String firstname, String lastname, EMailAddress emailAddress, List<String> phoneNumbers, Date dateOfBirth, Address address) {
        super.id = getId();
        this.firstname = firstname;
        this.lastname = lastname;
        this.eMailAddress = emailAddress;
        this.phoneNumbers = phoneNumbers;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.accounts = new ArrayList<>();
        this.tanNumbers = new ArrayList<>();
        this.moneyTransportOrders = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Firstname: " +
                this.firstname +
                " Lastname: " +
                this.lastname +
                " E-Mail Address: " +
                this.eMailAddress +
                " Phone Number: " +
                this.phoneNumbers +
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

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public EMailAddress geteMailAddress() {
        return this.eMailAddress;
    }

    public void seteMailAddress(EMailAddress eMailAddress) {
        this.eMailAddress = eMailAddress;
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

    public void setPinNumber(PIN pinNumber) {
        this.pinNumber = pinNumber;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public List<MoneyTransportOrder> getMoneyTransportOrders() {
        return moneyTransportOrders;
    }

    public void setMoneyTransportOrders(List<MoneyTransportOrder> moneyTransportOrders) {
        this.moneyTransportOrders = moneyTransportOrders;
    }

    public List<TAN> getTanNumbers() {

        return this.tanNumbers;
    }

    public void setTanNumbers(List<TAN> tanNumbers) {
        this.tanNumbers = tanNumbers;
    }

    public List<Account> getAccounts() {

        return this.accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    // ##### METHODDS ##### //

    public String getTanNumbersAsString() {
        String result = "";
        for (int i = 0; i < this.tanNumbers.size(); i++) {
            result += " TAN" + i + ":" + String.valueOf(this.tanNumbers.get(i).getTanNumber());
        }
        return result;
    }

    public Customer addPhoneNumber(String phoneNumber) {
        this.phoneNumbers.add(phoneNumber);
        return this;
    }

    public Customer removePhoneNumber(String phoneNumber) {
        this.phoneNumbers.remove(phoneNumber);
        return this;
    }

    public Customer addMoneyTransportOrder(MoneyTransportOrder transportOrder) {
        this.moneyTransportOrders.add(transportOrder);
        return this;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public void addTanNumber(TAN tanNumber) {
        this.tanNumbers.add(tanNumber);
    }

    public void removeTanNumber(TAN tanNumber) {
        this.tanNumbers.remove(tanNumber);
    }

}
