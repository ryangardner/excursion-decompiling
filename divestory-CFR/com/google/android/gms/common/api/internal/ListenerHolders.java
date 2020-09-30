/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 */
package com.google.android.gms.common.api.internal;

import android.os.Looper;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;

public class ListenerHolders {
    private final Set<ListenerHolder<?>> zaa = Collections.newSetFromMap(new WeakHashMap());

    public static <L> ListenerHolder<L> createListenerHolder(L l, Looper looper, String string2) {
        Preconditions.checkNotNull(l, "Listener must not be null");
        Preconditions.checkNotNull(looper, "Looper must not be null");
        Preconditions.checkNotNull(string2, "Listener type must not be null");
        return new ListenerHolder<L>(looper, l, string2);
    }

    public static <L> ListenerHolder.ListenerKey<L> createListenerKey(L l, String string2) {
        Preconditions.checkNotNull(l, "Listener must not be null");
        Preconditions.checkNotNull(string2, "Listener type must not be null");
        Preconditions.checkNotEmpty(string2, "Listener type must not be empty");
        return new ListenerHolder.ListenerKey<L>(l, string2);
    }

    public final <L> ListenerHolder<L> zaa(L object, Looper looper, String string2) {
        object = ListenerHolders.createListenerHolder(object, looper, string2);
        this.zaa.add((ListenerHolder<?>)object);
        return object;
    }

    public final void zaa() {
        Iterator<ListenerHolder<?>> iterator2 = this.zaa.iterator();
        do {
            if (!iterator2.hasNext()) {
                this.zaa.clear();
                return;
            }
            iterator2.next().clear();
        } while (true);
    }
}

