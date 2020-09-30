/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  kotlin.collections.AbstractCollection$toString
 */
package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.AbstractCollection;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(bv={1, 0, 3}, d1={"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010(\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\b'\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0003J\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00028\u0000H\u0096\u0002\u00a2\u0006\u0002\u0010\u000bJ\u0016\u0010\f\u001a\u00020\t2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002H\u0016J\b\u0010\u000e\u001a\u00020\tH\u0016J\u000f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u0010H\u00a6\u0002J\u0015\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00130\u0012H\u0015\u00a2\u0006\u0002\u0010\u0014J'\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00150\u0012\"\u0004\b\u0001\u0010\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\u00150\u0012H\u0014\u00a2\u0006\u0002\u0010\u0017J\b\u0010\u0018\u001a\u00020\u0019H\u0016R\u0012\u0010\u0004\u001a\u00020\u0005X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u001a"}, d2={"Lkotlin/collections/AbstractCollection;", "E", "", "()V", "size", "", "getSize", "()I", "contains", "", "element", "(Ljava/lang/Object;)Z", "containsAll", "elements", "isEmpty", "iterator", "", "toArray", "", "", "()[Ljava/lang/Object;", "T", "array", "([Ljava/lang/Object;)[Ljava/lang/Object;", "toString", "", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public abstract class AbstractCollection<E>
implements Collection<E>,
KMappedMarker {
    protected AbstractCollection() {
    }

    @Override
    public boolean add(E e) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public boolean contains(Object object) {
        boolean bl = this instanceof Collection;
        boolean bl2 = false;
        if (bl && ((Collection)this).isEmpty()) {
            return bl2;
        }
        Iterator iterator2 = this.iterator();
        do {
            bl = bl2;
            if (!iterator2.hasNext()) return bl;
        } while (!Intrinsics.areEqual(iterator2.next(), object));
        return true;
    }

    @Override
    public boolean containsAll(Collection<? extends Object> object) {
        Intrinsics.checkParameterIsNotNull(object, "elements");
        object = (Iterable)object;
        boolean bl = object.isEmpty();
        boolean bl2 = true;
        if (bl) {
            return bl2;
        }
        object = object.iterator();
        do {
            bl = bl2;
            if (!object.hasNext()) return bl;
        } while (this.contains(object.next()));
        return false;
    }

    public abstract int getSize();

    @Override
    public boolean isEmpty() {
        if (this.size() != 0) return false;
        return true;
    }

    @Override
    public abstract Iterator<E> iterator();

    @Override
    public boolean remove(Object object) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public boolean removeAll(Collection<? extends Object> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public boolean retainAll(Collection<? extends Object> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public Object[] toArray() {
        return CollectionToArray.toArray(this);
    }

    @Override
    public <T> T[] toArray(T[] arrobject) {
        Intrinsics.checkParameterIsNotNull(arrobject, "array");
        arrobject = CollectionToArray.toArray(this, arrobject);
        if (arrobject == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        return arrobject;
    }

    public String toString() {
        return CollectionsKt.joinToString$default(this, ", ", "[", "]", 0, null, new Function1<E, CharSequence>(this){
            final /* synthetic */ AbstractCollection this$0;
            {
                this.this$0 = abstractCollection;
                super(1);
            }

            public final CharSequence invoke(E object) {
                if (object == this.this$0) {
                    object = "(this Collection)";
                    return (CharSequence)object;
                }
                object = String.valueOf(object);
                return (CharSequence)object;
            }
        }, 24, null);
    }
}

