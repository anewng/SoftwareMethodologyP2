package bank;

import java.text.DecimalFormat;

public class AccountDatabase {
    private Account [] accounts;
    private int numAcct;
    private static final int NOT_FOUND = -1;
    private static final int DATABASE_GROWTH_INCREMENT = 4;

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
            if (accounts[i].equalsProfileAndType(account)){
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     Increases length of accounts by 4 to account for new accounts.
     */
    private void grow() {
        Account[] temp = new Account[accounts.length + DATABASE_GROWTH_INCREMENT];
        for (int i = 0; i < accounts.length; i++) {
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
        if(accounts.length < numAcct){
            grow();
        }
        accounts[numAcct - 1] = account;
        return true;
    }

    /**
     Removes an account from the bank.
     @param account the account being removed.
     @return true if the account is removed, else return false.
     */
    public boolean close(Account account) {
        int index = find(account);
        if(index == -1 ){
            return false;
        }
        Account[] temp = new Account[accounts.length];
        int j = 0;
        for(int i = 0; i < numAcct; i++){
            if(i != index){
                temp[j] = accounts[i];
                j++;
            }
        }
        numAcct--;
        accounts = temp;
        return true;
    }

    public void deposit(Account account) {
        int index = findAccountProfile(account);
        accounts[index] = account;
    }

    public boolean withdraw(Account account) {
        if (account.balance < 0) {
            return false;
        }
        int index = findAccountProfile(account);
        accounts[index] = account;
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
            System.out.println(accounts[i].toString() + "::fee " + d.format(accounts[i].fee()) + "::monthly interest " + d.format(accounts[i].monthlyInterest()));
        }
    }
    public void updateBalance() {
        for (int i = 0; i < numAcct; i++) {
            accounts[i].balance += accounts[i].monthlyInterest();
            accounts[i].balance -= accounts[i].fee();
        }
        print();
    }
}