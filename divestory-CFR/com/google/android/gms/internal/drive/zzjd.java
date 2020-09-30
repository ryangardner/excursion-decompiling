/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzjc;
import com.google.android.gms.internal.drive.zzjf;
import java.util.NoSuchElementException;

final class zzjd
extends zzjf {
    private final int limit = this.zznu.size();
    private int position = 0;
    private final /* synthetic */ zzjc zznu;

    zzjd(zzjc zzjc2) {
        this.zznu = zzjc2;
    }

    @Override
    public final boolean hasNext() {
        if (this.position >= this.limit) return false;
        return true;
    }

    @Override
    public final byte nextByte() {
        int n = this.position;
        if (n >= this.limit) throw new NoSuchElementException();
        this.position = n + 1;
        return this.zznu.zzt(n);
    }
}

