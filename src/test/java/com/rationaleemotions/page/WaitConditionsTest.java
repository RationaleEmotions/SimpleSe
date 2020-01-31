package com.rationaleemotions.page;

import com.google.common.base.Stopwatch;
import com.rationaleemotions.internal.JvmArgs;
import com.rationaleemotions.internal.locators.DefaultWaitConditions;
import java.time.Duration;
import org.openqa.selenium.WebDriver;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class WaitConditionsTest {

  @Test
  public void testPageObjectWithNoWaits() {
    System.setProperty(JvmArgs.USE_DEFAULT_WAIT_STRATEGY.toString(), "false");
    try {
      WebDriver driver = new FakeDriver(0, null);
      PageObject page = new PageObject(driver, "src/test/resources/PageWithNoWaits.json");
      Stopwatch watch = Stopwatch.createStarted();
      page.getGenericElement("bar");
      watch.stop();
      Duration duration = watch.elapsed();
      assertTrue(duration.getSeconds() < 1);
    } finally {
      System.setProperty(JvmArgs.USE_DEFAULT_WAIT_STRATEGY.toString(), "true");
    }
  }

  @Test
  public void testPageObjectWithImplicitDefaultWaits() {
    WebDriver driver = new FakeDriver(45, null);
    PageObject page = new PageObject(driver, "src/test/resources/PageWithNoWaits.json");
    Stopwatch watch = Stopwatch.createStarted();
    page.getGenericElement("bar");
    watch.stop();
    Duration duration = watch.elapsed();
    assertTrue(duration.getSeconds() <= 45);
  }

  @Test
  public void testPageObjectsWithDisabledWaits() {
    WebDriver driver = new FakeDriver(45, null);
    PageObject page = new PageObject(driver, "src/test/resources/PageWithNoWaits.json");
    Stopwatch watch = Stopwatch.createStarted();
    page.getGenericElement("foobar");
    watch.stop();
    Duration duration = watch.elapsed();
    assertTrue(duration.getSeconds() <= 1);
  }

  @Test(dataProvider = "dp")
  public void testPageObjectsWithAllWaitsMixed(int wait, String element, int expectedSeconds) {
    System.setProperty(JvmArgs.USE_DEFAULT_WAIT_STRATEGY.toString(), "true");
    WebDriver driver = new FakeDriver(wait, DefaultWaitConditions.AVAILABLE);
    PageObject page = new PageObject(driver, "src/test/resources/PageWithNoWaits.json");
    Stopwatch watch = Stopwatch.createStarted();
    page.getGenericElement(element);
    watch.stop();
    Duration duration = watch.elapsed();
    assertTrue(duration.getSeconds() <= expectedSeconds);
  }

  @DataProvider(name = "dp")
  public Object[][] getData() {
    return new Object[][]{
//        {45, "bar", 45},
//        {20, "foo", 20},
        {45, "foobar", 1},
    };
  }

}
