/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(value=RetentionPolicy.CLASS)
@Target(value={ElementType.TYPE, ElementType.METHOD})
public @interface GwtCompatible {
    public boolean emulated() default false;

    public boolean serializable() default false;
}

