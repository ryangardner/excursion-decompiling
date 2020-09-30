/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.base;

import com.google.common.base.CharMatcher;
import com.google.common.base.CommonPattern;
import com.google.common.base.Enums;
import com.google.common.base.JdkPattern;
import com.google.common.base.Optional;
import com.google.common.base.PatternCompiler;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.ServiceConfigurationError;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class Platform {
    private static final String GWT_RPC_PROPERTY_NAME = "guava.gwt.emergency_reenable_rpc";
    private static final Logger logger = Logger.getLogger(Platform.class.getName());
    private static final PatternCompiler patternCompiler = Platform.loadPatternCompiler();

    private Platform() {
    }

    static void checkGwtRpcEnabled() {
        if (!Boolean.parseBoolean(System.getProperty(GWT_RPC_PROPERTY_NAME, "true"))) throw new UnsupportedOperationException(Strings.lenientFormat("We are removing GWT-RPC support for Guava types. You can temporarily reenable support by setting the system property %s to true. For more about system properties, see %s. For more about Guava's GWT-RPC support, see %s.", GWT_RPC_PROPERTY_NAME, "https://stackoverflow.com/q/5189914/28465", "https://groups.google.com/d/msg/guava-announce/zHZTFg7YF3o/rQNnwdHeEwAJ"));
    }

    static CommonPattern compilePattern(String string2) {
        Preconditions.checkNotNull(string2);
        return patternCompiler.compile(string2);
    }

    static String emptyToNull(@NullableDecl String string2) {
        String string3 = string2;
        if (!Platform.stringIsNullOrEmpty(string2)) return string3;
        return null;
    }

    static String formatCompact4Digits(double d) {
        return String.format(Locale.ROOT, "%.4g", d);
    }

    static <T extends Enum<T>> Optional<T> getEnumIfPresent(Class<T> serializable, String weakReference) {
        weakReference = Enums.getEnumConstants(serializable).get(weakReference);
        if (weakReference != null) return Optional.of(serializable.cast(weakReference.get()));
        return Optional.absent();
    }

    private static PatternCompiler loadPatternCompiler() {
        return new JdkPatternCompiler();
    }

    private static void logPatternCompilerError(ServiceConfigurationError serviceConfigurationError) {
        logger.log(Level.WARNING, "Error loading regex compiler, falling back to next option", serviceConfigurationError);
    }

    static String nullToEmpty(@NullableDecl String string2) {
        String string3 = string2;
        if (string2 != null) return string3;
        return "";
    }

    static boolean patternCompilerIsPcreLike() {
        return patternCompiler.isPcreLike();
    }

    static CharMatcher precomputeCharMatcher(CharMatcher charMatcher) {
        return charMatcher.precomputedInternal();
    }

    static boolean stringIsNullOrEmpty(@NullableDecl String string2) {
        if (string2 == null) return true;
        if (string2.isEmpty()) return true;
        return false;
    }

    static long systemNanoTime() {
        return System.nanoTime();
    }

    private static final class JdkPatternCompiler
    implements PatternCompiler {
        private JdkPatternCompiler() {
        }

        @Override
        public CommonPattern compile(String string2) {
            return new JdkPattern(Pattern.compile(string2));
        }

        @Override
        public boolean isPcreLike() {
            return true;
        }
    }

}

