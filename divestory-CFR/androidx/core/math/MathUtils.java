/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.core.math;

public class MathUtils {
    private MathUtils() {
    }

    public static double clamp(double d, double d2, double d3) {
        if (d < d2) {
            return d2;
        }
        if (!(d > d3)) return d;
        return d3;
    }

    public static float clamp(float f, float f2, float f3) {
        if (f < f2) {
            return f2;
        }
        if (!(f > f3)) return f;
        return f3;
    }

    public static int clamp(int n, int n2, int n3) {
        if (n < n2) {
            return n2;
        }
        if (n <= n3) return n;
        return n3;
    }

    public static long clamp(long l, long l2, long l3) {
        if (l < l2) {
            return l2;
        }
        if (l <= l3) return l;
        return l3;
    }
}

