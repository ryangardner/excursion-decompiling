/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzkt;
import com.google.android.gms.internal.drive.zzku;
import com.google.android.gms.internal.drive.zzkv;
import java.util.Iterator;
import java.util.Map;

final class zzkw<K>
implements Iterator<Map.Entry<K, Object>> {
    private Iterator<Map.Entry<K, Object>> zztg;

    public zzkw(Iterator<Map.Entry<K, Object>> iterator2) {
        this.zztg = iterator2;
    }

    @Override
    public final boolean hasNext() {
        return this.zztg.hasNext();
    }

    @Override
    public final /* synthetic */ Object next() {
        Map.Entry<K, Object> entry = this.zztg.next();
        if (!(entry.getValue() instanceof zzkt)) return entry;
        return new zzkv(entry, null);
    }

    @Override
    public final void remove() {
        this.zztg.remove();
    }
}

