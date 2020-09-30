/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 */
package androidx.constraintlayout.helper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.R;

public class Layer
extends ConstraintHelper {
    private static final String TAG = "Layer";
    private boolean mApplyElevationOnAttach;
    private boolean mApplyVisibilityOnAttach;
    protected float mComputedCenterX = Float.NaN;
    protected float mComputedCenterY = Float.NaN;
    protected float mComputedMaxX = Float.NaN;
    protected float mComputedMaxY = Float.NaN;
    protected float mComputedMinX = Float.NaN;
    protected float mComputedMinY = Float.NaN;
    ConstraintLayout mContainer;
    private float mGroupRotateAngle = Float.NaN;
    boolean mNeedBounds = true;
    private float mRotationCenterX = Float.NaN;
    private float mRotationCenterY = Float.NaN;
    private float mScaleX = 1.0f;
    private float mScaleY = 1.0f;
    private float mShiftX = 0.0f;
    private float mShiftY = 0.0f;
    View[] mViews = null;

    public Layer(Context context) {
        super(context);
    }

    public Layer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public Layer(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    private void reCacheViews() {
        if (this.mContainer == null) {
            return;
        }
        if (this.mCount == 0) {
            return;
        }
        View[] arrview = this.mViews;
        if (arrview == null || arrview.length != this.mCount) {
            this.mViews = new View[this.mCount];
        }
        int n = 0;
        while (n < this.mCount) {
            int n2 = this.mIds[n];
            this.mViews[n] = this.mContainer.getViewById(n2);
            ++n;
        }
    }

    private void transform() {
        if (this.mContainer == null) {
            return;
        }
        if (this.mViews == null) {
            this.reCacheViews();
        }
        this.calcCenters();
        double d = Math.toRadians(this.mGroupRotateAngle);
        float f = (float)Math.sin(d);
        float f2 = (float)Math.cos(d);
        float f3 = this.mScaleX;
        float f4 = this.mScaleY;
        float f5 = -f4;
        int n = 0;
        while (n < this.mCount) {
            View view = this.mViews[n];
            int n2 = (view.getLeft() + view.getRight()) / 2;
            int n3 = (view.getTop() + view.getBottom()) / 2;
            float f6 = (float)n2 - this.mComputedCenterX;
            float f7 = (float)n3 - this.mComputedCenterY;
            float f8 = this.mShiftX;
            float f9 = this.mShiftY;
            view.setTranslationX(f3 * f2 * f6 + f5 * f * f7 - f6 + f8);
            view.setTranslationY(f6 * (f3 * f) + f4 * f2 * f7 - f7 + f9);
            view.setScaleY(this.mScaleY);
            view.setScaleX(this.mScaleX);
            view.setRotation(this.mGroupRotateAngle);
            ++n;
        }
    }

    protected void calcCenters() {
        if (this.mContainer == null) {
            return;
        }
        if (!(this.mNeedBounds || Float.isNaN(this.mComputedCenterX) || Float.isNaN(this.mComputedCenterY))) {
            return;
        }
        if (!Float.isNaN(this.mRotationCenterX) && !Float.isNaN(this.mRotationCenterY)) {
            this.mComputedCenterY = this.mRotationCenterY;
            this.mComputedCenterX = this.mRotationCenterX;
            return;
        }
        View[] arrview = this.getViews(this.mContainer);
        int n = arrview[0].getLeft();
        int n2 = arrview[0].getTop();
        int n3 = arrview[0].getRight();
        int n4 = arrview[0].getBottom();
        for (int i = 0; i < this.mCount; ++i) {
            View view = arrview[i];
            n = Math.min(n, view.getLeft());
            n2 = Math.min(n2, view.getTop());
            n3 = Math.max(n3, view.getRight());
            n4 = Math.max(n4, view.getBottom());
        }
        this.mComputedMaxX = n3;
        this.mComputedMaxY = n4;
        this.mComputedMinX = n;
        this.mComputedMinY = n2;
        this.mComputedCenterX = Float.isNaN(this.mRotationCenterX) ? (float)((n + n3) / 2) : this.mRotationCenterX;
        if (Float.isNaN(this.mRotationCenterY)) {
            this.mComputedCenterY = (n2 + n4) / 2;
            return;
        }
        this.mComputedCenterY = this.mRotationCenterY;
    }

    @Override
    protected void init(AttributeSet attributeSet) {
        super.init(attributeSet);
        int n = 0;
        this.mUseViewMeasure = false;
        if (attributeSet == null) return;
        attributeSet = this.getContext().obtainStyledAttributes(attributeSet, R.styleable.ConstraintLayout_Layout);
        int n2 = attributeSet.getIndexCount();
        while (n < n2) {
            int n3 = attributeSet.getIndex(n);
            if (n3 == R.styleable.ConstraintLayout_Layout_android_visibility) {
                this.mApplyVisibilityOnAttach = true;
            } else if (n3 == R.styleable.ConstraintLayout_Layout_android_elevation) {
                this.mApplyElevationOnAttach = true;
            }
            ++n;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mContainer = (ConstraintLayout)this.getParent();
        if (!this.mApplyVisibilityOnAttach) {
            if (!this.mApplyElevationOnAttach) return;
        }
        int n = this.getVisibility();
        float f = Build.VERSION.SDK_INT >= 21 ? this.getElevation() : 0.0f;
        int n2 = 0;
        while (n2 < this.mCount) {
            int n3 = this.mIds[n2];
            View view = this.mContainer.getViewById(n3);
            if (view != null) {
                if (this.mApplyVisibilityOnAttach) {
                    view.setVisibility(n);
                }
                if (this.mApplyElevationOnAttach && f > 0.0f && Build.VERSION.SDK_INT >= 21) {
                    view.setTranslationZ(view.getTranslationZ() + f);
                }
            }
            ++n2;
        }
    }

    public void setElevation(float f) {
        super.setElevation(f);
        this.applyLayoutFeatures();
    }

    public void setPivotX(float f) {
        this.mRotationCenterX = f;
        this.transform();
    }

    public void setPivotY(float f) {
        this.mRotationCenterY = f;
        this.transform();
    }

    public void setRotation(float f) {
        this.mGroupRotateAngle = f;
        this.transform();
    }

    public void setScaleX(float f) {
        this.mScaleX = f;
        this.transform();
    }

    public void setScaleY(float f) {
        this.mScaleY = f;
        this.transform();
    }

    public void setTranslationX(float f) {
        this.mShiftX = f;
        this.transform();
    }

    public void setTranslationY(float f) {
        this.mShiftY = f;
        this.transform();
    }

    public void setVisibility(int n) {
        super.setVisibility(n);
        this.applyLayoutFeatures();
    }

    @Override
    public void updatePostLayout(ConstraintLayout object) {
        this.reCacheViews();
        this.mComputedCenterX = Float.NaN;
        this.mComputedCenterY = Float.NaN;
        object = ((ConstraintLayout.LayoutParams)this.getLayoutParams()).getConstraintWidget();
        ((ConstraintWidget)object).setWidth(0);
        ((ConstraintWidget)object).setHeight(0);
        this.calcCenters();
        this.layout((int)this.mComputedMinX - this.getPaddingLeft(), (int)this.mComputedMinY - this.getPaddingTop(), (int)this.mComputedMaxX + this.getPaddingRight(), (int)this.mComputedMaxY + this.getPaddingBottom());
        if (Float.isNaN(this.mGroupRotateAngle)) return;
        this.transform();
    }

    @Override
    public void updatePreDraw(ConstraintLayout constraintLayout) {
        this.mContainer = constraintLayout;
        float f = this.getRotation();
        if (f == 0.0f) {
            if (Float.isNaN(this.mGroupRotateAngle)) return;
            this.mGroupRotateAngle = f;
            return;
        }
        this.mGroupRotateAngle = f;
    }
}

