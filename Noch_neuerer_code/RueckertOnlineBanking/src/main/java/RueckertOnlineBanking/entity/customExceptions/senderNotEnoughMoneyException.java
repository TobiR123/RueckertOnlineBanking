package RueckertOnlineBanking.entity.customExceptions;

public class senderNotEnoughMoneyException extends Exception {

    private String message;
    private double amount;

    public senderNotEnoughMoneyException(double amount) {
        this.message = "Du hast leider nicht genügend Guthaben auf dem ausgewählten Konto.";
        this.amount = amount;
    }

    @Override
    public String toString() {
        return this.message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public double getCredit() {
        return this.amount;
    }

}
