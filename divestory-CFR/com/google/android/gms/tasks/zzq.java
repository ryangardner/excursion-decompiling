/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.zzr;
import java.util.ArrayDeque;
import java.util.Queue;

final class zzq<TResult> {
    private final Object zza = new Object();
    private Queue<zzr<TResult>> zzb;
    private boolean zzc;

    zzq() {
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public final void zza(Task<TResult> task) {
        Object object = this.zza;
        synchronized (object) {
            if (this.zzb == null) return;
            if (this.zzc) {
                return;
            }
            this.zzc = true;
        }
        do {
            zzr<TResult> zzr2;
            object = this.zza;
            synchronized (object) {
                zzr2 = this.zzb.poll();
                if (zzr2 == null) {
                    this.zzc = false;
                    return;
                }
            }
            zzr2.zza(task);
        } while (true);
    }

    public final void zza(zzr<TResult> zzr2) {
        Object object = this.zza;
        synchronized (object) {
            if (this.zzb == null) {
                ArrayDeque<zzr<TResult>> arrayDeque = new ArrayDeque<zzr<TResult>>();
                this.zzb = arrayDeque;
            }
            this.zzb.add(zzr2);
            return;
        }
    }
}

