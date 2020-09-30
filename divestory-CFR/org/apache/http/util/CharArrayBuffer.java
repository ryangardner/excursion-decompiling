/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.util;

import java.io.Serializable;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.ByteArrayBuffer;

public final class CharArrayBuffer
implements Serializable {
    private static final long serialVersionUID = -6208952725094867135L;
    private char[] buffer;
    private int len;

    public CharArrayBuffer(int n) {
        if (n < 0) throw new IllegalArgumentException("Buffer capacity may not be negative");
        this.buffer = new char[n];
    }

    private void expand(int n) {
        char[] arrc = new char[Math.max(this.buffer.length << 1, n)];
        System.arraycopy(this.buffer, 0, arrc, 0, this.len);
        this.buffer = arrc;
    }

    public void append(char c) {
        int n = this.len + 1;
        if (n > this.buffer.length) {
            this.expand(n);
        }
        this.buffer[this.len] = c;
        this.len = n;
    }

    public void append(Object object) {
        this.append(String.valueOf(object));
    }

    public void append(String string2) {
        int n;
        int n2;
        String string3 = string2;
        if (string2 == null) {
            string3 = "null";
        }
        if ((n = this.len + (n2 = string3.length())) > this.buffer.length) {
            this.expand(n);
        }
        string3.getChars(0, n2, this.buffer, this.len);
        this.len = n;
    }

    public void append(ByteArrayBuffer byteArrayBuffer, int n, int n2) {
        if (byteArrayBuffer == null) {
            return;
        }
        this.append(byteArrayBuffer.buffer(), n, n2);
    }

    public void append(CharArrayBuffer charArrayBuffer) {
        if (charArrayBuffer == null) {
            return;
        }
        this.append(charArrayBuffer.buffer, 0, charArrayBuffer.len);
    }

    public void append(CharArrayBuffer charArrayBuffer, int n, int n2) {
        if (charArrayBuffer == null) {
            return;
        }
        this.append(charArrayBuffer.buffer, n, n2);
    }

    public void append(byte[] arrby, int n, int n2) {
        int n3;
        if (arrby == null) {
            return;
        }
        if (n >= 0 && n <= arrby.length && n2 >= 0 && (n3 = n + n2) >= 0 && n3 <= arrby.length) {
            if (n2 == 0) {
                return;
            }
            int n4 = this.len;
            int n5 = n2 + n4;
            n2 = n4;
            n3 = n;
            if (n5 > this.buffer.length) {
                this.expand(n5);
                n3 = n;
                n2 = n4;
            }
            do {
                if (n2 >= n5) {
                    this.len = n5;
                    return;
                }
                this.buffer[n2] = (char)(arrby[n3] & 255);
                ++n3;
                ++n2;
            } while (true);
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("off: ");
        stringBuffer.append(n);
        stringBuffer.append(" len: ");
        stringBuffer.append(n2);
        stringBuffer.append(" b.length: ");
        stringBuffer.append(arrby.length);
        throw new IndexOutOfBoundsException(stringBuffer.toString());
    }

    public void append(char[] arrc, int n, int n2) {
        int n3;
        if (arrc == null) {
            return;
        }
        if (n >= 0 && n <= arrc.length && n2 >= 0 && (n3 = n + n2) >= 0 && n3 <= arrc.length) {
            if (n2 == 0) {
                return;
            }
            n3 = this.len + n2;
            if (n3 > this.buffer.length) {
                this.expand(n3);
            }
            System.arraycopy(arrc, n, this.buffer, this.len, n2);
            this.len = n3;
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("off: ");
        stringBuffer.append(n);
        stringBuffer.append(" len: ");
        stringBuffer.append(n2);
        stringBuffer.append(" b.length: ");
        stringBuffer.append(arrc.length);
        throw new IndexOutOfBoundsException(stringBuffer.toString());
    }

    public char[] buffer() {
        return this.buffer;
    }

    public int capacity() {
        return this.buffer.length;
    }

    public char charAt(int n) {
        return this.buffer[n];
    }

    public void clear() {
        this.len = 0;
    }

    public void ensureCapacity(int n) {
        if (n <= 0) {
            return;
        }
        int n2 = this.buffer.length;
        int n3 = this.len;
        if (n <= n2 - n3) return;
        this.expand(n3 + n);
    }

    public int indexOf(int n) {
        return this.indexOf(n, 0, this.len);
    }

    public int indexOf(int n, int n2, int n3) {
        int n4 = n2;
        if (n2 < 0) {
            n4 = 0;
        }
        int n5 = this.len;
        n2 = n3;
        if (n3 > n5) {
            n2 = n5;
        }
        n3 = n4;
        if (n4 > n2) {
            return -1;
        }
        while (n3 < n2) {
            if (this.buffer[n3] == n) {
                return n3;
            }
            ++n3;
        }
        return -1;
    }

    public boolean isEmpty() {
        if (this.len != 0) return false;
        return true;
    }

    public boolean isFull() {
        if (this.len != this.buffer.length) return false;
        return true;
    }

    public int length() {
        return this.len;
    }

    public void setLength(int n) {
        if (n >= 0 && n <= this.buffer.length) {
            this.len = n;
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("len: ");
        stringBuffer.append(n);
        stringBuffer.append(" < 0 or > buffer len: ");
        stringBuffer.append(this.buffer.length);
        throw new IndexOutOfBoundsException(stringBuffer.toString());
    }

    public String substring(int n, int n2) {
        return new String(this.buffer, n, n2 - n);
    }

    public String substringTrimmed(int n, int n2) {
        int n3;
        if (n < 0) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Negative beginIndex: ");
            stringBuffer.append(n);
            throw new IndexOutOfBoundsException(stringBuffer.toString());
        }
        if (n2 > this.len) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("endIndex: ");
            stringBuffer.append(n2);
            stringBuffer.append(" > length: ");
            stringBuffer.append(this.len);
            throw new IndexOutOfBoundsException(stringBuffer.toString());
        }
        if (n > n2) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("beginIndex: ");
            stringBuffer.append(n);
            stringBuffer.append(" > endIndex: ");
            stringBuffer.append(n2);
            throw new IndexOutOfBoundsException(stringBuffer.toString());
        }
        do {
            n3 = n2;
            if (n >= n2) break;
            n3 = n2;
            if (!HTTP.isWhitespace(this.buffer[n])) break;
            ++n;
        } while (true);
        while (n3 > n) {
            if (!HTTP.isWhitespace(this.buffer[n3 - 1])) return new String(this.buffer, n, n3 - n);
            --n3;
        }
        return new String(this.buffer, n, n3 - n);
    }

    public char[] toCharArray() {
        int n = this.len;
        char[] arrc = new char[n];
        if (n <= 0) return arrc;
        System.arraycopy(this.buffer, 0, arrc, 0, n);
        return arrc;
    }

    public String toString() {
        return new String(this.buffer, 0, this.len);
    }
}

