/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.escape;

import com.google.common.base.Preconditions;
import com.google.common.escape.ArrayBasedCharEscaper;
import com.google.common.escape.CharEscaper;
import com.google.common.escape.Escaper;
import com.google.common.escape.UnicodeEscaper;
import java.util.HashMap;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Escapers {
    private static final Escaper NULL_ESCAPER = new CharEscaper(){

        @Override
        public String escape(String string2) {
            return Preconditions.checkNotNull(string2);
        }

        @Override
        protected char[] escape(char c) {
            return null;
        }
    };

    private Escapers() {
    }

    static UnicodeEscaper asUnicodeEscaper(Escaper escaper) {
        Preconditions.checkNotNull(escaper);
        if (escaper instanceof UnicodeEscaper) {
            return (UnicodeEscaper)escaper;
        }
        if (escaper instanceof CharEscaper) {
            return Escapers.wrap((CharEscaper)escaper);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot create a UnicodeEscaper from: ");
        stringBuilder.append(escaper.getClass().getName());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static Builder builder() {
        return new Builder();
    }

    public static String computeReplacement(CharEscaper charEscaper, char c) {
        return Escapers.stringOrNull(charEscaper.escape(c));
    }

    public static String computeReplacement(UnicodeEscaper unicodeEscaper, int n) {
        return Escapers.stringOrNull(unicodeEscaper.escape(n));
    }

    public static Escaper nullEscaper() {
        return NULL_ESCAPER;
    }

    private static String stringOrNull(char[] object) {
        if (object != null) return new String((char[])object);
        return null;
    }

    private static UnicodeEscaper wrap(final CharEscaper charEscaper) {
        return new UnicodeEscaper(){

            @Override
            protected char[] escape(int n) {
                if (n < 65536) {
                    return charEscaper.escape((char)n);
                }
                char[] arrc = new char[2];
                int n2 = 0;
                Character.toChars(n, arrc, 0);
                char[] arrc2 = charEscaper.escape(arrc[0]);
                char[] arrc3 = charEscaper.escape(arrc[1]);
                if (arrc2 == null && arrc3 == null) {
                    return null;
                }
                n = arrc2 != null ? arrc2.length : 1;
                int n3 = arrc3 != null ? arrc3.length : 1;
                char[] arrc4 = new char[n3 + n];
                if (arrc2 != null) {
                    for (n3 = 0; n3 < arrc2.length; ++n3) {
                        arrc4[n3] = arrc2[n3];
                    }
                } else {
                    arrc4[0] = arrc[0];
                }
                if (arrc3 == null) {
                    arrc4[n] = arrc[1];
                    return arrc4;
                }
                n3 = n2;
                while (n3 < arrc3.length) {
                    arrc4[n + n3] = arrc3[n3];
                    ++n3;
                }
                return arrc4;
            }
        };
    }

    public static final class Builder {
        private final Map<Character, String> replacementMap = new HashMap<Character, String>();
        private char safeMax = (char)65535;
        private char safeMin = (char)(false ? 1 : 0);
        private String unsafeReplacement = null;

        private Builder() {
        }

        public Builder addEscape(char c, String string2) {
            Preconditions.checkNotNull(string2);
            this.replacementMap.put(Character.valueOf(c), string2);
            return this;
        }

        public Escaper build() {
            return new ArrayBasedCharEscaper(this.replacementMap, this.safeMin, this.safeMax){
                private final char[] replacementChars;
                {
                    Builder.this = Builder.this.unsafeReplacement != null ? Builder.this.unsafeReplacement.toCharArray() : null;
                    this.replacementChars = Builder.this;
                }

                @Override
                protected char[] escapeUnsafe(char c) {
                    return this.replacementChars;
                }
            };
        }

        public Builder setSafeRange(char c, char c2) {
            this.safeMin = c;
            this.safeMax = c2;
            return this;
        }

        public Builder setUnsafeReplacement(@NullableDecl String string2) {
            this.unsafeReplacement = string2;
            return this;
        }

    }

}

