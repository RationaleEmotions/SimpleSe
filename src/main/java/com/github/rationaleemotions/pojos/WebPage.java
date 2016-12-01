package com.github.rationaleemotions.pojos;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FilenameUtils;
import com.github.rationaleemotions.internal.PageStore;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class WebPage {
    private String name;
    private String defaultLocale;
    private Map<String, JsonWebElement> elements = Maps.newConcurrentMap();

    public static WebPage getPage(String fileName) {
        File file = new File(fileName);
        Preconditions.checkArgument(file.exists(), "Cannot find file : " + file.getAbsolutePath());
        WebPage page = PageStore.getPage(FilenameUtils.getBaseName(fileName));
        if (page != null) {
            return page;
        }
        try {
            JsonParser parser = new JsonParser();
            JsonObject contents = parser.parse(new FileReader(fileName)).getAsJsonObject();
            page = new WebPage();
            page.name = contents.get("name").getAsString();
            page.defaultLocale = contents.get("defaultLocale").getAsString();
            JsonArray elements = contents.get("elements").getAsJsonArray();
            for (int i = 0; i < elements.size(); i++) {
                JsonObject object = elements.get(i).getAsJsonObject();
                JsonWebElement element = JsonWebElement.newElement(object, page.defaultLocale);
                page.elements.put(element.getName(), element);
            }
            PageStore.addPage(page);
        } catch (IOException e) {
            throw new WebPageParsingException(e);
        }
        return page;
    }

    public String getName() {
        return name;
    }

    public String getDefaultLocale() {
        return defaultLocale;
    }

    public JsonWebElement getWebElement(String name) {
        return elements.get(name);
    }

    public static class WebPageParsingException extends RuntimeException {
        public WebPageParsingException(Throwable t) {
            super(t);
        }
    }
}
