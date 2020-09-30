package javax.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.annotation.meta.TypeQualifier;
import javax.annotation.meta.TypeQualifierValidator;
import javax.annotation.meta.When;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@TypeQualifier(
   applicableTo = Number.class
)
public @interface Nonnegative {
   When when() default When.ALWAYS;

   public static class Checker implements TypeQualifierValidator<Nonnegative> {
      public When forConstantValue(Nonnegative var1, Object var2) {
         if (!(var2 instanceof Number)) {
            return When.NEVER;
         } else {
            Number var5 = (Number)var2;
            boolean var3 = var5 instanceof Long;
            boolean var4 = true;
            if (var3) {
               if (var5.longValue() < 0L) {
                  return var4 ? When.NEVER : When.ALWAYS;
               }
            } else if (var5 instanceof Double) {
               if (var5.doubleValue() < 0.0D) {
                  return var4 ? When.NEVER : When.ALWAYS;
               }
            } else if (var5 instanceof Float) {
               if (var5.floatValue() < 0.0F) {
                  return var4 ? When.NEVER : When.ALWAYS;
               }
            } else if (var5.intValue() < 0) {
               return var4 ? When.NEVER : When.ALWAYS;
            }

            var4 = false;
            return var4 ? When.NEVER : When.ALWAYS;
         }
      }
   }
}
