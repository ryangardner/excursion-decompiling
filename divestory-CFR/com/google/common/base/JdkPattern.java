/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.base;

import com.google.common.base.CommonMatcher;
import com.google.common.base.CommonPattern;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class JdkPattern
extends CommonPattern
implements Serializable {
    private static final long serialVersionUID = 0L;
    private final Pattern pattern;

    JdkPattern(Pattern pattern) {
        this.pattern = Preconditions.checkNotNull(pattern);
    }

    @Override
    public int flags() {
        return this.pattern.flags();
    }

    @Override
    public CommonMatcher matcher(CharSequence charSequence) {
        return new JdkMatcher(this.pattern.matcher(charSequence));
    }

    @Override
    public String pattern() {
        return this.pattern.pattern();
    }

    @Override
    public String toString() {
        return this.pattern.toString();
    }

    private static final class JdkMatcher
    extends CommonMatcher {
        final Matcher matcher;

        JdkMatcher(Matcher matcher) {
            this.matcher = Preconditions.checkNotNull(matcher);
        }

        @Override
        public int end() {
            return this.matcher.end();
        }

        @Override
        public boolean find() {
            return this.matcher.find();
        }

        @Override
        public boolean find(int n) {
            return this.matcher.find(n);
        }

        @Override
        public boolean matches() {
            return this.matcher.matches();
        }

        @Override
        public String replaceAll(String string2) {
            return this.matcher.replaceAll(string2);
        }

        @Override
        public int start() {
            return this.matcher.start();
        }
    }

}

