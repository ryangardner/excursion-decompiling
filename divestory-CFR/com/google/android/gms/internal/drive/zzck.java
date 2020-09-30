/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.internal.drive.zzaw;
import com.google.android.gms.internal.drive.zzbs;
import com.google.android.gms.internal.drive.zzch;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzck
extends TaskApiCall<zzaw, DriveFolder> {
    zzck(zzch zzch2) {
    }

    @Override
    protected final /* synthetic */ void doExecute(Api.AnyClient anyClient, TaskCompletionSource taskCompletionSource) throws RemoteException {
        if (((zzaw)(anyClient = (zzaw)anyClient)).zzae() == null) {
            taskCompletionSource.setException(new ApiException(new Status(10, "Drive#SCOPE_FILE must be requested")));
            return;
        }
        taskCompletionSource.setResult(new zzbs(((zzaw)anyClient).zzae()));
    }
}

