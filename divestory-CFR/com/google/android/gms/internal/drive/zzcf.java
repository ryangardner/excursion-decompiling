/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.DrivePreferencesApi;
import com.google.android.gms.drive.FileUploadPreferences;
import com.google.android.gms.internal.drive.zzcb;
import com.google.android.gms.internal.drive.zzcc;

final class zzcf
implements DrivePreferencesApi.FileUploadPreferencesResult {
    private final Status zzdy;
    private final FileUploadPreferences zzfm;

    private zzcf(zzcb zzcb2, Status status, FileUploadPreferences fileUploadPreferences) {
        this.zzdy = status;
        this.zzfm = fileUploadPreferences;
    }

    /* synthetic */ zzcf(zzcb zzcb2, Status status, FileUploadPreferences fileUploadPreferences, zzcc zzcc2) {
        this(zzcb2, status, fileUploadPreferences);
    }

    @Override
    public final FileUploadPreferences getFileUploadPreferences() {
        return this.zzfm;
    }

    @Override
    public final Status getStatus() {
        return this.zzdy;
    }
}

