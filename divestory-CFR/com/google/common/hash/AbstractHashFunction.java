/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.hash.Funnel;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.errorprone.annotations.Immutable;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

@Immutable
abstract class AbstractHashFunction
implements HashFunction {
    AbstractHashFunction() {
    }

    @Override
    public HashCode hashBytes(ByteBuffer byteBuffer) {
        return this.newHasher(byteBuffer.remaining()).putBytes(byteBuffer).hash();
    }

    @Override
    public HashCode hashBytes(byte[] arrby) {
        return this.hashBytes(arrby, 0, arrby.length);
    }

    @Override
    public HashCode hashBytes(byte[] arrby, int n, int n2) {
        Preconditions.checkPositionIndexes(n, n + n2, arrby.length);
        return this.newHasher(n2).putBytes(arrby, n, n2).hash();
    }

    @Override
    public HashCode hashInt(int n) {
        return this.newHasher(4).putInt(n).hash();
    }

    @Override
    public HashCode hashLong(long l) {
        return this.newHasher(8).putLong(l).hash();
    }

    @Override
    public <T> HashCode hashObject(T t, Funnel<? super T> funnel) {
        return this.newHasher().putObject(t, funnel).hash();
    }

    @Override
    public HashCode hashString(CharSequence charSequence, Charset charset) {
        return this.newHasher().putString(charSequence, charset).hash();
    }

    @Override
    public HashCode hashUnencodedChars(CharSequence charSequence) {
        return this.newHasher(charSequence.length() * 2).putUnencodedChars(charSequence).hash();
    }

    @Override
    public Hasher newHasher(int n) {
        boolean bl = n >= 0;
        Preconditions.checkArgument(bl, "expectedInputSize must be >= 0 but was %s", n);
        return this.newHasher();
    }
}

