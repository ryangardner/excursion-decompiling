package kotlin.collections;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import kotlin.Metadata;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0010*\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\bÀ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00060\u0003j\u0002`\u00042\u00060\u0005j\u0002`\u0006B\u0007\b\u0002¢\u0006\u0002\u0010\u0007J\u0011\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0002H\u0096\u0002J\u0016\u0010\u0011\u001a\u00020\u000f2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00020\u0013H\u0016J\u0013\u0010\u0014\u001a\u00020\u000f2\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0096\u0002J\u0011\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u000bH\u0096\u0002J\b\u0010\u0019\u001a\u00020\u000bH\u0016J\u0010\u0010\u001a\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u0002H\u0016J\b\u0010\u001b\u001a\u00020\u000fH\u0016J\u000f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00020\u001dH\u0096\u0002J\u0010\u0010\u001e\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u0002H\u0016J\u000e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00020 H\u0016J\u0016\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00020 2\u0006\u0010\u0018\u001a\u00020\u000bH\u0016J\b\u0010!\u001a\u00020\u0016H\u0002J\u001e\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010#\u001a\u00020\u000b2\u0006\u0010$\u001a\u00020\u000bH\u0016J\b\u0010%\u001a\u00020&H\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082T¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006'"},
   d2 = {"Lkotlin/collections/EmptyList;", "", "", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "()V", "serialVersionUID", "", "size", "", "getSize", "()I", "contains", "", "element", "containsAll", "elements", "", "equals", "other", "", "get", "index", "hashCode", "indexOf", "isEmpty", "iterator", "", "lastIndexOf", "listIterator", "", "readResolve", "subList", "fromIndex", "toIndex", "toString", "", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public final class EmptyList implements List, Serializable, RandomAccess, KMappedMarker {
   public static final EmptyList INSTANCE = new EmptyList();
   private static final long serialVersionUID = -7390468764508069838L;

   private EmptyList() {
   }

   private final Object readResolve() {
      return INSTANCE;
   }

   // $FF: synthetic method
   public void add(int var1, Object var2) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public void add(int var1, Void var2) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   // $FF: synthetic method
   public boolean add(Object var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean add(Void var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean addAll(int var1, Collection var2) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean addAll(Collection var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public void clear() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean contains(Void var1) {
      Intrinsics.checkParameterIsNotNull(var1, "element");
      return false;
   }

   public boolean containsAll(Collection var1) {
      Intrinsics.checkParameterIsNotNull(var1, "elements");
      return var1.isEmpty();
   }

   public boolean equals(Object var1) {
      boolean var2;
      if (var1 instanceof List && ((List)var1).isEmpty()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public Void get(int var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append("Empty list doesn't contain element at index ");
      var2.append(var1);
      var2.append('.');
      throw (Throwable)(new IndexOutOfBoundsException(var2.toString()));
   }

   public int getSize() {
      return 0;
   }

   public int hashCode() {
      return 1;
   }

   public int indexOf(Void var1) {
      Intrinsics.checkParameterIsNotNull(var1, "element");
      return -1;
   }

   public boolean isEmpty() {
      return true;
   }

   public Iterator iterator() {
      return (Iterator)EmptyIterator.INSTANCE;
   }

   public int lastIndexOf(Void var1) {
      Intrinsics.checkParameterIsNotNull(var1, "element");
      return -1;
   }

   public ListIterator listIterator() {
      return (ListIterator)EmptyIterator.INSTANCE;
   }

   public ListIterator listIterator(int var1) {
      if (var1 == 0) {
         return (ListIterator)EmptyIterator.INSTANCE;
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("Index: ");
         var2.append(var1);
         throw (Throwable)(new IndexOutOfBoundsException(var2.toString()));
      }
   }

   // $FF: synthetic method
   public Object remove(int var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public Void remove(int var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean remove(Object var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean removeAll(Collection var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean retainAll(Collection var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   // $FF: synthetic method
   public Object set(int var1, Object var2) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public Void set(int var1, Void var2) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public List subList(int var1, int var2) {
      if (var1 == 0 && var2 == 0) {
         return (List)this;
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("fromIndex: ");
         var3.append(var1);
         var3.append(", toIndex: ");
         var3.append(var2);
         throw (Throwable)(new IndexOutOfBoundsException(var3.toString()));
      }
   }

   public Object[] toArray() {
      return CollectionToArray.toArray(this);
   }

   public <T> T[] toArray(T[] var1) {
      return CollectionToArray.toArray(this, var1);
   }

   public String toString() {
      return "[]";
   }
}
