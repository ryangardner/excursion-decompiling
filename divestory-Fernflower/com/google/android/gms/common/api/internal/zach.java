package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.util.BiConsumer;
import com.google.android.gms.tasks.TaskCompletionSource;

// $FF: synthetic class
final class zach implements RemoteCall {
   private final BiConsumer zaa;

   zach(BiConsumer var1) {
      this.zaa = var1;
   }

   public final void accept(Object var1, Object var2) {
      this.zaa.accept((Api.AnyClient)var1, (TaskCompletionSource)var2);
   }
}
