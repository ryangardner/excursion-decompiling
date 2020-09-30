/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 */
package com.google.android.gms.common.api.internal;

import android.os.RemoteException;

public interface RemoteCall<T, U> {
    public void accept(T var1, U var2) throws RemoteException;
}

