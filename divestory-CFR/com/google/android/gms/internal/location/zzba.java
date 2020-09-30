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

final class zzba
extends zzan {
    private BaseImplementation.ResultHolder<Status> zzdf;

    public zzba(BaseImplementation.ResultHolder<Status> resultHolder) {
        this.zzdf = resultHolder;
    }

    @Override
    public final void zza(int n, PendingIntent pendingIntent) {
        Log.wtf((String)"LocationClientImpl", (String)"Unexpected call to onRemoveGeofencesByPendingIntentResult");
    }

    @Override
    public final void zza(int n, String[] object) {
        if (this.zzdf == null) {
            Log.wtf((String)"LocationClientImpl", (String)"onAddGeofenceResult called multiple times");
            return;
        }
        object = LocationStatusCodes.zzd(LocationStatusCodes.zzc(n));
        this.zzdf.setResult((Status)object);
        this.zzdf = null;
    }

    @Override
    public final void zzb(int n, String[] arrstring) {
        Log.wtf((String)"LocationClientImpl", (String)"Unexpected call to onRemoveGeofencesByRequestIdsResult");
    }
}

