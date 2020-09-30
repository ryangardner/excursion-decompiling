/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;

public final class AtomicLongMap<K>
implements Serializable {
    @MonotonicNonNullDecl
    private transient Map<K, Long> asMap;
    private final ConcurrentHashMap<K, AtomicLong> map;

    private AtomicLongMap(ConcurrentHashMap<K, AtomicLong> concurrentHashMap) {
        this.map = Preconditions.checkNotNull(concurrentHashMap);
    }

    public static <K> AtomicLongMap<K> create() {
        return new AtomicLongMap(new ConcurrentHashMap());
    }

    public static <K> AtomicLongMap<K> create(Map<? extends K, ? extends Long> map) {
        AtomicLongMap<? extends K> atomicLongMap = AtomicLongMap.create();
        atomicLongMap.putAll(map);
        return atomicLongMap;
    }

    private Map<K, Long> createAsMap() {
        return Collections.unmodifiableMap(Maps.transformValues(this.map, new Function<AtomicLong, Long>(){

            @Override
            public Long apply(AtomicLong atomicLong) {
                return atomicLong.get();
            }
        }));
    }

    public long addAndGet(K k, long l) {
        long l2;
        block0 : do {
            long l3;
            AtomicLong atomicLong;
            AtomicLong atomicLong2 = atomicLong = this.map.get(k);
            if (atomicLong == null) {
                atomicLong2 = atomicLong = this.map.putIfAbsent(k, new AtomicLong(l));
                if (atomicLong == null) {
                    return l;
                }
            }
            do {
                if ((l3 = atomicLong2.get()) != 0L) continue;
                if (!this.map.replace(k, atomicLong2, new AtomicLong(l))) continue block0;
                return l;
            } while (!atomicLong2.compareAndSet(l3, l2 = l3 + l));
            break;
        } while (true);
        return l2;
    }

    public Map<K, Long> asMap() {
        Map<K, Long> map;
        Map<K, Long> map2 = map = this.asMap;
        if (map != null) return map2;
        this.asMap = map2 = this.createAsMap();
        return map2;
    }

    public void clear() {
        this.map.clear();
    }

    public boolean containsKey(Object object) {
        return this.map.containsKey(object);
    }

    public long decrementAndGet(K k) {
        return this.addAndGet(k, -1L);
    }

    public long get(K object) {
        if ((object = this.map.get(object)) != null) return ((AtomicLong)object).get();
        return 0L;
    }

    public long getAndAdd(K k, long l) {
        long l2;
        block0 : do {
            AtomicLong atomicLong;
            AtomicLong atomicLong2 = atomicLong = this.map.get(k);
            if (atomicLong == null) {
                atomicLong2 = atomicLong = this.map.putIfAbsent(k, new AtomicLong(l));
                if (atomicLong == null) {
                    return 0L;
                }
            }
            do {
                if ((l2 = atomicLong2.get()) != 0L) continue;
                if (!this.map.replace(k, atomicLong2, new AtomicLong(l))) continue block0;
                return 0L;
            } while (!atomicLong2.compareAndSet(l2, l2 + l));
            break;
        } while (true);
        return l2;
    }

    public long getAndDecrement(K k) {
        return this.getAndAdd(k, -1L);
    }

    public long getAndIncrement(K k) {
        return this.getAndAdd(k, 1L);
    }

    public long incrementAndGet(K k) {
        return this.addAndGet(k, 1L);
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public long put(K k, long l) {
        long l2;
        block0 : do {
            AtomicLong atomicLong;
            AtomicLong atomicLong2 = atomicLong = this.map.get(k);
            if (atomicLong == null) {
                atomicLong2 = atomicLong = this.map.putIfAbsent(k, new AtomicLong(l));
                if (atomicLong == null) {
                    return 0L;
                }
            }
            do {
                if ((l2 = atomicLong2.get()) != 0L) continue;
                if (!this.map.replace(k, atomicLong2, new AtomicLong(l))) continue block0;
                return 0L;
            } while (!atomicLong2.compareAndSet(l2, l));
            break;
        } while (true);
        return l2;
    }

    public void putAll(Map<? extends K, ? extends Long> object) {
        Iterator<Map.Entry<K, Long>> iterator2 = object.entrySet().iterator();
        while (iterator2.hasNext()) {
            object = iterator2.next();
            this.put(object.getKey(), (Long)object.getValue());
        }
    }

    long putIfAbsent(K k, long l) {
        AtomicLong atomicLong;
        do {
            long l2;
            AtomicLong atomicLong2;
            atomicLong = atomicLong2 = this.map.get(k);
            if (atomicLong2 == null) {
                atomicLong = atomicLong2 = this.map.putIfAbsent(k, new AtomicLong(l));
                if (atomicLong2 == null) {
                    return 0L;
                }
            }
            if ((l2 = atomicLong.get()) != 0L) return l2;
        } while (!this.map.replace(k, atomicLong, new AtomicLong(l)));
        return 0L;
    }

    public long remove(K k) {
        long l;
        AtomicLong atomicLong = this.map.get(k);
        if (atomicLong == null) {
            return 0L;
        }
        while ((l = atomicLong.get()) != 0L && !atomicLong.compareAndSet(l, 0L)) {
        }
        this.map.remove(k, atomicLong);
        return l;
    }

    boolean remove(K k, long l) {
        AtomicLong atomicLong = this.map.get(k);
        if (atomicLong == null) {
            return false;
        }
        long l2 = atomicLong.get();
        if (l2 != l) {
            return false;
        }
        if (l2 != 0L) {
            if (!atomicLong.compareAndSet(l2, 0L)) return false;
        }
        this.map.remove(k, atomicLong);
        return true;
    }

    public void removeAllZeros() {
        Iterator<Map.Entry<K, AtomicLong>> iterator2 = this.map.entrySet().iterator();
        while (iterator2.hasNext()) {
            AtomicLong atomicLong = iterator2.next().getValue();
            if (atomicLong == null || atomicLong.get() != 0L) continue;
            iterator2.remove();
        }
    }

    public boolean removeIfZero(K k) {
        return this.remove(k, 0L);
    }

    boolean replace(K object, long l, long l2) {
        boolean bl = false;
        boolean bl2 = false;
        if (l == 0L) {
            if (this.putIfAbsent(object, l2) != 0L) return bl2;
            return true;
        }
        if ((object = this.map.get(object)) != null) return ((AtomicLong)object).compareAndSet(l, l2);
        return bl;
    }

    public int size() {
        return this.map.size();
    }

    public long sum() {
        Iterator<AtomicLong> iterator2 = this.map.values().iterator();
        long l = 0L;
        while (iterator2.hasNext()) {
            l += iterator2.next().get();
        }
        return l;
    }

    public String toString() {
        return this.map.toString();
    }

}

