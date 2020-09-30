/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.hash.AbstractHashFunction;
import com.google.common.hash.AbstractHasher;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import com.google.common.hash.PrimitiveSink;
import com.google.errorprone.annotations.Immutable;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Arrays;

@Immutable
abstract class AbstractNonStreamingHashFunction
extends AbstractHashFunction {
    AbstractNonStreamingHashFunction() {
    }

    @Override
    public HashCode hashBytes(ByteBuffer byteBuffer) {
        return this.newHasher(byteBuffer.remaining()).putBytes(byteBuffer).hash();
    }

    @Override
    public abstract HashCode hashBytes(byte[] var1, int var2, int var3);

    @Override
    public HashCode hashInt(int n) {
        return this.hashBytes(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(n).array());
    }

    @Override
    public HashCode hashLong(long l) {
        return this.hashBytes(ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putLong(l).array());
    }

    @Override
    public HashCode hashString(CharSequence charSequence, Charset charset) {
        return this.hashBytes(charSequence.toString().getBytes(charset));
    }

    @Override
    public HashCode hashUnencodedChars(CharSequence charSequence) {
        int n = charSequence.length();
        ByteBuffer byteBuffer = ByteBuffer.allocate(n * 2).order(ByteOrder.LITTLE_ENDIAN);
        int n2 = 0;
        while (n2 < n) {
            byteBuffer.putChar(charSequence.charAt(n2));
            ++n2;
        }
        return this.hashBytes(byteBuffer.array());
    }

    @Override
    public Hasher newHasher() {
        return this.newHasher(32);
    }

    @Override
    public Hasher newHasher(int n) {
        boolean bl = n >= 0;
        Preconditions.checkArgument(bl);
        return new BufferingHasher(n);
    }

    private final class BufferingHasher
    extends AbstractHasher {
        final ExposedByteArrayOutputStream stream;

        BufferingHasher(int n) {
            this.stream = new ExposedByteArrayOutputStream(n);
        }

        @Override
        public HashCode hash() {
            return AbstractNonStreamingHashFunction.this.hashBytes(this.stream.byteArray(), 0, this.stream.length());
        }

        @Override
        public Hasher putByte(byte by) {
            this.stream.write(by);
            return this;
        }

        @Override
        public Hasher putBytes(ByteBuffer byteBuffer) {
            this.stream.write(byteBuffer);
            return this;
        }

        @Override
        public Hasher putBytes(byte[] arrby, int n, int n2) {
            this.stream.write(arrby, n, n2);
            return this;
        }
    }

    private static final class ExposedByteArrayOutputStream
    extends ByteArrayOutputStream {
        ExposedByteArrayOutputStream(int n) {
            super(n);
        }

        byte[] byteArray() {
            return this.buf;
        }

        int length() {
            return this.count;
        }

        void write(ByteBuffer byteBuffer) {
            int n = byteBuffer.remaining();
            if (this.count + n > this.buf.length) {
                this.buf = Arrays.copyOf(this.buf, this.count + n);
            }
            byteBuffer.get(this.buf, this.count, n);
            this.count += n;
        }
    }

}

