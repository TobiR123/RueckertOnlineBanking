package RueckertOnlineBanking.ui.model;

import RueckertOnlineBanking.entity.Account;
import RueckertOnlineBanking.entity.Customer;
import RueckertOnlineBanking.entity.customExceptions.senderNotEnoughMoneyException;
import RueckertOnlineBanking.service.AccountService;
//import RueckertOnlineBanking.service.MoneyTransportServiceRemote;
import RueckertOnlineBanking.service.MoneyTransportServiceIF;
import RueckertOnlineBanking.service.MoneyTransportServiceRemote;
import RueckertOnlineBanking.service.TransactionService;
import RueckertOnlineBanking.ui.converter.SenderAccountConverter;
import RueckertOnlineBanking.loggerFactory.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class MoneyTransportModel implements Serializable {
    // ##### PROPERTIES ##### //
    private double amount;
    private Account selectedAccount;
    @Inject
    private AccountService accountService;
    @Inject
    private CustomerModel customerModel;
    @Inject
    private SenderAccountConverter converterSelectedAccount;
    @Inject
    private TransactionService transactionService;
    @Inject
    private MoneyTransportServiceIF moneyTransportService;
    @Inject
    private LoggerFactory loggerFactory;
    private Logger logger;

    private boolean senderNeedsCreditForMoneyTransport = false;
    private boolean senderAccountIsOutOfCreditRange = false;
    private boolean moneyTransportValid = true;

    private double creditAfter = 0.0;

    public MoneyTransportModel(){
        this.amount = 0;
        this.selectedAccount = new Account();
    }

    @PostConstruct
    public void init() {
        this.logger = this.loggerFactory.create();
    }

    // ##### GETTER AND SETTER ##### //
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Account getSelectedAccount() {
        return selectedAccount;
    }

    public void setSelectedAccount(Account selectedAccount) {
        this.selectedAccount = selectedAccount;
    }

    public SenderAccountConverter getConverterSelectedAccount() {
        return converterSelectedAccount;
    }

    public void setConverterSelectedAccount(SenderAccountConverter converterSelectedAccount) {
        this.converterSelectedAccount = converterSelectedAccount;
    }

    public boolean isSenderNeedsCreditForMoneyTransport() {
        return senderNeedsCreditForMoneyTransport;
    }

    public void setSenderNeedsCreditForMoneyTransport(boolean senderNeedsCreditForMoneyTransport) {
        this.senderNeedsCreditForMoneyTransport = senderNeedsCreditForMoneyTransport;
    }

    public boolean isSenderAccountIsOutOfCreditRange() {
        return senderAccountIsOutOfCreditRange;
    }

    public void setSenderAccountIsOutOfCreditRange(boolean senderAccountIsOutOfCreditRange) {
        this.senderAccountIsOutOfCreditRange = senderAccountIsOutOfCreditRange;
    }

    public boolean isMoneyTransportValid() {
        return moneyTransportValid;
    }

    public void setMoneyTransportValid(boolean moneyTransportValid) {
        this.moneyTransportValid = moneyTransportValid;
    }

    public double getCreditAfter() {
        return creditAfter;
    }

    public void setCreditAfter(double creditAfter) {
        this.creditAfter = creditAfter;
    }

    // ##### METHODS ##### //
    public String executeMoneyTransport() {
        this.moneyTransportService.callMoneyTransportCompany(customerModel.getLastRegistered(), this.amount);

        Customer updatedCustomer = this.transactionService.reduceAccountCredit(customerModel.getLastRegistered(), this.selectedAccount, this.amount);
        customerModel.setLastRegistered(updatedCustomer);
        this.moneyTransportValid = true;
        this.senderNeedsCreditForMoneyTransport = false;
        this.senderAccountIsOutOfCreditRange = false;
        this.amount = 0.0;

        return "moneyTransportConfirmationPage.xhtml";
    }

    public String goToMoneyTransportOverviewScreen() {
        try {
            this.selectedAccount = this.accountService.getAccountById(this.selectedAccount.getId());
            this.creditAfter = this.selectedAccount.getCredit() - this.amount;
            transactionService.senderHasEnoughMoney(this.selectedAccount, this.amount);
        } catch (senderNotEnoughMoneyException e) {
            if (e.getCredit() > -100) {
                this.logger.log(Level.SEVERE, "The sender needs a credit to execute the transaction.");
                this.senderNeedsCreditForMoneyTransport = true;
                this.senderAccountIsOutOfCreditRange = false;
                this.moneyTransportValid = true;
            } else {
                this.logger.log(Level.SEVERE, "The sender has not enough money to execute the transaction. No credit can be given.");
                this.senderNeedsCreditForMoneyTransport = false;
                this.senderAccountIsOutOfCreditRange = true;
                this.moneyTransportValid = false;
            }
        }

        if(this.moneyTransportValid && customerModel.isCustomerSuccessfulRegisteredOrLoggedIn()){
            this.moneyTransportValid = true;
        } else {
            this.moneyTransportValid = false;
        }
        return "moneyTransportOverviewScreen.xhtml";
    }

    public String goToCustomerOverviewScreen() {
        return "/views/customer/customerOverview.xhtml";
    }

    public String goToMoneyTransportScreen() { return "/views/moneyTransport/moneyTransport.xhtml"; }


}
