package com.google.android.gms.common.api.internal;

final class zabd implements Runnable {
   // $FF: synthetic field
   private final int zaa;
   // $FF: synthetic field
   private final GoogleApiManager.zaa zab;

   zabd(GoogleApiManager.zaa var1, int var2) {
      this.zab = var1;
      this.zaa = var2;
   }

   public final void run() {
      GoogleApiManager.zaa.zaa(this.zab, this.zaa);
   }
}
