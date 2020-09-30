package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.internal.BaseGmsClient;

final class zabf implements BaseGmsClient.SignOutCallbacks {
   // $FF: synthetic field
   final GoogleApiManager.zaa zaa;

   zabf(GoogleApiManager.zaa var1) {
      this.zaa = var1;
   }

   public final void onSignOutComplete() {
      GoogleApiManager.zaa(this.zaa.zaa).post(new zabh(this));
   }
}
