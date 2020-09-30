package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.TaskCompletionSource;

// $FF: synthetic class
final class zabx implements RemoteCall {
   private final RegistrationMethods.Builder zaa;

   zabx(RegistrationMethods.Builder var1) {
      this.zaa = var1;
   }

   public final void accept(Object var1, Object var2) {
      this.zaa.zaa((Api.AnyClient)var1, (TaskCompletionSource)var2);
   }
}
