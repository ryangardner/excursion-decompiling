/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.client.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.http.client.utils.Idn;

public class JdkIdn
implements Idn {
    private final Method toUnicode;

    public JdkIdn() throws ClassNotFoundException {
        Class<?> class_ = Class.forName("java.net.IDN");
        try {
            this.toUnicode = class_.getMethod("toUnicode", String.class);
            return;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw new IllegalStateException(noSuchMethodException.getMessage(), noSuchMethodException);
        }
        catch (SecurityException securityException) {
            throw new IllegalStateException(securityException.getMessage(), securityException);
        }
    }

    @Override
    public String toUnicode(String string2) {
        try {
            return (String)this.toUnicode.invoke(null, string2);
        }
        catch (InvocationTargetException invocationTargetException) {
            Throwable throwable = invocationTargetException.getCause();
            throw new RuntimeException(throwable.getMessage(), throwable);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new IllegalStateException(illegalAccessException.getMessage(), illegalAccessException);
        }
    }
}

