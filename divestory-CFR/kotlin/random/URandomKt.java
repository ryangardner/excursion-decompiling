/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.random;

import kotlin.Metadata;
import kotlin.UByteArray;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UnsignedKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.random.RandomKt;
import kotlin.ranges.UIntRange;
import kotlin.ranges.ULongRange;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(bv={1, 0, 3}, d1={"\u0000:\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\"\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0005\u0010\u0006\u001a\"\u0010\u0007\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\bH\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\t\u0010\n\u001a\u001c\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010\u001a\u001e\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u0011\u001a\u00020\fH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0012\u0010\u0013\u001a2\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u0011\u001a\u00020\f2\b\b\u0002\u0010\u0014\u001a\u00020\u000f2\b\b\u0002\u0010\u0015\u001a\u00020\u000fH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0016\u0010\u0017\u001a\u0014\u0010\u0018\u001a\u00020\u0003*\u00020\rH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0019\u001a\u001e\u0010\u0018\u001a\u00020\u0003*\u00020\r2\u0006\u0010\u0004\u001a\u00020\u0003H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001a\u0010\u001b\u001a&\u0010\u0018\u001a\u00020\u0003*\u00020\r2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001c\u0010\u001d\u001a\u001c\u0010\u0018\u001a\u00020\u0003*\u00020\r2\u0006\u0010\u001e\u001a\u00020\u001fH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010 \u001a\u0014\u0010!\u001a\u00020\b*\u00020\rH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\"\u001a\u001e\u0010!\u001a\u00020\b*\u00020\r2\u0006\u0010\u0004\u001a\u00020\bH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b#\u0010$\u001a&\u0010!\u001a\u00020\b*\u00020\r2\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\bH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b%\u0010&\u001a\u001c\u0010!\u001a\u00020\b*\u00020\r2\u0006\u0010\u001e\u001a\u00020'H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010(\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006)"}, d2={"checkUIntRangeBounds", "", "from", "Lkotlin/UInt;", "until", "checkUIntRangeBounds-J1ME1BU", "(II)V", "checkULongRangeBounds", "Lkotlin/ULong;", "checkULongRangeBounds-eb3DHEI", "(JJ)V", "nextUBytes", "Lkotlin/UByteArray;", "Lkotlin/random/Random;", "size", "", "(Lkotlin/random/Random;I)[B", "array", "nextUBytes-EVgfTAA", "(Lkotlin/random/Random;[B)[B", "fromIndex", "toIndex", "nextUBytes-Wvrt4B4", "(Lkotlin/random/Random;[BII)[B", "nextUInt", "(Lkotlin/random/Random;)I", "nextUInt-qCasIEU", "(Lkotlin/random/Random;I)I", "nextUInt-a8DCA5k", "(Lkotlin/random/Random;II)I", "range", "Lkotlin/ranges/UIntRange;", "(Lkotlin/random/Random;Lkotlin/ranges/UIntRange;)I", "nextULong", "(Lkotlin/random/Random;)J", "nextULong-V1Xi4fY", "(Lkotlin/random/Random;J)J", "nextULong-jmpaW-c", "(Lkotlin/random/Random;JJ)J", "Lkotlin/ranges/ULongRange;", "(Lkotlin/random/Random;Lkotlin/ranges/ULongRange;)J", "kotlin-stdlib"}, k=2, mv={1, 1, 16})
public final class URandomKt {
    public static final void checkUIntRangeBounds-J1ME1BU(int n, int n2) {
        boolean bl = UnsignedKt.uintCompare(n2, n) > 0;
        if (!bl) throw (Throwable)new IllegalArgumentException(RandomKt.boundsErrorMessage(UInt.box-impl(n), UInt.box-impl(n2)).toString());
    }

    public static final void checkULongRangeBounds-eb3DHEI(long l, long l2) {
        boolean bl = UnsignedKt.ulongCompare(l2, l) > 0;
        if (!bl) throw (Throwable)new IllegalArgumentException(RandomKt.boundsErrorMessage(ULong.box-impl(l), ULong.box-impl(l2)).toString());
    }

    public static final byte[] nextUBytes(Random random, int n) {
        Intrinsics.checkParameterIsNotNull(random, "$this$nextUBytes");
        return UByteArray.constructor-impl(random.nextBytes(n));
    }

    public static final byte[] nextUBytes-EVgfTAA(Random random, byte[] arrby) {
        Intrinsics.checkParameterIsNotNull(random, "$this$nextUBytes");
        Intrinsics.checkParameterIsNotNull(arrby, "array");
        random.nextBytes(arrby);
        return arrby;
    }

