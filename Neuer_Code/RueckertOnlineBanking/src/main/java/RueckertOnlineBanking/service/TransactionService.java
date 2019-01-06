package RueckertOnlineBanking.service;

import RueckertOnlineBanking.entity.Account;
import RueckertOnlineBanking.entity.Customer;
import RueckertOnlineBanking.entity.TAN;
import RueckertOnlineBanking.entity.Transaction;
import RueckertOnlineBanking.entity.customExceptions.invalidTanException;
import RueckertOnlineBanking.entity.customExceptions.senderNotEnoughMoneyException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@RequestScoped
public class TransactionService implements Serializable {
    // Dependencies:
    @PersistenceContext(unitName = "examplePU")
    private EntityManager entityManager;

    @Inject
    private CustomerService customerService;

    @Inject AccountService accountService;

    @Transactional
    public Transaction executeTransaction(Account senderAccount, Customer senderCustomer, Account receiver, String receiverIban, String receiverBic, double amount, String description, TAN tan) throws invalidTanException {
        senderAccount = this.accountService.getAccountById(senderAccount.getId());

        // Get the specific tan record of the customer from the database.
        TAN tanRecord = this.customerService.getTanRecordOfCustomerByTanNumber(senderCustomer, tan.getTanNumber());

        senderCustomer = this.customerService.getCustomerById(senderCustomer.getId());

        // Check if the given TAN belongs to the customer that executes the transaction.
        if(tanRecord == null) {
            throw new invalidTanException();
        } else {
            tanRecord = this.customerService.getTanById(tanRecord.getId());

            List<Account> senderAccountList = senderCustomer.getAccounts();
            int indexOfSenderAccount = senderAccountList.indexOf(senderAccount);

            senderAccount.setCredit(senderAccount.getCredit() - amount);

            entityManager.persist(senderAccount);

            if(receiver != null) {
                receiver.setCredit(receiver.getCredit() + amount);
            } else {
                // In case the receiver account is not registered at our bank, set the given values for it to store them in the transaction object.
                receiver= new Account();
                receiver.setIban(receiverIban);
                receiver.setBic(receiverBic);
            }
            entityManager.persist(receiver);

            senderAccountList.set(indexOfSenderAccount, senderAccount);
            senderCustomer.setAccounts(senderAccountList);

            // Problem: Aktualisierung des Kontostands beim Empfänger sofort ändern! evtl. customer attribut in account mit aufnehmen!
            Transaction transaction = new Transaction(senderAccount, receiver, amount, description, tanRecord);

            tanRecord.setTransaction(transaction);
            entityManager.persist(tanRecord);

            entityManager.persist(transaction);

            senderCustomer.removeTanNumber(tanRecord);
            entityManager.persist(senderCustomer);

            return transaction;
        }
    }


    @Transactional
    public boolean senderHasEnoughMoney(Account senderAccount, double amount) throws senderNotEnoughMoneyException {
        if(senderAccount.getCredit() - amount < 0.0) {
            throw new senderNotEnoughMoneyException(senderAccount.getCredit() - amount);
        } else {
            return true;
        }
    }


}
