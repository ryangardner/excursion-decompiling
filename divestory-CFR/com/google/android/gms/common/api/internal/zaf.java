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
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.api.internal.StatusExceptionMapper;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.api.internal.zab;
import com.google.android.gms.common.api.internal.zac;
import com.google.android.gms.common.api.internal.zaw;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zaf<ResultT>
extends zab {
    private final TaskApiCall<Api.AnyClient, ResultT> zab;
    private final TaskCompletionSource<ResultT> zac;
    private final StatusExceptionMapper zad;

    public zaf(int n, TaskApiCall<Api.AnyClient, ResultT> taskApiCall, TaskCompletionSource<ResultT> taskCompletionSource, StatusExceptionMapper statusExceptionMapper) {
        super(n);
        this.zac = taskCompletionSource;
        this.zab = taskApiCall;
        this.zad = statusExceptionMapper;
        if (n != 2) return;
        if (taskApiCall.shouldAutoResolveMissingFeatures()) throw new IllegalArgumentException("Best-effort write calls cannot pass methods that should auto-resolve missing features.");
    }

    @Override
    public final void zaa(Status status) {
        this.zac.trySetException(this.zad.getException(status));
    }

    @Override
    public final void zaa(zaw zaw2, boolean bl) {
        zaw2.zaa(this.zac, bl);
    }

    @Override
    public final void zaa(Exception exception) {
        this.zac.trySetException(exception);
    }

    @Override
    public final Feature[] zaa(GoogleApiManager.zaa<?> zaa2) {
        return this.zab.zaa();
    }

    @Override
    public final boolean zab(GoogleApiManager.zaa<?> zaa2) {
        return this.zab.shouldAutoResolveMissingFeatures();
    }

    @Override
    public final void zac(GoogleApiManager.zaa<?> zaa2) throws DeadObjectException {
        try {
            this.zab.doExecute(zaa2.zab(), this.zac);
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
            throw deadObjectException;
        }
    }
}

