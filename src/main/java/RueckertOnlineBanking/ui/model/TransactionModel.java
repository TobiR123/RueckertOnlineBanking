package RueckertOnlineBanking.ui.model;

import RueckertOnlineBanking.entity.*;
import RueckertOnlineBanking.service.CustomerService;
import RueckertOnlineBanking.service.TransactionService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.text.ParseException;

// Die meisten Modelle sind SessionScoped: Für die ganze Sitzung, vom login bis zum bestellen.
// RequestScoped:
// ApplicationScoped: Derzeit sind soundsoviele User in meinem System online. Singleton, immer das selbe, einfach counter erhöhen.


// Models sind dumme klassen! Nur getter und setter! Wenige action Methoden! keine DB zugriffe!



// BEI SESSION SCOPED: COUNTER WIRD HOCHGEZÄHLT! WEIL GANZE SITZUNG!
// ABER: BEI NEUEM FESTNER; ALSO NEUER SITZUNG: GEHT WIEDER BEI 0 los!
// BEI REQUEST SCOPED: COUNTER WIRD IMMER 0 BLEIBEN! JEDER REQUEST EINE NEUE BEAN!
// APPLICATION SCOPED: COUNTER WIRD IMMER HOCHGEZÄHLT; AUCH ÜBER VERSCHIEDENE SITZUNGEN HINWEG!

@Named
@SessionScoped
public class TransactionModel implements Serializable {

    public TransactionModel() {
        this.customerService = new CustomerService();
        this.transactionService = new TransactionService();
    }





    @Inject
    private CustomerService customerService;
    @Inject
    private TransactionService transactionService;

    // Transaction attributes.
    private String receiverIban;
    private String receiverBic;
    private double amount;
    private String description;
    private int tanNumber;

    // ##### GETTER AND SETTER ##### //
    public String getReceiverIban() {
        return receiverIban;
    }

    public void setReceiverIban(String receiverIban) {
        this.receiverIban = receiverIban;
    }

    public String getReceiverBic() {
        return receiverBic;
    }

    public void setReceiverBic(String receiverBic) {
        this.receiverBic = receiverBic;
    }
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTanNumber() {
        return tanNumber;
    }

    public void setTanNumber(int tanNumber) {
        this.tanNumber = tanNumber;
    }

    public void executeTransaction() {

    }

}
