/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.google.android.gms.common.images;

import android.net.Uri;
import com.google.android.gms.common.internal.Objects;

final class zaa {
    public final Uri zaa;

    public zaa(Uri uri) {
        this.zaa = uri;
    }

    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof zaa) return Objects.equal((Object)((zaa)object).zaa, (Object)this.zaa);
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(new Object[]{this.zaa});
    }
}

