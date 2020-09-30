package com.google.android.gms.common.api.internal;

final class zabc implements BackgroundDetector.BackgroundStateChangeListener {
   // $FF: synthetic field
   private final GoogleApiManager zaa;

   zabc(GoogleApiManager var1) {
      this.zaa = var1;
   }

   public final void onBackgroundStateChanged(boolean var1) {
      GoogleApiManager.zaa(this.zaa).sendMessage(GoogleApiManager.zaa(this.zaa).obtainMessage(1, var1));
   }
}
