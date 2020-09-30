/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.Hashing;
import com.google.common.collect.ImmutableEntry;
import com.google.common.collect.LinkedHashMultimapGwtSerializationDependencies;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Platform;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class LinkedHashMultimap<K, V>
extends LinkedHashMultimapGwtSerializationDependencies<K, V> {
    private static final int DEFAULT_KEY_CAPACITY = 16;
    private static final int DEFAULT_VALUE_SET_CAPACITY = 2;
    static final double VALUE_SET_LOAD_FACTOR = 1.0;
    private static final long serialVersionUID = 1L;
    private transient ValueEntry<K, V> multimapHeaderEntry;
    transient int valueSetCapacity = 2;

    private LinkedHashMultimap(int n, int n2) {
        super(Platform.newLinkedHashMapWithExpectedSize(n));
        CollectPreconditions.checkNonnegative(n2, "expectedValuesPerKey");
        this.valueSetCapacity = n2;
        ValueEntry<Object, Object> valueEntry = new ValueEntry<Object, Object>(null, null, 0, null);
        this.multimapHeaderEntry = valueEntry;
        LinkedHashMultimap.succeedsInMultimap(valueEntry, valueEntry);
    }

    public static <K, V> LinkedHashMultimap<K, V> create() {
        return new LinkedHashMultimap<K, V>(16, 2);
    }

    public static <K, V> LinkedHashMultimap<K, V> create(int n, int n2) {
        return new LinkedHashMultimap<K, V>(Maps.capacity(n), Maps.capacity(n2));
    }

    public static <K, V> LinkedHashMultimap<K, V> create(Multimap<? extends K, ? extends V> multimap) {
        LinkedHashMultimap<K, V> linkedHashMultimap = LinkedHashMultimap.create(multimap.keySet().size(), 2);
        linkedHashMultimap.putAll(multimap);
        return linkedHashMultimap;
    }

    private static <K, V> void deleteFromMultimap(ValueEntry<K, V> valueEntry) {
        LinkedHashMultimap.succeedsInMultimap(valueEntry.getPredecessorInMultimap(), valueEntry.getSuccessorInMultimap());
    }

    private static <K, V> void deleteFromValueSet(ValueSetLink<K, V> valueSetLink) {
        LinkedHashMultimap.succeedsInValueSet(valueSetLink.getPredecessorInValueSet(), valueSetLink.getSuccessorInValueSet());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        Object object;
        int n;
        objectInputStream.defaultReadObject();
        int n2 = 0;
        Object object2 = new ValueEntry<Object, Object>(null, null, 0, null);
        this.multimapHeaderEntry = object2;
        LinkedHashMultimap.succeedsInMultimap(object2, object2);
        this.valueSetCapacity = 2;
        int n3 = objectInputStream.readInt();
        object2 = Platform.newLinkedHashMapWithExpectedSize(12);
        for (n = 0; n < n3; ++n) {
            object = objectInputStream.readObject();
            object2.put(object, this.createCollection(object));
        }
        n3 = objectInputStream.readInt();
        n = n2;
        do {
            if (n >= n3) {
                this.setMap(object2);
                return;
            }
            Object object3 = objectInputStream.readObject();
            object = objectInputStream.readObject();
            ((Collection)object2.get(object3)).add(object);
            ++n;
        } while (true);
    }

    private static <K, V> void succeedsInMultimap(ValueEntry<K, V> valueEntry, ValueEntry<K, V> valueEntry2) {
        valueEntry.setSuccessorInMultimap(valueEntry2);
        valueEntry2.setPredecessorInMultimap(valueEntry);
    }

    private static <K, V> void succeedsInValueSet(ValueSetLink<K, V> valueSetLink, ValueSetLink<K, V> valueSetLink2) {
        valueSetLink.setSuccessorInValueSet(valueSetLink2);
        valueSetLink2.setPredecessorInValueSet(valueSetLink);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.keySet().size());
        Object object = this.keySet().iterator();
        while (object.hasNext()) {
            objectOutputStream.writeObject(object.next());
        }
        objectOutputStream.writeInt(this.size());
        Iterator iterator2 = this.entries().iterator();
        while (iterator2.hasNext()) {
            object = (Map.Entry)iterator2.next();
            objectOutputStream.writeObject(object.getKey());
            objectOutputStream.writeObject(object.getValue());
        }
    }

    @Override
    public void clear() {
        super.clear();
        ValueEntry<K, V> valueEntry = this.multimapHeaderEntry;
        LinkedHashMultimap.succeedsInMultimap(valueEntry, valueEntry);
    }

    @Override
    Collection<V> createCollection(K k) {
        return new ValueSet(k, this.valueSetCapacity);
    }

    @Override
    Set<V> createCollection() {
        return Platform.newLinkedHashSetWithExpectedSize(this.valueSetCapacity);
    }

    @Override
    public Set<Map.Entry<K, V>> entries() {
        return super.entries();
    }

    @Override
    Iterator<Map.Entry<K, V>> entryIterator() {
        return new Iterator<Map.Entry<K, V>>(){
            ValueEntry<K, V> nextEntry;
            @NullableDecl
            ValueEntry<K, V> toRemove;
            {
                this.nextEntry = LinkedHashMultimap.access$300((LinkedHashMultimap)LinkedHashMultimap.this).successorInMultimap;
            }

            @Override
            public boolean hasNext() {
                if (this.nextEntry == LinkedHashMultimap.this.multimapHeaderEntry) return false;
                return true;
            }

            @Override
            public Map.Entry<K, V> next() {
                if (!this.hasNext()) throw new NoSuchElementException();
                ValueEntry<K, V> valueEntry = this.nextEntry;
                this.toRemove = valueEntry;
                this.nextEntry = valueEntry.successorInMultimap;
                return valueEntry;
            }

            @Override
            public void remove() {
                boolean bl = this.toRemove != null;
                CollectPreconditions.checkRemove(bl);
                LinkedHashMultimap.this.remove(this.toRemove.getKey(), this.toRemove.getValue());
                this.toRemove = null;
            }
        };
    }

    @Override
    public Set<K> keySet() {
        return super.keySet();
    }

    @Override
    public Set<V> replaceValues(@NullableDecl K k, Iterable<? extends V> iterable) {
        return super.replaceValues((Object)k, (Iterable)iterable);
    }

    @Override
    Iterator<V> valueIterator() {
        return Maps.valueIterator(this.entryIterator());
    }

    @Override
    public Collection<V> values() {
        return super.values();
    }

    static final class ValueEntry<K, V>
    extends ImmutableEntry<K, V>
    implements ValueSetLink<K, V> {
        @NullableDecl
        ValueEntry<K, V> nextInValueBucket;
        @NullableDecl
        ValueEntry<K, V> predecessorInMultimap;
        @NullableDecl
        ValueSetLink<K, V> predecessorInValueSet;
        final int smearedValueHash;
        @NullableDecl
        ValueEntry<K, V> successorInMultimap;
        @NullableDecl
        ValueSetLink<K, V> successorInValueSet;

        ValueEntry(@NullableDecl K k, @NullableDecl V v, int n, @NullableDecl ValueEntry<K, V> valueEntry) {
            super(k, v);
            this.smearedValueHash = n;
            this.nextInValueBucket = valueEntry;
        }

        public ValueEntry<K, V> getPredecessorInMultimap() {
            return this.predecessorInMultimap;
        }

        @Override
        public ValueSetLink<K, V> getPredecessorInValueSet() {
            return this.predecessorInValueSet;
        }

        public ValueEntry<K, V> getSuccessorInMultimap() {
            return this.successorInMultimap;
        }

        @Override
        public ValueSetLink<K, V> getSuccessorInValueSet() {
            return this.successorInValueSet;
        }

        boolean matchesValue(@NullableDecl Object object, int n) {
            if (this.smearedValueHash != n) return false;
            if (!Objects.equal(this.getValue(), object)) return false;
            return true;
        }

        public void setPredecessorInMultimap(ValueEntry<K, V> valueEntry) {
            this.predecessorInMultimap = valueEntry;
        }

        @Override
        public void setPredecessorInValueSet(ValueSetLink<K, V> valueSetLink) {
            this.predecessorInValueSet = valueSetLink;
        }

        public void setSuccessorInMultimap(ValueEntry<K, V> valueEntry) {
            this.successorInMultimap = valueEntry;
        }

        @Override
        public void setSuccessorInValueSet(ValueSetLink<K, V> valueSetLink) {
            this.successorInValueSet = valueSetLink;
        }
    }

    final class ValueSet
    extends Sets.ImprovedAbstractSet<V>
    implements ValueSetLink<K, V> {
        private ValueSetLink<K, V> firstEntry;
        ValueEntry<K, V>[] hashTable;
        private final K key;
        private ValueSetLink<K, V> lastEntry;
        private int modCount = 0;
        private int size = 0;

        ValueSet(K k, int n) {
            this.key = k;
            this.firstEntry = this;
            this.lastEntry = this;
            this.hashTable = new ValueEntry[Hashing.closedTableSize(n, 1.0)];
        }

        private int mask() {
            return this.hashTable.length - 1;
        }

        private void rehashIfNecessary() {
            if (!Hashing.needsResizing(this.size, this.hashTable.length, 1.0)) return;
            int n = this.hashTable.length * 2;
            ValueEntry[] arrvalueEntry = new ValueEntry[n];
            this.hashTable = arrvalueEntry;
            ValueSetLink<K, V> valueSetLink = this.firstEntry;
            while (valueSetLink != this) {
                ValueEntry valueEntry = (ValueEntry)valueSetLink;
                int n2 = valueEntry.smearedValueHash & n - 1;
                valueEntry.nextInValueBucket = arrvalueEntry[n2];
                arrvalueEntry[n2] = valueEntry;
                valueSetLink = valueSetLink.getSuccessorInValueSet();
            }
        }

        @Override
        public boolean add(@NullableDecl V object) {
            ValueEntry<K, V> valueEntry;
            int n = Hashing.smearedHash(object);
            int n2 = this.mask() & n;
            ValueEntry<K, V> valueEntry2 = valueEntry = this.hashTable[n2];
            do {
                if (valueEntry2 == null) {
                    object = new ValueEntry<K, V>(this.key, object, n, valueEntry);
                    LinkedHashMultimap.succeedsInValueSet(this.lastEntry, object);
                    LinkedHashMultimap.succeedsInValueSet(object, this);
                    LinkedHashMultimap.succeedsInMultimap(LinkedHashMultimap.this.multimapHeaderEntry.getPredecessorInMultimap(), object);
                    LinkedHashMultimap.succeedsInMultimap(object, LinkedHashMultimap.this.multimapHeaderEntry);
                    this.hashTable[n2] = object;
                    ++this.size;
                    ++this.modCount;
                    this.rehashIfNecessary();
                    return true;
                }
                if (valueEntry2.matchesValue(object, n)) {
                    return false;
                }
                valueEntry2 = valueEntry2.nextInValueBucket;
            } while (true);
        }

        @Override
        public void clear() {
            Arrays.fill(this.hashTable, null);
            this.size = 0;
            ValueSetLink<K, V> valueSetLink = this.firstEntry;
            do {
                if (valueSetLink == this) {
                    LinkedHashMultimap.succeedsInValueSet(this, this);
                    ++this.modCount;
                    return;
                }
                LinkedHashMultimap.deleteFromMultimap((ValueEntry)valueSetLink);
                valueSetLink = valueSetLink.getSuccessorInValueSet();
            } while (true);
        }

        @Override
        public boolean contains(@NullableDecl Object object) {
            int n = Hashing.smearedHash(object);
            ValueEntry<K, V> valueEntry = this.hashTable[this.mask() & n];
            while (valueEntry != null) {
                if (valueEntry.matchesValue(object, n)) {
                    return true;
                }
                valueEntry = valueEntry.nextInValueBucket;
            }
            return false;
        }

        @Override
        public ValueSetLink<K, V> getPredecessorInValueSet() {
            return this.lastEntry;
        }

        @Override
        public ValueSetLink<K, V> getSuccessorInValueSet() {
            return this.firstEntry;
        }

        @Override
        public Iterator<V> iterator() {
            return new Iterator<V>(){
                int expectedModCount;
                ValueSetLink<K, V> nextEntry;
                @NullableDecl
                ValueEntry<K, V> toRemove;
                {
                    this.nextEntry = ValueSet.this.firstEntry;
                    this.expectedModCount = ValueSet.this.modCount;
                }

                private void checkForComodification() {
                    if (ValueSet.this.modCount != this.expectedModCount) throw new ConcurrentModificationException();
                }

                @Override
                public boolean hasNext() {
                    this.checkForComodification();
                    if (this.nextEntry == ValueSet.this) return false;
                    return true;
                }

                @Override
                public V next() {
                    if (!this.hasNext()) throw new NoSuchElementException();
                    ValueEntry valueEntry = (ValueEntry)this.nextEntry;
                    Object v = valueEntry.getValue();
                    this.toRemove = valueEntry;
                    this.nextEntry = valueEntry.getSuccessorInValueSet();
                    return v;
                }

                @Override
                public void remove() {
                    this.checkForComodification();
                    boolean bl = this.toRemove != null;
                    CollectPreconditions.checkRemove(bl);
                    ValueSet.this.remove(this.toRemove.getValue());
                    this.expectedModCount = ValueSet.this.modCount;
                    this.toRemove = null;
                }
            };
        }

        @Override
        public boolean remove(@NullableDecl Object object) {
            ValueEntry<K, V> valueEntry;
            block4 : {
                int n = Hashing.smearedHash(object);
                int n2 = this.mask() & n;
                valueEntry = this.hashTable[n2];
                ValueEntry<K, V> valueEntry2 = null;
                while (valueEntry != null) {
                    if (valueEntry.matchesValue(object, n)) {
                        if (valueEntry2 == null) {
                            this.hashTable[n2] = valueEntry.nextInValueBucket;
                        } else {
                            valueEntry2.nextInValueBucket = valueEntry.nextInValueBucket;
                        }
                        break block4;
                    }
                    ValueEntry valueEntry3 = valueEntry.nextInValueBucket;
                    valueEntry2 = valueEntry;
                    valueEntry = valueEntry3;
                }
                return false;
            }
            LinkedHashMultimap.deleteFromValueSet(valueEntry);
            LinkedHashMultimap.deleteFromMultimap(valueEntry);
            --this.size;
            ++this.modCount;
            return true;
        }

        @Override
        public void setPredecessorInValueSet(ValueSetLink<K, V> valueSetLink) {
            this.lastEntry = valueSetLink;
        }

        @Override
        public void setSuccessorInValueSet(ValueSetLink<K, V> valueSetLink) {
            this.firstEntry = valueSetLink;
        }

        @Override
        public int size() {
            return this.size;
        }

    }

    private static interface ValueSetLink<K, V> {
        public ValueSetLink<K, V> getPredecessorInValueSet();

        public ValueSetLink<K, V> getSuccessorInValueSet();

        public void setPredecessorInValueSet(ValueSetLink<K, V> var1);

        public void setSuccessorInValueSet(ValueSetLink<K, V> var1);
    }

}

