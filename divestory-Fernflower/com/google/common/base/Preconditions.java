package com.google.common.base;

import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Preconditions {
   private Preconditions() {
   }

   private static String badElementIndex(int var0, int var1, @NullableDecl String var2) {
      if (var0 < 0) {
         return Strings.lenientFormat("%s (%s) must not be negative", var2, var0);
      } else if (var1 >= 0) {
         return Strings.lenientFormat("%s (%s) must be less than size (%s)", var2, var0, var1);
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("negative size: ");
         var3.append(var1);
         throw new IllegalArgumentException(var3.toString());
      }
   }

   private static String badPositionIndex(int var0, int var1, @NullableDecl String var2) {
      if (var0 < 0) {
         return Strings.lenientFormat("%s (%s) must not be negative", var2, var0);
      } else if (var1 >= 0) {
         return Strings.lenientFormat("%s (%s) must not be greater than size (%s)", var2, var0, var1);
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("negative size: ");
         var3.append(var1);
         throw new IllegalArgumentException(var3.toString());
      }
   }

   private static String badPositionIndexes(int var0, int var1, int var2) {
      if (var0 >= 0 && var0 <= var2) {
         return var1 >= 0 && var1 <= var2 ? Strings.lenientFormat("end index (%s) must not be less than start index (%s)", var1, var0) : badPositionIndex(var1, var2, "end index");
      } else {
         return badPositionIndex(var0, var2, "start index");
      }
   }

   public static void checkArgument(boolean var0) {
      if (!var0) {
         throw new IllegalArgumentException();
      }
   }

   public static void checkArgument(boolean var0, @NullableDecl Object var1) {
      if (!var0) {
         throw new IllegalArgumentException(String.valueOf(var1));
      }
   }

   public static void checkArgument(boolean var0, @NullableDecl String var1, char var2) {
      if (!var0) {
         throw new IllegalArgumentException(Strings.lenientFormat(var1, var2));
      }
   }

   public static void checkArgument(boolean var0, @NullableDecl String var1, char var2, char var3) {
      if (!var0) {
         throw new IllegalArgumentException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void checkArgument(boolean var0, @NullableDecl String var1, char var2, int var3) {
      if (!var0) {
         throw new IllegalArgumentException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void checkArgument(boolean var0, @NullableDecl String var1, char var2, long var3) {
      if (!var0) {
         throw new IllegalArgumentException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void checkArgument(boolean var0, @NullableDecl String var1, char var2, @NullableDecl Object var3) {
      if (!var0) {
         throw new IllegalArgumentException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void checkArgument(boolean var0, @NullableDecl String var1, int var2) {
      if (!var0) {
         throw new IllegalArgumentException(Strings.lenientFormat(var1, var2));
      }
   }

   public static void checkArgument(boolean var0, @NullableDecl String var1, int var2, char var3) {
      if (!var0) {
         throw new IllegalArgumentException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void checkArgument(boolean var0, @NullableDecl String var1, int var2, int var3) {
      if (!var0) {
         throw new IllegalArgumentException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void checkArgument(boolean var0, @NullableDecl String var1, int var2, long var3) {
      if (!var0) {
         throw new IllegalArgumentException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void checkArgument(boolean var0, @NullableDecl String var1, int var2, @NullableDecl Object var3) {
      if (!var0) {
         throw new IllegalArgumentException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void checkArgument(boolean var0, @NullableDecl String var1, long var2) {
      if (!var0) {
         throw new IllegalArgumentException(Strings.lenientFormat(var1, var2));
      }
   }

   public static void checkArgument(boolean var0, @NullableDecl String var1, long var2, char var4) {
      if (!var0) {
         throw new IllegalArgumentException(Strings.lenientFormat(var1, var2, var4));
      }
   }

   public static void checkArgument(boolean var0, @NullableDecl String var1, long var2, int var4) {
      if (!var0) {
         throw new IllegalArgumentException(Strings.lenientFormat(var1, var2, var4));
      }
   }

   public static void checkArgument(boolean var0, @NullableDecl String var1, long var2, long var4) {
      if (!var0) {
         throw new IllegalArgumentException(Strings.lenientFormat(var1, var2, var4));
      }
   }

   public static void checkArgument(boolean var0, @NullableDecl String var1, long var2, @NullableDecl Object var4) {
      if (!var0) {
         throw new IllegalArgumentException(Strings.lenientFormat(var1, var2, var4));
      }
   }

   public static void checkArgument(boolean var0, @NullableDecl String var1, @NullableDecl Object var2) {
      if (!var0) {
         throw new IllegalArgumentException(Strings.lenientFormat(var1, var2));
      }
   }

   public static void checkArgument(boolean var0, @NullableDecl String var1, @NullableDecl Object var2, char var3) {
      if (!var0) {
         throw new IllegalArgumentException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void checkArgument(boolean var0, @NullableDecl String var1, @NullableDecl Object var2, int var3) {
      if (!var0) {
         throw new IllegalArgumentException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void checkArgument(boolean var0, @NullableDecl String var1, @NullableDecl Object var2, long var3) {
      if (!var0) {
         throw new IllegalArgumentException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void checkArgument(boolean var0, @NullableDecl String var1, @NullableDecl Object var2, @NullableDecl Object var3) {
      if (!var0) {
         throw new IllegalArgumentException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void checkArgument(boolean var0, @NullableDecl String var1, @NullableDecl Object var2, @NullableDecl Object var3, @NullableDecl Object var4) {
      if (!var0) {
         throw new IllegalArgumentException(Strings.lenientFormat(var1, var2, var3, var4));
      }
   }

   public static void checkArgument(boolean var0, @NullableDecl String var1, @NullableDecl Object var2, @NullableDecl Object var3, @NullableDecl Object var4, @NullableDecl Object var5) {
      if (!var0) {
         throw new IllegalArgumentException(Strings.lenientFormat(var1, var2, var3, var4, var5));
      }
   }

   public static void checkArgument(boolean var0, @NullableDecl String var1, @NullableDecl Object... var2) {
      if (!var0) {
         throw new IllegalArgumentException(Strings.lenientFormat(var1, var2));
      }
   }

   public static int checkElementIndex(int var0, int var1) {
      return checkElementIndex(var0, var1, "index");
   }

   public static int checkElementIndex(int var0, int var1, @NullableDecl String var2) {
      if (var0 >= 0 && var0 < var1) {
         return var0;
      } else {
         throw new IndexOutOfBoundsException(badElementIndex(var0, var1, var2));
      }
   }

   @NonNullDecl
   public static <T> T checkNotNull(@NonNullDecl T var0) {
      if (var0 != null) {
         return var0;
      } else {
         throw null;
      }
   }

   @NonNullDecl
   public static <T> T checkNotNull(@NonNullDecl T var0, @NullableDecl Object var1) {
      if (var0 != null) {
         return var0;
      } else {
         throw new NullPointerException(String.valueOf(var1));
      }
   }

   @NonNullDecl
   public static <T> T checkNotNull(@NonNullDecl T var0, @NullableDecl String var1, char var2) {
      if (var0 != null) {
         return var0;
      } else {
         throw new NullPointerException(Strings.lenientFormat(var1, var2));
      }
   }

   @NonNullDecl
   public static <T> T checkNotNull(@NonNullDecl T var0, @NullableDecl String var1, char var2, char var3) {
      if (var0 != null) {
         return var0;
      } else {
         throw new NullPointerException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   @NonNullDecl
   public static <T> T checkNotNull(@NonNullDecl T var0, @NullableDecl String var1, char var2, int var3) {
      if (var0 != null) {
         return var0;
      } else {
         throw new NullPointerException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   @NonNullDecl
   public static <T> T checkNotNull(@NonNullDecl T var0, @NullableDecl String var1, char var2, long var3) {
      if (var0 != null) {
         return var0;
      } else {
         throw new NullPointerException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   @NonNullDecl
   public static <T> T checkNotNull(@NonNullDecl T var0, @NullableDecl String var1, char var2, @NullableDecl Object var3) {
      if (var0 != null) {
         return var0;
      } else {
         throw new NullPointerException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   @NonNullDecl
   public static <T> T checkNotNull(@NonNullDecl T var0, @NullableDecl String var1, int var2) {
      if (var0 != null) {
         return var0;
      } else {
         throw new NullPointerException(Strings.lenientFormat(var1, var2));
      }
   }

   @NonNullDecl
   public static <T> T checkNotNull(@NonNullDecl T var0, @NullableDecl String var1, int var2, char var3) {
      if (var0 != null) {
         return var0;
      } else {
         throw new NullPointerException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   @NonNullDecl
   public static <T> T checkNotNull(@NonNullDecl T var0, @NullableDecl String var1, int var2, int var3) {
      if (var0 != null) {
         return var0;
      } else {
         throw new NullPointerException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   @NonNullDecl
   public static <T> T checkNotNull(@NonNullDecl T var0, @NullableDecl String var1, int var2, long var3) {
      if (var0 != null) {
         return var0;
      } else {
         throw new NullPointerException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   @NonNullDecl
   public static <T> T checkNotNull(@NonNullDecl T var0, @NullableDecl String var1, int var2, @NullableDecl Object var3) {
      if (var0 != null) {
         return var0;
      } else {
         throw new NullPointerException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   @NonNullDecl
   public static <T> T checkNotNull(@NonNullDecl T var0, @NullableDecl String var1, long var2) {
      if (var0 != null) {
         return var0;
      } else {
         throw new NullPointerException(Strings.lenientFormat(var1, var2));
      }
   }

   @NonNullDecl
   public static <T> T checkNotNull(@NonNullDecl T var0, @NullableDecl String var1, long var2, char var4) {
      if (var0 != null) {
         return var0;
      } else {
         throw new NullPointerException(Strings.lenientFormat(var1, var2, var4));
      }
   }

   @NonNullDecl
   public static <T> T checkNotNull(@NonNullDecl T var0, @NullableDecl String var1, long var2, int var4) {
      if (var0 != null) {
         return var0;
      } else {
         throw new NullPointerException(Strings.lenientFormat(var1, var2, var4));
      }
   }

   @NonNullDecl
   public static <T> T checkNotNull(@NonNullDecl T var0, @NullableDecl String var1, long var2, long var4) {
      if (var0 != null) {
         return var0;
      } else {
         throw new NullPointerException(Strings.lenientFormat(var1, var2, var4));
      }
   }

   @NonNullDecl
   public static <T> T checkNotNull(@NonNullDecl T var0, @NullableDecl String var1, long var2, @NullableDecl Object var4) {
      if (var0 != null) {
         return var0;
      } else {
         throw new NullPointerException(Strings.lenientFormat(var1, var2, var4));
      }
   }

   @NonNullDecl
   public static <T> T checkNotNull(@NonNullDecl T var0, @NullableDecl String var1, @NullableDecl Object var2) {
      if (var0 != null) {
         return var0;
      } else {
         throw new NullPointerException(Strings.lenientFormat(var1, var2));
      }
   }

   @NonNullDecl
   public static <T> T checkNotNull(@NonNullDecl T var0, @NullableDecl String var1, @NullableDecl Object var2, char var3) {
      if (var0 != null) {
         return var0;
      } else {
         throw new NullPointerException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   @NonNullDecl
   public static <T> T checkNotNull(@NonNullDecl T var0, @NullableDecl String var1, @NullableDecl Object var2, int var3) {
      if (var0 != null) {
         return var0;
      } else {
         throw new NullPointerException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   @NonNullDecl
   public static <T> T checkNotNull(@NonNullDecl T var0, @NullableDecl String var1, @NullableDecl Object var2, long var3) {
      if (var0 != null) {
         return var0;
      } else {
         throw new NullPointerException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   @NonNullDecl
   public static <T> T checkNotNull(@NonNullDecl T var0, @NullableDecl String var1, @NullableDecl Object var2, @NullableDecl Object var3) {
      if (var0 != null) {
         return var0;
      } else {
         throw new NullPointerException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   @NonNullDecl
   public static <T> T checkNotNull(@NonNullDecl T var0, @NullableDecl String var1, @NullableDecl Object var2, @NullableDecl Object var3, @NullableDecl Object var4) {
      if (var0 != null) {
         return var0;
      } else {
         throw new NullPointerException(Strings.lenientFormat(var1, var2, var3, var4));
      }
   }

   @NonNullDecl
   public static <T> T checkNotNull(@NonNullDecl T var0, @NullableDecl String var1, @NullableDecl Object var2, @NullableDecl Object var3, @NullableDecl Object var4, @NullableDecl Object var5) {
      if (var0 != null) {
         return var0;
      } else {
         throw new NullPointerException(Strings.lenientFormat(var1, var2, var3, var4, var5));
      }
   }

   @NonNullDecl
   public static <T> T checkNotNull(@NonNullDecl T var0, @NullableDecl String var1, @NullableDecl Object... var2) {
      if (var0 != null) {
         return var0;
      } else {
         throw new NullPointerException(Strings.lenientFormat(var1, var2));
      }
   }

   public static int checkPositionIndex(int var0, int var1) {
      return checkPositionIndex(var0, var1, "index");
   }

   public static int checkPositionIndex(int var0, int var1, @NullableDecl String var2) {
      if (var0 >= 0 && var0 <= var1) {
         return var0;
      } else {
         throw new IndexOutOfBoundsException(badPositionIndex(var0, var1, var2));
      }
   }

   public static void checkPositionIndexes(int var0, int var1, int var2) {
      if (var0 < 0 || var1 < var0 || var1 > var2) {
         throw new IndexOutOfBoundsException(badPositionIndexes(var0, var1, var2));
      }
   }

   public static void checkState(boolean var0) {
      if (!var0) {
         throw new IllegalStateException();
      }
   }

   public static void checkState(boolean var0, @NullableDecl Object var1) {
      if (!var0) {
         throw new IllegalStateException(String.valueOf(var1));
      }
   }

   public static void checkState(boolean var0, @NullableDecl String var1, char var2) {
      if (!var0) {
         throw new IllegalStateException(Strings.lenientFormat(var1, var2));
      }
   }

   public static void checkState(boolean var0, @NullableDecl String var1, char var2, char var3) {
      if (!var0) {
         throw new IllegalStateException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void checkState(boolean var0, @NullableDecl String var1, char var2, int var3) {
      if (!var0) {
         throw new IllegalStateException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void checkState(boolean var0, @NullableDecl String var1, char var2, long var3) {
      if (!var0) {
         throw new IllegalStateException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void checkState(boolean var0, @NullableDecl String var1, char var2, @NullableDecl Object var3) {
      if (!var0) {
         throw new IllegalStateException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void checkState(boolean var0, @NullableDecl String var1, int var2) {
      if (!var0) {
         throw new IllegalStateException(Strings.lenientFormat(var1, var2));
      }
   }

   public static void checkState(boolean var0, @NullableDecl String var1, int var2, char var3) {
      if (!var0) {
         throw new IllegalStateException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void checkState(boolean var0, @NullableDecl String var1, int var2, int var3) {
      if (!var0) {
         throw new IllegalStateException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void checkState(boolean var0, @NullableDecl String var1, int var2, long var3) {
      if (!var0) {
         throw new IllegalStateException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void checkState(boolean var0, @NullableDecl String var1, int var2, @NullableDecl Object var3) {
      if (!var0) {
         throw new IllegalStateException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void checkState(boolean var0, @NullableDecl String var1, long var2) {
      if (!var0) {
         throw new IllegalStateException(Strings.lenientFormat(var1, var2));
      }
   }

   public static void checkState(boolean var0, @NullableDecl String var1, long var2, char var4) {
      if (!var0) {
         throw new IllegalStateException(Strings.lenientFormat(var1, var2, var4));
      }
   }

   public static void checkState(boolean var0, @NullableDecl String var1, long var2, int var4) {
      if (!var0) {
         throw new IllegalStateException(Strings.lenientFormat(var1, var2, var4));
      }
   }

   public static void checkState(boolean var0, @NullableDecl String var1, long var2, long var4) {
      if (!var0) {
         throw new IllegalStateException(Strings.lenientFormat(var1, var2, var4));
      }
   }

   public static void checkState(boolean var0, @NullableDecl String var1, long var2, @NullableDecl Object var4) {
      if (!var0) {
         throw new IllegalStateException(Strings.lenientFormat(var1, var2, var4));
      }
   }

   public static void checkState(boolean var0, @NullableDecl String var1, @NullableDecl Object var2) {
      if (!var0) {
         throw new IllegalStateException(Strings.lenientFormat(var1, var2));
      }
   }

   public static void checkState(boolean var0, @NullableDecl String var1, @NullableDecl Object var2, char var3) {
      if (!var0) {
         throw new IllegalStateException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void checkState(boolean var0, @NullableDecl String var1, @NullableDecl Object var2, int var3) {
      if (!var0) {
         throw new IllegalStateException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void checkState(boolean var0, @NullableDecl String var1, @NullableDecl Object var2, long var3) {
      if (!var0) {
         throw new IllegalStateException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void checkState(boolean var0, @NullableDecl String var1, @NullableDecl Object var2, @NullableDecl Object var3) {
      if (!var0) {
         throw new IllegalStateException(Strings.lenientFormat(var1, var2, var3));
      }
   }

   public static void checkState(boolean var0, @NullableDecl String var1, @NullableDecl Object var2, @NullableDecl Object var3, @NullableDecl Object var4) {
      if (!var0) {
         throw new IllegalStateException(Strings.lenientFormat(var1, var2, var3, var4));
      }
   }

   public static void checkState(boolean var0, @NullableDecl String var1, @NullableDecl Object var2, @NullableDecl Object var3, @NullableDecl Object var4, @NullableDecl Object var5) {
      if (!var0) {
         throw new IllegalStateException(Strings.lenientFormat(var1, var2, var3, var4, var5));
      }
   }

   public static void checkState(boolean var0, @NullableDecl String var1, @NullableDecl Object... var2) {
      if (!var0) {
         throw new IllegalStateException(Strings.lenientFormat(var1, var2));
      }
   }
}
