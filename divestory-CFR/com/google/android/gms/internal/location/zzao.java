/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.location.Location
 *  android.os.IInterface
 *  android.os.RemoteException
 */
package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.location.Location;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.api.internal.IStatusCallback;
import com.google.android.gms.internal.location.zzaj;
import com.google.android.gms.internal.location.zzam;
import com.google.android.gms.internal.location.zzaq;
import com.google.android.gms.internal.location.zzbf;
import com.google.android.gms.internal.location.zzo;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.zzal;

public interface zzao
extends IInterface {
    public Location zza(String var1) throws RemoteException;

    public void zza(long var1, boolean var3, PendingIntent var4) throws RemoteException;

    public void zza(PendingIntent var1, IStatusCallback var2) throws RemoteException;

    public void zza(Location var1) throws RemoteException;

    public void zza(zzaj var1) throws RemoteException;

    public void zza(zzbf var1) throws RemoteException;

    public void zza(zzo var1) throws RemoteException;

    public void zza(ActivityTransitionRequest var1, PendingIntent var2, IStatusCallback var3) throws RemoteException;

    public void zza(GeofencingRequest var1, PendingIntent var2, zzam var3) throws RemoteException;

    public void zza(LocationSettingsRequest var1, zzaq var2, String var3) throws RemoteException;

    public void zza(zzal var1, zzam var2) throws RemoteException;

    public void zza(boolean var1) throws RemoteException;

    public LocationAvailability zzb(String var1) throws RemoteException;

    public void zzb(PendingIntent var1) throws RemoteException;
}

