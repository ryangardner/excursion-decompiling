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
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzcb;
import com.google.android.gms.internal.drive.zzce;
import com.google.android.gms.internal.drive.zzcg;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzeq;

final class zzcc
extends zzcg {
    private final /* synthetic */ zzcb zzfk;

    zzcc(zzcb zzcb2, GoogleApiClient googleApiClient) {
        this.zzfk = zzcb2;
        super(zzcb2, googleApiClient);
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzeo)((zzaw)anyClient).getService()).zzb(new zzce(this.zzfk, this, null));
    }
}

