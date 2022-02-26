package bank;

public class Checking extends Account{
    private static final double CHECKING_INTEREST = .001;
    private static final double CHECKING_FEE = 25;
    private static final String CHECKING_TYPE = "Checking";

    public Checking(Profile newHolder, boolean isClosed, double newBalance){
        super(newHolder, isClosed, newBalance);
        if(balance > 1000){
            fee = WAIVED_FEE;
        }else{
            fee = CHECKING_FEE;
        }
        interest = CHECKING_INTEREST;
        type = CHECKING_TYPE;
    }

    public double monthlyInterest(){
        return CHECKING_INTEREST;
    }//return the monthly interest

    public double fee(){
        return CHECKING_FEE;
    } //return the monthly fee

    public String getType(){
        return CHECKING_TYPE;
    } //return the account type (class name)

    @Override
    public String toString(){
        return CHECKING_TYPE + "::" + holder.toString() + "::Balance " + balance;
    }
}
