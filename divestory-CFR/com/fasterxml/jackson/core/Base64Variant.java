/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import java.io.Serializable;
import java.util.Arrays;

public final class Base64Variant
implements Serializable {
    public static final int BASE64_VALUE_INVALID = -1;
    public static final int BASE64_VALUE_PADDING = -2;
    private static final int INT_SPACE = 32;
    static final char PADDING_CHAR_NONE = '\u0000';
    private static final long serialVersionUID = 1L;
    private final transient int[] _asciiToBase64 = new int[128];
    private final transient byte[] _base64ToAsciiB;
    private final transient char[] _base64ToAsciiC = new char[64];
    private final transient int _maxLineLength;
    final String _name;
    private final transient char _paddingChar;
    private final transient boolean _usesPadding;

    public Base64Variant(Base64Variant base64Variant, String string2, int n) {
        this(base64Variant, string2, base64Variant._usesPadding, base64Variant._paddingChar, n);
    }

    public Base64Variant(Base64Variant arrn, String arrobject, boolean bl, char c, int n) {
        byte[] arrby = new byte[64];
        this._base64ToAsciiB = arrby;
        this._name = arrobject;
        arrobject = arrn._base64ToAsciiB;
        System.arraycopy(arrobject, 0, arrby, 0, arrobject.length);
        arrobject = arrn._base64ToAsciiC;
        System.arraycopy(arrobject, 0, this._base64ToAsciiC, 0, arrobject.length);
        arrn = arrn._asciiToBase64;
        System.arraycopy(arrn, 0, this._asciiToBase64, 0, arrn.length);
        this._usesPadding = bl;
        this._paddingChar = c;
        this._maxLineLength = n;
    }

    public Base64Variant(String object, String string2, boolean bl, char c, int n) {
        this._base64ToAsciiB = new byte[64];
        this._name = object;
        this._usesPadding = bl;
        this._paddingChar = c;
        this._maxLineLength = n;
        int n2 = string2.length();
        if (n2 != 64) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Base64Alphabet length must be exactly 64 (was ");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append(")");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        object = this._base64ToAsciiC;
        n = 0;
        string2.getChars(0, n2, (char[])object, 0);
        Arrays.fill(this._asciiToBase64, -1);
        do {
            if (n >= n2) {
                if (!bl) return;
                this._asciiToBase64[c] = -2;
                return;
            }
            char c2 = this._base64ToAsciiC[n];
            this._base64ToAsciiB[n] = (byte)c2;
            this._asciiToBase64[c2] = n++;
        } while (true);
    }

    protected void _reportBase64EOF() throws IllegalArgumentException {
        throw new IllegalArgumentException(this.missingPaddingMessage());
    }

    protected void _reportInvalidBase64(char c, int n, String string2) throws IllegalArgumentException {
        CharSequence charSequence;
        if (c <= ' ') {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Illegal white space character (code 0x");
            ((StringBuilder)charSequence).append(Integer.toHexString(c));
            ((StringBuilder)charSequence).append(") as character #");
            ((StringBuilder)charSequence).append(n + 1);
            ((StringBuilder)charSequence).append(" of 4-char base64 unit: can only used between units");
            charSequence = ((StringBuilder)charSequence).toString();
        } else if (this.usesPaddingChar(c)) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Unexpected padding character ('");
            ((StringBuilder)charSequence).append(this.getPaddingChar());
            ((StringBuilder)charSequence).append("') as character #");
            ((StringBuilder)charSequence).append(n + 1);
            ((StringBuilder)charSequence).append(" of 4-char base64 unit: padding only legal as 3rd or 4th character");
            charSequence = ((StringBuilder)charSequence).toString();
        } else if (Character.isDefined(c) && !Character.isISOControl(c)) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Illegal character '");
            ((StringBuilder)charSequence).append(c);
            ((StringBuilder)charSequence).append("' (code 0x");
            ((StringBuilder)charSequence).append(Integer.toHexString(c));
            ((StringBuilder)charSequence).append(") in base64 content");
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Illegal character (code 0x");
            ((StringBuilder)charSequence).append(Integer.toHexString(c));
            ((StringBuilder)charSequence).append(") in base64 content");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        CharSequence charSequence2 = charSequence;
        if (string2 == null) throw new IllegalArgumentException((String)charSequence2);
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append(": ");
        ((StringBuilder)charSequence2).append(string2);
        charSequence2 = ((StringBuilder)charSequence2).toString();
        throw new IllegalArgumentException((String)charSequence2);
    }

    public void decode(String string2, ByteArrayBuilder byteArrayBuilder) throws IllegalArgumentException {
        int n = string2.length();
        int n2 = 0;
        while (n2 < n) {
            int n3 = n2 + 1;
            char c = string2.charAt(n2);
            n2 = n3;
            if (c <= ' ') continue;
            int n4 = this.decodeBase64Char(c);
            if (n4 < 0) {
                this._reportInvalidBase64(c, 0, null);
            }
            if (n3 >= n) {
                this._reportBase64EOF();
            }
            n2 = n3 + 1;
            c = string2.charAt(n3);
            n3 = this.decodeBase64Char(c);
            if (n3 < 0) {
                this._reportInvalidBase64(c, 1, null);
            }
            n4 = n4 << 6 | n3;
            if (n2 >= n) {
                if (!this.usesPadding()) {
                    byteArrayBuilder.append(n4 >> 4);
                    return;
                }
                this._reportBase64EOF();
            }
            n3 = n2 + 1;
            c = string2.charAt(n2);
            n2 = this.decodeBase64Char(c);
            if (n2 < 0) {
                if (n2 != -2) {
                    this._reportInvalidBase64(c, 2, null);
                }
                if (n3 >= n) {
                    this._reportBase64EOF();
                }
                n2 = n3 + 1;
                c = string2.charAt(n3);
                if (!this.usesPaddingChar(c)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("expected padding character '");
                    stringBuilder.append(this.getPaddingChar());
                    stringBuilder.append("'");
                    this._reportInvalidBase64(c, 3, stringBuilder.toString());
                }
                byteArrayBuilder.append(n4 >> 4);
                continue;
            }
            n4 = n4 << 6 | n2;
            if (n3 >= n) {
                if (!this.usesPadding()) {
                    byteArrayBuilder.appendTwoBytes(n4 >> 2);
                    return;
                }
                this._reportBase64EOF();
            }
            n2 = n3 + 1;
            c = string2.charAt(n3);
            n3 = this.decodeBase64Char(c);
            if (n3 < 0) {
                if (n3 != -2) {
                    this._reportInvalidBase64(c, 3, null);
                }
                byteArrayBuilder.appendTwoBytes(n4 >> 2);
                continue;
            }
            byteArrayBuilder.appendThreeBytes(n4 << 6 | n3);
        }
        return;
    }

    public byte[] decode(String string2) throws IllegalArgumentException {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder();
        this.decode(string2, byteArrayBuilder);
        return byteArrayBuilder.toByteArray();
    }

    public int decodeBase64Byte(byte by) {
        if (by >= 0) return this._asciiToBase64[by];
        return -1;
    }

    public int decodeBase64Char(char c) {
        if (c > '') return (char)-1;
        return (char)this._asciiToBase64[c];
    }

    public int decodeBase64Char(int n) {
        if (n > 127) return -1;
        return this._asciiToBase64[n];
    }

    public String encode(byte[] arrby) {
        return this.encode(arrby, false);
    }

    public String encode(byte[] arrby, boolean bl) {
        int n;
        int n2 = arrby.length;
        StringBuilder stringBuilder = new StringBuilder((n2 >> 2) + n2 + (n2 >> 3));
        if (bl) {
            stringBuilder.append('\"');
        }
        int n3 = this.getMaxLineLength() >> 2;
        int n4 = 0;
        while (n4 <= n2 - 3) {
            int n5 = n4 + 1;
            n4 = arrby[n4];
            n = n5 + 1;
            this.encodeBase64Chunk(stringBuilder, (n4 << 8 | arrby[n5] & 255) << 8 | arrby[n] & 255);
            n4 = --n3;
            if (n3 <= 0) {
                stringBuilder.append('\\');
                stringBuilder.append('n');
                n4 = this.getMaxLineLength() >> 2;
            }
            n3 = n4;
            n4 = ++n;
        }
        if ((n2 -= n4) > 0) {
            n3 = n = arrby[n4] << 16;
            if (n2 == 2) {
                n3 = n | (arrby[n4 + 1] & 255) << 8;
            }
            this.encodeBase64Partial(stringBuilder, n3, n2);
        }
        if (!bl) return stringBuilder.toString();
        stringBuilder.append('\"');
        return stringBuilder.toString();
    }

    public String encode(byte[] arrby, boolean bl, String string2) {
        int n;
        int n2 = arrby.length;
        StringBuilder stringBuilder = new StringBuilder((n2 >> 2) + n2 + (n2 >> 3));
        if (bl) {
            stringBuilder.append('\"');
        }
        int n3 = this.getMaxLineLength() >> 2;
        int n4 = 0;
        while (n4 <= n2 - 3) {
            int n5 = n4 + 1;
            n4 = arrby[n4];
            n = n5 + 1;
            this.encodeBase64Chunk(stringBuilder, (n4 << 8 | arrby[n5] & 255) << 8 | arrby[n] & 255);
            n4 = --n3;
            if (n3 <= 0) {
                stringBuilder.append(string2);
                n4 = this.getMaxLineLength() >> 2;
            }
            n3 = n4;
            n4 = ++n;
        }
        if ((n2 -= n4) > 0) {
            n3 = n = arrby[n4] << 16;
            if (n2 == 2) {
                n3 = n | (arrby[n4 + 1] & 255) << 8;
            }
            this.encodeBase64Partial(stringBuilder, n3, n2);
        }
        if (!bl) return stringBuilder.toString();
        stringBuilder.append('\"');
        return stringBuilder.toString();
    }

    public byte encodeBase64BitsAsByte(int n) {
        return this._base64ToAsciiB[n];
    }

    public char encodeBase64BitsAsChar(int n) {
        return this._base64ToAsciiC[n];
    }

    public int encodeBase64Chunk(int n, byte[] arrby, int n2) {
        int n3 = n2 + 1;
        byte[] arrby2 = this._base64ToAsciiB;
        arrby[n2] = arrby2[n >> 18 & 63];
        n2 = n3 + 1;
        arrby[n3] = arrby2[n >> 12 & 63];
        n3 = n2 + 1;
        arrby[n2] = arrby2[n >> 6 & 63];
        arrby[n3] = arrby2[n & 63];
        return n3 + 1;
    }

    public int encodeBase64Chunk(int n, char[] arrc, int n2) {
        int n3 = n2 + 1;
        char[] arrc2 = this._base64ToAsciiC;
        arrc[n2] = arrc2[n >> 18 & 63];
        n2 = n3 + 1;
        arrc[n3] = arrc2[n >> 12 & 63];
        n3 = n2 + 1;
        arrc[n2] = arrc2[n >> 6 & 63];
        arrc[n3] = arrc2[n & 63];
        return n3 + 1;
    }

    public void encodeBase64Chunk(StringBuilder stringBuilder, int n) {
        stringBuilder.append(this._base64ToAsciiC[n >> 18 & 63]);
        stringBuilder.append(this._base64ToAsciiC[n >> 12 & 63]);
        stringBuilder.append(this._base64ToAsciiC[n >> 6 & 63]);
        stringBuilder.append(this._base64ToAsciiC[n & 63]);
    }

    public int encodeBase64Partial(int n, int n2, byte[] arrby, int n3) {
        int n4 = n3 + 1;
        byte[] arrby2 = this._base64ToAsciiB;
        arrby[n3] = arrby2[n >> 18 & 63];
        int n5 = n4 + 1;
        arrby[n4] = arrby2[n >> 12 & 63];
        if (!this._usesPadding) {
            n3 = n5;
            if (n2 != 2) return n3;
            arrby[n5] = arrby2[n >> 6 & 63];
            return n5 + 1;
        }
        n3 = (byte)this._paddingChar;
        n4 = n5 + 1;
        n = n2 == 2 ? arrby2[n >> 6 & 63] : n3;
        arrby[n5] = (byte)n;
        n = n4 + 1;
        arrby[n4] = (byte)n3;
        return n;
    }

    public int encodeBase64Partial(int n, int n2, char[] arrc, int n3) {
        int n4 = n3 + 1;
        char[] arrc2 = this._base64ToAsciiC;
        arrc[n3] = arrc2[n >> 18 & 63];
        int n5 = n4 + 1;
        arrc[n4] = arrc2[n >> 12 & 63];
        if (!this._usesPadding) {
            n3 = n5;
            if (n2 != 2) return n3;
            arrc[n5] = arrc2[n >> 6 & 63];
            return n5 + 1;
        }
        n4 = n5 + 1;
        n = n2 == 2 ? arrc2[n >> 6 & 63] : (int)this._paddingChar;
        arrc[n5] = (char)n;
        n3 = n4 + 1;
        arrc[n4] = this._paddingChar;
        return n3;
    }

    public void encodeBase64Partial(StringBuilder stringBuilder, int n, int n2) {
        stringBuilder.append(this._base64ToAsciiC[n >> 18 & 63]);
        stringBuilder.append(this._base64ToAsciiC[n >> 12 & 63]);
        if (!this._usesPadding) {
            if (n2 != 2) return;
            stringBuilder.append(this._base64ToAsciiC[n >> 6 & 63]);
            return;
        }
        int n3 = n2 == 2 ? (n = this._base64ToAsciiC[n >> 6 & 63]) : (n = (int)this._paddingChar);
        stringBuilder.append((char)n3);
        stringBuilder.append(this._paddingChar);
    }

    public boolean equals(Object object) {
        if (object != this) return false;
        return true;
    }

    public int getMaxLineLength() {
        return this._maxLineLength;
    }

    public String getName() {
        return this._name;
    }

    public byte getPaddingByte() {
        return (byte)this._paddingChar;
    }

    public char getPaddingChar() {
        return this._paddingChar;
    }

    public int hashCode() {
        return this._name.hashCode();
    }

    public String missingPaddingMessage() {
        return String.format("Unexpected end of base64-encoded String: base64 variant '%s' expects padding (one or more '%c' characters) at the end", this.getName(), Character.valueOf(this.getPaddingChar()));
    }

    protected Object readResolve() {
        return Base64Variants.valueOf(this._name);
    }

    public String toString() {
        return this._name;
    }

    public boolean usesPadding() {
        return this._usesPadding;
    }

    public boolean usesPaddingChar(char c) {
        if (c != this._paddingChar) return false;
        return true;
    }

    public boolean usesPaddingChar(int n) {
        if (n != this._paddingChar) return false;
        return true;
    }
}

