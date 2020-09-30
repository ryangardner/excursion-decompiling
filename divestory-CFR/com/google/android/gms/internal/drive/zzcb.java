/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.drive.DrivePreferencesApi;
import com.google.android.gms.drive.FileUploadPreferences;
import com.google.android.gms.internal.drive.zzcc;
import com.google.android.gms.internal.drive.zzcd;
import com.google.android.gms.internal.drive.zzei;

@Deprecated
public final class zzcb
implements DrivePreferencesApi {
    @Override
    public final PendingResult<DrivePreferencesApi.FileUploadPreferencesResult> getFileUploadPreferences(GoogleApiClient googleApiClient) {
        return googleApiClient.enqueue(new zzcc(this, googleApiClient));
    }

    @Override
    public final PendingResult<Status> setFileUploadPreferences(GoogleApiClient googleApiClient, FileUploadPreferences fileUploadPreferences) {
        if (!(fileUploadPreferences instanceof zzei)) throw new IllegalArgumentException("Invalid preference value");
        return googleApiClient.execute(new zzcd(this, googleApiClient, (zzei)fileUploadPreferences));
    }
}

