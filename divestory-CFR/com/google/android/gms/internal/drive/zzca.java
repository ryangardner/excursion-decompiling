/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.internal.drive.zzau;
import com.google.android.gms.internal.drive.zzbz;

abstract class zzca
extends zzau<DriveFolder.DriveFolderResult> {
    zzca(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    @Override
    public /* synthetic */ Result createFailedResult(Status status) {
        return new zzbz(status, null);
    }
}

