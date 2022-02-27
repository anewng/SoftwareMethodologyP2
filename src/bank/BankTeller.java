package bank;

import java.util.InputMismatchException;
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

                double balanceDouble = 0;
                try{
                    balanceDouble = Double.parseDouble(balance);
                }catch (NumberFormatException e){
                    System.out.println("Not a valid amount.");
                }

                Date birth = new Date(dob);
                Profile newProfile = new Profile(first, last, new Date(dob));

                if(openReturnErrorStatements(accountType, first, last, dob, balanceDouble, codes, birth,
                        bankDatabase, newAccount)){
                    continue;
                }

                if(accountType.equals("C")){
                    newAccount = new Checking(newProfile, false, balanceDouble);
                } else if(accountType.equals("CC")){
                    newAccount = new CollegeChecking(newProfile, false,
                            balanceDouble, Integer.parseInt(result[6]));
                } else if(accountType.equals("S")){
                    newAccount = new Savings(newProfile, false,
                            balanceDouble, Integer.parseInt(result[6]));
                } else if(accountType.equals("MM")){
                    newAccount = new MoneyMarket(newProfile, false, balanceDouble, 1);
                } else {
                    System.out.println("Invalid command!");
                    continue;
                }

                int index = bankDatabase.findClosedAccount(newAccount);
                if(index == -1){
                    bankDatabase.open(newAccount);
                }else{
                    bankDatabase.reopen(newAccount, index);
                }
            } else if (result[0].equals("C")) {
                Account newAccount = new Checking(new Profile(null, null, null), false, 0);
                String accountType = result[1], first = result[2], last = result[3], dob = result[4];
                Profile newProfile = new Profile(first, last, new Date(dob));
                if(accountType.equals("C")){
                    newAccount = new Checking(newProfile, false, 0);
                } else if(accountType.equals("CC")){
                    newAccount = new CollegeChecking(newProfile, false,
                            0, 0);
                } else if(accountType.equals("S")){
                    newAccount = new Savings(newProfile, false,
                            0, 0);
                } else if(accountType.equals("MM")){
                    newAccount = new MoneyMarket(newProfile, false, 0, 1);
                }
                Account closeAcc = bankDatabase.findByProfileType(newAccount);
                bankDatabase.close(closeAcc);
            } else if (result[0].equals("D")) {

            } else if (result[0].equals("W")) {

            } else if (result[0].equals("P")) {
                bankDatabase.print();
            } else if (result[0].equals("PT")) {
                bankDatabase.printByAccountType();
            } else if (result[0].equals("PI")) {
                bankDatabase.printFeeAndInterest();
            } else if (result[0].equals("UB")) {
                bankDatabase.updateBalance();
                bankDatabase.print();
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

    public boolean openReturnErrorStatements(String accountType, String first, String last, String dob, double balance,
                                         String codes, Date birth, AccountDatabase bankDatabase, Account newAccount){
        int campusCode = Integer.parseInt(codes);
        if( first == "" || last == "" || dob == "") {
            System.out.println("Missing data for opening an account.");
        } else if( (accountType.equals("S") || accountType.equals("CC"))
                && (codes == "") ){
            System.out.println("Missing data for opening an account.");
        } else if(!birth.isValid()){
            System.out.println("Date of birth invalid.");
        } else if(balance <= 0){
            System.out.println("Initial deposit cannot be 0 or negative.");
        } else if(accountType.equals("C") && (!(campusCode == 0 || campusCode == 1 || campusCode == 2))){
            System.out.println("Invalid campus code.");
        } else if(accountType.equals("C") && bankDatabase.findCCProfile(newAccount)){
            System.out.println(newAccount.holder.toString() + " same account(type) is in the database.");
        } else if(accountType.equals("CC") && bankDatabase.findCProfile(newAccount)){
            System.out.println(newAccount.holder.toString() + " same account(type) is in the database.");
        } else if(isInDatabase(bankDatabase, newAccount)){
            System.out.println(newAccount.holder.toString() + " same account(type) is in the database.");
        } else if(accountType.equals("MM")){
            if( balance >= 2500 ){
                System.out.println("Minimum of $2500 to open a MoneyMarket account.");
            }
        } else{
            return false;
        }
        return true;
    }
}
