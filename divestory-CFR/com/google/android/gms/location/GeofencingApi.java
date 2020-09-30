/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 */
package com.google.android.gms.location;

import android.app.PendingIntent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import java.util.List;

@Deprecated
public interface GeofencingApi {
    public PendingResult<Status> addGeofences(GoogleApiClient var1, GeofencingRequest var2, PendingIntent var3);

    @Deprecated
    public PendingResult<Status> addGeofences(GoogleApiClient var1, List<Geofence> var2, PendingIntent var3);

    public PendingResult<Status> removeGeofences(GoogleApiClient var1, PendingIntent var2);

    public PendingResult<Status> removeGeofences(GoogleApiClient var1, List<String> var2);
}

