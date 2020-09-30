package kotlin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.ANNOTATION_TYPE})
@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0087\u0002\u0018\u00002\u00020\u0001:\u0001\bB\u0014\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005R\u000f\u0010\u0004\u001a\u00020\u0005¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0006R\u000f\u0010\u0002\u001a\u00020\u0003¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0007ø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u0099F0\u0001¨\u0006\t"},
   d2 = {"Lkotlin/RequiresOptIn;", "", "message", "", "level", "Lkotlin/RequiresOptIn$Level;", "()Lkotlin/RequiresOptIn$Level;", "()Ljava/lang/String;", "Level", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
@kotlin.annotation.Retention(AnnotationRetention.BINARY)
@kotlin.annotation.Target(
   allowedTargets = {AnnotationTarget.ANNOTATION_CLASS}
)
public @interface RequiresOptIn {
   RequiresOptIn.Level level() default RequiresOptIn.Level.ERROR;

   String message() default "";

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"},
      d2 = {"Lkotlin/RequiresOptIn$Level;", "", "(Ljava/lang/String;I)V", "WARNING", "ERROR", "kotlin-stdlib"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static enum Level {
      ERROR,
      WARNING;

      static {
         RequiresOptIn.Level var0 = new RequiresOptIn.Level("WARNING", 0);
         WARNING = var0;
         RequiresOptIn.Level var1 = new RequiresOptIn.Level("ERROR", 1);
         ERROR = var1;
      }
   }
}
