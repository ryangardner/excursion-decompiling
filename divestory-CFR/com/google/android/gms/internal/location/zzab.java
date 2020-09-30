/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.location;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;

abstract class zzab
extends LocationServices.zza<Status> {
    public zzab(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    @Override
    public /* synthetic */ Result createFailedResult(Status status) {
        return status;
    }
}

