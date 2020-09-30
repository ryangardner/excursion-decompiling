/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.aztec.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.aztec.AztecDetectorResult;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.common.detector.WhiteRectangleDetector;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;

public final class Detector {
    private static final int[] EXPECTED_CORNER_BITS = new int[]{3808, 476, 2107, 1799};
    private boolean compact;
    private final BitMatrix image;
    private int nbCenterLayers;
    private int nbDataBlocks;
    private int nbLayers;
    private int shift;

    public Detector(BitMatrix bitMatrix) {
        this.image = bitMatrix;
    }

    private static float distance(ResultPoint resultPoint, ResultPoint resultPoint2) {
        return MathUtils.distance(resultPoint.getX(), resultPoint.getY(), resultPoint2.getX(), resultPoint2.getY());
    }

    private static float distance(Point point, Point point2) {
        return MathUtils.distance(point.getX(), point.getY(), point2.getX(), point2.getY());
    }

    private static ResultPoint[] expandSquare(ResultPoint[] arrresultPoint, float f, float f2) {
        f = f2 / (f * 2.0f);
        float f3 = arrresultPoint[0].getX();
        float f4 = arrresultPoint[2].getX();
        float f5 = arrresultPoint[0].getY();
        float f6 = arrresultPoint[2].getY();
        f2 = (arrresultPoint[0].getX() + arrresultPoint[2].getX()) / 2.0f;
        float f7 = (arrresultPoint[0].getY() + arrresultPoint[2].getY()) / 2.0f;
        f4 = (f3 - f4) * f;
        f6 = (f5 - f6) * f;
        ResultPoint resultPoint = new ResultPoint(f2 + f4, f7 + f6);
        ResultPoint resultPoint2 = new ResultPoint(f2 - f4, f7 - f6);
        f4 = arrresultPoint[1].getX();
        f3 = arrresultPoint[3].getX();
        f6 = arrresultPoint[1].getY();
        f5 = arrresultPoint[3].getY();
        f2 = (arrresultPoint[1].getX() + arrresultPoint[3].getX()) / 2.0f;
        f7 = (arrresultPoint[1].getY() + arrresultPoint[3].getY()) / 2.0f;
        f4 = (f4 - f3) * f;
        return new ResultPoint[]{resultPoint, new ResultPoint(f2 + f4, f7 + (f *= f6 - f5)), resultPoint2, new ResultPoint(f2 - f4, f7 - f)};
    }

    private void extractParameters(ResultPoint[] arrresultPoint) throws NotFoundException {
        int n;
        long l;
        if (!this.isValid(arrresultPoint[0])) throw NotFoundException.getNotFoundInstance();
        if (!this.isValid(arrresultPoint[1])) throw NotFoundException.getNotFoundInstance();
        if (!this.isValid(arrresultPoint[2])) throw NotFoundException.getNotFoundInstance();
        if (!this.isValid(arrresultPoint[3])) throw NotFoundException.getNotFoundInstance();
        int n2 = this.nbCenterLayers * 2;
        int[] arrn = new int[]{this.sampleLine(arrresultPoint[0], arrresultPoint[1], n2), this.sampleLine(arrresultPoint[1], arrresultPoint[2], n2), this.sampleLine(arrresultPoint[2], arrresultPoint[3], n2), this.sampleLine(arrresultPoint[3], arrresultPoint[0], n2)};
        this.shift = Detector.getRotation(arrn, n2);
        long l2 = 0L;
        for (n = 0; n < 4; l2 += l, ++n) {
            n2 = arrn[(this.shift + n) % 4];
            if (this.compact) {
                l2 <<= 7;
                l = n2 >> 1 & 127;
                continue;
            }
            l2 <<= 10;
            l = (n2 >> 2 & 992) + (n2 >> 1 & 31);
        }
        n = Detector.getCorrectedParameterData(l2, this.compact);
        if (this.compact) {
            this.nbLayers = (n >> 6) + 1;
            this.nbDataBlocks = (n & 63) + 1;
            return;
        }
        this.nbLayers = (n >> 11) + 1;
        this.nbDataBlocks = (n & 2047) + 1;
    }

