/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.zzv;
import com.google.android.gms.tasks.zzw;
import java.util.concurrent.Executor;

final class zzx
implements zzw {
    static final zzw zza = new zzx();

    private zzx() {
    }

    @Override
    public final Executor zza(Executor executor) {
        return zzv.zzb(executor);
    }
}

