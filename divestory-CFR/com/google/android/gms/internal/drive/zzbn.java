/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.internal.drive.zzbo;
import com.google.android.gms.internal.drive.zzbp;
import com.google.android.gms.internal.drive.zzdp;

public final class zzbn
extends zzdp
implements DriveFile {
    public zzbn(DriveId driveId) {
        super(driveId);
    }

    @Override
    public final PendingResult<DriveApi.DriveContentsResult> open(GoogleApiClient googleApiClient, int n, DriveFile.DownloadProgressListener downloadProgressListener) {
        if (n != 268435456 && n != 536870912) {
            if (n != 805306368) throw new IllegalArgumentException("Invalid mode provided.");
        }
        if (downloadProgressListener == null) {
            downloadProgressListener = null;
            return googleApiClient.enqueue(new zzbo(this, googleApiClient, n, downloadProgressListener));
        }
        downloadProgressListener = new zzbp(googleApiClient.registerListener(downloadProgressListener));
        return googleApiClient.enqueue(new zzbo(this, googleApiClient, n, downloadProgressListener));
    }
}

