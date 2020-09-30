package com.google.android.gms.dynamic;

import android.os.Bundle;
import java.util.Iterator;

final class zab implements OnDelegateCreatedListener<T> {
   // $FF: synthetic field
   private final DeferredLifecycleHelper zaa;

   zab(DeferredLifecycleHelper var1) {
      this.zaa = var1;
   }

   public final void onDelegateCreated(T var1) {
      DeferredLifecycleHelper.zaa(this.zaa, var1);
      Iterator var2 = DeferredLifecycleHelper.zaa(this.zaa).iterator();

      while(var2.hasNext()) {
         ((DeferredLifecycleHelper.zaa)var2.next()).zaa(DeferredLifecycleHelper.zab(this.zaa));
      }

      DeferredLifecycleHelper.zaa(this.zaa).clear();
      DeferredLifecycleHelper.zaa((DeferredLifecycleHelper)this.zaa, (Bundle)null);
   }
}
