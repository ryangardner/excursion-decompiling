package com.google.common.util.concurrent;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class UncaughtExceptionHandlers {
   private UncaughtExceptionHandlers() {
   }

   public static UncaughtExceptionHandler systemExit() {
      return new UncaughtExceptionHandlers.Exiter(Runtime.getRuntime());
   }

   static final class Exiter implements UncaughtExceptionHandler {
      private static final Logger logger = Logger.getLogger(UncaughtExceptionHandlers.Exiter.class.getName());
      private final Runtime runtime;

      Exiter(Runtime var1) {
         this.runtime = var1;
      }

      public void uncaughtException(Thread var1, Throwable var2) {
         try {
            logger.log(Level.SEVERE, String.format(Locale.ROOT, "Caught an exception in %s.  Shutting down.", var1), var2);
         } catch (Throwable var8) {
            Throwable var9 = var8;

            try {
               System.err.println(var2.getMessage());
               System.err.println(var9.getMessage());
               return;
            } finally {
               this.runtime.exit(1);
            }
         }

      }
   }
}
