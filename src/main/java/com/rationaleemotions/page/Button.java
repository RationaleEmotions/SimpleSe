package com.rationaleemotions.page;

import org.openqa.selenium.WebElement;

/**
 * A wrapper class that represents the html button elements.
 */
public final class Button extends GenericElement {

    Button(WebElement underlyingElement) {
        super(underlyingElement);
    }
}
