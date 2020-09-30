/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Color
 *  android.graphics.ColorSpace
 *  android.graphics.ColorSpace$Model
 */
package androidx.core.graphics;

import android.graphics.Color;
import android.graphics.ColorSpace;
import java.util.Objects;

public final class ColorUtils {
    private static final int MIN_ALPHA_SEARCH_MAX_ITERATIONS = 10;
    private static final int MIN_ALPHA_SEARCH_PRECISION = 1;
    private static final ThreadLocal<double[]> TEMP_ARRAY = new ThreadLocal();
    private static final double XYZ_EPSILON = 0.008856;
    private static final double XYZ_KAPPA = 903.3;
    private static final double XYZ_WHITE_REFERENCE_X = 95.047;
    private static final double XYZ_WHITE_REFERENCE_Y = 100.0;
    private static final double XYZ_WHITE_REFERENCE_Z = 108.883;

    private ColorUtils() {
    }

    public static int HSLToColor(float[] arrf) {
        int n;
        int n2;
        int n3;
        float f = arrf[0];
        float f2 = arrf[1];
        float f3 = arrf[2];
        f2 = (1.0f - Math.abs(f3 * 2.0f - 1.0f)) * f2;
        f3 -= 0.5f * f2;
        float f4 = (1.0f - Math.abs(f / 60.0f % 2.0f - 1.0f)) * f2;
        switch ((int)f / 60) {
            default: {
                n3 = 0;
                n2 = 0;
                n = 0;
                return Color.rgb((int)ColorUtils.constrain(n2, 0, 255), (int)ColorUtils.constrain(n, 0, 255), (int)ColorUtils.constrain(n3, 0, 255));
            }
            case 5: 
            case 6: {
                n2 = Math.round((f2 + f3) * 255.0f);
                n = Math.round(f3 * 255.0f);
                n3 = Math.round((f4 + f3) * 255.0f);
                return Color.rgb((int)ColorUtils.constrain(n2, 0, 255), (int)ColorUtils.constrain(n, 0, 255), (int)ColorUtils.constrain(n3, 0, 255));
            }
            case 4: {
                n2 = Math.round((f4 + f3) * 255.0f);
                n = Math.round(f3 * 255.0f);
                n3 = Math.round((f2 + f3) * 255.0f);
                return Color.rgb((int)ColorUtils.constrain(n2, 0, 255), (int)ColorUtils.constrain(n, 0, 255), (int)ColorUtils.constrain(n3, 0, 255));
            }
            case 3: {
                n2 = Math.round(f3 * 255.0f);
                n = Math.round((f4 + f3) * 255.0f);
                n3 = Math.round((f2 + f3) * 255.0f);
                return Color.rgb((int)ColorUtils.constrain(n2, 0, 255), (int)ColorUtils.constrain(n, 0, 255), (int)ColorUtils.constrain(n3, 0, 255));
            }
            case 2: {
                n2 = Math.round(f3 * 255.0f);
                n = Math.round((f2 + f3) * 255.0f);
                n3 = Math.round((f4 + f3) * 255.0f);
                return Color.rgb((int)ColorUtils.constrain(n2, 0, 255), (int)ColorUtils.constrain(n, 0, 255), (int)ColorUtils.constrain(n3, 0, 255));
            }
            case 1: {
                n2 = Math.round((f4 + f3) * 255.0f);
                n = Math.round((f2 + f3) * 255.0f);
                n3 = Math.round(f3 * 255.0f);
                return Color.rgb((int)ColorUtils.constrain(n2, 0, 255), (int)ColorUtils.constrain(n, 0, 255), (int)ColorUtils.constrain(n3, 0, 255));
            }
            case 0: 
        }
        n2 = Math.round((f2 + f3) * 255.0f);
        n = Math.round((f4 + f3) * 255.0f);
        n3 = Math.round(f3 * 255.0f);
        return Color.rgb((int)ColorUtils.constrain(n2, 0, 255), (int)ColorUtils.constrain(n, 0, 255), (int)ColorUtils.constrain(n3, 0, 255));
    }

    public static int LABToColor(double d, double d2, double d3) {
        double[] arrd = ColorUtils.getTempDouble3Array();
        ColorUtils.LABToXYZ(d, d2, d3, arrd);
        return ColorUtils.XYZToColor(arrd[0], arrd[1], arrd[2]);
    }

