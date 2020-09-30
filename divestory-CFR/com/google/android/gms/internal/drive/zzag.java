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
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.internal.drive.zzaf;
import com.google.android.gms.internal.drive.zzar;
import com.google.android.gms.internal.drive.zzas;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzeq;
import com.google.android.gms.internal.drive.zzgq;

final class zzag
extends zzar {
    private final /* synthetic */ Query zzdu;

    zzag(zzaf zzaf2, GoogleApiClient googleApiClient, Query query) {
        this.zzdu = query;
        super(googleApiClient);
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzeo)((zzaw)anyClient).getService()).zza(new zzgq(this.zzdu), (zzeq)new zzas(this));
    }
}

