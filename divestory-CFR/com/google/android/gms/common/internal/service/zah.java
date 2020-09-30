/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.internal.service;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.internal.service.Common;
import com.google.android.gms.common.internal.service.zaj;

abstract class zah<R extends Result>
extends BaseImplementation.ApiMethodImpl<R, zaj> {
    public zah(GoogleApiClient googleApiClient) {
        super(Common.API, googleApiClient);
    }
}

