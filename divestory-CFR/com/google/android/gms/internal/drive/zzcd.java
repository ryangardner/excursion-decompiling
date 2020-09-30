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
import com.google.android.gms.internal.drive.zzcb;
import com.google.android.gms.internal.drive.zzei;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzeq;
import com.google.android.gms.internal.drive.zzgu;
import com.google.android.gms.internal.drive.zzgy;

final class zzcd
extends zzav {
    private final /* synthetic */ zzei zzfl;

    zzcd(zzcb zzcb2, GoogleApiClient googleApiClient, zzei zzei2) {
        this.zzfl = zzei2;
        super(googleApiClient);
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzeo)((zzaw)anyClient).getService()).zza(new zzgu(this.zzfl), (zzeq)new zzgy(this));
    }
}

