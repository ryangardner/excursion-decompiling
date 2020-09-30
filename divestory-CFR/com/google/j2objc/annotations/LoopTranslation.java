/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.j2objc.annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.SOURCE)
@Target(value={ElementType.LOCAL_VARIABLE})
public @interface LoopTranslation {
    public LoopStyle value();

    public static final class LoopStyle
    extends Enum<LoopStyle> {
        private static final /* synthetic */ LoopStyle[] $VALUES;
        public static final /* enum */ LoopStyle FAST_ENUMERATION;
        public static final /* enum */ LoopStyle JAVA_ITERATOR;

        static {
            LoopStyle loopStyle;
            JAVA_ITERATOR = new LoopStyle();
            FAST_ENUMERATION = loopStyle = new LoopStyle();
            $VALUES = new LoopStyle[]{JAVA_ITERATOR, loopStyle};
        }

        public static LoopStyle valueOf(String string2) {
            return Enum.valueOf(LoopStyle.class, string2);
        }

        public static LoopStyle[] values() {
            return (LoopStyle[])$VALUES.clone();
        }
    }

}

