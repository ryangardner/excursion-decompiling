/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.Retention;
import kotlin.annotation.Target;

@java.lang.annotation.Retention(value=RetentionPolicy.CLASS)
@java.lang.annotation.Target(value={ElementType.ANNOTATION_TYPE})
@Metadata(bv={1, 0, 3}, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0087\u0002\u0018\u00002\u00020\u0001:\u0001\bB\u0014\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005R\u000f\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0006R\u000f\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0006\u001a\u0004\b\u0002\u0010\u0007\u00f8\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u0099F0\u0001\u00a8\u0006\t"}, d2={"Lkotlin/RequiresOptIn;", "", "message", "", "level", "Lkotlin/RequiresOptIn$Level;", "()Lkotlin/RequiresOptIn$Level;", "()Ljava/lang/String;", "Level", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
@Retention(value=AnnotationRetention.BINARY)
@Target(allowedTargets={AnnotationTarget.ANNOTATION_CLASS})
public @interface RequiresOptIn {
    public Level level() default Level.ERROR;

    public String message() default "";

    @Metadata(bv={1, 0, 3}, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2={"Lkotlin/RequiresOptIn$Level;", "", "(Ljava/lang/String;I)V", "WARNING", "ERROR", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
    public static final class Level
    extends Enum<Level> {
        private static final /* synthetic */ Level[] $VALUES;
        public static final /* enum */ Level ERROR;
        public static final /* enum */ Level WARNING;

        static {
            Level level;
            Level level2;
            WARNING = level = new Level();
            ERROR = level2 = new Level();
            $VALUES = new Level[]{level, level2};
        }

        public static Level valueOf(String string2) {
            return Enum.valueOf(Level.class, string2);
        }

        public static Level[] values() {
            return (Level[])$VALUES.clone();
        }
    }

}

