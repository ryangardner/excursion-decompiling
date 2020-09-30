/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzkk;
import com.google.android.gms.internal.drive.zzlo;
import com.google.android.gms.internal.drive.zzlp;

final class zzkj
implements zzlp {
    private static final zzkj zzrp = new zzkj();

    private zzkj() {
    }

    public static zzkj zzcv() {
        return zzrp;
    }

    @Override
    public final boolean zzb(Class<?> class_) {
        return zzkk.class.isAssignableFrom(class_);
    }

    @Override
    public final zzlo zzc(Class<?> object) {
        if (!zzkk.class.isAssignableFrom((Class<?>)object)) {
            if (((String)(object = String.valueOf(((Class)object).getName()))).length() != 0) {
                object = "Unsupported message type: ".concat((String)object);
                throw new IllegalArgumentException((String)object);
            }
            object = new String("Unsupported message type: ");
            throw new IllegalArgumentException((String)object);
        }
        try {
            return (zzlo)zzkk.zzd(((Class)object).asSubclass(zzkk.class)).zza(zzkk.zze.zzrz, null, null);
        }
        catch (Exception exception) {
            object = String.valueOf(((Class)object).getName());
            if (((String)object).length() != 0) {
                object = "Unable to get message info for ".concat((String)object);
                throw new RuntimeException((String)object, exception);
            }
            object = new String("Unable to get message info for ");
            throw new RuntimeException((String)object, exception);
        }
    }
}

