/*
 * Decompiled with CFR <Could not determine version>.
 */
package javax.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.RegEx;
import javax.annotation.meta.TypeQualifier;
import javax.annotation.meta.TypeQualifierValidator;
import javax.annotation.meta.When;

@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@TypeQualifier(applicableTo=String.class)
public @interface MatchesPattern {
    public int flags() default 0;

    @RegEx
    public String value();

    public static class Checker
    implements TypeQualifierValidator<MatchesPattern> {
        @Override
        public When forConstantValue(MatchesPattern matchesPattern, Object object) {
            if (!Pattern.compile(matchesPattern.value(), matchesPattern.flags()).matcher((String)object).matches()) return When.NEVER;
            return When.ALWAYS;
        }
    }

}

