/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.TimeInterpolator
 */
package com.github.mikephil.charting.animation;

import android.animation.TimeInterpolator;

public class Easing {
    private static final float DOUBLE_PI = 6.2831855f;
    public static final EasingFunction EaseInBack;
    public static final EasingFunction EaseInBounce;
    public static final EasingFunction EaseInCirc;
    public static final EasingFunction EaseInCubic;
    public static final EasingFunction EaseInElastic;
    public static final EasingFunction EaseInExpo;
    public static final EasingFunction EaseInOutBack;
    public static final EasingFunction EaseInOutBounce;
    public static final EasingFunction EaseInOutCirc;
    public static final EasingFunction EaseInOutCubic;
    public static final EasingFunction EaseInOutElastic;
    public static final EasingFunction EaseInOutExpo;
    public static final EasingFunction EaseInOutQuad;
    public static final EasingFunction EaseInOutQuart;
    public static final EasingFunction EaseInOutSine;
    public static final EasingFunction EaseInQuad;
    public static final EasingFunction EaseInQuart;
    public static final EasingFunction EaseInSine;
    public static final EasingFunction EaseOutBack;
    public static final EasingFunction EaseOutBounce;
    public static final EasingFunction EaseOutCirc;
    public static final EasingFunction EaseOutCubic;
    public static final EasingFunction EaseOutElastic;
    public static final EasingFunction EaseOutExpo;
    public static final EasingFunction EaseOutQuad;
    public static final EasingFunction EaseOutQuart;
    public static final EasingFunction EaseOutSine;
    public static final EasingFunction Linear;

