package androidx.versionedparcelable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE})
public @interface VersionedParcelize {
   boolean allowSerialization() default false;

   int[] deprecatedIds() default {};

   Class factory() default void.class;

   boolean ignoreParcelables() default false;

   boolean isCustom() default false;

   String jetifyAs() default "";
}
