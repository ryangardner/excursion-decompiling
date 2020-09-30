package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;

final class zav implements PendingResult.StatusListener {
   // $FF: synthetic field
   private final BasePendingResult zaa;
   // $FF: synthetic field
   private final zaw zab;

   zav(zaw var1, BasePendingResult var2) {
      this.zab = var1;
      this.zaa = var2;
   }

   public final void onComplete(Status var1) {
      zaw.zaa(this.zab).remove(this.zaa);
   }
}
