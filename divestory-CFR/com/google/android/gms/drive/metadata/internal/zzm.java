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
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.drive.metadata.zza;
import java.util.Collection;

public abstract class zzm<T extends ReflectedParcelable>
extends zza<T> {
    public zzm(String string2, Collection<String> collection, Collection<String> collection2, int n) {
        super(string2, collection, collection2, n);
    }

    @Override
    protected final /* synthetic */ void zza(Bundle bundle, Object object) {
        object = (ReflectedParcelable)object;
        bundle.putParcelable(this.getName(), (Parcelable)object);
    }

    @Override
    protected final /* synthetic */ Object zzb(Bundle bundle) {
        return (ReflectedParcelable)bundle.getParcelable(this.getName());
    }
}

