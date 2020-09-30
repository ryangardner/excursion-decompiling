/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  kotlin.collections.unsigned.UArraysKt___UArraysJvmKt$asList
 */
package kotlin.collections.unsigned;

import java.util.List;
import java.util.RandomAccess;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UByteArray;
import kotlin.UInt;
import kotlin.UIntArray;
import kotlin.ULong;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.UnsignedKt;
import kotlin.collections.AbstractList;
import kotlin.collections.unsigned.UArraysKt;
import kotlin.collections.unsigned.UArraysKt___UArraysJvmKt;
import kotlin.jvm.internal.Intrinsics;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(bv={1, 0, 3}, d1={"\u0000>\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0016\u001a\u001c\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0004\u0010\u0005\u001a\u001c\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00060\u0001*\u00020\u0007H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\b\u0010\t\u001a\u001c\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\n0\u0001*\u00020\u000bH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\f\u0010\r\u001a\u001c\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0001*\u00020\u000fH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0010\u0010\u0011\u001a2\u0010\u0012\u001a\u00020\u0013*\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u00022\b\b\u0002\u0010\u0015\u001a\u00020\u00132\b\b\u0002\u0010\u0016\u001a\u00020\u0013H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0017\u0010\u0018\u001a2\u0010\u0012\u001a\u00020\u0013*\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u00062\b\b\u0002\u0010\u0015\u001a\u00020\u00132\b\b\u0002\u0010\u0016\u001a\u00020\u0013H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0019\u0010\u001a\u001a2\u0010\u0012\u001a\u00020\u0013*\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\n2\b\b\u0002\u0010\u0015\u001a\u00020\u00132\b\b\u0002\u0010\u0016\u001a\u00020\u0013H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001b\u0010\u001c\u001a2\u0010\u0012\u001a\u00020\u0013*\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u000e2\b\b\u0002\u0010\u0015\u001a\u00020\u00132\b\b\u0002\u0010\u0016\u001a\u00020\u0013H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001d\u0010\u001e\u001a\u001f\u0010\u001f\u001a\u00020\u0002*\u00020\u00032\u0006\u0010 \u001a\u00020\u0013H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b!\u0010\"\u001a\u001f\u0010\u001f\u001a\u00020\u0006*\u00020\u00072\u0006\u0010 \u001a\u00020\u0013H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b#\u0010$\u001a\u001f\u0010\u001f\u001a\u00020\n*\u00020\u000b2\u0006\u0010 \u001a\u00020\u0013H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b%\u0010&\u001a\u001f\u0010\u001f\u001a\u00020\u000e*\u00020\u000f2\u0006\u0010 \u001a\u00020\u0013H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b'\u0010(\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006)"}, d2={"asList", "", "Lkotlin/UByte;", "Lkotlin/UByteArray;", "asList-GBYM_sE", "([B)Ljava/util/List;", "Lkotlin/UInt;", "Lkotlin/UIntArray;", "asList--ajY-9A", "([I)Ljava/util/List;", "Lkotlin/ULong;", "Lkotlin/ULongArray;", "asList-QwZRm1k", "([J)Ljava/util/List;", "Lkotlin/UShort;", "Lkotlin/UShortArray;", "asList-rL5Bavg", "([S)Ljava/util/List;", "binarySearch", "", "element", "fromIndex", "toIndex", "binarySearch-WpHrYlw", "([BBII)I", "binarySearch-2fe2U9s", "([IIII)I", "binarySearch-K6DWlUc", "([JJII)I", "binarySearch-EtDCXyQ", "([SSII)I", "elementAt", "index", "elementAt-PpDY95g", "([BI)B", "elementAt-qFRl0hI", "([II)I", "elementAt-r7IrZao", "([JI)J", "elementAt-nggk6HY", "([SI)S", "kotlin-stdlib"}, k=5, mv={1, 1, 16}, pn="kotlin.collections", xi=1, xs="kotlin/collections/unsigned/UArraysKt")
class UArraysKt___UArraysJvmKt {
    public static final List<UInt> asList--ajY-9A(int[] arrn) {
        Intrinsics.checkParameterIsNotNull(arrn, "$this$asList");
        return (List)((Object)new RandomAccess(arrn){
            final /* synthetic */ int[] $this_asList;
            {
                this.$this_asList = arrn;
            }

            public boolean contains-WZ4Q5Ns(int n) {
                return UIntArray.contains-WZ4Q5Ns(this.$this_asList, n);
            }

            public UInt get(int n) {
                return UInt.box-impl(UIntArray.get-impl(this.$this_asList, n));
            }

            public int getSize() {
                return UIntArray.getSize-impl(this.$this_asList);
            }

            public int indexOf-WZ4Q5Ns(int n) {
                return kotlin.collections.ArraysKt.indexOf(this.$this_asList, n);
            }

            public boolean isEmpty() {
                return UIntArray.isEmpty-impl(this.$this_asList);
            }

            public int lastIndexOf-WZ4Q5Ns(int n) {
                return kotlin.collections.ArraysKt.lastIndexOf(this.$this_asList, n);
            }
        });
    }

