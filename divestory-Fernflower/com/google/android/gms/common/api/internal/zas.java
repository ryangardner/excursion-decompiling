package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;

final class zas implements zabm {
   // $FF: synthetic field
   private final zaq zaa;

   private zas(zaq var1) {
      this.zaa = var1;
   }

   // $FF: synthetic method
   zas(zaq var1, zat var2) {
      this(var1);
   }

   public final void zaa(int var1, boolean var2) {
      zaq.zaa(this.zaa).lock();

      Throwable var10000;
      label166: {
         boolean var10001;
         label169: {
            try {
               if (!zaq.zac(this.zaa) && zaq.zad(this.zaa) != null && zaq.zad(this.zaa).isSuccess()) {
                  break label169;
               }
            } catch (Throwable var15) {
               var10000 = var15;
               var10001 = false;
               break label166;
            }

            try {
               zaq.zaa(this.zaa, false);
               zaq.zaa(this.zaa, var1, var2);
            } catch (Throwable var14) {
               var10000 = var14;
               var10001 = false;
               break label166;
            }

            zaq.zaa(this.zaa).unlock();
            return;
         }

         try {
            zaq.zaa(this.zaa, true);
            zaq.zae(this.zaa).onConnectionSuspended(var1);
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            break label166;
         }

         zaq.zaa(this.zaa).unlock();
         return;
      }

      Throwable var3 = var10000;
      zaq.zaa(this.zaa).unlock();
      throw var3;
   }

   public final void zaa(Bundle var1) {
      zaq.zaa(this.zaa).lock();

      try {
         zaq.zaa(this.zaa, var1);
         zaq.zaa(this.zaa, ConnectionResult.RESULT_SUCCESS);
         zaq.zab(this.zaa);
      } finally {
         zaq.zaa(this.zaa).unlock();
      }

   }

   public final void zaa(ConnectionResult var1) {
      zaq.zaa(this.zaa).lock();

      try {
         zaq.zaa(this.zaa, var1);
         zaq.zab(this.zaa);
      } finally {
         zaq.zaa(this.zaa).unlock();
      }

   }
}
