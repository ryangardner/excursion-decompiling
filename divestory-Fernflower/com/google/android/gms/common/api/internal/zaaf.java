package com.google.android.gms.common.api.internal;

import android.os.Looper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.Preconditions;
import java.lang.ref.WeakReference;

final class zaaf implements BaseGmsClient.ConnectionProgressReportCallbacks {
   private final WeakReference<zaad> zaa;
   private final Api<?> zab;
   private final boolean zac;

   public zaaf(zaad var1, Api<?> var2, boolean var3) {
      this.zaa = new WeakReference(var1);
      this.zab = var2;
      this.zac = var3;
   }

   // $FF: synthetic method
   static boolean zaa(zaaf var0) {
      return var0.zac;
   }

   public final void onReportServiceBinding(ConnectionResult var1) {
      zaad var2 = (zaad)this.zaa.get();
      if (var2 != null) {
         boolean var3;
         if (Looper.myLooper() == zaad.zad(var2).zad.getLooper()) {
            var3 = true;
         } else {
            var3 = false;
         }

         Preconditions.checkState(var3, "onReportServiceBinding must be called on the GoogleApiClient handler thread");
         zaad.zac(var2).lock();

         Throwable var10000;
         label184: {
            boolean var10001;
            try {
               var3 = zaad.zaa(var2, 0);
            } catch (Throwable var15) {
               var10000 = var15;
               var10001 = false;
               break label184;
            }

            if (!var3) {
               zaad.zac(var2).unlock();
               return;
            }

            try {
               if (!var1.isSuccess()) {
                  zaad.zaa(var2, var1, this.zab, this.zac);
               }
            } catch (Throwable var14) {
               var10000 = var14;
               var10001 = false;
               break label184;
            }

            try {
               if (zaad.zal(var2)) {
                  zaad.zak(var2);
               }
            } catch (Throwable var13) {
               var10000 = var13;
               var10001 = false;
               break label184;
            }

            zaad.zac(var2).unlock();
            return;
         }

         Throwable var16 = var10000;
         zaad.zac(var2).unlock();
         throw var16;
      }
   }
}
