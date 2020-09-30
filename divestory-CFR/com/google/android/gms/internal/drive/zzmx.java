/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzjc;
import com.google.android.gms.internal.drive.zzns;
import java.io.IOException;

abstract class zzmx<T, B> {
    zzmx() {
    }

    abstract void zza(B var1, int var2, long var3);

    abstract void zza(B var1, int var2, zzjc var3);

    abstract void zza(T var1, zzns var2) throws IOException;

    abstract void zzc(T var1, zzns var2) throws IOException;

    abstract void zzd(Object var1);

    abstract void zze(Object var1, T var2);

    abstract B zzez();

    abstract void zzf(Object var1, B var2);

    abstract T zzg(T var1, T var2);

    abstract int zzn(T var1);

    abstract T zzr(Object var1);

    abstract int zzs(T var1);
}

