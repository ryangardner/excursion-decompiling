package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ImmutableList<E> extends ImmutableCollection<E> implements List<E>, RandomAccess {
   private static final UnmodifiableListIterator<Object> EMPTY_ITR;

   static {
      EMPTY_ITR = new ImmutableList.Itr(RegularImmutableList.EMPTY, 0);
   }

   ImmutableList() {
   }

   static <E> ImmutableList<E> asImmutableList(Object[] var0) {
      return asImmutableList(var0, var0.length);
   }

   static <E> ImmutableList<E> asImmutableList(Object[] var0, int var1) {
      return (ImmutableList)(var1 == 0 ? of() : new RegularImmutableList(var0, var1));
   }

   public static <E> ImmutableList.Builder<E> builder() {
      return new ImmutableList.Builder();
   }

   public static <E> ImmutableList.Builder<E> builderWithExpectedSize(int var0) {
      CollectPreconditions.checkNonnegative(var0, "expectedSize");
      return new ImmutableList.Builder(var0);
   }

   private static <E> ImmutableList<E> construct(Object... var0) {
      return asImmutableList(ObjectArrays.checkElementsNotNull(var0));
   }

   public static <E> ImmutableList<E> copyOf(Iterable<? extends E> var0) {
      Preconditions.checkNotNull(var0);
      ImmutableList var1;
      if (var0 instanceof Collection) {
         var1 = copyOf((Collection)var0);
      } else {
         var1 = copyOf(var0.iterator());
      }

      return var1;
   }

   public static <E> ImmutableList<E> copyOf(Collection<? extends E> var0) {
      if (var0 instanceof ImmutableCollection) {
         ImmutableList var1 = ((ImmutableCollection)var0).asList();
         ImmutableList var2 = var1;
         if (var1.isPartialView()) {
            var2 = asImmutableList(var1.toArray());
         }

         return var2;
      } else {
         return construct(var0.toArray());
      }
   }

   public static <E> ImmutableList<E> copyOf(Iterator<? extends E> var0) {
      if (!var0.hasNext()) {
         return of();
      } else {
         Object var1 = var0.next();
         return !var0.hasNext() ? of(var1) : (new ImmutableList.Builder()).add(var1).addAll(var0).build();
      }
   }

   public static <E> ImmutableList<E> copyOf(E[] var0) {
      ImmutableList var1;
      if (var0.length == 0) {
         var1 = of();
      } else {
         var1 = construct((Object[])var0.clone());
      }

      return var1;
   }

   public static <E> ImmutableList<E> of() {
      return RegularImmutableList.EMPTY;
   }

   public static <E> ImmutableList<E> of(E var0) {
      return construct(var0);
   }

   public static <E> ImmutableList<E> of(E var0, E var1) {
      return construct(var0, var1);
   }

   public static <E> ImmutableList<E> of(E var0, E var1, E var2) {
      return construct(var0, var1, var2);
   }

   public static <E> ImmutableList<E> of(E var0, E var1, E var2, E var3) {
      return construct(var0, var1, var2, var3);
   }

   public static <E> ImmutableList<E> of(E var0, E var1, E var2, E var3, E var4) {
      return construct(var0, var1, var2, var3, var4);
   }

   public static <E> ImmutableList<E> of(E var0, E var1, E var2, E var3, E var4, E var5) {
      return construct(var0, var1, var2, var3, var4, var5);
   }

   public static <E> ImmutableList<E> of(E var0, E var1, E var2, E var3, E var4, E var5, E var6) {
      return construct(var0, var1, var2, var3, var4, var5, var6);
   }

   public static <E> ImmutableList<E> of(E var0, E var1, E var2, E var3, E var4, E var5, E var6, E var7) {
      return construct(var0, var1, var2, var3, var4, var5, var6, var7);
   }

   public static <E> ImmutableList<E> of(E var0, E var1, E var2, E var3, E var4, E var5, E var6, E var7, E var8) {
      return construct(var0, var1, var2, var3, var4, var5, var6, var7, var8);
   }

   public static <E> ImmutableList<E> of(E var0, E var1, E var2, E var3, E var4, E var5, E var6, E var7, E var8, E var9) {
      return construct(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }

   public static <E> ImmutableList<E> of(E var0, E var1, E var2, E var3, E var4, E var5, E var6, E var7, E var8, E var9, E var10) {
      return construct(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10);
   }

   @SafeVarargs
   public static <E> ImmutableList<E> of(E var0, E var1, E var2, E var3, E var4, E var5, E var6, E var7, E var8, E var9, E var10, E var11, E... var12) {
      boolean var13;
      if (var12.length <= 2147483635) {
         var13 = true;
      } else {
         var13 = false;
      }

      Preconditions.checkArgument(var13, "the total number of elements must fit in an int");
      Object[] var14 = new Object[var12.length + 12];
      var14[0] = var0;
      var14[1] = var1;
      var14[2] = var2;
      var14[3] = var3;
      var14[4] = var4;
      var14[5] = var5;
      var14[6] = var6;
      var14[7] = var7;
      var14[8] = var8;
      var14[9] = var9;
      var14[10] = var10;
      var14[11] = var11;
      System.arraycopy(var12, 0, var14, 12, var12.length);
      return construct(var14);
   }

   private void readObject(ObjectInputStream var1) throws InvalidObjectException {
      throw new InvalidObjectException("Use SerializedForm");
   }

   public static <E extends Comparable<? super E>> ImmutableList<E> sortedCopyOf(Iterable<? extends E> var0) {
      Comparable[] var1 = (Comparable[])Iterables.toArray(var0, (Object[])(new Comparable[0]));
      ObjectArrays.checkElementsNotNull((Object[])var1);
      Arrays.sort(var1);
      return asImmutableList(var1);
   }

   public static <E> ImmutableList<E> sortedCopyOf(Comparator<? super E> var0, Iterable<? extends E> var1) {
      Preconditions.checkNotNull(var0);
      Object[] var2 = (Object[])Iterables.toArray(var1);
      ObjectArrays.checkElementsNotNull(var2);
      Arrays.sort(var2, var0);
      return asImmutableList(var2);
   }

   @Deprecated
   public final void add(int var1, E var2) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public final boolean addAll(int var1, Collection<? extends E> var2) {
      throw new UnsupportedOperationException();
   }

   public final ImmutableList<E> asList() {
      return this;
   }

   public boolean contains(@NullableDecl Object var1) {
      boolean var2;
      if (this.indexOf(var1) >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   int copyIntoArray(Object[] var1, int var2) {
      int var3 = this.size();

      for(int var4 = 0; var4 < var3; ++var4) {
         var1[var2 + var4] = this.get(var4);
      }

      return var2 + var3;
   }

   public boolean equals(@NullableDecl Object var1) {
      return Lists.equalsImpl(this, var1);
   }

   public int hashCode() {
      int var1 = this.size();
      int var2 = 1;

      for(int var3 = 0; var3 < var1; ++var3) {
         var2 = var2 * 31 + this.get(var3).hashCode();
      }

      return var2;
   }

   public int indexOf(@NullableDecl Object var1) {
      int var2;
      if (var1 == null) {
         var2 = -1;
      } else {
         var2 = Lists.indexOfImpl(this, var1);
      }

      return var2;
   }

   public UnmodifiableIterator<E> iterator() {
      return this.listIterator();
   }

   public int lastIndexOf(@NullableDecl Object var1) {
      int var2;
      if (var1 == null) {
         var2 = -1;
      } else {
         var2 = Lists.lastIndexOfImpl(this, var1);
      }

      return var2;
   }

   public UnmodifiableListIterator<E> listIterator() {
      return this.listIterator(0);
   }

   public UnmodifiableListIterator<E> listIterator(int var1) {
      Preconditions.checkPositionIndex(var1, this.size());
      return (UnmodifiableListIterator)(this.isEmpty() ? EMPTY_ITR : new ImmutableList.Itr(this, var1));
   }

   @Deprecated
   public final E remove(int var1) {
      throw new UnsupportedOperationException();
   }

   public ImmutableList<E> reverse() {
      Object var1;
      if (this.size() <= 1) {
         var1 = this;
      } else {
         var1 = new ImmutableList.ReverseImmutableList(this);
      }

      return (ImmutableList)var1;
   }

   @Deprecated
   public final E set(int var1, E var2) {
      throw new UnsupportedOperationException();
   }

   public ImmutableList<E> subList(int var1, int var2) {
      Preconditions.checkPositionIndexes(var1, var2, this.size());
      int var3 = var2 - var1;
      if (var3 == this.size()) {
         return this;
      } else {
         return var3 == 0 ? of() : this.subListUnchecked(var1, var2);
      }
   }

   ImmutableList<E> subListUnchecked(int var1, int var2) {
      return new ImmutableList.SubList(var1, var2 - var1);
   }

   Object writeReplace() {
      return new ImmutableList.SerializedForm(this.toArray());
   }

   public static final class Builder<E> extends ImmutableCollection.ArrayBasedBuilder<E> {
      public Builder() {
         this(4);
      }

      Builder(int var1) {
         super(var1);
      }

      public ImmutableList.Builder<E> add(E var1) {
         super.add(var1);
         return this;
      }

      public ImmutableList.Builder<E> add(E... var1) {
         super.add(var1);
         return this;
      }

      public ImmutableList.Builder<E> addAll(Iterable<? extends E> var1) {
         super.addAll(var1);
         return this;
      }

      public ImmutableList.Builder<E> addAll(Iterator<? extends E> var1) {
         super.addAll(var1);
         return this;
      }

      public ImmutableList<E> build() {
         this.forceCopy = true;
         return ImmutableList.asImmutableList(this.contents, this.size);
      }
   }

   static class Itr<E> extends AbstractIndexedListIterator<E> {
      private final ImmutableList<E> list;

      Itr(ImmutableList<E> var1, int var2) {
         super(var1.size(), var2);
         this.list = var1;
      }

      protected E get(int var1) {
         return this.list.get(var1);
      }
   }

   private static class ReverseImmutableList<E> extends ImmutableList<E> {
      private final transient ImmutableList<E> forwardList;

      ReverseImmutableList(ImmutableList<E> var1) {
         this.forwardList = var1;
      }

      private int reverseIndex(int var1) {
         return this.size() - 1 - var1;
      }

      private int reversePosition(int var1) {
         return this.size() - var1;
      }

      public boolean contains(@NullableDecl Object var1) {
         return this.forwardList.contains(var1);
      }

      public E get(int var1) {
         Preconditions.checkElementIndex(var1, this.size());
         return this.forwardList.get(this.reverseIndex(var1));
      }

      public int indexOf(@NullableDecl Object var1) {
         int var2 = this.forwardList.lastIndexOf(var1);
         if (var2 >= 0) {
            var2 = this.reverseIndex(var2);
         } else {
            var2 = -1;
         }

         return var2;
      }

      boolean isPartialView() {
         return this.forwardList.isPartialView();
      }

      public int lastIndexOf(@NullableDecl Object var1) {
         int var2 = this.forwardList.indexOf(var1);
         if (var2 >= 0) {
            var2 = this.reverseIndex(var2);
         } else {
            var2 = -1;
         }

         return var2;
      }

      public ImmutableList<E> reverse() {
         return this.forwardList;
      }

      public int size() {
         return this.forwardList.size();
      }

      public ImmutableList<E> subList(int var1, int var2) {
         Preconditions.checkPositionIndexes(var1, var2, this.size());
         return this.forwardList.subList(this.reversePosition(var2), this.reversePosition(var1)).reverse();
      }
   }

   static class SerializedForm implements Serializable {
      private static final long serialVersionUID = 0L;
      final Object[] elements;

      SerializedForm(Object[] var1) {
         this.elements = var1;
      }

      Object readResolve() {
         return ImmutableList.copyOf(this.elements);
      }
   }

   class SubList extends ImmutableList<E> {
      final transient int length;
      final transient int offset;

      SubList(int var2, int var3) {
         this.offset = var2;
         this.length = var3;
      }

      public E get(int var1) {
         Preconditions.checkElementIndex(var1, this.length);
         return ImmutableList.this.get(var1 + this.offset);
      }

      Object[] internalArray() {
         return ImmutableList.this.internalArray();
      }

      int internalArrayEnd() {
         return ImmutableList.this.internalArrayStart() + this.offset + this.length;
      }

      int internalArrayStart() {
         return ImmutableList.this.internalArrayStart() + this.offset;
      }

      boolean isPartialView() {
         return true;
      }

      public int size() {
         return this.length;
      }

      public ImmutableList<E> subList(int var1, int var2) {
         Preconditions.checkPositionIndexes(var1, var2, this.length);
         ImmutableList var3 = ImmutableList.this;
         int var4 = this.offset;
         return var3.subList(var1 + var4, var2 + var4);
      }
   }
}
