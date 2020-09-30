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
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.api.internal.zaw;

public abstract class zac {
    public final int zaa;

    public zac(int n) {
        this.zaa = n;
    }

    static /* synthetic */ Status zaa(RemoteException remoteException) {
        return zac.zab(remoteException);
    }

    private static Status zab(RemoteException remoteException) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(((Object)((Object)remoteException)).getClass().getSimpleName());
        stringBuilder.append(": ");
        stringBuilder.append(remoteException.getLocalizedMessage());
        return new Status(19, stringBuilder.toString());
    }

    public abstract void zaa(Status var1);

    public abstract void zaa(zaw var1, boolean var2);

    public abstract void zaa(Exception var1);

    public abstract void zac(GoogleApiManager.zaa<?> var1) throws DeadObjectException;
}

