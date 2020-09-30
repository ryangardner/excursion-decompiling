package com.google.common.primitives;

import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Booleans {
   private Booleans() {
   }

   public static List<Boolean> asList(boolean... var0) {
      return (List)(var0.length == 0 ? Collections.emptyList() : new Booleans.BooleanArrayAsList(var0));
   }

   public static int compare(boolean var0, boolean var1) {
      byte var2;
      if (var0 == var1) {
         var2 = 0;
      } else if (var0) {
         var2 = 1;
      } else {
         var2 = -1;
      }

      return var2;
   }

   public static boolean[] concat(boolean[]... var0) {
      int var1 = var0.length;
      int var2 = 0;

      int var3;
      for(var3 = 0; var2 < var1; ++var2) {
         var3 += var0[var2].length;
      }

      boolean[] var4 = new boolean[var3];
      var1 = var0.length;
      var2 = 0;

      for(var3 = 0; var2 < var1; ++var2) {
         boolean[] var5 = var0[var2];
         System.arraycopy(var5, 0, var4, var3, var5.length);
         var3 += var5.length;
      }

      return var4;
   }

   public static boolean contains(boolean[] var0, boolean var1) {
      int var2 = var0.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         if (var0[var3] == var1) {
            return true;
         }
      }

      return false;
   }

   public static int countTrue(boolean... var0) {
      int var1 = var0.length;
      int var2 = 0;

      int var3;
      int var4;
      for(var3 = 0; var2 < var1; var3 = var4) {
         var4 = var3;
         if (var0[var2]) {
            var4 = var3 + 1;
         }

         ++var2;
      }

      return var3;
   }

   public static boolean[] ensureCapacity(boolean[] var0, int var1, int var2) {
      boolean var3 = true;
      boolean var4;
      if (var1 >= 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4, "Invalid minLength: %s", var1);
      if (var2 >= 0) {
         var4 = var3;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4, "Invalid padding: %s", var2);
      boolean[] var5 = var0;
      if (var0.length < var1) {
         var5 = Arrays.copyOf(var0, var1 + var2);
      }

      return var5;
   }

   public static Comparator<Boolean> falseFirst() {
      return Booleans.BooleanComparator.FALSE_FIRST;
   }

   public static int hashCode(boolean var0) {
      short var1;
      if (var0) {
         var1 = 1231;
      } else {
         var1 = 1237;
      }

      return var1;
   }

   public static int indexOf(boolean[] var0, boolean var1) {
      return indexOf(var0, var1, 0, var0.length);
   }

   private static int indexOf(boolean[] var0, boolean var1, int var2, int var3) {
      while(var2 < var3) {
         if (var0[var2] == var1) {
            return var2;
         }

         ++var2;
      }

      return -1;
   }

   public static int indexOf(boolean[] var0, boolean[] var1) {
      Preconditions.checkNotNull(var0, "array");
      Preconditions.checkNotNull(var1, "target");
      if (var1.length == 0) {
         return 0;
      } else {
         label27:
         for(int var2 = 0; var2 < var0.length - var1.length + 1; ++var2) {
            for(int var3 = 0; var3 < var1.length; ++var3) {
               if (var0[var2 + var3] != var1[var3]) {
                  continue label27;
               }
            }

            return var2;
         }

         return -1;
      }
   }

   public static String join(String var0, boolean... var1) {
      Preconditions.checkNotNull(var0);
      if (var1.length == 0) {
         return "";
      } else {
         StringBuilder var2 = new StringBuilder(var1.length * 7);
         var2.append(var1[0]);

         for(int var3 = 1; var3 < var1.length; ++var3) {
            var2.append(var0);
            var2.append(var1[var3]);
         }

         return var2.toString();
      }
   }

   public static int lastIndexOf(boolean[] var0, boolean var1) {
      return lastIndexOf(var0, var1, 0, var0.length);
   }

   private static int lastIndexOf(boolean[] var0, boolean var1, int var2, int var3) {
      --var3;

      while(var3 >= var2) {
         if (var0[var3] == var1) {
            return var3;
         }

         --var3;
      }

      return -1;
   }

   public static Comparator<boolean[]> lexicographicalComparator() {
      return Booleans.LexicographicalComparator.INSTANCE;
   }

   public static void reverse(boolean[] var0) {
      Preconditions.checkNotNull(var0);
      reverse(var0, 0, var0.length);
   }

   public static void reverse(boolean[] var0, int var1, int var2) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkPositionIndexes(var1, var2, var0.length);
      --var2;

      while(var1 < var2) {
         boolean var3 = var0[var1];
         var0[var1] = var0[var2];
         var0[var2] = var3;
         ++var1;
         --var2;
      }

   }

   public static boolean[] toArray(Collection<Boolean> var0) {
      if (var0 instanceof Booleans.BooleanArrayAsList) {
         return ((Booleans.BooleanArrayAsList)var0).toBooleanArray();
      } else {
         Object[] var4 = var0.toArray();
         int var1 = var4.length;
         boolean[] var2 = new boolean[var1];

         for(int var3 = 0; var3 < var1; ++var3) {
            var2[var3] = (Boolean)Preconditions.checkNotNull(var4[var3]);
         }

         return var2;
      }
   }

   public static Comparator<Boolean> trueFirst() {
      return Booleans.BooleanComparator.TRUE_FIRST;
   }

   private static class BooleanArrayAsList extends AbstractList<Boolean> implements RandomAccess, Serializable {
      private static final long serialVersionUID = 0L;
      final boolean[] array;
      final int end;
      final int start;

      BooleanArrayAsList(boolean[] var1) {
         this(var1, 0, var1.length);
      }

      BooleanArrayAsList(boolean[] var1, int var2, int var3) {
         this.array = var1;
         this.start = var2;
         this.end = var3;
      }

      public boolean contains(Object var1) {
         boolean var2;
         if (var1 instanceof Boolean && Booleans.indexOf(this.array, (Boolean)var1, this.start, this.end) != -1) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public boolean equals(@NullableDecl Object var1) {
         if (var1 == this) {
            return true;
         } else if (var1 instanceof Booleans.BooleanArrayAsList) {
            Booleans.BooleanArrayAsList var4 = (Booleans.BooleanArrayAsList)var1;
            int var2 = this.size();
            if (var4.size() != var2) {
               return false;
            } else {
               for(int var3 = 0; var3 < var2; ++var3) {
                  if (this.array[this.start + var3] != var4.array[var4.start + var3]) {
                     return false;
                  }
               }

               return true;
            }
         } else {
            return super.equals(var1);
         }
      }

      public Boolean get(int var1) {
         Preconditions.checkElementIndex(var1, this.size());
         return this.array[this.start + var1];
      }

      public int hashCode() {
         int var1 = this.start;

         int var2;
         for(var2 = 1; var1 < this.end; ++var1) {
            var2 = var2 * 31 + Booleans.hashCode(this.array[var1]);
         }

         return var2;
      }

      public int indexOf(Object var1) {
         if (var1 instanceof Boolean) {
            int var2 = Booleans.indexOf(this.array, (Boolean)var1, this.start, this.end);
            if (var2 >= 0) {
               return var2 - this.start;
            }
         }

         return -1;
      }

      public boolean isEmpty() {
         return false;
      }

      public int lastIndexOf(Object var1) {
         if (var1 instanceof Boolean) {
            int var2 = Booleans.lastIndexOf(this.array, (Boolean)var1, this.start, this.end);
            if (var2 >= 0) {
               return var2 - this.start;
            }
         }

         return -1;
      }

      public Boolean set(int var1, Boolean var2) {
         Preconditions.checkElementIndex(var1, this.size());
         boolean[] var3 = this.array;
         int var4 = this.start;
         boolean var5 = var3[var4 + var1];
         var3[var4 + var1] = (Boolean)Preconditions.checkNotNull(var2);
         return var5;
      }

      public int size() {
         return this.end - this.start;
      }

      public List<Boolean> subList(int var1, int var2) {
         Preconditions.checkPositionIndexes(var1, var2, this.size());
         if (var1 == var2) {
            return Collections.emptyList();
         } else {
            boolean[] var3 = this.array;
            int var4 = this.start;
            return new Booleans.BooleanArrayAsList(var3, var1 + var4, var4 + var2);
         }
      }

      boolean[] toBooleanArray() {
         return Arrays.copyOfRange(this.array, this.start, this.end);
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder(this.size() * 7);
         String var2;
         if (this.array[this.start]) {
            var2 = "[true";
         } else {
            var2 = "[false";
         }

         var1.append(var2);
         int var3 = this.start;

         while(true) {
            ++var3;
            if (var3 >= this.end) {
               var1.append(']');
               return var1.toString();
            }

            if (this.array[var3]) {
               var2 = ", true";
            } else {
               var2 = ", false";
            }

            var1.append(var2);
         }
      }
   }

   private static enum BooleanComparator implements Comparator<Boolean> {
      FALSE_FIRST,
      TRUE_FIRST(1, "Booleans.trueFirst()");

      private final String toString;
      private final int trueValue;

      static {
         Booleans.BooleanComparator var0 = new Booleans.BooleanComparator("FALSE_FIRST", 1, -1, "Booleans.falseFirst()");
         FALSE_FIRST = var0;
      }

      private BooleanComparator(int var3, String var4) {
         this.trueValue = var3;
         this.toString = var4;
      }

      public int compare(Boolean var1, Boolean var2) {
         boolean var3 = var1;
         int var4 = 0;
         int var5;
         if (var3) {
            var5 = this.trueValue;
         } else {
            var5 = 0;
         }

         if (var2) {
            var4 = this.trueValue;
         }

         return var4 - var5;
      }

      public String toString() {
         return this.toString;
      }
   }

   private static enum LexicographicalComparator implements Comparator<boolean[]> {
      INSTANCE;

      static {
         Booleans.LexicographicalComparator var0 = new Booleans.LexicographicalComparator("INSTANCE", 0);
         INSTANCE = var0;
      }

      public int compare(boolean[] var1, boolean[] var2) {
         int var3 = Math.min(var1.length, var2.length);

         for(int var4 = 0; var4 < var3; ++var4) {
            int var5 = Booleans.compare(var1[var4], var2[var4]);
            if (var5 != 0) {
               return var5;
            }
         }

         return var1.length - var2.length;
      }

      public String toString() {
         return "Booleans.lexicographicalComparator()";
      }
   }
}
