/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzjr;
import com.google.android.gms.internal.drive.zzkb;
import com.google.android.gms.internal.drive.zzlj;
import com.google.android.gms.internal.drive.zznm;
import java.io.IOException;

public final class zzli<K, V> {
    static <K, V> int zza(zzlj<K, V> zzlj2, K k, V v) {
        return zzkb.zza(zzlj2.zztu, 1, k) + zzkb.zza(zzlj2.zztw, 2, v);
    }

    static <K, V> void zza(zzjr zzjr2, zzlj<K, V> zzlj2, K k, V v) throws IOException {
        zzkb.zza(zzjr2, zzlj2.zztu, 1, k);
        zzkb.zza(zzjr2, zzlj2.zztw, 2, v);
    }
}

