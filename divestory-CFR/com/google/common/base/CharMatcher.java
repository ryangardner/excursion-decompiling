/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.base;

import com.google.common.base.Platform;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.SmallCharMatcher;
import java.util.Arrays;
import java.util.BitSet;

public abstract class CharMatcher
implements Predicate<Character> {
    private static final int DISTINCT_CHARS = 65536;

    protected CharMatcher() {
    }

    public static CharMatcher any() {
        return Any.INSTANCE;
    }

    public static CharMatcher anyOf(CharSequence charSequence) {
        int n = charSequence.length();
        if (n == 0) return CharMatcher.none();
        if (n == 1) return CharMatcher.is(charSequence.charAt(0));
        if (n == 2) return CharMatcher.isEither(charSequence.charAt(0), charSequence.charAt(1));
        return new AnyOf(charSequence);
    }

    public static CharMatcher ascii() {
        return Ascii.INSTANCE;
    }

    public static CharMatcher breakingWhitespace() {
        return BreakingWhitespace.INSTANCE;
    }

    @Deprecated
    public static CharMatcher digit() {
        return Digit.INSTANCE;
    }

    private String finishCollapseFrom(CharSequence charSequence, int n, int n2, char c, StringBuilder stringBuilder, boolean bl) {
        boolean bl2 = bl;
        while (n < n2) {
            char c2 = charSequence.charAt(n);
            if (this.matches(c2)) {
                bl = bl2;
                if (!bl2) {
                    stringBuilder.append(c);
                    bl = true;
                }
            } else {
                stringBuilder.append(c2);
                bl = false;
            }
            ++n;
            bl2 = bl;
        }
        return stringBuilder.toString();
    }

    public static CharMatcher forPredicate(Predicate<? super Character> charMatcher) {
        if (!(charMatcher instanceof CharMatcher)) return new ForPredicate(charMatcher);
        return charMatcher;
    }

    public static CharMatcher inRange(char c, char c2) {
        return new InRange(c, c2);
    }

    @Deprecated
    public static CharMatcher invisible() {
        return Invisible.INSTANCE;
    }

    public static CharMatcher is(char c) {
        return new Is(c);
    }

    private static IsEither isEither(char c, char c2) {
        return new IsEither(c, c2);
    }

    public static CharMatcher isNot(char c) {
        return new IsNot(c);
    }

    private static boolean isSmall(int n, int n2) {
        if (n > 1023) return false;
        if (n2 <= n * 4 * 16) return false;
        return true;
    }

    @Deprecated
    public static CharMatcher javaDigit() {
        return JavaDigit.INSTANCE;
    }

    public static CharMatcher javaIsoControl() {
        return JavaIsoControl.INSTANCE;
    }

    @Deprecated
    public static CharMatcher javaLetter() {
        return JavaLetter.INSTANCE;
    }

    @Deprecated
    public static CharMatcher javaLetterOrDigit() {
        return JavaLetterOrDigit.INSTANCE;
    }

    @Deprecated
    public static CharMatcher javaLowerCase() {
        return JavaLowerCase.INSTANCE;
    }

    @Deprecated
    public static CharMatcher javaUpperCase() {
        return JavaUpperCase.INSTANCE;
    }

    public static CharMatcher none() {
        return None.INSTANCE;
    }

    public static CharMatcher noneOf(CharSequence charSequence) {
        return CharMatcher.anyOf(charSequence).negate();
    }

    private static CharMatcher precomputedPositive(int n, BitSet object, String string2) {
        if (n == 0) return CharMatcher.none();
        if (n == 1) return CharMatcher.is((char)((BitSet)object).nextSetBit(0));
        if (n == 2) {
            char c = (char)((BitSet)object).nextSetBit(0);
            return CharMatcher.isEither(c, (char)((BitSet)object).nextSetBit(c + '\u0001'));
        }
        if (!CharMatcher.isSmall(n, ((BitSet)object).length())) return new BitSetMatcher((BitSet)object, string2);
        return SmallCharMatcher.from((BitSet)object, string2);
    }

    private static String showCharacter(char c) {
        char[] arrc;
        char[] arrc2 = arrc = new char[6];
        arrc2[0] = 92;
        arrc2[1] = 117;
        arrc2[2] = 0;
        arrc2[3] = 0;
        arrc2[4] = 0;
        arrc2[5] = 0;
        char c2 = '\u0000';
        char c3 = c;
        c = c2;
        while (c < '\u0004') {
            arrc[5 - c] = "0123456789ABCDEF".charAt(c3 & 15);
            c3 = (char)(c3 >> 4);
            c = (char)(c + 1);
        }
        return String.copyValueOf(arrc);
    }

    @Deprecated
    public static CharMatcher singleWidth() {
        return SingleWidth.INSTANCE;
    }

    public static CharMatcher whitespace() {
        return Whitespace.INSTANCE;
    }

    public CharMatcher and(CharMatcher charMatcher) {
        return new And(this, charMatcher);
    }

    @Deprecated
    @Override
    public boolean apply(Character c) {
        return this.matches(c.charValue());
    }

    public String collapseFrom(CharSequence charSequence, char c) {
        int n;
        int n2;
        block3 : {
            n2 = charSequence.length();
            n = 0;
            while (n < n2) {
                char c2 = charSequence.charAt(n);
                int n3 = n;
                if (this.matches(c2)) {
                    if (c2 != c || n != n2 - 1 && this.matches(charSequence.charAt(n + 1))) break block3;
                    n3 = n + 1;
                }
                n = n3 + 1;
            }
            return charSequence.toString();
        }
        StringBuilder stringBuilder = new StringBuilder(n2);
        stringBuilder.append(charSequence, 0, n);
        stringBuilder.append(c);
        return this.finishCollapseFrom(charSequence, n + 1, n2, c, stringBuilder, true);
    }

    public int countIn(CharSequence charSequence) {
        int n = 0;
        int n2 = 0;
        while (n < charSequence.length()) {
            int n3 = n2;
            if (this.matches(charSequence.charAt(n))) {
                n3 = n2 + 1;
            }
            ++n;
            n2 = n3;
        }
        return n2;
    }

    public int indexIn(CharSequence charSequence) {
        return this.indexIn(charSequence, 0);
    }

    public int indexIn(CharSequence charSequence, int n) {
        int n2 = charSequence.length();
        Preconditions.checkPositionIndex(n, n2);
        while (n < n2) {
            if (this.matches(charSequence.charAt(n))) {
                return n;
            }
            ++n;
        }
        return -1;
    }

    public int lastIndexIn(CharSequence charSequence) {
        int n = charSequence.length() - 1;
        while (n >= 0) {
            if (this.matches(charSequence.charAt(n))) {
                return n;
            }
            --n;
        }
        return -1;
    }

    public abstract boolean matches(char var1);

    public boolean matchesAllOf(CharSequence charSequence) {
        int n = charSequence.length() - 1;
        while (n >= 0) {
            if (!this.matches(charSequence.charAt(n))) {
                return false;
            }
            --n;
        }
        return true;
    }

    public boolean matchesAnyOf(CharSequence charSequence) {
        return this.matchesNoneOf(charSequence) ^ true;
    }

    public boolean matchesNoneOf(CharSequence charSequence) {
        if (this.indexIn(charSequence) != -1) return false;
        return true;
    }

    public CharMatcher negate() {
        return new Negated(this);
    }

    public CharMatcher or(CharMatcher charMatcher) {
        return new Or(this, charMatcher);
    }

    public CharMatcher precomputed() {
        return Platform.precomputeCharMatcher(this);
    }

    CharMatcher precomputedInternal() {
        CharSequence charSequence;
        BitSet bitSet = new BitSet();
        this.setBits(bitSet);
        int n = bitSet.cardinality();
        if (n * 2 <= 65536) {
            return CharMatcher.precomputedPositive(n, bitSet, this.toString());
        }
        bitSet.flip(0, 65536);
        final String string2 = this.toString();
        if (string2.endsWith(".negate()")) {
            charSequence = string2.substring(0, string2.length() - 9);
            return new NegatedFastMatcher(CharMatcher.precomputedPositive(65536 - n, bitSet, (String)charSequence)){

                @Override
                public String toString() {
                    return string2;
                }
            };
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append(".negate()");
        charSequence = ((StringBuilder)charSequence).toString();
        return new /* invalid duplicate definition of identical inner class */;
    }

    public String removeFrom(CharSequence arrc) {
        int n = this.indexIn((CharSequence)(arrc = arrc.toString()));
        if (n == -1) {
            return arrc;
        }
        arrc = arrc.toCharArray();
        int n2 = 1;
        block0 : do {
            ++n;
            do {
                if (n == arrc.length) {
                    return new String(arrc, 0, n - n2);
                }
                if (this.matches(arrc[n])) {
                    ++n2;
                    continue block0;
                }
                arrc[n - n2] = arrc[n];
                ++n;
            } while (true);
            break;
        } while (true);
    }

    public String replaceFrom(CharSequence arrc, char c) {
        int n;
        int n2 = this.indexIn((CharSequence)(arrc = arrc.toString()));
        if (n2 == -1) {
            return arrc;
        }
        arrc = arrc.toCharArray();
        arrc[n2] = c;
        while ((n = n2 + 1) < arrc.length) {
            n2 = n;
            if (!this.matches(arrc[n])) continue;
            arrc[n] = c;
            n2 = n;
        }
        return new String(arrc);
    }

    public String replaceFrom(CharSequence charSequence, CharSequence charSequence2) {
        int n;
        int n2;
        int n3 = charSequence2.length();
        if (n3 == 0) {
            return this.removeFrom(charSequence);
        }
        int n4 = 0;
        if (n3 == 1) {
            return this.replaceFrom(charSequence, charSequence2.charAt(0));
        }
        String string2 = charSequence.toString();
        n3 = this.indexIn(string2);
        if (n3 == -1) {
            return string2;
        }
        int n5 = string2.length();
        charSequence = new StringBuilder(n5 * 3 / 2 + 16);
        do {
            ((StringBuilder)charSequence).append(string2, n4, n3);
            ((StringBuilder)charSequence).append(charSequence2);
            n = n3 + 1;
            n3 = n2 = this.indexIn(string2, n);
            n4 = n;
        } while (n2 != -1);
        ((StringBuilder)charSequence).append(string2, n, n5);
        return ((StringBuilder)charSequence).toString();
    }

    public String retainFrom(CharSequence charSequence) {
        return this.negate().removeFrom(charSequence);
    }

    void setBits(BitSet bitSet) {
        int n = 65535;
        while (n >= 0) {
            if (this.matches((char)n)) {
                bitSet.set(n);
            }
            --n;
        }
    }

    public String toString() {
        return super.toString();
    }

    public String trimAndCollapseFrom(CharSequence charSequence, char c) {
        int n;
        int n2 = charSequence.length();
        int n3 = n2 - 1;
        for (n = 0; n < n2 && this.matches(charSequence.charAt(n)); ++n) {
        }
        for (n2 = n3; n2 > n && this.matches(charSequence.charAt(n2)); --n2) {
        }
        if (n != 0) return this.finishCollapseFrom(charSequence, n, ++n2, c, new StringBuilder(n2 - n), false);
        if (n2 != n3) return this.finishCollapseFrom(charSequence, n, ++n2, c, new StringBuilder(n2 - n), false);
        return this.collapseFrom(charSequence, c);
    }

    public String trimFrom(CharSequence charSequence) {
        int n;
        int n2 = charSequence.length();
        for (n = 0; n < n2 && this.matches(charSequence.charAt(n)); ++n) {
        }
        --n2;
        while (n2 > n) {
            if (!this.matches(charSequence.charAt(n2))) {
                return charSequence.subSequence(n, n2 + 1).toString();
            }
            --n2;
        }
        return charSequence.subSequence(n, n2 + 1).toString();
    }

    public String trimLeadingFrom(CharSequence charSequence) {
        int n = charSequence.length();
        int n2 = 0;
        while (n2 < n) {
            if (!this.matches(charSequence.charAt(n2))) {
                return charSequence.subSequence(n2, n).toString();
            }
            ++n2;
        }
        return "";
    }

    public String trimTrailingFrom(CharSequence charSequence) {
        int n = charSequence.length() - 1;
        while (n >= 0) {
            if (!this.matches(charSequence.charAt(n))) {
                return charSequence.subSequence(0, n + 1).toString();
            }
            --n;
        }
        return "";
    }

    private static final class And
    extends CharMatcher {
        final CharMatcher first;
        final CharMatcher second;

        And(CharMatcher charMatcher, CharMatcher charMatcher2) {
            this.first = Preconditions.checkNotNull(charMatcher);
            this.second = Preconditions.checkNotNull(charMatcher2);
        }

        @Override
        public boolean matches(char c) {
            if (!this.first.matches(c)) return false;
            if (!this.second.matches(c)) return false;
            return true;
        }

        @Override
        void setBits(BitSet bitSet) {
            BitSet bitSet2 = new BitSet();
            this.first.setBits(bitSet2);
            BitSet bitSet3 = new BitSet();
            this.second.setBits(bitSet3);
            bitSet2.and(bitSet3);
            bitSet.or(bitSet2);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CharMatcher.and(");
            stringBuilder.append(this.first);
            stringBuilder.append(", ");
            stringBuilder.append(this.second);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private static final class Any
    extends NamedFastMatcher {
        static final Any INSTANCE = new Any();

        private Any() {
            super("CharMatcher.any()");
        }

        @Override
        public CharMatcher and(CharMatcher charMatcher) {
            return Preconditions.checkNotNull(charMatcher);
        }

        @Override
        public String collapseFrom(CharSequence charSequence, char c) {
            if (charSequence.length() != 0) return String.valueOf(c);
            return "";
        }

        @Override
        public int countIn(CharSequence charSequence) {
            return charSequence.length();
        }

        @Override
        public int indexIn(CharSequence charSequence) {
            if (charSequence.length() != 0) return 0;
            return -1;
        }

        @Override
        public int indexIn(CharSequence charSequence, int n) {
            int n2 = charSequence.length();
            Preconditions.checkPositionIndex(n, n2);
            int n3 = n;
            if (n != n2) return n3;
            return -1;
        }

        @Override
        public int lastIndexIn(CharSequence charSequence) {
            return charSequence.length() - 1;
        }

        @Override
        public boolean matches(char c) {
            return true;
        }

        @Override
        public boolean matchesAllOf(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return true;
        }

        @Override
        public boolean matchesNoneOf(CharSequence charSequence) {
            if (charSequence.length() != 0) return false;
            return true;
        }

        @Override
        public CharMatcher negate() {
            return Any.none();
        }

        @Override
        public CharMatcher or(CharMatcher charMatcher) {
            Preconditions.checkNotNull(charMatcher);
            return this;
        }

        @Override
        public String removeFrom(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return "";
        }

        @Override
        public String replaceFrom(CharSequence arrc, char c) {
            arrc = new char[arrc.length()];
            Arrays.fill(arrc, c);
            return new String(arrc);
        }

        @Override
        public String replaceFrom(CharSequence charSequence, CharSequence charSequence2) {
            StringBuilder stringBuilder = new StringBuilder(charSequence.length() * charSequence2.length());
            int n = 0;
            while (n < charSequence.length()) {
                stringBuilder.append(charSequence2);
                ++n;
            }
            return stringBuilder.toString();
        }

        @Override
        public String trimFrom(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return "";
        }
    }

    private static final class AnyOf
    extends CharMatcher {
        private final char[] chars;

        public AnyOf(CharSequence arrc) {
            arrc = arrc.toString().toCharArray();
            this.chars = arrc;
            Arrays.sort(arrc);
        }

        @Override
        public boolean matches(char c) {
            if (Arrays.binarySearch(this.chars, c) < 0) return false;
            return true;
        }

        @Override
        void setBits(BitSet bitSet) {
            char[] arrc = this.chars;
            int n = arrc.length;
            int n2 = 0;
            while (n2 < n) {
                bitSet.set(arrc[n2]);
                ++n2;
            }
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("CharMatcher.anyOf(\"");
            char[] arrc = this.chars;
            int n = arrc.length;
            int n2 = 0;
            do {
                if (n2 >= n) {
                    stringBuilder.append("\")");
                    return stringBuilder.toString();
                }
                stringBuilder.append(CharMatcher.showCharacter(arrc[n2]));
                ++n2;
            } while (true);
        }
    }

    private static final class Ascii
    extends NamedFastMatcher {
        static final Ascii INSTANCE = new Ascii();

        Ascii() {
            super("CharMatcher.ascii()");
        }

        @Override
        public boolean matches(char c) {
            if (c > '') return false;
            return true;
        }
    }

    private static final class BitSetMatcher
    extends NamedFastMatcher {
        private final BitSet table;

        private BitSetMatcher(BitSet bitSet, String object) {
            super((String)object);
            object = bitSet;
            if (bitSet.length() + 64 < bitSet.size()) {
                object = (BitSet)bitSet.clone();
            }
            this.table = object;
        }

        @Override
        public boolean matches(char c) {
            return this.table.get(c);
        }

        @Override
        void setBits(BitSet bitSet) {
            bitSet.or(this.table);
        }
    }

    private static final class BreakingWhitespace
    extends CharMatcher {
        static final CharMatcher INSTANCE = new BreakingWhitespace();

        private BreakingWhitespace() {
        }

        /*
         * Exception decompiling
         */
        @Override
        public boolean matches(char var1_1) {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
            // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:478)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:376)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
            // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
            // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:778)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:886)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
            // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
            // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
            // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
            // org.benf.cfr.reader.Main.main(Main.java:48)
            // the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler.decompileToZip(CFRDecompiler.java:311)
            // the.bytecode.club.bytecodeviewer.gui.MainViewerGUI$14$1$3.run(MainViewerGUI.java:1211)
            throw new IllegalStateException("Decompilation failed");
        }

        @Override
        public String toString() {
            return "CharMatcher.breakingWhitespace()";
        }
    }

    private static final class Digit
    extends RangesMatcher {
        static final Digit INSTANCE = new Digit();
        private static final String ZEROES = "0\u0660\u06f0\u07c0\u0966\u09e6\u0a66\u0ae6\u0b66\u0be6\u0c66\u0ce6\u0d66\u0de6\u0e50\u0ed0\u0f20\u1040\u1090\u17e0\u1810\u1946\u19d0\u1a80\u1a90\u1b50\u1bb0\u1c40\u1c50\ua620\ua8d0\ua900\ua9d0\ua9f0\uaa50\uabf0\uff10";

        private Digit() {
            super("CharMatcher.digit()", Digit.zeroes(), Digit.nines());
        }

        private static char[] nines() {
            char[] arrc = new char[37];
            int n = 0;
            while (n < 37) {
                arrc[n] = (char)(ZEROES.charAt(n) + 9);
                ++n;
            }
            return arrc;
        }

        private static char[] zeroes() {
            return ZEROES.toCharArray();
        }
    }

    static abstract class FastMatcher
    extends CharMatcher {
        FastMatcher() {
        }

        @Override
        public CharMatcher negate() {
            return new NegatedFastMatcher(this);
        }

        @Override
        public final CharMatcher precomputed() {
            return this;
        }
    }

    private static final class ForPredicate
    extends CharMatcher {
        private final Predicate<? super Character> predicate;

        ForPredicate(Predicate<? super Character> predicate) {
            this.predicate = Preconditions.checkNotNull(predicate);
        }

        @Override
        public boolean apply(Character c) {
            return this.predicate.apply(Preconditions.checkNotNull(c));
        }

        @Override
        public boolean matches(char c) {
            return this.predicate.apply(Character.valueOf(c));
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CharMatcher.forPredicate(");
            stringBuilder.append(this.predicate);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private static final class InRange
    extends FastMatcher {
        private final char endInclusive;
        private final char startInclusive;

        InRange(char c, char c2) {
            boolean bl = c2 >= c;
            Preconditions.checkArgument(bl);
            this.startInclusive = c;
            this.endInclusive = c2;
        }

        @Override
        public boolean matches(char c) {
            if (this.startInclusive > c) return false;
            if (c > this.endInclusive) return false;
            return true;
        }

        @Override
        void setBits(BitSet bitSet) {
            bitSet.set((int)this.startInclusive, this.endInclusive + '\u0001');
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CharMatcher.inRange('");
            stringBuilder.append(CharMatcher.showCharacter(this.startInclusive));
            stringBuilder.append("', '");
            stringBuilder.append(CharMatcher.showCharacter(this.endInclusive));
            stringBuilder.append("')");
            return stringBuilder.toString();
        }
    }

    private static final class Invisible
    extends RangesMatcher {
        static final Invisible INSTANCE = new Invisible();
        private static final String RANGE_ENDS = " \u00a0\u00ad\u0605\u061c\u06dd\u070f\u08e2\u1680\u180e\u200f\u202f\u2064\u206f\u3000\uf8ff\ufeff\ufffb";
        private static final String RANGE_STARTS = "\u0000\u00ad\u0600\u061c\u06dd\u070f\u08e2\u1680\u180e\u2000\u2028\u205f\u2066\u3000\ud800\ufeff\ufff9";

        private Invisible() {
            super("CharMatcher.invisible()", RANGE_STARTS.toCharArray(), RANGE_ENDS.toCharArray());
        }
    }

    private static final class Is
    extends FastMatcher {
        private final char match;

        Is(char c) {
            this.match = c;
        }

        @Override
        public CharMatcher and(CharMatcher charMatcher) {
            if (!charMatcher.matches(this.match)) return Is.none();
            return this;
        }

        @Override
        public boolean matches(char c) {
            if (c != this.match) return false;
            return true;
        }

        @Override
        public CharMatcher negate() {
            return Is.isNot(this.match);
        }

        @Override
        public CharMatcher or(CharMatcher charMatcher) {
            if (!charMatcher.matches(this.match)) return super.or(charMatcher);
            return charMatcher;
        }

        @Override
        public String replaceFrom(CharSequence charSequence, char c) {
            return charSequence.toString().replace(this.match, c);
        }

        @Override
        void setBits(BitSet bitSet) {
            bitSet.set(this.match);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CharMatcher.is('");
            stringBuilder.append(CharMatcher.showCharacter(this.match));
            stringBuilder.append("')");
            return stringBuilder.toString();
        }
    }

    private static final class IsEither
    extends FastMatcher {
        private final char match1;
        private final char match2;

        IsEither(char c, char c2) {
            this.match1 = c;
            this.match2 = c2;
        }

        @Override
        public boolean matches(char c) {
            if (c == this.match1) return true;
            if (c == this.match2) return true;
            return false;
        }

        @Override
        void setBits(BitSet bitSet) {
            bitSet.set(this.match1);
            bitSet.set(this.match2);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CharMatcher.anyOf(\"");
            stringBuilder.append(CharMatcher.showCharacter(this.match1));
            stringBuilder.append(CharMatcher.showCharacter(this.match2));
            stringBuilder.append("\")");
            return stringBuilder.toString();
        }
    }

    private static final class IsNot
    extends FastMatcher {
        private final char match;

        IsNot(char c) {
            this.match = c;
        }

        @Override
        public CharMatcher and(CharMatcher charMatcher) {
            CharMatcher charMatcher2 = charMatcher;
            if (!charMatcher.matches(this.match)) return charMatcher2;
            return super.and(charMatcher);
        }

        @Override
        public boolean matches(char c) {
            if (c == this.match) return false;
            return true;
        }

        @Override
        public CharMatcher negate() {
            return IsNot.is(this.match);
        }

        @Override
        public CharMatcher or(CharMatcher charMatcher) {
            if (!charMatcher.matches(this.match)) return this;
            return IsNot.any();
        }

        @Override
        void setBits(BitSet bitSet) {
            bitSet.set(0, this.match);
            bitSet.set(this.match + '\u0001', 65536);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CharMatcher.isNot('");
            stringBuilder.append(CharMatcher.showCharacter(this.match));
            stringBuilder.append("')");
            return stringBuilder.toString();
        }
    }

    private static final class JavaDigit
    extends CharMatcher {
        static final JavaDigit INSTANCE = new JavaDigit();

        private JavaDigit() {
        }

        @Override
        public boolean matches(char c) {
            return Character.isDigit(c);
        }

        @Override
        public String toString() {
            return "CharMatcher.javaDigit()";
        }
    }

    private static final class JavaIsoControl
    extends NamedFastMatcher {
        static final JavaIsoControl INSTANCE = new JavaIsoControl();

        private JavaIsoControl() {
            super("CharMatcher.javaIsoControl()");
        }

        @Override
        public boolean matches(char c) {
            if (c <= '\u001f') return true;
            if (c < '') return false;
            if (c > '\u009f') return false;
            return true;
        }
    }

    private static final class JavaLetter
    extends CharMatcher {
        static final JavaLetter INSTANCE = new JavaLetter();

        private JavaLetter() {
        }

        @Override
        public boolean matches(char c) {
            return Character.isLetter(c);
        }

        @Override
        public String toString() {
            return "CharMatcher.javaLetter()";
        }
    }

    private static final class JavaLetterOrDigit
    extends CharMatcher {
        static final JavaLetterOrDigit INSTANCE = new JavaLetterOrDigit();

        private JavaLetterOrDigit() {
        }

        @Override
        public boolean matches(char c) {
            return Character.isLetterOrDigit(c);
        }

        @Override
        public String toString() {
            return "CharMatcher.javaLetterOrDigit()";
        }
    }

    private static final class JavaLowerCase
    extends CharMatcher {
        static final JavaLowerCase INSTANCE = new JavaLowerCase();

        private JavaLowerCase() {
        }

        @Override
        public boolean matches(char c) {
            return Character.isLowerCase(c);
        }

        @Override
        public String toString() {
            return "CharMatcher.javaLowerCase()";
        }
    }

    private static final class JavaUpperCase
    extends CharMatcher {
        static final JavaUpperCase INSTANCE = new JavaUpperCase();

        private JavaUpperCase() {
        }

        @Override
        public boolean matches(char c) {
            return Character.isUpperCase(c);
        }

        @Override
        public String toString() {
            return "CharMatcher.javaUpperCase()";
        }
    }

    static abstract class NamedFastMatcher
    extends FastMatcher {
        private final String description;

        NamedFastMatcher(String string2) {
            this.description = Preconditions.checkNotNull(string2);
        }

        @Override
        public final String toString() {
            return this.description;
        }
    }

    private static class Negated
    extends CharMatcher {
        final CharMatcher original;

        Negated(CharMatcher charMatcher) {
            this.original = Preconditions.checkNotNull(charMatcher);
        }

        @Override
        public int countIn(CharSequence charSequence) {
            return charSequence.length() - this.original.countIn(charSequence);
        }

        @Override
        public boolean matches(char c) {
            return this.original.matches(c) ^ true;
        }

        @Override
        public boolean matchesAllOf(CharSequence charSequence) {
            return this.original.matchesNoneOf(charSequence);
        }

        @Override
        public boolean matchesNoneOf(CharSequence charSequence) {
            return this.original.matchesAllOf(charSequence);
        }

        @Override
        public CharMatcher negate() {
            return this.original;
        }

        @Override
        void setBits(BitSet bitSet) {
            BitSet bitSet2 = new BitSet();
            this.original.setBits(bitSet2);
            bitSet2.flip(0, 65536);
            bitSet.or(bitSet2);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.original);
            stringBuilder.append(".negate()");
            return stringBuilder.toString();
        }
    }

    static class NegatedFastMatcher
    extends Negated {
        NegatedFastMatcher(CharMatcher charMatcher) {
            super(charMatcher);
        }

        @Override
        public final CharMatcher precomputed() {
            return this;
        }
    }

    private static final class None
    extends NamedFastMatcher {
        static final None INSTANCE = new None();

        private None() {
            super("CharMatcher.none()");
        }

        @Override
        public CharMatcher and(CharMatcher charMatcher) {
            Preconditions.checkNotNull(charMatcher);
            return this;
        }

        @Override
        public String collapseFrom(CharSequence charSequence, char c) {
            return charSequence.toString();
        }

        @Override
        public int countIn(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return 0;
        }

        @Override
        public int indexIn(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return -1;
        }

        @Override
        public int indexIn(CharSequence charSequence, int n) {
            Preconditions.checkPositionIndex(n, charSequence.length());
            return -1;
        }

        @Override
        public int lastIndexIn(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return -1;
        }

        @Override
        public boolean matches(char c) {
            return false;
        }

        @Override
        public boolean matchesAllOf(CharSequence charSequence) {
            if (charSequence.length() != 0) return false;
            return true;
        }

        @Override
        public boolean matchesNoneOf(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return true;
        }

        @Override
        public CharMatcher negate() {
            return None.any();
        }

        @Override
        public CharMatcher or(CharMatcher charMatcher) {
            return Preconditions.checkNotNull(charMatcher);
        }

        @Override
        public String removeFrom(CharSequence charSequence) {
            return charSequence.toString();
        }

        @Override
        public String replaceFrom(CharSequence charSequence, char c) {
            return charSequence.toString();
        }

        @Override
        public String replaceFrom(CharSequence charSequence, CharSequence charSequence2) {
            Preconditions.checkNotNull(charSequence2);
            return charSequence.toString();
        }

        @Override
        public String trimFrom(CharSequence charSequence) {
            return charSequence.toString();
        }

        @Override
        public String trimLeadingFrom(CharSequence charSequence) {
            return charSequence.toString();
        }

        @Override
        public String trimTrailingFrom(CharSequence charSequence) {
            return charSequence.toString();
        }
    }

    private static final class Or
    extends CharMatcher {
        final CharMatcher first;
        final CharMatcher second;

        Or(CharMatcher charMatcher, CharMatcher charMatcher2) {
            this.first = Preconditions.checkNotNull(charMatcher);
            this.second = Preconditions.checkNotNull(charMatcher2);
        }

        @Override
        public boolean matches(char c) {
            if (this.first.matches(c)) return true;
            if (this.second.matches(c)) return true;
            return false;
        }

        @Override
        void setBits(BitSet bitSet) {
            this.first.setBits(bitSet);
            this.second.setBits(bitSet);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CharMatcher.or(");
            stringBuilder.append(this.first);
            stringBuilder.append(", ");
            stringBuilder.append(this.second);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private static class RangesMatcher
    extends CharMatcher {
        private final String description;
        private final char[] rangeEnds;
        private final char[] rangeStarts;

        RangesMatcher(String string2, char[] arrc, char[] arrc2) {
            this.description = string2;
            this.rangeStarts = arrc;
            this.rangeEnds = arrc2;
            boolean bl = arrc.length == arrc2.length;
            Preconditions.checkArgument(bl);
            int n = 0;
            while (n < arrc.length) {
                bl = arrc[n] <= arrc2[n];
                Preconditions.checkArgument(bl);
                int n2 = n + 1;
                if (n2 < arrc.length) {
                    bl = arrc2[n] < arrc[n2];
                    Preconditions.checkArgument(bl);
                }
                n = n2;
            }
        }

        @Override
        public boolean matches(char c) {
            int n = Arrays.binarySearch(this.rangeStarts, c);
            boolean bl = true;
            if (n >= 0) {
                return true;
            }
            if (--n < 0) return false;
            if (c > this.rangeEnds[n]) return false;
            return bl;
        }

        @Override
        public String toString() {
            return this.description;
        }
    }

    private static final class SingleWidth
    extends RangesMatcher {
        static final SingleWidth INSTANCE = new SingleWidth();

        private SingleWidth() {
            super("CharMatcher.singleWidth()", "\u0000\u05be\u05d0\u05f3\u0600\u0750\u0e00\u1e00\u2100\ufb50\ufe70\uff61".toCharArray(), "\u04f9\u05be\u05ea\u05f4\u06ff\u077f\u0e7f\u20af\u213a\ufdff\ufeff\uffdc".toCharArray());
        }
    }

    static final class Whitespace
    extends NamedFastMatcher {
        static final Whitespace INSTANCE;
        static final int MULTIPLIER = 1682554634;
        static final int SHIFT;
        static final String TABLE = "\u2002\u3000\r\u0085\u200a\u2005\u2000\u3000\u2029\u000b\u3000\u2008\u2003\u205f\u3000\u1680\t \u2006\u2001\u202f\u00a0\f\u2009\u3000\u2004\u3000\u3000\u2028\n\u2007\u3000";

        static {
            SHIFT = Integer.numberOfLeadingZeros(31);
            INSTANCE = new Whitespace();
        }

        Whitespace() {
            super("CharMatcher.whitespace()");
        }

        @Override
        public boolean matches(char c) {
            if (TABLE.charAt(1682554634 * c >>> SHIFT) != c) return false;
            return true;
        }

        @Override
        void setBits(BitSet bitSet) {
            int n = 0;
            while (n < 32) {
                bitSet.set(TABLE.charAt(n));
                ++n;
            }
        }
    }

}

