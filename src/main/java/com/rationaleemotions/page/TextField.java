package com.rationaleemotions.page;

import static com.rationaleemotions.page.WebElementType.TEXTFIELD;

import org.openqa.selenium.WebElement;

/**
 * A wrapper class that represents the html textfield elements.
 */
public final class TextField extends GenericElement {

    TextField(WebElement underlyingElement) {
        super(underlyingElement);
    }

    /**
     * Helps clear the contents of the current text field.
     */
    public void clear() {
        getUnderlyingElement().clear();
    }

    /**
     * @return - <code>true</code> if the current text field is editable.
     */
    public boolean canType() {
        return getUnderlyingElement().isEnabled();
    }

    /**
     * @return - returns the contents of the text field as a string.
     */
    public String getText() {
        String text = getUnderlyingElement().getText();
        if (text.isEmpty()) {
            text = getUnderlyingElement().getAttribute("value");
        }
        return text;
    }

    /**
     * @param text - The string that needs to be typed on the text field.
     */
    public void type(CharSequence text) {
        getUnderlyingElement().sendKeys(text);
    }

    @Override
    public WebElementType getType() {
        return TEXTFIELD;
    }
}
