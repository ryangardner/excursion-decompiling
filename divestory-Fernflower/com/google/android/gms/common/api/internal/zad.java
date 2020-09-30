package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.util.Log;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;

public final class zad<A extends BaseImplementation.ApiMethodImpl<? extends Result, Api.AnyClient>> extends zac {
   private final A zab;

   public zad(int var1, A var2) {
      super(var1);
      this.zab = (BaseImplementation.ApiMethodImpl)Preconditions.checkNotNull(var2, "Null methods are not runnable.");
   }

   public final void zaa(Status var1) {
      try {
         this.zab.setFailedResult(var1);
      } catch (IllegalStateException var2) {
         Log.w("ApiCallRunner", "Exception reporting failure", var2);
      }
   }

   public final void zaa(zaw var1, boolean var2) {
      var1.zaa((BasePendingResult)this.zab, var2);
   }

   public final void zaa(Exception var1) {
      String var2 = var1.getClass().getSimpleName();
      String var5 = var1.getLocalizedMessage();
      StringBuilder var3 = new StringBuilder(String.valueOf(var2).length() + 2 + String.valueOf(var5).length());
      var3.append(var2);
      var3.append(": ");
      var3.append(var5);
      Status var6 = new Status(10, var3.toString());

      try {
         this.zab.setFailedResult(var6);
      } catch (IllegalStateException var4) {
         Log.w("ApiCallRunner", "Exception reporting failure", var4);
      }
   }

   public final void zac(GoogleApiManager.zaa<?> var1) throws DeadObjectException {
      try {
         this.zab.run(var1.zab());
      } catch (RuntimeException var2) {
         this.zaa((Exception)var2);
      }
   }
}
