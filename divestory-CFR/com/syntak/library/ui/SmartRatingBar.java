/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.View$MeasureSpec
 */
package com.syntak.library.ui;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.syntak.library.R;

public class SmartRatingBar
extends View {
    private int mGapSize;
    private boolean mIndicator = true;
    private int mMaxStarNum = 5;
    private OnRatingBarChangeListener mOnRatingBarChangeListener;
    private int mOrientation = 0;
    private Drawable mRatingBackgroundDrawable;
    private Drawable mRatingDrawable;
    private float mRatingNum = 3.3f;
    private float mRatingStepSize = 0.1f;

    public SmartRatingBar(Context context) {
        super(context);
        this.init(context, null);
    }

    public SmartRatingBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            attributeSet = context.obtainStyledAttributes(attributeSet, R.styleable.SmartRatingBar);
            this.mRatingNum = attributeSet.getFloat(R.styleable.SmartRatingBar_rating, 2.5f);
            this.mGapSize = attributeSet.getDimensionPixelSize(R.styleable.SmartRatingBar_gap, 0);
            this.mMaxStarNum = attributeSet.getInt(R.styleable.SmartRatingBar_maxRating, 5);
            this.mIndicator = attributeSet.getBoolean(R.styleable.SmartRatingBar_indicator, true);
            this.mOrientation = attributeSet.getInt(R.styleable.SmartRatingBar_orientation, 0);
            this.mRatingDrawable = attributeSet.getDrawable(R.styleable.SmartRatingBar_ratingDrawable);
            this.mRatingBackgroundDrawable = attributeSet.getDrawable(R.styleable.SmartRatingBar_backgroundDrawable);
            attributeSet.recycle();
        }
        if (this.mRatingDrawable != null) return;
        if (this.mRatingBackgroundDrawable != null) return;
        this.mRatingDrawable = context.getResources().getDrawable(R.drawable.smart_ratingbar_rating);
        this.mRatingBackgroundDrawable = context.getResources().getDrawable(R.drawable.smart_ratingbar_background);
    }

    private int measureHeight(int n) {
        int n2;
        int n3 = View.MeasureSpec.getMode((int)n);
        int n4 = View.MeasureSpec.getSize((int)n);
        if (n3 == 1073741824) {
            return n4;
        }
        n = n2 = this.mRatingDrawable.getBounds().height();
        if (n2 == 0) {
            n = this.mRatingDrawable.getIntrinsicHeight();
        }
        int n5 = this.getPaddingBottom() + this.getPaddingTop();
        if (this.mOrientation == 0) {
            n = n5 + n;
        } else {
            n2 = this.mMaxStarNum;
            n = n5 + ((n2 - 1) * this.mGapSize + n2 * n);
        }
        if (n3 != Integer.MIN_VALUE) return n;
        return Math.min(n4, n);
    }

    private int measureWidth(int n) {
        int n2;
        int n3 = View.MeasureSpec.getMode((int)n);
        int n4 = View.MeasureSpec.getSize((int)n);
        if (n3 == 1073741824) {
            return n4;
        }
        n = n2 = this.mRatingDrawable.getBounds().width();
        if (n2 == 0) {
            n = this.mRatingDrawable.getIntrinsicWidth();
        }
        n2 = this.getPaddingLeft() + this.getPaddingRight();
        if (this.mOrientation == 0) {
            int n5 = this.mMaxStarNum;
            n = n2 + ((n5 - 1) * this.mGapSize + n5 * n);
        } else {
            n = n2 + n;
        }
        if (n3 != Integer.MIN_VALUE) return n;
        return Math.min(n, n4);
    }

    private void translateCanvas(Canvas canvas, Rect rect) {
        if (this.mOrientation == 0) {
            canvas.translate((float)(this.mGapSize + rect.width()), 0.0f);
            return;
        }
        canvas.translate(0.0f, (float)(this.mGapSize + rect.height()));
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.mIndicator) {
            return true;
        }
        if (motionEvent.getAction() != 0) {
            return this.mIndicator;
        }
        if (this.mOrientation == 0) {
            int n = this.mRatingBackgroundDrawable.getBounds().width();
            float f = motionEvent.getX() - (float)this.getPaddingLeft();
            int n2 = this.mGapSize;
            int n3 = (int)(f / (float)(n + n2));
            f = (float)((int)(f % (float)(n2 + n) / (float)n / this.mRatingStepSize)) / 10.0f;
            this.setRatingNum((float)n3 + f);
            return true;
        }
        int n = this.mRatingBackgroundDrawable.getBounds().height();
        float f = motionEvent.getY() - (float)this.getPaddingTop();
        int n4 = this.mGapSize;
        int n5 = (int)(f / (float)(n + n4));
        f = (float)((int)(f % (float)(n4 + n) / (float)n / this.mRatingStepSize)) / 10.0f;
        this.setRatingNum((float)n5 + f);
        return true;
    }

    public int getGapSize() {
        return this.mGapSize;
    }

    public int getMaxStarNum() {
        return this.mMaxStarNum;
    }

    public Drawable getRatingBackgroundDrawable() {
        return this.mRatingBackgroundDrawable;
    }

    public Drawable getRatingDrawable() {
        return this.mRatingDrawable;
    }

    public float getRatingNum() {
        return this.mRatingNum;
    }

    public boolean isIndicator() {
        return this.mIndicator;
    }

    protected void onDraw(Canvas canvas) {
        int n;
        int n2;
        super.onDraw(canvas);
        Drawable drawable2 = this.mRatingDrawable;
        Drawable drawable3 = this.mRatingBackgroundDrawable;
        float f = this.mRatingNum;
        Rect rect = drawable2.getBounds();
        int n3 = (int)Math.floor(f);
        canvas.translate((float)this.getPaddingLeft(), (float)this.getPaddingTop());
        for (n = 0; n < n3; ++n) {
            drawable2.draw(canvas);
            this.translateCanvas(canvas, rect);
        }
        f -= (float)n3;
        if (this.mOrientation == 0) {
            n3 = (int)((float)rect.width() * f);
            n2 = rect.height();
        } else {
            n3 = rect.width();
            n2 = (int)((float)rect.height() * f);
        }
        canvas.save();
        canvas.clipRect(0, 0, n3, n2);
        drawable2.draw(canvas);
        canvas.restore();
        canvas.save();
        if (this.mOrientation == 0) {
            canvas.clipRect(n3, 0, rect.right, rect.bottom);
        } else {
            canvas.clipRect(0, n2, rect.right, rect.bottom);
        }
        drawable3.draw(canvas);
        canvas.restore();
        this.translateCanvas(canvas, rect);
        while (++n < this.mMaxStarNum) {
            drawable3.draw(canvas);
            this.translateCanvas(canvas, rect);
        }
    }

    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        if (this.mOrientation == 0) {
            n2 = this.measureHeight(n2);
            if (this.mRatingDrawable.getIntrinsicHeight() > n2) {
                this.mRatingDrawable.setBounds(0, 0, n2, n2);
                this.mRatingBackgroundDrawable.setBounds(0, 0, n2, n2);
            } else {
                Drawable drawable2 = this.mRatingDrawable;
                drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), this.mRatingDrawable.getIntrinsicHeight());
                this.mRatingBackgroundDrawable.setBounds(0, 0, this.mRatingDrawable.getIntrinsicWidth(), this.mRatingDrawable.getIntrinsicHeight());
            }
            n = this.measureWidth(n);
        } else {
            n = this.measureWidth(n);
            if (this.mRatingDrawable.getIntrinsicWidth() > n) {
                this.mRatingDrawable.setBounds(0, 0, n, n);
                this.mRatingBackgroundDrawable.setBounds(0, 0, n, n);
            } else {
                Drawable drawable3 = this.mRatingDrawable;
                drawable3.setBounds(0, 0, drawable3.getIntrinsicWidth(), this.mRatingDrawable.getIntrinsicHeight());
                this.mRatingBackgroundDrawable.setBounds(0, 0, this.mRatingDrawable.getIntrinsicWidth(), this.mRatingDrawable.getIntrinsicHeight());
            }
            n2 = this.measureHeight(n2);
        }
        this.setMeasuredDimension(n, n2);
    }

    protected void onRestoreInstanceState(Parcelable object) {
        object = (SavedState)((Object)object);
        super.onRestoreInstanceState(object.getSuperState());
        this.setRatingNum(((SavedState)((Object)object)).getRating());
        this.setMaxStarNum(((SavedState)((Object)object)).getStarNum());
    }

    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.mRating = this.getRatingNum();
        savedState.mStarNum = this.getMaxStarNum();
        return savedState;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mIndicator) {
            return true;
        }
        if (motionEvent.getAction() != 0) {
            return super.onTouchEvent(motionEvent);
        }
        if (this.mOrientation == 0) {
            int n = this.mRatingBackgroundDrawable.getBounds().width();
            float f = motionEvent.getX() - (float)this.getPaddingLeft();
            int n2 = this.mGapSize;
            int n3 = (int)(f / (float)(n + n2));
            f = (float)((int)(f % (float)(n2 + n) / (float)n / this.mRatingStepSize)) / 10.0f;
            this.setRatingNum((float)n3 + f);
            return true;
        }
        int n = this.mRatingBackgroundDrawable.getBounds().height();
        float f = motionEvent.getY() - (float)this.getPaddingTop();
        int n4 = this.mGapSize;
        int n5 = (int)(f / (float)(n + n4));
        f = (float)((int)(f % (float)(n4 + n) / (float)n / this.mRatingStepSize)) / 10.0f;
        this.setRatingNum((float)n5 + f);
        return true;
    }

    public void setGapSize(int n) {
        this.mGapSize = n;
        this.postInvalidate();
    }

    public void setIndicator(boolean bl) {
        this.mIndicator = bl;
    }

    public void setMaxStarNum(int n) {
        this.mMaxStarNum = n;
        this.postInvalidate();
    }

    public void setOnRatingBarChangeListener(OnRatingBarChangeListener onRatingBarChangeListener) {
        this.mOnRatingBarChangeListener = onRatingBarChangeListener;
    }

    public void setRatingBackgroundDrawable(Drawable drawable2) {
        this.mRatingBackgroundDrawable = drawable2;
    }

    public void setRatingDrawable(Drawable drawable2) {
        this.mRatingDrawable = drawable2;
        this.postInvalidate();
    }

    public void setRatingNum(float f) {
        this.mRatingNum = f;
        this.postInvalidate();
        OnRatingBarChangeListener onRatingBarChangeListener = this.mOnRatingBarChangeListener;
        if (onRatingBarChangeListener == null) return;
        onRatingBarChangeListener.onRatingChanged(this, f);
    }

    public static interface OnRatingBarChangeListener {
        public void onRatingChanged(SmartRatingBar var1, float var2);
    }

    static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        float mRating;
        int mStarNum;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.mRating = parcel.readFloat();
            this.mStarNum = parcel.readInt();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public float getRating() {
            return this.mRating;
        }

        public int getStarNum() {
            return this.mStarNum;
        }

        public void setRating(float f) {
            this.mRating = f;
        }

        public void setStarNum(int n) {
            this.mStarNum = n;
        }

        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeFloat(this.mRating);
            parcel.writeInt(this.mStarNum);
        }

    }

}

