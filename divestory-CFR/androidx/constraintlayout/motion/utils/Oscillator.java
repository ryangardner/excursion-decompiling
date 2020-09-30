/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.motion.utils;

import java.util.Arrays;

public class Oscillator {
    public static final int BOUNCE = 6;
    public static final int COS_WAVE = 5;
    public static final int REVERSE_SAW_WAVE = 4;
    public static final int SAW_WAVE = 3;
    public static final int SIN_WAVE = 0;
    public static final int SQUARE_WAVE = 1;
    public static String TAG = "Oscillator";
    public static final int TRIANGLE_WAVE = 2;
    double PI2 = 6.283185307179586;
    double[] mArea;
    private boolean mNormalized = false;
    float[] mPeriod = new float[0];
    double[] mPosition = new double[0];
    int mType;

    public void addPoint(double d, float f) {
        int n;
        int n2 = this.mPeriod.length + 1;
        int n3 = n = Arrays.binarySearch(this.mPosition, d);
        if (n < 0) {
            n3 = -n - 1;
        }
        this.mPosition = Arrays.copyOf(this.mPosition, n2);
        this.mPeriod = Arrays.copyOf(this.mPeriod, n2);
        this.mArea = new double[n2];
        double[] arrd = this.mPosition;
        System.arraycopy(arrd, n3, arrd, n3 + 1, n2 - n3 - 1);
        this.mPosition[n3] = d;
        this.mPeriod[n3] = f;
        this.mNormalized = false;
    }

    double getDP(double d) {
        double d2;
        double d3 = 0.0;
        if (d <= 0.0) {
            d2 = 1.0E-5;
        } else {
            d2 = d;
            if (d >= 1.0) {
                d2 = 0.999999;
            }
        }
        int n = Arrays.binarySearch(this.mPosition, d2);
        if (n > 0) {
            return 0.0;
        }
        d = d3;
        if (n == 0) return d;
        n = -n - 1;
        float[] arrf = this.mPeriod;
        float f = arrf[n];
        int n2 = n - 1;
        d = f - arrf[n2];
        double[] arrd = this.mPosition;
        d /= arrd[n] - arrd[n2];
        return (double)arrf[n2] - d * arrd[n2] + d2 * d;
    }

    double getP(double d) {
        double d2;
        double d3 = 1.0;
        if (d < 0.0) {
            d2 = 0.0;
        } else {
            d2 = d;
            if (d > 1.0) {
                d2 = 1.0;
            }
        }
        int n = Arrays.binarySearch(this.mPosition, d2);
        if (n > 0) {
            return d3;
        }
        if (n == 0) return 0.0;
        int n2 = -n - 1;
        float[] arrf = this.mPeriod;
        float f = arrf[n2];
        n = n2 - 1;
        d = f - arrf[n];
        double[] arrd = this.mPosition;
        d /= arrd[n2] - arrd[n];
        return this.mArea[n] + ((double)arrf[n] - arrd[n] * d) * (d2 - arrd[n]) + d * (d2 * d2 - arrd[n] * arrd[n]) / 2.0;
    }

    public double getSlope(double d) {
        switch (this.mType) {
            default: {
                double d2 = this.PI2 * this.getDP(d);
                d = Math.cos(this.PI2 * this.getP(d));
                return d2 * d;
            }
            case 6: {
                double d3 = this.getDP(d) * 4.0;
                d = (this.getP(d) * 4.0 + 2.0) % 4.0 - 2.0;
                return d3 * d;
            }
            case 5: {
                double d2 = -this.PI2 * this.getDP(d);
                d = Math.sin(this.PI2 * this.getP(d));
                return d2 * d;
            }
            case 4: {
                d = -this.getDP(d);
                return d * 2.0;
            }
            case 3: {
                d = this.getDP(d);
                return d * 2.0;
            }
            case 2: {
                double d3 = this.getDP(d) * 4.0;
                d = Math.signum((this.getP(d) * 4.0 + 3.0) % 4.0 - 2.0);
                return d3 * d;
            }
            case 1: 
        }
        return 0.0;
    }

    public double getValue(double d) {
        switch (this.mType) {
            default: {
                return Math.sin(this.PI2 * this.getP(d));
            }
            case 6: {
                d = 1.0 - Math.abs(this.getP(d) * 4.0 % 4.0 - 2.0);
                d *= d;
                return 1.0 - d;
            }
            case 5: {
                return Math.cos(this.PI2 * this.getP(d));
            }
            case 4: {
                d = (this.getP(d) * 2.0 + 1.0) % 2.0;
                return 1.0 - d;
            }
            case 3: {
                return (this.getP(d) * 2.0 + 1.0) % 2.0 - 1.0;
            }
            case 2: {
                d = Math.abs((this.getP(d) * 4.0 + 1.0) % 4.0 - 2.0);
                return 1.0 - d;
            }
            case 1: 
        }
        return Math.signum(0.5 - this.getP(d) % 1.0);
    }

    public void normalize() {
        int n;
        float f;
        float[] arrf;
        int n2;
        double d = 0.0;
        for (n2 = 0; n2 < (arrf = this.mPeriod).length; d += (double)arrf[n2], ++n2) {
        }
        double d2 = 0.0;
        for (n2 = 1; n2 < (arrf = this.mPeriod).length; d2 += (arrf[n2] - arrf[n]) * (double)f, ++n2) {
            n = n2 - 1;
            f = (arrf[n] + arrf[n2]) / 2.0f;
            arrf = this.mPosition;
        }
        for (n2 = 0; n2 < (arrf = this.mPeriod).length; ++n2) {
            arrf[n2] = (float)((double)arrf[n2] * (d / d2));
        }
        this.mArea[0] = 0.0;
        n2 = 1;
        do {
            if (n2 >= (arrf = this.mPeriod).length) {
                this.mNormalized = true;
                return;
            }
            n = n2 - 1;
            f = (arrf[n] + arrf[n2]) / 2.0f;
            arrf = this.mPosition;
            d2 = arrf[n2];
            d = arrf[n];
            arrf = this.mArea;
            arrf[n2] = arrf[n] + (d2 - d) * (double)f;
            ++n2;
        } while (true);
    }

    public void setType(int n) {
        this.mType = n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("pos =");
        stringBuilder.append(Arrays.toString(this.mPosition));
        stringBuilder.append(" period=");
        stringBuilder.append(Arrays.toString(this.mPeriod));
        return stringBuilder.toString();
    }
}

