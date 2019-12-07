package com.rationaleemotions.page;

/**
 * Represents the types of webelements that are supported by this library.
 */
public enum WebElementType {

  /**
   * Represents a button.
   */
  BUTTON("button"),
  /**
   * Represents a checkbox
   */
  CHECKBOX("checkbox"),
  /**
   * Represents a form element.
   */
  FORM("form"),
  /**
   * Represents any other element that is not mentioned here (for e.g., div/td/tr)
   */
  GENERIC("generic"),
  /**
   * Represents an image
   */
  IMAGE("image"),
  /**
   * Represents a label
   */
  LABEL("label"),
  /**
   * Represents a link
   */
  LINK("link"),
  /**
   * Represents a radio button
   */
  RADIO("radio"),
  /**
   * Represents a select list
   */
  SELECT("select"),
  /**
   * Represents a textbox
   */
  TEXTFIELD("text");

  WebElementType(String type) {
    this.type = type;
  }

  private String type;

  @Override
  public String toString() {
    return type;
  }

  /**
   * A factory method that helps with parsing a type into a recognized {@link WebElementType}
   *
   * @param type - The raw text that needs to be parsed.
   * @return - A {@link WebElementType} that represents the recognized type. If an unrecognized
   * value is encountered (or) a null value (or) an empty string the type gets identified as {@link
   * WebElementType#GENERIC}
   */
  public static WebElementType identifyType(String type) {
    if (type == null || type.trim().isEmpty()) {
      return GENERIC;
    }
    for (WebElementType each : WebElementType.values()) {
      if (each.type.equalsIgnoreCase(type)) {
        return each;
      }
    }
    return GENERIC;
  }
}
