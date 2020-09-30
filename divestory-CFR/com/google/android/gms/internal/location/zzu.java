/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.location.Location
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.location;

import android.location.Location;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.internal.location.zzab;
import com.google.android.gms.internal.location.zzaz;
import com.google.android.gms.internal.location.zzq;

final class zzu
extends zzab {
    private final /* synthetic */ Location zzco;

    zzu(zzq zzq2, GoogleApiClient googleApiClient, Location location) {
        this.zzco = location;
        super(googleApiClient);
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzaz)anyClient).zza(this.zzco);
        ((BasePendingResult)this).setResult(Status.RESULT_SUCCESS);
    }
}

