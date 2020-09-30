package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IAccountAccessor extends IInterface {
   Account zza() throws RemoteException;

   public abstract static class Stub extends com.google.android.gms.internal.common.zza implements IAccountAccessor {
      public Stub() {
         super("com.google.android.gms.common.internal.IAccountAccessor");
      }

      public static IAccountAccessor asInterface(IBinder var0) {
         if (var0 == null) {
            return null;
         } else {
            IInterface var1 = var0.queryLocalInterface("com.google.android.gms.common.internal.IAccountAccessor");
            return (IAccountAccessor)(var1 instanceof IAccountAccessor ? (IAccountAccessor)var1 : new IAccountAccessor.Stub.zza(var0));
         }
      }

      protected final boolean zza(int var1, Parcel var2, Parcel var3, int var4) throws RemoteException {
         if (var1 == 2) {
            Account var5 = this.zza();
            var3.writeNoException();
            com.google.android.gms.internal.common.zzd.zzb(var3, var5);
            return true;
         } else {
            return false;
         }
      }

      public static final class zza extends com.google.android.gms.internal.common.zzb implements IAccountAccessor {
         zza(IBinder var1) {
            super(var1, "com.google.android.gms.common.internal.IAccountAccessor");
         }

         public final Account zza() throws RemoteException {
            Parcel var1 = this.zza(2, this.a_());
            Account var2 = (Account)com.google.android.gms.internal.common.zzd.zza(var1, Account.CREATOR);
            var1.recycle();
            return var2;
         }
      }
   }
}
