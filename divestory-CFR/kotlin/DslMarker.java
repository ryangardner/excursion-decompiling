/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.MustBeDocumented;
import kotlin.annotation.Retention;
import kotlin.annotation.Target;

@Documented
@java.lang.annotation.Retention(value=RetentionPolicy.CLASS)
@java.lang.annotation.Target(value={ElementType.ANNOTATION_TYPE})
@Metadata(bv={1, 0, 3}, d1={"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000\u00a8\u0006\u0002"}, d2={"Lkotlin/DslMarker;", "", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
@MustBeDocumented
@Retention(value=AnnotationRetention.BINARY)
@Target(allowedTargets={AnnotationTarget.ANNOTATION_CLASS})
public @interface DslMarker {
}

