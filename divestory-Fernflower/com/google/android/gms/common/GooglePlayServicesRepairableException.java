package com.google.android.gms.common;

import android.content.Intent;

public class GooglePlayServicesRepairableException extends UserRecoverableException {
   private final int zza;

   public GooglePlayServicesRepairableException(int var1, String var2, Intent var3) {
      super(var2, var3);
      this.zza = var1;
   }

   public int getConnectionStatusCode() {
      return this.zza;
   }
}
