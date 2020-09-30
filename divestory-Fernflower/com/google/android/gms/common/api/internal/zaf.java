package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zaf<ResultT> extends zab {
   private final TaskApiCall<Api.AnyClient, ResultT> zab;
   private final TaskCompletionSource<ResultT> zac;
   private final StatusExceptionMapper zad;

   public zaf(int var1, TaskApiCall<Api.AnyClient, ResultT> var2, TaskCompletionSource<ResultT> var3, StatusExceptionMapper var4) {
      super(var1);
      this.zac = var3;
      this.zab = var2;
      this.zad = var4;
      if (var1 == 2 && var2.shouldAutoResolveMissingFeatures()) {
         throw new IllegalArgumentException("Best-effort write calls cannot pass methods that should auto-resolve missing features.");
      }
   }

   public final void zaa(Status var1) {
      this.zac.trySetException(this.zad.getException(var1));
   }

   public final void zaa(zaw var1, boolean var2) {
      var1.zaa(this.zac, var2);
   }

   public final void zaa(Exception var1) {
      this.zac.trySetException(var1);
   }

   public final Feature[] zaa(GoogleApiManager.zaa<?> var1) {
      return this.zab.zaa();
   }

   public final boolean zab(GoogleApiManager.zaa<?> var1) {
      return this.zab.shouldAutoResolveMissingFeatures();
   }

   public final void zac(GoogleApiManager.zaa<?> var1) throws DeadObjectException {
      try {
         this.zab.doExecute(var1.zab(), this.zac);
      } catch (DeadObjectException var2) {
         throw var2;
      } catch (RemoteException var3) {
         this.zaa(com.google.android.gms.common.api.internal.zac.zaa(var3));
      } catch (RuntimeException var4) {
         this.zaa((Exception)var4);
      }
   }
}
