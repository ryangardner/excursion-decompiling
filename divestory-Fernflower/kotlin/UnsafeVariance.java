package kotlin;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.MustBeDocumented;

@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({})
@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"},
   d2 = {"Lkotlin/UnsafeVariance;", "", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
@MustBeDocumented
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@kotlin.annotation.Target(
   allowedTargets = {AnnotationTarget.TYPE}
)
public @interface UnsafeVariance {
}
