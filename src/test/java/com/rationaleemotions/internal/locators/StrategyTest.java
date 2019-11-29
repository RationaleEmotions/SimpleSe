package com.rationaleemotions.internal.locators;

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
                {"xpath=//h1", By.ByXPath.class},
                {"xpath=/h1", By.ByXPath.class},
                {"xpath=(/h1)", By.ByXPath.class},
                {"xpath=./h1", By.ByXPath.class},
                {"css=", By.ByCssSelector.class},
                {"class=", By.ByClassName.class},
                {"linkText=", By.ByLinkText.class},
                {"partialLinkText=", By.ByPartialLinkText.class},
                {"tagName=", By.ByTagName.class},
                {"foo", ByIdOrName.class},
                {"//h1", By.ByXPath.class},
                {"//*", By.ByXPath.class},
                {"/html/body/h1", By.ByXPath.class},
                {"  //", By.ByXPath.class},
                {"//label[@id='message23']", By.ByXPath.class},
                {"./h1", By.ByXPath.class},
                {" ./html/body/table", By.ByXPath.class}
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
