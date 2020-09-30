package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zah extends zae<Boolean> {
   private final ListenerHolder.ListenerKey<?> zac;

   public zah(ListenerHolder.ListenerKey<?> var1, TaskCompletionSource<Boolean> var2) {
      super(4, var2);
      this.zac = var1;
   }

   public final Feature[] zaa(GoogleApiManager.zaa<?> var1) {
      zabs var2 = (zabs)var1.zac().get(this.zac);
      return var2 == null ? null : var2.zaa.getRequiredFeatures();
   }

   public final boolean zab(GoogleApiManager.zaa<?> var1) {
      zabs var2 = (zabs)var1.zac().get(this.zac);
      return var2 != null && var2.zaa.zaa();
   }

   public final void zad(GoogleApiManager.zaa<?> var1) throws RemoteException {
      zabs var2 = (zabs)var1.zac().remove(this.zac);
      if (var2 != null) {
         var2.zab.unregisterListener(var1.zab(), this.zab);
         var2.zaa.clearListener();
      } else {
         this.zab.trySetResult(false);
      }
   }
}
