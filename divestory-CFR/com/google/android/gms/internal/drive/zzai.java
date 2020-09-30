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
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.internal.drive.zzaf;
import com.google.android.gms.internal.drive.zzan;
import com.google.android.gms.internal.drive.zzap;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzek;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzeq;

final class zzai
extends zzap {
    private final /* synthetic */ String zzdw;

    zzai(zzaf zzaf2, GoogleApiClient googleApiClient, String string2) {
        this.zzdw = string2;
        super(googleApiClient);
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzeo)((zzaw)anyClient).getService()).zza(new zzek(DriveId.zza(this.zzdw), false), (zzeq)new zzan(this));
    }
}

