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
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.internal.drive.zzam;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzbi;
import com.google.android.gms.internal.drive.zzec;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzeq;
import com.google.android.gms.internal.drive.zzgj;
import com.google.android.gms.internal.drive.zzgl;

final class zzbj
extends zzam {
    private final /* synthetic */ zzbi zzev;

    zzbj(zzbi zzbi2, GoogleApiClient googleApiClient) {
        this.zzev = zzbi2;
        super(googleApiClient);
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        ((zzeo)((zzaw)anyClient).getService()).zza(new zzgj(this.zzev.getDriveId(), 536870912, zzbi.zza(this.zzev).getRequestId()), (zzeq)new zzgl(this, null));
    }
}

