/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.text.SpannableStringBuilder
 */
package androidx.core.text;

import android.text.SpannableStringBuilder;
import androidx.core.text.TextDirectionHeuristicCompat;
import androidx.core.text.TextDirectionHeuristicsCompat;
import androidx.core.text.TextUtilsCompat;
import java.util.Locale;

public final class BidiFormatter {
    private static final int DEFAULT_FLAGS = 2;
    static final BidiFormatter DEFAULT_LTR_INSTANCE;
    static final BidiFormatter DEFAULT_RTL_INSTANCE;
    static final TextDirectionHeuristicCompat DEFAULT_TEXT_DIRECTION_HEURISTIC;
    private static final int DIR_LTR = -1;
    private static final int DIR_RTL = 1;
    private static final int DIR_UNKNOWN = 0;
    private static final String EMPTY_STRING = "";
    private static final int FLAG_STEREO_RESET = 2;
    private static final char LRE = '\u202a';
    private static final char LRM = '\u200e';
    private static final String LRM_STRING;
    private static final char PDF = '\u202c';
    private static final char RLE = '\u202b';
    private static final char RLM = '\u200f';
    private static final String RLM_STRING;
    private final TextDirectionHeuristicCompat mDefaultTextDirectionHeuristicCompat;
    private final int mFlags;
    private final boolean mIsRtlContext;

    static {
        DEFAULT_TEXT_DIRECTION_HEURISTIC = TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR;
        LRM_STRING = Character.toString('\u200e');
        RLM_STRING = Character.toString('\u200f');
        DEFAULT_LTR_INSTANCE = new BidiFormatter(false, 2, DEFAULT_TEXT_DIRECTION_HEURISTIC);
        DEFAULT_RTL_INSTANCE = new BidiFormatter(true, 2, DEFAULT_TEXT_DIRECTION_HEURISTIC);
    }

    BidiFormatter(boolean bl, int n, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        this.mIsRtlContext = bl;
        this.mFlags = n;
        this.mDefaultTextDirectionHeuristicCompat = textDirectionHeuristicCompat;
    }

    private static int getEntryDir(CharSequence charSequence) {
        return new DirectionalityEstimator(charSequence, false).getEntryDir();
    }

    private static int getExitDir(CharSequence charSequence) {
        return new DirectionalityEstimator(charSequence, false).getExitDir();
    }

    public static BidiFormatter getInstance() {
        return new Builder().build();
    }

    public static BidiFormatter getInstance(Locale locale) {
        return new Builder(locale).build();
    }

    public static BidiFormatter getInstance(boolean bl) {
        return new Builder(bl).build();
    }

    static boolean isRtlLocale(Locale locale) {
        int n = TextUtilsCompat.getLayoutDirectionFromLocale(locale);
        boolean bl = true;
        if (n != 1) return false;
        return bl;
    }

    private String markAfter(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        boolean bl = textDirectionHeuristicCompat.isRtl(charSequence, 0, charSequence.length());
        if (!this.mIsRtlContext) {
            if (bl) return LRM_STRING;
            if (BidiFormatter.getExitDir(charSequence) == 1) {
                return LRM_STRING;
            }
        }
        if (!this.mIsRtlContext) return EMPTY_STRING;
        if (!bl) return RLM_STRING;
        if (BidiFormatter.getExitDir(charSequence) != -1) return EMPTY_STRING;
        return RLM_STRING;
    }

    private String markBefore(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        boolean bl = textDirectionHeuristicCompat.isRtl(charSequence, 0, charSequence.length());
        if (!this.mIsRtlContext) {
            if (bl) return LRM_STRING;
            if (BidiFormatter.getEntryDir(charSequence) == 1) {
                return LRM_STRING;
            }
        }
        if (!this.mIsRtlContext) return EMPTY_STRING;
        if (!bl) return RLM_STRING;
        if (BidiFormatter.getEntryDir(charSequence) != -1) return EMPTY_STRING;
        return RLM_STRING;
    }

    public boolean getStereoReset() {
        if ((this.mFlags & 2) == 0) return false;
        return true;
    }

    public boolean isRtl(CharSequence charSequence) {
        return this.mDefaultTextDirectionHeuristicCompat.isRtl(charSequence, 0, charSequence.length());
    }

