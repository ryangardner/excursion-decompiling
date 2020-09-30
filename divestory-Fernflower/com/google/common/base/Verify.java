package com.google.common.base;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Verify {
   private Verify() {
   }

   public static void verify(boolean var0) {
      if (!var0) {
         throw new VerifyException();
      }
   }

   public static void verify(boolean var0, @NullableDecl String var1, char var2) {
      if (!var0) {
         throw new VerifyException(Strings.lenientFormat(var1, var2));
      }
   }

   public static void verify(boolean var0, @NullableDecl String var1, char var2, char var3) {
      if (!var0) {
         throw new VerifyException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void verify(boolean var0, @NullableDecl String var1, char var2, int var3) {
      if (!var0) {
         throw new VerifyException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void verify(boolean var0, @NullableDecl String var1, char var2, long var3) {
      if (!var0) {
         throw new VerifyException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void verify(boolean var0, @NullableDecl String var1, char var2, @NullableDecl Object var3) {
      if (!var0) {
         throw new VerifyException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void verify(boolean var0, @NullableDecl String var1, int var2) {
      if (!var0) {
         throw new VerifyException(Strings.lenientFormat(var1, var2));
      }
   }

   public static void verify(boolean var0, @NullableDecl String var1, int var2, char var3) {
      if (!var0) {
         throw new VerifyException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void verify(boolean var0, @NullableDecl String var1, int var2, int var3) {
      if (!var0) {
         throw new VerifyException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void verify(boolean var0, @NullableDecl String var1, int var2, long var3) {
      if (!var0) {
         throw new VerifyException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void verify(boolean var0, @NullableDecl String var1, int var2, @NullableDecl Object var3) {
      if (!var0) {
         throw new VerifyException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void verify(boolean var0, @NullableDecl String var1, long var2) {
      if (!var0) {
         throw new VerifyException(Strings.lenientFormat(var1, var2));
      }
   }

   public static void verify(boolean var0, @NullableDecl String var1, long var2, char var4) {
      if (!var0) {
         throw new VerifyException(Strings.lenientFormat(var1, var2, var4));
      }
   }

   public static void verify(boolean var0, @NullableDecl String var1, long var2, int var4) {
      if (!var0) {
         throw new VerifyException(Strings.lenientFormat(var1, var2, var4));
      }
   }

   public static void verify(boolean var0, @NullableDecl String var1, long var2, long var4) {
      if (!var0) {
         throw new VerifyException(Strings.lenientFormat(var1, var2, var4));
      }
   }

   public static void verify(boolean var0, @NullableDecl String var1, long var2, @NullableDecl Object var4) {
      if (!var0) {
         throw new VerifyException(Strings.lenientFormat(var1, var2, var4));
      }
   }

   public static void verify(boolean var0, @NullableDecl String var1, @NullableDecl Object var2) {
      if (!var0) {
         throw new VerifyException(Strings.lenientFormat(var1, var2));
      }
   }

   public static void verify(boolean var0, @NullableDecl String var1, @NullableDecl Object var2, char var3) {
      if (!var0) {
         throw new VerifyException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void verify(boolean var0, @NullableDecl String var1, @NullableDecl Object var2, int var3) {
      if (!var0) {
         throw new VerifyException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void verify(boolean var0, @NullableDecl String var1, @NullableDecl Object var2, long var3) {
      if (!var0) {
         throw new VerifyException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void verify(boolean var0, @NullableDecl String var1, @NullableDecl Object var2, @NullableDecl Object var3) {
      if (!var0) {
         throw new VerifyException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void verify(boolean var0, @NullableDecl String var1, @NullableDecl Object var2, @NullableDecl Object var3, @NullableDecl Object var4) {
      if (!var0) {
         throw new VerifyException(Strings.lenientFormat(var1, var2, var3, var4));
      }
   }

   public static void verify(boolean var0, @NullableDecl String var1, @NullableDecl Object var2, @NullableDecl Object var3, @NullableDecl Object var4, @NullableDecl Object var5) {
      if (!var0) {
         throw new VerifyException(Strings.lenientFormat(var1, var2, var3, var4, var5));
      }
   }

   public static void verify(boolean var0, @NullableDecl String var1, @NullableDecl Object... var2) {
      if (!var0) {
         throw new VerifyException(Strings.lenientFormat(var1, var2));
      }
   }

   public static <T> T verifyNotNull(@NullableDecl T var0) {
      return verifyNotNull(var0, "expected a non-null reference");
   }

   public static <T> T verifyNotNull(@NullableDecl T var0, @NullableDecl String var1, @NullableDecl Object... var2) {
      boolean var3;
      if (var0 != null) {
         var3 = true;
      } else {
         var3 = false;
      }

      verify(var3, var1, var2);
      return var0;
   }
}
