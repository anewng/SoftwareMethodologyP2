package bank;

public class MoneyMarket extends Savings{
    private int withdrawalCount = 0;

    private static final double MM_INTEREST = .008;
    private static final double LOYAL_MM_INTEREST = .0095;
    private static final double MM_FEE = 10;
    private static final String MM_TYPE = "Money Market";

    public MoneyMarket(Profile newHolder, boolean isClosed, double newBalance, int isLoyal) {
        super(newHolder, isClosed, newBalance, isLoyal);
        loyal = 1;
        interest = LOYAL_MM_INTEREST;
        if(balance > 300){
            fee = WAIVED_FEE;
        }else{
            fee = MM_FEE;
        }
        type = MM_TYPE;
    }

    @Override
    public void withdraw(double amount) {
        this.balance -= amount;
        if(balance < 2500){
            loyal = 0;
            interest = MM_INTEREST;
        }
        withdrawalCount++;
    }

    @Override
    public String toString(){
        if(loyal == 1){
            return MM_TYPE + "::" + holder.toString() + "::Balance " + balance + "::Loyal";
        }
        return MM_TYPE + "::" + holder.toString() + "::Balance " + balance;

    }
}
