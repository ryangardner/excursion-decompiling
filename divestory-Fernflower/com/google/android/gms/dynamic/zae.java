package com.google.android.gms.dynamic;

final class zae implements DeferredLifecycleHelper.zaa {
   // $FF: synthetic field
   private final DeferredLifecycleHelper zaa;

   zae(DeferredLifecycleHelper var1) {
      this.zaa = var1;
   }

   public final int zaa() {
      return 4;
   }

   public final void zaa(LifecycleDelegate var1) {
      DeferredLifecycleHelper.zab(this.zaa).onStart();
   }
}
