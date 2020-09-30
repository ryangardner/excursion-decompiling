/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.base;

import com.google.common.base.Ascii;
import com.google.common.base.CharMatcher;
import com.google.common.base.Converter;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class CaseFormat
extends Enum<CaseFormat> {
    private static final /* synthetic */ CaseFormat[] $VALUES;
    public static final /* enum */ CaseFormat LOWER_CAMEL;
    public static final /* enum */ CaseFormat LOWER_HYPHEN;
    public static final /* enum */ CaseFormat LOWER_UNDERSCORE;
    public static final /* enum */ CaseFormat UPPER_CAMEL;
    public static final /* enum */ CaseFormat UPPER_UNDERSCORE;
    private final CharMatcher wordBoundary;
    private final String wordSeparator;

    static {
        CaseFormat caseFormat;
        LOWER_HYPHEN = new CaseFormat(CharMatcher.is('-'), "-"){

            @Override
            String convert(CaseFormat caseFormat, String string2) {
                if (caseFormat == LOWER_UNDERSCORE) {
                    return string2.replace('-', '_');
                }
                if (caseFormat != UPPER_UNDERSCORE) return super.convert(caseFormat, string2);
                return Ascii.toUpperCase(string2.replace('-', '_'));
            }

            @Override
            String normalizeWord(String string2) {
                return Ascii.toLowerCase(string2);
            }
        };
        LOWER_UNDERSCORE = new CaseFormat(CharMatcher.is('_'), "_"){

            @Override
            String convert(CaseFormat caseFormat, String string2) {
                if (caseFormat == LOWER_HYPHEN) {
                    return string2.replace('_', '-');
                }
                if (caseFormat != UPPER_UNDERSCORE) return super.convert(caseFormat, string2);
                return Ascii.toUpperCase(string2);
            }

            @Override
            String normalizeWord(String string2) {
                return Ascii.toLowerCase(string2);
            }
        };
        LOWER_CAMEL = new CaseFormat(CharMatcher.inRange('A', 'Z'), ""){

            @Override
            String normalizeFirstWord(String string2) {
                return Ascii.toLowerCase(string2);
            }

            @Override
            String normalizeWord(String string2) {
                return CaseFormat.firstCharOnlyToUpper(string2);
            }
        };
        UPPER_CAMEL = new CaseFormat(CharMatcher.inRange('A', 'Z'), ""){

            @Override
            String normalizeWord(String string2) {
                return CaseFormat.firstCharOnlyToUpper(string2);
            }
        };
        UPPER_UNDERSCORE = caseFormat = new CaseFormat(CharMatcher.is('_'), "_"){

            @Override
            String convert(CaseFormat caseFormat, String string2) {
                if (caseFormat == LOWER_HYPHEN) {
                    return Ascii.toLowerCase(string2.replace('_', '-'));
                }
                if (caseFormat != LOWER_UNDERSCORE) return super.convert(caseFormat, string2);
                return Ascii.toLowerCase(string2);
            }

            @Override
            String normalizeWord(String string2) {
                return Ascii.toUpperCase(string2);
            }
        };
        $VALUES = new CaseFormat[]{LOWER_HYPHEN, LOWER_UNDERSCORE, LOWER_CAMEL, UPPER_CAMEL, caseFormat};
    }

    private CaseFormat(CharMatcher charMatcher, String string3) {
        this.wordBoundary = charMatcher;
        this.wordSeparator = string3;
    }

    private static String firstCharOnlyToUpper(String string2) {
        if (string2.isEmpty()) {
            return string2;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Ascii.toUpperCase(string2.charAt(0)));
        stringBuilder.append(Ascii.toLowerCase(string2.substring(1)));
        return stringBuilder.toString();
    }

    public static CaseFormat valueOf(String string2) {
        return Enum.valueOf(CaseFormat.class, string2);
    }

    public static CaseFormat[] values() {
        return (CaseFormat[])$VALUES.clone();
    }

    String convert(CaseFormat object, String string2) {
        StringBuilder stringBuilder = null;
        int n = 0;
        int n2 = -1;
        while ((n2 = this.wordBoundary.indexIn(string2, n2 + 1)) != -1) {
            if (n == 0) {
                stringBuilder = new StringBuilder(string2.length() + ((CaseFormat)object).wordSeparator.length() * 4);
                stringBuilder.append(((CaseFormat)((Object)object)).normalizeFirstWord(string2.substring(n, n2)));
            } else {
                stringBuilder.append(((CaseFormat)((Object)object)).normalizeWord(string2.substring(n, n2)));
            }
            stringBuilder.append(((CaseFormat)object).wordSeparator);
            n = this.wordSeparator.length() + n2;
        }
        if (n == 0) {
            return ((CaseFormat)((Object)object)).normalizeFirstWord(string2);
        }
        stringBuilder.append(((CaseFormat)((Object)object)).normalizeWord(string2.substring(n)));
        return stringBuilder.toString();
    }

    public Converter<String, String> converterTo(CaseFormat caseFormat) {
        return new StringConverter(this, caseFormat);
    }

    String normalizeFirstWord(String string2) {
        return this.normalizeWord(string2);
    }

    abstract String normalizeWord(String var1);

    public final String to(CaseFormat caseFormat, String string2) {
        Preconditions.checkNotNull(caseFormat);
        Preconditions.checkNotNull(string2);
        if (caseFormat != this) return this.convert(caseFormat, string2);
        return string2;
    }

    private static final class StringConverter
    extends Converter<String, String>
    implements Serializable {
        private static final long serialVersionUID = 0L;
        private final CaseFormat sourceFormat;
        private final CaseFormat targetFormat;

        StringConverter(CaseFormat caseFormat, CaseFormat caseFormat2) {
            this.sourceFormat = Preconditions.checkNotNull(caseFormat);
            this.targetFormat = Preconditions.checkNotNull(caseFormat2);
        }

        @Override
        protected String doBackward(String string2) {
            return this.targetFormat.to(this.sourceFormat, string2);
        }

        @Override
        protected String doForward(String string2) {
            return this.sourceFormat.to(this.targetFormat, string2);
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            boolean bl;
            boolean bl2 = object instanceof StringConverter;
            boolean bl3 = bl = false;
            if (!bl2) return bl3;
            object = (StringConverter)object;
            bl3 = bl;
            if (!this.sourceFormat.equals((Object)((StringConverter)object).sourceFormat)) return bl3;
            bl3 = bl;
            if (!this.targetFormat.equals((Object)((StringConverter)object).targetFormat)) return bl3;
            return true;
        }

        public int hashCode() {
            return this.sourceFormat.hashCode() ^ this.targetFormat.hashCode();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((Object)this.sourceFormat);
            stringBuilder.append(".converterTo(");
            stringBuilder.append((Object)this.targetFormat);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

}

