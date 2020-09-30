package com.google.android.gms.common;

import android.content.Intent;

public class UserRecoverableException extends Exception {
   private final Intent zza;

   public UserRecoverableException(String var1, Intent var2) {
      super(var1);
      this.zza = var2;
   }

   public Intent getIntent() {
      return new Intent(this.zza);
   }
}
