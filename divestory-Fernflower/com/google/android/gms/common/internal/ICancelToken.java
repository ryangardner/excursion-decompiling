package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

public interface ICancelToken extends IInterface {
   void cancel() throws RemoteException;

   public abstract static class Stub extends com.google.android.gms.internal.common.zza implements ICancelToken {
      public Stub() {
         super("com.google.android.gms.common.internal.ICancelToken");
      }

      public static ICancelToken asInterface(IBinder var0) {
         if (var0 == null) {
            return null;
         } else {
            IInterface var1 = var0.queryLocalInterface("com.google.android.gms.common.internal.ICancelToken");
            return (ICancelToken)(var1 instanceof ICancelToken ? (ICancelToken)var1 : new ICancelToken.Stub.zza(var0));
         }
      }

      public static final class zza extends com.google.android.gms.internal.common.zzb implements ICancelToken {
         zza(IBinder var1) {
            super(var1, "com.google.android.gms.common.internal.ICancelToken");
         }

         public final void cancel() throws RemoteException {
            this.zzc(2, this.a_());
         }
      }
   }
}
