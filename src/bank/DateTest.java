package bank;

import static org.junit.Assert.*;

public class DateTest {

    @org.junit.Test
    public void isValid() {
        Date date = new Date("2/29/2011");
        assertFalse(date.isValid());
        Date t1 = new Date("12/12/1890");
        assertFalse(t1.isValid());
        Date t2 = new Date("12/00/2020");
        assertFalse(t2.isValid());
        Date t31 = new Date("00/12/2020");
        assertFalse(t31.isValid());
        Date t32 = new Date("13/12/2020");
        assertFalse(t32.isValid());
        Date t4 = new Date("02/29/2021");
        assertFalse(t4.isValid());
        Date t5 = new Date("02/30/2020");
        assertFalse(t5.isValid());
        Date t61 = new Date("01/32/2020");
        assertFalse(t61.isValid());
        Date t62 = new Date("03/32/2020");
        assertFalse(t62.isValid());
        Date t63 = new Date("05/32/2020");
        assertFalse(t63.isValid());
        Date t64 = new Date("07/32/2020");
        assertFalse(t64.isValid());
        Date t65 = new Date("08/32/2020");
        assertFalse(t65.isValid());
        Date t66 = new Date("10/32/2020");
        assertFalse(t66.isValid());
        Date t67 = new Date("12/32/2020");
        assertFalse(t67.isValid());
        Date t71 = new Date("04/32/2020");
        assertFalse(t71.isValid());
        Date t72 = new Date("06/32/2020");
        assertFalse(t72.isValid());
        Date t73 = new Date("09/32/2020");
        assertFalse(t73.isValid());
        Date t74 = new Date("11/32/2020");
        assertFalse(t74.isValid());
        Date valid = new Date("10/17/2001");
        assertTrue(valid.isValid());
    }
}