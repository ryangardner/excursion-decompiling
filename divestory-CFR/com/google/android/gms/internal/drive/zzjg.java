/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzjd;
import com.google.android.gms.internal.drive.zzji;
import java.util.Arrays;

final class zzjg
implements zzji {
    private zzjg() {
    }

    /* synthetic */ zzjg(zzjd zzjd2) {
        this();
    }

    @Override
    public final byte[] zzc(byte[] arrby, int n, int n2) {
        return Arrays.copyOfRange(arrby, n, n2 + n);
    }
}

