package com.google.android.gms.common.api.internal;

import java.lang.ref.WeakReference;

final class zaav extends zabl {
   private WeakReference<zaap> zaa;

   zaav(zaap var1) {
      this.zaa = new WeakReference(var1);
   }

   public final void zaa() {
      zaap var1 = (zaap)this.zaa.get();
      if (var1 != null) {
         zaap.zaa(var1);
      }
   }
}
