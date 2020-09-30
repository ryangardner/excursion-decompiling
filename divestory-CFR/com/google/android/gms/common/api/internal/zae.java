/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.DeadObjectException
 *  android.os.RemoteException
 */
package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.api.internal.zab;
import com.google.android.gms.common.api.internal.zac;
import com.google.android.gms.common.api.internal.zaw;
import com.google.android.gms.tasks.TaskCompletionSource;

abstract class zae<T>
extends zab {
    protected final TaskCompletionSource<T> zab;

    public zae(int n, TaskCompletionSource<T> taskCompletionSource) {
        super(n);
        this.zab = taskCompletionSource;
    }

    @Override
    public void zaa(Status status) {
        this.zab.trySetException(new ApiException(status));
    }

    @Override
    public void zaa(zaw zaw2, boolean bl) {
    }

    @Override
    public void zaa(Exception exception) {
        this.zab.trySetException(exception);
    }

    @Override
    public final void zac(GoogleApiManager.zaa<?> zaa2) throws DeadObjectException {
        try {
            this.zad(zaa2);
            return;
        }
        catch (RuntimeException runtimeException) {
            ((zac)this).zaa(runtimeException);
            return;
        }
        catch (RemoteException remoteException) {
            ((zac)this).zaa(zac.zaa(remoteException));
            return;
        }
        catch (DeadObjectException deadObjectException) {
            ((zac)this).zaa(zac.zaa((RemoteException)((Object)deadObjectException)));
            throw deadObjectException;
        }
    }

    protected abstract void zad(GoogleApiManager.zaa<?> var1) throws RemoteException;
}

