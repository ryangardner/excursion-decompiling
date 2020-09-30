/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.drive;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.drive.zzn;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzch;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzeq;
import com.google.android.gms.internal.drive.zzhr;
import com.google.android.gms.internal.drive.zzm;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzcy
extends TaskApiCall<zzaw, Void> {
    private final /* synthetic */ MetadataChangeSet zzew;
    private final /* synthetic */ DriveContents zzfx;
    private final /* synthetic */ zzn zzfy;

    zzcy(zzch zzch2, zzn zzn2, DriveContents driveContents, MetadataChangeSet metadataChangeSet) {
        this.zzfy = zzn2;
        this.zzfx = driveContents;
        this.zzew = metadataChangeSet;
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient, TaskCompletionSource taskCompletionSource) throws RemoteException {
        anyClient = (zzaw)anyClient;
        try {
            this.zzfy.zza((zzaw)anyClient);
        }
        catch (IllegalStateException illegalStateException) {
            taskCompletionSource.setException(illegalStateException);
        }
        this.zzfx.zzj();
        this.zzew.zzq().zza(((BaseGmsClient)((Object)anyClient)).getContext());
        ((zzeo)((BaseGmsClient)((Object)anyClient)).getService()).zza(new zzm(this.zzfx.getDriveId(), this.zzew.zzq(), this.zzfx.zzi().getRequestId(), this.zzfx.zzi().zzb(), this.zzfy), (zzeq)new zzhr(taskCompletionSource));
    }
}

