/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Parcelable
 */
package com.google.android.gms.common.internal.safeparcel;

import android.os.Parcelable;
import java.lang.annotation.Annotation;

public interface SafeParcelable
extends Parcelable {
    public static final String NULL = "SAFE_PARCELABLE_NULL_STRING";

    public static @interface Class {
        public String creator();

        public boolean validate() default false;
    }

    public static @interface Constructor {
    }

    public static @interface Field {
        public String defaultValue() default "SAFE_PARCELABLE_NULL_STRING";

        public String defaultValueUnchecked() default "SAFE_PARCELABLE_NULL_STRING";

        public String getter() default "SAFE_PARCELABLE_NULL_STRING";

        public int id();

        public String type() default "SAFE_PARCELABLE_NULL_STRING";
    }

    public static @interface Indicator {
        public String getter() default "SAFE_PARCELABLE_NULL_STRING";
    }

    public static @interface Param {
        public int id();
    }

    public static @interface Reserved {
        public int[] value();
    }

    public static @interface VersionField {
        public String getter() default "SAFE_PARCELABLE_NULL_STRING";

        public int id();

        public String type() default "SAFE_PARCELABLE_NULL_STRING";
    }

}

