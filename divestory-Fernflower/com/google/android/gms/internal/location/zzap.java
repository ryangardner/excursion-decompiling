package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.location.Location;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.api.internal.IStatusCallback;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationSettingsRequest;

public final class zzap extends zza implements zzao {
   zzap(IBinder var1) {
      super(var1, "com.google.android.gms.location.internal.IGoogleLocationManagerService");
   }

   public final Location zza(String var1) throws RemoteException {
      Parcel var2 = this.obtainAndWriteInterfaceToken();
      var2.writeString(var1);
      var2 = this.transactAndReadException(21, var2);
      Location var3 = (Location)zzc.zza(var2, Location.CREATOR);
      var2.recycle();
      return var3;
   }

   public final void zza(long var1, boolean var3, PendingIntent var4) throws RemoteException {
      Parcel var5 = this.obtainAndWriteInterfaceToken();
      var5.writeLong(var1);
      zzc.zza(var5, true);
      zzc.zza(var5, (Parcelable)var4);
      this.transactAndReadExceptionReturnVoid(5, var5);
   }

   public final void zza(PendingIntent var1, IStatusCallback var2) throws RemoteException {
      Parcel var3 = this.obtainAndWriteInterfaceToken();
      zzc.zza(var3, (Parcelable)var1);
      zzc.zza(var3, (IInterface)var2);
      this.transactAndReadExceptionReturnVoid(73, var3);
   }

   public final void zza(Location var1) throws RemoteException {
      Parcel var2 = this.obtainAndWriteInterfaceToken();
      zzc.zza(var2, (Parcelable)var1);
      this.transactAndReadExceptionReturnVoid(13, var2);
   }

   public final void zza(zzaj var1) throws RemoteException {
      Parcel var2 = this.obtainAndWriteInterfaceToken();
      zzc.zza(var2, (IInterface)var1);
      this.transactAndReadExceptionReturnVoid(67, var2);
   }

   public final void zza(zzbf var1) throws RemoteException {
      Parcel var2 = this.obtainAndWriteInterfaceToken();
      zzc.zza(var2, (Parcelable)var1);
      this.transactAndReadExceptionReturnVoid(59, var2);
   }

   public final void zza(zzo var1) throws RemoteException {
      Parcel var2 = this.obtainAndWriteInterfaceToken();
      zzc.zza(var2, (Parcelable)var1);
      this.transactAndReadExceptionReturnVoid(75, var2);
   }

   public final void zza(ActivityTransitionRequest var1, PendingIntent var2, IStatusCallback var3) throws RemoteException {
      Parcel var4 = this.obtainAndWriteInterfaceToken();
      zzc.zza(var4, (Parcelable)var1);
      zzc.zza(var4, (Parcelable)var2);
      zzc.zza(var4, (IInterface)var3);
      this.transactAndReadExceptionReturnVoid(72, var4);
   }

   public final void zza(GeofencingRequest var1, PendingIntent var2, zzam var3) throws RemoteException {
      Parcel var4 = this.obtainAndWriteInterfaceToken();
      zzc.zza(var4, (Parcelable)var1);
      zzc.zza(var4, (Parcelable)var2);
      zzc.zza(var4, (IInterface)var3);
      this.transactAndReadExceptionReturnVoid(57, var4);
   }

   public final void zza(LocationSettingsRequest var1, zzaq var2, String var3) throws RemoteException {
      Parcel var4 = this.obtainAndWriteInterfaceToken();
      zzc.zza(var4, (Parcelable)var1);
      zzc.zza(var4, (IInterface)var2);
      var4.writeString(var3);
      this.transactAndReadExceptionReturnVoid(63, var4);
   }

   public final void zza(com.google.android.gms.location.zzal var1, zzam var2) throws RemoteException {
      Parcel var3 = this.obtainAndWriteInterfaceToken();
      zzc.zza(var3, (Parcelable)var1);
      zzc.zza(var3, (IInterface)var2);
      this.transactAndReadExceptionReturnVoid(74, var3);
   }

   public final void zza(boolean var1) throws RemoteException {
      Parcel var2 = this.obtainAndWriteInterfaceToken();
      zzc.zza(var2, var1);
      this.transactAndReadExceptionReturnVoid(12, var2);
   }

   public final LocationAvailability zzb(String var1) throws RemoteException {
      Parcel var2 = this.obtainAndWriteInterfaceToken();
      var2.writeString(var1);
      Parcel var3 = this.transactAndReadException(34, var2);
      LocationAvailability var4 = (LocationAvailability)zzc.zza(var3, LocationAvailability.CREATOR);
      var3.recycle();
      return var4;
   }

   public final void zzb(PendingIntent var1) throws RemoteException {
      Parcel var2 = this.obtainAndWriteInterfaceToken();
      zzc.zza(var2, (Parcelable)var1);
      this.transactAndReadExceptionReturnVoid(6, var2);
   }
}
