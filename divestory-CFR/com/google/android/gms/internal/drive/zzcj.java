/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzch;
import com.google.android.gms.internal.drive.zzg;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;

final class zzcj
implements Continuation {
    private final zzg zzfp;

    zzcj(zzg zzg2) {
        this.zzfp = zzg2;
    }

    public final Object then(Task task) {
        return zzch.zza(this.zzfp, task);
    }
}

