package RueckertOnlineBanking.entity.customExceptions;

public class customerTooYoungException extends Exception {

    private String message;

    public customerTooYoungException() {
        this.message = "Du bist leider zu jung. Kunden müssen mindestens 18 Jahre alt sein.";
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
