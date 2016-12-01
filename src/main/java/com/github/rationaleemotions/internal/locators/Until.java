package com.github.rationaleemotions.internal.locators;

/**
 * This enum represents the different {@link org.openqa.selenium.support.ui.ExpectedCondition} variants that can be
 * applied to an element while it's being queried from the DOM.
 */
public enum Until {
    /**
     * Equivalent to one of the following:
     * <ul>
     * <li>{@link org.openqa.selenium.support.ui.ExpectedConditions#presenceOfElementLocated}</li>
     * <li>{@link org.openqa.selenium.support.ui.ExpectedConditions#presenceOfAllElementsLocatedBy}</li>
     * </ul>
     */
    Available,
    /**
     * Equivalent to one of the following:
     * <ul>
     * <li>{@link org.openqa.selenium.support.ui.ExpectedConditions#visibilityOfElementLocated}</li>
     * <li>{@link org.openqa.selenium.support.ui.ExpectedConditions#visibilityOfAllElementsLocatedBy}</li>
     * </ul>
     */
    Visible,
    /**
     * Equivalent to the following:
     * <ul>
     * <li>{@link org.openqa.selenium.support.ui.ExpectedConditions#elementToBeClickable}</li>
     * </ul>
     */
    Clickable;

    /**
     * A utility to parse raw text into one of the afore-mentioned
     * {@link org.openqa.selenium.support.ui.ExpectedConditions} variants.
     *
     * @param raw - The raw text.
     * @return - A {@link Until} object.
     */
    public static Until parse(String raw) {
        Until value = Available;
        if (raw == null || raw.trim().isEmpty()) {
            return value;
        }
        for (Until each : values()) {
            if (each.name().equalsIgnoreCase(raw.trim())) {
                value = each;
                break;
            }
        }
        return value;
    }
}
