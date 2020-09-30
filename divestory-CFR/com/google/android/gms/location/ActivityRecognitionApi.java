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
import com.google.android.gms.location.ActivityTransitionRequest;

@Deprecated
public interface ActivityRecognitionApi {
    public PendingResult<Status> removeActivityUpdates(GoogleApiClient var1, PendingIntent var2);

    public PendingResult<Status> requestActivityUpdates(GoogleApiClient var1, long var2, PendingIntent var4);

    public PendingResult<Status> zza(GoogleApiClient var1, PendingIntent var2);

    public PendingResult<Status> zza(GoogleApiClient var1, ActivityTransitionRequest var2, PendingIntent var3);
}

