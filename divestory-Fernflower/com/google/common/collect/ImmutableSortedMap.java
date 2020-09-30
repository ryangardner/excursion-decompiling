package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class ImmutableSortedMap<K, V> extends ImmutableSortedMapFauxverideShim<K, V> implements NavigableMap<K, V> {
   private static final ImmutableSortedMap<Comparable, Object> NATURAL_EMPTY_MAP = new ImmutableSortedMap(ImmutableSortedSet.emptySet(Ordering.natural()), ImmutableList.of());
   private static final Comparator<Comparable> NATURAL_ORDER = Ordering.natural();
   private static final long serialVersionUID = 0L;
   private transient ImmutableSortedMap<K, V> descendingMap;
   private final transient RegularImmutableSortedSet<K> keySet;
   private final transient ImmutableList<V> valueList;

   ImmutableSortedMap(RegularImmutableSortedSet<K> var1, ImmutableList<V> var2) {
      this(var1, var2, (ImmutableSortedMap)null);
   }

   ImmutableSortedMap(RegularImmutableSortedSet<K> var1, ImmutableList<V> var2, ImmutableSortedMap<K, V> var3) {
      this.keySet = var1;
      this.valueList = var2;
      this.descendingMap = var3;
   }

   // $FF: synthetic method
   static RegularImmutableSortedSet access$100(ImmutableSortedMap var0) {
      return var0.keySet;
   }

   // $FF: synthetic method
   static ImmutableList access$200(ImmutableSortedMap var0) {
      return var0.valueList;
   }

   public static <K, V> ImmutableSortedMap<K, V> copyOf(Iterable<? extends Entry<? extends K, ? extends V>> var0) {
      return copyOf((Iterable)var0, (Ordering)NATURAL_ORDER);
   }

   public static <K, V> ImmutableSortedMap<K, V> copyOf(Iterable<? extends Entry<? extends K, ? extends V>> var0, Comparator<? super K> var1) {
      return fromEntries((Comparator)Preconditions.checkNotNull(var1), false, var0);
   }

   public static <K, V> ImmutableSortedMap<K, V> copyOf(Map<? extends K, ? extends V> var0) {
      return copyOfInternal(var0, (Ordering)NATURAL_ORDER);
   }

   public static <K, V> ImmutableSortedMap<K, V> copyOf(Map<? extends K, ? extends V> var0, Comparator<? super K> var1) {
      return copyOfInternal(var0, (Comparator)Preconditions.checkNotNull(var1));
   }

   private static <K, V> ImmutableSortedMap<K, V> copyOfInternal(Map<? extends K, ? extends V> var0, Comparator<? super K> var1) {
      boolean var2 = var0 instanceof SortedMap;
      boolean var3 = false;
      boolean var4 = var3;
      if (var2) {
         Comparator var5 = ((SortedMap)var0).comparator();
         if (var5 == null) {
            var4 = var3;
            if (var1 == NATURAL_ORDER) {
               var4 = true;
            }
         } else {
            var4 = var1.equals(var5);
         }
      }

      if (var4 && var0 instanceof ImmutableSortedMap) {
         ImmutableSortedMap var6 = (ImmutableSortedMap)var0;
         if (!var6.isPartialView()) {
            return var6;
         }
      }

      return fromEntries(var1, var4, var0.entrySet());
   }

   public static <K, V> ImmutableSortedMap<K, V> copyOfSorted(SortedMap<K, ? extends V> var0) {
      Comparator var1 = var0.comparator();
      Comparator var2 = var1;
      if (var1 == null) {
         var2 = NATURAL_ORDER;
      }

      if (var0 instanceof ImmutableSortedMap) {
         ImmutableSortedMap var3 = (ImmutableSortedMap)var0;
         if (!var3.isPartialView()) {
            return var3;
         }
      }

      return fromEntries(var2, true, var0.entrySet());
   }

   static <K, V> ImmutableSortedMap<K, V> emptyMap(Comparator<? super K> var0) {
      return Ordering.natural().equals(var0) ? of() : new ImmutableSortedMap(ImmutableSortedSet.emptySet(var0), ImmutableList.of());
   }

   private static <K, V> ImmutableSortedMap<K, V> fromEntries(Comparator<? super K> var0, boolean var1, Iterable<? extends Entry<? extends K, ? extends V>> var2) {
      Entry[] var3 = (Entry[])Iterables.toArray(var2, (Object[])EMPTY_ENTRY_ARRAY);
      return fromEntries(var0, var1, var3, var3.length);
   }

   private static <K, V> ImmutableSortedMap<K, V> fromEntries(final Comparator<? super K> var0, boolean var1, Entry<K, V>[] var2, int var3) {
      if (var3 != 0) {
         int var4 = 0;
         if (var3 == 1) {
            return of(var0, var2[0].getKey(), var2[0].getValue());
         } else {
            Object[] var5 = new Object[var3];
            Object[] var6 = new Object[var3];
            Object var7;
            Object var8;
            if (var1) {
               while(var4 < var3) {
                  var7 = var2[var4].getKey();
                  var8 = var2[var4].getValue();
                  CollectPreconditions.checkEntryNotNull(var7, var8);
                  var5[var4] = var7;
                  var6[var4] = var8;
                  ++var4;
               }
            } else {
               Arrays.sort(var2, 0, var3, new Comparator<Entry<K, V>>() {
                  public int compare(Entry<K, V> var1, Entry<K, V> var2) {
                     return var0.compare(var1.getKey(), var2.getKey());
                  }
               });
               var7 = var2[0].getKey();
               var5[0] = var7;
               var6[0] = var2[0].getValue();
               CollectPreconditions.checkEntryNotNull(var5[0], var6[0]);

               for(var4 = 1; var4 < var3; var7 = var8) {
                  var8 = var2[var4].getKey();
                  Object var9 = var2[var4].getValue();
                  CollectPreconditions.checkEntryNotNull(var8, var9);
                  var5[var4] = var8;
                  var6[var4] = var9;
                  if (var0.compare(var7, var8) != 0) {
                     var1 = true;
                  } else {
                     var1 = false;
                  }

                  checkNoConflict(var1, "key", var2[var4 - 1], var2[var4]);
                  ++var4;
               }
            }

            return new ImmutableSortedMap(new RegularImmutableSortedSet(ImmutableList.asImmutableList(var5), var0), ImmutableList.asImmutableList(var6));
         }
      } else {
         return emptyMap(var0);
      }
   }

   private ImmutableSortedMap<K, V> getSubMap(int var1, int var2) {
      if (var1 == 0 && var2 == this.size()) {
         return this;
      } else {
         return var1 == var2 ? emptyMap(this.comparator()) : new ImmutableSortedMap(this.keySet.getSubSet(var1, var2), this.valueList.subList(var1, var2));
      }
   }

   public static <K extends Comparable<?>, V> ImmutableSortedMap.Builder<K, V> naturalOrder() {
      return new ImmutableSortedMap.Builder(Ordering.natural());
   }

   public static <K, V> ImmutableSortedMap<K, V> of() {
      return NATURAL_EMPTY_MAP;
   }

   public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K var0, V var1) {
      return of(Ordering.natural(), var0, var1);
   }

   public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K var0, V var1, K var2, V var3) {
      return ofEntries(entryOf(var0, var1), entryOf(var2, var3));
   }

   public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5) {
      return ofEntries(entryOf(var0, var1), entryOf(var2, var3), entryOf(var4, var5));
   }

   public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7) {
      return ofEntries(entryOf(var0, var1), entryOf(var2, var3), entryOf(var4, var5), entryOf(var6, var7));
   }

   public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7, K var8, V var9) {
      return ofEntries(entryOf(var0, var1), entryOf(var2, var3), entryOf(var4, var5), entryOf(var6, var7), entryOf(var8, var9));
   }

   private static <K, V> ImmutableSortedMap<K, V> of(Comparator<? super K> var0, K var1, V var2) {
      return new ImmutableSortedMap(new RegularImmutableSortedSet(ImmutableList.of(var1), (Comparator)Preconditions.checkNotNull(var0)), ImmutableList.of(var2));
   }

   private static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> ofEntries(Entry<K, V>... var0) {
      return fromEntries(Ordering.natural(), false, var0, var0.length);
   }

   public static <K, V> ImmutableSortedMap.Builder<K, V> orderedBy(Comparator<K> var0) {
      return new ImmutableSortedMap.Builder(var0);
   }

   public static <K extends Comparable<?>, V> ImmutableSortedMap.Builder<K, V> reverseOrder() {
      return new ImmutableSortedMap.Builder(Ordering.natural().reverse());
   }

   public Entry<K, V> ceilingEntry(K var1) {
      return this.tailMap(var1, true).firstEntry();
   }

   public K ceilingKey(K var1) {
      return Maps.keyOrNull(this.ceilingEntry(var1));
   }

   public Comparator<? super K> comparator() {
      return this.keySet().comparator();
   }

   ImmutableSet<Entry<K, V>> createEntrySet() {
      Object var1;
      if (this.isEmpty()) {
         var1 = ImmutableSet.of();
      } else {
         var1 = new ImmutableSortedMap$1EntrySet(this);
      }

      return (ImmutableSet)var1;
   }

   ImmutableSet<K> createKeySet() {
      throw new AssertionError("should never be called");
   }

   ImmutableCollection<V> createValues() {
      throw new AssertionError("should never be called");
   }

   public ImmutableSortedSet<K> descendingKeySet() {
      return this.keySet.descendingSet();
   }

   public ImmutableSortedMap<K, V> descendingMap() {
      ImmutableSortedMap var1 = this.descendingMap;
      ImmutableSortedMap var2 = var1;
      if (var1 == null) {
         if (this.isEmpty()) {
            return emptyMap(Ordering.from(this.comparator()).reverse());
         }

         var2 = new ImmutableSortedMap((RegularImmutableSortedSet)this.keySet.descendingSet(), this.valueList.reverse(), this);
      }

      return var2;
   }

   public ImmutableSet<Entry<K, V>> entrySet() {
      return super.entrySet();
   }

   public Entry<K, V> firstEntry() {
      Entry var1;
      if (this.isEmpty()) {
         var1 = null;
      } else {
         var1 = (Entry)this.entrySet().asList().get(0);
      }

      return var1;
   }

   public K firstKey() {
      return this.keySet().first();
   }

   public Entry<K, V> floorEntry(K var1) {
      return this.headMap(var1, true).lastEntry();
   }

   public K floorKey(K var1) {
      return Maps.keyOrNull(this.floorEntry(var1));
   }

   public V get(@NullableDecl Object var1) {
      int var2 = this.keySet.indexOf(var1);
      if (var2 == -1) {
         var1 = null;
      } else {
         var1 = this.valueList.get(var2);
      }

      return var1;
   }

   public ImmutableSortedMap<K, V> headMap(K var1) {
      return this.headMap(var1, false);
   }

   public ImmutableSortedMap<K, V> headMap(K var1, boolean var2) {
      return this.getSubMap(0, this.keySet.headIndex(Preconditions.checkNotNull(var1), var2));
   }

   public Entry<K, V> higherEntry(K var1) {
      return this.tailMap(var1, false).firstEntry();
   }

   public K higherKey(K var1) {
      return Maps.keyOrNull(this.higherEntry(var1));
   }

   boolean isPartialView() {
      boolean var1;
      if (!this.keySet.isPartialView() && !this.valueList.isPartialView()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public ImmutableSortedSet<K> keySet() {
      return this.keySet;
   }

   public Entry<K, V> lastEntry() {
      Entry var1;
      if (this.isEmpty()) {
         var1 = null;
      } else {
         var1 = (Entry)this.entrySet().asList().get(this.size() - 1);
      }

      return var1;
   }

   public K lastKey() {
      return this.keySet().last();
   }

   public Entry<K, V> lowerEntry(K var1) {
      return this.headMap(var1, false).lastEntry();
   }

   public K lowerKey(K var1) {
      return Maps.keyOrNull(this.lowerEntry(var1));
   }

   public ImmutableSortedSet<K> navigableKeySet() {
      return this.keySet;
   }

   @Deprecated
   public final Entry<K, V> pollFirstEntry() {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public final Entry<K, V> pollLastEntry() {
      throw new UnsupportedOperationException();
   }

   public int size() {
      return this.valueList.size();
   }

   public ImmutableSortedMap<K, V> subMap(K var1, K var2) {
      return this.subMap(var1, true, var2, false);
   }

   public ImmutableSortedMap<K, V> subMap(K var1, boolean var2, K var3, boolean var4) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var3);
      boolean var5;
      if (this.comparator().compare(var1, var3) <= 0) {
         var5 = true;
      } else {
         var5 = false;
      }

      Preconditions.checkArgument(var5, "expected fromKey <= toKey but %s > %s", var1, var3);
      return this.headMap(var3, var4).tailMap(var1, var2);
   }

   public ImmutableSortedMap<K, V> tailMap(K var1) {
      return this.tailMap(var1, true);
   }

   public ImmutableSortedMap<K, V> tailMap(K var1, boolean var2) {
      return this.getSubMap(this.keySet.tailIndex(Preconditions.checkNotNull(var1), var2), this.size());
   }

   public ImmutableCollection<V> values() {
      return this.valueList;
   }

   Object writeReplace() {
      return new ImmutableSortedMap.SerializedForm(this);
   }

   public static class Builder<K, V> extends ImmutableMap.Builder<K, V> {
      private final Comparator<? super K> comparator;
      private transient Object[] keys;
      private transient Object[] values;

      public Builder(Comparator<? super K> var1) {
         this(var1, 4);
      }

      private Builder(Comparator<? super K> var1, int var2) {
         this.comparator = (Comparator)Preconditions.checkNotNull(var1);
         this.keys = new Object[var2];
         this.values = new Object[var2];
      }

      private void ensureCapacity(int var1) {
         Object[] var2 = this.keys;
         if (var1 > var2.length) {
            var1 = ImmutableCollection.Builder.expandedCapacity(var2.length, var1);
            this.keys = Arrays.copyOf(this.keys, var1);
            this.values = Arrays.copyOf(this.values, var1);
         }

      }

      public ImmutableSortedMap<K, V> build() {
         int var1 = this.size;
         if (var1 != 0) {
            int var2 = 0;
            if (var1 != 1) {
               Object[] var3 = Arrays.copyOf(this.keys, this.size);
               Object[] var4 = (Object[])var3;
               Arrays.sort(var4, this.comparator);

               Object[] var5;
               for(var5 = new Object[this.size]; var2 < this.size; ++var2) {
                  if (var2 > 0) {
                     Comparator var6 = this.comparator;
                     var1 = var2 - 1;
                     if (var6.compare(var3[var1], var3[var2]) == 0) {
                        StringBuilder var7 = new StringBuilder();
                        var7.append("keys required to be distinct but compared as equal: ");
                        var7.append(var3[var1]);
                        var7.append(" and ");
                        var7.append(var3[var2]);
                        throw new IllegalArgumentException(var7.toString());
                     }
                  }

                  var5[Arrays.binarySearch(var4, this.keys[var2], this.comparator)] = this.values[var2];
               }

               return new ImmutableSortedMap(new RegularImmutableSortedSet(ImmutableList.asImmutableList(var3), this.comparator), ImmutableList.asImmutableList(var5));
            } else {
               return ImmutableSortedMap.of(this.comparator, this.keys[0], this.values[0]);
            }
         } else {
            return ImmutableSortedMap.emptyMap(this.comparator);
         }
      }

      @Deprecated
      public ImmutableSortedMap.Builder<K, V> orderEntriesByValue(Comparator<? super V> var1) {
         throw new UnsupportedOperationException("Not available on ImmutableSortedMap.Builder");
      }

      public ImmutableSortedMap.Builder<K, V> put(K var1, V var2) {
         this.ensureCapacity(this.size + 1);
         CollectPreconditions.checkEntryNotNull(var1, var2);
         this.keys[this.size] = var1;
         this.values[this.size] = var2;
         ++this.size;
         return this;
      }

      public ImmutableSortedMap.Builder<K, V> put(Entry<? extends K, ? extends V> var1) {
         super.put(var1);
         return this;
      }

      public ImmutableSortedMap.Builder<K, V> putAll(Iterable<? extends Entry<? extends K, ? extends V>> var1) {
         super.putAll(var1);
         return this;
      }

      public ImmutableSortedMap.Builder<K, V> putAll(Map<? extends K, ? extends V> var1) {
         super.putAll(var1);
         return this;
      }
   }

   private static class SerializedForm extends ImmutableMap.SerializedForm {
      private static final long serialVersionUID = 0L;
      private final Comparator<Object> comparator;

      SerializedForm(ImmutableSortedMap<?, ?> var1) {
         super(var1);
         this.comparator = var1.comparator();
      }

      Object readResolve() {
         return this.createMap(new ImmutableSortedMap.Builder(this.comparator));
      }
   }
}
