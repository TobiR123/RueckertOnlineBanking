package RueckertOnlineBanking.entity.customExceptions;

public class emailAddressAlreadyInUseException extends Exception {

    private String message;

    public emailAddressAlreadyInUseException(String mailAddress) {
        this.message = "Die Email-Adresse " + mailAddress + " wird bereits benutzt. Bitte wähle eine andere:";
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
