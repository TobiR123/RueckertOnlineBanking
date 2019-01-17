package RueckertOnlineBanking.ui.model;

import RueckertOnlineBanking.entity.Account;
import RueckertOnlineBanking.entity.Customer;
import RueckertOnlineBanking.entity.TAN;
import RueckertOnlineBanking.entity.Transaction;
import RueckertOnlineBanking.entity.customExceptions.invalidTanException;
import RueckertOnlineBanking.entity.customExceptions.senderNotEnoughMoneyException;
import RueckertOnlineBanking.service.AccountService;
import RueckertOnlineBanking.service.CustomerService;
import RueckertOnlineBanking.service.TransactionService;
import RueckertOnlineBanking.ui.converter.SenderAccountConverter;
import RueckertOnlineBanking.loggerFactory.LoggerFactory;

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
public class TransactionModel implements Serializable {

    // ##### PROPERTIES ##### //
    @Inject
    private CustomerService customerService;
    @Inject
    private AccountService accountService;
    @Inject
    private TransactionService transactionService;
    @Inject
    private CustomerModel customerModel;
    @Inject
    private LoggerFactory loggerFactory;
    private Logger logger;
    @Inject
    private SenderAccountConverter converterSelectedSenderAccount;
    private Account selectedSenderAccount;

    // Transaction attributes.
    private String receiverIban;
    private String receiverBic;
    private double amount;
    private String description;
    private TAN tanNumber;

    private List<Transaction> customerTransactions;

    // This flag shows, if the receiver account is registered at this bank or not.
    private boolean receiverAccountNotFound = false;
    // This flag shows, if the sender account is under 0 but it is possible that the customer gets an credit for this transaction.
    private boolean senderNeedsCreditForTransaction = false;
    // This flag shows, if the customer account is out of range for a credit.
    private boolean senderAccountIsOutOfCreditRange = false;
    // If the customers account is out of range for a credit, do not show the 'execute transaction' button.
    private boolean transactionValid = true;
    // If the tan is invalid, do not execute the transaction.
    private boolean invalidTan = false;

    public TransactionModel() {
        this.customerService = new CustomerService();
        this.transactionService = new TransactionService();
        this.tanNumber = new TAN();
        this.tanNumber.setTanNumber(0);
        this.customerTransactions = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        this.logger = this.loggerFactory.create();
    }

    // ##### GETTER AND SETTER ##### //

    public List<Transaction> getCustomerTransactions() {
        return customerTransactions;
    }

    public void setCustomerTransactions(List<Transaction> customerTransactions) {
        this.customerTransactions = customerTransactions;
    }

    public boolean isInvalidTan() {
        return invalidTan;
    }

    public void setInvalidTan(boolean invalidTan) {
        this.invalidTan = invalidTan;
    }

    public boolean isTransactionValid() {
        return transactionValid;
    }

    public void setTransactionValid(boolean transactionValid) {
        this.transactionValid = transactionValid;
    }

    public boolean isReceiverAccountNotFound() {
        return receiverAccountNotFound;
    }

    public void setReceiverAccountNotFound(boolean receiverAccountNotFound) { this.receiverAccountNotFound = receiverAccountNotFound; }

    public boolean isSenderNeedsCreditForTransaction() {
        return senderNeedsCreditForTransaction;
    }

    public void setSenderNeedsCreditForTransaction(boolean senderNeedsCreditForTransaction) { this.senderNeedsCreditForTransaction = senderNeedsCreditForTransaction; }

    public boolean isSenderAccountIsOutOfCreditRange() {
        return senderAccountIsOutOfCreditRange;
    }

    public void setSenderAccountIsOutOfCreditRange(boolean senderAccountIsOutOfCreditRange) { this.senderAccountIsOutOfCreditRange = senderAccountIsOutOfCreditRange; }

    public SenderAccountConverter getConverterSelectedSenderAccount() {
        return converterSelectedSenderAccount;
    }

    public void setConverterSelectedSenderAccount(SenderAccountConverter converterSelectedSenderAccount) { this.converterSelectedSenderAccount = converterSelectedSenderAccount; }

    public Account getSelectedSenderAccount() {
        return selectedSenderAccount;
    }

    public void setSelectedSenderAccount(Account selectedSenderAccount) { this.selectedSenderAccount = selectedSenderAccount; }

    public String getReceiverIban() {
        return receiverIban;
    }

    public void setReceiverIban(String receiverIban) {
        this.receiverIban = receiverIban;
    }

    public String getReceiverBic() {
        return receiverBic;
    }

    public void setReceiverBic(String receiverBic) {
        this.receiverBic = receiverBic;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TAN getTanNumber() {
        return tanNumber;
    }

    public void setTanNumber(TAN tanNumber) {
        this.tanNumber = tanNumber;
    }


    // ##### METHODS ##### //
    public String executeTransaction() {
        Customer senderCustomer = this.customerModel.getLastRegistered();
        try {
            Transaction successfulTransaction = transactionService.executeTransaction(this.selectedSenderAccount, senderCustomer, this.receiverIban, this.receiverBic, this.amount, this.description, this.tanNumber);

            this.customerTransactions = this.transactionService.getCustomerTransactions(senderCustomer);

            this.customerModel.setLastRegistered(this.customerService.getCustomerById(senderCustomer.getId()));
        } catch (invalidTanException e) {
            this.logger.log(Level.SEVERE, "The given TAN number is invalid.");
            this.invalidTan = true;
            return "transactionOverviewScreen.xhtml";
        }
        return "transactionConfirmationPage.xhtml";
    }

    public String goToTransactionOverviewScreen() throws senderNotEnoughMoneyException {
        try {
            this.selectedSenderAccount = this.accountService.getAccountById(this.selectedSenderAccount.getId());
            transactionService.senderHasEnoughMoney(this.selectedSenderAccount, this.amount);
        } catch (senderNotEnoughMoneyException e) {
            if (e.getCredit() > -100) {
                this.logger.log(Level.SEVERE, "The sender needs a credit to execute the transaction.");
                this.senderNeedsCreditForTransaction = true;
                this.senderAccountIsOutOfCreditRange = false;
                this.transactionValid = true;
            } else {
                this.logger.log(Level.SEVERE, "The sender has not enough money to execute the transaction. No credit can be given.");
                this.senderNeedsCreditForTransaction = false;
                this.senderAccountIsOutOfCreditRange = true;
                this.transactionValid = false;
            }
        } finally {
            if (accountService.getAccountByIban(this.receiverIban) == null) {
                this.logger.log(Level.SEVERE, "The receiver account could not be found.");
                this.receiverAccountNotFound = true;
            }
        }

        return "transactionOverviewScreen.xhtml";
    }

    public String goToCustomerOverviewScreen() {
        this.receiverIban = "";
        this.receiverBic = "";
        this.amount = 0.0;
        this.description = "";
        return "/views/customer/customerOverview.xhtml";
    }

}
