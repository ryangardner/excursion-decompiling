/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.hash;

import com.google.common.primitives.Longs;
import java.lang.reflect.Field;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import sun.misc.Unsafe;

final class LittleEndianByteArray {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final LittleEndianBytes byteArray;

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    static {
        var1_1 = var0 = JavaLittleEndianBytes.INSTANCE;
        try {
            if ("amd64".equals(System.getProperty("os.arch"))) {
                if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
                    var1_2 = UnsafeByteArray.UNSAFE_LITTLE_ENDIAN;
                } else {
                    var1_3 = UnsafeByteArray.UNSAFE_BIG_ENDIAN;
                }
            }
lbl8: // 5 sources:
            do {
                LittleEndianByteArray.byteArray = var1_4;
                return;
                break;
            } while (true);
        }
        catch (Throwable var1_5) {
            var1_6 = var0;
            ** continue;
        }
    }

    private LittleEndianByteArray() {
    }

    static int load32(byte[] arrby, int n) {
        byte by = arrby[n];
        byte by2 = arrby[n + 1];
        byte by3 = arrby[n + 2];
        return (arrby[n + 3] & 255) << 24 | (by & 255 | (by2 & 255) << 8 | (by3 & 255) << 16);
    }

    static long load64(byte[] arrby, int n) {
        return byteArray.getLongLittleEndian(arrby, n);
    }

    static long load64Safely(byte[] arrby, int n, int n2) {
        int n3 = Math.min(n2, 8);
        long l = 0L;
        n2 = 0;
        while (n2 < n3) {
            l |= ((long)arrby[n + n2] & 255L) << n2 * 8;
            ++n2;
        }
        return l;
    }

    static void store64(byte[] arrby, int n, long l) {
        byteArray.putLongLittleEndian(arrby, n, l);
    }

    static boolean usingUnsafe() {
        return byteArray instanceof UnsafeByteArray;
    }

    private static abstract class JavaLittleEndianBytes
    extends Enum<JavaLittleEndianBytes>
    implements LittleEndianBytes {
        private static final /* synthetic */ JavaLittleEndianBytes[] $VALUES;
        public static final /* enum */ JavaLittleEndianBytes INSTANCE;

        static {
            JavaLittleEndianBytes javaLittleEndianBytes;
            INSTANCE = javaLittleEndianBytes = new JavaLittleEndianBytes(){

                @Override
                public long getLongLittleEndian(byte[] arrby, int n) {
                    return Longs.fromBytes(arrby[n + 7], arrby[n + 6], arrby[n + 5], arrby[n + 4], arrby[n + 3], arrby[n + 2], arrby[n + 1], arrby[n]);
                }

                @Override
                public void putLongLittleEndian(byte[] arrby, int n, long l) {
                    long l2 = 255L;
                    int n2 = 0;
                    while (n2 < 8) {
                        arrby[n + n2] = (byte)((l & l2) >> n2 * 8);
                        l2 <<= 8;
                        ++n2;
                    }
                }
            };
            $VALUES = new JavaLittleEndianBytes[]{javaLittleEndianBytes};
        }

        public static JavaLittleEndianBytes valueOf(String string2) {
            return Enum.valueOf(JavaLittleEndianBytes.class, string2);
        }

        public static JavaLittleEndianBytes[] values() {
            return (JavaLittleEndianBytes[])$VALUES.clone();
        }

    }

    private static interface LittleEndianBytes {
        public long getLongLittleEndian(byte[] var1, int var2);

        public void putLongLittleEndian(byte[] var1, int var2, long var3);
    }

    private static abstract class UnsafeByteArray
    extends Enum<UnsafeByteArray>
    implements LittleEndianBytes {
        private static final /* synthetic */ UnsafeByteArray[] $VALUES;
        private static final int BYTE_ARRAY_BASE_OFFSET;
        public static final /* enum */ UnsafeByteArray UNSAFE_BIG_ENDIAN;
        public static final /* enum */ UnsafeByteArray UNSAFE_LITTLE_ENDIAN;
        private static final Unsafe theUnsafe;

        static {
            UNSAFE_LITTLE_ENDIAN = new UnsafeByteArray(){

                @Override
                public long getLongLittleEndian(byte[] arrby, int n) {
                    return theUnsafe.getLong(arrby, (long)n + (long)BYTE_ARRAY_BASE_OFFSET);
                }

                @Override
                public void putLongLittleEndian(byte[] arrby, int n, long l) {
                    theUnsafe.putLong(arrby, (long)n + (long)BYTE_ARRAY_BASE_OFFSET, l);
                }
            };
            Object object = new UnsafeByteArray(){

                @Override
                public long getLongLittleEndian(byte[] arrby, int n) {
                    return Long.reverseBytes(theUnsafe.getLong(arrby, (long)n + (long)BYTE_ARRAY_BASE_OFFSET));
                }

                @Override
                public void putLongLittleEndian(byte[] arrby, int n, long l) {
                    l = Long.reverseBytes(l);
                    theUnsafe.putLong(arrby, (long)n + (long)BYTE_ARRAY_BASE_OFFSET, l);
                }
            };
            UNSAFE_BIG_ENDIAN = object;
            $VALUES = new UnsafeByteArray[]{UNSAFE_LITTLE_ENDIAN, object};
            theUnsafe = object = UnsafeByteArray.getUnsafe();
            BYTE_ARRAY_BASE_OFFSET = ((Unsafe)object).arrayBaseOffset(byte[].class);
            if (theUnsafe.arrayIndexScale(byte[].class) != 1) throw new AssertionError();
        }

        private static Unsafe getUnsafe() {
            try {
                return Unsafe.getUnsafe();
            }
            catch (SecurityException securityException) {
                try {
                    PrivilegedExceptionAction<Unsafe> privilegedExceptionAction = new PrivilegedExceptionAction<Unsafe>(){

                        @Override
                        public Unsafe run() throws Exception {
                            Field[] arrfield = Unsafe.class.getDeclaredFields();
                            int n = arrfield.length;
                            int n2 = 0;
                            while (n2 < n) {
                                Object object = arrfield[n2];
                                ((Field)object).setAccessible(true);
                                object = ((Field)object).get(null);
                                if (Unsafe.class.isInstance(object)) {
                                    return (Unsafe)Unsafe.class.cast(object);
                                }
                                ++n2;
                            }
                            throw new NoSuchFieldError("the Unsafe");
                        }
                    };
                    return AccessController.doPrivileged(privilegedExceptionAction);
                }
                catch (PrivilegedActionException privilegedActionException) {
                    throw new RuntimeException("Could not initialize intrinsics", privilegedActionException.getCause());
                }
            }
        }

        public static UnsafeByteArray valueOf(String string2) {
            return Enum.valueOf(UnsafeByteArray.class, string2);
        }

        public static UnsafeByteArray[] values() {
            return (UnsafeByteArray[])$VALUES.clone();
        }

    }

}

