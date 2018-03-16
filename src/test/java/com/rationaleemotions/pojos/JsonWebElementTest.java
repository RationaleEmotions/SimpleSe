package com.rationaleemotions.pojos;

import com.rationaleemotions.internal.locators.Until;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ByIdOrName;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class JsonWebElementTest {

    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = JsonWebElement.MandatoryKeys.NAME
        + JsonWebElement.ATTRIBUTE_IS_MISSING)
    public void testMissingName() {
        JsonObject object = new JsonObject();
        JsonWebElement.newElement(object, "en_US");
    }

    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = JsonWebElement.MandatoryKeys.LOCALE +
        JsonWebElement.ATTRIBUTE_IS_MISSING)
    public void testMissingLocale() {
        JsonObject object = new JsonObject();
        object.addProperty(JsonWebElement.MandatoryKeys.NAME, "foo");
        JsonWebElement.newElement(object, "en_US");
    }

    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = JsonWebElement.MandatoryKeys.NAME +
        JsonWebElement.ATTRIBUTE_IS_MISSING)
    public void testMissingNameInLocaleObject() {
        JsonObject localeObject = new JsonObject();
        localeObject.addProperty(JsonWebElement.MandatoryKeys.LOCATOR, "//h1");
        JsonObject object = new JsonObject();
        object.addProperty(JsonWebElement.MandatoryKeys.NAME, "foo");
        JsonArray localeArray = new JsonArray();
        localeArray.add(localeObject);
        object.add(JsonWebElement.MandatoryKeys.LOCALE, localeArray);
        JsonWebElement.newElement(object, "en_US");
    }

    @Test (expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = JsonWebElement.MandatoryKeys.LOCATOR +
        JsonWebElement.ATTRIBUTE_IS_MISSING)
    public void testMissingLocatorInLocaleObject() {
        JsonObject localeObject = new JsonObject();
        localeObject.addProperty(JsonWebElement.MandatoryKeys.NAME, "en_US");
        JsonObject object = new JsonObject();
        object.addProperty(JsonWebElement.MandatoryKeys.NAME, "foo");
        JsonArray localeArray = new JsonArray();
        localeArray.add(localeObject);
        object.add(JsonWebElement.MandatoryKeys.LOCALE, localeArray);
        JsonWebElement.newElement(object, "en_US");
    }

    @Test
    public void testElementRetrievalForRequestedLocaleWhenMultipleLocalesPresent() {
        JsonObject object = newJson();
        JsonObject localeObject = new JsonObject();
        localeObject.addProperty(JsonWebElement.MandatoryKeys.NAME, "en_FR");
        localeObject.addProperty(JsonWebElement.MandatoryKeys.LOCATOR, "h2");
        object.get(JsonWebElement.MandatoryKeys.LOCALE).getAsJsonArray().add(localeObject);
        JsonWebElement element = JsonWebElement.newElement(object, "en_US");
        By actual = element.getLocationStrategy("en_FR");
        Assert.assertEquals(actual.getClass(), ByIdOrName.class);
    }

    @Test
    public void testElementRetrievalWhenRequestedLocaleNotPresentWithMultipleLocales() {
        JsonObject object = newJson();
        JsonWebElement element = JsonWebElement.newElement(object, "en_US");
        By actual = element.getLocationStrategy("en_FR");
        Assert.assertEquals(actual.getClass(), By.ByXPath.class);
    }

    @Test (dataProvider = "getTestData")
    public void testNewElementCreation(JsonObject object, String defLocale, String queryLocale, Until expUntil, int
        expWait, Class<By> expClass) {
        JsonWebElement element = JsonWebElement.newElement(object, defLocale);
        Assert.assertEquals(element.getName(), object.get(JsonWebElement.MandatoryKeys.NAME).getAsString());
        Assert.assertEquals(element.getUntil(), expUntil);
        Assert.assertEquals(element.getWaitForSeconds(), expWait);
        By actual = element.getLocationStrategy(queryLocale);
        Assert.assertEquals(actual.getClass(), expClass);
        String locator = object.get(JsonWebElement.MandatoryKeys.LOCALE).getAsJsonArray().get(0).getAsJsonObject().get(
            JsonWebElement.MandatoryKeys.LOCATOR).getAsString();

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
        return new Object[][] {
            //basic object creation test data
            {newJson(), "en_US", "en_US", Until.Available, JsonWebElement.DEFAULT_WAIT_TIME, By.ByXPath.class},
            //checking if different xpath combinations yield proper results.
            {newJson("foo", "en_US", "xpath=//h1"), "en_US", "fr_FR", Until.Available, JsonWebElement.DEFAULT_WAIT_TIME, By.ByXPath.class},
            {newJson("foo", "en_US", "xpath=/h1"), "en_US", "fr_FR", Until.Available, JsonWebElement.DEFAULT_WAIT_TIME, By.ByXPath.class},
            {newJson("foo", "en_US", "xpath=./h1"), "en_US", "fr_FR", Until.Available, JsonWebElement.DEFAULT_WAIT_TIME, By.ByXPath.class},
            //checking if css is parsed properly.
            {newJson("foo", "en_US", "css=foo"), "en_US", "fr_FR", Until.Available, JsonWebElement.DEFAULT_WAIT_TIME, By.ByCssSelector.class},
            // checking if class is parsed properly.
            {newJson("foo", "en_US", "class=foo"), "en_US", "fr_FR", Until.Available, JsonWebElement.DEFAULT_WAIT_TIME, By.ByClassName.class},
            // checking if tagName is parsed properly.
            {newJson("foo", "en_US", "tagName=foo"), "en_US", "fr_FR", Until.Available, JsonWebElement.DEFAULT_WAIT_TIME, By.ByTagName.class},
            // checking if linkText is parsed properly.
            {newJson("foo", "en_US", "linkText=foo"), "en_US", "fr_FR", Until.Available, JsonWebElement.DEFAULT_WAIT_TIME, By.ByLinkText.class},
            // checking if partialLinkText is parsed properly.
            {newJson("foo", "en_US", "partialLinkText=foo"), "en_US", "fr_FR", Until.Available, JsonWebElement.DEFAULT_WAIT_TIME, By.ByPartialLinkText.class},
            //checking if byId/byName is parsed properly.
            {newJson("foo", "en_US", "foo"), "en_US", "fr_FR", Until.Available, JsonWebElement.DEFAULT_WAIT_TIME, ByIdOrName.class},
            //checking if Until defaults to Available when its empty (or) missing
            {newJson("", 10), "en_US", "fr_FR", Until.Available, 10, By.ByXPath.class},
            {newJsonWithout(JsonWebElement.WaitAttributes.UNTIL, "25"), "en_US", "en_US", Until.Available, 25, By.ByXPath.class},
            //checking if other custom values for until are parsed properly.
            {newJson(Until.Clickable.name(), 10), "en_US", "fr_FR", Until.Clickable, 10, By.ByXPath.class},
            //checking if time defaults to default wait time if its less than zero (or) when its missing
            {newJson(Until.Clickable.name(), 0), "en_US", "fr_FR", Until.Clickable, JsonWebElement.DEFAULT_WAIT_TIME, By.ByXPath.class},
            {newJsonWithout(JsonWebElement.WaitAttributes.FOR, Until.Visible.name()), "en_US", "en_US", Until.Visible, JsonWebElement.DEFAULT_WAIT_TIME, By.ByXPath.class}
        };
    }

    private static JsonObject newJsonWithout(String attrib, String val) {
        JsonObject json = newJson();
        JsonObject wait = new JsonObject();
        switch (attrib) {
            case JsonWebElement.WaitAttributes.UNTIL:
                wait.addProperty(JsonWebElement.WaitAttributes.FOR, Integer.parseInt(val));
                break;
            case JsonWebElement.WaitAttributes.FOR:
                wait.addProperty(JsonWebElement.WaitAttributes.UNTIL, Until.parse(val).name());
        }
        json.add(JsonWebElement.OptionalKeys.WAIT, wait);
        return json;
    }

    private static JsonObject newJson(String until, int time) {
        JsonObject json = newJson();
        json.add(JsonWebElement.OptionalKeys.WAIT, newWait(until, time));
        return json;
    }

    private static JsonObject newWait(String until, int time) {
        JsonObject object = new JsonObject();
        object.addProperty(JsonWebElement.WaitAttributes.UNTIL, until);
        object.addProperty(JsonWebElement.WaitAttributes.FOR, time);
        return object;
    }

    private static JsonObject newJson(String name, String locale, String locator) {
        JsonObject localeObject = new JsonObject();
        localeObject.addProperty(JsonWebElement.MandatoryKeys.NAME, locale);
        localeObject.addProperty(JsonWebElement.MandatoryKeys.LOCATOR, locator);

        JsonArray locales = new JsonArray();
        locales.add(localeObject);
        JsonObject element = new JsonObject();
        element.addProperty(JsonWebElement.MandatoryKeys.NAME, name);
        element.add(JsonWebElement.MandatoryKeys.LOCALE, locales);
        return element;
    }

    private static JsonObject newJson() {
        return newJson("foo", "en_US", "xpath=//h1");
    }
}
