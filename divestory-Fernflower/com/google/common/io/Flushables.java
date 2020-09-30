package com.google.common.io;

import java.io.Flushable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Flushables {
   private static final Logger logger = Logger.getLogger(Flushables.class.getName());

   private Flushables() {
   }

   public static void flush(Flushable var0, boolean var1) throws IOException {
      try {
         var0.flush();
      } catch (IOException var2) {
         if (!var1) {
            throw var2;
         }

         logger.log(Level.WARNING, "IOException thrown while flushing Flushable.", var2);
      }

   }

   public static void flushQuietly(Flushable var0) {
      try {
         flush(var0, true);
      } catch (IOException var1) {
         logger.log(Level.SEVERE, "IOException should not have been thrown.", var1);
      }

   }
}
