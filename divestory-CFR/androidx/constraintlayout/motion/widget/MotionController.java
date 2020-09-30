/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.RectF
 *  android.util.Log
 *  android.util.SparseArray
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.RectF;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.motion.utils.CurveFit;
import androidx.constraintlayout.motion.utils.Easing;
import androidx.constraintlayout.motion.utils.VelocityMatrix;
import androidx.constraintlayout.motion.widget.Key;
import androidx.constraintlayout.motion.widget.KeyAttributes;
import androidx.constraintlayout.motion.widget.KeyCache;
import androidx.constraintlayout.motion.widget.KeyCycle;
import androidx.constraintlayout.motion.widget.KeyCycleOscillator;
import androidx.constraintlayout.motion.widget.KeyPosition;
import androidx.constraintlayout.motion.widget.KeyPositionBase;
import androidx.constraintlayout.motion.widget.KeyTimeCycle;
import androidx.constraintlayout.motion.widget.KeyTrigger;
import androidx.constraintlayout.motion.widget.MotionConstrainedPoint;
import androidx.constraintlayout.motion.widget.MotionPaths;
import androidx.constraintlayout.motion.widget.SplineSet;
import androidx.constraintlayout.motion.widget.TimeCycleSplineSet;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

public class MotionController {
    private static final boolean DEBUG = false;
    public static final int DRAW_PATH_AS_CONFIGURED = 4;
    public static final int DRAW_PATH_BASIC = 1;
    public static final int DRAW_PATH_CARTESIAN = 3;
    public static final int DRAW_PATH_NONE = 0;
    public static final int DRAW_PATH_RECTANGLE = 5;
    public static final int DRAW_PATH_RELATIVE = 2;
    public static final int DRAW_PATH_SCREEN = 6;
    private static final boolean FAVOR_FIXED_SIZE_VIEWS = false;
    public static final int HORIZONTAL_PATH_X = 2;
    public static final int HORIZONTAL_PATH_Y = 3;
    public static final int PATH_PERCENT = 0;
    public static final int PATH_PERPENDICULAR = 1;
    private static final String TAG = "MotionController";
    public static final int VERTICAL_PATH_X = 4;
    public static final int VERTICAL_PATH_Y = 5;
    private int MAX_DIMENSION = 4;
    String[] attributeTable;
    private CurveFit mArcSpline;
    private int[] mAttributeInterpCount;
    private String[] mAttributeNames;
    private HashMap<String, SplineSet> mAttributesMap;
    String mConstraintTag;
    private int mCurveFitType = -1;
    private HashMap<String, KeyCycleOscillator> mCycleMap;
    private MotionPaths mEndMotionPath = new MotionPaths();
    private MotionConstrainedPoint mEndPoint = new MotionConstrainedPoint();
    int mId;
    private double[] mInterpolateData;
    private int[] mInterpolateVariables;
    private double[] mInterpolateVelocity;
    private ArrayList<Key> mKeyList = new ArrayList();
    private KeyTrigger[] mKeyTriggers;
    private ArrayList<MotionPaths> mMotionPaths = new ArrayList();
    float mMotionStagger = Float.NaN;
    private int mPathMotionArc = Key.UNSET;
    private CurveFit[] mSpline;
    float mStaggerOffset = 0.0f;
    float mStaggerScale = 1.0f;
    private MotionPaths mStartMotionPath = new MotionPaths();
    private MotionConstrainedPoint mStartPoint = new MotionConstrainedPoint();
    private HashMap<String, TimeCycleSplineSet> mTimeCycleAttributesMap;
    private float[] mValuesBuff = new float[4];
    private float[] mVelocity = new float[1];
    View mView;

    MotionController(View view) {
        this.setView(view);
    }

    private float getAdjustedPosition(float f, float[] arrf) {
        float f2;
        float f3;
        float f4 = 0.0f;
        float f5 = 1.0f;
        if (arrf != null) {
            arrf[0] = 1.0f;
            f3 = f;
        } else {
            f3 = f;
            if ((double)this.mStaggerScale != 1.0) {
                f2 = f;
                if (f < this.mStaggerOffset) {
                    f2 = 0.0f;
                }
                f = this.mStaggerOffset;
                f3 = f2;
                if (f2 > f) {
                    f3 = f2;
                    if ((double)f2 < 1.0) {
                        f3 = (f2 - f) * this.mStaggerScale;
                    }
                }
            }
        }
        Easing easing = this.mStartMotionPath.mKeyFrameEasing;
        f = Float.NaN;
        Iterator<MotionPaths> iterator2 = this.mMotionPaths.iterator();
        f2 = f4;
        while (iterator2.hasNext()) {
            MotionPaths motionPaths = iterator2.next();
            if (motionPaths.mKeyFrameEasing == null) continue;
            if (motionPaths.time < f3) {
                easing = motionPaths.mKeyFrameEasing;
                f2 = motionPaths.time;
                continue;
            }
            if (!Float.isNaN(f)) continue;
            f = motionPaths.time;
        }
        f4 = f3;
        if (easing == null) return f4;
        if (Float.isNaN(f)) {
            f = f5;
        }
        double d = (f3 - f2) / (f -= f2);
        f4 = f = (float)easing.get(d) * f + f2;
        if (arrf == null) return f4;
        arrf[0] = (float)easing.getDiff(d);
        return f;
    }

    private float getPreCycleDistance() {
        float[] arrf = new float[2];
        float f = 1.0f / (float)99;
        double d = 0.0;
        double d2 = 0.0;
        int n = 0;
        float f2 = 0.0f;
        while (n < 100) {
            float f3 = (float)n * f;
            double d3 = f3;
            Easing easing = this.mStartMotionPath.mKeyFrameEasing;
            float f4 = Float.NaN;
            Iterator<MotionPaths> iterator2 = this.mMotionPaths.iterator();
            float f5 = 0.0f;
            while (iterator2.hasNext()) {
                MotionPaths motionPaths = iterator2.next();
                if (motionPaths.mKeyFrameEasing == null) continue;
                if (motionPaths.time < f3) {
                    easing = motionPaths.mKeyFrameEasing;
                    f5 = motionPaths.time;
                    continue;
                }
                if (!Float.isNaN(f4)) continue;
                f4 = motionPaths.time;
            }
            if (easing != null) {
                float f6 = f4;
                if (Float.isNaN(f4)) {
                    f6 = 1.0f;
                }
                f4 = f6 - f5;
                d3 = (float)easing.get((f3 - f5) / f4) * f4 + f5;
            }
            this.mSpline[0].getPos(d3, this.mInterpolateData);
            this.mStartMotionPath.getCenter(this.mInterpolateVariables, this.mInterpolateData, arrf, 0);
            f4 = f2;
            if (n > 0) {
                f4 = (float)((double)f2 + Math.hypot(d2 - (double)arrf[1], d - (double)arrf[0]));
            }
            d = arrf[0];
            d2 = arrf[1];
            ++n;
            f2 = f4;
        }
        return f2;
    }

