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
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.internal.drive.zzal;
import com.google.android.gms.internal.drive.zzbi;
import com.google.android.gms.internal.drive.zzfh;
import com.google.android.gms.internal.drive.zzl;

final class zzak
extends zzl {
    private final BaseImplementation.ResultHolder<DriveApi.DriveContentsResult> zzdx;

    zzak(BaseImplementation.ResultHolder<DriveApi.DriveContentsResult> resultHolder) {
        this.zzdx = resultHolder;
    }

    @Override
    public final void zza(Status status) throws RemoteException {
        this.zzdx.setResult(new zzal(status, null));
    }

    @Override
    public final void zza(zzfh zzfh2) throws RemoteException {
        this.zzdx.setResult(new zzal(Status.RESULT_SUCCESS, new zzbi(zzfh2.zzes)));
    }
}

