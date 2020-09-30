package com.google.android.gms.common.internal;

import android.content.Intent;
import androidx.fragment.app.Fragment;

final class zad extends zab {
   // $FF: synthetic field
   private final Intent zaa;
   // $FF: synthetic field
   private final Fragment zab;
   // $FF: synthetic field
   private final int zac;

   zad(Intent var1, Fragment var2, int var3) {
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
