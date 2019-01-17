package RueckertOnlineBanking.ui.model;

import RueckertOnlineBanking.entity.Account;
import RueckertOnlineBanking.service.AccountService;
import RueckertOnlineBanking.service.CustomerService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class AccountModel implements Serializable {

    // ##### PROPERTIES ##### //
    @Inject
    private AccountService accountService;

    // ##### GETTER AND SETTER ##### //

    // ##### METHDOS ##### //
    public String createAccount() {
        this.accountService.createAccount();
        return "customerOverview.xhtml";
    }

}
