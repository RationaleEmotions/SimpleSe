package com.rationaleemotions.pojos;

import com.google.common.collect.Maps;
import com.rationaleemotions.internal.JvmArgs;
import com.rationaleemotions.internal.parser.pojos.Element;
import com.rationaleemotions.internal.parser.pojos.Wait;
import com.rationaleemotions.page.WebElementType;
import com.rationaleemotions.utils.StringUtils;
import org.openqa.selenium.By;

import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

public final class JsonWebElement {
    static final int DEFAULT_WAIT_TIME = JvmArgs.DEFAULT_WAIT_TIME.asInt();
    static final String ATTRIBUTE_IS_MISSING = " attribute is missing.";
    private String name;
    private Map<String, By> locationStrategy = Maps.newHashMap();
    private String defaultLocale;
    private WebElementType type;
    private Wait wait;

    private JsonWebElement() {
        //Defeat instantiation
    }

    public Wait getWait() {
        return wait;
    }

    public WebElementType getType() {
        return type;
    }

    String getName() {
        return name;
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

    static JsonWebElement newElement(Element element, String defaultLocale) {
        element.validate();
        JsonWebElement jsonWebElement = new JsonWebElement();
        jsonWebElement.name = element.getName();
        List<LocaleDefinition> definitions = LocaleDefinition.newDefinition(element.getLocales());
        definitions.forEach(localeDefinition ->
                jsonWebElement.locationStrategy.put(localeDefinition.getLocale(), localeDefinition.getLocationStrategy()));
        jsonWebElement.defaultLocale = defaultLocale;
        if (element.getWait() != null && element.getWait().isValid()) {
            jsonWebElement.wait = element.getWait();
        }
        jsonWebElement.type = element.getType();
        return jsonWebElement;
    }

}