    static {
        Linear = new EasingFunction(){

            @Override
            public float getInterpolation(float f) {
                return f;
            }
        };
        EaseInQuad = new EasingFunction(){

            @Override
            public float getInterpolation(float f) {
                return f * f;
            }
        };
        EaseOutQuad = new EasingFunction(){

            @Override
            public float getInterpolation(float f) {
                return -f * (f - 2.0f);
            }
        };
        EaseInOutQuad = new EasingFunction(){

            @Override
            public float getInterpolation(float f) {
                if (!((f *= 2.0f) < 1.0f)) return ((f -= 1.0f) * (f - 2.0f) - 1.0f) * -0.5f;
                return 0.5f * f * f;
            }
        };
        EaseInCubic = new EasingFunction(){

            @Override
            public float getInterpolation(float f) {
                return (float)Math.pow(f, 3.0);
            }
        };
        EaseOutCubic = new EasingFunction(){

            @Override
            public float getInterpolation(float f) {
                return (float)Math.pow(f - 1.0f, 3.0) + 1.0f;
            }
        };
        EaseInOutCubic = new EasingFunction(){

            @Override
            public float getInterpolation(float f) {
                if ((f *= 2.0f) < 1.0f) {
                    f = (float)Math.pow(f, 3.0);
                    return f * 0.5f;
                }
                f = (float)Math.pow(f - 2.0f, 3.0) + 2.0f;
                return f * 0.5f;
            }
        };
        EaseInQuart = new EasingFunction(){

            @Override
            public float getInterpolation(float f) {
                return (float)Math.pow(f, 4.0);
            }
        };
        EaseOutQuart = new EasingFunction(){

            @Override
            public float getInterpolation(float f) {
                return -((float)Math.pow(f - 1.0f, 4.0) - 1.0f);
            }
        };
        EaseInOutQuart = new EasingFunction(){

            @Override
            public float getInterpolation(float f) {
                if (!((f *= 2.0f) < 1.0f)) return ((float)Math.pow(f - 2.0f, 4.0) - 2.0f) * -0.5f;
                return (float)Math.pow(f, 4.0) * 0.5f;
            }
        };
        EaseInSine = new EasingFunction(){

            @Override
            public float getInterpolation(float f) {
                return -((float)Math.cos((double)f * 1.5707963267948966)) + 1.0f;
            }
        };
        EaseOutSine = new EasingFunction(){

            @Override
            public float getInterpolation(float f) {
                return (float)Math.sin((double)f * 1.5707963267948966);
            }
        };
        EaseInOutSine = new EasingFunction(){

            @Override
            public float getInterpolation(float f) {
                return ((float)Math.cos((double)f * 3.141592653589793) - 1.0f) * -0.5f;
            }
        };
        EaseInExpo = new EasingFunction(){

            @Override
            public float getInterpolation(float f) {
                float f2 = 0.0f;
                if (f != 0.0f) return (float)Math.pow(2.0, (f - 1.0f) * 10.0f);
                return f2;
            }
        };
        EaseOutExpo = new EasingFunction(){

            @Override
            public float getInterpolation(float f) {
                float f2 = 1.0f;
                if (f != 1.0f) return -((float)Math.pow(2.0, (f + 1.0f) * -10.0f));
                return f2;
            }
        };
        EaseInOutExpo = new EasingFunction(){

            @Override
            public float getInterpolation(float f) {
                if (f == 0.0f) {
                    return 0.0f;
                }
                if (f == 1.0f) {
                    return 1.0f;
                }
                if ((f *= 2.0f) < 1.0f) {
                    f = (float)Math.pow(2.0, (f - 1.0f) * 10.0f);
                    return f * 0.5f;
                }
                f = -((float)Math.pow(2.0, (f - 1.0f) * -10.0f)) + 2.0f;
                return f * 0.5f;
            }
        };
        EaseInCirc = new EasingFunction(){

            @Override
            public float getInterpolation(float f) {
                return -((float)Math.sqrt(1.0f - f * f) - 1.0f);
            }
        };
        EaseOutCirc = new EasingFunction(){

            @Override
            public float getInterpolation(float f) {
                return (float)Math.sqrt(1.0f - (f -= 1.0f) * f);
            }
        };
        EaseInOutCirc = new EasingFunction(){

            @Override
            public float getInterpolation(float f) {
                if (!((f *= 2.0f) < 1.0f)) return ((float)Math.sqrt(1.0f - (f -= 2.0f) * f) + 1.0f) * 0.5f;
                return ((float)Math.sqrt(1.0f - f * f) - 1.0f) * -0.5f;
            }
        };
        EaseInElastic = new EasingFunction(){

            @Override
            public float getInterpolation(float f) {
                if (f == 0.0f) {
                    return 0.0f;
                }
                if (f == 1.0f) {
                    return 1.0f;
                }
                float f2 = (float)Math.asin(1.0);
                return -((float)Math.pow(2.0, 10.0f * (f -= 1.0f)) * (float)Math.sin((f - 0.047746483f * f2) * 6.2831855f / 0.3f));
            }
        };
        EaseOutElastic = new EasingFunction(){

            @Override
            public float getInterpolation(float f) {
                if (f == 0.0f) {
                    return 0.0f;
                }
                if (f == 1.0f) {
                    return 1.0f;
                }
                float f2 = (float)Math.asin(1.0);
                return (float)Math.pow(2.0, -10.0f * f) * (float)Math.sin((f - 0.047746483f * f2) * 6.2831855f / 0.3f) + 1.0f;
            }
        };
        EaseInOutElastic = new EasingFunction(){

            @Override
            public float getInterpolation(float f) {
                if (f == 0.0f) {
                    return 0.0f;
                }
                float f2 = f * 2.0f;
                if (f2 == 2.0f) {
                    return 1.0f;
                }
                f = (float)Math.asin(1.0) * 0.07161972f;
                if (!(f2 < 1.0f)) return (float)Math.pow(2.0, -10.0f * (f2 -= 1.0f)) * 0.5f * (float)Math.sin((f2 * 1.0f - f) * 6.2831855f * 2.2222223f) + 1.0f;
                return (float)Math.pow(2.0, 10.0f * (f2 -= 1.0f)) * (float)Math.sin((f2 * 1.0f - f) * 6.2831855f * 2.2222223f) * -0.5f;
            }
        };
        EaseInBack = new EasingFunction(){

            @Override
            public float getInterpolation(float f) {
                return f * f * (f * 2.70158f - 1.70158f);
            }
        };
        EaseOutBack = new EasingFunction(){

            @Override
            public float getInterpolation(float f) {
                return (f -= 1.0f) * f * (f * 2.70158f + 1.70158f) + 1.0f;
            }
        };
        EaseInOutBack = new EasingFunction(){

            @Override
            public float getInterpolation(float f) {
                if (!((f *= 2.0f) < 1.0f)) return ((f -= 2.0f) * f * (3.5949094f * f + 2.5949094f) + 2.0f) * 0.5f;
                return f * f * (3.5949094f * f - 2.5949094f) * 0.5f;
            }
        };
        EaseInBounce = new EasingFunction(){

            @Override
            public float getInterpolation(float f) {
                return 1.0f - EaseOutBounce.getInterpolation(1.0f - f);
            }
        };
        EaseOutBounce = new EasingFunction(){

            @Override
            public float getInterpolation(float f) {
                if (f < 0.36363637f) {
                    return 7.5625f * f * f;
                }
                if (f < 0.72727275f) {
                    return 7.5625f * (f -= 0.54545456f) * f + 0.75f;
                }
                if (!(f < 0.90909094f)) return 7.5625f * (f -= 0.95454544f) * f + 0.984375f;
                return 7.5625f * (f -= 0.8181818f) * f + 0.9375f;
            }
        };
        EaseInOutBounce = new EasingFunction(){

            @Override
            public float getInterpolation(float f) {
                if (!(f < 0.5f)) return EaseOutBounce.getInterpolation(f * 2.0f - 1.0f) * 0.5f + 0.5f;
                return EaseInBounce.getInterpolation(f * 2.0f) * 0.5f;
            }
        };
    }

    public static interface EasingFunction
    extends TimeInterpolator {
        public float getInterpolation(float var1);
    }

}

