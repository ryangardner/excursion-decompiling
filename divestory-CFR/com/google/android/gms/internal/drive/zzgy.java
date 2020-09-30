/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.internal.drive.zzl;

public final class zzgy
extends zzl {
    private final BaseImplementation.ResultHolder<Status> zzdx;

    public zzgy(BaseImplementation.ResultHolder<Status> resultHolder) {
        this.zzdx = resultHolder;
    }

    @Override
    public final void onSuccess() throws RemoteException {
        this.zzdx.setResult(Status.RESULT_SUCCESS);
    }

    @Override
    public final void zza(Status status) throws RemoteException {
        this.zzdx.setResult(status);
    }
}

