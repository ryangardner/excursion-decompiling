package com.google.android.gms.internal.base;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public class zaa extends Binder implements IInterface {
   private static zac zaa;

   protected zaa(String var1) {
      this.attachInterface(this, var1);
   }

   public IBinder asBinder() {
      return this;
   }

   public boolean onTransact(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      boolean var5;
      if (var1 > 16777215) {
         var5 = super.onTransact(var1, var2, var3, var4);
      } else {
         var2.enforceInterface(this.getInterfaceDescriptor());
         var5 = false;
      }

      return var5 ? true : this.zaa(var1, var2, var3, var4);
   }

   protected boolean zaa(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
      return false;
   }
}
