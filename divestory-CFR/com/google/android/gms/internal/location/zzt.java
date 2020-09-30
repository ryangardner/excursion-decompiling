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
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.internal.location.zzab;
import com.google.android.gms.internal.location.zzaz;
import com.google.android.gms.internal.location.zzq;

final class zzt
extends zzab {
    private final /* synthetic */ boolean zzcn;

    zzt(zzq zzq2, GoogleApiClient googleApiClient, boolean bl) {
        this.zzcn = bl;
        super(googleApiClient);
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzaz)anyClient).zza(this.zzcn);
        ((BasePendingResult)this).setResult(Status.RESULT_SUCCESS);
    }
}

