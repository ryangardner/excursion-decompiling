package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.Feature;
import com.google.android.gms.tasks.TaskCompletionSource;

public final class zag extends zae<Void> {
   private final zabs zac;

   public zag(zabs var1, TaskCompletionSource<Void> var2) {
      super(3, var2);
      this.zac = var1;
   }

   public final Feature[] zaa(GoogleApiManager.zaa<?> var1) {
      return this.zac.zaa.getRequiredFeatures();
   }

   public final boolean zab(GoogleApiManager.zaa<?> var1) {
      return this.zac.zaa.zaa();
   }

   public final void zad(GoogleApiManager.zaa<?> var1) throws RemoteException {
      this.zac.zaa.registerListener(var1.zab(), this.zab);
      ListenerHolder.ListenerKey var2 = this.zac.zaa.getListenerKey();
      if (var2 != null) {
         var1.zac().put(var2, this.zac);
      }

   }
}
