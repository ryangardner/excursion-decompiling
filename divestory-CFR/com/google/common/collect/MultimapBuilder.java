/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Ordering;
import com.google.common.collect.Platform;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.SortedSetMultimap;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public abstract class MultimapBuilder<K0, V0> {
    private static final int DEFAULT_EXPECTED_KEYS = 8;

    private MultimapBuilder() {
    }

    public static <K0 extends Enum<K0>> MultimapBuilderWithKeys<K0> enumKeys(final Class<K0> class_) {
        Preconditions.checkNotNull(class_);
        return new MultimapBuilderWithKeys<K0>(){

            @Override
            <K extends K0, V> Map<K, Collection<V>> createMap() {
                return new EnumMap(class_);
            }
        };
    }

    public static MultimapBuilderWithKeys<Object> hashKeys() {
        return MultimapBuilder.hashKeys(8);
    }

    public static MultimapBuilderWithKeys<Object> hashKeys(final int n) {
        CollectPreconditions.checkNonnegative(n, "expectedKeys");
        return new MultimapBuilderWithKeys<Object>(){

            @Override
            <K, V> Map<K, Collection<V>> createMap() {
                return Platform.newHashMapWithExpectedSize(n);
            }
        };
    }

    public static MultimapBuilderWithKeys<Object> linkedHashKeys() {
        return MultimapBuilder.linkedHashKeys(8);
    }

    public static MultimapBuilderWithKeys<Object> linkedHashKeys(final int n) {
        CollectPreconditions.checkNonnegative(n, "expectedKeys");
        return new MultimapBuilderWithKeys<Object>(){

            @Override
            <K, V> Map<K, Collection<V>> createMap() {
                return Platform.newLinkedHashMapWithExpectedSize(n);
            }
        };
    }

    public static MultimapBuilderWithKeys<Comparable> treeKeys() {
        return MultimapBuilder.treeKeys(Ordering.natural());
    }

    public static <K0> MultimapBuilderWithKeys<K0> treeKeys(final Comparator<K0> comparator) {
        Preconditions.checkNotNull(comparator);
        return new MultimapBuilderWithKeys<K0>(){

            @Override
            <K extends K0, V> Map<K, Collection<V>> createMap() {
                return new TreeMap(comparator);
            }
        };
    }

    public abstract <K extends K0, V extends V0> Multimap<K, V> build();

    public <K extends K0, V extends V0> Multimap<K, V> build(Multimap<? extends K, ? extends V> multimap) {
        Multimap<? extends K, ? extends V> multimap2 = this.build();
        multimap2.putAll(multimap);
        return multimap2;
    }

    private static final class ArrayListSupplier<V>
    implements Supplier<List<V>>,
    Serializable {
        private final int expectedValuesPerKey;

        ArrayListSupplier(int n) {
            this.expectedValuesPerKey = CollectPreconditions.checkNonnegative(n, "expectedValuesPerKey");
        }

        @Override
        public List<V> get() {
            return new ArrayList(this.expectedValuesPerKey);
        }
    }

    private static final class EnumSetSupplier<V extends Enum<V>>
    implements Supplier<Set<V>>,
    Serializable {
        private final Class<V> clazz;

        EnumSetSupplier(Class<V> class_) {
            this.clazz = Preconditions.checkNotNull(class_);
        }

        @Override
        public Set<V> get() {
            return EnumSet.noneOf(this.clazz);
        }
    }

    private static final class HashSetSupplier<V>
    implements Supplier<Set<V>>,
    Serializable {
        private final int expectedValuesPerKey;

        HashSetSupplier(int n) {
            this.expectedValuesPerKey = CollectPreconditions.checkNonnegative(n, "expectedValuesPerKey");
        }

        @Override
        public Set<V> get() {
            return Platform.newHashSetWithExpectedSize(this.expectedValuesPerKey);
        }
    }

    private static final class LinkedHashSetSupplier<V>
    implements Supplier<Set<V>>,
    Serializable {
        private final int expectedValuesPerKey;

        LinkedHashSetSupplier(int n) {
            this.expectedValuesPerKey = CollectPreconditions.checkNonnegative(n, "expectedValuesPerKey");
        }

        @Override
        public Set<V> get() {
            return Platform.newLinkedHashSetWithExpectedSize(this.expectedValuesPerKey);
        }
    }

    private static final class LinkedListSupplier
    extends Enum<LinkedListSupplier>
    implements Supplier<List<Object>> {
        private static final /* synthetic */ LinkedListSupplier[] $VALUES;
        public static final /* enum */ LinkedListSupplier INSTANCE;

        static {
            LinkedListSupplier linkedListSupplier;
            INSTANCE = linkedListSupplier = new LinkedListSupplier();
            $VALUES = new LinkedListSupplier[]{linkedListSupplier};
        }

        public static <V> Supplier<List<V>> instance() {
            return INSTANCE;
        }

        public static LinkedListSupplier valueOf(String string2) {
            return Enum.valueOf(LinkedListSupplier.class, string2);
        }

        public static LinkedListSupplier[] values() {
            return (LinkedListSupplier[])$VALUES.clone();
        }

        @Override
        public List<Object> get() {
            return new LinkedList<Object>();
        }
    }

    public static abstract class ListMultimapBuilder<K0, V0>
    extends MultimapBuilder<K0, V0> {
        ListMultimapBuilder() {
        }

        @Override
        public abstract <K extends K0, V extends V0> ListMultimap<K, V> build();

        @Override
        public <K extends K0, V extends V0> ListMultimap<K, V> build(Multimap<? extends K, ? extends V> multimap) {
            return (ListMultimap)super.build(multimap);
        }
    }

    public static abstract class MultimapBuilderWithKeys<K0> {
        private static final int DEFAULT_EXPECTED_VALUES_PER_KEY = 2;

        MultimapBuilderWithKeys() {
        }

        public ListMultimapBuilder<K0, Object> arrayListValues() {
            return this.arrayListValues(2);
        }

        public ListMultimapBuilder<K0, Object> arrayListValues(final int n) {
            CollectPreconditions.checkNonnegative(n, "expectedValuesPerKey");
            return new ListMultimapBuilder<K0, Object>(){

                @Override
                public <K extends K0, V> ListMultimap<K, V> build() {
                    return Multimaps.newListMultimap(MultimapBuilderWithKeys.this.createMap(), new ArrayListSupplier(n));
                }
            };
        }

        abstract <K extends K0, V> Map<K, Collection<V>> createMap();

        public <V0 extends Enum<V0>> SetMultimapBuilder<K0, V0> enumSetValues(final Class<V0> class_) {
            Preconditions.checkNotNull(class_, "valueClass");
            return new SetMultimapBuilder<K0, V0>(){

                @Override
                public <K extends K0, V extends V0> SetMultimap<K, V> build() {
                    EnumSetSupplier enumSetSupplier = new EnumSetSupplier(class_);
                    return Multimaps.newSetMultimap(MultimapBuilderWithKeys.this.createMap(), enumSetSupplier);
                }
            };
        }

        public SetMultimapBuilder<K0, Object> hashSetValues() {
            return this.hashSetValues(2);
        }

        public SetMultimapBuilder<K0, Object> hashSetValues(final int n) {
            CollectPreconditions.checkNonnegative(n, "expectedValuesPerKey");
            return new SetMultimapBuilder<K0, Object>(){

                @Override
                public <K extends K0, V> SetMultimap<K, V> build() {
                    return Multimaps.newSetMultimap(MultimapBuilderWithKeys.this.createMap(), new HashSetSupplier(n));
                }
            };
        }

        public SetMultimapBuilder<K0, Object> linkedHashSetValues() {
            return this.linkedHashSetValues(2);
        }

        public SetMultimapBuilder<K0, Object> linkedHashSetValues(final int n) {
            CollectPreconditions.checkNonnegative(n, "expectedValuesPerKey");
            return new SetMultimapBuilder<K0, Object>(){

                @Override
                public <K extends K0, V> SetMultimap<K, V> build() {
                    return Multimaps.newSetMultimap(MultimapBuilderWithKeys.this.createMap(), new LinkedHashSetSupplier(n));
                }
            };
        }

        public ListMultimapBuilder<K0, Object> linkedListValues() {
            return new ListMultimapBuilder<K0, Object>(){

                @Override
                public <K extends K0, V> ListMultimap<K, V> build() {
                    return Multimaps.newListMultimap(MultimapBuilderWithKeys.this.createMap(), LinkedListSupplier.instance());
                }
            };
        }

        public SortedSetMultimapBuilder<K0, Comparable> treeSetValues() {
            return this.treeSetValues(Ordering.<C>natural());
        }

        public <V0> SortedSetMultimapBuilder<K0, V0> treeSetValues(final Comparator<V0> comparator) {
            Preconditions.checkNotNull(comparator, "comparator");
            return new SortedSetMultimapBuilder<K0, V0>(){

                @Override
                public <K extends K0, V extends V0> SortedSetMultimap<K, V> build() {
                    return Multimaps.newSortedSetMultimap(MultimapBuilderWithKeys.this.createMap(), new TreeSetSupplier(comparator));
                }
            };
        }

    }

    public static abstract class SetMultimapBuilder<K0, V0>
    extends MultimapBuilder<K0, V0> {
        SetMultimapBuilder() {
        }

        @Override
        public abstract <K extends K0, V extends V0> SetMultimap<K, V> build();

        @Override
        public <K extends K0, V extends V0> SetMultimap<K, V> build(Multimap<? extends K, ? extends V> multimap) {
            return (SetMultimap)super.build(multimap);
        }
    }

    public static abstract class SortedSetMultimapBuilder<K0, V0>
    extends SetMultimapBuilder<K0, V0> {
        SortedSetMultimapBuilder() {
        }

        @Override
        public abstract <K extends K0, V extends V0> SortedSetMultimap<K, V> build();

        @Override
        public <K extends K0, V extends V0> SortedSetMultimap<K, V> build(Multimap<? extends K, ? extends V> multimap) {
            return (SortedSetMultimap)super.build((Multimap)multimap);
        }
    }

    private static final class TreeSetSupplier<V>
    implements Supplier<SortedSet<V>>,
    Serializable {
        private final Comparator<? super V> comparator;

        TreeSetSupplier(Comparator<? super V> comparator) {
            this.comparator = Preconditions.checkNotNull(comparator);
        }

        @Override
        public SortedSet<V> get() {
            return new TreeSet<V>(this.comparator);
        }
    }

}

