/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.internal;

import kotlin.Metadata;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UnsignedKt;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(bv={1, 0, 3}, d1={"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0001H\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0005\u0010\u0006\u001a*\u0010\u0000\u001a\u00020\u00072\u0006\u0010\u0002\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0007H\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b\b\u0010\t\u001a*\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000eH\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u000f\u0010\u0006\u001a*\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u0010H\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0011\u0010\t\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0012"}, d2={"differenceModulo", "Lkotlin/UInt;", "a", "b", "c", "differenceModulo-WZ9TVnA", "(III)I", "Lkotlin/ULong;", "differenceModulo-sambcqE", "(JJJ)J", "getProgressionLastElement", "start", "end", "step", "", "getProgressionLastElement-Nkh28Cs", "", "getProgressionLastElement-7ftBX0g", "kotlin-stdlib"}, k=2, mv={1, 1, 16})
public final class UProgressionUtilKt {
    private static final int differenceModulo-WZ9TVnA(int n, int n2, int n3) {
        n = UnsignedKt.uintRemainder-J1ME1BU(n, n3);
        int n4 = UnsignedKt.uintRemainder-J1ME1BU(n2, n3);
        n2 = UnsignedKt.uintCompare(n, n4);
        n = UInt.constructor-impl(n - n4);
        if (n2 < 0) return UInt.constructor-impl(n + n3);
        return n;
    }

    private static final long differenceModulo-sambcqE(long l, long l2, long l3) {
        l = UnsignedKt.ulongRemainder-eb3DHEI(l, l3);
        l2 = UnsignedKt.ulongRemainder-eb3DHEI(l2, l3);
        int n = UnsignedKt.ulongCompare(l, l2);
        l = ULong.constructor-impl(l - l2);
        if (n < 0) return ULong.constructor-impl(l + l3);
        return l;
    }

    public static final long getProgressionLastElement-7ftBX0g(long l, long l2, long l3) {
        long l4 = l3 LCMP 0L;
        if (l4 > 0) {
            if (UnsignedKt.ulongCompare(l, l2) < 0) return ULong.constructor-impl(l2 - UProgressionUtilKt.differenceModulo-sambcqE(l2, l, ULong.constructor-impl(l3)));
            return l2;
        }
        if (l4 >= 0) throw (Throwable)new IllegalArgumentException("Step is zero.");
        if (UnsignedKt.ulongCompare(l, l2) > 0) return ULong.constructor-impl(l2 + UProgressionUtilKt.differenceModulo-sambcqE(l, l2, ULong.constructor-impl(-l3)));
        return l2;
    }

    public static final int getProgressionLastElement-Nkh28Cs(int n, int n2, int n3) {
        if (n3 > 0) {
            if (UnsignedKt.uintCompare(n, n2) < 0) return UInt.constructor-impl(n2 - UProgressionUtilKt.differenceModulo-WZ9TVnA(n2, n, UInt.constructor-impl(n3)));
            return n2;
        }
        if (n3 >= 0) throw (Throwable)new IllegalArgumentException("Step is zero.");
        if (UnsignedKt.uintCompare(n, n2) > 0) return UInt.constructor-impl(n2 + UProgressionUtilKt.differenceModulo-WZ9TVnA(n, n2, UInt.constructor-impl(-n3)));
        return n2;
    }
}

