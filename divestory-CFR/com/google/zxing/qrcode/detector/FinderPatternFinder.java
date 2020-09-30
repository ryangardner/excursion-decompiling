/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.detector.FinderPattern;
import com.google.zxing.qrcode.detector.FinderPatternInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FinderPatternFinder {
    private static final int CENTER_QUORUM = 2;
    protected static final int MAX_MODULES = 57;
    protected static final int MIN_SKIP = 3;
    private final int[] crossCheckStateCount;
    private boolean hasSkipped;
    private final BitMatrix image;
    private final List<FinderPattern> possibleCenters;
    private final ResultPointCallback resultPointCallback;

    public FinderPatternFinder(BitMatrix bitMatrix) {
        this(bitMatrix, null);
    }

    public FinderPatternFinder(BitMatrix bitMatrix, ResultPointCallback resultPointCallback) {
        this.image = bitMatrix;
        this.possibleCenters = new ArrayList<FinderPattern>();
        this.crossCheckStateCount = new int[5];
        this.resultPointCallback = resultPointCallback;
    }

    private static float centerFromEnd(int[] arrn, int n) {
        return (float)(n - arrn[4] - arrn[3]) - (float)arrn[2] / 2.0f;
    }

    private boolean crossCheckDiagonal(int n, int n2, int n3, int n4) {
        int n5;
        int n6;
        int n7;
        int[] arrn = this.getCrossCheckStateCount();
        boolean bl = false;
        for (n6 = 0; n >= n6 && n2 >= n6 && this.image.get(n2 - n6, n - n6); ++n6) {
            arrn[2] = arrn[2] + 1;
        }
        boolean bl2 = bl;
        if (n < n6) return bl2;
        if (n2 < n6) {
            return bl;
        }
        for (n7 = n6; n >= n7 && n2 >= n7 && !this.image.get(n2 - n7, n - n7) && arrn[1] <= n3; ++n7) {
            arrn[1] = arrn[1] + 1;
        }
        bl2 = bl;
        if (n < n7) return bl2;
        bl2 = bl;
        if (n2 < n7) return bl2;
        if (arrn[1] > n3) {
            return bl;
        }
        while (n >= n7 && n2 >= n7 && this.image.get(n2 - n7, n - n7) && arrn[0] <= n3) {
            arrn[0] = arrn[0] + 1;
            ++n7;
        }
        if (arrn[0] > n3) {
            return false;
        }
        int n8 = this.image.getHeight();
        int n9 = this.image.getWidth();
        n7 = 1;
        while ((n6 = n + n7) < n8 && (n5 = n2 + n7) < n9 && this.image.get(n5, n6)) {
            arrn[2] = arrn[2] + 1;
            ++n7;
        }
        bl2 = bl;
        if (n6 >= n8) return bl2;
        n6 = n7;
        if (n2 + n7 >= n9) {
            return bl;
        }
        while ((n5 = n + n6) < n8 && (n7 = n2 + n6) < n9 && !this.image.get(n7, n5) && arrn[3] < n3) {
            arrn[3] = arrn[3] + 1;
            ++n6;
        }
        bl2 = bl;
        if (n5 >= n8) return bl2;
        bl2 = bl;
        if (n2 + n6 >= n9) return bl2;
        if (arrn[3] >= n3) {
            return bl;
        }
        while ((n7 = n + n6) < n8 && (n5 = n2 + n6) < n9 && this.image.get(n5, n7) && arrn[4] < n3) {
            arrn[4] = arrn[4] + 1;
            ++n6;
        }
        if (arrn[4] >= n3) {
            return false;
        }
        bl2 = bl;
        if (Math.abs(arrn[0] + arrn[1] + arrn[2] + arrn[3] + arrn[4] - n4) >= n4 * 2) return bl2;
        bl2 = bl;
        if (!FinderPatternFinder.foundPatternCross(arrn)) return bl2;
        return true;
    }

    private float crossCheckHorizontal(int n, int n2, int n3, int n4) {
        int n5;
        int n6;
        BitMatrix bitMatrix = this.image;
        int n7 = bitMatrix.getWidth();
        int[] arrn = this.getCrossCheckStateCount();
        for (n6 = n; n6 >= 0 && bitMatrix.get(n6, n2); --n6) {
            arrn[2] = arrn[2] + 1;
        }
        float f = Float.NaN;
        if (n6 < 0) {
            return Float.NaN;
        }
        for (n5 = n6; n5 >= 0 && !bitMatrix.get(n5, n2) && arrn[1] <= n3; --n5) {
            arrn[1] = arrn[1] + 1;
        }
        float f2 = f;
        if (n5 < 0) return f2;
        if (arrn[1] > n3) {
            return f;
        }
        while (n5 >= 0 && bitMatrix.get(n5, n2) && arrn[0] <= n3) {
            arrn[0] = arrn[0] + 1;
            --n5;
        }
        if (arrn[0] > n3) {
            return Float.NaN;
        }
        ++n;
        while (n < n7 && bitMatrix.get(n, n2)) {
            arrn[2] = arrn[2] + 1;
            ++n;
        }
        if (n == n7) {
            return Float.NaN;
        }
        for (n5 = n; n5 < n7 && !bitMatrix.get(n5, n2) && arrn[3] < n3; ++n5) {
            arrn[3] = arrn[3] + 1;
        }
        f2 = f;
        if (n5 == n7) return f2;
        if (arrn[3] >= n3) {
            return f;
        }
        while (n5 < n7 && bitMatrix.get(n5, n2) && arrn[4] < n3) {
            arrn[4] = arrn[4] + 1;
            ++n5;
        }
        if (arrn[4] >= n3) {
            return Float.NaN;
        }
        if (Math.abs(arrn[0] + arrn[1] + arrn[2] + arrn[3] + arrn[4] - n4) * 5 >= n4) {
            return Float.NaN;
        }
        f2 = f;
        if (!FinderPatternFinder.foundPatternCross(arrn)) return f2;
        return FinderPatternFinder.centerFromEnd(arrn, n5);
    }

    private float crossCheckVertical(int n, int n2, int n3, int n4) {
        int n5;
        int n6;
        BitMatrix bitMatrix = this.image;
        int n7 = bitMatrix.getHeight();
        int[] arrn = this.getCrossCheckStateCount();
        for (n6 = n; n6 >= 0 && bitMatrix.get(n2, n6); --n6) {
            arrn[2] = arrn[2] + 1;
        }
        float f = Float.NaN;
        if (n6 < 0) {
            return Float.NaN;
        }
        for (n5 = n6; n5 >= 0 && !bitMatrix.get(n2, n5) && arrn[1] <= n3; --n5) {
            arrn[1] = arrn[1] + 1;
        }
        float f2 = f;
        if (n5 < 0) return f2;
        if (arrn[1] > n3) {
            return f;
        }
        while (n5 >= 0 && bitMatrix.get(n2, n5) && arrn[0] <= n3) {
            arrn[0] = arrn[0] + 1;
            --n5;
        }
        if (arrn[0] > n3) {
            return Float.NaN;
        }
        for (n6 = n + 1; n6 < n7 && bitMatrix.get(n2, n6); ++n6) {
            arrn[2] = arrn[2] + 1;
        }
        if (n6 == n7) {
            return Float.NaN;
        }
        for (n = n6; n < n7 && !bitMatrix.get(n2, n) && arrn[3] < n3; ++n) {
            arrn[3] = arrn[3] + 1;
        }
        f2 = f;
        if (n == n7) return f2;
        if (arrn[3] >= n3) {
            return f;
        }
        while (n < n7 && bitMatrix.get(n2, n) && arrn[4] < n3) {
            arrn[4] = arrn[4] + 1;
            ++n;
        }
        if (arrn[4] >= n3) {
            return Float.NaN;
        }
        if (Math.abs(arrn[0] + arrn[1] + arrn[2] + arrn[3] + arrn[4] - n4) * 5 >= n4 * 2) {
            return Float.NaN;
        }
        f2 = f;
        if (!FinderPatternFinder.foundPatternCross(arrn)) return f2;
        return FinderPatternFinder.centerFromEnd(arrn, n);
    }

    private int findRowSkip() {
        if (this.possibleCenters.size() <= 1) {
            return 0;
        }
        FinderPattern finderPattern = null;
        Iterator<FinderPattern> iterator2 = this.possibleCenters.iterator();
        while (iterator2.hasNext()) {
            FinderPattern finderPattern2 = iterator2.next();
            if (finderPattern2.getCount() < 2) continue;
            if (finderPattern != null) {
                this.hasSkipped = true;
                return (int)(Math.abs(finderPattern.getX() - finderPattern2.getX()) - Math.abs(finderPattern.getY() - finderPattern2.getY())) / 2;
            }
            finderPattern = finderPattern2;
        }
        return 0;
    }

    protected static boolean foundPatternCross(int[] arrn) {
        int n;
        boolean bl = false;
        int n2 = 0;
        for (int i = 0; i < 5; n2 += n, ++i) {
            n = arrn[i];
            if (n != 0) continue;
            return false;
        }
        if (n2 < 7) {
            return false;
        }
        float f = (float)n2 / 7.0f;
        float f2 = f / 2.0f;
        boolean bl2 = bl;
        if (!(Math.abs(f - (float)arrn[0]) < f2)) return bl2;
        bl2 = bl;
        if (!(Math.abs(f - (float)arrn[1]) < f2)) return bl2;
        bl2 = bl;
        if (!(Math.abs(f * 3.0f - (float)arrn[2]) < 3.0f * f2)) return bl2;
        bl2 = bl;
        if (!(Math.abs(f - (float)arrn[3]) < f2)) return bl2;
        bl2 = bl;
        if (!(Math.abs(f - (float)arrn[4]) < f2)) return bl2;
        return true;
    }

    private int[] getCrossCheckStateCount() {
        int[] arrn = this.crossCheckStateCount;
        arrn[0] = 0;
        arrn[1] = 0;
        arrn[2] = 0;
        arrn[3] = 0;
        arrn[4] = 0;
        return arrn;
    }

    private boolean haveMultiplyConfirmedCenters() {
        int n = this.possibleCenters.size();
        Iterator<FinderPattern> iterator2 = this.possibleCenters.iterator();
        float f = 0.0f;
        boolean bl = false;
        int n2 = 0;
        float f2 = 0.0f;
        while (iterator2.hasNext()) {
            FinderPattern finderPattern = iterator2.next();
            if (finderPattern.getCount() < 2) continue;
            ++n2;
            f2 += finderPattern.getEstimatedModuleSize();
        }
        if (n2 < 3) {
            return false;
        }
        float f3 = f2 / (float)n;
        iterator2 = this.possibleCenters.iterator();
        do {
            if (!iterator2.hasNext()) {
                if (!(f <= f2 * 0.05f)) return bl;
                return true;
            }
            f += Math.abs(iterator2.next().getEstimatedModuleSize() - f3);
        } while (true);
    }

    private FinderPattern[] selectBestPatterns() throws NotFoundException {
        float f;
        Object object;
        int n = this.possibleCenters.size();
        if (n < 3) throw NotFoundException.getNotFoundInstance();
        float f2 = 0.0f;
        if (n > 3) {
            float f3;
            object = this.possibleCenters.iterator();
            float f4 = 0.0f;
            f = 0.0f;
            while (object.hasNext()) {
                f3 = object.next().getEstimatedModuleSize();
                f4 += f3;
                f += f3 * f3;
            }
            f3 = n;
            f = (float)Math.sqrt(f / f3 - (f4 /= f3) * f4);
            Collections.sort(this.possibleCenters, new FurthestFromAverageComparator(f4));
            f = Math.max(0.2f * f4, f);
            n = 0;
            while (n < this.possibleCenters.size() && this.possibleCenters.size() > 3) {
                int n2 = n;
                if (Math.abs(this.possibleCenters.get(n).getEstimatedModuleSize() - f4) > f) {
                    this.possibleCenters.remove(n);
                    n2 = n - 1;
                }
                n = n2 + 1;
            }
        }
        if (this.possibleCenters.size() <= 3) return new FinderPattern[]{this.possibleCenters.get(0), this.possibleCenters.get(1), this.possibleCenters.get(2)};
        object = this.possibleCenters.iterator();
        f = f2;
        while (object.hasNext()) {
            f += object.next().getEstimatedModuleSize();
        }
        Collections.sort(this.possibleCenters, new CenterComparator(f /= (float)this.possibleCenters.size()));
        object = this.possibleCenters;
        object.subList(3, object.size()).clear();
        return new FinderPattern[]{this.possibleCenters.get(0), this.possibleCenters.get(1), this.possibleCenters.get(2)};
    }

    final FinderPatternInfo find(Map<DecodeHintType, ?> arrobject) throws NotFoundException {
        int n = arrobject != null && arrobject.containsKey((Object)DecodeHintType.TRY_HARDER) ? 1 : 0;
        boolean bl = arrobject != null && arrobject.containsKey((Object)DecodeHintType.PURE_BARCODE);
        int n2 = this.image.getHeight();
        int n3 = this.image.getWidth();
        int n4 = n2 * 3 / 228;
        if (n4 < 3 || n != 0) {
            n4 = 3;
        }
        arrobject = new int[5];
        boolean bl2 = false;
        for (int i = n4 - 1; i < n2 && !bl2; i += n) {
            int n5;
            boolean bl3;
            arrobject[0] = 0;
            arrobject[1] = 0;
            arrobject[2] = 0;
            arrobject[3] = 0;
            arrobject[4] = 0;
            n = 0;
            for (n5 = 0; n5 < n3; ++n5) {
                int n6;
                if (this.image.get(n5, i)) {
                    n6 = n;
                    if ((n & 1) == 1) {
                        n6 = n + 1;
                    }
                    arrobject[n6] = arrobject[n6] + 1;
                    n = n6;
                    continue;
                }
                if ((n & 1) == 0) {
                    if (n == 4) {
                        if (FinderPatternFinder.foundPatternCross(arrobject)) {
                            if (this.handlePossibleCenter((int[])arrobject, i, n5, bl)) {
                                if (this.hasSkipped) {
                                    bl3 = this.haveMultiplyConfirmedCenters();
                                    n = i;
                                } else {
                                    n4 = this.findRowSkip();
                                    n = i;
                                    bl3 = bl2;
                                    if (n4 > arrobject[2]) {
                                        n = i + (n4 - arrobject[2] - 2);
                                        n5 = n3 - 1;
                                        bl3 = bl2;
                                    }
                                }
                                arrobject[0] = 0;
                                arrobject[1] = 0;
                                arrobject[2] = 0;
                                arrobject[3] = 0;
                                arrobject[4] = 0;
                                n4 = 2;
                                n6 = 0;
                                i = n;
                                bl2 = bl3;
                                n = n6;
                                continue;
                            }
                            arrobject[0] = arrobject[2];
                            arrobject[1] = arrobject[3];
                            arrobject[2] = arrobject[4];
                            arrobject[3] = 1;
                            arrobject[4] = 0;
                        } else {
                            arrobject[0] = arrobject[2];
                            arrobject[1] = arrobject[3];
                            arrobject[2] = arrobject[4];
                            arrobject[3] = 1;
                            arrobject[4] = 0;
                        }
                        n = 3;
                        continue;
                    }
                    arrobject[++n] = arrobject[n] + 1;
                    continue;
                }
                arrobject[n] = arrobject[n] + 1;
            }
            n = n4;
            bl3 = bl2;
            if (FinderPatternFinder.foundPatternCross(arrobject)) {
                n = n4;
                bl3 = bl2;
                if (this.handlePossibleCenter((int[])arrobject, i, n3, bl)) {
                    n = n5 = arrobject[0];
                    bl3 = bl2;
                    if (this.hasSkipped) {
                        bl3 = this.haveMultiplyConfirmedCenters();
                        n = n5;
                    }
                }
            }
            n4 = n;
            bl2 = bl3;
        }
        arrobject = this.selectBestPatterns();
        ResultPoint.orderBestPatterns((ResultPoint[])arrobject);
        return new FinderPatternInfo((FinderPattern[])arrobject);
    }

    protected final BitMatrix getImage() {
        return this.image;
    }

    protected final List<FinderPattern> getPossibleCenters() {
        return this.possibleCenters;
    }

    protected final boolean handlePossibleCenter(int[] object, int n, int n2, boolean bl) {
        int n3 = 0;
        int n4 = object[0] + object[1] + object[2] + object[3] + object[4];
        float f = this.crossCheckVertical(n, n2 = (int)FinderPatternFinder.centerFromEnd((int[])object, n2), object[2], n4);
        if (Float.isNaN(f)) return false;
        n = (int)f;
        float f2 = this.crossCheckHorizontal(n2, n, object[2], n4);
        if (Float.isNaN(f2)) return false;
        if (bl) {
            if (!this.crossCheckDiagonal(n, (int)f2, object[2], n4)) return false;
        }
        float f3 = (float)n4 / 7.0f;
        n2 = 0;
        do {
            n = n3;
            if (n2 >= this.possibleCenters.size()) break;
            object = this.possibleCenters.get(n2);
            if (((FinderPattern)object).aboutEquals(f3, f, f2)) {
                this.possibleCenters.set(n2, ((FinderPattern)object).combineEstimate(f, f2, f3));
                n = 1;
                break;
            }
            ++n2;
        } while (true);
        if (n != 0) return true;
        FinderPattern finderPattern = new FinderPattern(f2, f, f3);
        this.possibleCenters.add(finderPattern);
        object = this.resultPointCallback;
        if (object == null) return true;
        object.foundPossibleResultPoint(finderPattern);
        return true;
    }

    private static final class CenterComparator
    implements Comparator<FinderPattern>,
    Serializable {
        private final float average;

        private CenterComparator(float f) {
            this.average = f;
        }

        @Override
        public int compare(FinderPattern finderPattern, FinderPattern finderPattern2) {
            float f;
            if (finderPattern2.getCount() != finderPattern.getCount()) return finderPattern2.getCount() - finderPattern.getCount();
            float f2 = Math.abs(finderPattern2.getEstimatedModuleSize() - this.average);
            if (f2 < (f = Math.abs(finderPattern.getEstimatedModuleSize() - this.average))) {
                return 1;
            }
            if (f2 != f) return -1;
            return 0;
        }
    }

    private static final class FurthestFromAverageComparator
    implements Comparator<FinderPattern>,
    Serializable {
        private final float average;

        private FurthestFromAverageComparator(float f) {
            this.average = f;
        }

        @Override
        public int compare(FinderPattern finderPattern, FinderPattern finderPattern2) {
            float f;
            float f2 = Math.abs(finderPattern2.getEstimatedModuleSize() - this.average);
            if (f2 < (f = Math.abs(finderPattern.getEstimatedModuleSize() - this.average))) {
                return -1;
            }
            if (f2 != f) return 1;
            return 0;
        }
    }

}

