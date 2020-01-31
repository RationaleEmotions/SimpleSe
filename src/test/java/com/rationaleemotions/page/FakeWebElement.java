package com.rationaleemotions.page;

import com.rationaleemotions.internal.locators.DefaultWaitConditions;
import com.rationaleemotions.internal.locators.WaitCondition;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;

import java.util.List;

/**
 * A Fake {@link WebElement} implementation for demonstration purposes.
 */
public class FakeWebElement implements WebElement {

    private final WaitCondition waitCondition;
    private final boolean simulateFailure;
    private final int sleepTime;
    private CharSequence[] keysToSend;

    public FakeWebElement(WaitCondition waitCondition, boolean simulateFailure, int sleepTime) {
        this.waitCondition = waitCondition;
        this.simulateFailure = simulateFailure;
        this.sleepTime = sleepTime;
    }

    @Override
    public void click() {}

    @Override
    public void submit() {}

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        this.keysToSend = keysToSend;
    }

    @Override
    public void clear() {}

    @Override
    public String getTagName() {
        return "fakeTag";
    }

    @Override
    public String getAttribute(String name) {
        return "fakeAttribute";
    }

    @Override
    public boolean isSelected() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if (waitCondition != null && waitCondition.equals(DefaultWaitConditions.CLICKABLE)) {
            //This is set only when we want to simulate failures for our tests.
            return false;
        }
        sleep();
        return true;
    }

    @Override
    public String getText() {
        if (keysToSend == null) {
            return "Fake text";
        }
        StringBuilder sb = new StringBuilder();
        for (CharSequence each : keysToSend) {
            sb.append(each);
        }
        return sb.toString();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return null;
    }

    @Override
    public WebElement findElement(By by) {
        return null;
    }

    @Override
    public boolean isDisplayed() {
        if (waitCondition != null && waitCondition.equals(DefaultWaitConditions.VISIBLE) && simulateFailure) {
            //This is set only when we want to simulate failures for our tests.
            return false;
        }
        return true;
    }

    @Override
    public Point getLocation() {
        return new Point(0,0);
    }

    @Override
    public Dimension getSize() {
        return new Dimension(0,0);
    }

    @Override
    public Rectangle getRect() {
        return new Rectangle(0,0,0,0);
    }

    @Override
    public String getCssValue(String propertyName) {
        return "FakeCssValue";
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return null;
    }

    private void sleep() {
        if (sleepTime <= 0) {
            return;
        }
        try {
            TimeUnit.SECONDS.sleep(sleepTime);
            System.err.println("Woke up after sleeping for " + sleepTime + " seconds.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
