/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.location;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.UnregisterListenerMethod;
import com.google.android.gms.internal.location.zzaj;
import com.google.android.gms.internal.location.zzaz;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzo
extends UnregisterListenerMethod<zzaz, LocationCallback> {
    private final /* synthetic */ FusedLocationProviderClient zzaa;

    zzo(FusedLocationProviderClient fusedLocationProviderClient, ListenerHolder.ListenerKey listenerKey) {
        this.zzaa = fusedLocationProviderClient;
        super(listenerKey);
    }

    @Override
    protected final /* synthetic */ void unregisterListener(Api.AnyClient object, TaskCompletionSource taskCompletionSource) throws RemoteException {
        zzaz zzaz2 = (zzaz)object;
        object = FusedLocationProviderClient.zza(this.zzaa, taskCompletionSource);
        try {
            zzaz2.zzb(this.getListenerKey(), (zzaj)object);
            return;
        }
        catch (RuntimeException runtimeException) {
            taskCompletionSource.trySetException(runtimeException);
            return;
        }
    }
}

