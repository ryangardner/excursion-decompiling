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
import com.google.android.gms.internal.drive.zzes;
import com.google.android.gms.internal.drive.zzgs;
import com.google.android.gms.internal.drive.zzhr;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzcs
extends TaskApiCall<zzaw, Void> {
    private final /* synthetic */ DriveResource zzfq;

    zzcs(zzch zzch2, DriveResource driveResource) {
        this.zzfq = driveResource;
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzeo)((zzaw)anyClient).getService()).zza(new zzgs(this.zzfq.getDriveId(), 1), null, null, (zzeq)new zzhr(taskCompletionSource));
    }
}

