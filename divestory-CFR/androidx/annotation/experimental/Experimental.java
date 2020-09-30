/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.annotation.experimental;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.CLASS)
@Target(value={ElementType.ANNOTATION_TYPE})
public @interface Experimental {
    public Level level() default Level.ERROR;

    public static final class Level
    extends Enum<Level> {
        private static final /* synthetic */ Level[] $VALUES;
        public static final /* enum */ Level ERROR;
        public static final /* enum */ Level WARNING;

        static {
            Level level;
            WARNING = new Level();
            ERROR = level = new Level();
            $VALUES = new Level[]{WARNING, level};
        }

        public static Level valueOf(String string2) {
            return Enum.valueOf(Level.class, string2);
        }

        public static Level[] values() {
            return (Level[])$VALUES.clone();
        }
    }

}

