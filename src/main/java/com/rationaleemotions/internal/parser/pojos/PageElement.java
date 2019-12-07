package com.rationaleemotions.internal.parser.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PageElement {

  @SerializedName("name")
  @Expose
  private String name;

  @SerializedName("defaultLocale")
  @Expose
  private String defaultLocale;

  @SerializedName("elements")
  @Expose
  private List<Element> elements;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDefaultLocale() {
    return defaultLocale;
  }

  public void setDefaultLocale(String defaultLocale) {
    this.defaultLocale = defaultLocale;
  }

  public List<Element> getElements() {
    return elements;
  }

  public void setElements(List<Element> elements) {
    this.elements = elements;
  }
}
