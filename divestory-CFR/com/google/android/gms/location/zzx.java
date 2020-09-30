/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.location.Location
 *  android.os.IInterface
 *  android.os.RemoteException
 */
package com.google.android.gms.location;

import android.location.Location;
import android.os.IInterface;
import android.os.RemoteException;

public interface zzx
extends IInterface {
    public void onLocationChanged(Location var1) throws RemoteException;
}

