/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.hash.AbstractNonStreamingHashFunction;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.LittleEndianByteArray;

final class FarmHashFingerprint64
extends AbstractNonStreamingHashFunction {
    static final HashFunction FARMHASH_FINGERPRINT_64 = new FarmHashFingerprint64();
    private static final long K0 = -4348849565147123417L;
    private static final long K1 = -5435081209227447693L;
    private static final long K2 = -7286425919675154353L;

    FarmHashFingerprint64() {
    }

    static long fingerprint(byte[] arrby, int n, int n2) {
        if (n2 <= 32) {
            if (n2 > 16) return FarmHashFingerprint64.hashLength17to32(arrby, n, n2);
            return FarmHashFingerprint64.hashLength0to16(arrby, n, n2);
        }
        if (n2 > 64) return FarmHashFingerprint64.hashLength65Plus(arrby, n, n2);
        return FarmHashFingerprint64.hashLength33To64(arrby, n, n2);
    }

    private static long hashLength0to16(byte[] arrby, int n, int n2) {
        if (n2 >= 8) {
            long l = (long)(n2 * 2) - 7286425919675154353L;
            long l2 = LittleEndianByteArray.load64(arrby, n) - 7286425919675154353L;
            long l3 = LittleEndianByteArray.load64(arrby, n + n2 - 8);
            return FarmHashFingerprint64.hashLength16(Long.rotateRight(l3, 37) * l + l2, (Long.rotateRight(l2, 25) + l3) * l, l);
        }
        if (n2 >= 4) {
            long l = n2 * 2;
            long l4 = LittleEndianByteArray.load32(arrby, n);
            return FarmHashFingerprint64.hashLength16((long)n2 + ((l4 & 0xFFFFFFFFL) << 3), (long)LittleEndianByteArray.load32(arrby, n + n2 - 4) & 0xFFFFFFFFL, l - 7286425919675154353L);
        }
        if (n2 <= 0) return -7286425919675154353L;
        byte by = arrby[n];
        byte by2 = arrby[(n2 >> 1) + n];
        n = arrby[n + (n2 - 1)];
        return FarmHashFingerprint64.shiftMix((long)((by & 255) + ((by2 & 255) << 8)) * -7286425919675154353L ^ (long)(n2 + ((n & 255) << 2)) * -4348849565147123417L) * -7286425919675154353L;
    }

    private static long hashLength16(long l, long l2, long l3) {
        l = (l ^ l2) * l3;
        l = (l ^ l >>> 47 ^ l2) * l3;
        return (l ^ l >>> 47) * l3;
    }

    private static long hashLength17to32(byte[] arrby, int n, int n2) {
        long l = (long)(n2 * 2) - 7286425919675154353L;
        long l2 = LittleEndianByteArray.load64(arrby, n) * -5435081209227447693L;
        long l3 = LittleEndianByteArray.load64(arrby, n + 8);
        long l4 = LittleEndianByteArray.load64(arrby, (n += n2) - 8) * l;
        return FarmHashFingerprint64.hashLength16(LittleEndianByteArray.load64(arrby, n - 16) * -7286425919675154353L + (Long.rotateRight(l2 + l3, 43) + Long.rotateRight(l4, 30)), l2 + Long.rotateRight(l3 - 7286425919675154353L, 18) + l4, l);
    }

    private static long hashLength33To64(byte[] arrby, int n, int n2) {
        long l = (long)(n2 * 2) - 7286425919675154353L;
        long l2 = LittleEndianByteArray.load64(arrby, n) * -7286425919675154353L;
        long l3 = LittleEndianByteArray.load64(arrby, n + 8);
        n2 = n + n2;
        long l4 = LittleEndianByteArray.load64(arrby, n2 - 8) * l;
        long l5 = LittleEndianByteArray.load64(arrby, n2 - 16);
        l5 = Long.rotateRight(l2 + l3, 43) + Long.rotateRight(l4, 30) + l5 * -7286425919675154353L;
        l3 = FarmHashFingerprint64.hashLength16(l5, l4 + (Long.rotateRight(l3 - 7286425919675154353L, 18) + l2), l);
        long l6 = LittleEndianByteArray.load64(arrby, n + 16) * l;
        l4 = LittleEndianByteArray.load64(arrby, n + 24);
        l5 = (l5 + LittleEndianByteArray.load64(arrby, n2 - 32)) * l;
        return FarmHashFingerprint64.hashLength16((l3 + LittleEndianByteArray.load64(arrby, n2 - 24)) * l + (Long.rotateRight(l6 + l4, 43) + Long.rotateRight(l5, 30)), l6 + Long.rotateRight(l4 + l2, 18) + l5, l);
    }

    private static long hashLength65Plus(byte[] arrby, int n, int n2) {
        long l = FarmHashFingerprint64.shiftMix(-7956866745689871395L) * -7286425919675154353L;
        long[] arrl = new long[2];
        long[] arrl2 = new long[2];
        long l2 = 95310865018149119L + LittleEndianByteArray.load64(arrby, n);
        int n3 = n2 - 1;
        n2 = n + n3 / 64 * 64;
        int n4 = n3 & 63;
        n3 = n2 + n4 - 63;
        long l3 = 2480279821605975764L;
        do {
            long l4 = Long.rotateRight(l2 + l3 + arrl[0] + LittleEndianByteArray.load64(arrby, n + 8), 37);
            l2 = Long.rotateRight(l3 + arrl[1] + LittleEndianByteArray.load64(arrby, n + 48), 42);
            l3 = l4 * -5435081209227447693L ^ arrl2[1];
            l2 = l2 * -5435081209227447693L + (arrl[0] + LittleEndianByteArray.load64(arrby, n + 40));
            l4 = Long.rotateRight(l + arrl2[0], 33) * -5435081209227447693L;
            FarmHashFingerprint64.weakHashLength32WithSeeds(arrby, n, arrl[1] * -5435081209227447693L, l3 + arrl2[0], arrl);
            FarmHashFingerprint64.weakHashLength32WithSeeds(arrby, n + 32, l4 + arrl2[1], l2 + LittleEndianByteArray.load64(arrby, n + 16), arrl2);
            if ((n += 64) == n2) {
                l = ((l3 & 255L) << 1) - 5435081209227447693L;
                arrl2[0] = arrl2[0] + (long)n4;
                arrl[0] = arrl[0] + arrl2[0];
                arrl2[0] = arrl2[0] + arrl[0];
                long l5 = Long.rotateRight(l4 + l2 + arrl[0] + LittleEndianByteArray.load64(arrby, n3 + 8), 37);
                l4 = Long.rotateRight(l2 + arrl[1] + LittleEndianByteArray.load64(arrby, n3 + 48), 42);
                l2 = l5 * l ^ arrl2[1] * 9L;
                l4 = l4 * l + (arrl[0] * 9L + LittleEndianByteArray.load64(arrby, n3 + 40));
                l3 = Long.rotateRight(l3 + arrl2[0], 33) * l;
                FarmHashFingerprint64.weakHashLength32WithSeeds(arrby, n3, arrl[1] * l, l2 + arrl2[0], arrl);
                FarmHashFingerprint64.weakHashLength32WithSeeds(arrby, n3 + 32, l3 + arrl2[1], LittleEndianByteArray.load64(arrby, n3 + 16) + l4, arrl2);
                return FarmHashFingerprint64.hashLength16(FarmHashFingerprint64.hashLength16(arrl[0], arrl2[0], l) + FarmHashFingerprint64.shiftMix(l4) * -4348849565147123417L + l2, FarmHashFingerprint64.hashLength16(arrl[1], arrl2[1], l) + l3, l);
            }
            l = l3;
            l3 = l2;
            l2 = l4;
        } while (true);
    }

    private static long shiftMix(long l) {
        return l ^ l >>> 47;
    }

    private static void weakHashLength32WithSeeds(byte[] arrby, int n, long l, long l2, long[] arrl) {
        long l3 = LittleEndianByteArray.load64(arrby, n);
        long l4 = LittleEndianByteArray.load64(arrby, n + 8);
        long l5 = LittleEndianByteArray.load64(arrby, n + 16);
        long l6 = LittleEndianByteArray.load64(arrby, n + 24);
        l2 = Long.rotateRight(l2 + (l += l3) + l6, 21);
        l5 = l4 + l + l5;
        l4 = Long.rotateRight(l5, 44);
        arrl[0] = l5 + l6;
        arrl[1] = l2 + l4 + l;
    }

    @Override
    public int bits() {
        return 64;
    }

    @Override
    public HashCode hashBytes(byte[] arrby, int n, int n2) {
        Preconditions.checkPositionIndexes(n, n + n2, arrby.length);
        return HashCode.fromLong(FarmHashFingerprint64.fingerprint(arrby, n, n2));
    }

    public String toString() {
        return "Hashing.farmHashFingerprint64()";
    }
}

