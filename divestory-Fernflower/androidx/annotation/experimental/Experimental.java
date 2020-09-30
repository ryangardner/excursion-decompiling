package androidx.annotation.experimental;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.ANNOTATION_TYPE})
public @interface Experimental {
   Experimental.Level level() default Experimental.Level.ERROR;

   public static enum Level {
      ERROR,
      WARNING;

      static {
         Experimental.Level var0 = new Experimental.Level("ERROR", 1);
         ERROR = var0;
      }
   }
}
