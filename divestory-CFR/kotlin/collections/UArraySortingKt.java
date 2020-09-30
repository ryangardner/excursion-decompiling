/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.collections;

import kotlin.Metadata;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShortArray;
import kotlin.UnsignedKt;
import kotlin.jvm.internal.Intrinsics;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(bv={1, 0, 3}, d1={"\u00000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0012\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0006\u0010\u0007\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b\t\u0010\n\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b\f\u0010\r\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u000f\u0010\u0010\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0013\u0010\u0014\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0015\u0010\u0016\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0017\u0010\u0018\u001a*\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0019\u0010\u001a\u001a\u001a\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u0003H\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001c\u0010\u001d\u001a\u001a\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\bH\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001e\u0010\u001f\u001a\u001a\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000bH\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b \u0010!\u001a\u001a\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000eH\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\"\u0010#\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006$"}, d2={"partition", "", "array", "Lkotlin/UByteArray;", "left", "right", "partition-4UcCI2c", "([BII)I", "Lkotlin/UIntArray;", "partition-oBK06Vg", "([III)I", "Lkotlin/ULongArray;", "partition--nroSd4", "([JII)I", "Lkotlin/UShortArray;", "partition-Aa5vz7o", "([SII)I", "quickSort", "", "quickSort-4UcCI2c", "([BII)V", "quickSort-oBK06Vg", "([III)V", "quickSort--nroSd4", "([JII)V", "quickSort-Aa5vz7o", "([SII)V", "sortArray", "sortArray-GBYM_sE", "([B)V", "sortArray--ajY-9A", "([I)V", "sortArray-QwZRm1k", "([J)V", "sortArray-rL5Bavg", "([S)V", "kotlin-stdlib"}, k=2, mv={1, 1, 16})
public final class UArraySortingKt {
    private static final int partition--nroSd4(long[] arrl, int n, int n2) {
        long l = ULongArray.get-impl(arrl, (n + n2) / 2);
        while (n <= n2) {
            int n3;
            int n4 = n;
            do {
                n3 = n2;
                if (UnsignedKt.ulongCompare(ULongArray.get-impl(arrl, n4), l) >= 0) break;
                ++n4;
            } while (true);
            while (UnsignedKt.ulongCompare(ULongArray.get-impl(arrl, n3), l) > 0) {
                --n3;
            }
            n = n4;
            n2 = n3;
            if (n4 > n3) continue;
            long l2 = ULongArray.get-impl(arrl, n4);
            ULongArray.set-k8EXiF4(arrl, n4, ULongArray.get-impl(arrl, n3));
            ULongArray.set-k8EXiF4(arrl, n3, l2);
            n = n4 + 1;
            n2 = n3 - 1;
        }
        return n;
    }

    private static final int partition-4UcCI2c(byte[] arrby, int n, int n2) {
        byte by = UByteArray.get-impl(arrby, (n + n2) / 2);
        while (n <= n2) {
            int n3;
            int n4 = n;
            do {
                byte by2 = UByteArray.get-impl(arrby, n4);
                n = by & 255;
                n3 = n2;
                if (Intrinsics.compare(by2 & 255, n) >= 0) break;
                ++n4;
            } while (true);
            while (Intrinsics.compare(UByteArray.get-impl(arrby, n3) & 255, n) > 0) {
                --n3;
            }
            n = n4;
            n2 = n3;
            if (n4 > n3) continue;
            byte by3 = UByteArray.get-impl(arrby, n4);
            UByteArray.set-VurrAj0(arrby, n4, UByteArray.get-impl(arrby, n3));
            UByteArray.set-VurrAj0(arrby, n3, by3);
            n = n4 + 1;
            n2 = n3 - 1;
        }
        return n;
    }

    private static final int partition-Aa5vz7o(short[] arrs, int n, int n2) {
        short s = UShortArray.get-impl(arrs, (n + n2) / 2);
        while (n <= n2) {
            int n3;
            int n4 = n;
            do {
                short s2 = UShortArray.get-impl(arrs, n4);
                n = s & 65535;
                n3 = n2;
                if (Intrinsics.compare(s2 & 65535, n) >= 0) break;
                ++n4;
            } while (true);
            while (Intrinsics.compare(UShortArray.get-impl(arrs, n3) & 65535, n) > 0) {
                --n3;
            }
            n = n4;
            n2 = n3;
            if (n4 > n3) continue;
            short s3 = UShortArray.get-impl(arrs, n4);
            UShortArray.set-01HTLdE(arrs, n4, UShortArray.get-impl(arrs, n3));
            UShortArray.set-01HTLdE(arrs, n3, s3);
            n = n4 + 1;
            n2 = n3 - 1;
        }
        return n;
    }

