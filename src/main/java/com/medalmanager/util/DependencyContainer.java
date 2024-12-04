package com.medalmanager.util;

import java.util.HashMap;
import java.util.Map;

public class DependencyContainer {
    private static final Map<Class<?>, Object> container = new HashMap<>();

    private DependencyContainer() {
    }

    public static <T> void register(Class<T> type, T implementation) {
        container.put(type, implementation);
    }

    @SuppressWarnings("unchecked")
    public static <T> T resolve(Class<T> type) {
        Object implementation = container.get(type);
        if (implementation == null) {
            throw new IllegalStateException("No implementation registered for " + type.getName());
        }
        return (T) implementation;
    }

    public static void clear() {
        container.clear();
    }
}