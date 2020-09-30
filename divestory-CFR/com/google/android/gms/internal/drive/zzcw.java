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
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzch;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzeq;
import com.google.android.gms.internal.drive.zzhi;
import com.google.android.gms.internal.drive.zzr;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzcw
extends TaskApiCall<zzaw, DriveContents> {
    private final /* synthetic */ int zzdv;

    zzcw(zzch zzch2, int n) {
        this.zzdv = 536870912;
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzeo)((zzaw)anyClient).getService()).zza(new zzr(this.zzdv), (zzeq)new zzhi(taskCompletionSource));
    }
}