    private static final int partition-oBK06Vg(int[] arrn, int n, int n2) {
        int n3 = UIntArray.get-impl(arrn, (n + n2) / 2);
        while (n <= n2) {
            int n4;
            int n5 = n;
            do {
                n4 = n2;
                if (UnsignedKt.uintCompare(UIntArray.get-impl(arrn, n5), n3) >= 0) break;
                ++n5;
            } while (true);
            while (UnsignedKt.uintCompare(UIntArray.get-impl(arrn, n4), n3) > 0) {
                --n4;
            }
            n = n5;
            n2 = n4;
            if (n5 > n4) continue;
            n = UIntArray.get-impl(arrn, n5);
            UIntArray.set-VXSXFK8(arrn, n5, UIntArray.get-impl(arrn, n4));
            UIntArray.set-VXSXFK8(arrn, n4, n);
            n = n5 + 1;
            n2 = n4 - 1;
        }
        return n;
    }

    private static final void quickSort--nroSd4(long[] arrl, int n, int n2) {
        int n3 = UArraySortingKt.partition--nroSd4(arrl, n, n2);
        int n4 = n3 - 1;
        if (n < n4) {
            UArraySortingKt.quickSort--nroSd4(arrl, n, n4);
        }
        if (n3 >= n2) return;
        UArraySortingKt.quickSort--nroSd4(arrl, n3, n2);
    }

    private static final void quickSort-4UcCI2c(byte[] arrby, int n, int n2) {
        int n3 = UArraySortingKt.partition-4UcCI2c(arrby, n, n2);
        int n4 = n3 - 1;
        if (n < n4) {
            UArraySortingKt.quickSort-4UcCI2c(arrby, n, n4);
        }
        if (n3 >= n2) return;
        UArraySortingKt.quickSort-4UcCI2c(arrby, n3, n2);
    }

    private static final void quickSort-Aa5vz7o(short[] arrs, int n, int n2) {
        int n3 = UArraySortingKt.partition-Aa5vz7o(arrs, n, n2);
        int n4 = n3 - 1;
        if (n < n4) {
            UArraySortingKt.quickSort-Aa5vz7o(arrs, n, n4);
        }
        if (n3 >= n2) return;
        UArraySortingKt.quickSort-Aa5vz7o(arrs, n3, n2);
    }

    private static final void quickSort-oBK06Vg(int[] arrn, int n, int n2) {
        int n3 = UArraySortingKt.partition-oBK06Vg(arrn, n, n2);
        int n4 = n3 - 1;
        if (n < n4) {
            UArraySortingKt.quickSort-oBK06Vg(arrn, n, n4);
        }
        if (n3 >= n2) return;
        UArraySortingKt.quickSort-oBK06Vg(arrn, n3, n2);
    }

    public static final void sortArray--ajY-9A(int[] arrn) {
        Intrinsics.checkParameterIsNotNull(arrn, "array");
        UArraySortingKt.quickSort-oBK06Vg(arrn, 0, UIntArray.getSize-impl(arrn) - 1);
    }

    public static final void sortArray-GBYM_sE(byte[] arrby) {
        Intrinsics.checkParameterIsNotNull(arrby, "array");
        UArraySortingKt.quickSort-4UcCI2c(arrby, 0, UByteArray.getSize-impl(arrby) - 1);
    }

    public static final void sortArray-QwZRm1k(long[] arrl) {
        Intrinsics.checkParameterIsNotNull(arrl, "array");
        UArraySortingKt.quickSort--nroSd4(arrl, 0, ULongArray.getSize-impl(arrl) - 1);
    }

    public static final void sortArray-rL5Bavg(short[] arrs) {
        Intrinsics.checkParameterIsNotNull(arrs, "array");
        UArraySortingKt.quickSort-Aa5vz7o(arrs, 0, UShortArray.getSize-impl(arrs) - 1);
    }
}

