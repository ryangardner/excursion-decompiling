package kotlin;

import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import kotlin.collections.ArraysKt;
import kotlin.collections.UByteIterator;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u0000\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0087@\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001-B\u0014\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004ø\u0001\u0000¢\u0006\u0004\b\u0005\u0010\u0006B\u0014\b\u0001\u0012\u0006\u0010\u0007\u001a\u00020\bø\u0001\u0000¢\u0006\u0004\b\u0005\u0010\tJ\u001b\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0002H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\b\u0011\u0010\u0012J \u0010\u0013\u001a\u00020\u000f2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u0016ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016J\u0013\u0010\u0017\u001a\u00020\u000f2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019HÖ\u0003J\u001b\u0010\u001a\u001a\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0004H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u001dJ\t\u0010\u001e\u001a\u00020\u0004HÖ\u0001J\u000f\u0010\u001f\u001a\u00020\u000fH\u0016¢\u0006\u0004\b \u0010!J\u0010\u0010\"\u001a\u00020#H\u0096\u0002¢\u0006\u0004\b$\u0010%J#\u0010&\u001a\u00020'2\u0006\u0010\u001b\u001a\u00020\u00042\u0006\u0010(\u001a\u00020\u0002H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b)\u0010*J\t\u0010+\u001a\u00020,HÖ\u0001R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\u0007\u001a\u00020\b8\u0000X\u0081\u0004¢\u0006\b\n\u0000\u0012\u0004\b\f\u0010\rø\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006."},
   d2 = {"Lkotlin/UByteArray;", "", "Lkotlin/UByte;", "size", "", "constructor-impl", "(I)[B", "storage", "", "([B)[B", "getSize-impl", "([B)I", "storage$annotations", "()V", "contains", "", "element", "contains-7apg3OU", "([BB)Z", "containsAll", "elements", "containsAll-impl", "([BLjava/util/Collection;)Z", "equals", "other", "", "get", "index", "get-impl", "([BI)B", "hashCode", "isEmpty", "isEmpty-impl", "([B)Z", "iterator", "Lkotlin/collections/UByteIterator;", "iterator-impl", "([B)Lkotlin/collections/UByteIterator;", "set", "", "value", "set-VurrAj0", "([BIB)V", "toString", "", "Iterator", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public final class UByteArray implements Collection<UByte>, KMappedMarker {
   private final byte[] storage;

   // $FF: synthetic method
   private UByteArray(byte[] var1) {
      Intrinsics.checkParameterIsNotNull(var1, "storage");
      super();
      this.storage = var1;
   }

   // $FF: synthetic method
   public static final UByteArray box_impl/* $FF was: box-impl*/(byte[] var0) {
      Intrinsics.checkParameterIsNotNull(var0, "v");
      return new UByteArray(var0);
   }

   public static byte[] constructor_impl/* $FF was: constructor-impl*/(int var0) {
      return constructor-impl(new byte[var0]);
   }

   public static byte[] constructor_impl/* $FF was: constructor-impl*/(byte[] var0) {
      Intrinsics.checkParameterIsNotNull(var0, "storage");
      return var0;
   }

   public static boolean contains_7apg3OU/* $FF was: contains-7apg3OU*/(byte[] var0, byte var1) {
      return ArraysKt.contains(var0, var1);
   }

   public static boolean containsAll_impl/* $FF was: containsAll-impl*/(byte[] var0, Collection<UByte> var1) {
      Intrinsics.checkParameterIsNotNull(var1, "elements");
      Iterable var6 = (Iterable)var1;
      boolean var2 = ((Collection)var6).isEmpty();
      boolean var3 = false;
      if (!var2) {
         java.util.Iterator var7 = var6.iterator();

         while(var7.hasNext()) {
            Object var4 = var7.next();
            boolean var5;
            if (var4 instanceof UByte && ArraysKt.contains(var0, ((UByte)var4).unbox-impl())) {
               var5 = true;
            } else {
               var5 = false;
            }

            if (!var5) {
               return var3;
            }
         }
      }

      var3 = true;
      return var3;
   }

   public static boolean equals_impl/* $FF was: equals-impl*/(byte[] var0, Object var1) {
      return var1 instanceof UByteArray && Intrinsics.areEqual((Object)var0, (Object)((UByteArray)var1).unbox-impl());
   }

   public static final boolean equals_impl0/* $FF was: equals-impl0*/(byte[] var0, byte[] var1) {
      return Intrinsics.areEqual((Object)var0, (Object)var1);
   }

   public static final byte get_impl/* $FF was: get-impl*/(byte[] var0, int var1) {
      return UByte.constructor-impl(var0[var1]);
   }

   public static int getSize_impl/* $FF was: getSize-impl*/(byte[] var0) {
      return var0.length;
   }

   public static int hashCode_impl/* $FF was: hashCode-impl*/(byte[] var0) {
      int var1;
      if (var0 != null) {
         var1 = Arrays.hashCode(var0);
      } else {
         var1 = 0;
      }

      return var1;
   }

   public static boolean isEmpty_impl/* $FF was: isEmpty-impl*/(byte[] var0) {
      boolean var1;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static UByteIterator iterator_impl/* $FF was: iterator-impl*/(byte[] var0) {
      return (UByteIterator)(new UByteArray.Iterator(var0));
   }

   public static final void set_VurrAj0/* $FF was: set-VurrAj0*/(byte[] var0, int var1, byte var2) {
      var0[var1] = (byte)var2;
   }

   // $FF: synthetic method
   public static void storage$annotations() {
   }

   public static String toString_impl/* $FF was: toString-impl*/(byte[] var0) {
      StringBuilder var1 = new StringBuilder();
      var1.append("UByteArray(storage=");
      var1.append(Arrays.toString(var0));
      var1.append(")");
      return var1.toString();
   }

   // $FF: synthetic method
   public boolean add(Object var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean add_7apg3OU/* $FF was: add-7apg3OU*/(byte var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean addAll(Collection<? extends UByte> var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public void clear() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean contains_7apg3OU/* $FF was: contains-7apg3OU*/(byte var1) {
      return contains-7apg3OU(this.storage, var1);
   }

   public boolean containsAll(Collection<? extends Object> var1) {
      return containsAll-impl(this.storage, var1);
   }

   public boolean equals(Object var1) {
      return equals-impl(this.storage, var1);
   }

   public int getSize() {
      return getSize-impl(this.storage);
   }

   public int hashCode() {
      return hashCode-impl(this.storage);
   }

   public boolean isEmpty() {
      return isEmpty-impl(this.storage);
   }

   public UByteIterator iterator() {
      return iterator-impl(this.storage);
   }

   public boolean remove(Object var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean removeAll(Collection<? extends Object> var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean retainAll(Collection<? extends Object> var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public Object[] toArray() {
      return CollectionToArray.toArray(this);
   }

   public <T> T[] toArray(T[] var1) {
      return CollectionToArray.toArray(this, var1);
   }

   public String toString() {
      return toString-impl(this.storage);
   }

   // $FF: synthetic method
   public final byte[] unbox_impl/* $FF was: unbox-impl*/() {
      return this.storage;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\bH\u0096\u0002J\u0010\u0010\t\u001a\u00020\nH\u0016ø\u0001\u0000¢\u0006\u0002\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\f"},
      d2 = {"Lkotlin/UByteArray$Iterator;", "Lkotlin/collections/UByteIterator;", "array", "", "([B)V", "index", "", "hasNext", "", "nextUByte", "Lkotlin/UByte;", "()B", "kotlin-stdlib"},
      k = 1,
      mv = {1, 1, 16}
   )
   private static final class Iterator extends UByteIterator {
      private final byte[] array;
      private int index;

      public Iterator(byte[] var1) {
         Intrinsics.checkParameterIsNotNull(var1, "array");
         super();
         this.array = var1;
      }

      public boolean hasNext() {
         boolean var1;
         if (this.index < this.array.length) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public byte nextUByte() {
         int var1 = this.index;
         byte[] var2 = this.array;
         if (var1 < var2.length) {
            this.index = var1 + 1;
            return UByte.constructor-impl(var2[var1]);
         } else {
            throw (Throwable)(new NoSuchElementException(String.valueOf(this.index)));
         }
      }
   }
}
