/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UShort;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;

@Metadata(bv={1, 0, 3}, d1={"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0004\u0010\u0005\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00010\u0002H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0006\u0010\u0005\u001a\u001c\u0010\u0000\u001a\u00020\u0007*\b\u0012\u0004\u0012\u00020\u00070\u0002H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\b\u0010\t\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\n0\u0002H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u000b\u0010\u0005\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\f"}, d2={"sum", "Lkotlin/UInt;", "Lkotlin/sequences/Sequence;", "Lkotlin/UByte;", "sumOfUByte", "(Lkotlin/sequences/Sequence;)I", "sumOfUInt", "Lkotlin/ULong;", "sumOfULong", "(Lkotlin/sequences/Sequence;)J", "Lkotlin/UShort;", "sumOfUShort", "kotlin-stdlib"}, k=5, mv={1, 1, 16}, xi=1, xs="kotlin/sequences/USequencesKt")
class USequencesKt___USequencesKt {
    public static final int sumOfUByte(Sequence<UByte> object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$sum");
        object = object.iterator();
        int n = 0;
        while (object.hasNext()) {
            n = UInt.constructor-impl(n + UInt.constructor-impl(((UByte)object.next()).unbox-impl() & 255));
        }
        return n;
    }

    public static final int sumOfUInt(Sequence<UInt> object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$sum");
        object = object.iterator();
        int n = 0;
        while (object.hasNext()) {
            n = UInt.constructor-impl(n + ((UInt)object.next()).unbox-impl());
        }
        return n;
    }

    public static final long sumOfULong(Sequence<ULong> object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$sum");
        object = object.iterator();
        long l = 0L;
        while (object.hasNext()) {
            l = ULong.constructor-impl(l + ((ULong)object.next()).unbox-impl());
        }
        return l;
    }

    public static final int sumOfUShort(Sequence<UShort> object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$sum");
        object = object.iterator();
        int n = 0;
        while (object.hasNext()) {
            n = UInt.constructor-impl(n + UInt.constructor-impl(((UShort)object.next()).unbox-impl() & 65535));
        }
        return n;
    }
}

