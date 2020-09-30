package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.Preconditions;

final class zam {
   private final int zaa;
   private final ConnectionResult zab;

   zam(ConnectionResult var1, int var2) {
      Preconditions.checkNotNull(var1);
      this.zab = var1;
      this.zaa = var2;
   }

   final int zaa() {
      return this.zaa;
   }

   final ConnectionResult zab() {
      return this.zab;
   }
}
