/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util;

import com.google.api.client.util.Collections2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public final class Lists {
    private Lists() {
    }

    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList();
    }

    public static <E> ArrayList<E> newArrayList(Iterable<? extends E> iterable) {
        if (!(iterable instanceof Collection)) return Lists.newArrayList(iterable.iterator());
        return new ArrayList<E>(Collections2.cast(iterable));
    }

    public static <E> ArrayList<E> newArrayList(Iterator<? extends E> iterator2) {
        ArrayList<E> arrayList = Lists.newArrayList();
        while (iterator2.hasNext()) {
            arrayList.add(iterator2.next());
        }
        return arrayList;
    }

    public static <E> ArrayList<E> newArrayListWithCapacity(int n) {
        return new ArrayList(n);
    }
}

