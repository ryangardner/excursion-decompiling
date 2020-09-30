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
import com.google.android.gms.internal.location.zzag;
import com.google.android.gms.internal.location.zzah;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingApi;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.zzal;
import java.util.List;

public final class zzaf
implements GeofencingApi {
    private final PendingResult<Status> zza(GoogleApiClient googleApiClient, zzal zzal2) {
        return googleApiClient.execute(new zzah(this, googleApiClient, zzal2));
    }

    @Override
    public final PendingResult<Status> addGeofences(GoogleApiClient googleApiClient, GeofencingRequest geofencingRequest, PendingIntent pendingIntent) {
        return googleApiClient.execute(new zzag(this, googleApiClient, geofencingRequest, pendingIntent));
    }

    @Deprecated
    @Override
    public final PendingResult<Status> addGeofences(GoogleApiClient googleApiClient, List<Geofence> list, PendingIntent pendingIntent) {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.addGeofences(list);
        builder.setInitialTrigger(5);
        return this.addGeofences(googleApiClient, builder.build(), pendingIntent);
    }

    @Override
    public final PendingResult<Status> removeGeofences(GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
        return this.zza(googleApiClient, zzal.zza(pendingIntent));
    }

    @Override
    public final PendingResult<Status> removeGeofences(GoogleApiClient googleApiClient, List<String> list) {
        return this.zza(googleApiClient, zzal.zza(list));
    }
}

