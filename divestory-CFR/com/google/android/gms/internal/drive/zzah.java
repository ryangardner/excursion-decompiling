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
import com.google.android.gms.internal.drive.zzaf;
import com.google.android.gms.internal.drive.zzak;
import com.google.android.gms.internal.drive.zzam;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzeq;
import com.google.android.gms.internal.drive.zzr;

final class zzah
extends zzam {
    private final /* synthetic */ int zzdv;

    zzah(zzaf zzaf2, GoogleApiClient googleApiClient, int n) {
        this.zzdv = 536870912;
        super(googleApiClient);
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzeo)((zzaw)anyClient).getService()).zza(new zzr(this.zzdv), (zzeq)new zzak(this));
    }
}

