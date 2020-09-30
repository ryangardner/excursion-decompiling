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
import androidx.constraintlayout.motion.widget.Debug;
import androidx.constraintlayout.motion.widget.Key;
import androidx.constraintlayout.motion.widget.KeyCycleOscillator;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.SplineSet;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.R;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class KeyCycle
extends Key {
    public static final int KEY_TYPE = 4;
    static final String NAME = "KeyCycle";
    private static final String TAG = "KeyCycle";
    private float mAlpha = Float.NaN;
    private int mCurveFit = 0;
    private float mElevation = Float.NaN;
    private float mProgress = Float.NaN;
    private float mRotation = Float.NaN;
    private float mRotationX = Float.NaN;
    private float mRotationY = Float.NaN;
    private float mScaleX = Float.NaN;
    private float mScaleY = Float.NaN;
    private String mTransitionEasing = null;
    private float mTransitionPathRotate = Float.NaN;
    private float mTranslationX = Float.NaN;
    private float mTranslationY = Float.NaN;
    private float mTranslationZ = Float.NaN;
    private float mWaveOffset = 0.0f;
    private float mWavePeriod = Float.NaN;
    private int mWaveShape = -1;
    private int mWaveVariesBy = -1;

    public KeyCycle() {
        this.mType = 4;
        this.mCustomConstraints = new HashMap();
    }

    public void addCycleValues(HashMap<String, KeyCycleOscillator> hashMap) {
        Iterator<String> iterator2 = hashMap.keySet().iterator();
        while (iterator2.hasNext()) {
            float f;
            String string2 = iterator2.next();
            if (string2.startsWith("CUSTOM")) {
                Object object = string2.substring(7);
                if ((object = (ConstraintAttribute)this.mCustomConstraints.get(object)) != null && ((ConstraintAttribute)object).getType() == ConstraintAttribute.AttributeType.FLOAT_TYPE) {
                    hashMap.get(string2).setPoint(this.mFramePosition, this.mWaveShape, this.mWaveVariesBy, this.mWavePeriod, this.mWaveOffset, ((ConstraintAttribute)object).getValueToInterpolate(), (ConstraintAttribute)object);
                }
            }
            if (Float.isNaN(f = this.getValue(string2))) continue;
            hashMap.get(string2).setPoint(this.mFramePosition, this.mWaveShape, this.mWaveVariesBy, this.mWavePeriod, this.mWaveOffset, f);
        }
    }

    @Override
    public void addValues(HashMap<String, SplineSet> hashMap) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("add ");
        ((StringBuilder)object).append(hashMap.size());
        ((StringBuilder)object).append(" values");
        Debug.logStack("KeyCycle", ((StringBuilder)object).toString(), 2);
        object = hashMap.keySet().iterator();
        block30 : while (object.hasNext()) {
            String string2 = (String)object.next();
            Object object2 = hashMap.get(string2);
            int n = -1;
            switch (string2.hashCode()) {
                default: {
                    break;
                }
                case 156108012: {
                    if (!string2.equals("waveOffset")) break;
                    n = 11;
                    break;
                }
                case 92909918: {
                    if (!string2.equals("alpha")) break;
                    n = 0;
                    break;
                }
                case 37232917: {
                    if (!string2.equals("transitionPathRotate")) break;
                    n = 5;
                    break;
                }
                case -4379043: {
                    if (!string2.equals("elevation")) break;
                    n = 1;
                    break;
                }
                case -40300674: {
                    if (!string2.equals("rotation")) break;
                    n = 2;
                    break;
                }
                case -908189617: {
                    if (!string2.equals("scaleY")) break;
                    n = 7;
                    break;
                }
                case -908189618: {
                    if (!string2.equals("scaleX")) break;
                    n = 6;
                    break;
                }
                case -1001078227: {
                    if (!string2.equals("progress")) break;
                    n = 12;
                    break;
                }
                case -1225497655: {
                    if (!string2.equals("translationZ")) break;
                    n = 10;
                    break;
                }
                case -1225497656: {
                    if (!string2.equals("translationY")) break;
                    n = 9;
                    break;
                }
                case -1225497657: {
                    if (!string2.equals("translationX")) break;
                    n = 8;
                    break;
                }
                case -1249320805: {
                    if (!string2.equals("rotationY")) break;
                    n = 4;
                    break;
                }
                case -1249320806: {
                    if (!string2.equals("rotationX")) break;
                    n = 3;
                }
            }
            switch (n) {
                default: {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("  UNKNOWN  ");
                    ((StringBuilder)object2).append(string2);
                    Log.v((String)"WARNING KeyCycle", (String)((StringBuilder)object2).toString());
                    continue block30;
                }
                case 12: {
                    ((SplineSet)object2).setPoint(this.mFramePosition, this.mProgress);
                    continue block30;
                }
                case 11: {
                    ((SplineSet)object2).setPoint(this.mFramePosition, this.mWaveOffset);
                    continue block30;
                }
                case 10: {
                    ((SplineSet)object2).setPoint(this.mFramePosition, this.mTranslationZ);
                    continue block30;
                }
                case 9: {
                    ((SplineSet)object2).setPoint(this.mFramePosition, this.mTranslationY);
                    continue block30;
                }
                case 8: {
                    ((SplineSet)object2).setPoint(this.mFramePosition, this.mTranslationX);
                    continue block30;
                }
                case 7: {
                    ((SplineSet)object2).setPoint(this.mFramePosition, this.mScaleY);
                    continue block30;
                }
                case 6: {
                    ((SplineSet)object2).setPoint(this.mFramePosition, this.mScaleX);
                    continue block30;
                }
                case 5: {
                    ((SplineSet)object2).setPoint(this.mFramePosition, this.mTransitionPathRotate);
                    continue block30;
                }
                case 4: {
                    ((SplineSet)object2).setPoint(this.mFramePosition, this.mRotationY);
                    continue block30;
                }
                case 3: {
                    ((SplineSet)object2).setPoint(this.mFramePosition, this.mRotationX);
                    continue block30;
                }
                case 2: {
                    ((SplineSet)object2).setPoint(this.mFramePosition, this.mRotation);
                    continue block30;
                }
                case 1: {
                    ((SplineSet)object2).setPoint(this.mFramePosition, this.mElevation);
                    continue block30;
                }
                case 0: 
            }
            ((SplineSet)object2).setPoint(this.mFramePosition, this.mAlpha);
        }
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
        if (!Float.isNaN(this.mScaleX)) {
            hashSet.add("scaleX");
        }
        if (!Float.isNaN(this.mScaleY)) {
            hashSet.add("scaleY");
        }
        if (!Float.isNaN(this.mTransitionPathRotate)) {
            hashSet.add("transitionPathRotate");
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

    public float getValue(String string2) {
        int n;
        block30 : {
            switch (string2.hashCode()) {
                default: {
                    break;
                }
                case 156108012: {
                    if (!string2.equals("waveOffset")) break;
                    n = 11;
                    break block30;
                }
                case 92909918: {
                    if (!string2.equals("alpha")) break;
                    n = 0;
                    break block30;
                }
                case 37232917: {
                    if (!string2.equals("transitionPathRotate")) break;
                    n = 5;
                    break block30;
                }
                case -4379043: {
                    if (!string2.equals("elevation")) break;
                    n = 1;
                    break block30;
                }
                case -40300674: {
                    if (!string2.equals("rotation")) break;
                    n = 2;
                    break block30;
                }
                case -908189617: {
                    if (!string2.equals("scaleY")) break;
                    n = 7;
                    break block30;
                }
                case -908189618: {
                    if (!string2.equals("scaleX")) break;
                    n = 6;
                    break block30;
                }
                case -1001078227: {
                    if (!string2.equals("progress")) break;
                    n = 12;
                    break block30;
                }
                case -1225497655: {
                    if (!string2.equals("translationZ")) break;
                    n = 10;
                    break block30;
                }
                case -1225497656: {
                    if (!string2.equals("translationY")) break;
                    n = 9;
                    break block30;
                }
                case -1225497657: {
                    if (!string2.equals("translationX")) break;
                    n = 8;
                    break block30;
                }
                case -1249320805: {
                    if (!string2.equals("rotationY")) break;
                    n = 4;
                    break block30;
                }
                case -1249320806: {
                    if (!string2.equals("rotationX")) break;
                    n = 3;
                    break block30;
                }
            }
            n = -1;
        }
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("  UNKNOWN  ");
                stringBuilder.append(string2);
                Log.v((String)"WARNING! KeyCycle", (String)stringBuilder.toString());
                return Float.NaN;
            }
            case 12: {
                return this.mProgress;
            }
            case 11: {
                return this.mWaveOffset;
            }
            case 10: {
                return this.mTranslationZ;
            }
            case 9: {
                return this.mTranslationY;
            }
            case 8: {
                return this.mTranslationX;
            }
            case 7: {
                return this.mScaleY;
            }
            case 6: {
                return this.mScaleX;
            }
            case 5: {
                return this.mTransitionPathRotate;
            }
            case 4: {
                return this.mRotationY;
            }
            case 3: {
                return this.mRotationX;
            }
            case 2: {
                return this.mRotation;
            }
            case 1: {
                return this.mElevation;
            }
            case 0: 
        }
        return this.mAlpha;
    }

    @Override
    public void load(Context context, AttributeSet attributeSet) {
        Loader.read(this, context.obtainStyledAttributes(attributeSet, R.styleable.KeyCycle));
    }

    @Override
    public void setValue(String string2, Object object) {
        int n;
        block36 : {
            switch (string2.hashCode()) {
                default: {
                    break;
                }
                case 1317633238: {
                    if (!string2.equals("mTranslationZ")) break;
                    n = 13;
                    break block36;
                }
                case 579057826: {
                    if (!string2.equals("curveFit")) break;
                    n = 1;
                    break block36;
                }
                case 184161818: {
                    if (!string2.equals("wavePeriod")) break;
                    n = 14;
                    break block36;
                }
                case 156108012: {
                    if (!string2.equals("waveOffset")) break;
                    n = 15;
                    break block36;
                }
                case 92909918: {
                    if (!string2.equals("alpha")) break;
                    n = 0;
                    break block36;
                }
                case 37232917: {
                    if (!string2.equals("transitionPathRotate")) break;
                    n = 10;
                    break block36;
                }
                case -4379043: {
                    if (!string2.equals("elevation")) break;
                    n = 2;
                    break block36;
                }
                case -40300674: {
                    if (!string2.equals("rotation")) break;
                    n = 4;
                    break block36;
                }
                case -908189617: {
                    if (!string2.equals("scaleY")) break;
                    n = 8;
                    break block36;
                }
                case -908189618: {
                    if (!string2.equals("scaleX")) break;
                    n = 7;
                    break block36;
                }
                case -1001078227: {
                    if (!string2.equals("progress")) break;
                    n = 3;
                    break block36;
                }
                case -1225497656: {
                    if (!string2.equals("translationY")) break;
                    n = 12;
                    break block36;
                }
                case -1225497657: {
                    if (!string2.equals("translationX")) break;
                    n = 11;
                    break block36;
                }
                case -1249320805: {
                    if (!string2.equals("rotationY")) break;
                    n = 6;
                    break block36;
                }
                case -1249320806: {
                    if (!string2.equals("rotationX")) break;
                    n = 5;
                    break block36;
                }
                case -1812823328: {
                    if (!string2.equals("transitionEasing")) break;
                    n = 9;
                    break block36;
                }
            }
            n = -1;
        }
        switch (n) {
            default: {
                return;
            }
            case 15: {
                this.mWaveOffset = this.toFloat(object);
                return;
            }
            case 14: {
                this.mWavePeriod = this.toFloat(object);
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
        private static final int ANDROID_ALPHA = 9;
        private static final int ANDROID_ELEVATION = 10;
        private static final int ANDROID_ROTATION = 11;
        private static final int ANDROID_ROTATION_X = 12;
        private static final int ANDROID_ROTATION_Y = 13;
        private static final int ANDROID_SCALE_X = 15;
        private static final int ANDROID_SCALE_Y = 16;
        private static final int ANDROID_TRANSLATION_X = 17;
        private static final int ANDROID_TRANSLATION_Y = 18;
        private static final int ANDROID_TRANSLATION_Z = 19;
        private static final int CURVE_FIT = 4;
        private static final int FRAME_POSITION = 2;
        private static final int PROGRESS = 20;
        private static final int TARGET_ID = 1;
        private static final int TRANSITION_EASING = 3;
        private static final int TRANSITION_PATH_ROTATE = 14;
        private static final int WAVE_OFFSET = 7;
        private static final int WAVE_PERIOD = 6;
        private static final int WAVE_SHAPE = 5;
        private static final int WAVE_VARIES_BY = 8;
        private static SparseIntArray mAttrMap;

        static {
            SparseIntArray sparseIntArray;
            mAttrMap = sparseIntArray = new SparseIntArray();
            sparseIntArray.append(R.styleable.KeyCycle_motionTarget, 1);
            mAttrMap.append(R.styleable.KeyCycle_framePosition, 2);
            mAttrMap.append(R.styleable.KeyCycle_transitionEasing, 3);
            mAttrMap.append(R.styleable.KeyCycle_curveFit, 4);
            mAttrMap.append(R.styleable.KeyCycle_waveShape, 5);
            mAttrMap.append(R.styleable.KeyCycle_wavePeriod, 6);
            mAttrMap.append(R.styleable.KeyCycle_waveOffset, 7);
            mAttrMap.append(R.styleable.KeyCycle_waveVariesBy, 8);
            mAttrMap.append(R.styleable.KeyCycle_android_alpha, 9);
            mAttrMap.append(R.styleable.KeyCycle_android_elevation, 10);
            mAttrMap.append(R.styleable.KeyCycle_android_rotation, 11);
            mAttrMap.append(R.styleable.KeyCycle_android_rotationX, 12);
            mAttrMap.append(R.styleable.KeyCycle_android_rotationY, 13);
            mAttrMap.append(R.styleable.KeyCycle_transitionPathRotate, 14);
            mAttrMap.append(R.styleable.KeyCycle_android_scaleX, 15);
            mAttrMap.append(R.styleable.KeyCycle_android_scaleY, 16);
            mAttrMap.append(R.styleable.KeyCycle_android_translationX, 17);
            mAttrMap.append(R.styleable.KeyCycle_android_translationY, 18);
            mAttrMap.append(R.styleable.KeyCycle_android_translationZ, 19);
            mAttrMap.append(R.styleable.KeyCycle_motionProgress, 20);
        }

        private Loader() {
        }

        private static void read(KeyCycle keyCycle, TypedArray typedArray) {
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
                        Log.e((String)"KeyCycle", (String)stringBuilder.toString());
                        break;
                    }
                    case 20: {
                        keyCycle.mProgress = typedArray.getFloat(n3, keyCycle.mProgress);
                        break;
                    }
                    case 19: {
                        if (Build.VERSION.SDK_INT < 21) break;
                        keyCycle.mTranslationZ = typedArray.getDimension(n3, keyCycle.mTranslationZ);
                        break;
                    }
                    case 18: {
                        keyCycle.mTranslationY = typedArray.getDimension(n3, keyCycle.mTranslationY);
                        break;
                    }
                    case 17: {
                        keyCycle.mTranslationX = typedArray.getDimension(n3, keyCycle.mTranslationX);
                        break;
                    }
                    case 16: {
                        keyCycle.mScaleY = typedArray.getFloat(n3, keyCycle.mScaleY);
                        break;
                    }
                    case 15: {
                        keyCycle.mScaleX = typedArray.getFloat(n3, keyCycle.mScaleX);
                        break;
                    }
                    case 14: {
                        keyCycle.mTransitionPathRotate = typedArray.getFloat(n3, keyCycle.mTransitionPathRotate);
                        break;
                    }
                    case 13: {
                        keyCycle.mRotationY = typedArray.getFloat(n3, keyCycle.mRotationY);
                        break;
                    }
                    case 12: {
                        keyCycle.mRotationX = typedArray.getFloat(n3, keyCycle.mRotationX);
                        break;
                    }
                    case 11: {
                        keyCycle.mRotation = typedArray.getFloat(n3, keyCycle.mRotation);
                        break;
                    }
                    case 10: {
                        keyCycle.mElevation = typedArray.getDimension(n3, keyCycle.mElevation);
                        break;
                    }
                    case 9: {
                        keyCycle.mAlpha = typedArray.getFloat(n3, keyCycle.mAlpha);
                        break;
                    }
                    case 8: {
                        keyCycle.mWaveVariesBy = typedArray.getInt(n3, keyCycle.mWaveVariesBy);
                        break;
                    }
                    case 7: {
                        if (typedArray.peekValue((int)n3).type == 5) {
                            keyCycle.mWaveOffset = typedArray.getDimension(n3, keyCycle.mWaveOffset);
                            break;
                        }
                        keyCycle.mWaveOffset = typedArray.getFloat(n3, keyCycle.mWaveOffset);
                        break;
                    }
                    case 6: {
                        keyCycle.mWavePeriod = typedArray.getFloat(n3, keyCycle.mWavePeriod);
                        break;
                    }
                    case 5: {
                        keyCycle.mWaveShape = typedArray.getInt(n3, keyCycle.mWaveShape);
                        break;
                    }
                    case 4: {
                        keyCycle.mCurveFit = typedArray.getInteger(n3, keyCycle.mCurveFit);
                        break;
                    }
                    case 3: {
                        keyCycle.mTransitionEasing = typedArray.getString(n3);
                        break;
                    }
                    case 2: {
                        keyCycle.mFramePosition = typedArray.getInt(n3, keyCycle.mFramePosition);
                        break;
                    }
                    case 1: {
                        if (MotionLayout.IS_IN_EDIT_MODE) {
                            keyCycle.mTargetId = typedArray.getResourceId(n3, keyCycle.mTargetId);
                            if (keyCycle.mTargetId != -1) break;
                            keyCycle.mTargetString = typedArray.getString(n3);
                            break;
                        }
                        if (typedArray.peekValue((int)n3).type == 3) {
                            keyCycle.mTargetString = typedArray.getString(n3);
                            break;
                        }
                        keyCycle.mTargetId = typedArray.getResourceId(n3, keyCycle.mTargetId);
                    }
                }
                ++n2;
            }
        }
    }

}

