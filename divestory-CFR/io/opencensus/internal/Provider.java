/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.internal;

import java.lang.reflect.Constructor;
import java.util.ServiceConfigurationError;

public final class Provider {
    private Provider() {
    }

    public static <T> T createInstance(Class<?> class_, Class<T> class_2) {
        try {
            class_2 = class_.asSubclass(class_2).getConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Provider ");
            stringBuilder.append(class_.getName());
            stringBuilder.append(" could not be instantiated.");
            throw new ServiceConfigurationError(stringBuilder.toString(), exception);
        }
        return (T)class_2;
    }
}

