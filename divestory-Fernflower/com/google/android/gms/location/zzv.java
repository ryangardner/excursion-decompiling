package com.google.android.gms.location;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract class zzv extends com.google.android.gms.internal.location.zzb implements zzu {
   public zzv() {
      super("com.google.android.gms.location.ILocationCallback");
   }

   public static zzu zzb(IBinder var0) {
      if (var0 == null) {
         return null;
      } else {
         IInterface var1 = var0.queryLocalInterface("com.google.android.gms.location.ILocationCallback");
         return (zzu)(var1 instanceof zzu ? (zzu)var1 : new zzw(var0));
      }
   }

   protected final boolean dispatchTransaction(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      if (var1 != 1) {
         if (var1 != 2) {
            return false;
         }

         this.onLocationAvailability((LocationAvailability)com.google.android.gms.internal.location.zzc.zza(var2, LocationAvailability.CREATOR));
      } else {
         this.onLocationResult((LocationResult)com.google.android.gms.internal.location.zzc.zza(var2, LocationResult.CREATOR));
      }

      return true;
   }
}