    private ResultPoint[] getBullsEyeCorners(Point object) throws NotFoundException {
        int n;
        Object object2;
        Object object3;
        this.nbCenterLayers = 1;
        Object object4 = object3 = (object2 = object);
        boolean bl = true;
        while (this.nbCenterLayers < 9) {
            double d;
            Point point = this.getFirstDifferent((Point)object, bl, 1, -1);
            Point point2 = this.getFirstDifferent((Point)object2, bl, 1, 1);
            Point point3 = this.getFirstDifferent((Point)object3, bl, -1, 1);
            Point point4 = this.getFirstDifferent((Point)object4, bl, -1, -1);
            if (this.nbCenterLayers > 2 && ((d = (double)(Detector.distance(point4, point) * (float)this.nbCenterLayers / (Detector.distance((Point)object4, (Point)object) * (float)(this.nbCenterLayers + 2)))) < 0.75 || d > 1.25 || !this.isWhiteOrBlackRectangle(point, point2, point3, point4))) break;
            bl ^= true;
            ++this.nbCenterLayers;
            object4 = point4;
            object = point;
            object2 = point2;
            object3 = point3;
        }
        if ((n = this.nbCenterLayers) != 5) {
            if (n != 7) throw NotFoundException.getNotFoundInstance();
        }
        bl = this.nbCenterLayers == 5;
        this.compact = bl;
        object = new ResultPoint((float)((Point)object).getX() + 0.5f, (float)((Point)object).getY() - 0.5f);
        object2 = new ResultPoint((float)((Point)object2).getX() + 0.5f, (float)((Point)object2).getY() + 0.5f);
        object3 = new ResultPoint((float)((Point)object3).getX() - 0.5f, (float)((Point)object3).getY() + 0.5f);
        object4 = new ResultPoint((float)((Point)object4).getX() - 0.5f, (float)((Point)object4).getY() - 0.5f);
        n = this.nbCenterLayers;
        float f = n * 2 - 3;
        float f2 = n * 2;
        return Detector.expandSquare(new ResultPoint[]{object, object2, object3, object4}, f, f2);
    }

    private int getColor(Point point, Point point2) {
        float f = Detector.distance(point, point2);
        float f2 = (float)(point2.getX() - point.getX()) / f;
        float f3 = (float)(point2.getY() - point.getY()) / f;
        float f4 = point.getX();
        float f5 = point.getY();
        boolean bl = this.image.get(point.getX(), point.getY());
        boolean bl2 = false;
        int n = 0;
        int n2 = 0;
        while ((float)n < f) {
            int n3 = n2;
            if (this.image.get(MathUtils.round(f4 += f2), MathUtils.round(f5 += f3)) != bl) {
                n3 = n2 + 1;
            }
            ++n;
            n2 = n3;
        }
        f5 = (float)n2 / f;
        if (f5 > 0.1f && f5 < 0.9f) {
            return 0;
        }
        n2 = 1;
        if (f5 <= 0.1f) {
            bl2 = true;
        }
        if (bl2 != bl) return -1;
        return n2;
    }

    private static int getCorrectedParameterData(long l, boolean bl) throws NotFoundException {
        int n;
        int n2;
        int n3;
        if (bl) {
            n3 = 7;
            n = 2;
        } else {
            n3 = 10;
            n = 4;
        }
        int[] arrn = new int[n3];
        for (n2 = n3 - 1; n2 >= 0; l >>= 4, --n2) {
            arrn[n2] = (int)l & 15;
        }
        try {
            ReedSolomonDecoder reedSolomonDecoder = new ReedSolomonDecoder(GenericGF.AZTEC_PARAM);
            reedSolomonDecoder.decode(arrn, n3 - n);
            n3 = 0;
            n2 = 0;
        }
        catch (ReedSolomonException reedSolomonException) {
            throw NotFoundException.getNotFoundInstance();
        }
        while (n3 < n) {
            n2 = (n2 << 4) + arrn[n3];
            ++n3;
        }
        return n2;
    }

    private int getDimension() {
        if (this.compact) {
            return this.nbLayers * 4 + 11;
        }
        int n = this.nbLayers;
        if (n > 4) return n * 4 + ((n - 4) / 8 + 1) * 2 + 15;
        return n * 4 + 15;
    }

