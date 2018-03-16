package com.rationaleemotions.internal.locators;

import org.openqa.selenium.By;

/**
 * This interface represents the abilities that are to be possessed by a location strategy.
 */
public interface StrategyTraits {
    /**
     * @param locator - The locator that needs to be matched.
     * @return - <code>true</code> if the locator matches the required matching conditions for a location strategy.
     */
    boolean typeMatches(String locator);

    /**
     * @param locator - The locator that needs to be parsed.
     * @return - A {@link By} sub-variant that represents the corresponding location strategy based on the parsed
     * locator.
     */
    By getStrategy(String locator);

    /**
     * Defines a locator type
     * @return A locator type value
     */
    String locatorType();

}
