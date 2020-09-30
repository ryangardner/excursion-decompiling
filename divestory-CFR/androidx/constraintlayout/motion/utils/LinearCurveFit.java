/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.motion.utils;

import androidx.constraintlayout.motion.utils.CurveFit;

public class LinearCurveFit
extends CurveFit {
    private static final String TAG = "LinearCurveFit";
    private double[] mT;
    private double mTotalLength = Double.NaN;
    private double[][] mY;

    public LinearCurveFit(double[] arrd, double[][] arrd2) {
        double d;
        int n = arrd.length;
        n = arrd2[0].length;
        this.mT = arrd;
        this.mY = arrd2;
        if (n <= 2) return;
        double d2 = d = 0.0;
        n = 0;
        do {
            if (n >= arrd.length) {
                this.mTotalLength = 0.0;
                return;
            }
            double d3 = arrd2[n][0];
            double d4 = arrd2[n][0];
            if (n > 0) {
                Math.hypot(d3 - d, d4 - d2);
            }
            ++n;
            d = d3;
            d2 = d4;
        } while (true);
    }

    private double getLength2D(double d) {
        double d2;
        double d3;
        if (Double.isNaN(this.mTotalLength)) {
            return 0.0;
        }
        double[] arrd = this.mT;
        int n = arrd.length;
        if (d <= arrd[0]) {
            return 0.0;
        }
        int n2 = n - 1;
        if (d >= arrd[n2]) {
            return this.mTotalLength;
        }
        double d4 = d3 = (d2 = 0.0);
        n = 0;
        double d5 = d3;
        while (n < n2) {
            arrd = this.mY;
            void var14_11 = arrd[n][0];
            void var16_9 = arrd[n][1];
            d3 = d2;
            if (n > 0) {
                d3 = d2 + Math.hypot((double)(var14_11 - d5), (double)(var16_9 - d4));
            }
            if (d == (arrd = this.mT)[n]) {
                return d3;
            }
            int n3 = n + 1;
            if (d < arrd[n3]) {
                d2 = arrd[n3];
                d4 = arrd[n];
                double d6 = (d - arrd[n]) / (d2 - d4);
                arrd = this.mY;
                d4 = arrd[n][0];
                d5 = arrd[n3][0];
                void var21_13 = arrd[n][1];
                d2 = arrd[n3][1];
                d = 1.0 - d6;
                return d3 + Math.hypot((double)(var16_9 - (var21_13 * d + d2 * d6)), (double)(var14_11 - (d4 * d + d5 * d6)));
            }
            n = n3;
            d5 = var14_11;
            d4 = var16_9;
            d2 = d3;
        }
        return 0.0;
    }

    @Override
    public double getPos(double d, int n) {
        double[] arrd = this.mT;
        int n2 = arrd.length;
        int n3 = 0;
        if (d <= arrd[0]) {
            return this.mY[0][n];
        }
        int n4 = n2 - 1;
        if (d >= arrd[n4]) {
            return this.mY[n4][n];
        }
        while (n3 < n4) {
            arrd = this.mT;
            if (d == arrd[n3]) {
                return this.mY[n3][n];
            }
            n2 = n3 + 1;
            if (d < arrd[n2]) {
                double d2 = arrd[n2];
                double d3 = arrd[n3];
                d = (d - arrd[n3]) / (d2 - d3);
                arrd = this.mY;
                return (double)(arrd[n3][n] * (1.0 - d) + arrd[n2][n] * d);
            }
            n3 = n2;
        }
        return 0.0;
    }

    @Override
    public void getPos(double d, double[] arrd) {
        double[] arrd2 = this.mT;
        int n = arrd2.length;
        double[][] arrd3 = this.mY;
        int n2 = 0;
        int n3 = 0;
        int n4 = arrd3[0].length;
        if (d <= arrd2[0]) {
            n3 = 0;
            while (n3 < n4) {
                arrd[n3] = this.mY[0][n3];
                ++n3;
            }
            return;
        }
        int n5 = n - 1;
        if (d >= arrd2[n5]) {
            while (n3 < n4) {
                arrd[n3] = this.mY[n5][n3];
                ++n3;
            }
            return;
        }
        n3 = 0;
        while (n3 < n5) {
            int n6;
            if (d == this.mT[n3]) {
                for (n = 0; n < n4; ++n) {
                    arrd[n] = this.mY[n3][n];
                }
            }
            if (d < (arrd3 = this.mT)[n6 = n3 + 1]) {
                double[] arrd4 = arrd3[n6];
                double[] arrd5 = arrd3[n3];
                d = (d - arrd3[n3]) / (arrd4 - arrd5);
                n = n2;
                while (n < n4) {
                    arrd3 = this.mY;
                    arrd[n] = arrd3[n3][n] * (1.0 - d) + arrd3[n6][n] * d;
                    ++n;
                }
                return;
            }
            n3 = n6;
        }
    }

    @Override
    public void getPos(double d, float[] arrf) {
        double[] arrd = this.mT;
        int n = arrd.length;
        double[][] arrd2 = this.mY;
        int n2 = 0;
        int n3 = 0;
        int n4 = arrd2[0].length;
        if (d <= arrd[0]) {
            n3 = 0;
            while (n3 < n4) {
                arrf[n3] = (float)this.mY[0][n3];
                ++n3;
            }
            return;
        }
        int n5 = n - 1;
        if (d >= arrd[n5]) {
            while (n3 < n4) {
                arrf[n3] = (float)this.mY[n5][n3];
                ++n3;
            }
            return;
        }
        n3 = 0;
        while (n3 < n5) {
            int n6;
            if (d == this.mT[n3]) {
                for (n = 0; n < n4; ++n) {
                    arrf[n] = (float)this.mY[n3][n];
                }
            }
            if (d < (arrd2 = this.mT)[n6 = n3 + 1]) {
                double[] arrd3 = arrd2[n6];
                double[] arrd4 = arrd2[n3];
                d = (d - arrd2[n3]) / (arrd3 - arrd4);
                n = n2;
                while (n < n4) {
                    arrd2 = this.mY;
                    arrf[n] = (float)(arrd2[n3][n] * (1.0 - d) + arrd2[n6][n] * d);
                    ++n;
                }
                return;
            }
            n3 = n6;
        }
    }

    @Override
    public double getSlope(double d, int n) {
        int n2;
        double d2;
        double[] arrd = this.mT;
        int n3 = arrd.length;
        int n4 = 0;
        if (d < arrd[0]) {
            d2 = arrd[0];
            n2 = n4;
        } else {
            int n5 = n3 - 1;
            n2 = n4;
            d2 = d;
            if (d >= arrd[n5]) {
                d2 = arrd[n5];
                n2 = n4;
            }
        }
        while (n2 < n3 - 1) {
            arrd = this.mT;
            n4 = n2 + 1;
            if (d2 <= arrd[n4]) {
                d2 = arrd[n4];
                d = arrd[n2];
                double d3 = arrd[n2];
                arrd = this.mY;
                d3 = arrd[n2][n];
                return (double)((arrd[n4][n] - d3) / (d2 - d));
            }
            n2 = n4;
        }
        return 0.0;
    }

    @Override
    public void getSlope(double d, double[] arrd) {
        double d2;
        int n;
        double[] arrd2 = this.mT;
        int n2 = arrd2.length;
        double[][] arrd3 = this.mY;
        int n3 = 0;
        int n4 = arrd3[0].length;
        if (d <= arrd2[0]) {
            d2 = arrd2[0];
        } else {
            n = n2 - 1;
            d2 = d;
            if (d >= arrd2[n]) {
                d2 = arrd2[n];
            }
        }
        n = 0;
        while (n < n2 - 1) {
            arrd2 = this.mT;
            int n5 = n + 1;
            if (d2 <= arrd2[n5]) {
                d = arrd2[n5];
                d2 = arrd2[n];
                double d3 = arrd2[n];
                while (n3 < n4) {
                    arrd2 = this.mY;
                    d3 = arrd2[n][n3];
                    arrd[n3] = (arrd2[n5][n3] - d3) / (d - d2);
                    ++n3;
                }
                break block0;
                return;
            }
            n = n5;
        }
    }

    @Override
    public double[] getTimePoints() {
        return this.mT;
    }
}

