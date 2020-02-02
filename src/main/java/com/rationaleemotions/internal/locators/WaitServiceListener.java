package com.rationaleemotions.internal.locators;

import static com.rationaleemotions.internal.locators.DefaultWaitConditions.AVAILABLE;
import static com.rationaleemotions.internal.locators.DefaultWaitConditions.CLICKABLE;
import static com.rationaleemotions.internal.locators.DefaultWaitConditions.VISIBLE;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.logging.Logger;

public enum WaitServiceListener {
    INSTANCE;

    private Map<String, WaitCondition> conditions = new HashMap<>();

    WaitServiceListener() {
        ServiceLoader<WaitCondition> listeners = ServiceLoader.load(WaitCondition.class);
        conditions.put(AVAILABLE.getName().toLowerCase(), AVAILABLE);
        conditions.put(VISIBLE.getName().toLowerCase(), VISIBLE);
        conditions.put(CLICKABLE.getName().toLowerCase(), CLICKABLE);
        for (WaitCondition condition : listeners) {
            Objects.requireNonNull(condition.getName());
            if (conditions.containsKey(condition.getName())) {
                printWiredInImplementations();
                String msg = "Found an existing implementation via [" +
                    conditions.get(condition.getName()).getClass().getName()
                    + "] for the key [" + condition.getName() + "]. Overwriting it with ["
                    + condition.getClass().getName();
                Logger.getGlobal().warning(msg);
            }
            conditions.put(condition.getName().trim().toLowerCase(), condition);
        }
    }

    public  WaitCondition parse(String name) {
        String key = (name != null ? name.trim().toLowerCase(): name);
        return conditions.getOrDefault(key, AVAILABLE);
    }

    private void printWiredInImplementations() {
        Logger.getGlobal().info("Currently wired in implementations are as below:");
        conditions.forEach((k, v) -> {
            String msg = "Implementation name : " + k + ", "
                + "Implementation class : " + v.getClass().getName();
            Logger.getGlobal().info(msg);
        });
    }
}
