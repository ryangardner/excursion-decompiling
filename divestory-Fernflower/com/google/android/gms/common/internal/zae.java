package com.google.android.gms.common.internal;

import android.app.Activity;
import android.content.Intent;

final class zae extends zab {
   // $FF: synthetic field
   private final Intent zaa;
   // $FF: synthetic field
   private final Activity zab;
   // $FF: synthetic field
   private final int zac;

   zae(Intent var1, Activity var2, int var3) {
      this.zaa = var1;
      this.zab = var2;
      this.zac = var3;
   }

   public final void zaa() {
      Intent var1 = this.zaa;
      if (var1 != null) {
         this.zab.startActivityForResult(var1, this.zac);
      }

   }
}
