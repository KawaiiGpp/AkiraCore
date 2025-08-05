package com.akira.core.utils.base;

import org.apache.commons.lang3.Validate;

import java.util.HashSet;
import java.util.Set;

public abstract class Manager<T> {
    protected final Set<T> elements = new HashSet<>();

    public void register(T element) {
        Validate.notNull(element);
        Validate.isTrue(!elements.contains(element), "Element already registered.");
        elements.add(element);
    }

    protected void unregister(T element) {
        Validate.notNull(element);
        Validate.isTrue(elements.contains(element), "Element not registered.");
        elements.remove(element);
    }

    protected Set<T> copySet() {
        return new HashSet<>(elements);
    }

    protected abstract T fromString(String string);
}
