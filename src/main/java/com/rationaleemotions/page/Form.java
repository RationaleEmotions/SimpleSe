package com.rationaleemotions.page;

import org.openqa.selenium.WebElement;

/**
 * A wrapper class that represents the html form elements.
 */
public final class Form extends GenericElement {

    Form(WebElement underlyingElement) {
        super(underlyingElement);
    }

    /**
     * Helps submit a form.
     */
    public void submit() {
        getUnderlyingElement().submit();
    }
}
