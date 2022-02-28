package bank;

import org.junit.Test;

import static org.junit.Assert.*;

public class MoneyMarketTest {

    @Test
    public void testMonthlyInterest() {
        Profile holder = new Profile("Jasmine", "Flanders", new Date("10/17/2001"));
        MoneyMarket acc = new MoneyMarket(holder, false, 500, 0);
        assertEquals(acc.monthlyInterest(), 0.33, 0.01);
        MoneyMarket acc1 = new MoneyMarket(holder, false, 500, 1);
        assertEquals(acc1.monthlyInterest(), 0.33, 0.01);
        MoneyMarket acc2 = new MoneyMarket(holder, false, 3000, 0);
        assertEquals(acc2.monthlyInterest(), 2.38, 0.01);
        MoneyMarket acc3 = new MoneyMarket(holder, false, 3000, 1);
        assertEquals(acc3.monthlyInterest(), 2.38, 0.01);
        MoneyMarket acc4 = new MoneyMarket(holder, false, 2500, 0);
        assertEquals(acc4.monthlyInterest(), 1.98, 0.01);
        MoneyMarket acc5 = new MoneyMarket(holder, false, 2500, 1);
        assertEquals(acc5.monthlyInterest(), 1.98, 0.01);



        //loyal vs not loyal
        //
    }
}