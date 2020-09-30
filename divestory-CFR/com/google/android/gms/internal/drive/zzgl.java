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
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.internal.drive.zzal;
import com.google.android.gms.internal.drive.zzbi;
import com.google.android.gms.internal.drive.zzfh;
import com.google.android.gms.internal.drive.zzfl;
import com.google.android.gms.internal.drive.zzl;

final class zzgl
extends zzl {
    private final BaseImplementation.ResultHolder<DriveApi.DriveContentsResult> zzdx;
    private final DriveFile.DownloadProgressListener zziq;

    zzgl(BaseImplementation.ResultHolder<DriveApi.DriveContentsResult> resultHolder, DriveFile.DownloadProgressListener downloadProgressListener) {
        this.zzdx = resultHolder;
        this.zziq = downloadProgressListener;
    }

    @Override
    public final void zza(Status status) throws RemoteException {
        this.zzdx.setResult(new zzal(status, null));
    }

    @Override
    public final void zza(zzfh zzfh2) throws RemoteException {
        Status status = zzfh2.zzhv ? new Status(-1) : Status.RESULT_SUCCESS;
        this.zzdx.setResult(new zzal(status, new zzbi(zzfh2.zzes)));
    }

    @Override
    public final void zza(zzfl zzfl2) throws RemoteException {
        DriveFile.DownloadProgressListener downloadProgressListener = this.zziq;
        if (downloadProgressListener == null) return;
        downloadProgressListener.onProgress(zzfl2.zzhy, zzfl2.zzhz);
    }
}

