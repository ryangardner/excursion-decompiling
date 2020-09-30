/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.hash;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.hash.AbstractHashFunction;
import com.google.common.hash.AbstractHasher;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.common.hash.PrimitiveSink;
import com.google.common.primitives.Ints;
import com.google.common.primitives.UnsignedBytes;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable
final class Murmur3_32HashFunction
extends AbstractHashFunction
implements Serializable {
    private static final int C1 = -862048943;
    private static final int C2 = 461845907;
    private static final int CHUNK_SIZE = 4;
    static final HashFunction GOOD_FAST_HASH_32;
    static final HashFunction MURMUR3_32;
    private static final long serialVersionUID = 0L;
    private final int seed;

    static {
        MURMUR3_32 = new Murmur3_32HashFunction(0);
        GOOD_FAST_HASH_32 = new Murmur3_32HashFunction(Hashing.GOOD_FAST_HASH_SEED);
    }

    Murmur3_32HashFunction(int n) {
        this.seed = n;
    }

    private static long charToThreeUtf8Bytes(char c) {
        return (c & 63 | 128) << 16 | ((c >>> 12 | 480) & 255 | (c >>> 6 & 63 | 128) << 8);
    }

    private static long charToTwoUtf8Bytes(char c) {
        return (c & 63 | 128) << 8 | (c >>> 6 | 960) & 255;
    }

    private static long codePointToFourUtf8Bytes(int n) {
        return ((long)(n >>> 18) | 240L) & 255L | ((long)(n >>> 12 & 63) | 128L) << 8 | ((long)(n >>> 6 & 63) | 128L) << 16 | ((long)(n & 63) | 128L) << 24;
    }

    private static HashCode fmix(int n, int n2) {
        n ^= n2;
        n = (n ^ n >>> 16) * -2048144789;
        n = (n ^ n >>> 13) * -1028477387;
        return HashCode.fromInt(n ^ n >>> 16);
    }

    private static int getIntLittleEndian(byte[] arrby, int n) {
        return Ints.fromBytes(arrby[n + 3], arrby[n + 2], arrby[n + 1], arrby[n]);
    }

    private static int mixH1(int n, int n2) {
        return Integer.rotateLeft(n ^ n2, 13) * 5 - 430675100;
    }

    private static int mixK1(int n) {
        return Integer.rotateLeft(n * -862048943, 15) * 461845907;
    }

    @Override
    public int bits() {
        return 32;
    }

    public boolean equals(@NullableDecl Object object) {
        boolean bl;
        boolean bl2 = object instanceof Murmur3_32HashFunction;
        boolean bl3 = bl = false;
        if (!bl2) return bl3;
        object = (Murmur3_32HashFunction)object;
        bl3 = bl;
        if (this.seed != ((Murmur3_32HashFunction)object).seed) return bl3;
        return true;
    }

    @Override
    public HashCode hashBytes(byte[] arrby, int n, int n2) {
        int n3;
        Preconditions.checkPositionIndexes(n, n + n2, arrby.length);
        int n4 = this.seed;
        int n5 = 0;
        int n6 = 0;
        while ((n3 = n6 + 4) <= n2) {
            n4 = Murmur3_32HashFunction.mixH1(n4, Murmur3_32HashFunction.mixK1(Murmur3_32HashFunction.getIntLittleEndian(arrby, n6 + n)));
            n6 = n3;
        }
        int n7 = 0;
        n3 = n6;
        n6 = n7;
        while (n3 < n2) {
            n5 ^= UnsignedBytes.toInt(arrby[n + n3]) << n6;
            ++n3;
            n6 += 8;
        }
        return Murmur3_32HashFunction.fmix(Murmur3_32HashFunction.mixK1(n5) ^ n4, n2);
    }

    public int hashCode() {
        return this.getClass().hashCode() ^ this.seed;
    }

    @Override
    public HashCode hashInt(int n) {
        n = Murmur3_32HashFunction.mixK1(n);
        return Murmur3_32HashFunction.fmix(Murmur3_32HashFunction.mixH1(this.seed, n), 4);
    }

    @Override
    public HashCode hashLong(long l) {
        int n = (int)l;
        int n2 = (int)(l >>> 32);
        n = Murmur3_32HashFunction.mixK1(n);
        return Murmur3_32HashFunction.fmix(Murmur3_32HashFunction.mixH1(Murmur3_32HashFunction.mixH1(this.seed, n), Murmur3_32HashFunction.mixK1(n2)), 8);
    }

    @Override
    public HashCode hashString(CharSequence charSequence, Charset charset) {
        int n;
        int n2;
        if (!Charsets.UTF_8.equals(charset)) return this.hashBytes(charSequence.toString().getBytes(charset));
        int n3 = charSequence.length();
        int n4 = this.seed;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        while ((n2 = n6 + 4) <= n3) {
            n = charSequence.charAt(n6);
            char c = charSequence.charAt(n6 + 1);
            char c2 = charSequence.charAt(n6 + 2);
            char c3 = charSequence.charAt(n6 + 3);
            if (n >= 128 || c >= '' || c2 >= '' || c3 >= '') break;
            n4 = Murmur3_32HashFunction.mixH1(n4, Murmur3_32HashFunction.mixK1(c << 8 | n | c2 << 16 | c3 << 24));
            n7 += 4;
            n6 = n2;
        }
        long l = 0L;
        n2 = n4;
        while (n6 < n3) {
            long l2;
            char c = charSequence.charAt(n6);
            if (c < '') {
                l2 = l | (long)c << n5;
                n4 = n5 + 8;
                ++n7;
            } else if (c < '\u0800') {
                l2 = l | Murmur3_32HashFunction.charToTwoUtf8Bytes(c) << n5;
                n4 = n5 + 16;
                n7 += 2;
            } else if (c >= '\ud800' && c <= '\udfff') {
                n4 = Character.codePointAt(charSequence, n6);
                if (n4 == c) {
                    return this.hashBytes(charSequence.toString().getBytes(charset));
                }
                ++n6;
                l2 = l | Murmur3_32HashFunction.codePointToFourUtf8Bytes(n4) << n5;
                n7 += 4;
                n4 = n5;
            } else {
                l2 = l | Murmur3_32HashFunction.charToThreeUtf8Bytes(c) << n5;
                n4 = n5 + 24;
                n7 += 3;
            }
            n = n2;
            n5 = n4;
            l = l2;
            if (n4 >= 32) {
                n = Murmur3_32HashFunction.mixH1(n2, Murmur3_32HashFunction.mixK1((int)l2));
                l = l2 >>> 32;
                n5 = n4 - 32;
            }
            ++n6;
            n2 = n;
        }
        return Murmur3_32HashFunction.fmix(Murmur3_32HashFunction.mixK1((int)l) ^ n2, n7);
    }

    @Override
    public HashCode hashUnencodedChars(CharSequence charSequence) {
        int n = this.seed;
        int n2 = 1;
        do {
            if (n2 >= charSequence.length()) {
                n2 = n;
                if ((charSequence.length() & 1) != 1) return Murmur3_32HashFunction.fmix(n2, charSequence.length() * 2);
                n2 = n ^ Murmur3_32HashFunction.mixK1(charSequence.charAt(charSequence.length() - 1));
                return Murmur3_32HashFunction.fmix(n2, charSequence.length() * 2);
            }
            n = Murmur3_32HashFunction.mixH1(n, Murmur3_32HashFunction.mixK1(charSequence.charAt(n2 - 1) | charSequence.charAt(n2) << 16));
            n2 += 2;
        } while (true);
    }

    @Override
    public Hasher newHasher() {
        return new Murmur3_32Hasher(this.seed);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Hashing.murmur3_32(");
        stringBuilder.append(this.seed);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    private static final class Murmur3_32Hasher
    extends AbstractHasher {
        private long buffer;
        private int h1;
        private boolean isDone;
        private int length;
        private int shift;

        Murmur3_32Hasher(int n) {
            this.h1 = n;
            this.length = 0;
            this.isDone = false;
        }

        private void update(int n, long l) {
            long l2 = this.buffer;
            int n2 = this.shift;
            this.buffer = l = (l & 0xFFFFFFFFL) << n2 | l2;
            this.shift = n2 += n * 8;
            this.length += n;
            if (n2 < 32) return;
            this.h1 = Murmur3_32HashFunction.mixH1(this.h1, Murmur3_32HashFunction.mixK1((int)l));
            this.buffer >>>= 32;
            this.shift -= 32;
        }

        @Override
        public HashCode hash() {
            int n;
            Preconditions.checkState(this.isDone ^ true);
            this.isDone = true;
            this.h1 = n = this.h1 ^ Murmur3_32HashFunction.mixK1((int)this.buffer);
            return Murmur3_32HashFunction.fmix(n, this.length);
        }

        @Override
        public Hasher putByte(byte by) {
            this.update(1, by & 255);
            return this;
        }

        @Override
        public Hasher putBytes(ByteBuffer byteBuffer) {
            ByteOrder byteOrder = byteBuffer.order();
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            while (byteBuffer.remaining() >= 4) {
                this.putInt(byteBuffer.getInt());
            }
            do {
                if (!byteBuffer.hasRemaining()) {
                    byteBuffer.order(byteOrder);
                    return this;
                }
                this.putByte(byteBuffer.get());
            } while (true);
        }

        @Override
        public Hasher putBytes(byte[] arrby, int n, int n2) {
            Preconditions.checkPositionIndexes(n, n + n2, arrby.length);
            int n3 = 0;
            do {
                int n4 = n3 + 4;
                int n5 = n3;
                if (n4 > n2) {
                    while (n5 < n2) {
                        this.putByte(arrby[n + n5]);
                        ++n5;
                    }
                    return this;
                }
                this.update(4, Murmur3_32HashFunction.getIntLittleEndian(arrby, n3 + n));
                n3 = n4;
            } while (true);
        }

        @Override
        public Hasher putChar(char c) {
            this.update(2, c);
            return this;
        }

        @Override
        public Hasher putInt(int n) {
            this.update(4, n);
            return this;
        }

        @Override
        public Hasher putLong(long l) {
            this.update(4, (int)l);
            this.update(4, l >>> 32);
            return this;
        }

        @Override
        public Hasher putString(CharSequence charSequence, Charset charset) {
            int n;
            if (!Charsets.UTF_8.equals(charset)) return super.putString(charSequence, charset);
            int n2 = charSequence.length();
            int n3 = 0;
            do {
                int n4 = n3 + 4;
                n = n3;
                if (n4 > n2) break;
                char c = charSequence.charAt(n3);
                char c2 = charSequence.charAt(n3 + 1);
                char c3 = charSequence.charAt(n3 + 2);
                char c4 = charSequence.charAt(n3 + 3);
                n = n3;
                if (c >= '') break;
                n = n3;
                if (c2 >= '') break;
                n = n3;
                if (c3 >= '') break;
                n = n3;
                if (c4 >= '') break;
                this.update(4, c2 << 8 | c | c3 << 16 | c4 << 24);
                n3 = n4;
            } while (true);
            while (n < n2) {
                char c = charSequence.charAt(n);
                if (c < '') {
                    this.update(1, c);
                } else if (c < '\u0800') {
                    this.update(2, Murmur3_32HashFunction.charToTwoUtf8Bytes(c));
                } else if (c >= '\ud800' && c <= '\udfff') {
                    n3 = Character.codePointAt(charSequence, n);
                    if (n3 == c) {
                        this.putBytes(charSequence.subSequence(n, n2).toString().getBytes(charset));
                        return this;
                    }
                    ++n;
                    this.update(4, Murmur3_32HashFunction.codePointToFourUtf8Bytes(n3));
                } else {
                    this.update(3, Murmur3_32HashFunction.charToThreeUtf8Bytes(c));
                }
                ++n;
            }
            return this;
        }
    }

}

