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
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.internal.drive.zzav;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzdp;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzeq;
import com.google.android.gms.internal.drive.zzgw;
import com.google.android.gms.internal.drive.zzgy;
import java.util.List;

final class zzds
extends zzav {
    private final /* synthetic */ List zzgb;
    private final /* synthetic */ zzdp zzgq;

    zzds(zzdp zzdp2, GoogleApiClient googleApiClient, List list) {
        this.zzgq = zzdp2;
        this.zzgb = list;
        super(googleApiClient);
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzeo)((zzaw)anyClient).getService()).zza(new zzgw(this.zzgq.zzk, this.zzgb), (zzeq)new zzgy(this));
    }
}

