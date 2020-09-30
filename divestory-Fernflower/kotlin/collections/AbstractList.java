package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00008\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0010*\n\u0002\b\b\b'\u0018\u0000 \u001c*\u0006\b\u0000\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u0004\u001c\u001d\u001e\u001fB\u0007\b\u0004¢\u0006\u0002\u0010\u0004J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0096\u0002J\u0016\u0010\r\u001a\u00028\u00002\u0006\u0010\u000e\u001a\u00020\u0006H¦\u0002¢\u0006\u0002\u0010\u000fJ\b\u0010\u0010\u001a\u00020\u0006H\u0016J\u0015\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0013J\u000f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u0015H\u0096\u0002J\u0015\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0013J\u000e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00028\u00000\u0018H\u0016J\u0016\u0010\u0017\u001a\b\u0012\u0004\u0012\u00028\u00000\u00182\u0006\u0010\u000e\u001a\u00020\u0006H\u0016J\u001e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\u0006H\u0016R\u0012\u0010\u0005\u001a\u00020\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006 "},
   d2 = {"Lkotlin/collections/AbstractList;", "E", "Lkotlin/collections/AbstractCollection;", "", "()V", "size", "", "getSize", "()I", "equals", "", "other", "", "get", "index", "(I)Ljava/lang/Object;", "hashCode", "indexOf", "element", "(Ljava/lang/Object;)I", "iterator", "", "lastIndexOf", "listIterator", "", "subList", "fromIndex", "toIndex", "Companion", "IteratorImpl", "ListIteratorImpl", "SubList", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public abstract class AbstractList<E> extends AbstractCollection<E> implements List<E>, KMappedMarker {
   public static final AbstractList.Companion Companion = new AbstractList.Companion((DefaultConstructorMarker)null);

   protected AbstractList() {
   }

   public void add(int var1, E var2) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean addAll(int var1, Collection<? extends E> var2) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean equals(Object var1) {
      if (var1 == (AbstractList)this) {
         return true;
      } else {
         return !(var1 instanceof List) ? false : Companion.orderedEquals$kotlin_stdlib((Collection)this, (Collection)var1);
      }
   }

   public abstract E get(int var1);

   public abstract int getSize();

   public int hashCode() {
      return Companion.orderedHashCode$kotlin_stdlib((Collection)this);
   }

   public int indexOf(Object var1) {
      Iterator var2 = this.iterator();
      int var3 = 0;

      while(true) {
         if (!var2.hasNext()) {
            var3 = -1;
            break;
         }

         if (Intrinsics.areEqual(var2.next(), var1)) {
            break;
         }

         ++var3;
      }

      return var3;
   }

   public Iterator<E> iterator() {
      return (Iterator)(new AbstractList.IteratorImpl());
   }

   public int lastIndexOf(Object var1) {
      ListIterator var2 = this.listIterator(this.size());

      int var3;
      while(true) {
         if (var2.hasPrevious()) {
            if (!Intrinsics.areEqual(var2.previous(), var1)) {
               continue;
            }

            var3 = var2.nextIndex();
            break;
         }

         var3 = -1;
         break;
      }

      return var3;
   }

   public ListIterator<E> listIterator() {
      return (ListIterator)(new AbstractList.ListIteratorImpl(0));
   }

   public ListIterator<E> listIterator(int var1) {
      return (ListIterator)(new AbstractList.ListIteratorImpl(var1));
   }

   public E remove(int var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public E set(int var1, E var2) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public List<E> subList(int var1, int var2) {
      return (List)(new AbstractList.SubList(this, var1, var2));
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0005\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J%\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006H\u0000¢\u0006\u0002\b\tJ\u001d\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006H\u0000¢\u0006\u0002\b\fJ\u001d\u0010\r\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006H\u0000¢\u0006\u0002\b\u000eJ%\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006H\u0000¢\u0006\u0002\b\u0012J%\u0010\u0013\u001a\u00020\u00142\n\u0010\u0015\u001a\u0006\u0012\u0002\b\u00030\u00162\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u0016H\u0000¢\u0006\u0002\b\u0018J\u0019\u0010\u0019\u001a\u00020\u00062\n\u0010\u0015\u001a\u0006\u0012\u0002\b\u00030\u0016H\u0000¢\u0006\u0002\b\u001a¨\u0006\u001b"},
      d2 = {"Lkotlin/collections/AbstractList$Companion;", "", "()V", "checkBoundsIndexes", "", "startIndex", "", "endIndex", "size", "checkBoundsIndexes$kotlin_stdlib", "checkElementIndex", "index", "checkElementIndex$kotlin_stdlib", "checkPositionIndex", "checkPositionIndex$kotlin_stdlib", "checkRangeIndexes", "fromIndex", "toIndex", "checkRangeIndexes$kotlin_stdlib", "orderedEquals", "", "c", "", "other", "orderedEquals$kotlin_stdlib", "orderedHashCode", "orderedHashCode$kotlin_stdlib", "kotlin-stdlib"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Companion {
      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final void checkBoundsIndexes$kotlin_stdlib(int var1, int var2, int var3) {
         StringBuilder var4;
         if (var1 >= 0 && var2 <= var3) {
            if (var1 > var2) {
               var4 = new StringBuilder();
               var4.append("startIndex: ");
               var4.append(var1);
               var4.append(" > endIndex: ");
               var4.append(var2);
               throw (Throwable)(new IllegalArgumentException(var4.toString()));
            }
         } else {
            var4 = new StringBuilder();
            var4.append("startIndex: ");
            var4.append(var1);
            var4.append(", endIndex: ");
            var4.append(var2);
            var4.append(", size: ");
            var4.append(var3);
            throw (Throwable)(new IndexOutOfBoundsException(var4.toString()));
         }
      }

      public final void checkElementIndex$kotlin_stdlib(int var1, int var2) {
         if (var1 < 0 || var1 >= var2) {
            StringBuilder var3 = new StringBuilder();
            var3.append("index: ");
            var3.append(var1);
            var3.append(", size: ");
            var3.append(var2);
            throw (Throwable)(new IndexOutOfBoundsException(var3.toString()));
         }
      }

      public final void checkPositionIndex$kotlin_stdlib(int var1, int var2) {
         if (var1 < 0 || var1 > var2) {
            StringBuilder var3 = new StringBuilder();
            var3.append("index: ");
            var3.append(var1);
            var3.append(", size: ");
            var3.append(var2);
            throw (Throwable)(new IndexOutOfBoundsException(var3.toString()));
         }
      }

      public final void checkRangeIndexes$kotlin_stdlib(int var1, int var2, int var3) {
         StringBuilder var4;
         if (var1 >= 0 && var2 <= var3) {
            if (var1 > var2) {
               var4 = new StringBuilder();
               var4.append("fromIndex: ");
               var4.append(var1);
               var4.append(" > toIndex: ");
               var4.append(var2);
               throw (Throwable)(new IllegalArgumentException(var4.toString()));
            }
         } else {
            var4 = new StringBuilder();
            var4.append("fromIndex: ");
            var4.append(var1);
            var4.append(", toIndex: ");
            var4.append(var2);
            var4.append(", size: ");
            var4.append(var3);
            throw (Throwable)(new IndexOutOfBoundsException(var4.toString()));
         }
      }

      public final boolean orderedEquals$kotlin_stdlib(Collection<?> var1, Collection<?> var2) {
         Intrinsics.checkParameterIsNotNull(var1, "c");
         Intrinsics.checkParameterIsNotNull(var2, "other");
         if (var1.size() != var2.size()) {
            return false;
         } else {
            Iterator var4 = var2.iterator();
            Iterator var3 = var1.iterator();

            do {
               if (!var3.hasNext()) {
                  return true;
               }
            } while(!(Intrinsics.areEqual(var3.next(), var4.next()) ^ true));

            return false;
         }
      }

      public final int orderedHashCode$kotlin_stdlib(Collection<?> var1) {
         Intrinsics.checkParameterIsNotNull(var1, "c");
         Iterator var2 = var1.iterator();

         int var3;
         int var4;
         for(var3 = 1; var2.hasNext(); var3 = var3 * 31 + var4) {
            Object var5 = var2.next();
            if (var5 != null) {
               var4 = var5.hashCode();
            } else {
               var4 = 0;
            }
         }

         return var3;
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0092\u0004\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001B\u0005¢\u0006\u0002\u0010\u0002J\t\u0010\t\u001a\u00020\nH\u0096\u0002J\u000e\u0010\u000b\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\fR\u001a\u0010\u0003\u001a\u00020\u0004X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\r"},
      d2 = {"Lkotlin/collections/AbstractList$IteratorImpl;", "", "(Lkotlin/collections/AbstractList;)V", "index", "", "getIndex", "()I", "setIndex", "(I)V", "hasNext", "", "next", "()Ljava/lang/Object;", "kotlin-stdlib"},
      k = 1,
      mv = {1, 1, 16}
   )
   private class IteratorImpl implements Iterator<E>, KMappedMarker {
      private int index;

      public IteratorImpl() {
      }

      protected final int getIndex() {
         return this.index;
      }

      public boolean hasNext() {
         boolean var1;
         if (this.index < AbstractList.this.size()) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public E next() {
         if (this.hasNext()) {
            AbstractList var1 = AbstractList.this;
            int var2 = this.index++;
            return var1.get(var2);
         } else {
            throw (Throwable)(new NoSuchElementException());
         }
      }

      public void remove() {
         throw new UnsupportedOperationException("Operation is not supported for read-only collection");
      }

      protected final void setIndex(int var1) {
         this.index = var1;
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010*\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0092\u0004\u0018\u00002\f0\u0001R\b\u0012\u0004\u0012\u00028\u00000\u00022\b\u0012\u0004\u0012\u00028\u00000\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\u0005H\u0016J\r\u0010\n\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u000bJ\b\u0010\f\u001a\u00020\u0005H\u0016¨\u0006\r"},
      d2 = {"Lkotlin/collections/AbstractList$ListIteratorImpl;", "Lkotlin/collections/AbstractList$IteratorImpl;", "Lkotlin/collections/AbstractList;", "", "index", "", "(Lkotlin/collections/AbstractList;I)V", "hasPrevious", "", "nextIndex", "previous", "()Ljava/lang/Object;", "previousIndex", "kotlin-stdlib"},
      k = 1,
      mv = {1, 1, 16}
   )
   private class ListIteratorImpl extends AbstractList<E>.IteratorImpl implements ListIterator<E>, KMappedMarker {
      public ListIteratorImpl(int var2) {
         super();
         AbstractList.Companion.checkPositionIndex$kotlin_stdlib(var2, AbstractList.this.size());
         this.setIndex(var2);
      }

      public void add(E var1) {
         throw new UnsupportedOperationException("Operation is not supported for read-only collection");
      }

      public boolean hasPrevious() {
         boolean var1;
         if (this.getIndex() > 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public int nextIndex() {
         return this.getIndex();
      }

      public E previous() {
         if (this.hasPrevious()) {
            AbstractList var1 = AbstractList.this;
            this.setIndex(this.getIndex() - 1);
            return var1.get(this.getIndex());
         } else {
            throw (Throwable)(new NoSuchElementException());
         }
      }

      public int previousIndex() {
         return this.getIndex() - 1;
      }

      public void set(E var1) {
         throw new UnsupportedOperationException("Operation is not supported for read-only collection");
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\n\b\u0002\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00060\u0003j\u0002`\u0004B#\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00010\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tJ\u0016\u0010\u000e\u001a\u00028\u00012\u0006\u0010\u000f\u001a\u00020\u0007H\u0096\u0002¢\u0006\u0002\u0010\u0010R\u000e\u0010\n\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00010\u0002X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u0011"},
      d2 = {"Lkotlin/collections/AbstractList$SubList;", "E", "Lkotlin/collections/AbstractList;", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "list", "fromIndex", "", "toIndex", "(Lkotlin/collections/AbstractList;II)V", "_size", "size", "getSize", "()I", "get", "index", "(I)Ljava/lang/Object;", "kotlin-stdlib"},
      k = 1,
      mv = {1, 1, 16}
   )
   private static final class SubList<E> extends AbstractList<E> implements RandomAccess {
      private int _size;
      private final int fromIndex;
      private final AbstractList<E> list;

      public SubList(AbstractList<? extends E> var1, int var2, int var3) {
         Intrinsics.checkParameterIsNotNull(var1, "list");
         super();
         this.list = var1;
         this.fromIndex = var2;
         AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(this.fromIndex, var3, this.list.size());
         this._size = var3 - this.fromIndex;
      }

      public E get(int var1) {
         AbstractList.Companion.checkElementIndex$kotlin_stdlib(var1, this._size);
         return this.list.get(this.fromIndex + var1);
      }

      public int getSize() {
         return this._size;
      }
   }
}
