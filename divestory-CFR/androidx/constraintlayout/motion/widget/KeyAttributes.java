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
import androidx.constraintlayout.motion.widget.Key;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.SplineSet;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.R;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class KeyAttributes
extends Key {
    public static final int KEY_TYPE = 1;
    static final String NAME = "KeyAttribute";
    private static final String TAG = "KeyAttribute";
    private float mAlpha = Float.NaN;
    private int mCurveFit = -1;
    private float mElevation = Float.NaN;
    private float mPivotX = Float.NaN;
    private float mPivotY = Float.NaN;
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
    private boolean mVisibility = false;

    public KeyAttributes() {
        this.mType = 1;
        this.mCustomConstraints = new HashMap();
    }

    @Override
    public void addValues(HashMap<String, SplineSet> hashMap) {
        Iterator<String> iterator2 = hashMap.keySet().iterator();
        block32 : while (iterator2.hasNext()) {
            Object object;
            int n;
            Object object2;
            block34 : {
                object2 = iterator2.next();
                object = hashMap.get(object2);
                boolean bl = ((String)object2).startsWith("CUSTOM");
                n = 7;
                if (bl) {
                    object2 = ((String)object2).substring(7);
                    if ((object2 = (ConstraintAttribute)this.mCustomConstraints.get(object2)) == null) continue;
                    ((SplineSet.CustomSet)object).setPoint(this.mFramePosition, (ConstraintAttribute)object2);
                    continue;
                }
                switch (((String)object2).hashCode()) {
                    default: {
                        break;
                    }
                    case 92909918: {
                        if (!((String)object2).equals("alpha")) break;
                        n = 0;
                        break block34;
                    }
                    case 37232917: {
                        if (!((String)object2).equals("transitionPathRotate")) break;
                        break block34;
                    }
                    case -4379043: {
                        if (!((String)object2).equals("elevation")) break;
                        n = 1;
                        break block34;
                    }
                    case -40300674: {
                        if (!((String)object2).equals("rotation")) break;
                        n = 2;
                        break block34;
                    }
                    case -760884509: {
                        if (!((String)object2).equals("transformPivotY")) break;
                        n = 6;
                        break block34;
                    }
                    case -760884510: {
                        if (!((String)object2).equals("transformPivotX")) break;
                        n = 5;
                        break block34;
                    }
                    case -908189617: {
                        if (!((String)object2).equals("scaleY")) break;
                        n = 9;
                        break block34;
                    }
                    case -908189618: {
                        if (!((String)object2).equals("scaleX")) break;
                        n = 8;
                        break block34;
                    }
                    case -1001078227: {
                        if (!((String)object2).equals("progress")) break;
                        n = 13;
                        break block34;
                    }
                    case -1225497655: {
                        if (!((String)object2).equals("translationZ")) break;
                        n = 12;
                        break block34;
                    }
                    case -1225497656: {
                        if (!((String)object2).equals("translationY")) break;
                        n = 11;
                        break block34;
                    }
                    case -1225497657: {
                        if (!((String)object2).equals("translationX")) break;
                        n = 10;
                        break block34;
                    }
                    case -1249320805: {
                        if (!((String)object2).equals("rotationY")) break;
                        n = 4;
                        break block34;
                    }
                    case -1249320806: {
                        if (!((String)object2).equals("rotationX")) break;
                        n = 3;
                        break block34;
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
                    Log.v((String)"KeyAttributes", (String)((StringBuilder)object).toString());
                    continue block32;
                }
                case 13: {
                    if (Float.isNaN(this.mProgress)) continue block32;
                    ((SplineSet)object).setPoint(this.mFramePosition, this.mProgress);
                    continue block32;
                }
                case 12: {
                    if (Float.isNaN(this.mTranslationZ)) continue block32;
                    ((SplineSet)object).setPoint(this.mFramePosition, this.mTranslationZ);
                    continue block32;
                }
                case 11: {
                    if (Float.isNaN(this.mTranslationY)) continue block32;
                    ((SplineSet)object).setPoint(this.mFramePosition, this.mTranslationY);
                    continue block32;
                }
                case 10: {
                    if (Float.isNaN(this.mTranslationX)) continue block32;
                    ((SplineSet)object).setPoint(this.mFramePosition, this.mTranslationX);
                    continue block32;
                }
                case 9: {
                    if (Float.isNaN(this.mScaleY)) continue block32;
                    ((SplineSet)object).setPoint(this.mFramePosition, this.mScaleY);
                    continue block32;
                }
                case 8: {
                    if (Float.isNaN(this.mScaleX)) continue block32;
                    ((SplineSet)object).setPoint(this.mFramePosition, this.mScaleX);
                    continue block32;
                }
                case 7: {
                    if (Float.isNaN(this.mTransitionPathRotate)) continue block32;
                    ((SplineSet)object).setPoint(this.mFramePosition, this.mTransitionPathRotate);
                    continue block32;
                }
                case 6: {
                    if (Float.isNaN(this.mRotationY)) continue block32;
                    ((SplineSet)object).setPoint(this.mFramePosition, this.mPivotY);
                    continue block32;
                }
                case 5: {
                    if (Float.isNaN(this.mRotationX)) continue block32;
                    ((SplineSet)object).setPoint(this.mFramePosition, this.mPivotX);
                    continue block32;
                }
                case 4: {
                    if (Float.isNaN(this.mRotationY)) continue block32;
                    ((SplineSet)object).setPoint(this.mFramePosition, this.mRotationY);
                    continue block32;
                }
                case 3: {
                    if (Float.isNaN(this.mRotationX)) continue block32;
                    ((SplineSet)object).setPoint(this.mFramePosition, this.mRotationX);
                    continue block32;
                }
                case 2: {
                    if (Float.isNaN(this.mRotation)) continue block32;
                    ((SplineSet)object).setPoint(this.mFramePosition, this.mRotation);
                    continue block32;
                }
                case 1: {
                    if (Float.isNaN(this.mElevation)) continue block32;
                    ((SplineSet)object).setPoint(this.mFramePosition, this.mElevation);
                    continue block32;
                }
                case 0: 
            }
            if (Float.isNaN(this.mAlpha)) continue;
            ((SplineSet)object).setPoint(this.mFramePosition, this.mAlpha);
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
        if (!Float.isNaN(this.mPivotX)) {
            hashSet.add("transformPivotX");
        }
        if (!Float.isNaN(this.mPivotY)) {
            hashSet.add("transformPivotY");
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
        if (!Float.isNaN(this.mScaleX)) {
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
        Loader.read(this, context.obtainStyledAttributes(attributeSet, R.styleable.KeyAttribute));
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
        if (!Float.isNaN(this.mPivotX)) {
            hashMap.put("transformPivotX", this.mCurveFit);
        }
        if (!Float.isNaN(this.mPivotY)) {
            hashMap.put("transformPivotY", this.mCurveFit);
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
        if (!Float.isNaN(this.mScaleY)) {
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
        block38 : {
            switch (string2.hashCode()) {
                default: {
                    break;
                }
                case 1941332754: {
                    if (!string2.equals("visibility")) break;
                    n = 12;
                    break block38;
                }
                case 1317633238: {
                    if (!string2.equals("mTranslationZ")) break;
                    n = 16;
                    break block38;
                }
                case 579057826: {
                    if (!string2.equals("curveFit")) break;
                    n = 1;
                    break block38;
                }
                case 92909918: {
                    if (!string2.equals("alpha")) break;
                    n = 0;
                    break block38;
                }
                case 37232917: {
                    if (!string2.equals("transitionPathRotate")) break;
                    n = 13;
                    break block38;
                }
                case -4379043: {
                    if (!string2.equals("elevation")) break;
                    n = 2;
                    break block38;
                }
                case -40300674: {
                    if (!string2.equals("rotation")) break;
                    n = 4;
                    break block38;
                }
                case -908189617: {
                    if (!string2.equals("scaleY")) break;
                    n = 10;
                    break block38;
                }
                case -908189618: {
                    if (!string2.equals("scaleX")) break;
                    n = 9;
                    break block38;
                }
                case -987906985: {
                    if (!string2.equals("pivotY")) break;
                    n = 8;
                    break block38;
                }
                case -987906986: {
                    if (!string2.equals("pivotX")) break;
                    n = 7;
                    break block38;
                }
                case -1225497656: {
                    if (!string2.equals("translationY")) break;
                    n = 15;
                    break block38;
                }
                case -1225497657: {
                    if (!string2.equals("translationX")) break;
                    n = 14;
                    break block38;
                }
                case -1249320805: {
                    if (!string2.equals("rotationY")) break;
                    n = 6;
                    break block38;
                }
                case -1249320806: {
                    if (!string2.equals("rotationX")) break;
                    n = 5;
                    break block38;
                }
                case -1812823328: {
                    if (!string2.equals("transitionEasing")) break;
                    n = 11;
                    break block38;
                }
                case -1913008125: {
                    if (!string2.equals("motionProgress")) break;
                    n = 3;
                    break block38;
                }
            }
            n = -1;
        }
        switch (n) {
            default: {
                return;
            }
            case 16: {
                this.mTranslationZ = this.toFloat(object);
                return;
            }
            case 15: {
                this.mTranslationY = this.toFloat(object);
                return;
            }
            case 14: {
                this.mTranslationX = this.toFloat(object);
                return;
            }
            case 13: {
                this.mTransitionPathRotate = this.toFloat(object);
                return;
            }
            case 12: {
                this.mVisibility = this.toBoolean(object);
                return;
            }
            case 11: {
                this.mTransitionEasing = object.toString();
                return;
            }
            case 10: {
                this.mScaleY = this.toFloat(object);
                return;
            }
            case 9: {
                this.mScaleX = this.toFloat(object);
                return;
            }
            case 8: {
                this.mPivotY = this.toFloat(object);
                return;
            }
            case 7: {
                this.mPivotX = this.toFloat(object);
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
        private static final int ANDROID_PIVOT_X = 19;
        private static final int ANDROID_PIVOT_Y = 20;
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
        private static SparseIntArray mAttrMap;

        static {
            SparseIntArray sparseIntArray;
            mAttrMap = sparseIntArray = new SparseIntArray();
            sparseIntArray.append(R.styleable.KeyAttribute_android_alpha, 1);
            mAttrMap.append(R.styleable.KeyAttribute_android_elevation, 2);
            mAttrMap.append(R.styleable.KeyAttribute_android_rotation, 4);
            mAttrMap.append(R.styleable.KeyAttribute_android_rotationX, 5);
            mAttrMap.append(R.styleable.KeyAttribute_android_rotationY, 6);
            mAttrMap.append(R.styleable.KeyAttribute_android_transformPivotX, 19);
            mAttrMap.append(R.styleable.KeyAttribute_android_transformPivotY, 20);
            mAttrMap.append(R.styleable.KeyAttribute_android_scaleX, 7);
            mAttrMap.append(R.styleable.KeyAttribute_transitionPathRotate, 8);
            mAttrMap.append(R.styleable.KeyAttribute_transitionEasing, 9);
            mAttrMap.append(R.styleable.KeyAttribute_motionTarget, 10);
            mAttrMap.append(R.styleable.KeyAttribute_framePosition, 12);
            mAttrMap.append(R.styleable.KeyAttribute_curveFit, 13);
            mAttrMap.append(R.styleable.KeyAttribute_android_scaleY, 14);
            mAttrMap.append(R.styleable.KeyAttribute_android_translationX, 15);
            mAttrMap.append(R.styleable.KeyAttribute_android_translationY, 16);
            mAttrMap.append(R.styleable.KeyAttribute_android_translationZ, 17);
            mAttrMap.append(R.styleable.KeyAttribute_motionProgress, 18);
        }

        private Loader() {
        }

        public static void read(KeyAttributes keyAttributes, TypedArray typedArray) {
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
                        Log.e((String)"KeyAttribute", (String)stringBuilder.toString());
                        break;
                    }
                    case 20: {
                        keyAttributes.mPivotY = typedArray.getDimension(n3, keyAttributes.mPivotY);
                        break;
                    }
                    case 19: {
                        keyAttributes.mPivotX = typedArray.getDimension(n3, keyAttributes.mPivotX);
                        break;
                    }
                    case 18: {
                        keyAttributes.mProgress = typedArray.getFloat(n3, keyAttributes.mProgress);
                        break;
                    }
                    case 17: {
                        if (Build.VERSION.SDK_INT < 21) break;
                        keyAttributes.mTranslationZ = typedArray.getDimension(n3, keyAttributes.mTranslationZ);
                        break;
                    }
                    case 16: {
                        keyAttributes.mTranslationY = typedArray.getDimension(n3, keyAttributes.mTranslationY);
                        break;
                    }
                    case 15: {
                        keyAttributes.mTranslationX = typedArray.getDimension(n3, keyAttributes.mTranslationX);
                        break;
                    }
                    case 14: {
                        keyAttributes.mScaleY = typedArray.getFloat(n3, keyAttributes.mScaleY);
                        break;
                    }
                    case 13: {
                        keyAttributes.mCurveFit = typedArray.getInteger(n3, keyAttributes.mCurveFit);
                        break;
                    }
                    case 12: {
                        keyAttributes.mFramePosition = typedArray.getInt(n3, keyAttributes.mFramePosition);
                        break;
                    }
                    case 10: {
                        if (MotionLayout.IS_IN_EDIT_MODE) {
                            keyAttributes.mTargetId = typedArray.getResourceId(n3, keyAttributes.mTargetId);
                            if (keyAttributes.mTargetId != -1) break;
                            keyAttributes.mTargetString = typedArray.getString(n3);
                            break;
                        }
                        if (typedArray.peekValue((int)n3).type == 3) {
                            keyAttributes.mTargetString = typedArray.getString(n3);
                            break;
                        }
                        keyAttributes.mTargetId = typedArray.getResourceId(n3, keyAttributes.mTargetId);
                        break;
                    }
                    case 9: {
                        keyAttributes.mTransitionEasing = typedArray.getString(n3);
                        break;
                    }
                    case 8: {
                        keyAttributes.mTransitionPathRotate = typedArray.getFloat(n3, keyAttributes.mTransitionPathRotate);
                        break;
                    }
                    case 7: {
                        keyAttributes.mScaleX = typedArray.getFloat(n3, keyAttributes.mScaleX);
                        break;
                    }
                    case 6: {
                        keyAttributes.mRotationY = typedArray.getFloat(n3, keyAttributes.mRotationY);
                        break;
                    }
                    case 5: {
                        keyAttributes.mRotationX = typedArray.getFloat(n3, keyAttributes.mRotationX);
                        break;
                    }
                    case 4: {
                        keyAttributes.mRotation = typedArray.getFloat(n3, keyAttributes.mRotation);
                        break;
                    }
                    case 2: {
                        keyAttributes.mElevation = typedArray.getDimension(n3, keyAttributes.mElevation);
                        break;
                    }
                    case 1: {
                        keyAttributes.mAlpha = typedArray.getFloat(n3, keyAttributes.mAlpha);
                    }
                }
                ++n2;
            }
        }
    }

}

