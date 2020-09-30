/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.pdf417.detector;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.pdf417.detector.PDF417DetectorResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class Detector {
    private static final int BARCODE_MIN_HEIGHT = 10;
    private static final int[] INDEXES_START_PATTERN = new int[]{0, 4, 1, 5};
    private static final int[] INDEXES_STOP_PATTERN = new int[]{6, 2, 7, 3};
    private static final float MAX_AVG_VARIANCE = 0.42f;
    private static final float MAX_INDIVIDUAL_VARIANCE = 0.8f;
    private static final int MAX_PATTERN_DRIFT = 5;
    private static final int MAX_PIXEL_DRIFT = 3;
    private static final int ROW_STEP = 5;
    private static final int SKIPPED_ROW_COUNT_MAX = 25;
    private static final int[] START_PATTERN = new int[]{8, 1, 1, 1, 1, 1, 1, 3};
    private static final int[] STOP_PATTERN = new int[]{7, 1, 1, 3, 1, 1, 1, 2, 1};

    private Detector() {
    }

    private static void copyToResult(ResultPoint[] arrresultPoint, ResultPoint[] arrresultPoint2, int[] arrn) {
        int n = 0;
        while (n < arrn.length) {
            arrresultPoint[arrn[n]] = arrresultPoint2[n];
            ++n;
        }
    }

    public static PDF417DetectorResult detect(BinaryBitmap list, Map<DecodeHintType, ?> object, boolean bl) throws NotFoundException {
        BitMatrix bitMatrix = ((BinaryBitmap)((Object)list)).getBlackMatrix();
        List<ResultPoint[]> list2 = Detector.detect(bl, bitMatrix);
        object = bitMatrix;
        list = list2;
        if (!list2.isEmpty()) return new PDF417DetectorResult((BitMatrix)object, list);
        object = bitMatrix.clone();
        ((BitMatrix)object).rotate180();
        list = Detector.detect(bl, (BitMatrix)object);
        return new PDF417DetectorResult((BitMatrix)object, list);
    }

    private static List<ResultPoint[]> detect(boolean bl, BitMatrix bitMatrix) {
        ArrayList<ResultPoint[]> arrayList = new ArrayList<ResultPoint[]>();
        int n = 0;
        block0 : do {
            int n2 = 0;
            int n3 = 0;
            while (n < bitMatrix.getHeight()) {
                float f;
                ResultPoint[] arrresultPoint2 = Detector.findVertices(bitMatrix, n, n2);
                if (arrresultPoint2[0] == null && arrresultPoint2[3] == null) {
                    if (n3 == 0) {
                        return arrayList;
                    }
                    for (ResultPoint[] arrresultPoint2 : arrayList) {
                        n3 = n;
                        if (arrresultPoint2[1] != null) {
                            n3 = (int)Math.max((float)n, arrresultPoint2[1].getY());
                        }
                        n = n3;
                        if (arrresultPoint2[3] == null) continue;
                        n = Math.max(n3, (int)arrresultPoint2[3].getY());
                    }
                    n += 5;
                    continue block0;
                }
                arrayList.add(arrresultPoint2);
                if (!bl) {
                    return arrayList;
                }
                if (arrresultPoint2[2] != null) {
                    n = (int)arrresultPoint2[2].getX();
                    f = arrresultPoint2[2].getY();
                } else {
                    n = (int)arrresultPoint2[4].getX();
                    f = arrresultPoint2[4].getY();
                }
                int n4 = (int)f;
                n2 = n;
                n3 = 1;
                n = n4;
            }
            return arrayList;
            break;
        } while (true);
    }

    private static int[] findGuardPattern(BitMatrix bitMatrix, int n, int n2, int n3, boolean bl, int[] arrn, int[] arrn2) {
        int n4;
        Arrays.fill(arrn2, 0, arrn2.length, 0);
        int n5 = arrn.length;
        for (n4 = 0; bitMatrix.get(n, n2) && n > 0 && n4 < 3; --n, ++n4) {
        }
        int n6 = 0;
        n4 = n;
        int n7 = n;
        n = n6;
        do {
            if (n7 >= n3) {
                if (n != n5 - 1) return null;
                if (!(Detector.patternMatchVariance(arrn2, arrn, 0.8f) < 0.42f)) return null;
                return new int[]{n4, n7 - 1};
            }
            if (bitMatrix.get(n7, n2) ^ bl) {
                arrn2[n] = arrn2[n] + 1;
            } else {
                n6 = n5 - 1;
                if (n == n6) {
                    if (Detector.patternMatchVariance(arrn2, arrn, 0.8f) < 0.42f) {
                        return new int[]{n4, n7};
                    }
                    n4 += arrn2[0] + arrn2[1];
                    int n8 = n5 - 2;
                    System.arraycopy(arrn2, 2, arrn2, 0, n8);
                    arrn2[n8] = 0;
                    arrn2[n6] = 0;
                    --n;
                } else {
                    ++n;
                }
                arrn2[n] = 1;
                bl ^= true;
            }
            ++n7;
        } while (true);
    }

    private static ResultPoint[] findRowsWithPattern(BitMatrix bitMatrix, int n, int n2, int n3, int n4, int[] arrn) {
        ResultPoint[] arrresultPoint;
        int[] arrn2;
        float f;
        int[] arrn3;
        int n5;
        int[] arrn4;
        float f2;
        int n6;
        block9 : {
            arrresultPoint = new ResultPoint[4];
            arrn2 = new int[arrn.length];
            do {
                n6 = 0;
                if (n3 >= n) break;
                arrn4 = Detector.findGuardPattern(bitMatrix, n4, n3, n2, false, arrn, arrn2);
                if (arrn4 != null) {
                    while (n3 > 0) {
                        if ((arrn3 = Detector.findGuardPattern(bitMatrix, n4, --n3, n2, false, arrn, arrn2)) != null) {
                            arrn4 = arrn3;
                            continue;
                        }
                        ++n3;
                        break;
                    }
                    f = arrn4[0];
                    f2 = n3;
                    arrresultPoint[0] = new ResultPoint(f, f2);
                    arrresultPoint[1] = new ResultPoint(arrn4[1], f2);
                    n4 = n3;
                    n3 = 1;
                    break block9;
                }
                n3 += 5;
            } while (true);
            n5 = 0;
            n4 = n3;
            n3 = n5;
        }
        int n7 = n5 = n4 + 1;
        if (n3 != 0) {
            arrn4 = new int[]{(int)arrresultPoint[0].getX(), (int)arrresultPoint[1].getX()};
            n3 = 0;
            while (n5 < n) {
                int n8 = arrn4[0];
                n7 = n3;
                arrn3 = Detector.findGuardPattern(bitMatrix, n8, n5, n2, false, arrn, arrn2);
                if (arrn3 != null && Math.abs(arrn4[0] - arrn3[0]) < 5 && Math.abs(arrn4[1] - arrn3[1]) < 5) {
                    arrn4 = arrn3;
                    n3 = 0;
                } else {
                    if (n7 > 25) break;
                    n3 = n7 + 1;
                }
                ++n5;
            }
            n7 = n5 - (n3 + 1);
            f2 = arrn4[0];
            f = n7;
            arrresultPoint[2] = new ResultPoint(f2, f);
            arrresultPoint[3] = new ResultPoint(arrn4[1], f);
        }
        if (n7 - n4 >= 10) return arrresultPoint;
        n = n6;
        while (n < 4) {
            arrresultPoint[n] = null;
            ++n;
        }
        return arrresultPoint;
    }

    private static ResultPoint[] findVertices(BitMatrix bitMatrix, int n, int n2) {
        int n3 = bitMatrix.getHeight();
        int n4 = bitMatrix.getWidth();
        ResultPoint[] arrresultPoint = new ResultPoint[8];
        Detector.copyToResult(arrresultPoint, Detector.findRowsWithPattern(bitMatrix, n3, n4, n, n2, START_PATTERN), INDEXES_START_PATTERN);
        if (arrresultPoint[4] != null) {
            n2 = (int)arrresultPoint[4].getX();
            n = (int)arrresultPoint[4].getY();
        }
        Detector.copyToResult(arrresultPoint, Detector.findRowsWithPattern(bitMatrix, n3, n4, n, n2, STOP_PATTERN), INDEXES_STOP_PATTERN);
        return arrresultPoint;
    }

    private static float patternMatchVariance(int[] arrn, int[] arrn2, float f) {
        int n = arrn.length;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        for (int i = 0; i < n; n3 += arrn[i], n4 += arrn2[i], ++i) {
        }
        if (n3 < n4) {
            return Float.POSITIVE_INFINITY;
        }
        float f2 = n3;
        float f3 = f2 / (float)n4;
        float f4 = 0.0f;
        n4 = n2;
        while (n4 < n) {
            n3 = arrn[n4];
            float f5 = n3;
            float f6 = (float)arrn2[n4] * f3;
            f6 = f5 > f6 ? f5 - f6 : (f6 -= f5);
            if (f6 > f * f3) {
                return Float.POSITIVE_INFINITY;
            }
            f4 += f6;
            ++n4;
        }
        return f4 / f2;
    }
}

