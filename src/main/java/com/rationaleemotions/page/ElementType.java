package com.rationaleemotions.page;

/**
 * Represents the capability of a {@link GenericElement} or its known sub-classes to identify the
 * type of themselves.
 */
public interface ElementType {

  /**
   * @return - A {@link WebElementType} that represents the type of the element.
   */
  WebElementType getType();

}
