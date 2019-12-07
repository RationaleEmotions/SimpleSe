package com.rationaleemotions.page;

import static com.rationaleemotions.page.WebElementType.GENERIC;

import org.openqa.selenium.WebElement;

/**
 * A wrapper class that represents a generic/custom html elements.
 */
public class GenericElement implements ElementType {
    private WebElement underlyingElement;

    GenericElement(WebElement underlyingElement) {
        this.underlyingElement = underlyingElement;
    }

    /**
     * @return - The underlying actual Selenium's {@link WebElement}
     */
    public final WebElement getUnderlyingElement() {
        return underlyingElement;
    }

    /**
     * @return - <code>true</code> if the current element is enabled.
     */
    public final boolean isEnabled() {
        return getUnderlyingElement().isEnabled();
    }

    /**
     * @return - <code>true</code> if the current element is displayed.
     */
    public final boolean isDisplayed() {
        return getUnderlyingElement().isDisplayed();
    }

    /**
     * @return - <code>true</code> if the current element is selected.
     */
    public final boolean isSelected() {
        return getUnderlyingElement().isSelected();
    }

    /**
     * Helps click the current element.
     */
    public final void click() {
        getUnderlyingElement().click();
    }

    public WebElementType getType() {
        return GENERIC;
    }
}
