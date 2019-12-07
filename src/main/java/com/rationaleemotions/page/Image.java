package com.rationaleemotions.page;

import static com.rationaleemotions.page.WebElementType.IMAGE;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

/**
 * A wrapper class that represents the html image elements.
 */
public final class Image extends GenericElement {

    Image(WebElement underlyingElement) {
        super(underlyingElement);
    }

    /**
     * @return - A {@link Dimension} object that represent's the current image's size.
     */
    public Dimension getSize() {
        return getUnderlyingElement().getSize();
    }

    @Override
    public WebElementType getType() {
        return IMAGE;
    }
}
