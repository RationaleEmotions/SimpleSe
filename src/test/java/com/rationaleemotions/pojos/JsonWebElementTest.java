package com.rationaleemotions.pojos;

import com.rationaleemotions.internal.locators.DefaultWaitConditions;
import com.rationaleemotions.internal.locators.WaitCondition;
import com.rationaleemotions.internal.parser.pojos.Element;
import com.rationaleemotions.internal.parser.pojos.Locale;
import com.rationaleemotions.internal.parser.pojos.Wait;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ByIdOrName;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class JsonWebElementTest {

    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = MandatoryKeys.NAME
        + JsonWebElement.ATTRIBUTE_IS_MISSING)
    public void testMissingName() {
        JsonWebElement.newElement(new Element(), "en_US");
    }

    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = MandatoryKeys.LOCALE +
        JsonWebElement.ATTRIBUTE_IS_MISSING)
    public void testMissingLocale() {
        Element element = new Element();
        element.setName("foo");
        JsonWebElement.newElement(element, "en_US");
    }

    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = MandatoryKeys.NAME +
        JsonWebElement.ATTRIBUTE_IS_MISSING)
    public void testMissingNameInLocaleObject() {
        Locale locale = new Locale();
        locale.setLocator("//h1");
        Element element = new Element();
        element.setName("foo");
        element.setLocales(Collections.singletonList(locale));
        JsonWebElement.newElement(element, "en_US");
    }

    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = MandatoryKeys.LOCATOR +
        JsonWebElement.ATTRIBUTE_IS_MISSING)
    public void testMissingLocatorInLocaleObject() {
        Locale locale = new Locale();
        locale.setName("en_US");
        Element element = new Element();
        element.setName("foo");
        element.setLocales(Collections.singletonList(locale));
        JsonWebElement.newElement(element, "en_US");
    }

    @Test
    public void testElementRetrievalForRequestedLocaleWhenMultipleLocalesPresent() {
        Locale locale = new Locale();
        locale.setName("en_FR");
        locale.setLocator( "h2");
        Element e = newElement();
        e.getLocales().add(locale);
        JsonWebElement element = JsonWebElement.newElement(e, "en_US");
        By actual = element.getLocationStrategy("en_FR");
        Assert.assertEquals(actual.getClass(), ByIdOrName.class);
    }

    @Test
    public void testElementRetrievalWhenRequestedLocaleNotPresentWithMultipleLocales() {
        Element object = newElement();
        JsonWebElement element = JsonWebElement.newElement(object, "en_US");
        By actual = element.getLocationStrategy("en_FR");
        Assert.assertEquals(actual.getClass(), By.ByXPath.class);
    }

    @Test (dataProvider = "getTestData")
    public void testNewElementCreation(Element object, String defLocale, String queryLocale, WaitCondition waitCondition, int
        expWait, Class<By> expClass) {
        JsonWebElement element = JsonWebElement.newElement(object, defLocale);
        Assert.assertEquals(element.getName(), object.getName());
        Assert.assertEquals(element.getWait().getWaitCondition(), waitCondition);
        Assert.assertEquals(element.getWait().getDuration(), expWait);
        By actual = element.getLocationStrategy(queryLocale);
        Assert.assertEquals(actual.getClass(), expClass);
        String locator = object.getLocales().get(0).getLocator();

        // For CSS
        if (expClass.equals(By.ByCssSelector.class)) {
            locator = locator.substring(4);
        } else if (expClass.equals(By.ByClassName.class)) {
            locator = locator.substring(6);
        } else if (expClass.equals(By.ByLinkText.class)) {
            locator = locator.substring(9);
        } else if (expClass.equals(By.ByPartialLinkText.class)) {
            locator = locator.substring(16);
        } else if (expClass.equals(By.ByTagName.class)) {
            locator = locator.substring(8);
        } else if (expClass.equals(By.ByXPath.class)) {
            locator = locator.substring(6);
        }

        Assert.assertTrue(actual.toString().contains(locator));
    }

    @DataProvider
    public Object[][] getTestData() {
        WaitCondition available = DefaultWaitConditions.AVAILABLE;
        WaitCondition visible = DefaultWaitConditions.VISIBLE;
        WaitCondition clickable = DefaultWaitConditions.CLICKABLE;
        return new Object[][] {
            //basic object creation test data
            {newElement(), "en_US", "en_US", available, JsonWebElement.DEFAULT_WAIT_TIME, By.ByXPath.class},
            //checking if different xpath combinations yield proper results.
            {newElement("xpath=//h1"), "en_US", "fr_FR", available, JsonWebElement.DEFAULT_WAIT_TIME, By.ByXPath.class},
            {newElement("xpath=/h1"), "en_US", "fr_FR", available, JsonWebElement.DEFAULT_WAIT_TIME, By.ByXPath.class},
            {newElement("xpath=./h1"), "en_US", "fr_FR", available, JsonWebElement.DEFAULT_WAIT_TIME, By.ByXPath.class},
            //checking if css is parsed properly.
            {newElement("css=foo"), "en_US", "fr_FR", available, JsonWebElement.DEFAULT_WAIT_TIME, By.ByCssSelector.class},
            // checking if class is parsed properly.
            {newElement("class=foo"), "en_US", "fr_FR", available, JsonWebElement.DEFAULT_WAIT_TIME, By.ByClassName.class},
            // checking if tagName is parsed properly.
            {newElement("tagName=foo"), "en_US", "fr_FR", available, JsonWebElement.DEFAULT_WAIT_TIME, By.ByTagName.class},
            // checking if linkText is parsed properly.
            {newElement("linkText=foo"), "en_US", "fr_FR", available, JsonWebElement.DEFAULT_WAIT_TIME, By.ByLinkText.class},
            // checking if partialLinkText is parsed properly.
            {newElement("partialLinkText=foo"), "en_US", "fr_FR", available, JsonWebElement.DEFAULT_WAIT_TIME, By.ByPartialLinkText.class},
            //checking if byId/byName is parsed properly.
            {newElement("foo"), "en_US", "fr_FR", available, JsonWebElement.DEFAULT_WAIT_TIME, ByIdOrName.class},
            //checking if Until defaults to Available when its empty (or) missing
            {newElement("", 10), "en_US", "fr_FR", available, 10, By.ByXPath.class},
            {newElementWithout(WaitAttributes.UNTIL, "25"), "en_US", "en_US", available, 25, By.ByXPath.class},
            //checking if other custom values for until are parsed properly.
            {newElement(clickable.getName(), 10), "en_US", "fr_FR", clickable, 10, By.ByXPath.class},
            //checking if time defaults to default wait time if its less than zero (or) when its missing
            {newElement(clickable.getName(), 0), "en_US", "fr_FR", clickable, JsonWebElement.DEFAULT_WAIT_TIME, By.ByXPath.class},
            {newElementWithout(WaitAttributes.FOR, visible.getName()), "en_US", "en_US", visible, JsonWebElement.DEFAULT_WAIT_TIME, By.ByXPath.class}
        };
    }

    private static Element newElementWithout(String attrib, String val) {
        Element element = newElement();

        Wait wait = new Wait();
        switch (attrib) {
            case WaitAttributes.UNTIL:
                wait.setDuration(Integer.parseInt(val));
                break;
            case WaitAttributes.FOR:
                wait.setUntil(val);
        }
        element.setWait(wait);
        return element;
    }

    private static Element newElement(String until, int time) {
        Element element = newElement();
        Wait wait = new Wait();
        wait.setUntil(until);
        wait.setDuration(time);
        element.setWait(wait);
        return element;
    }

    private static Element newElement(String locator) {
        Locale l = new Locale();
        l.setName("en_US");
        l.setLocator(locator);
        Element element = new Element();
        element.setName("foo");
        List<Locale> locales = new ArrayList<>();
        locales.add(l);
        element.setLocales(locales);
        return element;
    }

    private static Element newElement() {
        return newElement("xpath=//h1");
    }

    interface MandatoryKeys {
        String NAME = "name";
        String LOCALE = "locale";
        String LOCATOR = "locator";
    }

    interface WaitAttributes {
        String UNTIL = "until";
        String FOR = "for";
    }
}
