/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.motion.utils;

import androidx.constraintlayout.motion.utils.CurveFit;

public class MonotonicCurveFit
extends CurveFit {
    private static final String TAG = "MonotonicCurveFit";
    private double[] mT;
    private double[][] mTangent;
    private double[][] mY;

    public MonotonicCurveFit(double[] arrd, double[][] arrd2) {
        int n;
        double d;
        int n2;
        double d2;
        int n3 = arrd.length;
        int n4 = arrd2[0].length;
        int n5 = n3 - 1;
        double[][] arrd3 = new double[n5][n4];
        double[][] arrd4 = new double[n3][n4];
        int n6 = 0;
        do {
            if (n6 >= n4) break;
            n2 = 0;
            while (n2 < n5) {
                n = n2 + 1;
                d2 = arrd[n];
                d = arrd[n2];
                arrd3[n2][n6] = (arrd2[n][n6] - arrd2[n2][n6]) / (d2 - d);
                arrd4[n2][n6] = n2 == 0 ? arrd3[n2][n6] : (arrd3[n2 - 1][n6] + arrd3[n2][n6]) * 0.5;
                n2 = n;
            }
            arrd4[n5][n6] = arrd3[n3 - 2][n6];
            ++n6;
        } while (true);
        n6 = 0;
        do {
            if (n6 >= n5) {
                this.mT = arrd;
                this.mY = arrd2;
                this.mTangent = arrd4;
                return;
            }
            for (n2 = 0; n2 < n4; ++n2) {
                if (arrd3[n6][n2] == 0.0) {
                    arrd4[n6][n2] = 0.0;
                    arrd4[n6 + 1][n2] = 0.0;
                    continue;
                }
                d = arrd4[n6][n2] / arrd3[n6][n2];
                n = n6 + 1;
                d2 = arrd4[n][n2] / arrd3[n6][n2];
                double d3 = Math.hypot(d, d2);
                if (!(d3 > 9.0)) continue;
                d3 = 3.0 / d3;
                arrd4[n6][n2] = d * d3 * arrd3[n6][n2];
                arrd4[n][n2] = d3 * d2 * arrd3[n6][n2];
            }
            ++n6;
        } while (true);
    }

    private static double diff(double d, double d2, double d3, double d4, double d5, double d6) {
        double d7 = d2 * d2;
        double d8 = d2 * 6.0;
        double d9 = 3.0 * d;
        return -6.0 * d7 * d4 + d8 * d4 + 6.0 * d7 * d3 - d8 * d3 + d9 * d6 * d7 + d9 * d5 * d7 - 2.0 * d * d6 * d2 - 4.0 * d * d5 * d2 + d * d5;
    }

    private static double interpolate(double d, double d2, double d3, double d4, double d5, double d6) {
        double d7 = d2 * d2;
        double d8 = d7 * d2;
        double d9 = 3.0 * d7;
        double d10 = d * d6;
        d6 = d * d5;
        return -2.0 * d8 * d4 + d9 * d4 + d8 * 2.0 * d3 - d9 * d3 + d3 + d10 * d8 + d8 * d6 - d10 * d7 - d * 2.0 * d5 * d7 + d6 * d2;
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
                double d2 = arrd[n2] - arrd[n3];
                double d3 = (d - arrd[n3]) / d2;
                arrd = this.mY;
                void var12_9 = arrd[n3][n];
                d = arrd[n2][n];
                arrd = this.mTangent;
                return MonotonicCurveFit.interpolate(d2, d3, (double)var12_9, d, (double)arrd[n3][n], (double)arrd[n2][n]);
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
            if (d < (arrd2 = this.mT)[n6 = n3 + 1]) {
                double d2 = arrd2[n6] - arrd2[n3];
                double d3 = (d - arrd2[n3]) / d2;
                n = n2;
                while (n < n4) {
                    arrd2 = this.mY;
                    void var16_13 = arrd2[n3][n];
                    d = arrd2[n6][n];
                    arrd2 = this.mTangent;
                    arrd[n] = MonotonicCurveFit.interpolate(d2, d3, (double)var16_13, d, (double)arrd2[n3][n], (double)arrd2[n6][n]);
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
                reference var12_11 = arrd2[n6] - arrd2[n3];
                double d2 = (d - arrd2[n3]) / var12_11;
                n = n2;
                while (n < n4) {
                    arrd2 = this.mY;
                    double d3 = arrd2[n3][n];
                    d = arrd2[n6][n];
                    arrd2 = this.mTangent;
                    arrf[n] = (float)MonotonicCurveFit.interpolate((double)var12_11, d2, d3, d, arrd2[n3][n], arrd2[n6][n]);
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
        double[] arrd = this.mT;
        int n3 = arrd.length;
        int n4 = 0;
        if (d < arrd[0]) {
            d = arrd[0];
        } else {
            n2 = n3 - 1;
            if (d >= arrd[n2]) {
                d = arrd[n2];
            }
        }
        while (n4 < n3 - 1) {
            arrd = this.mT;
            n2 = n4 + 1;
            if (d <= arrd[n2]) {
                double d2 = arrd[n2] - arrd[n4];
                double d3 = (d - arrd[n4]) / d2;
                arrd = this.mY;
                d = arrd[n4][n];
                void var12_9 = arrd[n2][n];
                arrd = this.mTangent;
                return MonotonicCurveFit.diff(d2, d3, d, (double)var12_9, (double)arrd[n4][n], (double)arrd[n2][n]) / d2;
            }
            n4 = n2;
        }
        return 0.0;
    }

    @Override
    public void getSlope(double d, double[] arrd) {
        int n;
        double[] arrd2 = this.mT;
        int n2 = arrd2.length;
        double[][] arrd3 = this.mY;
        int n3 = 0;
        int n4 = arrd3[0].length;
        if (d <= arrd2[0]) {
            d = arrd2[0];
        } else {
            n = n2 - 1;
            if (d >= arrd2[n]) {
                d = arrd2[n];
            }
        }
        n = 0;
        while (n < n2 - 1) {
            arrd2 = this.mT;
            int n5 = n + 1;
            if (d <= arrd2[n5]) {
                double d2 = arrd2[n5] - arrd2[n];
                double d3 = (d - arrd2[n]) / d2;
                while (n3 < n4) {
                    arrd2 = this.mY;
                    void var15_12 = arrd2[n][n3];
                    d = arrd2[n5][n3];
                    arrd2 = this.mTangent;
                    arrd[n3] = MonotonicCurveFit.diff(d2, d3, (double)var15_12, d, (double)arrd2[n][n3], (double)arrd2[n5][n3]) / d2;
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

