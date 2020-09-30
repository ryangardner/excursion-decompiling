/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.reflect;

import com.google.common.base.Preconditions;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public final class Reflection {
    private Reflection() {
    }

    public static String getPackageName(Class<?> class_) {
        return Reflection.getPackageName(class_.getName());
    }

    public static String getPackageName(String string2) {
        int n = string2.lastIndexOf(46);
        if (n >= 0) return string2.substring(0, n);
        return "";
    }

    public static void initialize(Class<?> ... arrclass) {
        int n = arrclass.length;
        int n2 = 0;
        while (n2 < n) {
            Class<?> class_ = arrclass[n2];
            try {
                Class.forName(class_.getName(), true, class_.getClassLoader());
                ++n2;
            }
            catch (ClassNotFoundException classNotFoundException) {
                throw new AssertionError(classNotFoundException);
            }
        }
    }

    public static <T> T newProxy(Class<T> class_, InvocationHandler invocationHandler) {
        Preconditions.checkNotNull(invocationHandler);
        Preconditions.checkArgument(class_.isInterface(), "%s is not an interface", class_);
        return class_.cast(Proxy.newProxyInstance(class_.getClassLoader(), new Class[]{class_}, invocationHandler));
    }
}

