/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.common.api.internal.RegistrationMethods;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zabw
extends RegisterListenerMethod<A, L> {
    private final /* synthetic */ RegistrationMethods.Builder zaa;

    zabw(RegistrationMethods.Builder builder, ListenerHolder listenerHolder, Feature[] arrfeature, boolean bl) {
        this.zaa = builder;
        super(listenerHolder, arrfeature, bl);
    }

    @Override
    protected final void registerListener(A a, TaskCompletionSource<Void> taskCompletionSource) throws RemoteException {
        RegistrationMethods.Builder.zaa(this.zaa).accept(a, taskCompletionSource);
    }
}

