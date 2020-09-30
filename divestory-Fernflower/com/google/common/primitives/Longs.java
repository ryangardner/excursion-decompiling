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

public final class Longs {
   public static final int BYTES = 8;
   public static final long MAX_POWER_OF_TWO = 4611686018427387904L;

   private Longs() {
   }

   public static List<Long> asList(long... var0) {
      return (List)(var0.length == 0 ? Collections.emptyList() : new Longs.LongArrayAsList(var0));
   }

   public static int compare(long var0, long var2) {
      long var6;
      int var4 = (var6 = var0 - var2) == 0L ? 0 : (var6 < 0L ? -1 : 1);
      byte var5;
      if (var4 < 0) {
         var5 = -1;
      } else if (var4 > 0) {
         var5 = 1;
      } else {
         var5 = 0;
      }

      return var5;
   }

   public static long[] concat(long[]... var0) {
      int var1 = var0.length;
      int var2 = 0;

      int var3;
      for(var3 = 0; var2 < var1; ++var2) {
         var3 += var0[var2].length;
      }

      long[] var4 = new long[var3];
      var1 = var0.length;
      var3 = 0;

      for(var2 = 0; var3 < var1; ++var3) {
         long[] var5 = var0[var3];
         System.arraycopy(var5, 0, var4, var2, var5.length);
         var2 += var5.length;
      }

      return var4;
   }

   public static long constrainToRange(long var0, long var2, long var4) {
      boolean var6;
      if (var2 <= var4) {
         var6 = true;
      } else {
         var6 = false;
      }

      Preconditions.checkArgument(var6, "min (%s) must be less than or equal to max (%s)", var2, var4);
      return Math.min(Math.max(var0, var2), var4);
   }

   public static boolean contains(long[] var0, long var1) {
      int var3 = var0.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         if (var0[var4] == var1) {
            return true;
         }
      }

