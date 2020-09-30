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
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzch;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzeq;
import com.google.android.gms.internal.drive.zzes;
import com.google.android.gms.internal.drive.zzhr;
import com.google.android.gms.internal.drive.zzj;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzcr
extends TaskApiCall<zzaw, Void> {
    private final /* synthetic */ DriveResource zzfq;

    zzcr(zzch zzch2, DriveResource driveResource) {
        this.zzfq = driveResource;
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient, TaskCompletionSource taskCompletionSource) throws RemoteException {
        anyClient = (zzaw)anyClient;
        if (!((zzaw)anyClient).zzec) throw new IllegalStateException("Application must define an exported DriveEventService subclass in AndroidManifest.xml to add event subscriptions");
        ((zzeo)((BaseGmsClient)((Object)anyClient)).getService()).zza(new zzj(1, this.zzfq.getDriveId()), null, null, (zzeq)new zzhr(taskCompletionSource));
    }
}

