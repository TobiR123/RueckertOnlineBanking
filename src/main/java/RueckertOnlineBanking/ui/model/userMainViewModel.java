package RueckertOnlineBanking.ui.model;

import RueckertOnlineBanking.entity.Address;
import RueckertOnlineBanking.entity.Customer;
import RueckertOnlineBanking.entity.EMailAddress;
import RueckertOnlineBanking.entity.PIN;
import RueckertOnlineBanking.service.CustomerService;

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
public class userMainViewModel implements Serializable {

    public userMainViewModel() {
        this.service = new CustomerService();
    }

    @Inject
    private CustomerService service;

    public void viewUserProfile() {

    }

    public void editUserProfile() {

    }

    public String goToTransactionScreen() {
        return "TransactionView.xhtml";
    }


    // ##### GETTER AND SETTER ##### //




}
