/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.util;

import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;

public final class ExceptionUtils {
    private static final Method INIT_CAUSE_METHOD = ExceptionUtils.getInitCauseMethod();
    static /* synthetic */ Class class$java$lang$Throwable;

    private ExceptionUtils() {
    }

    static /* synthetic */ Class class$(String object) {
        try {
            return Class.forName((String)object);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError(classNotFoundException.getMessage());
        }
    }

    private static Method getInitCauseMethod() {
        try {
            Class class_;
            GenericDeclaration genericDeclaration = class$java$lang$Throwable;
            if (genericDeclaration == null) {
                class$java$lang$Throwable = genericDeclaration = ExceptionUtils.class$("java.lang.Throwable");
            } else {
                genericDeclaration = class$java$lang$Throwable;
            }
            if (class$java$lang$Throwable == null) {
                class$java$lang$Throwable = class_ = ExceptionUtils.class$("java.lang.Throwable");
                return class_.getMethod("initCause", new Class[]{genericDeclaration});
            } else {
                class_ = class$java$lang$Throwable;
            }
            return class_.getMethod("initCause", new Class[]{genericDeclaration});
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return null;
        }
    }

    public static void initCause(Throwable throwable, Throwable throwable2) {
        Method method = INIT_CAUSE_METHOD;
        if (method == null) return;
        try {
            method.invoke(throwable, throwable2);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }
}

