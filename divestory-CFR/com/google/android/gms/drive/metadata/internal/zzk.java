/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.drive.metadata.internal;

import com.google.android.gms.common.internal.Preconditions;
import java.util.Locale;

public final class zzk {
    private String zzji;

    private zzk(String string2) {
        this.zzji = string2.toLowerCase(Locale.US);
    }

    public static zzk zzg(String string2) {
        boolean bl = string2 == null || !string2.isEmpty();
        Preconditions.checkArgument(bl);
        if (string2 != null) return new zzk(string2);
        return null;
    }

    public final boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null) return false;
        if (object.getClass() != this.getClass()) {
            return false;
        }
        object = (zzk)object;
        return this.zzji.equals(((zzk)object).zzji);
    }

    public final int hashCode() {
        return this.zzji.hashCode();
    }

    public final boolean isFolder() {
        return this.zzji.equals("application/vnd.google-apps.folder");
    }

    public final String toString() {
        return this.zzji;
    }

    public final boolean zzbh() {
        return this.zzji.startsWith("application/vnd.google-apps");
    }
}

