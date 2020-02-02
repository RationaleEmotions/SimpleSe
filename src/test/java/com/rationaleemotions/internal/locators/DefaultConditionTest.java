package com.rationaleemotions.internal.locators;

import static com.rationaleemotions.internal.locators.WaitServiceListener.INSTANCE;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DefaultConditionTest {

    @Test(dataProvider = "getTestData")
    public void testAllCombinations(String rawText, WaitCondition expected) {
        Assert.assertEquals(INSTANCE.parse(rawText), expected);
    }

    @DataProvider
    public Object[][] getTestData() {
        return new Object[][]{
            {null, DefaultWaitConditions.AVAILABLE},
            {"", DefaultWaitConditions.AVAILABLE},
            {"AVAILABLE", DefaultWaitConditions.AVAILABLE},
            {"available", DefaultWaitConditions.AVAILABLE},
            {"IdontExist", DefaultWaitConditions.AVAILABLE},
            {"vIsIbLe", DefaultWaitConditions.VISIBLE},
            {"Clickable", DefaultWaitConditions.CLICKABLE}
        };
    }
}
