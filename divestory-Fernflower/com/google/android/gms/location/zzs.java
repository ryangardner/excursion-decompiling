package com.google.android.gms.location;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public class zzs extends com.google.android.gms.internal.location.zzb implements zzr {
   public static zzr zza(IBinder var0) {
      if (var0 == null) {
         return null;
      } else {
         IInterface var1 = var0.queryLocalInterface("com.google.android.gms.location.IDeviceOrientationListener");
         return (zzr)(var1 instanceof zzr ? (zzr)var1 : new zzt(var0));
      }
   }

   protected final boolean dispatchTransaction(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      throw new NoSuchMethodError();
   }
}
