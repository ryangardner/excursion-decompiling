/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.format;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.format.DataFormatMatcher;
import com.fasterxml.jackson.core.format.MatchStrength;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public interface InputAccessor {
    public boolean hasMoreBytes() throws IOException;

    public byte nextByte() throws IOException;

    public void reset();

    public static class Std
    implements InputAccessor {
        protected final byte[] _buffer;
        protected int _bufferedEnd;
        protected final int _bufferedStart;
        protected final InputStream _in;
        protected int _ptr;

        public Std(InputStream inputStream2, byte[] arrby) {
            this._in = inputStream2;
            this._buffer = arrby;
            this._bufferedStart = 0;
            this._ptr = 0;
            this._bufferedEnd = 0;
        }

        public Std(byte[] arrby) {
            this._in = null;
            this._buffer = arrby;
            this._bufferedStart = 0;
            this._bufferedEnd = arrby.length;
        }

        public Std(byte[] arrby, int n, int n2) {
            this._in = null;
            this._buffer = arrby;
            this._ptr = n;
            this._bufferedStart = n;
            this._bufferedEnd = n + n2;
        }

        public DataFormatMatcher createMatcher(JsonFactory jsonFactory, MatchStrength matchStrength) {
            InputStream inputStream2 = this._in;
            byte[] arrby = this._buffer;
            int n = this._bufferedStart;
            return new DataFormatMatcher(inputStream2, arrby, n, this._bufferedEnd - n, jsonFactory, matchStrength);
        }

        @Override
        public boolean hasMoreBytes() throws IOException {
            int n = this._ptr;
            if (n < this._bufferedEnd) {
                return true;
            }
            InputStream inputStream2 = this._in;
            if (inputStream2 == null) {
                return false;
            }
            byte[] arrby = this._buffer;
            int n2 = arrby.length - n;
            if (n2 < 1) {
                return false;
            }
            if ((n2 = inputStream2.read(arrby, n, n2)) <= 0) {
                return false;
            }
            this._bufferedEnd += n2;
            return true;
        }

        @Override
        public byte nextByte() throws IOException {
            if (this._ptr >= this._bufferedEnd && !this.hasMoreBytes()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed auto-detect: could not read more than ");
                stringBuilder.append(this._ptr);
                stringBuilder.append(" bytes (max buffer size: ");
                stringBuilder.append(this._buffer.length);
                stringBuilder.append(")");
                throw new EOFException(stringBuilder.toString());
            }
            byte[] arrby = this._buffer;
            int n = this._ptr;
            this._ptr = n + 1;
            return arrby[n];
        }

        @Override
        public void reset() {
            this._ptr = this._bufferedStart;
        }
    }

}

