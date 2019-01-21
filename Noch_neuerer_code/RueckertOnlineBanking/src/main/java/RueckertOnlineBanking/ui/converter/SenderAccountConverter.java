package RueckertOnlineBanking.ui.converter;

import RueckertOnlineBanking.entity.Account;
import RueckertOnlineBanking.service.AccountService;

import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import java.io.Serializable;

@SessionScoped
public class SenderAccountConverter implements Converter, Serializable {

    @Inject
    private AccountService accountService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) {
            return "";
        }

        long parsedValue = Long.parseLong(value);
        return accountService.getAccountById(parsedValue);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        }

        if (!value.getClass().equals(Account.class)) {
            return null;
        }

        return String.valueOf(((Account) value).getId());
    }
}
