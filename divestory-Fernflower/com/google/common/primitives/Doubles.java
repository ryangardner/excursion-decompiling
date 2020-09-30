package com.google.common.primitives;

import com.google.common.base.Converter;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import java.util.regex.Pattern;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Doubles {
   public static final int BYTES = 8;
   static final Pattern FLOATING_POINT_PATTERN = fpPattern();

   private Doubles() {
   }

   public static List<Double> asList(double... var0) {
      return (List)(var0.length == 0 ? Collections.emptyList() : new Doubles.DoubleArrayAsList(var0));
   }

   public static int compare(double var0, double var2) {
      return Double.compare(var0, var2);
   }

   public static double[] concat(double[]... var0) {
      int var1 = var0.length;
      int var2 = 0;

      int var3;
      for(var3 = 0; var2 < var1; ++var2) {
         var3 += var0[var2].length;
      }

      double[] var4 = new double[var3];
      var1 = var0.length;
      var3 = 0;

      for(var2 = 0; var3 < var1; ++var3) {
         double[] var5 = var0[var3];
         System.arraycopy(var5, 0, var4, var2, var5.length);
         var2 += var5.length;
      }

      return var4;
   }

   public static double constrainToRange(double var0, double var2, double var4) {
      boolean var6;
      if (var2 <= var4) {
         var6 = true;
      } else {
         var6 = false;
      }

      Preconditions.checkArgument(var6, "min (%s) must be less than or equal to max (%s)", var2, var4);
      return Math.min(Math.max(var0, var2), var4);
   }

   public static boolean contains(double[] var0, double var1) {
      int var3 = var0.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         if (var0[var4] == var1) {
            return true;
         }
      }

      return false;
   }

   public static double[] ensureCapacity(double[] var0, int var1, int var2) {
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
      double[] var5 = var0;
      if (var0.length < var1) {
         var5 = Arrays.copyOf(var0, var1 + var2);
      }

      return var5;
   }

   private static Pattern fpPattern() {
      StringBuilder var0 = new StringBuilder();
      var0.append("(?:\\d+#(?:\\.\\d*#)?|\\.\\d+#)");
      var0.append("(?:[eE][+-]?\\d+#)?[fFdD]?");
      String var3 = var0.toString();
      StringBuilder var1 = new StringBuilder();
      var1.append("0[xX]");
      var1.append("(?:[0-9a-fA-F]+#(?:\\.[0-9a-fA-F]*#)?|\\.[0-9a-fA-F]+#)");
      var1.append("[pP][+-]?\\d+#[fFdD]?");
      String var4 = var1.toString();
      StringBuilder var2 = new StringBuilder();
      var2.append("[+-]?(?:NaN|Infinity|");
      var2.append(var3);
      var2.append("|");
      var2.append(var4);
      var2.append(")");
      return Pattern.compile(var2.toString().replace("#", "+"));
   }

   public static int hashCode(double var0) {
      return Double.valueOf(var0).hashCode();
   }

   public static int indexOf(double[] var0, double var1) {
      return indexOf(var0, var1, 0, var0.length);
   }

   private static int indexOf(double[] var0, double var1, int var3, int var4) {
      while(var3 < var4) {
         if (var0[var3] == var1) {
            return var3;
         }

         ++var3;
      }

      return -1;
   }

   public static int indexOf(double[] var0, double[] var1) {
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

   public static boolean isFinite(double var0) {
      boolean var2;
      if (Double.NEGATIVE_INFINITY < var0 && var0 < Double.POSITIVE_INFINITY) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public static String join(String var0, double... var1) {
      Preconditions.checkNotNull(var0);
      if (var1.length == 0) {
         return "";
      } else {
         StringBuilder var2 = new StringBuilder(var1.length * 12);
         var2.append(var1[0]);

         for(int var3 = 1; var3 < var1.length; ++var3) {
            var2.append(var0);
            var2.append(var1[var3]);
         }

         return var2.toString();
      }
   }

   public static int lastIndexOf(double[] var0, double var1) {
      return lastIndexOf(var0, var1, 0, var0.length);
   }

   private static int lastIndexOf(double[] var0, double var1, int var3, int var4) {
      --var4;

      while(var4 >= var3) {
         if (var0[var4] == var1) {
            return var4;
         }

         --var4;
      }

      return -1;
   }

   public static Comparator<double[]> lexicographicalComparator() {
      return Doubles.LexicographicalComparator.INSTANCE;
   }

   public static double max(double... var0) {
      int var1 = var0.length;
      int var2 = 1;
      boolean var3;
      if (var1 > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);

      double var4;
      for(var4 = var0[0]; var2 < var0.length; ++var2) {
         var4 = Math.max(var4, var0[var2]);
      }

      return var4;
   }

   public static double min(double... var0) {
      int var1 = var0.length;
      int var2 = 1;
      boolean var3;
      if (var1 > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);

      double var4;
      for(var4 = var0[0]; var2 < var0.length; ++var2) {
         var4 = Math.min(var4, var0[var2]);
      }

      return var4;
   }

   public static void reverse(double[] var0) {
      Preconditions.checkNotNull(var0);
      reverse(var0, 0, var0.length);
   }

   public static void reverse(double[] var0, int var1, int var2) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkPositionIndexes(var1, var2, var0.length);
      --var2;

      while(var1 < var2) {
         double var3 = var0[var1];
         var0[var1] = var0[var2];
         var0[var2] = var3;
         ++var1;
         --var2;
      }

   }

   public static void sortDescending(double[] var0) {
      Preconditions.checkNotNull(var0);
      sortDescending(var0, 0, var0.length);
   }

   public static void sortDescending(double[] var0, int var1, int var2) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkPositionIndexes(var1, var2, var0.length);
      Arrays.sort(var0, var1, var2);
      reverse(var0, var1, var2);
   }

   public static Converter<String, Double> stringConverter() {
      return Doubles.DoubleConverter.INSTANCE;
   }

   public static double[] toArray(Collection<? extends Number> var0) {
      if (var0 instanceof Doubles.DoubleArrayAsList) {
         return ((Doubles.DoubleArrayAsList)var0).toDoubleArray();
      } else {
         Object[] var4 = var0.toArray();
         int var1 = var4.length;
         double[] var2 = new double[var1];

         for(int var3 = 0; var3 < var1; ++var3) {
            var2[var3] = ((Number)Preconditions.checkNotNull(var4[var3])).doubleValue();
         }

         return var2;
      }
   }

   @NullableDecl
   public static Double tryParse(String var0) {
      if (FLOATING_POINT_PATTERN.matcher(var0).matches()) {
         double var1;
         try {
            var1 = Double.parseDouble(var0);
         } catch (NumberFormatException var3) {
            return null;
         }

         return var1;
      } else {
         return null;
      }
   }

   private static class DoubleArrayAsList extends AbstractList<Double> implements RandomAccess, Serializable {
      private static final long serialVersionUID = 0L;
      final double[] array;
      final int end;
      final int start;

      DoubleArrayAsList(double[] var1) {
         this(var1, 0, var1.length);
      }

      DoubleArrayAsList(double[] var1, int var2, int var3) {
         this.array = var1;
         this.start = var2;
         this.end = var3;
      }

      public boolean contains(Object var1) {
         boolean var2;
         if (var1 instanceof Double && Doubles.indexOf(this.array, (Double)var1, this.start, this.end) != -1) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public boolean equals(@NullableDecl Object var1) {
         if (var1 == this) {
            return true;
         } else if (var1 instanceof Doubles.DoubleArrayAsList) {
            Doubles.DoubleArrayAsList var4 = (Doubles.DoubleArrayAsList)var1;
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

      public Double get(int var1) {
         Preconditions.checkElementIndex(var1, this.size());
         return this.array[this.start + var1];
      }

      public int hashCode() {
         int var1 = this.start;

         int var2;
         for(var2 = 1; var1 < this.end; ++var1) {
            var2 = var2 * 31 + Doubles.hashCode(this.array[var1]);
         }

         return var2;
      }

      public int indexOf(Object var1) {
         if (var1 instanceof Double) {
            int var2 = Doubles.indexOf(this.array, (Double)var1, this.start, this.end);
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
         if (var1 instanceof Double) {
            int var2 = Doubles.lastIndexOf(this.array, (Double)var1, this.start, this.end);
            if (var2 >= 0) {
               return var2 - this.start;
            }
         }

         return -1;
      }

      public Double set(int var1, Double var2) {
         Preconditions.checkElementIndex(var1, this.size());
         double[] var3 = this.array;
         int var4 = this.start;
         double var5 = var3[var4 + var1];
         var3[var4 + var1] = (Double)Preconditions.checkNotNull(var2);
         return var5;
      }

      public int size() {
         return this.end - this.start;
      }

      public List<Double> subList(int var1, int var2) {
         Preconditions.checkPositionIndexes(var1, var2, this.size());
         if (var1 == var2) {
            return Collections.emptyList();
         } else {
            double[] var3 = this.array;
            int var4 = this.start;
            return new Doubles.DoubleArrayAsList(var3, var1 + var4, var4 + var2);
         }
      }

      double[] toDoubleArray() {
         return Arrays.copyOfRange(this.array, this.start, this.end);
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder(this.size() * 12);
         var1.append('[');
         var1.append(this.array[this.start]);
         int var2 = this.start;

         while(true) {
            ++var2;
            if (var2 >= this.end) {
               var1.append(']');
               return var1.toString();
            }

            var1.append(", ");
            var1.append(this.array[var2]);
         }
      }
   }

   private static final class DoubleConverter extends Converter<String, Double> implements Serializable {
      static final Doubles.DoubleConverter INSTANCE = new Doubles.DoubleConverter();
      private static final long serialVersionUID = 1L;

      private Object readResolve() {
         return INSTANCE;
      }

      protected String doBackward(Double var1) {
         return var1.toString();
      }

      protected Double doForward(String var1) {
         return Double.valueOf(var1);
      }

      public String toString() {
         return "Doubles.stringConverter()";
      }
   }

   private static enum LexicographicalComparator implements Comparator<double[]> {
      INSTANCE;

      static {
         Doubles.LexicographicalComparator var0 = new Doubles.LexicographicalComparator("INSTANCE", 0);
         INSTANCE = var0;
      }

      public int compare(double[] var1, double[] var2) {
         int var3 = Math.min(var1.length, var2.length);

         for(int var4 = 0; var4 < var3; ++var4) {
            int var5 = Double.compare(var1[var4], var2[var4]);
            if (var5 != 0) {
               return var5;
            }
         }

         return var1.length - var2.length;
      }

      public String toString() {
         return "Doubles.lexicographicalComparator()";
      }
   }
}
