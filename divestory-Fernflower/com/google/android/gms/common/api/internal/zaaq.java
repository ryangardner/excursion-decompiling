package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import java.util.Collections;
import java.util.Iterator;
import org.checkerframework.checker.initialization.qual.NotOnlyInitialized;

public final class zaaq implements zaay {
   @NotOnlyInitialized
   private final zaax zaa;

   public zaaq(zaax var1) {
      this.zaa = var1;
   }

   public final <A extends Api.AnyClient, R extends Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T zaa(T var1) {
      this.zaa.zad.zaa.add(var1);
      return var1;
   }

   public final void zaa() {
      Iterator var1 = this.zaa.zaa.values().iterator();

      while(var1.hasNext()) {
         ((Api.Client)var1.next()).disconnect();
      }

      this.zaa.zad.zac = Collections.emptySet();
   }

   public final void zaa(int var1) {
   }

   public final void zaa(Bundle var1) {
   }

   public final void zaa(ConnectionResult var1, Api<?> var2, boolean var3) {
   }

   public final <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T zab(T var1) {
      throw new IllegalStateException("GoogleApiClient is not connected yet.");
   }

   public final boolean zab() {
      return true;
   }

   public final void zac() {
      this.zaa.zah();
   }
}
