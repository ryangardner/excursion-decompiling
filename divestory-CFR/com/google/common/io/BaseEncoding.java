/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.io;

import com.google.common.base.Ascii;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;
import com.google.common.io.CharSink;
import com.google.common.io.CharSource;
import com.google.common.math.IntMath;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.RoundingMode;
import java.util.Arrays;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class BaseEncoding {
    private static final BaseEncoding BASE16;
    private static final BaseEncoding BASE32;
    private static final BaseEncoding BASE32_HEX;
    private static final BaseEncoding BASE64;
    private static final BaseEncoding BASE64_URL;

    static {
        Character c = Character.valueOf('=');
        BASE64 = new Base64Encoding("base64()", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/", c);
        BASE64_URL = new Base64Encoding("base64Url()", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_", c);
        BASE32 = new StandardBaseEncoding("base32()", "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567", c);
        BASE32_HEX = new StandardBaseEncoding("base32Hex()", "0123456789ABCDEFGHIJKLMNOPQRSTUV", c);
        BASE16 = new Base16Encoding("base16()", "0123456789ABCDEF");
    }

    BaseEncoding() {
    }

    public static BaseEncoding base16() {
        return BASE16;
    }

    public static BaseEncoding base32() {
        return BASE32;
    }

    public static BaseEncoding base32Hex() {
        return BASE32_HEX;
    }

    public static BaseEncoding base64() {
        return BASE64;
    }

    public static BaseEncoding base64Url() {
        return BASE64_URL;
    }

    private static byte[] extract(byte[] arrby, int n) {
        if (n == arrby.length) {
            return arrby;
        }
        byte[] arrby2 = new byte[n];
        System.arraycopy(arrby, 0, arrby2, 0, n);
        return arrby2;
    }

    static Reader ignoringReader(final Reader reader, final String string2) {
        Preconditions.checkNotNull(reader);
        Preconditions.checkNotNull(string2);
        return new Reader(){

            @Override
            public void close() throws IOException {
                reader.close();
            }

            @Override
            public int read() throws IOException {
                int n;
                do {
                    if ((n = reader.read()) == -1) return n;
                } while (string2.indexOf((char)n) >= 0);
                return n;
            }

            @Override
            public int read(char[] arrc, int n, int n2) throws IOException {
                throw new UnsupportedOperationException();
            }
        };
    }

    static Appendable separatingAppendable(final Appendable appendable, final String string2, final int n) {
        Preconditions.checkNotNull(appendable);
        Preconditions.checkNotNull(string2);
        boolean bl = n > 0;
        Preconditions.checkArgument(bl);
        return new Appendable(){
            int charsUntilSeparator;
            {
                this.charsUntilSeparator = n;
            }

            @Override
            public Appendable append(char c) throws IOException {
                if (this.charsUntilSeparator == 0) {
                    appendable.append(string2);
                    this.charsUntilSeparator = n;
                }
                appendable.append(c);
                --this.charsUntilSeparator;
                return this;
            }

            @Override
            public Appendable append(@NullableDecl CharSequence charSequence) throws IOException {
                throw new UnsupportedOperationException();
            }

            @Override
            public Appendable append(@NullableDecl CharSequence charSequence, int n3, int n2) throws IOException {
                throw new UnsupportedOperationException();
            }
        };
    }

    static Writer separatingWriter(Writer writer, String string2, int n) {
        return new Writer(BaseEncoding.separatingAppendable(writer, string2, n), writer){
            final /* synthetic */ Writer val$delegate;
            final /* synthetic */ Appendable val$seperatingAppendable;
            {
                this.val$seperatingAppendable = appendable;
                this.val$delegate = writer;
            }

            @Override
            public void close() throws IOException {
                this.val$delegate.close();
            }

            @Override
            public void flush() throws IOException {
                this.val$delegate.flush();
            }

            @Override
            public void write(int n) throws IOException {
                this.val$seperatingAppendable.append((char)n);
            }

            @Override
            public void write(char[] arrc, int n, int n2) throws IOException {
                throw new UnsupportedOperationException();
            }
        };
    }

    public abstract boolean canDecode(CharSequence var1);

    public final byte[] decode(CharSequence arrby) {
        try {
            return this.decodeChecked((CharSequence)arrby);
        }
        catch (DecodingException decodingException) {
            throw new IllegalArgumentException(decodingException);
        }
    }

    final byte[] decodeChecked(CharSequence charSequence) throws DecodingException {
        charSequence = this.trimTrailingPadding(charSequence);
        byte[] arrby = new byte[this.maxDecodedSize(charSequence.length())];
        return BaseEncoding.extract(arrby, this.decodeTo(arrby, charSequence));
    }

    abstract int decodeTo(byte[] var1, CharSequence var2) throws DecodingException;

    public final ByteSource decodingSource(final CharSource charSource) {
        Preconditions.checkNotNull(charSource);
        return new ByteSource(){

            @Override
            public InputStream openStream() throws IOException {
                return BaseEncoding.this.decodingStream(charSource.openStream());
            }
        };
    }

    public abstract InputStream decodingStream(Reader var1);

    public String encode(byte[] arrby) {
        return this.encode(arrby, 0, arrby.length);
    }

    public final String encode(byte[] arrby, int n, int n2) {
        Preconditions.checkPositionIndexes(n, n + n2, arrby.length);
        StringBuilder stringBuilder = new StringBuilder(this.maxEncodedSize(n2));
        try {
            this.encodeTo(stringBuilder, arrby, n, n2);
            return stringBuilder.toString();
        }
        catch (IOException iOException) {
            throw new AssertionError(iOException);
        }
    }

    abstract void encodeTo(Appendable var1, byte[] var2, int var3, int var4) throws IOException;

    public final ByteSink encodingSink(final CharSink charSink) {
        Preconditions.checkNotNull(charSink);
        return new ByteSink(){

            @Override
            public OutputStream openStream() throws IOException {
                return BaseEncoding.this.encodingStream(charSink.openStream());
            }
        };
    }

    public abstract OutputStream encodingStream(Writer var1);

    public abstract BaseEncoding lowerCase();

    abstract int maxDecodedSize(int var1);

    abstract int maxEncodedSize(int var1);

    public abstract BaseEncoding omitPadding();

    CharSequence trimTrailingPadding(CharSequence charSequence) {
        return Preconditions.checkNotNull(charSequence);
    }

    public abstract BaseEncoding upperCase();

    public abstract BaseEncoding withPadChar(char var1);

    public abstract BaseEncoding withSeparator(String var1, int var2);

    private static final class Alphabet {
        final int bitsPerChar;
        final int bytesPerChunk;
        private final char[] chars;
        final int charsPerChunk;
        private final byte[] decodabet;
        final int mask;
        private final String name;
        private final boolean[] validPadding;

        Alphabet(String charSequence, char[] arrc) {
            int n;
            this.name = Preconditions.checkNotNull(charSequence);
            this.chars = Preconditions.checkNotNull(arrc);
            try {
                this.bitsPerChar = n = IntMath.log2(arrc.length, RoundingMode.UNNECESSARY);
            }
            catch (ArithmeticException arithmeticException) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Illegal alphabet length ");
                ((StringBuilder)charSequence).append(arrc.length);
                throw new IllegalArgumentException(((StringBuilder)charSequence).toString(), arithmeticException);
            }
            n = Math.min(8, Integer.lowestOneBit(n));
            try {
                this.charsPerChunk = 8 / n;
                this.bytesPerChunk = this.bitsPerChar / n;
            }
            catch (ArithmeticException arithmeticException) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Illegal alphabet ");
                ((StringBuilder)charSequence).append(new String(arrc));
                throw new IllegalArgumentException(((StringBuilder)charSequence).toString(), arithmeticException);
            }
            this.mask = arrc.length - 1;
            charSequence = new byte[128];
            Arrays.fill((byte[])charSequence, (byte)-1);
            int n2 = 0;
            for (n = 0; n < arrc.length; ++n) {
                char c = arrc[n];
                boolean bl = c < '';
                Preconditions.checkArgument(bl, "Non-ASCII character: %s", c);
                bl = charSequence[c] == -1;
                Preconditions.checkArgument(bl, "Duplicate character: %s", c);
                charSequence[c] = (CharSequence)((byte)((byte)n));
            }
            this.decodabet = charSequence;
            charSequence = new boolean[this.charsPerChunk];
            n = n2;
            do {
                if (n >= this.bytesPerChunk) {
                    this.validPadding = charSequence;
                    return;
                }
                charSequence[IntMath.divide((int)(n * 8), (int)this.bitsPerChar, (RoundingMode)RoundingMode.CEILING)] = (CharSequence)true;
                ++n;
            } while (true);
        }

        private boolean hasLowerCase() {
            char[] arrc = this.chars;
            int n = arrc.length;
            int n2 = 0;
            while (n2 < n) {
                if (Ascii.isLowerCase(arrc[n2])) {
                    return true;
                }
                ++n2;
            }
            return false;
        }

        private boolean hasUpperCase() {
            char[] arrc = this.chars;
            int n = arrc.length;
            int n2 = 0;
            while (n2 < n) {
                if (Ascii.isUpperCase(arrc[n2])) {
                    return true;
                }
                ++n2;
            }
            return false;
        }

        boolean canDecode(char c) {
            if (c > '') return false;
            if (this.decodabet[c] == -1) return false;
            return true;
        }

        int decode(char c) throws DecodingException {
            if (c > '') {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unrecognized character: 0x");
                stringBuilder.append(Integer.toHexString(c));
                throw new DecodingException(stringBuilder.toString());
            }
            byte by = this.decodabet[c];
            if (by != -1) return by;
            if (c > ' ' && c != '') {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unrecognized character: ");
                stringBuilder.append(c);
                throw new DecodingException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unrecognized character: 0x");
            stringBuilder.append(Integer.toHexString(c));
            throw new DecodingException(stringBuilder.toString());
        }

        char encode(int n) {
            return this.chars[n];
        }

        public boolean equals(@NullableDecl Object object) {
            if (!(object instanceof Alphabet)) return false;
            object = (Alphabet)object;
            return Arrays.equals(this.chars, ((Alphabet)object).chars);
        }

        public int hashCode() {
            return Arrays.hashCode(this.chars);
        }

        boolean isValidPaddingStartPosition(int n) {
            return this.validPadding[n % this.charsPerChunk];
        }

        Alphabet lowerCase() {
            if (!this.hasUpperCase()) {
                return this;
            }
            Preconditions.checkState(this.hasLowerCase() ^ true, "Cannot call lowerCase() on a mixed-case alphabet");
            char[] arrc = new char[this.chars.length];
            int n = 0;
            do {
                Object object;
                if (n >= ((char[])(object = this.chars)).length) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append(this.name);
                    ((StringBuilder)object).append(".lowerCase()");
                    return new Alphabet(((StringBuilder)object).toString(), arrc);
                }
                arrc[n] = Ascii.toLowerCase(object[n]);
                ++n;
            } while (true);
        }

        public boolean matches(char c) {
            byte[] arrby = this.decodabet;
            if (c >= arrby.length) return false;
            if (arrby[c] == -1) return false;
            return true;
        }

        public String toString() {
            return this.name;
        }

        Alphabet upperCase() {
            if (!this.hasLowerCase()) {
                return this;
            }
            Preconditions.checkState(this.hasUpperCase() ^ true, "Cannot call upperCase() on a mixed-case alphabet");
            char[] arrc = new char[this.chars.length];
            int n = 0;
            do {
                Object object;
                if (n >= ((char[])(object = this.chars)).length) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append(this.name);
                    ((StringBuilder)object).append(".upperCase()");
                    return new Alphabet(((StringBuilder)object).toString(), arrc);
                }
                arrc[n] = Ascii.toUpperCase(object[n]);
                ++n;
            } while (true);
        }
    }

    static final class Base16Encoding
    extends StandardBaseEncoding {
        final char[] encoding = new char[512];

        private Base16Encoding(Alphabet alphabet) {
            super(alphabet, null);
            int n = alphabet.chars.length;
            int n2 = 0;
            boolean bl = n == 16;
            Preconditions.checkArgument(bl);
            while (n2 < 256) {
                this.encoding[n2] = alphabet.encode(n2 >>> 4);
                this.encoding[n2 | 256] = alphabet.encode(n2 & 15);
                ++n2;
            }
        }

        Base16Encoding(String string2, String string3) {
            this(new Alphabet(string2, string3.toCharArray()));
        }

        @Override
        int decodeTo(byte[] object, CharSequence charSequence) throws DecodingException {
            Preconditions.checkNotNull(object);
            if (charSequence.length() % 2 == 1) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Invalid input length ");
                ((StringBuilder)object).append(charSequence.length());
                throw new DecodingException(((StringBuilder)object).toString());
            }
            int n = 0;
            int n2 = 0;
            while (n < charSequence.length()) {
                object[n2] = (byte)(this.alphabet.decode(charSequence.charAt(n)) << 4 | this.alphabet.decode(charSequence.charAt(n + 1)));
                n += 2;
                ++n2;
            }
            return n2;
        }

        @Override
        void encodeTo(Appendable appendable, byte[] arrby, int n, int n2) throws IOException {
            Preconditions.checkNotNull(appendable);
            Preconditions.checkPositionIndexes(n, n + n2, arrby.length);
            int n3 = 0;
            while (n3 < n2) {
                int n4 = arrby[n + n3] & 255;
                appendable.append(this.encoding[n4]);
                appendable.append(this.encoding[n4 | 256]);
                ++n3;
            }
        }

        @Override
        BaseEncoding newInstance(Alphabet alphabet, @NullableDecl Character c) {
            return new Base16Encoding(alphabet);
        }
    }

    static final class Base64Encoding
    extends StandardBaseEncoding {
        private Base64Encoding(Alphabet alphabet, @NullableDecl Character c) {
            super(alphabet, c);
            boolean bl = alphabet.chars.length == 64;
            Preconditions.checkArgument(bl);
        }

        Base64Encoding(String string2, String string3, @NullableDecl Character c) {
            this(new Alphabet(string2, string3.toCharArray()), c);
        }

        @Override
        int decodeTo(byte[] object, CharSequence charSequence) throws DecodingException {
            Preconditions.checkNotNull(object);
            charSequence = this.trimTrailingPadding(charSequence);
            if (!this.alphabet.isValidPaddingStartPosition(charSequence.length())) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Invalid input length ");
                ((StringBuilder)object).append(charSequence.length());
                throw new DecodingException(((StringBuilder)object).toString());
            }
            int n = 0;
            int n2 = 0;
            while (n < charSequence.length()) {
                Alphabet alphabet = this.alphabet;
                int n3 = n + 1;
                n = alphabet.decode(charSequence.charAt(n));
                alphabet = this.alphabet;
                int n4 = n3 + 1;
                int n5 = n << 18 | alphabet.decode(charSequence.charAt(n3)) << 12;
                n3 = n2 + 1;
                object[n2] = (byte)(n5 >>> 16);
                n = n3;
                n2 = n4;
                if (n4 < charSequence.length()) {
                    alphabet = this.alphabet;
                    n = n4 + 1;
                    n2 = n3 + 1;
                    object[n3] = (byte)((n5 |= alphabet.decode(charSequence.charAt(n4)) << 6) >>> 8 & 255);
                    if (n >= charSequence.length()) continue;
                    alphabet = this.alphabet;
                    n4 = n + 1;
                    n3 = alphabet.decode(charSequence.charAt(n));
                    n = n2 + 1;
                    object[n2] = (byte)((n5 | n3) & 255);
                    n2 = n4;
                }
                n4 = n;
                n = n2;
                n2 = n4;
            }
            return n2;
        }

        @Override
        void encodeTo(Appendable appendable, byte[] arrby, int n, int n2) throws IOException {
            Preconditions.checkNotNull(appendable);
            int n3 = n + n2;
            Preconditions.checkPositionIndexes(n, n3, arrby.length);
            do {
                if (n2 < 3) {
                    if (n >= n3) return;
                    this.encodeChunkTo(appendable, arrby, n, n3 - n);
                    return;
                }
                int n4 = n + 1;
                byte by = arrby[n];
                n = n4 + 1;
                n4 = (by & 255) << 16 | (arrby[n4] & 255) << 8 | arrby[n] & 255;
                appendable.append(this.alphabet.encode(n4 >>> 18));
                appendable.append(this.alphabet.encode(n4 >>> 12 & 63));
                appendable.append(this.alphabet.encode(n4 >>> 6 & 63));
                appendable.append(this.alphabet.encode(n4 & 63));
                n2 -= 3;
                ++n;
            } while (true);
        }

        @Override
        BaseEncoding newInstance(Alphabet alphabet, @NullableDecl Character c) {
            return new Base64Encoding(alphabet, c);
        }
    }

    public static final class DecodingException
    extends IOException {
        DecodingException(String string2) {
            super(string2);
        }

        DecodingException(Throwable throwable) {
            super(throwable);
        }
    }

    static final class SeparatedBaseEncoding
    extends BaseEncoding {
        private final int afterEveryChars;
        private final BaseEncoding delegate;
        private final String separator;

        SeparatedBaseEncoding(BaseEncoding baseEncoding, String string2, int n) {
            this.delegate = Preconditions.checkNotNull(baseEncoding);
            this.separator = Preconditions.checkNotNull(string2);
            this.afterEveryChars = n;
            boolean bl = n > 0;
            Preconditions.checkArgument(bl, "Cannot add a separator after every %s chars", n);
        }

        @Override
        public boolean canDecode(CharSequence charSequence) {
            StringBuilder stringBuilder = new StringBuilder();
            int n = 0;
            while (n < charSequence.length()) {
                char c = charSequence.charAt(n);
                if (this.separator.indexOf(c) < 0) {
                    stringBuilder.append(c);
                }
                ++n;
            }
            return this.delegate.canDecode(stringBuilder);
        }

        @Override
        int decodeTo(byte[] arrby, CharSequence charSequence) throws DecodingException {
            StringBuilder stringBuilder = new StringBuilder(charSequence.length());
            int n = 0;
            while (n < charSequence.length()) {
                char c = charSequence.charAt(n);
                if (this.separator.indexOf(c) < 0) {
                    stringBuilder.append(c);
                }
                ++n;
            }
            return this.delegate.decodeTo(arrby, stringBuilder);
        }

        @Override
        public InputStream decodingStream(Reader reader) {
            return this.delegate.decodingStream(SeparatedBaseEncoding.ignoringReader(reader, this.separator));
        }

        @Override
        void encodeTo(Appendable appendable, byte[] arrby, int n, int n2) throws IOException {
            this.delegate.encodeTo(SeparatedBaseEncoding.separatingAppendable(appendable, this.separator, this.afterEveryChars), arrby, n, n2);
        }

        @Override
        public OutputStream encodingStream(Writer writer) {
            return this.delegate.encodingStream(SeparatedBaseEncoding.separatingWriter(writer, this.separator, this.afterEveryChars));
        }

        @Override
        public BaseEncoding lowerCase() {
            return this.delegate.lowerCase().withSeparator(this.separator, this.afterEveryChars);
        }

        @Override
        int maxDecodedSize(int n) {
            return this.delegate.maxDecodedSize(n);
        }

        @Override
        int maxEncodedSize(int n) {
            n = this.delegate.maxEncodedSize(n);
            return n + this.separator.length() * IntMath.divide(Math.max(0, n - 1), this.afterEveryChars, RoundingMode.FLOOR);
        }

        @Override
        public BaseEncoding omitPadding() {
            return this.delegate.omitPadding().withSeparator(this.separator, this.afterEveryChars);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.delegate);
            stringBuilder.append(".withSeparator(\"");
            stringBuilder.append(this.separator);
            stringBuilder.append("\", ");
            stringBuilder.append(this.afterEveryChars);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }

        @Override
        CharSequence trimTrailingPadding(CharSequence charSequence) {
            return this.delegate.trimTrailingPadding(charSequence);
        }

        @Override
        public BaseEncoding upperCase() {
            return this.delegate.upperCase().withSeparator(this.separator, this.afterEveryChars);
        }

        @Override
        public BaseEncoding withPadChar(char c) {
            return this.delegate.withPadChar(c).withSeparator(this.separator, this.afterEveryChars);
        }

        @Override
        public BaseEncoding withSeparator(String string2, int n) {
            throw new UnsupportedOperationException("Already have a separator");
        }
    }

    static class StandardBaseEncoding
    extends BaseEncoding {
        final Alphabet alphabet;
        @MonotonicNonNullDecl
        private transient BaseEncoding lowerCase;
        @NullableDecl
        final Character paddingChar;
        @MonotonicNonNullDecl
        private transient BaseEncoding upperCase;

        StandardBaseEncoding(Alphabet alphabet, @NullableDecl Character c) {
            this.alphabet = Preconditions.checkNotNull(alphabet);
            boolean bl = c == null || !alphabet.matches(c.charValue());
            Preconditions.checkArgument(bl, "Padding character %s was already in alphabet", (Object)c);
            this.paddingChar = c;
        }

        StandardBaseEncoding(String string2, String string3, @NullableDecl Character c) {
            this(new Alphabet(string2, string3.toCharArray()), c);
        }

        @Override
        public boolean canDecode(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            charSequence = this.trimTrailingPadding(charSequence);
            if (!this.alphabet.isValidPaddingStartPosition(charSequence.length())) {
                return false;
            }
            int n = 0;
            while (n < charSequence.length()) {
                if (!this.alphabet.canDecode(charSequence.charAt(n))) {
                    return false;
                }
                ++n;
            }
            return true;
        }

        @Override
        int decodeTo(byte[] object, CharSequence charSequence) throws DecodingException {
            Preconditions.checkNotNull(object);
            charSequence = this.trimTrailingPadding(charSequence);
            if (!this.alphabet.isValidPaddingStartPosition(charSequence.length())) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Invalid input length ");
                ((StringBuilder)object).append(charSequence.length());
                throw new DecodingException(((StringBuilder)object).toString());
            }
            int n = 0;
            int n2 = 0;
            while (n < charSequence.length()) {
                int n3;
                int n4;
                long l = 0L;
                int n5 = 0;
                for (n3 = 0; n3 < this.alphabet.charsPerChunk; ++n3) {
                    long l2;
                    l = l2 = l << this.alphabet.bitsPerChar;
                    n4 = n5;
                    if (n + n3 < charSequence.length()) {
                        l = l2 | (long)this.alphabet.decode(charSequence.charAt(n5 + n));
                        n4 = n5 + 1;
                    }
                    n5 = n4;
                }
                n3 = this.alphabet.bytesPerChunk;
                int n6 = this.alphabet.bitsPerChar;
                for (n4 = (this.alphabet.bytesPerChunk - 1) * 8; n4 >= n3 * 8 - n5 * n6; n4 -= 8, ++n2) {
                    object[n2] = (byte)(l >>> n4 & 255L);
                }
                n += this.alphabet.charsPerChunk;
            }
            return n2;
        }

        @Override
        public InputStream decodingStream(final Reader reader) {
            Preconditions.checkNotNull(reader);
            return new InputStream(){
                int bitBuffer = 0;
                int bitBufferLength = 0;
                boolean hitPadding = false;
                int readChars = 0;

                @Override
                public void close() throws IOException {
                    reader.close();
                }

                @Override
                public int read() throws IOException {
                    block6 : {
                        int n;
                        do {
                            if ((n = reader.read()) == -1) {
                                if (this.hitPadding) return -1;
                                if (StandardBaseEncoding.this.alphabet.isValidPaddingStartPosition(this.readChars)) {
                                    return -1;
                                }
                                break block6;
                            }
                            ++this.readChars;
                            char c = (char)n;
                            if (StandardBaseEncoding.this.paddingChar != null && StandardBaseEncoding.this.paddingChar.charValue() == c) {
                                if (!(this.hitPadding || this.readChars != 1 && StandardBaseEncoding.this.alphabet.isValidPaddingStartPosition(this.readChars - 1))) {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append("Padding cannot start at index ");
                                    stringBuilder.append(this.readChars);
                                    throw new DecodingException(stringBuilder.toString());
                                }
                                this.hitPadding = true;
                                continue;
                            }
                            if (this.hitPadding) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Expected padding character but found '");
                                stringBuilder.append(c);
                                stringBuilder.append("' at index ");
                                stringBuilder.append(this.readChars);
                                throw new DecodingException(stringBuilder.toString());
                            }
                            this.bitBuffer = n = this.bitBuffer << StandardBaseEncoding.this.alphabet.bitsPerChar;
                            this.bitBuffer = StandardBaseEncoding.this.alphabet.decode(c) | n;
                            this.bitBufferLength = n = this.bitBufferLength + StandardBaseEncoding.this.alphabet.bitsPerChar;
                            if (n >= 8) break;
                        } while (true);
                        this.bitBufferLength = n -= 8;
                        return this.bitBuffer >> n & 255;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Invalid input length ");
                    stringBuilder.append(this.readChars);
                    throw new DecodingException(stringBuilder.toString());
                }

                @Override
                public int read(byte[] arrby, int n, int n2) throws IOException {
                    int n3 = n2 + n;
                    Preconditions.checkPositionIndexes(n, n3, arrby.length);
                    n2 = n;
                    while (n2 < n3) {
                        int n4 = this.read();
                        int n5 = -1;
                        if (n4 == -1) {
                            if ((n = n2 - n) != 0) return n;
                            return n5;
                        }
                        arrby[n2] = (byte)n4;
                        ++n2;
                    }
                    return n2 - n;
                }
            };
        }

        void encodeChunkTo(Appendable appendable, byte[] arrby, int n, int n2) throws IOException {
            Preconditions.checkNotNull(appendable);
            Preconditions.checkPositionIndexes(n, n + n2, arrby.length);
            int n3 = this.alphabet.bytesPerChunk;
            int n4 = 0;
            boolean bl = n2 <= n3;
            Preconditions.checkArgument(bl);
            long l = 0L;
            for (n3 = 0; n3 < n2; ++n3) {
                l = (l | (long)(arrby[n + n3] & 255)) << 8;
            }
            n3 = this.alphabet.bitsPerChar;
            for (n = n4; n < n2 * 8; n += this.alphabet.bitsPerChar) {
                int n5 = (int)(l >>> (n2 + 1) * 8 - n3 - n);
                n4 = this.alphabet.mask;
                appendable.append(this.alphabet.encode(n5 & n4));
            }
            if (this.paddingChar == null) return;
            while (n < this.alphabet.bytesPerChunk * 8) {
                appendable.append(this.paddingChar.charValue());
                n += this.alphabet.bitsPerChar;
            }
        }

        @Override
        void encodeTo(Appendable appendable, byte[] arrby, int n, int n2) throws IOException {
            Preconditions.checkNotNull(appendable);
            Preconditions.checkPositionIndexes(n, n + n2, arrby.length);
            int n3 = 0;
            while (n3 < n2) {
                this.encodeChunkTo(appendable, arrby, n + n3, Math.min(this.alphabet.bytesPerChunk, n2 - n3));
                n3 += this.alphabet.bytesPerChunk;
            }
        }

        @Override
        public OutputStream encodingStream(final Writer writer) {
            Preconditions.checkNotNull(writer);
            return new OutputStream(){
                int bitBuffer = 0;
                int bitBufferLength = 0;
                int writtenChars = 0;

                @Override
                public void close() throws IOException {
                    if (this.bitBufferLength > 0) {
                        int n = this.bitBuffer;
                        int n2 = StandardBaseEncoding.this.alphabet.bitsPerChar;
                        int n3 = this.bitBufferLength;
                        int n4 = StandardBaseEncoding.this.alphabet.mask;
                        writer.write(StandardBaseEncoding.this.alphabet.encode(n << n2 - n3 & n4));
                        ++this.writtenChars;
                        if (StandardBaseEncoding.this.paddingChar != null) {
                            while (this.writtenChars % StandardBaseEncoding.this.alphabet.charsPerChunk != 0) {
                                writer.write(StandardBaseEncoding.this.paddingChar.charValue());
                                ++this.writtenChars;
                            }
                        }
                    }
                    writer.close();
                }

                @Override
                public void flush() throws IOException {
                    writer.flush();
                }

                @Override
                public void write(int n) throws IOException {
                    int n2;
                    this.bitBuffer = n2 = this.bitBuffer << 8;
                    this.bitBuffer = n & 255 | n2;
                    this.bitBufferLength += 8;
                    while (this.bitBufferLength >= StandardBaseEncoding.this.alphabet.bitsPerChar) {
                        n = this.bitBuffer;
                        int n3 = this.bitBufferLength;
                        n2 = StandardBaseEncoding.this.alphabet.bitsPerChar;
                        int n4 = StandardBaseEncoding.this.alphabet.mask;
                        writer.write(StandardBaseEncoding.this.alphabet.encode(n >> n3 - n2 & n4));
                        ++this.writtenChars;
                        this.bitBufferLength -= StandardBaseEncoding.this.alphabet.bitsPerChar;
                    }
                }
            };
        }

        public boolean equals(@NullableDecl Object object) {
            boolean bl;
            boolean bl2 = object instanceof StandardBaseEncoding;
            boolean bl3 = bl = false;
            if (!bl2) return bl3;
            object = (StandardBaseEncoding)object;
            bl3 = bl;
            if (!this.alphabet.equals(((StandardBaseEncoding)object).alphabet)) return bl3;
            bl3 = bl;
            if (!Objects.equal(this.paddingChar, ((StandardBaseEncoding)object).paddingChar)) return bl3;
            return true;
        }

        public int hashCode() {
            return this.alphabet.hashCode() ^ Objects.hashCode(this.paddingChar);
        }

        @Override
        public BaseEncoding lowerCase() {
            BaseEncoding baseEncoding = this.lowerCase;
            Object object = baseEncoding;
            if (baseEncoding != null) return object;
            object = this.alphabet.lowerCase();
            object = object == this.alphabet ? this : this.newInstance((Alphabet)object, this.paddingChar);
            this.lowerCase = object;
            return object;
        }

        @Override
        int maxDecodedSize(int n) {
            return (int)(((long)this.alphabet.bitsPerChar * (long)n + 7L) / 8L);
        }

        @Override
        int maxEncodedSize(int n) {
            return this.alphabet.charsPerChunk * IntMath.divide(n, this.alphabet.bytesPerChunk, RoundingMode.CEILING);
        }

        BaseEncoding newInstance(Alphabet alphabet, @NullableDecl Character c) {
            return new StandardBaseEncoding(alphabet, c);
        }

        @Override
        public BaseEncoding omitPadding() {
            if (this.paddingChar != null) return this.newInstance(this.alphabet, null);
            return this;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("BaseEncoding.");
            stringBuilder.append(this.alphabet.toString());
            if (8 % this.alphabet.bitsPerChar == 0) return stringBuilder.toString();
            if (this.paddingChar == null) {
                stringBuilder.append(".omitPadding()");
                return stringBuilder.toString();
            }
            stringBuilder.append(".withPadChar('");
            stringBuilder.append(this.paddingChar);
            stringBuilder.append("')");
            return stringBuilder.toString();
        }

        @Override
        CharSequence trimTrailingPadding(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            Character c = this.paddingChar;
            if (c == null) {
                return charSequence;
            }
            char c2 = c.charValue();
            int n = charSequence.length() - 1;
            while (n >= 0) {
                if (charSequence.charAt(n) != c2) {
                    return charSequence.subSequence(0, n + 1);
                }
                --n;
            }
            return charSequence.subSequence(0, n + 1);
        }

        @Override
        public BaseEncoding upperCase() {
            BaseEncoding baseEncoding = this.upperCase;
            Object object = baseEncoding;
            if (baseEncoding != null) return object;
            object = this.alphabet.upperCase();
            object = object == this.alphabet ? this : this.newInstance((Alphabet)object, this.paddingChar);
            this.upperCase = object;
            return object;
        }

        @Override
        public BaseEncoding withPadChar(char c) {
            if (8 % this.alphabet.bitsPerChar == 0) return this;
            Character c2 = this.paddingChar;
            if (c2 == null) return this.newInstance(this.alphabet, Character.valueOf(c));
            if (c2.charValue() != c) return this.newInstance(this.alphabet, Character.valueOf(c));
            return this;
        }

        @Override
        public BaseEncoding withSeparator(String string2, int n) {
            boolean bl = false;
            for (int i = 0; i < string2.length(); ++i) {
                Preconditions.checkArgument(this.alphabet.matches(string2.charAt(i)) ^ true, "Separator (%s) cannot contain alphabet characters", (Object)string2);
            }
            Character c = this.paddingChar;
            if (c == null) return new SeparatedBaseEncoding(this, string2, n);
            if (string2.indexOf(c.charValue()) < 0) {
                bl = true;
            }
            Preconditions.checkArgument(bl, "Separator (%s) cannot contain padding character", (Object)string2);
            return new SeparatedBaseEncoding(this, string2, n);
        }

    }

}

