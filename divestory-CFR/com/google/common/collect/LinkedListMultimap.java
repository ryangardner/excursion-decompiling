/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractMapEntry;
import com.google.common.collect.AbstractMultimap;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.CompactLinkedHashMap;
import com.google.common.collect.Iterators;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Platform;
import com.google.common.collect.Sets;
import com.google.common.collect.TransformedListIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class LinkedListMultimap<K, V>
extends AbstractMultimap<K, V>
implements ListMultimap<K, V>,
Serializable {
    private static final long serialVersionUID = 0L;
    @NullableDecl
    private transient Node<K, V> head;
    private transient Map<K, KeyList<K, V>> keyToKeyList;
    private transient int modCount;
    private transient int size;
    @NullableDecl
    private transient Node<K, V> tail;

    LinkedListMultimap() {
        this(12);
    }

    private LinkedListMultimap(int n) {
        this.keyToKeyList = Platform.newHashMapWithExpectedSize(n);
    }

    private LinkedListMultimap(Multimap<? extends K, ? extends V> multimap) {
        this(multimap.keySet().size());
        this.putAll(multimap);
    }

    private Node<K, V> addNode(@NullableDecl K object, @NullableDecl V object2, @NullableDecl Node<K, V> keyList) {
        object2 = new Node<K, V>(object, object2);
        if (this.head == null) {
            this.tail = object2;
            this.head = object2;
            this.keyToKeyList.put(object, new KeyList(object2));
            ++this.modCount;
        } else if (keyList == null) {
            this.tail.next = object2;
            ((Node)object2).previous = this.tail;
            this.tail = object2;
            keyList = this.keyToKeyList.get(object);
            if (keyList == null) {
                this.keyToKeyList.put(object, new KeyList(object2));
                ++this.modCount;
            } else {
                ++keyList.count;
                object = keyList.tail;
                ((Node)object).nextSibling = object2;
                ((Node)object2).previousSibling = object;
                keyList.tail = object2;
            }
        } else {
            KeyList<K, V> keyList2 = this.keyToKeyList.get(object);
            ++keyList2.count;
            ((Node)object2).previous = ((Node)keyList).previous;
            ((Node)object2).previousSibling = ((Node)keyList).previousSibling;
            ((Node)object2).next = keyList;
            ((Node)object2).nextSibling = keyList;
            if (((Node)keyList).previousSibling == null) {
                this.keyToKeyList.get(object).head = object2;
            } else {
                keyList.previousSibling.nextSibling = object2;
            }
            if (((Node)keyList).previous == null) {
                this.head = object2;
            } else {
                keyList.previous.next = object2;
            }
            ((Node)keyList).previous = object2;
            ((Node)keyList).previousSibling = object2;
        }
        ++this.size;
        return object2;
    }

    private static void checkElement(@NullableDecl Object object) {
        if (object == null) throw new NoSuchElementException();
    }

    public static <K, V> LinkedListMultimap<K, V> create() {
        return new LinkedListMultimap<K, V>();
    }

    public static <K, V> LinkedListMultimap<K, V> create(int n) {
        return new LinkedListMultimap<K, V>(n);
    }

    public static <K, V> LinkedListMultimap<K, V> create(Multimap<? extends K, ? extends V> multimap) {
        return new LinkedListMultimap<K, V>(multimap);
    }

    private List<V> getCopy(@NullableDecl Object object) {
        return Collections.unmodifiableList(Lists.newArrayList(new ValueForKeyIterator(object)));
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.keyToKeyList = CompactLinkedHashMap.create();
        int n = objectInputStream.readInt();
        int n2 = 0;
        while (n2 < n) {
            this.put(objectInputStream.readObject(), objectInputStream.readObject());
            ++n2;
        }
    }

    private void removeAllNodes(@NullableDecl Object object) {
        Iterators.clear(new ValueForKeyIterator(object));
    }

    private void removeNode(Node<K, V> node) {
        if (node.previous != null) {
            node.previous.next = node.next;
        } else {
            this.head = node.next;
        }
        if (node.next != null) {
            node.next.previous = node.previous;
        } else {
            this.tail = node.previous;
        }
        if (node.previousSibling == null && node.nextSibling == null) {
            this.keyToKeyList.remove(node.key).count = 0;
            ++this.modCount;
        } else {
            KeyList<K, V> keyList = this.keyToKeyList.get(node.key);
            --keyList.count;
            if (node.previousSibling == null) {
                keyList.head = node.nextSibling;
            } else {
                node.previousSibling.nextSibling = node.nextSibling;
            }
            if (node.nextSibling == null) {
                keyList.tail = node.previousSibling;
            } else {
                node.nextSibling.previousSibling = node.previousSibling;
            }
        }
        --this.size;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.size());
        Iterator iterator2 = this.entries().iterator();
        while (iterator2.hasNext()) {
            Map.Entry entry = (Map.Entry)iterator2.next();
            objectOutputStream.writeObject(entry.getKey());
            objectOutputStream.writeObject(entry.getValue());
        }
    }

    @Override
    public void clear() {
        this.head = null;
        this.tail = null;
        this.keyToKeyList.clear();
        this.size = 0;
        ++this.modCount;
    }

    @Override
    public boolean containsKey(@NullableDecl Object object) {
        return this.keyToKeyList.containsKey(object);
    }

    @Override
    public boolean containsValue(@NullableDecl Object object) {
        return this.values().contains(object);
    }

    @Override
    Map<K, Collection<V>> createAsMap() {
        return new Multimaps.AsMap(this);
    }

    @Override
    List<Map.Entry<K, V>> createEntries() {
        return new 1EntriesImpl();
    }

    @Override
    Set<K> createKeySet() {
        return new 1KeySetImpl();
    }

    @Override
    Multiset<K> createKeys() {
        return new Multimaps.Keys(this);
    }

    @Override
    List<V> createValues() {
        return new 1ValuesImpl();
    }

    @Override
    public List<Map.Entry<K, V>> entries() {
        return (List)super.entries();
    }

    @Override
    Iterator<Map.Entry<K, V>> entryIterator() {
        throw new AssertionError((Object)"should never be called");
    }

    @Override
    public List<V> get(final @NullableDecl K k) {
        return new AbstractSequentialList<V>(){

            @Override
            public ListIterator<V> listIterator(int n) {
                return new ValueForKeyIterator(k, n);
            }

            @Override
            public int size() {
                KeyList keyList = (KeyList)LinkedListMultimap.this.keyToKeyList.get(k);
                if (keyList != null) return keyList.count;
                return 0;
            }
        };
    }

    @Override
    public boolean isEmpty() {
        if (this.head != null) return false;
        return true;
    }

    @Override
    public boolean put(@NullableDecl K k, @NullableDecl V v) {
        this.addNode(k, v, null);
        return true;
    }

    @Override
    public List<V> removeAll(@NullableDecl Object object) {
        List<V> list = this.getCopy(object);
        this.removeAllNodes(object);
        return list;
    }

    @Override
    public List<V> replaceValues(@NullableDecl K object, Iterable<? extends V> object2) {
        List<V> list = this.getCopy(object);
        object = new ValueForKeyIterator(object);
        object2 = object2.iterator();
        while (object.hasNext() && object2.hasNext()) {
            object.next();
            object.set(object2.next());
        }
        do {
            if (!object.hasNext()) {
                while (object2.hasNext()) {
                    object.add(object2.next());
                }
                return list;
            }
            object.next();
            object.remove();
        } while (true);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public List<V> values() {
        return (List)super.values();
    }

    class 1EntriesImpl
    extends AbstractSequentialList<Map.Entry<K, V>> {
        1EntriesImpl() {
        }

        @Override
        public ListIterator<Map.Entry<K, V>> listIterator(int n) {
            return new NodeIterator(n);
        }

        @Override
        public int size() {
            return LinkedListMultimap.this.size;
        }
    }

    class 1KeySetImpl
    extends Sets.ImprovedAbstractSet<K> {
        1KeySetImpl() {
        }

        @Override
        public boolean contains(Object object) {
            return LinkedListMultimap.this.containsKey(object);
        }

        @Override
        public Iterator<K> iterator() {
            return new DistinctKeyIterator();
        }

        @Override
        public boolean remove(Object object) {
            return LinkedListMultimap.this.removeAll(object).isEmpty() ^ true;
        }

        @Override
        public int size() {
            return LinkedListMultimap.this.keyToKeyList.size();
        }
    }

    class 1ValuesImpl
    extends AbstractSequentialList<V> {
        1ValuesImpl() {
        }

        @Override
        public ListIterator<V> listIterator(int n) {
            final NodeIterator nodeIterator = new NodeIterator(n);
            return new TransformedListIterator<Map.Entry<K, V>, V>(nodeIterator){

                @Override
                public void set(V v) {
                    nodeIterator.setValue(v);
                }

                @Override
                V transform(Map.Entry<K, V> entry) {
                    return entry.getValue();
                }
            };
        }

        @Override
        public int size() {
            return LinkedListMultimap.this.size;
        }

    }

    private class DistinctKeyIterator
    implements Iterator<K> {
        @NullableDecl
        Node<K, V> current;
        int expectedModCount;
        Node<K, V> next;
        final Set<K> seenKeys;

        private DistinctKeyIterator() {
            this.seenKeys = Sets.newHashSetWithExpectedSize(LinkedListMultimap.this.keySet().size());
            this.next = LinkedListMultimap.this.head;
            this.expectedModCount = LinkedListMultimap.this.modCount;
        }

        private void checkForConcurrentModification() {
            if (LinkedListMultimap.this.modCount != this.expectedModCount) throw new ConcurrentModificationException();
        }

        @Override
        public boolean hasNext() {
            this.checkForConcurrentModification();
            if (this.next == null) return false;
            return true;
        }

        @Override
        public K next() {
            this.checkForConcurrentModification();
            LinkedListMultimap.checkElement(this.next);
            Node<K, V> node = this.next;
            this.current = node;
            this.seenKeys.add(node.key);
            do {
                this.next = node = this.next.next;
                if (node == null) return this.current.key;
            } while (!this.seenKeys.add(node.key));
            return this.current.key;
        }

        @Override
        public void remove() {
            this.checkForConcurrentModification();
            boolean bl = this.current != null;
            CollectPreconditions.checkRemove(bl);
            LinkedListMultimap.this.removeAllNodes(this.current.key);
            this.current = null;
            this.expectedModCount = LinkedListMultimap.this.modCount;
        }
    }

    private static class KeyList<K, V> {
        int count;
        Node<K, V> head;
        Node<K, V> tail;

        KeyList(Node<K, V> node) {
            this.head = node;
            this.tail = node;
            node.previousSibling = null;
            node.nextSibling = null;
            this.count = 1;
        }
    }

    private static final class Node<K, V>
    extends AbstractMapEntry<K, V> {
        @NullableDecl
        final K key;
        @NullableDecl
        Node<K, V> next;
        @NullableDecl
        Node<K, V> nextSibling;
        @NullableDecl
        Node<K, V> previous;
        @NullableDecl
        Node<K, V> previousSibling;
        @NullableDecl
        V value;

        Node(@NullableDecl K k, @NullableDecl V v) {
            this.key = k;
            this.value = v;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(@NullableDecl V v) {
            V v2 = this.value;
            this.value = v;
            return v2;
        }
    }

    private class NodeIterator
    implements ListIterator<Map.Entry<K, V>> {
        @NullableDecl
        Node<K, V> current;
        int expectedModCount;
        @NullableDecl
        Node<K, V> next;
        int nextIndex;
        @NullableDecl
        Node<K, V> previous;

        NodeIterator(int n) {
            this.expectedModCount = LinkedListMultimap.this.modCount;
            int n2 = LinkedListMultimap.this.size();
            Preconditions.checkPositionIndex(n, n2);
            if (n >= n2 / 2) {
                this.previous = LinkedListMultimap.this.tail;
                this.nextIndex = n2;
                while (n < n2) {
                    this.previous();
                    ++n;
                }
            } else {
                this.next = LinkedListMultimap.this.head;
                while (n > 0) {
                    this.next();
                    --n;
                }
            }
            this.current = null;
        }

        private void checkForConcurrentModification() {
            if (LinkedListMultimap.this.modCount != this.expectedModCount) throw new ConcurrentModificationException();
        }

        @Override
        public void add(Map.Entry<K, V> entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            this.checkForConcurrentModification();
            if (this.next == null) return false;
            return true;
        }

        @Override
        public boolean hasPrevious() {
            this.checkForConcurrentModification();
            if (this.previous == null) return false;
            return true;
        }

        @Override
        public Node<K, V> next() {
            this.checkForConcurrentModification();
            LinkedListMultimap.checkElement(this.next);
            Node<K, V> node = this.next;
            this.current = node;
            this.previous = node;
            this.next = node.next;
            ++this.nextIndex;
            return this.current;
        }

        @Override
        public int nextIndex() {
            return this.nextIndex;
        }

        @Override
        public Node<K, V> previous() {
            this.checkForConcurrentModification();
            LinkedListMultimap.checkElement(this.previous);
            Node<K, V> node = this.previous;
            this.current = node;
            this.next = node;
            this.previous = node.previous;
            --this.nextIndex;
            return this.current;
        }

        @Override
        public int previousIndex() {
            return this.nextIndex - 1;
        }

        @Override
        public void remove() {
            this.checkForConcurrentModification();
            boolean bl = this.current != null;
            CollectPreconditions.checkRemove(bl);
            Node<K, V> node = this.current;
            if (node != this.next) {
                this.previous = node.previous;
                --this.nextIndex;
            } else {
                this.next = node.next;
            }
            LinkedListMultimap.this.removeNode(this.current);
            this.current = null;
            this.expectedModCount = LinkedListMultimap.this.modCount;
        }

        @Override
        public void set(Map.Entry<K, V> entry) {
            throw new UnsupportedOperationException();
        }

        void setValue(V v) {
            boolean bl = this.current != null;
            Preconditions.checkState(bl);
            this.current.value = v;
        }
    }

    private class ValueForKeyIterator
    implements ListIterator<V> {
        @NullableDecl
        Node<K, V> current;
        @NullableDecl
        final Object key;
        @NullableDecl
        Node<K, V> next;
        int nextIndex;
        @NullableDecl
        Node<K, V> previous;

        ValueForKeyIterator(Object object) {
            this.key = object;
            LinkedListMultimap.this = (KeyList)((LinkedListMultimap)((Object)LinkedListMultimap.this)).keyToKeyList.get(object);
            LinkedListMultimap.this = LinkedListMultimap.this == null ? null : ((KeyList)LinkedListMultimap.this).head;
            this.next = LinkedListMultimap.this;
        }

        public ValueForKeyIterator(Object object, int n) {
            LinkedListMultimap.this = (KeyList)((LinkedListMultimap)((Object)LinkedListMultimap.this)).keyToKeyList.get(object);
            int n2 = LinkedListMultimap.this == null ? 0 : ((KeyList)LinkedListMultimap.this).count;
            Preconditions.checkPositionIndex(n, n2);
            if (n >= n2 / 2) {
                LinkedListMultimap.this = LinkedListMultimap.this == null ? null : ((KeyList)LinkedListMultimap.this).tail;
                this.previous = LinkedListMultimap.this;
                this.nextIndex = n2;
                while (n < n2) {
                    this.previous();
                    ++n;
                }
            } else {
                LinkedListMultimap.this = LinkedListMultimap.this == null ? null : ((KeyList)LinkedListMultimap.this).head;
                this.next = LinkedListMultimap.this;
                while (n > 0) {
                    this.next();
                    --n;
                }
            }
            this.key = object;
            this.current = null;
        }

        @Override
        public void add(V v) {
            this.previous = LinkedListMultimap.this.addNode(this.key, v, this.next);
            ++this.nextIndex;
            this.current = null;
        }

        @Override
        public boolean hasNext() {
            if (this.next == null) return false;
            return true;
        }

        @Override
        public boolean hasPrevious() {
            if (this.previous == null) return false;
            return true;
        }

        @Override
        public V next() {
            LinkedListMultimap.checkElement(this.next);
            Node<K, V> node = this.next;
            this.current = node;
            this.previous = node;
            this.next = node.nextSibling;
            ++this.nextIndex;
            return this.current.value;
        }

        @Override
        public int nextIndex() {
            return this.nextIndex;
        }

        @Override
        public V previous() {
            LinkedListMultimap.checkElement(this.previous);
            Node<K, V> node = this.previous;
            this.current = node;
            this.next = node;
            this.previous = node.previousSibling;
            --this.nextIndex;
            return this.current.value;
        }

        @Override
        public int previousIndex() {
            return this.nextIndex - 1;
        }

        @Override
        public void remove() {
            boolean bl = this.current != null;
            CollectPreconditions.checkRemove(bl);
            Node<K, V> node = this.current;
            if (node != this.next) {
                this.previous = node.previousSibling;
                --this.nextIndex;
            } else {
                this.next = node.nextSibling;
            }
            LinkedListMultimap.this.removeNode(this.current);
            this.current = null;
        }

        @Override
        public void set(V v) {
            boolean bl = this.current != null;
            Preconditions.checkState(bl);
            this.current.value = v;
        }
    }

}

