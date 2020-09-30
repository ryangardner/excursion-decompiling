/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin;

import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.collections.ArraysKt;
import kotlin.collections.UByteIterator;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(bv={1, 0, 3}, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u0000\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0087@\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001-B\u0014\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0005\u0010\u0006B\u0014\b\u0001\u0012\u0006\u0010\u0007\u001a\u00020\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0005\u0010\tJ\u001b\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0002H\u0096\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0011\u0010\u0012J \u0010\u0013\u001a\u00020\u000f2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u0016\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0013\u0010\u0017\u001a\u00020\u000f2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u00d6\u0003J\u001b\u0010\u001a\u001a\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0004H\u0086\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\t\u0010\u001e\u001a\u00020\u0004H\u00d6\u0001J\u000f\u0010\u001f\u001a\u00020\u000fH\u0016\u00a2\u0006\u0004\b \u0010!J\u0010\u0010\"\u001a\u00020#H\u0096\u0002\u00a2\u0006\u0004\b$\u0010%J#\u0010&\u001a\u00020'2\u0006\u0010\u001b\u001a\u00020\u00042\u0006\u0010(\u001a\u00020\u0002H\u0086\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b)\u0010*J\t\u0010+\u001a\u00020,H\u00d6\u0001R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\u0007\u001a\u00020\b8\u0000X\u0081\u0004\u00a2\u0006\b\n\u0000\u0012\u0004\b\f\u0010\r\u00f8\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006."}, d2={"Lkotlin/UByteArray;", "", "Lkotlin/UByte;", "size", "", "constructor-impl", "(I)[B", "storage", "", "([B)[B", "getSize-impl", "([B)I", "storage$annotations", "()V", "contains", "", "element", "contains-7apg3OU", "([BB)Z", "containsAll", "elements", "containsAll-impl", "([BLjava/util/Collection;)Z", "equals", "other", "", "get", "index", "get-impl", "([BI)B", "hashCode", "isEmpty", "isEmpty-impl", "([B)Z", "iterator", "Lkotlin/collections/UByteIterator;", "iterator-impl", "([B)Lkotlin/collections/UByteIterator;", "set", "", "value", "set-VurrAj0", "([BIB)V", "toString", "", "Iterator", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public final class UByteArray
implements Collection<UByte>,
KMappedMarker {
    private final byte[] storage;

    private /* synthetic */ UByteArray(byte[] arrby) {
        Intrinsics.checkParameterIsNotNull(arrby, "storage");
        this.storage = arrby;
    }

    public static final /* synthetic */ UByteArray box-impl(byte[] arrby) {
        Intrinsics.checkParameterIsNotNull(arrby, "v");
        return new UByteArray(arrby);
    }

    public static byte[] constructor-impl(int n) {
        return UByteArray.constructor-impl(new byte[n]);
    }

    public static byte[] constructor-impl(byte[] arrby) {
        Intrinsics.checkParameterIsNotNull(arrby, "storage");
        return arrby;
    }

    public static boolean contains-7apg3OU(byte[] arrby, byte by) {
        return ArraysKt.contains(arrby, by);
    }

    public static boolean containsAll-impl(byte[] arrby, Collection<UByte> object) {
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
        } while (bl = (e = object.next()) instanceof UByte && ArraysKt.contains(arrby, ((UByte)e).unbox-impl()));
        return bl3;
    }

    public static boolean equals-impl(byte[] arrby, Object object) {
        if (!(object instanceof UByteArray)) return false;
        if (!Intrinsics.areEqual(arrby, ((UByteArray)object).unbox-impl())) return false;
        return true;
    }

    public static final boolean equals-impl0(byte[] arrby, byte[] arrby2) {
        return Intrinsics.areEqual(arrby, arrby2);
    }

    public static final byte get-impl(byte[] arrby, int n) {
        return UByte.constructor-impl(arrby[n]);
    }

    public static int getSize-impl(byte[] arrby) {
        return arrby.length;
    }

    public static int hashCode-impl(byte[] arrby) {
        if (arrby == null) return 0;
        return Arrays.hashCode(arrby);
    }

    public static boolean isEmpty-impl(byte[] arrby) {
        if (arrby.length != 0) return false;
        return true;
    }

    public static UByteIterator iterator-impl(byte[] arrby) {
        return new Iterator(arrby);
    }

    public static final void set-VurrAj0(byte[] arrby, int n, byte by) {
        arrby[n] = by;
    }

    public static /* synthetic */ void storage$annotations() {
    }

    public static String toString-impl(byte[] arrby) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UByteArray(storage=");
        stringBuilder.append(Arrays.toString(arrby));
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Override
    public /* synthetic */ boolean add(Object object) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean add-7apg3OU(byte by) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public boolean addAll(Collection<? extends UByte> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean contains-7apg3OU(byte by) {
        return UByteArray.contains-7apg3OU(this.storage, by);
    }

    @Override
    public boolean containsAll(Collection<? extends Object> collection) {
        return UByteArray.containsAll-impl(this.storage, collection);
    }

    @Override
    public boolean equals(Object object) {
        return UByteArray.equals-impl(this.storage, object);
    }

    public int getSize() {
        return UByteArray.getSize-impl(this.storage);
    }

    @Override
    public int hashCode() {
        return UByteArray.hashCode-impl(this.storage);
    }

    @Override
    public boolean isEmpty() {
        return UByteArray.isEmpty-impl(this.storage);
    }

    public UByteIterator iterator() {
        return UByteArray.iterator-impl(this.storage);
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
        return UByteArray.toString-impl(this.storage);
    }

    public final /* synthetic */ byte[] unbox-impl() {
        return this.storage;
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\bH\u0096\u0002J\u0010\u0010\t\u001a\u00020\nH\u0016\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\f"}, d2={"Lkotlin/UByteArray$Iterator;", "Lkotlin/collections/UByteIterator;", "array", "", "([B)V", "index", "", "hasNext", "", "nextUByte", "Lkotlin/UByte;", "()B", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
    private static final class Iterator
    extends UByteIterator {
        private final byte[] array;
        private int index;

        public Iterator(byte[] arrby) {
            Intrinsics.checkParameterIsNotNull(arrby, "array");
            this.array = arrby;
        }

        @Override
        public boolean hasNext() {
            if (this.index >= this.array.length) return false;
            return true;
        }

        @Override
        public byte nextUByte() {
            int n = this.index;
            byte[] arrby = this.array;
            if (n >= arrby.length) throw (Throwable)new NoSuchElementException(String.valueOf(this.index));
            this.index = n + 1;
            return UByte.constructor-impl(arrby[n]);
        }
    }

}

