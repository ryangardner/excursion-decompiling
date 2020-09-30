/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  kotlin.collections.CollectionsKt__CollectionsKt$binarySearchBy
 */
package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArrayAsCollection;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;

@Metadata(bv={1, 0, 3}, d1={"\u0000|\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000f\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a@\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u00072\u0006\u0010\f\u001a\u00020\u00062!\u0010\r\u001a\u001d\u0012\u0013\u0012\u00110\u0006\u00a2\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u0002H\u00070\u000eH\u0087\b\u001a@\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0013\"\u0004\b\u0000\u0010\u00072\u0006\u0010\f\u001a\u00020\u00062!\u0010\r\u001a\u001d\u0012\u0013\u0012\u00110\u0006\u00a2\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u0002H\u00070\u000eH\u0087\b\u001a\u001f\u0010\u0014\u001a\u0012\u0012\u0004\u0012\u0002H\u00070\u0015j\b\u0012\u0004\u0012\u0002H\u0007`\u0016\"\u0004\b\u0000\u0010\u0007H\u0087\b\u001a5\u0010\u0014\u001a\u0012\u0012\u0004\u0012\u0002H\u00070\u0015j\b\u0012\u0004\u0012\u0002H\u0007`\u0016\"\u0004\b\u0000\u0010\u00072\u0012\u0010\u0017\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00070\u0018\"\u0002H\u0007\u00a2\u0006\u0002\u0010\u0019\u001aK\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u001b0\b\"\u0004\b\u0000\u0010\u001b2\u0006\u0010\u001c\u001a\u00020\u00062\u001f\b\u0001\u0010\u001d\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u001b0\u0013\u0012\u0004\u0012\u00020\u001e0\u000e\u00a2\u0006\u0002\b\u001fH\u0087\b\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001\u001aC\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u001b0\b\"\u0004\b\u0000\u0010\u001b2\u001f\b\u0001\u0010\u001d\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u001b0\u0013\u0012\u0004\u0012\u00020\u001e0\u000e\u00a2\u0006\u0002\b\u001fH\u0087\b\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a\u0012\u0010 \u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u0007\u001a\u0015\u0010!\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u0007H\u0087\b\u001a+\u0010!\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u00072\u0012\u0010\u0017\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00070\u0018\"\u0002H\u0007\u00a2\u0006\u0002\u0010\"\u001a%\u0010#\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\b\b\u0000\u0010\u0007*\u00020$2\b\u0010%\u001a\u0004\u0018\u0001H\u0007\u00a2\u0006\u0002\u0010&\u001a3\u0010#\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\b\b\u0000\u0010\u0007*\u00020$2\u0016\u0010\u0017\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u0001H\u00070\u0018\"\u0004\u0018\u0001H\u0007\u00a2\u0006\u0002\u0010\"\u001a\u0015\u0010'\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0013\"\u0004\b\u0000\u0010\u0007H\u0087\b\u001a+\u0010'\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0013\"\u0004\b\u0000\u0010\u00072\u0012\u0010\u0017\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00070\u0018\"\u0002H\u0007\u00a2\u0006\u0002\u0010\"\u001a%\u0010(\u001a\u00020\u001e2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010)\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\u0006H\u0002\u00a2\u0006\u0002\b+\u001a\b\u0010,\u001a\u00020\u001eH\u0001\u001a\b\u0010-\u001a\u00020\u001eH\u0001\u001a%\u0010.\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0002\"\u0004\b\u0000\u0010\u0007*\n\u0012\u0006\b\u0001\u0012\u0002H\u00070\u0018H\u0000\u00a2\u0006\u0002\u0010/\u001aS\u00100\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\b2\u0006\u0010%\u001a\u0002H\u00072\u001a\u00101\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u000702j\n\u0012\u0006\b\u0000\u0012\u0002H\u0007`32\b\b\u0002\u0010)\u001a\u00020\u00062\b\b\u0002\u0010*\u001a\u00020\u0006\u00a2\u0006\u0002\u00104\u001a>\u00100\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\b2\b\b\u0002\u0010)\u001a\u00020\u00062\b\b\u0002\u0010*\u001a\u00020\u00062\u0012\u00105\u001a\u000e\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u00020\u00060\u000e\u001aE\u00100\u001a\u00020\u0006\"\u000e\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u000706*\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00070\b2\b\u0010%\u001a\u0004\u0018\u0001H\u00072\b\b\u0002\u0010)\u001a\u00020\u00062\b\b\u0002\u0010*\u001a\u00020\u0006\u00a2\u0006\u0002\u00107\u001ad\u00108\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u0007\"\u000e\b\u0001\u00109*\b\u0012\u0004\u0012\u0002H906*\b\u0012\u0004\u0012\u0002H\u00070\b2\b\u0010:\u001a\u0004\u0018\u0001H92\b\b\u0002\u0010)\u001a\u00020\u00062\b\b\u0002\u0010*\u001a\u00020\u00062\u0016\b\u0004\u0010;\u001a\u0010\u0012\u0004\u0012\u0002H\u0007\u0012\u0006\u0012\u0004\u0018\u0001H90\u000eH\u0086\b\u00a2\u0006\u0002\u0010<\u001a,\u0010=\u001a\u00020>\"\t\b\u0000\u0010\u0007\u00a2\u0006\u0002\b?*\b\u0012\u0004\u0012\u0002H\u00070\u00022\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0002H\u0087\b\u001a8\u0010@\u001a\u0002HA\"\u0010\b\u0000\u0010B*\u0006\u0012\u0002\b\u00030\u0002*\u0002HA\"\u0004\b\u0001\u0010A*\u0002HB2\f\u0010C\u001a\b\u0012\u0004\u0012\u0002HA0DH\u0087\b\u00a2\u0006\u0002\u0010E\u001a\u0019\u0010F\u001a\u00020>\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u0002H\u0087\b\u001a,\u0010G\u001a\u00020>\"\u0004\b\u0000\u0010\u0007*\n\u0012\u0004\u0012\u0002H\u0007\u0018\u00010\u0002H\u0087\b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000\u001a\u001e\u0010H\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\bH\u0000\u001a!\u0010I\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0002\"\u0004\b\u0000\u0010\u0007*\n\u0012\u0004\u0012\u0002H\u0007\u0018\u00010\u0002H\u0087\b\u001a!\u0010I\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u0007*\n\u0012\u0004\u0012\u0002H\u0007\u0018\u00010\bH\u0087\b\"\u0019\u0010\u0000\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"!\u0010\u0005\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\b8F\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\n\u00a8\u0006J"}, d2={"indices", "Lkotlin/ranges/IntRange;", "", "getIndices", "(Ljava/util/Collection;)Lkotlin/ranges/IntRange;", "lastIndex", "", "T", "", "getLastIndex", "(Ljava/util/List;)I", "List", "size", "init", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "index", "MutableList", "", "arrayListOf", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "elements", "", "([Ljava/lang/Object;)Ljava/util/ArrayList;", "buildList", "E", "capacity", "builderAction", "", "Lkotlin/ExtensionFunctionType;", "emptyList", "listOf", "([Ljava/lang/Object;)Ljava/util/List;", "listOfNotNull", "", "element", "(Ljava/lang/Object;)Ljava/util/List;", "mutableListOf", "rangeCheck", "fromIndex", "toIndex", "rangeCheck$CollectionsKt__CollectionsKt", "throwCountOverflow", "throwIndexOverflow", "asCollection", "([Ljava/lang/Object;)Ljava/util/Collection;", "binarySearch", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/util/List;Ljava/lang/Object;Ljava/util/Comparator;II)I", "comparison", "", "(Ljava/util/List;Ljava/lang/Comparable;II)I", "binarySearchBy", "K", "key", "selector", "(Ljava/util/List;Ljava/lang/Comparable;IILkotlin/jvm/functions/Function1;)I", "containsAll", "", "Lkotlin/internal/OnlyInputTypes;", "ifEmpty", "R", "C", "defaultValue", "Lkotlin/Function0;", "(Ljava/util/Collection;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isNotEmpty", "isNullOrEmpty", "optimizeReadOnlyList", "orEmpty", "kotlin-stdlib"}, k=5, mv={1, 1, 16}, xi=1, xs="kotlin/collections/CollectionsKt")
class CollectionsKt__CollectionsKt
extends CollectionsKt__CollectionsJVMKt {
    private static final <T> List<T> List(int n, Function1<? super Integer, ? extends T> function1) {
        ArrayList<T> arrayList = new ArrayList<T>(n);
        int n2 = 0;
        while (n2 < n) {
            arrayList.add(function1.invoke((Integer)n2));
            ++n2;
        }
        return arrayList;
    }

    private static final <T> List<T> MutableList(int n, Function1<? super Integer, ? extends T> function1) {
        ArrayList<T> arrayList = new ArrayList<T>(n);
        int n2 = 0;
        while (n2 < n) {
            arrayList.add(function1.invoke((Integer)n2));
            ++n2;
        }
        return arrayList;
    }

    private static final <T> ArrayList<T> arrayListOf() {
        return new ArrayList();
    }

    public static final <T> ArrayList<T> arrayListOf(T ... object) {
        Intrinsics.checkParameterIsNotNull(object, "elements");
        if (((T[])object).length != 0) return new ArrayList((Collection)new ArrayAsCollection<T>((T[])object, true));
        return new ArrayList();
    }

    public static final <T> Collection<T> asCollection(T[] arrT) {
        Intrinsics.checkParameterIsNotNull(arrT, "$this$asCollection");
        return new ArrayAsCollection<T>(arrT, false);
    }

    public static final <T> int binarySearch(List<? extends T> list, int n, int n2, Function1<? super T, Integer> function1) {
        Intrinsics.checkParameterIsNotNull(list, "$this$binarySearch");
        Intrinsics.checkParameterIsNotNull(function1, "comparison");
        CollectionsKt__CollectionsKt.rangeCheck$CollectionsKt__CollectionsKt(list.size(), n, n2);
        --n2;
        while (n <= n2) {
            int n3 = n + n2 >>> 1;
            int n4 = ((Number)function1.invoke(list.get(n3))).intValue();
            if (n4 < 0) {
                n = n3 + 1;
                continue;
            }
            if (n4 <= 0) return n3;
            n2 = n3 - 1;
        }
        return -(n + 1);
    }

    public static final <T extends Comparable<? super T>> int binarySearch(List<? extends T> list, T t, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(list, "$this$binarySearch");
        CollectionsKt__CollectionsKt.rangeCheck$CollectionsKt__CollectionsKt(list.size(), n, n2);
        --n2;
        while (n <= n2) {
            int n3 = n + n2 >>> 1;
            int n4 = ComparisonsKt.compareValues((Comparable)list.get(n3), t);
            if (n4 < 0) {
                n = n3 + 1;
                continue;
            }
            if (n4 <= 0) return n3;
            n2 = n3 - 1;
        }
        return -(n + 1);
    }

    public static final <T> int binarySearch(List<? extends T> list, T t, Comparator<? super T> comparator, int n, int n2) {
        Intrinsics.checkParameterIsNotNull(list, "$this$binarySearch");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        CollectionsKt__CollectionsKt.rangeCheck$CollectionsKt__CollectionsKt(list.size(), n, n2);
        --n2;
        while (n <= n2) {
            int n3 = n + n2 >>> 1;
            int n4 = comparator.compare(list.get(n3), t);
            if (n4 < 0) {
                n = n3 + 1;
                continue;
            }
            if (n4 <= 0) return n3;
            n2 = n3 - 1;
        }
        return -(n + 1);
    }

    public static /* synthetic */ int binarySearch$default(List list, int n, int n2, Function1 function1, int n3, Object object) {
        if ((n3 & 1) != 0) {
            n = 0;
        }
        if ((n3 & 2) == 0) return CollectionsKt.binarySearch(list, n, n2, function1);
        n2 = list.size();
        return CollectionsKt.binarySearch(list, n, n2, function1);
    }

    public static /* synthetic */ int binarySearch$default(List list, Comparable comparable, int n, int n2, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) == 0) return CollectionsKt.binarySearch(list, comparable, n, n2);
        n2 = list.size();
        return CollectionsKt.binarySearch(list, comparable, n, n2);
    }

    public static /* synthetic */ int binarySearch$default(List list, Object object, Comparator comparator, int n, int n2, int n3, Object object2) {
        if ((n3 & 4) != 0) {
            n = 0;
        }
        if ((n3 & 8) == 0) return CollectionsKt.binarySearch(list, object, comparator, n, n2);
        n2 = list.size();
        return CollectionsKt.binarySearch(list, object, comparator, n, n2);
    }

    public static final <T, K extends Comparable<? super K>> int binarySearchBy(List<? extends T> list, K k, int n, int n2, Function1<? super T, ? extends K> function1) {
        Intrinsics.checkParameterIsNotNull(list, "$this$binarySearchBy");
        Intrinsics.checkParameterIsNotNull(function1, "selector");
        return CollectionsKt.binarySearch(list, n, n2, new Function1<T, Integer>(function1, k){
            final /* synthetic */ Comparable $key;
            final /* synthetic */ Function1 $selector;
            {
                this.$selector = function1;
                this.$key = comparable;
                super(1);
            }

            public final int invoke(T t) {
                return ComparisonsKt.compareValues((Comparable)this.$selector.invoke(t), this.$key);
            }
        });
    }

    public static /* synthetic */ int binarySearchBy$default(List list, Comparable comparable, int n, int n2, Function1 function1, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) != 0) {
            n2 = list.size();
        }
        Intrinsics.checkParameterIsNotNull(list, "$this$binarySearchBy");
        Intrinsics.checkParameterIsNotNull(function1, "selector");
        return CollectionsKt.binarySearch(list, n, n2, new /* invalid duplicate definition of identical inner class */);
    }

    private static final <E> List<E> buildList(int n, Function1<? super List<E>, Unit> function1) {
        ArrayList arrayList = new ArrayList(n);
        function1.invoke(arrayList);
        return arrayList;
    }

    private static final <E> List<E> buildList(Function1<? super List<E>, Unit> function1) {
        ArrayList arrayList = new ArrayList();
        function1.invoke(arrayList);
        return arrayList;
    }

    private static final <T> boolean containsAll(Collection<? extends T> collection, Collection<? extends T> collection2) {
        return collection.containsAll(collection2);
    }

    public static final <T> List<T> emptyList() {
        return EmptyList.INSTANCE;
    }

    public static final IntRange getIndices(Collection<?> collection) {
        Intrinsics.checkParameterIsNotNull(collection, "$this$indices");
        return new IntRange(0, collection.size() - 1);
    }

    public static final <T> int getLastIndex(List<? extends T> list) {
        Intrinsics.checkParameterIsNotNull(list, "$this$lastIndex");
        return list.size() - 1;
    }

    private static final <C extends Collection<?> & R, R> R ifEmpty(C c, Function0<? extends R> function0) {
        Object object = c;
        if (!c.isEmpty()) return object;
        object = function0.invoke();
        return object;
    }

    private static final <T> boolean isNotEmpty(Collection<? extends T> collection) {
        return collection.isEmpty() ^ true;
    }

    private static final <T> boolean isNullOrEmpty(Collection<? extends T> collection) {
        if (collection == null) return true;
        if (collection.isEmpty()) return true;
        return false;
    }

    private static final <T> List<T> listOf() {
        return CollectionsKt.emptyList();
    }

    public static final <T> List<T> listOf(T ... object) {
        Intrinsics.checkParameterIsNotNull(object, "elements");
        if (((T[])object).length <= 0) return CollectionsKt.emptyList();
        return ArraysKt.asList(object);
    }

    public static final <T> List<T> listOfNotNull(T object) {
        if (object == null) return CollectionsKt.emptyList();
        return CollectionsKt.listOf(object);
    }

    public static final <T> List<T> listOfNotNull(T ... arrT) {
        Intrinsics.checkParameterIsNotNull(arrT, "elements");
        return ArraysKt.filterNotNull(arrT);
    }

    private static final <T> List<T> mutableListOf() {
        return new ArrayList();
    }

    public static final <T> List<T> mutableListOf(T ... object) {
        Intrinsics.checkParameterIsNotNull(object, "elements");
        if (((T[])object).length != 0) return new ArrayList((Collection)new ArrayAsCollection<T>((T[])object, true));
        return new ArrayList();
    }

    public static final <T> List<T> optimizeReadOnlyList(List<? extends T> list) {
        Intrinsics.checkParameterIsNotNull(list, "$this$optimizeReadOnlyList");
        int n = list.size();
        if (n == 0) {
            return CollectionsKt.emptyList();
        }
        if (n == 1) return CollectionsKt.listOf(list.get(0));
        return list;
    }

    private static final <T> Collection<T> orEmpty(Collection<? extends T> collection) {
        if (collection == null) return CollectionsKt.emptyList();
        return collection;
    }

    private static final <T> List<T> orEmpty(List<? extends T> list) {
        if (list == null) return CollectionsKt.emptyList();
        return list;
    }

    private static final void rangeCheck$CollectionsKt__CollectionsKt(int n, int n2, int n3) {
        if (n2 > n3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("fromIndex (");
            stringBuilder.append(n2);
            stringBuilder.append(") is greater than toIndex (");
            stringBuilder.append(n3);
            stringBuilder.append(").");
            throw (Throwable)new IllegalArgumentException(stringBuilder.toString());
        }
        if (n2 < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("fromIndex (");
            stringBuilder.append(n2);
            stringBuilder.append(") is less than zero.");
            throw (Throwable)new IndexOutOfBoundsException(stringBuilder.toString());
        }
        if (n3 <= n) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("toIndex (");
        stringBuilder.append(n3);
        stringBuilder.append(") is greater than size (");
        stringBuilder.append(n);
        stringBuilder.append(").");
        throw (Throwable)new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public static final void throwCountOverflow() {
        throw (Throwable)new ArithmeticException("Count overflow has happened.");
    }

    public static final void throwIndexOverflow() {
        throw (Throwable)new ArithmeticException("Index overflow has happened.");
    }
}

