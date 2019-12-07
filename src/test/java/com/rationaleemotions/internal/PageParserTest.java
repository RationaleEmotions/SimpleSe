package com.rationaleemotions.internal;

import com.rationaleemotions.internal.parser.PageParser;
import com.rationaleemotions.internal.parser.pojos.Element;
import com.rationaleemotions.internal.parser.pojos.PageElement;
import com.rationaleemotions.internal.parser.pojos.Wait;
import com.rationaleemotions.page.WebElementType;
import java.io.FileNotFoundException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PageParserTest {

  @Test
  public void testParser() throws FileNotFoundException {
    PageElement loginPage = PageParser.parsePage("src/test/resources/LoginPage.json");
    Assert.assertEquals(loginPage.getName(), "LoginPage");
    Assert.assertEquals(loginPage.getDefaultLocale(), "en_US");
    Assert.assertEquals(loginPage.getElements().size(), 3);
    Element heading = findElement(loginPage, "heading");
    Assert.assertEquals(heading.getType(), WebElementType.GENERIC);
    Wait wait = new Wait();
    wait.setDuration(10);
    wait.setUntil("visible");
    Assert.assertEquals(heading.getWait(), wait);
    Assert.assertFalse(heading.isList());
    Element checkboxesLink = findElement(loginPage, "checkboxesLink");
    Assert.assertTrue(checkboxesLink.isList());
  }

  private Element findElement(PageElement page, String name) {
    return page.getElements().stream().filter(element -> element.getName().equalsIgnoreCase(name))
        .findFirst().orElse(null);
  }

}
