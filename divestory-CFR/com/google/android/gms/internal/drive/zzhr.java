/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.internal.drive.zzhh;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zzhr
extends zzhh<Void> {
    public zzhr(TaskCompletionSource<Void> taskCompletionSource) {
        super(taskCompletionSource);
    }

    @Override
    public final void onSuccess() throws RemoteException {
        TaskUtil.setResultOrApiException(Status.RESULT_SUCCESS, this.zzay());
    }
}

