/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.internal.drive.zzau;
import com.google.android.gms.internal.drive.zzdp;
import com.google.android.gms.internal.drive.zzdq;
import com.google.android.gms.internal.drive.zzdz;

abstract class zzea
extends zzau<DriveResource.MetadataResult> {
    private zzea(zzdp zzdp2, GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    /* synthetic */ zzea(zzdp zzdp2, GoogleApiClient googleApiClient, zzdq zzdq2) {
        this(zzdp2, googleApiClient);
    }

    @Override
    public /* synthetic */ Result createFailedResult(Status status) {
        return new zzdz(status, null);
    }
}

