/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package androidx.constraintlayout.motion.utils;

import android.util.Log;
import java.util.Arrays;

public class Easing {
    private static final String ACCELERATE = "cubic(0.4, 0.05, 0.8, 0.7)";
    private static final String ACCELERATE_NAME = "accelerate";
    private static final String DECELERATE = "cubic(0.0, 0.0, 0.2, 0.95)";
    private static final String DECELERATE_NAME = "decelerate";
    private static final String LINEAR = "cubic(1, 1, 0, 0)";
    private static final String LINEAR_NAME = "linear";
    public static String[] NAMED_EASING;
    private static final String STANDARD = "cubic(0.4, 0.0, 0.2, 1)";
    private static final String STANDARD_NAME = "standard";
    static Easing sDefault;
    String str = "identity";

    static {
        sDefault = new Easing();
        NAMED_EASING = new String[]{STANDARD_NAME, ACCELERATE_NAME, DECELERATE_NAME, LINEAR_NAME};
    }

    public static Easing getInterpolator(String charSequence) {
        if (charSequence == null) {
            return null;
        }
        if (((String)charSequence).startsWith("cubic")) {
            return new CubicEasing((String)charSequence);
        }
        int n = -1;
        switch (((String)charSequence).hashCode()) {
            default: {
                break;
            }
            case 1312628413: {
                if (!((String)charSequence).equals(STANDARD_NAME)) break;
                n = 0;
                break;
            }
            case -1102672091: {
                if (!((String)charSequence).equals(LINEAR_NAME)) break;
                n = 3;
                break;
            }
            case -1263948740: {
                if (!((String)charSequence).equals(DECELERATE_NAME)) break;
                n = 2;
                break;
            }
            case -1354466595: {
                if (!((String)charSequence).equals(ACCELERATE_NAME)) break;
                n = 1;
            }
        }
        if (n == 0) return new CubicEasing(STANDARD);
        if (n == 1) return new CubicEasing(ACCELERATE);
        if (n == 2) return new CubicEasing(DECELERATE);
        if (n == 3) return new CubicEasing(LINEAR);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("transitionEasing syntax error syntax:transitionEasing=\"cubic(1.0,0.5,0.0,0.6)\" or ");
        ((StringBuilder)charSequence).append(Arrays.toString(NAMED_EASING));
        Log.e((String)"ConstraintSet", (String)((StringBuilder)charSequence).toString());
        return sDefault;
    }

    public double get(double d) {
        return d;
    }

    public double getDiff(double d) {
        return 1.0;
    }

    public String toString() {
        return this.str;
    }

    static class CubicEasing
    extends Easing {
        private static double d_error = 1.0E-4;
        private static double error = 0.01;
        double x1;
        double x2;
        double y1;
        double y2;

        public CubicEasing(double d, double d2, double d3, double d4) {
            this.setup(d, d2, d3, d4);
        }

        CubicEasing(String string2) {
            this.str = string2;
            int n = string2.indexOf(40);
            int n2 = string2.indexOf(44, n);
            this.x1 = Double.parseDouble(string2.substring(n + 1, n2).trim());
            n = string2.indexOf(44, ++n2);
            this.y1 = Double.parseDouble(string2.substring(n2, n).trim());
            n2 = string2.indexOf(44, ++n);
            this.x2 = Double.parseDouble(string2.substring(n, n2).trim());
            n = n2 + 1;
            this.y2 = Double.parseDouble(string2.substring(n, string2.indexOf(41, n)).trim());
        }

        private double getDiffX(double d) {
            double d2 = 1.0 - d;
            double d3 = this.x1;
            double d4 = this.x2;
            return d2 * 3.0 * d2 * d3 + d2 * 6.0 * d * (d4 - d3) + 3.0 * d * d * (1.0 - d4);
        }

        private double getDiffY(double d) {
            double d2 = 1.0 - d;
            double d3 = this.y1;
            double d4 = this.y2;
            return d2 * 3.0 * d2 * d3 + d2 * 6.0 * d * (d4 - d3) + 3.0 * d * d * (1.0 - d4);
        }

        private double getX(double d) {
            double d2 = 1.0 - d;
            double d3 = 3.0 * d2;
            return this.x1 * (d2 * d3 * d) + this.x2 * (d3 * d * d) + d * d * d;
        }

        private double getY(double d) {
            double d2 = 1.0 - d;
            double d3 = 3.0 * d2;
            return this.y1 * (d2 * d3 * d) + this.y2 * (d3 * d * d) + d * d * d;
        }

        @Override
        public double get(double d) {
            double d2;
            if (d <= 0.0) {
                return 0.0;
            }
            if (d >= 1.0) {
                return 1.0;
            }
            double d3 = d2 = 0.5;
            do {
                double d4;
                if (!(d2 > error)) {
                    double d5 = d3 - d2;
                    d4 = this.getX(d5);
                    d3 += d2;
                    d2 = this.getX(d3);
                    d5 = this.getY(d5);
                    return (this.getY(d3) - d5) * (d - d4) / (d2 - d4) + d5;
                }
                d4 = this.getX(d3);
                d2 *= 0.5;
                if (d4 < d) {
                    d3 += d2;
                    continue;
                }
                d3 -= d2;
            } while (true);
        }

        @Override
        public double getDiff(double d) {
            double d2;
            double d3 = d2 = 0.5;
            do {
                double d4;
                if (!(d2 > d_error)) {
                    d4 = d3 - d2;
                    d = this.getX(d4);
                    d3 += d2;
                    d2 = this.getX(d3);
                    d4 = this.getY(d4);
                    return (this.getY(d3) - d4) / (d2 - d);
                }
                d4 = this.getX(d3);
                d2 *= 0.5;
                if (d4 < d) {
                    d3 += d2;
                    continue;
                }
                d3 -= d2;
            } while (true);
        }

        void setup(double d, double d2, double d3, double d4) {
            this.x1 = d;
            this.y1 = d2;
            this.x2 = d3;
            this.y2 = d4;
        }
    }

}

