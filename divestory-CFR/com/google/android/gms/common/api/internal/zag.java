/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.common.api.internal.zabs;
import com.google.android.gms.common.api.internal.zae;
import com.google.android.gms.common.api.internal.zaw;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Map;

public final class zag
extends zae<Void> {
    private final zabs zac;

    public zag(zabs zabs2, TaskCompletionSource<Void> taskCompletionSource) {
        super(3, taskCompletionSource);
        this.zac = zabs2;
    }

    @Override
    public final Feature[] zaa(GoogleApiManager.zaa<?> zaa2) {
        return this.zac.zaa.getRequiredFeatures();
    }

    @Override
    public final boolean zab(GoogleApiManager.zaa<?> zaa2) {
        return this.zac.zaa.zaa();
    }

    @Override
    public final void zad(GoogleApiManager.zaa<?> zaa2) throws RemoteException {
        this.zac.zaa.registerListener(zaa2.zab(), this.zab);
        ListenerHolder.ListenerKey<?> listenerKey = this.zac.zaa.getListenerKey();
        if (listenerKey == null) return;
        zaa2.zac().put(listenerKey, this.zac);
    }
}

