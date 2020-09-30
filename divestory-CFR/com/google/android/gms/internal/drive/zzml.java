/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzmi;
import com.google.android.gms.internal.drive.zzmj;
import com.google.android.gms.internal.drive.zzmk;
import com.google.android.gms.internal.drive.zzmr;
import java.util.Iterator;
import java.util.Map;

final class zzml
extends zzmr {
    private final /* synthetic */ zzmi zzvk;

    private zzml(zzmi zzmi2) {
        this.zzvk = zzmi2;
        super(zzmi2, null);
    }

    /* synthetic */ zzml(zzmi zzmi2, zzmj zzmj2) {
        this(zzmi2);
    }

    @Override
    public final Iterator<Map.Entry<K, V>> iterator() {
        return new zzmk(this.zzvk, null);
    }
}

