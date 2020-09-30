/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.internal.location.zzaz;
import com.google.android.gms.internal.location.zze;
import com.google.android.gms.internal.location.zzj;

final class zzf
extends zzj {
    private final /* synthetic */ long zzbw;
    private final /* synthetic */ PendingIntent zzbx;

    zzf(zze zze2, GoogleApiClient googleApiClient, long l, PendingIntent pendingIntent) {
        this.zzbw = l;
        this.zzbx = pendingIntent;
        super(googleApiClient);
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzaz)anyClient).zza(this.zzbw, this.zzbx);
        ((BasePendingResult)this).setResult(Status.RESULT_SUCCESS);
    }
}

