/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.api.internal.zacf;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zacg
extends TaskApiCall<A, ResultT> {
    private final /* synthetic */ TaskApiCall.Builder zaa;

    zacg(TaskApiCall.Builder builder, Feature[] arrfeature, boolean bl, int n) {
        this.zaa = builder;
        super(arrfeature, bl, n, null);
    }

    @Override
    protected final void doExecute(A a, TaskCompletionSource<ResultT> taskCompletionSource) throws RemoteException {
        TaskApiCall.Builder.zaa(this.zaa).accept(a, taskCompletionSource);
    }
}

