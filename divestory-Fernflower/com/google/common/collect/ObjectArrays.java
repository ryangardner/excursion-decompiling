package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class ObjectArrays {
   private ObjectArrays() {
   }

   static Object checkElementNotNull(Object var0, int var1) {
      if (var0 != null) {
         return var0;
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("at index ");
         var2.append(var1);
         throw new NullPointerException(var2.toString());
      }
   }

   static Object[] checkElementsNotNull(Object... var0) {
      return checkElementsNotNull(var0, var0.length);
   }

   static Object[] checkElementsNotNull(Object[] var0, int var1) {
      for(int var2 = 0; var2 < var1; ++var2) {
         checkElementNotNull(var0[var2], var2);
      }

      return var0;
   }

   public static <T> T[] concat(@NullableDecl T var0, T[] var1) {
      Object[] var2 = newArray(var1, var1.length + 1);
      var2[0] = var0;
      System.arraycopy(var1, 0, var2, 1, var1.length);
      return var2;
   }

   public static <T> T[] concat(T[] var0, @NullableDecl T var1) {
      Object[] var2 = Arrays.copyOf(var0, var0.length + 1);
      var2[var0.length] = var1;
      return var2;
   }

   public static <T> T[] concat(T[] var0, T[] var1, Class<T> var2) {
      Object[] var3 = newArray(var2, var0.length + var1.length);
      System.arraycopy(var0, 0, var3, 0, var0.length);
      System.arraycopy(var1, 0, var3, var0.length, var1.length);
      return var3;
   }

   static Object[] copyAsObjectArray(Object[] var0, int var1, int var2) {
      Preconditions.checkPositionIndexes(var1, var1 + var2, var0.length);
      if (var2 == 0) {
         return new Object[0];
      } else {
         Object[] var3 = new Object[var2];
         System.arraycopy(var0, var1, var3, 0, var2);
         return var3;
      }
   }

   private static Object[] fillArray(Iterable<?> var0, Object[] var1) {
      Iterator var3 = var0.iterator();

      for(int var2 = 0; var3.hasNext(); ++var2) {
         var1[var2] = var3.next();
      }

      return var1;
   }

   public static <T> T[] newArray(Class<T> var0, int var1) {
      return (Object[])Array.newInstance(var0, var1);
   }

   public static <T> T[] newArray(T[] var0, int var1) {
      return Platform.newArray(var0, var1);
   }

   static void swap(Object[] var0, int var1, int var2) {
      Object var3 = var0[var1];
      var0[var1] = var0[var2];
      var0[var2] = var3;
   }

   static Object[] toArrayImpl(Collection<?> var0) {
      return fillArray(var0, new Object[var0.size()]);
   }

   static <T> T[] toArrayImpl(Collection<?> var0, T[] var1) {
      int var2 = var0.size();
      Object[] var3 = var1;
      if (var1.length < var2) {
         var3 = newArray(var1, var2);
      }

      fillArray(var0, var3);
      if (var3.length > var2) {
         var3[var2] = null;
      }

      return var3;
   }

   static <T> T[] toArrayImpl(Object[] var0, int var1, int var2, T[] var3) {
      Preconditions.checkPositionIndexes(var1, var1 + var2, var0.length);
      Object[] var4;
      if (var3.length < var2) {
         var4 = newArray(var3, var2);
      } else {
         var4 = var3;
         if (var3.length > var2) {
            var3[var2] = null;
            var4 = var3;
         }
      }

      System.arraycopy(var0, var1, var4, 0, var2);
      return var4;
   }
}
