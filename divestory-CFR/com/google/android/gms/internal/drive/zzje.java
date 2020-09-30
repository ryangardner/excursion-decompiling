/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzjc;
import com.google.android.gms.internal.drive.zzjj;
import java.util.Comparator;
import java.util.Iterator;

final class zzje
implements Comparator<zzjc> {
    zzje() {
    }

    @Override
    public final /* synthetic */ int compare(Object object, Object object2) {
        int n;
        object = (zzjc)object;
        object2 = (zzjc)object2;
        zzjj zzjj2 = (zzjj)((zzjc)object).iterator();
        zzjj zzjj3 = (zzjj)((zzjc)object2).iterator();
        do {
            if (!zzjj2.hasNext()) return Integer.compare(((zzjc)object).size(), ((zzjc)object2).size());
            if (!zzjj3.hasNext()) return Integer.compare(((zzjc)object).size(), ((zzjc)object2).size());
        } while ((n = Integer.compare(zzjc.zzb(zzjj2.nextByte()), zzjc.zzb(zzjj3.nextByte()))) == 0);
        return n;
    }
}

