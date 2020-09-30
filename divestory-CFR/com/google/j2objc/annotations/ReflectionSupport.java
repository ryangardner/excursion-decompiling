/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.j2objc.annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.CLASS)
@Target(value={ElementType.TYPE, ElementType.PACKAGE})
public @interface ReflectionSupport {
    public Level value();

    public static final class Level
    extends Enum<Level> {
        private static final /* synthetic */ Level[] $VALUES;
        public static final /* enum */ Level FULL;
        public static final /* enum */ Level NATIVE_ONLY;

        static {
            Level level;
            NATIVE_ONLY = new Level();
            FULL = level = new Level();
            $VALUES = new Level[]{NATIVE_ONLY, level};
        }

        public static Level valueOf(String string2) {
            return Enum.valueOf(Level.class, string2);
        }

        public static Level[] values() {
            return (Level[])$VALUES.clone();
        }
    }

}

