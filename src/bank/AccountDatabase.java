package bank;

public class AccountDatabase {
    private Account [] accounts;
    private int numAcct;
    private int find(Account account) { }
    private void grow() { }
    public boolean open(Account account) { }
    public boolean close(Account account) { }
    public void deposit(Account account) { }
    public boolean withdraw(Account account) { } //return false if insufficient fund  public void print() { }
    public void printByAccountType() { }
    public void printFeeAndInterest() { }
}