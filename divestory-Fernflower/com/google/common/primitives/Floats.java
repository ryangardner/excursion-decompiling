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
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Floats {
   public static final int BYTES = 4;

   private Floats() {
   }

   public static List<Float> asList(float... var0) {
      return (List)(var0.length == 0 ? Collections.emptyList() : new Floats.FloatArrayAsList(var0));
   }

   public static int compare(float var0, float var1) {
      return Float.compare(var0, var1);
   }

   public static float[] concat(float[]... var0) {
      int var1 = var0.length;
      int var2 = 0;

      int var3;
      for(var3 = 0; var2 < var1; ++var2) {
         var3 += var0[var2].length;
      }

      float[] var4 = new float[var3];
      var1 = var0.length;
      var2 = 0;

      for(var3 = 0; var2 < var1; ++var2) {
         float[] var5 = var0[var2];
         System.arraycopy(var5, 0, var4, var3, var5.length);
         var3 += var5.length;
      }

      return var4;
   }

   public static float constrainToRange(float var0, float var1, float var2) {
      boolean var3;
      if (var1 <= var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3, "min (%s) must be less than or equal to max (%s)", var1, var2);
      return Math.min(Math.max(var0, var1), var2);
   }

   public static boolean contains(float[] var0, float var1) {
      int var2 = var0.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         if (var0[var3] == var1) {
            return true;
         }
      }

      return false;
   }

   public static float[] ensureCapacity(float[] var0, int var1, int var2) {
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
      float[] var5 = var0;
      if (var0.length < var1) {
         var5 = Arrays.copyOf(var0, var1 + var2);
      }

      return var5;
   }

   public static int hashCode(float var0) {
      return Float.valueOf(var0).hashCode();
   }

   public static int indexOf(float[] var0, float var1) {
      return indexOf(var0, var1, 0, var0.length);
   }

   private static int indexOf(float[] var0, float var1, int var2, int var3) {
      while(var2 < var3) {
         if (var0[var2] == var1) {
            return var2;
         }

         ++var2;
      }

      return -1;
   }

   public static int indexOf(float[] var0, float[] var1) {
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

   public static boolean isFinite(float var0) {
      boolean var1;
      if (Float.NEGATIVE_INFINITY < var0 && var0 < Float.POSITIVE_INFINITY) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static String join(String var0, float... var1) {
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

   public static int lastIndexOf(float[] var0, float var1) {
      return lastIndexOf(var0, var1, 0, var0.length);
   }

   private static int lastIndexOf(float[] var0, float var1, int var2, int var3) {
      --var3;

      while(var3 >= var2) {
         if (var0[var3] == var1) {
            return var3;
         }

         --var3;
      }

      return -1;
   }

   public static Comparator<float[]> lexicographicalComparator() {
      return Floats.LexicographicalComparator.INSTANCE;
   }

   public static float max(float... var0) {
      int var1 = var0.length;
      int var2 = 1;
      boolean var3;
      if (var1 > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);

      float var4;
      for(var4 = var0[0]; var2 < var0.length; ++var2) {
         var4 = Math.max(var4, var0[var2]);
      }

      return var4;
   }

   public static float min(float... var0) {
      int var1 = var0.length;
      int var2 = 1;
      boolean var3;
      if (var1 > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);

      float var4;
      for(var4 = var0[0]; var2 < var0.length; ++var2) {
         var4 = Math.min(var4, var0[var2]);
      }

      return var4;
   }

   public static void reverse(float[] var0) {
      Preconditions.checkNotNull(var0);
      reverse(var0, 0, var0.length);
   }

   public static void reverse(float[] var0, int var1, int var2) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkPositionIndexes(var1, var2, var0.length);
      --var2;

      while(var1 < var2) {
         float var3 = var0[var1];
         var0[var1] = var0[var2];
         var0[var2] = var3;
         ++var1;
         --var2;
      }

   }

   public static void sortDescending(float[] var0) {
      Preconditions.checkNotNull(var0);
      sortDescending(var0, 0, var0.length);
   }

   public static void sortDescending(float[] var0, int var1, int var2) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkPositionIndexes(var1, var2, var0.length);
      Arrays.sort(var0, var1, var2);
      reverse(var0, var1, var2);
   }

   public static Converter<String, Float> stringConverter() {
      return Floats.FloatConverter.INSTANCE;
   }

   public static float[] toArray(Collection<? extends Number> var0) {
      if (var0 instanceof Floats.FloatArrayAsList) {
         return ((Floats.FloatArrayAsList)var0).toFloatArray();
      } else {
         Object[] var4 = var0.toArray();
         int var1 = var4.length;
         float[] var2 = new float[var1];

         for(int var3 = 0; var3 < var1; ++var3) {
            var2[var3] = ((Number)Preconditions.checkNotNull(var4[var3])).floatValue();
         }

         return var2;
      }
   }

   @NullableDecl
   public static Float tryParse(String var0) {
      if (Doubles.FLOATING_POINT_PATTERN.matcher(var0).matches()) {
         float var1;
         try {
            var1 = Float.parseFloat(var0);
         } catch (NumberFormatException var2) {
            return null;
         }

         return var1;
      } else {
         return null;
      }
   }

   private static class FloatArrayAsList extends AbstractList<Float> implements RandomAccess, Serializable {
      private static final long serialVersionUID = 0L;
      final float[] array;
      final int end;
      final int start;

      FloatArrayAsList(float[] var1) {
         this(var1, 0, var1.length);
      }

      FloatArrayAsList(float[] var1, int var2, int var3) {
         this.array = var1;
         this.start = var2;
         this.end = var3;
      }

      public boolean contains(Object var1) {
         boolean var2;
         if (var1 instanceof Float && Floats.indexOf(this.array, (Float)var1, this.start, this.end) != -1) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public boolean equals(@NullableDecl Object var1) {
         if (var1 == this) {
            return true;
         } else if (var1 instanceof Floats.FloatArrayAsList) {
            Floats.FloatArrayAsList var4 = (Floats.FloatArrayAsList)var1;
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

      public Float get(int var1) {
         Preconditions.checkElementIndex(var1, this.size());
         return this.array[this.start + var1];
      }

      public int hashCode() {
         int var1 = this.start;

         int var2;
         for(var2 = 1; var1 < this.end; ++var1) {
            var2 = var2 * 31 + Floats.hashCode(this.array[var1]);
         }

         return var2;
      }

      public int indexOf(Object var1) {
         if (var1 instanceof Float) {
            int var2 = Floats.indexOf(this.array, (Float)var1, this.start, this.end);
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
         if (var1 instanceof Float) {
            int var2 = Floats.lastIndexOf(this.array, (Float)var1, this.start, this.end);
            if (var2 >= 0) {
               return var2 - this.start;
            }
         }

         return -1;
      }

      public Float set(int var1, Float var2) {
         Preconditions.checkElementIndex(var1, this.size());
         float[] var3 = this.array;
         int var4 = this.start;
         float var5 = var3[var4 + var1];
         var3[var4 + var1] = (Float)Preconditions.checkNotNull(var2);
         return var5;
      }

      public int size() {
         return this.end - this.start;
      }

      public List<Float> subList(int var1, int var2) {
         Preconditions.checkPositionIndexes(var1, var2, this.size());
         if (var1 == var2) {
            return Collections.emptyList();
         } else {
            float[] var3 = this.array;
            int var4 = this.start;
            return new Floats.FloatArrayAsList(var3, var1 + var4, var4 + var2);
         }
      }

      float[] toFloatArray() {
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

   private static final class FloatConverter extends Converter<String, Float> implements Serializable {
      static final Floats.FloatConverter INSTANCE = new Floats.FloatConverter();
      private static final long serialVersionUID = 1L;

      private Object readResolve() {
         return INSTANCE;
      }

      protected String doBackward(Float var1) {
         return var1.toString();
      }

      protected Float doForward(String var1) {
         return Float.valueOf(var1);
      }

      public String toString() {
         return "Floats.stringConverter()";
      }
   }

   private static enum LexicographicalComparator implements Comparator<float[]> {
      INSTANCE;

      static {
         Floats.LexicographicalComparator var0 = new Floats.LexicographicalComparator("INSTANCE", 0);
         INSTANCE = var0;
      }

      public int compare(float[] var1, float[] var2) {
         int var3 = Math.min(var1.length, var2.length);

         for(int var4 = 0; var4 < var3; ++var4) {
            int var5 = Float.compare(var1[var4], var2[var4]);
            if (var5 != 0) {
               return var5;
            }
         }

         return var1.length - var2.length;
      }

      public String toString() {
         return "Floats.lexicographicalComparator()";
      }
   }
}
