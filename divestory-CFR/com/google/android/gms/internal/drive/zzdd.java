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
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzch;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzeq;
import com.google.android.gms.internal.drive.zzhf;
import com.google.android.gms.internal.drive.zzhp;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzdd
extends TaskApiCall<zzaw, Metadata> {
    private final /* synthetic */ MetadataChangeSet zzfd;
    private final /* synthetic */ DriveResource zzfq;

    zzdd(zzch zzch2, MetadataChangeSet metadataChangeSet, DriveResource driveResource) {
        this.zzfd = metadataChangeSet;
        this.zzfq = driveResource;
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient, TaskCompletionSource taskCompletionSource) throws RemoteException {
        anyClient = (zzaw)anyClient;
        this.zzfd.zzq().zza(((BaseGmsClient)((Object)anyClient)).getContext());
        ((zzeo)((BaseGmsClient)((Object)anyClient)).getService()).zza(new zzhf(this.zzfq.getDriveId(), this.zzfd.zzq()), (zzeq)new zzhp(taskCompletionSource));
    }
}

