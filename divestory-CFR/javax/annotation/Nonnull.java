/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.annotation.meta.TypeQualifier;
import javax.annotation.meta.TypeQualifierValidator;
import javax.annotation.meta.When;

@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@TypeQualifier
public @interface Nonnull {
    public When when() default When.ALWAYS;

    public static class Checker
    implements TypeQualifierValidator<Nonnull> {
        @Override
        public When forConstantValue(Nonnull nonnull, Object object) {
            if (object != null) return When.ALWAYS;
            return When.NEVER;
        }
    }

}