    public static void LABToXYZ(double d, double d2, double d3, double[] arrd) {
        double d4 = (d + 16.0) / 116.0;
        double d5 = d2 / 500.0 + d4;
        double d6 = d4 - d3 / 200.0;
        d2 = Math.pow(d5, 3.0);
        if (!(d2 > 0.008856)) {
            d2 = (d5 * 116.0 - 16.0) / 903.3;
        }
        d = d > 7.9996247999999985 ? Math.pow(d4, 3.0) : (d /= 903.3);
        d3 = Math.pow(d6, 3.0);
        if (!(d3 > 0.008856)) {
            d3 = (d6 * 116.0 - 16.0) / 903.3;
        }
        arrd[0] = d2 * 95.047;
        arrd[1] = d * 100.0;
        arrd[2] = d3 * 108.883;
    }

    public static void RGBToHSL(int n, int n2, int n3, float[] arrf) {
        float f = (float)n / 255.0f;
        float f2 = (float)n2 / 255.0f;
        float f3 = (float)n3 / 255.0f;
        float f4 = Math.max(f, Math.max(f2, f3));
        float f5 = Math.min(f, Math.min(f2, f3));
        float f6 = f4 - f5;
        float f7 = (f4 + f5) / 2.0f;
        if (f4 == f5) {
            f6 = 0.0f;
            f5 = 0.0f;
        } else {
            f5 = f4 == f ? (f2 - f3) / f6 % 6.0f : (f4 == f2 ? (f3 - f) / f6 + 2.0f : 4.0f + (f - f2) / f6);
            f = f6 / (1.0f - Math.abs(2.0f * f7 - 1.0f));
            f6 = f5;
            f5 = f;
        }
        f6 = f = f6 * 60.0f % 360.0f;
        if (f < 0.0f) {
            f6 = f + 360.0f;
        }
        arrf[0] = ColorUtils.constrain(f6, 0.0f, 360.0f);
        arrf[1] = ColorUtils.constrain(f5, 0.0f, 1.0f);
        arrf[2] = ColorUtils.constrain(f7, 0.0f, 1.0f);
    }

    public static void RGBToLAB(int n, int n2, int n3, double[] arrd) {
        ColorUtils.RGBToXYZ(n, n2, n3, arrd);
        ColorUtils.XYZToLAB(arrd[0], arrd[1], arrd[2], arrd);
    }

    public static void RGBToXYZ(int n, int n2, int n3, double[] arrd) {
        if (arrd.length != 3) throw new IllegalArgumentException("outXyz must have a length of 3.");
        double d = (double)n / 255.0;
        d = d < 0.04045 ? (d /= 12.92) : Math.pow((d + 0.055) / 1.055, 2.4);
        double d2 = (double)n2 / 255.0;
        d2 = d2 < 0.04045 ? (d2 /= 12.92) : Math.pow((d2 + 0.055) / 1.055, 2.4);
        double d3 = (double)n3 / 255.0;
        d3 = d3 < 0.04045 ? (d3 /= 12.92) : Math.pow((d3 + 0.055) / 1.055, 2.4);
        arrd[0] = (0.4124 * d + 0.3576 * d2 + 0.1805 * d3) * 100.0;
        arrd[1] = (0.2126 * d + 0.7152 * d2 + 0.0722 * d3) * 100.0;
        arrd[2] = (d * 0.0193 + d2 * 0.1192 + d3 * 0.9505) * 100.0;
    }

    public static int XYZToColor(double d, double d2, double d3) {
        double d4 = (3.2406 * d + -1.5372 * d2 + -0.4986 * d3) / 100.0;
        double d5 = (-0.9689 * d + 1.8758 * d2 + 0.0415 * d3) / 100.0;
        d3 = (0.0557 * d + -0.204 * d2 + 1.057 * d3) / 100.0;
        d = d4 > 0.0031308 ? Math.pow(d4, 0.4166666666666667) * 1.055 - 0.055 : d4 * 12.92;
        d2 = d5 > 0.0031308 ? Math.pow(d5, 0.4166666666666667) * 1.055 - 0.055 : d5 * 12.92;
        if (d3 > 0.0031308) {
            d3 = Math.pow(d3, 0.4166666666666667) * 1.055 - 0.055;
            return Color.rgb((int)ColorUtils.constrain((int)Math.round(d * 255.0), 0, 255), (int)ColorUtils.constrain((int)Math.round(d2 * 255.0), 0, 255), (int)ColorUtils.constrain((int)Math.round(d3 * 255.0), 0, 255));
        }
        d3 *= 12.92;
        return Color.rgb((int)ColorUtils.constrain((int)Math.round(d * 255.0), 0, 255), (int)ColorUtils.constrain((int)Math.round(d2 * 255.0), 0, 255), (int)ColorUtils.constrain((int)Math.round(d3 * 255.0), 0, 255));
    }

