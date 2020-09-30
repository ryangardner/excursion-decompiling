/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.internal.drive.zzav;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzbi;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzeq;
import com.google.android.gms.internal.drive.zzgy;
import com.google.android.gms.internal.drive.zzo;

final class zzbm
extends zzav {
    private final /* synthetic */ zzbi zzev;

    zzbm(zzbi zzbi2, GoogleApiClient googleApiClient) {
        this.zzev = zzbi2;
        super(googleApiClient);
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzeo)((zzaw)anyClient).getService()).zza(new zzo(zzbi.zza(this.zzev).getRequestId(), false), (zzeq)new zzgy(this));
    }
}

