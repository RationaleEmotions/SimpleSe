package com.rationaleemotions.internal.locators;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * This interface represents the custom wait implementations that downstream consumers can use
 * to wire in
 */
public interface WaitCondition {

    /**
     *
     * @return - The name to be used to symbolically represent this implementation. This is the same
     * name that you will be providing in your Json file as wait strategy.
     */
    String getName();

    /**
     * @param by - The {@link By} that represents the location strategy.
     * @return - The {@link ExpectedCondition} to be used.
     */
    ExpectedCondition<WebElement> element(By by);

    /**
     * @param by - The {@link By} that represents the location strategy.
     * @return - The {@link ExpectedCondition} to be used.
     */
    ExpectedCondition<List<WebElement>> elements(By by);
}
