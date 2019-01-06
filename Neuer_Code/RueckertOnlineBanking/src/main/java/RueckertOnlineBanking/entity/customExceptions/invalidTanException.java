package RueckertOnlineBanking.entity.customExceptions;

public class invalidTanException extends Exception {

    @Override
    public String toString() {
        return "Du hast eine ungültige TAN-Nummer eingegeben! (Vielleicht hast du diese schon einmal benutzt?)";
    }

}
