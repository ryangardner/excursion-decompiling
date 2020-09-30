/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzlj;
import com.google.android.gms.internal.drive.zzlk;
import com.google.android.gms.internal.drive.zzll;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

final class zzlm
implements zzll {
    zzlm() {
    }

    @Override
    public final int zzb(int n, Object object, Object object2) {
        if (((HashMap)(object = (zzlk)object)).isEmpty()) {
            return 0;
        }
        if (!(object = ((zzlk)object).entrySet().iterator()).hasNext()) {
            return 0;
        }
        object = object.next();
        object.getKey();
        object.getValue();
        throw new NoSuchMethodError();
    }

    @Override
    public final Object zzb(Object zzlk2, Object object) {
        zzlk zzlk3 = zzlk2;
        object = (zzlk)object;
        zzlk2 = zzlk3;
        if (((HashMap)object).isEmpty()) return zzlk2;
        zzlk2 = zzlk3;
        if (!zzlk3.isMutable()) {
            zzlk2 = zzlk3.zzdx();
        }
        zzlk2.zza(object);
        return zzlk2;
    }

    @Override
    public final Map<?, ?> zzh(Object object) {
        return (zzlk)object;
    }

    @Override
    public final Map<?, ?> zzi(Object object) {
        return (zzlk)object;
    }

    @Override
    public final boolean zzj(Object object) {
        if (((zzlk)object).isMutable()) return false;
        return true;
    }

    @Override
    public final Object zzk(Object object) {
        ((zzlk)object).zzbp();
        return object;
    }

    @Override
    public final Object zzl(Object object) {
        return zzlk.zzdw().zzdx();
    }

    @Override
    public final zzlj<?, ?> zzm(Object object) {
        throw new NoSuchMethodError();
    }
}

