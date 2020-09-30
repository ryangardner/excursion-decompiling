/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzjy;
import com.google.android.gms.internal.drive.zzjz;
import java.lang.reflect.Constructor;

final class zzka {
    private static final zzjy<?> zzoq = new zzjz();
    private static final zzjy<?> zzor = zzka.zzck();

    private static zzjy<?> zzck() {
        try {
            return (zzjy)Class.forName("com.google.protobuf.ExtensionSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (Exception exception) {
            return null;
        }
    }

    static zzjy<?> zzcl() {
        return zzoq;
    }

    static zzjy<?> zzcm() {
        zzjy<?> zzjy2 = zzor;
        if (zzjy2 == null) throw new IllegalStateException("Protobuf runtime is not correctly loaded.");
        return zzjy2;
    }
}

