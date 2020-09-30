package com.google.android.gms.dynamic;

import android.os.Bundle;

final class zad implements DeferredLifecycleHelper.zaa {
   // $FF: synthetic field
   private final Bundle zaa;
   // $FF: synthetic field
   private final DeferredLifecycleHelper zab;

   zad(DeferredLifecycleHelper var1, Bundle var2) {
      this.zab = var1;
      this.zaa = var2;
   }

   public final int zaa() {
      return 1;
   }

   public final void zaa(LifecycleDelegate var1) {
      DeferredLifecycleHelper.zab(this.zab).onCreate(this.zaa);
   }
}
