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
public class TransactionService implements Serializable {
    // Dependencies:
    @PersistenceContext(unitName = "examplePU")
    private EntityManager entityManager;

    @Transactional
    public Account getAccountByIban(String iban) {
        TypedQuery<Account> accountQuery = entityManager.createQuery(
                "SELECT a FROM Account AS a WHERE a.iban = :iban",
                Account.class
        );
        List<Account> result = accountQuery.getResultList();
        if(result.size() > 0){
            return result.get(0);
        }
        return null;
    }

    @Transactional
    public boolean checkIfSenderHasEnoughMoney(Account sender) {


        return true;
    }


}
