package com.rationaleemotions.pojos;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.rationaleemotions.internal.PageStore;
import com.rationaleemotions.internal.parser.PageParser;
import com.rationaleemotions.internal.parser.pojos.Element;
import com.rationaleemotions.internal.parser.pojos.PageElement;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public final class WebPage {
    private String name;
    private String defaultLocale;
    private Map<String, JsonWebElement> elements = Maps.newConcurrentMap();

    private WebPage() {
        //Defeat instantiation.
    }

    public static WebPage getPage(String fileName) {
        File file = new File(fileName);
        Preconditions.checkArgument(file.exists(), "Cannot find file : " + file.getAbsolutePath());
        WebPage page = PageStore.getPage(FilenameUtils.getBaseName(fileName));
        if (page != null) {
            return page;
        }
        try {
            PageElement pageElement = PageParser.parsePage(fileName);
            page = new WebPage();
            page.name = pageElement.getName();
            page.defaultLocale = pageElement.getDefaultLocale();
            for (Element each : pageElement.getElements()) {
                JsonWebElement element = JsonWebElement.newElement(each, page.defaultLocale);
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
