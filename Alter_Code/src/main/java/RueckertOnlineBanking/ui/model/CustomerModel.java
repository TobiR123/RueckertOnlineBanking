package RueckertOnlineBanking.ui.model;

import RueckertOnlineBanking.entity.*;
import RueckertOnlineBanking.service.CustomerService;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
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
public class CustomerModel implements Serializable {

    private Customer tempCustomer;
    private Customer lastRegistered;
    private Address tempAddress;
    private Address lastAddress;
    private PIN tempPin;
    private PIN lastPin;
    private EMailAddress tempEmailAddress;
    private EMailAddress lastEmailAddress;
    private boolean inChange;

    @Inject
    private CustomerService service;


    public CustomerModel() {
        this.tempCustomer = new Customer();
        this.lastRegistered = new Customer();
        this.tempAddress = new Address();
        this.tempEmailAddress = new EMailAddress();
        this.inChange = false;

        this.service = new CustomerService();
    }




    public Customer getTempCustomer() {
        return tempCustomer;
    }

    public void setTempCustomer(Customer tempCustomer) {
        this.tempCustomer = tempCustomer;
    }

    public Address getTempAddress() {
        return tempAddress;
    }

    public void setTempAddress(Address tempAddress) {
        this.tempAddress = tempAddress;
    }

    public PIN getTempPin() {
        return tempPin;
    }

    public void setTempPin(PIN tempPin) {
        this.tempPin = tempPin;
    }

    public EMailAddress getTempEmailAddress() {
        return tempEmailAddress;
    }

    public void setTempEmailAddress(EMailAddress tempEmailAddress) {
        this.tempEmailAddress = tempEmailAddress;
    }

    public boolean isInChange() {
        return inChange;
    }

    public void setInChange(boolean inChange) {
        this.inChange = inChange;
    }
    public Customer getLastRegistered() {
        return lastRegistered;
    }

    public void setLastRegistered(Customer lastRegistered) {
        this.lastRegistered = lastRegistered;
    }





    public String registerCustomer() throws ParseException {

        tempCustomer.setAddress(this.tempAddress);
        tempCustomer.seteMailAddress(this.tempEmailAddress);

        this.lastRegistered = service.registerCustomer(tempCustomer);
        System.out.println(lastRegistered.toString());

        tempCustomer = new Customer();
        tempAddress = new Address();
        tempEmailAddress = new EMailAddress();
        tempPin = new PIN();

        return "afterRegistrationViewWithTemplate.xhtml";
    }


    public String prepareUpdateCustomer() {
        inChange = true;

        return "afterRegistrationViewWithTemplate.xhtml";
    }

    public String updateCustomer() throws ParseException {
        inChange = false;

//        tempCustomer.setAddress(this.tempAddress);
//        tempCustomer.seteMailAddress(this.tempEmailAddress);
//        tempCustomer.setPinNumber(this.tempPin);

        this.lastRegistered = service.updateCustomer(this.lastRegistered);

        System.out.println(this.lastRegistered.toString());

        return "afterRegistrationViewWithTemplate.xhtml";
    }

    public String showEditCustomerPage() {
        return "editCustomer.xhtml";
    }

    public String goToTransactionScreen() {
        return "TransactionView.xhtml";
    }

    public String logout() {
        return "mainView.xhtml";
    }

    public void viewUserProfile() {

    }

    public void editUserProfile() {

    }


}
