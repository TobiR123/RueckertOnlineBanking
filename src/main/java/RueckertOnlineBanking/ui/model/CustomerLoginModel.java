package RueckertOnlineBanking.ui.model;

import RueckertOnlineBanking.entity.*;
import RueckertOnlineBanking.service.CustomerService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;

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
public class CustomerLoginModel implements Serializable {

    private EMailAddress loginEmailAddress;
    private PIN loginPin;

    @Inject
    private CustomerService service;


    public CustomerLoginModel() {
        this.loginEmailAddress = new EMailAddress();
        this.loginPin = new PIN();

        this.service = new CustomerService();
    }

    public EMailAddress getLoginEmailAddress() {
        return loginEmailAddress;
    }

    public void setLoginEmailAddress(EMailAddress loginEmailAddress) {
        this.loginEmailAddress = loginEmailAddress;
    }

    public PIN getLoginPin() {
        return loginPin;
    }

    public void setLoginPin(PIN loginPin) {
        this.loginPin = loginPin;
    }


    public String loginCustomer() {
        boolean success = service.loginCustomer(this.loginEmailAddress, this.loginPin);
        if(success == true){
            System.out.println("Sie haben sich erfolgreich angemeldet!");
        } else {
            System.out.println("Bei der Anmeldung lief etwas schief ... ");
        }

        return "afterRegistrationViewWithTemplate.xhtml";
    }


}
