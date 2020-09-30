/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzkd;
import com.google.android.gms.internal.drive.zzmj;
import com.google.android.gms.internal.drive.zzml;
import com.google.android.gms.internal.drive.zzmm;
import com.google.android.gms.internal.drive.zzmp;
import com.google.android.gms.internal.drive.zzmr;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

class zzmi<K extends Comparable<K>, V>
extends AbstractMap<K, V> {
    private boolean zzot;
    private final int zzvd;
    private List<zzmp> zzve;
    private Map<K, V> zzvf;
    private volatile zzmr zzvg;
    private Map<K, V> zzvh;
    private volatile zzml zzvi;

    private zzmi(int n) {
        this.zzvd = n;
        this.zzve = Collections.emptyList();
        this.zzvf = Collections.emptyMap();
        this.zzvh = Collections.emptyMap();
    }

    /* synthetic */ zzmi(int n, zzmj zzmj2) {
        this(n);
    }

    private final int zza(K k) {
        int n;
        int n2 = this.zzve.size() - 1;
        if (n2 >= 0) {
            n = k.compareTo((Comparable)((Comparable)this.zzve.get(n2).getKey()));
            if (n > 0) {
                return -(n2 + 2);
            }
            if (n == 0) {
                return n2;
            }
        }
        n = 0;
        while (n <= n2) {
            int n3 = (n + n2) / 2;
            int n4 = k.compareTo((Comparable)((Comparable)this.zzve.get(n3).getKey()));
            if (n4 < 0) {
                n2 = n3 - 1;
                continue;
            }
            if (n4 <= 0) return n3;
            n = n3 + 1;
        }
        return -(n + 1);
    }

    static /* synthetic */ Object zza(zzmi zzmi2, int n) {
        return zzmi2.zzax(n);
    }

    static /* synthetic */ void zza(zzmi zzmi2) {
        zzmi2.zzeu();
    }

    static <FieldDescriptorType extends zzkd<FieldDescriptorType>> zzmi<FieldDescriptorType, Object> zzav(int n) {
        return new zzmj(n);
    }

    private final V zzax(int n) {
        this.zzeu();
        Object v = this.zzve.remove(n).getValue();
        if (this.zzvf.isEmpty()) return v;
        Iterator<Map.Entry<K, V>> iterator2 = this.zzev().entrySet().iterator();
        this.zzve.add(new zzmp(this, iterator2.next()));
        iterator2.remove();
        return v;
    }

    static /* synthetic */ List zzb(zzmi zzmi2) {
        return zzmi2.zzve;
    }

    static /* synthetic */ Map zzc(zzmi zzmi2) {
        return zzmi2.zzvf;
    }

    static /* synthetic */ Map zzd(zzmi zzmi2) {
        return zzmi2.zzvh;
    }

    private final void zzeu() {
        if (this.zzot) throw new UnsupportedOperationException();
    }

    private final SortedMap<K, V> zzev() {
        this.zzeu();
        if (!this.zzvf.isEmpty()) return (SortedMap)this.zzvf;
        if (this.zzvf instanceof TreeMap) return (SortedMap)this.zzvf;
        TreeMap treeMap = new TreeMap();
        this.zzvf = treeMap;
        this.zzvh = treeMap.descendingMap();
        return (SortedMap)this.zzvf;
    }

    @Override
    public void clear() {
        this.zzeu();
        if (!this.zzve.isEmpty()) {
            this.zzve.clear();
        }
        if (this.zzvf.isEmpty()) return;
        this.zzvf.clear();
    }

    @Override
    public boolean containsKey(Object object) {
        if (this.zza(object = (Comparable)object) >= 0) return true;
        if (!this.zzvf.containsKey(object)) return false;
        return true;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        if (this.zzvg != null) return this.zzvg;
        this.zzvg = new zzmr(this, null);
        return this.zzvg;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof zzmi)) {
            return super.equals(object);
        }
        object = (zzmi)object;
        int n = this.size();
        if (n != ((zzmi)object).size()) {
            return false;
        }
        int n2 = this.zzer();
        if (n2 != ((zzmi)object).zzer()) {
            return this.entrySet().equals(((zzmi)object).entrySet());
        }
        int n3 = 0;
        do {
            if (n3 >= n2) {
                if (n2 == n) return true;
                return this.zzvf.equals(((zzmi)object).zzvf);
            }
            if (!this.zzaw(n3).equals(((zzmi)object).zzaw(n3))) {
                return false;
            }
            ++n3;
        } while (true);
    }

    @Override
    public V get(Object object) {
        int n = this.zza(object = (Comparable)object);
        if (n < 0) return this.zzvf.get(object);
        return this.zzve.get(n).getValue();
    }

    @Override
    public int hashCode() {
        int n = this.zzer();
        int n2 = 0;
        int n3 = 0;
        do {
            if (n2 >= n) {
                n2 = n3;
                if (this.zzvf.size() <= 0) return n2;
                return n3 + this.zzvf.hashCode();
            }
            n3 += this.zzve.get(n2).hashCode();
            ++n2;
        } while (true);
    }

    public final boolean isImmutable() {
        return this.zzot;
    }

    @Override
    public /* synthetic */ Object put(Object object, Object object2) {
        return this.zza((Comparable)object, object2);
    }

    @Override
    public V remove(Object object) {
        this.zzeu();
        object = (Comparable)object;
        int n = this.zza(object);
        if (n >= 0) {
            return this.zzax(n);
        }
        if (!this.zzvf.isEmpty()) return this.zzvf.remove(object);
        return null;
    }

    @Override
    public int size() {
        return this.zzve.size() + this.zzvf.size();
    }

    public final V zza(K k, V v) {
        int n;
        this.zzeu();
        int n2 = this.zza(k);
        if (n2 >= 0) {
            return this.zzve.get(n2).setValue(v);
        }
        this.zzeu();
        if (this.zzve.isEmpty() && !(this.zzve instanceof ArrayList)) {
            this.zzve = new ArrayList<zzmp>(this.zzvd);
        }
        if ((n = -(n2 + 1)) >= this.zzvd) {
            return this.zzev().put(k, v);
        }
        int n3 = this.zzve.size();
        if (n3 == (n2 = this.zzvd)) {
            zzmp zzmp2 = this.zzve.remove(n2 - 1);
            this.zzev().put((Comparable)zzmp2.getKey(), zzmp2.getValue());
        }
        this.zzve.add(n, new zzmp(this, k, v));
        return null;
    }

    public final Map.Entry<K, V> zzaw(int n) {
        return this.zzve.get(n);
    }

    public void zzbp() {
        if (this.zzot) return;
        Map map = this.zzvf.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(this.zzvf);
        this.zzvf = map;
        map = this.zzvh.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(this.zzvh);
        this.zzvh = map;
        this.zzot = true;
    }

    public final int zzer() {
        return this.zzve.size();
    }

    public final Iterable<Map.Entry<K, V>> zzes() {
        if (!this.zzvf.isEmpty()) return this.zzvf.entrySet();
        return zzmm.zzex();
    }

    final Set<Map.Entry<K, V>> zzet() {
        if (this.zzvi != null) return this.zzvi;
        this.zzvi = new zzml(this, null);
        return this.zzvi;
    }
}