    public static void XYZToLAB(double d, double d2, double d3, double[] arrd) {
        if (arrd.length != 3) throw new IllegalArgumentException("outLab must have a length of 3.");
        d = ColorUtils.pivotXyzComponent(d / 95.047);
        d2 = ColorUtils.pivotXyzComponent(d2 / 100.0);
        d3 = ColorUtils.pivotXyzComponent(d3 / 108.883);
        arrd[0] = Math.max(0.0, 116.0 * d2 - 16.0);
        arrd[1] = (d - d2) * 500.0;
        arrd[2] = (d2 - d3) * 200.0;
    }

    public static int blendARGB(int n, int n2, float f) {
        float f2 = 1.0f - f;
        float f3 = Color.alpha((int)n);
        float f4 = Color.alpha((int)n2);
        float f5 = Color.red((int)n);
        float f6 = Color.red((int)n2);
        float f7 = Color.green((int)n);
        float f8 = Color.green((int)n2);
        float f9 = Color.blue((int)n);
        float f10 = Color.blue((int)n2);
        return Color.argb((int)((int)(f3 * f2 + f4 * f)), (int)((int)(f5 * f2 + f6 * f)), (int)((int)(f7 * f2 + f8 * f)), (int)((int)(f9 * f2 + f10 * f)));
    }

    public static void blendHSL(float[] arrf, float[] arrf2, float f, float[] arrf3) {
        if (arrf3.length != 3) throw new IllegalArgumentException("result must have a length of 3.");
        float f2 = 1.0f - f;
        arrf3[0] = ColorUtils.circularInterpolate(arrf[0], arrf2[0], f);
        arrf3[1] = arrf[1] * f2 + arrf2[1] * f;
        arrf3[2] = arrf[2] * f2 + arrf2[2] * f;
    }

    public static void blendLAB(double[] arrd, double[] arrd2, double d, double[] arrd3) {
        if (arrd3.length != 3) throw new IllegalArgumentException("outResult must have a length of 3.");
        double d2 = 1.0 - d;
        arrd3[0] = arrd[0] * d2 + arrd2[0] * d;
        arrd3[1] = arrd[1] * d2 + arrd2[1] * d;
        arrd3[2] = arrd[2] * d2 + arrd2[2] * d;
    }

    public static double calculateContrast(int n, int n2) {
        if (Color.alpha((int)n2) != 255) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("background can not be translucent: #");
            stringBuilder.append(Integer.toHexString(n2));
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        int n3 = n;
        if (Color.alpha((int)n) < 255) {
            n3 = ColorUtils.compositeColors(n, n2);
        }
        double d = ColorUtils.calculateLuminance(n3) + 0.05;
        double d2 = ColorUtils.calculateLuminance(n2) + 0.05;
        return Math.max(d, d2) / Math.min(d, d2);
    }

    public static double calculateLuminance(int n) {
        double[] arrd = ColorUtils.getTempDouble3Array();
        ColorUtils.colorToXYZ(n, arrd);
        return arrd[1] / 100.0;
    }

    public static int calculateMinimumAlpha(int n, int n2, float f) {
        double d;
        int n3 = Color.alpha((int)n2);
        int n4 = 255;
        if (n3 != 255) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("background can not be translucent: #");
            stringBuilder.append(Integer.toHexString(n2));
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        double d2 = ColorUtils.calculateContrast(ColorUtils.setAlphaComponent(n, 255), n2);
        if (d2 < (d = (double)f)) {
            return -1;
        }
        n3 = 0;
        int n5 = 0;
        while (n3 <= 10) {
            if (n4 - n5 <= 1) return n4;
            int n6 = (n5 + n4) / 2;
            if (ColorUtils.calculateContrast(ColorUtils.setAlphaComponent(n, n6), n2) < d) {
                n5 = n6;
            } else {
                n4 = n6;
            }
            ++n3;
        }
        return n4;
    }

    static float circularInterpolate(float f, float f2, float f3) {
        float f4 = f;
        float f5 = f2;
        if (!(Math.abs(f2 - f) > 180.0f)) return (f4 + (f5 - f4) * f3) % 360.0f;
        if (f2 > f) {
            f4 = f + 360.0f;
            f5 = f2;
            return (f4 + (f5 - f4) * f3) % 360.0f;
        }
        f5 = f2 + 360.0f;
        f4 = f;
        return (f4 + (f5 - f4) * f3) % 360.0f;
    }

