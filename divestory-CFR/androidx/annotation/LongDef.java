/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.SOURCE)
@Target(value={ElementType.ANNOTATION_TYPE})
public @interface LongDef {
    public boolean flag() default false;

    public boolean open() default false;

    public long[] value() default {};
}

