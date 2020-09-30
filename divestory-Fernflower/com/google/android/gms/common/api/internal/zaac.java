package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.os.DeadObjectException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Iterator;
import java.util.Set;

public final class zaac implements zaay {
   private final zaax zaa;
   private boolean zab = false;

   public zaac(zaax var1) {
      this.zaa = var1;
   }

   // $FF: synthetic method
   static zaax zaa(zaac var0) {
      return var0.zaa;
   }

   public final <A extends Api.AnyClient, R extends Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T zaa(T var1) {
      return this.zab(var1);
   }

   public final void zaa() {
   }

   public final void zaa(int var1) {
      this.zaa.zaa((ConnectionResult)null);
      this.zaa.zae.zaa(var1, this.zab);
   }

   public final void zaa(Bundle var1) {
   }

   public final void zaa(ConnectionResult var1, Api<?> var2, boolean var3) {
   }

   public final <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T zab(T var1) {
      try {
         this.zaa.zad.zae.zaa(var1);
         zaap var2 = this.zaa.zad;
         Api.AnyClientKey var3 = var1.getClientKey();
         Api.Client var5 = (Api.Client)var2.zab.get(var3);
         Preconditions.checkNotNull(var5, "Appropriate Api was not requested.");
         if (!var5.isConnected() && this.zaa.zab.containsKey(var1.getClientKey())) {
            Status var6 = new Status(17);
            var1.setFailedResult(var6);
         } else {
            var1.run(var5);
         }
      } catch (DeadObjectException var4) {
         this.zaa.zaa((zaba)(new zaab(this, this)));
      }

      return var1;
   }

   public final boolean zab() {
      if (this.zab) {
         return false;
      } else {
         Set var1 = this.zaa.zad.zad;
         if (var1 != null && !var1.isEmpty()) {
            this.zab = true;
            Iterator var2 = var1.iterator();

            while(var2.hasNext()) {
               ((zack)var2.next()).zaa();
            }

            return false;
         } else {
            this.zaa.zaa((ConnectionResult)null);
            return true;
         }
      }
   }

   public final void zac() {
      if (this.zab) {
         this.zab = false;
         this.zaa.zaa((zaba)(new zaae(this, this)));
      }

   }

   final void zad() {
      if (this.zab) {
         this.zab = false;
         this.zaa.zad.zae.zaa();
         this.zab();
      }

   }
}
