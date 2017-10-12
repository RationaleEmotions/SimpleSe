package com.rationaleemotions.utils;

/**
 * A simple utility class for housing string related functions.
 */
public class StringUtils {
    private StringUtils() {
        //Utility class. Defeat instantiation.
    }

    /**
     * @param input - The string to be vetted.
     * @return - <code>true</code> if the given string is neither NOT <code>null</code> (nor) NOT empty (after trimming).
     */
    public static boolean isNotBlank(String input) {
        return !isBlank(input);
    }

    /**
     * @param input - The string to be vetted.
     * @return - <code>true</code> if the given string is either <code>null</code> (or) empty (after trimming).
     */
    public static boolean isBlank(String input) {
        return (input == null || input.trim().isEmpty());
    }
}
