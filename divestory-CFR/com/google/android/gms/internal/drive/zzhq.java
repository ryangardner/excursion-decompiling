/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.internal.drive.zzhh;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zzhq
extends zzhh<Boolean> {
    public zzhq(TaskCompletionSource<Boolean> taskCompletionSource) {
        super(taskCompletionSource);
    }

    @Override
    public final void onSuccess() throws RemoteException {
        this.zzay().setResult(true);
    }
}

