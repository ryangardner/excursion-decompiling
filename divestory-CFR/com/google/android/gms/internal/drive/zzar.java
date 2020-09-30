/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.internal.drive.zzaq;
import com.google.android.gms.internal.drive.zzau;

abstract class zzar
extends zzau<DriveApi.MetadataBufferResult> {
    zzar(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    @Override
    public /* synthetic */ Result createFailedResult(Status status) {
        return new zzaq(status, null, false);
    }
}

