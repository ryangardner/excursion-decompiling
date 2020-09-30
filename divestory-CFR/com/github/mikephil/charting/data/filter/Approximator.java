/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.github.mikephil.charting.data.filter;

import java.util.Arrays;

public class Approximator {
    float[] concat(float[] ... arrf) {
        int n;
        int n2 = arrf.length;
        int n3 = 0;
        for (n = 0; n < n2; n3 += arrf[n].length, ++n) {
        }
        float[] arrf2 = new float[n3];
        int n4 = arrf.length;
        n = 0;
        n3 = 0;
        while (n < n4) {
            float[] arrf3 = arrf[n];
            int n5 = arrf3.length;
            for (n2 = 0; n2 < n5; ++n3, ++n2) {
                arrf2[n3] = arrf3[n2];
            }
            ++n;
        }
        return arrf2;
    }

    public float[] reduceWithDouglasPeucker(float[] arrf, float f) {
        float[] arrf2 = new Line(arrf[0], arrf[1], arrf[arrf.length - 2], arrf[arrf.length - 1]);
        float f2 = 0.0f;
        int n = 2;
        int n2 = 0;
        do {
            if (n >= arrf.length - 2) {
                if (!(f2 > f)) return arrf2.getPoints();
                arrf2 = this.reduceWithDouglasPeucker(Arrays.copyOfRange(arrf, 0, n2 + 2), f);
                arrf = this.reduceWithDouglasPeucker(Arrays.copyOfRange(arrf, n2, arrf.length), f);
                return this.concat(arrf2, Arrays.copyOfRange(arrf, 2, arrf.length));
            }
            float f3 = arrf2.distance(arrf[n], arrf[n + 1]);
            float f4 = f2;
            if (f3 > f2) {
                n2 = n;
                f4 = f3;
            }
            n += 2;
            f2 = f4;
        } while (true);
    }

    private class Line {
        private float dx;
        private float dy;
        private float exsy;
        private float length;
        private float[] points;
        private float sxey;

        public Line(float f, float f2, float f3, float f4) {
            float f5;
            float f6;
            this.dx = f5 = f - f3;
            this.dy = f6 = f2 - f4;
            this.sxey = f * f4;
            this.exsy = f3 * f2;
            this.length = (float)Math.sqrt(f5 * f5 + f6 * f6);
            this.points = new float[]{f, f2, f3, f4};
        }

        public float distance(float f, float f2) {
            return Math.abs(this.dy * f - this.dx * f2 + this.sxey - this.exsy) / this.length;
        }

        public float[] getPoints() {
            return this.points;
        }
    }

}

