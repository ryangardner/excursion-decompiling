/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.motion.utils;

import androidx.constraintlayout.motion.utils.ArcCurveFit;
import androidx.constraintlayout.motion.utils.LinearCurveFit;
import androidx.constraintlayout.motion.utils.MonotonicCurveFit;

public abstract class CurveFit {
    public static final int CONSTANT = 2;
    public static final int LINEAR = 1;
    public static final int SPLINE = 0;

    public static CurveFit get(int n, double[] arrd, double[][] arrd2) {
        if (arrd.length == 1) {
            n = 2;
        }
        if (n == 0) return new MonotonicCurveFit(arrd, arrd2);
        if (n == 2) return new Constant(arrd[0], arrd2[0]);
        return new LinearCurveFit(arrd, arrd2);
    }

    public static CurveFit getArc(int[] arrn, double[] arrd, double[][] arrd2) {
        return new ArcCurveFit(arrn, arrd, arrd2);
    }

    public abstract double getPos(double var1, int var3);

    public abstract void getPos(double var1, double[] var3);

    public abstract void getPos(double var1, float[] var3);

    public abstract double getSlope(double var1, int var3);

    public abstract void getSlope(double var1, double[] var3);

    public abstract double[] getTimePoints();

    static class Constant
    extends CurveFit {
        double mTime;
        double[] mValue;

        Constant(double d, double[] arrd) {
            this.mTime = d;
            this.mValue = arrd;
        }

        @Override
        public double getPos(double d, int n) {
            return this.mValue[n];
        }

        @Override
        public void getPos(double d, double[] arrd) {
            double[] arrd2 = this.mValue;
            System.arraycopy(arrd2, 0, arrd, 0, arrd2.length);
        }

        @Override
        public void getPos(double d, float[] arrf) {
            double[] arrd;
            int n = 0;
            while (n < (arrd = this.mValue).length) {
                arrf[n] = (float)arrd[n];
                ++n;
            }
        }

        @Override
        public double getSlope(double d, int n) {
            return 0.0;
        }

        @Override
        public void getSlope(double d, double[] arrd) {
            int n = 0;
            while (n < this.mValue.length) {
                arrd[n] = 0.0;
                ++n;
            }
        }

        @Override
        public double[] getTimePoints() {
            return new double[]{this.mTime};
        }
    }

}

