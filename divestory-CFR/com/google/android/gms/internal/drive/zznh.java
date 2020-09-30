/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzkq;

abstract class zznh {
    zznh() {
    }

    abstract int zzb(int var1, byte[] var2, int var3, int var4);

    abstract int zzb(CharSequence var1, byte[] var2, int var3, int var4);

    final boolean zze(byte[] arrby, int n, int n2) {
        if (this.zzb(0, arrby, n, n2) != 0) return false;
        return true;
    }

    abstract String zzg(byte[] var1, int var2, int var3) throws zzkq;
}

