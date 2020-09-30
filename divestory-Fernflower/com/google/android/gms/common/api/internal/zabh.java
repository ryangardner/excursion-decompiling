package com.google.android.gms.common.api.internal;

final class zabh implements Runnable {
   // $FF: synthetic field
   private final zabf zaa;

   zabh(zabf var1) {
      this.zaa = var1;
   }

   public final void run() {
      GoogleApiManager.zaa.zac(this.zaa.zaa).disconnect(String.valueOf(GoogleApiManager.zaa.zac(this.zaa.zaa).getClass().getName()).concat(" disconnecting because it was signed out."));
   }
}
