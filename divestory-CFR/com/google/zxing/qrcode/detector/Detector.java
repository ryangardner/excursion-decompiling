/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.PerspectiveTransform;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.qrcode.decoder.Version;
import com.google.zxing.qrcode.detector.AlignmentPattern;
import com.google.zxing.qrcode.detector.AlignmentPatternFinder;
import com.google.zxing.qrcode.detector.FinderPattern;
import com.google.zxing.qrcode.detector.FinderPatternFinder;
import com.google.zxing.qrcode.detector.FinderPatternInfo;
import java.util.Map;

public class Detector {
    private final BitMatrix image;
    private ResultPointCallback resultPointCallback;

    public Detector(BitMatrix bitMatrix) {
        this.image = bitMatrix;
    }

    private float calculateModuleSizeOneWay(ResultPoint resultPoint, ResultPoint resultPoint2) {
        float f = this.sizeOfBlackWhiteBlackRunBothWays((int)resultPoint.getX(), (int)resultPoint.getY(), (int)resultPoint2.getX(), (int)resultPoint2.getY());
        float f2 = this.sizeOfBlackWhiteBlackRunBothWays((int)resultPoint2.getX(), (int)resultPoint2.getY(), (int)resultPoint.getX(), (int)resultPoint.getY());
        if (Float.isNaN(f)) {
            return f2 / 7.0f;
        }
        if (!Float.isNaN(f2)) return (f + f2) / 14.0f;
        return f / 7.0f;
    }

    private static int computeDimension(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, float f) throws NotFoundException {
        int n = (MathUtils.round(ResultPoint.distance(resultPoint, resultPoint2) / f) + MathUtils.round(ResultPoint.distance(resultPoint, resultPoint3) / f)) / 2 + 7;
        int n2 = n & 3;
        if (n2 == 0) {
            ++n;
            return n;
        }
        if (n2 != 2) {
            if (n2 == 3) throw NotFoundException.getNotFoundInstance();
            return n;
        }
        return --n;
    }

    private static PerspectiveTransform createTransform(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4, int n) {
        float f;
        float f2;
        float f3;
        float f4 = (float)n - 3.5f;
        if (resultPoint4 != null) {
            f = resultPoint4.getX();
            f2 = resultPoint4.getY();
            f3 = f4 - 3.0f;
            return PerspectiveTransform.quadrilateralToQuadrilateral(3.5f, 3.5f, f4, 3.5f, f3, f3, 3.5f, f4, resultPoint.getX(), resultPoint.getY(), resultPoint2.getX(), resultPoint2.getY(), f, f2, resultPoint3.getX(), resultPoint3.getY());
        }
        f = resultPoint2.getX();
        float f5 = resultPoint.getX();
        float f6 = resultPoint3.getX();
        f2 = resultPoint2.getY();
        f3 = resultPoint.getY();
        float f7 = resultPoint3.getY();
        f = f - f5 + f6;
        f2 = f2 - f3 + f7;
        f3 = f4;
        return PerspectiveTransform.quadrilateralToQuadrilateral(3.5f, 3.5f, f4, 3.5f, f3, f3, 3.5f, f4, resultPoint.getX(), resultPoint.getY(), resultPoint2.getX(), resultPoint2.getY(), f, f2, resultPoint3.getX(), resultPoint3.getY());
    }

    private static BitMatrix sampleGrid(BitMatrix bitMatrix, PerspectiveTransform perspectiveTransform, int n) throws NotFoundException {
        return GridSampler.getInstance().sampleGrid(bitMatrix, n, n, perspectiveTransform);
    }

    private float sizeOfBlackWhiteBlackRun(int n, int n2, int n3, int n4) {
        int n5;
        int n6;
        int n7;
        block8 : {
            int n8;
            n6 = Math.abs(n4 - n2) > Math.abs(n3 - n) ? 1 : 0;
            if (n6 != 0) {
                n8 = n2;
                n5 = n3;
                n2 = n4;
                n4 = n8;
                n3 = n;
                n = n2;
            } else {
                n8 = n;
                n = n3;
                n5 = n4;
                n3 = n2;
                n4 = n8;
            }
            int n9 = Math.abs(n - n4);
            int n10 = Math.abs(n5 - n3);
            int n11 = -n9 / 2;
            int n12 = -1;
            int n13 = n4 < n ? 1 : -1;
            if (n3 < n5) {
                n12 = 1;
            }
            n7 = n + n13;
            n2 = n3;
            n8 = 0;
            int n14 = n6;
            for (n = n4; n != n7; n += n13) {
                int n15 = n14 != 0 ? n2 : n;
                int n16 = n14 != 0 ? n : n2;
                boolean bl = n8 == 1;
                n6 = n8;
                if (bl == this.image.get(n15, n16)) {
                    if (n8 == 2) {
                        return MathUtils.distance(n, n2, n4, n3);
                    }
                    n6 = n8 + 1;
                }
                n8 = n11 += n10;
                n15 = n2;
                if (n11 > 0) {
                    if (n2 == n5) break block8;
                    n15 = n2 + n12;
                    n8 = n11 - n9;
                }
                n11 = n8;
                n2 = n15;
                n8 = n6;
            }
            n6 = n8;
        }
        if (n6 != 2) return Float.NaN;
        return MathUtils.distance(n7, n5, n4, n3);
    }

