package RueckertOnlineBanking.entity.customExceptions;

public class customerTooYoungException extends Exception {

    @Override
    public String toString() {
        return "Du bist leider zu jung. Kunden müssen mindestens 18 Jahre alt sein.";
    }
}
