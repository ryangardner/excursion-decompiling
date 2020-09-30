/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 */
package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.internal.location.zzf;
import com.google.android.gms.internal.location.zzg;
import com.google.android.gms.internal.location.zzh;
import com.google.android.gms.internal.location.zzi;
import com.google.android.gms.location.ActivityRecognitionApi;
import com.google.android.gms.location.ActivityTransitionRequest;

public final class zze
implements ActivityRecognitionApi {
    @Override
    public final PendingResult<Status> removeActivityUpdates(GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
        return googleApiClient.execute(new zzg(this, googleApiClient, pendingIntent));
    }

    @Override
    public final PendingResult<Status> requestActivityUpdates(GoogleApiClient googleApiClient, long l, PendingIntent pendingIntent) {
        return googleApiClient.execute(new zzf(this, googleApiClient, l, pendingIntent));
    }

    @Override
    public final PendingResult<Status> zza(GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
        return googleApiClient.execute(new zzi(this, googleApiClient, pendingIntent));
    }

    @Override
    public final PendingResult<Status> zza(GoogleApiClient googleApiClient, ActivityTransitionRequest activityTransitionRequest, PendingIntent pendingIntent) {
        return googleApiClient.execute(new zzh(this, googleApiClient, activityTransitionRequest, pendingIntent));
    }
}

