/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.drive;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.internal.ICancelToken;
import com.google.android.gms.drive.events.ListenerToken;

public final class zzg
implements ListenerToken {
    private final ListenerHolder.ListenerKey zzcy;
    private ICancelToken zzcz = null;

    public zzg(ListenerHolder.ListenerKey listenerKey) {
        this.zzcy = listenerKey;
    }

    public final boolean cancel() {
        ICancelToken iCancelToken = this.zzcz;
        if (iCancelToken == null) return false;
        try {
            iCancelToken.cancel();
            return true;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    public final void setCancelToken(ICancelToken iCancelToken) {
        this.zzcz = iCancelToken;
    }

    public final ListenerHolder.ListenerKey zzad() {
        return this.zzcy;
    }
}

