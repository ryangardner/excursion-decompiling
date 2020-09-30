package com.google.android.gms.common.api;

import com.google.android.gms.common.Feature;

public final class UnsupportedApiCallException extends UnsupportedOperationException {
   private final Feature zza;

   public UnsupportedApiCallException(Feature var1) {
      this.zza = var1;
   }

   public final String getMessage() {
      String var1 = String.valueOf(this.zza);
      StringBuilder var2 = new StringBuilder(String.valueOf(var1).length() + 8);
      var2.append("Missing ");
      var2.append(var1);
      return var2.toString();
   }
}
