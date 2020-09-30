/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.RectF
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseIntArray
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 */
package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.constraintlayout.motion.utils.Easing;
import androidx.constraintlayout.motion.widget.KeyPositionBase;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.SplineSet;
import androidx.constraintlayout.widget.R;
import java.io.PrintStream;
import java.util.HashMap;

public class KeyPosition
extends KeyPositionBase {
    static final int KEY_TYPE = 2;
    static final String NAME = "KeyPosition";
    private static final String PERCENT_X = "percentX";
    private static final String PERCENT_Y = "percentY";
    private static final String TAG = "KeyPosition";
    public static final int TYPE_CARTESIAN = 0;
    public static final int TYPE_PATH = 1;
    public static final int TYPE_SCREEN = 2;
    float mAltPercentX = Float.NaN;
    float mAltPercentY = Float.NaN;
    private float mCalculatedPositionX = Float.NaN;
    private float mCalculatedPositionY = Float.NaN;
    int mDrawPath = 0;
    int mPathMotionArc = UNSET;
    float mPercentHeight = Float.NaN;
    float mPercentWidth = Float.NaN;
    float mPercentX = Float.NaN;
    float mPercentY = Float.NaN;
    int mPositionType = 0;
    String mTransitionEasing = null;

    public KeyPosition() {
        this.mType = 2;
    }

    private void calcCartesianPosition(float f, float f2, float f3, float f4) {
        float f5 = f3 - f;
        float f6 = f4 - f2;
        boolean bl = Float.isNaN(this.mPercentX);
        float f7 = 0.0f;
        f3 = bl ? 0.0f : this.mPercentX;
        f4 = Float.isNaN(this.mAltPercentY) ? 0.0f : this.mAltPercentY;
        float f8 = Float.isNaN(this.mPercentY) ? 0.0f : this.mPercentY;
        if (!Float.isNaN(this.mAltPercentX)) {
            f7 = this.mAltPercentX;
        }
        this.mCalculatedPositionX = (int)(f + f3 * f5 + f7 * f6);
        this.mCalculatedPositionY = (int)(f2 + f5 * f4 + f6 * f8);
    }

    private void calcPathPosition(float f, float f2, float f3, float f4) {
        float f5 = f4 - f2;
        float f6 = -f5;
        f4 = this.mPercentX;
        float f7 = this.mPercentY;
        this.mCalculatedPositionX = f + (f3 -= f) * f4 + f6 * f7;
        this.mCalculatedPositionY = f2 + f5 * f4 + f3 * f7;
    }

    private void calcScreenPosition(int n, int n2) {
        float f = n - 0;
        float f2 = this.mPercentX;
        float f3 = (float)false;
        this.mCalculatedPositionX = f * f2 + f3;
        this.mCalculatedPositionY = (float)(n2 - 0) * f2 + f3;
    }

    @Override
    public void addValues(HashMap<String, SplineSet> hashMap) {
    }

    @Override
    void calcPosition(int n, int n2, float f, float f2, float f3, float f4) {
        int n3 = this.mPositionType;
        if (n3 == 1) {
            this.calcPathPosition(f, f2, f3, f4);
            return;
        }
        if (n3 != 2) {
            this.calcCartesianPosition(f, f2, f3, f4);
            return;
        }
        this.calcScreenPosition(n, n2);
    }

    @Override
    float getPositionX() {
        return this.mCalculatedPositionX;
    }

    @Override
    float getPositionY() {
        return this.mCalculatedPositionY;
    }

    @Override
    public boolean intersects(int n, int n2, RectF rectF, RectF rectF2, float f, float f2) {
        this.calcPosition(n, n2, rectF.centerX(), rectF.centerY(), rectF2.centerX(), rectF2.centerY());
        if (!(Math.abs(f - this.mCalculatedPositionX) < 20.0f)) return false;
        if (!(Math.abs(f2 - this.mCalculatedPositionY) < 20.0f)) return false;
        return true;
    }

    @Override
    public void load(Context context, AttributeSet attributeSet) {
        Loader.read(this, context.obtainStyledAttributes(attributeSet, R.styleable.KeyPosition));
    }

    @Override
    public void positionAttributes(View view, RectF rectF, RectF rectF2, float f, float f2, String[] arrstring, float[] arrf) {
        int n = this.mPositionType;
        if (n == 1) {
            this.positionPathAttributes(rectF, rectF2, f, f2, arrstring, arrf);
            return;
        }
        if (n != 2) {
            this.positionCartAttributes(rectF, rectF2, f, f2, arrstring, arrf);
            return;
        }
        this.positionScreenAttributes(view, rectF, rectF2, f, f2, arrstring, arrf);
    }

    void positionCartAttributes(RectF rectF, RectF rectF2, float f, float f2, String[] arrstring, float[] arrf) {
        float f3 = rectF.centerX();
        float f4 = rectF.centerY();
        float f5 = rectF2.centerX();
        float f6 = rectF2.centerY();
        f5 -= f3;
        f6 -= f4;
        if (arrstring[0] == null) {
            arrstring[0] = PERCENT_X;
            arrf[0] = (f - f3) / f5;
            arrstring[1] = PERCENT_Y;
            arrf[1] = (f2 - f4) / f6;
            return;
        }
        if (PERCENT_X.equals(arrstring[0])) {
            arrf[0] = (f - f3) / f5;
            arrf[1] = (f2 - f4) / f6;
            return;
        }
        arrf[1] = (f - f3) / f5;
        arrf[0] = (f2 - f4) / f6;
    }

    void positionPathAttributes(RectF rectF, RectF rectF2, float f, float f2, String[] arrstring, float[] arrf) {
        float f3 = rectF.centerX();
        float f4 = rectF.centerY();
        float f5 = rectF2.centerX();
        float f6 = rectF2.centerY();
        f5 -= f3;
        float f7 = f6 - f4;
        f6 = (float)Math.hypot(f5, f7);
        if ((double)f6 < 1.0E-4) {
            System.out.println("distance ~ 0");
            arrf[0] = 0.0f;
            arrf[1] = 0.0f;
            return;
        }
        f3 = f - f3;
        f = ((f5 /= f6) * (f2 -= f4) - f3 * (f7 /= f6)) / f6;
        f2 = (f5 * f3 + f7 * f2) / f6;
        if (arrstring[0] != null) {
            if (!PERCENT_X.equals(arrstring[0])) return;
            arrf[0] = f2;
            arrf[1] = f;
            return;
        }
        arrstring[0] = PERCENT_X;
        arrstring[1] = PERCENT_Y;
        arrf[0] = f2;
        arrf[1] = f;
    }

    void positionScreenAttributes(View view, RectF rectF, RectF rectF2, float f, float f2, String[] arrstring, float[] arrf) {
        rectF.centerX();
        rectF.centerY();
        rectF2.centerX();
        rectF2.centerY();
        view = (ViewGroup)view.getParent();
        int n = view.getWidth();
        int n2 = view.getHeight();
        if (arrstring[0] == null) {
            arrstring[0] = PERCENT_X;
            arrf[0] = f / (float)n;
            arrstring[1] = PERCENT_Y;
            arrf[1] = f2 / (float)n2;
            return;
        }
        if (PERCENT_X.equals(arrstring[0])) {
            arrf[0] = f / (float)n;
            arrf[1] = f2 / (float)n2;
            return;
        }
        arrf[1] = f / (float)n;
        arrf[0] = f2 / (float)n2;
    }

    @Override
    public void setValue(String string2, Object object) {
        int n;
        block18 : {
            switch (string2.hashCode()) {
                default: {
                    break;
                }
                case 428090548: {
                    if (!string2.equals(PERCENT_Y)) break;
                    n = 6;
                    break block18;
                }
                case 428090547: {
                    if (!string2.equals(PERCENT_X)) break;
                    n = 5;
                    break block18;
                }
                case -200259324: {
                    if (!string2.equals("sizePercent")) break;
                    n = 4;
                    break block18;
                }
                case -827014263: {
                    if (!string2.equals("drawPath")) break;
                    n = 1;
                    break block18;
                }
                case -1017587252: {
                    if (!string2.equals("percentHeight")) break;
                    n = 3;
                    break block18;
                }
                case -1127236479: {
                    if (!string2.equals("percentWidth")) break;
                    n = 2;
                    break block18;
                }
                case -1812823328: {
                    if (!string2.equals("transitionEasing")) break;
                    n = 0;
                    break block18;
                }
            }
            n = -1;
        }
        switch (n) {
            default: {
                return;
            }
            case 6: {
                this.mPercentY = this.toFloat(object);
                return;
            }
            case 5: {
                this.mPercentX = this.toFloat(object);
                return;
            }
            case 4: {
                float f;
                this.mPercentWidth = f = this.toFloat(object);
                this.mPercentHeight = f;
                return;
            }
            case 3: {
                this.mPercentHeight = this.toFloat(object);
                return;
            }
            case 2: {
                this.mPercentWidth = this.toFloat(object);
                return;
            }
            case 1: {
                this.mDrawPath = this.toInt(object);
                return;
            }
            case 0: 
        }
        this.mTransitionEasing = object.toString();
    }

    private static class Loader {
        private static final int CURVE_FIT = 4;
        private static final int DRAW_PATH = 5;
        private static final int FRAME_POSITION = 2;
        private static final int PATH_MOTION_ARC = 10;
        private static final int PERCENT_HEIGHT = 12;
        private static final int PERCENT_WIDTH = 11;
        private static final int PERCENT_X = 6;
        private static final int PERCENT_Y = 7;
        private static final int SIZE_PERCENT = 8;
        private static final int TARGET_ID = 1;
        private static final int TRANSITION_EASING = 3;
        private static final int TYPE = 9;
        private static SparseIntArray mAttrMap;

        static {
            SparseIntArray sparseIntArray;
            mAttrMap = sparseIntArray = new SparseIntArray();
            sparseIntArray.append(R.styleable.KeyPosition_motionTarget, 1);
            mAttrMap.append(R.styleable.KeyPosition_framePosition, 2);
            mAttrMap.append(R.styleable.KeyPosition_transitionEasing, 3);
            mAttrMap.append(R.styleable.KeyPosition_curveFit, 4);
            mAttrMap.append(R.styleable.KeyPosition_drawPath, 5);
            mAttrMap.append(R.styleable.KeyPosition_percentX, 6);
            mAttrMap.append(R.styleable.KeyPosition_percentY, 7);
            mAttrMap.append(R.styleable.KeyPosition_keyPositionType, 9);
            mAttrMap.append(R.styleable.KeyPosition_sizePercent, 8);
            mAttrMap.append(R.styleable.KeyPosition_percentWidth, 11);
            mAttrMap.append(R.styleable.KeyPosition_percentHeight, 12);
            mAttrMap.append(R.styleable.KeyPosition_pathMotionArc, 10);
        }

        private Loader() {
        }

        /*
         * Unable to fully structure code
         */
        private static void read(KeyPosition var0, TypedArray var1_1) {
            var2_2 = var1_1.getIndexCount();
            var3_3 = 0;
            do {
                if (var3_3 >= var2_2) {
                    if (var0.mFramePosition != -1) return;
                    Log.e((String)"KeyPosition", (String)"no frame position");
                    return;
                }
                var4_4 = var1_1.getIndex(var3_3);
                switch (Loader.mAttrMap.get(var4_4)) {
                    default: {
                        var5_5 = new StringBuilder();
                        var5_5.append("unused attribute 0x");
                        var5_5.append(Integer.toHexString(var4_4));
                        var5_5.append("   ");
                        var5_5.append(Loader.mAttrMap.get(var4_4));
                        Log.e((String)"KeyPosition", (String)var5_5.toString());
                        ** break;
                    }
                    case 12: {
                        var0.mPercentHeight = var1_1.getFloat(var4_4, var0.mPercentHeight);
                        ** break;
                    }
                    case 11: {
                        var0.mPercentWidth = var1_1.getFloat(var4_4, var0.mPercentWidth);
                        ** break;
                    }
                    case 10: {
                        var0.mPathMotionArc = var1_1.getInt(var4_4, var0.mPathMotionArc);
                        ** break;
                    }
                    case 9: {
                        var0.mPositionType = var1_1.getInt(var4_4, var0.mPositionType);
                        ** break;
                    }
                    case 8: {
                        var0.mPercentWidth = var6_6 = var1_1.getFloat(var4_4, var0.mPercentHeight);
                        var0.mPercentHeight = var6_6;
                        ** break;
                    }
                    case 7: {
                        var0.mPercentY = var1_1.getFloat(var4_4, var0.mPercentY);
                        ** break;
                    }
                    case 6: {
                        var0.mPercentX = var1_1.getFloat(var4_4, var0.mPercentX);
                        ** break;
                    }
                    case 5: {
                        var0.mDrawPath = var1_1.getInt(var4_4, var0.mDrawPath);
                        ** break;
                    }
                    case 4: {
                        var0.mCurveFit = var1_1.getInteger(var4_4, var0.mCurveFit);
                        ** break;
                    }
                    case 3: {
                        if (var1_1.peekValue((int)var4_4).type == 3) {
                            var0.mTransitionEasing = var1_1.getString(var4_4);
                            ** break;
                        }
                        var0.mTransitionEasing = Easing.NAMED_EASING[var1_1.getInteger(var4_4, 0)];
                        ** break;
                    }
                    case 2: {
                        var0.mFramePosition = var1_1.getInt(var4_4, var0.mFramePosition);
                        ** break;
                    }
                    case 1: 
                }
                if (MotionLayout.IS_IN_EDIT_MODE) {
                    var0.mTargetId = var1_1.getResourceId(var4_4, var0.mTargetId);
                    if (var0.mTargetId == -1) {
                        var0.mTargetString = var1_1.getString(var4_4);
                        ** break;
                    }
                } else {
                    if (var1_1.peekValue((int)var4_4).type == 3) {
                        var0.mTargetString = var1_1.getString(var4_4);
                        ** break;
                    }
                    var0.mTargetId = var1_1.getResourceId(var4_4, var0.mTargetId);
                }
lbl72: // 17 sources:
                ++var3_3;
            } while (true);
        }
    }

}

