package com.google.common.collect;

import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AbstractRangeSet<C extends Comparable> implements RangeSet<C> {
   public void add(Range<C> var1) {
      throw new UnsupportedOperationException();
   }

   public void addAll(RangeSet<C> var1) {
      this.addAll((Iterable)var1.asRanges());
   }

   public void addAll(Iterable<Range<C>> var1) {
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         this.add((Range)var2.next());
      }

   }

   public void clear() {
      this.remove(Range.all());
   }

   public boolean contains(C var1) {
      boolean var2;
      if (this.rangeContaining(var1) != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public abstract boolean encloses(Range<C> var1);

   public boolean enclosesAll(RangeSet<C> var1) {
      return this.enclosesAll((Iterable)var1.asRanges());
   }

   public boolean enclosesAll(Iterable<Range<C>> var1) {
      Iterator var2 = var1.iterator();

      do {
         if (!var2.hasNext()) {
            return true;
         }
      } while(this.encloses((Range)var2.next()));

      return false;
   }

   public boolean equals(@NullableDecl Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 instanceof RangeSet) {
         RangeSet var2 = (RangeSet)var1;
         return this.asRanges().equals(var2.asRanges());
      } else {
         return false;
      }
   }

   public final int hashCode() {
      return this.asRanges().hashCode();
   }

   public boolean intersects(Range<C> var1) {
      return this.subRangeSet(var1).isEmpty() ^ true;
   }

   public boolean isEmpty() {
      return this.asRanges().isEmpty();
   }

   public abstract Range<C> rangeContaining(C var1);

   public void remove(Range<C> var1) {
      throw new UnsupportedOperationException();
   }

   public void removeAll(RangeSet<C> var1) {
      this.removeAll((Iterable)var1.asRanges());
   }

   public void removeAll(Iterable<Range<C>> var1) {
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         this.remove((Range)var2.next());
      }

   }

   public final String toString() {
      return this.asRanges().toString();
   }
}
