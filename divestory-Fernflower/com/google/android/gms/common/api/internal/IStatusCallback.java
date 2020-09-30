package com.google.android.gms.common.api.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;

public interface IStatusCallback extends IInterface {
   void onResult(Status var1) throws RemoteException;

   public abstract static class Stub extends com.google.android.gms.internal.base.zaa implements IStatusCallback {
      public Stub() {
         super("com.google.android.gms.common.api.internal.IStatusCallback");
      }

      public static IStatusCallback asInterface(IBinder var0) {
         if (var0 == null) {
            return null;
         } else {
            IInterface var1 = var0.queryLocalInterface("com.google.android.gms.common.api.internal.IStatusCallback");
            return (IStatusCallback)(var1 instanceof IStatusCallback ? (IStatusCallback)var1 : new IStatusCallback.Stub.zaa(var0));
         }
      }

      protected final boolean zaa(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
         if (var1 == 1) {
            this.onResult((Status)com.google.android.gms.internal.base.zad.zaa(var2, Status.CREATOR));
            return true;
         } else {
            return false;
         }
      }

      public static final class zaa extends com.google.android.gms.internal.base.zab implements IStatusCallback {
         zaa(IBinder var1) {
            super(var1, "com.google.android.gms.common.api.internal.IStatusCallback");
         }

         public final void onResult(Status var1) throws RemoteException {
            Parcel var2 = this.zaa();
            com.google.android.gms.internal.base.zad.zaa(var2, (Parcelable)var1);
            this.zac(1, var2);
         }
      }
   }
}
