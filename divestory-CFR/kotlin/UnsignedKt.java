/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin;

import kotlin.Metadata;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(bv={1, 0, 3}, d1={"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0001\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0004\u001a\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u0003H\u0001\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0007\u001a\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\tH\u0001\u001a\"\u0010\f\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u0001H\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\r\u0010\u000e\u001a\"\u0010\u000f\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u0001H\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0010\u0010\u000e\u001a\u0010\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\tH\u0001\u001a\u0018\u0010\u0012\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00132\u0006\u0010\u000b\u001a\u00020\u0013H\u0001\u001a\"\u0010\u0014\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006H\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0015\u0010\u0016\u001a\"\u0010\u0017\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006H\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0018\u0010\u0016\u001a\u0010\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0013H\u0001\u001a\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0002\u001a\u00020\u0013H\u0000\u001a\u0018\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0002\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\tH\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001d"}, d2={"doubleToUInt", "Lkotlin/UInt;", "v", "", "(D)I", "doubleToULong", "Lkotlin/ULong;", "(D)J", "uintCompare", "", "v1", "v2", "uintDivide", "uintDivide-J1ME1BU", "(II)I", "uintRemainder", "uintRemainder-J1ME1BU", "uintToDouble", "ulongCompare", "", "ulongDivide", "ulongDivide-eb3DHEI", "(JJ)J", "ulongRemainder", "ulongRemainder-eb3DHEI", "ulongToDouble", "ulongToString", "", "base", "kotlin-stdlib"}, k=2, mv={1, 1, 16})
public final class UnsignedKt {
    public static final int doubleToUInt(double d) {
        boolean bl = Double.isNaN(d);
        int n = -1;
        if (bl) return 0;
        if (d <= UnsignedKt.uintToDouble(0)) {
            return 0;
        }
        if (d >= UnsignedKt.uintToDouble(-1)) {
            return n;
        }
        double d2 = Integer.MAX_VALUE;
        if (!(d <= d2)) return UInt.constructor-impl(UInt.constructor-impl((int)(d - d2)) + UInt.constructor-impl(Integer.MAX_VALUE));
        return UInt.constructor-impl((int)d);
    }

    public static final long doubleToULong(double d) {
        boolean bl = Double.isNaN(d);
        long l = -1L;
        if (bl) return 0L;
        if (d <= UnsignedKt.ulongToDouble(0L)) {
            return 0L;
        }
        if (d >= UnsignedKt.ulongToDouble(-1L)) {
            return l;
        }
        if (!(d < (double)Long.MAX_VALUE)) return ULong.constructor-impl(ULong.constructor-impl((long)(d - 9.223372036854776E18)) - Long.MIN_VALUE);
        return ULong.constructor-impl((long)d);
    }

    public static final int uintCompare(int n, int n2) {
        return Intrinsics.compare(n ^ Integer.MIN_VALUE, n2 ^ Integer.MIN_VALUE);
    }

    public static final int uintDivide-J1ME1BU(int n, int n2) {
        return UInt.constructor-impl((int)(((long)n & 0xFFFFFFFFL) / ((long)n2 & 0xFFFFFFFFL)));
    }

    public static final int uintRemainder-J1ME1BU(int n, int n2) {
        return UInt.constructor-impl((int)(((long)n & 0xFFFFFFFFL) % ((long)n2 & 0xFFFFFFFFL)));
    }

    public static final double uintToDouble(int n) {
        return (double)(Integer.MAX_VALUE & n) + (double)(n >>> 31 << 30) * (double)2;
    }

    public static final int ulongCompare(long l, long l2) {
        return (int)(l ^ Long.MIN_VALUE LCMP l2 ^ Long.MIN_VALUE);
    }

    public static final long ulongDivide-eb3DHEI(long l, long l2) {
        if (l2 < 0L) {
            if (UnsignedKt.ulongCompare(l, l2) >= 0) return ULong.constructor-impl(1L);
            return ULong.constructor-impl(0L);
        }
        if (l >= 0L) {
            return ULong.constructor-impl(l / l2);
        }
        int n = 1;
        long l3 = (l >>> 1) / l2 << 1;
        if (UnsignedKt.ulongCompare(ULong.constructor-impl(l - l3 * l2), ULong.constructor-impl(l2)) >= 0) {
            return ULong.constructor-impl(l3 + (long)n);
        }
        n = 0;
        return ULong.constructor-impl(l3 + (long)n);
    }

    public static final long ulongRemainder-eb3DHEI(long l, long l2) {
        if (l2 < 0L) {
            if (UnsignedKt.ulongCompare(l, l2) >= 0) return ULong.constructor-impl(l - l2);
            return l;
        }
        if (l >= 0L) {
            return ULong.constructor-impl(l % l2);
        }
        if (UnsignedKt.ulongCompare(ULong.constructor-impl(l -= ((l >>> 1) / l2 << 1) * l2), ULong.constructor-impl(l2)) >= 0) {
            return ULong.constructor-impl(l - l2);
        }
        l2 = 0L;
        return ULong.constructor-impl(l - l2);
    }

    public static final double ulongToDouble(long l) {
        return (double)(l >>> 11) * (double)2048 + (double)(l & 2047L);
    }

    public static final String ulongToString(long l) {
        return UnsignedKt.ulongToString(l, 10);
    }

    public static final String ulongToString(long l, int n) {
        if (l >= 0L) {
            String string2 = Long.toString(l, CharsKt.checkRadix(n));
            Intrinsics.checkExpressionValueIsNotNull(string2, "java.lang.Long.toString(this, checkRadix(radix))");
            return string2;
        }
        long l2 = n;
        long l3 = (l >>> 1) / l2 << 1;
        long l4 = l - l3 * l2;
        long l5 = l3;
        l = l4;
        if (l4 >= l2) {
            l = l4 - l2;
            l5 = l3 + 1L;
        }
        StringBuilder stringBuilder = new StringBuilder();
        String string3 = Long.toString(l5, CharsKt.checkRadix(n));
        Intrinsics.checkExpressionValueIsNotNull(string3, "java.lang.Long.toString(this, checkRadix(radix))");
        stringBuilder.append(string3);
        string3 = Long.toString(l, CharsKt.checkRadix(n));
        Intrinsics.checkExpressionValueIsNotNull(string3, "java.lang.Long.toString(this, checkRadix(radix))");
        stringBuilder.append(string3);
        return stringBuilder.toString();
    }
}

