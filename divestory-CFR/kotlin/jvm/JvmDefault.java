/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.jvm;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import kotlin.Metadata;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@java.lang.annotation.Target(value={ElementType.METHOD})
@Metadata(bv={1, 0, 3}, d1={"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000\u00f8\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u0091(0\u0001\u00a8\u0006\u0002"}, d2={"Lkotlin/jvm/JvmDefault;", "", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
@Target(allowedTargets={AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY})
public @interface JvmDefault {
}

