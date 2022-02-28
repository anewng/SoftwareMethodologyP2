package bank;

import java.text.DecimalFormat;

public class AccountDatabase {
    private Account [] accounts;
    private int numAcct;
    private static final int NOT_FOUND = -1;
    private static final int DATABASE_GROWTH_INCREMENT = 4;

    /**
     Constructor creates an AccountDatabase object.
     */
    public AccountDatabase(){
        numAcct = 0;
        accounts = new Account[DATABASE_GROWTH_INCREMENT];
    }

    /**
     Gets numAcct
     @return int is the number of accounts in the Database
     */
    public int getNumAcct(){
        return numAcct;
    }

    /**
     Gets accounts
     @return Account[] is the accounts Database itself
     */
    public Account[] getDatabase(){
        return accounts;
    }

    /**
     Finds an account in a bank.
     @param account the account being searched for.
     @return true index of account in bank if found, else returns NOT_FOUND.
     */
    private int find(Account account) {
        for (int i = 0; i < numAcct; i++){
            if (accounts[i].equals(account)){
                return i;
            }
        }
        return NOT_FOUND;
    }

    private int findAccountProfile(Account account) {
        for (int i = 0; i < numAcct; i++){
            if (accounts[i].equalsProfileTypeClosed(account)){
                return i;
            }
        }
        return NOT_FOUND;
    }

    public int findClosedAccount(Account account){
        for (int i = 0; i < numAcct; i++){
            if (accounts[i].equalsProfileType(account)){
                return i;
            }
        }
        return NOT_FOUND;
    }


    public boolean findCProfile(Account account){
        for (int i = 0; i < numAcct; i++){
            if ((accounts[i].holder.equals(account.holder) == 0)
                    && (accounts[i].getType().compareTo("Checking") == 0)){
                return true;
            }
        }
        return false;
    }

    public boolean findCCProfile(Account account){
        for (int i = 0; i < numAcct; i++){
            if ((accounts[i].holder.equals(account.holder) == 0)
                    && (accounts[i].getType().compareTo("College Checking") == 0)){
                return true;
            }
        }
        return false;
    }

    public Account findByProfileType(Account account) {
        for (int i = 0; i < numAcct; i++){
            if ((accounts[i].holder.equals(account.holder) == 0) && accounts[i].getType().compareTo(account.getType()) == 0){
                return accounts[i];
            }
        }
        return null;
    }

    public boolean isInDatabase(Account account){
        for(int i = 0; i < numAcct; i++){
            if(accounts[i].equals(account)){
                return true;
            }
        }
        return false;
    }

    /**
     Increases length of accounts by 4 to account for new accounts.
     */
    private void grow() {
        Account[] temp = new Account[accounts.length + DATABASE_GROWTH_INCREMENT];
        for (int i = 0; i < numAcct-1; i++) {
            temp[i] = accounts[i];
        }
        accounts = temp;
    }

    /**
     Adds an account to the bank.
     @param account the account being added.
     @return true if account is added.
     */
    public boolean open(Account account) {
        numAcct++;
        if (accounts.length < numAcct){
            grow();
        }
        accounts[numAcct-1] = account;
        return true;
    }

    public void reopen(Account account, int index){
        accounts[index].balance = account.balance;
        accounts[index].closed = false;
        if(accounts[index].getType().compareTo("Savings") == 0){
            ((Savings) accounts[index]).loyal = ((Savings) account).loyal;
        } else if (accounts[index].getType().compareTo("Money Market") == 0 && accounts[index].balance >= 2500) {
            ((MoneyMarket) accounts[index]).loyal = 1;
        } else if (accounts[index].getType().compareTo("College Checking") == 0){
            ((CollegeChecking) accounts[index]).collegeCode = ((CollegeChecking) account).collegeCode;
        }
    }

    /**
     Closes an account in the database.
     @param account the account being closed.
     @return true if the account is closed, else return false.
     */
    public boolean close(Account account) {
        int index = find(account);
        if (index == -1 ){
            return false;
        }
        accounts[index].closed = true;
        accounts[index].balance = 0;
        Account acc = accounts[index];
        if (accounts[index] instanceof Savings) {
            ((Savings) accounts[index]).loyal = 0;
        }
        return true;
    }

    public void deposit(Account account) {
        int index = findAccountProfile(account);
        accounts[index] = account;
        if (accounts[index].getType().compareTo("Money Market") == 0 && accounts[index].balance >= 2500) {
            ((MoneyMarket) accounts[index]).loyal = 1;
        }
    }

    public boolean withdraw(Account account) {
        if (account.balance < 0) {
            return false;
        }
        int index = findAccountProfile(account);
        accounts[index] = account;
        if (accounts[index].getType().compareTo("Money Market") == 0 ) {
            if(accounts[index].balance < 2500){
                ((MoneyMarket) accounts[index]).loyal = 0;
            }
            ((MoneyMarket) accounts[index]).withdrawalCount ++;
        }
        return true;
    }

    public void print() {
        for (int i = 0; i < numAcct; i++) {
            System.out.println(accounts[i].toString());
        }
    }

    public void printByAccountType() {
        for (int i = 1; i < numAcct; ++i) {
            Account key = accounts[i];
            int j = i - 1;
            while (j >= 0 && key.alphabetizeAccountType(accounts[j]) == -1 ) {
                accounts[j + 1] = accounts[j];
                j = j - 1;
            }
            accounts[j + 1] = key;
        }
        print();
    }

    public void printFeeAndInterest() {
        for (int i = 0; i < numAcct; i++) {
            DecimalFormat d = new DecimalFormat("'$'0.00");
            System.out.println(accounts[i].toString() + "::fee " + d.format(accounts[i].fee())
                    + "::monthly interest " + d.format(accounts[i].monthlyInterest()));
        }
    }

    public void updateBalance() {
        for (int i = 0; i < numAcct; i++) {
            accounts[i].balance += accounts[i].monthlyInterest();
            accounts[i].balance -= accounts[i].fee();
        }
    }
}