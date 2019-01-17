package RueckertOnlineBanking.entity.customExceptions;

public class emailAddressAlreadyInUseException extends Exception {

    private String mailAddress = "";
    private String message;

    public emailAddressAlreadyInUseException(String mailAddress) {
        this.mailAddress = mailAddress;
        this.message = "Die Email-Adresse " + this.mailAddress + " wird bereits benutzt. Bitte wähle eine andere:";
    }

    @Override
    public String toString() {
        return this.message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
