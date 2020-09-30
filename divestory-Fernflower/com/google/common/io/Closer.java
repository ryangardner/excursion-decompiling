package com.google.common.io;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Closer implements Closeable {
   private static final Closer.Suppressor SUPPRESSOR;
   private final Deque<Closeable> stack = new ArrayDeque(4);
   final Closer.Suppressor suppressor;
   @MonotonicNonNullDecl
   private Throwable thrown;

   static {
      Object var0;
      if (Closer.SuppressingSuppressor.isAvailable()) {
         var0 = Closer.SuppressingSuppressor.INSTANCE;
      } else {
         var0 = Closer.LoggingSuppressor.INSTANCE;
      }

      SUPPRESSOR = (Closer.Suppressor)var0;
   }

   Closer(Closer.Suppressor var1) {
      this.suppressor = (Closer.Suppressor)Preconditions.checkNotNull(var1);
   }

   public static Closer create() {
      return new Closer(SUPPRESSOR);
   }

   public void close() throws IOException {
      Throwable var1 = this.thrown;

      while(!this.stack.isEmpty()) {
         Closeable var2 = (Closeable)this.stack.removeFirst();

         try {
            var2.close();
         } catch (Throwable var5) {
            if (var1 == null) {
               var1 = var5;
            } else {
               this.suppressor.suppress(var2, var1, var5);
            }
            continue;
         }
      }

      if (this.thrown == null && var1 != null) {
         Throwables.propagateIfPossible(var1, IOException.class);
         throw new AssertionError(var1);
      }
   }

   public <C extends Closeable> C register(@NullableDecl C var1) {
      if (var1 != null) {
         this.stack.addFirst(var1);
      }

      return var1;
   }

   public RuntimeException rethrow(Throwable var1) throws IOException {
      Preconditions.checkNotNull(var1);
      this.thrown = var1;
      Throwables.propagateIfPossible(var1, IOException.class);
      throw new RuntimeException(var1);
   }

   public <X extends Exception> RuntimeException rethrow(Throwable var1, Class<X> var2) throws IOException, X {
      Preconditions.checkNotNull(var1);
      this.thrown = var1;
      Throwables.propagateIfPossible(var1, IOException.class);
      Throwables.propagateIfPossible(var1, var2);
      throw new RuntimeException(var1);
   }

   public <X1 extends Exception, X2 extends Exception> RuntimeException rethrow(Throwable var1, Class<X1> var2, Class<X2> var3) throws IOException, X1, X2 {
      Preconditions.checkNotNull(var1);
      this.thrown = var1;
      Throwables.propagateIfPossible(var1, IOException.class);
      Throwables.propagateIfPossible(var1, var2, var3);
      throw new RuntimeException(var1);
   }

   static final class LoggingSuppressor implements Closer.Suppressor {
      static final Closer.LoggingSuppressor INSTANCE = new Closer.LoggingSuppressor();

      public void suppress(Closeable var1, Throwable var2, Throwable var3) {
         Logger var6 = Closeables.logger;
         Level var4 = Level.WARNING;
         StringBuilder var5 = new StringBuilder();
         var5.append("Suppressing exception thrown when closing ");
         var5.append(var1);
         var6.log(var4, var5.toString(), var3);
      }
   }

   static final class SuppressingSuppressor implements Closer.Suppressor {
      static final Closer.SuppressingSuppressor INSTANCE = new Closer.SuppressingSuppressor();
      static final Method addSuppressed = addSuppressedMethodOrNull();

      private static Method addSuppressedMethodOrNull() {
         try {
            Method var0 = Throwable.class.getMethod("addSuppressed", Throwable.class);
            return var0;
         } finally {
            ;
         }
      }

      static boolean isAvailable() {
         boolean var0;
         if (addSuppressed != null) {
            var0 = true;
         } else {
            var0 = false;
         }

         return var0;
      }

      public void suppress(Closeable var1, Throwable var2, Throwable var3) {
         if (var2 != var3) {
            boolean var6 = false;

            try {
               var6 = true;
               addSuppressed.invoke(var2, var3);
               var6 = false;
            } finally {
               if (var6) {
                  Closer.LoggingSuppressor.INSTANCE.suppress(var1, var2, var3);
                  return;
               }
            }

         }
      }
   }

   interface Suppressor {
      void suppress(Closeable var1, Throwable var2, Throwable var3);
   }
}
