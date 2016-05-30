package ua.com.juja;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PairScheduleTableTest {

    @Test
    public void test() {

//Test 2 ------------------------------------------------------------------
        PairScheduleTable actuals1 = new PairScheduleTable(30);
        PairScheduleTable expected1 = new PairScheduleTable(30, 1, 5);

        System.out.println(actuals1);
        System.out.println(expected1);

        assertEquals(actuals1, expected1);
        System.out.println("test 1 is OK");

//Test 2 ------------------------------------------------------------------
        PairScheduleTable actuals2 = new PairScheduleTable();
        PairScheduleTable expected2 = new PairScheduleTable(3);

        System.out.println(actuals2);
        System.out.println(expected2);

        assertEquals(actuals2, expected2);
        System.out.println("test 2 is OK");
    }
}
