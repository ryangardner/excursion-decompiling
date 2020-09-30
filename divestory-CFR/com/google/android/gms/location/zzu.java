/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.IInterface
 *  android.os.RemoteException
 */
package com.google.android.gms.location;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationResult;

public interface zzu
extends IInterface {
    public void onLocationAvailability(LocationAvailability var1) throws RemoteException;

    public void onLocationResult(LocationResult var1) throws RemoteException;
}

