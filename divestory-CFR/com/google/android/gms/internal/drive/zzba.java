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
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzeq;
import com.google.android.gms.internal.drive.zzes;
import com.google.android.gms.internal.drive.zzgs;
import com.google.android.gms.internal.drive.zzgy;

final class zzba
extends zzav {
    private final /* synthetic */ DriveId zzen;
    private final /* synthetic */ int zzeo;

    zzba(zzaw zzaw2, GoogleApiClient googleApiClient, DriveId driveId, int n) {
        this.zzen = driveId;
        this.zzeo = 1;
        super(googleApiClient);
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzeo)((zzaw)anyClient).getService()).zza(new zzgs(this.zzen, this.zzeo), null, null, (zzeq)new zzgy(this));
    }
}

