/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseIntArray
 */
package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.TypedValue;
import androidx.constraintlayout.motion.utils.CurveFit;
import androidx.constraintlayout.motion.widget.Key;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.SplineSet;
import androidx.constraintlayout.motion.widget.TimeCycleSplineSet;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.R;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class KeyTimeCycle
extends Key {
    public static final int KEY_TYPE = 3;
    static final String NAME = "KeyTimeCycle";
    private static final String TAG = "KeyTimeCycle";
    private float mAlpha = Float.NaN;
    private int mCurveFit = -1;
    private float mElevation = Float.NaN;
    private float mProgress = Float.NaN;
    private float mRotation = Float.NaN;
    private float mRotationX = Float.NaN;
    private float mRotationY = Float.NaN;
    private float mScaleX = Float.NaN;
    private float mScaleY = Float.NaN;
    private String mTransitionEasing;
    private float mTransitionPathRotate = Float.NaN;
    private float mTranslationX = Float.NaN;
    private float mTranslationY = Float.NaN;
    private float mTranslationZ = Float.NaN;
    private float mWaveOffset = 0.0f;
    private CurveFit mWaveOffsetSpline;
    private float mWavePeriod = Float.NaN;
    private CurveFit mWavePeriodSpline;
    private int mWaveShape = 0;

    public KeyTimeCycle() {
        this.mType = 3;
        this.mCustomConstraints = new HashMap();
    }

    public void addTimeValues(HashMap<String, TimeCycleSplineSet> hashMap) {
        Iterator<String> iterator2 = hashMap.keySet().iterator();
        block28 : while (iterator2.hasNext()) {
            Object object;
            int n;
            Object object2;
            block30 : {
                object2 = iterator2.next();
                object = hashMap.get(object2);
                boolean bl = ((String)object2).startsWith("CUSTOM");
                n = 7;
                if (bl) {
                    object2 = ((String)object2).substring(7);
                    if ((object2 = (ConstraintAttribute)this.mCustomConstraints.get(object2)) == null) continue;
                    ((TimeCycleSplineSet.CustomSet)object).setPoint(this.mFramePosition, (ConstraintAttribute)object2, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
                    continue;
                }
                switch (((String)object2).hashCode()) {
                    default: {
                        break;
                    }
                    case 92909918: {
                        if (!((String)object2).equals("alpha")) break;
                        n = 0;
                        break block30;
                    }
                    case 37232917: {
                        if (!((String)object2).equals("transitionPathRotate")) break;
                        n = 5;
                        break block30;
                    }
                    case -4379043: {
                        if (!((String)object2).equals("elevation")) break;
                        n = 1;
                        break block30;
                    }
                    case -40300674: {
                        if (!((String)object2).equals("rotation")) break;
                        n = 2;
                        break block30;
                    }
                    case -908189617: {
                        if (!((String)object2).equals("scaleY")) break;
                        break block30;
                    }
                    case -908189618: {
                        if (!((String)object2).equals("scaleX")) break;
                        n = 6;
                        break block30;
                    }
                    case -1001078227: {
                        if (!((String)object2).equals("progress")) break;
                        n = 11;
                        break block30;
                    }
                    case -1225497655: {
                        if (!((String)object2).equals("translationZ")) break;
                        n = 10;
                        break block30;
                    }
                    case -1225497656: {
                        if (!((String)object2).equals("translationY")) break;
                        n = 9;
                        break block30;
                    }
                    case -1225497657: {
                        if (!((String)object2).equals("translationX")) break;
                        n = 8;
                        break block30;
                    }
                    case -1249320805: {
                        if (!((String)object2).equals("rotationY")) break;
                        n = 4;
                        break block30;
                    }
                    case -1249320806: {
                        if (!((String)object2).equals("rotationX")) break;
                        n = 3;
                        break block30;
                    }
                }
                n = -1;
            }
            switch (n) {
                default: {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("UNKNOWN addValues \"");
                    ((StringBuilder)object).append((String)object2);
                    ((StringBuilder)object).append("\"");
                    Log.e((String)"KeyTimeCycles", (String)((StringBuilder)object).toString());
                    continue block28;
                }
                case 11: {
                    if (Float.isNaN(this.mProgress)) continue block28;
                    ((TimeCycleSplineSet)object).setPoint(this.mFramePosition, this.mProgress, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
                    continue block28;
                }
                case 10: {
                    if (Float.isNaN(this.mTranslationZ)) continue block28;
                    ((TimeCycleSplineSet)object).setPoint(this.mFramePosition, this.mTranslationZ, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
                    continue block28;
                }
                case 9: {
                    if (Float.isNaN(this.mTranslationY)) continue block28;
                    ((TimeCycleSplineSet)object).setPoint(this.mFramePosition, this.mTranslationY, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
                    continue block28;
                }
                case 8: {
                    if (Float.isNaN(this.mTranslationX)) continue block28;
                    ((TimeCycleSplineSet)object).setPoint(this.mFramePosition, this.mTranslationX, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
                    continue block28;
                }
                case 7: {
                    if (Float.isNaN(this.mScaleY)) continue block28;
                    ((TimeCycleSplineSet)object).setPoint(this.mFramePosition, this.mScaleY, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
                    continue block28;
                }
                case 6: {
                    if (Float.isNaN(this.mScaleX)) continue block28;
                    ((TimeCycleSplineSet)object).setPoint(this.mFramePosition, this.mScaleX, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
                    continue block28;
                }
                case 5: {
                    if (Float.isNaN(this.mTransitionPathRotate)) continue block28;
                    ((TimeCycleSplineSet)object).setPoint(this.mFramePosition, this.mTransitionPathRotate, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
                    continue block28;
                }
                case 4: {
                    if (Float.isNaN(this.mRotationY)) continue block28;
                    ((TimeCycleSplineSet)object).setPoint(this.mFramePosition, this.mRotationY, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
                    continue block28;
                }
                case 3: {
                    if (Float.isNaN(this.mRotationX)) continue block28;
                    ((TimeCycleSplineSet)object).setPoint(this.mFramePosition, this.mRotationX, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
                    continue block28;
                }
                case 2: {
                    if (Float.isNaN(this.mRotation)) continue block28;
                    ((TimeCycleSplineSet)object).setPoint(this.mFramePosition, this.mRotation, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
                    continue block28;
                }
                case 1: {
                    if (Float.isNaN(this.mElevation)) continue block28;
                    ((TimeCycleSplineSet)object).setPoint(this.mFramePosition, this.mElevation, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
                    continue block28;
                }
                case 0: 
            }
            if (Float.isNaN(this.mAlpha)) continue;
            ((TimeCycleSplineSet)object).setPoint(this.mFramePosition, this.mAlpha, this.mWavePeriod, this.mWaveShape, this.mWaveOffset);
        }
    }

    @Override
    public void addValues(HashMap<String, SplineSet> hashMap) {
        throw new IllegalArgumentException(" KeyTimeCycles do not support SplineSet");
    }

    @Override
    public void getAttributeNames(HashSet<String> hashSet) {
        if (!Float.isNaN(this.mAlpha)) {
            hashSet.add("alpha");
        }
        if (!Float.isNaN(this.mElevation)) {
            hashSet.add("elevation");
        }
        if (!Float.isNaN(this.mRotation)) {
            hashSet.add("rotation");
        }
        if (!Float.isNaN(this.mRotationX)) {
            hashSet.add("rotationX");
        }
        if (!Float.isNaN(this.mRotationY)) {
            hashSet.add("rotationY");
        }
        if (!Float.isNaN(this.mTranslationX)) {
            hashSet.add("translationX");
        }
        if (!Float.isNaN(this.mTranslationY)) {
            hashSet.add("translationY");
        }
        if (!Float.isNaN(this.mTranslationZ)) {
            hashSet.add("translationZ");
        }
        if (!Float.isNaN(this.mTransitionPathRotate)) {
            hashSet.add("transitionPathRotate");
        }
        if (!Float.isNaN(this.mScaleX)) {
            hashSet.add("scaleX");
        }
        if (!Float.isNaN(this.mScaleY)) {
            hashSet.add("scaleY");
        }
        if (!Float.isNaN(this.mProgress)) {
            hashSet.add("progress");
        }
        if (this.mCustomConstraints.size() <= 0) return;
        Iterator iterator2 = this.mCustomConstraints.keySet().iterator();
        while (iterator2.hasNext()) {
            String string2 = (String)iterator2.next();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CUSTOM,");
            stringBuilder.append(string2);
            hashSet.add(stringBuilder.toString());
        }
    }

    int getCurveFit() {
        return this.mCurveFit;
    }

    @Override
    public void load(Context context, AttributeSet attributeSet) {
        Loader.read(this, context.obtainStyledAttributes(attributeSet, R.styleable.KeyTimeCycle));
    }

    @Override
    public void setInterpolation(HashMap<String, Integer> hashMap) {
        if (this.mCurveFit == -1) {
            return;
        }
        if (!Float.isNaN(this.mAlpha)) {
            hashMap.put("alpha", this.mCurveFit);
        }
        if (!Float.isNaN(this.mElevation)) {
            hashMap.put("elevation", this.mCurveFit);
        }
        if (!Float.isNaN(this.mRotation)) {
            hashMap.put("rotation", this.mCurveFit);
        }
        if (!Float.isNaN(this.mRotationX)) {
            hashMap.put("rotationX", this.mCurveFit);
        }
        if (!Float.isNaN(this.mRotationY)) {
            hashMap.put("rotationY", this.mCurveFit);
        }
        if (!Float.isNaN(this.mTranslationX)) {
            hashMap.put("translationX", this.mCurveFit);
        }
        if (!Float.isNaN(this.mTranslationY)) {
            hashMap.put("translationY", this.mCurveFit);
        }
        if (!Float.isNaN(this.mTranslationZ)) {
            hashMap.put("translationZ", this.mCurveFit);
        }
        if (!Float.isNaN(this.mTransitionPathRotate)) {
            hashMap.put("transitionPathRotate", this.mCurveFit);
        }
        if (!Float.isNaN(this.mScaleX)) {
            hashMap.put("scaleX", this.mCurveFit);
        }
        if (!Float.isNaN(this.mScaleX)) {
            hashMap.put("scaleY", this.mCurveFit);
        }
        if (!Float.isNaN(this.mProgress)) {
            hashMap.put("progress", this.mCurveFit);
        }
        if (this.mCustomConstraints.size() <= 0) return;
        Iterator iterator2 = this.mCustomConstraints.keySet().iterator();
        while (iterator2.hasNext()) {
            String string2 = (String)iterator2.next();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CUSTOM,");
            stringBuilder.append(string2);
            hashMap.put(stringBuilder.toString(), this.mCurveFit);
        }
    }

    @Override
    public void setValue(String string2, Object object) {
        int n;
        block32 : {
            switch (string2.hashCode()) {
                default: {
                    break;
                }
                case 1317633238: {
                    if (!string2.equals("mTranslationZ")) break;
                    n = 13;
                    break block32;
                }
                case 579057826: {
                    if (!string2.equals("curveFit")) break;
                    n = 1;
                    break block32;
                }
                case 92909918: {
                    if (!string2.equals("alpha")) break;
                    n = 0;
                    break block32;
                }
                case 37232917: {
                    if (!string2.equals("transitionPathRotate")) break;
                    n = 10;
                    break block32;
                }
                case -4379043: {
                    if (!string2.equals("elevation")) break;
                    n = 2;
                    break block32;
                }
                case -40300674: {
                    if (!string2.equals("rotation")) break;
                    n = 4;
                    break block32;
                }
                case -908189617: {
                    if (!string2.equals("scaleY")) break;
                    n = 8;
                    break block32;
                }
                case -908189618: {
                    if (!string2.equals("scaleX")) break;
                    n = 7;
                    break block32;
                }
                case -1001078227: {
                    if (!string2.equals("progress")) break;
                    n = 3;
                    break block32;
                }
                case -1225497656: {
                    if (!string2.equals("translationY")) break;
                    n = 12;
                    break block32;
                }
                case -1225497657: {
                    if (!string2.equals("translationX")) break;
                    n = 11;
                    break block32;
                }
                case -1249320805: {
                    if (!string2.equals("rotationY")) break;
                    n = 6;
                    break block32;
                }
                case -1249320806: {
                    if (!string2.equals("rotationX")) break;
                    n = 5;
                    break block32;
                }
                case -1812823328: {
                    if (!string2.equals("transitionEasing")) break;
                    n = 9;
                    break block32;
                }
            }
            n = -1;
        }
        switch (n) {
            default: {
                return;
            }
            case 13: {
                this.mTranslationZ = this.toFloat(object);
                return;
            }
            case 12: {
                this.mTranslationY = this.toFloat(object);
                return;
            }
            case 11: {
                this.mTranslationX = this.toFloat(object);
                return;
            }
            case 10: {
                this.mTransitionPathRotate = this.toFloat(object);
                return;
            }
            case 9: {
                this.mTransitionEasing = object.toString();
                return;
            }
            case 8: {
                this.mScaleY = this.toFloat(object);
                return;
            }
            case 7: {
                this.mScaleX = this.toFloat(object);
                return;
            }
            case 6: {
                this.mRotationY = this.toFloat(object);
                return;
            }
            case 5: {
                this.mRotationX = this.toFloat(object);
                return;
            }
            case 4: {
                this.mRotation = this.toFloat(object);
                return;
            }
            case 3: {
                this.mProgress = this.toFloat(object);
                return;
            }
            case 2: {
                this.mElevation = this.toFloat(object);
                return;
            }
            case 1: {
                this.mCurveFit = this.toInt(object);
                return;
            }
            case 0: 
        }
        this.mAlpha = this.toFloat(object);
    }

    private static class Loader {
        private static final int ANDROID_ALPHA = 1;
        private static final int ANDROID_ELEVATION = 2;
        private static final int ANDROID_ROTATION = 4;
        private static final int ANDROID_ROTATION_X = 5;
        private static final int ANDROID_ROTATION_Y = 6;
        private static final int ANDROID_SCALE_X = 7;
        private static final int ANDROID_SCALE_Y = 14;
        private static final int ANDROID_TRANSLATION_X = 15;
        private static final int ANDROID_TRANSLATION_Y = 16;
        private static final int ANDROID_TRANSLATION_Z = 17;
        private static final int CURVE_FIT = 13;
        private static final int FRAME_POSITION = 12;
        private static final int PROGRESS = 18;
        private static final int TARGET_ID = 10;
        private static final int TRANSITION_EASING = 9;
        private static final int TRANSITION_PATH_ROTATE = 8;
        private static final int WAVE_OFFSET = 21;
        private static final int WAVE_PERIOD = 20;
        private static final int WAVE_SHAPE = 19;
        private static SparseIntArray mAttrMap;

        static {
            SparseIntArray sparseIntArray;
            mAttrMap = sparseIntArray = new SparseIntArray();
            sparseIntArray.append(R.styleable.KeyTimeCycle_android_alpha, 1);
            mAttrMap.append(R.styleable.KeyTimeCycle_android_elevation, 2);
            mAttrMap.append(R.styleable.KeyTimeCycle_android_rotation, 4);
            mAttrMap.append(R.styleable.KeyTimeCycle_android_rotationX, 5);
            mAttrMap.append(R.styleable.KeyTimeCycle_android_rotationY, 6);
            mAttrMap.append(R.styleable.KeyTimeCycle_android_scaleX, 7);
            mAttrMap.append(R.styleable.KeyTimeCycle_transitionPathRotate, 8);
            mAttrMap.append(R.styleable.KeyTimeCycle_transitionEasing, 9);
            mAttrMap.append(R.styleable.KeyTimeCycle_motionTarget, 10);
            mAttrMap.append(R.styleable.KeyTimeCycle_framePosition, 12);
            mAttrMap.append(R.styleable.KeyTimeCycle_curveFit, 13);
            mAttrMap.append(R.styleable.KeyTimeCycle_android_scaleY, 14);
            mAttrMap.append(R.styleable.KeyTimeCycle_android_translationX, 15);
            mAttrMap.append(R.styleable.KeyTimeCycle_android_translationY, 16);
            mAttrMap.append(R.styleable.KeyTimeCycle_android_translationZ, 17);
            mAttrMap.append(R.styleable.KeyTimeCycle_motionProgress, 18);
            mAttrMap.append(R.styleable.KeyTimeCycle_wavePeriod, 20);
            mAttrMap.append(R.styleable.KeyTimeCycle_waveOffset, 21);
            mAttrMap.append(R.styleable.KeyTimeCycle_waveShape, 19);
        }

        private Loader() {
        }

        public static void read(KeyTimeCycle keyTimeCycle, TypedArray typedArray) {
            int n = typedArray.getIndexCount();
            int n2 = 0;
            while (n2 < n) {
                int n3 = typedArray.getIndex(n2);
                switch (mAttrMap.get(n3)) {
                    default: {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("unused attribute 0x");
                        stringBuilder.append(Integer.toHexString(n3));
                        stringBuilder.append("   ");
                        stringBuilder.append(mAttrMap.get(n3));
                        Log.e((String)"KeyTimeCycle", (String)stringBuilder.toString());
                        break;
                    }
                    case 21: {
                        if (typedArray.peekValue((int)n3).type == 5) {
                            keyTimeCycle.mWaveOffset = typedArray.getDimension(n3, keyTimeCycle.mWaveOffset);
                            break;
                        }
                        keyTimeCycle.mWaveOffset = typedArray.getFloat(n3, keyTimeCycle.mWaveOffset);
                        break;
                    }
                    case 20: {
                        keyTimeCycle.mWavePeriod = typedArray.getFloat(n3, keyTimeCycle.mWavePeriod);
                        break;
                    }
                    case 19: {
                        keyTimeCycle.mWaveShape = typedArray.getInt(n3, keyTimeCycle.mWaveShape);
                        break;
                    }
                    case 18: {
                        keyTimeCycle.mProgress = typedArray.getFloat(n3, keyTimeCycle.mProgress);
                        break;
                    }
                    case 17: {
                        if (Build.VERSION.SDK_INT < 21) break;
                        keyTimeCycle.mTranslationZ = typedArray.getDimension(n3, keyTimeCycle.mTranslationZ);
                        break;
                    }
                    case 16: {
                        keyTimeCycle.mTranslationY = typedArray.getDimension(n3, keyTimeCycle.mTranslationY);
                        break;
                    }
                    case 15: {
                        keyTimeCycle.mTranslationX = typedArray.getDimension(n3, keyTimeCycle.mTranslationX);
                        break;
                    }
                    case 14: {
                        keyTimeCycle.mScaleY = typedArray.getFloat(n3, keyTimeCycle.mScaleY);
                        break;
                    }
                    case 13: {
                        keyTimeCycle.mCurveFit = typedArray.getInteger(n3, keyTimeCycle.mCurveFit);
                        break;
                    }
                    case 12: {
                        keyTimeCycle.mFramePosition = typedArray.getInt(n3, keyTimeCycle.mFramePosition);
                        break;
                    }
                    case 10: {
                        if (MotionLayout.IS_IN_EDIT_MODE) {
                            keyTimeCycle.mTargetId = typedArray.getResourceId(n3, keyTimeCycle.mTargetId);
                            if (keyTimeCycle.mTargetId != -1) break;
                            keyTimeCycle.mTargetString = typedArray.getString(n3);
                            break;
                        }
                        if (typedArray.peekValue((int)n3).type == 3) {
                            keyTimeCycle.mTargetString = typedArray.getString(n3);
                            break;
                        }
                        keyTimeCycle.mTargetId = typedArray.getResourceId(n3, keyTimeCycle.mTargetId);
                        break;
                    }
                    case 9: {
                        keyTimeCycle.mTransitionEasing = typedArray.getString(n3);
                        break;
                    }
                    case 8: {
                        keyTimeCycle.mTransitionPathRotate = typedArray.getFloat(n3, keyTimeCycle.mTransitionPathRotate);
                        break;
                    }
                    case 7: {
                        keyTimeCycle.mScaleX = typedArray.getFloat(n3, keyTimeCycle.mScaleX);
                        break;
                    }
                    case 6: {
                        keyTimeCycle.mRotationY = typedArray.getFloat(n3, keyTimeCycle.mRotationY);
                        break;
                    }
                    case 5: {
                        keyTimeCycle.mRotationX = typedArray.getFloat(n3, keyTimeCycle.mRotationX);
                        break;
                    }
                    case 4: {
                        keyTimeCycle.mRotation = typedArray.getFloat(n3, keyTimeCycle.mRotation);
                        break;
                    }
                    case 2: {
                        keyTimeCycle.mElevation = typedArray.getDimension(n3, keyTimeCycle.mElevation);
                        break;
                    }
                    case 1: {
                        keyTimeCycle.mAlpha = typedArray.getFloat(n3, keyTimeCycle.mAlpha);
                    }
                }
                ++n2;
            }
        }
    }

}

