/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.util.Log
 */
package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.internal.location.zzan;
import com.google.android.gms.location.LocationStatusCodes;

final class zzbb
extends zzan {
    private BaseImplementation.ResultHolder<Status> zzdf;

    public zzbb(BaseImplementation.ResultHolder<Status> resultHolder) {
        this.zzdf = resultHolder;
    }

    private final void zze(int n) {
        if (this.zzdf == null) {
            Log.wtf((String)"LocationClientImpl", (String)"onRemoveGeofencesResult called multiple times");
            return;
        }
        Status status = LocationStatusCodes.zzd(LocationStatusCodes.zzc(n));
        this.zzdf.setResult(status);
        this.zzdf = null;
    }

    @Override
    public final void zza(int n, PendingIntent pendingIntent) {
        this.zze(n);
    }

    @Override
    public final void zza(int n, String[] arrstring) {
        Log.wtf((String)"LocationClientImpl", (String)"Unexpected call to onAddGeofencesResult");
    }

    @Override
    public final void zzb(int n, String[] arrstring) {
        this.zze(n);
    }
}

