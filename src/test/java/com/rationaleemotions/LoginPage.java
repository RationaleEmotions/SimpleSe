package com.rationaleemotions;

import com.rationaleemotions.page.GenericElement;
import com.rationaleemotions.page.Label;
import com.rationaleemotions.page.Link;
import com.rationaleemotions.page.PageObject;
import com.rationaleemotions.page.TextField;
import java.util.List;
import org.openqa.selenium.WebDriver;

public class LoginPage {

  private final PageObject page;

  public LoginPage(WebDriver driver) {
    this(driver, "");
  }

  public LoginPage(WebDriver driver, String locale) {
    PageObject pageObject = new PageObject(driver, "src/test/resources/LoginPage.json");
    if (locale != null && !locale.trim().isEmpty()) {
      pageObject = new PageObject(driver, "src/test/resources/LoginPage.json").forLocale(locale);
    }
    this.page = pageObject;
  }

  public Label getHeading() {
    return page.getLabel("heading");
  }

  public GenericElement getGenericElement() {
    return page.getGenericElement("foo");
  }

  public List<Link> getCheckboxesLink() {
    return page.getLinks("checkboxesLink");
  }

  public TextField getSampleTxtField() {
    return page.getTextField("sampleTxtField");
  }
}
