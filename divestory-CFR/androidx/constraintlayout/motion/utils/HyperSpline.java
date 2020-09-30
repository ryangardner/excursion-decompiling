/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.motion.utils;

public class HyperSpline {
    double[][] mCtl;
    Cubic[][] mCurve;
    double[] mCurveLength;
    int mDimensionality;
    int mPoints;
    double mTotalLength;

    public HyperSpline() {
    }

    public HyperSpline(double[][] arrd) {
        this.setup(arrd);
    }

    static Cubic[] calcNaturalCubic(int n, double[] arrd) {
        double d;
        double[] arrd2 = new double[n];
        Object[] arrobject = new double[n];
        double[] arrd3 = new double[n];
        int n2 = n - 1;
        int n3 = 0;
        arrd2[0] = 0.5;
        int n4 = 1;
        for (n = 1; n < n2; ++n) {
            arrd2[n] = 1.0 / (4.0 - arrd2[n - 1]);
        }
        int n5 = n2 - 1;
        arrd2[n2] = 1.0 / (2.0 - arrd2[n5]);
        arrobject[0] = (arrd[1] - arrd[0]) * 3.0 * arrd2[0];
        n = n4;
        while (n < n2) {
            n4 = n + 1;
            d = arrd[n4];
            int n6 = n - 1;
            arrobject[n] = ((d - arrd[n6]) * 3.0 - arrobject[n6]) * arrd2[n];
            n = n4;
        }
        arrobject[n2] = ((arrd[n2] - arrd[n5]) * 3.0 - arrobject[n5]) * arrd2[n2];
        arrd3[n2] = arrobject[n2];
        for (n = n5; n >= 0; --n) {
            arrd3[n] = arrobject[n] - arrd2[n] * arrd3[n + 1];
        }
        arrobject = new Cubic[n2];
        n = n3;
        while (n < n2) {
            d = (float)arrd[n];
            double d2 = arrd3[n];
            n3 = n + 1;
            arrobject[n] = (double)new Cubic(d, d2, (arrd[n3] - arrd[n]) * 3.0 - arrd3[n] * 2.0 - arrd3[n3], (arrd[n] - arrd[n3]) * 2.0 + arrd3[n] + arrd3[n3]);
            n = n3;
        }
        return arrobject;
    }

    public double approxLength(Cubic[] arrcubic) {
        double d;
        int n = arrcubic.length;
        double[] arrd = new double[arrcubic.length];
        double d2 = 0.0;
        double d3 = d = 0.0;
        do {
            double d4;
            n = 0;
            int n2 = 0;
            double d5 = d2;
            if (!(d < 1.0)) {
                while (n < arrcubic.length) {
                    d4 = arrd[n];
                    arrd[n] = d = arrcubic[n].eval(1.0);
                    d = d4 - d;
                    d5 += d * d;
                    ++n;
                }
                return d3 + Math.sqrt(d5);
            }
            d5 = 0.0;
            for (n = n2; n < arrcubic.length; d5 += d4 * d4, ++n) {
                double d6 = arrd[n];
                arrd[n] = d4 = arrcubic[n].eval(d);
                d4 = d6 - d4;
            }
            d4 = d3;
            if (d > 0.0) {
                d4 = d3 + Math.sqrt(d5);
            }
            d += 0.1;
            d3 = d4;
        } while (true);
    }

    public double getPos(double d, int n) {
        double[] arrd;
        d *= this.mTotalLength;
        int n2 = 0;
        while (n2 < (arrd = this.mCurveLength).length - 1) {
            if (!(arrd[n2] < d)) return this.mCurve[n][n2].eval(d / this.mCurveLength[n2]);
            d -= arrd[n2];
            ++n2;
        }
        return this.mCurve[n][n2].eval(d / this.mCurveLength[n2]);
    }

    public void getPos(double d, double[] arrd) {
        int n;
        d *= this.mTotalLength;
        int n2 = 0;
        int n3 = 0;
        do {
            double[] arrd2 = this.mCurveLength;
            n = n2;
            if (n3 >= arrd2.length - 1) break;
            n = n2;
            if (!(arrd2[n3] < d)) break;
            d -= arrd2[n3];
            ++n3;
        } while (true);
        while (n < arrd.length) {
            arrd[n] = this.mCurve[n][n3].eval(d / this.mCurveLength[n3]);
            ++n;
        }
    }

    public void getPos(double d, float[] arrf) {
        int n;
        d *= this.mTotalLength;
        int n2 = 0;
        int n3 = 0;
        do {
            double[] arrd = this.mCurveLength;
            n = n2;
            if (n3 >= arrd.length - 1) break;
            n = n2;
            if (!(arrd[n3] < d)) break;
            d -= arrd[n3];
            ++n3;
        } while (true);
        while (n < arrf.length) {
            arrf[n] = (float)this.mCurve[n][n3].eval(d / this.mCurveLength[n3]);
            ++n;
        }
    }

    public void getVelocity(double d, double[] arrd) {
        int n;
        d *= this.mTotalLength;
        int n2 = 0;
        int n3 = 0;
        do {
            double[] arrd2 = this.mCurveLength;
            n = n2;
            if (n3 >= arrd2.length - 1) break;
            n = n2;
            if (!(arrd2[n3] < d)) break;
            d -= arrd2[n3];
            ++n3;
        } while (true);
        while (n < arrd.length) {
            arrd[n] = this.mCurve[n][n3].vel(d / this.mCurveLength[n3]);
            ++n;
        }
    }

    public void setup(double[][] arrd) {
        int n;
        int n2;
        Cubic[][] arrcubic;
        this.mDimensionality = n = arrd[0].length;
        this.mPoints = n2 = arrd.length;
        this.mCtl = new double[n][n2];
        this.mCurve = new Cubic[this.mDimensionality][];
        for (n = 0; n < this.mDimensionality; ++n) {
            for (n2 = 0; n2 < this.mPoints; ++n2) {
                this.mCtl[n][n2] = arrd[n2][n];
            }
        }
        for (n = 0; n < (n2 = this.mDimensionality); ++n) {
            arrcubic = this.mCurve;
            arrd = this.mCtl;
            arrcubic[n] = HyperSpline.calcNaturalCubic(arrd[n].length, arrd[n]);
        }
        this.mCurveLength = new double[this.mPoints - 1];
        this.mTotalLength = 0.0;
        arrcubic = new Cubic[n2];
        n = 0;
        while (n < this.mCurveLength.length) {
            for (n2 = 0; n2 < this.mDimensionality; ++n2) {
                arrcubic[n2] = this.mCurve[n2][n];
            }
            double d = this.mTotalLength;
            arrd = this.mCurveLength;
            double d2 = this.approxLength((Cubic[])arrcubic);
            arrd[n] = (double[])d2;
            this.mTotalLength = d + d2;
            ++n;
        }
    }

    public static class Cubic {
        public static final double HALF = 0.5;
        public static final double THIRD = 0.3333333333333333;
        double mA;
        double mB;
        double mC;
        double mD;

        public Cubic(double d, double d2, double d3, double d4) {
            this.mA = d;
            this.mB = d2;
            this.mC = d3;
            this.mD = d4;
        }

        public double eval(double d) {
            return ((this.mD * d + this.mC) * d + this.mB) * d + this.mA;
        }

        public double vel(double d) {
            return (this.mD * 0.3333333333333333 * d + this.mC * 0.5) * d + this.mB;
        }
    }

}

