/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.grpc;

import java.util.Arrays;

final class PersistentHashArrayMappedTrie<K, V> {
    private final Node<K, V> root;

    PersistentHashArrayMappedTrie() {
        this(null);
    }

    private PersistentHashArrayMappedTrie(Node<K, V> node) {
        this.root = node;
    }

    public V get(K k) {
        Node<K, V> node = this.root;
        if (node != null) return node.get(k, k.hashCode(), 0);
        return null;
    }

    public PersistentHashArrayMappedTrie<K, V> put(K k, V v) {
        if (this.root != null) return new PersistentHashArrayMappedTrie<K, V>(this.root.put(k, v, k.hashCode(), 0));
        return new PersistentHashArrayMappedTrie<K, V>(new Leaf<K, V>(k, v));
    }

    public int size() {
        Node<K, V> node = this.root;
        if (node != null) return node.size();
        return 0;
    }

    static final class CollisionLeaf<K, V>
    implements Node<K, V> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final K[] keys;
        private final V[] values;

        CollisionLeaf(K k, V v, K k2, V v2) {
            this(new Object[]{k, k2}, new Object[]{v, v2});
        }

        private CollisionLeaf(K[] arrK, V[] arrV) {
            this.keys = arrK;
            this.values = arrV;
        }

        private int indexOfKey(K k) {
            K[] arrK;
            int n = 0;
            while (n < (arrK = this.keys).length) {
                if (arrK[n] == k) {
                    return n;
                }
                ++n;
            }
            return -1;
        }

        @Override
        public V get(K k, int n, int n2) {
            K[] arrK;
            n = 0;
            while (n < (arrK = this.keys).length) {
                if (arrK[n] == k) {
                    return this.values[n];
                }
                ++n;
            }
            return null;
        }

        @Override
        public Node<K, V> put(K k, V v, int n, int n2) {
            int n3 = this.keys[0].hashCode();
            if (n3 != n) {
                return CompressedIndex.combine(new Leaf<K, V>(k, v), n, this, n3, n2);
            }
            n = this.indexOfKey(k);
            if (n != -1) {
                K[] arrK = this.keys;
                arrK = Arrays.copyOf(arrK, arrK.length);
                V[] arrV = Arrays.copyOf(this.values, this.keys.length);
                arrK[n] = k;
                arrV[n] = v;
                return new CollisionLeaf<K, V>(arrK, arrV);
            }
            K[] arrK = this.keys;
            arrK = Arrays.copyOf(arrK, arrK.length + 1);
            V[] arrV = Arrays.copyOf(this.values, this.keys.length + 1);
            K[] arrK2 = this.keys;
            arrK[arrK2.length] = k;
            arrV[arrK2.length] = v;
            return new CollisionLeaf<K, V>(arrK, arrV);
        }

        @Override
        public int size() {
            return this.values.length;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CollisionLeaf(");
            int n = 0;
            do {
                if (n >= this.values.length) {
                    stringBuilder.append(")");
                    return stringBuilder.toString();
                }
                stringBuilder.append("(key=");
                stringBuilder.append(this.keys[n]);
                stringBuilder.append(" value=");
                stringBuilder.append(this.values[n]);
                stringBuilder.append(") ");
                ++n;
            } while (true);
        }
    }

    static final class CompressedIndex<K, V>
    implements Node<K, V> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final int BITS = 5;
        private static final int BITS_MASK = 31;
        final int bitmap;
        private final int size;
        final Node<K, V>[] values;

        private CompressedIndex(int n, Node<K, V>[] arrnode, int n2) {
            this.bitmap = n;
            this.values = arrnode;
            this.size = n2;
        }

