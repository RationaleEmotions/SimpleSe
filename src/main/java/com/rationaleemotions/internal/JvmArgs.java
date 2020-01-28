package com.rationaleemotions.internal;

/**
 * Represents all the supported JVM arguments
 */
public enum JvmArgs {
  /**
   * Represents the default wait time.
   */
  DEFAULT_WAIT_TIME("simplese.default.waittime", "45"),
  /**
   * Represents whether SimpleSe should default to using a default wait strategy i.e., wait for an
   * element to be visible for 45 seconds (or) not involve any wait strategy at all.
   */
  USE_DEFAULT_WAIT_STRATEGY("simplese.default.waitstrategy", "true");
  String key;
  String value;

  JvmArgs(String key, String defaultValue) {
    this.key = key;
    this.value = defaultValue;
  }

  public int asInt() {
    return Integer.parseInt(System.getProperty(key, value));
  }

  public boolean asBoolean() {
    return Boolean.parseBoolean(System.getProperty(key, value));
  }

  @Override
  public String toString() {
    return key;
  }
}