    private float sizeOfBlackWhiteBlackRunBothWays(int n, int n2, int n3, int n4) {
        float f;
        float f2 = this.sizeOfBlackWhiteBlackRun(n, n2, n3, n4);
        n3 = n - (n3 - n);
        int n5 = 0;
        if (n3 < 0) {
            f = (float)n / (float)(n - n3);
            n3 = 0;
        } else if (n3 >= this.image.getWidth()) {
            f = (float)(this.image.getWidth() - 1 - n) / (float)(n3 - n);
            n3 = this.image.getWidth() - 1;
        } else {
            f = 1.0f;
        }
        float f3 = n2;
        n4 = (int)(f3 - (float)(n4 - n2) * f);
        if (n4 < 0) {
            f = f3 / (float)(n2 - n4);
            n4 = n5;
            return f2 + this.sizeOfBlackWhiteBlackRun(n, n2, (int)((float)n + (float)(n3 - n) * f), n4) - 1.0f;
        }
        if (n4 >= this.image.getHeight()) {
            f = (float)(this.image.getHeight() - 1 - n2) / (float)(n4 - n2);
            n4 = this.image.getHeight() - 1;
            return f2 + this.sizeOfBlackWhiteBlackRun(n, n2, (int)((float)n + (float)(n3 - n) * f), n4) - 1.0f;
        }
        f = 1.0f;
        return f2 + this.sizeOfBlackWhiteBlackRun(n, n2, (int)((float)n + (float)(n3 - n) * f), n4) - 1.0f;
    }

    protected final float calculateModuleSize(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3) {
        return (this.calculateModuleSizeOneWay(resultPoint, resultPoint2) + this.calculateModuleSizeOneWay(resultPoint, resultPoint3)) / 2.0f;
    }

    public DetectorResult detect() throws NotFoundException, FormatException {
        return this.detect(null);
    }

    public final DetectorResult detect(Map<DecodeHintType, ?> map) throws NotFoundException, FormatException {
        ResultPointCallback resultPointCallback = map == null ? null : (ResultPointCallback)map.get((Object)DecodeHintType.NEED_RESULT_POINT_CALLBACK);
        this.resultPointCallback = resultPointCallback;
        return this.processFinderPatternInfo(new FinderPatternFinder(this.image, this.resultPointCallback).find(map));
    }

    protected final AlignmentPattern findAlignmentInRegion(float f, int n, int n2, float f2) throws NotFoundException {
        int n3 = (int)(f2 * f);
        int n4 = Math.max(0, n - n3);
        int n5 = Math.min(this.image.getWidth() - 1, n + n3) - n4;
        float f3 = n5;
        if (f3 < (f2 = 3.0f * f)) throw NotFoundException.getNotFoundInstance();
        n = Math.max(0, n2 - n3);
        n2 = Math.min(this.image.getHeight() - 1, n2 + n3) - n;
        if ((float)n2 < f2) throw NotFoundException.getNotFoundInstance();
        return new AlignmentPatternFinder(this.image, n4, n, n5, n2, f, this.resultPointCallback).find();
    }

    protected final BitMatrix getImage() {
        return this.image;
    }

    protected final ResultPointCallback getResultPointCallback() {
        return this.resultPointCallback;
    }

    protected final DetectorResult processFinderPatternInfo(FinderPatternInfo object) throws NotFoundException, FormatException {
        FinderPattern finderPattern;
        FinderPattern finderPattern2;
        FinderPattern finderPattern3 = object.getTopLeft();
        float f = this.calculateModuleSize(finderPattern3, finderPattern = object.getTopRight(), finderPattern2 = object.getBottomLeft());
        if (f < 1.0f) throw NotFoundException.getNotFoundInstance();
        int n = Detector.computeDimension(finderPattern3, finderPattern, finderPattern2, f);
        Version version = Version.getProvisionalVersionForDimension(n);
        int n2 = version.getDimensionForVersion();
        Object object2 = null;
        object = object2;
        if (version.getAlignmentPatternCenters().length > 0) {
            float f2 = finderPattern.getX();
            float f3 = finderPattern3.getX();
            float f4 = finderPattern2.getX();
            float f5 = finderPattern.getY();
            float f6 = finderPattern3.getY();
            float f7 = finderPattern2.getY();
            float f8 = 1.0f - 3.0f / (float)(n2 - 7);
            int n3 = (int)(finderPattern3.getX() + (f2 - f3 + f4 - finderPattern3.getX()) * f8);
            int n4 = (int)(finderPattern3.getY() + f8 * (f5 - f6 + f7 - finderPattern3.getY()));
            n2 = 4;
            do {
                object = object2;
                if (n2 > 16) break;
                f2 = n2;
                try {
                    object = this.findAlignmentInRegion(f, n3, n4, f2);
                }
                catch (NotFoundException notFoundException) {
                    n2 <<= 1;
                    continue;
                }
                break;
            } while (true);
        }
        object2 = Detector.createTransform(finderPattern3, finderPattern, finderPattern2, (ResultPoint)object, n);
        object2 = Detector.sampleGrid(this.image, (PerspectiveTransform)object2, n);
        if (object == null) {
            object = new ResultPoint[]{finderPattern2, finderPattern3, finderPattern};
            return new DetectorResult((BitMatrix)object2, (ResultPoint[])object);
        }
        object = new ResultPoint[]{finderPattern2, finderPattern3, finderPattern, object};
        return new DetectorResult((BitMatrix)object2, (ResultPoint[])object);
    }
}

