/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.stats;

import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

@Deprecated
public abstract class StatsEvent
extends AbstractSafeParcelable
implements ReflectedParcelable {
    public String toString() {
        long l = this.zza();
        int n = this.zzb();
        long l2 = this.zzc();
        String string2 = this.zzd();
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(string2).length() + 53);
        stringBuilder.append(l);
        stringBuilder.append("\t");
        stringBuilder.append(n);
        stringBuilder.append("\t");
        stringBuilder.append(l2);
        stringBuilder.append(string2);
        return stringBuilder.toString();
    }

    public abstract long zza();

    public abstract int zzb();

    public abstract long zzc();

    public abstract String zzd();

    public static interface Types {
        public static final int EVENT_TYPE_ACQUIRE_WAKE_LOCK = 7;
        public static final int EVENT_TYPE_RELEASE_WAKE_LOCK = 8;
    }

}

