/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzll;
import com.google.android.gms.internal.drive.zzlm;
import java.lang.reflect.Constructor;

final class zzln {
    private static final zzll zztz = zzln.zzeb();
    private static final zzll zzua = new zzlm();

    static zzll zzdz() {
        return zztz;
    }

    static zzll zzea() {
        return zzua;
    }

    private static zzll zzeb() {
        try {
            return (zzll)Class.forName("com.google.protobuf.MapFieldSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (Exception exception) {
            return null;
        }
    }
}

