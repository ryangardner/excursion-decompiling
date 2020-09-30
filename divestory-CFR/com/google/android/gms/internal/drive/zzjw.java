/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzjx;
import java.lang.reflect.Method;

final class zzjw {
    private static final Class<?> zzok = zzjw.zzce();

    private static Class<?> zzce() {
        try {
            return Class.forName("com.google.protobuf.ExtensionRegistry");
        }
        catch (ClassNotFoundException classNotFoundException) {
            return null;
        }
    }

    public static zzjx zzcf() {
        if (zzok == null) return zzjx.zzoo;
        try {
            return zzjw.zzn("getEmptyRegistry");
        }
        catch (Exception exception) {
            return zzjx.zzoo;
        }
    }

    static zzjx zzcg() {
        zzjx zzjx2;
        block4 : {
            if (zzok != null) {
                try {
                    zzjx2 = zzjw.zzn("loadGeneratedRegistry");
                    break block4;
                }
                catch (Exception exception) {}
            }
            zzjx2 = null;
        }
        zzjx zzjx3 = zzjx2;
        if (zzjx2 == null) {
            zzjx3 = zzjx.zzcg();
        }
        zzjx2 = zzjx3;
        if (zzjx3 != null) return zzjx2;
        return zzjw.zzcf();
    }

    private static final zzjx zzn(String string2) throws Exception {
        return (zzjx)zzok.getDeclaredMethod(string2, new Class[0]).invoke(null, new Object[0]);
    }
}

