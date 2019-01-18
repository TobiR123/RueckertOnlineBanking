package RueckertOnlineBanking.service;

import RueckertOnlineBanking.entity.*;
import RueckertOnlineBanking.entity.customExceptions.customerTooYoungException;
import RueckertOnlineBanking.entity.customExceptions.emailAddressAlreadyInUseException;
import RueckertOnlineBanking.entity.customExceptions.pinTooShortException;
import RueckertOnlineBanking.loggerFactory.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebService
@RequestScoped
public class CustomerService implements Serializable {

    @PersistenceContext(unitName = "RueckertPU")
    //@PersistenceContext(unitName = "examplePU")
    private EntityManager entityManager;

    @Inject
    private LoggerFactory loggerFactory;
    private Logger logger;

    @WebMethod(exclude = true)
    @PostConstruct
    public void init() {
        logger = loggerFactory.create();
    }

    @Inject
    private AccountService accountService;

    @WebMethod(exclude = false)
    @Transactional(Transactional.TxType.REQUIRED)
    public Customer registerCustomer(Customer customer) throws emailAddressAlreadyInUseException, ParseException, customerTooYoungException {


        if(customer.geteMailAddress() != null) {
            // check if email address already exists.
            if(this.checkIfEmailAddressAlreadyExists(customer.geteMailAddress())){
                throw new emailAddressAlreadyInUseException(customer.geteMailAddress().getMailAddress());
            }
        }

        if(!this.checkIfCustomerIsAdult(customer.getDateOfBirth())){
            throw new customerTooYoungException();
        }

        Account account = this.accountService.createAccount();

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
        if(customer.geteMailAddress() != null) {
            entityManager.persist(customer.geteMailAddress());

            entityManager.persist(customer);

            EMailAddress systemEmailAddress = this.getSystemEmailAddressRecord();
            String topic = "Ihre TAN-Nummern zum bestätigen Ihrer Transaktionen";
            String message = customer.getTanNumbersAsString();
            Email email = new Email(systemEmailAddress, customer.geteMailAddress(), topic, message);
            entityManager.persist(email);
            System.out.println("SENT EMAIL: " + email);
        }

        this.logger.log(Level.INFO, "Customer successfull registered.");
        return customer;
    }

    @WebMethod(exclude = true)
    @Transactional(Transactional.TxType.REQUIRED)
    private boolean checkIfCustomerIsAdult(Date dateOfBirth) throws ParseException {
        long diff = new Date().getTime() - dateOfBirth.getTime();
        int diffInDays = (int)(diff / (24 * 60 * 60 * 1000));

        if(diffInDays / 365 >= 18){
            return true;
        }
        return false;
    }

    @WebMethod(exclude = true)
    @Transactional(Transactional.TxType.REQUIRED)
    private boolean checkIfEmailAddressAlreadyExists(EMailAddress eMailAddress) {

        TypedQuery<EMailAddress> emailAddressQuery = entityManager.createQuery(
                "SELECT e FROM EMailAddress AS e WHERE e.mailAddress = :emailToCheck",
                EMailAddress.class
        );
        emailAddressQuery.setParameter("emailToCheck", eMailAddress.getMailAddress());
        List<EMailAddress> result = emailAddressQuery.getResultList();

        if(result.size() > 0){
            return true;
        }
        return false;
    }

    @WebMethod(exclude = true)
    @Transactional(Transactional.TxType.REQUIRED)
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

