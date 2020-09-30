/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.errorprone.annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(value={ElementType.CONSTRUCTOR, ElementType.METHOD})
public @interface RestrictedApi {
    public String allowedOnPath() default "";

    public String checkerName() default "RestrictedApi";

    public String explanation();

    public String link();

    public Class<? extends Annotation>[] whitelistAnnotations() default {};

    public Class<? extends Annotation>[] whitelistWithWarningAnnotations() default {};
}

