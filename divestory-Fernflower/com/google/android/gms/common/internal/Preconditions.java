package com.google.android.gms.common.internal;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

public final class Preconditions {
   private Preconditions() {
      throw new AssertionError("Uninstantiable");
   }

   public static void checkArgument(boolean var0) {
      if (!var0) {
         throw new IllegalArgumentException();
      }
   }

   public static void checkArgument(boolean var0, Object var1) {
      if (!var0) {
         throw new IllegalArgumentException(String.valueOf(var1));
      }
   }

   public static void checkArgument(boolean var0, String var1, Object... var2) {
      if (!var0) {
         throw new IllegalArgumentException(String.format(var1, var2));
      }
   }

   public static void checkHandlerThread(Handler var0) {
      Looper var1 = Looper.myLooper();
      if (var1 != var0.getLooper()) {
         String var4;
         if (var1 != null) {
            var4 = var1.getThread().getName();
         } else {
            var4 = "null current looper";
         }

         String var3 = var0.getLooper().getThread().getName();
         StringBuilder var2 = new StringBuilder(String.valueOf(var3).length() + 36 + String.valueOf(var4).length());
         var2.append("Must be called on ");
         var2.append(var3);
         var2.append(" thread, but got ");
         var2.append(var4);
         var2.append(".");
         throw new IllegalStateException(var2.toString());
      }
   }

   public static void checkHandlerThread(Handler var0, String var1) {
      if (Looper.myLooper() != var0.getLooper()) {
         throw new IllegalStateException(var1);
      }
   }

   public static void checkMainThread(String var0) {
      if (!com.google.android.gms.common.util.zzb.zza()) {
         throw new IllegalStateException(var0);
      }
   }

   public static String checkNotEmpty(String var0) {
      if (!TextUtils.isEmpty(var0)) {
         return var0;
      } else {
         throw new IllegalArgumentException("Given String is empty or null");
      }
   }

   public static String checkNotEmpty(String var0, Object var1) {
      if (!TextUtils.isEmpty(var0)) {
         return var0;
      } else {
         throw new IllegalArgumentException(String.valueOf(var1));
      }
   }

   public static void checkNotMainThread() {
      checkNotMainThread("Must not be called on the main application thread");
   }

   public static void checkNotMainThread(String var0) {
      if (com.google.android.gms.common.util.zzb.zza()) {
         throw new IllegalStateException(var0);
      }
   }

   @EnsuresNonNull({"#1"})
   public static <T> T checkNotNull(T var0) {
      if (var0 != null) {
         return var0;
      } else {
         throw new NullPointerException("null reference");
      }
   }

   @EnsuresNonNull({"#1"})
   public static <T> T checkNotNull(T var0, Object var1) {
      if (var0 != null) {
         return var0;
      } else {
         throw new NullPointerException(String.valueOf(var1));
      }
   }

   public static int checkNotZero(int var0) {
      if (var0 != 0) {
         return var0;
      } else {
         throw new IllegalArgumentException("Given Integer is zero");
      }
   }

   public static int checkNotZero(int var0, Object var1) {
      if (var0 != 0) {
         return var0;
      } else {
         throw new IllegalArgumentException(String.valueOf(var1));
      }
   }

   public static long checkNotZero(long var0) {
      if (var0 != 0L) {
         return var0;
      } else {
         throw new IllegalArgumentException("Given Long is zero");
      }
   }

   public static long checkNotZero(long var0, Object var2) {
      if (var0 != 0L) {
         return var0;
      } else {
         throw new IllegalArgumentException(String.valueOf(var2));
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

   public static void checkState(boolean var0, String var1, Object... var2) {
      if (!var0) {
         throw new IllegalStateException(String.format(var1, var2));
      }
   }
}
