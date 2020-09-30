/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.internal.drive.zzaa;
import com.google.android.gms.internal.drive.zzao;
import com.google.android.gms.internal.drive.zzfn;
import com.google.android.gms.internal.drive.zzfy;
import com.google.android.gms.internal.drive.zzl;

final class zzan
extends zzl {
    private final BaseImplementation.ResultHolder<DriveApi.DriveIdResult> zzdx;

    public zzan(BaseImplementation.ResultHolder<DriveApi.DriveIdResult> resultHolder) {
        this.zzdx = resultHolder;
    }

    @Override
    public final void zza(Status status) throws RemoteException {
        this.zzdx.setResult(new zzao(status, null));
    }

    @Override
    public final void zza(zzfn zzfn2) throws RemoteException {
        this.zzdx.setResult(new zzao(Status.RESULT_SUCCESS, zzfn2.zzdd));
    }

    @Override
    public final void zza(zzfy zzfy2) throws RemoteException {
        this.zzdx.setResult(new zzao(Status.RESULT_SUCCESS, new zzaa(zzfy2.zzdn).getDriveId()));
    }
}

