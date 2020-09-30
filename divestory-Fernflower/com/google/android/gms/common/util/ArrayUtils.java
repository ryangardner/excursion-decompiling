package com.google.android.gms.common.util;

import com.google.android.gms.common.internal.Objects;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public final class ArrayUtils {
   private ArrayUtils() {
   }

   public static <T> T[] appendToArray(T[] var0, T var1) {
      if (var0 == null && var1 == null) {
         throw new IllegalArgumentException("Cannot generate array of generic type w/o class info");
      } else {
         if (var0 == null) {
            var0 = (Object[])Array.newInstance(var1.getClass(), 1);
         } else {
            var0 = Arrays.copyOf(var0, var0.length + 1);
         }

         var0[var0.length - 1] = var1;
         return var0;
      }
   }

   public static <T> T[] concat(T[]... var0) {
      if (var0.length == 0) {
         return (Object[])Array.newInstance(var0.getClass(), 0);
      } else {
         int var1 = 0;

         int var2;
         for(var2 = 0; var1 < var0.length; ++var1) {
            var2 += var0[var1].length;
         }

         Object[] var3 = Arrays.copyOf(var0[0], var2);
         var2 = var0[0].length;

         for(var1 = 1; var1 < var0.length; ++var1) {
            Object[] var4 = var0[var1];
            System.arraycopy(var4, 0, var3, var2, var4.length);
            var2 += var4.length;
         }

         return var3;
      }
   }

   public static byte[] concatByteArrays(byte[]... var0) {
      if (var0.length == 0) {
         return new byte[0];
      } else {
         int var1 = 0;

         int var2;
         for(var2 = 0; var1 < var0.length; ++var1) {
            var2 += var0[var1].length;
         }

         byte[] var3 = Arrays.copyOf(var0[0], var2);
         var1 = var0[0].length;

         for(var2 = 1; var2 < var0.length; ++var2) {
            byte[] var4 = var0[var2];
            System.arraycopy(var4, 0, var3, var1, var4.length);
            var1 += var4.length;
         }

         return var3;
      }
   }

   public static boolean contains(int[] var0, int var1) {
      if (var0 == null) {
         return false;
      } else {
         int var2 = var0.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            if (var0[var3] == var1) {
               return true;
            }
         }

         return false;
      }
   }

   public static <T> boolean contains(T[] var0, T var1) {
      int var2;
      if (var0 != null) {
         var2 = var0.length;
      } else {
         var2 = 0;
      }

      int var3 = 0;

      while(true) {
         if (var3 >= var2) {
            var3 = -1;
            break;
         }

         if (Objects.equal(var0[var3], var1)) {
            break;
         }

         ++var3;
      }

      return var3 >= 0;
   }

   public static <T> ArrayList<T> newArrayList() {
      return new ArrayList();
   }

   public static <T> T[] removeAll(T[] var0, T... var1) {
      if (var0 == null) {
         return null;
      } else if (var1 != null && var1.length != 0) {
         Object[] var2 = (Object[])Array.newInstance(var1.getClass().getComponentType(), var0.length);
         int var3 = var1.length;
         int var4 = 0;
         int var5;
         int var6;
         Object var7;
         if (var3 == 1) {
            var5 = var0.length;
            var4 = 0;
            var3 = 0;

            while(true) {
               var6 = var3;
               if (var4 >= var5) {
                  break;
               }

               var7 = var0[var4];
               var6 = var3;
               if (!Objects.equal(var1[0], var7)) {
                  var2[var3] = var7;
                  var6 = var3 + 1;
               }

               ++var4;
               var3 = var6;
            }
         } else {
            var5 = var0.length;

            for(var3 = 0; var4 < var5; var3 = var6) {
               var7 = var0[var4];
               var6 = var3;
               if (!contains(var1, var7)) {
                  var2[var3] = var7;
                  var6 = var3 + 1;
               }

               ++var4;
            }

            var6 = var3;
         }

         if (var2 == null) {
            return null;
         } else {
            var0 = var2;
            if (var6 != var2.length) {
               var0 = Arrays.copyOf(var2, var6);
            }

            return var0;
         }
      } else {
         return Arrays.copyOf(var0, var0.length);
      }
   }

   public static <T> ArrayList<T> toArrayList(T[] var0) {
      int var1 = var0.length;
      ArrayList var2 = new ArrayList(var1);

      for(int var3 = 0; var3 < var1; ++var3) {
         var2.add(var0[var3]);
      }

      return var2;
   }

   public static int[] toPrimitiveArray(Collection<Integer> var0) {
      int var1 = 0;
      if (var0 != null && var0.size() != 0) {
         int[] var2 = new int[var0.size()];

         for(Iterator var3 = var0.iterator(); var3.hasNext(); ++var1) {
            var2[var1] = (Integer)var3.next();
         }

         return var2;
      } else {
         return new int[0];
      }
   }

   public static Integer[] toWrapperArray(int[] var0) {
      if (var0 == null) {
         return null;
      } else {
         int var1 = var0.length;
         Integer[] var2 = new Integer[var1];

         for(int var3 = 0; var3 < var1; ++var3) {
            var2[var3] = var0[var3];
         }

         return var2;
      }
   }

   public static void writeArray(StringBuilder var0, double[] var1) {
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         if (var3 != 0) {
            var0.append(",");
         }

         var0.append(Double.toString(var1[var3]));
      }

   }

   public static void writeArray(StringBuilder var0, float[] var1) {
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         if (var3 != 0) {
            var0.append(",");
         }

         var0.append(Float.toString(var1[var3]));
      }

   }

   public static void writeArray(StringBuilder var0, int[] var1) {
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         if (var3 != 0) {
            var0.append(",");
         }

         var0.append(Integer.toString(var1[var3]));
      }

   }

   public static void writeArray(StringBuilder var0, long[] var1) {
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         if (var3 != 0) {
            var0.append(",");
         }

         var0.append(Long.toString(var1[var3]));
      }

   }

   public static <T> void writeArray(StringBuilder var0, T[] var1) {
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         if (var3 != 0) {
            var0.append(",");
         }

         var0.append(var1[var3]);
      }

   }

   public static void writeArray(StringBuilder var0, boolean[] var1) {
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         if (var3 != 0) {
            var0.append(",");
         }

         var0.append(Boolean.toString(var1[var3]));
      }

   }

   public static void writeStringArray(StringBuilder var0, String[] var1) {
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         if (var3 != 0) {
            var0.append(",");
         }

         var0.append("\"");
         var0.append(var1[var3]);
         var0.append("\"");
      }

   }
}
