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
import com.google.android.gms.drive.TransferPreferences;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzbb;
import com.google.android.gms.internal.drive.zzei;
import com.google.android.gms.internal.drive.zzeo;
import com.google.android.gms.internal.drive.zzeq;
import com.google.android.gms.internal.drive.zzgu;
import com.google.android.gms.internal.drive.zzhr;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzbe
extends TaskApiCall<zzaw, Void> {
    private final /* synthetic */ TransferPreferences zzep;

    zzbe(zzbb zzbb2, TransferPreferences transferPreferences) {
        this.zzep = transferPreferences;
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzeo)((zzaw)anyClient).getService()).zza(new zzgu(new zzei(this.zzep)), (zzeq)new zzhr(taskCompletionSource));
    }
}

