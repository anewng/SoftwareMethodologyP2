package bank;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class BankTeller {

    /**
     Runs the BankTeller.
     */
    public void run() {
        System.out.println("Bank Teller is running.\n");
        Scanner s = new Scanner(System.in);
        AccountDatabase bankDatabase = new AccountDatabase();
        while (s.hasNext()) {
            String inputLine = s.nextLine();
            String[] result = inputLine.split("\\s+");
            if(inputLine == ""){
                continue;
            }

            if (result[0].equals("Q")) {
                System.out.println("Bank Teller is terminated.");
                break;
            } else if (result[0].equals("O")) {
                Account newAccount = new Checking(new Profile(null, null, null),
                        false, 0);
                String accountType = "", first = "", last = "", dob = "", balance = "";
                int codes= -1;

                try{
                    accountType = result[1];
                    first = result[2];
                    last = result[3];
                    dob = result[4];
                    balance = result[5];
                    if( accountType.equals("S") || accountType.equals("CC") ){
                        codes = Integer.parseInt(result[6]);
                    }
                }catch(ArrayIndexOutOfBoundsException e){
                    System.out.println("Missing data for opening an account.");
                    continue;
                }

                double balanceDouble = 0;
                try{
                    balanceDouble = Double.parseDouble(balance);
                }catch (NumberFormatException e){
                    System.out.println("Not a valid amount.");
                    continue;
                }

                Date birth = new Date(dob);
                Profile newProfile = new Profile(first, last, birth);

                if(accountType.equals("C")){
                    newAccount = new Checking(newProfile, false, balanceDouble);
                } else if (accountType.equals("CC")) {
                    newAccount = new CollegeChecking(newProfile, false,
                            balanceDouble, codes);
                } else if (accountType.equals("S")) {
                    newAccount = new Savings(newProfile, false,
                            balanceDouble, codes);
                } else if (accountType.equals("MM")) {
                    newAccount = new MoneyMarket(newProfile, false, balanceDouble, 1);
                }

                if(openReturnErrorStatements(newAccount, bankDatabase)){
                    continue;
                }

                int index = bankDatabase.findClosedAccount(newAccount);
                if(index == -1){
                    bankDatabase.open(newAccount);
                    System.out.println("Account opened.");
                }else{
                    bankDatabase.reopen(newAccount, index);
                    System.out.println("Account reopened.");
                }
            } else if (result[0].equals("C")) {
                Account newAccount = new Checking(new Profile(null, null, null),
                        false, 0);
                String accountType = "", first = "", last = "", dob = "";

                try{
                    accountType = result[1];
                    first = result[2];
                    last = result[3];
                    dob = result[4];
                }catch(ArrayIndexOutOfBoundsException e){
                    System.out.println("Missing data for opening an account.");
                    continue;
                }

                Date birth = new Date(dob);
                Profile newProfile = new Profile(first, last, birth);

                if(accountType.equals("C")){
                    newAccount = new Checking(newProfile, false, 0);
                } else if(accountType.equals("CC")){
                    newAccount = new CollegeChecking(newProfile, false,
                            0, 0);
                } else if(accountType.equals("S")){
                    newAccount = new Savings(newProfile, false,
                            0, 0);
                } else if(accountType.equals("MM")){
                    newAccount = new MoneyMarket(newProfile, false, 0, 0);
                }

                Account closeAcc = bankDatabase.findByProfileType(newAccount);
                if(closeAcc.closed){
                    System.out.println("Account closed already.");
                } else {
                    bankDatabase.close(closeAcc);
                    System.out.println("Account closed.");
                }
            } else if (result[0].equals("D")) {
                Account newAccount = new Checking(new Profile(null, null, null),
                        false, 0);
                String accountType = result[1], first = result[2], last = result[3], dob = result[4],
                        balance = result[5];

                double balanceDouble = 0;
                try{
                    balanceDouble = Double.parseDouble(balance);
                }catch (NumberFormatException e){
                    System.out.println("Not a valid amount.");
                    continue;
                }

                Date birth = new Date(dob);
                Profile newProfile = new Profile(first, last, birth);

                if(balanceDouble <= 0){
                    System.out.println("Deposit - amount cannot be 0 or negative.");
                    continue;
                }

                if(accountType.equals("C")){
                    newAccount = new Checking(newProfile, false, balanceDouble);
                } else if(accountType.equals("CC")){
                    newAccount = new CollegeChecking(newProfile, false,
                            balanceDouble, 0);
                } else if(accountType.equals("S")){
                    newAccount = new Savings(newProfile, false,
                            balanceDouble, 0);
                } else if(accountType.equals("MM")){
                    newAccount = new MoneyMarket(newProfile, false, balanceDouble, 1);
                }

                Account depositAccount = bankDatabase.findByProfileType(newAccount);
                if(depositAccount == null){
                    System.out.println(newAccount.holder.toString() + " " + newAccount.getType()
                            + " is not in the database.");
                } else {
                    depositAccount.deposit(newAccount.balance);
                    bankDatabase.deposit(depositAccount);
                    System.out.println("Deposit - balance updated.");
                }
            } else if (result[0].equals("W")) {
                Account newAccount = new Checking(new Profile(null, null, null),
                        false, 0);
                String accountType = result[1], first = result[2], last = result[3], dob = result[4],
                        balance = result[5];

                double balanceDouble = 0;
                try{
                    balanceDouble = Double.parseDouble(balance);
                }catch (NumberFormatException e){
                    System.out.println("Not a valid amount.");
                    continue;
                }
                if(balanceDouble <= 0){
                    System.out.println("Withdraw - amount cannot be 0 or negative.");
                    continue;
                }

                Date birth = new Date(dob);
                Profile newProfile = new Profile(first, last, birth);

                if(accountType.equals("C")){
                    newAccount = new Checking(newProfile, false, balanceDouble);
                } else if(accountType.equals("CC")){
                    newAccount = new CollegeChecking(newProfile, false,
                            balanceDouble, 0);
                } else if(accountType.equals("S")){
                    newAccount = new Savings(newProfile, false,
                            balanceDouble, 0);
                } else if(accountType.equals("MM")){
                    newAccount = new MoneyMarket(newProfile, false, balanceDouble, 1);
                }

                Account withdrawAccount = bankDatabase.findByProfileType(newAccount);
                if(withdrawAccount == null){
                    System.out.println(newAccount.holder.toString() + " " + newAccount.getType()
                            + " is not in the database.");
                } else {
                    if(newAccount.balance > withdrawAccount.balance){
                        System.out.println("Withdraw - insufficient fund.");
                    }else{
                        withdrawAccount.withdraw(newAccount.balance);
                        bankDatabase.withdraw(withdrawAccount);
                        System.out.println("Withdraw - balance updated.");
                    }
                }
            } else if (result[0].equals("P")) {
                if(bankDatabase.getNumAcct() == 0){
                    System.out.println("Account Database is empty!");
                }else{
                    System.out.println("\n*list of accounts in the database*");
                    bankDatabase.print();
                    System.out.println("*end of list*\n");
                }
            } else if (result[0].equals("PT")) {
                if(bankDatabase.getNumAcct() == 0){
                    System.out.println("Account Database is empty!");
                }else{
                    System.out.println("\n*list of accounts by account type.");
                    bankDatabase.printByAccountType();
                    System.out.println("*end of list.\n");
                }
            } else if (result[0].equals("PI")) {
                if(bankDatabase.getNumAcct() == 0){
                    System.out.println("Account Database is empty!");
                }else{
                    System.out.println("\n*list of accounts with fee and monthly interest");
                    bankDatabase.printFeeAndInterest();
                    System.out.println("*end of list.\n");
                }
            } else if (result[0].equals("UB")) {
                bankDatabase.updateBalance();
                if(bankDatabase.getNumAcct() == 0){
                    System.out.println("Account Database is empty!");
                }else{
                    System.out.println("\n*list of accounts with updated balance");
                    bankDatabase.print();
                    System.out.println("*end of list.\n");
                }
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    public boolean openReturnErrorStatements(Account newAccount, AccountDatabase bankDatabase){
        int campusCode = -1;
        if(newAccount.getType().equals("College Checking")){
            campusCode = ((CollegeChecking) newAccount).collegeCode;
        }
        if(!newAccount.holder.getDob().isValid()){
            System.out.println("Date of birth invalid.");
        } else if(newAccount.balance <= 0){
            System.out.println("Initial deposit cannot be 0 or negative.");
        } else if(newAccount.getType().equals("College Checking")
                && !( campusCode == 0 || campusCode == 1 || campusCode == 2) ){
            System.out.println("Invalid campus code.");
        } else if(newAccount.getType().equals("Checking") && bankDatabase.findCCProfile(newAccount)){
            System.out.println(newAccount.holder.toString() + " same account(type) is in the database.");
        } else if(newAccount.getType().equals("College Checking") && bankDatabase.findCProfile(newAccount)){
            System.out.println(newAccount.holder.toString() + " same account(type) is in the database.");
        } else if(bankDatabase.isInDatabase(newAccount)){
            System.out.println(newAccount.holder.toString() + " same account(type) is in the database.");
        } else if(newAccount.getType().equals("Money Market") && newAccount.balance < 2500){
            System.out.println("Minimum of $2500 to open a MoneyMarket account.");
        } else{
            return false;
        }
        return true;
    }


}
