package RueckertOnlineBanking.entity.customExceptions;

public class senderNotEnoughMoneyException extends Exception {

    // Variable that represents the credit of an account.
    private double credit = 0.0;

    public senderNotEnoughMoneyException(double credit) {
        this.credit = credit;
    }

    public double getCredit() {
        return this.credit;
    }

    @Override
    public String toString() {
        return "Du hast leider nicht genügend Guthaben auf dem ausgewählten Konto.";
    }

}
