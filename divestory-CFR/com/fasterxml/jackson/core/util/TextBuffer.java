/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.io.NumberInput;
import com.fasterxml.jackson.core.util.BufferRecycler;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

public final class TextBuffer {
    static final int MAX_SEGMENT_LEN = 65536;
    static final int MIN_SEGMENT_LEN = 500;
    static final char[] NO_CHARS = new char[0];
    private final BufferRecycler _allocator;
    private char[] _currentSegment;
    private int _currentSize;
    private boolean _hasSegments;
    private char[] _inputBuffer;
    private int _inputLen;
    private int _inputStart;
    private char[] _resultArray;
    private String _resultString;
    private int _segmentSize;
    private ArrayList<char[]> _segments;

    public TextBuffer(BufferRecycler bufferRecycler) {
        this._allocator = bufferRecycler;
    }

    protected TextBuffer(BufferRecycler bufferRecycler, char[] arrc) {
        this._allocator = bufferRecycler;
        this._currentSegment = arrc;
        this._currentSize = arrc.length;
        this._inputStart = -1;
    }

    private char[] buf(int n) {
        BufferRecycler bufferRecycler = this._allocator;
        if (bufferRecycler == null) return new char[Math.max(n, 500)];
        return bufferRecycler.allocCharBuffer(2, n);
    }

    private char[] carr(int n) {
        return new char[n];
    }

    private void clearSegments() {
        this._hasSegments = false;
        this._segments.clear();
        this._segmentSize = 0;
        this._currentSize = 0;
    }

    private void expand(int n) {
        if (this._segments == null) {
            this._segments = new ArrayList();
        }
        char[] arrc = this._currentSegment;
        this._hasSegments = true;
        this._segments.add(arrc);
        this._segmentSize += arrc.length;
        this._currentSize = 0;
        n = arrc.length;
        int n2 = n + (n >> 1);
        if (n2 < 500) {
            n = 500;
        } else {
            n = n2;
            if (n2 > 65536) {
                n = 65536;
            }
        }
        this._currentSegment = this.carr(n);
    }

    public static TextBuffer fromInitial(char[] arrc) {
        return new TextBuffer(null, arrc);
    }

    private char[] resultArray() {
        int n;
        char[] arrc = this._resultString;
        if (arrc != null) {
            return arrc.toCharArray();
        }
        int n2 = this._inputStart;
        if (n2 >= 0) {
            int n3 = this._inputLen;
            if (n3 < 1) {
                return NO_CHARS;
            }
            if (n2 != 0) return Arrays.copyOfRange(this._inputBuffer, n2, n3 + n2);
            return Arrays.copyOf(this._inputBuffer, n3);
        }
        n2 = this.size();
        if (n2 < 1) {
            return NO_CHARS;
        }
        arrc = this.carr(n2);
        char[] arrc2 = this._segments;
        if (arrc2 != null) {
            int n4 = arrc2.size();
            int n5 = 0;
            n2 = 0;
            do {
                n = n2;
                if (n5 < n4) {
                    arrc2 = this._segments.get(n5);
                    n = arrc2.length;
                    System.arraycopy(arrc2, 0, arrc, n2, n);
                    n2 += n;
                    ++n5;
                    continue;
                }
                break;
            } while (true);
        } else {
            n = 0;
        }
        System.arraycopy(this._currentSegment, 0, arrc, n, this._currentSize);
        return arrc;
    }

    private void unshare(int n) {
        int n2 = this._inputLen;
        this._inputLen = 0;
        char[] arrc = this._inputBuffer;
        this._inputBuffer = null;
        int n3 = this._inputStart;
        this._inputStart = -1;
        char[] arrc2 = this._currentSegment;
        if (arrc2 == null || (n += n2) > arrc2.length) {
            this._currentSegment = this.buf(n);
        }
        if (n2 > 0) {
            System.arraycopy(arrc, n3, this._currentSegment, 0, n2);
        }
        this._segmentSize = 0;
        this._currentSize = n2;
    }

    public void append(char c) {
        char[] arrc;
        if (this._inputStart >= 0) {
            this.unshare(16);
        }
        this._resultString = null;
        this._resultArray = null;
        char[] arrc2 = arrc = this._currentSegment;
        if (this._currentSize >= arrc.length) {
            this.expand(1);
            arrc2 = this._currentSegment;
        }
        int n = this._currentSize;
        this._currentSize = n + 1;
        arrc2[n] = c;
    }

