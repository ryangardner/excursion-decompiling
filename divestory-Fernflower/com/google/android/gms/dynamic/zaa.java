package com.google.android.gms.dynamic;

import android.app.Activity;
import android.os.Bundle;

final class zaa implements DeferredLifecycleHelper.zaa {
   // $FF: synthetic field
   private final Activity zaa;
   // $FF: synthetic field
   private final Bundle zab;
   // $FF: synthetic field
   private final Bundle zac;
   // $FF: synthetic field
   private final DeferredLifecycleHelper zad;

   zaa(DeferredLifecycleHelper var1, Activity var2, Bundle var3, Bundle var4) {
      this.zad = var1;
      this.zaa = var2;
      this.zab = var3;
      this.zac = var4;
   }

   public final int zaa() {
      return 0;
   }

   public final void zaa(LifecycleDelegate var1) {
      DeferredLifecycleHelper.zab(this.zad).onInflate(this.zaa, this.zab, this.zac);
   }
}
