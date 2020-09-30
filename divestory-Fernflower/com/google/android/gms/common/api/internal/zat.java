package com.google.android.gms.common.api.internal;

final class zat implements Runnable {
   // $FF: synthetic field
   private final zaq zaa;

   zat(zaq var1) {
      this.zaa = var1;
   }

   public final void run() {
      zaq.zaa(this.zaa).lock();

      try {
         zaq.zab(this.zaa);
      } finally {
         zaq.zaa(this.zaa).unlock();
      }

   }
}
