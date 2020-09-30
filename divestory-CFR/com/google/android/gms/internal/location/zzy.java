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
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.internal.location.zzab;
import com.google.android.gms.internal.location.zzac;
import com.google.android.gms.internal.location.zzaj;
import com.google.android.gms.internal.location.zzaz;
import com.google.android.gms.internal.location.zzq;
import com.google.android.gms.location.LocationRequest;

final class zzy
extends zzab {
    private final /* synthetic */ PendingIntent zzbx;
    private final /* synthetic */ LocationRequest zzck;

    zzy(zzq zzq2, GoogleApiClient googleApiClient, LocationRequest locationRequest, PendingIntent pendingIntent) {
        this.zzck = locationRequest;
        this.zzbx = pendingIntent;
        super(googleApiClient);
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        anyClient = (zzaz)anyClient;
        zzac zzac2 = new zzac(this);
        ((zzaz)anyClient).zza(this.zzck, this.zzbx, (zzaj)zzac2);
    }
}

