package com.rationaleemotions.internal.locators;

import static com.rationaleemotions.internal.locators.WaitServiceListener.INSTANCE;

import com.rationaleemotions.internal.parser.pojos.Wait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class WaitServiceListenerTest {

    @Test
    public void testWaitValid() {
        Wait wait = new Wait();
        wait.setDuration(10);
        wait.setUntil("visible");
        Assert.assertTrue(wait.isElementConditionValid());
        Assert.assertTrue(wait.isElementsConditionValid());
    }

    @Test
    public void testWaitNull() {
        Wait wait = new Wait();
        wait.setDuration(10);
        wait.setUntil("nullwait");
        Assert.assertFalse(wait.isElementConditionValid());
        Assert.assertFalse(wait.isElementsConditionValid());
    }
    @Test
    public void testPartialWaitNull() {
        Wait wait = new Wait();
        wait.setDuration(10);
        wait.setUntil("partialNullWait");
        Assert.assertTrue(wait.isElementConditionValid());
        Assert.assertFalse(wait.isElementsConditionValid());
    }

    @Test(dataProvider = "getPositiveTestData")
    public void testConditionParseHappyFlow(String name) {
        WaitCondition condition = INSTANCE.parse(name);
        Assert.assertNotNull(condition);
    }

    @Test(dataProvider = "getNegativeTestData")
    public void testConditionParseNegativeFlow(String name) {
        WaitCondition condition = INSTANCE.parse(name);
        Assert.assertEquals(condition.getName(), DefaultWaitConditions.AVAILABLE.getName());
    }

    @DataProvider
    public Object[][] getPositiveTestData() {
        return new Object[][] {
            {"AVAILABLE"},
            {"PResence"},
            {"partialNullWait"},
            {"nullwait"},
            {"ClICkable"},

        };
    }
    @DataProvider
    public Object[][] getNegativeTestData() {
        return new Object[][] {
            {"AVILABLE"},
            {"vailable"},
            {"IdontExist"},
            {"vIsIbL"},
            {"lickable"}
        };
    }
}