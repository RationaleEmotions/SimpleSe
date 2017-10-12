package com.rationaleemotions.page;

import com.rationaleemotions.internal.locators.Until;
import org.openqa.selenium.*;

import java.util.List;

/**
 * A Fake {@link WebElement} implementation for demonstration purposes.
 */
public class FakeWebElement implements WebElement {

    private Until until;
    private boolean simulateFailure;

    public FakeWebElement(Until until, boolean simulateFailure) {
        this.until = until;
        this.simulateFailure = simulateFailure;
    }
    @Override
    public void click() {}

    @Override
    public void submit() {}

    @Override
    public void sendKeys(CharSequence... keysToSend) {}

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
        if (until == Until.Clickable) {
            //This is set only when we want to simulate failures for our tests.
            return false;
        }
        return true;
    }

    @Override
    public String getText() {
        return "Fake text";
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
        if (until == Until.Visible && simulateFailure) {
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
}
