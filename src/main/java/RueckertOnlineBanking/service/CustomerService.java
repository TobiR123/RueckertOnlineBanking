package RueckertOnlineBanking.service;

import RueckertOnlineBanking.entity.*;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@RequestScoped
public class CustomerService implements Serializable {

    @PersistenceContext(unitName = "examplePU")
    private EntityManager entityManager;

    @Transactional
    public Customer registerCustomer(EMailAddress eMailAddress, Address address, Customer customer) {

        Account account = new Account();
        entityManager.persist(account);

        PIN pin = new PIN();
        entityManager.persist(pin);

        // generate 20 TAN numbers for customer.
        for(int i = 0; i < 20; i++) {
            TAN tan = new TAN();
            entityManager.persist(tan);
            customer.addTanNumber(tan);
        }

        customer.setPinNumber(pin);
        customer.addAccount(account);

        entityManager.persist(eMailAddress);
        entityManager.persist(address);
        entityManager.persist(customer);
        return customer;
    }

    @Transactional
    public boolean loginCustomer(String eMailAddress, int pin){

        Query emailAddressQuery = entityManager.createQuery(
                "SELECT e FROM EMailAddress  AS e WHERE e.mailAddress = :emailAddress"
        );
        emailAddressQuery.setParameter("emailAddress", eMailAddress);
        List<EMailAddress> eMailAddresses= (List<EMailAddress>)emailAddressQuery.getResultList();
        if(eMailAddresses.size() == 0){
            return false;
        }
        EMailAddress eMailAddressRecord = eMailAddresses.get(0);


        Query pinQuery = entityManager.createQuery(
                "SELECT p FROM PIN AS p WHERE  p.pinNumber = :pin"
        );
        pinQuery.setParameter("pin", pin);
        List<PIN> pins = (List<PIN>)pinQuery.getResultList();
        if(pins.size() == 0){
            return false;
        }
        PIN pinRecord = pins.get(0);


        TypedQuery<Customer> customerQuery = entityManager.createQuery(
                "SELECT c FROM Customer AS c WHERE :eMailAddressRecord MEMBER OF c.eMailAddresses AND c.pinNumber = :pin",
                Customer.class
        );
        customerQuery.setParameter("eMailAddressRecord", eMailAddressRecord);
        customerQuery.setParameter("pin", pinRecord);


        List<Customer> customer = customerQuery.getResultList();
        System.out.println(customer);

        if(customer.size() == 0) {
            System.out.println(this.getCustomers().size());

            return false;
        }
        return true;
    }

    @Transactional
    public List<Customer> getCustomers(){
        TypedQuery<Customer> query = entityManager.createQuery(
                "SELECT c FROM Customer AS c",
                Customer.class
        );
        return query.getResultList();
    }
}
