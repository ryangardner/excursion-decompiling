/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.common.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.detector.MathUtils;

public final class WhiteRectangleDetector {
    private static final int CORR = 1;
    private static final int INIT_SIZE = 10;
    private final int downInit;
    private final int height;
    private final BitMatrix image;
    private final int leftInit;
    private final int rightInit;
    private final int upInit;
    private final int width;

    public WhiteRectangleDetector(BitMatrix bitMatrix) throws NotFoundException {
        this(bitMatrix, 10, bitMatrix.getWidth() / 2, bitMatrix.getHeight() / 2);
    }

    public WhiteRectangleDetector(BitMatrix bitMatrix, int n, int n2, int n3) throws NotFoundException {
        int n4;
        int n5;
        this.image = bitMatrix;
        this.height = bitMatrix.getHeight();
        this.width = n5 = bitMatrix.getWidth();
        int n6 = n / 2;
        this.leftInit = n = n2 - n6;
        this.rightInit = n4 = n2 + n6;
        this.upInit = n2 = n3 - n6;
        this.downInit = n3 += n6;
        if (n2 < 0) throw NotFoundException.getNotFoundInstance();
        if (n < 0) throw NotFoundException.getNotFoundInstance();
        if (n3 >= this.height) throw NotFoundException.getNotFoundInstance();
        if (n4 >= n5) throw NotFoundException.getNotFoundInstance();
    }

    private ResultPoint[] centerEdges(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4) {
        float f = resultPoint.getX();
        float f2 = resultPoint.getY();
        float f3 = resultPoint2.getX();
        float f4 = resultPoint2.getY();
        float f5 = resultPoint3.getX();
        float f6 = resultPoint3.getY();
        float f7 = resultPoint4.getX();
        float f8 = resultPoint4.getY();
        if (!(f < (float)this.width / 2.0f)) return new ResultPoint[]{new ResultPoint(f7 + 1.0f, f8 + 1.0f), new ResultPoint(f3 + 1.0f, f4 - 1.0f), new ResultPoint(f5 - 1.0f, f6 + 1.0f), new ResultPoint(f - 1.0f, f2 - 1.0f)};
        return new ResultPoint[]{new ResultPoint(f7 - 1.0f, f8 + 1.0f), new ResultPoint(f3 + 1.0f, f4 + 1.0f), new ResultPoint(f5 - 1.0f, f6 - 1.0f), new ResultPoint(f + 1.0f, f2 - 1.0f)};
    }

    private boolean containsBlackPoint(int n, int n2, int n3, boolean bl) {
        int n4 = n;
        if (bl) {
            while (n <= n2) {
                if (this.image.get(n, n3)) {
                    return true;
                }
                ++n;
            }
            return false;
        }
        while (n4 <= n2) {
            if (this.image.get(n3, n4)) {
                return true;
            }
            ++n4;
        }
        return false;
    }

    private ResultPoint getBlackPointOnSegment(float f, float f2, float f3, float f4) {
        int n = MathUtils.round(MathUtils.distance(f, f2, f3, f4));
        float f5 = n;
        f3 = (f3 - f) / f5;
        f4 = (f4 - f2) / f5;
        int n2 = 0;
        while (n2 < n) {
            int n3;
            f5 = n2;
            int n4 = MathUtils.round(f5 * f3 + f);
            if (this.image.get(n4, n3 = MathUtils.round(f5 * f4 + f2))) {
                return new ResultPoint(n4, n3);
            }
            ++n2;
        }
        return null;
    }

