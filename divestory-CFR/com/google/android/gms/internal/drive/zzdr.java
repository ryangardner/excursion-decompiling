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
import com.google.android.gms.internal.drive.zzar;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzdp;
import com.google.android.gms.internal.drive.zzdx;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzeq;
import com.google.android.gms.internal.drive.zzex;

final class zzdr
extends zzar {
    private final /* synthetic */ zzdp zzgq;

    zzdr(zzdp zzdp2, GoogleApiClient googleApiClient) {
        this.zzgq = zzdp2;
        super(googleApiClient);
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzeo)((zzaw)anyClient).getService()).zza(new zzex(this.zzgq.zzk), (zzeq)new zzdx(this));
    }
}
