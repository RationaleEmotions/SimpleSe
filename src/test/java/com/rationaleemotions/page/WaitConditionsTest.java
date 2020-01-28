package com.rationaleemotions.page;

import com.google.common.base.Stopwatch;
import com.rationaleemotions.internal.JvmArgs;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

public class WaitConditionsTest {

  @Test
  public void testPageObjectWithNoWaits() {
    System.setProperty(JvmArgs.USE_DEFAULT_WAIT_STRATEGY.toString(), "false");
    try {
      WebDriver driver = new FakeDriver(0,null);
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
    WebDriver driver = new FakeDriver(45,null);
    PageObject page = new PageObject(driver, "src/test/resources/PageWithNoWaits.json");
    Stopwatch watch = Stopwatch.createStarted();
    page.getGenericElement("bar");
    watch.stop();
    Duration duration = watch.elapsed();
    assertTrue(duration.getSeconds() <= 45);
  }

}
