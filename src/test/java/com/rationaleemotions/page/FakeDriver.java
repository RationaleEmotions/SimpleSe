package com.rationaleemotions.page;

import com.beust.jcommander.internal.Lists;
import com.rationaleemotions.internal.locators.Until;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * A Fake {@link WebDriver} implementation that is used only for demonstration purposes.
 */
public class FakeDriver implements WebDriver {

    private int delay;
    private Until until;
    private boolean simulateFailure;

    public FakeDriver() {
        this(0, null);
    }

    public FakeDriver(int delay, Until until) {
        this(delay,until, false);
    }

    public FakeDriver(int delay, Until until, boolean simulateFailure) {
        this.delay = delay;
        this.until = until;
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
        elements.add(new FakeWebElement(until, simulateFailure, delay));
        return elements;
    }

    @Override
    public WebElement findElement(By by) {
        return new FakeWebElement(until, simulateFailure, delay);
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
