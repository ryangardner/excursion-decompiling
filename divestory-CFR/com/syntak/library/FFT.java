/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.syntak.library;

public class FFT {
    double[] cos;
    int m;
    int n;
    double[] sin;

    public FFT(int n) {
        int n2;
        this.n = n;
        double d = n;
        this.m = n2 = (int)(Math.log(d) / Math.log(2.0));
        if (n != 1 << n2) throw new RuntimeException("FFT length must be power of 2");
        n2 = n / 2;
        this.cos = new double[n2];
        this.sin = new double[n2];
        n = 0;
        while (n < n2) {
            double[] arrd = this.cos;
            double d2 = (double)n * -6.283185307179586 / d;
            arrd[n] = Math.cos(d2);
            this.sin[n] = Math.sin(d2);
            ++n;
        }
    }

    public void fft(double[] arrd, double[] arrd2) {
        int n;
        int n2;
        double d;
        int n3 = this.n / 2;
        int n4 = 0;
        for (n = 1; n < this.n - 1; ++n) {
            for (n2 = n3; n4 >= n2; n4 -= n2, n2 /= 2) {
            }
            if (n >= (n4 += n2)) continue;
            d = arrd[n];
            arrd[n] = arrd[n4];
            arrd[n4] = d;
            d = arrd2[n];
            arrd2[n] = arrd2[n4];
            arrd2[n4] = d;
        }
        n2 = 0;
        n = 1;
        while (n2 < this.m) {
            int n5 = n + n;
            n3 = 0;
            for (n4 = 0; n4 < n; ++n4) {
                d = this.cos[n3];
                double d2 = this.sin[n3];
                int n6 = n3 + (1 << this.m - n2 - 1);
                for (n3 = n4; n3 < this.n; n3 += n5) {
                    int n7 = n3 + n;
                    double d3 = arrd[n7] * d - arrd2[n7] * d2;
                    double d4 = arrd[n7] * d2 + arrd2[n7] * d;
                    arrd[n7] = arrd[n3] - d3;
                    arrd2[n7] = arrd2[n3] - d4;
                    arrd[n3] = arrd[n3] + d3;
                    arrd2[n3] = arrd2[n3] + d4;
                }
                n3 = n6;
            }
            ++n2;
            n = n5;
        }
    }
}