    public static void colorToHSL(int n, float[] arrf) {
        ColorUtils.RGBToHSL(Color.red((int)n), Color.green((int)n), Color.blue((int)n), arrf);
    }

    public static void colorToLAB(int n, double[] arrd) {
        ColorUtils.RGBToLAB(Color.red((int)n), Color.green((int)n), Color.blue((int)n), arrd);
    }

    public static void colorToXYZ(int n, double[] arrd) {
        ColorUtils.RGBToXYZ(Color.red((int)n), Color.green((int)n), Color.blue((int)n), arrd);
    }

    private static int compositeAlpha(int n, int n2) {
        return 255 - (255 - n2) * (255 - n) / 255;
    }

    public static int compositeColors(int n, int n2) {
        int n3 = Color.alpha((int)n2);
        int n4 = Color.alpha((int)n);
        int n5 = ColorUtils.compositeAlpha(n4, n3);
        return Color.argb((int)n5, (int)ColorUtils.compositeComponent(Color.red((int)n), n4, Color.red((int)n2), n3, n5), (int)ColorUtils.compositeComponent(Color.green((int)n), n4, Color.green((int)n2), n3, n5), (int)ColorUtils.compositeComponent(Color.blue((int)n), n4, Color.blue((int)n2), n3, n5));
    }

    public static Color compositeColors(Color color2, Color color3) {
        if (!Objects.equals((Object)color2.getModel(), (Object)color3.getModel())) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Color models must match (");
            stringBuilder.append((Object)color2.getModel());
            stringBuilder.append(" vs. ");
            stringBuilder.append((Object)color3.getModel());
            stringBuilder.append(")");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (!Objects.equals((Object)color3.getColorSpace(), (Object)color2.getColorSpace())) {
            color2 = color2.convert(color3.getColorSpace());
        }
        float[] arrf = color2.getComponents();
        float[] arrf2 = color3.getComponents();
        float f = color2.alpha();
        float f2 = color3.alpha() * (1.0f - f);
        int n = color3.getComponentCount() - 1;
        arrf2[n] = f + f2;
        float f3 = f2;
        float f4 = f;
        if (arrf2[n] > 0.0f) {
            f4 = f / arrf2[n];
            f3 = f2 / arrf2[n];
        }
        int n2 = 0;
        while (n2 < n) {
            arrf2[n2] = arrf[n2] * f4 + arrf2[n2] * f3;
            ++n2;
        }
        return Color.valueOf((float[])arrf2, (ColorSpace)color3.getColorSpace());
    }

    private static int compositeComponent(int n, int n2, int n3, int n4, int n5) {
        if (n5 != 0) return (n * 255 * n2 + n3 * n4 * (255 - n2)) / (n5 * 255);
        return 0;
    }

    private static float constrain(float f, float f2, float f3) {
        if (f < f2) {
            return f2;
        }
        f2 = f;
        if (!(f > f3)) return f2;
        return f3;
    }

    private static int constrain(int n, int n2, int n3) {
        if (n < n2) {
            return n2;
        }
        n2 = n;
        if (n <= n3) return n2;
        return n3;
    }

    public static double distanceEuclidean(double[] arrd, double[] arrd2) {
        return Math.sqrt(Math.pow(arrd[0] - arrd2[0], 2.0) + Math.pow(arrd[1] - arrd2[1], 2.0) + Math.pow(arrd[2] - arrd2[2], 2.0));
    }

    private static double[] getTempDouble3Array() {
        double[] arrd;
        double[] arrd2 = arrd = TEMP_ARRAY.get();
        if (arrd != null) return arrd2;
        arrd2 = new double[3];
        TEMP_ARRAY.set(arrd2);
        return arrd2;
    }

    private static double pivotXyzComponent(double d) {
        if (!(d > 0.008856)) return (d * 903.3 + 16.0) / 116.0;
        return Math.pow(d, 0.3333333333333333);
    }

    public static int setAlphaComponent(int n, int n2) {
        if (n2 < 0) throw new IllegalArgumentException("alpha must be between 0 and 255.");
        if (n2 > 255) throw new IllegalArgumentException("alpha must be between 0 and 255.");
        return n & 16777215 | n2 << 24;
    }
}

