/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned.rss;

import com.google.zxing.NotFoundException;
import com.google.zxing.oned.OneDReader;

public abstract class AbstractRSSReader
extends OneDReader {
    private static final float MAX_AVG_VARIANCE = 0.2f;
    private static final float MAX_FINDER_PATTERN_RATIO = 0.89285713f;
    private static final float MAX_INDIVIDUAL_VARIANCE = 0.45f;
    private static final float MIN_FINDER_PATTERN_RATIO = 0.7916667f;
    private final int[] dataCharacterCounters;
    private final int[] decodeFinderCounters = new int[4];
    private final int[] evenCounts;
    private final float[] evenRoundingErrors;
    private final int[] oddCounts;
    private final float[] oddRoundingErrors;

    protected AbstractRSSReader() {
        int[] arrn = new int[8];
        this.dataCharacterCounters = arrn;
        this.oddRoundingErrors = new float[4];
        this.evenRoundingErrors = new float[4];
        this.oddCounts = new int[arrn.length / 2];
        this.evenCounts = new int[arrn.length / 2];
    }

    protected static int count(int[] arrn) {
        int n = arrn.length;
        int n2 = 0;
        int n3 = 0;
        while (n2 < n) {
            n3 += arrn[n2];
            ++n2;
        }
        return n3;
    }

    protected static void decrement(int[] arrn, float[] arrf) {
        int n = 0;
        float f = arrf[0];
        int n2 = 1;
        do {
            if (n2 >= arrn.length) {
                arrn[n] = arrn[n] - 1;
                return;
            }
            float f2 = f;
            if (arrf[n2] < f) {
                f2 = arrf[n2];
                n = n2;
            }
            ++n2;
            f = f2;
        } while (true);
    }

    protected static void increment(int[] arrn, float[] arrf) {
        int n = 0;
        float f = arrf[0];
        int n2 = 1;
        do {
            if (n2 >= arrn.length) {
                arrn[n] = arrn[n] + 1;
                return;
            }
            float f2 = f;
            if (arrf[n2] > f) {
                f2 = arrf[n2];
                n = n2;
            }
            ++n2;
            f = f2;
        } while (true);
    }

    protected static boolean isFinderPattern(int[] arrn) {
        boolean bl = false;
        int n = arrn[0] + arrn[1];
        int n2 = arrn[2];
        int n3 = arrn[3];
        float f = (float)n / (float)(n2 + n + n3);
        boolean bl2 = bl;
        if (!(f >= 0.7916667f)) return bl2;
        bl2 = bl;
        if (!(f <= 0.89285713f)) return bl2;
        n = Integer.MAX_VALUE;
        int n4 = Integer.MIN_VALUE;
        int n5 = arrn.length;
        n3 = 0;
        do {
            if (n3 >= n5) {
                bl2 = bl;
                if (n4 >= n * 10) return bl2;
                return true;
            }
            int n6 = arrn[n3];
            n2 = n4;
            if (n6 > n4) {
                n2 = n6;
            }
            n4 = n;
            if (n6 < n) {
                n4 = n6;
            }
            ++n3;
            n = n4;
            n4 = n2;
        } while (true);
    }

    protected static int parseFinderValue(int[] arrn, int[][] arrn2) throws NotFoundException {
        int n = 0;
        while (n < arrn2.length) {
            if (AbstractRSSReader.patternMatchVariance(arrn, arrn2[n], 0.45f) < 0.2f) {
                return n;
            }
            ++n;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    protected final int[] getDataCharacterCounters() {
        return this.dataCharacterCounters;
    }

    protected final int[] getDecodeFinderCounters() {
        return this.decodeFinderCounters;
    }

    protected final int[] getEvenCounts() {
        return this.evenCounts;
    }

    protected final float[] getEvenRoundingErrors() {
        return this.evenRoundingErrors;
    }

    protected final int[] getOddCounts() {
        return this.oddCounts;
    }

    protected final float[] getOddRoundingErrors() {
        return this.oddRoundingErrors;
    }
}

