/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.gms.tasks.zzd;
import java.util.concurrent.Executor;

final class zzf
implements Runnable {
    private final /* synthetic */ Task zza;
    private final /* synthetic */ zzd zzb;

    zzf(zzd zzd2, Task task) {
        this.zzb = zzd2;
        this.zza = task;
    }

    @Override
    public final void run() {
        Task task;
        block4 : {
            try {
                task = (Task)zzd.zza(this.zzb).then(this.zza);
                if (task != null) break block4;
            }
            catch (Exception exception) {
                zzd.zzb(this.zzb).zza(exception);
                return;
            }
            catch (RuntimeExecutionException runtimeExecutionException) {
                if (runtimeExecutionException.getCause() instanceof Exception) {
                    zzd.zzb(this.zzb).zza((Exception)runtimeExecutionException.getCause());
                    return;
                }
                zzd.zzb(this.zzb).zza(runtimeExecutionException);
                return;
            }
            this.zzb.onFailure(new NullPointerException("Continuation returned null"));
            return;
        }
        task.addOnSuccessListener(TaskExecutors.zza, this.zzb);
        task.addOnFailureListener(TaskExecutors.zza, (OnFailureListener)this.zzb);
        task.addOnCanceledListener(TaskExecutors.zza, (OnCanceledListener)this.zzb);
        return;
    }
}