    private Point getFirstDifferent(Point point, boolean bl, int n, int n2) {
        int n3 = point.getX() + n;
        int n4 = point.getY();
        while (this.isValid(n3, n4 += n2) && this.image.get(n3, n4) == bl) {
            n3 += n;
        }
        int n5 = n3 - n;
        n3 = n4 - n2;
        n4 = n5;
        while (this.isValid(n4, n3) && this.image.get(n4, n3) == bl) {
            n4 += n;
        }
        n4 -= n;
        n = n3;
        while (this.isValid(n4, n)) {
            if (this.image.get(n4, n) != bl) return new Point(n4, n - n2);
            n += n2;
        }
        return new Point(n4, n - n2);
    }

    private Point getMatrixCenter() {
        int n;
        Object object;
        int n2;
        ResultPoint resultPoint;
        Object object2;
        Object object3;
        int n3;
        int n4;
        try {
            object = new WhiteRectangleDetector(this.image);
            object3 = ((WhiteRectangleDetector)object).detect();
        }
        catch (NotFoundException notFoundException) {
            n = this.image.getWidth() / 2;
            n2 = this.image.getHeight() / 2;
            n4 = n + 7;
            n3 = n2 - 7;
            object = this.getFirstDifferent(new Point(n4, n3), false, 1, -1).toResultPoint();
            object2 = this.getFirstDifferent(new Point(n4, n2 += 7), false, 1, 1).toResultPoint();
            resultPoint = this.getFirstDifferent(new Point(n -= 7, n2), false, -1, 1).toResultPoint();
            object3 = this.getFirstDifferent(new Point(n, n3), false, -1, -1).toResultPoint();
        }
        object = object3[0];
        object2 = object3[1];
        resultPoint = object3[2];
        object3 = object3[3];
        n = MathUtils.round((((ResultPoint)object).getX() + ((ResultPoint)object3).getX() + ((ResultPoint)object2).getX() + resultPoint.getX()) / 4.0f);
        n2 = MathUtils.round((((ResultPoint)object).getY() + ((ResultPoint)object3).getY() + ((ResultPoint)object2).getY() + resultPoint.getY()) / 4.0f);
        try {
            object = new WhiteRectangleDetector(this.image, 15, n, n2);
            object2 = ((WhiteRectangleDetector)object).detect();
        }
        catch (NotFoundException notFoundException) {
            n4 = n + 7;
            n3 = n2 - 7;
            resultPoint = this.getFirstDifferent(new Point(n4, n3), false, 1, -1).toResultPoint();
            object = this.getFirstDifferent(new Point(n4, n2 += 7), false, 1, 1).toResultPoint();
            object3 = this.getFirstDifferent(new Point(n -= 7, n2), false, -1, 1).toResultPoint();
            object2 = this.getFirstDifferent(new Point(n, n3), false, -1, -1).toResultPoint();
        }
        resultPoint = object2[0];
        object = object2[1];
        object3 = object2[2];
        object2 = object2[3];
        return new Point(MathUtils.round((resultPoint.getX() + ((ResultPoint)object2).getX() + ((ResultPoint)object).getX() + ((ResultPoint)object3).getX()) / 4.0f), MathUtils.round((resultPoint.getY() + ((ResultPoint)object2).getY() + ((ResultPoint)object).getY() + ((ResultPoint)object3).getY()) / 4.0f));
    }

    private ResultPoint[] getMatrixCornerPoints(ResultPoint[] arrresultPoint) {
        return Detector.expandSquare(arrresultPoint, this.nbCenterLayers * 2, this.getDimension());
    }

