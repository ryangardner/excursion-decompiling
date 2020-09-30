/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.tasks;

import com.google.android.gms.tasks.zzu;
import java.util.concurrent.Callable;

final class zzy
implements Runnable {
    private final /* synthetic */ zzu zza;
    private final /* synthetic */ Callable zzb;

    zzy(zzu zzu2, Callable callable) {
        this.zza = zzu2;
        this.zzb = callable;
    }

    @Override
    public final void run() {
        try {
            this.zza.zza(this.zzb.call());
            return;
        }
        catch (Throwable throwable) {
            this.zza.zza(new RuntimeException(throwable));
            return;
        }
        catch (Exception exception) {
            this.zza.zza(exception);
            return;
        }
    }
}

