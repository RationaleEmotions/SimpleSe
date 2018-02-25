package com.rationaleemotions.internal.locators;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ByIdOrName;

/**
 * This enum houses all the locating strategies and its associated identifying and parsing logic.
 */
public enum Strategy implements StrategyTraits {
    /**
     * Represents the xPath locating strategy.
     */
    XPATH {
        @Override
        public boolean typeMatches(String locator) {
            return Strategy.isNotNullAndEmpty(locator) &&
                (locator.startsWith("/") || locator.startsWith("//") || locator.startsWith("."));
        }

        @Override
        public By getStrategy(String locator) {
            return By.xpath(locator);
        }
    },
    /**
     * Represents the css locating strategy.
     */
    CSS {
        @Override
        public boolean typeMatches(String locator) {
            return Strategy.isNotNullAndEmpty(locator) && locator.startsWith("css");
        }

        @Override
        public By getStrategy(String locator) {
            return By.cssSelector(locator.substring(4));
        }
    },
    /**
     * Represents the Id/Name locating strategy.
     */
    ID_NAME {
        @Override
        public boolean typeMatches(String locator) {
            return Strategy.isNotNullAndEmpty(locator) && ((! XPATH.typeMatches(locator)) && (! CSS.typeMatches
                (locator)));
        }

        @Override
        public By getStrategy(String locator) {
            return new ByIdOrName(locator);
        }
    };

    private static boolean isNotNullAndEmpty(String locator) {
        return locator != null && ! locator.trim().isEmpty();
    }

    /**
     * A utility method which helps identify the corresponding location strategy based on the input locator.
     *
     * @param locator - The locator that needs to be parsed.
     * @return - A {@link Strategy} object that represents the location strategy
     * and its associated identifying and parsing logic.
     */
    public static Strategy identifyStrategy(String locator) {
        for (Strategy value : values()) {
            if (value.typeMatches(locator)) {
                return value;
            }
        }
        throw new IllegalStateException("Encountered an unparseable locator : " + locator);
    }
}
