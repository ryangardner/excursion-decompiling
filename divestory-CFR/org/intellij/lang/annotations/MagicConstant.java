/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.intellij.lang.annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.SOURCE)
@Target(value={ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.ANNOTATION_TYPE, ElementType.METHOD})
public @interface MagicConstant {
    public long[] flags() default {};

    public Class flagsFromClass() default void.class;

    public long[] intValues() default {};

    public String[] stringValues() default {};

    public Class valuesFromClass() default void.class;
}

