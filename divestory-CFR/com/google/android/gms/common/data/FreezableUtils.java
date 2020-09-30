/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.data;

import com.google.android.gms.common.data.Freezable;
import java.util.ArrayList;
import java.util.Iterator;

public final class FreezableUtils {
    public static <T, E extends Freezable<T>> ArrayList<T> freeze(ArrayList<E> arrayList) {
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        int n = arrayList.size();
        int n2 = 0;
        while (n2 < n) {
            arrayList2.add(((Freezable)arrayList.get(n2)).freeze());
            ++n2;
        }
        return arrayList2;
    }

    public static <T, E extends Freezable<T>> ArrayList<T> freeze(E[] arrE) {
        ArrayList<T> arrayList = new ArrayList<T>(arrE.length);
        int n = 0;
        while (n < arrE.length) {
            arrayList.add(arrE[n].freeze());
            ++n;
        }
        return arrayList;
    }

    public static <T, E extends Freezable<T>> ArrayList<T> freezeIterable(Iterable<E> object) {
        ArrayList arrayList = new ArrayList();
        object = object.iterator();
        while (object.hasNext()) {
            arrayList.add(((Freezable)object.next()).freeze());
        }
        return arrayList;
    }
}

