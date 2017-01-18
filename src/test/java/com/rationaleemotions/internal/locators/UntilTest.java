package com.rationaleemotions.internal.locators;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.rationaleemotions.internal.locators.Until.Available;
import static com.rationaleemotions.internal.locators.Until.Clickable;
import static com.rationaleemotions.internal.locators.Until.Visible;

public class UntilTest {
    @Test(dataProvider = "getTestData")
    public void testAllCombinations(String rawText, Until expected) {
        Assert.assertEquals(Until.parse(rawText), expected);
    }

    @DataProvider
    public Object[][] getTestData() {
        return new Object[][] {
            {null, Available},
            {"", Available},
            {"AVAILABLE", Available},
            {"available", Available},
            {"IdontExist", Available},
            {"vIsIbLe", Visible},
            {"Clickable", Clickable}
        };
    }
}
