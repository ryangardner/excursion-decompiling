/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.collections;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;

@Metadata(bv={1, 0, 3}, d1={"\u0000\u001c\n\u0000\n\u0002\u0010\"\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a,\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u0002H\u0002H\u0086\u0002\u00a2\u0006\u0002\u0010\u0004\u001a4\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0006H\u0086\u0002\u00a2\u0006\u0002\u0010\u0007\u001a-\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\bH\u0086\u0002\u001a-\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\tH\u0086\u0002\u001a,\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u0002H\u0002H\u0087\b\u00a2\u0006\u0002\u0010\u0004\u001a,\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u0002H\u0002H\u0086\u0002\u00a2\u0006\u0002\u0010\u0004\u001a4\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0006H\u0086\u0002\u00a2\u0006\u0002\u0010\u0007\u001a-\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\bH\u0086\u0002\u001a-\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\tH\u0086\u0002\u001a,\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u0002H\u0002H\u0087\b\u00a2\u0006\u0002\u0010\u0004\u00a8\u0006\r"}, d2={"minus", "", "T", "element", "(Ljava/util/Set;Ljava/lang/Object;)Ljava/util/Set;", "elements", "", "(Ljava/util/Set;[Ljava/lang/Object;)Ljava/util/Set;", "", "Lkotlin/sequences/Sequence;", "minusElement", "plus", "plusElement", "kotlin-stdlib"}, k=5, mv={1, 1, 16}, xi=1, xs="kotlin/collections/SetsKt")
class SetsKt___SetsKt
extends SetsKt__SetsKt {
    public static final <T> Set<T> minus(Set<? extends T> collection, Iterable<? extends T> iterable) {
        Intrinsics.checkParameterIsNotNull(collection, "$this$minus");
        Intrinsics.checkParameterIsNotNull(iterable, "elements");
        Iterable iterable2 = collection;
        iterable = CollectionsKt.convertToSetForSetOperationWith(iterable, iterable2);
        if (iterable.isEmpty()) {
            return CollectionsKt.toSet(iterable2);
        }
        if (!(iterable instanceof Set)) {
            collection = new LinkedHashSet<T>((Collection)collection);
            ((AbstractSet)collection).removeAll((Collection<?>)iterable);
            return (Set)collection;
        }
        collection = new LinkedHashSet();
        Iterator iterator2 = iterable2.iterator();
        while (iterator2.hasNext()) {
            iterable2 = iterator2.next();
            if (iterable.contains(iterable2)) continue;
            collection.add(iterable2);
        }
        return (Set)collection;
    }

    public static final <T> Set<T> minus(Set<? extends T> object, T t) {
        Intrinsics.checkParameterIsNotNull(object, "$this$minus");
        LinkedHashSet linkedHashSet = new LinkedHashSet(MapsKt.mapCapacity(object.size()));
        object = ((Iterable)object).iterator();
        boolean bl = false;
        while (object.hasNext()) {
            Object e = object.next();
            boolean bl2 = true;
            boolean bl3 = bl;
            boolean bl4 = bl2;
            if (!bl) {
                bl3 = bl;
                bl4 = bl2;
                if (Intrinsics.areEqual(e, t)) {
                    bl3 = true;
                    bl4 = false;
                }
            }
            bl = bl3;
            if (!bl4) continue;
            ((Collection)linkedHashSet).add(e);
            bl = bl3;
        }
        return linkedHashSet;
    }

    public static final <T> Set<T> minus(Set<? extends T> set, Sequence<? extends T> sequence) {
        Intrinsics.checkParameterIsNotNull(set, "$this$minus");
        Intrinsics.checkParameterIsNotNull(sequence, "elements");
        set = new LinkedHashSet<T>((Collection)set);
        CollectionsKt.removeAll((Collection)set, sequence);
        return set;
    }

    public static final <T> Set<T> minus(Set<? extends T> set, T[] arrT) {
        Intrinsics.checkParameterIsNotNull(set, "$this$minus");
        Intrinsics.checkParameterIsNotNull(arrT, "elements");
        set = new LinkedHashSet<T>((Collection)set);
        CollectionsKt.removeAll((Collection)set, arrT);
        return set;
    }

    private static final <T> Set<T> minusElement(Set<? extends T> set, T t) {
        return SetsKt.minus(set, t);
    }

    public static final <T> Set<T> plus(Set<? extends T> set, Iterable<? extends T> iterable) {
        int n;
        Intrinsics.checkParameterIsNotNull(set, "$this$plus");
        Intrinsics.checkParameterIsNotNull(iterable, "elements");
        Serializable serializable = CollectionsKt.collectionSizeOrNull(iterable);
        if (serializable != null) {
            n = ((Number)serializable).intValue();
            n = set.size() + n;
        } else {
            n = set.size() * 2;
        }
        serializable = new LinkedHashSet(MapsKt.mapCapacity(n));
        ((AbstractCollection)((Object)serializable)).addAll((Collection)set);
        CollectionsKt.addAll((Collection)((Object)serializable), iterable);
        return (Set)((Object)serializable);
    }

    public static final <T> Set<T> plus(Set<? extends T> set, T t) {
        Intrinsics.checkParameterIsNotNull(set, "$this$plus");
        LinkedHashSet<T> linkedHashSet = new LinkedHashSet<T>(MapsKt.mapCapacity(set.size() + 1));
        linkedHashSet.addAll((Collection)set);
        linkedHashSet.add(t);
        return linkedHashSet;
    }

    public static final <T> Set<T> plus(Set<? extends T> set, Sequence<? extends T> sequence) {
        Intrinsics.checkParameterIsNotNull(set, "$this$plus");
        Intrinsics.checkParameterIsNotNull(sequence, "elements");
        LinkedHashSet linkedHashSet = new LinkedHashSet(MapsKt.mapCapacity(set.size() * 2));
        linkedHashSet.addAll(set);
        CollectionsKt.addAll((Collection)linkedHashSet, sequence);
        return linkedHashSet;
    }

    public static final <T> Set<T> plus(Set<? extends T> set, T[] arrT) {
        Intrinsics.checkParameterIsNotNull(set, "$this$plus");
        Intrinsics.checkParameterIsNotNull(arrT, "elements");
        LinkedHashSet linkedHashSet = new LinkedHashSet(MapsKt.mapCapacity(set.size() + arrT.length));
        linkedHashSet.addAll(set);
        CollectionsKt.addAll((Collection)linkedHashSet, arrT);
        return linkedHashSet;
    }

    private static final <T> Set<T> plusElement(Set<? extends T> set, T t) {
        return SetsKt.plus(set, t);
    }
}

