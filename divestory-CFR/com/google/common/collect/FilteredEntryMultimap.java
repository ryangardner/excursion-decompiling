/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.AbstractMultimap;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.Collections2;
import com.google.common.collect.FilteredMultimap;
import com.google.common.collect.FilteredMultimapValues;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class FilteredEntryMultimap<K, V>
extends AbstractMultimap<K, V>
implements FilteredMultimap<K, V> {
    final Predicate<? super Map.Entry<K, V>> predicate;
    final Multimap<K, V> unfiltered;

    FilteredEntryMultimap(Multimap<K, V> multimap, Predicate<? super Map.Entry<K, V>> predicate) {
        this.unfiltered = Preconditions.checkNotNull(multimap);
        this.predicate = Preconditions.checkNotNull(predicate);
    }

    static <E> Collection<E> filterCollection(Collection<E> collection, Predicate<? super E> predicate) {
        if (!(collection instanceof Set)) return Collections2.filter(collection, predicate);
        return Sets.filter((Set)collection, predicate);
    }

    private boolean satisfies(K k, V v) {
        return this.predicate.apply(Maps.immutableEntry(k, v));
    }

    @Override
    public void clear() {
        this.entries().clear();
    }

    @Override
    public boolean containsKey(@NullableDecl Object object) {
        if (this.asMap().get(object) == null) return false;
        return true;
    }

    @Override
    Map<K, Collection<V>> createAsMap() {
        return new AsMap();
    }

    @Override
    Collection<Map.Entry<K, V>> createEntries() {
        return FilteredEntryMultimap.filterCollection(this.unfiltered.entries(), this.predicate);
    }

    @Override
    Set<K> createKeySet() {
        return this.asMap().keySet();
    }

    @Override
    Multiset<K> createKeys() {
        return new Keys();
    }

    @Override
    Collection<V> createValues() {
        return new FilteredMultimapValues(this);
    }

    @Override
    Iterator<Map.Entry<K, V>> entryIterator() {
        throw new AssertionError((Object)"should never be called");
    }

    @Override
    public Predicate<? super Map.Entry<K, V>> entryPredicate() {
        return this.predicate;
    }

    @Override
    public Collection<V> get(K k) {
        return FilteredEntryMultimap.filterCollection(this.unfiltered.get(k), new ValuePredicate(k));
    }

    @Override
    public Collection<V> removeAll(@NullableDecl Object object) {
        return MoreObjects.firstNonNull(this.asMap().remove(object), this.unmodifiableEmptyCollection());
    }

    boolean removeEntriesIf(Predicate<? super Map.Entry<K, Collection<V>>> predicate) {
        Iterator<Map.Entry<K, Collection<V>>> iterator2 = this.unfiltered.asMap().entrySet().iterator();
        boolean bl = false;
        while (iterator2.hasNext()) {
            Map.Entry<K, Collection<V>> entry = iterator2.next();
            K k = entry.getKey();
            Collection<V> collection = FilteredEntryMultimap.filterCollection(entry.getValue(), new ValuePredicate(k));
            if (collection.isEmpty() || !predicate.apply(Maps.immutableEntry(k, collection))) continue;
            if (collection.size() == entry.getValue().size()) {
                iterator2.remove();
            } else {
                collection.clear();
            }
            bl = true;
        }
        return bl;
    }

    @Override
    public int size() {
        return this.entries().size();
    }

    @Override
    public Multimap<K, V> unfiltered() {
        return this.unfiltered;
    }

    Collection<V> unmodifiableEmptyCollection() {
        if (!(this.unfiltered instanceof SetMultimap)) return Collections.emptyList();
        return Collections.emptySet();
    }

    class AsMap
    extends Maps.ViewCachingAbstractMap<K, Collection<V>> {
        AsMap() {
        }

        @Override
        public void clear() {
            FilteredEntryMultimap.this.clear();
        }

        @Override
        public boolean containsKey(@NullableDecl Object object) {
            if (this.get(object) == null) return false;
            return true;
        }

        @Override
        Set<Map.Entry<K, Collection<V>>> createEntrySet() {
            return new 1EntrySetImpl();
        }

        @Override
        Set<K> createKeySet() {
            return new 1KeySetImpl();
        }

        @Override
        Collection<Collection<V>> createValues() {
            return new 1ValuesImpl();
        }

        @Override
        public Collection<V> get(@NullableDecl Object collection) {
            Collection collection2 = FilteredEntryMultimap.this.unfiltered.asMap().get(collection);
            Object var3_3 = null;
            if (collection2 == null) {
                return null;
            }
            if (!(collection = FilteredEntryMultimap.filterCollection(collection2, new ValuePredicate(collection))).isEmpty()) return collection;
            return var3_3;
        }

        @Override
        public Collection<V> remove(@NullableDecl Object object) {
            Collection collection = FilteredEntryMultimap.this.unfiltered.asMap().get(object);
            if (collection == null) {
                return null;
            }
            ArrayList arrayList = Lists.newArrayList();
            collection = collection.iterator();
            while (collection.hasNext()) {
                Object e = collection.next();
                if (!FilteredEntryMultimap.this.satisfies(object, e)) continue;
                collection.remove();
                arrayList.add(e);
            }
            if (arrayList.isEmpty()) {
                return null;
            }
            if (!(FilteredEntryMultimap.this.unfiltered instanceof SetMultimap)) return Collections.unmodifiableList(arrayList);
            return Collections.unmodifiableSet(Sets.newLinkedHashSet(arrayList));
        }

        class 1EntrySetImpl
        extends Maps.EntrySet<K, Collection<V>> {
            1EntrySetImpl() {
            }

            @Override
            public Iterator<Map.Entry<K, Collection<V>>> iterator() {
                return new AbstractIterator<Map.Entry<K, Collection<V>>>(){
                    final Iterator<Map.Entry<K, Collection<V>>> backingIterator;
                    {
                        this.backingIterator = FilteredEntryMultimap.this.unfiltered.asMap().entrySet().iterator();
                    }

                    @Override
                    protected Map.Entry<K, Collection<V>> computeNext() {
                        Object object;
                        K k;
                        do {
                            if (!this.backingIterator.hasNext()) return (Map.Entry)this.endOfData();
                            object = this.backingIterator.next();
                            k = object.getKey();
                        } while ((object = FilteredEntryMultimap.filterCollection(object.getValue(), new ValuePredicate(k))).isEmpty());
                        return Maps.immutableEntry(k, object);
                    }
                };
            }

            @Override
            Map<K, Collection<V>> map() {
                return AsMap.this;
            }

            @Override
            public boolean removeAll(Collection<?> collection) {
                return FilteredEntryMultimap.this.removeEntriesIf(Predicates.in(collection));
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                return FilteredEntryMultimap.this.removeEntriesIf(Predicates.not(Predicates.in(collection)));
            }

            @Override
            public int size() {
                return Iterators.size(this.iterator());
            }

        }

        class 1KeySetImpl
        extends Maps.KeySet<K, Collection<V>> {
            1KeySetImpl() {
                super(AsMap.this);
            }

            @Override
            public boolean remove(@NullableDecl Object object) {
                if (AsMap.this.remove(object) == null) return false;
                return true;
            }

            @Override
            public boolean removeAll(Collection<?> collection) {
                return FilteredEntryMultimap.this.removeEntriesIf(Maps.keyPredicateOnEntries(Predicates.in(collection)));
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                return FilteredEntryMultimap.this.removeEntriesIf(Maps.keyPredicateOnEntries(Predicates.not(Predicates.in(collection))));
            }
        }

        class 1ValuesImpl
        extends Maps.Values<K, Collection<V>> {
            1ValuesImpl() {
                super(AsMap.this);
            }

            @Override
            public boolean remove(@NullableDecl Object iterator2) {
                Map.Entry entry;
                Object object;
                if (!(iterator2 instanceof Collection)) return false;
                Collection collection = (Collection)((Object)iterator2);
                iterator2 = FilteredEntryMultimap.this.unfiltered.asMap().entrySet().iterator();
                do {
                    if (!iterator2.hasNext()) return false;
                    entry = iterator2.next();
                    object = entry.getKey();
                } while ((object = FilteredEntryMultimap.filterCollection(entry.getValue(), new ValuePredicate(object))).isEmpty() || !collection.equals(object));
                if (object.size() == entry.getValue().size()) {
                    iterator2.remove();
                    return true;
                }
                object.clear();
                return true;
            }

            @Override
            public boolean removeAll(Collection<?> collection) {
                return FilteredEntryMultimap.this.removeEntriesIf(Maps.valuePredicateOnEntries(Predicates.in(collection)));
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                return FilteredEntryMultimap.this.removeEntriesIf(Maps.valuePredicateOnEntries(Predicates.not(Predicates.in(collection))));
            }
        }

    }

    class Keys
    extends Multimaps.Keys<K, V> {
        Keys() {
            super(FilteredEntryMultimap.this);
        }

        @Override
        public Set<Multiset.Entry<K>> entrySet() {
            return new Multisets.EntrySet<K>(){

                private boolean removeEntriesIf(final Predicate<? super Multiset.Entry<K>> predicate) {
                    return FilteredEntryMultimap.this.removeEntriesIf(new Predicate<Map.Entry<K, Collection<V>>>(){

                        @Override
                        public boolean apply(Map.Entry<K, Collection<V>> entry) {
                            return predicate.apply(Multisets.immutableEntry(entry.getKey(), entry.getValue().size()));
                        }
                    });
                }

                @Override
                public Iterator<Multiset.Entry<K>> iterator() {
                    return Keys.this.entryIterator();
                }

                @Override
                Multiset<K> multiset() {
                    return Keys.this;
                }

                @Override
                public boolean removeAll(Collection<?> collection) {
                    return this.removeEntriesIf(Predicates.in(collection));
                }

                @Override
                public boolean retainAll(Collection<?> collection) {
                    return this.removeEntriesIf(Predicates.not(Predicates.in(collection)));
                }

                @Override
                public int size() {
                    return FilteredEntryMultimap.this.keySet().size();
                }

            };
        }

        @Override
        public int remove(@NullableDecl Object object, int n) {
            CollectPreconditions.checkNonnegative(n, "occurrences");
            if (n == 0) {
                return this.count(object);
            }
            Object object2 = FilteredEntryMultimap.this.unfiltered.asMap().get(object);
            int n2 = 0;
            if (object2 == null) {
                return 0;
            }
            object2 = object2.iterator();
            while (object2.hasNext()) {
                int n3;
                Object e = object2.next();
                if (!FilteredEntryMultimap.this.satisfies(object, e)) continue;
                n2 = n3 = n2 + 1;
                if (n3 > n) continue;
                object2.remove();
                n2 = n3;
            }
            return n2;
        }

    }

    final class ValuePredicate
    implements Predicate<V> {
        private final K key;

        ValuePredicate(K k) {
            this.key = k;
        }

        @Override
        public boolean apply(@NullableDecl V v) {
            return FilteredEntryMultimap.this.satisfies(this.key, v);
        }
    }

}

