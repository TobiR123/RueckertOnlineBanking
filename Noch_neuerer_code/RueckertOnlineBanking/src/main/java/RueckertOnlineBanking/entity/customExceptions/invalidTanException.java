package RueckertOnlineBanking.entity.customExceptions;

public class invalidTanException extends Exception {

    private String message;

    public invalidTanException() {
        this.message = "Du hast eine ungültige TAN-Nummer eingegeben! (Vielleicht hast du diese schon einmal benutzt?)";
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
