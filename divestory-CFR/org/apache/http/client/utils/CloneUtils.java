/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.client.utils;

import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CloneUtils {
    private CloneUtils() {
    }

    public static Object clone(Object object) throws CloneNotSupportedException {
        if (object == null) {
            return null;
        }
        if (!(object instanceof Cloneable)) throw new CloneNotSupportedException();
        GenericDeclaration genericDeclaration = object.getClass();
        try {
            genericDeclaration = ((Class)genericDeclaration).getMethod("clone", null);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw new NoSuchMethodError(noSuchMethodException.getMessage());
        }
        try {
            return ((Method)genericDeclaration).invoke(object, null);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new IllegalAccessError(illegalAccessException.getMessage());
        }
        catch (InvocationTargetException invocationTargetException) {
            Throwable throwable = invocationTargetException.getCause();
            if (!(throwable instanceof CloneNotSupportedException)) throw new Error("Unexpected exception", throwable);
            throw (CloneNotSupportedException)throwable;
        }
    }
}

