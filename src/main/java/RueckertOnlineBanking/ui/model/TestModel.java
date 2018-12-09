package RueckertOnlineBanking.ui.model;

import RueckertOnlineBanking.entity.*;
import RueckertOnlineBanking.service.CustomerService;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

// Die meisten Modelle sind SessionScoped: Für die ganze Sitzung, vom login bis zum bestellen.
// RequestScoped:
// ApplicationScoped: Derzeit sind soundsoviele User in meinem System online. Singleton, immer das selbe, einfach counter erhöhen.


// Models sind dumme klassen! Nur getter und setter! Wenige action Methoden! keine DB zugriffe!



// BEI SESSION SCOPED: COUNTER WIRD HOCHGEZÄHLT! WEIL GANZE SITZUNG!
// ABER: BEI NEUEM FESTNER; ALSO NEUER SITZUNG: GEHT WIEDER BEI 0 los!
// BEI REQUEST SCOPED: COUNTER WIRD IMMER 0 BLEIBEN! JEDER REQUEST EINE NEUE BEAN!
// APPLICATION SCOPED: COUNTER WIRD IMMER HOCHGEZÄHLT; AUCH ÜBER VERSCHIEDENE SITZUNGEN HINWEG!

@Named
@SessionScoped
public class TestModel implements Serializable {

    public TestModel() {
        this.service = new CustomerService();
//        this.customer = new Customer();
//        this.address = new Address();
//        this.eMailAddress = new EMailAddress();
    }



    // E-Mail Address field.
    private String eMailAddress;

    // Address fields.
    private String street;
    private String houseNumber;
    private int postcode;
    private String place;

    // Customer fields.
    private String firstname;
    private String lastname;
    private int phoneNumber;
    private String dateOfBirth;
    // To show customer attributes after registration/login.
    private Customer createdCustomer;

    // Login fields.
    private String eMailAddressLogin;
    private int pinLogin;

    @Inject
    private CustomerService service;
    private int counter = 0;


    // ##### GETTER AND SETTER ##### //
    public String geteMailAddress() {
        return eMailAddress;
    }

    public void seteMailAddress(String eMailAddress) {
        this.eMailAddress = eMailAddress;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String geteMailAddressLogin() {
        return eMailAddressLogin;
    }

    public void seteMailAddressLogin(String eMailAddressLogin) {
        this.eMailAddressLogin = eMailAddressLogin;
    }

    public int getPinLogin() {
        return pinLogin;
    }

    public void setPinLogin(int pinLogin) {
        this.pinLogin = pinLogin;
    }

    public Customer getCreatedCustomer() {
        return createdCustomer;
    }

    public void setCreatedCustomer(Customer createdCustomer) {
        this.createdCustomer = createdCustomer;
    }


    // PAGEFLOW: STRING ZURÜCKGEBEN; DER DIE FOLGESEITE ENTHÄLT!!!!
    public String registerCustomer() throws ParseException {

        // TODO: BRAUCHEN WIR DEN THIS.CUSTOMER USW: ÜBERHAUPT? EIGENTLICH MÜSSEN WIR DOCH NUR DIE ATTRIBUTE EINZELN ABSPEICHERN, UND DANN DEN NEUEN INSTANZEN HINZUFÜGEN?
        Customer newCustomer = new Customer();
        EMailAddress newEmailAddress = new EMailAddress();
        Address newAddress = new Address();

        newCustomer = this.fillNewCustomerInstance(newCustomer);
        newAddress = this.fillNewAddressInstance(newAddress);
        newEmailAddress = this.fillNewEmailAddressInstance(newEmailAddress);

        newCustomer.setAddress(newAddress);
        newCustomer.addEmailAddress(newEmailAddress);

        newCustomer = service.registerCustomer(newEmailAddress, newAddress, newCustomer);
        System.out.println(newCustomer.toString());
        this.createdCustomer = newCustomer;
        return "afterRegistrationView.xhtml";
    }

    public String loginCustomer() {
        EMailAddress loginEmailAddress = new EMailAddress(this.eMailAddressLogin);
        PIN loginPIN = new PIN(this.pinLogin);
        boolean success = service.loginCustomer(this.eMailAddressLogin, this.pinLogin);
        if(success == true){
            System.out.println("Sie haben sich erfolgreich angemeldet!");
        } else {
            System.out.println("Bei der Anmeldung lief etwas schief ... ");
        }

        return "afterLoginView.xhtml";
    }

    // ##### AUXILIARY METHODS ##### //


    private EMailAddress fillNewEmailAddressInstance(EMailAddress newEmailAddress) {
        newEmailAddress.setMailAddress(this.eMailAddress);

        return newEmailAddress;
    }

    private Address fillNewAddressInstance(Address newAddress) {
        newAddress.setStreet(this.street);
        newAddress.setHouseNumber(this.houseNumber);
        newAddress.setPostcode(this.postcode);
        newAddress.setPlace(this.place);

        return newAddress;
    }

    private Customer fillNewCustomerInstance(Customer newCustomer) throws ParseException {
        newCustomer.setFirstname(this.firstname);
        newCustomer.setLastname(this.lastname);
        newCustomer.setPhoneNumber(this.phoneNumber);
        newCustomer.setDateOfBirth(this.dateOfBirth);

        return newCustomer;
    }

//    private EMailAddress fillNewEmailAddressInstance(EMailAddress newEmailAddress) {
//        newEmailAddress.setMailAddress(this.eMailAddress.getMailAddress());
//
//        return newEmailAddress;
//    }
//
//    private Address fillNewAddressInstance(Address newAddress) {
//        newAddress.setStreet(this.address.getStreet());
//        newAddress.setHouseNumber(this.address.getHouseNumber());
//        newAddress.setPostcode(this.address.getPostcode());
//        newAddress.setPlace(this.address.getPlace());
//
//        return newAddress;
//    }
//
//    private Customer fillNewCustomerInstance(Customer newCustomer) {
//        newCustomer.setFirstname(this.customer.getFirstname());
//        newCustomer.setLastname(this.customer.getLastname());
//        newCustomer.setPhoneNumber(this.customer.getPhoneNumber());
//        newCustomer.setDateOfBirth(this.customer.getDateOfBirth());
//
//        return newCustomer;
//    }



}
