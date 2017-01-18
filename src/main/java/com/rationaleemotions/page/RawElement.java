package com.rationaleemotions.page;

import com.rationaleemotions.pojos.JsonWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;

final class RawElement {
    private JsonWebElement jsonWebElement;
    private String locale;
    private SearchContext context;

    RawElement(SearchContext context, JsonWebElement jsonWebElement, String locale) {
        this.context = context;
        this.jsonWebElement = jsonWebElement;
        this.locale = locale;
    }

    final WebElement getWebElement() {
        By by = jsonWebElement.getLocationStrategy(locale);
        return context.findElement(by);
    }

    final List<WebElement> getWebElements() {
        By by = jsonWebElement.getLocationStrategy(locale);
        return context.findElements(by);
    }
}
