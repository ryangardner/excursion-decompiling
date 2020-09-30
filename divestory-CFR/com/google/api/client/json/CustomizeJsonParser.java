/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.json;

import java.lang.reflect.Field;
import java.util.Collection;

public class CustomizeJsonParser {
    public void handleUnrecognizedKey(Object object, String string2) {
    }

    public Collection<Object> newInstanceForArray(Object object, Field field) {
        return null;
    }

    public Object newInstanceForObject(Object object, Class<?> class_) {
        return null;
    }

    public boolean stopAt(Object object, String string2) {
        return false;
    }
}

