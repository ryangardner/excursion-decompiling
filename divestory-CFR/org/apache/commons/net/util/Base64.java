/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.commons.net.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

public class Base64 {
    private static final byte[] CHUNK_SEPARATOR = new byte[]{13, 10};
    static final int CHUNK_SIZE = 76;
    private static final byte[] DECODE_TABLE;
    private static final int DEFAULT_BUFFER_RESIZE_FACTOR = 2;
    private static final int DEFAULT_BUFFER_SIZE = 8192;
    private static final byte[] EMPTY_BYTE_ARRAY;
    private static final int MASK_6BITS = 63;
    private static final int MASK_8BITS = 255;
    private static final byte PAD = 61;
    private static final byte[] STANDARD_ENCODE_TABLE;
    private static final byte[] URL_SAFE_ENCODE_TABLE;
    private byte[] buffer;
    private int currentLinePos;
    private final int decodeSize;
    private final int encodeSize;
    private final byte[] encodeTable;
    private boolean eof;
    private final int lineLength;
    private final byte[] lineSeparator;
    private int modulus;
    private int pos;
    private int readPos;
    private int x;

    static {
        EMPTY_BYTE_ARRAY = new byte[0];
        STANDARD_ENCODE_TABLE = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
        URL_SAFE_ENCODE_TABLE = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
        DECODE_TABLE = new byte[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, 62, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51};
    }

    public Base64() {
        this(false);
    }

    public Base64(int n) {
        this(n, CHUNK_SEPARATOR);
    }

    public Base64(int n, byte[] arrby) {
        this(n, arrby, false);
    }

    public Base64(int n, byte[] object, boolean bl) {
        Object object2 = object;
        if (object == null) {
            object2 = EMPTY_BYTE_ARRAY;
            n = 0;
        }
        int n2 = n > 0 ? n / 4 * 4 : 0;
        this.lineLength = n2;
        object = new byte[((byte[])object2).length];
        this.lineSeparator = object;
        System.arraycopy(object2, 0, object, 0, ((byte[])object2).length);
        this.encodeSize = n > 0 ? ((byte[])object2).length + 4 : 4;
        this.decodeSize = this.encodeSize - 1;
        if (Base64.containsBase64Byte((byte[])object2)) {
            object = Base64.newStringUtf8((byte[])object2);
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("lineSeperator must not contain base64 characters: [");
            ((StringBuilder)object2).append((String)object);
            ((StringBuilder)object2).append("]");
            throw new IllegalArgumentException(((StringBuilder)object2).toString());
        }
        object = bl ? URL_SAFE_ENCODE_TABLE : STANDARD_ENCODE_TABLE;
        this.encodeTable = object;
    }

    public Base64(boolean bl) {
        this(76, CHUNK_SEPARATOR, bl);
    }

