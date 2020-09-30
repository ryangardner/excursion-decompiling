package com.google.android.gms.common.providers;

import java.util.concurrent.ScheduledExecutorService;

@Deprecated
public class PooledExecutorsProvider {
   private static PooledExecutorsProvider.PooledExecutorFactory zza;

   private PooledExecutorsProvider() {
   }

   @Deprecated
   public static PooledExecutorsProvider.PooledExecutorFactory getInstance() {
      synchronized(PooledExecutorsProvider.class){}

      PooledExecutorsProvider.PooledExecutorFactory var3;
      try {
         if (zza == null) {
            zza var0 = new zza();
            zza = var0;
         }

         var3 = zza;
      } finally {
         ;
      }

      return var3;
   }

   public interface PooledExecutorFactory {
      @Deprecated
      ScheduledExecutorService newSingleThreadScheduledExecutor();
   }
}
