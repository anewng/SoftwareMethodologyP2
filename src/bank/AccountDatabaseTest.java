package bank;

import org.junit.Test;

import static org.junit.Assert.*;

public class AccountDatabaseTest {

    @Test
    public void open() {
        // Testing opening all four types of accounts + grow function for account database
        AccountDatabase database = new AccountDatabase();
        Profile holder = new Profile("Jasmine", "Flanders", new Date("10/17/2001"));
        MoneyMarket acc = new MoneyMarket(holder, false, 500, 0);
        assertTrue(database.open(acc));
        Profile holder2 = new Profile("Annie", "Wang", new Date("12/3/2002"));
        CollegeChecking acc2 = new CollegeChecking(holder2, false, 500, 2);
        assertTrue(database.open(acc2));
        Profile holder3 = new Profile("Connie", "Ying", new Date("11/9/2002"));
        Checking acc3 = new Checking(holder3, false, 500);
        assertTrue(database.open(acc3));
        Profile holder4 = new Profile("Reece", "Tong", new Date("11/11/2002"));
        Savings acc4 = new Savings(holder4, false, 500, 0);
        assertTrue(database.open(acc4));
        Profile holder5 = new Profile("Charu", "Jain", new Date("1/28/2002"));
        MoneyMarket acc5 = new MoneyMarket(holder5, false, 500, 0);
        assertTrue(database.open(acc5));
    }

    @Test
    public void close() {
        // Testing closing an account that does not exist in account database
        AccountDatabase database = new AccountDatabase();
        Profile holder = new Profile("Dorothy", "Lu", new Date("8/7/2002"));
        CollegeChecking acc = new CollegeChecking(holder, false, 500, 1);
        assertFalse(database.close(acc));
        database.open(acc);
        // Testing closing an account that does exist in account database
        assertTrue(database.close(acc));
        // Testing that loyalties are updated for Savings
        Profile holder2 = new Profile("Reece", "Tong", new Date("11/11/2002"));
        Savings acc2 = new Savings(holder2, false, 500, 1);
        database.open(acc2);
        database.close(acc2);
        assertTrue(database.findByProfileType(acc2).closed);
        Profile holder3 = new Profile("Charu", "Jain", new Date("1/28/2002"));
        MoneyMarket acc3 = new MoneyMarket(holder3, false, 500, 1);
        database.open(acc3);
        database.close(acc2);
        assertTrue(database.findByProfileType(acc2).closed);
        // Testing that campus codes are NOT updated for College Checking
        Profile holder4 = new Profile("Annie", "Wang", new Date("12/3/2002"));
        CollegeChecking acc4 = new CollegeChecking(holder4, false, 500, 2);
        database.open(acc4);
        database.close(acc4);
        assertEquals(2, ((CollegeChecking) database.findByProfileType(acc4)).getCollegeCode());
    }
}