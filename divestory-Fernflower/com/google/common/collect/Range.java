package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Range<C extends Comparable> extends RangeGwtSerializationDependencies implements Predicate<C>, Serializable {
   private static final Range<Comparable> ALL = new Range(Cut.belowAll(), Cut.aboveAll());
   private static final long serialVersionUID = 0L;
   final Cut<C> lowerBound;
   final Cut<C> upperBound;

   private Range(Cut<C> var1, Cut<C> var2) {
      this.lowerBound = (Cut)Preconditions.checkNotNull(var1);
      this.upperBound = (Cut)Preconditions.checkNotNull(var2);
      if (var1.compareTo(var2) > 0 || var1 == Cut.aboveAll() || var2 == Cut.belowAll()) {
         StringBuilder var3 = new StringBuilder();
         var3.append("Invalid range: ");
         var3.append(toString(var1, var2));
         throw new IllegalArgumentException(var3.toString());
      }
   }

   public static <C extends Comparable<?>> Range<C> all() {
      return ALL;
   }

   public static <C extends Comparable<?>> Range<C> atLeast(C var0) {
      return create(Cut.belowValue(var0), Cut.aboveAll());
   }

   public static <C extends Comparable<?>> Range<C> atMost(C var0) {
      return create(Cut.belowAll(), Cut.aboveValue(var0));
   }

   private static <T> SortedSet<T> cast(Iterable<T> var0) {
      return (SortedSet)var0;
   }

   public static <C extends Comparable<?>> Range<C> closed(C var0, C var1) {
      return create(Cut.belowValue(var0), Cut.aboveValue(var1));
   }

   public static <C extends Comparable<?>> Range<C> closedOpen(C var0, C var1) {
      return create(Cut.belowValue(var0), Cut.belowValue(var1));
   }

   static int compareOrThrow(Comparable var0, Comparable var1) {
      return var0.compareTo(var1);
   }

   static <C extends Comparable<?>> Range<C> create(Cut<C> var0, Cut<C> var1) {
      return new Range(var0, var1);
   }

   public static <C extends Comparable<?>> Range<C> downTo(C var0, BoundType var1) {
      int var2 = null.$SwitchMap$com$google$common$collect$BoundType[var1.ordinal()];
      if (var2 != 1) {
         if (var2 == 2) {
            return atLeast(var0);
         } else {
            throw new AssertionError();
         }
      } else {
         return greaterThan(var0);
      }
   }

   public static <C extends Comparable<?>> Range<C> encloseAll(Iterable<C> var0) {
      Preconditions.checkNotNull(var0);
      if (var0 instanceof SortedSet) {
         SortedSet var1 = cast(var0);
         Comparator var2 = var1.comparator();
         if (Ordering.natural().equals(var2) || var2 == null) {
            return closed((Comparable)var1.first(), (Comparable)var1.last());
         }
      }

      Iterator var5 = var0.iterator();
      Comparable var6 = (Comparable)Preconditions.checkNotNull(var5.next());

      Comparable var3;
      Comparable var4;
      for(var4 = var6; var5.hasNext(); var4 = (Comparable)Ordering.natural().max(var4, var3)) {
         var3 = (Comparable)Preconditions.checkNotNull(var5.next());
         var6 = (Comparable)Ordering.natural().min(var6, var3);
      }

      return closed(var6, var4);
   }

   public static <C extends Comparable<?>> Range<C> greaterThan(C var0) {
      return create(Cut.aboveValue(var0), Cut.aboveAll());
   }

   public static <C extends Comparable<?>> Range<C> lessThan(C var0) {
      return create(Cut.belowAll(), Cut.belowValue(var0));
   }

   static <C extends Comparable<?>> Function<Range<C>, Cut<C>> lowerBoundFn() {
      return Range.LowerBoundFn.INSTANCE;
   }

   public static <C extends Comparable<?>> Range<C> open(C var0, C var1) {
      return create(Cut.aboveValue(var0), Cut.belowValue(var1));
   }

   public static <C extends Comparable<?>> Range<C> openClosed(C var0, C var1) {
      return create(Cut.aboveValue(var0), Cut.aboveValue(var1));
   }

   public static <C extends Comparable<?>> Range<C> range(C var0, BoundType var1, C var2, BoundType var3) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var3);
      Cut var4;
      if (var1 == BoundType.OPEN) {
         var4 = Cut.aboveValue(var0);
      } else {
         var4 = Cut.belowValue(var0);
      }

      Cut var5;
      if (var3 == BoundType.OPEN) {
         var5 = Cut.belowValue(var2);
      } else {
         var5 = Cut.aboveValue(var2);
      }

      return create(var4, var5);
   }

   static <C extends Comparable<?>> Ordering<Range<C>> rangeLexOrdering() {
      return Range.RangeLexOrdering.INSTANCE;
   }

   public static <C extends Comparable<?>> Range<C> singleton(C var0) {
      return closed(var0, var0);
   }

   private static String toString(Cut<?> var0, Cut<?> var1) {
      StringBuilder var2 = new StringBuilder(16);
      var0.describeAsLowerBound(var2);
      var2.append("..");
      var1.describeAsUpperBound(var2);
      return var2.toString();
   }

   public static <C extends Comparable<?>> Range<C> upTo(C var0, BoundType var1) {
      int var2 = null.$SwitchMap$com$google$common$collect$BoundType[var1.ordinal()];
      if (var2 != 1) {
         if (var2 == 2) {
            return atMost(var0);
         } else {
            throw new AssertionError();
         }
      } else {
         return lessThan(var0);
      }
   }

   static <C extends Comparable<?>> Function<Range<C>, Cut<C>> upperBoundFn() {
      return Range.UpperBoundFn.INSTANCE;
   }

   @Deprecated
   public boolean apply(C var1) {
      return this.contains(var1);
   }

   public Range<C> canonical(DiscreteDomain<C> var1) {
      Preconditions.checkNotNull(var1);
      Cut var2 = this.lowerBound.canonical(var1);
      Cut var3 = this.upperBound.canonical(var1);
      Range var4;
      if (var2 == this.lowerBound && var3 == this.upperBound) {
         var4 = this;
      } else {
         var4 = create(var2, var3);
      }

      return var4;
   }

   public boolean contains(C var1) {
      Preconditions.checkNotNull(var1);
      boolean var2;
      if (this.lowerBound.isLessThan(var1) && !this.upperBound.isLessThan(var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean containsAll(Iterable<? extends C> var1) {
      boolean var2 = Iterables.isEmpty(var1);
      boolean var3 = true;
      if (var2) {
         return true;
      } else {
         if (var1 instanceof SortedSet) {
            SortedSet var4 = cast(var1);
            Comparator var5 = var4.comparator();
            if (Ordering.natural().equals(var5) || var5 == null) {
               if (!this.contains((Comparable)var4.first()) || !this.contains((Comparable)var4.last())) {
                  var3 = false;
               }

               return var3;
            }
         }

         Iterator var6 = var1.iterator();

         do {
            if (!var6.hasNext()) {
               return true;
            }
         } while(this.contains((Comparable)var6.next()));

         return false;
      }
   }

   public boolean encloses(Range<C> var1) {
      boolean var2;
      if (this.lowerBound.compareTo(var1.lowerBound) <= 0 && this.upperBound.compareTo(var1.upperBound) >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean equals(@NullableDecl Object var1) {
      boolean var2 = var1 instanceof Range;
      boolean var3 = false;
      boolean var4 = var3;
      if (var2) {
         Range var5 = (Range)var1;
         var4 = var3;
         if (this.lowerBound.equals(var5.lowerBound)) {
            var4 = var3;
            if (this.upperBound.equals(var5.upperBound)) {
               var4 = true;
            }
         }
      }

      return var4;
   }

   public Range<C> gap(Range<C> var1) {
      boolean var2;
      if (this.lowerBound.compareTo(var1.lowerBound) < 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      Range var3;
      if (var2) {
         var3 = this;
      } else {
         var3 = var1;
      }

      if (!var2) {
         var1 = this;
      }

      return create(var3.upperBound, var1.lowerBound);
   }

   public boolean hasLowerBound() {
      boolean var1;
      if (this.lowerBound != Cut.belowAll()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean hasUpperBound() {
      boolean var1;
      if (this.upperBound != Cut.aboveAll()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public int hashCode() {
      return this.lowerBound.hashCode() * 31 + this.upperBound.hashCode();
   }

   public Range<C> intersection(Range<C> var1) {
      int var2 = this.lowerBound.compareTo(var1.lowerBound);
      int var3 = this.upperBound.compareTo(var1.upperBound);
      if (var2 >= 0 && var3 <= 0) {
         return this;
      } else if (var2 <= 0 && var3 >= 0) {
         return var1;
      } else {
         Cut var4;
         if (var2 >= 0) {
            var4 = this.lowerBound;
         } else {
            var4 = var1.lowerBound;
         }

         Cut var5;
         if (var3 <= 0) {
            var5 = this.upperBound;
         } else {
            var5 = var1.upperBound;
         }

         return create(var4, var5);
      }
   }

   public boolean isConnected(Range<C> var1) {
      boolean var2;
      if (this.lowerBound.compareTo(var1.upperBound) <= 0 && var1.lowerBound.compareTo(this.upperBound) <= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean isEmpty() {
      return this.lowerBound.equals(this.upperBound);
   }

   public BoundType lowerBoundType() {
      return this.lowerBound.typeAsLowerBound();
   }

   public C lowerEndpoint() {
      return this.lowerBound.endpoint();
   }

   Object readResolve() {
      return this.equals(ALL) ? all() : this;
   }

   public Range<C> span(Range<C> var1) {
      int var2 = this.lowerBound.compareTo(var1.lowerBound);
      int var3 = this.upperBound.compareTo(var1.upperBound);
      if (var2 <= 0 && var3 >= 0) {
         return this;
      } else if (var2 >= 0 && var3 <= 0) {
         return var1;
      } else {
         Cut var4;
         if (var2 <= 0) {
            var4 = this.lowerBound;
         } else {
            var4 = var1.lowerBound;
         }

         Cut var5;
         if (var3 >= 0) {
            var5 = this.upperBound;
         } else {
            var5 = var1.upperBound;
         }

         return create(var4, var5);
      }
   }

   public String toString() {
      return toString(this.lowerBound, this.upperBound);
   }

   public BoundType upperBoundType() {
      return this.upperBound.typeAsUpperBound();
   }

   public C upperEndpoint() {
      return this.upperBound.endpoint();
   }

   static class LowerBoundFn implements Function<Range, Cut> {
      static final Range.LowerBoundFn INSTANCE = new Range.LowerBoundFn();

      public Cut apply(Range var1) {
         return var1.lowerBound;
      }
   }

   private static class RangeLexOrdering extends Ordering<Range<?>> implements Serializable {
      static final Ordering<Range<?>> INSTANCE = new Range.RangeLexOrdering();
      private static final long serialVersionUID = 0L;

      public int compare(Range<?> var1, Range<?> var2) {
         return ComparisonChain.start().compare((Comparable)var1.lowerBound, (Comparable)var2.lowerBound).compare((Comparable)var1.upperBound, (Comparable)var2.upperBound).result();
      }
   }

   static class UpperBoundFn implements Function<Range, Cut> {
      static final Range.UpperBoundFn INSTANCE = new Range.UpperBoundFn();

      public Cut apply(Range var1) {
         return var1.upperBound;
      }
   }
}
