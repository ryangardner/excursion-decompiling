package com.google.android.gms.common.internal;

import android.os.Looper;
import android.util.Log;

public final class Asserts {
   private Asserts() {
      throw new AssertionError("Uninstantiable");
   }

   public static void checkMainThread(String var0) {
      if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
         String var1 = String.valueOf(Thread.currentThread());
         String var2 = String.valueOf(Looper.getMainLooper().getThread());
         StringBuilder var3 = new StringBuilder(String.valueOf(var1).length() + 57 + String.valueOf(var2).length());
         var3.append("checkMainThread: current thread ");
         var3.append(var1);
         var3.append(" IS NOT the main thread ");
         var3.append(var2);
         var3.append("!");
         Log.e("Asserts", var3.toString());
         throw new IllegalStateException(var0);
      }
   }

   public static void checkNotMainThread(String var0) {
      if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
         String var1 = String.valueOf(Thread.currentThread());
         String var2 = String.valueOf(Looper.getMainLooper().getThread());
         StringBuilder var3 = new StringBuilder(String.valueOf(var1).length() + 56 + String.valueOf(var2).length());
         var3.append("checkNotMainThread: current thread ");
         var3.append(var1);
         var3.append(" IS the main thread ");
         var3.append(var2);
         var3.append("!");
         Log.e("Asserts", var3.toString());
         throw new IllegalStateException(var0);
      }
   }

   public static void checkNotNull(Object var0) {
      if (var0 == null) {
         throw new IllegalArgumentException("null reference");
      }
   }

   public static void checkNotNull(Object var0, Object var1) {
      if (var0 == null) {
         throw new IllegalArgumentException(String.valueOf(var1));
      }
   }

   public static void checkNull(Object var0) {
      if (var0 != null) {
         throw new IllegalArgumentException("non-null reference");
      }
   }

   public static void checkState(boolean var0) {
      if (!var0) {
         throw new IllegalStateException();
      }
   }

   public static void checkState(boolean var0, Object var1) {
      if (!var0) {
         throw new IllegalStateException(String.valueOf(var1));
      }
   }
}
