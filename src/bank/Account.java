package bank;

import java.nio.file.AtomicMoveNotSupportedException;

public abstract class Account {
    protected Profile holder;
    protected boolean closed;
    protected double balance;

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

    }
    public void deposit(double amount) {

    }
    public abstract double monthlyInterest(); //return the monthly interest
    public abstract double fee(); //return the monthly fee
    public abstract String getType(); //return the account type (class name)
}