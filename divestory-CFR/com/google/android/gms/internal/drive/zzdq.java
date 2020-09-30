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
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzdp;
import com.google.android.gms.internal.drive.zzdy;
import com.google.android.gms.internal.drive.zzea;
import com.google.android.gms.internal.drive.zzek;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzeq;

final class zzdq
extends zzea {
    private final /* synthetic */ boolean zzga;
    private final /* synthetic */ zzdp zzgq;

    zzdq(zzdp zzdp2, GoogleApiClient googleApiClient, boolean bl) {
        this.zzgq = zzdp2;
        this.zzga = false;
        super(zzdp2, googleApiClient, null);
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzeo)((zzaw)anyClient).getService()).zza(new zzek(this.zzgq.zzk, this.zzga), (zzeq)new zzdy(this));
    }
}