    public static final List<UByte> asList-GBYM_sE(byte[] arrby) {
        Intrinsics.checkParameterIsNotNull(arrby, "$this$asList");
        return (List)((Object)new RandomAccess(arrby){
            final /* synthetic */ byte[] $this_asList;
            {
                this.$this_asList = arrby;
            }

            public boolean contains-7apg3OU(byte by) {
                return UByteArray.contains-7apg3OU(this.$this_asList, by);
            }

            public UByte get(int n) {
                return UByte.box-impl(UByteArray.get-impl(this.$this_asList, n));
            }

            public int getSize() {
                return UByteArray.getSize-impl(this.$this_asList);
            }

            public int indexOf-7apg3OU(byte by) {
                return kotlin.collections.ArraysKt.indexOf(this.$this_asList, by);
            }

            public boolean isEmpty() {
                return UByteArray.isEmpty-impl(this.$this_asList);
            }

            public int lastIndexOf-7apg3OU(byte by) {
                return kotlin.collections.ArraysKt.lastIndexOf(this.$this_asList, by);
            }
        });
    }

    public static final List<ULong> asList-QwZRm1k(long[] arrl) {
        Intrinsics.checkParameterIsNotNull(arrl, "$this$asList");
        return (List)((Object)new RandomAccess(arrl){
            final /* synthetic */ long[] $this_asList;
            {
                this.$this_asList = arrl;
            }

            public boolean contains-VKZWuLQ(long l) {
                return ULongArray.contains-VKZWuLQ(this.$this_asList, l);
            }

            public ULong get(int n) {
                return ULong.box-impl(ULongArray.get-impl(this.$this_asList, n));
            }

            public int getSize() {
                return ULongArray.getSize-impl(this.$this_asList);
            }

            public int indexOf-VKZWuLQ(long l) {
                return kotlin.collections.ArraysKt.indexOf(this.$this_asList, l);
            }

            public boolean isEmpty() {
                return ULongArray.isEmpty-impl(this.$this_asList);
            }

            public int lastIndexOf-VKZWuLQ(long l) {
                return kotlin.collections.ArraysKt.lastIndexOf(this.$this_asList, l);
            }
        });
    }

    public static final List<UShort> asList-rL5Bavg(short[] arrs) {
        Intrinsics.checkParameterIsNotNull(arrs, "$this$asList");
        return (List)((Object)new RandomAccess(arrs){
            final /* synthetic */ short[] $this_asList;
            {
                this.$this_asList = arrs;
            }

            public boolean contains-xj2QHRw(short s) {
                return UShortArray.contains-xj2QHRw(this.$this_asList, s);
            }

            public UShort get(int n) {
                return UShort.box-impl(UShortArray.get-impl(this.$this_asList, n));
            }

            public int getSize() {
                return UShortArray.getSize-impl(this.$this_asList);
            }

            public int indexOf-xj2QHRw(short s) {
                return kotlin.collections.ArraysKt.indexOf(this.$this_asList, s);
            }

            public boolean isEmpty() {
                return UShortArray.isEmpty-impl(this.$this_asList);
            }

            public int lastIndexOf-xj2QHRw(short s) {
                return kotlin.collections.ArraysKt.lastIndexOf(this.$this_asList, s);
            }
        });
    }

