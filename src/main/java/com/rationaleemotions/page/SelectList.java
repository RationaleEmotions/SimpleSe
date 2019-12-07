package com.rationaleemotions.page;

import static com.rationaleemotions.page.WebElementType.SELECT;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * A wrapper class that represents the html select list elements.
 */
public final class SelectList extends GenericElement {

    SelectList(WebElement underlyingElement) {
        super(underlyingElement);
    }

    /**
     * @param text - the text based on which an option has to be selected in the select list.
     */
    public void selectByText(String text) {
        constructSelectElement().selectByVisibleText(text);
    }

    /**
     * @param text - the text based on which an option has to be de-selected in the select list.
     */
    public void deselectByText(String text) {
        constructSelectElement().deselectByVisibleText(text);
    }

    /**
     * @param value - the option's value based on which an option has to be selected in the select list.
     */
    public void selectByValue(String value) {
        constructSelectElement().selectByValue(value);
    }

    /**
     * @param value - the option's value based on which an option has to be de-selected in the select list.
     */
    public void deselectByValue(String value) {
        constructSelectElement().deselectByValue(value);
    }

    /**
     * @param index - the option's index based on which an option has to be selected in the select list.
     */
    public void selectByIndex(int index) {
        constructSelectElement().selectByIndex(index);
    }

    /**
     * @param index - the option's index based on which an option has to be de-selected in the select list.
     */
    public void deselectByIndex(int index) {
        constructSelectElement().deselectByIndex(index);
    }

    /**
     * Helps select all the options in the select list.
     */
    public void selectAllOptions() {
        for (WebElement option : constructSelectElement().getOptions()) {
            if (! option.isSelected()) {
                option.click();
            }
        }
    }

    /**
     * Helps de-select all the options in the select list.
     */
    public void deselectAllOptions() {
        constructSelectElement().deselectAll();
    }

    /**
     * @return - a list of {@link WebElement} that represents all the options in the selectlist.
     */
    public List<WebElement> allOptions() {
        return constructSelectElement().getOptions();
    }

    /**
     * @return - The text of the selected option in the select list.
     */
    public String getTextOfSelectedOption() {
        WebElement element = selectedOption();
        if (element == null) {
            return "";
        }
        return element.getText();
    }

    /**
     * @return - The value of the selected option in the select list.
     */
    public String getValueOfSelectedOption() {
        WebElement element = selectedOption();
        if (element == null) {
            return "";
        }
        return element.getAttribute("value");
    }

    @Override
    public WebElementType getType() {
        return SELECT;
    }

    private Select constructSelectElement() {
        return new Select(getUnderlyingElement());
    }

    private WebElement selectedOption() {
        for (WebElement option : constructSelectElement().getOptions()) {
            if (option.isSelected()) {
                return option;
            }
        }
        return null;
    }
}
