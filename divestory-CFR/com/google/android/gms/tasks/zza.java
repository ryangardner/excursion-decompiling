/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.zzb;
import com.google.android.gms.tasks.zzu;

final class zza
extends CancellationToken {
    private final zzu<Void> zza = new zzu();

    zza() {
    }

    @Override
    public final boolean isCancellationRequested() {
        return ((Task)this.zza).isComplete();
    }

    @Override
    public final CancellationToken onCanceledRequested(OnTokenCanceledListener onTokenCanceledListener) {
        ((Task)this.zza).addOnSuccessListener(new zzb(this, onTokenCanceledListener));
        return this;
    }

    public final void zza() {
        this.zza.zzb((Void)null);
    }
}

