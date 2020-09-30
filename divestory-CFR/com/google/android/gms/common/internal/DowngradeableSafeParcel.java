/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common.internal;

import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

public abstract class DowngradeableSafeParcel
extends AbstractSafeParcelable
implements ReflectedParcelable {
    private static final Object zza = new Object();
    private static ClassLoader zzb = null;
    private static Integer zzc = null;
    private boolean zzd = false;

    protected static boolean canUnparcelSafely(String string2) {
        DowngradeableSafeParcel.zza();
        return true;
    }

    protected static Integer getUnparcelClientVersion() {
        Object object = zza;
        synchronized (object) {
            return null;
        }
    }

    private static ClassLoader zza() {
        Object object = zza;
        synchronized (object) {
            return null;
        }
    }

    protected abstract boolean prepareForClientVersion(int var1);

    public void setShouldDowngrade(boolean bl) {
        this.zzd = bl;
    }

    protected boolean shouldDowngrade() {
        return this.zzd;
    }
}

