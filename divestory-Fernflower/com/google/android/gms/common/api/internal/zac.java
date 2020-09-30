package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;

public abstract class zac {
   public final int zaa;

   public zac(int var1) {
      this.zaa = var1;
   }

   // $FF: synthetic method
   static Status zaa(RemoteException var0) {
      return zab(var0);
   }

   private static Status zab(RemoteException var0) {
      StringBuilder var1 = new StringBuilder();
      var1.append(var0.getClass().getSimpleName());
      var1.append(": ");
      var1.append(var0.getLocalizedMessage());
      return new Status(19, var1.toString());
   }

   public abstract void zaa(Status var1);

   public abstract void zaa(zaw var1, boolean var2);

   public abstract void zaa(Exception var1);

   public abstract void zac(GoogleApiManager.zaa<?> var1) throws DeadObjectException;
}
