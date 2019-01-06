package RueckertOnlineBanking.service;

import RueckertOnlineBanking.entity.Account;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@RequestScoped
public class AccountService implements Serializable {

    @PersistenceContext(unitName = "examplePU")
    private EntityManager entityManager;

    @Transactional
    public Account createAccount() {
        Account account = new Account();
        // Check if there already exists an account with the exact same IBAN.
        boolean accountAlreadyExists = this.checkIfAccountAlreadyExists(account);
        if(accountAlreadyExists){
            while(accountAlreadyExists) {
                account = new Account();
                accountAlreadyExists = this.checkIfAccountAlreadyExists(account);
            }
        }
        entityManager.persist(account);
        return account;
    }

    @Transactional
    private boolean checkIfAccountAlreadyExists(Account account) {

        TypedQuery<Account> accountQuery = entityManager.createQuery(
                "SELECT a FROM Account AS a WHERE a.iban = :ibanToCheck",
                Account.class
        );
        accountQuery.setParameter("ibanToCheck", account.getIban());
        List<Account> result = accountQuery.getResultList();

        if(result.size() > 0){
            return true;
        }
        return false;
    }

    @Transactional
    public Account getAccountById(long accountId){
        return this.entityManager.find(Account.class, accountId);
    }

    @Transactional
    public Account getAccountByIban(String iban) {
        TypedQuery<Account> accountQuery = entityManager.createQuery(
                "SELECT a FROM Account AS a WHERE a.iban = :iban",
                Account.class
        );
        accountQuery.setParameter("iban", iban);
        List<Account> result = accountQuery.getResultList();
        if(result.size() > 0){
            return result.get(0);
        }
        return null;
    }
}