    public boolean isRtl(String string2) {
        return this.isRtl((CharSequence)string2);
    }

    public boolean isRtlContext() {
        return this.mIsRtlContext;
    }

    public CharSequence unicodeWrap(CharSequence charSequence) {
        return this.unicodeWrap(charSequence, this.mDefaultTextDirectionHeuristicCompat, true);
    }

    public CharSequence unicodeWrap(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        return this.unicodeWrap(charSequence, textDirectionHeuristicCompat, true);
    }

    public CharSequence unicodeWrap(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat, boolean bl) {
        if (charSequence == null) {
            return null;
        }
        boolean bl2 = textDirectionHeuristicCompat.isRtl(charSequence, 0, charSequence.length());
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        if (this.getStereoReset() && bl) {
            textDirectionHeuristicCompat = bl2 ? TextDirectionHeuristicsCompat.RTL : TextDirectionHeuristicsCompat.LTR;
            spannableStringBuilder.append((CharSequence)this.markBefore(charSequence, textDirectionHeuristicCompat));
        }
        if (bl2 != this.mIsRtlContext) {
            char c;
            char c2;
            char c3 = bl2 ? (c = '\u202b') : (c2 = '\u202a');
            spannableStringBuilder.append(c3);
            spannableStringBuilder.append(charSequence);
            spannableStringBuilder.append('\u202c');
        } else {
            spannableStringBuilder.append(charSequence);
        }
        if (!bl) return spannableStringBuilder;
        textDirectionHeuristicCompat = bl2 ? TextDirectionHeuristicsCompat.RTL : TextDirectionHeuristicsCompat.LTR;
        spannableStringBuilder.append((CharSequence)this.markAfter(charSequence, textDirectionHeuristicCompat));
        return spannableStringBuilder;
    }

    public CharSequence unicodeWrap(CharSequence charSequence, boolean bl) {
        return this.unicodeWrap(charSequence, this.mDefaultTextDirectionHeuristicCompat, bl);
    }

    public String unicodeWrap(String string2) {
        return this.unicodeWrap(string2, this.mDefaultTextDirectionHeuristicCompat, true);
    }

    public String unicodeWrap(String string2, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        return this.unicodeWrap(string2, textDirectionHeuristicCompat, true);
    }

    public String unicodeWrap(String string2, TextDirectionHeuristicCompat textDirectionHeuristicCompat, boolean bl) {
        if (string2 != null) return this.unicodeWrap((CharSequence)string2, textDirectionHeuristicCompat, bl).toString();
        return null;
    }

    public String unicodeWrap(String string2, boolean bl) {
        return this.unicodeWrap(string2, this.mDefaultTextDirectionHeuristicCompat, bl);
    }

    public static final class Builder {
        private int mFlags;
        private boolean mIsRtlContext;
        private TextDirectionHeuristicCompat mTextDirectionHeuristicCompat;

        public Builder() {
            this.initialize(BidiFormatter.isRtlLocale(Locale.getDefault()));
        }

        public Builder(Locale locale) {
            this.initialize(BidiFormatter.isRtlLocale(locale));
        }

        public Builder(boolean bl) {
            this.initialize(bl);
        }

        private static BidiFormatter getDefaultInstanceFromContext(boolean bl) {
            if (!bl) return DEFAULT_LTR_INSTANCE;
            return DEFAULT_RTL_INSTANCE;
        }

        private void initialize(boolean bl) {
            this.mIsRtlContext = bl;
            this.mTextDirectionHeuristicCompat = DEFAULT_TEXT_DIRECTION_HEURISTIC;
            this.mFlags = 2;
        }

        public BidiFormatter build() {
            if (this.mFlags != 2) return new BidiFormatter(this.mIsRtlContext, this.mFlags, this.mTextDirectionHeuristicCompat);
            if (this.mTextDirectionHeuristicCompat != DEFAULT_TEXT_DIRECTION_HEURISTIC) return new BidiFormatter(this.mIsRtlContext, this.mFlags, this.mTextDirectionHeuristicCompat);
            return Builder.getDefaultInstanceFromContext(this.mIsRtlContext);
        }

