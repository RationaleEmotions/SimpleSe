package com.rationaleemotions.internal.locators;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public final class DefaultWaitConditions {

    public static final WaitCondition AVAILABLE = new AvailableWaitCondition();

    public static final WaitCondition VISIBLE = new VisibleWaitCondition();

    public static final WaitCondition CLICKABLE = new ClickableWaitCondition();

    private DefaultWaitConditions() {
    }

    static class AvailableWaitCondition implements WaitCondition {

        public String getName() {
            return "AVAILABLE";
        }

        public ExpectedCondition<WebElement> element(By by) {
            return ExpectedConditions.presenceOfElementLocated(by);
        }

        public ExpectedCondition<List<WebElement>> elements(By by) {
            return ExpectedConditions.presenceOfAllElementsLocatedBy(by);
        }
    }

    static class VisibleWaitCondition implements WaitCondition {

        public String getName() {
            return "VISIBLE";
        }

        public ExpectedCondition<WebElement> element(By by) {
            return ExpectedConditions.visibilityOfElementLocated(by);
        }

        public ExpectedCondition<List<WebElement>> elements(By by) {
            return ExpectedConditions.visibilityOfAllElementsLocatedBy(by);
        }
    }

    static class ClickableWaitCondition implements WaitCondition {

        public String getName() {
            return "CLICKABLE";
        }

        public ExpectedCondition<WebElement> element(By by) {
            return ExpectedConditions.elementToBeClickable(by);
        }

        public ExpectedCondition<List<WebElement>> elements(By by) {
            return null;
        }
    }
}
