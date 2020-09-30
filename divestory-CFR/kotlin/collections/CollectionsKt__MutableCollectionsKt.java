/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.random.Random;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

@Metadata(bv={1, 0, 3}, d1={"\u0000^\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u001f\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001d\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\u001a-\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0005\u00a2\u0006\u0002\u0010\u0006\u001a&\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\u001a&\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\b\u001a9\u0010\t\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f2\u0006\u0010\r\u001a\u00020\u0001H\u0002\u00a2\u0006\u0002\b\u000e\u001a9\u0010\t\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000f2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f2\u0006\u0010\r\u001a\u00020\u0001H\u0002\u00a2\u0006\u0002\b\u000e\u001a(\u0010\u0010\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\u0006\u0010\u0012\u001a\u0002H\u0002H\u0087\n\u00a2\u0006\u0002\u0010\u0013\u001a.\u0010\u0010\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005H\u0087\n\u00a2\u0006\u0002\u0010\u0014\u001a)\u0010\u0010\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007H\u0087\n\u001a)\u0010\u0010\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\bH\u0087\n\u001a(\u0010\u0015\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\u0006\u0010\u0012\u001a\u0002H\u0002H\u0087\n\u00a2\u0006\u0002\u0010\u0013\u001a.\u0010\u0015\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005H\u0087\n\u00a2\u0006\u0002\u0010\u0014\u001a)\u0010\u0015\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007H\u0087\n\u001a)\u0010\u0015\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\bH\u0087\n\u001a-\u0010\u0016\u001a\u00020\u0001\"\t\b\u0000\u0010\u0002\u00a2\u0006\u0002\b\u0017*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u0006\u0010\u0012\u001a\u0002H\u0002H\u0087\b\u00a2\u0006\u0002\u0010\u0018\u001a&\u0010\u0016\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u001aH\u0087\b\u00a2\u0006\u0002\u0010\u001b\u001a-\u0010\u001c\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0005\u00a2\u0006\u0002\u0010\u0006\u001a&\u0010\u001c\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\u001a&\u0010\u001c\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\b\u001a.\u0010\u001c\u001a\u00020\u0001\"\t\b\u0000\u0010\u0002\u00a2\u0006\u0002\b\u0017*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u001dH\u0087\b\u001a*\u0010\u001c\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f\u001a*\u0010\u001c\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000f2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f\u001a\u001d\u0010\u001e\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000fH\u0007\u00a2\u0006\u0002\u0010\u001f\u001a\u001f\u0010 \u001a\u0004\u0018\u0001H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000fH\u0007\u00a2\u0006\u0002\u0010\u001f\u001a\u001d\u0010!\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000fH\u0007\u00a2\u0006\u0002\u0010\u001f\u001a\u001f\u0010\"\u001a\u0004\u0018\u0001H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000fH\u0007\u00a2\u0006\u0002\u0010\u001f\u001a-\u0010#\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0005\u00a2\u0006\u0002\u0010\u0006\u001a&\u0010#\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\u001a&\u0010#\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\b\u001a.\u0010#\u001a\u00020\u0001\"\t\b\u0000\u0010\u0002\u00a2\u0006\u0002\b\u0017*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u001dH\u0087\b\u001a*\u0010#\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f\u001a*\u0010#\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000f2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\f\u001a\u0015\u0010$\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u0003H\u0002\u00a2\u0006\u0002\b%\u001a \u0010&\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000f2\u0006\u0010'\u001a\u00020(H\u0007\u001a&\u0010)\u001a\b\u0012\u0004\u0012\u0002H\u00020*\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00072\u0006\u0010'\u001a\u00020(H\u0007\u00a8\u0006+"}, d2={"addAll", "", "T", "", "elements", "", "(Ljava/util/Collection;[Ljava/lang/Object;)Z", "", "Lkotlin/sequences/Sequence;", "filterInPlace", "", "predicate", "Lkotlin/Function1;", "predicateResultToRemove", "filterInPlace$CollectionsKt__MutableCollectionsKt", "", "minusAssign", "", "element", "(Ljava/util/Collection;Ljava/lang/Object;)V", "(Ljava/util/Collection;[Ljava/lang/Object;)V", "plusAssign", "remove", "Lkotlin/internal/OnlyInputTypes;", "(Ljava/util/Collection;Ljava/lang/Object;)Z", "index", "", "(Ljava/util/List;I)Ljava/lang/Object;", "removeAll", "", "removeFirst", "(Ljava/util/List;)Ljava/lang/Object;", "removeFirstOrNull", "removeLast", "removeLastOrNull", "retainAll", "retainNothing", "retainNothing$CollectionsKt__MutableCollectionsKt", "shuffle", "random", "Lkotlin/random/Random;", "shuffled", "", "kotlin-stdlib"}, k=5, mv={1, 1, 16}, xi=1, xs="kotlin/collections/CollectionsKt")
class CollectionsKt__MutableCollectionsKt
extends CollectionsKt__MutableCollectionsJVMKt {
    public static final <T> boolean addAll(Collection<? super T> collection, Iterable<? extends T> object) {
        Intrinsics.checkParameterIsNotNull(collection, "$this$addAll");
        Intrinsics.checkParameterIsNotNull(object, "elements");
        if (object instanceof Collection) {
            return collection.addAll((Collection)object);
        }
        boolean bl = false;
        object = object.iterator();
        while (object.hasNext()) {
            if (!collection.add(object.next())) continue;
            bl = true;
        }
        return bl;
    }

    public static final <T> boolean addAll(Collection<? super T> collection, Sequence<? extends T> object) {
        Intrinsics.checkParameterIsNotNull(collection, "$this$addAll");
        Intrinsics.checkParameterIsNotNull(object, "elements");
        object = object.iterator();
        boolean bl = false;
        while (object.hasNext()) {
            if (!collection.add(object.next())) continue;
            bl = true;
        }
        return bl;
    }

    public static final <T> boolean addAll(Collection<? super T> collection, T[] arrT) {
        Intrinsics.checkParameterIsNotNull(collection, "$this$addAll");
        Intrinsics.checkParameterIsNotNull(arrT, "elements");
        return collection.addAll((Collection)ArraysKt.asList(arrT));
    }

    private static final <T> boolean filterInPlace$CollectionsKt__MutableCollectionsKt(Iterable<? extends T> object, Function1<? super T, Boolean> function1, boolean bl) {
        object = object.iterator();
        boolean bl2 = false;
        while (object.hasNext()) {
            if (function1.invoke(object.next()) != bl) continue;
            object.remove();
            bl2 = true;
        }
        return bl2;
    }

    private static final <T> boolean filterInPlace$CollectionsKt__MutableCollectionsKt(List<T> list, Function1<? super T, Boolean> function1, boolean bl) {
        int n;
        int n2;
        if (!(list instanceof RandomAccess)) {
            if (list == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableIterable<T>");
            return CollectionsKt__MutableCollectionsKt.filterInPlace$CollectionsKt__MutableCollectionsKt(TypeIntrinsics.asMutableIterable(list), function1, bl);
        }
        int n3 = CollectionsKt.getLastIndex(list);
        if (n3 < 0) {
            n = 0;
        } else {
            int n4 = 0;
            n2 = 0;
            do {
                T t;
                if (function1.invoke(t = list.get(n4)) != bl && n2 != n4) {
                    list.set(n2, t);
                }
                n = ++n2;
                if (n4 == n3) break;
                ++n4;
            } while (true);
        }
        if (n >= list.size()) return false;
        n2 = CollectionsKt.getLastIndex(list);
        if (n2 < n) return true;
        do {
            list.remove(n2);
            if (n2 == n) return true;
            --n2;
        } while (true);
    }

    private static final <T> void minusAssign(Collection<? super T> collection, Iterable<? extends T> iterable) {
        Intrinsics.checkParameterIsNotNull(collection, "$this$minusAssign");
        CollectionsKt.removeAll(collection, iterable);
    }

    private static final <T> void minusAssign(Collection<? super T> collection, T t) {
        Intrinsics.checkParameterIsNotNull(collection, "$this$minusAssign");
        collection.remove(t);
    }

    private static final <T> void minusAssign(Collection<? super T> collection, Sequence<? extends T> sequence) {
        Intrinsics.checkParameterIsNotNull(collection, "$this$minusAssign");
        CollectionsKt.removeAll(collection, sequence);
    }

    private static final <T> void minusAssign(Collection<? super T> collection, T[] arrT) {
        Intrinsics.checkParameterIsNotNull(collection, "$this$minusAssign");
        CollectionsKt.removeAll(collection, arrT);
    }

    private static final <T> void plusAssign(Collection<? super T> collection, Iterable<? extends T> iterable) {
        Intrinsics.checkParameterIsNotNull(collection, "$this$plusAssign");
        CollectionsKt.addAll(collection, iterable);
    }

    private static final <T> void plusAssign(Collection<? super T> collection, T t) {
        Intrinsics.checkParameterIsNotNull(collection, "$this$plusAssign");
        collection.add(t);
    }

    private static final <T> void plusAssign(Collection<? super T> collection, Sequence<? extends T> sequence) {
        Intrinsics.checkParameterIsNotNull(collection, "$this$plusAssign");
        CollectionsKt.addAll(collection, sequence);
    }

    private static final <T> void plusAssign(Collection<? super T> collection, T[] arrT) {
        Intrinsics.checkParameterIsNotNull(collection, "$this$plusAssign");
        CollectionsKt.addAll(collection, arrT);
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="Use removeAt(index) instead.", replaceWith=@ReplaceWith(expression="removeAt(index)", imports={}))
    private static final <T> T remove(List<T> list, int n) {
        return list.remove(n);
    }

    private static final <T> boolean remove(Collection<? extends T> collection, T t) {
        if (collection == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableCollection<T>");
        return TypeIntrinsics.asMutableCollection(collection).remove(t);
    }

    public static final <T> boolean removeAll(Iterable<? extends T> iterable, Function1<? super T, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(iterable, "$this$removeAll");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        return CollectionsKt__MutableCollectionsKt.filterInPlace$CollectionsKt__MutableCollectionsKt(iterable, function1, true);
    }

    public static final <T> boolean removeAll(Collection<? super T> collection, Iterable<? extends T> iterable) {
        Intrinsics.checkParameterIsNotNull(collection, "$this$removeAll");
        Intrinsics.checkParameterIsNotNull(iterable, "elements");
        iterable = CollectionsKt.convertToSetForSetOperationWith(iterable, (Iterable)collection);
        return TypeIntrinsics.asMutableCollection(collection).removeAll((Collection<?>)iterable);
    }

    private static final <T> boolean removeAll(Collection<? extends T> collection, Collection<? extends T> collection2) {
        if (collection == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableCollection<T>");
        return TypeIntrinsics.asMutableCollection(collection).removeAll(collection2);
    }

    public static final <T> boolean removeAll(Collection<? super T> collection, Sequence<? extends T> object) {
        Intrinsics.checkParameterIsNotNull(collection, "$this$removeAll");
        Intrinsics.checkParameterIsNotNull(object, "elements");
        object = SequencesKt.toHashSet(object);
        boolean bl = object.isEmpty();
        boolean bl2 = true;
        if (!(bl ^ true)) return false;
        if (!collection.removeAll((Collection<?>)object)) return false;
        return bl2;
    }

    public static final <T> boolean removeAll(Collection<? super T> collection, T[] arrT) {
        Intrinsics.checkParameterIsNotNull(collection, "$this$removeAll");
        Intrinsics.checkParameterIsNotNull(arrT, "elements");
        int n = arrT.length;
        boolean bl = false;
        n = n == 0 ? 1 : 0;
        boolean bl2 = bl;
        if ((n ^ 1) == 0) return bl2;
        bl2 = bl;
        if (!collection.removeAll((Collection)ArraysKt.toHashSet(arrT))) return bl2;
        return true;
    }

    public static final <T> boolean removeAll(List<T> list, Function1<? super T, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(list, "$this$removeAll");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        return CollectionsKt__MutableCollectionsKt.filterInPlace$CollectionsKt__MutableCollectionsKt(list, function1, true);
    }

    public static final <T> T removeFirst(List<T> list) {
        Intrinsics.checkParameterIsNotNull(list, "$this$removeFirst");
        if (list.isEmpty()) throw (Throwable)new NoSuchElementException("List is empty.");
        return list.remove(0);
    }

    public static final <T> T removeFirstOrNull(List<T> list) {
        Intrinsics.checkParameterIsNotNull(list, "$this$removeFirstOrNull");
        if (list.isEmpty()) {
            list = null;
            return (T)list;
        }
        list = list.remove(0);
        return (T)list;
    }

    public static final <T> T removeLast(List<T> list) {
        Intrinsics.checkParameterIsNotNull(list, "$this$removeLast");
        if (list.isEmpty()) throw (Throwable)new NoSuchElementException("List is empty.");
        return list.remove(CollectionsKt.getLastIndex(list));
    }

    public static final <T> T removeLastOrNull(List<T> list) {
        Intrinsics.checkParameterIsNotNull(list, "$this$removeLastOrNull");
        if (list.isEmpty()) {
            list = null;
            return (T)list;
        }
        list = list.remove(CollectionsKt.getLastIndex(list));
        return (T)list;
    }

    public static final <T> boolean retainAll(Iterable<? extends T> iterable, Function1<? super T, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(iterable, "$this$retainAll");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        return CollectionsKt__MutableCollectionsKt.filterInPlace$CollectionsKt__MutableCollectionsKt(iterable, function1, false);
    }

    public static final <T> boolean retainAll(Collection<? super T> collection, Iterable<? extends T> iterable) {
        Intrinsics.checkParameterIsNotNull(collection, "$this$retainAll");
        Intrinsics.checkParameterIsNotNull(iterable, "elements");
        iterable = CollectionsKt.convertToSetForSetOperationWith(iterable, (Iterable)collection);
        return TypeIntrinsics.asMutableCollection(collection).retainAll((Collection<?>)iterable);
    }

    private static final <T> boolean retainAll(Collection<? extends T> collection, Collection<? extends T> collection2) {
        if (collection == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableCollection<T>");
        return TypeIntrinsics.asMutableCollection(collection).retainAll(collection2);
    }

    public static final <T> boolean retainAll(Collection<? super T> collection, Sequence<? extends T> object) {
        Intrinsics.checkParameterIsNotNull(collection, "$this$retainAll");
        Intrinsics.checkParameterIsNotNull(object, "elements");
        object = SequencesKt.toHashSet(object);
        if (!(object.isEmpty() ^ true)) return CollectionsKt__MutableCollectionsKt.retainNothing$CollectionsKt__MutableCollectionsKt(collection);
        return collection.retainAll((Collection<?>)object);
    }

    public static final <T> boolean retainAll(Collection<? super T> collection, T[] arrT) {
        Intrinsics.checkParameterIsNotNull(collection, "$this$retainAll");
        Intrinsics.checkParameterIsNotNull(arrT, "elements");
        boolean bl = arrT.length == 0;
        if (!(bl ^ true)) return CollectionsKt__MutableCollectionsKt.retainNothing$CollectionsKt__MutableCollectionsKt(collection);
        return collection.retainAll((Collection)ArraysKt.toHashSet(arrT));
    }

    public static final <T> boolean retainAll(List<T> list, Function1<? super T, Boolean> function1) {
        Intrinsics.checkParameterIsNotNull(list, "$this$retainAll");
        Intrinsics.checkParameterIsNotNull(function1, "predicate");
        return CollectionsKt__MutableCollectionsKt.filterInPlace$CollectionsKt__MutableCollectionsKt(list, function1, false);
    }

    private static final boolean retainNothing$CollectionsKt__MutableCollectionsKt(Collection<?> collection) {
        boolean bl = collection.isEmpty();
        collection.clear();
        return bl ^ true;
    }

    public static final <T> void shuffle(List<T> list, Random random) {
        Intrinsics.checkParameterIsNotNull(list, "$this$shuffle");
        Intrinsics.checkParameterIsNotNull(random, "random");
        int n = CollectionsKt.getLastIndex(list);
        while (n >= 1) {
            int n2 = random.nextInt(n + 1);
            T t = list.get(n);
            list.set(n, list.get(n2));
            list.set(n2, t);
            --n;
        }
    }

    public static final <T> List<T> shuffled(Iterable<? extends T> iterable, Random random) {
        Intrinsics.checkParameterIsNotNull(iterable, "$this$shuffled");
        Intrinsics.checkParameterIsNotNull(random, "random");
        iterable = CollectionsKt.toMutableList(iterable);
        CollectionsKt.shuffle(iterable, random);
        return iterable;
    }
}

