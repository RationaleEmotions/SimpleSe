package com.rationaleemotions.pojos;

import com.rationaleemotions.internal.locators.Strategy;
import com.rationaleemotions.internal.locators.StrategyTraits;
import com.google.common.collect.Lists;
import com.rationaleemotions.internal.parser.pojos.Locale;
import org.openqa.selenium.By;

import java.util.List;

public class LocaleDefinition {
    private String locale;
    private By locationStrategy;

    private LocaleDefinition() {
    }

    static List<LocaleDefinition> newDefinition(List<Locale> locales) {
        List<LocaleDefinition> definitions = Lists.newArrayList();
        locales.forEach(each -> {
            each.validate();
            LocaleDefinition definition = new LocaleDefinition();
            StrategyTraits traits = Strategy.identifyStrategy(each.getLocator());
            definition.locale = each.getName();
            definition.locationStrategy = traits.getStrategy(each.getLocator());
            definitions.add(definition);
        });
        return definitions;
    }

    By getLocationStrategy() {
        return locationStrategy;
    }

    String getLocale() {
        return locale;
    }
}
