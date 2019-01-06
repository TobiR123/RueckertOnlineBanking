package RueckertOnlineBanking.service;

import RueckertOnlineBanking.entity.*;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RequestScoped
public class CustomerService implements Serializable {

    @PersistenceContext(unitName = "examplePU")
    private EntityManager entityManager;

    @Transactional
    public Customer registerCustomer(Customer customer) {

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

        PIN pin = new PIN();
        pin.setPinNumber(pin.generatePin());
        entityManager.persist(pin);

        // generate 20 TAN numbers for customer.
        for(int i = 0; i < 20; i++) {
            TAN tan = new TAN();
            entityManager.persist(tan);
            customer.addTanNumber(tan);
        }

        customer.setPinNumber(pin);
        customer.addAccount(account);

        entityManager.persist(customer.getAddress());
        entityManager.persist(customer.geteMailAddress());
        entityManager.persist(customer);

        EMailAddress systemEmailAddress = this.getSystemEmailAddressRecord();
        String topic = "Ihre TAN-Nummern zum bestätigen Ihrer Transaktionen";
//        String message = "Sehr geehrter Kunde,\ndiese E-Mail enthält ihre generierten TAN-Nummern zur Bestätigung durchgeführter Transaktionen.\n\n"
//                + customer.getTanNumbersAsString();
        String message = customer.getTanNumbersAsString();
        Email email = new Email(systemEmailAddress, customer.geteMailAddress(), topic, message);
        entityManager.persist(email);
        System.out.println("SENT EMAIL: " + email);

        return customer;
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
    private EMailAddress getSystemEmailAddressRecord() {
        String systemEmailAddress = "tobias.rueckert@st.oth-regensburg.de";

        TypedQuery<EMailAddress> systemEmailAddressQuery = entityManager.createQuery(
                "SELECT  m FROM  EMailAddress  AS m WHERE m.mailAddress = :systemEmail",
                EMailAddress.class
        );
        systemEmailAddressQuery.setParameter("systemEmail", systemEmailAddress);
        List<EMailAddress> result = (List<EMailAddress>)systemEmailAddressQuery.getResultList();

        if(result.size() > 0) {
            return result.get(0);
        }

        // In case the system email address does not exist yet, create it.
        EMailAddress systemEmailAddressRecord = new EMailAddress(systemEmailAddress);
        entityManager.persist(systemEmailAddressRecord);
        return systemEmailAddressRecord;
    }

    @Transactional
    public boolean loginCustomer(EMailAddress eMailAddress, PIN pin){

        // ##### Get the email record with the given address from the database. ##### //
        TypedQuery<EMailAddress> emailAddressQuery = entityManager.createQuery(
                "SELECT e FROM EMailAddress  AS e WHERE e.mailAddress = :emailAddress",
                EMailAddress.class
        );
        emailAddressQuery.setParameter("emailAddress", eMailAddress.getMailAddress());
        List<EMailAddress> eMailAddresses= emailAddressQuery.getResultList();
        if(eMailAddresses.size() == 0){
            return false;
        }
        EMailAddress eMailAddressRecord = eMailAddresses.get(0);

//        // ##### Get the pin record with the given number from the database. ##### //
//        TypedQuery<PIN> pinQuery = entityManager.createQuery(
//                "SELECT p FROM PIN AS p WHERE  p.pinNumber = :pin",
//                PIN.class
//        );
//        pinQuery.setParameter("pin", pin.getPinNumber());
//        List<PIN> pins = pinQuery.getResultList();
//        if(pins.size() == 0){
//            return false;
//        }

        // ##### Check if there exists an Customer with the found email record and a matching pin. ##### //
        TypedQuery<Customer> customerQuery = entityManager.createQuery(
                "SELECT c FROM Customer AS c WHERE :eMailAddressRecord = c.eMailAddress AND c.pinNumber.pinNumber = :pin",
                Customer.class
        );
        customerQuery.setParameter("eMailAddressRecord", eMailAddressRecord);
        customerQuery.setParameter("pin", pin.getPinNumber());


        List<Customer> customer = customerQuery.getResultList();
        System.out.println(customer);

        if(customer.size() == 0) {
            return false;
        }
        return true;
    }

    @Transactional
    public Customer updateCustomer(Customer customer) {
        Customer original = this.getCustomerById(customer.getId());

        original.setFirstname(customer.getFirstname());

        entityManager.persist(original);

        return original;
    }

    private Customer getCustomerById(long id){
        return entityManager.find(Customer.class, id);
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
