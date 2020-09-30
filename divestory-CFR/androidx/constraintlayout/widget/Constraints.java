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
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.R;

public class Constraints
extends ViewGroup {
    public static final String TAG = "Constraints";
    ConstraintSet myConstraintSet;

    public Constraints(Context context) {
        super(context);
        super.setVisibility(8);
    }

    public Constraints(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init(attributeSet);
        super.setVisibility(8);
    }

    public Constraints(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.init(attributeSet);
        super.setVisibility(8);
    }

    private void init(AttributeSet attributeSet) {
        Log.v((String)TAG, (String)" ################# init");
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new ConstraintLayout.LayoutParams(layoutParams);
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    public ConstraintSet getConstraintSet() {
        if (this.myConstraintSet == null) {
            this.myConstraintSet = new ConstraintSet();
        }
        this.myConstraintSet.clone(this);
        return this.myConstraintSet;
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
    }

    public static class LayoutParams
    extends ConstraintLayout.LayoutParams {
        public float alpha = 1.0f;
        public boolean applyElevation;
        public float elevation;
        public float rotation;
        public float rotationX;
        public float rotationY;
        public float scaleX;
        public float scaleY;
        public float transformPivotX;
        public float transformPivotY;
        public float translationX;
        public float translationY;
        public float translationZ;

        public LayoutParams(int n, int n2) {
            super(n, n2);
            this.applyElevation = false;
            this.elevation = 0.0f;
            this.rotation = 0.0f;
            this.rotationX = 0.0f;
            this.rotationY = 0.0f;
            this.scaleX = 1.0f;
            this.scaleY = 1.0f;
            this.transformPivotX = 0.0f;
            this.transformPivotY = 0.0f;
            this.translationX = 0.0f;
            this.translationY = 0.0f;
            this.translationZ = 0.0f;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            int n = 0;
            this.applyElevation = false;
            this.elevation = 0.0f;
            this.rotation = 0.0f;
            this.rotationX = 0.0f;
            this.rotationY = 0.0f;
            this.scaleX = 1.0f;
            this.scaleY = 1.0f;
            this.transformPivotX = 0.0f;
            this.transformPivotY = 0.0f;
            this.translationX = 0.0f;
            this.translationY = 0.0f;
            this.translationZ = 0.0f;
            context = context.obtainStyledAttributes(attributeSet, R.styleable.ConstraintSet);
            int n2 = context.getIndexCount();
            while (n < n2) {
                int n3 = context.getIndex(n);
                if (n3 == R.styleable.ConstraintSet_android_alpha) {
                    this.alpha = context.getFloat(n3, this.alpha);
                } else if (n3 == R.styleable.ConstraintSet_android_elevation) {
                    if (Build.VERSION.SDK_INT >= 21) {
                        this.elevation = context.getFloat(n3, this.elevation);
                        this.applyElevation = true;
                    }
                } else if (n3 == R.styleable.ConstraintSet_android_rotationX) {
                    this.rotationX = context.getFloat(n3, this.rotationX);
                } else if (n3 == R.styleable.ConstraintSet_android_rotationY) {
                    this.rotationY = context.getFloat(n3, this.rotationY);
                } else if (n3 == R.styleable.ConstraintSet_android_rotation) {
                    this.rotation = context.getFloat(n3, this.rotation);
                } else if (n3 == R.styleable.ConstraintSet_android_scaleX) {
                    this.scaleX = context.getFloat(n3, this.scaleX);
                } else if (n3 == R.styleable.ConstraintSet_android_scaleY) {
                    this.scaleY = context.getFloat(n3, this.scaleY);
                } else if (n3 == R.styleable.ConstraintSet_android_transformPivotX) {
                    this.transformPivotX = context.getFloat(n3, this.transformPivotX);
                } else if (n3 == R.styleable.ConstraintSet_android_transformPivotY) {
                    this.transformPivotY = context.getFloat(n3, this.transformPivotY);
                } else if (n3 == R.styleable.ConstraintSet_android_translationX) {
                    this.translationX = context.getFloat(n3, this.translationX);
                } else if (n3 == R.styleable.ConstraintSet_android_translationY) {
                    this.translationY = context.getFloat(n3, this.translationY);
                } else if (n3 == R.styleable.ConstraintSet_android_translationZ && Build.VERSION.SDK_INT >= 21) {
                    this.translationZ = context.getFloat(n3, this.translationZ);
                }
                ++n;
            }
        }

        public LayoutParams(LayoutParams layoutParams) {
            super(layoutParams);
            this.applyElevation = false;
            this.elevation = 0.0f;
            this.rotation = 0.0f;
            this.rotationX = 0.0f;
            this.rotationY = 0.0f;
            this.scaleX = 1.0f;
            this.scaleY = 1.0f;
            this.transformPivotX = 0.0f;
            this.transformPivotY = 0.0f;
            this.translationX = 0.0f;
            this.translationY = 0.0f;
            this.translationZ = 0.0f;
        }
    }

}

