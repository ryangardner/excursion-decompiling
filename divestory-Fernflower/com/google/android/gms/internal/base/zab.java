package com.google.android.gms.internal.base;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public class zab implements IInterface {
   private final IBinder zaa;
   private final String zab;

   protected zab(IBinder var1, String var2) {
      this.zaa = var1;
      this.zab = var2;
   }

   public IBinder asBinder() {
      return this.zaa;
   }

   protected final Parcel zaa() {
      Parcel var1 = Parcel.obtain();
      var1.writeInterfaceToken(this.zab);
      return var1;
   }

   protected final Parcel zaa(int var1, Parcel var2) throws RemoteException {
      Parcel var3 = Parcel.obtain();

      try {
         this.zaa.transact(2, var2, var3, 0);
         var3.readException();
      } catch (RuntimeException var7) {
         var3.recycle();
         throw var7;
      } finally {
         var2.recycle();
      }

      return var3;
   }

   protected final void zab(int var1, Parcel var2) throws RemoteException {
      Parcel var3 = Parcel.obtain();

      try {
         this.zaa.transact(var1, var2, var3, 0);
         var3.readException();
      } finally {
         var2.recycle();
         var3.recycle();
      }

   }

   protected final void zac(int var1, Parcel var2) throws RemoteException {
      try {
         this.zaa.transact(1, var2, (Parcel)null, 1);
      } finally {
         var2.recycle();
      }

   }
}