    @WebMethod(exclude = true)
    @Transactional(Transactional.TxType.REQUIRED)
    public Customer loginCustomer(EMailAddress eMailAddress, PIN pin){

        // ##### Get the email record with the given address from the database. ##### //
        TypedQuery<EMailAddress> emailAddressQuery = entityManager.createQuery(
                "SELECT e FROM EMailAddress  AS e WHERE e.mailAddress = :emailAddress",
                EMailAddress.class
        );
        emailAddressQuery.setParameter("emailAddress", eMailAddress.getMailAddress());
        List<EMailAddress> eMailAddresses= emailAddressQuery.getResultList();
        if(eMailAddresses.size() == 0){
            return null;
        }
        EMailAddress eMailAddressRecord = eMailAddresses.get(0);


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
            return null;
        }
        this.logger.log(Level.INFO, "Customer successfull logged in.");
        return customer.get(0);
    }

    @WebMethod(exclude = true)
    @Transactional(Transactional.TxType.REQUIRED)
    public Customer updateCustomer(Customer customer) throws emailAddressAlreadyInUseException, pinTooShortException {
        Customer original = this.getCustomerById(customer.getId());

        // Check if e-mail address is the same as before. If not, check if the new one is available.
        if(!original.geteMailAddress().getMailAddress().equals(customer.geteMailAddress().getMailAddress())){
            if(this.checkIfEmailAddressAlreadyExists(customer.geteMailAddress())){
                throw new emailAddressAlreadyInUseException(customer.geteMailAddress().getMailAddress());
            }
        }

        int newPin = customer.getPinNumber().getPinNumber();
        int length = String.valueOf(newPin).length();
        if(length < 6){
            throw new pinTooShortException(newPin);
        }

        original.setFirstname(customer.getFirstname());
        original.setLastname(customer.getLastname());

        EMailAddress originalEmailAddress = this.getEMailAddressById(customer.geteMailAddress().getId());
        originalEmailAddress.setMailAddress(customer.geteMailAddress().getMailAddress());
        entityManager.persist(originalEmailAddress);
        original.seteMailAddress(originalEmailAddress);

        original.setPhoneNumber(customer.getPhoneNumber());
        original.setDateOfBirth(customer.getDateOfBirth());

        Address originalAddress = this.getAddressById(customer.getAddress().getId());
        originalAddress.setStreet(customer.getAddress().getStreet());
        originalAddress.setHouseNumber(customer.getAddress().getHouseNumber());
        originalAddress.setPostcode(customer.getAddress().getPostcode());
        originalAddress.setPlace(customer.getAddress().getPlace());
        entityManager.persist(originalAddress);
        original.setAddress(originalAddress);

        PIN originalPin = this.getPinById(customer.getPinNumber().getId());
        originalPin.setPinNumber(customer.getPinNumber().getPinNumber());
        entityManager.persist(originalPin);
        original.setPinNumber(originalPin);

        entityManager.persist(original);

        //this.logger.log(Level.INFO, "Customer successfull updated.");
        return original;
    }

    @WebMethod(exclude = true)
    @Transactional(Transactional.TxType.REQUIRED)
    public Customer addAccountToCustomer(Customer customer) {
        Account newAccount = this.accountService.createAccount();
        Customer original = this.getCustomerById(customer.getId());
        original.addAccount(newAccount);
        entityManager.persist(original);
        return original;
    }

    @WebMethod(exclude = true)
    @Transactional(Transactional.TxType.REQUIRED)
    public void deleteCustomer(Customer customer) {
        List<Email> listOfEmailsToCustomer = this.getAllEmailsWhereCustomerIsReceiver(customer.geteMailAddress());
        for (Email e :
                listOfEmailsToCustomer) {
            entityManager.remove(e);
        }

        // NOTE: Address must not be deleted via persistence manager, because it is marked as orphanRemoval = true in Customer entity!
        // Because no other reference on the address record exists, it will be deleted automatically.
        entityManager.remove(this.getEMailAddressById(customer.geteMailAddress().getId()));
        entityManager.remove(this.getPinById(customer.getPinNumber().getId()));
        entityManager.remove(this.getCustomerById(customer.getId()));
        this.logger.log(Level.INFO, "Customer successfull deleted.");
    }

    @WebMethod(exclude = true)
    public Customer getCustomerById(long id){
        return entityManager.find(Customer.class, id);
    }

    @WebMethod(exclude = true)
    private EMailAddress getEMailAddressById(long id) {
        return entityManager.find(EMailAddress.class, id);
    }

    @WebMethod(exclude = true)
    private Address getAddressById(long id){
        return entityManager.find(Address.class, id);
    }

    @WebMethod(exclude = true)
    private PIN getPinById(long id){
        return entityManager.find(PIN.class, id);
    }

    @WebMethod(exclude = true)
    public TAN getTanById(long id){
        return entityManager.find(TAN.class, id);
    }

    @WebMethod(exclude = true)
    private List<Email> getAllEmailsWhereCustomerIsReceiver(EMailAddress eMailAddress) {
        TypedQuery<Email> emailQuery = entityManager.createQuery(
                "SELECT  e FROM  Email  AS e WHERE e.to = :receiver",
                Email.class
        );
        emailQuery.setParameter("receiver", eMailAddress);
        return emailQuery.getResultList();
    }

    @WebMethod(exclude = true)
    public TAN getTanRecordOfCustomerByTanNumber(Customer customer, TAN tanNumber) {
        // Search for the tan record in the customers tan list by a given number.
        List<TAN> customerTans = customer.getTanNumbers();
        for (TAN tan : customerTans) {
            if(tan.getTanNumber() == tanNumber.getTanNumber()){
                return tan;
            }
        }
        return null;
    }

    @WebMethod(exclude = true)
    public Customer getCustomerByAccount(Account account) {
        Query customerQuery1 = entityManager.createQuery(
                "SELECT DISTINCT c FROM Customer c, IN (c.accounts) AS a WHERE a.iban = :iban");
        customerQuery1.setParameter("iban", account.getIban());

//        Query customerQuery = entityManager.createQuery(
//                "SELECT c FROM Customer c JOIN c.accounts a JOIN a.id i WHERE a.iban = :iban");
//        customerQuery.setParameter("iban", account.getIban());

        List result = (customerQuery1.getResultList());
        if((result).size() > 0){
            return (Customer)customerQuery1.getResultList().get(0);
        } else {
            return null;
        }
    }


}
