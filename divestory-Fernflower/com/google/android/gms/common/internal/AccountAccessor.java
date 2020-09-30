package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Binder;
import android.os.RemoteException;
import android.util.Log;

public class AccountAccessor extends IAccountAccessor.Stub {
   public static Account getAccountBinderSafe(IAccountAccessor var0) {
      Account var7;
      if (var0 != null) {
         long var1 = Binder.clearCallingIdentity();

         try {
            var7 = var0.zza();
            return var7;
         } catch (RemoteException var5) {
            Log.w("AccountAccessor", "Remote account accessor probably died");
         } finally {
            Binder.restoreCallingIdentity(var1);
         }
      }

      var7 = null;
      return var7;
   }

   public boolean equals(Object var1) {
      throw new NoSuchMethodError();
   }

   public final Account zza() {
      throw new NoSuchMethodError();
   }
}