        public Builder setTextDirectionHeuristic(TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
            this.mTextDirectionHeuristicCompat = textDirectionHeuristicCompat;
            return this;
        }

        public Builder stereoReset(boolean bl) {
            if (bl) {
                this.mFlags |= 2;
                return this;
            }
            this.mFlags &= -3;
            return this;
        }
    }

    private static class DirectionalityEstimator {
        private static final byte[] DIR_TYPE_CACHE = new byte[1792];
        private static final int DIR_TYPE_CACHE_SIZE = 1792;
        private int charIndex;
        private final boolean isHtml;
        private char lastChar;
        private final int length;
        private final CharSequence text;

        static {
            int n = 0;
            while (n < 1792) {
                DirectionalityEstimator.DIR_TYPE_CACHE[n] = Character.getDirectionality(n);
                ++n;
            }
        }

        DirectionalityEstimator(CharSequence charSequence, boolean bl) {
            this.text = charSequence;
            this.isHtml = bl;
            this.length = charSequence.length();
        }

        private static byte getCachedDirectionality(char c) {
            byte by = Character.getDirectionality(c);
            if (c >= '\u0700') return by;
            byte by2 = DIR_TYPE_CACHE[c];
            return by2;
        }

        private byte skipEntityBackward() {
            int n;
            int n2 = this.charIndex;
            while ((n = this.charIndex) > 0) {
                CharSequence charSequence = this.text;
                this.charIndex = --n;
                n = charSequence.charAt(n);
                this.lastChar = (char)n;
                if (n == 38) {
                    return 12;
                }
                if (n != 59) continue;
            }
            this.charIndex = n2;
            this.lastChar = (char)59;
            return 13;
        }

        private byte skipEntityForward() {
            int n;
            while ((n = this.charIndex) < this.length) {
                CharSequence charSequence = this.text;
                this.charIndex = n + 1;
                n = charSequence.charAt(n);
                this.lastChar = (char)n;
                if (n == 59) return 12;
            }
            return 12;
        }

        private byte skipTagBackward() {
            int n;
            int n2 = this.charIndex;
            block0 : while ((n = this.charIndex) > 0) {
                int n3;
                CharSequence charSequence = this.text;
                this.charIndex = --n;
                n = charSequence.charAt(n);
                this.lastChar = (char)n;
                if (n == 60) {
                    return 12;
                }
                if (n == 62) break;
                if (n != 34 && n != 39) continue;
                n = this.lastChar;
                while ((n3 = this.charIndex) > 0) {
                    charSequence = this.text;
                    this.charIndex = --n3;
                    n3 = charSequence.charAt(n3);
                    this.lastChar = (char)n3;
                    if (n3 == n) continue block0;
                }
            }
            this.charIndex = n2;
            this.lastChar = (char)62;
            return 13;
        }

        /*
         * Unable to fully structure code
         */
        private byte skipTagForward() {
            var1_1 = this.charIndex;
            block0 : do lbl-1000: // 4 sources:
            {
                if ((var2_2 = this.charIndex) >= this.length) {
                    this.charIndex = var1_1;
                    this.lastChar = (char)60;
                    return 13;
                }
                var3_3 = this.text;
                this.charIndex = var2_2 + 1;
                var2_2 = var3_3.charAt(var2_2);
                this.lastChar = (char)var2_2;
                if (var2_2 == 62) {
                    return 12;
                }
                if (var2_2 != 34 && var2_2 != 39) ** GOTO lbl-1000
                var2_2 = this.lastChar;
                do {
                    if ((var4_4 = this.charIndex) >= this.length) ** GOTO lbl-1000
                    var3_3 = this.text;
                    this.charIndex = var4_4 + 1;
                    var4_4 = var3_3.charAt(var4_4);
                    this.lastChar = (char)var4_4;
                    if (var4_4 == var2_2) continue block0;
                } while (true);
                break;
            } while (true);
        }