    public static final byte[] nextUBytes-Wvrt4B4(Random random, byte[] arrby, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(random, "$this$nextUBytes");
        Intrinsics.checkParameterIsNotNull(arrby, "array");
        random.nextBytes(arrby, n, n2);
        return arrby;
    }

    public static /* synthetic */ byte[] nextUBytes-Wvrt4B4$default(Random random, byte[] arrby, int n, int n2, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) == 0) return URandomKt.nextUBytes-Wvrt4B4(random, arrby, n, n2);
        n2 = UByteArray.getSize-impl(arrby);
        return URandomKt.nextUBytes-Wvrt4B4(random, arrby, n, n2);
    }

    public static final int nextUInt(Random random) {
        Intrinsics.checkParameterIsNotNull(random, "$this$nextUInt");
        return UInt.constructor-impl(random.nextInt());
    }

    public static final int nextUInt(Random object, UIntRange uIntRange) {
        Intrinsics.checkParameterIsNotNull(object, "$this$nextUInt");
        Intrinsics.checkParameterIsNotNull(uIntRange, "range");
        if (uIntRange.isEmpty()) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Cannot get random in empty range: ");
            ((StringBuilder)object).append(uIntRange);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString());
        }
        if (UnsignedKt.uintCompare(uIntRange.getLast(), -1) < 0) {
            return URandomKt.nextUInt-a8DCA5k((Random)object, uIntRange.getFirst(), UInt.constructor-impl(uIntRange.getLast() + 1));
        }
        if (UnsignedKt.uintCompare(uIntRange.getFirst(), 0) <= 0) return URandomKt.nextUInt((Random)object);
        return UInt.constructor-impl(URandomKt.nextUInt-a8DCA5k((Random)object, UInt.constructor-impl(uIntRange.getFirst() - 1), uIntRange.getLast()) + 1);
    }

    public static final int nextUInt-a8DCA5k(Random random, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(random, "$this$nextUInt");
        URandomKt.checkUIntRangeBounds-J1ME1BU(n, n2);
        return UInt.constructor-impl(random.nextInt(n ^ Integer.MIN_VALUE, n2 ^ Integer.MIN_VALUE) ^ Integer.MIN_VALUE);
    }

    public static final int nextUInt-qCasIEU(Random random, int n) {
        Intrinsics.checkParameterIsNotNull(random, "$this$nextUInt");
        return URandomKt.nextUInt-a8DCA5k(random, 0, n);
    }

    public static final long nextULong(Random random) {
        Intrinsics.checkParameterIsNotNull(random, "$this$nextULong");
        return ULong.constructor-impl(random.nextLong());
    }

    public static final long nextULong(Random object, ULongRange uLongRange) {
        Intrinsics.checkParameterIsNotNull(object, "$this$nextULong");
        Intrinsics.checkParameterIsNotNull(uLongRange, "range");
        if (uLongRange.isEmpty()) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Cannot get random in empty range: ");
            ((StringBuilder)object).append(uLongRange);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString());
        }
        if (UnsignedKt.ulongCompare(uLongRange.getLast(), -1L) < 0) {
            return URandomKt.nextULong-jmpaW-c((Random)object, uLongRange.getFirst(), ULong.constructor-impl(uLongRange.getLast() + ULong.constructor-impl((long)1 & 0xFFFFFFFFL)));
        }
        if (UnsignedKt.ulongCompare(uLongRange.getFirst(), 0L) <= 0) return URandomKt.nextULong((Random)object);
        long l = uLongRange.getFirst();
        long l2 = (long)1 & 0xFFFFFFFFL;
        return ULong.constructor-impl(URandomKt.nextULong-jmpaW-c((Random)object, ULong.constructor-impl(l - ULong.constructor-impl(l2)), uLongRange.getLast()) + ULong.constructor-impl(l2));
    }

    public static final long nextULong-V1Xi4fY(Random random, long l) {
        Intrinsics.checkParameterIsNotNull(random, "$this$nextULong");
        return URandomKt.nextULong-jmpaW-c(random, 0L, l);
    }

    public static final long nextULong-jmpaW-c(Random random, long l, long l2) {
        Intrinsics.checkParameterIsNotNull(random, "$this$nextULong");
        URandomKt.checkULongRangeBounds-eb3DHEI(l, l2);
        return ULong.constructor-impl(random.nextLong(l ^ Long.MIN_VALUE, l2 ^ Long.MIN_VALUE) ^ Long.MIN_VALUE);
    }
}

