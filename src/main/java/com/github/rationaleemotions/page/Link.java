package com.github.rationaleemotions.page;

import org.openqa.selenium.WebElement;

/**
 * A wrapper class that represents the html link elements.
 */
public final class Link extends GenericElement {

    Link(WebElement underlyingElement) {
        super(underlyingElement);
    }
}
