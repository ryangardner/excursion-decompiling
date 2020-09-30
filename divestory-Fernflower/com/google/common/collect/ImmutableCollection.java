package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.DoNotMock;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@DoNotMock("Use ImmutableList.of or another implementation")
public abstract class ImmutableCollection<E> extends AbstractCollection<E> implements Serializable {
   private static final Object[] EMPTY_ARRAY = new Object[0];

   ImmutableCollection() {
   }

   @Deprecated
   public final boolean add(E var1) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public final boolean addAll(Collection<? extends E> var1) {
      throw new UnsupportedOperationException();
   }

   public ImmutableList<E> asList() {
      ImmutableList var1;
      if (this.isEmpty()) {
         var1 = ImmutableList.of();
      } else {
         var1 = ImmutableList.asImmutableList(this.toArray());
      }

      return var1;
   }

   @Deprecated
   public final void clear() {
      throw new UnsupportedOperationException();
   }

   public abstract boolean contains(@NullableDecl Object var1);

   int copyIntoArray(Object[] var1, int var2) {
      for(UnmodifiableIterator var3 = this.iterator(); var3.hasNext(); ++var2) {
         var1[var2] = var3.next();
      }

      return var2;
   }

   Object[] internalArray() {
      return null;
   }

   int internalArrayEnd() {
      throw new UnsupportedOperationException();
   }

   int internalArrayStart() {
      throw new UnsupportedOperationException();
   }

   abstract boolean isPartialView();

   public abstract UnmodifiableIterator<E> iterator();

   @Deprecated
   public final boolean remove(Object var1) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public final boolean removeAll(Collection<?> var1) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public final boolean retainAll(Collection<?> var1) {
      throw new UnsupportedOperationException();
   }

   public final Object[] toArray() {
      return this.toArray(EMPTY_ARRAY);
   }

   public final <T> T[] toArray(T[] var1) {
      Preconditions.checkNotNull(var1);
      int var2 = this.size();
      Object[] var3;
      if (var1.length < var2) {
         var3 = this.internalArray();
         if (var3 != null) {
            return Platform.copy(var3, this.internalArrayStart(), this.internalArrayEnd(), var1);
         }

         var3 = ObjectArrays.newArray(var1, var2);
      } else {
         var3 = var1;
         if (var1.length > var2) {
            var1[var2] = null;
            var3 = var1;
         }
      }

      this.copyIntoArray(var3, 0);
      return var3;
   }

   Object writeReplace() {
      return new ImmutableList.SerializedForm(this.toArray());
   }

   abstract static class ArrayBasedBuilder<E> extends ImmutableCollection.Builder<E> {
      Object[] contents;
      boolean forceCopy;
      int size;

      ArrayBasedBuilder(int var1) {
         CollectPreconditions.checkNonnegative(var1, "initialCapacity");
         this.contents = new Object[var1];
         this.size = 0;
      }

      private void getReadyToExpandTo(int var1) {
         Object[] var2 = this.contents;
         if (var2.length < var1) {
            this.contents = Arrays.copyOf(var2, expandedCapacity(var2.length, var1));
            this.forceCopy = false;
         } else if (this.forceCopy) {
            this.contents = (Object[])var2.clone();
            this.forceCopy = false;
         }

      }

      public ImmutableCollection.ArrayBasedBuilder<E> add(E var1) {
         Preconditions.checkNotNull(var1);
         this.getReadyToExpandTo(this.size + 1);
         Object[] var2 = this.contents;
         int var3 = this.size++;
         var2[var3] = var1;
         return this;
      }

      public ImmutableCollection.Builder<E> add(E... var1) {
         ObjectArrays.checkElementsNotNull(var1);
         this.getReadyToExpandTo(this.size + var1.length);
         System.arraycopy(var1, 0, this.contents, this.size, var1.length);
         this.size += var1.length;
         return this;
      }

      public ImmutableCollection.Builder<E> addAll(Iterable<? extends E> var1) {
         if (var1 instanceof Collection) {
            Collection var2 = (Collection)var1;
            this.getReadyToExpandTo(this.size + var2.size());
            if (var2 instanceof ImmutableCollection) {
               this.size = ((ImmutableCollection)var2).copyIntoArray(this.contents, this.size);
               return this;
            }
         }

         super.addAll(var1);
         return this;
      }
   }

   @DoNotMock
   public abstract static class Builder<E> {
      static final int DEFAULT_INITIAL_CAPACITY = 4;

      Builder() {
      }

      static int expandedCapacity(int var0, int var1) {
         if (var1 >= 0) {
            int var2 = var0 + (var0 >> 1) + 1;
            var0 = var2;
            if (var2 < var1) {
               var0 = Integer.highestOneBit(var1 - 1) << 1;
            }

            var1 = var0;
            if (var0 < 0) {
               var1 = Integer.MAX_VALUE;
            }

            return var1;
         } else {
            throw new AssertionError("cannot store more than MAX_VALUE elements");
         }
      }

      public abstract ImmutableCollection.Builder<E> add(E var1);

      public ImmutableCollection.Builder<E> add(E... var1) {
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            this.add(var1[var3]);
         }

         return this;
      }

      public ImmutableCollection.Builder<E> addAll(Iterable<? extends E> var1) {
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            this.add(var2.next());
         }

         return this;
      }

      public ImmutableCollection.Builder<E> addAll(Iterator<? extends E> var1) {
         while(var1.hasNext()) {
            this.add(var1.next());
         }

         return this;
      }

      public abstract ImmutableCollection<E> build();
   }
}
