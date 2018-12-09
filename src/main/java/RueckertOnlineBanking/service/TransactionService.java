package RueckertOnlineBanking.service;

import RueckertOnlineBanking.entity.Account;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.io.Serializable;

@RequestScoped
public class TransactionService implements Serializable {
    // Dependencies:
    @PersistenceContext(unitName = "examplePU")
    private EntityManager entityManager;

    @Transactional
    public boolean checkIfSenderHasEnoughMoney(Account sender) {


        return true;
    }


}
