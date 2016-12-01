package com.github.rationaleemotions.pojos;

import com.github.rationaleemotions.internal.locators.Until;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;

import java.util.List;
import java.util.Map;

import static com.github.rationaleemotions.pojos.JsonWebElement.MandatoryKeys.LOCALE;
import static com.github.rationaleemotions.pojos.JsonWebElement.MandatoryKeys.NAME;
import static com.github.rationaleemotions.pojos.JsonWebElement.OptionalKeys.WAIT;
import static com.github.rationaleemotions.pojos.JsonWebElement.WaitAttributes.FOR;
import static com.github.rationaleemotions.pojos.JsonWebElement.WaitAttributes.UNTIL;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

public class JsonWebElement {
    private static final String defaultValue = System.getProperty("default.wait.time", "45");
    static final int DEFAULT_WAIT_TIME = Integer.parseInt(defaultValue);
    static final String ATTRIBUTE_IS_MISSING = " attribute is missing.";
    private String name;
    private Map<String, By> locationStrategy = Maps.newHashMap();
    private Until until;
    private int forSeconds;
    private String defaultLocale;

    String getName() {
        return name;
    }

    public Until getUntil() {
        return until;
    }

    public int getWaitForSeconds() {
        return forSeconds;
    }

    public By getLocationStrategy(String whichLocale) {
        checkArgument(StringUtils.isNotBlank(whichLocale), "Querying locale cannot be empty (or) null.");
        checkState(locationStrategy.containsKey(defaultLocale), "Un-recognized default locale [" + defaultLocale + "]"
            + " provided.");
        if (locationStrategy.containsKey(whichLocale)) {
            return locationStrategy.get(whichLocale);
        }
        return locationStrategy.get(defaultLocale);
    }

    static JsonWebElement newElement(JsonObject json, String defaultLocale) {
        ensureMandatoryKeysArePresent(json);
        JsonWebElement element = new JsonWebElement();
        element.name = json.get(NAME).getAsString();
        List<LocaleDefinition> localeDefinitions = LocaleDefinition.newDefinition(json.get(LOCALE).getAsJsonArray());
        for (LocaleDefinition localeDefinition : localeDefinitions) {
            element.locationStrategy.put(localeDefinition.getLocale(), localeDefinition.getLocationStrategy());
        }
        JsonElement waitNode = json.get(WAIT);
        if (waitNode == null) {
            element.until = Until.Available;
            element.forSeconds = DEFAULT_WAIT_TIME;
        } else {
            JsonObject waitObject = waitNode.getAsJsonObject();
            Until until = Until.Available;
            if (waitObject.has(UNTIL)) {
                until = Until.parse(waitNode.getAsJsonObject().get(UNTIL).getAsString());
            }
            element.until = until;
            int wait = DEFAULT_WAIT_TIME;
            if (waitObject.has(FOR) && waitObject.get(FOR).getAsInt() > 0) {
                wait = waitObject.get(FOR).getAsInt();
            }
            element.forSeconds = wait;
        }
        element.defaultLocale = defaultLocale;
        return element;
    }

    private static void ensureMandatoryKeysArePresent(JsonObject json) {
        checkArgument(json.has(NAME), NAME + ATTRIBUTE_IS_MISSING);
        checkArgument(json.has(LOCALE), LOCALE + ATTRIBUTE_IS_MISSING);
        //        checkArgument(json.has(LOCATOR), LOCATOR + ATTRIBUTE_IS_MISSING);
    }

    interface MandatoryKeys {
        String NAME = "name";
        String LOCALE = "locale";
        String LOCATOR = "locator";
    }


    interface OptionalKeys {
        String WAIT = "wait";
    }


    interface WaitAttributes {
        String UNTIL = "until";
        String FOR = "for";
    }
}
