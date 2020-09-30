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
import com.google.android.gms.internal.drive.zzee;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzeq;
import com.google.android.gms.internal.drive.zzes;
import com.google.android.gms.internal.drive.zzgy;
import com.google.android.gms.internal.drive.zzj;

final class zzax
extends zzav {
    private final /* synthetic */ zzj zzek;
    private final /* synthetic */ zzee zzel;

    zzax(zzaw zzaw2, GoogleApiClient googleApiClient, zzj zzj2, zzee zzee2) {
        this.zzek = zzj2;
        this.zzel = zzee2;
        super(googleApiClient);
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzeo)((zzaw)anyClient).getService()).zza(this.zzek, (zzes)this.zzel, null, (zzeq)new zzgy(this));
    }
}

