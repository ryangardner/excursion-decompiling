/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.drive.zzau;

public abstract class zzav
extends zzau<Status> {
    public zzav(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    @Override
    protected /* synthetic */ Result createFailedResult(Status status) {
        return status;
    }
}

