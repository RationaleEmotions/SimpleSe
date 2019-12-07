package com.rationaleemotions.page;

import static com.rationaleemotions.page.WebElementType.RADIO;

import org.openqa.selenium.WebElement;

/**
 * A wrapper class that represents the html radio button elements.
 */
public final class RadioButton extends GenericElement {

    RadioButton(WebElement underlyingElement) {
        super(underlyingElement);
    }

    @Override
    public WebElementType getType() {
        return RADIO;
    }
}
