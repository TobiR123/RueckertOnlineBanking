package RueckertOnlineBanking.service;

import RueckertOnlineBanking.entity.Account;
import RueckertOnlineBanking.entity.Customer;
import RueckertOnlineBanking.entity.TAN;
import RueckertOnlineBanking.entity.Transaction;
import RueckertOnlineBanking.entity.customExceptions.invalidTanException;
import RueckertOnlineBanking.entity.customExceptions.senderNotEnoughMoneyException;
import RueckertOnlineBanking.loggerFactory.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.TransactionScoped;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequestScoped
public class TransactionService implements Serializable {

    @PersistenceContext(unitName = "RueckertPU")
    private EntityManager entityManager;

    @Inject
    private LoggerFactory loggerFactory;
    private Logger logger;

    @PostConstruct
    public void init() {
        logger = loggerFactory.create();
    }

    @Inject
    private CustomerService customerService;

    @Inject AccountService accountService;


    @Transactional(Transactional.TxType.REQUIRED)
    public Transaction executeTransaction(Account senderAccount, Customer senderCustomer, String receiverIban, String receiverBic, double amount, String description, TAN tan) throws invalidTanException {
        // Get the specific tan record of the customer from the database.
        TAN tanRecord = this.customerService.getTanRecordOfCustomerByTanNumber(senderCustomer, tan.getTanNumber());

        senderCustomer = this.customerService.getCustomerById(senderCustomer.getId());

        // Check if the given TAN belongs to the customer that executes the transaction.
        if(tanRecord == null) {
            throw new invalidTanException();
        } else {
            tanRecord = this.customerService.getTanById(tanRecord.getId());


            List<Account> senderAccountList = senderCustomer.getAccounts();
            // Retrieve sender account from database.
            senderAccount = this.accountService.getAccountById(senderAccount.getId());
            int indexOfSenderAccount = senderAccountList.indexOf(senderAccount);
            senderAccount.setCredit(senderAccount.getCredit() - amount);
            entityManager.persist(senderAccount);

            // Fetch the receiver account from database.
            Account receiverAccount = accountService.getAccountByIban(receiverIban);
            if(receiverAccount != null) {
                receiverAccount.setCredit(receiverAccount.getCredit() + amount);
            } else {
                // In case the receiver account is not registered at our bank, still set the given values for it to store them in the transaction object.
                receiverAccount = new Account();
                receiverAccount.setIban(receiverIban);
                receiverAccount.setBic(receiverBic);
            }
            entityManager.persist(receiverAccount);

            senderAccountList.set(indexOfSenderAccount, senderAccount);
            senderCustomer.setAccounts(senderAccountList);

            Transaction transaction = new Transaction(senderAccount, receiverAccount, amount, description, tanRecord);

            tanRecord.setTransaction(transaction);
            entityManager.persist(tanRecord);

            entityManager.persist(transaction);

            senderCustomer.removeTanNumber(tanRecord);
            entityManager.persist(senderCustomer);

            this.logger.log(Level.INFO, "Successfully executed transaction.");
            return transaction;
        }
    }


    @Transactional(Transactional.TxType.REQUIRED)
    public boolean senderHasEnoughMoney(Account senderAccount, double amount) throws senderNotEnoughMoneyException {
        if(senderAccount.getCredit() - amount < 0.0) {
            throw new senderNotEnoughMoneyException(senderAccount.getCredit() - amount);
        } else {
            return true;
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public List<Transaction> getCustomerTransactions(Customer customer) {
        // A transaction object has always two accounts: a sender and a receiver.
        // This method returns all transactions of all accounts of the given customer (regardless of whether the account is sender or receiver).
        List<Transaction> customerTransactions = new ArrayList<>();

        List<Account> customerAccounts = customer.getAccounts();
        for (Account a :
                customerAccounts) {
            TypedQuery<Transaction> transactionQuery = entityManager.createQuery(
                    "SELECT t FROM Transaction AS t WHERE t.sender = :senderAcc OR t.receiver = :receiverAcc",
                    Transaction.class
            );
            transactionQuery.setParameter("senderAcc", a);
            transactionQuery.setParameter("receiverAcc", a);
            List<Transaction> accountTransactions = transactionQuery.getResultList();

            // For each fetched transaction, check if it is already in the customers transactions. If not, add it to the list.
            if(accountTransactions != null){
                for (Transaction t :
                        accountTransactions) {
                    // If the transaction is not in the customers transactions list, add it to it.
                    if(customerTransactions.indexOf(t) == -1){
                        customerTransactions.add(t);
                    }
                }
            }
        }
        return customerTransactions;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public Customer reduceAccountCredit(Customer customer, Account account, double amount) {
        Account originalAccount = this.accountService.getAccountById(account.getId());

        Customer originalCustomer = this.customerService.getCustomerById(customer.getId());
        List<Account> customerAccounts = originalCustomer.getAccounts();
        int indexOfChangedAccount = customerAccounts.indexOf(account);

        originalAccount.setCredit(originalAccount.getCredit() - amount);
        entityManager.persist(originalAccount);

        customerAccounts.set(indexOfChangedAccount, originalAccount);
        originalCustomer.setAccounts(customerAccounts);
        entityManager.persist(originalCustomer);

        return originalCustomer;
    }
}
