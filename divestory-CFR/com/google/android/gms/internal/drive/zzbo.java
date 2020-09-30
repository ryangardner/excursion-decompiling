/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.drive;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.internal.ICancelToken;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.internal.drive.zzam;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzbn;
import com.google.android.gms.internal.drive.zzec;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzeq;
import com.google.android.gms.internal.drive.zzgj;
import com.google.android.gms.internal.drive.zzgl;

final class zzbo
extends zzam {
    private final /* synthetic */ int zzdv;
    private final /* synthetic */ DriveFile.DownloadProgressListener zzey;
    private final /* synthetic */ zzbn zzez;

    zzbo(zzbn zzbn2, GoogleApiClient googleApiClient, int n, DriveFile.DownloadProgressListener downloadProgressListener) {
        this.zzez = zzbn2;
        this.zzdv = n;
        this.zzey = downloadProgressListener;
        super(googleApiClient);
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        this.setCancelToken(ICancelToken.Stub.asInterface(((zzeo)((zzaw)anyClient).getService()).zza((zzgj)new zzgj((DriveId)this.zzez.getDriveId(), (int)this.zzdv, (int)0), (zzeq)new zzgl((BaseImplementation.ResultHolder<DriveApi.DriveContentsResult>)this, (DriveFile.DownloadProgressListener)this.zzey)).zzgs));
    }
}

