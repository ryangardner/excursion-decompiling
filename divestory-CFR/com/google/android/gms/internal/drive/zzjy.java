/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzjx;
import com.google.android.gms.internal.drive.zzkb;
import com.google.android.gms.internal.drive.zzkd;
import com.google.android.gms.internal.drive.zzlq;
import com.google.android.gms.internal.drive.zzns;
import java.io.IOException;
import java.util.Map;

abstract class zzjy<T extends zzkd<T>> {
    zzjy() {
    }

    abstract int zza(Map.Entry<?, ?> var1);

    abstract Object zza(zzjx var1, zzlq var2, int var3);

    abstract void zza(zzns var1, Map.Entry<?, ?> var2) throws IOException;

    abstract zzkb<T> zzb(Object var1);

    abstract zzkb<T> zzc(Object var1);

    abstract void zzd(Object var1);

    abstract boolean zze(zzlq var1);
}

