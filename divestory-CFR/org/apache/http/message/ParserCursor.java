/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http.message;

import org.apache.http.util.CharArrayBuffer;

public class ParserCursor {
    private final int lowerBound;
    private int pos;
    private final int upperBound;

    public ParserCursor(int n, int n2) {
        if (n < 0) throw new IndexOutOfBoundsException("Lower bound cannot be negative");
        if (n > n2) throw new IndexOutOfBoundsException("Lower bound cannot be greater then upper bound");
        this.lowerBound = n;
        this.upperBound = n2;
        this.pos = n;
    }

    public boolean atEnd() {
        if (this.pos < this.upperBound) return false;
        return true;
    }

    public int getLowerBound() {
        return this.lowerBound;
    }

    public int getPos() {
        return this.pos;
    }

    public int getUpperBound() {
        return this.upperBound;
    }

    public String toString() {
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(16);
        charArrayBuffer.append('[');
        charArrayBuffer.append(Integer.toString(this.lowerBound));
        charArrayBuffer.append('>');
        charArrayBuffer.append(Integer.toString(this.pos));
        charArrayBuffer.append('>');
        charArrayBuffer.append(Integer.toString(this.upperBound));
        charArrayBuffer.append(']');
        return charArrayBuffer.toString();
    }

    public void updatePos(int n) {
        if (n < this.lowerBound) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("pos: ");
            stringBuffer.append(n);
            stringBuffer.append(" < lowerBound: ");
            stringBuffer.append(this.lowerBound);
            throw new IndexOutOfBoundsException(stringBuffer.toString());
        }
        if (n <= this.upperBound) {
            this.pos = n;
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("pos: ");
        stringBuffer.append(n);
        stringBuffer.append(" > upperBound: ");
        stringBuffer.append(this.upperBound);
        throw new IndexOutOfBoundsException(stringBuffer.toString());
    }
}

