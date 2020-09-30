/*
 * Decompiled with CFR <Could not determine version>.
 */
package okio;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okio.internal.ByteStringKt;

@Metadata(bv={1, 0, 3}, d1={"\u0000:\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0005\n\u0002\b\u0002\n\u0002\u0010\n\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\u001a0\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H\u0000\u001a \u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\b\u001a\u00020\fH\u0000\u001a\u0019\u0010\u000e\u001a\u00020\f2\u0006\u0010\u0002\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\fH\b\u001a\u0019\u0010\u000e\u001a\u00020\f2\u0006\u0010\u0002\u001a\u00020\f2\u0006\u0010\u0006\u001a\u00020\u0005H\b\u001a\u0015\u0010\u000f\u001a\u00020\u0005*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0005H\f\u001a\u0015\u0010\u000f\u001a\u00020\f*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\fH\f\u001a\u0015\u0010\u000f\u001a\u00020\f*\u00020\u00052\u0006\u0010\u0011\u001a\u00020\fH\f\u001a\f\u0010\u0012\u001a\u00020\u0005*\u00020\u0005H\u0000\u001a\f\u0010\u0012\u001a\u00020\f*\u00020\fH\u0000\u001a\f\u0010\u0012\u001a\u00020\u0013*\u00020\u0013H\u0000\u001a\u0015\u0010\u0014\u001a\u00020\u0005*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0005H\f\u001a\u0015\u0010\u0015\u001a\u00020\u0005*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0005H\f\u001a\f\u0010\u0016\u001a\u00020\u0017*\u00020\u0010H\u0000\u001a\f\u0010\u0016\u001a\u00020\u0017*\u00020\u0005H\u0000\u001a\f\u0010\u0016\u001a\u00020\u0017*\u00020\fH\u0000\u00a8\u0006\u0018"}, d2={"arrayRangeEquals", "", "a", "", "aOffset", "", "b", "bOffset", "byteCount", "checkOffsetAndCount", "", "size", "", "offset", "minOf", "and", "", "other", "reverseBytes", "", "shl", "shr", "toHexString", "", "okio"}, k=2, mv={1, 1, 16})
public final class _Util {
    public static final int and(byte by, int n) {
        return by & n;
    }

    public static final long and(byte by, long l) {
        return (long)by & l;
    }

    public static final long and(int n, long l) {
        return (long)n & l;
    }

    public static final boolean arrayRangeEquals(byte[] arrby, int n, byte[] arrby2, int n2, int n3) {
        Intrinsics.checkParameterIsNotNull(arrby, "a");
        Intrinsics.checkParameterIsNotNull(arrby2, "b");
        int n4 = 0;
        while (n4 < n3) {
            if (arrby[n4 + n] != arrby2[n4 + n2]) {
                return false;
            }
            ++n4;
        }
        return true;
    }

    public static final void checkOffsetAndCount(long l, long l2, long l3) {
        if ((l2 | l3) >= 0L && l2 <= l && l - l2 >= l3) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("size=");
        stringBuilder.append(l);
        stringBuilder.append(" offset=");
        stringBuilder.append(l2);
        stringBuilder.append(" byteCount=");
        stringBuilder.append(l3);
        throw (Throwable)new ArrayIndexOutOfBoundsException(stringBuilder.toString());
    }

    public static final long minOf(int n, long l) {
        return Math.min((long)n, l);
    }

    public static final long minOf(long l, int n) {
        return Math.min(l, (long)n);
    }

    public static final int reverseBytes(int n) {
        return (n & 255) << 24 | ((-16777216 & n) >>> 24 | (16711680 & n) >>> 8 | (65280 & n) << 8);
    }

    public static final long reverseBytes(long l) {
        return (l & 255L) << 56 | ((-72057594037927936L & l) >>> 56 | (0xFF000000000000L & l) >>> 40 | (0xFF0000000000L & l) >>> 24 | (0xFF00000000L & l) >>> 8 | (0xFF000000L & l) << 8 | (0xFF0000L & l) << 24 | (65280L & l) << 40);
    }

