package bank;

public abstract class Account {
    protected Profile holder;
    protected boolean closed;
    protected double balance;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Account) {
            Account account = (Account) obj;
            if (this.holder.equals(holder) == 0
                    && this.slot.compareTo(appointment.slot) == 0
                    && this.location.compareTo(appointment.location) == 0) { //returns 0 if they're the same right? lmao
                return true;
            }
        }
        return false;
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