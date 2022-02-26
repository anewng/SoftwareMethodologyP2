package bank;

public class Savings extends Account{
    protected int loyal;

    private static final double SAVINGS_INTEREST = .003 / 12;
    private static final double LOYAL_SAVINGS_INTEREST = .0045 / 12;
    private static final double SAVINGS_FEE = 6;
    private static final String SAVINGS_TYPE = "Savings";

    public Savings(Profile newHolder, boolean isClosed, double newBalance, int isLoyal){
        super(newHolder, isClosed, newBalance);
        loyal = isLoyal;
    }

    public double monthlyInterest(){
        if(loyal == 1){
            return LOYAL_SAVINGS_INTEREST * balance;
        }
        return SAVINGS_INTEREST * balance;
    }//return the monthly interest

    public double fee(){
        if (balance >= 300) {
            return WAIVED_FEE;
        }
        return SAVINGS_FEE;
    } //return the monthly fee

    public String getType(){
        return SAVINGS_TYPE;
    } //return the account type (class name)

}
