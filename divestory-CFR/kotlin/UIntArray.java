/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin;

import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.UInt;
import kotlin.collections.ArraysKt;
import kotlin.collections.UIntIterator;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(bv={1, 0, 3}, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0015\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u0000\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0087@\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001-B\u0014\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0005\u0010\u0006B\u0014\b\u0001\u0012\u0006\u0010\u0007\u001a\u00020\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0005\u0010\tJ\u001b\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0002H\u0096\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0011\u0010\u0012J \u0010\u0013\u001a\u00020\u000f2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u0016\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0013\u0010\u0017\u001a\u00020\u000f2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u00d6\u0003J\u001b\u0010\u001a\u001a\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0004H\u0086\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\t\u0010\u001e\u001a\u00020\u0004H\u00d6\u0001J\u000f\u0010\u001f\u001a\u00020\u000fH\u0016\u00a2\u0006\u0004\b \u0010!J\u0010\u0010\"\u001a\u00020#H\u0096\u0002\u00a2\u0006\u0004\b$\u0010%J#\u0010&\u001a\u00020'2\u0006\u0010\u001b\u001a\u00020\u00042\u0006\u0010(\u001a\u00020\u0002H\u0086\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b)\u0010*J\t\u0010+\u001a\u00020,H\u00d6\u0001R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\u0007\u001a\u00020\b8\u0000X\u0081\u0004\u00a2\u0006\b\n\u0000\u0012\u0004\b\f\u0010\r\u00f8\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006."}, d2={"Lkotlin/UIntArray;", "", "Lkotlin/UInt;", "size", "", "constructor-impl", "(I)[I", "storage", "", "([I)[I", "getSize-impl", "([I)I", "storage$annotations", "()V", "contains", "", "element", "contains-WZ4Q5Ns", "([II)Z", "containsAll", "elements", "containsAll-impl", "([ILjava/util/Collection;)Z", "equals", "other", "", "get", "index", "get-impl", "([II)I", "hashCode", "isEmpty", "isEmpty-impl", "([I)Z", "iterator", "Lkotlin/collections/UIntIterator;", "iterator-impl", "([I)Lkotlin/collections/UIntIterator;", "set", "", "value", "set-VXSXFK8", "([III)V", "toString", "", "Iterator", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public final class UIntArray
implements Collection<UInt>,
KMappedMarker {
    private final int[] storage;

    private /* synthetic */ UIntArray(int[] arrn) {
        Intrinsics.checkParameterIsNotNull(arrn, "storage");
        this.storage = arrn;
    }

    public static final /* synthetic */ UIntArray box-impl(int[] arrn) {
        Intrinsics.checkParameterIsNotNull(arrn, "v");
        return new UIntArray(arrn);
    }

    public static int[] constructor-impl(int n) {
        return UIntArray.constructor-impl(new int[n]);
    }

    public static int[] constructor-impl(int[] arrn) {
        Intrinsics.checkParameterIsNotNull(arrn, "storage");
        return arrn;
    }

    public static boolean contains-WZ4Q5Ns(int[] arrn, int n) {
        return ArraysKt.contains(arrn, n);
    }

    public static boolean containsAll-impl(int[] arrn, Collection<UInt> object) {
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
        } while (bl = (e = object.next()) instanceof UInt && ArraysKt.contains(arrn, ((UInt)e).unbox-impl()));
        return bl3;
    }

    public static boolean equals-impl(int[] arrn, Object object) {
        if (!(object instanceof UIntArray)) return false;
        if (!Intrinsics.areEqual(arrn, ((UIntArray)object).unbox-impl())) return false;
        return true;
    }

    public static final boolean equals-impl0(int[] arrn, int[] arrn2) {
        return Intrinsics.areEqual(arrn, arrn2);
    }

    public static final int get-impl(int[] arrn, int n) {
        return UInt.constructor-impl(arrn[n]);
    }

    public static int getSize-impl(int[] arrn) {
        return arrn.length;
    }

    public static int hashCode-impl(int[] arrn) {
        if (arrn == null) return 0;
        return Arrays.hashCode(arrn);
    }

    public static boolean isEmpty-impl(int[] arrn) {
        if (arrn.length != 0) return false;
        return true;
    }

    public static UIntIterator iterator-impl(int[] arrn) {
        return new Iterator(arrn);
    }

    public static final void set-VXSXFK8(int[] arrn, int n, int n2) {
        arrn[n] = n2;
    }

    public static /* synthetic */ void storage$annotations() {
    }

    public static String toString-impl(int[] arrn) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UIntArray(storage=");
        stringBuilder.append(Arrays.toString(arrn));
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Override
    public /* synthetic */ boolean add(Object object) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean add-WZ4Q5Ns(int n) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public boolean addAll(Collection<? extends UInt> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean contains-WZ4Q5Ns(int n) {
        return UIntArray.contains-WZ4Q5Ns(this.storage, n);
    }

    @Override
    public boolean containsAll(Collection<? extends Object> collection) {
        return UIntArray.containsAll-impl(this.storage, collection);
    }

    @Override
    public boolean equals(Object object) {
        return UIntArray.equals-impl(this.storage, object);
    }

    public int getSize() {
        return UIntArray.getSize-impl(this.storage);
    }

    @Override
    public int hashCode() {
        return UIntArray.hashCode-impl(this.storage);
    }

    @Override
    public boolean isEmpty() {
        return UIntArray.isEmpty-impl(this.storage);
    }

    public UIntIterator iterator() {
        return UIntArray.iterator-impl(this.storage);
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
        return UIntArray.toString-impl(this.storage);
    }

    public final /* synthetic */ int[] unbox-impl() {
        return this.storage;
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\bH\u0096\u0002J\u0010\u0010\t\u001a\u00020\nH\u0016\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\f"}, d2={"Lkotlin/UIntArray$Iterator;", "Lkotlin/collections/UIntIterator;", "array", "", "([I)V", "index", "", "hasNext", "", "nextUInt", "Lkotlin/UInt;", "()I", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
    private static final class Iterator
    extends UIntIterator {
        private final int[] array;
        private int index;

        public Iterator(int[] arrn) {
            Intrinsics.checkParameterIsNotNull(arrn, "array");
            this.array = arrn;
        }

        @Override
        public boolean hasNext() {
            if (this.index >= this.array.length) return false;
            return true;
        }

        @Override
        public int nextUInt() {
            int n = this.index;
            int[] arrn = this.array;
            if (n >= arrn.length) throw (Throwable)new NoSuchElementException(String.valueOf(this.index));
            this.index = n + 1;
            return UInt.constructor-impl(arrn[n]);
        }
    }

}

