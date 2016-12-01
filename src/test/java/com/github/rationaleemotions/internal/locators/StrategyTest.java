package com.github.rationaleemotions.internal.locators;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ByIdOrName;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class StrategyTest {
    @Test (dataProvider = "getTestData")
    public void testStrategyHappyFlows(String locator, Class<?> clazz) {
        Strategy strategy = Strategy.identifyStrategy(locator);
        By by = strategy.getStrategy(locator);
        Assert.assertEquals(by.getClass(), clazz);
    }

    @DataProvider
    public Object[][] getTestData(Method method) {
        if (method.getName().equals("testStrategyHappyFlows")) {
            return new Object[][] {
                {"//h1", By.ByXPath.class},
                {"/h1", By.ByXPath.class},
                {"./h1", By.ByXPath.class},
                {"css=", By.ByCssSelector.class},
                {"foo", ByIdOrName.class}
            };
        }
        return new Object[][] {
            {""},
            {null}

        };
    }

    @Test (dataProvider = "getTestData", expectedExceptions = IllegalStateException.class,
        expectedExceptionsMessageRegExp = "Encountered an unparseable locator .*")
    public void testStrategyNegativeFlows(String locator) {
        Strategy.identifyStrategy(locator);
    }

}
