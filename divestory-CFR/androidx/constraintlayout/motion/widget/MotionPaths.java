/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$MeasureSpec
 */
package androidx.constraintlayout.motion.widget;

import android.view.View;
import androidx.constraintlayout.motion.utils.Easing;
import androidx.constraintlayout.motion.widget.Key;
import androidx.constraintlayout.motion.widget.KeyPosition;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintSet;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

class MotionPaths
implements Comparable<MotionPaths> {
    static final int CARTESIAN = 2;
    public static final boolean DEBUG = false;
    static final int OFF_HEIGHT = 4;
    static final int OFF_PATH_ROTATE = 5;
    static final int OFF_POSITION = 0;
    static final int OFF_WIDTH = 3;
    static final int OFF_X = 1;
    static final int OFF_Y = 2;
    public static final boolean OLD_WAY = false;
    static final int PERPENDICULAR = 1;
    static final int SCREEN = 3;
    public static final String TAG = "MotionPaths";
    static String[] names = new String[]{"position", "x", "y", "width", "height", "pathRotate"};
    LinkedHashMap<String, ConstraintAttribute> attributes = new LinkedHashMap();
    float height;
    int mDrawPath = 0;
    Easing mKeyFrameEasing;
    int mMode = 0;
    int mPathMotionArc = Key.UNSET;
    float mPathRotate = Float.NaN;
    float mProgress = Float.NaN;
    double[] mTempDelta = new double[18];
    double[] mTempValue = new double[18];
    float position;
    float time;
    float width;
    float x;
    float y;

    public MotionPaths() {
    }

    public MotionPaths(int n, int n2, KeyPosition keyPosition, MotionPaths motionPaths, MotionPaths motionPaths2) {
        int n3 = keyPosition.mPositionType;
        if (n3 == 1) {
            this.initPath(keyPosition, motionPaths, motionPaths2);
            return;
        }
        if (n3 != 2) {
            this.initCartesian(keyPosition, motionPaths, motionPaths2);
            return;
        }
        this.initScreen(n, n2, keyPosition, motionPaths, motionPaths2);
    }

    private boolean diff(float f, float f2) {
        boolean bl = Float.isNaN(f);
        boolean bl2 = true;
        boolean bl3 = true;
        if (!bl && !Float.isNaN(f2)) {
            if (!(Math.abs(f - f2) > 1.0E-6f)) return false;
            return bl3;
        }
        if (Float.isNaN(f) == Float.isNaN(f2)) return false;
        return bl2;
    }

    private static final float xRotate(float f, float f2, float f3, float f4, float f5, float f6) {
        return (f5 - f3) * f2 - (f6 - f4) * f + f3;
    }

    private static final float yRotate(float f, float f2, float f3, float f4, float f5, float f6) {
        return (f5 - f3) * f + (f6 - f4) * f2 + f4;
    }

    public void applyParameters(ConstraintSet.Constraint constraint) {
        this.mKeyFrameEasing = Easing.getInterpolator(constraint.motion.mTransitionEasing);
        this.mPathMotionArc = constraint.motion.mPathMotionArc;
        this.mPathRotate = constraint.motion.mPathRotate;
        this.mDrawPath = constraint.motion.mDrawPath;
        this.mProgress = constraint.propertySet.mProgress;
        Iterator<String> iterator2 = constraint.mCustomConstraints.keySet().iterator();
        while (iterator2.hasNext()) {
            String string2 = iterator2.next();
            ConstraintAttribute constraintAttribute = constraint.mCustomConstraints.get(string2);
            if (constraintAttribute.getType() == ConstraintAttribute.AttributeType.STRING_TYPE) continue;
            this.attributes.put(string2, constraintAttribute);
        }
    }

    @Override
    public int compareTo(MotionPaths motionPaths) {
        return Float.compare(this.position, motionPaths.position);
    }

    void different(MotionPaths motionPaths, boolean[] arrbl, String[] arrstring, boolean bl) {
        arrbl[0] = arrbl[0] | this.diff(this.position, motionPaths.position);
        arrbl[1] = arrbl[1] | (this.diff(this.x, motionPaths.x) | bl);
        boolean bl2 = arrbl[2];
        arrbl[2] = bl | this.diff(this.y, motionPaths.y) | bl2;
        arrbl[3] = arrbl[3] | this.diff(this.width, motionPaths.width);
        bl = arrbl[4];
        arrbl[4] = this.diff(this.height, motionPaths.height) | bl;
    }

    void fillStandard(double[] arrd, int[] arrn) {
        float f = this.position;
        int n = 0;
        float f2 = this.x;
        float f3 = this.y;
        float f4 = this.width;
        float f5 = this.height;
        float f6 = this.mPathRotate;
        int n2 = 0;
        while (n < arrn.length) {
            int n3 = n2;
            if (arrn[n] < 6) {
                n3 = arrn[n];
                arrd[n2] = new float[]{f, f2, f3, f4, f5, f6}[n3];
                n3 = n2 + 1;
            }
            ++n;
            n2 = n3;
        }
    }

    void getBounds(int[] arrn, double[] arrd, float[] arrf, int n) {
        float f = this.width;
        float f2 = this.height;
        int n2 = 0;
        do {
            if (n2 >= arrn.length) {
                arrf[n] = f;
                arrf[n + 1] = f2;
                return;
            }
            float f3 = (float)arrd[n2];
            int n3 = arrn[n2];
            if (n3 != 3) {
                if (n3 == 4) {
                    f2 = f3;
                }
            } else {
                f = f3;
            }
            ++n2;
        } while (true);
    }

    void getCenter(int[] arrn, double[] arrd, float[] arrf, int n) {
        float f = this.x;
        float f2 = this.y;
        float f3 = this.width;
        float f4 = this.height;
        int n2 = 0;
        do {
            if (n2 >= arrn.length) {
                arrf[n] = f + f3 / 2.0f + 0.0f;
                arrf[n + 1] = f2 + f4 / 2.0f + 0.0f;
                return;
            }
            float f5 = (float)arrd[n2];
            int n3 = arrn[n2];
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 != 3) {
                        if (n3 == 4) {
                            f4 = f5;
                        }
                    } else {
                        f3 = f5;
                    }
                } else {
                    f2 = f5;
                }
            } else {
                f = f5;
            }
            ++n2;
        } while (true);
    }

    int getCustomData(String object, double[] arrd, int n) {
        if (((ConstraintAttribute)(object = this.attributes.get(object))).noOfInterpValues() == 1) {
            arrd[n] = ((ConstraintAttribute)object).getValueToInterpolate();
            return 1;
        }
        int n2 = ((ConstraintAttribute)object).noOfInterpValues();
        float[] arrf = new float[n2];
        ((ConstraintAttribute)object).getValuesToInterpolate(arrf);
        int n3 = 0;
        while (n3 < n2) {
            arrd[n] = arrf[n3];
            ++n3;
            ++n;
        }
        return n2;
    }

    int getCustomDataCount(String string2) {
        return this.attributes.get(string2).noOfInterpValues();
    }

    void getRect(int[] arrn, double[] arrd, float[] arrf, int n) {
        float f = this.x;
        float f2 = this.y;
        float f3 = this.width;
        float f4 = this.height;
        int n2 = 0;
        do {
            float f5;
            if (n2 >= arrn.length) {
                f5 = f3 + f;
                Float.isNaN(Float.NaN);
                Float.isNaN(Float.NaN);
                n2 = n + 1;
                arrf[n] = f + 0.0f;
                n = n2 + 1;
                arrf[n2] = f2 + 0.0f;
                n2 = n + 1;
                arrf[n] = f5 + 0.0f;
                n = n2 + 1;
                arrf[n2] = f2 + 0.0f;
                n2 = n + 1;
                arrf[n] = f5 + 0.0f;
                n = n2 + 1;
                arrf[n2] = (f4 += f2) + 0.0f;
                arrf[n] = f + 0.0f;
                arrf[n + 1] = f4 + 0.0f;
                return;
            }
            f5 = (float)arrd[n2];
            int n3 = arrn[n2];
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 != 3) {
                        if (n3 == 4) {
                            f4 = f5;
                        }
                    } else {
                        f3 = f5;
                    }
                } else {
                    f2 = f5;
                }
            } else {
                f = f5;
            }
            ++n2;
        } while (true);
    }

    boolean hasCustomData(String string2) {
        return this.attributes.containsKey(string2);
    }

    void initCartesian(KeyPosition keyPosition, MotionPaths motionPaths, MotionPaths motionPaths2) {
        float f;
        this.time = f = (float)keyPosition.mFramePosition / 100.0f;
        this.mDrawPath = keyPosition.mDrawPath;
        float f2 = Float.isNaN(keyPosition.mPercentWidth) ? f : keyPosition.mPercentWidth;
        float f3 = Float.isNaN(keyPosition.mPercentHeight) ? f : keyPosition.mPercentHeight;
        float f4 = motionPaths2.width;
        float f5 = motionPaths.width;
        float f6 = motionPaths2.height;
        float f7 = motionPaths.height;
        this.position = this.time;
        float f8 = motionPaths.x;
        float f9 = f5 / 2.0f;
        float f10 = motionPaths.y;
        float f11 = f7 / 2.0f;
        float f12 = motionPaths2.x;
        float f13 = f4 / 2.0f;
        float f14 = motionPaths2.y;
        float f15 = f6 / 2.0f;
        f9 = f12 + f13 - (f9 + f8);
        f15 = f14 + f15 - (f10 + f11);
        f2 = (f4 - f5) * f2;
        f4 = f2 / 2.0f;
        this.x = (int)(f8 + f9 * f - f4);
        f3 = (f6 - f7) * f3;
        f6 = f3 / 2.0f;
        this.y = (int)(f10 + f15 * f - f6);
        this.width = (int)(f5 + f2);
        this.height = (int)(f7 + f3);
        f2 = Float.isNaN(keyPosition.mPercentX) ? f : keyPosition.mPercentX;
        boolean bl = Float.isNaN(keyPosition.mAltPercentY);
        f7 = 0.0f;
        f3 = bl ? 0.0f : keyPosition.mAltPercentY;
        if (!Float.isNaN(keyPosition.mPercentY)) {
            f = keyPosition.mPercentY;
        }
        if (!Float.isNaN(keyPosition.mAltPercentX)) {
            f7 = keyPosition.mAltPercentX;
        }
        this.mMode = 2;
        this.x = (int)(motionPaths.x + f2 * f9 + f7 * f15 - f4);
        this.y = (int)(motionPaths.y + f9 * f3 + f15 * f - f6);
        this.mKeyFrameEasing = Easing.getInterpolator(keyPosition.mTransitionEasing);
        this.mPathMotionArc = keyPosition.mPathMotionArc;
    }

    void initPath(KeyPosition keyPosition, MotionPaths motionPaths, MotionPaths motionPaths2) {
        float f;
        this.time = f = (float)keyPosition.mFramePosition / 100.0f;
        this.mDrawPath = keyPosition.mDrawPath;
        float f2 = Float.isNaN(keyPosition.mPercentWidth) ? f : keyPosition.mPercentWidth;
        float f3 = Float.isNaN(keyPosition.mPercentHeight) ? f : keyPosition.mPercentHeight;
        float f4 = motionPaths2.width;
        float f5 = motionPaths.width;
        float f6 = motionPaths2.height;
        float f7 = motionPaths.height;
        this.position = this.time;
        if (!Float.isNaN(keyPosition.mPercentX)) {
            f = keyPosition.mPercentX;
        }
        float f8 = motionPaths.x;
        float f9 = motionPaths.width;
        float f10 = f9 / 2.0f;
        float f11 = motionPaths.y;
        float f12 = motionPaths.height;
        float f13 = f12 / 2.0f;
        float f14 = motionPaths2.x;
        float f15 = motionPaths2.width / 2.0f;
        float f16 = motionPaths2.y;
        float f17 = motionPaths2.height / 2.0f;
        f14 = f14 + f15 - (f10 + f8);
        f16 = f16 + f17 - (f13 + f11);
        f13 = f14 * f;
        f4 = (f4 - f5) * f2;
        f2 = f4 / 2.0f;
        this.x = (int)(f8 + f13 - f2);
        f8 = f * f16;
        f = (f6 - f7) * f3;
        f3 = f / 2.0f;
        this.y = (int)(f11 + f8 - f3);
        this.width = (int)(f9 + f4);
        this.height = (int)(f12 + f);
        f = Float.isNaN(keyPosition.mPercentY) ? 0.0f : keyPosition.mPercentY;
        f6 = -f16;
        this.mMode = 1;
        this.x = f2 = (float)((int)(motionPaths.x + f13 - f2));
        this.y = f3 = (float)((int)(motionPaths.y + f8 - f3));
        this.x = f2 + f6 * f;
        this.y = f3 + f14 * f;
        this.mKeyFrameEasing = Easing.getInterpolator(keyPosition.mTransitionEasing);
        this.mPathMotionArc = keyPosition.mPathMotionArc;
    }

    void initScreen(int n, int n2, KeyPosition keyPosition, MotionPaths motionPaths, MotionPaths motionPaths2) {
        float f;
        this.time = f = (float)keyPosition.mFramePosition / 100.0f;
        this.mDrawPath = keyPosition.mDrawPath;
        float f2 = Float.isNaN(keyPosition.mPercentWidth) ? f : keyPosition.mPercentWidth;
        float f3 = Float.isNaN(keyPosition.mPercentHeight) ? f : keyPosition.mPercentHeight;
        float f4 = motionPaths2.width;
        float f5 = motionPaths.width;
        float f6 = motionPaths2.height;
        float f7 = motionPaths.height;
        this.position = this.time;
        float f8 = motionPaths.x;
        float f9 = f5 / 2.0f;
        float f10 = motionPaths.y;
        float f11 = f7 / 2.0f;
        float f12 = motionPaths2.x;
        float f13 = f4 / 2.0f;
        float f14 = motionPaths2.y;
        float f15 = f6 / 2.0f;
        f2 = (f4 - f5) * f2;
        this.x = (int)(f8 + (f12 + f13 - (f9 + f8)) * f - f2 / 2.0f);
        f3 = (f6 - f7) * f3;
        this.y = (int)(f10 + (f14 + f15 - (f10 + f11)) * f - f3 / 2.0f);
        this.width = (int)(f5 + f2);
        this.height = (int)(f7 + f3);
        this.mMode = 3;
        if (!Float.isNaN(keyPosition.mPercentX)) {
            n = (int)((float)n - this.width);
            this.x = (int)(keyPosition.mPercentX * (float)n);
        }
        if (!Float.isNaN(keyPosition.mPercentY)) {
            n = (int)((float)n2 - this.height);
            this.y = (int)(keyPosition.mPercentY * (float)n);
        }
        this.mKeyFrameEasing = Easing.getInterpolator(keyPosition.mTransitionEasing);
        this.mPathMotionArc = keyPosition.mPathMotionArc;
    }

    void setBounds(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.width = f3;
        this.height = f4;
    }

    void setDpDt(float f, float f2, float[] arrf, int[] arrn, double[] arrd, double[] arrd2) {
        int n = 0;
        float f3 = 0.0f;
        float f4 = 0.0f;
        float f5 = 0.0f;
        float f6 = 0.0f;
        do {
            float f7;
            if (n >= arrn.length) {
                f7 = f3 - 0.0f * f4 / 2.0f;
                arrf[0] = f7 * (1.0f - f) + (f4 * 1.0f + f7) * f + 0.0f;
                arrf[1] = (f5 -= 0.0f * f6 / 2.0f) * (1.0f - f2) + (f6 * 1.0f + f5) * f2 + 0.0f;
                return;
            }
            f7 = (float)arrd[n];
            double d = arrd2[n];
            int n2 = arrn[n];
            if (n2 != 1) {
                if (n2 != 2) {
                    if (n2 != 3) {
                        if (n2 == 4) {
                            f6 = f7;
                        }
                    } else {
                        f4 = f7;
                    }
                } else {
                    f5 = f7;
                }
            } else {
                f3 = f7;
            }
            ++n;
        } while (true);
    }

    void setView(View view, int[] arrn, double[] arrd, double[] arrd2, double[] arrd3) {
        int n;
        float f;
        float f2;
        float f3 = this.x;
        float f4 = this.y;
        float f5 = this.width;
        float f6 = this.height;
        if (arrn.length != 0 && this.mTempValue.length <= arrn[arrn.length - 1]) {
            n = arrn[arrn.length - 1] + 1;
            this.mTempValue = new double[n];
            this.mTempDelta = new double[n];
        }
        Arrays.fill(this.mTempValue, Double.NaN);
        for (n = 0; n < arrn.length; ++n) {
            this.mTempValue[arrn[n]] = arrd[n];
            this.mTempDelta[arrn[n]] = arrd2[n];
        }
        float f7 = Float.NaN;
        float f8 = 0.0f;
        float f9 = 0.0f;
        float f10 = 0.0f;
        float f11 = 0.0f;
        for (n = 0; n < (arrn = this.mTempValue).length; ++n) {
            boolean bl = Double.isNaN(arrn[n]);
            double d = 0.0;
            if (bl && (arrd3 == null || arrd3[n] == 0.0)) continue;
            if (arrd3 != null) {
                d = arrd3[n];
            }
            if (!Double.isNaN(this.mTempValue[n])) {
                d = this.mTempValue[n] + d;
            }
            f2 = (float)d;
            f = (float)this.mTempDelta[n];
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) continue;
                            f7 = f2;
                            continue;
                        }
                        f6 = f2;
                        f11 = f;
                        continue;
                    }
                    f5 = f2;
                    f9 = f;
                    continue;
                }
                f4 = f2;
                f10 = f;
                continue;
            }
            f8 = f;
            f3 = f2;
        }
        if (Float.isNaN(f7)) {
            if (!Float.isNaN(Float.NaN)) {
                view.setRotation(Float.NaN);
            }
        } else {
            f2 = Float.NaN;
            if (Float.isNaN(Float.NaN)) {
                f2 = 0.0f;
            }
            f = f9 / 2.0f;
            view.setRotation((float)((double)f2 + ((double)f7 + Math.toDegrees(Math.atan2(f10 + (f11 /= 2.0f), f8 + f)))));
        }
        f2 = f3 + 0.5f;
        int n2 = (int)f2;
        f3 = f4 + 0.5f;
        int n3 = (int)f3;
        int n4 = (int)(f2 + f5);
        int n5 = (int)(f3 + f6);
        int n6 = n4 - n2;
        int n7 = n5 - n3;
        n = n6 == view.getMeasuredWidth() && n7 == view.getMeasuredHeight() ? 0 : 1;
        if (n != 0) {
            view.measure(View.MeasureSpec.makeMeasureSpec((int)n6, (int)1073741824), View.MeasureSpec.makeMeasureSpec((int)n7, (int)1073741824));
        }
        view.layout(n2, n3, n4, n5);
    }
}

