/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzch;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzeq;
import com.google.android.gms.internal.drive.zzhd;
import com.google.android.gms.internal.drive.zzhr;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzcn
extends TaskApiCall<zzaw, Void> {
    private final /* synthetic */ DriveResource zzfq;

    zzcn(zzch zzch2, DriveResource driveResource) {
        this.zzfq = driveResource;
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzeo)((zzaw)anyClient).getService()).zza(new zzhd(this.zzfq.getDriveId()), (zzeq)new zzhr(taskCompletionSource));
    }
}

