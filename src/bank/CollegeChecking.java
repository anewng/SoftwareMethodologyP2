package bank;

public class CollegeChecking extends Checking{
    private int collegeCode;

    private static final double C_CHECKING_INTEREST = .0025;
    private static final double C_CHECKING_FEE = 0;
    private static final String C_CHECKING_TYPE = "College Checking";
    private static final int NEW_BRUNSWICK = 0;
    private static final int NEWARK = 1;
    private static final int CAMDEN = 2;

    public CollegeChecking(Profile newHolder, boolean isClosed, double newBalance, int newCollegeCode){
        super(newHolder, isClosed, newBalance);
    }

    @Override
    public String toString(){
        String campus = "";
        if(collegeCode == NEW_BRUNSWICK){
            campus = "NEW_BRUNSWICK";
        }else if(collegeCode == NEWARK){
            campus = "NEWARK";
        }else if(collegeCode == CAMDEN){
            campus = "CAMDEN";
        }
        return C_CHECKING_TYPE + "::" + holder.toString() + "::Balance " + balance + "::" + campus;
    }
}
