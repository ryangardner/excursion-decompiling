/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UByteArray;
import kotlin.UInt;
import kotlin.UIntArray;
import kotlin.ULong;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\u0000F\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0004\u0010\u0005\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00010\u0002H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0006\u0010\u0005\u001a\u001c\u0010\u0000\u001a\u00020\u0007*\b\u0012\u0004\u0012\u00020\u00070\u0002H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\b\u0010\t\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\n0\u0002H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u000b\u0010\u0005\u001a\u001a\u0010\f\u001a\u00020\r*\b\u0012\u0004\u0012\u00020\u00030\u000eH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000f\u001a\u001a\u0010\u0010\u001a\u00020\u0011*\b\u0012\u0004\u0012\u00020\u00010\u000eH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0012\u001a\u001a\u0010\u0013\u001a\u00020\u0014*\b\u0012\u0004\u0012\u00020\u00070\u000eH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0015\u001a\u001a\u0010\u0016\u001a\u00020\u0017*\b\u0012\u0004\u0012\u00020\n0\u000eH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0019"}, d2={"sum", "Lkotlin/UInt;", "", "Lkotlin/UByte;", "sumOfUByte", "(Ljava/lang/Iterable;)I", "sumOfUInt", "Lkotlin/ULong;", "sumOfULong", "(Ljava/lang/Iterable;)J", "Lkotlin/UShort;", "sumOfUShort", "toUByteArray", "Lkotlin/UByteArray;", "", "(Ljava/util/Collection;)[B", "toUIntArray", "Lkotlin/UIntArray;", "(Ljava/util/Collection;)[I", "toULongArray", "Lkotlin/ULongArray;", "(Ljava/util/Collection;)[J", "toUShortArray", "Lkotlin/UShortArray;", "(Ljava/util/Collection;)[S", "kotlin-stdlib"}, k=5, mv={1, 1, 16}, xi=1, xs="kotlin/collections/UCollectionsKt")
class UCollectionsKt___UCollectionsKt {
    public static final int sumOfUByte(Iterable<UByte> object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$sum");
        object = object.iterator();
        int n = 0;
        while (object.hasNext()) {
            n = UInt.constructor-impl(n + UInt.constructor-impl(((UByte)object.next()).unbox-impl() & 255));
        }
        return n;
    }

    public static final int sumOfUInt(Iterable<UInt> object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$sum");
        object = object.iterator();
        int n = 0;
        while (object.hasNext()) {
            n = UInt.constructor-impl(n + ((UInt)object.next()).unbox-impl());
        }
        return n;
    }

    public static final long sumOfULong(Iterable<ULong> object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$sum");
        object = object.iterator();
        long l = 0L;
        while (object.hasNext()) {
            l = ULong.constructor-impl(l + ((ULong)object.next()).unbox-impl());
        }
        return l;
    }

    public static final int sumOfUShort(Iterable<UShort> object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$sum");
        object = object.iterator();
        int n = 0;
        while (object.hasNext()) {
            n = UInt.constructor-impl(n + UInt.constructor-impl(((UShort)object.next()).unbox-impl() & 65535));
        }
        return n;
    }

    public static final byte[] toUByteArray(Collection<UByte> object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$toUByteArray");
        byte[] arrby = UByteArray.constructor-impl(object.size());
        object = object.iterator();
        int n = 0;
        while (object.hasNext()) {
            UByteArray.set-VurrAj0(arrby, n, ((UByte)object.next()).unbox-impl());
            ++n;
        }
        return arrby;
    }

    public static final int[] toUIntArray(Collection<UInt> object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$toUIntArray");
        int[] arrn = UIntArray.constructor-impl(object.size());
        object = object.iterator();
        int n = 0;
        while (object.hasNext()) {
            UIntArray.set-VXSXFK8(arrn, n, ((UInt)object.next()).unbox-impl());
            ++n;
        }
        return arrn;
    }

    public static final long[] toULongArray(Collection<ULong> object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$toULongArray");
        long[] arrl = ULongArray.constructor-impl(object.size());
        object = object.iterator();
        int n = 0;
        while (object.hasNext()) {
            ULongArray.set-k8EXiF4(arrl, n, ((ULong)object.next()).unbox-impl());
            ++n;
        }
        return arrl;
    }

    public static final short[] toUShortArray(Collection<UShort> object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$toUShortArray");
        short[] arrs = UShortArray.constructor-impl(object.size());
        object = object.iterator();
        int n = 0;
        while (object.hasNext()) {
            UShortArray.set-01HTLdE(arrs, n, ((UShort)object.next()).unbox-impl());
            ++n;
        }
        return arrs;
    }
}

