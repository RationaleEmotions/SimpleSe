package com.rationaleemotions.page;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class PageObjectTest {
    private WebDriver driver;

    @BeforeClass
    public void setup() {
        driver = new FakeDriver();
    }

    @Test
    public void testCheckboxCount() {
        driver.get("http://localhost:4444");
        PageObject checkboxPage = new PageObject(driver, "src/test/resources/CheckboxPage.json");
        List<Checkbox> checkboxList = checkboxPage.getCheckboxes("checkbox");
        Assert.assertEquals(checkboxList.size(), 1);
        int checkedCount = 0;
        for (Checkbox checkbox : checkboxList) {
            if (checkbox.isChecked()) {
                checkedCount += 1;
            }
        }
        Assert.assertEquals(checkedCount, 1);
    }

    @Test
    public void testPageWithDifferentLocale() {
        driver.get("http://localhost:4444");
        PageObject homePage = new PageObject(driver, "src/test/resources/HomePage.json").forLocale("en_FR");
        Label heading = homePage.getLabel("heading");
        Assert.assertEquals(heading.getText(), "Fake text");
        Link checkbox = homePage.getLink("checkboxesLink");
        Assert.assertEquals(checkbox.isDisplayed(), true);
    }

    @AfterClass
    public void cleanup() {
        if (driver != null) {
            driver.quit();
        }
    }
}
