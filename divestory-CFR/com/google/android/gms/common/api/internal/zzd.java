/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.api.internal.LifecycleCallback;
import com.google.android.gms.common.api.internal.zzc;

final class zzd
implements Runnable {
    private final /* synthetic */ LifecycleCallback zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ zzc zzc;

    zzd(zzc zzc2, LifecycleCallback lifecycleCallback, String string2) {
        this.zzc = zzc2;
        this.zza = lifecycleCallback;
        this.zzb = string2;
    }

    @Override
    public final void run() {
        if (zzc.zza(this.zzc) > 0) {
            LifecycleCallback lifecycleCallback = this.zza;
            Bundle bundle = zzc.zzb(this.zzc) != null ? zzc.zzb(this.zzc).getBundle(this.zzb) : null;
            lifecycleCallback.onCreate(bundle);
        }
        if (zzc.zza(this.zzc) >= 2) {
            this.zza.onStart();
        }
        if (zzc.zza(this.zzc) >= 3) {
            this.zza.onResume();
        }
        if (zzc.zza(this.zzc) >= 4) {
            this.zza.onStop();
        }
        if (zzc.zza(this.zzc) < 5) return;
        this.zza.onDestroy();
    }
}

