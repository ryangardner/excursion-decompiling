/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.gms.common.wrappers;

import android.content.Context;
import com.google.android.gms.common.wrappers.PackageManagerWrapper;

public class Wrappers {
    private static Wrappers zzb = new Wrappers();
    private PackageManagerWrapper zza = null;

    public static PackageManagerWrapper packageManager(Context context) {
        return zzb.zza(context);
    }

    private final PackageManagerWrapper zza(Context object) {
        synchronized (this) {
            PackageManagerWrapper packageManagerWrapper;
            if (this.zza != null) return this.zza;
            if (object.getApplicationContext() != null) {
                object = object.getApplicationContext();
            }
            this.zza = packageManagerWrapper = new PackageManagerWrapper((Context)object);
            return this.zza;
        }
    }
}

