/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.common.detector;

public final class MathUtils {
    private MathUtils() {
    }

    public static float distance(float f, float f2, float f3, float f4) {
        return (float)Math.sqrt((f -= f3) * f + (f2 -= f4) * f2);
    }

    public static float distance(int n, int n2, int n3, int n4) {
        return (float)Math.sqrt((n -= n3) * n + (n2 -= n4) * n2);
    }

    public static int round(float f) {
        float f2;
        if (f < 0.0f) {
            f2 = -0.5f;
            return (int)(f + f2);
        }
        f2 = 0.5f;
        return (int)(f + f2);
    }
}

