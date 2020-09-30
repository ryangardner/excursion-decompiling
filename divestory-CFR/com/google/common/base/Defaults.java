/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.base;

import com.google.common.base.Preconditions;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Defaults {
    private static final Double DOUBLE_DEFAULT = 0.0;
    private static final Float FLOAT_DEFAULT = Float.valueOf(0.0f);

    private Defaults() {
    }

    @NullableDecl
    public static <T> T defaultValue(Class<T> class_) {
        Preconditions.checkNotNull(class_);
        if (class_ == Boolean.TYPE) {
            return (T)Boolean.FALSE;
        }
        if (class_ == Character.TYPE) {
            return (T)Character.valueOf('\u0000');
        }
        if (class_ == Byte.TYPE) {
            return (byte)0;
        }
        if (class_ == Short.TYPE) {
            return (short)0;
        }
        if (class_ == Integer.TYPE) {
            return 0;
        }
        if (class_ == Long.TYPE) {
            return 0L;
        }
        if (class_ == Float.TYPE) {
            return (T)FLOAT_DEFAULT;
        }
        if (class_ != Double.TYPE) return null;
        return (T)DOUBLE_DEFAULT;
    }
}

