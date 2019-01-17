package RueckertOnlineBanking.service;

import RueckertOnlineBanking.entity.Account;
import RueckertOnlineBanking.loggerFactory.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebService
@RequestScoped
public class AccountService implements Serializable {

    @PersistenceContext(unitName = "RueckertPU")
    private EntityManager entityManager;

    @Inject
    private LoggerFactory loggerFactory;
    private Logger logger;

    @PostConstruct
    public void init() {
        logger = loggerFactory.create();
    }

    @WebMethod
    @Transactional(Transactional.TxType.REQUIRED)
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
        this.logger.log(Level.INFO, "Account successfull created.");
        return account;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    private boolean checkIfAccountAlreadyExists(Account account) {

        TypedQuery<Account> accountQuery = entityManager.createQuery(
                "SELECT a FROM Account AS a WHERE a.iban = :ibanToCheck",
                Account.class
        );
        accountQuery.setParameter("ibanToCheck", account.getIban());
        List<Account> result = accountQuery.getResultList();

        if(result.size() > 0){
            this.logger.log(Level.INFO, "Account already exists. Generate a new one.");
            return true;
        }
        return false;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public Account getAccountById(long accountId){
        return this.entityManager.find(Account.class, accountId);
    }

    @Transactional(Transactional.TxType.REQUIRED)
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
