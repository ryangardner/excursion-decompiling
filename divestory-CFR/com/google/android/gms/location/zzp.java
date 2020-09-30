/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.location;

import android.os.RemoteException;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.ApiExceptionUtil;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.internal.location.zzad;
import com.google.android.gms.internal.location.zzak;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzp
extends zzak {
    private final /* synthetic */ TaskCompletionSource zzab;

    zzp(FusedLocationProviderClient fusedLocationProviderClient, TaskCompletionSource taskCompletionSource) {
        this.zzab = taskCompletionSource;
    }

    @Override
    public final void zza(zzad abstractSafeParcelable) throws RemoteException {
        if ((abstractSafeParcelable = ((zzad)abstractSafeParcelable).getStatus()) == null) {
            this.zzab.trySetException(new ApiException(new Status(8, "Got null status from location service")));
            return;
        }
        if (((Status)abstractSafeParcelable).getStatusCode() == 0) {
            this.zzab.setResult(true);
            return;
        }
        this.zzab.trySetException(ApiExceptionUtil.fromStatus((Status)abstractSafeParcelable));
    }
}

