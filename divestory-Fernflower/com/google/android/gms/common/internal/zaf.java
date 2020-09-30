package com.google.android.gms.common.internal;

import android.content.Intent;
import com.google.android.gms.common.api.internal.LifecycleFragment;

final class zaf extends zab {
   // $FF: synthetic field
   private final Intent zaa;
   // $FF: synthetic field
   private final LifecycleFragment zab;
   // $FF: synthetic field
   private final int zac;

   zaf(Intent var1, LifecycleFragment var2, int var3) {
      this.zaa = var1;
      this.zab = var2;
      this.zac = 2;
      super();
   }

   public final void zaa() {
      Intent var1 = this.zaa;
      if (var1 != null) {
         this.zab.startActivityForResult(var1, this.zac);
      }

   }
}