        static <K, V> Node<K, V> combine(Node<K, V> node, int n, Node<K, V> node2, int n2, int n3) {
            int n4;
            int n5 = CompressedIndex.indexBit(n, n3);
            if (n5 == (n4 = CompressedIndex.indexBit(n2, n3))) {
                node = CompressedIndex.combine(node, n, node2, n2, n3 + 5);
                n = node.size();
                return new CompressedIndex<K, V>(n5, new Node[]{node}, n);
            }
            Node<K, V> node3 = node;
            Node<K, V> node4 = node2;
            if (CompressedIndex.uncompressedIndex(n, n3) > CompressedIndex.uncompressedIndex(n2, n3)) {
                node4 = node;
                node3 = node2;
            }
            n2 = node3.size();
            n = node4.size();
            return new CompressedIndex<K, V>(n5 | n4, new Node[]{node3, node4}, n2 + n);
        }

        private int compressedIndex(int n) {
            return Integer.bitCount(n - 1 & this.bitmap);
        }

        private static int indexBit(int n, int n2) {
            return 1 << CompressedIndex.uncompressedIndex(n, n2);
        }

        private static int uncompressedIndex(int n, int n2) {
            return n >>> n2 & 31;
        }

        @Override
        public V get(K k, int n, int n2) {
            int n3 = CompressedIndex.indexBit(n, n2);
            if ((this.bitmap & n3) == 0) {
                return null;
            }
            n3 = this.compressedIndex(n3);
            return this.values[n3].get(k, n, n2 + 5);
        }

        @Override
        public Node<K, V> put(K object, V v, int n, int n2) {
            int n3 = CompressedIndex.indexBit(n, n2);
            int n4 = this.compressedIndex(n3);
            int n5 = this.bitmap;
            if ((n5 & n3) == 0) {
                Node<K, V>[] arrnode = this.values;
                Node[] arrnode2 = new Node[arrnode.length + 1];
                System.arraycopy(arrnode, 0, arrnode2, 0, n4);
                arrnode2[n4] = new Leaf<K, V>(object, v);
                object = this.values;
                System.arraycopy(object, n4, arrnode2, n4 + 1, ((K)object).length - n4);
                return new CompressedIndex<K, V>(n5 | n3, arrnode2, this.size() + 1);
            }
            Node<K, V>[] arrnode = this.values;
            arrnode = Arrays.copyOf(arrnode, arrnode.length);
            arrnode[n4] = this.values[n4].put(object, v, n, n2 + 5);
            n = this.size();
            n2 = arrnode[n4].size();
            n4 = this.values[n4].size();
            return new CompressedIndex<K, V>(this.bitmap, arrnode, n + n2 - n4);
        }

        @Override
        public int size() {
            return this.size;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CompressedIndex(");
            Node<K, V>[] arrnode = Integer.toBinaryString(this.bitmap);
            int n = 0;
            stringBuilder.append(String.format("bitmap=%s ", new Object[]{arrnode}));
            arrnode = this.values;
            int n2 = arrnode.length;
            do {
                if (n >= n2) {
                    stringBuilder.append(")");
                    return stringBuilder.toString();
                }
                stringBuilder.append(arrnode[n]);
                stringBuilder.append(" ");
                ++n;
            } while (true);
        }
    }

    static final class Leaf<K, V>
    implements Node<K, V> {
        private final K key;
        private final V value;

        public Leaf(K k, V v) {
            this.key = k;
            this.value = v;
        }

        @Override
        public V get(K k, int n, int n2) {
            if (this.key != k) return null;
            return this.value;
        }

        @Override
        public Node<K, V> put(K k, V v, int n, int n2) {
            int n3 = this.key.hashCode();
            if (n3 != n) {
                return CompressedIndex.combine(new Leaf<K, V>(k, v), n, this, n3, n2);
            }
            if (this.key != k) return new CollisionLeaf<K, V>(this.key, this.value, k, v);
            return new Leaf<K, V>(k, v);
        }

        @Override
        public int size() {
            return 1;
        }

        public String toString() {
            return String.format("Leaf(key=%s value=%s)", this.key, this.value);
        }
    }

    static interface Node<K, V> {
        public V get(K var1, int var2, int var3);

        public Node<K, V> put(K var1, V var2, int var3, int var4);

        public int size();
    }

}

