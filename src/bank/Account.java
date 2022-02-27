package bank;

import java.text.DecimalFormat;

public abstract class Account {
    protected Profile holder;
    protected boolean closed;
    protected double balance;

    protected static final int WAIVED_FEE = 0;
    private static final int FIRST_TWO_LETTERS = 2;

    public Account(Profile holder, boolean closed, double balance){    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Account) {
            Account account = (Account) obj;
            if( ((account instanceof Checking && this instanceof Checking)
                && (account instanceof CollegeChecking && this instanceof CollegeChecking ||
                    !(account instanceof CollegeChecking) && !(this instanceof CollegeChecking)))
            || ((account instanceof Savings && this instanceof Savings)
                    && (account instanceof MoneyMarket && this instanceof MoneyMarket ||
                    !(account instanceof MoneyMarket) && !(this instanceof MoneyMarket)))){
                if (this.holder.equals(holder) == 0
                        && (this.closed == account.closed)
                        && (this.balance == account.balance)){
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public String toString() {
        String returnString = "";
        DecimalFormat d = new DecimalFormat("'$'0.00");
        if (this instanceof Checking) {
            Checking checkingAcct = (Checking) this;
            returnString += checkingAcct.getType() + "::" + holder.toString() + "::Balance " + d.format(balance);
        } else if (this instanceof Savings) {
            Savings savingsAcct = (Savings) this;
            returnString += savingsAcct.getType() + "::" + holder.toString() + "::Balance " + d.format(balance);
        }
        return returnString;
    }
    public void withdraw(double amount) {
        this.balance -= amount;
        if (this instanceof MoneyMarket && this.balance < 2500) {
            MoneyMarket account = (MoneyMarket) this;
            account.loyal = 0;
        }
    }
    public void deposit(double amount) {
        this.balance += amount;
    }
    public boolean equalsProfileTypeClosed(Object obj) {
        if (obj instanceof Account) {
            Account account = (Account) obj;
            if( ((account instanceof Checking && this instanceof Checking)
                    && (account instanceof CollegeChecking && this instanceof CollegeChecking ||
                    !(account instanceof CollegeChecking) && !(this instanceof CollegeChecking)))
                    || ((account instanceof Savings && this instanceof Savings)
                    && (account instanceof MoneyMarket && this instanceof MoneyMarket ||
                    !(account instanceof MoneyMarket) && !(this instanceof MoneyMarket)))){
                if (this.holder.equals(holder) == 0
                        && (this.closed == account.closed)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean equalsProfileType(Object obj) {
        if (obj instanceof Account) {
            Account account = (Account) obj;
            if( ((account instanceof Checking && this instanceof Checking)
                    && (account instanceof CollegeChecking && this instanceof CollegeChecking ||
                    !(account instanceof CollegeChecking) && !(this instanceof CollegeChecking)))
                    || ((account instanceof Savings && this instanceof Savings)
                    && (account instanceof MoneyMarket && this instanceof MoneyMarket ||
                    !(account instanceof MoneyMarket) && !(this instanceof MoneyMarket)))){
                if (this.holder.equals(holder) == 0){
                    return true;
                }
            }
        }
        return false;
    }
    public abstract double monthlyInterest(); //return the monthly interest
    public abstract double fee(); //return the monthly fee
    public abstract String getType(); //return the account type (class name)

    public int alphabetizeAccountType(Account account) {
        char thisAccountType[] = this.getType().toCharArray();
        char accountType[] = account.getType().toCharArray();
        if (this.getType().compareTo(account.getType()) == 0){
            return 0;
        }else {
            for (int i = 0; i < FIRST_TWO_LETTERS; i++) {
                if (thisAccountType[i] < accountType[i]) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }
        return 0;
    }
}