package com.rationaleemotions.internal.locators;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TestConditions {

    public static class PresenceCondition implements WaitCondition {

        @Override
        public String getName() {
            return "PRESENCE";
        }

        @Override
        public ExpectedCondition<WebElement> element(By by) {
            return ExpectedConditions.presenceOfElementLocated(by);
        }

        @Override
        public ExpectedCondition<List<WebElement>> elements(By by) {
            return ExpectedConditions.presenceOfAllElementsLocatedBy(by);
        }
    }
    public static class NullCondition implements WaitCondition {

        @Override
        public String getName() {
            return "nullWait";
        }

        @Override
        public ExpectedCondition<WebElement> element(By by) {
            return null;
        }

        @Override
        public ExpectedCondition<List<WebElement>> elements(By by) {
            return null;
        }
    }

    public static class PartialNullCondition implements WaitCondition {

        @Override
        public String getName() {
            return "partialNullWait";
        }

        @Override
        public ExpectedCondition<WebElement> element(By by) {
            return ExpectedConditions.elementToBeClickable(by);
        }

        @Override
        public ExpectedCondition<List<WebElement>> elements(By by) {
            return null;
        }
    }
}
