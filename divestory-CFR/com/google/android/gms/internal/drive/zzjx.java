/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzjw;
import com.google.android.gms.internal.drive.zzki;
import com.google.android.gms.internal.drive.zzkk;
import com.google.android.gms.internal.drive.zzlq;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class zzjx {
    private static volatile boolean zzol = false;
    private static final Class<?> zzom = zzjx.zzch();
    private static volatile zzjx zzon;
    static final zzjx zzoo;
    private final Map<zza, zzkk.zzd<?, ?>> zzop;

    static {
        zzoo = new zzjx(true);
    }

    zzjx() {
        this.zzop = new HashMap();
    }

    private zzjx(boolean bl) {
        this.zzop = Collections.emptyMap();
    }

    static zzjx zzcg() {
        return zzki.zza(zzjx.class);
    }

    private static Class<?> zzch() {
        try {
            return Class.forName("com.google.protobuf.Extension");
        }
        catch (ClassNotFoundException classNotFoundException) {
            return null;
        }
    }

    public static zzjx zzci() {
        return zzjw.zzcf();
    }

    public static zzjx zzcj() {
        zzjx zzjx2;
        zzjx zzjx3 = zzjx2 = zzon;
        if (zzjx2 != null) return zzjx3;
        synchronized (zzjx.class) {
            zzjx3 = zzjx2 = zzon;
            if (zzjx2 != null) return zzjx3;
            zzon = zzjx3 = zzjw.zzcg();
            return zzjx3;
        }
    }

    public final <ContainingType extends zzlq> zzkk.zzd<ContainingType, ?> zza(ContainingType ContainingType, int n) {
        return this.zzop.get(new zza(ContainingType, n));
    }

    static final class zza {
        private final int number;
        private final Object object;

        zza(Object object, int n) {
            this.object = object;
            this.number = n;
        }

        public final boolean equals(Object object) {
            if (!(object instanceof zza)) {
                return false;
            }
            object = (zza)object;
            if (this.object != ((zza)object).object) return false;
            if (this.number != ((zza)object).number) return false;
            return true;
        }

        public final int hashCode() {
            return System.identityHashCode(this.object) * 65535 + this.number;
        }
    }

}

