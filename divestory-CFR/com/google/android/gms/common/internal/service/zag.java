/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.internal.service;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.service.zah;

abstract class zag
extends zah<Status> {
    public zag(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    @Override
    public /* synthetic */ Result createFailedResult(Status status) {
        return status;
    }
}

