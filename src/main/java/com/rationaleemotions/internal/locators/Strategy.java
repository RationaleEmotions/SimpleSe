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
            return Strategy.matches(locator, locatorType());
        }

        @Override
        public By getStrategy(String locator) {
            String value = Strategy.extractLocator(locator, locatorType());
            return By.xpath(value);
        }

        @Override
        public String locatorType() {
            return "xpath";
        }
    },
    /**
     * Represents the css locating strategy.
     */
    CSS {
        @Override
        public boolean typeMatches(String locator) {
            return Strategy.matches(locator, locatorType());
        }

        @Override
        public By getStrategy(String locator) {
            String value = Strategy.extractLocator(locator, locatorType());
            return By.cssSelector(value);
        }

        @Override
        public String locatorType() {
            return "css";
        }
    },
    /**
     * Represents the class locating strategy.
     */
    CLASS {
        @Override
        public boolean typeMatches(String locator) {
            return Strategy.matches(locator, locatorType());
        }

        @Override
        public By getStrategy(String locator) {
            String value = Strategy.extractLocator(locator, locatorType());
            return By.className(value);
        }

        @Override
        public String locatorType() {
            return "class";
        }
    },
    /**
     * Represents the linkText locating strategy.
     */
    LINK_TEXT {
        @Override
        public boolean typeMatches(String locator) {
            return Strategy.matches(locator, locatorType());
        }

        @Override
        public By getStrategy(String locator) {
            String value = Strategy.extractLocator(locator, locatorType());
            return By.linkText(value);
        }

        @Override
        public String locatorType() {
            return "linkText";
        }
    },
    /**
     * Represents the partialLinkText locating strategy.
     */
    PARTIAL_LINK_TEXT {
        @Override
        public boolean typeMatches(String locator) {
            return Strategy.matches(locator, locatorType());
        }

        @Override
        public By getStrategy(String locator) {
            String value = Strategy.extractLocator(locator, locatorType());
            return By.partialLinkText(value);
        }

        @Override
        public String locatorType() {
            return "partialLinkText";
        }
    },
    /**
     * Represents the tagName locating strategy.
     */
    TAG_NAME {
        @Override
        public boolean typeMatches(String locator) {
            return Strategy.matches(locator, locatorType());
        }

        @Override
        public By getStrategy(String locator) {
            String value = Strategy.extractLocator(locator, locatorType());
            return By.tagName(value);
        }

        @Override
        public String locatorType() {
            return "tagName";
        }
    },
    /**
     * Represents the Id/Name locating strategy.
     */
    ID_NAME {
        @Override
        public boolean typeMatches(String locator) {
            return Strategy.isNotNullAndEmpty(locator) &&
                    !locator.matches("(xpath|css|class|linkText|partialLinkText|tagName).*");
        }

        @Override
        public By getStrategy(String locator) {
            return new ByIdOrName(locator);
        }

        @Override
        public String locatorType() {
            return null;
        }
    };

    private static final String DEFAULT_REGEXP_PATTERN_XPATH_IDENTIFIER = "^(/|//|./|.//).*$";

    private static boolean isNotNullAndEmpty(String locator) {
        return locator != null && ! locator.trim().isEmpty();
    }

    private static boolean matches(String locator, String type) {
        return Strategy.isNotNullAndEmpty(locator) && (locator.toLowerCase().startsWith(type.toLowerCase().trim()) || locator.toLowerCase().trim().matches(DEFAULT_REGEXP_PATTERN_XPATH_IDENTIFIER));
    }

    private static String extractLocator(String locator, String type) {
        if (locator.trim().matches(DEFAULT_REGEXP_PATTERN_XPATH_IDENTIFIER)) {
            return locator;
        }
        return locator.substring(type.length() + 1);
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
