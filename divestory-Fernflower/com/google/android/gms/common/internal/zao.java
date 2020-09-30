package com.google.android.gms.common.internal;

import com.google.android.gms.common.api.Response;
import com.google.android.gms.common.api.Result;

final class zao implements PendingResultUtil.ResultConverter<R, T> {
   // $FF: synthetic field
   private final Response zaa;

   zao(Response var1) {
      this.zaa = var1;
   }

   // $FF: synthetic method
   public final Object convert(Result var1) {
      this.zaa.setResult(var1);
      return this.zaa;
   }
}
