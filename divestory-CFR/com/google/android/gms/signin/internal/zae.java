/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.IInterface
 *  android.os.RemoteException
 */
package com.google.android.gms.signin.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.signin.internal.zac;
import com.google.android.gms.signin.internal.zak;

public interface zae
extends IInterface {
    public void zaa(int var1) throws RemoteException;

    public void zaa(IAccountAccessor var1, int var2, boolean var3) throws RemoteException;

    public void zaa(zak var1, zac var2) throws RemoteException;
}

