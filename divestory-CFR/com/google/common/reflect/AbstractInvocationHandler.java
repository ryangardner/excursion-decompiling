/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class AbstractInvocationHandler
implements InvocationHandler {
    private static final Object[] NO_ARGS = new Object[0];

    private static boolean isProxyOfSameInterfaces(Object object, Class<?> class_) {
        if (class_.isInstance(object)) return true;
        if (!Proxy.isProxyClass(object.getClass())) return false;
        if (!Arrays.equals(object.getClass().getInterfaces(), class_.getInterfaces())) return false;
        return true;
    }

    public boolean equals(Object object) {
        return super.equals(object);
    }

    protected abstract Object handleInvocation(Object var1, Method var2, Object[] var3) throws Throwable;

    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public final Object invoke(Object object, Method object2, @NullableDecl Object[] arrobject) throws Throwable {
        Object[] arrobject2 = arrobject;
        if (arrobject == null) {
            arrobject2 = NO_ARGS;
        }
        if (arrobject2.length == 0 && ((Method)object2).getName().equals("hashCode")) {
            return this.hashCode();
        }
        int n = arrobject2.length;
        boolean bl = true;
        if (n == 1 && ((Method)object2).getName().equals("equals") && ((Method)object2).getParameterTypes()[0] == Object.class) {
            object2 = arrobject2[0];
            if (object2 == null) {
                return false;
            }
            if (object == object2) {
                return true;
            }
            if (AbstractInvocationHandler.isProxyOfSameInterfaces(object2, object.getClass()) && this.equals(Proxy.getInvocationHandler(object2))) {
                return bl;
            }
            bl = false;
            return bl;
        }
        if (arrobject2.length != 0) return this.handleInvocation(object, (Method)object2, arrobject2);
        if (!((Method)object2).getName().equals("toString")) return this.handleInvocation(object, (Method)object2, arrobject2);
        return this.toString();
    }

    public String toString() {
        return super.toString();
    }
}

