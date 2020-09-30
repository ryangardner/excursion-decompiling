/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.versionedparcelable;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.SOURCE)
@Target(value={ElementType.FIELD})
public @interface ParcelField {
    public String defaultValue() default "";

    public int value();
}

