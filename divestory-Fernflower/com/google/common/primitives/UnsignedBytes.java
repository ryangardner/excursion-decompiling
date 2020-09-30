package com.google.common.primitives;

import com.google.common.base.Preconditions;
import java.lang.reflect.Field;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Arrays;
import java.util.Comparator;
import sun.misc.Unsafe;

public final class UnsignedBytes {
   public static final byte MAX_POWER_OF_TWO = -128;
   public static final byte MAX_VALUE = -1;
   private static final int UNSIGNED_MASK = 255;

   private UnsignedBytes() {
   }

   public static byte checkedCast(long var0) {
      boolean var2;
      if (var0 >> 8 == 0L) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "out of range: %s", var0);
      return (byte)((int)var0);
   }

   public static int compare(byte var0, byte var1) {
      return toInt(var0) - toInt(var1);
   }

   private static byte flip(byte var0) {
      return (byte)(var0 ^ 128);
   }

   public static String join(String var0, byte... var1) {
      Preconditions.checkNotNull(var0);
      if (var1.length == 0) {
         return "";
      } else {
         StringBuilder var2 = new StringBuilder(var1.length * (var0.length() + 3));
         var2.append(toInt(var1[0]));

         for(int var3 = 1; var3 < var1.length; ++var3) {
            var2.append(var0);
            var2.append(toString(var1[var3]));
         }

         return var2.toString();
      }
   }

   public static Comparator<byte[]> lexicographicalComparator() {
      return UnsignedBytes.LexicographicalComparatorHolder.BEST_COMPARATOR;
   }

   static Comparator<byte[]> lexicographicalComparatorJavaImpl() {
      return UnsignedBytes.LexicographicalComparatorHolder.PureJavaComparator.INSTANCE;
   }

   public static byte max(byte... var0) {
      int var1 = var0.length;
      int var2 = 1;
      boolean var3;
      if (var1 > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);

      int var4;
      for(var4 = toInt(var0[0]); var2 < var0.length; var4 = var1) {
         int var5 = toInt(var0[var2]);
         var1 = var4;
         if (var5 > var4) {
            var1 = var5;
         }

         ++var2;
      }

      return (byte)var4;
   }

   public static byte min(byte... var0) {
      int var1 = var0.length;
      int var2 = 1;
      boolean var3;
      if (var1 > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3);

      int var4;
      for(var4 = toInt(var0[0]); var2 < var0.length; var4 = var1) {
         int var5 = toInt(var0[var2]);
         var1 = var4;
         if (var5 < var4) {
            var1 = var5;
         }

         ++var2;
      }

      return (byte)var4;
   }

   public static byte parseUnsignedByte(String var0) {
      return parseUnsignedByte(var0, 10);
   }

   public static byte parseUnsignedByte(String var0, int var1) {
      var1 = Integer.parseInt((String)Preconditions.checkNotNull(var0), var1);
      if (var1 >> 8 == 0) {
         return (byte)var1;
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("out of range: ");
         var2.append(var1);
         throw new NumberFormatException(var2.toString());
      }
   }

   public static byte saturatedCast(long var0) {
      if (var0 > (long)toInt((byte)-1)) {
         return -1;
      } else {
         return var0 < 0L ? 0 : (byte)((int)var0);
      }
   }

   public static void sort(byte[] var0) {
      Preconditions.checkNotNull(var0);
      sort(var0, 0, var0.length);
   }

   public static void sort(byte[] var0, int var1, int var2) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkPositionIndexes(var1, var2, var0.length);

      for(int var3 = var1; var3 < var2; ++var3) {
         var0[var3] = flip(var0[var3]);
      }

      Arrays.sort(var0, var1, var2);

      while(var1 < var2) {
         var0[var1] = flip(var0[var1]);
         ++var1;
      }

   }

   public static void sortDescending(byte[] var0) {
      Preconditions.checkNotNull(var0);
      sortDescending(var0, 0, var0.length);
   }

   public static void sortDescending(byte[] var0, int var1, int var2) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkPositionIndexes(var1, var2, var0.length);

      for(int var3 = var1; var3 < var2; ++var3) {
         var0[var3] = (byte)((byte)(var0[var3] ^ 127));
      }

      Arrays.sort(var0, var1, var2);

      while(var1 < var2) {
         var0[var1] = (byte)((byte)(var0[var1] ^ 127));
         ++var1;
      }

   }

   public static int toInt(byte var0) {
      return var0 & 255;
   }

   public static String toString(byte var0) {
      return toString(var0, 10);
   }

   public static String toString(byte var0, int var1) {
      boolean var2;
      if (var1 >= 2 && var1 <= 36) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "radix (%s) must be between Character.MIN_RADIX and Character.MAX_RADIX", var1);
      return Integer.toString(toInt(var0), var1);
   }

   static class LexicographicalComparatorHolder {
      static final Comparator<byte[]> BEST_COMPARATOR;
      static final String UNSAFE_COMPARATOR_NAME;

      static {
         StringBuilder var0 = new StringBuilder();
         var0.append(UnsignedBytes.LexicographicalComparatorHolder.class.getName());
         var0.append("$UnsafeComparator");
         UNSAFE_COMPARATOR_NAME = var0.toString();
         BEST_COMPARATOR = getBestComparator();
      }

      static Comparator<byte[]> getBestComparator() {
         try {
            Comparator var0 = (Comparator)Class.forName(UNSAFE_COMPARATOR_NAME).getEnumConstants()[0];
            return var0;
         } finally {
            ;
         }
      }

      static enum PureJavaComparator implements Comparator<byte[]> {
         INSTANCE;

         static {
            UnsignedBytes.LexicographicalComparatorHolder.PureJavaComparator var0 = new UnsignedBytes.LexicographicalComparatorHolder.PureJavaComparator("INSTANCE", 0);
            INSTANCE = var0;
         }

         public int compare(byte[] var1, byte[] var2) {
            int var3 = Math.min(var1.length, var2.length);

            for(int var4 = 0; var4 < var3; ++var4) {
               int var5 = UnsignedBytes.compare(var1[var4], var2[var4]);
               if (var5 != 0) {
                  return var5;
               }
            }

            return var1.length - var2.length;
         }

         public String toString() {
            return "UnsignedBytes.lexicographicalComparator() (pure Java version)";
         }
      }

      static enum UnsafeComparator implements Comparator<byte[]> {
         static final boolean BIG_ENDIAN;
         static final int BYTE_ARRAY_BASE_OFFSET;
         INSTANCE;

         static final Unsafe theUnsafe;

         static {
            UnsignedBytes.LexicographicalComparatorHolder.UnsafeComparator var0 = new UnsignedBytes.LexicographicalComparatorHolder.UnsafeComparator("INSTANCE", 0);
            INSTANCE = var0;
            BIG_ENDIAN = ByteOrder.nativeOrder().equals(ByteOrder.BIG_ENDIAN);
            Unsafe var1 = getUnsafe();
            theUnsafe = var1;
            BYTE_ARRAY_BASE_OFFSET = var1.arrayBaseOffset(byte[].class);
            if (!"64".equals(System.getProperty("sun.arch.data.model")) || BYTE_ARRAY_BASE_OFFSET % 8 != 0 || theUnsafe.arrayIndexScale(byte[].class) != 1) {
               throw new Error();
            }
         }

         private static Unsafe getUnsafe() {
            Unsafe var3;
            try {
               var3 = Unsafe.getUnsafe();
               return var3;
            } catch (SecurityException var2) {
               try {
                  PrivilegedExceptionAction var0 = new PrivilegedExceptionAction<Unsafe>() {
                     public Unsafe run() throws Exception {
                        Field[] var1 = Unsafe.class.getDeclaredFields();
                        int var2 = var1.length;

                        for(int var3 = 0; var3 < var2; ++var3) {
                           Field var4 = var1[var3];
                           var4.setAccessible(true);
                           Object var5 = var4.get((Object)null);
                           if (Unsafe.class.isInstance(var5)) {
                              return (Unsafe)Unsafe.class.cast(var5);
                           }
                        }

                        throw new NoSuchFieldError("the Unsafe");
                     }
                  };
                  var3 = (Unsafe)AccessController.doPrivileged(var0);
                  return var3;
               } catch (PrivilegedActionException var1) {
                  throw new RuntimeException("Could not initialize intrinsics", var1.getCause());
               }
            }
         }

         public int compare(byte[] var1, byte[] var2) {
            int var3 = Math.min(var1.length, var2.length);
            int var4 = 0;

            while(true) {
               int var5 = var4;
               if (var4 >= (var3 & -8)) {
                  while(var5 < var3) {
                     var4 = UnsignedBytes.compare(var1[var5], var2[var5]);
                     if (var4 != 0) {
                        return var4;
                     }

                     ++var5;
                  }

                  return var1.length - var2.length;
               }

               Unsafe var6 = theUnsafe;
               long var7 = (long)BYTE_ARRAY_BASE_OFFSET;
               long var9 = (long)var4;
               var7 = var6.getLong(var1, var7 + var9);
               var9 = theUnsafe.getLong(var2, (long)BYTE_ARRAY_BASE_OFFSET + var9);
               if (var7 != var9) {
                  if (BIG_ENDIAN) {
                     return UnsignedLongs.compare(var7, var9);
                  }

                  var4 = Long.numberOfTrailingZeros(var7 ^ var9) & -8;
                  return (int)(var7 >>> var4 & 255L) - (int)(var9 >>> var4 & 255L);
               }

               var4 += 8;
            }
         }

         public String toString() {
            return "UnsignedBytes.lexicographicalComparator() (sun.misc.Unsafe version)";
         }
      }
   }
}
