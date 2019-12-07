package com.rationaleemotions.internal.parser.pojos;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rationaleemotions.utils.StringUtils;

public class Locale {

  private static final String ATTRIBUTE_IS_MISSING = " attribute is missing.";

  @SerializedName("name")
  @Expose
  private String name;

  @SerializedName("locator")
  @Expose
  private String locator;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLocator() {
    return locator;
  }

  public void setLocator(String locator) {
    this.locator = locator;
  }

  public void validate() {
    checkArgument(StringUtils.isNotBlank(name), "name" + ATTRIBUTE_IS_MISSING);
    checkArgument(StringUtils.isNotBlank(locator), "locator" + ATTRIBUTE_IS_MISSING);
  }


}