    public static final int binarySearch-2fe2U9s(int[] arrn, int n, int n2, int n3) {
        Intrinsics.checkParameterIsNotNull(arrn, "$this$binarySearch");
        AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(n2, n3, UIntArray.getSize-impl(arrn));
        --n3;
        while (n2 <= n3) {
            int n4 = n2 + n3 >>> 1;
            int n5 = UnsignedKt.uintCompare(arrn[n4], n);
            if (n5 < 0) {
                n2 = n4 + 1;
                continue;
            }
            if (n5 <= 0) return n4;
            n3 = n4 - 1;
        }
        return -(n2 + 1);
    }

    public static /* synthetic */ int binarySearch-2fe2U9s$default(int[] arrn, int n, int n2, int n3, int n4, Object object) {
        if ((n4 & 2) != 0) {
            n2 = 0;
        }
        if ((n4 & 4) == 0) return UArraysKt.binarySearch-2fe2U9s(arrn, n, n2, n3);
        n3 = UIntArray.getSize-impl(arrn);
        return UArraysKt.binarySearch-2fe2U9s(arrn, n, n2, n3);
    }

    public static final int binarySearch-EtDCXyQ(short[] arrs, short s, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(arrs, "$this$binarySearch");
        AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(n, n2, UShortArray.getSize-impl(arrs));
        --n2;
        while (n <= n2) {
            int n3 = n + n2 >>> 1;
            int n4 = UnsignedKt.uintCompare(arrs[n3], s & 65535);
            if (n4 < 0) {
                n = n3 + 1;
                continue;
            }
            if (n4 <= 0) return n3;
            n2 = n3 - 1;
        }
        return -(n + 1);
    }

    public static /* synthetic */ int binarySearch-EtDCXyQ$default(short[] arrs, short s, int n, int n2, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) == 0) return UArraysKt.binarySearch-EtDCXyQ(arrs, s, n, n2);
        n2 = UShortArray.getSize-impl(arrs);
        return UArraysKt.binarySearch-EtDCXyQ(arrs, s, n, n2);
    }

    public static final int binarySearch-K6DWlUc(long[] arrl, long l, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(arrl, "$this$binarySearch");
        AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(n, n2, ULongArray.getSize-impl(arrl));
        --n2;
        while (n <= n2) {
            int n3 = n + n2 >>> 1;
            int n4 = UnsignedKt.ulongCompare(arrl[n3], l);
            if (n4 < 0) {
                n = n3 + 1;
                continue;
            }
            if (n4 <= 0) return n3;
            n2 = n3 - 1;
        }
        return -(n + 1);
    }

    public static /* synthetic */ int binarySearch-K6DWlUc$default(long[] arrl, long l, int n, int n2, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) == 0) return UArraysKt.binarySearch-K6DWlUc(arrl, l, n, n2);
        n2 = ULongArray.getSize-impl(arrl);
        return UArraysKt.binarySearch-K6DWlUc(arrl, l, n, n2);
    }

    public static final int binarySearch-WpHrYlw(byte[] arrby, byte by, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(arrby, "$this$binarySearch");
        AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(n, n2, UByteArray.getSize-impl(arrby));
        --n2;
        while (n <= n2) {
            int n3 = n + n2 >>> 1;
            int n4 = UnsignedKt.uintCompare(arrby[n3], by & 255);
            if (n4 < 0) {
                n = n3 + 1;
                continue;
            }
            if (n4 <= 0) return n3;
            n2 = n3 - 1;
        }
        return -(n + 1);
    }

    public static /* synthetic */ int binarySearch-WpHrYlw$default(byte[] arrby, byte by, int n, int n2, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) == 0) return UArraysKt.binarySearch-WpHrYlw(arrby, by, n, n2);
        n2 = UByteArray.getSize-impl(arrby);
        return UArraysKt.binarySearch-WpHrYlw(arrby, by, n, n2);
    }

    private static final byte elementAt-PpDY95g(byte[] arrby, int n) {
        return UByteArray.get-impl(arrby, n);
    }

    private static final short elementAt-nggk6HY(short[] arrs, int n) {
        return UShortArray.get-impl(arrs, n);
    }

    private static final int elementAt-qFRl0hI(int[] arrn, int n) {
        return UIntArray.get-impl(arrn, n);
    }

    private static final long elementAt-r7IrZao(long[] arrl, int n) {
        return ULongArray.get-impl(arrl, n);
    }
}

