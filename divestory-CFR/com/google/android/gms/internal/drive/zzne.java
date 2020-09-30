/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import java.lang.reflect.Field;
import java.security.PrivilegedExceptionAction;
import sun.misc.Unsafe;

final class zzne
implements PrivilegedExceptionAction<Unsafe> {
    zzne() {
    }

    @Override
    public final /* synthetic */ Object run() throws Exception {
        Field[] arrfield = Unsafe.class.getDeclaredFields();
        int n = arrfield.length;
        int n2 = 0;
        while (n2 < n) {
            Object object = arrfield[n2];
            ((Field)object).setAccessible(true);
            object = ((Field)object).get(null);
            if (Unsafe.class.isInstance(object)) {
                return (Unsafe)Unsafe.class.cast(object);
            }
            ++n2;
        }
        return null;
    }
}

