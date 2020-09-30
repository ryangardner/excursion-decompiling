package com.google.common.collect;

import com.google.common.base.Objects;
import java.util.Arrays;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class CompactHashing {
   private static final int BYTE_MASK = 255;
   private static final int BYTE_MAX_SIZE = 256;
   static final int DEFAULT_SIZE = 3;
   static final int HASH_TABLE_BITS_MASK = 31;
   private static final int HASH_TABLE_BITS_MAX_BITS = 5;
   static final int MAX_SIZE = 1073741823;
   private static final int MIN_HASH_TABLE_SIZE = 4;
   static final int MODIFICATION_COUNT_INCREMENT = 32;
   private static final int SHORT_MASK = 65535;
   private static final int SHORT_MAX_SIZE = 65536;
   static final byte UNSET = 0;

   private CompactHashing() {
   }

   static Object createTable(int var0) {
      if (var0 >= 2 && var0 <= 1073741824 && Integer.highestOneBit(var0) == var0) {
         if (var0 <= 256) {
            return new byte[var0];
         } else {
            return var0 <= 65536 ? new short[var0] : new int[var0];
         }
      } else {
         StringBuilder var1 = new StringBuilder();
         var1.append("must be power of 2 between 2^1 and 2^30: ");
         var1.append(var0);
         throw new IllegalArgumentException(var1.toString());
      }
   }

   static int getHashPrefix(int var0, int var1) {
      return var0 & var1;
   }

   static int getNext(int var0, int var1) {
      return var0 & var1;
   }

   static int maskCombine(int var0, int var1, int var2) {
      return var0 & var2 | var1 & var2;
   }

   static int newCapacity(int var0) {
      byte var1;
      if (var0 < 32) {
         var1 = 4;
      } else {
         var1 = 2;
      }

      return var1 * (var0 + 1);
   }

   static int remove(@NullableDecl Object var0, @NullableDecl Object var1, int var2, Object var3, int[] var4, Object[] var5, @NullableDecl Object[] var6) {
      int var7 = Hashing.smearedHash(var0);
      int var8 = var7 & var2;
      int var9 = tableGet(var3, var8);
      if (var9 == 0) {
         return -1;
      } else {
         int var10 = getHashPrefix(var7, var2);
         var7 = -1;

         while(true) {
            --var9;
            int var11 = var4[var9];
            if (getHashPrefix(var11, var2) == var10 && Objects.equal(var0, var5[var9]) && (var6 == null || Objects.equal(var1, var6[var9]))) {
               var11 = getNext(var11, var2);
               if (var7 == -1) {
                  tableSet(var3, var8, var11);
               } else {
                  var4[var7] = maskCombine(var4[var7], var11, var2);
               }

               return var9;
            }

            var11 = getNext(var11, var2);
            if (var11 == 0) {
               return -1;
            }

            var7 = var9;
            var9 = var11;
         }
      }
   }

   static void tableClear(Object var0) {
      if (var0 instanceof byte[]) {
         Arrays.fill((byte[])var0, (byte)0);
      } else if (var0 instanceof short[]) {
         Arrays.fill((short[])var0, (short)0);
      } else {
         Arrays.fill((int[])var0, 0);
      }

   }

   static int tableGet(Object var0, int var1) {
      if (var0 instanceof byte[]) {
         return ((byte[])var0)[var1] & 255;
      } else {
         return var0 instanceof short[] ? ((short[])var0)[var1] & '\uffff' : ((int[])var0)[var1];
      }
   }

   static void tableSet(Object var0, int var1, int var2) {
      if (var0 instanceof byte[]) {
         ((byte[])var0)[var1] = (byte)((byte)var2);
      } else if (var0 instanceof short[]) {
         ((short[])var0)[var1] = (short)((short)var2);
      } else {
         ((int[])var0)[var1] = var2;
      }

   }

   static int tableSize(int var0) {
      return Math.max(4, Hashing.closedTableSize(var0 + 1, 1.0D));
   }
}
