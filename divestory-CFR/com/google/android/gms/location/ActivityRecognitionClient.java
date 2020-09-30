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
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionApi;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;

public class ActivityRecognitionClient
extends GoogleApi<Api.ApiOptions.NoOptions> {
    public ActivityRecognitionClient(Activity activity) {
        super(activity, LocationServices.API, null, (StatusExceptionMapper)new ApiExceptionMapper());
    }

    public ActivityRecognitionClient(Context context) {
        super(context, LocationServices.API, null, (StatusExceptionMapper)new ApiExceptionMapper());
    }

    public Task<Void> removeActivityTransitionUpdates(PendingIntent pendingIntent) {
        return PendingResultUtil.toVoidTask(ActivityRecognition.ActivityRecognitionApi.zza(this.asGoogleApiClient(), pendingIntent));
    }

    public Task<Void> removeActivityUpdates(PendingIntent pendingIntent) {
        return PendingResultUtil.toVoidTask(ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(this.asGoogleApiClient(), pendingIntent));
    }

    public Task<Void> requestActivityTransitionUpdates(ActivityTransitionRequest activityTransitionRequest, PendingIntent pendingIntent) {
        return PendingResultUtil.toVoidTask(ActivityRecognition.ActivityRecognitionApi.zza(this.asGoogleApiClient(), activityTransitionRequest, pendingIntent));
    }

    public Task<Void> requestActivityUpdates(long l, PendingIntent pendingIntent) {
        return PendingResultUtil.toVoidTask(ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(this.asGoogleApiClient(), l, pendingIntent));
    }
}

