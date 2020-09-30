/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.hash;

import com.google.common.hash.AbstractHashFunction;
import com.google.common.hash.AbstractStreamingHasher;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.common.primitives.UnsignedBytes;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable
final class Murmur3_128HashFunction
extends AbstractHashFunction
implements Serializable {
    static final HashFunction GOOD_FAST_HASH_128;
    static final HashFunction MURMUR3_128;
    private static final long serialVersionUID = 0L;
    private final int seed;

    static {
        MURMUR3_128 = new Murmur3_128HashFunction(0);
        GOOD_FAST_HASH_128 = new Murmur3_128HashFunction(Hashing.GOOD_FAST_HASH_SEED);
    }

    Murmur3_128HashFunction(int n) {
        this.seed = n;
    }

    @Override
    public int bits() {
        return 128;
    }

    public boolean equals(@NullableDecl Object object) {
        boolean bl;
        boolean bl2 = object instanceof Murmur3_128HashFunction;
        boolean bl3 = bl = false;
        if (!bl2) return bl3;
        object = (Murmur3_128HashFunction)object;
        bl3 = bl;
        if (this.seed != ((Murmur3_128HashFunction)object).seed) return bl3;
        return true;
    }

    public int hashCode() {
        return this.getClass().hashCode() ^ this.seed;
    }

    @Override
    public Hasher newHasher() {
        return new Murmur3_128Hasher(this.seed);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Hashing.murmur3_128(");
        stringBuilder.append(this.seed);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    private static final class Murmur3_128Hasher
    extends AbstractStreamingHasher {
        private static final long C1 = -8663945395140668459L;
        private static final long C2 = 5545529020109919103L;
        private static final int CHUNK_SIZE = 16;
        private long h1;
        private long h2;
        private int length;

        Murmur3_128Hasher(int n) {
            super(16);
            long l;
            this.h1 = l = (long)n;
            this.h2 = l;
            this.length = 0;
        }

        private void bmix64(long l, long l2) {
            long l3 = this.h1;
            this.h1 = l = Murmur3_128Hasher.mixK1(l) ^ l3;
            this.h1 = l3 = Long.rotateLeft(l, 27);
            l = this.h2;
            this.h1 = l3 += l;
            this.h1 = l3 * 5L + 1390208809L;
            this.h2 = l = Murmur3_128Hasher.mixK2(l2) ^ l;
            this.h2 = l = Long.rotateLeft(l, 31);
            this.h2 = l += this.h1;
            this.h2 = l * 5L + 944331445L;
        }

        private static long fmix64(long l) {
            l = (l ^ l >>> 33) * -49064778989728563L;
            l = (l ^ l >>> 33) * -4265267296055464877L;
            return l ^ l >>> 33;
        }

        private static long mixK1(long l) {
            return Long.rotateLeft(l * -8663945395140668459L, 31) * 5545529020109919103L;
        }

        private static long mixK2(long l) {
            return Long.rotateLeft(l * 5545529020109919103L, 33) * -8663945395140668459L;
        }

        @Override
        public HashCode makeHash() {
            long l;
            long l2 = this.h1;
            int n = this.length;
            this.h1 = l = l2 ^ (long)n;
            this.h2 = l2 = this.h2 ^ (long)n;
            this.h1 = l += l2;
            this.h2 = l2 + l;
            this.h1 = Murmur3_128Hasher.fmix64(l);
            this.h2 = l = Murmur3_128Hasher.fmix64(this.h2);
            this.h1 = l2 = this.h1 + l;
            this.h2 = l + l2;
            return HashCode.fromBytesNoCopy(ByteBuffer.wrap(new byte[16]).order(ByteOrder.LITTLE_ENDIAN).putLong(this.h1).putLong(this.h2).array());
        }

        @Override
        protected void process(ByteBuffer byteBuffer) {
            this.bmix64(byteBuffer.getLong(), byteBuffer.getLong());
            this.length += 16;
        }

        /*
         * Unable to fully structure code
         */
        @Override
        protected void processRemaining(ByteBuffer var1_1) {
            block17 : {
                block18 : {
                    this.length += var1_1.remaining();
                    var2_2 = var1_1.remaining();
                    var3_3 = 0L;
                    switch (var2_2) {
                        default: {
                            throw new AssertionError((Object)"Should never get here.");
                        }
                        case 15: {
                            var5_4 = (long)UnsignedBytes.toInt(var1_1.get(14)) << 48 ^ 0L;
                            ** GOTO lbl12
                        }
                        case 14: {
                            var5_4 = 0L;
lbl12: // 2 sources:
                            var5_4 ^= (long)UnsignedBytes.toInt(var1_1.get(13)) << 40;
                            ** GOTO lbl16
                        }
                        case 13: {
                            var5_4 = 0L;
lbl16: // 2 sources:
                            var5_4 ^= (long)UnsignedBytes.toInt(var1_1.get(12)) << 32;
                            ** GOTO lbl20
                        }
                        case 12: {
                            var5_4 = 0L;
lbl20: // 2 sources:
                            var5_4 ^= (long)UnsignedBytes.toInt(var1_1.get(11)) << 24;
                            ** GOTO lbl24
                        }
                        case 11: {
                            var5_4 = 0L;
lbl24: // 2 sources:
                            var5_4 ^= (long)UnsignedBytes.toInt(var1_1.get(10)) << 16;
                            ** GOTO lbl28
                        }
                        case 10: {
                            var5_4 = 0L;
lbl28: // 2 sources:
                            var5_4 ^= (long)UnsignedBytes.toInt(var1_1.get(9)) << 8;
                            ** GOTO lbl32
                        }
                        case 9: {
                            var5_4 = 0L;
lbl32: // 2 sources:
                            var5_4 ^= (long)UnsignedBytes.toInt(var1_1.get(8));
                            ** GOTO lbl36
                        }
                        case 8: {
                            var5_4 = 0L;
lbl36: // 2 sources:
                            var7_5 = var1_1.getLong() ^ 0L;
                            var3_3 = var5_4;
                            var5_4 = var7_5;
                            break block17;
                        }
                        case 7: {
                            var5_4 = (long)UnsignedBytes.toInt(var1_1.get(6)) << 48 ^ 0L;
                            ** GOTO lbl45
                        }
                        case 6: {
                            var5_4 = 0L;
lbl45: // 2 sources:
                            var5_4 ^= (long)UnsignedBytes.toInt(var1_1.get(5)) << 40;
                            ** GOTO lbl49
                        }
                        case 5: {
                            var5_4 = 0L;
lbl49: // 2 sources:
                            var5_4 ^= (long)UnsignedBytes.toInt(var1_1.get(4)) << 32;
                            ** GOTO lbl53
                        }
                        case 4: {
                            var5_4 = 0L;
lbl53: // 2 sources:
                            var5_4 ^= (long)UnsignedBytes.toInt(var1_1.get(3)) << 24;
                            ** GOTO lbl57
                        }
                        case 3: {
                            var5_4 = 0L;
lbl57: // 2 sources:
                            var5_4 ^= (long)UnsignedBytes.toInt(var1_1.get(2)) << 16;
                            ** GOTO lbl61
                        }
                        case 2: {
                            var5_4 = 0L;
lbl61: // 2 sources:
                            var5_4 ^= (long)UnsignedBytes.toInt(var1_1.get(1)) << 8;
                            break block18;
                        }
                        case 1: 
                    }
                    var5_4 = 0L;
                }
                var5_4 = (long)UnsignedBytes.toInt(var1_1.get(0)) ^ var5_4;
            }
            this.h1 ^= Murmur3_128Hasher.mixK1(var5_4);
            this.h2 ^= Murmur3_128Hasher.mixK2(var3_3);
        }
    }

}

