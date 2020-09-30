/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.material.math;

public final class MathUtils {
    public static final float DEFAULT_EPSILON = 1.0E-4f;

    private MathUtils() {
    }

    public static float dist(float f, float f2, float f3, float f4) {
        return (float)Math.hypot(f3 - f, f4 - f2);
    }

    public static float distanceToFurthestCorner(float f, float f2, float f3, float f4, float f5, float f6) {
        return MathUtils.max(MathUtils.dist(f, f2, f3, f4), MathUtils.dist(f, f2, f5, f4), MathUtils.dist(f, f2, f5, f6), MathUtils.dist(f, f2, f3, f6));
    }

    public static float floorMod(float f, int n) {
        int n2;
        float f2 = n;
        int n3 = n2 = (int)(f / f2);
        if (!(Math.signum(f) * f2 < 0.0f)) return f - (float)(n3 * n);
        n3 = n2;
        if ((float)(n2 * n) == f) return f - (float)(n3 * n);
        n3 = n2 - 1;
        return f - (float)(n3 * n);
    }

    public static int floorMod(int n, int n2) {
        int n3;
        int n4 = n3 = n / n2;
        if ((n ^ n2) >= 0) return n - n4 * n2;
        n4 = n3;
        if (n3 * n2 == n) return n - n4 * n2;
        n4 = n3 - 1;
        return n - n4 * n2;
    }

    public static boolean geq(float f, float f2, float f3) {
        if (!(f + f3 >= f2)) return false;
        return true;
    }

    public static float lerp(float f, float f2, float f3) {
        return (1.0f - f3) * f + f3 * f2;
    }

    private static float max(float f, float f2, float f3, float f4) {
        if (f > f2 && f > f3 && f > f4) {
            return f;
        }
        if (f2 > f3 && f2 > f4) {
            return f2;
        }
        if (!(f3 > f4)) return f4;
        return f3;
    }
}

