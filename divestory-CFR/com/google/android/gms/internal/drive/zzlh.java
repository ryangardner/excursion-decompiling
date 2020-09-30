/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzlo;
import com.google.android.gms.internal.drive.zzlp;

final class zzlh
implements zzlp {
    private zzlp[] zztt;

    zzlh(zzlp ... arrzzlp) {
        this.zztt = arrzzlp;
    }

    @Override
    public final boolean zzb(Class<?> class_) {
        zzlp[] arrzzlp = this.zztt;
        int n = arrzzlp.length;
        int n2 = 0;
        while (n2 < n) {
            if (arrzzlp[n2].zzb(class_)) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    @Override
    public final zzlo zzc(Class<?> object) {
        for (zzlp zzlp2 : this.zztt) {
            if (!zzlp2.zzb((Class<?>)object)) continue;
            return zzlp2.zzc((Class<?>)object);
        }
        if (((String)(object = String.valueOf(((Class)object).getName()))).length() != 0) {
            object = "No factory is available for message type: ".concat((String)object);
            throw new UnsupportedOperationException((String)object);
        }
        object = new String("No factory is available for message type: ");
        throw new UnsupportedOperationException((String)object);
    }
}

