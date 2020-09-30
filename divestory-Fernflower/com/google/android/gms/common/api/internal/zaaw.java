package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import android.util.Log;

final class zaaw extends com.google.android.gms.internal.base.zap {
   // $FF: synthetic field
   private final zaap zaa;

   zaaw(zaap var1, Looper var2) {
      super(var2);
      this.zaa = var1;
   }

   public final void handleMessage(Message var1) {
      int var2 = var1.what;
      if (var2 != 1) {
         if (var2 != 2) {
            var2 = var1.what;
            StringBuilder var3 = new StringBuilder(31);
            var3.append("Unknown message id: ");
            var3.append(var2);
            Log.w("GoogleApiClientImpl", var3.toString());
         } else {
            zaap.zaa(this.zaa);
         }
      } else {
         zaap.zab(this.zaa);
      }
   }
}