    public void append(String string2, int n, int n2) {
        if (this._inputStart >= 0) {
            this.unshare(n2);
        }
        this._resultString = null;
        this._resultArray = null;
        char[] arrc = this._currentSegment;
        int n3 = arrc.length;
        int n4 = this._currentSize;
        int n5 = n3 - n4;
        if (n5 >= n2) {
            string2.getChars(n, n + n2, arrc, n4);
            this._currentSize += n2;
            return;
        }
        int n6 = n;
        n3 = n2;
        if (n5 > 0) {
            n6 = n + n5;
            string2.getChars(n, n6, arrc, n4);
            n3 = n2 - n5;
        }
        do {
            this.expand(n3);
            n2 = Math.min(this._currentSegment.length, n3);
            n = n6 + n2;
            string2.getChars(n6, n, this._currentSegment, 0);
            this._currentSize += n2;
            if ((n3 -= n2) <= 0) {
                return;
            }
            n6 = n;
        } while (true);
    }

    public void append(char[] arrc, int n, int n2) {
        if (this._inputStart >= 0) {
            this.unshare(n2);
        }
        this._resultString = null;
        this._resultArray = null;
        char[] arrc2 = this._currentSegment;
        int n3 = arrc2.length;
        int n4 = this._currentSize;
        int n5 = n3 - n4;
        if (n5 >= n2) {
            System.arraycopy(arrc, n, arrc2, n4, n2);
            this._currentSize += n2;
            return;
        }
        int n6 = n;
        n3 = n2;
        if (n5 > 0) {
            System.arraycopy(arrc, n, arrc2, n4, n5);
            n6 = n + n5;
            n3 = n2 - n5;
        }
        do {
            this.expand(n3);
            n = Math.min(this._currentSegment.length, n3);
            System.arraycopy(arrc, n6, this._currentSegment, 0, n);
            this._currentSize += n;
            n6 += n;
            n3 = n = n3 - n;
        } while (n > 0);
    }

    public char[] contentsAsArray() {
        char[] arrc;
        char[] arrc2 = arrc = this._resultArray;
        if (arrc != null) return arrc2;
        this._resultArray = arrc2 = this.resultArray();
        return arrc2;
    }

    public BigDecimal contentsAsDecimal() throws NumberFormatException {
        char[] arrc = this._resultArray;
        if (arrc != null) {
            return NumberInput.parseBigDecimal(arrc);
        }
        int n = this._inputStart;
        if (n >= 0 && (arrc = this._inputBuffer) != null) {
            return NumberInput.parseBigDecimal(arrc, n, this._inputLen);
        }
        if (this._segmentSize != 0) return NumberInput.parseBigDecimal(this.contentsAsArray());
        arrc = this._currentSegment;
        if (arrc == null) return NumberInput.parseBigDecimal(this.contentsAsArray());
        return NumberInput.parseBigDecimal(arrc, 0, this._currentSize);
    }

    public double contentsAsDouble() throws NumberFormatException {
        return NumberInput.parseDouble(this.contentsAsString());
    }

    public int contentsAsInt(boolean bl) {
        char[] arrc;
        int n = this._inputStart;
        if (n >= 0 && (arrc = this._inputBuffer) != null) {
            if (!bl) return NumberInput.parseInt(arrc, n, this._inputLen);
            return -NumberInput.parseInt(arrc, n + 1, this._inputLen - 1);
        }
        if (!bl) return NumberInput.parseInt(this._currentSegment, 0, this._currentSize);
        return -NumberInput.parseInt(this._currentSegment, 1, this._currentSize - 1);
    }

    public long contentsAsLong(boolean bl) {
        char[] arrc;
        int n = this._inputStart;
        if (n >= 0 && (arrc = this._inputBuffer) != null) {
            if (!bl) return NumberInput.parseLong(arrc, n, this._inputLen);
            return -NumberInput.parseLong(arrc, n + 1, this._inputLen - 1);
        }
        if (!bl) return NumberInput.parseLong(this._currentSegment, 0, this._currentSize);
        return -NumberInput.parseLong(this._currentSegment, 1, this._currentSize - 1);
    }

