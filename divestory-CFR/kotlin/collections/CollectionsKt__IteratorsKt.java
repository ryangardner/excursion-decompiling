/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.collections;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IteratorsJVMKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.IndexingIterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\u0000\u001c\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010(\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a-\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\u0005H\u0086\b\u001a\u001f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0087\n\u001a\"\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\b0\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003\u00a8\u0006\t"}, d2={"forEach", "", "T", "", "operation", "Lkotlin/Function1;", "iterator", "withIndex", "Lkotlin/collections/IndexedValue;", "kotlin-stdlib"}, k=5, mv={1, 1, 16}, xi=1, xs="kotlin/collections/CollectionsKt")
class CollectionsKt__IteratorsKt
extends CollectionsKt__IteratorsJVMKt {
    public static final <T> void forEach(Iterator<? extends T> iterator2, Function1<? super T, Unit> function1) {
        Intrinsics.checkParameterIsNotNull(iterator2, "$this$forEach");
        Intrinsics.checkParameterIsNotNull(function1, "operation");
        while (iterator2.hasNext()) {
            function1.invoke(iterator2.next());
        }
    }

    private static final <T> Iterator<T> iterator(Iterator<? extends T> iterator2) {
        Intrinsics.checkParameterIsNotNull(iterator2, "$this$iterator");
        return iterator2;
    }

    public static final <T> Iterator<IndexedValue<T>> withIndex(Iterator<? extends T> iterator2) {
        Intrinsics.checkParameterIsNotNull(iterator2, "$this$withIndex");
        return new IndexingIterator<T>(iterator2);
    }
}

