package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

public abstract class zzo extends com.google.android.gms.internal.common.zza implements zzm {
   public zzo() {
      super("com.google.android.gms.common.internal.ICertData");
   }

   public static zzm zza(IBinder var0) {
      if (var0 == null) {
         return null;
      } else {
         IInterface var1 = var0.queryLocalInterface("com.google.android.gms.common.internal.ICertData");
         return (zzm)(var1 instanceof zzm ? (zzm)var1 : new zzn(var0));
      }
   }

   protected final boolean zza(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      if (var1 != 1) {
         if (var1 != 2) {
            return false;
         }

         var1 = this.zzc();
         var3.writeNoException();
         var3.writeInt(var1);
      } else {
         IObjectWrapper var5 = this.zzb();
         var3.writeNoException();
         com.google.android.gms.internal.common.zzd.zza(var3, (IInterface)var5);
      }

      return true;
   }
}
