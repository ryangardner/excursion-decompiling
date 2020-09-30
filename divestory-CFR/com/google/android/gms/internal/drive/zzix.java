/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

final class zzix {
    private static final Class<?> zzni = zzix.zzj("libcore.io.Memory");
    private static final boolean zznj;

    static {
        boolean bl = zzix.zzj("org.robolectric.Robolectric") != null;
        zznj = bl;
    }

    static boolean zzbr() {
        if (zzni == null) return false;
        if (zznj) return false;
        return true;
    }

    static Class<?> zzbs() {
        return zzni;
    }

    private static <T> Class<T> zzj(String object) {
        try {
            return Class.forName((String)object);
        }
        catch (Throwable throwable) {
            return null;
        }
    }
}

