package com.google.common.collect;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableMap;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class TreeRangeSet<C extends Comparable<?>> extends AbstractRangeSet<C> implements Serializable {
   @MonotonicNonNullDecl
   private transient Set<Range<C>> asDescendingSetOfRanges;
   @MonotonicNonNullDecl
   private transient Set<Range<C>> asRanges;
   @MonotonicNonNullDecl
   private transient RangeSet<C> complement;
   final NavigableMap<Cut<C>, Range<C>> rangesByLowerBound;

   private TreeRangeSet(NavigableMap<Cut<C>, Range<C>> var1) {
      this.rangesByLowerBound = var1;
   }

   // $FF: synthetic method
   TreeRangeSet(NavigableMap var1, Object var2) {
      this(var1);
   }

   public static <C extends Comparable<?>> TreeRangeSet<C> create() {
      return new TreeRangeSet(new TreeMap());
   }

   public static <C extends Comparable<?>> TreeRangeSet<C> create(RangeSet<C> var0) {
      TreeRangeSet var1 = create();
      var1.addAll(var0);
      return var1;
   }

   public static <C extends Comparable<?>> TreeRangeSet<C> create(Iterable<Range<C>> var0) {
      TreeRangeSet var1 = create();
      var1.addAll(var0);
      return var1;
   }

   @NullableDecl
   private Range<C> rangeEnclosing(Range<C> var1) {
      Preconditions.checkNotNull(var1);
      Entry var2 = this.rangesByLowerBound.floorEntry(var1.lowerBound);
      if (var2 != null && ((Range)var2.getValue()).encloses(var1)) {
         var1 = (Range)var2.getValue();
      } else {
         var1 = null;
      }

      return var1;
   }

   private void replaceRangeWithSameLowerBound(Range<C> var1) {
      if (var1.isEmpty()) {
         this.rangesByLowerBound.remove(var1.lowerBound);
      } else {
         this.rangesByLowerBound.put(var1.lowerBound, var1);
      }

   }

   public void add(Range<C> var1) {
      Preconditions.checkNotNull(var1);
      if (!var1.isEmpty()) {
         Cut var2 = var1.lowerBound;
         Cut var3 = var1.upperBound;
         Entry var4 = this.rangesByLowerBound.lowerEntry(var2);
         Cut var5 = var2;
         Cut var6 = var3;
         if (var4 != null) {
            Range var9 = (Range)var4.getValue();
            var5 = var2;
            var6 = var3;
            if (var9.upperBound.compareTo(var2) >= 0) {
               var6 = var3;
               if (var9.upperBound.compareTo(var3) >= 0) {
                  var6 = var9.upperBound;
               }

               var5 = var9.lowerBound;
            }
         }

         Entry var7 = this.rangesByLowerBound.floorEntry(var6);
         var3 = var6;
         if (var7 != null) {
            Range var8 = (Range)var7.getValue();
            var3 = var6;
            if (var8.upperBound.compareTo(var6) >= 0) {
               var3 = var8.upperBound;
            }
         }

         this.rangesByLowerBound.subMap(var5, var3).clear();
         this.replaceRangeWithSameLowerBound(Range.create(var5, var3));
      }
   }

   public Set<Range<C>> asDescendingSetOfRanges() {
      Set var1 = this.asDescendingSetOfRanges;
      Object var2 = var1;
      if (var1 == null) {
         var2 = new TreeRangeSet.AsRanges(this.rangesByLowerBound.descendingMap().values());
         this.asDescendingSetOfRanges = (Set)var2;
      }

      return (Set)var2;
   }

   public Set<Range<C>> asRanges() {
      Set var1 = this.asRanges;
      Object var2 = var1;
      if (var1 == null) {
         var2 = new TreeRangeSet.AsRanges(this.rangesByLowerBound.values());
         this.asRanges = (Set)var2;
      }

      return (Set)var2;
   }

   public RangeSet<C> complement() {
      RangeSet var1 = this.complement;
      Object var2 = var1;
      if (var1 == null) {
         var2 = new TreeRangeSet.Complement();
         this.complement = (RangeSet)var2;
      }

      return (RangeSet)var2;
   }

   public boolean encloses(Range<C> var1) {
      Preconditions.checkNotNull(var1);
      Entry var2 = this.rangesByLowerBound.floorEntry(var1.lowerBound);
      boolean var3;
      if (var2 != null && ((Range)var2.getValue()).encloses(var1)) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean intersects(Range<C> var1) {
      Preconditions.checkNotNull(var1);
      Entry var2 = this.rangesByLowerBound.ceilingEntry(var1.lowerBound);
      boolean var3 = true;
      if (var2 != null && ((Range)var2.getValue()).isConnected(var1) && !((Range)var2.getValue()).intersection(var1).isEmpty()) {
         return true;
      } else {
         var2 = this.rangesByLowerBound.lowerEntry(var1.lowerBound);
         if (var2 == null || !((Range)var2.getValue()).isConnected(var1) || ((Range)var2.getValue()).intersection(var1).isEmpty()) {
            var3 = false;
         }

         return var3;
      }
   }

   @NullableDecl
   public Range<C> rangeContaining(C var1) {
      Preconditions.checkNotNull(var1);
      Entry var2 = this.rangesByLowerBound.floorEntry(Cut.belowValue(var1));
      return var2 != null && ((Range)var2.getValue()).contains(var1) ? (Range)var2.getValue() : null;
   }

   public void remove(Range<C> var1) {
      Preconditions.checkNotNull(var1);
      if (!var1.isEmpty()) {
         Entry var2 = this.rangesByLowerBound.lowerEntry(var1.lowerBound);
         Range var3;
         if (var2 != null) {
            var3 = (Range)var2.getValue();
            if (var3.upperBound.compareTo(var1.lowerBound) >= 0) {
               if (var1.hasUpperBound() && var3.upperBound.compareTo(var1.upperBound) >= 0) {
                  this.replaceRangeWithSameLowerBound(Range.create(var1.upperBound, var3.upperBound));
               }

               this.replaceRangeWithSameLowerBound(Range.create(var3.lowerBound, var1.lowerBound));
            }
         }

         var2 = this.rangesByLowerBound.floorEntry(var1.upperBound);
         if (var2 != null) {
            var3 = (Range)var2.getValue();
            if (var1.hasUpperBound() && var3.upperBound.compareTo(var1.upperBound) >= 0) {
               this.replaceRangeWithSameLowerBound(Range.create(var1.upperBound, var3.upperBound));
            }
         }

         this.rangesByLowerBound.subMap(var1.lowerBound, var1.upperBound).clear();
      }
   }

   public Range<C> span() {
      Entry var1 = this.rangesByLowerBound.firstEntry();
      Entry var2 = this.rangesByLowerBound.lastEntry();
      if (var1 != null) {
         return Range.create(((Range)var1.getValue()).lowerBound, ((Range)var2.getValue()).upperBound);
      } else {
         throw new NoSuchElementException();
      }
   }

   public RangeSet<C> subRangeSet(Range<C> var1) {
      Object var2;
      if (var1.equals(Range.all())) {
         var2 = this;
      } else {
         var2 = new TreeRangeSet.SubRangeSet(var1);
      }

      return (RangeSet)var2;
   }

   final class AsRanges extends ForwardingCollection<Range<C>> implements Set<Range<C>> {
      final Collection<Range<C>> delegate;

      AsRanges(Collection<Range<C>> var2) {
         this.delegate = var2;
      }

      protected Collection<Range<C>> delegate() {
         return this.delegate;
      }

      public boolean equals(@NullableDecl Object var1) {
         return Sets.equalsImpl(this, var1);
      }

      public int hashCode() {
         return Sets.hashCodeImpl(this);
      }
   }

   private final class Complement extends TreeRangeSet<C> {
      Complement() {
         super(new TreeRangeSet.ComplementRangesByLowerBound(TreeRangeSet.this.rangesByLowerBound), null);
      }

      public void add(Range<C> var1) {
         TreeRangeSet.this.remove(var1);
      }

      public RangeSet<C> complement() {
         return TreeRangeSet.this;
      }

      public boolean contains(C var1) {
         return TreeRangeSet.this.contains(var1) ^ true;
      }

      public void remove(Range<C> var1) {
         TreeRangeSet.this.add(var1);
      }
   }

   private static final class ComplementRangesByLowerBound<C extends Comparable<?>> extends AbstractNavigableMap<Cut<C>, Range<C>> {
      private final Range<Cut<C>> complementLowerBoundWindow;
      private final NavigableMap<Cut<C>, Range<C>> positiveRangesByLowerBound;
      private final NavigableMap<Cut<C>, Range<C>> positiveRangesByUpperBound;

      ComplementRangesByLowerBound(NavigableMap<Cut<C>, Range<C>> var1) {
         this(var1, Range.all());
      }

      private ComplementRangesByLowerBound(NavigableMap<Cut<C>, Range<C>> var1, Range<Cut<C>> var2) {
         this.positiveRangesByLowerBound = var1;
         this.positiveRangesByUpperBound = new TreeRangeSet.RangesByUpperBound(var1);
         this.complementLowerBoundWindow = var2;
      }

      private NavigableMap<Cut<C>, Range<C>> subMap(Range<Cut<C>> var1) {
         if (!this.complementLowerBoundWindow.isConnected(var1)) {
            return ImmutableSortedMap.of();
         } else {
            var1 = var1.intersection(this.complementLowerBoundWindow);
            return new TreeRangeSet.ComplementRangesByLowerBound(this.positiveRangesByLowerBound, var1);
         }
      }

      public Comparator<? super Cut<C>> comparator() {
         return Ordering.natural();
      }

      public boolean containsKey(Object var1) {
         boolean var2;
         if (this.get(var1) != null) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      Iterator<Entry<Cut<C>, Range<C>>> descendingEntryIterator() {
         Cut var1;
         if (this.complementLowerBoundWindow.hasUpperBound()) {
            var1 = (Cut)this.complementLowerBoundWindow.upperEndpoint();
         } else {
            var1 = Cut.aboveAll();
         }

         boolean var2;
         if (this.complementLowerBoundWindow.hasUpperBound() && this.complementLowerBoundWindow.upperBoundType() == BoundType.CLOSED) {
            var2 = true;
         } else {
            var2 = false;
         }

         final PeekingIterator var3 = Iterators.peekingIterator(this.positiveRangesByUpperBound.headMap(var1, var2).descendingMap().values().iterator());
         if (var3.hasNext()) {
            if (((Range)var3.peek()).upperBound == Cut.aboveAll()) {
               var1 = ((Range)var3.next()).lowerBound;
            } else {
               var1 = (Cut)this.positiveRangesByLowerBound.higherKey(((Range)var3.peek()).upperBound);
            }
         } else {
            if (!this.complementLowerBoundWindow.contains(Cut.belowAll()) || this.positiveRangesByLowerBound.containsKey(Cut.belowAll())) {
               return Iterators.emptyIterator();
            }

            var1 = (Cut)this.positiveRangesByLowerBound.higherKey(Cut.belowAll());
         }

         return new AbstractIterator<Entry<Cut<C>, Range<C>>>((Cut)MoreObjects.firstNonNull(var1, Cut.aboveAll())) {
            Cut<C> nextComplementRangeUpperBound;
            // $FF: synthetic field
            final Cut val$firstComplementRangeUpperBound;

            {
               this.val$firstComplementRangeUpperBound = var2;
               this.nextComplementRangeUpperBound = this.val$firstComplementRangeUpperBound;
            }

            protected Entry<Cut<C>, Range<C>> computeNext() {
               if (this.nextComplementRangeUpperBound == Cut.belowAll()) {
                  return (Entry)this.endOfData();
               } else {
                  Range var1;
                  if (var3.hasNext()) {
                     var1 = (Range)var3.next();
                     Range var2 = Range.create(var1.upperBound, this.nextComplementRangeUpperBound);
                     this.nextComplementRangeUpperBound = var1.lowerBound;
                     if (ComplementRangesByLowerBound.this.complementLowerBoundWindow.lowerBound.isLessThan(var2.lowerBound)) {
                        return Maps.immutableEntry(var2.lowerBound, var2);
                     }
                  } else if (ComplementRangesByLowerBound.this.complementLowerBoundWindow.lowerBound.isLessThan(Cut.belowAll())) {
                     var1 = Range.create(Cut.belowAll(), this.nextComplementRangeUpperBound);
                     this.nextComplementRangeUpperBound = Cut.belowAll();
                     return Maps.immutableEntry(Cut.belowAll(), var1);
                  }

                  return (Entry)this.endOfData();
               }
            }
         };
      }

      Iterator<Entry<Cut<C>, Range<C>>> entryIterator() {
         Collection var5;
         if (this.complementLowerBoundWindow.hasLowerBound()) {
            NavigableMap var1 = this.positiveRangesByUpperBound;
            Comparable var2 = this.complementLowerBoundWindow.lowerEndpoint();
            boolean var3;
            if (this.complementLowerBoundWindow.lowerBoundType() == BoundType.CLOSED) {
               var3 = true;
            } else {
               var3 = false;
            }

            var5 = var1.tailMap(var2, var3).values();
         } else {
            var5 = this.positiveRangesByUpperBound.values();
         }

         final PeekingIterator var4 = Iterators.peekingIterator(var5.iterator());
         final Cut var6;
         if (this.complementLowerBoundWindow.contains(Cut.belowAll()) && (!var4.hasNext() || ((Range)var4.peek()).lowerBound != Cut.belowAll())) {
            var6 = Cut.belowAll();
         } else {
            if (!var4.hasNext()) {
               return Iterators.emptyIterator();
            }

            var6 = ((Range)var4.next()).upperBound;
         }

         return new AbstractIterator<Entry<Cut<C>, Range<C>>>() {
            Cut<C> nextComplementRangeLowerBound = var6;

            protected Entry<Cut<C>, Range<C>> computeNext() {
               if (!ComplementRangesByLowerBound.this.complementLowerBoundWindow.upperBound.isLessThan(this.nextComplementRangeLowerBound) && this.nextComplementRangeLowerBound != Cut.aboveAll()) {
                  Range var2;
                  if (var4.hasNext()) {
                     Range var1 = (Range)var4.next();
                     var2 = Range.create(this.nextComplementRangeLowerBound, var1.lowerBound);
                     this.nextComplementRangeLowerBound = var1.upperBound;
                  } else {
                     var2 = Range.create(this.nextComplementRangeLowerBound, Cut.aboveAll());
                     this.nextComplementRangeLowerBound = Cut.aboveAll();
                  }

                  return Maps.immutableEntry(var2.lowerBound, var2);
               } else {
                  return (Entry)this.endOfData();
               }
            }
         };
      }

      @NullableDecl
      public Range<C> get(Object var1) {
         if (var1 instanceof Cut) {
            boolean var10001;
            Cut var2;
            Entry var5;
            try {
               var2 = (Cut)var1;
               var5 = this.tailMap(var2, true).firstEntry();
            } catch (ClassCastException var4) {
               var10001 = false;
               return null;
            }

            if (var5 != null) {
               try {
                  if (((Cut)var5.getKey()).equals(var2)) {
                     Range var6 = (Range)var5.getValue();
                     return var6;
                  }
               } catch (ClassCastException var3) {
                  var10001 = false;
               }
            }
         }

         return null;
      }

      public NavigableMap<Cut<C>, Range<C>> headMap(Cut<C> var1, boolean var2) {
         return this.subMap(Range.upTo(var1, BoundType.forBoolean(var2)));
      }

      public int size() {
         return Iterators.size(this.entryIterator());
      }

      public NavigableMap<Cut<C>, Range<C>> subMap(Cut<C> var1, boolean var2, Cut<C> var3, boolean var4) {
         return this.subMap(Range.range(var1, BoundType.forBoolean(var2), var3, BoundType.forBoolean(var4)));
      }

      public NavigableMap<Cut<C>, Range<C>> tailMap(Cut<C> var1, boolean var2) {
         return this.subMap(Range.downTo(var1, BoundType.forBoolean(var2)));
      }
   }

   static final class RangesByUpperBound<C extends Comparable<?>> extends AbstractNavigableMap<Cut<C>, Range<C>> {
      private final NavigableMap<Cut<C>, Range<C>> rangesByLowerBound;
      private final Range<Cut<C>> upperBoundWindow;

      RangesByUpperBound(NavigableMap<Cut<C>, Range<C>> var1) {
         this.rangesByLowerBound = var1;
         this.upperBoundWindow = Range.all();
      }

      private RangesByUpperBound(NavigableMap<Cut<C>, Range<C>> var1, Range<Cut<C>> var2) {
         this.rangesByLowerBound = var1;
         this.upperBoundWindow = var2;
      }

      private NavigableMap<Cut<C>, Range<C>> subMap(Range<Cut<C>> var1) {
         return (NavigableMap)(var1.isConnected(this.upperBoundWindow) ? new TreeRangeSet.RangesByUpperBound(this.rangesByLowerBound, var1.intersection(this.upperBoundWindow)) : ImmutableSortedMap.of());
      }

      public Comparator<? super Cut<C>> comparator() {
         return Ordering.natural();
      }

      public boolean containsKey(@NullableDecl Object var1) {
         boolean var2;
         if (this.get(var1) != null) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      Iterator<Entry<Cut<C>, Range<C>>> descendingEntryIterator() {
         Collection var1;
         if (this.upperBoundWindow.hasUpperBound()) {
            var1 = this.rangesByLowerBound.headMap(this.upperBoundWindow.upperEndpoint(), false).descendingMap().values();
         } else {
            var1 = this.rangesByLowerBound.descendingMap().values();
         }

         final PeekingIterator var2 = Iterators.peekingIterator(var1.iterator());
         if (var2.hasNext() && this.upperBoundWindow.upperBound.isLessThan(((Range)var2.peek()).upperBound)) {
            var2.next();
         }

         return new AbstractIterator<Entry<Cut<C>, Range<C>>>() {
            protected Entry<Cut<C>, Range<C>> computeNext() {
               if (!var2.hasNext()) {
                  return (Entry)this.endOfData();
               } else {
                  Range var1 = (Range)var2.next();
                  Entry var2x;
                  if (RangesByUpperBound.this.upperBoundWindow.lowerBound.isLessThan(var1.upperBound)) {
                     var2x = Maps.immutableEntry(var1.upperBound, var1);
                  } else {
                     var2x = (Entry)this.endOfData();
                  }

                  return var2x;
               }
            }
         };
      }

      Iterator<Entry<Cut<C>, Range<C>>> entryIterator() {
         final Iterator var1;
         if (!this.upperBoundWindow.hasLowerBound()) {
            var1 = this.rangesByLowerBound.values().iterator();
         } else {
            Entry var2 = this.rangesByLowerBound.lowerEntry(this.upperBoundWindow.lowerEndpoint());
            if (var2 == null) {
               var1 = this.rangesByLowerBound.values().iterator();
            } else if (this.upperBoundWindow.lowerBound.isLessThan(((Range)var2.getValue()).upperBound)) {
               var1 = this.rangesByLowerBound.tailMap(var2.getKey(), true).values().iterator();
            } else {
               var1 = this.rangesByLowerBound.tailMap(this.upperBoundWindow.lowerEndpoint(), true).values().iterator();
            }
         }

         return new AbstractIterator<Entry<Cut<C>, Range<C>>>() {
            protected Entry<Cut<C>, Range<C>> computeNext() {
               if (!var1.hasNext()) {
                  return (Entry)this.endOfData();
               } else {
                  Range var1x = (Range)var1.next();
                  return RangesByUpperBound.this.upperBoundWindow.upperBound.isLessThan(var1x.upperBound) ? (Entry)this.endOfData() : Maps.immutableEntry(var1x.upperBound, var1x);
               }
            }
         };
      }

      public Range<C> get(@NullableDecl Object var1) {
         if (var1 instanceof Cut) {
            boolean var10001;
            Cut var6;
            try {
               var6 = (Cut)var1;
               if (!this.upperBoundWindow.contains(var6)) {
                  return null;
               }
            } catch (ClassCastException var5) {
               var10001 = false;
               return null;
            }

            Entry var2;
            try {
               var2 = this.rangesByLowerBound.lowerEntry(var6);
            } catch (ClassCastException var4) {
               var10001 = false;
               return null;
            }

            if (var2 != null) {
               try {
                  if (((Range)var2.getValue()).upperBound.equals(var6)) {
                     Range var7 = (Range)var2.getValue();
                     return var7;
                  }
               } catch (ClassCastException var3) {
                  var10001 = false;
               }
            }
         }

         return null;
      }

      public NavigableMap<Cut<C>, Range<C>> headMap(Cut<C> var1, boolean var2) {
         return this.subMap(Range.upTo(var1, BoundType.forBoolean(var2)));
      }

      public boolean isEmpty() {
         boolean var1;
         if (this.upperBoundWindow.equals(Range.all())) {
            var1 = this.rangesByLowerBound.isEmpty();
         } else if (!this.entryIterator().hasNext()) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public int size() {
         return this.upperBoundWindow.equals(Range.all()) ? this.rangesByLowerBound.size() : Iterators.size(this.entryIterator());
      }

      public NavigableMap<Cut<C>, Range<C>> subMap(Cut<C> var1, boolean var2, Cut<C> var3, boolean var4) {
         return this.subMap(Range.range(var1, BoundType.forBoolean(var2), var3, BoundType.forBoolean(var4)));
      }

      public NavigableMap<Cut<C>, Range<C>> tailMap(Cut<C> var1, boolean var2) {
         return this.subMap(Range.downTo(var1, BoundType.forBoolean(var2)));
      }
   }

   private final class SubRangeSet extends TreeRangeSet<C> {
      private final Range<C> restriction;

      SubRangeSet(Range<C> var2) {
         super(new TreeRangeSet.SubRangeSetRangesByLowerBound(Range.all(), var2, TreeRangeSet.this.rangesByLowerBound), null);
         this.restriction = var2;
      }

      public void add(Range<C> var1) {
         Preconditions.checkArgument(this.restriction.encloses(var1), "Cannot add range %s to subRangeSet(%s)", var1, this.restriction);
         super.add(var1);
      }

      public void clear() {
         TreeRangeSet.this.remove(this.restriction);
      }

      public boolean contains(C var1) {
         boolean var2;
         if (this.restriction.contains(var1) && TreeRangeSet.this.contains(var1)) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public boolean encloses(Range<C> var1) {
         boolean var2 = this.restriction.isEmpty();
         boolean var3 = false;
         boolean var4 = var3;
         if (!var2) {
            var4 = var3;
            if (this.restriction.encloses(var1)) {
               var1 = TreeRangeSet.this.rangeEnclosing(var1);
               var4 = var3;
               if (var1 != null) {
                  var4 = var3;
                  if (!var1.intersection(this.restriction).isEmpty()) {
                     var4 = true;
                  }
               }
            }
         }

         return var4;
      }

      @NullableDecl
      public Range<C> rangeContaining(C var1) {
         boolean var2 = this.restriction.contains(var1);
         Object var3 = null;
         if (!var2) {
            return null;
         } else {
            Range var4 = TreeRangeSet.this.rangeContaining(var1);
            if (var4 == null) {
               var4 = (Range)var3;
            } else {
               var4 = var4.intersection(this.restriction);
            }

            return var4;
         }
      }

      public void remove(Range<C> var1) {
         if (var1.isConnected(this.restriction)) {
            TreeRangeSet.this.remove(var1.intersection(this.restriction));
         }

      }

      public RangeSet<C> subRangeSet(Range<C> var1) {
         if (var1.encloses(this.restriction)) {
            return this;
         } else {
            return (RangeSet)(var1.isConnected(this.restriction) ? new TreeRangeSet.SubRangeSet(this.restriction.intersection(var1)) : ImmutableRangeSet.of());
         }
      }
   }

   private static final class SubRangeSetRangesByLowerBound<C extends Comparable<?>> extends AbstractNavigableMap<Cut<C>, Range<C>> {
      private final Range<Cut<C>> lowerBoundWindow;
      private final NavigableMap<Cut<C>, Range<C>> rangesByLowerBound;
      private final NavigableMap<Cut<C>, Range<C>> rangesByUpperBound;
      private final Range<C> restriction;

      private SubRangeSetRangesByLowerBound(Range<Cut<C>> var1, Range<C> var2, NavigableMap<Cut<C>, Range<C>> var3) {
         this.lowerBoundWindow = (Range)Preconditions.checkNotNull(var1);
         this.restriction = (Range)Preconditions.checkNotNull(var2);
         this.rangesByLowerBound = (NavigableMap)Preconditions.checkNotNull(var3);
         this.rangesByUpperBound = new TreeRangeSet.RangesByUpperBound(var3);
      }

      // $FF: synthetic method
      SubRangeSetRangesByLowerBound(Range var1, Range var2, NavigableMap var3, Object var4) {
         this(var1, var2, var3);
      }

      private NavigableMap<Cut<C>, Range<C>> subMap(Range<Cut<C>> var1) {
         return (NavigableMap)(!var1.isConnected(this.lowerBoundWindow) ? ImmutableSortedMap.of() : new TreeRangeSet.SubRangeSetRangesByLowerBound(this.lowerBoundWindow.intersection(var1), this.restriction, this.rangesByLowerBound));
      }

      public Comparator<? super Cut<C>> comparator() {
         return Ordering.natural();
      }

      public boolean containsKey(@NullableDecl Object var1) {
         boolean var2;
         if (this.get(var1) != null) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      Iterator<Entry<Cut<C>, Range<C>>> descendingEntryIterator() {
         if (this.restriction.isEmpty()) {
            return Iterators.emptyIterator();
         } else {
            Cut var1 = (Cut)Ordering.natural().min(this.lowerBoundWindow.upperBound, Cut.belowValue(this.restriction.upperBound));
            NavigableMap var2 = this.rangesByLowerBound;
            Comparable var3 = var1.endpoint();
            boolean var4;
            if (var1.typeAsUpperBound() == BoundType.CLOSED) {
               var4 = true;
            } else {
               var4 = false;
            }

            return new AbstractIterator<Entry<Cut<C>, Range<C>>>(var2.headMap(var3, var4).descendingMap().values().iterator()) {
               // $FF: synthetic field
               final Iterator val$completeRangeItr;

               {
                  this.val$completeRangeItr = var2;
               }

               protected Entry<Cut<C>, Range<C>> computeNext() {
                  if (!this.val$completeRangeItr.hasNext()) {
                     return (Entry)this.endOfData();
                  } else {
                     Range var1 = (Range)this.val$completeRangeItr.next();
                     if (SubRangeSetRangesByLowerBound.this.restriction.lowerBound.compareTo(var1.upperBound) >= 0) {
                        return (Entry)this.endOfData();
                     } else {
                        var1 = var1.intersection(SubRangeSetRangesByLowerBound.this.restriction);
                        return SubRangeSetRangesByLowerBound.this.lowerBoundWindow.contains(var1.lowerBound) ? Maps.immutableEntry(var1.lowerBound, var1) : (Entry)this.endOfData();
                     }
                  }
               }
            };
         }
      }

      Iterator<Entry<Cut<C>, Range<C>>> entryIterator() {
         if (this.restriction.isEmpty()) {
            return Iterators.emptyIterator();
         } else if (this.lowerBoundWindow.upperBound.isLessThan(this.restriction.lowerBound)) {
            return Iterators.emptyIterator();
         } else {
            boolean var1 = this.lowerBoundWindow.lowerBound.isLessThan(this.restriction.lowerBound);
            boolean var2 = false;
            final Iterator var3;
            if (var1) {
               var3 = this.rangesByUpperBound.tailMap(this.restriction.lowerBound, false).values().iterator();
            } else {
               NavigableMap var5 = this.rangesByLowerBound;
               Comparable var4 = this.lowerBoundWindow.lowerBound.endpoint();
               if (this.lowerBoundWindow.lowerBoundType() == BoundType.CLOSED) {
                  var2 = true;
               }

               var3 = var5.tailMap(var4, var2).values().iterator();
            }

            return new AbstractIterator<Entry<Cut<C>, Range<C>>>((Cut)Ordering.natural().min(this.lowerBoundWindow.upperBound, Cut.belowValue(this.restriction.upperBound))) {
               // $FF: synthetic field
               final Cut val$upperBoundOnLowerBounds;

               {
                  this.val$upperBoundOnLowerBounds = var3x;
               }

               protected Entry<Cut<C>, Range<C>> computeNext() {
                  if (!var3.hasNext()) {
                     return (Entry)this.endOfData();
                  } else {
                     Range var1 = (Range)var3.next();
                     if (this.val$upperBoundOnLowerBounds.isLessThan(var1.lowerBound)) {
                        return (Entry)this.endOfData();
                     } else {
                        var1 = var1.intersection(SubRangeSetRangesByLowerBound.this.restriction);
                        return Maps.immutableEntry(var1.lowerBound, var1);
                     }
                  }
               }
            };
         }
      }

      @NullableDecl
      public Range<C> get(@NullableDecl Object var1) {
         if (var1 instanceof Cut) {
            boolean var10001;
            Cut var7;
            try {
               var7 = (Cut)var1;
               if (!this.lowerBoundWindow.contains(var7) || var7.compareTo(this.restriction.lowerBound) < 0 || var7.compareTo(this.restriction.upperBound) >= 0) {
                  return null;
               }
            } catch (ClassCastException var6) {
               var10001 = false;
               return null;
            }

            Range var8;
            label80: {
               try {
                  if (var7.equals(this.restriction.lowerBound)) {
                     var8 = (Range)Maps.valueOrNull(this.rangesByLowerBound.floorEntry(var7));
                     break label80;
                  }
               } catch (ClassCastException var5) {
                  var10001 = false;
                  return null;
               }

               try {
                  var8 = (Range)this.rangesByLowerBound.get(var7);
               } catch (ClassCastException var3) {
                  var10001 = false;
                  return null;
               }

               if (var8 != null) {
                  try {
                     var8 = var8.intersection(this.restriction);
                     return var8;
                  } catch (ClassCastException var2) {
                     var10001 = false;
                  }
               }

               return null;
            }

            if (var8 != null) {
               try {
                  if (var8.upperBound.compareTo(this.restriction.lowerBound) > 0) {
                     return var8.intersection(this.restriction);
                  }
               } catch (ClassCastException var4) {
                  var10001 = false;
               }
            }
         }

         return null;
      }

      public NavigableMap<Cut<C>, Range<C>> headMap(Cut<C> var1, boolean var2) {
         return this.subMap(Range.upTo(var1, BoundType.forBoolean(var2)));
      }

      public int size() {
         return Iterators.size(this.entryIterator());
      }

      public NavigableMap<Cut<C>, Range<C>> subMap(Cut<C> var1, boolean var2, Cut<C> var3, boolean var4) {
         return this.subMap(Range.range(var1, BoundType.forBoolean(var2), var3, BoundType.forBoolean(var4)));
      }

      public NavigableMap<Cut<C>, Range<C>> tailMap(Cut<C> var1, boolean var2) {
         return this.subMap(Range.downTo(var1, BoundType.forBoolean(var2)));
      }
   }
}
