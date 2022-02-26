package bank;

import java.text.DecimalFormat;

public abstract class Account {
    protected Profile holder;
    protected boolean closed;
    protected double balance;

    protected double interest;
    protected double fee;
    protected String type;
    protected static int WAIVED_FEE = 0;

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
        if (this instanceof Checking) {
            Checking checkingAcct = (Checking) this;
            if (this instanceof CollegeChecking) {
                CollegeChecking cCheckingAcct = (CollegeChecking) checkingAcct;
                returnString = cCheckingAcct.toString();
            }else{
                returnString = checkingAcct.toString();
            }
        }else if (this instanceof Savings) {
            Savings savingsAcct = (Savings) this;
            if (this instanceof MoneyMarket) {
                MoneyMarket mMAcct = (MoneyMarket) savingsAcct;
                returnString = mMAcct.toString();
            }else{
                returnString = savingsAcct.toString();
            }
        }
        return returnString;
    }
    public void withdraw(double amount) {
        this.balance -= amount;
    }
    public void deposit(double amount) {
        this.balance += amount;
    }
    public abstract double monthlyInterest(); //return the monthly interest
    public abstract double fee(); //return the monthly fee
    public abstract String getType(); //return the account type (class name)
}