/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.DrivePreferencesApi;
import com.google.android.gms.drive.FileUploadPreferences;
import com.google.android.gms.internal.drive.zzau;
import com.google.android.gms.internal.drive.zzcb;
import com.google.android.gms.internal.drive.zzcc;
import com.google.android.gms.internal.drive.zzcf;

abstract class zzcg
extends zzau<DrivePreferencesApi.FileUploadPreferencesResult> {
    private final /* synthetic */ zzcb zzfk;

    public zzcg(zzcb zzcb2, GoogleApiClient googleApiClient) {
        this.zzfk = zzcb2;
        super(googleApiClient);
    }

    @Override
    protected /* synthetic */ Result createFailedResult(Status status) {
        return new zzcf(this.zzfk, status, null, null);
    }
}

