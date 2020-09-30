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
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzch;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzeq;
import com.google.android.gms.internal.drive.zzhr;
import com.google.android.gms.internal.drive.zzo;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzda
extends TaskApiCall<zzaw, Void> {
    private final /* synthetic */ DriveContents zzfx;

    zzda(zzch zzch2, DriveContents driveContents) {
        this.zzfx = driveContents;
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzeo)((zzaw)anyClient).getService()).zza(new zzo(this.zzfx.zzi().getRequestId(), false), (zzeq)new zzhr(taskCompletionSource));
    }
}

