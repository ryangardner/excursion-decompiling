/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.common.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;

public final class MonochromeRectangleDetector {
    private static final int MAX_MODULES = 32;
    private final BitMatrix image;

    public MonochromeRectangleDetector(BitMatrix bitMatrix) {
        this.image = bitMatrix;
    }

    private int[] blackWhiteRange(int n, int n2, int n3, int n4, boolean bl) {
        int n5;
        int n6;
        Object object;
        int n7 = n6 = (n3 + n4) / 2;
        while (n7 >= n3) {
            int n8;
            object = this.image;
            if (bl ? ((BitMatrix)object).get(n7, n) : ((BitMatrix)object).get(n, n7)) {
                --n7;
                continue;
            }
            n5 = n7;
            while ((n8 = n5 - 1) >= n3) {
                object = this.image;
                if (bl) {
                    n5 = n8;
                    if (!((BitMatrix)object).get(n8, n)) continue;
                    break;
                }
                n5 = n8;
                if (!((BitMatrix)object).get(n, n8)) continue;
            }
            if (n8 < n3 || n7 - n8 > n2) break;
            n7 = n8;
        }
        n5 = n7 + 1;
        n3 = n6;
        while (n3 < n4) {
            object = this.image;
            if (bl ? ((BitMatrix)object).get(n3, n) : ((BitMatrix)object).get(n, n3)) {
                ++n3;
                continue;
            }
            n7 = n3;
            while ((n6 = n7 + 1) < n4) {
                object = this.image;
                if (bl) {
                    n7 = n6;
                    if (!((BitMatrix)object).get(n6, n)) continue;
                    break;
                }
                n7 = n6;
                if (!((BitMatrix)object).get(n, n6)) continue;
            }
            if (n6 >= n4 || n6 - n3 > n2) break;
            n3 = n6;
        }
        if ((n = n3 - 1) <= n5) return null;
        object = new int[]{n5, n};
        return object;
    }

    private ResultPoint findCornerFromCenter(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) throws NotFoundException {
        int[] arrn = null;
        int n10 = n;
        int n11 = n5;
        while (n11 < n8) {
            if (n11 < n7) throw NotFoundException.getNotFoundInstance();
            if (n10 >= n4) throw NotFoundException.getNotFoundInstance();
            if (n10 < n3) throw NotFoundException.getNotFoundInstance();
            int[] arrn2 = n2 == 0 ? this.blackWhiteRange(n11, n9, n3, n4, true) : this.blackWhiteRange(n10, n9, n7, n8, false);
            if (arrn2 == null) {
                if (arrn == null) throw NotFoundException.getNotFoundInstance();
                if (n2 == 0) {
                    n2 = n11 - n6;
                    if (arrn[0] >= n) return new ResultPoint(arrn[1], n2);
                    if (arrn[1] <= n) return new ResultPoint(arrn[0], n2);
                    if (n6 > 0) {
                        n = arrn[0];
                        return new ResultPoint(n, n2);
                    }
                    n = arrn[1];
                    return new ResultPoint(n, n2);
                }
                n = n10 - n2;
                if (arrn[0] >= n5) return new ResultPoint(n, arrn[1]);
                if (arrn[1] <= n5) return new ResultPoint(n, arrn[0]);
                float f = n;
                if (n2 < 0) {
                    n = arrn[0];
                    return new ResultPoint(f, n);
                }
                n = arrn[1];
                return new ResultPoint(f, n);
            }
            n11 += n6;
            n10 += n2;
            arrn = arrn2;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    public ResultPoint[] detect() throws NotFoundException {
        int n = this.image.getHeight();
        int n2 = this.image.getWidth();
        int n3 = n / 2;
        int n4 = n2 / 2;
        int n5 = Math.max(1, n / 256);
        int n6 = Math.max(1, n2 / 256);
        int n7 = -n5;
        int n8 = n4 / 2;
        int n9 = (int)this.findCornerFromCenter(n4, 0, 0, n2, n3, n7, 0, n, n8).getY() - 1;
        int n10 = -n6;
        int n11 = n3 / 2;
        ResultPoint resultPoint = this.findCornerFromCenter(n4, n10, 0, n2, n3, 0, n9, n, n11);
        n10 = (int)resultPoint.getX() - 1;
        ResultPoint resultPoint2 = this.findCornerFromCenter(n4, n6, n10, n2, n3, 0, n9, n, n11);
        n11 = (int)resultPoint2.getX() + 1;
        ResultPoint resultPoint3 = this.findCornerFromCenter(n4, 0, n10, n11, n3, n5, n9, n, n8);
        return new ResultPoint[]{this.findCornerFromCenter(n4, 0, n10, n11, n3, n7, n9, (int)resultPoint3.getY() + 1, n4 / 4), resultPoint, resultPoint2, resultPoint3};
    }
}

