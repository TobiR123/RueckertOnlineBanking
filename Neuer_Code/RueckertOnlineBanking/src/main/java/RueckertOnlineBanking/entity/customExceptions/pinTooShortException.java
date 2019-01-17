package RueckertOnlineBanking.entity.customExceptions;

public class pinTooShortException extends Exception {

    private int pin;
    private String message;

    public pinTooShortException(int pin) {
        this.pin = pin;
        this.message = "Deine neue PIN ist zu kurz! Bitte wähle ein PIN mit mindestens 6 Stellen!";
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
