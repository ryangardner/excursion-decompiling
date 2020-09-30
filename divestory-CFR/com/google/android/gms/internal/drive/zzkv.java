/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzkt;
import com.google.android.gms.internal.drive.zzku;
import com.google.android.gms.internal.drive.zzlq;
import java.util.Map;

final class zzkv<K>
implements Map.Entry<K, Object> {
    private Map.Entry<K, zzkt> zztf;

    private zzkv(Map.Entry<K, zzkt> entry) {
        this.zztf = entry;
    }

    /* synthetic */ zzkv(Map.Entry entry, zzku zzku2) {
        this(entry);
    }

    @Override
    public final K getKey() {
        return this.zztf.getKey();
    }

    @Override
    public final Object getValue() {
        if (this.zztf.getValue() != null) return zzkt.zzdp();
        return null;
    }

    @Override
    public final Object setValue(Object object) {
        if (!(object instanceof zzlq)) throw new IllegalArgumentException("LazyField now only used for MessageSet, and the value of MessageSet must be an instance of MessageLite");
        return this.zztf.getValue().zzi((zzlq)object);
    }

    public final zzkt zzdq() {
        return this.zztf.getValue();
    }
}

