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
import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.internal.location.zzaj;
import com.google.android.gms.internal.location.zzaz;
import com.google.android.gms.internal.location.zzbd;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzn
extends RegisterListenerMethod<zzaz, LocationCallback> {
    private final /* synthetic */ zzbd zzy;
    private final /* synthetic */ ListenerHolder zzz;

    zzn(FusedLocationProviderClient fusedLocationProviderClient, ListenerHolder listenerHolder, zzbd zzbd2, ListenerHolder listenerHolder2) {
        this.zzy = zzbd2;
        this.zzz = listenerHolder2;
        super(listenerHolder);
    }

    @Override
    protected final /* synthetic */ void registerListener(Api.AnyClient anyClient, TaskCompletionSource object) throws RemoteException {
        anyClient = (zzaz)anyClient;
        object = new FusedLocationProviderClient.zza((TaskCompletionSource<Void>)object);
        ((zzaz)anyClient).zza(this.zzy, this.zzz, (zzaj)object);
    }
}

