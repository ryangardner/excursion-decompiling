/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.Platform;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class ObjectArrays {
    private ObjectArrays() {
    }

    static Object checkElementNotNull(Object object, int n) {
        if (object != null) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("at index ");
        ((StringBuilder)object).append(n);
        throw new NullPointerException(((StringBuilder)object).toString());
    }

    static Object[] checkElementsNotNull(Object ... arrobject) {
        return ObjectArrays.checkElementsNotNull(arrobject, arrobject.length);
    }

    static Object[] checkElementsNotNull(Object[] arrobject, int n) {
        int n2 = 0;
        while (n2 < n) {
            ObjectArrays.checkElementNotNull(arrobject[n2], n2);
            ++n2;
        }
        return arrobject;
    }

    public static <T> T[] concat(@NullableDecl T t, T[] arrT) {
        T[] arrT2 = ObjectArrays.newArray(arrT, arrT.length + 1);
        arrT2[0] = t;
        System.arraycopy(arrT, 0, arrT2, 1, arrT.length);
        return arrT2;
    }

    public static <T> T[] concat(T[] arrT, @NullableDecl T t) {
        T[] arrT2 = Arrays.copyOf(arrT, arrT.length + 1);
        arrT2[arrT.length] = t;
        return arrT2;
    }

    public static <T> T[] concat(T[] arrT, T[] arrT2, Class<T> arrT3) {
        arrT3 = ObjectArrays.newArray(arrT3, arrT.length + arrT2.length);
        System.arraycopy(arrT, 0, arrT3, 0, arrT.length);
        System.arraycopy(arrT2, 0, arrT3, arrT.length, arrT2.length);
        return arrT3;
    }

    static Object[] copyAsObjectArray(Object[] arrobject, int n, int n2) {
        Preconditions.checkPositionIndexes(n, n + n2, arrobject.length);
        if (n2 == 0) {
            return new Object[0];
        }
        Object[] arrobject2 = new Object[n2];
        System.arraycopy(arrobject, n, arrobject2, 0, n2);
        return arrobject2;
    }

    private static Object[] fillArray(Iterable<?> object, Object[] arrobject) {
        object = object.iterator();
        int n = 0;
        while (object.hasNext()) {
            arrobject[n] = object.next();
            ++n;
        }
        return arrobject;
    }

    public static <T> T[] newArray(Class<T> class_, int n) {
        return (Object[])Array.newInstance(class_, n);
    }

    public static <T> T[] newArray(T[] arrT, int n) {
        return Platform.newArray(arrT, n);
    }

    static void swap(Object[] arrobject, int n, int n2) {
        Object object = arrobject[n];
        arrobject[n] = arrobject[n2];
        arrobject[n2] = object;
    }

    static Object[] toArrayImpl(Collection<?> collection) {
        return ObjectArrays.fillArray(collection, new Object[collection.size()]);
    }

    static <T> T[] toArrayImpl(Collection<?> collection, T[] arrT) {
        int n = collection.size();
        Object[] arrobject = arrT;
        if (arrT.length < n) {
            arrobject = ObjectArrays.newArray(arrT, n);
        }
        ObjectArrays.fillArray(collection, arrobject);
        if (arrobject.length <= n) return arrobject;
        arrobject[n] = null;
        return arrobject;
    }

    static <T> T[] toArrayImpl(Object[] arrobject, int n, int n2, T[] arrT) {
        T[] arrT2;
        Preconditions.checkPositionIndexes(n, n + n2, arrobject.length);
        if (arrT.length < n2) {
            arrT2 = ObjectArrays.newArray(arrT, n2);
        } else {
            arrT2 = arrT;
            if (arrT.length > n2) {
                arrT[n2] = null;
                arrT2 = arrT;
            }
        }
        System.arraycopy(arrobject, n, arrT2, 0, n2);
        return arrT2;
    }
}

