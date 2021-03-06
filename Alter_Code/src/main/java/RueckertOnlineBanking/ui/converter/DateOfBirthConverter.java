package RueckertOnlineBanking.ui.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;

@FacesConverter("dateOfBirthConverter")
public class DateOfBirthConverter extends DateTimeConverter {

    public DateOfBirthConverter() {
        setPattern("yyyy-MM-dd");
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value != null && value.length() != getPattern().length()) {
            throw new ConverterException("Invalid format.");
        }

        return super.getAsObject(context, component, value);
    }

}
