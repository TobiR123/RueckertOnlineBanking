package RueckertOnlineBanking.service;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@RequestScoped
public class TransactionService {
    // Dependencies:
    @PersistenceContext(unitName = "examplePU")
    private EntityManager entityManager;


}
