package com.google.api.client.util;

import java.io.IOException;

public final class BackOffUtils {
   private BackOffUtils() {
   }

   public static boolean next(Sleeper var0, BackOff var1) throws InterruptedException, IOException {
      long var2 = var1.nextBackOffMillis();
      if (var2 == -1L) {
         return false;
      } else {
         var0.sleep(var2);
         return true;
      }
   }
}
