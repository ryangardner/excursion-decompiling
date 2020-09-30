package com.google.common.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Closeables {
   static final Logger logger = Logger.getLogger(Closeables.class.getName());

   private Closeables() {
   }

   public static void close(@NullableDecl Closeable var0, boolean var1) throws IOException {
      if (var0 != null) {
         try {
            var0.close();
         } catch (IOException var2) {
            if (!var1) {
               throw var2;
            }

            logger.log(Level.WARNING, "IOException thrown while closing Closeable.", var2);
         }

      }
   }

   public static void closeQuietly(@NullableDecl InputStream var0) {
      try {
         close(var0, true);
      } catch (IOException var1) {
         throw new AssertionError(var1);
      }
   }

   public static void closeQuietly(@NullableDecl Reader var0) {
      try {
         close(var0, true);
      } catch (IOException var1) {
         throw new AssertionError(var1);
      }
   }
}
