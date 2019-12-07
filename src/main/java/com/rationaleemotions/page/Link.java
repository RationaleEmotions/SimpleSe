package com.rationaleemotions.page;

import static com.rationaleemotions.page.WebElementType.LINK;

import org.openqa.selenium.WebElement;

/**
 * A wrapper class that represents the html link elements.
 */
public final class Link extends GenericElement {

    Link(WebElement underlyingElement) {
        super(underlyingElement);
    }

    @Override
    public WebElementType getType() {
        return LINK;
    }
}
