package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ImmutableMultiset<E> extends ImmutableMultisetGwtSerializationDependencies<E> implements Multiset<E> {
   @LazyInit
   private transient ImmutableList<E> asList;
   @LazyInit
   private transient ImmutableSet<Multiset.Entry<E>> entrySet;

   ImmutableMultiset() {
   }

   public static <E> ImmutableMultiset.Builder<E> builder() {
      return new ImmutableMultiset.Builder();
   }

   private static <E> ImmutableMultiset<E> copyFromElements(E... var0) {
      return (new ImmutableMultiset.Builder()).add(var0).build();
   }

   static <E> ImmutableMultiset<E> copyFromEntries(Collection<? extends Multiset.Entry<? extends E>> var0) {
      ImmutableMultiset.Builder var1 = new ImmutableMultiset.Builder(var0.size());
      Iterator var3 = var0.iterator();

      while(var3.hasNext()) {
         Multiset.Entry var2 = (Multiset.Entry)var3.next();
         var1.addCopies(var2.getElement(), var2.getCount());
      }

      return var1.build();
   }

   public static <E> ImmutableMultiset<E> copyOf(Iterable<? extends E> var0) {
      if (var0 instanceof ImmutableMultiset) {
         ImmutableMultiset var1 = (ImmutableMultiset)var0;
         if (!var1.isPartialView()) {
            return var1;
         }
      }

      ImmutableMultiset.Builder var2 = new ImmutableMultiset.Builder(Multisets.inferDistinctElements(var0));
      var2.addAll(var0);
      return var2.build();
   }

   public static <E> ImmutableMultiset<E> copyOf(Iterator<? extends E> var0) {
      return (new ImmutableMultiset.Builder()).addAll(var0).build();
   }

   public static <E> ImmutableMultiset<E> copyOf(E[] var0) {
      return copyFromElements(var0);
   }

   private ImmutableSet<Multiset.Entry<E>> createEntrySet() {
      Object var1;
      if (this.isEmpty()) {
         var1 = ImmutableSet.of();
      } else {
         var1 = new ImmutableMultiset.EntrySet();
      }

      return (ImmutableSet)var1;
   }

   public static <E> ImmutableMultiset<E> of() {
      return RegularImmutableMultiset.EMPTY;
   }

   public static <E> ImmutableMultiset<E> of(E var0) {
      return copyFromElements(var0);
   }

   public static <E> ImmutableMultiset<E> of(E var0, E var1) {
      return copyFromElements(var0, var1);
   }

   public static <E> ImmutableMultiset<E> of(E var0, E var1, E var2) {
      return copyFromElements(var0, var1, var2);
   }

   public static <E> ImmutableMultiset<E> of(E var0, E var1, E var2, E var3) {
      return copyFromElements(var0, var1, var2, var3);
   }

   public static <E> ImmutableMultiset<E> of(E var0, E var1, E var2, E var3, E var4) {
      return copyFromElements(var0, var1, var2, var3, var4);
   }

   public static <E> ImmutableMultiset<E> of(E var0, E var1, E var2, E var3, E var4, E var5, E... var6) {
      return (new ImmutableMultiset.Builder()).add(var0).add(var1).add(var2).add(var3).add(var4).add(var5).add(var6).build();
   }

   @Deprecated
   public final int add(E var1, int var2) {
      throw new UnsupportedOperationException();
   }

   public ImmutableList<E> asList() {
      ImmutableList var1 = this.asList;
      ImmutableList var2 = var1;
      if (var1 == null) {
         var2 = super.asList();
         this.asList = var2;
      }

      return var2;
   }

   public boolean contains(@NullableDecl Object var1) {
      boolean var2;
      if (this.count(var1) > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   int copyIntoArray(Object[] var1, int var2) {
      Multiset.Entry var4;
      for(UnmodifiableIterator var3 = this.entrySet().iterator(); var3.hasNext(); var2 += var4.getCount()) {
         var4 = (Multiset.Entry)var3.next();
         Arrays.fill(var1, var2, var4.getCount() + var2, var4.getElement());
      }

      return var2;
   }

   public abstract ImmutableSet<E> elementSet();

   public ImmutableSet<Multiset.Entry<E>> entrySet() {
      ImmutableSet var1 = this.entrySet;
      ImmutableSet var2 = var1;
      if (var1 == null) {
         var2 = this.createEntrySet();
         this.entrySet = var2;
      }

      return var2;
   }

   public boolean equals(@NullableDecl Object var1) {
      return Multisets.equalsImpl(this, var1);
   }

   abstract Multiset.Entry<E> getEntry(int var1);

   public int hashCode() {
      return Sets.hashCodeImpl(this.entrySet());
   }

   public UnmodifiableIterator<E> iterator() {
      return new UnmodifiableIterator<E>(this.entrySet().iterator()) {
         @MonotonicNonNullDecl
         E element;
         int remaining;
         // $FF: synthetic field
         final Iterator val$entryIterator;

         {
            this.val$entryIterator = var2;
         }

         public boolean hasNext() {
            boolean var1;
            if (this.remaining <= 0 && !this.val$entryIterator.hasNext()) {
               var1 = false;
            } else {
               var1 = true;
            }

            return var1;
         }

         public E next() {
            if (this.remaining <= 0) {
               Multiset.Entry var1 = (Multiset.Entry)this.val$entryIterator.next();
               this.element = var1.getElement();
               this.remaining = var1.getCount();
            }

            --this.remaining;
            return this.element;
         }
      };
   }

   @Deprecated
   public final int remove(Object var1, int var2) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public final int setCount(E var1, int var2) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public final boolean setCount(E var1, int var2, int var3) {
      throw new UnsupportedOperationException();
   }

   public String toString() {
      return this.entrySet().toString();
   }

   abstract Object writeReplace();

   public static class Builder<E> extends ImmutableCollection.Builder<E> {
      boolean buildInvoked;
      ObjectCountHashMap<E> contents;
      boolean isLinkedHash;

      public Builder() {
         this(4);
      }

      Builder(int var1) {
         this.buildInvoked = false;
         this.isLinkedHash = false;
         this.contents = ObjectCountHashMap.createWithExpectedSize(var1);
      }

      Builder(boolean var1) {
         this.buildInvoked = false;
         this.isLinkedHash = false;
         this.contents = null;
      }

      @NullableDecl
      static <T> ObjectCountHashMap<T> tryGetMap(Iterable<T> var0) {
         if (var0 instanceof RegularImmutableMultiset) {
            return ((RegularImmutableMultiset)var0).contents;
         } else {
            return var0 instanceof AbstractMapBasedMultiset ? ((AbstractMapBasedMultiset)var0).backingMap : null;
         }
      }

      public ImmutableMultiset.Builder<E> add(E var1) {
         return this.addCopies(var1, 1);
      }

      public ImmutableMultiset.Builder<E> add(E... var1) {
         super.add(var1);
         return this;
      }

      public ImmutableMultiset.Builder<E> addAll(Iterable<? extends E> var1) {
         if (var1 instanceof Multiset) {
            Multiset var5 = Multisets.cast(var1);
            ObjectCountHashMap var2 = tryGetMap(var5);
            if (var2 != null) {
               ObjectCountHashMap var6 = this.contents;
               var6.ensureCapacity(Math.max(var6.size(), var2.size()));

               for(int var3 = var2.firstIndex(); var3 >= 0; var3 = var2.nextIndex(var3)) {
                  this.addCopies(var2.getKey(var3), var2.getValue(var3));
               }
            } else {
               Set var4 = var5.entrySet();
               var2 = this.contents;
               var2.ensureCapacity(Math.max(var2.size(), var4.size()));
               Iterator var8 = var5.entrySet().iterator();

               while(var8.hasNext()) {
                  Multiset.Entry var7 = (Multiset.Entry)var8.next();
                  this.addCopies(var7.getElement(), var7.getCount());
               }
            }
         } else {
            super.addAll(var1);
         }

         return this;
      }

      public ImmutableMultiset.Builder<E> addAll(Iterator<? extends E> var1) {
         super.addAll(var1);
         return this;
      }

      public ImmutableMultiset.Builder<E> addCopies(E var1, int var2) {
         if (var2 == 0) {
            return this;
         } else {
            if (this.buildInvoked) {
               this.contents = new ObjectCountHashMap(this.contents);
               this.isLinkedHash = false;
            }

            this.buildInvoked = false;
            Preconditions.checkNotNull(var1);
            ObjectCountHashMap var3 = this.contents;
            var3.put(var1, var2 + var3.get(var1));
            return this;
         }
      }

      public ImmutableMultiset<E> build() {
         if (this.contents.size() == 0) {
            return ImmutableMultiset.of();
         } else {
            if (this.isLinkedHash) {
               this.contents = new ObjectCountHashMap(this.contents);
               this.isLinkedHash = false;
            }

            this.buildInvoked = true;
            return new RegularImmutableMultiset(this.contents);
         }
      }

      public ImmutableMultiset.Builder<E> setCount(E var1, int var2) {
         if (var2 == 0 && !this.isLinkedHash) {
            this.contents = new ObjectCountLinkedHashMap(this.contents);
            this.isLinkedHash = true;
         } else if (this.buildInvoked) {
            this.contents = new ObjectCountHashMap(this.contents);
            this.isLinkedHash = false;
         }

         this.buildInvoked = false;
         Preconditions.checkNotNull(var1);
         if (var2 == 0) {
            this.contents.remove(var1);
         } else {
            this.contents.put(Preconditions.checkNotNull(var1), var2);
         }

         return this;
      }
   }

   private final class EntrySet extends IndexedImmutableSet<Multiset.Entry<E>> {
      private static final long serialVersionUID = 0L;

      private EntrySet() {
      }

      // $FF: synthetic method
      EntrySet(Object var2) {
         this();
      }

      public boolean contains(Object var1) {
         boolean var2 = var1 instanceof Multiset.Entry;
         boolean var3 = false;
         boolean var4 = var3;
         if (var2) {
            Multiset.Entry var5 = (Multiset.Entry)var1;
            if (var5.getCount() <= 0) {
               return false;
            }

            var4 = var3;
            if (ImmutableMultiset.this.count(var5.getElement()) == var5.getCount()) {
               var4 = true;
            }
         }

         return var4;
      }

      Multiset.Entry<E> get(int var1) {
         return ImmutableMultiset.this.getEntry(var1);
      }

      public int hashCode() {
         return ImmutableMultiset.this.hashCode();
      }

      boolean isPartialView() {
         return ImmutableMultiset.this.isPartialView();
      }

      public int size() {
         return ImmutableMultiset.this.elementSet().size();
      }

      Object writeReplace() {
         return new ImmutableMultiset.EntrySetSerializedForm(ImmutableMultiset.this);
      }
   }

   static class EntrySetSerializedForm<E> implements Serializable {
      final ImmutableMultiset<E> multiset;

      EntrySetSerializedForm(ImmutableMultiset<E> var1) {
         this.multiset = var1;
      }

      Object readResolve() {
         return this.multiset.entrySet();
      }
   }
}
