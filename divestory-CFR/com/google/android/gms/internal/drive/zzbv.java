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
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.internal.drive.zzbn;
import com.google.android.gms.internal.drive.zzbx;
import com.google.android.gms.internal.drive.zzfn;
import com.google.android.gms.internal.drive.zzl;

final class zzbv
extends zzl {
    private final BaseImplementation.ResultHolder<DriveFolder.DriveFileResult> zzdx;

    public zzbv(BaseImplementation.ResultHolder<DriveFolder.DriveFileResult> resultHolder) {
        this.zzdx = resultHolder;
    }

    @Override
    public final void zza(Status status) throws RemoteException {
        this.zzdx.setResult(new zzbx(status, null));
    }

    @Override
    public final void zza(zzfn zzfn2) throws RemoteException {
        this.zzdx.setResult(new zzbx(Status.RESULT_SUCCESS, new zzbn(zzfn2.zzdd)));
    }
}

