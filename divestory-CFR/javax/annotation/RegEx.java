/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.annotation.Syntax;
import javax.annotation.meta.TypeQualifierValidator;
import javax.annotation.meta.When;

@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@Syntax(value="RegEx")
public @interface RegEx {
    public When when() default When.ALWAYS;

    public static class Checker
    implements TypeQualifierValidator<RegEx> {
        @Override
        public When forConstantValue(RegEx regEx, Object object) {
            if (!(object instanceof String)) {
                return When.NEVER;
            }
            try {
                Pattern.compile((String)object);
                return When.ALWAYS;
            }
            catch (PatternSyntaxException patternSyntaxException) {
                return When.NEVER;
            }
        }
    }

}

