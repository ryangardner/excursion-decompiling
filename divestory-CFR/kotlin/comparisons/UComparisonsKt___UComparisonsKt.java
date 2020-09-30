/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.comparisons;

import kotlin.Metadata;
import kotlin.UnsignedKt;
import kotlin.comparisons.UComparisonsKt;
import kotlin.jvm.internal.Intrinsics;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(bv={1, 0, 3}, d1={"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000e\u001a\"\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0004\u0010\u0005\u001a+\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0001H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0007\u0010\b\u001a\"\u0010\u0000\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\tH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\n\u0010\u000b\u001a+\u0010\u0000\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\tH\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b\f\u0010\r\u001a\"\u0010\u0000\u001a\u00020\u000e2\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u000eH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u000f\u0010\u0010\u001a+\u0010\u0000\u001a\u00020\u000e2\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u000e2\u0006\u0010\u0006\u001a\u00020\u000eH\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0011\u0010\u0012\u001a\"\u0010\u0000\u001a\u00020\u00132\u0006\u0010\u0002\u001a\u00020\u00132\u0006\u0010\u0003\u001a\u00020\u0013H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0014\u0010\u0015\u001a+\u0010\u0000\u001a\u00020\u00132\u0006\u0010\u0002\u001a\u00020\u00132\u0006\u0010\u0003\u001a\u00020\u00132\u0006\u0010\u0006\u001a\u00020\u0013H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0016\u0010\u0017\u001a\"\u0010\u0018\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0019\u0010\u0005\u001a+\u0010\u0018\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0001H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001a\u0010\b\u001a\"\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\tH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001b\u0010\u000b\u001a+\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\tH\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001c\u0010\r\u001a\"\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u000eH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001d\u0010\u0010\u001a+\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u000e2\u0006\u0010\u0006\u001a\u00020\u000eH\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001e\u0010\u0012\u001a\"\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0002\u001a\u00020\u00132\u0006\u0010\u0003\u001a\u00020\u0013H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001f\u0010\u0015\u001a+\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0002\u001a\u00020\u00132\u0006\u0010\u0003\u001a\u00020\u00132\u0006\u0010\u0006\u001a\u00020\u0013H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b \u0010\u0017\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006!"}, d2={"maxOf", "Lkotlin/UByte;", "a", "b", "maxOf-Kr8caGY", "(BB)B", "c", "maxOf-b33U2AM", "(BBB)B", "Lkotlin/UInt;", "maxOf-J1ME1BU", "(II)I", "maxOf-WZ9TVnA", "(III)I", "Lkotlin/ULong;", "maxOf-eb3DHEI", "(JJ)J", "maxOf-sambcqE", "(JJJ)J", "Lkotlin/UShort;", "maxOf-5PvTz6A", "(SS)S", "maxOf-VKSA0NQ", "(SSS)S", "minOf", "minOf-Kr8caGY", "minOf-b33U2AM", "minOf-J1ME1BU", "minOf-WZ9TVnA", "minOf-eb3DHEI", "minOf-sambcqE", "minOf-5PvTz6A", "minOf-VKSA0NQ", "kotlin-stdlib"}, k=5, mv={1, 1, 16}, xi=1, xs="kotlin/comparisons/UComparisonsKt")
class UComparisonsKt___UComparisonsKt {
    public static final short maxOf-5PvTz6A(short s, short s2) {
        if (Intrinsics.compare(s & 65535, 65535 & s2) < 0) return s2;
        return s;
    }

    public static final int maxOf-J1ME1BU(int n, int n2) {
        if (UnsignedKt.uintCompare(n, n2) < 0) return n2;
        return n;
    }

    public static final byte maxOf-Kr8caGY(byte by, byte by2) {
        if (Intrinsics.compare(by & 255, by2 & 255) < 0) return by2;
        return by;
    }

    private static final short maxOf-VKSA0NQ(short s, short s2, short s3) {
        return UComparisonsKt.maxOf-5PvTz6A(s, UComparisonsKt.maxOf-5PvTz6A(s2, s3));
    }

    private static final int maxOf-WZ9TVnA(int n, int n2, int n3) {
        return UComparisonsKt.maxOf-J1ME1BU(n, UComparisonsKt.maxOf-J1ME1BU(n2, n3));
    }

    private static final byte maxOf-b33U2AM(byte by, byte by2, byte by3) {
        return UComparisonsKt.maxOf-Kr8caGY(by, UComparisonsKt.maxOf-Kr8caGY(by2, by3));
    }

    public static final long maxOf-eb3DHEI(long l, long l2) {
        if (UnsignedKt.ulongCompare(l, l2) < 0) return l2;
        return l;
    }

    private static final long maxOf-sambcqE(long l, long l2, long l3) {
        return UComparisonsKt.maxOf-eb3DHEI(l, UComparisonsKt.maxOf-eb3DHEI(l2, l3));
    }

    public static final short minOf-5PvTz6A(short s, short s2) {
        if (Intrinsics.compare(s & 65535, 65535 & s2) > 0) return s2;
        return s;
    }

    public static final int minOf-J1ME1BU(int n, int n2) {
        if (UnsignedKt.uintCompare(n, n2) > 0) return n2;
        return n;
    }

    public static final byte minOf-Kr8caGY(byte by, byte by2) {
        if (Intrinsics.compare(by & 255, by2 & 255) > 0) return by2;
        return by;
    }

    private static final short minOf-VKSA0NQ(short s, short s2, short s3) {
        return UComparisonsKt.minOf-5PvTz6A(s, UComparisonsKt.minOf-5PvTz6A(s2, s3));
    }

    private static final int minOf-WZ9TVnA(int n, int n2, int n3) {
        return UComparisonsKt.minOf-J1ME1BU(n, UComparisonsKt.minOf-J1ME1BU(n2, n3));
    }

    private static final byte minOf-b33U2AM(byte by, byte by2, byte by3) {
        return UComparisonsKt.minOf-Kr8caGY(by, UComparisonsKt.minOf-Kr8caGY(by2, by3));
    }

    public static final long minOf-eb3DHEI(long l, long l2) {
        if (UnsignedKt.ulongCompare(l, l2) > 0) return l2;
        return l;
    }

    private static final long minOf-sambcqE(long l, long l2, long l3) {
        return UComparisonsKt.minOf-eb3DHEI(l, UComparisonsKt.minOf-eb3DHEI(l2, l3));
    }
}

