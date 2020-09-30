/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzkm;
import com.google.android.gms.internal.drive.zzkn;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public final class zzlk<K, V>
extends LinkedHashMap<K, V> {
    private static final zzlk zzty;
    private boolean zznh = true;

    static {
        zzlk<K, V> zzlk2;
        zzty = zzlk2 = new zzlk<K, V>();
        zzlk2.zznh = false;
    }

    private zzlk() {
    }

    private zzlk(Map<K, V> map) {
        super(map);
    }

    public static <K, V> zzlk<K, V> zzdw() {
        return zzty;
    }

    private final void zzdy() {
        if (!this.zznh) throw new UnsupportedOperationException();
    }

    private static int zzg(Object object) {
        if (object instanceof byte[]) {
            return zzkm.hashCode((byte[])object);
        }
        if (object instanceof zzkn) throw new UnsupportedOperationException();
        return object.hashCode();
    }

    @Override
    public final void clear() {
        this.zzdy();
        super.clear();
    }

    @Override
    public final Set<Map.Entry<K, V>> entrySet() {
        if (!this.isEmpty()) return super.entrySet();
        return Collections.emptySet();
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public final boolean equals(Object var1_1) {
        block5 : {
            block6 : {
                if (var1_1 instanceof Map == false) return false;
                if (this == (var1_1 = (Map)var1_1)) break block6;
                if (this.size() == var1_1.size()) {
                    var3_3 = this.entrySet().iterator();
                } else lbl-1000: // 2 sources:
                {
                    do {
                        var2_2 = false;
                        break block5;
                        break;
                    } while (true);
                }
                while (var3_3.hasNext()) {
                    var4_5 = var3_3.next();
                    if (var1_1.containsKey(var4_5.getKey())) {
                        var5_6 = var4_5.getValue();
                        var4_4 = var1_1.get(var4_5.getKey());
                        var6_7 = var5_6 instanceof byte[] != false && var4_4 instanceof byte[] != false ? Arrays.equals((byte[])var5_6, (byte[])var4_4) : var5_6.equals(var4_4);
                        if (var6_7) continue;
                        ** continue;
                    }
lbl17: // 3 sources:
                    ** GOTO lbl-1000
                }
            }
            var2_2 = true;
        }
        if (var2_2 == false) return false;
        return true;
    }

    @Override
    public final int hashCode() {
        Iterator iterator2 = this.entrySet().iterator();
        int n = 0;
        while (iterator2.hasNext()) {
            Map.Entry entry = iterator2.next();
            int n2 = zzlk.zzg(entry.getKey());
            n += zzlk.zzg(entry.getValue()) ^ n2;
        }
        return n;
    }

    public final boolean isMutable() {
        return this.zznh;
    }

    @Override
    public final V put(K k, V v) {
        this.zzdy();
        zzkm.checkNotNull(k);
        zzkm.checkNotNull(v);
        return super.put(k, v);
    }

    @Override
    public final void putAll(Map<? extends K, ? extends V> map) {
        this.zzdy();
        Iterator<K> iterator2 = map.keySet().iterator();
        do {
            if (!iterator2.hasNext()) {
                super.putAll(map);
                return;
            }
            K k = iterator2.next();
            zzkm.checkNotNull(k);
            zzkm.checkNotNull(map.get(k));
        } while (true);
    }

    @Override
    public final V remove(Object object) {
        this.zzdy();
        return super.remove(object);
    }

    public final void zza(zzlk<K, V> zzlk2) {
        this.zzdy();
        if (zzlk2.isEmpty()) return;
        this.putAll(zzlk2);
    }

    public final void zzbp() {
        this.zznh = false;
    }

    public final zzlk<K, V> zzdx() {
        if (!this.isEmpty()) return new zzlk<K, V>(this);
        return new zzlk<K, V>();
    }
}

