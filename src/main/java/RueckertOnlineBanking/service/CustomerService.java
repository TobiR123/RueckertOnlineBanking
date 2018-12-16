package RueckertOnlineBanking.service;

import RueckertOnlineBanking.entity.*;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@RequestScoped
public class CustomerService implements Serializable {

    @PersistenceContext(unitName = "examplePU")
    private EntityManager entityManager;

    @Transactional
    public Customer registerCustomer(EMailAddress eMailAddress, Address address, Customer customer) {

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

        EMailAddress systemEmailAddress = this.getSystemEmailAddressRecord();
        String topic = "Ihre TAN-Nummern zum bestätigen Ihrer Transaktionen";
//        String message = "Sehr geehrter Kunde,\ndiese E-Mail enthält ihre generierten TAN-Nummern zur Bestätigung durchgeführter Transaktionen.\n\n"
//                + customer.getTanNumbersAsString();
        String message = customer.getTanNumbersAsString();
        Email email = new Email(systemEmailAddress, eMailAddress, topic, message);
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
    public boolean loginCustomer(String eMailAddress, int pin){

        // ##### Get the email record with the given address from the database. ##### //
        Query emailAddressQuery = entityManager.createQuery(
                "SELECT e FROM EMailAddress  AS e WHERE e.mailAddress = :emailAddress"
        );
        emailAddressQuery.setParameter("emailAddress", eMailAddress);
        List<EMailAddress> eMailAddresses= (List<EMailAddress>)emailAddressQuery.getResultList();
        if(eMailAddresses.size() == 0){
            return false;
        }
        EMailAddress eMailAddressRecord = eMailAddresses.get(0);

        // ##### Get the pin record with the given number from the database. ##### //
        Query pinQuery = entityManager.createQuery(
                "SELECT p FROM PIN AS p WHERE  p.pinNumber = :pin"
        );
        pinQuery.setParameter("pin", pin);
        List<PIN> pins = (List<PIN>)pinQuery.getResultList();
        if(pins.size() == 0){
            return false;
        }
        PIN pinRecord = pins.get(0);


        // ##### Check if there exists an Customer with the found email record and pin. ##### //
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
    public Customer updateCustomer(long customerId, String firstname, String lastname, long eMailAddressId, String eMailAddress, int phoneNumber, Date dateOfBirth, long addressId, String street, String houseNumber, int postalcode, String place, long pinId, int pinNumber) {
        // Fetch all required datasets.
        TypedQuery<Customer> customerQuery = entityManager.createQuery(
                "SELECT c FROM  Customer AS c WHERE :customerId = c.id",
                Customer.class
        );

        customerQuery.setParameter("customerId", customerId);
        Customer customerToUpdate = customerQuery.getResultList().get(0);

        TypedQuery<Address> addressQuery = entityManager.createQuery(
                "SELECT a FROM  Address AS a WHERE :addressId = a.id",
                Address.class
        );
        addressQuery.setParameter("addressId", addressId);
        Address addressToUpdate = addressQuery.getResultList().get(0);

        TypedQuery<PIN> pinQuery = entityManager.createQuery(
                "SELECT p FROM  PIN AS p WHERE :pinId = p.id",
                PIN.class
        );
        pinQuery.setParameter("pinId", pinId);
        PIN pinToUpdate = pinQuery.getResultList().get(0);

        TypedQuery<EMailAddress> emailAddressQuery = entityManager.createQuery(
                "SELECT e FROM  EMailAddress AS e WHERE :emailAddressId = e.id",
                EMailAddress.class
        );
        emailAddressQuery.setParameter("emailAddressId", eMailAddressId);
        EMailAddress emailAddressToUpdate = emailAddressQuery.getResultList().get(0);

        // Set updated values.
        pinToUpdate.setPinNumber(pinNumber);
        entityManager.persist(pinToUpdate);

        addressToUpdate.setStreet(street);
        addressToUpdate.setHouseNumber(houseNumber);
        addressToUpdate.setPostcode(postalcode);
        addressToUpdate.setPlace(place);
        entityManager.persist(addressToUpdate);

        emailAddressToUpdate.setMailAddress(eMailAddress);
        entityManager.persist(emailAddressToUpdate);

        customerToUpdate.setFirstname(firstname);
        customerToUpdate.setLastname(lastname);
        customerToUpdate.updateEmailAddressOnIndex(0, emailAddressToUpdate);
        customerToUpdate.setPhoneNumber(phoneNumber);
        customerToUpdate.setDateOfBirth(dateOfBirth);
        customerToUpdate.setAddress(addressToUpdate);
        customerToUpdate.setPinNumber(pinToUpdate);
        entityManager.persist(customerToUpdate);

        return customerToUpdate;
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
