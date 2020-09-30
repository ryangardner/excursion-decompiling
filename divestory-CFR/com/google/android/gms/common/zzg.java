/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common;

import com.google.android.gms.common.zzd;
import java.util.Arrays;

final class zzg
extends zzd {
    private final byte[] zza;

    zzg(byte[] arrby) {
        super(Arrays.copyOfRange(arrby, 0, 25));
        this.zza = arrby;
    }

    @Override
    final byte[] zza() {
        return this.zza;
    }
}

