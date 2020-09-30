/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.api.internal.LifecycleCallback;
import com.google.android.gms.common.api.internal.zzb;

final class zza
implements Runnable {
    private final /* synthetic */ LifecycleCallback zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ zzb zzc;

    zza(zzb zzb2, LifecycleCallback lifecycleCallback, String string2) {
        this.zzc = zzb2;
        this.zza = lifecycleCallback;
        this.zzb = string2;
    }

    @Override
    public final void run() {
        if (zzb.zza(this.zzc) > 0) {
            LifecycleCallback lifecycleCallback = this.zza;
            Bundle bundle = zzb.zzb(this.zzc) != null ? zzb.zzb(this.zzc).getBundle(this.zzb) : null;
            lifecycleCallback.onCreate(bundle);
        }
        if (zzb.zza(this.zzc) >= 2) {
            this.zza.onStart();
        }
        if (zzb.zza(this.zzc) >= 3) {
            this.zza.onResume();
        }
        if (zzb.zza(this.zzc) >= 4) {
            this.zza.onStop();
        }
        if (zzb.zza(this.zzc) < 5) return;
        this.zza.onDestroy();
    }
}

