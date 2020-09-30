/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.sun.mail.util;

import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BASE64DecoderStream
extends FilterInputStream {
    private static final char[] pem_array = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    private static final byte[] pem_convert_array = new byte[256];
    private byte[] buffer = new byte[3];
    private int bufsize;
    private boolean ignoreErrors;
    private int index;
    private byte[] input_buffer;
    private int input_len;
    private int input_pos;

    static {
        int n = 0;
        int n2 = 0;
        do {
            if (n2 >= 255) break;
            BASE64DecoderStream.pem_convert_array[n2] = (byte)-1;
            ++n2;
        } while (true);
        n2 = n;
        char[] arrc;
        while (n2 < (arrc = pem_array).length) {
            BASE64DecoderStream.pem_convert_array[arrc[n2]] = (byte)n2;
            ++n2;
        }
        return;
    }

    public BASE64DecoderStream(InputStream object) {
        super((InputStream)object);
        boolean bl = false;
        this.bufsize = 0;
        this.index = 0;
        this.input_buffer = new byte[8190];
        this.input_pos = 0;
        this.input_len = 0;
        this.ignoreErrors = false;
        try {
            object = System.getProperty("mail.mime.base64.ignoreerrors");
            boolean bl2 = bl;
            if (object != null) {
                bl2 = bl;
                if (!((String)object).equalsIgnoreCase("false")) {
                    bl2 = true;
                }
            }
            this.ignoreErrors = bl2;
            return;
        }
        catch (SecurityException securityException) {
            return;
        }
    }

    public BASE64DecoderStream(InputStream inputStream2, boolean bl) {
        super(inputStream2);
        this.bufsize = 0;
        this.index = 0;
        this.input_buffer = new byte[8190];
        this.input_pos = 0;
        this.input_len = 0;
        this.ignoreErrors = false;
        this.ignoreErrors = bl;
    }

    private int decode(byte[] object, int n, int n2) throws IOException {
        int n3;
        int n4;
        int n5;
        int n6 = n;
        int n7 = n2;
        n2 = n6;
        block0 : do {
            if (n7 < 3) {
                return n2 - n;
            }
            n6 = 0;
            n4 = 0;
            n3 = 0;
            do {
                if (n4 >= 4) {
                    object[n2 + 2] = (byte)(n3 & 255);
                    n6 = n3 >> 8;
                    object[n2 + 1] = (byte)(n6 & 255);
                    object[n2] = (byte)(n6 >> 8 & 255);
                    n7 -= 3;
                    n2 += 3;
                    continue block0;
                }
                n5 = this.getByte();
                if (n5 == -1 || n5 == -2) break block0;
                ++n4;
                n3 = n3 << 6 | n5;
            } while (true);
            break;
        } while (true);
        if (n5 == -1) {
            if (n4 == 0) {
                return n2 - n;
            }
            if (!this.ignoreErrors) {
                object = new StringBuilder("Error in encoded stream: needed 4 valid base64 characters but only got ");
                ((StringBuilder)object).append(n4);
                ((StringBuilder)object).append(" before EOF");
                ((StringBuilder)object).append(this.recentChars());
                throw new IOException(((StringBuilder)object).toString());
            }
            n6 = 1;
        } else {
            if (n4 < 2 && !this.ignoreErrors) {
                object = new StringBuilder("Error in encoded stream: needed at least 2 valid base64 characters, but only got ");
                ((StringBuilder)object).append(n4);
                ((StringBuilder)object).append(" before padding character (=)");
                ((StringBuilder)object).append(this.recentChars());
                throw new IOException(((StringBuilder)object).toString());
            }
            if (n4 == 0) {
                return n2 - n;
            }
        }
        n7 = n5 = n4 - 1;
        if (n5 == 0) {
            n7 = 1;
        }
        n5 = n4 + 1;
        n4 = n3 << 6;
        n3 = n5;
        do {
            if (n3 >= 4) {
                n6 = n4 >> 8;
                if (n7 == 2) {
                    object[n2 + 1] = (byte)(n6 & 255);
                }
                object[n2] = (byte)(n6 >> 8 & 255);
                return n2 + n7 - n;
            }
            if (n6 == 0) {
                n5 = this.getByte();
                if (n5 == -1) {
                    if (!this.ignoreErrors) {
                        object = new StringBuilder("Error in encoded stream: hit EOF while looking for padding characters (=)");
                        ((StringBuilder)object).append(this.recentChars());
                        throw new IOException(((StringBuilder)object).toString());
                    }
                } else if (n5 != -2 && !this.ignoreErrors) {
                    object = new StringBuilder("Error in encoded stream: found valid base64 character after a padding character (=)");
                    ((StringBuilder)object).append(this.recentChars());
                    throw new IOException(((StringBuilder)object).toString());
                }
            }
            n4 <<= 6;
            ++n3;
        } while (true);
    }

    public static byte[] decode(byte[] arrby) {
        int n = arrby.length / 4 * 3;
        if (n == 0) {
            return arrby;
        }
        int n2 = n--;
        if (arrby[arrby.length - 1] == 61) {
            n2 = n;
            if (arrby[arrby.length - 2] == 61) {
                n2 = n - 1;
            }
        }
        byte[] arrby2 = new byte[n2];
        int n3 = arrby.length;
        n2 = 0;
        int n4 = 0;
        while (n3 > 0) {
            byte[] arrby3 = pem_convert_array;
            int n5 = n2 + 1;
            n2 = arrby3[arrby[n2] & 255];
            n = n5 + 1;
            n5 = (n2 << 6 | arrby3[arrby[n5] & 255]) << 6;
            if (arrby[n] != 61) {
                n5 |= arrby3[arrby[n] & 255];
                ++n;
                n2 = 3;
            } else {
                n2 = 2;
            }
            n5 <<= 6;
            if (arrby[n] != 61) {
                n5 |= pem_convert_array[arrby[n] & 255];
                ++n;
            } else {
                --n2;
            }
            if (n2 > 2) {
                arrby2[n4 + 2] = (byte)(n5 & 255);
            }
            n5 >>= 8;
            if (n2 > 1) {
                arrby2[n4 + 1] = (byte)(n5 & 255);
            }
            arrby2[n4] = (byte)(n5 >> 8 & 255);
            n4 += n2;
            n3 -= 4;
            n2 = n;
        }
        return arrby2;
    }

    private int getByte() throws IOException {
        int n;
        do {
            if (this.input_pos >= this.input_len) {
                try {
                    this.input_len = n = this.in.read(this.input_buffer);
                    if (n <= 0) {
                        return -1;
                    }
                    this.input_pos = 0;
                }
                catch (EOFException eOFException) {
                    return -1;
                }
            }
            byte[] arrby = this.input_buffer;
            n = this.input_pos;
            this.input_pos = n + 1;
            if ((n = arrby[n] & 255) != 61) continue;
            return -2;
        } while ((n = pem_convert_array[n]) == -1);
        return n;
    }

    private String recentChars() {
        int n;
        int n2 = n = this.input_pos;
        if (n > 10) {
            n2 = 10;
        }
        CharSequence charSequence = "";
        if (n2 <= 0) return charSequence;
        charSequence = new StringBuilder("");
        ((StringBuilder)charSequence).append(", the ");
        ((StringBuilder)charSequence).append(n2);
        ((StringBuilder)charSequence).append(" most recent characters were: \"");
        charSequence = ((StringBuilder)charSequence).toString();
        n2 = this.input_pos - n2;
        do {
            if (n2 >= this.input_pos) {
                charSequence = new StringBuilder(String.valueOf(charSequence));
                ((StringBuilder)charSequence).append("\"");
                return ((StringBuilder)charSequence).toString();
            }
            char c = (char)(this.input_buffer[n2] & 255);
            if (c != '\t') {
                if (c != '\n') {
                    if (c != '\r') {
                        if (c >= ' ' && c < '') {
                            charSequence = new StringBuilder(String.valueOf(charSequence));
                            ((StringBuilder)charSequence).append(c);
                            charSequence = ((StringBuilder)charSequence).toString();
                        } else {
                            charSequence = new StringBuilder(String.valueOf(charSequence));
                            ((StringBuilder)charSequence).append("\\");
                            ((StringBuilder)charSequence).append((int)c);
                            charSequence = ((StringBuilder)charSequence).toString();
                        }
                    } else {
                        charSequence = new StringBuilder(String.valueOf(charSequence));
                        ((StringBuilder)charSequence).append("\\r");
                        charSequence = ((StringBuilder)charSequence).toString();
                    }
                } else {
                    charSequence = new StringBuilder(String.valueOf(charSequence));
                    ((StringBuilder)charSequence).append("\\n");
                    charSequence = ((StringBuilder)charSequence).toString();
                }
            } else {
                charSequence = new StringBuilder(String.valueOf(charSequence));
                ((StringBuilder)charSequence).append("\\t");
                charSequence = ((StringBuilder)charSequence).toString();
            }
            ++n2;
        } while (true);
    }

    @Override
    public int available() throws IOException {
        return this.in.available() * 3 / 4 + (this.bufsize - this.index);
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public int read() throws IOException {
        byte[] arrby;
        int n;
        if (this.index >= this.bufsize) {
            arrby = this.buffer;
            this.bufsize = n = this.decode(arrby, 0, arrby.length);
            if (n <= 0) {
                return -1;
            }
            this.index = 0;
        }
        arrby = this.buffer;
        n = this.index;
        this.index = n + 1;
        return arrby[n] & 255;
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        int n3 = n;
        int n4 = n2;
        while ((n2 = this.index) < this.bufsize && n4 > 0) {
            byte[] arrby2 = this.buffer;
            this.index = n2 + 1;
            arrby[n3] = arrby2[n2];
            --n4;
            ++n3;
        }
        if (this.index >= this.bufsize) {
            this.index = 0;
            this.bufsize = 0;
        }
        int n5 = n4 / 3 * 3;
        n2 = n3;
        int n6 = n4;
        if (n5 > 0) {
            int n7 = this.decode(arrby, n3, n5);
            n6 = n4 - n7;
            n2 = n3 += n7;
            if (n7 != n5) {
                if (n3 != n) return n3 - n;
                return -1;
            }
        }
        while (n6 > 0 && (n4 = this.read()) != -1) {
            arrby[n2] = (byte)n4;
            --n6;
            ++n2;
        }
        if (n2 != n) return n2 - n;
        return -1;
    }
}

