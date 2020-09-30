/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.core.text;

import androidx.core.text.TextDirectionHeuristicCompat;
import androidx.core.text.TextUtilsCompat;
import java.nio.CharBuffer;
import java.util.Locale;

public final class TextDirectionHeuristicsCompat {
    public static final TextDirectionHeuristicCompat ANYRTL_LTR;
    public static final TextDirectionHeuristicCompat FIRSTSTRONG_LTR;
    public static final TextDirectionHeuristicCompat FIRSTSTRONG_RTL;
    public static final TextDirectionHeuristicCompat LOCALE;
    public static final TextDirectionHeuristicCompat LTR;
    public static final TextDirectionHeuristicCompat RTL;
    private static final int STATE_FALSE = 1;
    private static final int STATE_TRUE = 0;
    private static final int STATE_UNKNOWN = 2;

    static {
        LTR = new TextDirectionHeuristicInternal(null, false);
        RTL = new TextDirectionHeuristicInternal(null, true);
        FIRSTSTRONG_LTR = new TextDirectionHeuristicInternal(FirstStrong.INSTANCE, false);
        FIRSTSTRONG_RTL = new TextDirectionHeuristicInternal(FirstStrong.INSTANCE, true);
        ANYRTL_LTR = new TextDirectionHeuristicInternal(AnyStrong.INSTANCE_RTL, false);
        LOCALE = TextDirectionHeuristicLocale.INSTANCE;
    }

    private TextDirectionHeuristicsCompat() {
    }

    static int isRtlText(int n) {
        if (n == 0) return 1;
        if (n == 1) return 0;
        if (n == 2) return 0;
        return 2;
    }

    static int isRtlTextOrFormat(int n) {
        if (n == 0) return 1;
        if (n == 1) return 0;
        if (n == 2) return 0;
        switch (n) {
            default: {
                return 2;
            }
            case 16: 
            case 17: {
                return 0;
            }
            case 14: 
            case 15: 
        }
        return 1;
    }

    private static class AnyStrong
    implements TextDirectionAlgorithm {
        static final AnyStrong INSTANCE_RTL = new AnyStrong(true);
        private final boolean mLookForRtl;

        private AnyStrong(boolean bl) {
            this.mLookForRtl = bl;
        }

        @Override
        public int checkRtl(CharSequence charSequence, int n, int n2) {
            boolean bl = false;
            int n3 = n;
            do {
                block5 : {
                    block6 : {
                        block4 : {
                            if (n3 >= n2 + n) {
                                if (!bl) return 2;
                                return (int)this.mLookForRtl;
                            }
                            int n4 = TextDirectionHeuristicsCompat.isRtlText(Character.getDirectionality(charSequence.charAt(n3)));
                            if (n4 == 0) break block4;
                            if (n4 != 1) break block5;
                            if (!this.mLookForRtl) {
                                return 1;
                            }
                            break block6;
                        }
                        if (this.mLookForRtl) {
                            return 0;
                        }
                    }
                    bl = true;
                }
                ++n3;
            } while (true);
        }
    }

    private static class FirstStrong
    implements TextDirectionAlgorithm {
        static final FirstStrong INSTANCE = new FirstStrong();

        private FirstStrong() {
        }

        @Override
        public int checkRtl(CharSequence charSequence, int n, int n2) {
            int n3 = 2;
            int n4 = n;
            while (n4 < n2 + n) {
                if (n3 != 2) return n3;
                n3 = TextDirectionHeuristicsCompat.isRtlTextOrFormat(Character.getDirectionality(charSequence.charAt(n4)));
                ++n4;
            }
            return n3;
        }
    }

    private static interface TextDirectionAlgorithm {
        public int checkRtl(CharSequence var1, int var2, int var3);
    }

    private static abstract class TextDirectionHeuristicImpl
    implements TextDirectionHeuristicCompat {
        private final TextDirectionAlgorithm mAlgorithm;

        TextDirectionHeuristicImpl(TextDirectionAlgorithm textDirectionAlgorithm) {
            this.mAlgorithm = textDirectionAlgorithm;
        }

        private boolean doCheck(CharSequence charSequence, int n, int n2) {
            if ((n = this.mAlgorithm.checkRtl(charSequence, n, n2)) == 0) return true;
            if (n == 1) return false;
            return this.defaultIsRtl();
        }

        protected abstract boolean defaultIsRtl();

        @Override
        public boolean isRtl(CharSequence charSequence, int n, int n2) {
            if (charSequence == null) throw new IllegalArgumentException();
            if (n < 0) throw new IllegalArgumentException();
            if (n2 < 0) throw new IllegalArgumentException();
            if (charSequence.length() - n2 < n) throw new IllegalArgumentException();
            if (this.mAlgorithm != null) return this.doCheck(charSequence, n, n2);
            return this.defaultIsRtl();
        }

        @Override
        public boolean isRtl(char[] arrc, int n, int n2) {
            return this.isRtl(CharBuffer.wrap(arrc), n, n2);
        }
    }

    private static class TextDirectionHeuristicInternal
    extends TextDirectionHeuristicImpl {
        private final boolean mDefaultIsRtl;

        TextDirectionHeuristicInternal(TextDirectionAlgorithm textDirectionAlgorithm, boolean bl) {
            super(textDirectionAlgorithm);
            this.mDefaultIsRtl = bl;
        }

        @Override
        protected boolean defaultIsRtl() {
            return this.mDefaultIsRtl;
        }
    }

    private static class TextDirectionHeuristicLocale
    extends TextDirectionHeuristicImpl {
        static final TextDirectionHeuristicLocale INSTANCE = new TextDirectionHeuristicLocale();

        TextDirectionHeuristicLocale() {
            super(null);
        }

        @Override
        protected boolean defaultIsRtl() {
            int n = TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault());
            boolean bl = true;
            if (n != 1) return false;
            return bl;
        }
    }

}

