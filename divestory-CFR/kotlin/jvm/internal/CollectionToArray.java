/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.jvm.internal;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\u00002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a#\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\bH\u0007\u00a2\u0006\u0004\b\t\u0010\n\u001a5\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\u0010\u0010\u000b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u0001H\u0007\u00a2\u0006\u0004\b\t\u0010\f\u001a~\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\u0014\u0010\u000e\u001a\u0010\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00010\u000f2\u001a\u0010\u0010\u001a\u0016\u0012\u0004\u0012\u00020\u0005\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00010\u00112(\u0010\u0012\u001a$\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001\u0012\u0004\u0012\u00020\u0005\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00010\u0013H\u0082\b\u00a2\u0006\u0002\u0010\u0014\"\u0018\u0010\u0000\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0003\"\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2={"EMPTY", "", "", "[Ljava/lang/Object;", "MAX_SIZE", "", "collectionToArray", "collection", "", "toArray", "(Ljava/util/Collection;)[Ljava/lang/Object;", "a", "(Ljava/util/Collection;[Ljava/lang/Object;)[Ljava/lang/Object;", "toArrayImpl", "empty", "Lkotlin/Function0;", "alloc", "Lkotlin/Function1;", "trim", "Lkotlin/Function2;", "(Ljava/util/Collection;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;)[Ljava/lang/Object;", "kotlin-stdlib"}, k=2, mv={1, 1, 16})
public final class CollectionToArray {
    private static final Object[] EMPTY = new Object[0];
    private static final int MAX_SIZE = 2147483645;

    public static final Object[] toArray(Collection<?> arrobject) {
        Intrinsics.checkParameterIsNotNull(arrobject, "collection");
        int n = arrobject.size();
        if (n == 0) return EMPTY;
        Iterator iterator2 = arrobject.iterator();
        if (!iterator2.hasNext()) {
            return EMPTY;
        }
        arrobject = new Object[n];
        n = 0;
        do {
            Object[] arrobject2;
            int n2 = n + 1;
            arrobject[n] = iterator2.next();
            if (n2 >= arrobject.length) {
                int n3;
                if (!iterator2.hasNext()) {
                    return arrobject;
                }
                n = n3 = n2 * 3 + 1 >>> 1;
                if (n3 <= n2) {
                    if (n2 >= 2147483645) throw (Throwable)new OutOfMemoryError();
                    n = 2147483645;
                }
                arrobject2 = Arrays.copyOf(arrobject, n);
                Intrinsics.checkExpressionValueIsNotNull(arrobject2, "Arrays.copyOf(result, newSize)");
            } else {
                arrobject2 = arrobject;
                if (!iterator2.hasNext()) {
                    arrobject = Arrays.copyOf(arrobject, n2);
                    Intrinsics.checkExpressionValueIsNotNull(arrobject, "Arrays.copyOf(result, size)");
                    return arrobject;
                }
            }
            n = n2;
            arrobject = arrobject2;
        } while (true);
    }

    public static final Object[] toArray(Collection<?> object, Object[] arrobject) {
        Intrinsics.checkParameterIsNotNull(object, "collection");
        if (arrobject == null) throw (Throwable)new NullPointerException();
        int n = object.size();
        int n2 = 0;
        if (n == 0) {
            object = arrobject;
            if (arrobject.length <= 0) return object;
            arrobject[0] = null;
            return arrobject;
        }
        Iterator iterator2 = object.iterator();
        if (!iterator2.hasNext()) {
            object = arrobject;
            if (arrobject.length <= 0) return object;
            arrobject[0] = null;
            return arrobject;
        }
        if (n <= arrobject.length) {
            object = arrobject;
        } else {
            object = Array.newInstance(arrobject.getClass().getComponentType(), n);
            if (object == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
        }
        do {
            Object object2;
            n = n2 + 1;
            object[n2] = iterator2.next();
            if (n >= ((Object[])object).length) {
                int n3;
                if (!iterator2.hasNext()) {
                    return object;
                }
                n2 = n3 = n * 3 + 1 >>> 1;
                if (n3 <= n) {
                    if (n >= 2147483645) throw (Throwable)new OutOfMemoryError();
                    n2 = 2147483645;
                }
                object2 = Arrays.copyOf(object, n2);
                Intrinsics.checkExpressionValueIsNotNull(object2, "Arrays.copyOf(result, newSize)");
            } else {
                object2 = object;
                if (!iterator2.hasNext()) {
                    if (object == arrobject) {
                        arrobject[n] = null;
                        return arrobject;
                    }
                    object = Arrays.copyOf(object, n);
                    Intrinsics.checkExpressionValueIsNotNull(object, "Arrays.copyOf(result, size)");
                    return object;
                }
            }
            n2 = n;
            object = object2;
        } while (true);
    }

    private static final Object[] toArrayImpl(Collection<?> arrobject, Function0<Object[]> arrobject2, Function1<? super Integer, Object[]> function1, Function2<? super Object[], ? super Integer, Object[]> function2) {
        int n = arrobject.size();
        if (n == 0) {
            return arrobject2.invoke();
        }
        Iterator<?> iterator2 = arrobject.iterator();
        if (!iterator2.hasNext()) {
            return arrobject2.invoke();
        }
        arrobject = function1.invoke((Integer)n);
        n = 0;
        do {
            int n2 = n + 1;
            arrobject[n] = iterator2.next();
            if (n2 >= arrobject.length) {
                int n3;
                if (!iterator2.hasNext()) {
                    return arrobject;
                }
                n = n3 = n2 * 3 + 1 >>> 1;
                if (n3 <= n2) {
                    if (n2 >= 2147483645) throw (Throwable)new OutOfMemoryError();
                    n = 2147483645;
                }
                arrobject2 = Arrays.copyOf(arrobject, n);
                Intrinsics.checkExpressionValueIsNotNull(arrobject2, "Arrays.copyOf(result, newSize)");
            } else {
                arrobject2 = arrobject;
                if (!iterator2.hasNext()) {
                    return function2.invoke((Object[])arrobject, (Integer)n2);
                }
            }
            n = n2;
            arrobject = arrobject2;
        } while (true);
    }
}