    public static final short reverseBytes(short s) {
        s = (short)(s & 65535);
        return (short)((s & 255) << 8 | (65280 & s) >>> 8);
    }

    public static final int shl(byte by, int n) {
        return by << n;
    }

    public static final int shr(byte by, int n) {
        return by >> n;
    }

    public static final String toHexString(byte by) {
        return new String(new char[]{ByteStringKt.getHEX_DIGIT_CHARS()[by >> 4 & 15], ByteStringKt.getHEX_DIGIT_CHARS()[by & 15]});
    }

    public static final String toHexString(int n) {
        if (n == 0) {
            return "0";
        }
        char[] arrc = new char[8];
        char c = ByteStringKt.getHEX_DIGIT_CHARS()[n >> 28 & 15];
        int n2 = 0;
        arrc[0] = c;
        arrc[1] = ByteStringKt.getHEX_DIGIT_CHARS()[n >> 24 & 15];
        arrc[2] = ByteStringKt.getHEX_DIGIT_CHARS()[n >> 20 & 15];
        arrc[3] = ByteStringKt.getHEX_DIGIT_CHARS()[n >> 16 & 15];
        arrc[4] = ByteStringKt.getHEX_DIGIT_CHARS()[n >> 12 & 15];
        arrc[5] = ByteStringKt.getHEX_DIGIT_CHARS()[n >> 8 & 15];
        arrc[6] = ByteStringKt.getHEX_DIGIT_CHARS()[n >> 4 & 15];
        arrc[7] = ByteStringKt.getHEX_DIGIT_CHARS()[n & 15];
        n = n2;
        while (n < 8) {
            if (arrc[n] != '0') {
                return new String(arrc, n, 8 - n);
            }
            ++n;
        }
        return new String(arrc, n, 8 - n);
    }

    public static final String toHexString(long l) {
        if (l == 0L) {
            return "0";
        }
        char[] arrc = new char[16];
        char c = ByteStringKt.getHEX_DIGIT_CHARS()[(int)(l >> 60 & 15L)];
        int n = 0;
        arrc[0] = c;
        arrc[1] = ByteStringKt.getHEX_DIGIT_CHARS()[(int)(l >> 56 & 15L)];
        arrc[2] = ByteStringKt.getHEX_DIGIT_CHARS()[(int)(l >> 52 & 15L)];
        arrc[3] = ByteStringKt.getHEX_DIGIT_CHARS()[(int)(l >> 48 & 15L)];
        arrc[4] = ByteStringKt.getHEX_DIGIT_CHARS()[(int)(l >> 44 & 15L)];
        arrc[5] = ByteStringKt.getHEX_DIGIT_CHARS()[(int)(l >> 40 & 15L)];
        arrc[6] = ByteStringKt.getHEX_DIGIT_CHARS()[(int)(l >> 36 & 15L)];
        arrc[7] = ByteStringKt.getHEX_DIGIT_CHARS()[(int)(l >> 32 & 15L)];
        arrc[8] = ByteStringKt.getHEX_DIGIT_CHARS()[(int)(l >> 28 & 15L)];
        arrc[9] = ByteStringKt.getHEX_DIGIT_CHARS()[(int)(l >> 24 & 15L)];
        arrc[10] = ByteStringKt.getHEX_DIGIT_CHARS()[(int)(l >> 20 & 15L)];
        arrc[11] = ByteStringKt.getHEX_DIGIT_CHARS()[(int)(l >> 16 & 15L)];
        arrc[12] = ByteStringKt.getHEX_DIGIT_CHARS()[(int)(l >> 12 & 15L)];
        arrc[13] = ByteStringKt.getHEX_DIGIT_CHARS()[(int)(l >> 8 & 15L)];
        arrc[14] = ByteStringKt.getHEX_DIGIT_CHARS()[(int)(l >> 4 & 15L)];
        arrc[15] = ByteStringKt.getHEX_DIGIT_CHARS()[(int)(l & 15L)];
        while (n < 16) {
            if (arrc[n] != '0') {
                return new String(arrc, n, 16 - n);
            }
            ++n;
        }
        return new String(arrc, n, 16 - n);
    }
}

