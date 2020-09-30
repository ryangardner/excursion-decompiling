package com.google.android.gms.internal.drive;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public class zza implements IInterface {
   private final IBinder zza;
   private final String zzb;

   protected zza(IBinder var1, String var2) {
      this.zza = var1;
      this.zzb = var2;
   }

   public IBinder asBinder() {
      return this.zza;
   }

   protected final Parcel zza() {
      Parcel var1 = Parcel.obtain();
      var1.writeInterfaceToken(this.zzb);
      return var1;
   }

   protected final Parcel zza(int var1, Parcel var2) throws RemoteException {
      Parcel var3 = Parcel.obtain();

      try {
         this.zza.transact(var1, var2, var3, 0);
         var3.readException();
      } catch (RuntimeException var7) {
         var3.recycle();
         throw var7;
      } finally {
         var2.recycle();
      }

      return var3;
   }

   protected final void zzb(int var1, Parcel var2) throws RemoteException {
      Parcel var3 = Parcel.obtain();

      try {
         this.zza.transact(var1, var2, var3, 0);
         var3.readException();
      } finally {
         var2.recycle();
         var3.recycle();
      }

   }

   protected final void zzc(int var1, Parcel var2) throws RemoteException {
      try {
         this.zza.transact(1, var2, (Parcel)null, 1);
      } finally {
         var2.recycle();
      }

   }
}
