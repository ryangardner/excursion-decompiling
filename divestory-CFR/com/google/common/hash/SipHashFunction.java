/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.hash.AbstractHashFunction;
import com.google.common.hash.AbstractStreamingHasher;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.nio.ByteBuffer;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable
final class SipHashFunction
extends AbstractHashFunction
implements Serializable {
    static final HashFunction SIP_HASH_24 = new SipHashFunction(2, 4, 506097522914230528L, 1084818905618843912L);
    private static final long serialVersionUID = 0L;
    private final int c;
    private final int d;
    private final long k0;
    private final long k1;

    SipHashFunction(int n, int n2, long l, long l2) {
        boolean bl = true;
        boolean bl2 = n > 0;
        Preconditions.checkArgument(bl2, "The number of SipRound iterations (c=%s) during Compression must be positive.", n);
        bl2 = n2 > 0 ? bl : false;
        Preconditions.checkArgument(bl2, "The number of SipRound iterations (d=%s) during Finalization must be positive.", n2);
        this.c = n;
        this.d = n2;
        this.k0 = l;
        this.k1 = l2;
    }

    @Override
    public int bits() {
        return 64;
    }

    public boolean equals(@NullableDecl Object object) {
        boolean bl;
        boolean bl2 = object instanceof SipHashFunction;
        boolean bl3 = bl = false;
        if (!bl2) return bl3;
        object = (SipHashFunction)object;
        bl3 = bl;
        if (this.c != ((SipHashFunction)object).c) return bl3;
        bl3 = bl;
        if (this.d != ((SipHashFunction)object).d) return bl3;
        bl3 = bl;
        if (this.k0 != ((SipHashFunction)object).k0) return bl3;
        bl3 = bl;
        if (this.k1 != ((SipHashFunction)object).k1) return bl3;
        return true;
    }

    public int hashCode() {
        return (int)((long)(this.getClass().hashCode() ^ this.c ^ this.d) ^ this.k0 ^ this.k1);
    }

    @Override
    public Hasher newHasher() {
        return new SipHasher(this.c, this.d, this.k0, this.k1);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Hashing.sipHash");
        stringBuilder.append(this.c);
        stringBuilder.append("");
        stringBuilder.append(this.d);
        stringBuilder.append("(");
        stringBuilder.append(this.k0);
        stringBuilder.append(", ");
        stringBuilder.append(this.k1);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    private static final class SipHasher
    extends AbstractStreamingHasher {
        private static final int CHUNK_SIZE = 8;
        private long b = 0L;
        private final int c;
        private final int d;
        private long finalM = 0L;
        private long v0 = 8317987319222330741L;
        private long v1 = 7237128888997146477L;
        private long v2 = 7816392313619706465L;
        private long v3 = 8387220255154660723L;

        SipHasher(int n, int n2, long l, long l2) {
            super(8);
            this.c = n;
            this.d = n2;
            this.v0 = 8317987319222330741L ^ l;
            this.v1 = 7237128888997146477L ^ l2;
            this.v2 = 7816392313619706465L ^ l;
            this.v3 = 8387220255154660723L ^ l2;
        }

        private void processM(long l) {
            this.v3 ^= l;
            this.sipRound(this.c);
            this.v0 = l ^ this.v0;
        }

        private void sipRound(int n) {
            int n2 = 0;
            while (n2 < n) {
                long l;
                long l2 = this.v0;
                long l3 = this.v1;
                this.v0 = l2 + l3;
                this.v2 += this.v3;
                this.v1 = Long.rotateLeft(l3, 13);
                this.v3 = l = Long.rotateLeft(this.v3, 16);
                l2 = this.v1;
                l3 = this.v0;
                this.v1 = l2 ^ l3;
                this.v3 = l ^ this.v2;
                this.v0 = l2 = Long.rotateLeft(l3, 32);
                l3 = this.v2;
                l = this.v1;
                this.v2 = l3 + l;
                this.v0 = l2 + this.v3;
                this.v1 = Long.rotateLeft(l, 17);
                this.v3 = l = Long.rotateLeft(this.v3, 21);
                l2 = this.v1;
                l3 = this.v2;
                this.v1 = l2 ^ l3;
                this.v3 = l ^ this.v0;
                this.v2 = Long.rotateLeft(l3, 32);
                ++n2;
            }
        }

        @Override
        public HashCode makeHash() {
            long l;
            this.finalM = l = this.finalM ^ this.b << 56;
            this.processM(l);
            this.v2 ^= 255L;
            this.sipRound(this.d);
            return HashCode.fromLong(this.v0 ^ this.v1 ^ this.v2 ^ this.v3);
        }

        @Override
        protected void process(ByteBuffer byteBuffer) {
            this.b += 8L;
            this.processM(byteBuffer.getLong());
        }

        @Override
        protected void processRemaining(ByteBuffer byteBuffer) {
            this.b += (long)byteBuffer.remaining();
            int n = 0;
            while (byteBuffer.hasRemaining()) {
                this.finalM ^= ((long)byteBuffer.get() & 255L) << n;
                n += 8;
            }
        }
    }

}

