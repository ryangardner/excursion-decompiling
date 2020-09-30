/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 *  android.os.Message
 */
package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.base.zap;

public final class ListenerHolder<L> {
    private final zaa zaa;
    private volatile L zab;
    private volatile ListenerKey<L> zac;

    ListenerHolder(Looper looper, L l, String string2) {
        this.zaa = new zaa(looper);
        this.zab = Preconditions.checkNotNull(l, "Listener must not be null");
        this.zac = new ListenerKey<L>(l, Preconditions.checkNotEmpty(string2));
    }

    public final void clear() {
        this.zab = null;
        this.zac = null;
    }

    public final ListenerKey<L> getListenerKey() {
        return this.zac;
    }

    public final boolean hasListener() {
        if (this.zab == null) return false;
        return true;
    }

    public final void notifyListener(Notifier<? super L> message) {
        Preconditions.checkNotNull(message, "Notifier must not be null");
        message = this.zaa.obtainMessage(1, message);
        this.zaa.sendMessage(message);
    }

    final void notifyListenerInternal(Notifier<? super L> notifier) {
        L l = this.zab;
        if (l == null) {
            notifier.onNotifyListenerFailed();
            return;
        }
        try {
            notifier.notifyListener(l);
            return;
        }
        catch (RuntimeException runtimeException) {
            notifier.onNotifyListenerFailed();
            throw runtimeException;
        }
    }

    public static final class ListenerKey<L> {
        private final L zaa;
        private final String zab;

        ListenerKey(L l, String string2) {
            this.zaa = l;
            this.zab = string2;
        }

        public final boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof ListenerKey)) {
                return false;
            }
            object = (ListenerKey)object;
            if (this.zaa != ((ListenerKey)object).zaa) return false;
            if (!this.zab.equals(((ListenerKey)object).zab)) return false;
            return true;
        }

        public final int hashCode() {
            return System.identityHashCode(this.zaa) * 31 + this.zab.hashCode();
        }
    }

    public static interface Notifier<L> {
        public void notifyListener(L var1);

        public void onNotifyListenerFailed();
    }

    private final class zaa
    extends zap {
        public zaa(Looper looper) {
            super(looper);
        }

        public final void handleMessage(Message object) {
            int n = object.what;
            boolean bl = true;
            if (n != 1) {
                bl = false;
            }
            Preconditions.checkArgument(bl);
            object = (Notifier)object.obj;
            ListenerHolder.this.notifyListenerInternal(object);
        }
    }

}

