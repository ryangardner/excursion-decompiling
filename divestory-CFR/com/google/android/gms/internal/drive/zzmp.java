/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzmi;
import java.util.Map;

final class zzmp
implements Comparable<zzmp>,
Map.Entry<K, V> {
    private V value;
    private final /* synthetic */ zzmi zzvk;
    private final K zzvn;

    zzmp(K k, V v) {
        this.zzvk = var1_1;
        this.zzvn = k;
        this.value = v;
    }

    zzmp(Map.Entry<K, V> entry) {
        this((zzmi)var1_1, (Comparable)entry.getKey(), entry.getValue());
    }

    private static boolean equals(Object object, Object object2) {
        if (object != null) return object.equals(object2);
        if (object2 != null) return false;
        return true;
    }

    @Override
    public final /* synthetic */ int compareTo(Object object) {
        object = (zzmp)object;
        return ((Comparable)this.getKey()).compareTo((Comparable)((zzmp)object).getKey());
    }

    @Override
    public final boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Map.Entry)) {
            return false;
        }
        if (!zzmp.equals(this.zzvn, (object = (Map.Entry)object).getKey())) return false;
        if (!zzmp.equals(this.value, object.getValue())) return false;
        return true;
    }

    @Override
    public final /* synthetic */ Object getKey() {
        return this.zzvn;
    }

    @Override
    public final V getValue() {
        return this.value;
    }

    @Override
    public final int hashCode() {
        Object object = this.zzvn;
        int n = 0;
        int n2 = object == null ? 0 : object.hashCode();
        object = this.value;
        if (object == null) {
            return n2 ^ n;
        }
        n = object.hashCode();
        return n2 ^ n;
    }

    @Override
    public final V setValue(V v) {
        zzmi.zza(this.zzvk);
        V v2 = this.value;
        this.value = v;
        return v2;
    }

    public final String toString() {
        String string2 = String.valueOf(this.zzvn);
        String string3 = String.valueOf(this.value);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(string2).length() + 1 + String.valueOf(string3).length());
        stringBuilder.append(string2);
        stringBuilder.append("=");
        stringBuilder.append(string3);
        return stringBuilder.toString();
    }
}