    private static int getRotation(int[] arrn, int n) throws NotFoundException {
        int n2 = arrn.length;
        int n3 = 0;
        int n4 = 0;
        for (int i = 0; i < n2; ++i) {
            int n5 = arrn[i];
            n4 = (n4 << 3) + ((n5 >> n - 2 << 1) + (n5 & 1));
        }
        n = n3;
        while (n < 4) {
            if (Integer.bitCount(EXPECTED_CORNER_BITS[n] ^ ((n4 & 1) << 11) + (n4 >> 1)) <= 2) {
                return n;
            }
            ++n;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private boolean isValid(int n, int n2) {
        if (n < 0) return false;
        if (n >= this.image.getWidth()) return false;
        if (n2 <= 0) return false;
        if (n2 >= this.image.getHeight()) return false;
        return true;
    }

    private boolean isValid(ResultPoint resultPoint) {
        return this.isValid(MathUtils.round(resultPoint.getX()), MathUtils.round(resultPoint.getY()));
    }

    private boolean isWhiteOrBlackRectangle(Point point, Point point2, Point point3, Point point4) {
        point = new Point(point.getX() - 3, point.getY() + 3);
        point2 = new Point(point2.getX() - 3, point2.getY() - 3);
        point3 = new Point(point3.getX() + 3, point3.getY() - 3);
        point4 = new Point(point4.getX() + 3, point4.getY() + 3);
        int n = this.getColor(point4, point);
        boolean bl = false;
        if (n == 0) {
            return false;
        }
        if (this.getColor(point, point2) != n) {
            return false;
        }
        if (this.getColor(point2, point3) != n) {
            return false;
        }
        if (this.getColor(point3, point4) != n) return bl;
        return true;
    }

    private BitMatrix sampleGrid(BitMatrix bitMatrix, ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4) throws NotFoundException {
        GridSampler gridSampler = GridSampler.getInstance();
        int n = this.getDimension();
        float f = (float)n / 2.0f;
        int n2 = this.nbCenterLayers;
        float f2 = f - (float)n2;
        return gridSampler.sampleGrid(bitMatrix, n, n, f2, f2, f += (float)n2, f2, f, f, f2, f, resultPoint.getX(), resultPoint.getY(), resultPoint2.getX(), resultPoint2.getY(), resultPoint3.getX(), resultPoint3.getY(), resultPoint4.getX(), resultPoint4.getY());
    }

    private int sampleLine(ResultPoint object, ResultPoint resultPoint, int n) {
        float f = Detector.distance((ResultPoint)object, resultPoint);
        float f2 = f / (float)n;
        float f3 = ((ResultPoint)object).getX();
        float f4 = ((ResultPoint)object).getY();
        float f5 = (resultPoint.getX() - ((ResultPoint)object).getX()) * f2 / f;
        f2 = f2 * (resultPoint.getY() - ((ResultPoint)object).getY()) / f;
        int n2 = 0;
        int n3 = 0;
        while (n2 < n) {
            object = this.image;
            f = n2;
            int n4 = n3;
            if (((BitMatrix)object).get(MathUtils.round(f * f5 + f3), MathUtils.round(f * f2 + f4))) {
                n4 = n3 | 1 << n - n2 - 1;
            }
            ++n2;
            n3 = n4;
        }
        return n3;
    }

    public AztecDetectorResult detect() throws NotFoundException {
        return this.detect(false);
    }

    public AztecDetectorResult detect(boolean bl) throws NotFoundException {
        Object object;
        ResultPoint[] arrresultPoint = this.getBullsEyeCorners(this.getMatrixCenter());
        if (bl) {
            object = arrresultPoint[0];
            arrresultPoint[0] = arrresultPoint[2];
            arrresultPoint[2] = object;
        }
        this.extractParameters(arrresultPoint);
        object = this.image;
        int n = this.shift;
        return new AztecDetectorResult(this.sampleGrid((BitMatrix)object, arrresultPoint[n % 4], arrresultPoint[(n + 1) % 4], arrresultPoint[(n + 2) % 4], arrresultPoint[(n + 3) % 4]), this.getMatrixCornerPoints(arrresultPoint), this.compact, this.nbDataBlocks, this.nbLayers);
    }

    static final class Point {
        private final int x;
        private final int y;

        Point(int n, int n2) {
            this.x = n;
            this.y = n2;
        }

        int getX() {
            return this.x;
        }

        int getY() {
            return this.y;
        }

        ResultPoint toResultPoint() {
            return new ResultPoint(this.getX(), this.getY());
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<");
            stringBuilder.append(this.x);
            stringBuilder.append(' ');
            stringBuilder.append(this.y);
            stringBuilder.append('>');
            return stringBuilder.toString();
        }
    }

}