        byte dirTypeBackward() {
            char c;
            char c2;
            this.lastChar = c = this.text.charAt(this.charIndex - 1);
            if (Character.isLowSurrogate(c)) {
                int n = Character.codePointBefore(this.text, this.charIndex);
                this.charIndex -= Character.charCount(n);
                return Character.getDirectionality(n);
            }
            --this.charIndex;
            char c3 = c2 = DirectionalityEstimator.getCachedDirectionality(this.lastChar);
            if (!this.isHtml) return (byte)c3;
            char c4 = this.lastChar;
            if (c4 == '>') {
                c4 = (char)this.skipTagBackward();
                return (byte)c4;
            }
            c3 = c2;
            if (c4 != ';') return (byte)c3;
            c4 = (char)this.skipEntityBackward();
            return (byte)c4;
        }

        byte dirTypeForward() {
            char c;
            byte by;
            this.lastChar = c = this.text.charAt(this.charIndex);
            if (Character.isHighSurrogate(c)) {
                int n = Character.codePointAt(this.text, this.charIndex);
                this.charIndex += Character.charCount(n);
                return Character.getDirectionality(n);
            }
            ++this.charIndex;
            byte by2 = by = DirectionalityEstimator.getCachedDirectionality(this.lastChar);
            if (!this.isHtml) return by2;
            char c2 = this.lastChar;
            if (c2 == '<') {
                by = this.skipTagForward();
                return by;
            }
            by2 = by;
            if (c2 != '&') return by2;
            by = this.skipEntityForward();
            return by;
        }

        /*
         * Unable to fully structure code
         */
        int getEntryDir() {
            this.charIndex = 0;
            var1_1 = 0;
            var2_2 = 0;
            var3_3 = 0;
            while (this.charIndex < this.length && var1_1 == 0) {
                block18 : {
                    block16 : {
                        block17 : {
                            var4_4 = this.dirTypeForward();
                            if (var4_4 == 0) break block16;
                            if (var4_4 == 1 || var4_4 == 2) break block17;
                            if (var4_4 == 9) continue;
                            switch (var4_4) {
                                default: {
                                    break block18;
                                }
                                case 18: {
                                    --var3_3;
                                    var2_2 = 0;
                                    ** break;
                                }
                                case 16: 
                                case 17: {
                                    ++var3_3;
                                    var2_2 = 1;
                                    ** break;
                                }
                                case 14: 
                                case 15: {
                                    ++var3_3;
                                    var2_2 = -1;
                                    ** break;
lbl25: // 3 sources:
                                    break;
                                }
                            }
                            continue;
                        }
                        if (var3_3 == 0) {
                            return 1;
                        }
                        break block18;
                    }
                    if (var3_3 == 0) {
                        return -1;
                    }
                }
                var1_1 = var3_3;
            }
            if (var1_1 == 0) {
                return 0;
            }
            if (var2_2 != 0) {
                return var2_2;
            }
            block11 : while (this.charIndex > 0) {
                switch (this.dirTypeBackward()) {
                    default: {
                        continue block11;
                    }
                    case 18: {
                        ++var3_3;
                        continue block11;
                    }
                    case 16: 
                    case 17: {
                        if (var1_1 != var3_3) break;
                        return 1;
                    }
                    case 14: 
                    case 15: {
                        if (var1_1 != var3_3) break;
                        return -1;
                    }
                }
                --var3_3;
            }
            return 0;
        }

        int getExitDir() {
            this.charIndex = this.length;
            int n = 0;
            int n2 = 0;
            block5 : while (this.charIndex > 0) {
                block10 : {
                    block8 : {
                        block9 : {
                            byte by = this.dirTypeBackward();
                            if (by == 0) break block8;
                            if (by == 1 || by == 2) break block9;
                            if (by == 9) continue;
                            switch (by) {
                                default: {
                                    if (n2 != 0) continue block5;
                                    break block10;
                                }
                                case 18: {
                                    ++n;
                                    continue block5;
                                }
                                case 16: 
                                case 17: {
                                    if (n2 != n) break;
                                    return 1;
                                }
                                case 14: 
                                case 15: {
                                    if (n2 != n) break;
                                    return -1;
                                }
                            }
                            --n;
                            continue;
                        }
                        if (n == 0) {
                            return 1;
                        }
                        if (n2 != 0) continue;
                        break block10;
                    }
                    if (n == 0) {
                        return -1;
                    }
                    if (n2 != 0) continue;
                }
                n2 = n;
            }
            return 0;
        }
    }

}

