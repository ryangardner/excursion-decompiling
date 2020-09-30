/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzkk;
import com.google.android.gms.internal.drive.zzlo;
import com.google.android.gms.internal.drive.zzlq;

final class zzme
implements zzlo {
    private final int flags;
    private final String info;
    private final Object[] zzue;
    private final zzlq zzuh;

    zzme(zzlq zzlq2, String string2, Object[] arrobject) {
        this.zzuh = zzlq2;
        this.info = string2;
        this.zzue = arrobject;
        int n = string2.charAt(0);
        if (n < 55296) {
            this.flags = n;
            return;
        }
        int n2 = n & 8191;
        int n3 = 13;
        n = 1;
        do {
            char c;
            if ((c = string2.charAt(n)) < '\ud800') {
                this.flags = n2 | c << n3;
                return;
            }
            n2 |= (c & 8191) << n3;
            n3 += 13;
            ++n;
        } while (true);
    }

    @Override
    public final int zzec() {
        if ((this.flags & 1) != 1) return zzkk.zze.zzsg;
        return zzkk.zze.zzsf;
    }

    @Override
    public final boolean zzed() {
        if ((this.flags & 2) != 2) return false;
        return true;
    }

    @Override
    public final zzlq zzee() {
        return this.zzuh;
    }

    final String zzek() {
        return this.info;
    }

    final Object[] zzel() {
        return this.zzue;
    }
}

