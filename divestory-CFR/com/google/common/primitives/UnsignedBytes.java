/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.primitives;

import com.google.common.base.Preconditions;
import com.google.common.primitives.UnsignedLongs;
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

    public static byte checkedCast(long l) {
        boolean bl = l >> 8 == 0L;
        Preconditions.checkArgument(bl, "out of range: %s", l);
        return (byte)l;
    }

    public static int compare(byte by, byte by2) {
        return UnsignedBytes.toInt(by) - UnsignedBytes.toInt(by2);
    }

    private static byte flip(byte by) {
        return (byte)(by ^ 128);
    }

    public static String join(String string2, byte ... arrby) {
        Preconditions.checkNotNull(string2);
        if (arrby.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(arrby.length * (string2.length() + 3));
        stringBuilder.append(UnsignedBytes.toInt(arrby[0]));
        int n = 1;
        while (n < arrby.length) {
            stringBuilder.append(string2);
            stringBuilder.append(UnsignedBytes.toString(arrby[n]));
            ++n;
        }
        return stringBuilder.toString();
    }

    public static Comparator<byte[]> lexicographicalComparator() {
        return LexicographicalComparatorHolder.BEST_COMPARATOR;
    }

    static Comparator<byte[]> lexicographicalComparatorJavaImpl() {
        return LexicographicalComparatorHolder.PureJavaComparator.INSTANCE;
    }

    public static byte max(byte ... arrby) {
        int n = arrby.length;
        int n2 = 1;
        boolean bl = n > 0;
        Preconditions.checkArgument(bl);
        int n3 = UnsignedBytes.toInt(arrby[0]);
        while (n2 < arrby.length) {
            int n4 = UnsignedBytes.toInt(arrby[n2]);
            n = n3;
            if (n4 > n3) {
                n = n4;
            }
            ++n2;
            n3 = n;
        }
        return (byte)n3;
    }

    public static byte min(byte ... arrby) {
        int n = arrby.length;
        int n2 = 1;
        boolean bl = n > 0;
        Preconditions.checkArgument(bl);
        int n3 = UnsignedBytes.toInt(arrby[0]);
        while (n2 < arrby.length) {
            int n4 = UnsignedBytes.toInt(arrby[n2]);
            n = n3;
            if (n4 < n3) {
                n = n4;
            }
            ++n2;
            n3 = n;
        }
        return (byte)n3;
    }

    public static byte parseUnsignedByte(String string2) {
        return UnsignedBytes.parseUnsignedByte(string2, 10);
    }

    public static byte parseUnsignedByte(String charSequence, int n) {
        n = Integer.parseInt(Preconditions.checkNotNull(charSequence), n);
        if (n >> 8 == 0) {
            return (byte)n;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("out of range: ");
        ((StringBuilder)charSequence).append(n);
        throw new NumberFormatException(((StringBuilder)charSequence).toString());
    }

    public static byte saturatedCast(long l) {
        if (l > (long)UnsignedBytes.toInt((byte)-1)) {
            return -1;
        }
        if (l >= 0L) return (byte)l;
        return 0;
    }

    public static void sort(byte[] arrby) {
        Preconditions.checkNotNull(arrby);
        UnsignedBytes.sort(arrby, 0, arrby.length);
    }

    public static void sort(byte[] arrby, int n, int n2) {
        Preconditions.checkNotNull(arrby);
        Preconditions.checkPositionIndexes(n, n2, arrby.length);
        for (int i = n; i < n2; ++i) {
            arrby[i] = UnsignedBytes.flip(arrby[i]);
        }
        Arrays.sort(arrby, n, n2);
        while (n < n2) {
            arrby[n] = UnsignedBytes.flip(arrby[n]);
            ++n;
        }
    }

    public static void sortDescending(byte[] arrby) {
        Preconditions.checkNotNull(arrby);
        UnsignedBytes.sortDescending(arrby, 0, arrby.length);
    }

    public static void sortDescending(byte[] arrby, int n, int n2) {
        Preconditions.checkNotNull(arrby);
        Preconditions.checkPositionIndexes(n, n2, arrby.length);
        for (int i = n; i < n2; ++i) {
            arrby[i] = (byte)(arrby[i] ^ 127);
        }
        Arrays.sort(arrby, n, n2);
        while (n < n2) {
            arrby[n] = (byte)(arrby[n] ^ 127);
            ++n;
        }
    }

    public static int toInt(byte by) {
        return by & 255;
    }

    public static String toString(byte by) {
        return UnsignedBytes.toString(by, 10);
    }

    public static String toString(byte by, int n) {
        boolean bl = n >= 2 && n <= 36;
        Preconditions.checkArgument(bl, "radix (%s) must be between Character.MIN_RADIX and Character.MAX_RADIX", n);
        return Integer.toString(UnsignedBytes.toInt(by), n);
    }

    static class LexicographicalComparatorHolder {
        static final Comparator<byte[]> BEST_COMPARATOR;
        static final String UNSAFE_COMPARATOR_NAME;

        static {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(LexicographicalComparatorHolder.class.getName());
            stringBuilder.append("$UnsafeComparator");
            UNSAFE_COMPARATOR_NAME = stringBuilder.toString();
            BEST_COMPARATOR = LexicographicalComparatorHolder.getBestComparator();
        }

        LexicographicalComparatorHolder() {
        }

        static Comparator<byte[]> getBestComparator() {
            try {
                return (Comparator)Class.forName(UNSAFE_COMPARATOR_NAME).getEnumConstants()[0];
            }
            catch (Throwable throwable) {
                return UnsignedBytes.lexicographicalComparatorJavaImpl();
            }
        }

        static final class PureJavaComparator
        extends Enum<PureJavaComparator>
        implements Comparator<byte[]> {
            private static final /* synthetic */ PureJavaComparator[] $VALUES;
            public static final /* enum */ PureJavaComparator INSTANCE;

            static {
                PureJavaComparator pureJavaComparator;
                INSTANCE = pureJavaComparator = new PureJavaComparator();
                $VALUES = new PureJavaComparator[]{pureJavaComparator};
            }

            public static PureJavaComparator valueOf(String string2) {
                return Enum.valueOf(PureJavaComparator.class, string2);
            }

            public static PureJavaComparator[] values() {
                return (PureJavaComparator[])$VALUES.clone();
            }

            @Override
            public int compare(byte[] arrby, byte[] arrby2) {
                int n = Math.min(arrby.length, arrby2.length);
                int n2 = 0;
                while (n2 < n) {
                    int n3 = UnsignedBytes.compare(arrby[n2], arrby2[n2]);
                    if (n3 != 0) {
                        return n3;
                    }
                    ++n2;
                }
                return arrby.length - arrby2.length;
            }

            public String toString() {
                return "UnsignedBytes.lexicographicalComparator() (pure Java version)";
            }
        }

        static final class UnsafeComparator
        extends Enum<UnsafeComparator>
        implements Comparator<byte[]> {
            private static final /* synthetic */ UnsafeComparator[] $VALUES;
            static final boolean BIG_ENDIAN;
            static final int BYTE_ARRAY_BASE_OFFSET;
            public static final /* enum */ UnsafeComparator INSTANCE;
            static final Unsafe theUnsafe;

            static {
                Object object = new UnsafeComparator();
                INSTANCE = object;
                $VALUES = new UnsafeComparator[]{object};
                BIG_ENDIAN = ByteOrder.nativeOrder().equals(ByteOrder.BIG_ENDIAN);
                theUnsafe = object = UnsafeComparator.getUnsafe();
                BYTE_ARRAY_BASE_OFFSET = ((Unsafe)object).arrayBaseOffset(byte[].class);
                if (!"64".equals(System.getProperty("sun.arch.data.model"))) throw new Error();
                if (BYTE_ARRAY_BASE_OFFSET % 8 != 0) throw new Error();
                if (theUnsafe.arrayIndexScale(byte[].class) != 1) throw new Error();
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

            public static UnsafeComparator valueOf(String string2) {
                return Enum.valueOf(UnsafeComparator.class, string2);
            }

            public static UnsafeComparator[] values() {
                return (UnsafeComparator[])$VALUES.clone();
            }

            @Override
            public int compare(byte[] arrby, byte[] arrby2) {
                int n;
                int n2;
                int n3;
                block4 : {
                    long l;
                    long l2;
                    n3 = Math.min(arrby.length, arrby2.length);
                    n = 0;
                    do {
                        n2 = n;
                        if (n >= (n3 & -8)) break block4;
                        Unsafe unsafe = theUnsafe;
                        l = BYTE_ARRAY_BASE_OFFSET;
                        l2 = n;
                        l = unsafe.getLong(arrby, l + l2);
                        if (l != (l2 = theUnsafe.getLong(arrby2, (long)BYTE_ARRAY_BASE_OFFSET + l2))) {
                            if (!BIG_ENDIAN) break;
                            return UnsignedLongs.compare(l, l2);
                        }
                        n += 8;
                    } while (true);
                    n = Long.numberOfTrailingZeros(l ^ l2) & -8;
                    return (int)(l >>> n & 255L) - (int)(l2 >>> n & 255L);
                }
                while (n2 < n3) {
                    n = UnsignedBytes.compare(arrby[n2], arrby2[n2]);
                    if (n != 0) {
                        return n;
                    }
                    ++n2;
                }
                return arrby.length - arrby2.length;
            }

            public String toString() {
                return "UnsignedBytes.lexicographicalComparator() (sun.misc.Unsafe version)";
            }

        }

    }

}

