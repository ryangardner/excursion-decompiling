package com.sun.activation.registries;

import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogSupport {
   private static boolean debug;
   private static final Level level;
   private static Logger logger;

   static {
      level = Level.FINE;

      label23:
      try {
         debug = Boolean.getBoolean("javax.activation.debug");
      } finally {
         break label23;
      }

      logger = Logger.getLogger("javax.activation");
   }

   private LogSupport() {
   }

   public static boolean isLoggable() {
      return debug || logger.isLoggable(level);
   }

   public static void log(String var0) {
      if (debug) {
         System.out.println(var0);
      }

      logger.log(level, var0);
   }

   public static void log(String var0, Throwable var1) {
      if (debug) {
         PrintStream var2 = System.out;
         StringBuilder var3 = new StringBuilder(String.valueOf(var0));
         var3.append("; Exception: ");
         var3.append(var1);
         var2.println(var3.toString());
      }

      logger.log(level, var0, var1);
   }
}
