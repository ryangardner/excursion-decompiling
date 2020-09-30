package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class ImmutableRangeSet<C extends Comparable> extends AbstractRangeSet<C> implements Serializable {
   private static final ImmutableRangeSet<Comparable<?>> ALL = new ImmutableRangeSet(ImmutableList.of(Range.all()));
   private static final ImmutableRangeSet<Comparable<?>> EMPTY = new ImmutableRangeSet(ImmutableList.of());
   @LazyInit
   private transient ImmutableRangeSet<C> complement;
   private final transient ImmutableList<Range<C>> ranges;

   ImmutableRangeSet(ImmutableList<Range<C>> var1) {
      this.ranges = var1;
   }

   private ImmutableRangeSet(ImmutableList<Range<C>> var1, ImmutableRangeSet<C> var2) {
      this.ranges = var1;
      this.complement = var2;
   }

   static <C extends Comparable> ImmutableRangeSet<C> all() {
      return ALL;
   }

   public static <C extends Comparable<?>> ImmutableRangeSet.Builder<C> builder() {
      return new ImmutableRangeSet.Builder();
   }

   public static <C extends Comparable> ImmutableRangeSet<C> copyOf(RangeSet<C> var0) {
      Preconditions.checkNotNull(var0);
      if (var0.isEmpty()) {
         return of();
      } else if (var0.encloses(Range.all())) {
         return all();
      } else {
         if (var0 instanceof ImmutableRangeSet) {
            ImmutableRangeSet var1 = (ImmutableRangeSet)var0;
            if (!var1.isPartialView()) {
               return var1;
            }
         }

         return new ImmutableRangeSet(ImmutableList.copyOf((Collection)var0.asRanges()));
      }
   }

   public static <C extends Comparable<?>> ImmutableRangeSet<C> copyOf(Iterable<Range<C>> var0) {
      return (new ImmutableRangeSet.Builder()).addAll(var0).build();
   }

   private ImmutableList<Range<C>> intersectRanges(final Range<C> var1) {
      if (!this.ranges.isEmpty() && !var1.isEmpty()) {
         if (var1.encloses(this.span())) {
            return this.ranges;
         } else {
            final int var2;
            if (var1.hasLowerBound()) {
               var2 = SortedLists.binarySearch(this.ranges, (Function)Range.upperBoundFn(), (Comparable)var1.lowerBound, SortedLists.KeyPresentBehavior.FIRST_AFTER, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
            } else {
               var2 = 0;
            }

            final int var3;
            if (var1.hasUpperBound()) {
               var3 = SortedLists.binarySearch(this.ranges, (Function)Range.lowerBoundFn(), (Comparable)var1.upperBound, SortedLists.KeyPresentBehavior.FIRST_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
            } else {
               var3 = this.ranges.size();
            }

            var3 -= var2;
            return var3 == 0 ? ImmutableList.of() : new ImmutableList<Range<C>>() {
               public Range<C> get(int var1x) {
                  Preconditions.checkElementIndex(var1x, var3);
                  return var1x != 0 && var1x != var3 - 1 ? (Range)ImmutableRangeSet.this.ranges.get(var1x + var2) : ((Range)ImmutableRangeSet.this.ranges.get(var1x + var2)).intersection(var1);
               }

               boolean isPartialView() {
                  return true;
               }

               public int size() {
                  return var3;
               }
            };
         }
      } else {
         return ImmutableList.of();
      }
   }

   public static <C extends Comparable> ImmutableRangeSet<C> of() {
      return EMPTY;
   }

   public static <C extends Comparable> ImmutableRangeSet<C> of(Range<C> var0) {
      Preconditions.checkNotNull(var0);
      if (var0.isEmpty()) {
         return of();
      } else {
         return var0.equals(Range.all()) ? all() : new ImmutableRangeSet(ImmutableList.of(var0));
      }
   }

   public static <C extends Comparable<?>> ImmutableRangeSet<C> unionOf(Iterable<Range<C>> var0) {
      return copyOf((RangeSet)TreeRangeSet.create(var0));
   }

   @Deprecated
   public void add(Range<C> var1) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public void addAll(RangeSet<C> var1) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public void addAll(Iterable<Range<C>> var1) {
      throw new UnsupportedOperationException();
   }

   public ImmutableSet<Range<C>> asDescendingSetOfRanges() {
      return (ImmutableSet)(this.ranges.isEmpty() ? ImmutableSet.of() : new RegularImmutableSortedSet(this.ranges.reverse(), Range.rangeLexOrdering().reverse()));
   }

   public ImmutableSet<Range<C>> asRanges() {
      return (ImmutableSet)(this.ranges.isEmpty() ? ImmutableSet.of() : new RegularImmutableSortedSet(this.ranges, Range.rangeLexOrdering()));
   }

   public ImmutableSortedSet<C> asSet(DiscreteDomain<C> var1) {
      Preconditions.checkNotNull(var1);
      if (this.isEmpty()) {
         return ImmutableSortedSet.of();
      } else {
         Range var2 = this.span().canonical(var1);
         if (var2.hasLowerBound()) {
            if (!var2.hasUpperBound()) {
               try {
                  var1.maxValue();
               } catch (NoSuchElementException var3) {
                  throw new IllegalArgumentException("Neither the DiscreteDomain nor this range set are bounded above");
               }
            }

            return new ImmutableRangeSet.AsSet(var1);
         } else {
            throw new IllegalArgumentException("Neither the DiscreteDomain nor this range set are bounded below");
         }
      }
   }

   public ImmutableRangeSet<C> complement() {
      ImmutableRangeSet var1 = this.complement;
      if (var1 != null) {
         return var1;
      } else if (this.ranges.isEmpty()) {
         var1 = all();
         this.complement = var1;
         return var1;
      } else if (this.ranges.size() == 1 && ((Range)this.ranges.get(0)).equals(Range.all())) {
         var1 = of();
         this.complement = var1;
         return var1;
      } else {
         var1 = new ImmutableRangeSet(new ImmutableRangeSet.ComplementRanges(), this);
         this.complement = var1;
         return var1;
      }
   }

   public ImmutableRangeSet<C> difference(RangeSet<C> var1) {
      TreeRangeSet var2 = TreeRangeSet.create((RangeSet)this);
      var2.removeAll(var1);
      return copyOf((RangeSet)var2);
   }

   public boolean encloses(Range<C> var1) {
      int var2 = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), var1.lowerBound, Ordering.natural(), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER);
      boolean var3;
      if (var2 != -1 && ((Range)this.ranges.get(var2)).encloses(var1)) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public ImmutableRangeSet<C> intersection(RangeSet<C> var1) {
      TreeRangeSet var2 = TreeRangeSet.create((RangeSet)this);
      var2.removeAll(var1.complement());
      return copyOf((RangeSet)var2);
   }

   public boolean intersects(Range<C> var1) {
      int var2 = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), var1.lowerBound, Ordering.natural(), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
      int var3 = this.ranges.size();
      boolean var4 = true;
      if (var2 < var3 && ((Range)this.ranges.get(var2)).isConnected(var1) && !((Range)this.ranges.get(var2)).intersection(var1).isEmpty()) {
         return true;
      } else {
         if (var2 > 0) {
            ImmutableList var5 = this.ranges;
            --var2;
            if (((Range)var5.get(var2)).isConnected(var1) && !((Range)this.ranges.get(var2)).intersection(var1).isEmpty()) {
               return var4;
            }
         }

         var4 = false;
         return var4;
      }
   }

   public boolean isEmpty() {
      return this.ranges.isEmpty();
   }

   boolean isPartialView() {
      return this.ranges.isPartialView();
   }

   public Range<C> rangeContaining(C var1) {
      int var2 = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), Cut.belowValue(var1), Ordering.natural(), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER);
      Object var3 = null;
      Range var4 = (Range)var3;
      if (var2 != -1) {
         Range var5 = (Range)this.ranges.get(var2);
         var4 = (Range)var3;
         if (var5.contains(var1)) {
            var4 = var5;
         }
      }

      return var4;
   }

   @Deprecated
   public void remove(Range<C> var1) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public void removeAll(RangeSet<C> var1) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public void removeAll(Iterable<Range<C>> var1) {
      throw new UnsupportedOperationException();
   }

   public Range<C> span() {
      if (!this.ranges.isEmpty()) {
         Cut var1 = ((Range)this.ranges.get(0)).lowerBound;
         ImmutableList var2 = this.ranges;
         return Range.create(var1, ((Range)var2.get(var2.size() - 1)).upperBound);
      } else {
         throw new NoSuchElementException();
      }
   }

   public ImmutableRangeSet<C> subRangeSet(Range<C> var1) {
      if (!this.isEmpty()) {
         Range var2 = this.span();
         if (var1.encloses(var2)) {
            return this;
         }

         if (var1.isConnected(var2)) {
            return new ImmutableRangeSet(this.intersectRanges(var1));
         }
      }

      return of();
   }

   public ImmutableRangeSet<C> union(RangeSet<C> var1) {
      return unionOf(Iterables.concat(this.asRanges(), var1.asRanges()));
   }

   Object writeReplace() {
      return new ImmutableRangeSet.SerializedForm(this.ranges);
   }

   private final class AsSet extends ImmutableSortedSet<C> {
      private final DiscreteDomain<C> domain;
      @MonotonicNonNullDecl
      private transient Integer size;

      AsSet(DiscreteDomain<C> var2) {
         super(Ordering.natural());
         this.domain = var2;
      }

      public boolean contains(@NullableDecl Object var1) {
         if (var1 == null) {
            return false;
         } else {
            try {
               Comparable var4 = (Comparable)var1;
               boolean var2 = ImmutableRangeSet.this.contains(var4);
               return var2;
            } catch (ClassCastException var3) {
               return false;
            }
         }
      }

      ImmutableSortedSet<C> createDescendingSet() {
         return new DescendingImmutableSortedSet(this);
      }

      public UnmodifiableIterator<C> descendingIterator() {
         return new AbstractIterator<C>() {
            Iterator<C> elemItr;
            final Iterator<Range<C>> rangeItr;

            {
               this.rangeItr = ImmutableRangeSet.this.ranges.reverse().iterator();
               this.elemItr = Iterators.emptyIterator();
            }

            protected C computeNext() {
               while(true) {
                  if (!this.elemItr.hasNext()) {
                     if (this.rangeItr.hasNext()) {
                        this.elemItr = ContiguousSet.create((Range)this.rangeItr.next(), AsSet.this.domain).descendingIterator();
                        continue;
                     }

                     return (Comparable)this.endOfData();
                  }

                  return (Comparable)this.elemItr.next();
               }
            }
         };
      }

      ImmutableSortedSet<C> headSetImpl(C var1, boolean var2) {
         return this.subSet(Range.upTo(var1, BoundType.forBoolean(var2)));
      }

      int indexOf(Object var1) {
         if (this.contains(var1)) {
            Comparable var2 = (Comparable)var1;
            long var3 = 0L;

            Range var6;
            for(UnmodifiableIterator var5 = ImmutableRangeSet.this.ranges.iterator(); var5.hasNext(); var3 += (long)ContiguousSet.create(var6, this.domain).size()) {
               var6 = (Range)var5.next();
               if (var6.contains(var2)) {
                  return Ints.saturatedCast(var3 + (long)ContiguousSet.create(var6, this.domain).indexOf(var2));
               }
            }

            throw new AssertionError("impossible");
         } else {
            return -1;
         }
      }

      boolean isPartialView() {
         return ImmutableRangeSet.this.ranges.isPartialView();
      }

      public UnmodifiableIterator<C> iterator() {
         return new AbstractIterator<C>() {
            Iterator<C> elemItr;
            final Iterator<Range<C>> rangeItr;

            {
               this.rangeItr = ImmutableRangeSet.this.ranges.iterator();
               this.elemItr = Iterators.emptyIterator();
            }

            protected C computeNext() {
               while(true) {
                  if (!this.elemItr.hasNext()) {
                     if (this.rangeItr.hasNext()) {
                        this.elemItr = ContiguousSet.create((Range)this.rangeItr.next(), AsSet.this.domain).iterator();
                        continue;
                     }

                     return (Comparable)this.endOfData();
                  }

                  return (Comparable)this.elemItr.next();
               }
            }
         };
      }

      public int size() {
         Integer var1 = this.size;
         Integer var2 = var1;
         if (var1 == null) {
            long var3 = 0L;
            UnmodifiableIterator var7 = ImmutableRangeSet.this.ranges.iterator();

            long var5;
            do {
               var5 = var3;
               if (!var7.hasNext()) {
                  break;
               }

               var5 = var3 + (long)ContiguousSet.create((Range)var7.next(), this.domain).size();
               var3 = var5;
            } while(var5 < 2147483647L);

            var2 = Ints.saturatedCast(var5);
            this.size = var2;
         }

         return var2;
      }

      ImmutableSortedSet<C> subSet(Range<C> var1) {
         return ImmutableRangeSet.this.subRangeSet(var1).asSet(this.domain);
      }

      ImmutableSortedSet<C> subSetImpl(C var1, boolean var2, C var3, boolean var4) {
         return !var2 && !var4 && Range.compareOrThrow(var1, var3) == 0 ? ImmutableSortedSet.of() : this.subSet(Range.range(var1, BoundType.forBoolean(var2), var3, BoundType.forBoolean(var4)));
      }

      ImmutableSortedSet<C> tailSetImpl(C var1, boolean var2) {
         return this.subSet(Range.downTo(var1, BoundType.forBoolean(var2)));
      }

      public String toString() {
         return ImmutableRangeSet.this.ranges.toString();
      }

      Object writeReplace() {
         return new ImmutableRangeSet.AsSetSerializedForm(ImmutableRangeSet.this.ranges, this.domain);
      }
   }

   private static class AsSetSerializedForm<C extends Comparable> implements Serializable {
      private final DiscreteDomain<C> domain;
      private final ImmutableList<Range<C>> ranges;

      AsSetSerializedForm(ImmutableList<Range<C>> var1, DiscreteDomain<C> var2) {
         this.ranges = var1;
         this.domain = var2;
      }

      Object readResolve() {
         return (new ImmutableRangeSet(this.ranges)).asSet(this.domain);
      }
   }

   public static class Builder<C extends Comparable<?>> {
      private final List<Range<C>> ranges = Lists.newArrayList();

      public ImmutableRangeSet.Builder<C> add(Range<C> var1) {
         Preconditions.checkArgument(var1.isEmpty() ^ true, "range must not be empty, but was %s", (Object)var1);
         this.ranges.add(var1);
         return this;
      }

      public ImmutableRangeSet.Builder<C> addAll(RangeSet<C> var1) {
         return this.addAll((Iterable)var1.asRanges());
      }

      public ImmutableRangeSet.Builder<C> addAll(Iterable<Range<C>> var1) {
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            this.add((Range)var2.next());
         }

         return this;
      }

      public ImmutableRangeSet<C> build() {
         ImmutableList.Builder var1 = new ImmutableList.Builder(this.ranges.size());
         Collections.sort(this.ranges, Range.rangeLexOrdering());

         Range var3;
         for(PeekingIterator var2 = Iterators.peekingIterator(this.ranges.iterator()); var2.hasNext(); var1.add((Object)var3)) {
            for(var3 = (Range)var2.next(); var2.hasNext(); var3 = var3.span((Range)var2.next())) {
               Range var4 = (Range)var2.peek();
               if (!var3.isConnected(var4)) {
                  break;
               }

               Preconditions.checkArgument(var3.intersection(var4).isEmpty(), "Overlapping ranges not permitted but found %s overlapping %s", var3, var4);
            }
         }

         ImmutableList var5 = var1.build();
         if (var5.isEmpty()) {
            return ImmutableRangeSet.EMPTY;
         } else if (var5.size() == 1 && ((Range)Iterables.getOnlyElement(var5)).equals(Range.all())) {
            return ImmutableRangeSet.ALL;
         } else {
            return new ImmutableRangeSet(var5);
         }
      }
   }

   private final class ComplementRanges extends ImmutableList<Range<C>> {
      private final boolean positiveBoundedAbove;
      private final boolean positiveBoundedBelow;
      private final int size;

      ComplementRanges() {
         this.positiveBoundedBelow = ((Range)ImmutableRangeSet.this.ranges.get(0)).hasLowerBound();
         this.positiveBoundedAbove = ((Range)Iterables.getLast(ImmutableRangeSet.this.ranges)).hasUpperBound();
         int var2 = ImmutableRangeSet.this.ranges.size() - 1;
         int var3 = var2;
         if (this.positiveBoundedBelow) {
            var3 = var2 + 1;
         }

         var2 = var3;
         if (this.positiveBoundedAbove) {
            var2 = var3 + 1;
         }

         this.size = var2;
      }

      public Range<C> get(int var1) {
         Preconditions.checkElementIndex(var1, this.size);
         Cut var2;
         if (this.positiveBoundedBelow) {
            if (var1 == 0) {
               var2 = Cut.belowAll();
            } else {
               var2 = ((Range)ImmutableRangeSet.this.ranges.get(var1 - 1)).upperBound;
            }
         } else {
            var2 = ((Range)ImmutableRangeSet.this.ranges.get(var1)).upperBound;
         }

         Cut var3;
         if (this.positiveBoundedAbove && var1 == this.size - 1) {
            var3 = Cut.aboveAll();
         } else {
            var3 = ((Range)ImmutableRangeSet.this.ranges.get(var1 + (this.positiveBoundedBelow ^ 1))).lowerBound;
         }

         return Range.create(var2, var3);
      }

      boolean isPartialView() {
         return true;
      }

      public int size() {
         return this.size;
      }
   }

   private static final class SerializedForm<C extends Comparable> implements Serializable {
      private final ImmutableList<Range<C>> ranges;

      SerializedForm(ImmutableList<Range<C>> var1) {
         this.ranges = var1;
      }

      Object readResolve() {
         if (this.ranges.isEmpty()) {
            return ImmutableRangeSet.EMPTY;
         } else {
            return this.ranges.equals(ImmutableList.of(Range.all())) ? ImmutableRangeSet.ALL : new ImmutableRangeSet(this.ranges);
         }
      }
   }
}
