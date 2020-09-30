/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  kotlin.coroutines.experimental.CombinedContext$toString
 */
package kotlin.coroutines.experimental;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.coroutines.experimental.CombinedContext;
import kotlin.coroutines.experimental.CoroutineContext;
import kotlin.coroutines.experimental.EmptyCoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u0004H\u0002J\u0010\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u0000H\u0002J\u0013\u0010\u000e\u001a\u00020\u000b2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J5\u0010\u0011\u001a\u0002H\u0012\"\u0004\b\u0000\u0010\u00122\u0006\u0010\u0013\u001a\u0002H\u00122\u0018\u0010\u0014\u001a\u0014\u0012\u0004\u0012\u0002H\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u0002H\u00120\u0015H\u0016\u00a2\u0006\u0002\u0010\u0016J(\u0010\u0017\u001a\u0004\u0018\u0001H\u0018\"\b\b\u0000\u0010\u0018*\u00020\u00042\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00180\u001aH\u0096\u0002\u00a2\u0006\u0002\u0010\u001bJ\b\u0010\u001c\u001a\u00020\u001dH\u0016J\u0014\u0010\u001e\u001a\u00020\u00012\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\u001aH\u0016J\b\u0010\u001f\u001a\u00020\u001dH\u0002J\b\u0010 \u001a\u00020!H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u00a8\u0006\""}, d2={"Lkotlin/coroutines/experimental/CombinedContext;", "Lkotlin/coroutines/experimental/CoroutineContext;", "left", "element", "Lkotlin/coroutines/experimental/CoroutineContext$Element;", "(Lkotlin/coroutines/experimental/CoroutineContext;Lkotlin/coroutines/experimental/CoroutineContext$Element;)V", "getElement", "()Lkotlin/coroutines/experimental/CoroutineContext$Element;", "getLeft", "()Lkotlin/coroutines/experimental/CoroutineContext;", "contains", "", "containsAll", "context", "equals", "other", "", "fold", "R", "initial", "operation", "Lkotlin/Function2;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "get", "E", "key", "Lkotlin/coroutines/experimental/CoroutineContext$Key;", "(Lkotlin/coroutines/experimental/CoroutineContext$Key;)Lkotlin/coroutines/experimental/CoroutineContext$Element;", "hashCode", "", "minusKey", "size", "toString", "", "kotlin-stdlib-coroutines"}, k=1, mv={1, 1, 16})
public final class CombinedContext
implements CoroutineContext {
    private final CoroutineContext.Element element;
    private final CoroutineContext left;

    public CombinedContext(CoroutineContext coroutineContext, CoroutineContext.Element element) {
        Intrinsics.checkParameterIsNotNull(coroutineContext, "left");
        Intrinsics.checkParameterIsNotNull(element, "element");
        this.left = coroutineContext;
        this.element = element;
    }

    private final boolean contains(CoroutineContext.Element element) {
        return Intrinsics.areEqual(this.get(element.getKey()), element);
    }

    private final boolean containsAll(CombinedContext coroutineContext) {
        do {
            if (this.contains(coroutineContext.element)) continue;
            return false;
        } while ((coroutineContext = coroutineContext.left) instanceof CombinedContext);
        if (coroutineContext == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.experimental.CoroutineContext.Element");
        return this.contains((CoroutineContext.Element)coroutineContext);
    }

    private final int size() {
        CoroutineContext coroutineContext = this.left;
        if (!(coroutineContext instanceof CombinedContext)) return 2;
        return ((CombinedContext)coroutineContext).size() + 1;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof CombinedContext)) return false;
        if (CombinedContext.super.size() != this.size()) return false;
        if (!CombinedContext.super.containsAll(this)) return false;
        return true;
    }

    @Override
    public <R> R fold(R r, Function2<? super R, ? super CoroutineContext.Element, ? extends R> function2) {
        Intrinsics.checkParameterIsNotNull(function2, "operation");
        return function2.invoke(this.left.fold(r, function2), this.element);
    }

    @Override
    public <E extends CoroutineContext.Element> E get(CoroutineContext.Key<E> key) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        CoroutineContext coroutineContext = this;
        E e;
        while ((e = ((CombinedContext)coroutineContext).element.get(key)) == null) {
            coroutineContext = ((CombinedContext)coroutineContext).left;
            if (!(coroutineContext instanceof CombinedContext)) return coroutineContext.get(key);
        }
        return e;
    }

    public final CoroutineContext.Element getElement() {
        return this.element;
    }

    public final CoroutineContext getLeft() {
        return this.left;
    }

    public int hashCode() {
        return this.left.hashCode() + this.element.hashCode();
    }

    @Override
    public CoroutineContext minusKey(CoroutineContext.Key<?> object) {
        Intrinsics.checkParameterIsNotNull(object, "key");
        if (this.element.get(object) != null) {
            return this.left;
        }
        if ((object = this.left.minusKey((CoroutineContext.Key<?>)object)) == this.left) {
            return this;
        }
        if (object != EmptyCoroutineContext.INSTANCE) return new CombinedContext((CoroutineContext)object, this.element);
        return this.element;
    }

    @Override
    public CoroutineContext plus(CoroutineContext coroutineContext) {
        Intrinsics.checkParameterIsNotNull(coroutineContext, "context");
        return CoroutineContext.DefaultImpls.plus(this, coroutineContext);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(this.fold("", toString.1.INSTANCE));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

