package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.DoNotMock;
import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class ImmutableRangeMap<K extends Comparable<?>, V> implements RangeMap<K, V>, Serializable {
   private static final ImmutableRangeMap<Comparable<?>, Object> EMPTY = new ImmutableRangeMap(ImmutableList.of(), ImmutableList.of());
   private static final long serialVersionUID = 0L;
   private final transient ImmutableList<Range<K>> ranges;
   private final transient ImmutableList<V> values;

   ImmutableRangeMap(ImmutableList<Range<K>> var1, ImmutableList<V> var2) {
      this.ranges = var1;
      this.values = var2;
   }

   public static <K extends Comparable<?>, V> ImmutableRangeMap.Builder<K, V> builder() {
      return new ImmutableRangeMap.Builder();
   }

   public static <K extends Comparable<?>, V> ImmutableRangeMap<K, V> copyOf(RangeMap<K, ? extends V> var0) {
      if (var0 instanceof ImmutableRangeMap) {
         return (ImmutableRangeMap)var0;
      } else {
         Map var1 = var0.asMapOfRanges();
         ImmutableList.Builder var2 = new ImmutableList.Builder(var1.size());
         ImmutableList.Builder var4 = new ImmutableList.Builder(var1.size());
         Iterator var5 = var1.entrySet().iterator();

         while(var5.hasNext()) {
            Entry var3 = (Entry)var5.next();
            var2.add(var3.getKey());
            var4.add(var3.getValue());
         }

         return new ImmutableRangeMap(var2.build(), var4.build());
      }
   }

   public static <K extends Comparable<?>, V> ImmutableRangeMap<K, V> of() {
      return EMPTY;
   }

   public static <K extends Comparable<?>, V> ImmutableRangeMap<K, V> of(Range<K> var0, V var1) {
      return new ImmutableRangeMap(ImmutableList.of(var0), ImmutableList.of(var1));
   }

   public ImmutableMap<Range<K>, V> asDescendingMapOfRanges() {
      return (ImmutableMap)(this.ranges.isEmpty() ? ImmutableMap.of() : new ImmutableSortedMap(new RegularImmutableSortedSet(this.ranges.reverse(), Range.rangeLexOrdering().reverse()), this.values.reverse()));
   }

   public ImmutableMap<Range<K>, V> asMapOfRanges() {
      return (ImmutableMap)(this.ranges.isEmpty() ? ImmutableMap.of() : new ImmutableSortedMap(new RegularImmutableSortedSet(this.ranges, Range.rangeLexOrdering()), this.values));
   }

   @Deprecated
   public void clear() {
      throw new UnsupportedOperationException();
   }

   public boolean equals(@NullableDecl Object var1) {
      if (var1 instanceof RangeMap) {
         RangeMap var2 = (RangeMap)var1;
         return this.asMapOfRanges().equals(var2.asMapOfRanges());
      } else {
         return false;
      }
   }

   @NullableDecl
   public V get(K var1) {
      int var2 = SortedLists.binarySearch(this.ranges, (Function)Range.lowerBoundFn(), (Comparable)Cut.belowValue(var1), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER);
      Object var3 = null;
      if (var2 == -1) {
         return null;
      } else {
         if (((Range)this.ranges.get(var2)).contains(var1)) {
            var3 = this.values.get(var2);
         }

         return var3;
      }
   }

   @NullableDecl
   public Entry<Range<K>, V> getEntry(K var1) {
      int var2 = SortedLists.binarySearch(this.ranges, (Function)Range.lowerBoundFn(), (Comparable)Cut.belowValue(var1), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER);
      Entry var3 = null;
      if (var2 == -1) {
         return null;
      } else {
         Range var4 = (Range)this.ranges.get(var2);
         if (var4.contains(var1)) {
            var3 = Maps.immutableEntry(var4, this.values.get(var2));
         }

         return var3;
      }
   }

   public int hashCode() {
      return this.asMapOfRanges().hashCode();
   }

   @Deprecated
   public void put(Range<K> var1, V var2) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public void putAll(RangeMap<K, V> var1) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public void putCoalescing(Range<K> var1, V var2) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public void remove(Range<K> var1) {
      throw new UnsupportedOperationException();
   }

   public Range<K> span() {
      if (!this.ranges.isEmpty()) {
         Range var1 = (Range)this.ranges.get(0);
         ImmutableList var2 = this.ranges;
         Range var3 = (Range)var2.get(var2.size() - 1);
         return Range.create(var1.lowerBound, var3.upperBound);
      } else {
         throw new NoSuchElementException();
      }
   }

   public ImmutableRangeMap<K, V> subRangeMap(final Range<K> var1) {
      if (((Range)Preconditions.checkNotNull(var1)).isEmpty()) {
         return of();
      } else if (!this.ranges.isEmpty() && !var1.encloses(this.span())) {
         final int var2 = SortedLists.binarySearch(this.ranges, (Function)Range.upperBoundFn(), (Comparable)var1.lowerBound, SortedLists.KeyPresentBehavior.FIRST_AFTER, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
         int var3 = SortedLists.binarySearch(this.ranges, (Function)Range.lowerBoundFn(), (Comparable)var1.upperBound, SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
         return var2 >= var3 ? of() : new ImmutableRangeMap<K, V>(new ImmutableList<Range<K>>(var3 - var2) {
            // $FF: synthetic field
            final int val$len;

            {
               this.val$len = var2x;
            }

            public Range<K> get(int var1x) {
               Preconditions.checkElementIndex(var1x, this.val$len);
               return var1x != 0 && var1x != this.val$len - 1 ? (Range)ImmutableRangeMap.this.ranges.get(var1x + var2) : ((Range)ImmutableRangeMap.this.ranges.get(var1x + var2)).intersection(var1);
            }

            boolean isPartialView() {
               return true;
            }

            public int size() {
               return this.val$len;
            }
         }, this.values.subList(var2, var3)) {
            public ImmutableRangeMap<K, V> subRangeMap(Range<K> var1x) {
               return var1.isConnected(var1x) ? ImmutableRangeMap.this.subRangeMap(var1x.intersection(var1)) : ImmutableRangeMap.EMPTY;
            }
         };
      } else {
         return this;
      }
   }

   public String toString() {
      return this.asMapOfRanges().toString();
   }

   Object writeReplace() {
      return new ImmutableRangeMap.SerializedForm(this.asMapOfRanges());
   }

   @DoNotMock
   public static final class Builder<K extends Comparable<?>, V> {
      private final List<Entry<Range<K>, V>> entries = Lists.newArrayList();

      public ImmutableRangeMap<K, V> build() {
         Collections.sort(this.entries, Range.rangeLexOrdering().onKeys());
         ImmutableList.Builder var1 = new ImmutableList.Builder(this.entries.size());
         ImmutableList.Builder var2 = new ImmutableList.Builder(this.entries.size());

         for(int var3 = 0; var3 < this.entries.size(); ++var3) {
            Range var4 = (Range)((Entry)this.entries.get(var3)).getKey();
            if (var3 > 0) {
               Range var5 = (Range)((Entry)this.entries.get(var3 - 1)).getKey();
               if (var4.isConnected(var5) && !var4.intersection(var5).isEmpty()) {
                  StringBuilder var6 = new StringBuilder();
                  var6.append("Overlapping ranges: range ");
                  var6.append(var5);
                  var6.append(" overlaps with entry ");
                  var6.append(var4);
                  throw new IllegalArgumentException(var6.toString());
               }
            }

            var1.add((Object)var4);
            var2.add(((Entry)this.entries.get(var3)).getValue());
         }

         return new ImmutableRangeMap(var1.build(), var2.build());
      }

      public ImmutableRangeMap.Builder<K, V> put(Range<K> var1, V var2) {
         Preconditions.checkNotNull(var1);
         Preconditions.checkNotNull(var2);
         Preconditions.checkArgument(var1.isEmpty() ^ true, "Range must not be empty, but was %s", (Object)var1);
         this.entries.add(Maps.immutableEntry(var1, var2));
         return this;
      }

      public ImmutableRangeMap.Builder<K, V> putAll(RangeMap<K, ? extends V> var1) {
         Iterator var3 = var1.asMapOfRanges().entrySet().iterator();

         while(var3.hasNext()) {
            Entry var2 = (Entry)var3.next();
            this.put((Range)var2.getKey(), var2.getValue());
         }

         return this;
      }
   }

   private static class SerializedForm<K extends Comparable<?>, V> implements Serializable {
      private static final long serialVersionUID = 0L;
      private final ImmutableMap<Range<K>, V> mapOfRanges;

      SerializedForm(ImmutableMap<Range<K>, V> var1) {
         this.mapOfRanges = var1;
      }

      Object createRangeMap() {
         ImmutableRangeMap.Builder var1 = new ImmutableRangeMap.Builder();
         UnmodifiableIterator var2 = this.mapOfRanges.entrySet().iterator();

         while(var2.hasNext()) {
            Entry var3 = (Entry)var2.next();
            var1.put((Range)var3.getKey(), var3.getValue());
         }

         return var1.build();
      }

      Object readResolve() {
         return this.mapOfRanges.isEmpty() ? ImmutableRangeMap.EMPTY : this.createRangeMap();
      }
   }
}
