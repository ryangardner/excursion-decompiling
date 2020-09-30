/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.internal.drive.zzch;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;

final class zzci
implements Continuation {
    private final ListenerHolder zzfo;

    zzci(ListenerHolder listenerHolder) {
        this.zzfo = listenerHolder;
    }

    public final Object then(Task task) {
        return zzch.zza(this.zzfo, task);
    }
}

