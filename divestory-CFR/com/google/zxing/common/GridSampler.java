/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.common;

import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DefaultGridSampler;
import com.google.zxing.common.PerspectiveTransform;

public abstract class GridSampler {
    private static GridSampler gridSampler = new DefaultGridSampler();

    /*
     * Unable to fully structure code
     */
    protected static void checkAndNudgePoints(BitMatrix var0, float[] var1_1) throws NotFoundException {
        var2_2 = var0.getWidth();
        var3_3 = var0.getHeight();
        var5_5 = 1;
        for (var4_4 = 0; var4_4 < var1_1.length && var5_5 != 0; var4_4 += 2) {
            block11 : {
                var5_5 = (int)var1_1[var4_4];
                var6_6 = var4_4 + 1;
                var7_7 = (int)var1_1[var6_6];
                if (var5_5 < -1) throw NotFoundException.getNotFoundInstance();
                if (var5_5 > var2_2) throw NotFoundException.getNotFoundInstance();
                if (var7_7 < -1) throw NotFoundException.getNotFoundInstance();
                if (var7_7 > var3_3) throw NotFoundException.getNotFoundInstance();
                if (var5_5 != -1) break block11;
                var1_1[var4_4] = 0.0f;
                ** GOTO lbl18
            }
            if (var5_5 == var2_2) {
                var1_1[var4_4] = var2_2 - 1;
lbl18: // 2 sources:
                var5_5 = 1;
            } else {
                var5_5 = 0;
            }
            if (var7_7 == -1) {
                var1_1[var6_6] = 0.0f;
            } else {
                if (var7_7 != var3_3) continue;
                var1_1[var6_6] = var3_3 - 1;
            }
            var5_5 = 1;
        }
        var4_4 = var1_1.length - 2;
        var5_5 = 1;
        while (var4_4 >= 0) {
            block15 : {
                block14 : {
                    block13 : {
                        block12 : {
                            if (var5_5 == 0) return;
                            var5_5 = (int)var1_1[var4_4];
                            var6_6 = var4_4 + 1;
                            var7_7 = (int)var1_1[var6_6];
                            if (var5_5 < -1) throw NotFoundException.getNotFoundInstance();
                            if (var5_5 > var2_2) throw NotFoundException.getNotFoundInstance();
                            if (var7_7 < -1) throw NotFoundException.getNotFoundInstance();
                            if (var7_7 > var3_3) throw NotFoundException.getNotFoundInstance();
                            if (var5_5 != -1) break block12;
                            var1_1[var4_4] = 0.0f;
                            ** GOTO lbl45
                        }
                        if (var5_5 == var2_2) {
                            var1_1[var4_4] = var2_2 - 1;
lbl45: // 2 sources:
                            var5_5 = 1;
                        } else {
                            var5_5 = 0;
                        }
                        if (var7_7 != -1) break block13;
                        var1_1[var6_6] = 0.0f;
                        break block14;
                    }
                    if (var7_7 != var3_3) break block15;
                    var1_1[var6_6] = var3_3 - 1;
                }
                var5_5 = 1;
            }
            var4_4 -= 2;
        }
    }

    public static GridSampler getInstance() {
        return gridSampler;
    }

    public static void setGridSampler(GridSampler gridSampler) {
        GridSampler.gridSampler = gridSampler;
    }

    public abstract BitMatrix sampleGrid(BitMatrix var1, int var2, int var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11, float var12, float var13, float var14, float var15, float var16, float var17, float var18, float var19) throws NotFoundException;

    public abstract BitMatrix sampleGrid(BitMatrix var1, int var2, int var3, PerspectiveTransform var4) throws NotFoundException;
}

