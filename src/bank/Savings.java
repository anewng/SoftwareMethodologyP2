package bank;

public class Savings extends Account{
    protected int loyal;

    private static final double SAVINGS_INTEREST = .003;
    private static final double LOYAL_SAVINGS_INTEREST = .0045;
    private static final double SAVINGS_FEE = 6;
    private static final String SAVINGS_TYPE = "Savings";

    public Savings(Profile newHolder, boolean isClosed, double newBalance, int isLoyal){
        super(newHolder, isClosed, newBalance);
        loyal = isLoyal;
    }

    public double monthlyInterest(){
        if(loyal == 1){
            return LOYAL_SAVINGS_INTEREST;
        }
        return SAVINGS_INTEREST;
    }//return the monthly interest

    public double fee(){
        return SAVINGS_FEE;
    } //return the monthly fee

    public String getType(){
        return SAVINGS_TYPE;
    } //return the account type (class name)

    @Override
    public String toString(){
        return SAVINGS_TYPE + "::" + holder.toString() + "::Balance " + balance;
    }
}
