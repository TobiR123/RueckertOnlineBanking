package RueckertOnlineBanking.entity.customExceptions;

public class emailAddressAlreadyInUseException extends Exception {
    private String mailAddress = "";

    public emailAddressAlreadyInUseException(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    @Override
    public String toString() {
        return "Die Email-Adresse " + this.mailAddress + " wird bereits benutzt. Bitte wähle eine andere:";
    }
}
