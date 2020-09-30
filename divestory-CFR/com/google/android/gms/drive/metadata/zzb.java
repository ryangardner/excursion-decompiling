/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.drive.metadata;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.metadata.zza;
import java.util.Collection;

public abstract class zzb<T>
extends zza<Collection<T>> {
    protected zzb(String string2, Collection<String> collection, Collection<String> collection2, int n) {
        super(string2, collection, collection2, n);
    }

    @Override
    protected /* synthetic */ Object zzc(DataHolder dataHolder, int n, int n2) {
        return this.zzd(dataHolder, n, n2);
    }

    protected Collection<T> zzd(DataHolder dataHolder, int n, int n2) {
        throw new UnsupportedOperationException("Cannot read collections from a dataHolder.");
    }
}

