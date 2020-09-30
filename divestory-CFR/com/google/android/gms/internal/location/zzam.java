/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.IInterface
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.os.IInterface;
import android.os.RemoteException;

public interface zzam
extends IInterface {
    public void zza(int var1, PendingIntent var2) throws RemoteException;

    public void zza(int var1, String[] var2) throws RemoteException;

    public void zzb(int var1, String[] var2) throws RemoteException;
}

