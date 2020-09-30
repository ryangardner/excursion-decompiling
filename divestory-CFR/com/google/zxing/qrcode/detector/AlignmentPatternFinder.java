/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.qrcode.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.detector.AlignmentPattern;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

final class AlignmentPatternFinder {
    private final int[] crossCheckStateCount;
    private final int height;
    private final BitMatrix image;
    private final float moduleSize;
    private final List<AlignmentPattern> possibleCenters;
    private final ResultPointCallback resultPointCallback;
    private final int startX;
    private final int startY;
    private final int width;

    AlignmentPatternFinder(BitMatrix bitMatrix, int n, int n2, int n3, int n4, float f, ResultPointCallback resultPointCallback) {
        this.image = bitMatrix;
        this.possibleCenters = new ArrayList<AlignmentPattern>(5);
        this.startX = n;
        this.startY = n2;
        this.width = n3;
        this.height = n4;
        this.moduleSize = f;
        this.crossCheckStateCount = new int[3];
        this.resultPointCallback = resultPointCallback;
    }

    private static float centerFromEnd(int[] arrn, int n) {
        return (float)(n - arrn[2]) - (float)arrn[1] / 2.0f;
    }

    private float crossCheckVertical(int n, int n2, int n3, int n4) {
        float f;
        int n5;
        BitMatrix bitMatrix = this.image;
        int n6 = bitMatrix.getHeight();
        int[] arrn = this.crossCheckStateCount;
        arrn[0] = 0;
        arrn[1] = 0;
        arrn[2] = 0;
        for (n5 = n; n5 >= 0 && bitMatrix.get(n2, n5) && arrn[1] <= n3; --n5) {
            arrn[1] = arrn[1] + 1;
        }
        float f2 = f = Float.NaN;
        if (n5 < 0) return f2;
        if (arrn[1] > n3) {
            return f;
        }
        while (n5 >= 0 && !bitMatrix.get(n2, n5) && arrn[0] <= n3) {
            arrn[0] = arrn[0] + 1;
            --n5;
        }
        if (arrn[0] > n3) {
            return Float.NaN;
        }
        ++n;
        while (n < n6 && bitMatrix.get(n2, n) && arrn[1] <= n3) {
            arrn[1] = arrn[1] + 1;
            ++n;
        }
        f2 = f;
        if (n == n6) return f2;
        if (arrn[1] > n3) {
            return f;
        }
        while (n < n6 && !bitMatrix.get(n2, n) && arrn[2] <= n3) {
            arrn[2] = arrn[2] + 1;
            ++n;
        }
        if (arrn[2] > n3) {
            return Float.NaN;
        }
        if (Math.abs(arrn[0] + arrn[1] + arrn[2] - n4) * 5 >= n4 * 2) {
            return Float.NaN;
        }
        f2 = f;
        if (!this.foundPatternCross(arrn)) return f2;
        return AlignmentPatternFinder.centerFromEnd(arrn, n);
    }

    private boolean foundPatternCross(int[] arrn) {
        float f = this.moduleSize;
        float f2 = f / 2.0f;
        int n = 0;
        while (n < 3) {
            if (Math.abs(f - (float)arrn[n]) >= f2) {
                return false;
            }
            ++n;
        }
        return true;
    }

    private AlignmentPattern handlePossibleCenter(int[] object, int n, int n2) {
        AlignmentPattern alignmentPattern;
        int n3 = object[0];
        int n4 = object[1];
        int n5 = object[2];
        float f = AlignmentPatternFinder.centerFromEnd((int[])object, n2);
        float f2 = this.crossCheckVertical(n, (int)f, object[1] * 2, n3 + n4 + n5);
        if (Float.isNaN(f2)) return null;
        float f3 = (float)(object[0] + object[1] + object[2]) / 3.0f;
        object = this.possibleCenters.iterator();
        do {
            if (object.hasNext()) continue;
            alignmentPattern = new AlignmentPattern(f, f2, f3);
            this.possibleCenters.add(alignmentPattern);
            object = this.resultPointCallback;
            if (object == null) return null;
            object.foundPossibleResultPoint(alignmentPattern);
            return null;
        } while (!(alignmentPattern = (AlignmentPattern)object.next()).aboutEquals(f3, f2, f));
        return alignmentPattern.combineEstimate(f2, f, f3);
    }

    AlignmentPattern find() throws NotFoundException {
        int n = this.startX;
        int n2 = this.height;
        int n3 = this.width + n;
        int n4 = this.startY;
        int n5 = n2 / 2;
        int[] arrn = new int[3];
        int n6 = 0;
        do {
            AlignmentPattern alignmentPattern;
            int n7;
            if (n6 >= n2) {
                if (this.possibleCenters.isEmpty()) throw NotFoundException.getNotFoundInstance();
                return this.possibleCenters.get(0);
            }
            int n8 = (n6 & 1) == 0 ? (n6 + 1) / 2 : -((n6 + 1) / 2);
            int n9 = n8 + (n4 + n5);
            arrn[0] = 0;
            arrn[1] = 0;
            arrn[2] = 0;
            for (n7 = n; n7 < n3 && !this.image.get(n7, n9); ++n7) {
            }
            n8 = 0;
            for (int i = n7; i < n3; ++i) {
                if (this.image.get(i, n9)) {
                    if (n8 == 1) {
                        arrn[n8] = arrn[n8] + 1;
                        continue;
                    }
                    if (n8 == 2) {
                        if (this.foundPatternCross(arrn) && (alignmentPattern = this.handlePossibleCenter(arrn, n9, i)) != null) {
                            return alignmentPattern;
                        }
                        arrn[0] = arrn[2];
                        arrn[1] = 1;
                        arrn[2] = 0;
                        n8 = 1;
                        continue;
                    }
                    arrn[++n8] = arrn[n8] + 1;
                    continue;
                }
                n7 = n8;
                if (n8 == 1) {
                    n7 = n8 + 1;
                }
                arrn[n7] = arrn[n7] + 1;
                n8 = n7;
            }
            if (this.foundPatternCross(arrn) && (alignmentPattern = this.handlePossibleCenter(arrn, n9, n3)) != null) {
                return alignmentPattern;
            }
            ++n6;
        } while (true);
    }
}

