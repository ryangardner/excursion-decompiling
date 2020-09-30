/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.PendingIntent
 *  android.content.Context
 */
package com.google.android.gms.location;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.ApiExceptionMapper;
import com.google.android.gms.common.api.internal.StatusExceptionMapper;
import com.google.android.gms.common.internal.PendingResultUtil;
import com.google.android.gms.location.GeofencingApi;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import java.util.List;

public class GeofencingClient
extends GoogleApi<Api.ApiOptions.NoOptions> {
    public GeofencingClient(Activity activity) {
        super(activity, LocationServices.API, null, (StatusExceptionMapper)new ApiExceptionMapper());
    }

    public GeofencingClient(Context context) {
        super(context, LocationServices.API, null, (StatusExceptionMapper)new ApiExceptionMapper());
    }

    public Task<Void> addGeofences(GeofencingRequest geofencingRequest, PendingIntent pendingIntent) {
        return PendingResultUtil.toVoidTask(LocationServices.GeofencingApi.addGeofences(this.asGoogleApiClient(), geofencingRequest, pendingIntent));
    }

    public Task<Void> removeGeofences(PendingIntent pendingIntent) {
        return PendingResultUtil.toVoidTask(LocationServices.GeofencingApi.removeGeofences(this.asGoogleApiClient(), pendingIntent));
    }

    public Task<Void> removeGeofences(List<String> list) {
        return PendingResultUtil.toVoidTask(LocationServices.GeofencingApi.removeGeofences(this.asGoogleApiClient(), list));
    }
}

