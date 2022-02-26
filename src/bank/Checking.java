package bank;

public class Checking extends Account{
    private static final double CHECKING_INTEREST = .001 / 12;
    private static final double CHECKING_FEE = 25;
    private static final String CHECKING_TYPE = "Checking";

    public Checking(Profile newHolder, boolean isClosed, double newBalance){
        super(newHolder, isClosed, newBalance);
    }

    public double monthlyInterest(){
        return CHECKING_INTEREST * balance;
    }//return the monthly interest

    public double fee(){
        if (balance >= 1000) {
            return WAIVED_FEE;
        }
        return CHECKING_FEE;
    } //return the monthly fee

    public String getType(){
        return CHECKING_TYPE;
    } //return the account type (class name)

}
