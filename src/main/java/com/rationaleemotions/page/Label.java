package com.rationaleemotions.page;

import org.openqa.selenium.WebElement;

/**
 * A wrapper class that represents the html label elements.
 */
public class Label extends GenericElement {

    Label(WebElement underlyingElement) {
        super(underlyingElement);
    }

    /**
     * @return - A string that represent's the current label's text.
     */
    public String getText() {
        return getUnderlyingElement().getText();
    }
}