    public String contentsAsString() {
        if (this._resultString != null) return this._resultString;
        if (this._resultArray != null) {
            this._resultString = new String(this._resultArray);
            return this._resultString;
        }
        int n = this._inputStart;
        CharSequence charSequence = "";
        if (n >= 0) {
            if (this._inputLen < 1) {
                this._resultString = "";
                return "";
            }
            this._resultString = new String(this._inputBuffer, this._inputStart, this._inputLen);
            return this._resultString;
        }
        int n2 = this._segmentSize;
        n = this._currentSize;
        if (n2 == 0) {
            if (n != 0) {
                charSequence = new String(this._currentSegment, 0, n);
            }
            this._resultString = charSequence;
            return this._resultString;
        }
        charSequence = new StringBuilder(n2 + n);
        char[] arrc = this._segments;
        if (arrc != null) {
            n2 = arrc.size();
            for (n = 0; n < n2; ++n) {
                arrc = this._segments.get(n);
                ((StringBuilder)charSequence).append(arrc, 0, arrc.length);
            }
        }
        ((StringBuilder)charSequence).append(this._currentSegment, 0, this._currentSize);
        this._resultString = ((StringBuilder)charSequence).toString();
        return this._resultString;
    }

    public int contentsToWriter(Writer writer) throws IOException {
        int n;
        int n2;
        Object object = this._resultArray;
        if (object != null) {
            writer.write((char[])object);
            return this._resultArray.length;
        }
        object = this._resultString;
        if (object != null) {
            writer.write((String)object);
            return this._resultString.length();
        }
        int n3 = this._inputStart;
        if (n3 >= 0) {
            int n4 = this._inputLen;
            if (n4 <= 0) return n4;
            writer.write(this._inputBuffer, n3, n4);
            return n4;
        }
        object = this._segments;
        if (object != null) {
            int n5 = ((ArrayList)object).size();
            n2 = 0;
            n = 0;
            do {
                n3 = n;
                if (n2 < n5) {
                    object = this._segments.get(n2);
                    n3 = ((Object)object).length;
                    writer.write((char[])object, 0, n3);
                    n += n3;
                    ++n2;
                    continue;
                }
                break;
            } while (true);
        } else {
            n3 = 0;
        }
        n2 = this._currentSize;
        n = n3;
        if (n2 <= 0) return n;
        writer.write(this._currentSegment, 0, n2);
        return n3 + n2;
    }

    public char[] emptyAndGetCurrentSegment() {
        char[] arrc;
        this._inputStart = -1;
        this._currentSize = 0;
        this._inputLen = 0;
        this._inputBuffer = null;
        this._resultString = null;
        this._resultArray = null;
        if (this._hasSegments) {
            this.clearSegments();
        }
        char[] arrc2 = arrc = this._currentSegment;
        if (arrc != null) return arrc2;
        this._currentSegment = arrc2 = this.buf(0);
        return arrc2;
    }

    public void ensureNotShared() {
        if (this._inputStart < 0) return;
        this.unshare(16);
    }

    public char[] expandCurrentSegment() {
        int n;
        char[] arrc = this._currentSegment;
        int n2 = arrc.length;
        int n3 = n = (n2 >> 1) + n2;
        if (n > 65536) {
            n3 = (n2 >> 2) + n2;
        }
        this._currentSegment = arrc = Arrays.copyOf(arrc, n3);
        return arrc;
    }

    public char[] expandCurrentSegment(int n) {
        char[] arrc = this._currentSegment;
        if (arrc.length >= n) {
            return arrc;
        }
        this._currentSegment = arrc = Arrays.copyOf(arrc, n);
        return arrc;
    }

    public char[] finishCurrentSegment() {
        if (this._segments == null) {
            this._segments = new ArrayList();
        }
        this._hasSegments = true;
        this._segments.add(this._currentSegment);
        int n = this._currentSegment.length;
        this._segmentSize += n;
        this._currentSize = 0;
        int n2 = n + (n >> 1);
        if (n2 < 500) {
            n = 500;
        } else {
            n = n2;
            if (n2 > 65536) {
                n = 65536;
            }
        }
        char[] arrc = this.carr(n);
        this._currentSegment = arrc;
        return arrc;
    }

    public char[] getBufferWithoutReset() {
        return this._currentSegment;
    }

    public char[] getCurrentSegment() {
        if (this._inputStart >= 0) {
            this.unshare(1);
            return this._currentSegment;
        }
        char[] arrc = this._currentSegment;
        if (arrc == null) {
            this._currentSegment = this.buf(0);
            return this._currentSegment;
        }
        if (this._currentSize < arrc.length) return this._currentSegment;
        this.expand(1);
        return this._currentSegment;
    }

