package com.google.common.hash;

import com.google.common.primitives.Longs;
import java.lang.reflect.Field;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import sun.misc.Unsafe;

final class LittleEndianByteArray {
   // $FF: synthetic field
   static final boolean $assertionsDisabled = false;
   private static final LittleEndianByteArray.LittleEndianBytes byteArray;

   static {
      LittleEndianByteArray.JavaLittleEndianBytes var0 = LittleEndianByteArray.JavaLittleEndianBytes.INSTANCE;
      Object var1 = var0;

      try {
         if ("amd64".equals(System.getProperty("os.arch"))) {
            if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
               var1 = LittleEndianByteArray.UnsafeByteArray.UNSAFE_LITTLE_ENDIAN;
            } else {
               var1 = LittleEndianByteArray.UnsafeByteArray.UNSAFE_BIG_ENDIAN;
            }
         }
      } finally {
         ;
      }

      byteArray = (LittleEndianByteArray.LittleEndianBytes)var1;
   }

   private LittleEndianByteArray() {
   }

   static int load32(byte[] var0, int var1) {
      byte var2 = var0[var1];
      byte var3 = var0[var1 + 1];
      byte var4 = var0[var1 + 2];
      return (var0[var1 + 3] & 255) << 24 | var2 & 255 | (var3 & 255) << 8 | (var4 & 255) << 16;
   }

   static long load64(byte[] var0, int var1) {
      return byteArray.getLongLittleEndian(var0, var1);
   }

   static long load64Safely(byte[] var0, int var1, int var2) {
      int var3 = Math.min(var2, 8);
      long var4 = 0L;

      for(var2 = 0; var2 < var3; ++var2) {
         var4 |= ((long)var0[var1 + var2] & 255L) << var2 * 8;
      }

      return var4;
   }

   static void store64(byte[] var0, int var1, long var2) {
      byteArray.putLongLittleEndian(var0, var1, var2);
   }

   static boolean usingUnsafe() {
      return byteArray instanceof LittleEndianByteArray.UnsafeByteArray;
   }

   private static enum JavaLittleEndianBytes implements LittleEndianByteArray.LittleEndianBytes {
      INSTANCE;

      static {
         LittleEndianByteArray.JavaLittleEndianBytes var0 = new LittleEndianByteArray.JavaLittleEndianBytes("INSTANCE", 0) {
            public long getLongLittleEndian(byte[] var1, int var2) {
               return Longs.fromBytes(var1[var2 + 7], var1[var2 + 6], var1[var2 + 5], var1[var2 + 4], var1[var2 + 3], var1[var2 + 2], var1[var2 + 1], var1[var2]);
            }

            public void putLongLittleEndian(byte[] var1, int var2, long var3) {
               long var5 = 255L;

               for(int var7 = 0; var7 < 8; ++var7) {
                  var1[var2 + var7] = (byte)((byte)((int)((var3 & var5) >> var7 * 8)));
                  var5 <<= 8;
               }

            }
         };
         INSTANCE = var0;
      }

      private JavaLittleEndianBytes() {
      }

      // $FF: synthetic method
      JavaLittleEndianBytes(Object var3) {
         this();
      }
   }

   private interface LittleEndianBytes {
      long getLongLittleEndian(byte[] var1, int var2);

      void putLongLittleEndian(byte[] var1, int var2, long var3);
   }

   private static enum UnsafeByteArray implements LittleEndianByteArray.LittleEndianBytes {
      private static final int BYTE_ARRAY_BASE_OFFSET;
      UNSAFE_BIG_ENDIAN,
      UNSAFE_LITTLE_ENDIAN {
         public long getLongLittleEndian(byte[] var1, int var2) {
            return LittleEndianByteArray.UnsafeByteArray.theUnsafe.getLong(var1, (long)var2 + (long)LittleEndianByteArray.UnsafeByteArray.BYTE_ARRAY_BASE_OFFSET);
         }

         public void putLongLittleEndian(byte[] var1, int var2, long var3) {
            LittleEndianByteArray.UnsafeByteArray.theUnsafe.putLong(var1, (long)var2 + (long)LittleEndianByteArray.UnsafeByteArray.BYTE_ARRAY_BASE_OFFSET, var3);
         }
      };

      private static final Unsafe theUnsafe;

      static {
         LittleEndianByteArray.UnsafeByteArray var0 = new LittleEndianByteArray.UnsafeByteArray("UNSAFE_BIG_ENDIAN", 1) {
            public long getLongLittleEndian(byte[] var1, int var2) {
               return Long.reverseBytes(LittleEndianByteArray.UnsafeByteArray.theUnsafe.getLong(var1, (long)var2 + (long)LittleEndianByteArray.UnsafeByteArray.BYTE_ARRAY_BASE_OFFSET));
            }

            public void putLongLittleEndian(byte[] var1, int var2, long var3) {
               var3 = Long.reverseBytes(var3);
               LittleEndianByteArray.UnsafeByteArray.theUnsafe.putLong(var1, (long)var2 + (long)LittleEndianByteArray.UnsafeByteArray.BYTE_ARRAY_BASE_OFFSET, var3);
            }
         };
         UNSAFE_BIG_ENDIAN = var0;
         Unsafe var1 = getUnsafe();
         theUnsafe = var1;
         BYTE_ARRAY_BASE_OFFSET = var1.arrayBaseOffset(byte[].class);
         if (theUnsafe.arrayIndexScale(byte[].class) != 1) {
            throw new AssertionError();
         }
      }

      private UnsafeByteArray() {
      }

      // $FF: synthetic method
      UnsafeByteArray(Object var3) {
         this();
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
   }
}
