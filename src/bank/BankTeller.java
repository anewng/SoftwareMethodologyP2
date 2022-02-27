package bank;

import java.util.Scanner;
import java.util.StringTokenizer;

public class BankTeller {

    /**
     Runs the BankTeller.
     */
    public void run() {
        Scanner s = new Scanner(System.in);
        System.out.println("Bank Teller is running.\n");
        AccountDatabase bankDatabase = new AccountDatabase();
        while (s.hasNext()) {
            String inputLine = s.nextLine();
            String[] result = inputLine.split(" ");

            if (result[0].equals("Q")) {
                System.out.println("Bank Teller is terminated.");
                break;
            } else if (result[0].equals("O")) {
                Account newAccount = new Checking(new Profile(null, null, null), false, 0);
                String accountType = result[1], first = result[2], last = result[3], dob = result[4],
                        balance = result[5], codes = result[6];
                Date birth = new Date(dob);

                Profile newProfile = new Profile(first, last, new Date(dob));

                if(returnErrorStatements(accountType, first, last, dob, balance, codes, birth,
                        bankDatabase, newAccount) == false){
                    continue;
                }

                if(accountType.equals("C")){
                    newAccount = new Checking(newProfile, false, Double.parseDouble(result[5]));
                } else if(accountType.equals("CC")){
                    newAccount = new CollegeChecking(newProfile, false,
                            Double.parseDouble(result[5]), Integer.parseInt(result[6]));
                } else if(accountType.equals("S")){
                    newAccount = new Savings(newProfile, false,
                            Double.parseDouble(result[5]), Integer.parseInt(result[6]));
                } else if(accountType.equals("MM")){
                    newAccount = new MoneyMarket(newProfile, false, Double.parseDouble(result[5]), 1);
                } else {
                    System.out.println("Invalid command!");
                }
            } else if (result[0].equals("C")) {

            } else if (result[0].equals("D")) {

            } else if (result[0].equals("W")) {

            } else if (result[0].equals("P")) {

            } else if (result[0].equals("PT")) {

            } else if (result[0].equals("UB")) {

            } else {
                System.out.println("Invalid command!");
            }

        }
    }

    public boolean isInDatabase(AccountDatabase database, Account account){
        for(int i = 0; i < database.getNumAcct(); i++){
            if(database.getDatabase()[i].equals(account)){
                return true;
            }
        }
        return false;
    }

    public boolean returnErrorStatements(String accountType, String first, String last, String dob, String balance,
                                         String codes, Date birth, AccountDatabase bankDatabase, Account newAccount){
        if( first == null || last == null || dob == null) {
            System.out.println("Missing data for opening an account.");
            return false;
        } else if( (accountType.equals("S") || accountType.equals("CC"))
                && (codes == null) ){
            System.out.println("Missing data for opening an account.");
            return false;
        } else if(!birth.isValid()){
            System.out.println("Date of birth invalid.");
            return false;
        } else if(Double.parseDouble(balance) <= 0){
            System.out.println("Initial deposit cannot be 0 or negative.");
            return false;
        } else if(accountType.equals("C")){
            int campusCode = Integer.parseInt(codes);
            if( !(campusCode == 0 || campusCode == 1 || campusCode == 2) ){
                System.out.println("Invalid campus code.");
            }
            return false;
        } else if(accountType.equals("MM")){
            if( !(Double.parseDouble(balance) >= 2500) ){
                System.out.println("Minimum of $2500 to open a MoneyMarket account.");
            }
            return false;
        } else if(isInDatabase(bankDatabase, newAccount)){
            System.out.println("Account opened.");
            return false;
        }
        return true;
    }
}
