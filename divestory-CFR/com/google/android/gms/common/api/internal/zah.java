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
import com.google.android.gms.common.api.internal.UnregisterListenerMethod;
import com.google.android.gms.common.api.internal.zabs;
import com.google.android.gms.common.api.internal.zae;
import com.google.android.gms.common.api.internal.zaw;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Map;

public final class zah
extends zae<Boolean> {
    private final ListenerHolder.ListenerKey<?> zac;

    public zah(ListenerHolder.ListenerKey<?> listenerKey, TaskCompletionSource<Boolean> taskCompletionSource) {
        super(4, taskCompletionSource);
        this.zac = listenerKey;
    }

    @Override
    public final Feature[] zaa(GoogleApiManager.zaa<?> object) {
        if ((object = ((GoogleApiManager.zaa)object).zac().get(this.zac)) != null) return ((zabs)object).zaa.getRequiredFeatures();
        return null;
    }

    @Override
    public final boolean zab(GoogleApiManager.zaa<?> object) {
        if ((object = ((GoogleApiManager.zaa)object).zac().get(this.zac)) == null) return false;
        if (!((zabs)object).zaa.zaa()) return false;
        return true;
    }

    @Override
    public final void zad(GoogleApiManager.zaa<?> zaa2) throws RemoteException {
        zabs zabs2 = zaa2.zac().remove(this.zac);
        if (zabs2 != null) {
            zabs2.zab.unregisterListener(zaa2.zab(), this.zab);
            zabs2.zaa.clearListener();
            return;
        }
        this.zab.trySetResult(false);
    }
}