      return false;
   }

   public static long[] ensureCapacity(long[] var0, int var1, int var2) {
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
      long[] var5 = var0;
      if (var0.length < var1) {
         var5 = Arrays.copyOf(var0, var1 + var2);
      }

      return var5;
   }

   public static long fromByteArray(byte[] var0) {
      boolean var1;
      if (var0.length >= 8) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkArgument(var1, "array too small: %s < %s", (int)var0.length, (int)8);
      return fromBytes(var0[0], var0[1], var0[2], var0[3], var0[4], var0[5], var0[6], var0[7]);
   }

   public static long fromBytes(byte var0, byte var1, byte var2, byte var3, byte var4, byte var5, byte var6, byte var7) {
      long var8 = (long)var0;
      return ((long)var1 & 255L) << 48 | (var8 & 255L) << 56 | ((long)var2 & 255L) << 40 | ((long)var3 & 255L) << 32 | ((long)var4 & 255L) << 24 | ((long)var5 & 255L) << 16 | ((long)var6 & 255L) << 8 | (long)var7 & 255L;
   }

   public static int hashCode(long var0) {
      return (int)(var0 ^ var0 >>> 32);
   }

   public static int indexOf(long[] var0, long var1) {
      return indexOf(var0, var1, 0, var0.length);
   }

   private static int indexOf(long[] var0, long var1, int var3, int var4) {
      while(var3 < var4) {
         if (var0[var3] == var1) {
            return var3;
         }

         ++var3;
      }

      return -1;
   }

   public static int indexOf(long[] var0, long[] var1) {
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

   public static String join(String var0, long... var1) {
      Preconditions.checkNotNull(var0);
      if (var1.length == 0) {
         return "";
      } else {
         StringBuilder var2 = new StringBuilder(var1.length * 10);
         var2.append(var1[0]);

         for(int var3 = 1; var3 < var1.length; ++var3) {
            var2.append(var0);
            var2.append(var1[var3]);
         }

         return var2.toString();
      }
   }

   public static int lastIndexOf(long[] var0, long var1) {
      return lastIndexOf(var0, var1, 0, var0.length);
   }

   private static int lastIndexOf(long[] var0, long var1, int var3, int var4) {
      --var4;

      while(var4 >= var3) {
         if (var0[var4] == var1) {
            return var4;
         }

         --var4;
      }

      return -1;
   }

   public static Comparator<long[]> lexicographicalComparator() {
      return Longs.LexicographicalComparator.INSTANCE;
   }

   public static long max(long... var0) {
      int var1 = var0.length;
      int var2 = 1;
      boolean var3;
      if (var1 > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);

      long var4;
      long var6;
      for(var4 = var0[0]; var2 < var0.length; var4 = var6) {
         var6 = var4;
         if (var0[var2] > var4) {
            var6 = var0[var2];
         }

         ++var2;
      }

      return var4;
   }

   public static long min(long... var0) {
      int var1 = var0.length;
      int var2 = 1;
      boolean var3;
      if (var1 > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);

      long var4;
      long var6;
      for(var4 = var0[0]; var2 < var0.length; var4 = var6) {
         var6 = var4;
         if (var0[var2] < var4) {
            var6 = var0[var2];
         }

         ++var2;
      }

      return var4;
   }

   public static void reverse(long[] var0) {
      Preconditions.checkNotNull(var0);
      reverse(var0, 0, var0.length);
   }

   public static void reverse(long[] var0, int var1, int var2) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkPositionIndexes(var1, var2, var0.length);
      --var2;

      while(var1 < var2) {
         long var3 = var0[var1];
         var0[var1] = var0[var2];
         var0[var2] = var3;
         ++var1;
         --var2;
      }

   }

   public static void sortDescending(long[] var0) {
      Preconditions.checkNotNull(var0);
      sortDescending(var0, 0, var0.length);
   }

   public static void sortDescending(long[] var0, int var1, int var2) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkPositionIndexes(var1, var2, var0.length);
      Arrays.sort(var0, var1, var2);
      reverse(var0, var1, var2);
   }

   public static Converter<String, Long> stringConverter() {
      return Longs.LongConverter.INSTANCE;
   }

   public static long[] toArray(Collection<? extends Number> var0) {
      if (var0 instanceof Longs.LongArrayAsList) {
         return ((Longs.LongArrayAsList)var0).toLongArray();
      } else {
         Object[] var4 = var0.toArray();
         int var1 = var4.length;
         long[] var2 = new long[var1];

         for(int var3 = 0; var3 < var1; ++var3) {
            var2[var3] = ((Number)Preconditions.checkNotNull(var4[var3])).longValue();
         }

         return var2;
      }
   }

   public static byte[] toByteArray(long var0) {
      byte[] var2 = new byte[8];

      for(int var3 = 7; var3 >= 0; --var3) {
         var2[var3] = (byte)((byte)((int)(255L & var0)));
         var0 >>= 8;
      }

      return var2;
   }

   @NullableDecl
   public static Long tryParse(String var0) {
      return tryParse(var0, 10);
   }

   @NullableDecl
   public static Long tryParse(String var0, int var1) {
      if (((String)Preconditions.checkNotNull(var0)).isEmpty()) {
         return null;
      } else if (var1 >= 2 && var1 <= 36) {
         byte var2 = 0;
         if (var0.charAt(0) == '-') {
            var2 = 1;
         }

         if (var2 == var0.length()) {
            return null;
         } else {
            int var3 = var2 + 1;
            int var4 = Longs.AsciiDigits.digit(var0.charAt(var2));
            if (var4 >= 0 && var4 < var1) {
               long var5 = (long)(-var4);
               long var7 = (long)var1;

               for(long var9 = Long.MIN_VALUE / var7; var3 < var0.length(); ++var3) {
                  var4 = Longs.AsciiDigits.digit(var0.charAt(var3));
                  if (var4 < 0 || var4 >= var1 || var5 < var9) {
                     return null;
                  }

                  var5 *= var7;
                  long var11 = (long)var4;
                  if (var5 < var11 - Long.MIN_VALUE) {
                     return null;
                  }

                  var5 -= var11;
               }

               if (var2 != 0) {
                  return var5;
               } else if (var5 == Long.MIN_VALUE) {
                  return null;
               } else {
                  return -var5;
               }
            } else {
               return null;
            }
         }
      } else {
         StringBuilder var13 = new StringBuilder();
         var13.append("radix must be between MIN_RADIX and MAX_RADIX but was ");
         var13.append(var1);
         throw new IllegalArgumentException(var13.toString());
      }
   }

   static final class AsciiDigits {
      private static final byte[] asciiDigits;

      static {
         byte[] var0 = new byte[128];
         Arrays.fill(var0, (byte)-1);
         byte var1 = 0;
         int var2 = 0;

         while(true) {
            int var3 = var1;
            if (var2 > 9) {
               while(var3 <= 26) {
                  byte var4 = (byte)(var3 + 10);
                  var0[var3 + 65] = (byte)var4;
                  var0[var3 + 97] = (byte)var4;
                  ++var3;
               }

               asciiDigits = var0;
               return;
            }

            var0[var2 + 48] = (byte)((byte)var2);
            ++var2;
         }
      }

      private AsciiDigits() {
      }

      static int digit(char var0) {
         byte var1;
         if (var0 < 128) {
            var1 = asciiDigits[var0];
         } else {
            var1 = -1;
         }

         return var1;
      }
   }

   private static enum LexicographicalComparator implements Comparator<long[]> {
      INSTANCE;

      static {
         Longs.LexicographicalComparator var0 = new Longs.LexicographicalComparator("INSTANCE", 0);
         INSTANCE = var0;
      }

      public int compare(long[] var1, long[] var2) {
         int var3 = Math.min(var1.length, var2.length);

         for(int var4 = 0; var4 < var3; ++var4) {
            int var5 = Longs.compare(var1[var4], var2[var4]);
            if (var5 != 0) {
               return var5;
            }
         }

         return var1.length - var2.length;
      }

      public String toString() {
         return "Longs.lexicographicalComparator()";
      }
   }

   private static class LongArrayAsList extends AbstractList<Long> implements RandomAccess, Serializable {
      private static final long serialVersionUID = 0L;
      final long[] array;
      final int end;
      final int start;

      LongArrayAsList(long[] var1) {
         this(var1, 0, var1.length);
      }

      LongArrayAsList(long[] var1, int var2, int var3) {
         this.array = var1;
         this.start = var2;
         this.end = var3;
      }

      public boolean contains(Object var1) {
         boolean var2;
         if (var1 instanceof Long && Longs.indexOf(this.array, (Long)var1, this.start, this.end) != -1) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public boolean equals(@NullableDecl Object var1) {
         if (var1 == this) {
            return true;
         } else if (var1 instanceof Longs.LongArrayAsList) {
            Longs.LongArrayAsList var4 = (Longs.LongArrayAsList)var1;
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

      public Long get(int var1) {
         Preconditions.checkElementIndex(var1, this.size());
         return this.array[this.start + var1];
      }

      public int hashCode() {
         int var1 = this.start;

         int var2;
         for(var2 = 1; var1 < this.end; ++var1) {
            var2 = var2 * 31 + Longs.hashCode(this.array[var1]);
         }

         return var2;
      }

      public int indexOf(Object var1) {
         if (var1 instanceof Long) {
            int var2 = Longs.indexOf(this.array, (Long)var1, this.start, this.end);
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
         if (var1 instanceof Long) {
            int var2 = Longs.lastIndexOf(this.array, (Long)var1, this.start, this.end);
            if (var2 >= 0) {
               return var2 - this.start;
            }
         }

         return -1;
      }

      public Long set(int var1, Long var2) {
         Preconditions.checkElementIndex(var1, this.size());
         long[] var3 = this.array;
         int var4 = this.start;
         long var5 = var3[var4 + var1];
         var3[var4 + var1] = (Long)Preconditions.checkNotNull(var2);
         return var5;
      }

      public int size() {
         return this.end - this.start;
      }

      public List<Long> subList(int var1, int var2) {
         Preconditions.checkPositionIndexes(var1, var2, this.size());
         if (var1 == var2) {
            return Collections.emptyList();
         } else {
            long[] var3 = this.array;
            int var4 = this.start;
            return new Longs.LongArrayAsList(var3, var1 + var4, var4 + var2);
         }
      }

      long[] toLongArray() {
         return Arrays.copyOfRange(this.array, this.start, this.end);
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder(this.size() * 10);
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

   private static final class LongConverter extends Converter<String, Long> implements Serializable {
      static final Longs.LongConverter INSTANCE = new Longs.LongConverter();
      private static final long serialVersionUID = 1L;

      private Object readResolve() {
         return INSTANCE;
      }

      protected String doBackward(Long var1) {
         return var1.toString();
      }

      protected Long doForward(String var1) {
         return Long.decode(var1);
      }

      public String toString() {
         return "Longs.stringConverter()";
      }
   }
}
