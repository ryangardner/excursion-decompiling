/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.AbstractCoroutineContextKey;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\u0000\u0018\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a+\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u00020\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0004H\u0007\u00a2\u0006\u0002\u0010\u0005\u001a\u0018\u0010\u0006\u001a\u00020\u0007*\u00020\u00022\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004H\u0007\u00a8\u0006\b"}, d2={"getPolymorphicElement", "E", "Lkotlin/coroutines/CoroutineContext$Element;", "key", "Lkotlin/coroutines/CoroutineContext$Key;", "(Lkotlin/coroutines/CoroutineContext$Element;Lkotlin/coroutines/CoroutineContext$Key;)Lkotlin/coroutines/CoroutineContext$Element;", "minusPolymorphicKey", "Lkotlin/coroutines/CoroutineContext;", "kotlin-stdlib"}, k=2, mv={1, 1, 16})
public final class CoroutineContextImplKt {
    public static final <E extends CoroutineContext.Element> E getPolymorphicElement(CoroutineContext.Element element, CoroutineContext.Key<E> key) {
        Intrinsics.checkParameterIsNotNull(element, "$this$getPolymorphicElement");
        Intrinsics.checkParameterIsNotNull(key, "key");
        boolean bl = key instanceof AbstractCoroutineContextKey;
        Object var3_3 = null;
        if (bl) {
            AbstractCoroutineContextKey abstractCoroutineContextKey = (AbstractCoroutineContextKey)key;
            key = var3_3;
            if (!abstractCoroutineContextKey.isSubKey$kotlin_stdlib(element.getKey())) return (E)key;
            key = abstractCoroutineContextKey.tryCast$kotlin_stdlib(element);
            if (key instanceof CoroutineContext.Element) return (E)key;
            key = var3_3;
            return (E)key;
        }
        if (element.getKey() == key) {
            return (E)element;
        }
        element = null;
        return (E)element;
    }

    public static final CoroutineContext minusPolymorphicKey(CoroutineContext.Element element, CoroutineContext.Key<?> object) {
        Intrinsics.checkParameterIsNotNull(element, "$this$minusPolymorphicKey");
        Intrinsics.checkParameterIsNotNull(object, "key");
        if (object instanceof AbstractCoroutineContextKey) {
            AbstractCoroutineContextKey abstractCoroutineContextKey = (AbstractCoroutineContextKey)object;
            object = element;
            if (!abstractCoroutineContextKey.isSubKey$kotlin_stdlib(element.getKey())) return (CoroutineContext)object;
            object = element;
            if (abstractCoroutineContextKey.tryCast$kotlin_stdlib(element) == null) return (CoroutineContext)object;
            object = EmptyCoroutineContext.INSTANCE;
            return (CoroutineContext)object;
        }
        CoroutineContext coroutineContext = element;
        if (element.getKey() != object) return coroutineContext;
        coroutineContext = EmptyCoroutineContext.INSTANCE;
        return coroutineContext;
    }
}

