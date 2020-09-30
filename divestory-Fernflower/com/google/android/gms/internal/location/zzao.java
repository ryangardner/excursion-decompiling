package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.location.Location;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.api.internal.IStatusCallback;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationSettingsRequest;

public interface zzao extends IInterface {
   Location zza(String var1) throws RemoteException;

   void zza(long var1, boolean var3, PendingIntent var4) throws RemoteException;

   void zza(PendingIntent var1, IStatusCallback var2) throws RemoteException;

   void zza(Location var1) throws RemoteException;

   void zza(zzaj var1) throws RemoteException;

   void zza(zzbf var1) throws RemoteException;

   void zza(zzo var1) throws RemoteException;

   void zza(ActivityTransitionRequest var1, PendingIntent var2, IStatusCallback var3) throws RemoteException;

   void zza(GeofencingRequest var1, PendingIntent var2, zzam var3) throws RemoteException;

   void zza(LocationSettingsRequest var1, zzaq var2, String var3) throws RemoteException;

   void zza(com.google.android.gms.location.zzal var1, zzam var2) throws RemoteException;

   void zza(boolean var1) throws RemoteException;

   LocationAvailability zzb(String var1) throws RemoteException;

   void zzb(PendingIntent var1) throws RemoteException;
}
