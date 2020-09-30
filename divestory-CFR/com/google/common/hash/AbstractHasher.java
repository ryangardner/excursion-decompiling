/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.hash.Funnel;
import com.google.common.hash.Hasher;
import com.google.common.hash.PrimitiveSink;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

abstract class AbstractHasher
implements Hasher {
    AbstractHasher() {
    }

    @Override
    public final Hasher putBoolean(boolean bl) {
        return this.putByte((byte)bl);
    }

    @Override
    public Hasher putBytes(ByteBuffer byteBuffer) {
        if (byteBuffer.hasArray()) {
            this.putBytes(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining());
            byteBuffer.position(byteBuffer.limit());
            return this;
        }
        int n = byteBuffer.remaining();
        while (n > 0) {
            this.putByte(byteBuffer.get());
            --n;
        }
        return this;
    }

    @Override
    public Hasher putBytes(byte[] arrby) {
        return this.putBytes(arrby, 0, arrby.length);
    }

    @Override
    public Hasher putBytes(byte[] arrby, int n, int n2) {
        Preconditions.checkPositionIndexes(n, n + n2, arrby.length);
        int n3 = 0;
        while (n3 < n2) {
            this.putByte(arrby[n + n3]);
            ++n3;
        }
        return this;
    }

    @Override
    public Hasher putChar(char c) {
        this.putByte((byte)c);
        this.putByte((byte)(c >>> 8));
        return this;
    }

    @Override
    public final Hasher putDouble(double d) {
        return this.putLong(Double.doubleToRawLongBits(d));
    }

    @Override
    public final Hasher putFloat(float f) {
        return this.putInt(Float.floatToRawIntBits(f));
    }

    @Override
    public Hasher putInt(int n) {
        this.putByte((byte)n);
        this.putByte((byte)(n >>> 8));
        this.putByte((byte)(n >>> 16));
        this.putByte((byte)(n >>> 24));
        return this;
    }

    @Override
    public Hasher putLong(long l) {
        int n = 0;
        while (n < 64) {
            this.putByte((byte)(l >>> n));
            n += 8;
        }
        return this;
    }

    @Override
    public <T> Hasher putObject(T t, Funnel<? super T> funnel) {
        funnel.funnel(t, this);
        return this;
    }

    @Override
    public Hasher putShort(short s) {
        this.putByte((byte)s);
        this.putByte((byte)(s >>> 8));
        return this;
    }

    @Override
    public Hasher putString(CharSequence charSequence, Charset charset) {
        return this.putBytes(charSequence.toString().getBytes(charset));
    }

    @Override
    public Hasher putUnencodedChars(CharSequence charSequence) {
        int n = charSequence.length();
        int n2 = 0;
        while (n2 < n) {
            this.putChar(charSequence.charAt(n2));
            ++n2;
        }
        return this;
    }
}

