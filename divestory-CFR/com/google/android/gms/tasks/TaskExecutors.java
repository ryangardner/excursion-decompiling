/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 */
package com.google.android.gms.tasks;

import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.internal.tasks.zzb;
import com.google.android.gms.tasks.zzt;
import java.util.concurrent.Executor;

public final class TaskExecutors {
    public static final Executor MAIN_THREAD = new zza();
    static final Executor zza = new zzt();

    private TaskExecutors() {
    }

    private static final class zza
    implements Executor {
        private final Handler zza = new zzb(Looper.getMainLooper());

        @Override
        public final void execute(Runnable runnable2) {
            this.zza.post(runnable2);
        }
    }

}

