package bank;

public abstract class Account {
    protected Profile holder;
    protected boolean closed;
    protected double balance;

    @Override
    public boolean equals(Object obj) {
        
    }
    @Override
    public String toString() {

    }
    public void withdraw(double amount) {

    }
    public void deposit(double amount) {

    }
    public abstract double monthlyInterest(); //return the monthly interest
    public abstract double fee(); //return the monthly fee
    public abstract String getType(); //return the account type (class name)
}