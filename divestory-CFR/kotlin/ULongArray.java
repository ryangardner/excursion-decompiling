/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin;

import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.ULong;
import kotlin.collections.ArraysKt;
import kotlin.collections.ULongIterator;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(bv={1, 0, 3}, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0016\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u0000\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0087@\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001-B\u0014\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0005\u0010\u0006B\u0014\b\u0001\u0012\u0006\u0010\u0007\u001a\u00020\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0005\u0010\tJ\u001b\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0002H\u0096\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0011\u0010\u0012J \u0010\u0013\u001a\u00020\u000f2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u0016\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0013\u0010\u0017\u001a\u00020\u000f2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u00d6\u0003J\u001b\u0010\u001a\u001a\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0004H\u0086\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\t\u0010\u001e\u001a\u00020\u0004H\u00d6\u0001J\u000f\u0010\u001f\u001a\u00020\u000fH\u0016\u00a2\u0006\u0004\b \u0010!J\u0010\u0010\"\u001a\u00020#H\u0096\u0002\u00a2\u0006\u0004\b$\u0010%J#\u0010&\u001a\u00020'2\u0006\u0010\u001b\u001a\u00020\u00042\u0006\u0010(\u001a\u00020\u0002H\u0086\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b)\u0010*J\t\u0010+\u001a\u00020,H\u00d6\u0001R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\u0007\u001a\u00020\b8\u0000X\u0081\u0004\u00a2\u0006\b\n\u0000\u0012\u0004\b\f\u0010\r\u00f8\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006."}, d2={"Lkotlin/ULongArray;", "", "Lkotlin/ULong;", "size", "", "constructor-impl", "(I)[J", "storage", "", "([J)[J", "getSize-impl", "([J)I", "storage$annotations", "()V", "contains", "", "element", "contains-VKZWuLQ", "([JJ)Z", "containsAll", "elements", "containsAll-impl", "([JLjava/util/Collection;)Z", "equals", "other", "", "get", "index", "get-impl", "([JI)J", "hashCode", "isEmpty", "isEmpty-impl", "([J)Z", "iterator", "Lkotlin/collections/ULongIterator;", "iterator-impl", "([J)Lkotlin/collections/ULongIterator;", "set", "", "value", "set-k8EXiF4", "([JIJ)V", "toString", "", "Iterator", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public final class ULongArray
implements Collection<ULong>,
KMappedMarker {
    private final long[] storage;

    private /* synthetic */ ULongArray(long[] arrl) {
        Intrinsics.checkParameterIsNotNull(arrl, "storage");
        this.storage = arrl;
    }

    public static final /* synthetic */ ULongArray box-impl(long[] arrl) {
        Intrinsics.checkParameterIsNotNull(arrl, "v");
        return new ULongArray(arrl);
    }

    public static long[] constructor-impl(int n) {
        return ULongArray.constructor-impl(new long[n]);
    }

    public static long[] constructor-impl(long[] arrl) {
        Intrinsics.checkParameterIsNotNull(arrl, "storage");
        return arrl;
    }

    public static boolean contains-VKZWuLQ(long[] arrl, long l) {
        return ArraysKt.contains(arrl, l);
    }

    public static boolean containsAll-impl(long[] arrl, Collection<ULong> object) {
        Object e;
        boolean bl;
        Intrinsics.checkParameterIsNotNull(object, "elements");
        object = (Iterable)object;
        boolean bl2 = object.isEmpty();
        boolean bl3 = false;
        if (bl2) return true;
        object = object.iterator();
        do {
            if (object.hasNext()) continue;
            return true;
        } while (bl = (e = object.next()) instanceof ULong && ArraysKt.contains(arrl, ((ULong)e).unbox-impl()));
        return bl3;
    }

    public static boolean equals-impl(long[] arrl, Object object) {
        if (!(object instanceof ULongArray)) return false;
        if (!Intrinsics.areEqual(arrl, ((ULongArray)object).unbox-impl())) return false;
        return true;
    }

    public static final boolean equals-impl0(long[] arrl, long[] arrl2) {
        return Intrinsics.areEqual(arrl, arrl2);
    }

    public static final long get-impl(long[] arrl, int n) {
        return ULong.constructor-impl(arrl[n]);
    }

    public static int getSize-impl(long[] arrl) {
        return arrl.length;
    }

    public static int hashCode-impl(long[] arrl) {
        if (arrl == null) return 0;
        return Arrays.hashCode(arrl);
    }

    public static boolean isEmpty-impl(long[] arrl) {
        if (arrl.length != 0) return false;
        return true;
    }

    public static ULongIterator iterator-impl(long[] arrl) {
        return new Iterator(arrl);
    }

    public static final void set-k8EXiF4(long[] arrl, int n, long l) {
        arrl[n] = l;
    }

    public static /* synthetic */ void storage$annotations() {
    }

    public static String toString-impl(long[] arrl) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ULongArray(storage=");
        stringBuilder.append(Arrays.toString(arrl));
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Override
    public /* synthetic */ boolean add(Object object) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean add-VKZWuLQ(long l) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public boolean addAll(Collection<? extends ULong> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean contains-VKZWuLQ(long l) {
        return ULongArray.contains-VKZWuLQ(this.storage, l);
    }

    @Override
    public boolean containsAll(Collection<? extends Object> collection) {
        return ULongArray.containsAll-impl(this.storage, collection);
    }

    @Override
    public boolean equals(Object object) {
        return ULongArray.equals-impl(this.storage, object);
    }

    public int getSize() {
        return ULongArray.getSize-impl(this.storage);
    }

    @Override
    public int hashCode() {
        return ULongArray.hashCode-impl(this.storage);
    }

    @Override
    public boolean isEmpty() {
        return ULongArray.isEmpty-impl(this.storage);
    }

    public ULongIterator iterator() {
        return ULongArray.iterator-impl(this.storage);
    }

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
    public <T> T[] toArray(T[] arrT) {
        return CollectionToArray.toArray(this, arrT);
    }

    public String toString() {
        return ULongArray.toString-impl(this.storage);
    }

    public final /* synthetic */ long[] unbox-impl() {
        return this.storage;
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0016\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\bH\u0096\u0002J\u0010\u0010\t\u001a\u00020\nH\u0016\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\f"}, d2={"Lkotlin/ULongArray$Iterator;", "Lkotlin/collections/ULongIterator;", "array", "", "([J)V", "index", "", "hasNext", "", "nextULong", "Lkotlin/ULong;", "()J", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
    private static final class Iterator
    extends ULongIterator {
        private final long[] array;
        private int index;

        public Iterator(long[] arrl) {
            Intrinsics.checkParameterIsNotNull(arrl, "array");
            this.array = arrl;
        }

        @Override
        public boolean hasNext() {
            if (this.index >= this.array.length) return false;
            return true;
        }

        @Override
        public long nextULong() {
            int n = this.index;
            long[] arrl = this.array;
            if (n >= arrl.length) throw (Throwable)new NoSuchElementException(String.valueOf(this.index));
            this.index = n + 1;
            return ULong.constructor-impl(arrl[n]);
        }
    }

}

