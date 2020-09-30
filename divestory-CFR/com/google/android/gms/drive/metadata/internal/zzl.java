/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcelable
 */
package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import android.os.Parcelable;
import com.google.android.gms.drive.metadata.zzb;
import java.util.ArrayList;
import java.util.Collection;

public class zzl<T extends Parcelable>
extends zzb<T> {
    public zzl(String string2, Collection<String> collection, Collection<String> collection2, int n) {
        super(string2, collection, collection2, n);
    }

    @Override
    protected final /* synthetic */ void zza(Bundle bundle, Object arrayList) {
        arrayList = arrayList;
        String string2 = this.getName();
        arrayList = arrayList instanceof ArrayList ? (ArrayList)arrayList : new ArrayList(arrayList);
        bundle.putParcelableArrayList(string2, arrayList);
    }

    @Override
    protected /* synthetic */ Object zzb(Bundle bundle) {
        return this.zzc(bundle);
    }

    protected Collection<T> zzc(Bundle bundle) {
        return bundle.getParcelableArrayList(this.getName());
    }
}

