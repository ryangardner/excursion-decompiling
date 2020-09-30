/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Matrix
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
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.constraintlayout.motion.widget.Debug;
import androidx.constraintlayout.motion.widget.Key;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.SplineSet;
import androidx.constraintlayout.widget.R;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;

public class KeyTrigger
extends Key {
    public static final int KEY_TYPE = 5;
    static final String NAME = "KeyTrigger";
    private static final String TAG = "KeyTrigger";
    RectF mCollisionRect = new RectF();
    private String mCross = null;
    private int mCurveFit = -1;
    private Method mFireCross;
    private boolean mFireCrossReset = true;
    private float mFireLastPos;
    private Method mFireNegativeCross;
    private boolean mFireNegativeReset = true;
    private Method mFirePositiveCross;
    private boolean mFirePositiveReset = true;
    private float mFireThreshold = Float.NaN;
    private String mNegativeCross = null;
    private String mPositiveCross = null;
    private boolean mPostLayout = false;
    RectF mTargetRect = new RectF();
    private int mTriggerCollisionId = UNSET;
    private View mTriggerCollisionView = null;
    private int mTriggerID = UNSET;
    private int mTriggerReceiver = UNSET;
    float mTriggerSlack = 0.1f;

    public KeyTrigger() {
        this.mType = 5;
        this.mCustomConstraints = new HashMap();
    }

    static /* synthetic */ float access$002(KeyTrigger keyTrigger, float f) {
        keyTrigger.mFireThreshold = f;
        return f;
    }

    static /* synthetic */ String access$102(KeyTrigger keyTrigger, String string2) {
        keyTrigger.mNegativeCross = string2;
        return string2;
    }

    static /* synthetic */ String access$202(KeyTrigger keyTrigger, String string2) {
        keyTrigger.mPositiveCross = string2;
        return string2;
    }

    static /* synthetic */ String access$302(KeyTrigger keyTrigger, String string2) {
        keyTrigger.mCross = string2;
        return string2;
    }

    static /* synthetic */ int access$400(KeyTrigger keyTrigger) {
        return keyTrigger.mTriggerID;
    }

    static /* synthetic */ int access$402(KeyTrigger keyTrigger, int n) {
        keyTrigger.mTriggerID = n;
        return n;
    }

    static /* synthetic */ int access$500(KeyTrigger keyTrigger) {
        return keyTrigger.mTriggerCollisionId;
    }

    static /* synthetic */ int access$502(KeyTrigger keyTrigger, int n) {
        keyTrigger.mTriggerCollisionId = n;
        return n;
    }

    static /* synthetic */ boolean access$600(KeyTrigger keyTrigger) {
        return keyTrigger.mPostLayout;
    }

    static /* synthetic */ boolean access$602(KeyTrigger keyTrigger, boolean bl) {
        keyTrigger.mPostLayout = bl;
        return bl;
    }

    static /* synthetic */ int access$700(KeyTrigger keyTrigger) {
        return keyTrigger.mTriggerReceiver;
    }

    static /* synthetic */ int access$702(KeyTrigger keyTrigger, int n) {
        keyTrigger.mTriggerReceiver = n;
        return n;
    }

    private void setUpRect(RectF rectF, View view, boolean bl) {
        rectF.top = view.getTop();
        rectF.bottom = view.getBottom();
        rectF.left = view.getLeft();
        rectF.right = view.getRight();
        if (!bl) return;
        view.getMatrix().mapRect(rectF);
    }

    @Override
    public void addValues(HashMap<String, SplineSet> hashMap) {
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public void conditionallyFire(float var1_1, View var2_2) {
        block37 : {
            block43 : {
                block42 : {
                    block41 : {
                        block40 : {
                            block39 : {
                                block38 : {
                                    block35 : {
                                        block36 : {
                                            var3_3 = this.mTriggerCollisionId;
                                            var4_4 = KeyTrigger.UNSET;
                                            var5_5 = true;
                                            if (var3_3 == var4_4) break block35;
                                            if (this.mTriggerCollisionView == null) {
                                                this.mTriggerCollisionView = ((ViewGroup)var2_2.getParent()).findViewById(this.mTriggerCollisionId);
                                            }
                                            this.setUpRect(this.mCollisionRect, this.mTriggerCollisionView, this.mPostLayout);
                                            this.setUpRect(this.mTargetRect, var2_2, this.mPostLayout);
                                            if (!this.mCollisionRect.intersect(this.mTargetRect)) break block36;
                                            if (this.mFireCrossReset) {
                                                this.mFireCrossReset = false;
                                                var4_4 = 1;
                                            } else {
                                                var4_4 = 0;
                                            }
                                            if (this.mFirePositiveReset) {
                                                this.mFirePositiveReset = false;
                                                var5_5 = true;
                                            } else {
                                                var5_5 = false;
                                            }
                                            this.mFireNegativeReset = true;
                                            var3_3 = 0;
                                            break block37;
                                        }
                                        if (!this.mFireCrossReset) {
                                            this.mFireCrossReset = true;
                                            var4_4 = 1;
                                        } else {
                                            var4_4 = 0;
                                        }
                                        if (this.mFireNegativeReset) {
                                            this.mFireNegativeReset = false;
                                            var3_3 = 1;
                                        } else {
                                            var3_3 = 0;
                                        }
                                        this.mFirePositiveReset = true;
                                        ** GOTO lbl72
                                    }
                                    if (!this.mFireCrossReset) break block38;
                                    var6_6 = this.mFireThreshold;
                                    if (!((var1_1 - var6_6) * (this.mFireLastPos - var6_6) < 0.0f)) break block39;
                                    this.mFireCrossReset = false;
                                    var4_4 = 1;
                                    break block40;
                                }
                                if (Math.abs(var1_1 - this.mFireThreshold) > this.mTriggerSlack) {
                                    this.mFireCrossReset = true;
                                }
                            }
                            var4_4 = 0;
                        }
                        if (!this.mFireNegativeReset) break block41;
                        var6_6 = this.mFireThreshold;
                        var7_7 = var1_1 - var6_6;
                        if (!((this.mFireLastPos - var6_6) * var7_7 < 0.0f) || !(var7_7 < 0.0f)) break block42;
                        this.mFireNegativeReset = false;
                        var3_3 = 1;
                        break block43;
                    }
                    if (Math.abs(var1_1 - this.mFireThreshold) > this.mTriggerSlack) {
                        this.mFireNegativeReset = true;
                    }
                }
                var3_3 = 0;
            }
            if (this.mFirePositiveReset) {
                var7_7 = this.mFireThreshold;
                var6_6 = var1_1 - var7_7;
                if ((this.mFireLastPos - var7_7) * var6_6 < 0.0f && var6_6 > 0.0f) {
                    this.mFirePositiveReset = false;
                } else {
                    var5_5 = false;
                }
            } else {
                if (Math.abs(var1_1 - this.mFireThreshold) > this.mTriggerSlack) {
                    this.mFirePositiveReset = true;
                }
lbl72: // 4 sources:
                var5_5 = false;
            }
        }
        this.mFireLastPos = var1_1;
        if (var3_3 != 0 || var4_4 != 0 || var5_5) {
            ((MotionLayout)var2_2.getParent()).fireTrigger(this.mTriggerID, var5_5, var1_1);
        }
        if (this.mTriggerReceiver != KeyTrigger.UNSET) {
            var2_2 = ((MotionLayout)var2_2.getParent()).findViewById(this.mTriggerReceiver);
        }
        if (var3_3 != 0 && this.mNegativeCross != null) {
            if (this.mFireNegativeCross == null) {
                try {
                    this.mFireNegativeCross = var2_2.getClass().getMethod(this.mNegativeCross, new Class[0]);
                }
                catch (NoSuchMethodException var8_8) {
                    var8_9 = new StringBuilder();
                    var8_9.append("Could not find method \"");
                    var8_9.append(this.mNegativeCross);
                    var8_9.append("\"on class ");
                    var8_9.append(var2_2.getClass().getSimpleName());
                    var8_9.append(" ");
                    var8_9.append(Debug.getName(var2_2));
                    Log.e((String)"KeyTrigger", (String)var8_9.toString());
                }
            }
            try {
                this.mFireNegativeCross.invoke((Object)var2_2, new Object[0]);
            }
            catch (Exception var8_10) {
                var8_9 = new StringBuilder();
                var8_9.append("Exception in call \"");
                var8_9.append(this.mNegativeCross);
                var8_9.append("\"on class ");
                var8_9.append(var2_2.getClass().getSimpleName());
                var8_9.append(" ");
                var8_9.append(Debug.getName(var2_2));
                Log.e((String)"KeyTrigger", (String)var8_9.toString());
            }
        }
        if (var5_5 && this.mPositiveCross != null) {
            if (this.mFirePositiveCross == null) {
                try {
                    this.mFirePositiveCross = var2_2.getClass().getMethod(this.mPositiveCross, new Class[0]);
                }
                catch (NoSuchMethodException var8_11) {
                    var8_9 = new StringBuilder();
                    var8_9.append("Could not find method \"");
                    var8_9.append(this.mPositiveCross);
                    var8_9.append("\"on class ");
                    var8_9.append(var2_2.getClass().getSimpleName());
                    var8_9.append(" ");
                    var8_9.append(Debug.getName(var2_2));
                    Log.e((String)"KeyTrigger", (String)var8_9.toString());
                }
            }
            try {
                this.mFirePositiveCross.invoke((Object)var2_2, new Object[0]);
            }
            catch (Exception var8_12) {
                var8_9 = new StringBuilder();
                var8_9.append("Exception in call \"");
                var8_9.append(this.mPositiveCross);
                var8_9.append("\"on class ");
                var8_9.append(var2_2.getClass().getSimpleName());
                var8_9.append(" ");
                var8_9.append(Debug.getName(var2_2));
                Log.e((String)"KeyTrigger", (String)var8_9.toString());
            }
        }
        if (var4_4 == 0) return;
        if (this.mCross == null) return;
        if (this.mFireCross == null) {
            try {
                this.mFireCross = var2_2.getClass().getMethod(this.mCross, new Class[0]);
            }
            catch (NoSuchMethodException var8_13) {
                var8_9 = new StringBuilder();
                var8_9.append("Could not find method \"");
                var8_9.append(this.mCross);
                var8_9.append("\"on class ");
                var8_9.append(var2_2.getClass().getSimpleName());
                var8_9.append(" ");
                var8_9.append(Debug.getName(var2_2));
                Log.e((String)"KeyTrigger", (String)var8_9.toString());
            }
        }
        try {
            this.mFireCross.invoke((Object)var2_2, new Object[0]);
            return;
        }
        catch (Exception var8_14) {
            var8_15 = new StringBuilder();
            var8_15.append("Exception in call \"");
            var8_15.append(this.mCross);
            var8_15.append("\"on class ");
            var8_15.append(var2_2.getClass().getSimpleName());
            var8_15.append(" ");
            var8_15.append(Debug.getName(var2_2));
            Log.e((String)"KeyTrigger", (String)var8_15.toString());
        }
    }

    @Override
    public void getAttributeNames(HashSet<String> hashSet) {
    }

    int getCurveFit() {
        return this.mCurveFit;
    }

    @Override
    public void load(Context context, AttributeSet attributeSet) {
        Loader.read(this, context.obtainStyledAttributes(attributeSet, R.styleable.KeyTrigger), context);
    }

    @Override
    public void setValue(String string2, Object object) {
    }

    private static class Loader {
        private static final int COLLISION = 9;
        private static final int CROSS = 4;
        private static final int FRAME_POS = 8;
        private static final int NEGATIVE_CROSS = 1;
        private static final int POSITIVE_CROSS = 2;
        private static final int POST_LAYOUT = 10;
        private static final int TARGET_ID = 7;
        private static final int TRIGGER_ID = 6;
        private static final int TRIGGER_RECEIVER = 11;
        private static final int TRIGGER_SLACK = 5;
        private static SparseIntArray mAttrMap;

        static {
            SparseIntArray sparseIntArray;
            mAttrMap = sparseIntArray = new SparseIntArray();
            sparseIntArray.append(R.styleable.KeyTrigger_framePosition, 8);
            mAttrMap.append(R.styleable.KeyTrigger_onCross, 4);
            mAttrMap.append(R.styleable.KeyTrigger_onNegativeCross, 1);
            mAttrMap.append(R.styleable.KeyTrigger_onPositiveCross, 2);
            mAttrMap.append(R.styleable.KeyTrigger_motionTarget, 7);
            mAttrMap.append(R.styleable.KeyTrigger_triggerId, 6);
            mAttrMap.append(R.styleable.KeyTrigger_triggerSlack, 5);
            mAttrMap.append(R.styleable.KeyTrigger_motion_triggerOnCollision, 9);
            mAttrMap.append(R.styleable.KeyTrigger_motion_postLayoutCollision, 10);
            mAttrMap.append(R.styleable.KeyTrigger_triggerReceiver, 11);
        }

        private Loader() {
        }

        /*
         * Unable to fully structure code
         */
        public static void read(KeyTrigger var0, TypedArray var1_1, Context var2_2) {
            var3_3 = var1_1.getIndexCount();
            var4_4 = 0;
            while (var4_4 < var3_3) {
                block15 : {
                    var5_5 = var1_1.getIndex(var4_4);
                    switch (Loader.mAttrMap.get(var5_5)) {
                        default: {
                            ** break;
                        }
                        case 11: {
                            KeyTrigger.access$702(var0, var1_1.getResourceId(var5_5, KeyTrigger.access$700(var0)));
                            ** break;
                        }
                        case 10: {
                            KeyTrigger.access$602(var0, var1_1.getBoolean(var5_5, KeyTrigger.access$600(var0)));
                            break;
                        }
                        case 9: {
                            KeyTrigger.access$502(var0, var1_1.getResourceId(var5_5, KeyTrigger.access$500(var0)));
                            break;
                        }
                        case 8: {
                            var0.mFramePosition = var1_1.getInteger(var5_5, var0.mFramePosition);
                            KeyTrigger.access$002(var0, ((float)var0.mFramePosition + 0.5f) / 100.0f);
                            break;
                        }
                        case 7: {
                            if (MotionLayout.IS_IN_EDIT_MODE) {
                                var0.mTargetId = var1_1.getResourceId(var5_5, var0.mTargetId);
                                if (var0.mTargetId != -1) break;
                                var0.mTargetString = var1_1.getString(var5_5);
                                break;
                            }
                            if (var1_1.peekValue((int)var5_5).type == 3) {
                                var0.mTargetString = var1_1.getString(var5_5);
                                break;
                            }
                            var0.mTargetId = var1_1.getResourceId(var5_5, var0.mTargetId);
                            break;
                        }
                        case 6: {
                            KeyTrigger.access$402(var0, var1_1.getResourceId(var5_5, KeyTrigger.access$400(var0)));
                            break;
                        }
                        case 5: {
                            var0.mTriggerSlack = var1_1.getFloat(var5_5, var0.mTriggerSlack);
                            break;
                        }
                        case 4: {
                            KeyTrigger.access$302(var0, var1_1.getString(var5_5));
                            break;
                        }
                        case 2: {
                            KeyTrigger.access$202(var0, var1_1.getString(var5_5));
                            break;
                        }
                        case 1: {
                            KeyTrigger.access$102(var0, var1_1.getString(var5_5));
                            break;
                        }
                    }
                    break block15;
lbl56: // 2 sources:
                    var2_2 = new StringBuilder();
                    var2_2.append("unused attribute 0x");
                    var2_2.append(Integer.toHexString(var5_5));
                    var2_2.append("   ");
                    var2_2.append(Loader.mAttrMap.get(var5_5));
                    Log.e((String)"KeyTrigger", (String)var2_2.toString());
                }
                ++var4_4;
            }
        }
    }

}