    /*
     * Unable to fully structure code
     */
    public ResultPoint[] detect() throws NotFoundException {
        var1_1 = this.leftInit;
        var2_2 = this.rightInit;
        var3_3 = this.upInit;
        var4_4 = this.downInit;
        var5_5 = 0;
        var6_6 = 1;
        var7_7 = 1;
        var8_8 = false;
        var9_9 = false;
        var10_10 = false;
        var11_11 = false;
        var12_12 = false;
        block0 : do {
            block20 : {
                var13_13 = var1_1;
                var14_14 = var2_2;
                var15_15 = var3_3;
                var16_16 = var4_4;
                var17_17 = var5_5;
                if (var7_7 == 0) break;
                var18_18 = true;
                var15_15 = 0;
                var19_19 = var8_8;
                var14_14 = var2_2;
                while ((var18_18 || !var19_19) && var14_14 < this.width) {
                    var20_20 = this.containsBlackPoint(var3_3, var4_4, var14_14, false);
                    if (var20_20) {
                        ++var14_14;
                        var19_19 = true;
                        var15_15 = 1;
                        var18_18 = var20_20;
                        continue;
                    }
                    var18_18 = var20_20;
                    if (var19_19) continue;
                    ++var14_14;
                    var18_18 = var20_20;
                }
                if (var14_14 < this.width) {
                    var18_18 = true;
                    var21_21 = var9_9;
                    var16_16 = var4_4;
                } else {
                    var16_16 = var4_4;
lbl43: // 4 sources:
                    do {
                        var17_17 = 1;
                        var13_13 = var1_1;
                        var15_15 = var3_3;
                        break block0;
                        break;
                    } while (true);
                }
                while ((var18_18 || !var21_21) && var16_16 < this.height) {
                    var20_20 = this.containsBlackPoint(var1_1, var14_14, var16_16, true);
                    if (var20_20) {
                        ++var16_16;
                        var21_21 = true;
                        var15_15 = 1;
                        var18_18 = var20_20;
                        continue;
                    }
                    var18_18 = var20_20;
                    if (var21_21) continue;
                    ++var16_16;
                    var18_18 = var20_20;
                }
                if (var16_16 >= this.height) ** GOTO lbl43
                var18_18 = true;
                var4_4 = var15_15;
                var22_22 = var10_10;
                var15_15 = var1_1;
                while ((var18_18 || !var22_22) && var15_15 >= 0) {
                    var20_20 = this.containsBlackPoint(var3_3, var16_16, var15_15, false);
                    if (var20_20) {
                        --var15_15;
                        var22_22 = true;
                        var4_4 = 1;
                        var18_18 = var20_20;
                        continue;
                    }
                    var18_18 = var20_20;
                    if (var22_22) continue;
                    --var15_15;
                    var18_18 = var20_20;
                }
                if (var15_15 >= 0) break block20;
                var1_1 = var15_15;
                ** GOTO lbl43
            }
            var18_18 = true;
            var23_23 = var12_12;
            var17_17 = var4_4;
            var13_13 = var3_3;
            while ((var18_18 || !var23_23) && var13_13 >= 0) {
                var20_20 = this.containsBlackPoint(var15_15, var14_14, var13_13, true);
                if (var20_20) {
                    --var13_13;
                    var17_17 = 1;
                    var23_23 = true;
                    var18_18 = var20_20;
                    continue;
                }
                var18_18 = var20_20;
                if (var23_23) continue;
                --var13_13;
                var18_18 = var20_20;
            }
            if (var13_13 < 0) {
                var1_1 = var15_15;
                var3_3 = var13_13;
                ** continue;
            }
            var1_1 = var15_15;
            var2_2 = var14_14;
            var3_3 = var13_13;
            var4_4 = var16_16;
            var7_7 = var17_17;
            var8_8 = var19_19;
            var9_9 = var21_21;
            var10_10 = var22_22;
            var12_12 = var23_23;
            if (var17_17 == 0) continue;
            var11_11 = true;
            var1_1 = var15_15;
            var2_2 = var14_14;
            var3_3 = var13_13;
            var4_4 = var16_16;
            var7_7 = var17_17;
            var8_8 = var19_19;
            var9_9 = var21_21;
            var10_10 = var22_22;
            var12_12 = var23_23;
        } while (true);
        if (var17_17 != 0) throw NotFoundException.getNotFoundInstance();
        if (var11_11 == false) throw NotFoundException.getNotFoundInstance();
        var1_1 = var14_14 - var13_13;
        var24_24 = null;
        var25_25 = null;
        var3_3 = 1;
        do {
            var26_26 = var25_25;
            if (var3_3 >= var1_1) break;
            var25_25 = this.getBlackPointOnSegment(var13_13, var16_16 - var3_3, var13_13 + var3_3, var16_16);
            if (var25_25 != null) {
                var26_26 = var25_25;
                break;
            }
            ++var3_3;
        } while (true);
        if (var26_26 == null) throw NotFoundException.getNotFoundInstance();
        var25_25 = null;
        var3_3 = 1;
        do {
            var27_27 = var25_25;
            if (var3_3 >= var1_1) break;
            var25_25 = this.getBlackPointOnSegment(var13_13, var15_15 + var3_3, var13_13 + var3_3, var15_15);
            if (var25_25 != null) {
                var27_27 = var25_25;
                break;
            }
            ++var3_3;
        } while (true);
        if (var27_27 == null) throw NotFoundException.getNotFoundInstance();
        var25_25 = null;
        var3_3 = 1;
        do {
            var28_28 = var25_25;
            if (var3_3 >= var1_1) break;
            var25_25 = this.getBlackPointOnSegment(var14_14, var15_15 + var3_3, var14_14 - var3_3, var15_15);
            if (var25_25 != null) {
                var28_28 = var25_25;
                break;
            }
            ++var3_3;
        } while (true);
        if (var28_28 == null) throw NotFoundException.getNotFoundInstance();
        var25_25 = var24_24;
        for (var3_3 = var6_6; var3_3 < var1_1 && (var25_25 = this.getBlackPointOnSegment(var14_14, var16_16 - var3_3, var14_14 - var3_3, var16_16)) == null; ++var3_3) {
        }
        if (var25_25 == null) throw NotFoundException.getNotFoundInstance();
        return this.centerEdges(var25_25, var26_26, var28_28, var27_27);
    }
}

