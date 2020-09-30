/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.drive.metadata;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public abstract class zza<T>
implements MetadataField<T> {
    private final String fieldName;
    private final Set<String> zziw;
    private final Set<String> zzix;
    private final int zziy;

    protected zza(String string2, int n) {
        this.fieldName = Preconditions.checkNotNull(string2, "fieldName");
        this.zziw = Collections.singleton(string2);
        this.zzix = Collections.emptySet();
        this.zziy = n;
    }

    protected zza(String string2, Collection<String> collection, Collection<String> collection2, int n) {
        this.fieldName = Preconditions.checkNotNull(string2, "fieldName");
        this.zziw = Collections.unmodifiableSet(new HashSet<String>(collection));
        this.zzix = Collections.unmodifiableSet(new HashSet<String>(collection2));
        this.zziy = n;
    }

    @Override
    public final String getName() {
        return this.fieldName;
    }

    public String toString() {
        return this.fieldName;
    }

    @Override
    public final T zza(Bundle bundle) {
        Preconditions.checkNotNull(bundle, "bundle");
        if (bundle.get(this.fieldName) == null) return null;
        return this.zzb(bundle);
    }

    @Override
    public final T zza(DataHolder dataHolder, int n, int n2) {
        if (!this.zzb(dataHolder, n, n2)) return null;
        return this.zzc(dataHolder, n, n2);
    }

    protected abstract void zza(Bundle var1, T var2);

    @Override
    public final void zza(DataHolder dataHolder, MetadataBundle metadataBundle, int n, int n2) {
        Preconditions.checkNotNull(dataHolder, "dataHolder");
        Preconditions.checkNotNull(metadataBundle, "bundle");
        if (!this.zzb(dataHolder, n, n2)) return;
        metadataBundle.zzb(this, this.zzc(dataHolder, n, n2));
    }

    @Override
    public final void zza(T t, Bundle bundle) {
        Preconditions.checkNotNull(bundle, "bundle");
        if (t == null) {
            bundle.putString(this.fieldName, null);
            return;
        }
        this.zza(bundle, t);
    }

    public final Collection<String> zzaz() {
        return this.zziw;
    }

    protected abstract T zzb(Bundle var1);

    protected boolean zzb(DataHolder dataHolder, int n, int n2) {
        String string2;
        Iterator<String> iterator2 = this.zziw.iterator();
        do {
            if (!iterator2.hasNext()) return true;
            string2 = iterator2.next();
            if (dataHolder.isClosed()) return false;
            if (!dataHolder.hasColumn(string2)) return false;
        } while (!dataHolder.hasNull(string2, n, n2));
        return false;
    }

    protected abstract T zzc(DataHolder var1, int var2, int var3);
}

