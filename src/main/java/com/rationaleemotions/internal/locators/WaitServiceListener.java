package com.rationaleemotions.internal.locators;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class WaitServiceListener {

    private static List<WaitCondition> conditions;

    private static List<WaitCondition> getConditions() {
        if (conditions == null) {
            lazyLoad();
        }
        return conditions;
    }

    public static WaitCondition parse(String name) {
        return getConditions().stream()
            .filter(e-> e.getName().equalsIgnoreCase(name))
            .findFirst()
            .orElse(DefaultWaitConditions.AVAILABLE);
    }

    private static synchronized void lazyLoad() {
        ServiceLoader<WaitCondition> listeners = ServiceLoader.load(WaitCondition.class);
        conditions = new ArrayList<>();
        conditions.add(DefaultWaitConditions.AVAILABLE);
        conditions.add(DefaultWaitConditions.VISIBLE);
        conditions.add(DefaultWaitConditions.CLICKABLE);
        for (WaitCondition condition : listeners) {
            boolean exists = conditions.stream()
                .anyMatch(e -> e.getName().equals(condition.getName()));
            if (exists) {
                String message = condition.getName() + " condition is already added";
                throw new RuntimeException(message);
            } else {
                conditions.add(condition);
            }
        }
    }
}
