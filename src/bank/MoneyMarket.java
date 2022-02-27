package bank;

import java.text.DecimalFormat;

public class MoneyMarket extends Savings{
    protected int withdrawalCount = 0;

    private static final double MM_INTEREST = .008 / 12;
    private static final double LOYAL_MM_INTEREST = .0095 / 12;
    private static final double MM_FEE = 10;
    private static final String MM_TYPE = "Money Market";

    public MoneyMarket(Profile newHolder, boolean isClosed, double newBalance, int isLoyal) {
        super(newHolder, isClosed, newBalance, isLoyal);
        loyal = 1;
    }

    @Override
    public double monthlyInterest(){
        if (balance < 2500) {
            loyal = 0;
        } else {
            loyal = 1;
        }
        if (loyal == 1){
            return LOYAL_MM_INTEREST * balance;
        }
        return MM_INTEREST * balance;
    }//return the monthly interest

    @Override
    public double fee(){
        if (balance >= 2500 && withdrawalCount < 3) {
            return WAIVED_FEE;
        }
        return MM_FEE;
    } //return the monthly fee

    @Override
    public String getType(){
        return MM_TYPE;
    } //return the account type (class name)

    @Override
    public String toString(){
        DecimalFormat d = new DecimalFormat("'$'#,##0.00");
        String returnString = getType() + "::" + holder.toString() + "::Balance " + d.format(balance);
        if (loyal == 1){
            returnString += "::Loyal";
        } else if (closed) {
            returnString += "::CLOSED";
        }
        returnString += "::withdrawl: " + withdrawalCount;
        return returnString;
    }

}
