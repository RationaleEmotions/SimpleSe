package com.rationaleemotions.internal.parser.pojos;

import com.google.common.base.Preconditions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rationaleemotions.internal.JvmArgs;
import com.rationaleemotions.page.WebElementType;
import com.rationaleemotions.utils.StringUtils;
import java.util.LinkedList;
import java.util.List;

public class Element {
  private static final String ATTRIBUTE_IS_MISSING = " attribute is missing.";

  @SerializedName("name")
  @Expose
  private String name;

  @SerializedName("type")
  @Expose
  private String type;

  @SerializedName("locale")
  @Expose
  private List<Locale> locales = new LinkedList<>();

  @SerializedName("wait")
  @Expose
  private Wait wait;

  @SerializedName("list")
  @Expose
  private boolean isList = false;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public WebElementType getType() {
    return WebElementType.identifyType(type);
  }

  public void setType(String type) {
    this.type = type;
  }

  public List<Locale> getLocales() {
    return locales;
  }

  public void setLocales(List<Locale> locales) {
    this.locales = locales;
  }

  public Wait getWait() {
    if (JvmArgs.USE_DEFAULT_WAIT_STRATEGY.asBoolean() && wait == null) {
      return Wait.defaultInstance();
    }
    return wait;
  }

  public void setWait(Wait wait) {
    this.wait = wait;
  }

  public boolean isList() {
    return isList;
  }

  public void setList(boolean list) {
    isList = list;
  }

  public void validate() {
    Preconditions.checkArgument(StringUtils.isNotBlank(name), "name" + ATTRIBUTE_IS_MISSING);
    Preconditions.checkArgument(!locales.isEmpty(), "locale" + ATTRIBUTE_IS_MISSING);
  }

  @Override
  public String toString() {
    return "Element{" +
        "name='" + name + '\'' +
        ", type='" + type + '\'' +
        ", locales=" + locales +
        ", wait=" + wait +
        ", isList=" + isList +
        '}';
  }
}
