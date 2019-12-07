package com.rationaleemotions.internal.parser;

import com.google.gson.Gson;
import com.rationaleemotions.internal.parser.pojos.PageElement;
import java.io.FileNotFoundException;
import java.io.FileReader;

public final class PageParser {

  private PageParser() {
    //defeat instantiation
  }

  public static PageElement parsePage(String fileName) throws FileNotFoundException {
    return new Gson().fromJson(new FileReader(fileName), PageElement.class);
  }
}
