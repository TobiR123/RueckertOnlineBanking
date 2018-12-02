package RueckertOnlineBanking.service;

import RueckertOnlineBanking.entity.Customer;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;

@RequestScoped
public class CustomerService implements Serializable {

    @PersistenceContext(unitName = "examplePU")
    private EntityManager entityManager;

    @Transactional
    public Customer registerCustomer(Customer customer) {

        entityManager.persist(customer);

        return customer;
    }
}
