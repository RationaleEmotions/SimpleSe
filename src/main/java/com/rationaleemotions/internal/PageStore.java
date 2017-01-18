package com.rationaleemotions.internal;

import com.rationaleemotions.pojos.WebPage;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * An internal data store that is meant to be used to prevent reading a file system repeatedly for every element.
 */
public class PageStore {
    private static final Map<String, WebPage> pageStore = Maps.newConcurrentMap();

    /**
     * @param page - A {@link WebPage} object that needs to be persisted into an internal data store.
     */
    public static void addPage(WebPage page) {
        if (pageStore.containsKey(page.getName())) {
            return;
        }
        pageStore.put(page.getName(), page);
    }

    /**
     * @param pageName - The name of a page that needs to be queried.
     * @return - A {@link WebPage} object that matches the name which is being queried.
     */
    public static WebPage getPage(String pageName) {
        return pageStore.get(pageName);
    }
}
