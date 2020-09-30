/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.location;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.internal.location.zzaf;
import com.google.android.gms.internal.location.zzai;
import com.google.android.gms.internal.location.zzaz;
import com.google.android.gms.location.zzal;

final class zzah
extends zzai {
    private final /* synthetic */ zzal zzct;

    zzah(zzaf zzaf2, GoogleApiClient googleApiClient, zzal zzal2) {
        this.zzct = zzal2;
        super(googleApiClient);
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzaz)anyClient).zza(this.zzct, (BaseImplementation.ResultHolder<Status>)this);
    }
}

