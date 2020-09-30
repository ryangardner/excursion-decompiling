/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.IInterface
 *  android.os.RemoteException
 */
package com.google.android.gms.dynamite;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

public interface zzk
extends IInterface {
    public int zza(IObjectWrapper var1, String var2, boolean var3) throws RemoteException;

    public IObjectWrapper zza(IObjectWrapper var1, String var2, int var3) throws RemoteException;

    public IObjectWrapper zza(IObjectWrapper var1, String var2, int var3, IObjectWrapper var4) throws RemoteException;

    public int zzb() throws RemoteException;

    public int zzb(IObjectWrapper var1, String var2, boolean var3) throws RemoteException;

    public IObjectWrapper zzb(IObjectWrapper var1, String var2, int var3) throws RemoteException;

    public IObjectWrapper zzc(IObjectWrapper var1, String var2, boolean var3) throws RemoteException;
}

