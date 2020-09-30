package com.google.android.gms.common.api.internal;

import android.os.IBinder;

// $FF: synthetic class
final class zabp implements Runnable {
   private final NonGmsServiceBrokerClient zaa;
   private final IBinder zab;

   zabp(NonGmsServiceBrokerClient var1, IBinder var2) {
      this.zaa = var1;
      this.zab = var2;
   }

   public final void run() {
      this.zaa.zaa(this.zab);
   }
}
