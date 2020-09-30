package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.Collection;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class RegularContiguousSet<C extends Comparable> extends ContiguousSet<C> {
   private static final long serialVersionUID = 0L;
   private final Range<C> range;

   RegularContiguousSet(Range<C> var1, DiscreteDomain<C> var2) {
      super(var2);
      this.range = var1;
   }

   private static boolean equalsOrThrow(Comparable<?> var0, @NullableDecl Comparable<?> var1) {
      boolean var2;
      if (var1 != null && Range.compareOrThrow(var0, var1) == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private ContiguousSet<C> intersectionInCurrentDomain(Range<C> var1) {
      Object var2;
      if (this.range.isConnected(var1)) {
         var2 = ContiguousSet.create(this.range.intersection(var1), this.domain);
      } else {
         var2 = new EmptyContiguousSet(this.domain);
      }

      return (ContiguousSet)var2;
   }

   public boolean contains(@NullableDecl Object var1) {
      if (var1 == null) {
         return false;
      } else {
         try {
            boolean var2 = this.range.contains((Comparable)var1);
            return var2;
         } catch (ClassCastException var3) {
            return false;
         }
      }
   }

   public boolean containsAll(Collection<?> var1) {
      return Collections2.containsAllImpl(this, var1);
   }

   ImmutableList<C> createAsList() {
      return (ImmutableList)(this.domain.supportsFastOffset ? new ImmutableAsList<C>() {
         ImmutableSortedSet<C> delegateCollection() {
            return RegularContiguousSet.this;
         }

         public C get(int var1) {
            Preconditions.checkElementIndex(var1, this.size());
            return RegularContiguousSet.this.domain.offset(RegularContiguousSet.this.first(), (long)var1);
         }
      } : super.createAsList());
   }

   public UnmodifiableIterator<C> descendingIterator() {
      return new AbstractSequentialIterator<C>(this.last()) {
         final C first = RegularContiguousSet.this.first();

         protected C computeNext(C var1) {
            if (RegularContiguousSet.equalsOrThrow(var1, this.first)) {
               var1 = null;
            } else {
               var1 = RegularContiguousSet.this.domain.previous(var1);
            }

            return var1;
         }
      };
   }

   public boolean equals(@NullableDecl Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else {
         if (var1 instanceof RegularContiguousSet) {
            RegularContiguousSet var3 = (RegularContiguousSet)var1;
            if (this.domain.equals(var3.domain)) {
               if (!this.first().equals(var3.first()) || !this.last().equals(var3.last())) {
                  var2 = false;
               }

               return var2;
            }
         }

         return super.equals(var1);
      }
   }

   public C first() {
      return this.range.lowerBound.leastValueAbove(this.domain);
   }

   public int hashCode() {
      return Sets.hashCodeImpl(this);
   }

   ContiguousSet<C> headSetImpl(C var1, boolean var2) {
      return this.intersectionInCurrentDomain(Range.upTo(var1, BoundType.forBoolean(var2)));
   }

   int indexOf(Object var1) {
      int var2;
      if (this.contains(var1)) {
         var2 = (int)this.domain.distance(this.first(), (Comparable)var1);
      } else {
         var2 = -1;
      }

      return var2;
   }

   public ContiguousSet<C> intersection(ContiguousSet<C> var1) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkArgument(this.domain.equals(var1.domain));
      if (var1.isEmpty()) {
         return var1;
      } else {
         Comparable var2 = (Comparable)Ordering.natural().max(this.first(), var1.first());
         Comparable var3 = (Comparable)Ordering.natural().min(this.last(), var1.last());
         Object var4;
         if (var2.compareTo(var3) <= 0) {
            var4 = ContiguousSet.create(Range.closed(var2, var3), this.domain);
         } else {
            var4 = new EmptyContiguousSet(this.domain);
         }

         return (ContiguousSet)var4;
      }
   }

   public boolean isEmpty() {
      return false;
   }

   boolean isPartialView() {
      return false;
   }

   public UnmodifiableIterator<C> iterator() {
      return new AbstractSequentialIterator<C>(this.first()) {
         final C last = RegularContiguousSet.this.last();

         protected C computeNext(C var1) {
            if (RegularContiguousSet.equalsOrThrow(var1, this.last)) {
               var1 = null;
            } else {
               var1 = RegularContiguousSet.this.domain.next(var1);
            }

            return var1;
         }
      };
   }

   public C last() {
      return this.range.upperBound.greatestValueBelow(this.domain);
   }

   public Range<C> range() {
      return this.range(BoundType.CLOSED, BoundType.CLOSED);
   }

   public Range<C> range(BoundType var1, BoundType var2) {
      return Range.create(this.range.lowerBound.withLowerBoundType(var1, this.domain), this.range.upperBound.withUpperBoundType(var2, this.domain));
   }

   public int size() {
      long var1 = this.domain.distance(this.first(), this.last());
      int var3;
      if (var1 >= 2147483647L) {
         var3 = Integer.MAX_VALUE;
      } else {
         var3 = (int)var1 + 1;
      }

      return var3;
   }

   ContiguousSet<C> subSetImpl(C var1, boolean var2, C var3, boolean var4) {
      return (ContiguousSet)(var1.compareTo(var3) == 0 && !var2 && !var4 ? new EmptyContiguousSet(this.domain) : this.intersectionInCurrentDomain(Range.range(var1, BoundType.forBoolean(var2), var3, BoundType.forBoolean(var4))));
   }

   ContiguousSet<C> tailSetImpl(C var1, boolean var2) {
      return this.intersectionInCurrentDomain(Range.downTo(var1, BoundType.forBoolean(var2)));
   }

   Object writeReplace() {
      return new RegularContiguousSet.SerializedForm(this.range, this.domain);
   }

   private static final class SerializedForm<C extends Comparable> implements Serializable {
      final DiscreteDomain<C> domain;
      final Range<C> range;

      private SerializedForm(Range<C> var1, DiscreteDomain<C> var2) {
         this.range = var1;
         this.domain = var2;
      }

      // $FF: synthetic method
      SerializedForm(Range var1, DiscreteDomain var2, Object var3) {
         this(var1, var2);
      }

      private Object readResolve() {
         return new RegularContiguousSet(this.range, this.domain);
      }
   }
}
