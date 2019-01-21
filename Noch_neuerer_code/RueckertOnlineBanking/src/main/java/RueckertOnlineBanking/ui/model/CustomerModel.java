package RueckertOnlineBanking.ui.model;

import RueckertOnlineBanking.entity.*;
import RueckertOnlineBanking.entity.customExceptions.customerTooYoungException;
import RueckertOnlineBanking.entity.customExceptions.emailAddressAlreadyInUseException;
import RueckertOnlineBanking.entity.customExceptions.pinTooShortException;
import RueckertOnlineBanking.loggerFactory.LoggerFactory;
import RueckertOnlineBanking.service.CustomerService;
import RueckertOnlineBanking.service.TransactionService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class CustomerModel implements Serializable {
    // ##### PROPERTIES ##### //
    @Inject
    private LoggerFactory loggerFactory;
    private Logger logger;

    @Inject
    private TransactionModel transactionModel;

    @Inject
    private TransactionService transactionService;

    // Login fields.
    private EMailAddress loginEmailAddress;
    private int loginPin;
    private boolean invalidLogin = false;

    // Transaction of current customer.
    private List<Transaction> transactions;

    private Customer tempCustomer;
    private Customer lastRegistered;

    // This properties are need for rendering the left column. If the customer has currently registered, show him his TANs. If not, show him some text.
    private boolean recentlyRegistered = true;
    private boolean recentlyLoggedIn = false;

    private Account lastCreatedAccount;
    private Address tempAddress;
    private EMailAddress tempEmailAddress;

    private boolean customerLoggedOut = false;

    // This property decides if a click on a navigation bar item leads to a filled site or not.
    private boolean customerSuccessfulRegisteredOrLoggedIn = false;

    private String emailAddressAlreadyInUseExceptionMessage;
    private boolean duplicateEmailAddress = false;

    private String customerTooYoungExceptionMessage;
    private boolean customerTooYoung = false;

    private String pinTooShortExceptionMessage;
    private boolean pinTooShort = false;

    private String newPhoneNumber;

    private TAN generateNewTansAuthenticationTan;

    @Inject
    private CustomerService customerService;
    private boolean customerDeleted = false;


    public CustomerModel() {
        this.loginEmailAddress = new EMailAddress();

        this.tempCustomer = new Customer();
        this.lastRegistered = new Customer();
        this.lastCreatedAccount = new Account();
        this.tempAddress = new Address();
        this.tempEmailAddress = new EMailAddress();

        this.customerService = new CustomerService();

        this.transactions = new ArrayList<>();
        this.generateNewTansAuthenticationTan = new TAN();
    }

    @PostConstruct
    public void init() {
        this.logger = this.loggerFactory.create();
    }

    // ##### GETTER AND SETTER ##### //

    public String getNewPhoneNumber() {
        return newPhoneNumber;
    }

    public void setNewPhoneNumber(String newPhoneNumber) {
        this.newPhoneNumber = newPhoneNumber;
    }

    public boolean isCustomerSuccessfulRegisteredOrLoggedIn() {
        return customerSuccessfulRegisteredOrLoggedIn;
    }

    public void setCustomerSuccessfulRegisteredOrLoggedIn(boolean customerSuccessfulRegisteredOrLoggedIn) {
        this.customerSuccessfulRegisteredOrLoggedIn = customerSuccessfulRegisteredOrLoggedIn;
    }

    public String getPinTooShortExceptionMessage() {
        return pinTooShortExceptionMessage;
    }

    public void setPinTooShortExceptionMessage(String pinTooShortExceptionMessage) {
        this.pinTooShortExceptionMessage = pinTooShortExceptionMessage;
    }

    public boolean isPinTooShort() {
        return pinTooShort;
    }

    public void setPinTooShort(boolean pinTooShort) {
        this.pinTooShort = pinTooShort;
    }

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

    public boolean isRecentlyLoggedIn() {
        return recentlyLoggedIn;
    }

    public void setRecentlyLoggedIn(boolean recentlyLoggedIn) {
        this.recentlyLoggedIn = recentlyLoggedIn;
    }

    public boolean isRecentlyRegistered() {
        return recentlyRegistered;
    }

    public void setRecentlyRegistered(boolean recentlyRegistered) {
        this.recentlyRegistered = recentlyRegistered;
    }

    public EMailAddress getLoginEmailAddress() {
        return loginEmailAddress;
    }

    public void setLoginEmailAddress(EMailAddress loginEmailAddress) {
        this.loginEmailAddress = loginEmailAddress;
    }

    public int getLoginPin() {
        return loginPin;
    }

    public void setLoginPin(int loginPin) {
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

    public void setEmailAddressAlreadyInUseExceptionMessage(String emailAddressAlreadyInUseExceptionMessage) {
        this.emailAddressAlreadyInUseExceptionMessage = emailAddressAlreadyInUseExceptionMessage;
    }

    public boolean isDuplicateEmailAddress() {
        return duplicateEmailAddress;
    }

    public void setDuplicateEmailAddress(boolean duplicateEmailAddress) {
        this.duplicateEmailAddress = duplicateEmailAddress;
    }

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

    public TAN getGenerateNewTansAuthenticationTan() {
        return generateNewTansAuthenticationTan;
    }

    public void setGenerateNewTansAuthenticationTan(TAN generateNewTansAuthenticationTan) {
        this.generateNewTansAuthenticationTan = generateNewTansAuthenticationTan;
    }


    // ##### METHODS ##### //

    public String loginCustomer() {
        this.recentlyRegistered = false;
        this.recentlyLoggedIn = true;
        Customer customer = customerService.loginCustomer(this.loginEmailAddress, this.loginPin);
        if (customer != null) {
            this.transactionModel.setCustomerTransactions(this.transactionService.getCustomerTransactions(customer));
            this.lastRegistered = customer;
            this.logger.log(Level.INFO, "Successful login!");
            this.loginEmailAddress = new EMailAddress();
            this.loginPin = 0;
            this.customerSuccessfulRegisteredOrLoggedIn = true;
            return "customerOverview.xhtml";
        } else {
            this.invalidLogin = true;
            this.logger.log(Level.SEVERE, "Login data could not be verified. Login failed.");
            return "index.xhtml";
        }
    }

    public String registerCustomer() {
        this.tempCustomer.setAddress(this.tempAddress);
        this.tempCustomer.seteMailAddress(this.tempEmailAddress);

        if (this.newPhoneNumber != "") {
            this.lastRegistered.addPhoneNumber(this.newPhoneNumber);
        }

        try {
            this.lastRegistered = customerService.registerCustomer(tempCustomer);

            this.recentlyRegistered = true;
            this.recentlyLoggedIn = false;

            this.tempCustomer = new Customer();
            this.tempAddress = new Address();
            this.tempEmailAddress = new EMailAddress();
            this.customerSuccessfulRegisteredOrLoggedIn = true;
            this.newPhoneNumber = "";

        } catch (emailAddressAlreadyInUseException e) {
            this.logger.log(Level.SEVERE, "E-Mail address already in use.");
            this.emailAddressAlreadyInUseExceptionMessage = e.toString();
            this.duplicateEmailAddress = true;
            return "/views/customer/index.xhtml";
        } catch (customerTooYoungException e) {
            this.logger.log(Level.SEVERE, "The customer is under 18 years old.");
            this.customerTooYoungExceptionMessage = e.toString();
            this.customerTooYoung = true;
            return "index.xhtml";
        }
        this.logger.log(Level.INFO, "Customer successful registered.");
        return "/views/customer/customerOverview.xhtml";
    }

    public String updateCustomer() {
        try {
            if (this.newPhoneNumber != null && !this.newPhoneNumber.equals("")) {
                this.lastRegistered.addPhoneNumber(this.newPhoneNumber);
            }
            this.lastRegistered = customerService.updateCustomer(this.lastRegistered);
            this.transactionModel.setCustomerTransactions(this.transactionService.getCustomerTransactions(this.lastRegistered));
            this.newPhoneNumber = "";
        } catch (emailAddressAlreadyInUseException e) {
            this.logger.log(Level.SEVERE, "E-Mail address already in use.");
            this.emailAddressAlreadyInUseExceptionMessage = e.toString();
            this.duplicateEmailAddress = true;
            return "editCustomer.xhtml";
        } catch (pinTooShortException e) {
            this.logger.log(Level.SEVERE, "PIN to short.");
            this.pinTooShort = true;
            this.pinTooShortExceptionMessage = e.toString();
            return "editCustomer.xhtml";
        }
        this.logger.log(Level.INFO, "Successful updated customer.");
        return "customerOverview.xhtml";
    }

    public String showEditCustomerPage() {
        return "editCustomer.xhtml";
    }

    public String goToTransactionScreen() {
        return "/views/transaction/transactionView.xhtml";
    }

    public String showCustomerOverview() {
        return "/views/customer/customerOverview.xhtml";
    }

    public String logout() {
        this.customerLoggedOut = true;
        this.lastRegistered = new Customer();
        this.customerSuccessfulRegisteredOrLoggedIn = false;
        this.logger.log(Level.INFO, "Successful logged out.");
        return "index.xhtml";
    }

    public String deleteCustomer() {
        customerService.deleteCustomer(this.lastRegistered);
        this.customerDeleted = true;
        this.lastRegistered = new Customer();
        this.customerSuccessfulRegisteredOrLoggedIn = false;
        this.logger.log(Level.INFO, "Successful deleted customer.");
        return "/views/customer/index.xhtml";
    }

    public String addAccountToCustomer() {
        this.lastRegistered = this.customerService.addAccountToCustomer(this.lastRegistered);
        this.transactionModel.setCustomerTransactions(this.transactionService.getCustomerTransactions(this.lastRegistered));
        // Set the currently created account as the last created Account.
        this.lastCreatedAccount = this.lastRegistered.getAccounts().get(this.lastRegistered.getAccounts().size() - 1);
        return "newAccountAddedConfirmationPage.xhtml";
    }

    public String removePhoneNumberFromEditCustomer(String phoneNumber) {
        this.lastRegistered = this.customerService.removePhoneNumber(this.lastRegistered, phoneNumber);
        return "/views/customer/editCustomer.xhtml";
    }

    public String addPhoneNumber() {
        this.lastRegistered = this.customerService.addPhoneNumber(this.lastRegistered, this.newPhoneNumber);
        this.newPhoneNumber = "";
        return "/views/customer/editCustomer.xhtml";
    }

    public String goToTanGenerationScreen() {
        return "/views/tan/tanGenerationRequestScreen.xhtml";
    }

    public String generateNewTans() {
        // check if the given tan belongs to the customer.
        boolean tanIsValid = this.customerService.checkIfTanIsValidForCustomer(this.lastRegistered, this.generateNewTansAuthenticationTan);
        if (tanIsValid) {
            // if true, generate new tans for the customer.
            this.lastRegistered = this.customerService.generateTanNumbersForCustomer(20, this.lastRegistered);
            this.generateNewTansAuthenticationTan.setTanNumber(0);
            return "/views/tan/tansGeneratedConfirmationScreen.xhtml";
        }
        this.generateNewTansAuthenticationTan.setTanNumber(0);
        return "/views/tan/tanGenerationErrorScreen.xhtml";
    }

}