    public int getCurrentSegmentSize() {
        return this._currentSize;
    }

    public char[] getTextBuffer() {
        if (this._inputStart >= 0) {
            return this._inputBuffer;
        }
        Object object = this._resultArray;
        if (object != null) {
            return object;
        }
        object = this._resultString;
        if (object != null) {
            object = ((String)object).toCharArray();
            this._resultArray = object;
            return object;
        }
        if (this._hasSegments) return this.contentsAsArray();
        char[] arrc = this._currentSegment;
        object = arrc;
        if (arrc != null) return object;
        return NO_CHARS;
    }

    public int getTextOffset() {
        int n = this._inputStart;
        if (n < 0) return 0;
        return n;
    }

    public boolean hasTextAsCharacters() {
        if (this._inputStart >= 0) return true;
        if (this._resultArray != null) {
            return true;
        }
        if (this._resultString == null) return true;
        return false;
    }

    public void releaseBuffers() {
        if (this._allocator == null) {
            this.resetWithEmpty();
            return;
        }
        if (this._currentSegment == null) return;
        this.resetWithEmpty();
        char[] arrc = this._currentSegment;
        this._currentSegment = null;
        this._allocator.releaseCharBuffer(2, arrc);
    }

    public void resetWith(char c) {
        this._inputStart = -1;
        this._inputLen = 0;
        this._resultString = null;
        this._resultArray = null;
        if (this._hasSegments) {
            this.clearSegments();
        } else if (this._currentSegment == null) {
            this._currentSegment = this.buf(1);
        }
        this._currentSegment[0] = c;
        this._segmentSize = 1;
        this._currentSize = 1;
    }

    public void resetWithCopy(String string2, int n, int n2) {
        this._inputBuffer = null;
        this._inputStart = -1;
        this._inputLen = 0;
        this._resultString = null;
        this._resultArray = null;
        if (this._hasSegments) {
            this.clearSegments();
        } else if (this._currentSegment == null) {
            this._currentSegment = this.buf(n2);
        }
        this._segmentSize = 0;
        this._currentSize = 0;
        this.append(string2, n, n2);
    }

    public void resetWithCopy(char[] arrc, int n, int n2) {
        this._inputBuffer = null;
        this._inputStart = -1;
        this._inputLen = 0;
        this._resultString = null;
        this._resultArray = null;
        if (this._hasSegments) {
            this.clearSegments();
        } else if (this._currentSegment == null) {
            this._currentSegment = this.buf(n2);
        }
        this._segmentSize = 0;
        this._currentSize = 0;
        this.append(arrc, n, n2);
    }

    public void resetWithEmpty() {
        this._inputStart = -1;
        this._currentSize = 0;
        this._inputLen = 0;
        this._inputBuffer = null;
        this._resultString = null;
        this._resultArray = null;
        if (!this._hasSegments) return;
        this.clearSegments();
    }

    public void resetWithShared(char[] arrc, int n, int n2) {
        this._resultString = null;
        this._resultArray = null;
        this._inputBuffer = arrc;
        this._inputStart = n;
        this._inputLen = n2;
        if (!this._hasSegments) return;
        this.clearSegments();
    }

    public void resetWithString(String string2) {
        this._inputBuffer = null;
        this._inputStart = -1;
        this._inputLen = 0;
        this._resultString = string2;
        this._resultArray = null;
        if (this._hasSegments) {
            this.clearSegments();
        }
        this._currentSize = 0;
    }

    public String setCurrentAndReturn(int n) {
        this._currentSize = n;
        if (this._segmentSize > 0) {
            return this.contentsAsString();
        }
        String string2 = n == 0 ? "" : new String(this._currentSegment, 0, n);
        this._resultString = string2;
        return string2;
    }

    public void setCurrentLength(int n) {
        this._currentSize = n;
    }

    public int size() {
        if (this._inputStart >= 0) {
            return this._inputLen;
        }
        Object object = this._resultArray;
        if (object != null) {
            return ((char[])object).length;
        }
        object = this._resultString;
        if (object == null) return this._segmentSize + this._currentSize;
        return ((String)object).length();
    }

    public String toString() {
        return this.contentsAsString();
    }
}

