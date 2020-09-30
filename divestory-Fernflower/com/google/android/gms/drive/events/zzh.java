package com.google.android.gms.drive.events;

import android.os.Looper;
import java.util.concurrent.CountDownLatch;

final class zzh extends Thread {
   // $FF: synthetic field
   private final CountDownLatch zzcn;
   // $FF: synthetic field
   private final DriveEventService zzco;

   zzh(DriveEventService var1, CountDownLatch var2) {
      this.zzco = var1;
      this.zzcn = var2;
   }

   public final void run() {
      try {
         Looper.prepare();
         DriveEventService var1 = this.zzco;
         DriveEventService.zza var2 = new DriveEventService.zza(this.zzco, (zzh)null);
         var1.zzck = var2;
         this.zzco.zzcl = false;
         this.zzcn.countDown();
         Looper.loop();
      } finally {
         if (DriveEventService.zzb(this.zzco) != null) {
            DriveEventService.zzb(this.zzco).countDown();
         }

      }

   }
}
