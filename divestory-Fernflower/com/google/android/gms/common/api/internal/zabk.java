package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Result;
import org.checkerframework.checker.initialization.qual.NotOnlyInitialized;

public final class zabk<O extends Api.ApiOptions> extends zaz {
   @NotOnlyInitialized
   private final GoogleApi<O> zaa;

   public zabk(GoogleApi<O> var1) {
      super("Method is not supported by connectionless client. APIs supporting connectionless client must not call this method.");
      this.zaa = var1;
   }

   public final <A extends Api.AnyClient, R extends Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T enqueue(T var1) {
      return this.zaa.doRead(var1);
   }

   public final <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T execute(T var1) {
      return this.zaa.doWrite(var1);
   }

   public final Context getContext() {
      return this.zaa.getApplicationContext();
   }

   public final Looper getLooper() {
      return this.zaa.getLooper();
   }

   public final void zaa(zack var1) {
   }

   public final void zab(zack var1) {
   }
}
