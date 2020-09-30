package com.google.android.gms.common.api.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public final class zabj extends BroadcastReceiver {
   private Context zaa;
   private final zabl zab;

   public zabj(zabl var1) {
      this.zab = var1;
   }

   public final void onReceive(Context var1, Intent var2) {
      Uri var3 = var2.getData();
      String var4;
      if (var3 != null) {
         var4 = var3.getSchemeSpecificPart();
      } else {
         var4 = null;
      }

      if ("com.google.android.gms".equals(var4)) {
         this.zab.zaa();
         this.zaa();
      }

   }

   public final void zaa() {
      synchronized(this){}

      try {
         if (this.zaa != null) {
            this.zaa.unregisterReceiver(this);
         }

         this.zaa = null;
      } finally {
         ;
      }

   }

   public final void zaa(Context var1) {
      this.zaa = var1;
   }
}
