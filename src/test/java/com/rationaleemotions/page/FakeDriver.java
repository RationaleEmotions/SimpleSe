package com.rationaleemotions.page;

import com.beust.jcommander.internal.Lists;
import com.rationaleemotions.internal.locators.WaitCondition;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * A Fake {@link WebDriver} implementation that is used only for demonstration purposes.
 */
public class FakeDriver implements WebDriver {

    private int delay;
    private WaitCondition waitCondition;
    private boolean simulateFailure;

    public FakeDriver() {
        this(0, null);
    }

    public FakeDriver(int delay, WaitCondition waitCondition) {
        this(delay,waitCondition, false);
    }

    public FakeDriver(int delay, WaitCondition waitCondition, boolean simulateFailure) {
        this.delay = delay;
        this.waitCondition = waitCondition;
        this.simulateFailure = simulateFailure;
    }


    @Override
    public void get(String url) {

    }

    @Override
    public String getCurrentUrl() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public List<WebElement> findElements(By by) {
        List<WebElement> elements = Lists.newArrayList();
        elements.add(new FakeWebElement(waitCondition, simulateFailure, delay));
        return elements;
    }

    @Override
    public WebElement findElement(By by) {
        return new FakeWebElement(waitCondition, simulateFailure, delay);
    }

    @Override
    public String getPageSource() {
        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public void quit() {

    }

    @Override
    public Set<String> getWindowHandles() {
        return null;
    }

    @Override
    public String getWindowHandle() {
        return null;
    }

    @Override
    public TargetLocator switchTo() {
        return null;
    }

    @Override
    public Navigation navigate() {
        return null;
    }

    @Override
    public Options manage() {
        return null;
    }
}
