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
@TypeQualifier(applicableTo=Number.class)
public @interface Nonnegative {
    public When when() default When.ALWAYS;

    public static class Checker
    implements TypeQualifierValidator<Nonnegative> {
        @Override
        public When forConstantValue(Nonnegative object, Object object2) {
            if (!(object2 instanceof Number)) {
                return When.NEVER;
            }
            object = (Number)object2;
            boolean bl = object instanceof Long;
            boolean bl2 = true;
            if (!(!bl ? (!(object instanceof Double) ? (!(object instanceof Float) ? ((Number)object).intValue() < 0 : ((Number)object).floatValue() < 0.0f) : ((Number)object).doubleValue() < 0.0) : ((Number)object).longValue() < 0L)) {
                bl2 = false;
            }
            if (!bl2) return When.ALWAYS;
            return When.NEVER;
        }
    }

}