    private void insertKey(MotionPaths motionPaths) {
        int n = Collections.binarySearch(this.mMotionPaths, motionPaths);
        if (n == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" KeyPath positon \"");
            stringBuilder.append(motionPaths.position);
            stringBuilder.append("\" outside of range");
            Log.e((String)TAG, (String)stringBuilder.toString());
        }
        this.mMotionPaths.add(-n - 1, motionPaths);
    }

    private void readView(MotionPaths motionPaths) {
        motionPaths.setBounds((int)this.mView.getX(), (int)this.mView.getY(), this.mView.getWidth(), this.mView.getHeight());
    }

    void addKey(Key key) {
        this.mKeyList.add(key);
    }

    void addKeys(ArrayList<Key> arrayList) {
        this.mKeyList.addAll(arrayList);
    }

    void buildBounds(float[] arrf, int n) {
        float f = 1.0f / (float)(n - 1);
        HashMap<String, SplineSet> hashMap = this.mAttributesMap;
        if (hashMap != null) {
            hashMap = hashMap.get("translationX");
        }
        hashMap = this.mAttributesMap;
        if (hashMap != null) {
            hashMap = (SplineSet)hashMap.get("translationY");
        }
        hashMap = this.mCycleMap;
        if (hashMap != null) {
            hashMap = (KeyCycleOscillator)hashMap.get("translationX");
        }
        hashMap = this.mCycleMap;
        if (hashMap != null) {
            hashMap = (KeyCycleOscillator)hashMap.get("translationY");
        }
        int n2 = 0;
        while (n2 < n) {
            float f2 = (float)n2 * f;
            float f3 = this.mStaggerScale;
            float f4 = 0.0f;
            float f5 = f2;
            if (f3 != 1.0f) {
                f3 = f2;
                if (f2 < this.mStaggerOffset) {
                    f3 = 0.0f;
                }
                f2 = this.mStaggerOffset;
                f5 = f3;
                if (f3 > f2) {
                    f5 = f3;
                    if ((double)f3 < 1.0) {
                        f5 = (f3 - f2) * this.mStaggerScale;
                    }
                }
            }
            double d = f5;
            hashMap = this.mStartMotionPath.mKeyFrameEasing;
            f3 = Float.NaN;
            double[] arrd = this.mMotionPaths.iterator();
            f2 = f4;
            while (arrd.hasNext()) {
                MotionPaths motionPaths = arrd.next();
                if (motionPaths.mKeyFrameEasing == null) continue;
                if (motionPaths.time < f5) {
                    hashMap = motionPaths.mKeyFrameEasing;
                    f2 = motionPaths.time;
                    continue;
                }
                if (!Float.isNaN(f3)) continue;
                f3 = motionPaths.time;
            }
            if (hashMap != null) {
                f4 = f3;
                if (Float.isNaN(f3)) {
                    f4 = 1.0f;
                }
                f3 = f4 - f2;
                d = (float)((Easing)((Object)hashMap)).get((f5 - f2) / f3) * f3 + f2;
            }
            this.mSpline[0].getPos(d, this.mInterpolateData);
            hashMap = this.mArcSpline;
            if (hashMap != null && (arrd = this.mInterpolateData).length > 0) {
                ((CurveFit)((Object)hashMap)).getPos(d, arrd);
            }
            this.mStartMotionPath.getBounds(this.mInterpolateVariables, this.mInterpolateData, arrf, n2 * 2);
            ++n2;
        }
    }

    int buildKeyBounds(float[] arrf, int[] arrn) {
        int n;
        if (arrf == null) return 0;
        double[] arrd = this.mSpline[0].getTimePoints();
        if (arrn != null) {
            Iterator<MotionPaths> iterator2 = this.mMotionPaths.iterator();
            n = 0;
            while (iterator2.hasNext()) {
                arrn[n] = iterator2.next().mMode;
                ++n;
            }
        }
        n = 0;
        int n2 = 0;
        while (n < arrd.length) {
            this.mSpline[0].getPos(arrd[n], this.mInterpolateData);
            this.mStartMotionPath.getBounds(this.mInterpolateVariables, this.mInterpolateData, arrf, n2);
            n2 += 2;
            ++n;
        }
        return n2 / 2;
    }

    int buildKeyFrames(float[] arrf, int[] arrn) {
        int n;
        if (arrf == null) return 0;
        double[] arrd = this.mSpline[0].getTimePoints();
        if (arrn != null) {
            Iterator<MotionPaths> iterator2 = this.mMotionPaths.iterator();
            n = 0;
            while (iterator2.hasNext()) {
                arrn[n] = iterator2.next().mMode;
                ++n;
            }
        }
        n = 0;
        int n2 = 0;
        while (n < arrd.length) {
            this.mSpline[0].getPos(arrd[n], this.mInterpolateData);
            this.mStartMotionPath.getCenter(this.mInterpolateVariables, this.mInterpolateData, arrf, n2);
            n2 += 2;
            ++n;
        }
        return n2 / 2;
    }

    void buildPath(float[] arrf, int n) {
        float f = 1.0f / (float)(n - 1);
        Object object = this.mAttributesMap;
        KeyCycleOscillator keyCycleOscillator = null;
        object = object == null ? null : ((HashMap)object).get("translationX");
        Object object2 = this.mAttributesMap;
        object2 = object2 == null ? null : ((HashMap)object2).get("translationY");
        Object object3 = this.mCycleMap;
        object3 = object3 == null ? null : ((HashMap)object3).get("translationX");
        Object object4 = this.mCycleMap;
        if (object4 != null) {
            keyCycleOscillator = object4.get("translationY");
        }
        int n2 = 0;
        while (n2 < n) {
            Object object5;
            float f2 = (float)n2 * f;
            float f3 = this.mStaggerScale;
            float f4 = 0.0f;
            float f5 = f2;
            if (f3 != 1.0f) {
                f3 = f2;
                if (f2 < this.mStaggerOffset) {
                    f3 = 0.0f;
                }
                f2 = this.mStaggerOffset;
                f5 = f3;
                if (f3 > f2) {
                    f5 = f3;
                    if ((double)f3 < 1.0) {
                        f5 = (f3 - f2) * this.mStaggerScale;
                    }
                }
            }
            double d = f5;
            object4 = this.mStartMotionPath.mKeyFrameEasing;
            f3 = Float.NaN;
            Object object6 = this.mMotionPaths.iterator();
            f2 = f4;
            while (object6.hasNext()) {
                MotionPaths motionPaths = object6.next();
                object5 = object4;
                float f6 = f2;
                f4 = f3;
                if (motionPaths.mKeyFrameEasing != null) {
                    if (motionPaths.time < f5) {
                        object5 = motionPaths.mKeyFrameEasing;
                        f6 = motionPaths.time;
                        f4 = f3;
                    } else {
                        object5 = object4;
                        f6 = f2;
                        f4 = f3;
                        if (Float.isNaN(f3)) {
                            f4 = motionPaths.time;
                            f6 = f2;
                            object5 = object4;
                        }
                    }
                }
                object4 = object5;
                f2 = f6;
                f3 = f4;
            }
            if (object4 != null) {
                f4 = f3;
                if (Float.isNaN(f3)) {
                    f4 = 1.0f;
                }
                f3 = f4 - f2;
                d = (float)object4.get((f5 - f2) / f3) * f3 + f2;
            }
            this.mSpline[0].getPos(d, this.mInterpolateData);
            object5 = this.mArcSpline;
            if (object5 != null && ((double[])(object4 = this.mInterpolateData)).length > 0) {
                ((CurveFit)object5).getPos(d, (double[])object4);
            }
            object6 = this.mStartMotionPath;
            object4 = this.mInterpolateVariables;
            object5 = this.mInterpolateData;
            int n3 = n2 * 2;
            ((MotionPaths)object6).getCenter((int[])object4, (double[])object5, arrf, n3);
            if (object3 != null) {
                arrf[n3] = arrf[n3] + ((KeyCycleOscillator)object3).get(f5);
            } else if (object != null) {
                arrf[n3] = arrf[n3] + ((SplineSet)object).get(f5);
            }
            if (keyCycleOscillator != null) {
                arrf[++n3] = arrf[n3] + keyCycleOscillator.get(f5);
            } else if (object2 != null) {
                arrf[++n3] = arrf[n3] + ((SplineSet)object2).get(f5);
            }
            ++n2;
        }
    }

    void buildRect(float f, float[] arrf, int n) {
        f = this.getAdjustedPosition(f, null);
        this.mSpline[0].getPos((double)f, this.mInterpolateData);
        this.mStartMotionPath.getRect(this.mInterpolateVariables, this.mInterpolateData, arrf, n);
    }

    void buildRectangles(float[] arrf, int n) {
        float f = 1.0f / (float)(n - 1);
        int n2 = 0;
        while (n2 < n) {
            float f2 = this.getAdjustedPosition((float)n2 * f, null);
            this.mSpline[0].getPos((double)f2, this.mInterpolateData);
            this.mStartMotionPath.getRect(this.mInterpolateVariables, this.mInterpolateData, arrf, n2 * 8);
            ++n2;
        }
    }

    int getAttributeValues(String object, float[] arrf, int n) {
        if ((object = this.mAttributesMap.get(object)) == null) {
            return -1;
        }
        n = 0;
        while (n < arrf.length) {
            arrf[n] = ((SplineSet)object).get(n / (arrf.length - 1));
            ++n;
        }
        return arrf.length;
    }

    void getDpDt(float f, float f2, float f3, float[] arrf) {
        double[] arrd;
        f = this.getAdjustedPosition(f, this.mVelocity);
        Object object = this.mSpline;
        if (object == null) {
            float f4 = this.mEndMotionPath.x - this.mStartMotionPath.x;
            float f5 = this.mEndMotionPath.y - this.mStartMotionPath.y;
            float f6 = this.mEndMotionPath.width;
            f = this.mStartMotionPath.width;
            float f7 = this.mEndMotionPath.height;
            float f8 = this.mStartMotionPath.height;
            arrf[0] = f4 * (1.0f - f2) + (f6 - f + f4) * f2;
            arrf[1] = f5 * (1.0f - f3) + (f7 - f8 + f5) * f3;
            return;
        }
        object = object[0];
        double d = f;
        ((CurveFit)object).getSlope(d, this.mInterpolateVelocity);
        this.mSpline[0].getPos(d, this.mInterpolateData);
        f = this.mVelocity[0];
        for (int i = 0; i < (arrd = this.mInterpolateVelocity).length; ++i) {
            arrd[i] = arrd[i] * (double)f;
        }
        object = this.mArcSpline;
        if (object != null) {
            arrd = this.mInterpolateData;
            if (arrd.length <= 0) return;
            ((CurveFit)object).getPos(d, arrd);
            this.mArcSpline.getSlope(d, this.mInterpolateVelocity);
            this.mStartMotionPath.setDpDt(f2, f3, arrf, this.mInterpolateVariables, this.mInterpolateVelocity, this.mInterpolateData);
            return;
        }
        this.mStartMotionPath.setDpDt(f2, f3, arrf, this.mInterpolateVariables, arrd, this.mInterpolateData);
    }

    public int getDrawPath() {
        int n = this.mStartMotionPath.mDrawPath;
        Iterator<MotionPaths> iterator2 = this.mMotionPaths.iterator();
        while (iterator2.hasNext()) {
            n = Math.max(n, iterator2.next().mDrawPath);
        }
        return Math.max(n, this.mEndMotionPath.mDrawPath);
    }

    float getFinalX() {
        return this.mEndMotionPath.x;
    }

    float getFinalY() {
        return this.mEndMotionPath.y;
    }

    MotionPaths getKeyFrame(int n) {
        return this.mMotionPaths.get(n);
    }

    public int getKeyFrameInfo(int n, int[] arrn) {
        float[] arrf = new float[2];
        Iterator<Key> iterator2 = this.mKeyList.iterator();
        int n2 = 0;
        int n3 = 0;
        while (iterator2.hasNext()) {
            Key key = iterator2.next();
            if (key.mType != n && n == -1) continue;
            arrn[n3] = 0;
            int n4 = n3 + 1;
            arrn[n4] = key.mType;
            arrn[++n4] = key.mFramePosition;
            float f = (float)key.mFramePosition / 100.0f;
            this.mSpline[0].getPos((double)f, this.mInterpolateData);
            this.mStartMotionPath.getCenter(this.mInterpolateVariables, this.mInterpolateData, arrf, 0);
            arrn[++n4] = Float.floatToIntBits(arrf[0]);
            int n5 = n4 + 1;
            arrn[n5] = Float.floatToIntBits(arrf[1]);
            n4 = n5;
            if (key instanceof KeyPosition) {
                key = (KeyPosition)key;
                n4 = n5 + 1;
                arrn[n4] = ((KeyPosition)key).mPositionType;
                arrn[++n4] = Float.floatToIntBits(((KeyPosition)key).mPercentX);
                arrn[++n4] = Float.floatToIntBits(((KeyPosition)key).mPercentY);
            }
            arrn[n3] = ++n4 - n3;
            ++n2;
            n3 = n4;
        }
        return n2;
    }

    float getKeyFrameParameter(int n, float f, float f2) {
        float f3 = this.mEndMotionPath.x - this.mStartMotionPath.x;
        float f4 = this.mEndMotionPath.y - this.mStartMotionPath.y;
        float f5 = this.mStartMotionPath.x;
        float f6 = this.mStartMotionPath.width / 2.0f;
        float f7 = this.mStartMotionPath.y;
        float f8 = this.mStartMotionPath.height / 2.0f;
        float f9 = (float)Math.hypot(f3, f4);
        if ((double)f9 < 1.0E-7) {
            return Float.NaN;
        }
        if ((float)Math.hypot(f -= f5 + f6, f8 = f2 - (f7 + f8)) == 0.0f) {
            return 0.0f;
        }
        f2 = f * f3 + f8 * f4;
        if (n == 0) return f2 / f9;
        if (n == 1) return (float)Math.sqrt(f9 * f9 - f2 * f2);
        if (n == 2) return f / f3;
        if (n == 3) return f8 / f3;
        if (n == 4) return f / f4;
        if (n == 5) return f8 / f4;
        return 0.0f;
    }

    KeyPositionBase getPositionKeyframe(int n, int n2, float f, float f2) {
        Key key;
        RectF rectF = new RectF();
        rectF.left = this.mStartMotionPath.x;
        rectF.top = this.mStartMotionPath.y;
        rectF.right = rectF.left + this.mStartMotionPath.width;
        rectF.bottom = rectF.top + this.mStartMotionPath.height;
        RectF rectF2 = new RectF();
        rectF2.left = this.mEndMotionPath.x;
        rectF2.top = this.mEndMotionPath.y;
        rectF2.right = rectF2.left + this.mEndMotionPath.width;
        rectF2.bottom = rectF2.top + this.mEndMotionPath.height;
        Iterator<Key> iterator2 = this.mKeyList.iterator();
        do {
            if (!iterator2.hasNext()) return null;
        } while (!((key = iterator2.next()) instanceof KeyPositionBase) || !((KeyPositionBase)(key = (KeyPositionBase)key)).intersects(n, n2, rectF, rectF2, f, f2));
        return key;
    }

    void getPostLayoutDvDp(float f, int n, int n2, float f2, float f3, float[] arrf) {
        f = this.getAdjustedPosition(f, this.mVelocity);
        Object object = this.mAttributesMap;
        KeyCycleOscillator keyCycleOscillator = null;
        object = object == null ? null : ((HashMap)object).get("translationX");
        Object object2 = this.mAttributesMap;
        object2 = object2 == null ? null : ((HashMap)object2).get("translationY");
        Object object3 = this.mAttributesMap;
        object3 = object3 == null ? null : ((HashMap)object3).get("rotation");
        Object object4 = this.mAttributesMap;
        object4 = object4 == null ? null : ((HashMap)object4).get("scaleX");
        Object object5 = this.mAttributesMap;
        object5 = object5 == null ? null : ((HashMap)object5).get("scaleY");
        Object object6 = this.mCycleMap;
        object6 = object6 == null ? null : ((HashMap)object6).get("translationX");
        Object object7 = this.mCycleMap;
        object7 = object7 == null ? null : ((HashMap)object7).get("translationY");
        Object object8 = this.mCycleMap;
        object8 = object8 == null ? null : ((HashMap)object8).get("rotation");
        Object object9 = this.mCycleMap;
        object9 = object9 == null ? null : ((HashMap)object9).get("scaleX");
        HashMap<String, KeyCycleOscillator> hashMap = this.mCycleMap;
        if (hashMap != null) {
            keyCycleOscillator = hashMap.get("scaleY");
        }
        hashMap = new VelocityMatrix();
        ((VelocityMatrix)((Object)hashMap)).clear();
        ((VelocityMatrix)((Object)hashMap)).setRotationVelocity((SplineSet)object3, f);
        ((VelocityMatrix)((Object)hashMap)).setTranslationVelocity((SplineSet)object, (SplineSet)object2, f);
        ((VelocityMatrix)((Object)hashMap)).setScaleVelocity((SplineSet)object4, (SplineSet)object5, f);
        ((VelocityMatrix)((Object)hashMap)).setRotationVelocity((KeyCycleOscillator)object8, f);
        ((VelocityMatrix)((Object)hashMap)).setTranslationVelocity((KeyCycleOscillator)object6, (KeyCycleOscillator)object7, f);
        ((VelocityMatrix)((Object)hashMap)).setScaleVelocity((KeyCycleOscillator)object9, keyCycleOscillator, f);
        CurveFit[] arrcurveFit = this.mArcSpline;
        if (arrcurveFit != null) {
            object = this.mInterpolateData;
            if (((double[])object).length > 0) {
                double d = f;
                arrcurveFit.getPos(d, (double[])object);
                this.mArcSpline.getSlope(d, this.mInterpolateVelocity);
                this.mStartMotionPath.setDpDt(f2, f3, arrf, this.mInterpolateVariables, this.mInterpolateVelocity, this.mInterpolateData);
            }
            ((VelocityMatrix)((Object)hashMap)).applyTransform(f2, f3, n, n2, arrf);
            return;
        }
        arrcurveFit = this.mSpline;
        int n3 = 0;
        if (arrcurveFit == null) {
            float f4 = this.mEndMotionPath.x - this.mStartMotionPath.x;
            float f5 = this.mEndMotionPath.y - this.mStartMotionPath.y;
            float f6 = this.mEndMotionPath.width;
            float f7 = this.mStartMotionPath.width;
            float f8 = this.mEndMotionPath.height;
            float f9 = this.mStartMotionPath.height;
            arrf[0] = f4 * (1.0f - f2) + (f6 - f7 + f4) * f2;
            arrf[1] = f5 * (1.0f - f3) + (f8 - f9 + f5) * f3;
            ((VelocityMatrix)((Object)hashMap)).clear();
            ((VelocityMatrix)((Object)hashMap)).setRotationVelocity((SplineSet)object3, f);
            ((VelocityMatrix)((Object)hashMap)).setTranslationVelocity((SplineSet)object, (SplineSet)object2, f);
            ((VelocityMatrix)((Object)hashMap)).setScaleVelocity((SplineSet)object4, (SplineSet)object5, f);
            ((VelocityMatrix)((Object)hashMap)).setRotationVelocity((KeyCycleOscillator)object8, f);
            ((VelocityMatrix)((Object)hashMap)).setTranslationVelocity((KeyCycleOscillator)object6, (KeyCycleOscillator)object7, f);
            ((VelocityMatrix)((Object)hashMap)).setScaleVelocity((KeyCycleOscillator)object9, keyCycleOscillator, f);
            ((VelocityMatrix)((Object)hashMap)).applyTransform(f2, f3, n, n2, arrf);
            return;
        }
        f = this.getAdjustedPosition(f, this.mVelocity);
        object = this.mSpline[0];
        double d = f;
        ((CurveFit)object).getSlope(d, this.mInterpolateVelocity);
        this.mSpline[0].getPos(d, this.mInterpolateData);
        f = this.mVelocity[0];
        do {
            if (n3 >= ((Object)(object = this.mInterpolateVelocity)).length) {
                this.mStartMotionPath.setDpDt(f2, f3, arrf, this.mInterpolateVariables, (double[])object, this.mInterpolateData);
                ((VelocityMatrix)((Object)hashMap)).applyTransform(f2, f3, n, n2, arrf);
                return;
            }
            object[n3] = object[n3] * (double)f;
            ++n3;
        } while (true);
    }

    float getStartX() {
        return this.mStartMotionPath.x;
    }

    float getStartY() {
        return this.mStartMotionPath.y;
    }

    public int getkeyFramePositions(int[] arrn, float[] arrf) {
        Iterator<Key> iterator2 = this.mKeyList.iterator();
        int n = 0;
        int n2 = 0;
        while (iterator2.hasNext()) {
            Key key = iterator2.next();
            arrn[n] = key.mFramePosition + key.mType * 1000;
            float f = (float)key.mFramePosition / 100.0f;
            this.mSpline[0].getPos((double)f, this.mInterpolateData);
            this.mStartMotionPath.getCenter(this.mInterpolateVariables, this.mInterpolateData, arrf, n2);
            n2 += 2;
            ++n;
        }
        return n;
    }

    boolean interpolate(View view, float f, long l, KeyCache object) {
        Object object22;
        int n;
        boolean bl;
        boolean bl2;
        Object object3;
        float f2 = this.getAdjustedPosition(f, null);
        Object object4 = this.mAttributesMap;
        if (object4 != null) {
            object4 = ((HashMap)object4).values().iterator();
            while (object4.hasNext()) {
                ((SplineSet)object4.next()).setProperty(view, f2);
            }
        }
        if ((object4 = this.mTimeCycleAttributesMap) == null) {
            object4 = null;
            bl2 = false;
        } else {
            object3 = ((HashMap)object4).values().iterator();
            object4 = null;
            bl2 = false;
            while (object3.hasNext()) {
                object22 = (TimeCycleSplineSet)object3.next();
                if (object22 instanceof TimeCycleSplineSet.PathRotate) {
                    object4 = (TimeCycleSplineSet.PathRotate)object22;
                    continue;
                }
                bl2 |= object22.setProperty(view, f2, l, (KeyCache)object);
            }
        }
        if ((object3 = this.mSpline) != null) {
            object3 = object3[0];
            double d = f2;
            ((CurveFit)object3).getPos(d, this.mInterpolateData);
            this.mSpline[0].getSlope(d, this.mInterpolateVelocity);
            object22 = this.mArcSpline;
            if (object22 != null && ((Object)(object3 = this.mInterpolateData)).length > 0) {
                object22.getPos(d, (double[])object3);
                this.mArcSpline.getSlope(d, this.mInterpolateVelocity);
            }
            this.mStartMotionPath.setView(view, this.mInterpolateVariables, this.mInterpolateData, this.mInterpolateVelocity, null);
            object3 = this.mAttributesMap;
            if (object3 != null) {
                for (Object object22 : ((HashMap)object3).values()) {
                    if (!(object22 instanceof SplineSet.PathRotate)) continue;
                    SplineSet.PathRotate pathRotate = (SplineSet.PathRotate)object22;
                    object22 = this.mInterpolateVelocity;
                    pathRotate.setPathRotate(view, f2, object22[0], object22[1]);
                }
            }
            if (object4 != null) {
                object3 = this.mInterpolateVelocity;
                bl2 = ((TimeCycleSplineSet.PathRotate)object4).setPathRotate(view, (KeyCache)object, f2, l, (double)object3[0], (double)object3[1]) | bl2;
            }
            for (n = 1; n < ((CurveFit[])(object = this.mSpline)).length; ++n) {
                object[n].getPos(d, this.mValuesBuff);
                this.mStartMotionPath.attributes.get(this.mAttributeNames[n - 1]).setInterpolatedValue(view, this.mValuesBuff);
            }
            if (this.mStartPoint.mVisibilityMode == 0) {
                if (f2 <= 0.0f) {
                    view.setVisibility(this.mStartPoint.visibility);
                } else if (f2 >= 1.0f) {
                    view.setVisibility(this.mEndPoint.visibility);
                } else if (this.mEndPoint.visibility != this.mStartPoint.visibility) {
                    view.setVisibility(0);
                }
            }
            bl = bl2;
            if (this.mKeyTriggers != null) {
                n = 0;
                do {
                    object = this.mKeyTriggers;
                    bl = bl2;
                    if (n < ((Object)object).length) {
                        ((KeyTrigger)object[n]).conditionallyFire(f2, view);
                        ++n;
                        continue;
                    }
                    break;
                } while (true);
            }
        } else {
            float f3 = this.mStartMotionPath.x;
            float f4 = this.mEndMotionPath.x;
            float f5 = this.mStartMotionPath.x;
            float f6 = this.mStartMotionPath.y;
            float f7 = this.mEndMotionPath.y;
            float f8 = this.mStartMotionPath.y;
            float f9 = this.mStartMotionPath.width;
            float f10 = this.mEndMotionPath.width;
            float f11 = this.mStartMotionPath.width;
            float f12 = this.mStartMotionPath.height;
            float f13 = this.mEndMotionPath.height;
            f = this.mStartMotionPath.height;
            f5 = f3 + (f4 - f5) * f2 + 0.5f;
            int n2 = (int)f5;
            f7 = f6 + (f7 - f8) * f2 + 0.5f;
            n = (int)f7;
            int n3 = (int)(f5 + (f9 + (f10 - f11) * f2));
            int n4 = (int)(f7 + (f12 + (f13 - f) * f2));
            if (this.mEndMotionPath.width != this.mStartMotionPath.width || this.mEndMotionPath.height != this.mStartMotionPath.height) {
                view.measure(View.MeasureSpec.makeMeasureSpec((int)(n3 - n2), (int)1073741824), View.MeasureSpec.makeMeasureSpec((int)(n4 - n), (int)1073741824));
            }
            view.layout(n2, n, n3, n4);
            bl = bl2;
        }
        if ((object = this.mCycleMap) == null) return bl;
        object = ((HashMap)object).values().iterator();
        while (object.hasNext()) {
            object4 = (KeyCycleOscillator)object.next();
            if (object4 instanceof KeyCycleOscillator.PathRotateSet) {
                object4 = (KeyCycleOscillator.PathRotateSet)object4;
                object3 = this.mInterpolateVelocity;
                ((KeyCycleOscillator.PathRotateSet)object4).setPathRotate(view, f2, (double)object3[0], (double)object3[1]);
                continue;
            }
            ((KeyCycleOscillator)object4).setProperty(view, f2);
        }
        return bl;
    }

    String name() {
        return this.mView.getContext().getResources().getResourceEntryName(this.mView.getId());
    }

    void positionKeyframe(View view, KeyPositionBase keyPositionBase, float f, float f2, String[] arrstring, float[] arrf) {
        RectF rectF = new RectF();
        rectF.left = this.mStartMotionPath.x;
        rectF.top = this.mStartMotionPath.y;
        rectF.right = rectF.left + this.mStartMotionPath.width;
        rectF.bottom = rectF.top + this.mStartMotionPath.height;
        RectF rectF2 = new RectF();
        rectF2.left = this.mEndMotionPath.x;
        rectF2.top = this.mEndMotionPath.y;
        rectF2.right = rectF2.left + this.mEndMotionPath.width;
        rectF2.bottom = rectF2.top + this.mEndMotionPath.height;
        keyPositionBase.positionAttributes(view, rectF, rectF2, f, f2, arrstring, arrf);
    }

    public void setDrawPath(int n) {
        this.mStartMotionPath.mDrawPath = n;
    }

    void setEndState(ConstraintWidget constraintWidget, ConstraintSet constraintSet) {
        this.mEndMotionPath.time = 1.0f;
        this.mEndMotionPath.position = 1.0f;
        this.readView(this.mEndMotionPath);
        this.mEndMotionPath.setBounds(constraintWidget.getX(), constraintWidget.getY(), constraintWidget.getWidth(), constraintWidget.getHeight());
        this.mEndMotionPath.applyParameters(constraintSet.getParameters(this.mId));
        this.mEndPoint.setState(constraintWidget, constraintSet, this.mId);
    }

    public void setPathMotionArc(int n) {
        this.mPathMotionArc = n;
    }

    void setStartCurrentState(View view) {
        this.mStartMotionPath.time = 0.0f;
        this.mStartMotionPath.position = 0.0f;
        this.mStartMotionPath.setBounds(view.getX(), view.getY(), view.getWidth(), view.getHeight());
        this.mStartPoint.setState(view);
    }

    void setStartState(ConstraintWidget constraintWidget, ConstraintSet constraintSet) {
        this.mStartMotionPath.time = 0.0f;
        this.mStartMotionPath.position = 0.0f;
        this.readView(this.mStartMotionPath);
        this.mStartMotionPath.setBounds(constraintWidget.getX(), constraintWidget.getY(), constraintWidget.getWidth(), constraintWidget.getHeight());
        ConstraintSet.Constraint constraint = constraintSet.getParameters(this.mId);
        this.mStartMotionPath.applyParameters(constraint);
        this.mMotionStagger = constraint.motion.mMotionStagger;
        this.mStartPoint.setState(constraintWidget, constraintSet, this.mId);
    }

    public void setView(View view) {
        this.mView = view;
        this.mId = view.getId();
        if (!((view = view.getLayoutParams()) instanceof ConstraintLayout.LayoutParams)) return;
        this.mConstraintTag = ((ConstraintLayout.LayoutParams)view).getConstraintTag();
    }

    /*
     * WARNING - void declaration
     */
    public void setup(int n, int n2, float f, long l) {
        int[] arrn;
        Object object;
        HashSet<String> hashSet;
        Object object2;
        HashSet<String> object72;
        int n3;
        Object object5;
        Object object3;
        double[] arrd;
        int n4;
        Object object4;
        block52 : {
            Object object6;
            block47 : {
                new HashSet();
                object5 = new HashSet();
                object72 = new HashSet<String>();
                hashSet = new HashSet<String>();
                object4 = new HashMap<String, Integer>();
                if (this.mPathMotionArc != Key.UNSET) {
                    this.mStartMotionPath.mPathMotionArc = this.mPathMotionArc;
                }
                this.mStartPoint.different(this.mEndPoint, object72);
                ArrayList<Key> arrayList = this.mKeyList;
                if (arrayList == null) {
                    object3 = null;
                } else {
                    arrd = arrayList.iterator();
                    Object var10_22 = null;
                    do {
                        void var10_23;
                        object3 = var10_23;
                        if (!arrd.hasNext()) break;
                        object2 = arrd.next();
                        if (object2 instanceof KeyPosition) {
                            object3 = (KeyPosition)object2;
                            this.insertKey(new MotionPaths(n, n2, (KeyPosition)object3, this.mStartMotionPath, this.mEndMotionPath));
                            if (((KeyPosition)object3).mCurveFit == Key.UNSET) continue;
                            this.mCurveFitType = ((KeyPosition)object3).mCurveFit;
                            continue;
                        }
                        if (object2 instanceof KeyCycle) {
                            object2.getAttributeNames(hashSet);
                            continue;
                        }
                        if (object2 instanceof KeyTimeCycle) {
                            object2.getAttributeNames((HashSet<String>)object5);
                            continue;
                        }
                        if (object2 instanceof KeyTrigger) {
                            object3 = var10_23;
                            if (var10_23 == null) {
                                object3 = new ArrayList();
                            }
                            ((ArrayList)object3).add((KeyTrigger)((KeyTrigger)object2));
                            Iterator<String> iterator2 = object3;
                            continue;
                        }
                        object2.setInterpolation((HashMap<String, Integer>)object4);
                        object2.getAttributeNames(object72);
                    } while (true);
                }
                if (object3 != null) {
                    this.mKeyTriggers = ((ArrayList)object3).toArray(new KeyTrigger[0]);
                }
                if (object72.isEmpty()) break block47;
                this.mAttributesMap = new HashMap();
                arrd = object72.iterator();
                do {
                    void var10_31;
                    block51 : {
                        Iterator<Key> iterator3;
                        block50 : {
                            block48 : {
                                block49 : {
                                    if (!arrd.hasNext()) break block48;
                                    object3 = (String)arrd.next();
                                    if (!((String)object3).startsWith("CUSTOM,")) break block49;
                                    object = new SparseArray();
                                    object2 = ((String)object3).split(",")[1];
                                    iterator3 = this.mKeyList.iterator();
                                    break block50;
                                }
                                SplineSet splineSet = SplineSet.makeSpline(object3);
                                break block51;
                            }
                            ArrayList<Key> arrayList2 = this.mKeyList;
                            if (arrayList2 != null) {
                                for (Key key : arrayList2) {
                                    if (!(key instanceof KeyAttributes)) continue;
                                    key.addValues(this.mAttributesMap);
                                }
                            }
                            this.mStartPoint.addValues(this.mAttributesMap, 0);
                            this.mEndPoint.addValues(this.mAttributesMap, 100);
                            object3 = this.mAttributesMap.keySet().iterator();
                            break;
                        }
                        while (iterator3.hasNext()) {
                            ConstraintAttribute constraintAttribute;
                            object6 = iterator3.next();
                            if (object6.mCustomConstraints == null || (constraintAttribute = object6.mCustomConstraints.get(object2)) == null) continue;
                            object.append(object6.mFramePosition, (Object)constraintAttribute);
                        }
                        SplineSet splineSet = SplineSet.makeCustomSpline(object3, (SparseArray<ConstraintAttribute>)object);
                    }
                    if (var10_31 == null) continue;
                    var10_31.setType((String)object3);
                    this.mAttributesMap.put((String)object3, (SplineSet)var10_31);
                } while (true);
                while (object3.hasNext()) {
                    String string2 = object3.next();
                    n = ((HashMap)object4).containsKey(string2) ? ((HashMap)object4).get(string2) : 0;
                    this.mAttributesMap.get(string2).setup(n);
                }
            }
            if (((HashSet)object5).isEmpty()) break block52;
            if (this.mTimeCycleAttributesMap == null) {
                this.mTimeCycleAttributesMap = new HashMap();
            }
            object3 = ((HashSet)object5).iterator();
            do {
                void var10_42;
                block56 : {
                    String string3;
                    block55 : {
                        block53 : {
                            block54 : {
                                if (!object3.hasNext()) break block53;
                                object5 = (String)object3.next();
                                if (this.mTimeCycleAttributesMap.containsKey(object5)) continue;
                                if (!((String)object5).startsWith("CUSTOM,")) break block54;
                                object6 = new SparseArray();
                                string3 = ((String)object5).split(",")[1];
                                object = this.mKeyList.iterator();
                                break block55;
                            }
                            TimeCycleSplineSet timeCycleSplineSet = TimeCycleSplineSet.makeSpline((String)object5, l);
                            break block56;
                        }
                        ArrayList<Key> arrayList = this.mKeyList;
                        if (arrayList != null) {
                            for (Key key : arrayList) {
                                if (!(key instanceof KeyTimeCycle)) continue;
                                ((KeyTimeCycle)key).addTimeValues(this.mTimeCycleAttributesMap);
                            }
                        }
                        object3 = this.mTimeCycleAttributesMap.keySet().iterator();
                        break;
                    }
                    while (object.hasNext()) {
                        object2 = (Key)object.next();
                        if (object2.mCustomConstraints == null || (arrd = object2.mCustomConstraints.get(string3)) == null) continue;
                        object6.append(object2.mFramePosition, (Object)arrd);
                    }
                    TimeCycleSplineSet timeCycleSplineSet = TimeCycleSplineSet.makeCustomSpline((String)object5, (SparseArray<ConstraintAttribute>)object6);
                }
                if (var10_42 == null) continue;
                var10_42.setType((String)object5);
                this.mTimeCycleAttributesMap.put((String)object5, (TimeCycleSplineSet)var10_42);
            } while (true);
            while (object3.hasNext()) {
                String string4 = (String)object3.next();
                n = ((HashMap)object4).containsKey(string4) ? ((HashMap)object4).get(string4) : 0;
                this.mTimeCycleAttributesMap.get(string4).setup(n);
            }
        }
        int n5 = this.mMotionPaths.size() + 2;
        object5 = new MotionPaths[n5];
        object5[0] = this.mStartMotionPath;
        object5[n5 - 1] = this.mEndMotionPath;
        if (this.mMotionPaths.size() > 0 && this.mCurveFitType == -1) {
            this.mCurveFitType = 0;
        }
        Iterator<MotionPaths> iterator4 = this.mMotionPaths.iterator();
        n = 1;
        while (iterator4.hasNext()) {
            object5[n] = iterator4.next();
            ++n;
        }
        arrd = new HashSet();
        for (String string5 : this.mEndMotionPath.attributes.keySet()) {
            if (!this.mStartMotionPath.attributes.containsKey(string5)) continue;
            object3 = new StringBuilder();
            ((StringBuilder)object3).append("CUSTOM,");
            ((StringBuilder)object3).append(string5);
            if (object72.contains(((StringBuilder)object3).toString())) continue;
            arrd.add(string5);
        }
        String[] arrstring = arrd.toArray(new String[0]);
        this.mAttributeNames = arrstring;
        this.mAttributeInterpCount = new int[arrstring.length];
        n = 0;
        do {
            String string6;
            String[] arrstring2;
            if (n < (arrstring2 = this.mAttributeNames).length) {
                string6 = arrstring2[n];
                this.mAttributeInterpCount[n] = 0;
            } else {
                boolean bl = ((MotionPaths)object5[0]).mPathMotionArc != Key.UNSET;
                n3 = 18 + this.mAttributeNames.length;
                object3 = new boolean[n3];
                for (n = 1; n < n5; ++n) {
                    ((MotionPaths)object5[n]).different((MotionPaths)object5[n - 1], (boolean[])object3, this.mAttributeNames, bl);
                }
                n4 = 0;
                for (n = 1; n < n3; ++n) {
                    n2 = n4;
                    if (object3[n]) {
                        n2 = n4 + 1;
                    }
                    n4 = n2;
                }
                int[] arrn2 = new int[n4];
                this.mInterpolateVariables = arrn2;
                this.mInterpolateData = new double[arrn2.length];
                this.mInterpolateVelocity = new double[arrn2.length];
                n2 = 0;
                for (n = 1; n < n3; ++n) {
                    n4 = n2;
                    if (object3[n]) {
                        this.mInterpolateVariables[n2] = n;
                        n4 = n2 + 1;
                    }
                    n2 = n4;
                }
                object2 = new double[n5][this.mInterpolateVariables.length];
                arrd = new double[n5];
                for (n = 0; n < n5; ++n) {
                    ((MotionPaths)object5[n]).fillStandard(object2[n], this.mInterpolateVariables);
                    arrd[n] = ((MotionPaths)object5[n]).time;
                }
                break;
            }
            for (n2 = 0; n2 < n5; ++n2) {
                if (!((MotionPaths)object5[n2]).attributes.containsKey(string6)) continue;
                object3 = this.mAttributeInterpCount;
                object3[n] = object3[n] + ((MotionPaths)object5[n2]).attributes.get(string6).noOfInterpValues();
                break;
            }
            ++n;
        } while (true);
        for (n = 0; n < (arrn = this.mInterpolateVariables).length; ++n) {
            if (arrn[n] >= MotionPaths.names.length) continue;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(MotionPaths.names[this.mInterpolateVariables[n]]);
            stringBuilder.append(" [");
            String string7 = stringBuilder.toString();
            for (n2 = 0; n2 < n5; ++n2) {
                void var10_61;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append((String)var10_61);
                ((StringBuilder)object3).append(object2[n2][n]);
                String string8 = ((StringBuilder)object3).toString();
            }
        }
        this.mSpline = new CurveFit[this.mAttributeNames.length + 1];
        n = 0;
        while (n < ((boolean[])(object3 = this.mAttributeNames)).length) {
            void var10_66;
            double[][] arrd2 = null;
            object = object3[n];
            object3 = null;
            n4 = 0;
            for (n3 = 0; n3 < n5; ++n3) {
                void var7_13;
                void var7_9 = var10_66;
                object4 = object3;
                n2 = n4;
                if (((MotionPaths)object5[n3]).hasCustomData((String)object)) {
                    void var7_12;
                    void var7_10 = var10_66;
                    if (var10_66 == null) {
                        object3 = new double[n5];
                        double[][] arrd3 = new double[n5][((MotionPaths)object5[n3]).getCustomDataCount((String)object)];
                    }
                    object3[n4] = (double)((MotionPaths)object5[n3]).time;
                    ((MotionPaths)object5[n3]).getCustomData((String)object, (double[])var7_12[n4], 0);
                    n2 = n4 + 1;
                    object4 = object3;
                }
                void var10_67 = var7_13;
                object3 = object4;
                n4 = n2;
            }
            object3 = Arrays.copyOf((double[])object3, n4);
            double[][] arrd4 = (double[][])Arrays.copyOf(var10_66, n4);
            CurveFit[] arrcurveFit = this.mSpline;
            arrcurveFit[++n] = CurveFit.get(this.mCurveFitType, (double[])object3, arrd4);
        }
        this.mSpline[0] = CurveFit.get(this.mCurveFitType, arrd, object2);
        if (((MotionPaths)object5[0]).mPathMotionArc != Key.UNSET) {
            int[] arrn3 = new int[n5];
            double[] arrd5 = new double[n5];
            object3 = new double[n5][2];
            for (n = 0; n < n5; ++n) {
                arrn3[n] = ((MotionPaths)object5[n]).mPathMotionArc;
                arrd5[n] = ((MotionPaths)object5[n]).time;
                object3[n][0] = (double)((MotionPaths)object5[n]).x;
                object3[n][1] = (double)((MotionPaths)object5[n]).y;
            }
            this.mArcSpline = CurveFit.getArc(arrn3, arrd5, (double[][])object3);
        }
        float f2 = Float.NaN;
        this.mCycleMap = new HashMap();
        if (this.mKeyList == null) return;
        for (String string9 : hashSet) {
            KeyCycleOscillator keyCycleOscillator = KeyCycleOscillator.makeSpline(string9);
            if (keyCycleOscillator == null) continue;
            f = f2;
            if (keyCycleOscillator.variesByPath()) {
                f = f2;
                if (Float.isNaN(f2)) {
                    f = this.getPreCycleDistance();
                }
            }
            keyCycleOscillator.setType(string9);
            this.mCycleMap.put(string9, keyCycleOscillator);
            f2 = f;
        }
        for (Key key : this.mKeyList) {
            if (!(key instanceof KeyCycle)) continue;
            ((KeyCycle)key).addCycleValues(this.mCycleMap);
        }
        Iterator<KeyCycleOscillator> iterator5 = this.mCycleMap.values().iterator();
        while (iterator5.hasNext()) {
            iterator5.next().setup(f2);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" start: x: ");
        stringBuilder.append(this.mStartMotionPath.x);
        stringBuilder.append(" y: ");
        stringBuilder.append(this.mStartMotionPath.y);
        stringBuilder.append(" end: x: ");
        stringBuilder.append(this.mEndMotionPath.x);
        stringBuilder.append(" y: ");
        stringBuilder.append(this.mEndMotionPath.y);
        return stringBuilder.toString();
    }
}

