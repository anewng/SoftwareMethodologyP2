package bank;

import java.text.DecimalFormat;

public class CollegeChecking extends Checking{
    protected int collegeCode;

    private static final double C_CHECKING_INTEREST = .0025 / 12;
    private static final double C_CHECKING_FEE = 0;
    private static final String C_CHECKING_TYPE = "College Checking";
    private static final int NEW_BRUNSWICK = 0;
    private static final int NEWARK = 1;
    private static final int CAMDEN = 2;

    public CollegeChecking(Profile newHolder, boolean isClosed, double newBalance, int newCollegeCode){
        super(newHolder, isClosed, newBalance);
        collegeCode = newCollegeCode;
    }
    @Override
    public double monthlyInterest(){
        return C_CHECKING_INTEREST * balance;
    }//return the monthly interest

    @Override
    public double fee(){
        return C_CHECKING_FEE;
    } //return the monthly fee

    @Override
    public String getType(){
        return C_CHECKING_TYPE;
    } //return the account type (class name)

    @Override
    public String toString(){
        String campus = "";
        String returnString = "";
        DecimalFormat d = new DecimalFormat("'$'0.00");
        if(collegeCode == NEW_BRUNSWICK){
            campus = "NEW_BRUNSWICK";
        }else if(collegeCode == NEWARK){
            campus = "NEWARK";
        }else if(collegeCode == CAMDEN){
            campus = "CAMDEN";
        }
        returnString += getType() + "::" + holder.toString() + "::Balance " + d.format(balance);
        if (closed) {
            returnString += "::CLOSED";
        }
        returnString += "::" + campus;
        return returnString;
    }
}
