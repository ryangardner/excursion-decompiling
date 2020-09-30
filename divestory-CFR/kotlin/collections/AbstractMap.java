/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  kotlin.collections.AbstractMap$keys
 *  kotlin.collections.AbstractMap$toString
 *  kotlin.collections.AbstractMap$values
 */
package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.AbstractCollection;
import kotlin.collections.AbstractMap;
import kotlin.collections.AbstractSet;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(bv={1, 0, 3}, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010&\n\u0002\b\b\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0003\b'\u0018\u0000 )*\u0004\b\u0000\u0010\u0001*\u0006\b\u0001\u0010\u0002 \u00012\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003:\u0001)B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0004J\u001f\u0010\u0013\u001a\u00020\u00142\u0010\u0010\u0015\u001a\f\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0018\u00010\u0016H\u0000\u00a2\u0006\u0002\b\u0017J\u0015\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u0010\u001aJ\u0015\u0010\u001b\u001a\u00020\u00142\u0006\u0010\u001c\u001a\u00028\u0001H\u0016\u00a2\u0006\u0002\u0010\u001aJ\u0013\u0010\u001d\u001a\u00020\u00142\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0096\u0002J\u0018\u0010 \u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0019\u001a\u00028\u0000H\u0096\u0002\u00a2\u0006\u0002\u0010!J\b\u0010\"\u001a\u00020\rH\u0016J#\u0010#\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u00162\u0006\u0010\u0019\u001a\u00028\u0000H\u0002\u00a2\u0006\u0002\u0010$J\b\u0010%\u001a\u00020\u0014H\u0016J\b\u0010&\u001a\u00020'H\u0016J\u0012\u0010&\u001a\u00020'2\b\u0010(\u001a\u0004\u0018\u00010\u001fH\u0002J\u001c\u0010&\u001a\u00020'2\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0016H\bR\u0016\u0010\u0005\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\bX\u0088\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00010\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006*"}, d2={"Lkotlin/collections/AbstractMap;", "K", "V", "", "()V", "_keys", "", "_values", "", "keys", "getKeys", "()Ljava/util/Set;", "size", "", "getSize", "()I", "values", "getValues", "()Ljava/util/Collection;", "containsEntry", "", "entry", "", "containsEntry$kotlin_stdlib", "containsKey", "key", "(Ljava/lang/Object;)Z", "containsValue", "value", "equals", "other", "", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", "hashCode", "implFindEntry", "(Ljava/lang/Object;)Ljava/util/Map$Entry;", "isEmpty", "toString", "", "o", "Companion", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public abstract class AbstractMap<K, V>
implements Map<K, V>,
KMappedMarker {
    public static final Companion Companion = new Companion(null);
    private volatile Set<? extends K> _keys;
    private volatile Collection<? extends V> _values;

    protected AbstractMap() {
    }

    public static final /* synthetic */ String access$toString(AbstractMap abstractMap, Map.Entry entry) {
        return abstractMap.toString(entry);
    }

    private final Map.Entry<K, V> implFindEntry(K object) {
        Object t;
        Iterator iterator2 = ((Iterable)this.entrySet()).iterator();
        do {
            if (iterator2.hasNext()) continue;
            object = null;
            return (Map.Entry)object;
        } while (!Intrinsics.areEqual(((Map.Entry)(t = iterator2.next())).getKey(), object));
        object = t;
        return (Map.Entry)object;
    }

    private final String toString(Object object) {
        if (object != this) return String.valueOf(object);
        return "(this Map)";
    }

    private final String toString(Map.Entry<? extends K, ? extends V> entry) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.toString(entry.getKey()));
        stringBuilder.append("=");
        stringBuilder.append(this.toString(entry.getValue()));
        return stringBuilder.toString();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public final boolean containsEntry$kotlin_stdlib(Map.Entry<?, ?> entry) {
        if (!(entry instanceof Map.Entry)) {
            return false;
        }
        Object obj = entry.getKey();
        Object obj2 = entry.getValue();
        Map map = this;
        entry = map.get(obj);
        if (Intrinsics.areEqual(obj2, entry) ^ true) {
            return false;
        }
        if (entry != null) return true;
        if (map.containsKey(obj)) return true;
        return false;
    }

    @Override
    public boolean containsKey(Object object) {
        if (this.implFindEntry(object) == null) return false;
        return true;
    }

    @Override
    public boolean containsValue(Object object) {
        Object object2 = this.entrySet();
        boolean bl = object2 instanceof Collection;
        boolean bl2 = false;
        if (bl && ((Collection)object2).isEmpty()) {
            return bl2;
        }
        object2 = object2.iterator();
        do {
            bl = bl2;
            if (!object2.hasNext()) return bl;
        } while (!Intrinsics.areEqual(((Map.Entry)object2.next()).getValue(), object));
        return true;
    }

    @Override
    public boolean equals(Object iterator2) {
        AbstractMap abstractMap = this;
        boolean bl = true;
        if (iterator2 == abstractMap) {
            return true;
        }
        if (!(iterator2 instanceof Map)) {
            return false;
        }
        int n = this.size();
        if (n != (iterator2 = (Map)((Object)iterator2)).size()) {
            return false;
        }
        if ((iterator2 = (Iterable)iterator2.entrySet()) instanceof Collection && ((Collection)((Object)iterator2)).isEmpty()) {
            return bl;
        }
        iterator2 = iterator2.iterator();
        do {
            boolean bl2 = bl;
            if (!iterator2.hasNext()) return bl2;
        } while (this.containsEntry$kotlin_stdlib((Map.Entry)iterator2.next()));
        return false;
    }

    @Override
    public V get(Object entry) {
        if ((entry = this.implFindEntry(entry)) != null) {
            entry = entry.getValue();
            return (V)entry;
        }
        entry = null;
        return (V)entry;
    }

    public abstract Set getEntries();

    public Set<K> getKeys() {
        Set<? extends K> set;
        if (this._keys == null) {
            this._keys = new AbstractSet<K>(this){
                final /* synthetic */ AbstractMap this$0;
                {
                    this.this$0 = abstractMap;
                }

                public boolean contains(Object object) {
                    return this.this$0.containsKey(object);
                }

                public int getSize() {
                    return this.this$0.size();
                }

                public Iterator<K> iterator() {
                    return new Iterator<K>(this.this$0.entrySet().iterator()){
                        final /* synthetic */ Iterator $entryIterator;
                        {
                            this.$entryIterator = iterator2;
                        }

                        public boolean hasNext() {
                            return this.$entryIterator.hasNext();
                        }

                        public K next() {
                            return ((Map.Entry)this.$entryIterator.next()).getKey();
                        }

                        public void remove() {
                            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
                        }
                    };
                }
            };
        }
        if ((set = this._keys) != null) return set;
        Intrinsics.throwNpe();
        return set;
    }

    public int getSize() {
        return this.entrySet().size();
    }

    public Collection<V> getValues() {
        Collection<? extends V> collection;
        if (this._values == null) {
            this._values = new AbstractCollection<V>(this){
                final /* synthetic */ AbstractMap this$0;
                {
                    this.this$0 = abstractMap;
                }

                public boolean contains(Object object) {
                    return this.this$0.containsValue(object);
                }

                public int getSize() {
                    return this.this$0.size();
                }

                public Iterator<V> iterator() {
                    return new Iterator<V>(this.this$0.entrySet().iterator()){
                        final /* synthetic */ Iterator $entryIterator;
                        {
                            this.$entryIterator = iterator2;
                        }

                        public boolean hasNext() {
                            return this.$entryIterator.hasNext();
                        }

                        public V next() {
                            return ((Map.Entry)this.$entryIterator.next()).getValue();
                        }

                        public void remove() {
                            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
                        }
                    };
                }
            };
        }
        if ((collection = this._values) != null) return collection;
        Intrinsics.throwNpe();
        return collection;
    }

    @Override
    public int hashCode() {
        return ((Object)this.entrySet()).hashCode();
    }

    @Override
    public boolean isEmpty() {
        if (this.size() != 0) return false;
        return true;
    }

    @Override
    public V put(K k, V v) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override
    public V remove(Object object) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public String toString() {
        return CollectionsKt.joinToString$default(this.entrySet(), ", ", "{", "}", 0, null, new Function1<Map.Entry<? extends K, ? extends V>, String>(this){
            final /* synthetic */ AbstractMap this$0;
            {
                this.this$0 = abstractMap;
                super(1);
            }

            public final String invoke(Map.Entry<? extends K, ? extends V> entry) {
                Intrinsics.checkParameterIsNotNull(entry, "it");
                return AbstractMap.access$toString(this.this$0, entry);
            }
        }, 24, null);
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010&\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J'\u0010\u0003\u001a\u00020\u00042\u000e\u0010\u0005\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0001H\u0000\u00a2\u0006\u0002\b\bJ\u001d\u0010\t\u001a\u00020\n2\u000e\u0010\u0005\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0006H\u0000\u00a2\u0006\u0002\b\u000bJ\u001d\u0010\f\u001a\u00020\r2\u000e\u0010\u0005\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0006H\u0000\u00a2\u0006\u0002\b\u000e\u00a8\u0006\u000f"}, d2={"Lkotlin/collections/AbstractMap$Companion;", "", "()V", "entryEquals", "", "e", "", "other", "entryEquals$kotlin_stdlib", "entryHashCode", "", "entryHashCode$kotlin_stdlib", "entryToString", "", "entryToString$kotlin_stdlib", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final boolean entryEquals$kotlin_stdlib(Map.Entry<?, ?> entry, Object object) {
            Intrinsics.checkParameterIsNotNull(entry, "e");
            boolean bl = object instanceof Map.Entry;
            boolean bl2 = false;
            if (!bl) {
                return false;
            }
            Object obj = entry.getKey();
            object = (Map.Entry)object;
            bl = bl2;
            if (!Intrinsics.areEqual(obj, object.getKey())) return bl;
            bl = bl2;
            if (!Intrinsics.areEqual(entry.getValue(), object.getValue())) return bl;
            return true;
        }

        public final int entryHashCode$kotlin_stdlib(Map.Entry<?, ?> entry) {
            Intrinsics.checkParameterIsNotNull(entry, "e");
            Object obj = entry.getKey();
            int n = 0;
            int n2 = obj != null ? obj.hashCode() : 0;
            entry = entry.getValue();
            if (entry == null) return n2 ^ n;
            n = ((Object)entry).hashCode();
            return n2 ^ n;
        }

        public final String entryToString$kotlin_stdlib(Map.Entry<?, ?> entry) {
            Intrinsics.checkParameterIsNotNull(entry, "e");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(entry.getKey());
            stringBuilder.append('=');
            stringBuilder.append(entry.getValue());
            return stringBuilder.toString();
        }
    }

}