    private static boolean containsBase64Byte(byte[] arrby) {
        int n = arrby.length;
        int n2 = 0;
        while (n2 < n) {
            if (Base64.isBase64(arrby[n2])) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    public static byte[] decodeBase64(String string2) {
        return new Base64().decode(string2);
    }

    public static byte[] decodeBase64(byte[] arrby) {
        return new Base64().decode(arrby);
    }

    public static BigInteger decodeInteger(byte[] arrby) {
        return new BigInteger(1, Base64.decodeBase64(arrby));
    }

    public static byte[] encodeBase64(byte[] arrby) {
        return Base64.encodeBase64(arrby, false);
    }

    public static byte[] encodeBase64(byte[] arrby, boolean bl) {
        return Base64.encodeBase64(arrby, bl, false);
    }

    public static byte[] encodeBase64(byte[] arrby, boolean bl, boolean bl2) {
        return Base64.encodeBase64(arrby, bl, bl2, Integer.MAX_VALUE);
    }

    public static byte[] encodeBase64(byte[] object, boolean bl, boolean bl2, int n) {
        Object object2;
        if (object == null) return object;
        if (((byte[])object).length == 0) {
            return object;
        }
        int n2 = bl ? 76 : 0;
        long l = Base64.getEncodeLength((byte[])object, n2, object2 = bl ? CHUNK_SEPARATOR : EMPTY_BYTE_ARRAY);
        if (l > (long)n) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Input array too big, the output array would be bigger (");
            ((StringBuilder)object).append(l);
            ((StringBuilder)object).append(") than the specified maxium size of ");
            ((StringBuilder)object).append(n);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        if (bl) {
            object2 = new Base64(bl2);
            return object2.encode((byte[])object);
        }
        object2 = new Base64(0, CHUNK_SEPARATOR, bl2);
        return object2.encode((byte[])object);
    }

    public static byte[] encodeBase64Chunked(byte[] arrby) {
        return Base64.encodeBase64(arrby, true);
    }

    public static String encodeBase64String(byte[] arrby) {
        return Base64.newStringUtf8(Base64.encodeBase64(arrby, true));
    }

    public static String encodeBase64String(byte[] arrby, boolean bl) {
        return Base64.newStringUtf8(Base64.encodeBase64(arrby, bl));
    }

    public static String encodeBase64StringUnChunked(byte[] arrby) {
        return Base64.newStringUtf8(Base64.encodeBase64(arrby, false));
    }

    public static byte[] encodeBase64URLSafe(byte[] arrby) {
        return Base64.encodeBase64(arrby, false, true);
    }

    public static String encodeBase64URLSafeString(byte[] arrby) {
        return Base64.newStringUtf8(Base64.encodeBase64(arrby, false, true));
    }

    public static byte[] encodeInteger(BigInteger bigInteger) {
        if (bigInteger == null) throw new NullPointerException("encodeInteger called with null parameter");
        return Base64.encodeBase64(Base64.toIntegerBytes(bigInteger), false);
    }

    private byte[] getBytesUtf8(String arrby) {
        try {
            return arrby.getBytes("UTF8");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException(unsupportedEncodingException);
        }
    }

    private static long getEncodeLength(byte[] arrby, int n, byte[] arrby2) {
        n = n / 4 * 4;
        long l = arrby.length * 4 / 3;
        long l2 = l % 4L;
        long l3 = l;
        if (l2 != 0L) {
            l3 = l + (4L - l2);
        }
        l = l3;
        if (n <= 0) return l;
        l = n;
        n = l3 % l == 0L ? 1 : 0;
        l3 += l3 / l * (long)arrby2.length;
        l = l3;
        if (n != 0) return l;
        return l3 + (long)arrby2.length;
    }

    public static boolean isArrayByteBase64(byte[] arrby) {
        int n = 0;
        while (n < arrby.length) {
            if (!Base64.isBase64(arrby[n]) && !Base64.isWhiteSpace(arrby[n])) {
                return false;
            }
            ++n;
        }
        return true;
    }

    public static boolean isBase64(byte by) {
        if (by == 61) return true;
        if (by < 0) return false;
        byte[] arrby = DECODE_TABLE;
        if (by >= arrby.length) return false;
        if (arrby[by] == -1) return false;
        return true;
    }

    private static boolean isWhiteSpace(byte by) {
        if (by == 9) return true;
        if (by == 10) return true;
        if (by == 13) return true;
        if (by == 32) return true;
        return false;
    }

    private static String newStringUtf8(byte[] object) {
        try {
            return new String((byte[])object, "UTF8");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException(unsupportedEncodingException);
        }
    }

    private void reset() {
        this.buffer = null;
        this.pos = 0;
        this.readPos = 0;
        this.currentLinePos = 0;
        this.modulus = 0;
        this.eof = false;
    }

    private void resizeBuffer() {
        byte[] arrby = this.buffer;
        if (arrby == null) {
            this.buffer = new byte[8192];
            this.pos = 0;
            this.readPos = 0;
            return;
        }
        byte[] arrby2 = new byte[arrby.length * 2];
        System.arraycopy(arrby, 0, arrby2, 0, arrby.length);
        this.buffer = arrby2;
    }

    static byte[] toIntegerBytes(BigInteger arrby) {
        int n = arrby.bitLength() + 7 >> 3 << 3;
        byte[] arrby2 = arrby.toByteArray();
        int n2 = arrby.bitLength();
        int n3 = 1;
        if (n2 % 8 != 0 && arrby.bitLength() / 8 + 1 == n / 8) {
            return arrby2;
        }
        n2 = arrby2.length;
        if (arrby.bitLength() % 8 == 0) {
            --n2;
        } else {
            n3 = 0;
        }
        arrby = new byte[n /= 8];
        System.arraycopy(arrby2, n3, arrby, n - n2, n2);
        return arrby;
    }

    int avail() {
        if (this.buffer == null) return 0;
        return this.pos - this.readPos;
    }

    void decode(byte[] arrby, int n, int n2) {
        int n3;
        if (this.eof) {
            return;
        }
        if (n2 < 0) {
            this.eof = true;
        }
        for (n3 = 0; n3 < n2; ++n3, ++n) {
            int n4;
            int n5;
            byte[] arrby2 = this.buffer;
            if (arrby2 == null || arrby2.length - this.pos < this.decodeSize) {
                this.resizeBuffer();
            }
            if ((n5 = arrby[n]) == 61) {
                this.eof = true;
                break;
            }
            if (n5 < 0 || n5 >= (arrby2 = DECODE_TABLE).length || (n5 = arrby2[n5]) < 0) continue;
            this.modulus = n4 = this.modulus + 1;
            this.modulus = n4 %= 4;
            this.x = n5 = (this.x << 6) + n5;
            if (n4 != 0) continue;
            arrby2 = this.buffer;
            int n6 = this.pos;
            this.pos = n4 = n6 + 1;
            arrby2[n6] = (byte)(n5 >> 16 & 255);
            this.pos = n6 = n4 + 1;
            arrby2[n4] = (byte)(n5 >> 8 & 255);
            this.pos = n6 + 1;
            arrby2[n6] = (byte)(n5 & 255);
        }
        if (!this.eof) return;
        n2 = this.modulus;
        if (n2 == 0) return;
        this.x = n = this.x << 6;
        if (n2 == 2) {
            this.x = n2 = n << 6;
            arrby = this.buffer;
            n = this.pos;
            this.pos = n + 1;
            arrby[n] = (byte)(n2 >> 16 & 255);
            return;
        }
        if (n2 != 3) {
            return;
        }
        arrby = this.buffer;
        n2 = this.pos;
        this.pos = n3 = n2 + 1;
        arrby[n2] = (byte)(n >> 16 & 255);
        this.pos = n3 + 1;
        arrby[n3] = (byte)(n >> 8 & 255);
    }

    public byte[] decode(String string2) {
        return this.decode(this.getBytesUtf8(string2));
    }

    public byte[] decode(byte[] arrby) {
        this.reset();
        if (arrby == null) return arrby;
        if (arrby.length == 0) {
            return arrby;
        }
        int n = arrby.length * 3 / 4;
        this.setInitialBuffer(new byte[n], 0, n);
        this.decode(arrby, 0, arrby.length);
        this.decode(arrby, 0, -1);
        n = this.pos;
        arrby = new byte[n];
        this.readResults(arrby, 0, n);
        return arrby;
    }

    void encode(byte[] arrby, int n, int n2) {
        if (this.eof) {
            return;
        }
        if (n2 < 0) {
            this.eof = true;
            arrby = this.buffer;
            if (arrby == null || arrby.length - this.pos < this.encodeSize) {
                this.resizeBuffer();
            }
            if ((n = this.modulus) != 1) {
                if (n == 2) {
                    byte[] arrby2 = this.buffer;
                    int n3 = this.pos;
                    this.pos = n2 = n3 + 1;
                    arrby = this.encodeTable;
                    n = this.x;
                    arrby2[n3] = arrby[n >> 10 & 63];
                    this.pos = n3 = n2 + 1;
                    arrby2[n2] = arrby[n >> 4 & 63];
                    this.pos = n2 = n3 + 1;
                    arrby2[n3] = arrby[n << 2 & 63];
                    if (arrby == STANDARD_ENCODE_TABLE) {
                        this.pos = n2 + 1;
                        arrby2[n2] = (byte)61;
                    }
                }
            } else {
                byte[] arrby3 = this.buffer;
                n = this.pos;
                this.pos = n2 = n + 1;
                arrby = this.encodeTable;
                int n4 = this.x;
                arrby3[n] = arrby[n4 >> 2 & 63];
                this.pos = n = n2 + 1;
                arrby3[n2] = arrby[n4 << 4 & 63];
                if (arrby == STANDARD_ENCODE_TABLE) {
                    this.pos = n2 = n + 1;
                    arrby3[n] = (byte)61;
                    this.pos = n2 + 1;
                    arrby3[n2] = (byte)61;
                }
            }
            if (this.lineLength <= 0) return;
            n = this.pos;
            if (n <= 0) return;
            arrby = this.lineSeparator;
            System.arraycopy(arrby, 0, this.buffer, n, arrby.length);
            this.pos += this.lineSeparator.length;
            return;
        }
        int n5 = 0;
        while (n5 < n2) {
            int n6;
            int n7;
            byte[] arrby4 = this.buffer;
            if (arrby4 == null || arrby4.length - this.pos < this.encodeSize) {
                this.resizeBuffer();
            }
            this.modulus = n6 = this.modulus + 1;
            this.modulus = n6 % 3;
            n6 = n7 = arrby[n];
            if (n7 < 0) {
                n6 = n7 + 256;
            }
            this.x = n6 = (this.x << 8) + n6;
            if (this.modulus == 0) {
                int n8;
                arrby4 = this.buffer;
                int n9 = this.pos;
                this.pos = n7 = n9 + 1;
                byte[] arrby5 = this.encodeTable;
                arrby4[n9] = arrby5[n6 >> 18 & 63];
                this.pos = n9 = n7 + 1;
                arrby4[n7] = arrby5[n6 >> 12 & 63];
                this.pos = n8 = n9 + 1;
                arrby4[n9] = arrby5[n6 >> 6 & 63];
                this.pos = n7 = n8 + 1;
                arrby4[n8] = arrby5[n6 & 63];
                this.currentLinePos = n6 = this.currentLinePos + 4;
                n9 = this.lineLength;
                if (n9 > 0 && n9 <= n6) {
                    arrby5 = this.lineSeparator;
                    System.arraycopy(arrby5, 0, arrby4, n7, arrby5.length);
                    this.pos += this.lineSeparator.length;
                    this.currentLinePos = 0;
                }
            }
            ++n5;
            ++n;
        }
    }

    public byte[] encode(byte[] arrby) {
        this.reset();
        if (arrby == null) return arrby;
        if (arrby.length == 0) {
            return arrby;
        }
        int n = (int)Base64.getEncodeLength(arrby, this.lineLength, this.lineSeparator);
        byte[] arrby2 = new byte[n];
        this.setInitialBuffer(arrby2, 0, n);
        this.encode(arrby, 0, arrby.length);
        this.encode(arrby, 0, -1);
        if (this.buffer != arrby2) {
            this.readResults(arrby2, 0, n);
        }
        arrby = arrby2;
        if (!this.isUrlSafe()) return arrby;
        int n2 = this.pos;
        arrby = arrby2;
        if (n2 >= n) return arrby;
        arrby = new byte[n2];
        System.arraycopy(arrby2, 0, arrby, 0, n2);
        return arrby;
    }

    public String encodeToString(byte[] arrby) {
        return Base64.newStringUtf8(this.encode(arrby));
    }

    int getLineLength() {
        return this.lineLength;
    }

    byte[] getLineSeparator() {
        return (byte[])this.lineSeparator.clone();
    }

    boolean hasData() {
        if (this.buffer == null) return false;
        return true;
    }

    public boolean isUrlSafe() {
        if (this.encodeTable != URL_SAFE_ENCODE_TABLE) return false;
        return true;
    }

    int readResults(byte[] arrby, int n, int n2) {
        if (this.buffer != null) {
            n2 = Math.min(this.avail(), n2);
            byte[] arrby2 = this.buffer;
            if (arrby2 != arrby) {
                System.arraycopy(arrby2, this.readPos, arrby, n, n2);
                this.readPos = n = this.readPos + n2;
                if (n < this.pos) return n2;
                this.buffer = null;
                return n2;
            }
            this.buffer = null;
            return n2;
        }
        if (!this.eof) return 0;
        return -1;
    }

    void setInitialBuffer(byte[] arrby, int n, int n2) {
        if (arrby == null) return;
        if (arrby.length != n2) return;
        this.buffer = arrby;
        this.pos = n;
        this.readPos = n;
    }
}

