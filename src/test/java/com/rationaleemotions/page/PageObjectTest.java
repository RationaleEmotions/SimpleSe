package com.rationaleemotions.page;

import com.rationaleemotions.internal.locators.DefaultWaitConditions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class PageObjectTest {

    @Test
    public void testCheckboxCount() {
        WebDriver driver = new FakeDriver();
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
        WebDriver driver = new FakeDriver();
        PageObject homePage = new PageObject(driver, "src/test/resources/HomePage.json").forLocale("en_FR");
        Label heading = homePage.getLabel("heading");
        Assert.assertEquals(heading.getText(), "Fake text");
        Link checkbox = homePage.getLink("checkboxesLink");
        Assert.assertTrue(checkbox.isDisplayed());
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Unable to locate element.*")
    public void testInvalidElement() {
        WebDriver driver = new FakeDriver();
        PageObject homePage = new PageObject(driver, "src/test/resources/HomePage.json");
        homePage.getLabel("iDontExist");
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "A field name cannot be null \\Q(\\Eor\\Q)\\E empty")
    public void testInvalidElementWithNullValues() {
        WebDriver driver = new FakeDriver();
        PageObject homePage = new PageObject(driver, "src/test/resources/HomePage.json");
        homePage.getLabel("");
    }

    @Test
    public void testFindElementWithWaits() {
        WebDriver driver = new FakeDriver(2, DefaultWaitConditions.CLICKABLE);
        PageObject homePage = new PageObject(driver, "src/test/resources/HomePage.json");
        Label heading = homePage.getLabel("heading");
        Assert.assertEquals(heading.getText(), "Fake text");
    }

    @Test(expectedExceptions = TimeoutException.class,
            expectedExceptionsMessageRegExp = "Expected condition failed: waiting for visibility of element located .*")
    public void testFindElementsWithWaitsTimingOutVisibilityCondition() {
        WebDriver driver = new FakeDriver(5, DefaultWaitConditions.VISIBLE, true);
        PageObject homePage = new PageObject(driver, "src/test/resources/HomePage.json");
        homePage.getLabel("heading");
    }

    @Test(expectedExceptions = TimeoutException.class,
            expectedExceptionsMessageRegExp = "Expected condition failed: waiting for element to be clickable.*")
    public void testFindElementsWithWaitsTimingoutClickableCondition() {
        WebDriver driver = new FakeDriver(5, DefaultWaitConditions.CLICKABLE, true);
        PageObject homePage = new PageObject(driver, "src/test/resources/HomePage.json");
        homePage.getLink("checkboxesLink");
    }

    @Test
    public void testKeyPressMethodsForTextField() {
        WebDriver driver = new FakeDriver(5, DefaultWaitConditions.CLICKABLE);
        PageObject homePage = new PageObject(driver, "src/test/resources/HomePage.json");
        TextField textField = homePage.getTextField("sampleTxtField");
        textField.type(Keys.DIVIDE);
        Assert.assertEquals(textField.getText(), Keys.DIVIDE.toString());
    }

    @Test
    public void testKeysChordMethodsForTextField() {
        WebDriver driver = new FakeDriver(1, DefaultWaitConditions.CLICKABLE);
        PageObject homePage = new PageObject(driver, "src/test/resources/HomePage.json");
        TextField textField = homePage.getTextField("sampleTxtField");
        String expected = Keys.chord(Keys.CONTROL, "a");
        textField.type(Keys.chord(Keys.CONTROL, "a"));
        Assert.assertEquals(textField.getText(), expected);
    }
}
