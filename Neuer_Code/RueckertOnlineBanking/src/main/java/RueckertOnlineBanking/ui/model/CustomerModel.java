package RueckertOnlineBanking.ui.model;

import RueckertOnlineBanking.entity.*;
import RueckertOnlineBanking.entity.customExceptions.customerTooYoungException;
import RueckertOnlineBanking.entity.customExceptions.emailAddressAlreadyInUseException;
import RueckertOnlineBanking.service.CustomerService;
import RueckertOnlineBanking.ui.loggerFactory.LoggerFactory;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class CustomerModel implements Serializable {

//    @Inject
//    private LoggerFactory loggerFactory;
//    private Logger logger;

    // Login fields.
    private EMailAddress loginEmailAddress;
    private PIN loginPin;

    // Transaction fields.
    private List<Transaction> transactions;

    // Other fields.
    private boolean invalidLogin = false;
    private Customer tempCustomer;
    private Customer lastRegistered;

    // This properties are need for rendering the left column. If the customer has currently registered, show him his TANs. If not, show him some text.
    private boolean recentlyRegistered = true;
    private boolean recentlyLoggedIn = false;

    private Account lastCreatedAccount;
    private Address tempAddress;
    private EMailAddress tempEmailAddress;

    private boolean customerLoggedOut = false;

    private String emailAddressAlreadyInUseExceptionMessage;
    private boolean duplicateEmailAddress = false;

    private String customerTooYoungExceptionMessage;
    private boolean customerTooYoung = false;

    @Inject
    private CustomerService service;
    private boolean customerDeleted = false;


    public CustomerModel() {
        this.loginEmailAddress = new EMailAddress();
        this.loginPin = new PIN();

        this.tempCustomer = new Customer();
        this.lastRegistered = new Customer();
        this.lastCreatedAccount = new Account();
        this.tempAddress = new Address();
        this.tempEmailAddress = new EMailAddress();

        this.service = new CustomerService();

        this.transactions = new ArrayList<>();
//        this.loggerFactory = new LoggerFactory();
//        this.logger = loggerFactory.create();
    }

    // ##### GETTER AND SETTER ##### //
    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public boolean isInvalidLogin() {
        return invalidLogin;
    }

    public void setInvalidLogin(boolean invalidLogin) {
        this.invalidLogin = invalidLogin;
    }

    public boolean isCustomerDeleted() {
        return customerDeleted;
    }

    public void setCustomerDeleted(boolean customerDeleted) {
        this.customerDeleted = customerDeleted;
    }

    public boolean isCustomerLoggedOut() {
        return customerLoggedOut;
    }

    public void setCustomerLoggedOut(boolean customerLoggedOut) {
        this.customerLoggedOut = customerLoggedOut;
    }

    public Account getLastCreatedAccount() {
        return lastCreatedAccount;
    }

    public void setLastCreatedAccount(Account lastCreatedAccount) {
        this.lastCreatedAccount = lastCreatedAccount;
    }

    public boolean isRecentlyLoggedIn() { return recentlyLoggedIn; }

    public void setRecentlyLoggedIn(boolean recentlyLoggedIn) { this.recentlyLoggedIn = recentlyLoggedIn; }

    public boolean isRecentlyRegistered() { return recentlyRegistered; }

    public void setRecentlyRegistered(boolean recentlyRegistered) { this.recentlyRegistered = recentlyRegistered; }

    public EMailAddress getLoginEmailAddress() {
        return loginEmailAddress;
    }

    public void setLoginEmailAddress(EMailAddress loginEmailAddress) {
        this.loginEmailAddress = loginEmailAddress;
    }

    public PIN getLoginPin() {
        return loginPin;
    }

    public void setLoginPin(PIN loginPin) {
        this.loginPin = loginPin;
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

    public EMailAddress getTempEmailAddress() {
        return tempEmailAddress;
    }

    public void setTempEmailAddress(EMailAddress tempEmailAddress) {
        this.tempEmailAddress = tempEmailAddress;
    }

    public Customer getLastRegistered() {
        return lastRegistered;
    }

    public void setLastRegistered(Customer lastRegistered) {
        this.lastRegistered = lastRegistered;
    }

    public String getEmailAddressAlreadyInUseExceptionMessage() {
        return emailAddressAlreadyInUseExceptionMessage;
    }

    public void setEmailAddressAlreadyInUseExceptionMessage(String emailAddressAlreadyInUseExceptionMessage) { this.emailAddressAlreadyInUseExceptionMessage = emailAddressAlreadyInUseExceptionMessage; }

    public boolean isDuplicateEmailAddress() {
        return duplicateEmailAddress;
    }

    public void setDuplicateEmailAddress(boolean duplicateEmailAddress) { this.duplicateEmailAddress = duplicateEmailAddress; }

    public boolean isCustomerTooYoung() {
        return customerTooYoung;
    }

    public void setCustomerTooYoung(boolean customerTooYoung) {
        this.customerTooYoung = customerTooYoung;
    }

    public String getCustomerTooYoungExceptionMessage() {
        return customerTooYoungExceptionMessage;
    }

    public void setCustomerTooYoungExceptionMessage(String customerTooYoungExceptionMessage) {
        this.customerTooYoungExceptionMessage = customerTooYoungExceptionMessage;
    }


    public String loginCustomer() {
        this.recentlyRegistered = false;
        this.recentlyLoggedIn = true;
        Customer customer = service.loginCustomer(this.loginEmailAddress, this.loginPin);
        if (customer != null) {
            this.lastRegistered = customer;
//            this.logger.log(Level.INFO, "Successful login!");
            this.loginEmailAddress = new EMailAddress();
            this.loginPin = new PIN();
            return "afterRegistrationViewWithTemplate.xhtml";
        } else {
            this.invalidLogin = true;
//            this.logger.log(Level.SEVERE, "Login data could not be verified. Login failed.");
            return "mainView.xhtml";
        }
    }

    public String registerCustomer() {

        this.tempCustomer.setAddress(this.tempAddress);
        this.tempCustomer.seteMailAddress(this.tempEmailAddress);

        try {
            this.lastRegistered = service.registerCustomer(tempCustomer);

            this.recentlyRegistered = true;
            this.recentlyLoggedIn = false;

            this.tempCustomer = new Customer();
            this.tempAddress = new Address();
            this.tempEmailAddress = new EMailAddress();

        } catch (emailAddressAlreadyInUseException e) {
//            this.logger.log(Level.SEVERE, "E-Mail address already in use.");
            this.emailAddressAlreadyInUseExceptionMessage = e.toString();
            this.duplicateEmailAddress = true;
            return "mainView.xhtml";
        } catch (customerTooYoungException e) {
//            this.logger.log(Level.SEVERE, "The customer is under 18 years old.");
            this.customerTooYoungExceptionMessage = e.toString();
            this.customerTooYoung = true;
            return "mainView.xhtml";
        } catch (ParseException e) {
//            this.logger.log(Level.INFO, "Something went wrong while parsing the input values.");
            e.printStackTrace();
        }
//        this.logger.log(Level.INFO, "Customer successful registered.");
        return "afterRegistrationViewWithTemplate.xhtml";
    }

    public String updateCustomer() {
        try {
            this.lastRegistered = service.updateCustomer(this.lastRegistered);
        } catch (emailAddressAlreadyInUseException e) {
//            this.logger.log(Level.SEVERE, "E-Mail address already in use.");
            this.emailAddressAlreadyInUseExceptionMessage = e.toString();
            this.duplicateEmailAddress = true;
            return "editCustomer.xhtml";
        }
//        this.logger.log(Level.INFO, "Successful updated customer.");
        return "afterRegistrationViewWithTemplate.xhtml";
    }

    public String showEditCustomerPage() {
        return "editCustomer.xhtml";
    }

    public String goToTransactionScreen() {
        return "TransactionView.xhtml";
    }

    public String showAfterRegistrationScreen() {
        return "afterRegistrationViewWithTemplate.xhtml";
    }

    public String logout() {
        this.customerLoggedOut = true;
        this.lastRegistered = new Customer();
//        this.logger.log(Level.INFO, "Successful logged out.");
        return "mainView.xhtml";
    }

    public String deleteCustomer() {
        service.deleteCustomer(this.lastRegistered);
        this.customerDeleted = true;
        this.lastRegistered = new Customer();
//        this.logger.log(Level.INFO, "Successful deleted customer.");
        return "mainView.xhtml";
    }

    public String addAccountToCustomer() {
        this.lastRegistered = this.service.addAccountToCustomer(this.lastRegistered);
        // Set the currently created account as the last created Account.
        this.lastCreatedAccount = this.lastRegistered.getAccounts().get(this.lastRegistered.getAccounts().size() - 1);
        return "newAccountAddedConfirmationPage.xhtml";
    }


}
