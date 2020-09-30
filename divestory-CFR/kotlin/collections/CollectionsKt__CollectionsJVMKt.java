/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.collections;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\u00002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0011\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0081\b\u001a\u0011\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0001H\u0081\b\u001a\"\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00062\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\tH\u0081\b\u00a2\u0006\u0002\u0010\n\u001a4\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u0006\"\u0004\b\u0000\u0010\u000b2\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\t2\f\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u0006H\u0081\b\u00a2\u0006\u0002\u0010\r\u001a\u001f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u000f\"\u0004\b\u0000\u0010\u000b2\u0006\u0010\u0010\u001a\u0002H\u000b\u00a2\u0006\u0002\u0010\u0011\u001a1\u0010\u0012\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00070\u0006\"\u0004\b\u0000\u0010\u000b*\n\u0012\u0006\b\u0001\u0012\u0002H\u000b0\u00062\u0006\u0010\u0013\u001a\u00020\u0014H\u0000\u00a2\u0006\u0002\u0010\u0015\u001a\u001f\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u000f\"\u0004\b\u0000\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\u0017H\u0087\b\u00a8\u0006\u0018"}, d2={"checkCountOverflow", "", "count", "checkIndexOverflow", "index", "copyToArrayImpl", "", "", "collection", "", "(Ljava/util/Collection;)[Ljava/lang/Object;", "T", "array", "(Ljava/util/Collection;[Ljava/lang/Object;)[Ljava/lang/Object;", "listOf", "", "element", "(Ljava/lang/Object;)Ljava/util/List;", "copyToArrayOfAny", "isVarargs", "", "([Ljava/lang/Object;Z)[Ljava/lang/Object;", "toList", "Ljava/util/Enumeration;", "kotlin-stdlib"}, k=5, mv={1, 1, 16}, xi=1, xs="kotlin/collections/CollectionsKt")
class CollectionsKt__CollectionsJVMKt {
    private static final int checkCountOverflow(int n) {
        if (n >= 0) return n;
        if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) throw (Throwable)new ArithmeticException("Count overflow has happened.");
        CollectionsKt.throwCountOverflow();
        return n;
    }

    private static final int checkIndexOverflow(int n) {
        if (n >= 0) return n;
        if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) throw (Throwable)new ArithmeticException("Index overflow has happened.");
        CollectionsKt.throwIndexOverflow();
        return n;
    }

    private static final Object[] copyToArrayImpl(Collection<?> collection) {
        return CollectionToArray.toArray(collection);
    }

    private static final <T> T[] copyToArrayImpl(Collection<?> arrobject, T[] arrT) {
        if (arrT == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
        if ((arrobject = CollectionToArray.toArray(arrobject, arrT)) == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        return arrobject;
    }

    public static final <T> Object[] copyToArrayOfAny(T[] arrT, boolean bl) {
        Intrinsics.checkParameterIsNotNull(arrT, "$this$copyToArrayOfAny");
        if (bl && Intrinsics.areEqual(arrT.getClass(), Object[].class)) {
            return arrT;
        }
        arrT = Arrays.copyOf(arrT, arrT.length, Object[].class);
        Intrinsics.checkExpressionValueIsNotNull(arrT, "java.util.Arrays.copyOf(\u2026 Array<Any?>::class.java)");
        return arrT;
    }

    public static final <T> List<T> listOf(T object) {
        object = Collections.singletonList(object);
        Intrinsics.checkExpressionValueIsNotNull(object, "java.util.Collections.singletonList(element)");
        return object;
    }

    private static final <T> List<T> toList(Enumeration<T> object) {
        object = Collections.list(object);
        Intrinsics.checkExpressionValueIsNotNull(object, "java.util.Collections.list(this)");
        return (List)object;
    }
}
