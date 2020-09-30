package com.google.common.collect;

import com.google.common.base.Converter;
import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Maps {
   private Maps() {
   }

   public static <A, B> Converter<A, B> asConverter(BiMap<A, B> var0) {
      return new Maps.BiMapConverter(var0);
   }

   static <K, V1, V2> Function<Entry<K, V1>, Entry<K, V2>> asEntryToEntryFunction(final Maps.EntryTransformer<? super K, ? super V1, V2> var0) {
      Preconditions.checkNotNull(var0);
      return new Function<Entry<K, V1>, Entry<K, V2>>() {
         public Entry<K, V2> apply(Entry<K, V1> var1) {
            return Maps.transformEntry(var0, var1);
         }
      };
   }

   static <K, V1, V2> Function<Entry<K, V1>, V2> asEntryToValueFunction(final Maps.EntryTransformer<? super K, ? super V1, V2> var0) {
      Preconditions.checkNotNull(var0);
      return new Function<Entry<K, V1>, V2>() {
         public V2 apply(Entry<K, V1> var1) {
            return var0.transformEntry(var1.getKey(), var1.getValue());
         }
      };
   }

   static <K, V1, V2> Maps.EntryTransformer<K, V1, V2> asEntryTransformer(final Function<? super V1, V2> var0) {
      Preconditions.checkNotNull(var0);
      return new Maps.EntryTransformer<K, V1, V2>() {
         public V2 transformEntry(K var1, V1 var2) {
            return var0.apply(var2);
         }
      };
   }

   public static <K, V> Map<K, V> asMap(Set<K> var0, Function<? super K, V> var1) {
      return new Maps.AsMapView(var0, var1);
   }

   public static <K, V> NavigableMap<K, V> asMap(NavigableSet<K> var0, Function<? super K, V> var1) {
      return new Maps.NavigableAsMapView(var0, var1);
   }

   public static <K, V> SortedMap<K, V> asMap(SortedSet<K> var0, Function<? super K, V> var1) {
      return new Maps.SortedAsMapView(var0, var1);
   }

   static <K, V> Iterator<Entry<K, V>> asMapEntryIterator(Set<K> var0, final Function<? super K, V> var1) {
      return new TransformedIterator<K, Entry<K, V>>(var0.iterator()) {
         Entry<K, V> transform(K var1x) {
            return Maps.immutableEntry(var1x, var1.apply(var1x));
         }
      };
   }

   static <K, V1, V2> Function<V1, V2> asValueToValueFunction(final Maps.EntryTransformer<? super K, V1, V2> var0, final K var1) {
      Preconditions.checkNotNull(var0);
      return new Function<V1, V2>() {
         public V2 apply(@NullableDecl V1 var1x) {
            return var0.transformEntry(var1, var1x);
         }
      };
   }

   static int capacity(int var0) {
      if (var0 < 3) {
         CollectPreconditions.checkNonnegative(var0, "expectedSize");
         return var0 + 1;
      } else {
         return var0 < 1073741824 ? (int)((float)var0 / 0.75F + 1.0F) : Integer.MAX_VALUE;
      }
   }

   static <K, V> boolean containsEntryImpl(Collection<Entry<K, V>> var0, Object var1) {
      return !(var1 instanceof Entry) ? false : var0.contains(unmodifiableEntry((Entry)var1));
   }

   static boolean containsKeyImpl(Map<?, ?> var0, @NullableDecl Object var1) {
      return Iterators.contains(keyIterator(var0.entrySet().iterator()), var1);
   }

   static boolean containsValueImpl(Map<?, ?> var0, @NullableDecl Object var1) {
      return Iterators.contains(valueIterator(var0.entrySet().iterator()), var1);
   }

   public static <K, V> MapDifference<K, V> difference(Map<? extends K, ? extends V> var0, Map<? extends K, ? extends V> var1) {
      return (MapDifference)(var0 instanceof SortedMap ? difference((SortedMap)var0, var1) : difference(var0, var1, Equivalence.equals()));
   }

   public static <K, V> MapDifference<K, V> difference(Map<? extends K, ? extends V> var0, Map<? extends K, ? extends V> var1, Equivalence<? super V> var2) {
      Preconditions.checkNotNull(var2);
      LinkedHashMap var3 = newLinkedHashMap();
      LinkedHashMap var4 = new LinkedHashMap(var1);
      LinkedHashMap var5 = newLinkedHashMap();
      LinkedHashMap var6 = newLinkedHashMap();
      doDifference(var0, var1, var2, var3, var4, var5, var6);
      return new Maps.MapDifferenceImpl(var3, var4, var5, var6);
   }

   public static <K, V> SortedMapDifference<K, V> difference(SortedMap<K, ? extends V> var0, Map<? extends K, ? extends V> var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      Comparator var2 = orNaturalOrder(var0.comparator());
      TreeMap var3 = newTreeMap(var2);
      TreeMap var4 = newTreeMap(var2);
      var4.putAll(var1);
      TreeMap var5 = newTreeMap(var2);
      TreeMap var6 = newTreeMap(var2);
      doDifference(var0, var1, Equivalence.equals(), var3, var4, var5, var6);
      return new Maps.SortedMapDifferenceImpl(var3, var4, var5, var6);
   }

   private static <K, V> void doDifference(Map<? extends K, ? extends V> var0, Map<? extends K, ? extends V> var1, Equivalence<? super V> var2, Map<K, V> var3, Map<K, V> var4, Map<K, V> var5, Map<K, MapDifference.ValueDifference<V>> var6) {
      Iterator var10 = var0.entrySet().iterator();

      while(var10.hasNext()) {
         Entry var7 = (Entry)var10.next();
         Object var8 = var7.getKey();
         Object var11 = var7.getValue();
         if (var1.containsKey(var8)) {
            Object var9 = var4.remove(var8);
            if (var2.equivalent(var11, var9)) {
               var5.put(var8, var11);
            } else {
               var6.put(var8, Maps.ValueDifferenceImpl.create(var11, var9));
            }
         } else {
            var3.put(var8, var11);
         }
      }

   }

   static boolean equalsImpl(Map<?, ?> var0, Object var1) {
      if (var0 == var1) {
         return true;
      } else if (var1 instanceof Map) {
         Map var2 = (Map)var1;
         return var0.entrySet().equals(var2.entrySet());
      } else {
         return false;
      }
   }

   public static <K, V> BiMap<K, V> filterEntries(BiMap<K, V> var0, Predicate<? super Entry<K, V>> var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      Object var2;
      if (var0 instanceof Maps.FilteredEntryBiMap) {
         var2 = filterFiltered((Maps.FilteredEntryBiMap)var0, var1);
      } else {
         var2 = new Maps.FilteredEntryBiMap(var0, var1);
      }

      return (BiMap)var2;
   }

   public static <K, V> Map<K, V> filterEntries(Map<K, V> var0, Predicate<? super Entry<K, V>> var1) {
      Preconditions.checkNotNull(var1);
      Object var2;
      if (var0 instanceof Maps.AbstractFilteredMap) {
         var2 = filterFiltered((Maps.AbstractFilteredMap)var0, var1);
      } else {
         var2 = new Maps.FilteredEntryMap((Map)Preconditions.checkNotNull(var0), var1);
      }

      return (Map)var2;
   }

   public static <K, V> NavigableMap<K, V> filterEntries(NavigableMap<K, V> var0, Predicate<? super Entry<K, V>> var1) {
      Preconditions.checkNotNull(var1);
      Object var2;
      if (var0 instanceof Maps.FilteredEntryNavigableMap) {
         var2 = filterFiltered((Maps.FilteredEntryNavigableMap)var0, var1);
      } else {
         var2 = new Maps.FilteredEntryNavigableMap((NavigableMap)Preconditions.checkNotNull(var0), var1);
      }

      return (NavigableMap)var2;
   }

   public static <K, V> SortedMap<K, V> filterEntries(SortedMap<K, V> var0, Predicate<? super Entry<K, V>> var1) {
      Preconditions.checkNotNull(var1);
      Object var2;
      if (var0 instanceof Maps.FilteredEntrySortedMap) {
         var2 = filterFiltered((Maps.FilteredEntrySortedMap)var0, var1);
      } else {
         var2 = new Maps.FilteredEntrySortedMap((SortedMap)Preconditions.checkNotNull(var0), var1);
      }

      return (SortedMap)var2;
   }

   private static <K, V> BiMap<K, V> filterFiltered(Maps.FilteredEntryBiMap<K, V> var0, Predicate<? super Entry<K, V>> var1) {
      var1 = Predicates.and(var0.predicate, var1);
      return new Maps.FilteredEntryBiMap(var0.unfiltered(), var1);
   }

   private static <K, V> Map<K, V> filterFiltered(Maps.AbstractFilteredMap<K, V> var0, Predicate<? super Entry<K, V>> var1) {
      return new Maps.FilteredEntryMap(var0.unfiltered, Predicates.and(var0.predicate, var1));
   }

   private static <K, V> NavigableMap<K, V> filterFiltered(Maps.FilteredEntryNavigableMap<K, V> var0, Predicate<? super Entry<K, V>> var1) {
      var1 = Predicates.and(var0.entryPredicate, var1);
      return new Maps.FilteredEntryNavigableMap(var0.unfiltered, var1);
   }

   private static <K, V> SortedMap<K, V> filterFiltered(Maps.FilteredEntrySortedMap<K, V> var0, Predicate<? super Entry<K, V>> var1) {
      var1 = Predicates.and(var0.predicate, var1);
      return new Maps.FilteredEntrySortedMap(var0.sortedMap(), var1);
   }

   public static <K, V> BiMap<K, V> filterKeys(BiMap<K, V> var0, Predicate<? super K> var1) {
      Preconditions.checkNotNull(var1);
      return filterEntries(var0, keyPredicateOnEntries(var1));
   }

   public static <K, V> Map<K, V> filterKeys(Map<K, V> var0, Predicate<? super K> var1) {
      Preconditions.checkNotNull(var1);
      Predicate var2 = keyPredicateOnEntries(var1);
      Object var3;
      if (var0 instanceof Maps.AbstractFilteredMap) {
         var3 = filterFiltered((Maps.AbstractFilteredMap)var0, var2);
      } else {
         var3 = new Maps.FilteredKeyMap((Map)Preconditions.checkNotNull(var0), var1, var2);
      }

      return (Map)var3;
   }

   public static <K, V> NavigableMap<K, V> filterKeys(NavigableMap<K, V> var0, Predicate<? super K> var1) {
      return filterEntries(var0, keyPredicateOnEntries(var1));
   }

   public static <K, V> SortedMap<K, V> filterKeys(SortedMap<K, V> var0, Predicate<? super K> var1) {
      return filterEntries(var0, keyPredicateOnEntries(var1));
   }

   public static <K, V> BiMap<K, V> filterValues(BiMap<K, V> var0, Predicate<? super V> var1) {
      return filterEntries(var0, valuePredicateOnEntries(var1));
   }

   public static <K, V> Map<K, V> filterValues(Map<K, V> var0, Predicate<? super V> var1) {
      return filterEntries(var0, valuePredicateOnEntries(var1));
   }

   public static <K, V> NavigableMap<K, V> filterValues(NavigableMap<K, V> var0, Predicate<? super V> var1) {
      return filterEntries(var0, valuePredicateOnEntries(var1));
   }

   public static <K, V> SortedMap<K, V> filterValues(SortedMap<K, V> var0, Predicate<? super V> var1) {
      return filterEntries(var0, valuePredicateOnEntries(var1));
   }

   public static ImmutableMap<String, String> fromProperties(Properties var0) {
      ImmutableMap.Builder var1 = ImmutableMap.builder();
      Enumeration var2 = var0.propertyNames();

      while(var2.hasMoreElements()) {
         String var3 = (String)var2.nextElement();
         var1.put(var3, var0.getProperty(var3));
      }

      return var1.build();
   }

   public static <K, V> Entry<K, V> immutableEntry(@NullableDecl K var0, @NullableDecl V var1) {
      return new ImmutableEntry(var0, var1);
   }

   public static <K extends Enum<K>, V> ImmutableMap<K, V> immutableEnumMap(Map<K, ? extends V> var0) {
      if (var0 instanceof ImmutableEnumMap) {
         return (ImmutableEnumMap)var0;
      } else {
         Iterator var4 = var0.entrySet().iterator();
         if (!var4.hasNext()) {
            return ImmutableMap.of();
         } else {
            Entry var1 = (Entry)var4.next();
            Enum var2 = (Enum)var1.getKey();
            Object var3 = var1.getValue();
            CollectPreconditions.checkEntryNotNull(var2, var3);
            EnumMap var5 = new EnumMap(var2.getDeclaringClass());
            var5.put(var2, var3);

            while(var4.hasNext()) {
               Entry var6 = (Entry)var4.next();
               var2 = (Enum)var6.getKey();
               var3 = var6.getValue();
               CollectPreconditions.checkEntryNotNull(var2, var3);
               var5.put(var2, var3);
            }

            return ImmutableEnumMap.asImmutable(var5);
         }
      }
   }

   static <E> ImmutableMap<E, Integer> indexMap(Collection<E> var0) {
      ImmutableMap.Builder var1 = new ImmutableMap.Builder(var0.size());
      Iterator var3 = var0.iterator();

      for(int var2 = 0; var3.hasNext(); ++var2) {
         var1.put(var3.next(), var2);
      }

      return var1.build();
   }

   static <K> Function<Entry<K, ?>, K> keyFunction() {
      return Maps.EntryFunction.KEY;
   }

   static <K, V> Iterator<K> keyIterator(Iterator<Entry<K, V>> var0) {
      return new TransformedIterator<Entry<K, V>, K>(var0) {
         K transform(Entry<K, V> var1) {
            return var1.getKey();
         }
      };
   }

   @NullableDecl
   static <K> K keyOrNull(@NullableDecl Entry<K, ?> var0) {
      Object var1;
      if (var0 == null) {
         var1 = null;
      } else {
         var1 = var0.getKey();
      }

      return var1;
   }

   static <K> Predicate<Entry<K, ?>> keyPredicateOnEntries(Predicate<? super K> var0) {
      return Predicates.compose(var0, keyFunction());
   }

   public static <K, V> ConcurrentMap<K, V> newConcurrentMap() {
      return new ConcurrentHashMap();
   }

   public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(Class<K> var0) {
      return new EnumMap((Class)Preconditions.checkNotNull(var0));
   }

   public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(Map<K, ? extends V> var0) {
      return new EnumMap(var0);
   }

   public static <K, V> HashMap<K, V> newHashMap() {
      return new HashMap();
   }

   public static <K, V> HashMap<K, V> newHashMap(Map<? extends K, ? extends V> var0) {
      return new HashMap(var0);
   }

   public static <K, V> HashMap<K, V> newHashMapWithExpectedSize(int var0) {
      return new HashMap(capacity(var0));
   }

   public static <K, V> IdentityHashMap<K, V> newIdentityHashMap() {
      return new IdentityHashMap();
   }

   public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
      return new LinkedHashMap();
   }

   public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(Map<? extends K, ? extends V> var0) {
      return new LinkedHashMap(var0);
   }

   public static <K, V> LinkedHashMap<K, V> newLinkedHashMapWithExpectedSize(int var0) {
      return new LinkedHashMap(capacity(var0));
   }

   public static <K extends Comparable, V> TreeMap<K, V> newTreeMap() {
      return new TreeMap();
   }

   public static <C, K extends C, V> TreeMap<K, V> newTreeMap(@NullableDecl Comparator<C> var0) {
      return new TreeMap(var0);
   }

   public static <K, V> TreeMap<K, V> newTreeMap(SortedMap<K, ? extends V> var0) {
      return new TreeMap(var0);
   }

   static <E> Comparator<? super E> orNaturalOrder(@NullableDecl Comparator<? super E> var0) {
      return (Comparator)(var0 != null ? var0 : Ordering.natural());
   }

   static <K, V> void putAllImpl(Map<K, V> var0, Map<? extends K, ? extends V> var1) {
      Iterator var3 = var1.entrySet().iterator();

      while(var3.hasNext()) {
         Entry var2 = (Entry)var3.next();
         var0.put(var2.getKey(), var2.getValue());
      }

   }

   static <K, V> boolean removeEntryImpl(Collection<Entry<K, V>> var0, Object var1) {
      return !(var1 instanceof Entry) ? false : var0.remove(unmodifiableEntry((Entry)var1));
   }

   private static <E> NavigableSet<E> removeOnlyNavigableSet(final NavigableSet<E> var0) {
      return new ForwardingNavigableSet<E>() {
         public boolean add(E var1) {
            throw new UnsupportedOperationException();
         }

         public boolean addAll(Collection<? extends E> var1) {
            throw new UnsupportedOperationException();
         }

         protected NavigableSet<E> delegate() {
            return var0;
         }

         public NavigableSet<E> descendingSet() {
            return Maps.removeOnlyNavigableSet(super.descendingSet());
         }

         public NavigableSet<E> headSet(E var1, boolean var2) {
            return Maps.removeOnlyNavigableSet(super.headSet(var1, var2));
         }

         public SortedSet<E> headSet(E var1) {
            return Maps.removeOnlySortedSet(super.headSet(var1));
         }

         public NavigableSet<E> subSet(E var1, boolean var2, E var3, boolean var4) {
            return Maps.removeOnlyNavigableSet(super.subSet(var1, var2, var3, var4));
         }

         public SortedSet<E> subSet(E var1, E var2) {
            return Maps.removeOnlySortedSet(super.subSet(var1, var2));
         }

         public NavigableSet<E> tailSet(E var1, boolean var2) {
            return Maps.removeOnlyNavigableSet(super.tailSet(var1, var2));
         }

         public SortedSet<E> tailSet(E var1) {
            return Maps.removeOnlySortedSet(super.tailSet(var1));
         }
      };
   }

   private static <E> Set<E> removeOnlySet(final Set<E> var0) {
      return new ForwardingSet<E>() {
         public boolean add(E var1) {
            throw new UnsupportedOperationException();
         }

         public boolean addAll(Collection<? extends E> var1) {
            throw new UnsupportedOperationException();
         }

         protected Set<E> delegate() {
            return var0;
         }
      };
   }

   private static <E> SortedSet<E> removeOnlySortedSet(final SortedSet<E> var0) {
      return new ForwardingSortedSet<E>() {
         public boolean add(E var1) {
            throw new UnsupportedOperationException();
         }

         public boolean addAll(Collection<? extends E> var1) {
            throw new UnsupportedOperationException();
         }

         protected SortedSet<E> delegate() {
            return var0;
         }

         public SortedSet<E> headSet(E var1) {
            return Maps.removeOnlySortedSet(super.headSet(var1));
         }

         public SortedSet<E> subSet(E var1, E var2) {
            return Maps.removeOnlySortedSet(super.subSet(var1, var2));
         }

         public SortedSet<E> tailSet(E var1) {
            return Maps.removeOnlySortedSet(super.tailSet(var1));
         }
      };
   }

   static boolean safeContainsKey(Map<?, ?> var0, Object var1) {
      Preconditions.checkNotNull(var0);

      try {
         boolean var2 = var0.containsKey(var1);
         return var2;
      } catch (NullPointerException | ClassCastException var3) {
         return false;
      }
   }

   static <V> V safeGet(Map<?, V> var0, @NullableDecl Object var1) {
      Preconditions.checkNotNull(var0);

      try {
         Object var3 = var0.get(var1);
         return var3;
      } catch (NullPointerException | ClassCastException var2) {
         return null;
      }
   }

   static <V> V safeRemove(Map<?, V> var0, Object var1) {
      Preconditions.checkNotNull(var0);

      try {
         Object var3 = var0.remove(var1);
         return var3;
      } catch (NullPointerException | ClassCastException var2) {
         return null;
      }
   }

   public static <K extends Comparable<? super K>, V> NavigableMap<K, V> subMap(NavigableMap<K, V> var0, Range<K> var1) {
      Comparator var2 = var0.comparator();
      boolean var3 = true;
      boolean var4 = true;
      boolean var5 = true;
      boolean var6;
      if (var2 != null && var0.comparator() != Ordering.natural() && var1.hasLowerBound() && var1.hasUpperBound()) {
         if (var0.comparator().compare(var1.lowerEndpoint(), var1.upperEndpoint()) <= 0) {
            var6 = true;
         } else {
            var6 = false;
         }

         Preconditions.checkArgument(var6, "map is using a custom comparator which is inconsistent with the natural ordering.");
      }

      Comparable var8;
      if (var1.hasLowerBound() && var1.hasUpperBound()) {
         var8 = var1.lowerEndpoint();
         if (var1.lowerBoundType() == BoundType.CLOSED) {
            var6 = true;
         } else {
            var6 = false;
         }

         Comparable var7 = var1.upperEndpoint();
         if (var1.upperBoundType() != BoundType.CLOSED) {
            var5 = false;
         }

         return var0.subMap(var8, var6, var7, var5);
      } else if (var1.hasLowerBound()) {
         var8 = var1.lowerEndpoint();
         if (var1.lowerBoundType() == BoundType.CLOSED) {
            var6 = var3;
         } else {
            var6 = false;
         }

         return var0.tailMap(var8, var6);
      } else if (var1.hasUpperBound()) {
         var8 = var1.upperEndpoint();
         if (var1.upperBoundType() == BoundType.CLOSED) {
            var6 = var4;
         } else {
            var6 = false;
         }

         return var0.headMap(var8, var6);
      } else {
         return (NavigableMap)Preconditions.checkNotNull(var0);
      }
   }

   public static <K, V> BiMap<K, V> synchronizedBiMap(BiMap<K, V> var0) {
      return Synchronized.biMap(var0, (Object)null);
   }

   public static <K, V> NavigableMap<K, V> synchronizedNavigableMap(NavigableMap<K, V> var0) {
      return Synchronized.navigableMap(var0);
   }

   public static <K, V> ImmutableMap<K, V> toMap(Iterable<K> var0, Function<? super K, V> var1) {
      return toMap(var0.iterator(), var1);
   }

   public static <K, V> ImmutableMap<K, V> toMap(Iterator<K> var0, Function<? super K, V> var1) {
      Preconditions.checkNotNull(var1);
      LinkedHashMap var2 = newLinkedHashMap();

      while(var0.hasNext()) {
         Object var3 = var0.next();
         var2.put(var3, var1.apply(var3));
      }

      return ImmutableMap.copyOf((Map)var2);
   }

   static String toStringImpl(Map<?, ?> var0) {
      StringBuilder var1 = Collections2.newStringBuilderForCollection(var0.size());
      var1.append('{');
      Iterator var4 = var0.entrySet().iterator();
      boolean var2 = true;

      while(var4.hasNext()) {
         Entry var3 = (Entry)var4.next();
         if (!var2) {
            var1.append(", ");
         }

         var2 = false;
         var1.append(var3.getKey());
         var1.append('=');
         var1.append(var3.getValue());
      }

      var1.append('}');
      return var1.toString();
   }

   public static <K, V1, V2> Map<K, V2> transformEntries(Map<K, V1> var0, Maps.EntryTransformer<? super K, ? super V1, V2> var1) {
      return new Maps.TransformedEntriesMap(var0, var1);
   }

   public static <K, V1, V2> NavigableMap<K, V2> transformEntries(NavigableMap<K, V1> var0, Maps.EntryTransformer<? super K, ? super V1, V2> var1) {
      return new Maps.TransformedEntriesNavigableMap(var0, var1);
   }

   public static <K, V1, V2> SortedMap<K, V2> transformEntries(SortedMap<K, V1> var0, Maps.EntryTransformer<? super K, ? super V1, V2> var1) {
      return new Maps.TransformedEntriesSortedMap(var0, var1);
   }

   static <V2, K, V1> Entry<K, V2> transformEntry(final Maps.EntryTransformer<? super K, ? super V1, V2> var0, final Entry<K, V1> var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      return new AbstractMapEntry<K, V2>() {
         public K getKey() {
            return var1.getKey();
         }

         public V2 getValue() {
            return var0.transformEntry(var1.getKey(), var1.getValue());
         }
      };
   }

   public static <K, V1, V2> Map<K, V2> transformValues(Map<K, V1> var0, Function<? super V1, V2> var1) {
      return transformEntries(var0, asEntryTransformer(var1));
   }

   public static <K, V1, V2> NavigableMap<K, V2> transformValues(NavigableMap<K, V1> var0, Function<? super V1, V2> var1) {
      return transformEntries(var0, asEntryTransformer(var1));
   }

   public static <K, V1, V2> SortedMap<K, V2> transformValues(SortedMap<K, V1> var0, Function<? super V1, V2> var1) {
      return transformEntries(var0, asEntryTransformer(var1));
   }

   public static <K, V> ImmutableMap<K, V> uniqueIndex(Iterable<V> var0, Function<? super V, K> var1) {
      return uniqueIndex(var0.iterator(), var1);
   }

   public static <K, V> ImmutableMap<K, V> uniqueIndex(Iterator<V> var0, Function<? super V, K> var1) {
      Preconditions.checkNotNull(var1);
      ImmutableMap.Builder var2 = ImmutableMap.builder();

      while(var0.hasNext()) {
         Object var3 = var0.next();
         var2.put(var1.apply(var3), var3);
      }

      try {
         ImmutableMap var6 = var2.build();
         return var6;
      } catch (IllegalArgumentException var4) {
         StringBuilder var5 = new StringBuilder();
         var5.append(var4.getMessage());
         var5.append(". To index multiple values under a key, use Multimaps.index.");
         throw new IllegalArgumentException(var5.toString());
      }
   }

   public static <K, V> BiMap<K, V> unmodifiableBiMap(BiMap<? extends K, ? extends V> var0) {
      return new Maps.UnmodifiableBiMap(var0, (BiMap)null);
   }

   static <K, V> Entry<K, V> unmodifiableEntry(final Entry<? extends K, ? extends V> var0) {
      Preconditions.checkNotNull(var0);
      return new AbstractMapEntry<K, V>() {
         public K getKey() {
            return var0.getKey();
         }

         public V getValue() {
            return var0.getValue();
         }
      };
   }

   static <K, V> UnmodifiableIterator<Entry<K, V>> unmodifiableEntryIterator(final Iterator<Entry<K, V>> var0) {
      return new UnmodifiableIterator<Entry<K, V>>() {
         public boolean hasNext() {
            return var0.hasNext();
         }

         public Entry<K, V> next() {
            return Maps.unmodifiableEntry((Entry)var0.next());
         }
      };
   }

   static <K, V> Set<Entry<K, V>> unmodifiableEntrySet(Set<Entry<K, V>> var0) {
      return new Maps.UnmodifiableEntrySet(Collections.unmodifiableSet(var0));
   }

   private static <K, V> Map<K, V> unmodifiableMap(Map<K, ? extends V> var0) {
      return (Map)(var0 instanceof SortedMap ? Collections.unmodifiableSortedMap((SortedMap)var0) : Collections.unmodifiableMap(var0));
   }

   public static <K, V> NavigableMap<K, V> unmodifiableNavigableMap(NavigableMap<K, ? extends V> var0) {
      Preconditions.checkNotNull(var0);
      return (NavigableMap)(var0 instanceof Maps.UnmodifiableNavigableMap ? var0 : new Maps.UnmodifiableNavigableMap(var0));
   }

   @NullableDecl
   private static <K, V> Entry<K, V> unmodifiableOrNull(@NullableDecl Entry<K, ? extends V> var0) {
      if (var0 == null) {
         var0 = null;
      } else {
         var0 = unmodifiableEntry(var0);
      }

      return var0;
   }

   static <V> Function<Entry<?, V>, V> valueFunction() {
      return Maps.EntryFunction.VALUE;
   }

   static <K, V> Iterator<V> valueIterator(Iterator<Entry<K, V>> var0) {
      return new TransformedIterator<Entry<K, V>, V>(var0) {
         V transform(Entry<K, V> var1) {
            return var1.getValue();
         }
      };
   }

   @NullableDecl
   static <V> V valueOrNull(@NullableDecl Entry<?, V> var0) {
      Object var1;
      if (var0 == null) {
         var1 = null;
      } else {
         var1 = var0.getValue();
      }

      return var1;
   }

   static <V> Predicate<Entry<?, V>> valuePredicateOnEntries(Predicate<? super V> var0) {
      return Predicates.compose(var0, valueFunction());
   }

   private abstract static class AbstractFilteredMap<K, V> extends Maps.ViewCachingAbstractMap<K, V> {
      final Predicate<? super Entry<K, V>> predicate;
      final Map<K, V> unfiltered;

      AbstractFilteredMap(Map<K, V> var1, Predicate<? super Entry<K, V>> var2) {
         this.unfiltered = var1;
         this.predicate = var2;
      }

      boolean apply(@NullableDecl Object var1, @NullableDecl V var2) {
         return this.predicate.apply(Maps.immutableEntry(var1, var2));
      }

      public boolean containsKey(Object var1) {
         boolean var2;
         if (this.unfiltered.containsKey(var1) && this.apply(var1, this.unfiltered.get(var1))) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      Collection<V> createValues() {
         return new Maps.FilteredMapValues(this, this.unfiltered, this.predicate);
      }

      public V get(Object var1) {
         Object var2 = this.unfiltered.get(var1);
         if (var2 != null && this.apply(var1, var2)) {
            var1 = var2;
         } else {
            var1 = null;
         }

         return var1;
      }

      public boolean isEmpty() {
         return this.entrySet().isEmpty();
      }

      public V put(K var1, V var2) {
         Preconditions.checkArgument(this.apply(var1, var2));
         return this.unfiltered.put(var1, var2);
      }

      public void putAll(Map<? extends K, ? extends V> var1) {
         Iterator var2 = var1.entrySet().iterator();

         while(var2.hasNext()) {
            Entry var3 = (Entry)var2.next();
            Preconditions.checkArgument(this.apply(var3.getKey(), var3.getValue()));
         }

         this.unfiltered.putAll(var1);
      }

      public V remove(Object var1) {
         if (this.containsKey(var1)) {
            var1 = this.unfiltered.remove(var1);
         } else {
            var1 = null;
         }

         return var1;
      }
   }

   private static class AsMapView<K, V> extends Maps.ViewCachingAbstractMap<K, V> {
      final Function<? super K, V> function;
      private final Set<K> set;

      AsMapView(Set<K> var1, Function<? super K, V> var2) {
         this.set = (Set)Preconditions.checkNotNull(var1);
         this.function = (Function)Preconditions.checkNotNull(var2);
      }

      Set<K> backingSet() {
         return this.set;
      }

      public void clear() {
         this.backingSet().clear();
      }

      public boolean containsKey(@NullableDecl Object var1) {
         return this.backingSet().contains(var1);
      }

      protected Set<Entry<K, V>> createEntrySet() {
         return new Maps$AsMapView$1EntrySetImpl(this);
      }

      public Set<K> createKeySet() {
         return Maps.removeOnlySet(this.backingSet());
      }

      Collection<V> createValues() {
         return Collections2.transform(this.set, this.function);
      }

      public V get(@NullableDecl Object var1) {
         return Collections2.safeContains(this.backingSet(), var1) ? this.function.apply(var1) : null;
      }

      public V remove(@NullableDecl Object var1) {
         return this.backingSet().remove(var1) ? this.function.apply(var1) : null;
      }

      public int size() {
         return this.backingSet().size();
      }
   }

   private static final class BiMapConverter<A, B> extends Converter<A, B> implements Serializable {
      private static final long serialVersionUID = 0L;
      private final BiMap<A, B> bimap;

      BiMapConverter(BiMap<A, B> var1) {
         this.bimap = (BiMap)Preconditions.checkNotNull(var1);
      }

      private static <X, Y> Y convert(BiMap<X, Y> var0, X var1) {
         Object var3 = var0.get(var1);
         boolean var2;
         if (var3 != null) {
            var2 = true;
         } else {
            var2 = false;
         }

         Preconditions.checkArgument(var2, "No non-null mapping present for input: %s", var1);
         return var3;
      }

      protected A doBackward(B var1) {
         return convert(this.bimap.inverse(), var1);
      }

      protected B doForward(A var1) {
         return convert(this.bimap, var1);
      }

      public boolean equals(@NullableDecl Object var1) {
         if (var1 instanceof Maps.BiMapConverter) {
            Maps.BiMapConverter var2 = (Maps.BiMapConverter)var1;
            return this.bimap.equals(var2.bimap);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return this.bimap.hashCode();
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("Maps.asConverter(");
         var1.append(this.bimap);
         var1.append(")");
         return var1.toString();
      }
   }

   abstract static class DescendingMap<K, V> extends ForwardingMap<K, V> implements NavigableMap<K, V> {
      @MonotonicNonNullDecl
      private transient Comparator<? super K> comparator;
      @MonotonicNonNullDecl
      private transient Set<Entry<K, V>> entrySet;
      @MonotonicNonNullDecl
      private transient NavigableSet<K> navigableKeySet;

      private static <T> Ordering<T> reverse(Comparator<T> var0) {
         return Ordering.from(var0).reverse();
      }

      public Entry<K, V> ceilingEntry(K var1) {
         return this.forward().floorEntry(var1);
      }

      public K ceilingKey(K var1) {
         return this.forward().floorKey(var1);
      }

      public Comparator<? super K> comparator() {
         Comparator var1 = this.comparator;
         Object var2 = var1;
         if (var1 == null) {
            var1 = this.forward().comparator();
            var2 = var1;
            if (var1 == null) {
               var2 = Ordering.natural();
            }

            var2 = reverse((Comparator)var2);
            this.comparator = (Comparator)var2;
         }

         return (Comparator)var2;
      }

      Set<Entry<K, V>> createEntrySet() {
         return new Maps$DescendingMap$1EntrySetImpl(this);
      }

      protected final Map<K, V> delegate() {
         return this.forward();
      }

      public NavigableSet<K> descendingKeySet() {
         return this.forward().navigableKeySet();
      }

      public NavigableMap<K, V> descendingMap() {
         return this.forward();
      }

      abstract Iterator<Entry<K, V>> entryIterator();

      public Set<Entry<K, V>> entrySet() {
         Set var1 = this.entrySet;
         Set var2 = var1;
         if (var1 == null) {
            var2 = this.createEntrySet();
            this.entrySet = var2;
         }

         return var2;
      }

      public Entry<K, V> firstEntry() {
         return this.forward().lastEntry();
      }

      public K firstKey() {
         return this.forward().lastKey();
      }

      public Entry<K, V> floorEntry(K var1) {
         return this.forward().ceilingEntry(var1);
      }

      public K floorKey(K var1) {
         return this.forward().ceilingKey(var1);
      }

      abstract NavigableMap<K, V> forward();

      public NavigableMap<K, V> headMap(K var1, boolean var2) {
         return this.forward().tailMap(var1, var2).descendingMap();
      }

      public SortedMap<K, V> headMap(K var1) {
         return this.headMap(var1, false);
      }

      public Entry<K, V> higherEntry(K var1) {
         return this.forward().lowerEntry(var1);
      }

      public K higherKey(K var1) {
         return this.forward().lowerKey(var1);
      }

      public Set<K> keySet() {
         return this.navigableKeySet();
      }

      public Entry<K, V> lastEntry() {
         return this.forward().firstEntry();
      }

      public K lastKey() {
         return this.forward().firstKey();
      }

      public Entry<K, V> lowerEntry(K var1) {
         return this.forward().higherEntry(var1);
      }

      public K lowerKey(K var1) {
         return this.forward().higherKey(var1);
      }

      public NavigableSet<K> navigableKeySet() {
         NavigableSet var1 = this.navigableKeySet;
         Object var2 = var1;
         if (var1 == null) {
            var2 = new Maps.NavigableKeySet(this);
            this.navigableKeySet = (NavigableSet)var2;
         }

         return (NavigableSet)var2;
      }

      public Entry<K, V> pollFirstEntry() {
         return this.forward().pollLastEntry();
      }

      public Entry<K, V> pollLastEntry() {
         return this.forward().pollFirstEntry();
      }

      public NavigableMap<K, V> subMap(K var1, boolean var2, K var3, boolean var4) {
         return this.forward().subMap(var3, var4, var1, var2).descendingMap();
      }

      public SortedMap<K, V> subMap(K var1, K var2) {
         return this.subMap(var1, true, var2, false);
      }

      public NavigableMap<K, V> tailMap(K var1, boolean var2) {
         return this.forward().headMap(var1, var2).descendingMap();
      }

      public SortedMap<K, V> tailMap(K var1) {
         return this.tailMap(var1, true);
      }

      public String toString() {
         return this.standardToString();
      }

      public Collection<V> values() {
         return new Maps.Values(this);
      }
   }

   private static enum EntryFunction implements Function<Entry<?, ?>, Object> {
      KEY {
         @NullableDecl
         public Object apply(Entry<?, ?> var1) {
            return var1.getKey();
         }
      },
      VALUE;

      static {
         Maps.EntryFunction var0 = new Maps.EntryFunction("VALUE", 1) {
            @NullableDecl
            public Object apply(Entry<?, ?> var1) {
               return var1.getValue();
            }
         };
         VALUE = var0;
      }

      private EntryFunction() {
      }

      // $FF: synthetic method
      EntryFunction(Object var3) {
         this();
      }
   }

   abstract static class EntrySet<K, V> extends Sets.ImprovedAbstractSet<Entry<K, V>> {
      public void clear() {
         this.map().clear();
      }

      public boolean contains(Object var1) {
         boolean var2 = var1 instanceof Entry;
         boolean var3 = false;
         boolean var4 = var3;
         if (var2) {
            Entry var5 = (Entry)var1;
            Object var6 = var5.getKey();
            var1 = Maps.safeGet(this.map(), var6);
            var4 = var3;
            if (Objects.equal(var1, var5.getValue())) {
               if (var1 == null) {
                  var4 = var3;
                  if (!this.map().containsKey(var6)) {
                     return var4;
                  }
               }

               var4 = true;
            }
         }

         return var4;
      }

      public boolean isEmpty() {
         return this.map().isEmpty();
      }

      abstract Map<K, V> map();

      public boolean remove(Object var1) {
         if (this.contains(var1)) {
            Entry var2 = (Entry)var1;
            return this.map().keySet().remove(var2.getKey());
         } else {
            return false;
         }
      }

      public boolean removeAll(Collection<?> var1) {
         try {
            boolean var2 = super.removeAll((Collection)Preconditions.checkNotNull(var1));
            return var2;
         } catch (UnsupportedOperationException var4) {
            return Sets.removeAllImpl(this, (Iterator)var1.iterator());
         }
      }

      public boolean retainAll(Collection<?> var1) {
         try {
            boolean var2 = super.retainAll((Collection)Preconditions.checkNotNull(var1));
            return var2;
         } catch (UnsupportedOperationException var5) {
            HashSet var3 = Sets.newHashSetWithExpectedSize(var1.size());
            Iterator var6 = var1.iterator();

            while(var6.hasNext()) {
               Object var4 = var6.next();
               if (this.contains(var4)) {
                  var3.add(((Entry)var4).getKey());
               }
            }

            return this.map().keySet().retainAll(var3);
         }
      }

      public int size() {
         return this.map().size();
      }
   }

   public interface EntryTransformer<K, V1, V2> {
      V2 transformEntry(@NullableDecl K var1, @NullableDecl V1 var2);
   }

   static final class FilteredEntryBiMap<K, V> extends Maps.FilteredEntryMap<K, V> implements BiMap<K, V> {
      private final BiMap<V, K> inverse;

      FilteredEntryBiMap(BiMap<K, V> var1, Predicate<? super Entry<K, V>> var2) {
         super(var1, var2);
         this.inverse = new Maps.FilteredEntryBiMap(var1.inverse(), inversePredicate(var2), this);
      }

      private FilteredEntryBiMap(BiMap<K, V> var1, Predicate<? super Entry<K, V>> var2, BiMap<V, K> var3) {
         super(var1, var2);
         this.inverse = var3;
      }

      private static <K, V> Predicate<Entry<V, K>> inversePredicate(final Predicate<? super Entry<K, V>> var0) {
         return new Predicate<Entry<V, K>>() {
            public boolean apply(Entry<V, K> var1) {
               return var0.apply(Maps.immutableEntry(var1.getValue(), var1.getKey()));
            }
         };
      }

      public V forcePut(@NullableDecl K var1, @NullableDecl V var2) {
         Preconditions.checkArgument(this.apply(var1, var2));
         return this.unfiltered().forcePut(var1, var2);
      }

      public BiMap<V, K> inverse() {
         return this.inverse;
      }

      BiMap<K, V> unfiltered() {
         return (BiMap)this.unfiltered;
      }

      public Set<V> values() {
         return this.inverse.keySet();
      }
   }

   static class FilteredEntryMap<K, V> extends Maps.AbstractFilteredMap<K, V> {
      final Set<Entry<K, V>> filteredEntrySet;

      FilteredEntryMap(Map<K, V> var1, Predicate<? super Entry<K, V>> var2) {
         super(var1, var2);
         this.filteredEntrySet = Sets.filter(var1.entrySet(), this.predicate);
      }

      static <K, V> boolean removeAllKeys(Map<K, V> var0, Predicate<? super Entry<K, V>> var1, Collection<?> var2) {
         Iterator var3 = var0.entrySet().iterator();
         boolean var4 = false;

         while(var3.hasNext()) {
            Entry var5 = (Entry)var3.next();
            if (var1.apply(var5) && var2.contains(var5.getKey())) {
               var3.remove();
               var4 = true;
            }
         }

         return var4;
      }

      static <K, V> boolean retainAllKeys(Map<K, V> var0, Predicate<? super Entry<K, V>> var1, Collection<?> var2) {
         Iterator var3 = var0.entrySet().iterator();
         boolean var4 = false;

         while(var3.hasNext()) {
            Entry var5 = (Entry)var3.next();
            if (var1.apply(var5) && !var2.contains(var5.getKey())) {
               var3.remove();
               var4 = true;
            }
         }

         return var4;
      }

      protected Set<Entry<K, V>> createEntrySet() {
         return new Maps.FilteredEntryMap.EntrySet();
      }

      Set<K> createKeySet() {
         return new Maps.FilteredEntryMap.KeySet();
      }

      private class EntrySet extends ForwardingSet<Entry<K, V>> {
         private EntrySet() {
         }

         // $FF: synthetic method
         EntrySet(Object var2) {
            this();
         }

         protected Set<Entry<K, V>> delegate() {
            return FilteredEntryMap.this.filteredEntrySet;
         }

         public Iterator<Entry<K, V>> iterator() {
            return new TransformedIterator<Entry<K, V>, Entry<K, V>>(FilteredEntryMap.this.filteredEntrySet.iterator()) {
               Entry<K, V> transform(final Entry<K, V> var1) {
                  return new ForwardingMapEntry<K, V>() {
                     protected Entry<K, V> delegate() {
                        return var1;
                     }

                     public V setValue(V var1x) {
                        Preconditions.checkArgument(FilteredEntryMap.this.apply(this.getKey(), var1x));
                        return super.setValue(var1x);
                     }
                  };
               }
            };
         }
      }

      class KeySet extends Maps.KeySet<K, V> {
         KeySet() {
            super(FilteredEntryMap.this);
         }

         public boolean remove(Object var1) {
            if (FilteredEntryMap.this.containsKey(var1)) {
               FilteredEntryMap.this.unfiltered.remove(var1);
               return true;
            } else {
               return false;
            }
         }

         public boolean removeAll(Collection<?> var1) {
            return Maps.FilteredEntryMap.removeAllKeys(FilteredEntryMap.this.unfiltered, FilteredEntryMap.this.predicate, var1);
         }

         public boolean retainAll(Collection<?> var1) {
            return Maps.FilteredEntryMap.retainAllKeys(FilteredEntryMap.this.unfiltered, FilteredEntryMap.this.predicate, var1);
         }

         public Object[] toArray() {
            return Lists.newArrayList(this.iterator()).toArray();
         }

         public <T> T[] toArray(T[] var1) {
            return Lists.newArrayList(this.iterator()).toArray(var1);
         }
      }
   }

   private static class FilteredEntryNavigableMap<K, V> extends AbstractNavigableMap<K, V> {
      private final Predicate<? super Entry<K, V>> entryPredicate;
      private final Map<K, V> filteredDelegate;
      private final NavigableMap<K, V> unfiltered;

      FilteredEntryNavigableMap(NavigableMap<K, V> var1, Predicate<? super Entry<K, V>> var2) {
         this.unfiltered = (NavigableMap)Preconditions.checkNotNull(var1);
         this.entryPredicate = var2;
         this.filteredDelegate = new Maps.FilteredEntryMap(var1, var2);
      }

      public void clear() {
         this.filteredDelegate.clear();
      }

      public Comparator<? super K> comparator() {
         return this.unfiltered.comparator();
      }

      public boolean containsKey(@NullableDecl Object var1) {
         return this.filteredDelegate.containsKey(var1);
      }

      Iterator<Entry<K, V>> descendingEntryIterator() {
         return Iterators.filter(this.unfiltered.descendingMap().entrySet().iterator(), this.entryPredicate);
      }

      public NavigableMap<K, V> descendingMap() {
         return Maps.filterEntries(this.unfiltered.descendingMap(), this.entryPredicate);
      }

      Iterator<Entry<K, V>> entryIterator() {
         return Iterators.filter(this.unfiltered.entrySet().iterator(), this.entryPredicate);
      }

      public Set<Entry<K, V>> entrySet() {
         return this.filteredDelegate.entrySet();
      }

      @NullableDecl
      public V get(@NullableDecl Object var1) {
         return this.filteredDelegate.get(var1);
      }

      public NavigableMap<K, V> headMap(K var1, boolean var2) {
         return Maps.filterEntries(this.unfiltered.headMap(var1, var2), this.entryPredicate);
      }

      public boolean isEmpty() {
         return Iterables.any(this.unfiltered.entrySet(), this.entryPredicate) ^ true;
      }

      public NavigableSet<K> navigableKeySet() {
         return new Maps.NavigableKeySet<K, V>(this) {
            public boolean removeAll(Collection<?> var1) {
               return Maps.FilteredEntryMap.removeAllKeys(FilteredEntryNavigableMap.this.unfiltered, FilteredEntryNavigableMap.this.entryPredicate, var1);
            }

            public boolean retainAll(Collection<?> var1) {
               return Maps.FilteredEntryMap.retainAllKeys(FilteredEntryNavigableMap.this.unfiltered, FilteredEntryNavigableMap.this.entryPredicate, var1);
            }
         };
      }

      public Entry<K, V> pollFirstEntry() {
         return (Entry)Iterables.removeFirstMatching(this.unfiltered.entrySet(), this.entryPredicate);
      }

      public Entry<K, V> pollLastEntry() {
         return (Entry)Iterables.removeFirstMatching(this.unfiltered.descendingMap().entrySet(), this.entryPredicate);
      }

      public V put(K var1, V var2) {
         return this.filteredDelegate.put(var1, var2);
      }

      public void putAll(Map<? extends K, ? extends V> var1) {
         this.filteredDelegate.putAll(var1);
      }

      public V remove(@NullableDecl Object var1) {
         return this.filteredDelegate.remove(var1);
      }

      public int size() {
         return this.filteredDelegate.size();
      }

      public NavigableMap<K, V> subMap(K var1, boolean var2, K var3, boolean var4) {
         return Maps.filterEntries(this.unfiltered.subMap(var1, var2, var3, var4), this.entryPredicate);
      }

      public NavigableMap<K, V> tailMap(K var1, boolean var2) {
         return Maps.filterEntries(this.unfiltered.tailMap(var1, var2), this.entryPredicate);
      }

      public Collection<V> values() {
         return new Maps.FilteredMapValues(this, this.unfiltered, this.entryPredicate);
      }
   }

   private static class FilteredEntrySortedMap<K, V> extends Maps.FilteredEntryMap<K, V> implements SortedMap<K, V> {
      FilteredEntrySortedMap(SortedMap<K, V> var1, Predicate<? super Entry<K, V>> var2) {
         super(var1, var2);
      }

      public Comparator<? super K> comparator() {
         return this.sortedMap().comparator();
      }

      SortedSet<K> createKeySet() {
         return new Maps.FilteredEntrySortedMap.SortedKeySet();
      }

      public K firstKey() {
         return this.keySet().iterator().next();
      }

      public SortedMap<K, V> headMap(K var1) {
         return new Maps.FilteredEntrySortedMap(this.sortedMap().headMap(var1), this.predicate);
      }

      public SortedSet<K> keySet() {
         return (SortedSet)super.keySet();
      }

      public K lastKey() {
         SortedMap var1 = this.sortedMap();

         while(true) {
            Object var2 = var1.lastKey();
            if (this.apply(var2, this.unfiltered.get(var2))) {
               return var2;
            }

            var1 = this.sortedMap().headMap(var2);
         }
      }

      SortedMap<K, V> sortedMap() {
         return (SortedMap)this.unfiltered;
      }

      public SortedMap<K, V> subMap(K var1, K var2) {
         return new Maps.FilteredEntrySortedMap(this.sortedMap().subMap(var1, var2), this.predicate);
      }

      public SortedMap<K, V> tailMap(K var1) {
         return new Maps.FilteredEntrySortedMap(this.sortedMap().tailMap(var1), this.predicate);
      }

      class SortedKeySet extends Maps.FilteredEntryMap<K, V>.KeySet implements SortedSet<K> {
         SortedKeySet() {
            super();
         }

         public Comparator<? super K> comparator() {
            return FilteredEntrySortedMap.this.sortedMap().comparator();
         }

         public K first() {
            return FilteredEntrySortedMap.this.firstKey();
         }

         public SortedSet<K> headSet(K var1) {
            return (SortedSet)FilteredEntrySortedMap.this.headMap(var1).keySet();
         }

         public K last() {
            return FilteredEntrySortedMap.this.lastKey();
         }

         public SortedSet<K> subSet(K var1, K var2) {
            return (SortedSet)FilteredEntrySortedMap.this.subMap(var1, var2).keySet();
         }

         public SortedSet<K> tailSet(K var1) {
            return (SortedSet)FilteredEntrySortedMap.this.tailMap(var1).keySet();
         }
      }
   }

   private static class FilteredKeyMap<K, V> extends Maps.AbstractFilteredMap<K, V> {
      final Predicate<? super K> keyPredicate;

      FilteredKeyMap(Map<K, V> var1, Predicate<? super K> var2, Predicate<? super Entry<K, V>> var3) {
         super(var1, var3);
         this.keyPredicate = var2;
      }

      public boolean containsKey(Object var1) {
         boolean var2;
         if (this.unfiltered.containsKey(var1) && this.keyPredicate.apply(var1)) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      protected Set<Entry<K, V>> createEntrySet() {
         return Sets.filter(this.unfiltered.entrySet(), this.predicate);
      }

      Set<K> createKeySet() {
         return Sets.filter(this.unfiltered.keySet(), this.keyPredicate);
      }
   }

   private static final class FilteredMapValues<K, V> extends Maps.Values<K, V> {
      final Predicate<? super Entry<K, V>> predicate;
      final Map<K, V> unfiltered;

      FilteredMapValues(Map<K, V> var1, Map<K, V> var2, Predicate<? super Entry<K, V>> var3) {
         super(var1);
         this.unfiltered = var2;
         this.predicate = var3;
      }

      public boolean remove(Object var1) {
         Iterator var2 = this.unfiltered.entrySet().iterator();

         Entry var3;
         do {
            if (!var2.hasNext()) {
               return false;
            }

            var3 = (Entry)var2.next();
         } while(!this.predicate.apply(var3) || !Objects.equal(var3.getValue(), var1));

         var2.remove();
         return true;
      }

      public boolean removeAll(Collection<?> var1) {
         Iterator var2 = this.unfiltered.entrySet().iterator();
         boolean var3 = false;

         while(var2.hasNext()) {
            Entry var4 = (Entry)var2.next();
            if (this.predicate.apply(var4) && var1.contains(var4.getValue())) {
               var2.remove();
               var3 = true;
            }
         }

         return var3;
      }

      public boolean retainAll(Collection<?> var1) {
         Iterator var2 = this.unfiltered.entrySet().iterator();
         boolean var3 = false;

         while(var2.hasNext()) {
            Entry var4 = (Entry)var2.next();
            if (this.predicate.apply(var4) && !var1.contains(var4.getValue())) {
               var2.remove();
               var3 = true;
            }
         }

         return var3;
      }

      public Object[] toArray() {
         return Lists.newArrayList(this.iterator()).toArray();
      }

      public <T> T[] toArray(T[] var1) {
         return Lists.newArrayList(this.iterator()).toArray(var1);
      }
   }

   abstract static class IteratorBasedAbstractMap<K, V> extends AbstractMap<K, V> {
      public void clear() {
         Iterators.clear(this.entryIterator());
      }

      abstract Iterator<Entry<K, V>> entryIterator();

      public Set<Entry<K, V>> entrySet() {
         return new Maps.EntrySet<K, V>() {
            public Iterator<Entry<K, V>> iterator() {
               return IteratorBasedAbstractMap.this.entryIterator();
            }

            Map<K, V> map() {
               return IteratorBasedAbstractMap.this;
            }
         };
      }

      public abstract int size();
   }

   static class KeySet<K, V> extends Sets.ImprovedAbstractSet<K> {
      final Map<K, V> map;

      KeySet(Map<K, V> var1) {
         this.map = (Map)Preconditions.checkNotNull(var1);
      }

      public void clear() {
         this.map().clear();
      }

      public boolean contains(Object var1) {
         return this.map().containsKey(var1);
      }

      public boolean isEmpty() {
         return this.map().isEmpty();
      }

      public Iterator<K> iterator() {
         return Maps.keyIterator(this.map().entrySet().iterator());
      }

      Map<K, V> map() {
         return this.map;
      }

      public boolean remove(Object var1) {
         if (this.contains(var1)) {
            this.map().remove(var1);
            return true;
         } else {
            return false;
         }
      }

      public int size() {
         return this.map().size();
      }
   }

   static class MapDifferenceImpl<K, V> implements MapDifference<K, V> {
      final Map<K, MapDifference.ValueDifference<V>> differences;
      final Map<K, V> onBoth;
      final Map<K, V> onlyOnLeft;
      final Map<K, V> onlyOnRight;

      MapDifferenceImpl(Map<K, V> var1, Map<K, V> var2, Map<K, V> var3, Map<K, MapDifference.ValueDifference<V>> var4) {
         this.onlyOnLeft = Maps.unmodifiableMap(var1);
         this.onlyOnRight = Maps.unmodifiableMap(var2);
         this.onBoth = Maps.unmodifiableMap(var3);
         this.differences = Maps.unmodifiableMap(var4);
      }

      public boolean areEqual() {
         boolean var1;
         if (this.onlyOnLeft.isEmpty() && this.onlyOnRight.isEmpty() && this.differences.isEmpty()) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public Map<K, MapDifference.ValueDifference<V>> entriesDiffering() {
         return this.differences;
      }

      public Map<K, V> entriesInCommon() {
         return this.onBoth;
      }

      public Map<K, V> entriesOnlyOnLeft() {
         return this.onlyOnLeft;
      }

      public Map<K, V> entriesOnlyOnRight() {
         return this.onlyOnRight;
      }

      public boolean equals(Object var1) {
         boolean var2 = true;
         if (var1 == this) {
            return true;
         } else if (!(var1 instanceof MapDifference)) {
            return false;
         } else {
            MapDifference var3 = (MapDifference)var1;
            if (!this.entriesOnlyOnLeft().equals(var3.entriesOnlyOnLeft()) || !this.entriesOnlyOnRight().equals(var3.entriesOnlyOnRight()) || !this.entriesInCommon().equals(var3.entriesInCommon()) || !this.entriesDiffering().equals(var3.entriesDiffering())) {
               var2 = false;
            }

            return var2;
         }
      }

      public int hashCode() {
         return Objects.hashCode(this.entriesOnlyOnLeft(), this.entriesOnlyOnRight(), this.entriesInCommon(), this.entriesDiffering());
      }

      public String toString() {
         if (this.areEqual()) {
            return "equal";
         } else {
            StringBuilder var1 = new StringBuilder("not equal");
            if (!this.onlyOnLeft.isEmpty()) {
               var1.append(": only on left=");
               var1.append(this.onlyOnLeft);
            }

            if (!this.onlyOnRight.isEmpty()) {
               var1.append(": only on right=");
               var1.append(this.onlyOnRight);
            }

            if (!this.differences.isEmpty()) {
               var1.append(": value differences=");
               var1.append(this.differences);
            }

            return var1.toString();
         }
      }
   }

   private static final class NavigableAsMapView<K, V> extends AbstractNavigableMap<K, V> {
      private final Function<? super K, V> function;
      private final NavigableSet<K> set;

      NavigableAsMapView(NavigableSet<K> var1, Function<? super K, V> var2) {
         this.set = (NavigableSet)Preconditions.checkNotNull(var1);
         this.function = (Function)Preconditions.checkNotNull(var2);
      }

      public void clear() {
         this.set.clear();
      }

      public Comparator<? super K> comparator() {
         return this.set.comparator();
      }

      Iterator<Entry<K, V>> descendingEntryIterator() {
         return this.descendingMap().entrySet().iterator();
      }

      public NavigableMap<K, V> descendingMap() {
         return Maps.asMap(this.set.descendingSet(), this.function);
      }

      Iterator<Entry<K, V>> entryIterator() {
         return Maps.asMapEntryIterator(this.set, this.function);
      }

      @NullableDecl
      public V get(@NullableDecl Object var1) {
         return Collections2.safeContains(this.set, var1) ? this.function.apply(var1) : null;
      }

      public NavigableMap<K, V> headMap(K var1, boolean var2) {
         return Maps.asMap(this.set.headSet(var1, var2), this.function);
      }

      public NavigableSet<K> navigableKeySet() {
         return Maps.removeOnlyNavigableSet(this.set);
      }

      public int size() {
         return this.set.size();
      }

      public NavigableMap<K, V> subMap(K var1, boolean var2, K var3, boolean var4) {
         return Maps.asMap(this.set.subSet(var1, var2, var3, var4), this.function);
      }

      public NavigableMap<K, V> tailMap(K var1, boolean var2) {
         return Maps.asMap(this.set.tailSet(var1, var2), this.function);
      }
   }

   static class NavigableKeySet<K, V> extends Maps.SortedKeySet<K, V> implements NavigableSet<K> {
      NavigableKeySet(NavigableMap<K, V> var1) {
         super(var1);
      }

      public K ceiling(K var1) {
         return this.map().ceilingKey(var1);
      }

      public Iterator<K> descendingIterator() {
         return this.descendingSet().iterator();
      }

      public NavigableSet<K> descendingSet() {
         return this.map().descendingKeySet();
      }

      public K floor(K var1) {
         return this.map().floorKey(var1);
      }

      public NavigableSet<K> headSet(K var1, boolean var2) {
         return this.map().headMap(var1, var2).navigableKeySet();
      }

      public SortedSet<K> headSet(K var1) {
         return this.headSet(var1, false);
      }

      public K higher(K var1) {
         return this.map().higherKey(var1);
      }

      public K lower(K var1) {
         return this.map().lowerKey(var1);
      }

      NavigableMap<K, V> map() {
         return (NavigableMap)this.map;
      }

      public K pollFirst() {
         return Maps.keyOrNull(this.map().pollFirstEntry());
      }

      public K pollLast() {
         return Maps.keyOrNull(this.map().pollLastEntry());
      }

      public NavigableSet<K> subSet(K var1, boolean var2, K var3, boolean var4) {
         return this.map().subMap(var1, var2, var3, var4).navigableKeySet();
      }

      public SortedSet<K> subSet(K var1, K var2) {
         return this.subSet(var1, true, var2, false);
      }

      public NavigableSet<K> tailSet(K var1, boolean var2) {
         return this.map().tailMap(var1, var2).navigableKeySet();
      }

      public SortedSet<K> tailSet(K var1) {
         return this.tailSet(var1, true);
      }
   }

   private static class SortedAsMapView<K, V> extends Maps.AsMapView<K, V> implements SortedMap<K, V> {
      SortedAsMapView(SortedSet<K> var1, Function<? super K, V> var2) {
         super(var1, var2);
      }

      SortedSet<K> backingSet() {
         return (SortedSet)super.backingSet();
      }

      public Comparator<? super K> comparator() {
         return this.backingSet().comparator();
      }

      public K firstKey() {
         return this.backingSet().first();
      }

      public SortedMap<K, V> headMap(K var1) {
         return Maps.asMap(this.backingSet().headSet(var1), this.function);
      }

      public Set<K> keySet() {
         return Maps.removeOnlySortedSet(this.backingSet());
      }

      public K lastKey() {
         return this.backingSet().last();
      }

      public SortedMap<K, V> subMap(K var1, K var2) {
         return Maps.asMap(this.backingSet().subSet(var1, var2), this.function);
      }

      public SortedMap<K, V> tailMap(K var1) {
         return Maps.asMap(this.backingSet().tailSet(var1), this.function);
      }
   }

   static class SortedKeySet<K, V> extends Maps.KeySet<K, V> implements SortedSet<K> {
      SortedKeySet(SortedMap<K, V> var1) {
         super(var1);
      }

      public Comparator<? super K> comparator() {
         return this.map().comparator();
      }

      public K first() {
         return this.map().firstKey();
      }

      public SortedSet<K> headSet(K var1) {
         return new Maps.SortedKeySet(this.map().headMap(var1));
      }

      public K last() {
         return this.map().lastKey();
      }

      SortedMap<K, V> map() {
         return (SortedMap)super.map();
      }

      public SortedSet<K> subSet(K var1, K var2) {
         return new Maps.SortedKeySet(this.map().subMap(var1, var2));
      }

      public SortedSet<K> tailSet(K var1) {
         return new Maps.SortedKeySet(this.map().tailMap(var1));
      }
   }

   static class SortedMapDifferenceImpl<K, V> extends Maps.MapDifferenceImpl<K, V> implements SortedMapDifference<K, V> {
      SortedMapDifferenceImpl(SortedMap<K, V> var1, SortedMap<K, V> var2, SortedMap<K, V> var3, SortedMap<K, MapDifference.ValueDifference<V>> var4) {
         super(var1, var2, var3, var4);
      }

      public SortedMap<K, MapDifference.ValueDifference<V>> entriesDiffering() {
         return (SortedMap)super.entriesDiffering();
      }

      public SortedMap<K, V> entriesInCommon() {
         return (SortedMap)super.entriesInCommon();
      }

      public SortedMap<K, V> entriesOnlyOnLeft() {
         return (SortedMap)super.entriesOnlyOnLeft();
      }

      public SortedMap<K, V> entriesOnlyOnRight() {
         return (SortedMap)super.entriesOnlyOnRight();
      }
   }

   static class TransformedEntriesMap<K, V1, V2> extends Maps.IteratorBasedAbstractMap<K, V2> {
      final Map<K, V1> fromMap;
      final Maps.EntryTransformer<? super K, ? super V1, V2> transformer;

      TransformedEntriesMap(Map<K, V1> var1, Maps.EntryTransformer<? super K, ? super V1, V2> var2) {
         this.fromMap = (Map)Preconditions.checkNotNull(var1);
         this.transformer = (Maps.EntryTransformer)Preconditions.checkNotNull(var2);
      }

      public void clear() {
         this.fromMap.clear();
      }

      public boolean containsKey(Object var1) {
         return this.fromMap.containsKey(var1);
      }

      Iterator<Entry<K, V2>> entryIterator() {
         return Iterators.transform(this.fromMap.entrySet().iterator(), Maps.asEntryToEntryFunction(this.transformer));
      }

      public V2 get(Object var1) {
         Object var2 = this.fromMap.get(var1);
         if (var2 == null && !this.fromMap.containsKey(var1)) {
            var1 = null;
         } else {
            var1 = this.transformer.transformEntry(var1, var2);
         }

         return var1;
      }

      public Set<K> keySet() {
         return this.fromMap.keySet();
      }

      public V2 remove(Object var1) {
         if (this.fromMap.containsKey(var1)) {
            var1 = this.transformer.transformEntry(var1, this.fromMap.remove(var1));
         } else {
            var1 = null;
         }

         return var1;
      }

      public int size() {
         return this.fromMap.size();
      }

      public Collection<V2> values() {
         return new Maps.Values(this);
      }
   }

   private static class TransformedEntriesNavigableMap<K, V1, V2> extends Maps.TransformedEntriesSortedMap<K, V1, V2> implements NavigableMap<K, V2> {
      TransformedEntriesNavigableMap(NavigableMap<K, V1> var1, Maps.EntryTransformer<? super K, ? super V1, V2> var2) {
         super(var1, var2);
      }

      @NullableDecl
      private Entry<K, V2> transformEntry(@NullableDecl Entry<K, V1> var1) {
         if (var1 == null) {
            var1 = null;
         } else {
            var1 = Maps.transformEntry(this.transformer, var1);
         }

         return var1;
      }

      public Entry<K, V2> ceilingEntry(K var1) {
         return this.transformEntry(this.fromMap().ceilingEntry(var1));
      }

      public K ceilingKey(K var1) {
         return this.fromMap().ceilingKey(var1);
      }

      public NavigableSet<K> descendingKeySet() {
         return this.fromMap().descendingKeySet();
      }

      public NavigableMap<K, V2> descendingMap() {
         return Maps.transformEntries(this.fromMap().descendingMap(), this.transformer);
      }

      public Entry<K, V2> firstEntry() {
         return this.transformEntry(this.fromMap().firstEntry());
      }

      public Entry<K, V2> floorEntry(K var1) {
         return this.transformEntry(this.fromMap().floorEntry(var1));
      }

      public K floorKey(K var1) {
         return this.fromMap().floorKey(var1);
      }

      protected NavigableMap<K, V1> fromMap() {
         return (NavigableMap)super.fromMap();
      }

      public NavigableMap<K, V2> headMap(K var1) {
         return this.headMap(var1, false);
      }

      public NavigableMap<K, V2> headMap(K var1, boolean var2) {
         return Maps.transformEntries(this.fromMap().headMap(var1, var2), this.transformer);
      }

      public Entry<K, V2> higherEntry(K var1) {
         return this.transformEntry(this.fromMap().higherEntry(var1));
      }

      public K higherKey(K var1) {
         return this.fromMap().higherKey(var1);
      }

      public Entry<K, V2> lastEntry() {
         return this.transformEntry(this.fromMap().lastEntry());
      }

      public Entry<K, V2> lowerEntry(K var1) {
         return this.transformEntry(this.fromMap().lowerEntry(var1));
      }

      public K lowerKey(K var1) {
         return this.fromMap().lowerKey(var1);
      }

      public NavigableSet<K> navigableKeySet() {
         return this.fromMap().navigableKeySet();
      }

      public Entry<K, V2> pollFirstEntry() {
         return this.transformEntry(this.fromMap().pollFirstEntry());
      }

      public Entry<K, V2> pollLastEntry() {
         return this.transformEntry(this.fromMap().pollLastEntry());
      }

      public NavigableMap<K, V2> subMap(K var1, K var2) {
         return this.subMap(var1, true, var2, false);
      }

      public NavigableMap<K, V2> subMap(K var1, boolean var2, K var3, boolean var4) {
         return Maps.transformEntries(this.fromMap().subMap(var1, var2, var3, var4), this.transformer);
      }

      public NavigableMap<K, V2> tailMap(K var1) {
         return this.tailMap(var1, true);
      }

      public NavigableMap<K, V2> tailMap(K var1, boolean var2) {
         return Maps.transformEntries(this.fromMap().tailMap(var1, var2), this.transformer);
      }
   }

   static class TransformedEntriesSortedMap<K, V1, V2> extends Maps.TransformedEntriesMap<K, V1, V2> implements SortedMap<K, V2> {
      TransformedEntriesSortedMap(SortedMap<K, V1> var1, Maps.EntryTransformer<? super K, ? super V1, V2> var2) {
         super(var1, var2);
      }

      public Comparator<? super K> comparator() {
         return this.fromMap().comparator();
      }

      public K firstKey() {
         return this.fromMap().firstKey();
      }

      protected SortedMap<K, V1> fromMap() {
         return (SortedMap)this.fromMap;
      }

      public SortedMap<K, V2> headMap(K var1) {
         return Maps.transformEntries(this.fromMap().headMap(var1), this.transformer);
      }

      public K lastKey() {
         return this.fromMap().lastKey();
      }

      public SortedMap<K, V2> subMap(K var1, K var2) {
         return Maps.transformEntries(this.fromMap().subMap(var1, var2), this.transformer);
      }

      public SortedMap<K, V2> tailMap(K var1) {
         return Maps.transformEntries(this.fromMap().tailMap(var1), this.transformer);
      }
   }

   private static class UnmodifiableBiMap<K, V> extends ForwardingMap<K, V> implements BiMap<K, V>, Serializable {
      private static final long serialVersionUID = 0L;
      final BiMap<? extends K, ? extends V> delegate;
      @MonotonicNonNullDecl
      BiMap<V, K> inverse;
      final Map<K, V> unmodifiableMap;
      @MonotonicNonNullDecl
      transient Set<V> values;

      UnmodifiableBiMap(BiMap<? extends K, ? extends V> var1, @NullableDecl BiMap<V, K> var2) {
         this.unmodifiableMap = Collections.unmodifiableMap(var1);
         this.delegate = var1;
         this.inverse = var2;
      }

      protected Map<K, V> delegate() {
         return this.unmodifiableMap;
      }

      public V forcePut(K var1, V var2) {
         throw new UnsupportedOperationException();
      }

      public BiMap<V, K> inverse() {
         BiMap var1 = this.inverse;
         Object var2 = var1;
         if (var1 == null) {
            var2 = new Maps.UnmodifiableBiMap(this.delegate.inverse(), this);
            this.inverse = (BiMap)var2;
         }

         return (BiMap)var2;
      }

      public Set<V> values() {
         Set var1 = this.values;
         Set var2 = var1;
         if (var1 == null) {
            var2 = Collections.unmodifiableSet(this.delegate.values());
            this.values = var2;
         }

         return var2;
      }
   }

   static class UnmodifiableEntries<K, V> extends ForwardingCollection<Entry<K, V>> {
      private final Collection<Entry<K, V>> entries;

      UnmodifiableEntries(Collection<Entry<K, V>> var1) {
         this.entries = var1;
      }

      protected Collection<Entry<K, V>> delegate() {
         return this.entries;
      }

      public Iterator<Entry<K, V>> iterator() {
         return Maps.unmodifiableEntryIterator(this.entries.iterator());
      }

      public Object[] toArray() {
         return this.standardToArray();
      }

      public <T> T[] toArray(T[] var1) {
         return this.standardToArray(var1);
      }
   }

   static class UnmodifiableEntrySet<K, V> extends Maps.UnmodifiableEntries<K, V> implements Set<Entry<K, V>> {
      UnmodifiableEntrySet(Set<Entry<K, V>> var1) {
         super(var1);
      }

      public boolean equals(@NullableDecl Object var1) {
         return Sets.equalsImpl(this, var1);
      }

      public int hashCode() {
         return Sets.hashCodeImpl(this);
      }
   }

   static class UnmodifiableNavigableMap<K, V> extends ForwardingSortedMap<K, V> implements NavigableMap<K, V>, Serializable {
      private final NavigableMap<K, ? extends V> delegate;
      @MonotonicNonNullDecl
      private transient Maps.UnmodifiableNavigableMap<K, V> descendingMap;

      UnmodifiableNavigableMap(NavigableMap<K, ? extends V> var1) {
         this.delegate = var1;
      }

      UnmodifiableNavigableMap(NavigableMap<K, ? extends V> var1, Maps.UnmodifiableNavigableMap<K, V> var2) {
         this.delegate = var1;
         this.descendingMap = var2;
      }

      public Entry<K, V> ceilingEntry(K var1) {
         return Maps.unmodifiableOrNull(this.delegate.ceilingEntry(var1));
      }

      public K ceilingKey(K var1) {
         return this.delegate.ceilingKey(var1);
      }

      protected SortedMap<K, V> delegate() {
         return Collections.unmodifiableSortedMap(this.delegate);
      }

      public NavigableSet<K> descendingKeySet() {
         return Sets.unmodifiableNavigableSet(this.delegate.descendingKeySet());
      }

      public NavigableMap<K, V> descendingMap() {
         Maps.UnmodifiableNavigableMap var1 = this.descendingMap;
         Maps.UnmodifiableNavigableMap var2 = var1;
         if (var1 == null) {
            var2 = new Maps.UnmodifiableNavigableMap(this.delegate.descendingMap(), this);
            this.descendingMap = var2;
         }

         return var2;
      }

      public Entry<K, V> firstEntry() {
         return Maps.unmodifiableOrNull(this.delegate.firstEntry());
      }

      public Entry<K, V> floorEntry(K var1) {
         return Maps.unmodifiableOrNull(this.delegate.floorEntry(var1));
      }

      public K floorKey(K var1) {
         return this.delegate.floorKey(var1);
      }

      public NavigableMap<K, V> headMap(K var1, boolean var2) {
         return Maps.unmodifiableNavigableMap(this.delegate.headMap(var1, var2));
      }

      public SortedMap<K, V> headMap(K var1) {
         return this.headMap(var1, false);
      }

      public Entry<K, V> higherEntry(K var1) {
         return Maps.unmodifiableOrNull(this.delegate.higherEntry(var1));
      }

      public K higherKey(K var1) {
         return this.delegate.higherKey(var1);
      }

      public Set<K> keySet() {
         return this.navigableKeySet();
      }

      public Entry<K, V> lastEntry() {
         return Maps.unmodifiableOrNull(this.delegate.lastEntry());
      }

      public Entry<K, V> lowerEntry(K var1) {
         return Maps.unmodifiableOrNull(this.delegate.lowerEntry(var1));
      }

      public K lowerKey(K var1) {
         return this.delegate.lowerKey(var1);
      }

      public NavigableSet<K> navigableKeySet() {
         return Sets.unmodifiableNavigableSet(this.delegate.navigableKeySet());
      }

      public final Entry<K, V> pollFirstEntry() {
         throw new UnsupportedOperationException();
      }

      public final Entry<K, V> pollLastEntry() {
         throw new UnsupportedOperationException();
      }

      public NavigableMap<K, V> subMap(K var1, boolean var2, K var3, boolean var4) {
         return Maps.unmodifiableNavigableMap(this.delegate.subMap(var1, var2, var3, var4));
      }

      public SortedMap<K, V> subMap(K var1, K var2) {
         return this.subMap(var1, true, var2, false);
      }

      public NavigableMap<K, V> tailMap(K var1, boolean var2) {
         return Maps.unmodifiableNavigableMap(this.delegate.tailMap(var1, var2));
      }

      public SortedMap<K, V> tailMap(K var1) {
         return this.tailMap(var1, true);
      }
   }

   static class ValueDifferenceImpl<V> implements MapDifference.ValueDifference<V> {
      @NullableDecl
      private final V left;
      @NullableDecl
      private final V right;

      private ValueDifferenceImpl(@NullableDecl V var1, @NullableDecl V var2) {
         this.left = var1;
         this.right = var2;
      }

      static <V> MapDifference.ValueDifference<V> create(@NullableDecl V var0, @NullableDecl V var1) {
         return new Maps.ValueDifferenceImpl(var0, var1);
      }

      public boolean equals(@NullableDecl Object var1) {
         boolean var2 = var1 instanceof MapDifference.ValueDifference;
         boolean var3 = false;
         boolean var4 = var3;
         if (var2) {
            MapDifference.ValueDifference var5 = (MapDifference.ValueDifference)var1;
            var4 = var3;
            if (Objects.equal(this.left, var5.leftValue())) {
               var4 = var3;
               if (Objects.equal(this.right, var5.rightValue())) {
                  var4 = true;
               }
            }
         }

         return var4;
      }

      public int hashCode() {
         return Objects.hashCode(this.left, this.right);
      }

      public V leftValue() {
         return this.left;
      }

      public V rightValue() {
         return this.right;
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("(");
         var1.append(this.left);
         var1.append(", ");
         var1.append(this.right);
         var1.append(")");
         return var1.toString();
      }
   }

   static class Values<K, V> extends AbstractCollection<V> {
      final Map<K, V> map;

      Values(Map<K, V> var1) {
         this.map = (Map)Preconditions.checkNotNull(var1);
      }

      public void clear() {
         this.map().clear();
      }

      public boolean contains(@NullableDecl Object var1) {
         return this.map().containsValue(var1);
      }

      public boolean isEmpty() {
         return this.map().isEmpty();
      }

      public Iterator<V> iterator() {
         return Maps.valueIterator(this.map().entrySet().iterator());
      }

      final Map<K, V> map() {
         return this.map;
      }

      public boolean remove(Object var1) {
         try {
            boolean var2 = super.remove(var1);
            return var2;
         } catch (UnsupportedOperationException var5) {
            Iterator var4 = this.map().entrySet().iterator();

            Entry var3;
            do {
               if (!var4.hasNext()) {
                  return false;
               }

               var3 = (Entry)var4.next();
            } while(!Objects.equal(var1, var3.getValue()));

            this.map().remove(var3.getKey());
            return true;
         }
      }

      public boolean removeAll(Collection<?> var1) {
         try {
            boolean var2 = super.removeAll((Collection)Preconditions.checkNotNull(var1));
            return var2;
         } catch (UnsupportedOperationException var6) {
            HashSet var4 = Sets.newHashSet();
            Iterator var5 = this.map().entrySet().iterator();

            while(var5.hasNext()) {
               Entry var3 = (Entry)var5.next();
               if (var1.contains(var3.getValue())) {
                  var4.add(var3.getKey());
               }
            }

            return this.map().keySet().removeAll(var4);
         }
      }

      public boolean retainAll(Collection<?> var1) {
         try {
            boolean var2 = super.retainAll((Collection)Preconditions.checkNotNull(var1));
            return var2;
         } catch (UnsupportedOperationException var6) {
            HashSet var4 = Sets.newHashSet();
            Iterator var3 = this.map().entrySet().iterator();

            while(var3.hasNext()) {
               Entry var5 = (Entry)var3.next();
               if (var1.contains(var5.getValue())) {
                  var4.add(var5.getKey());
               }
            }

            return this.map().keySet().retainAll(var4);
         }
      }

      public int size() {
         return this.map().size();
      }
   }

   abstract static class ViewCachingAbstractMap<K, V> extends AbstractMap<K, V> {
      @MonotonicNonNullDecl
      private transient Set<Entry<K, V>> entrySet;
      @MonotonicNonNullDecl
      private transient Set<K> keySet;
      @MonotonicNonNullDecl
      private transient Collection<V> values;

      abstract Set<Entry<K, V>> createEntrySet();

      Set<K> createKeySet() {
         return new Maps.KeySet(this);
      }

      Collection<V> createValues() {
         return new Maps.Values(this);
      }

      public Set<Entry<K, V>> entrySet() {
         Set var1 = this.entrySet;
         Set var2 = var1;
         if (var1 == null) {
            var2 = this.createEntrySet();
            this.entrySet = var2;
         }

         return var2;
      }

      public Set<K> keySet() {
         Set var1 = this.keySet;
         Set var2 = var1;
         if (var1 == null) {
            var2 = this.createKeySet();
            this.keySet = var2;
         }

         return var2;
      }

      public Collection<V> values() {
         Collection var1 = this.values;
         Collection var2 = var1;
         if (var1 == null) {
            var2 = this.createValues();
            this.values = var2;
         }

         return var2;
      }
   }
}
