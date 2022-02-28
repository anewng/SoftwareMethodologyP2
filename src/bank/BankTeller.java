package bank;

import java.util.Scanner;

/**
 The Bankteller class processes commands from the user to manipulate the database.
 It allows the user to open, close, and display accounts.
 Accounts can also be sorted by type or have their balance updated by interest/fees.
 @author Annie Wang, Jasmine Flanders
 */
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
                openAccount(result, bankDatabase);
            } else if (result[0].equals("C")) {
                closeAccount(result, bankDatabase);
            } else if (result[0].equals("D")) {
                depositBalance(result, bankDatabase);
            } else if (result[0].equals("W")) {
                withdrawBalance(result, bankDatabase);
            } else if (result[0].equals("P")) {
                printAccounts(bankDatabase);
            } else if (result[0].equals("PT")) {
                printAccountsByType(bankDatabase);
            } else if (result[0].equals("PI")) {
                printAccountsByFeesInterest(bankDatabase);
            } else if (result[0].equals("UB")) {
                updateAndPrint(bankDatabase);
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    /**
     Returns relevant error statements for an invalid open command.
     @param newAccount the account being referenced by the open command.
     @param bankDatabase the database containing the accounts.
     @return true if there is an error, else return false.
     */
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

    /**
     Opens an account by adding it to the database.
     @param result a string array containing information from a user open command.
     @param bankDatabase the database to which an account is being added.
     */
    public void openAccount(String[] result, AccountDatabase bankDatabase){
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
            return;
        }

        double balanceDouble = 0;
        try{
            balanceDouble = Double.parseDouble(balance);
        }catch (NumberFormatException e){
            System.out.println("Not a valid amount.");
            return;
        }

        Date birth = new Date(dob);
        Profile newProfile = new Profile(first, last, birth);
        newAccount = createNewAccount(birth, newProfile, balanceDouble, codes, accountType);

        if(openReturnErrorStatements(newAccount, bankDatabase)){
            return;
        }
        openAccountLastStep(bankDatabase, newAccount);
    }

    /**
     Closes an account in a database.
     @param result a string array containing information from a user close command.
     @param bankDatabase the database from which an account is being closed.
     */
    public void closeAccount(String[] result, AccountDatabase bankDatabase){
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
            return;
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
    }

    /**
     Deposits money into an account in the database.
     @param result a string array containing information from a user deposit command.
     @param bankDatabase the database to which a deposit is being made.
     */
    public void depositBalance(String[] result, AccountDatabase bankDatabase){
        Account newAccount = new Checking(new Profile(null, null, null),
                false, 0);
        String accountType = result[1], first = result[2], last = result[3], dob = result[4],
                balance = result[5];

        double balanceDouble = 0;
        try{
            balanceDouble = Double.parseDouble(balance);
        }catch (NumberFormatException e){
            System.out.println("Not a valid amount.");
            return;
        }

        Date birth = new Date(dob);
        Profile newProfile = new Profile(first, last, birth);

        if(balanceDouble <= 0){
            System.out.println("Deposit - amount cannot be 0 or negative.");
            return;
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
        depositBalanceLastStep(newAccount, bankDatabase);

    }

    /**
     Withdraws money from an account in the database.
     @param result a string array containing information from a user deposit command.
     @param bankDatabase the database from which a withdrawal is being made.
     */
    public void withdrawBalance(String[] result, AccountDatabase bankDatabase){
        Account newAccount = new Checking(new Profile(null, null, null),
                false, 0);
        String accountType = result[1], first = result[2], last = result[3], dob = result[4],
                balance = result[5];

        double balanceDouble = 0;
        try{
            balanceDouble = Double.parseDouble(balance);
        }catch (NumberFormatException e){
            System.out.println("Not a valid amount.");
            return;
        }
        if(balanceDouble <= 0){
            System.out.println("Withdraw - amount cannot be 0 or negative.");
            return;
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
        withdrawBalanceLastStep(bankDatabase, newAccount);
    }

    /**
     Prints the accounts in the database in their current order.
     @param bankDatabase the database from which accounts are being printed.
     */
    public void printAccounts(AccountDatabase bankDatabase){
        if(bankDatabase.getNumAcct() == 0){
            System.out.println("Account Database is empty!");
        }else{
            System.out.println("\n*list of accounts in the database*");
            bankDatabase.print();
            System.out.println("*end of list*\n");
        }
    }

    /**
     Prints the accounts in the database by order of type.
     @param bankDatabase the database from which accounts are being printed.
     */
    public void printAccountsByType(AccountDatabase bankDatabase){
        if(bankDatabase.getNumAcct() == 0){
            System.out.println("Account Database is empty!");
        }else{
            System.out.println("\n*list of accounts by account type.");
            bankDatabase.printByAccountType();
            System.out.println("*end of list.\n");
        }
    }

    /**
     Prints the accounts in the database in their current order with their calculated fees/interests.
     @param bankDatabase the database from which accounts are being printed.
     */
    public void printAccountsByFeesInterest(AccountDatabase bankDatabase){
        if(bankDatabase.getNumAcct() == 0){
            System.out.println("Account Database is empty!");
        }else{
            System.out.println("\n*list of accounts with fee and monthly interest");
            bankDatabase.printFeeAndInterest();
            System.out.println("*end of list.\n");
        }
    }

    /**
     Prints the accounts in the database in their current order with updated balances based on fees/interests.
     @param bankDatabase the database from which accounts are being updated/printed.
     */
    public void updateAndPrint(AccountDatabase bankDatabase){
        bankDatabase.updateBalance();
        if(bankDatabase.getNumAcct() == 0){
            System.out.println("Account Database is empty!");
        }else{
            System.out.println("\n*list of accounts with updated balance");
            bankDatabase.print();
            System.out.println("*end of list.\n");
        }
    }

    /**
     Creates an account from values given by a user.
     @param bankDatabase the database from which accounts are being printed.
     */
    public Account createNewAccount(Date birth, Profile newProfile, double balanceDouble,
                                    int codes, String accountType){
        Account newAccount = new Checking(newProfile, true, balanceDouble);
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
        return newAccount;
    }

    public void openAccountLastStep(AccountDatabase bankDatabase, Account newAccount){
        int index = bankDatabase.findClosedAccount(newAccount);
        if(index == -1){
            bankDatabase.open(newAccount);
            System.out.println("Account opened.");
        }else{
            bankDatabase.reopen(newAccount, index);
            System.out.println("Account reopened.");
        }
    }

    public void depositBalanceLastStep(Account newAccount, AccountDatabase bankDatabase){
        Account depositAccount = bankDatabase.findByProfileType(newAccount);
        if(depositAccount == null){
            System.out.println(newAccount.holder.toString() + " " + newAccount.getType()
                    + " is not in the database.");
        } else {
            depositAccount.deposit(newAccount.balance);
            bankDatabase.deposit(depositAccount);
            System.out.println("Deposit - balance updated.");
        }
    }

    public void withdrawBalanceLastStep(AccountDatabase bankDatabase, Account newAccount){
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
    }
}
