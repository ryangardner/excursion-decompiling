/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.util;

import java.io.Serializable;
import org.apache.http.util.CharArrayBuffer;

public final class ByteArrayBuffer
implements Serializable {
    private static final long serialVersionUID = 4359112959524048036L;
    private byte[] buffer;
    private int len;

    public ByteArrayBuffer(int n) {
        if (n < 0) throw new IllegalArgumentException("Buffer capacity may not be negative");
        this.buffer = new byte[n];
    }

    private void expand(int n) {
        byte[] arrby = new byte[Math.max(this.buffer.length << 1, n)];
        System.arraycopy(this.buffer, 0, arrby, 0, this.len);
        this.buffer = arrby;
    }

    public void append(int n) {
        int n2 = this.len + 1;
        if (n2 > this.buffer.length) {
            this.expand(n2);
        }
        this.buffer[this.len] = (byte)n;
        this.len = n2;
    }

    public void append(CharArrayBuffer charArrayBuffer, int n, int n2) {
        if (charArrayBuffer == null) {
            return;
        }
        this.append(charArrayBuffer.buffer(), n, n2);
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
            n3 = this.len + n2;
            if (n3 > this.buffer.length) {
                this.expand(n3);
            }
            System.arraycopy(arrby, n, this.buffer, this.len, n2);
            this.len = n3;
            return;
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
                this.buffer[n2] = (byte)arrc[n3];
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
        stringBuffer.append(arrc.length);
        throw new IndexOutOfBoundsException(stringBuffer.toString());
    }

    public byte[] buffer() {
        return this.buffer;
    }

    public int byteAt(int n) {
        return this.buffer[n];
    }

    public int capacity() {
        return this.buffer.length;
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

    public int indexOf(byte by) {
        return this.indexOf(by, 0, this.len);
    }

    public int indexOf(byte by, int n, int n2) {
        int n3 = n;
        if (n < 0) {
            n3 = 0;
        }
        int n4 = this.len;
        n = n2;
        if (n2 > n4) {
            n = n4;
        }
        n2 = n3;
        if (n3 > n) {
            return -1;
        }
        while (n2 < n) {
            if (this.buffer[n2] == by) {
                return n2;
            }
            ++n2;
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

    public byte[] toByteArray() {
        int n = this.len;
        byte[] arrby = new byte[n];
        if (n <= 0) return arrby;
        System.arraycopy(this.buffer, 0, arrby, 0, n);
        return arrby;
    }
}

