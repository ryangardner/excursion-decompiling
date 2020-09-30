/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.appcompat.R;
import java.lang.ref.WeakReference;

public final class ViewStubCompat
extends View {
    private OnInflateListener mInflateListener;
    private int mInflatedId;
    private WeakReference<View> mInflatedViewRef;
    private LayoutInflater mInflater;
    private int mLayoutResource = 0;

    public ViewStubCompat(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ViewStubCompat(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        context = context.obtainStyledAttributes(attributeSet, R.styleable.ViewStubCompat, n, 0);
        this.mInflatedId = context.getResourceId(R.styleable.ViewStubCompat_android_inflatedId, -1);
        this.mLayoutResource = context.getResourceId(R.styleable.ViewStubCompat_android_layout, 0);
        this.setId(context.getResourceId(R.styleable.ViewStubCompat_android_id, -1));
        context.recycle();
        this.setVisibility(8);
        this.setWillNotDraw(true);
    }

    protected void dispatchDraw(Canvas canvas) {
    }

    public void draw(Canvas canvas) {
    }

    public int getInflatedId() {
        return this.mInflatedId;
    }

    public LayoutInflater getLayoutInflater() {
        return this.mInflater;
    }

    public int getLayoutResource() {
        return this.mLayoutResource;
    }

    public View inflate() {
        ViewParent viewParent = this.getParent();
        if (!(viewParent instanceof ViewGroup)) throw new IllegalStateException("ViewStub must have a non-null ViewGroup viewParent");
        if (this.mLayoutResource == 0) throw new IllegalArgumentException("ViewStub must have a valid layoutResource");
        Object object = (ViewGroup)viewParent;
        viewParent = this.mInflater;
        if (viewParent == null) {
            viewParent = LayoutInflater.from((Context)this.getContext());
        }
        viewParent = viewParent.inflate(this.mLayoutResource, (ViewGroup)object, false);
        int n = this.mInflatedId;
        if (n != -1) {
            viewParent.setId(n);
        }
        n = object.indexOfChild((View)this);
        object.removeViewInLayout((View)this);
        ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
        if (layoutParams != null) {
            object.addView((View)viewParent, n, layoutParams);
        } else {
            object.addView((View)viewParent, n);
        }
        this.mInflatedViewRef = new WeakReference<ViewParent>(viewParent);
        object = this.mInflateListener;
        if (object == null) return viewParent;
        object.onInflate(this, (View)viewParent);
        return viewParent;
    }

    protected void onMeasure(int n, int n2) {
        this.setMeasuredDimension(0, 0);
    }

    public void setInflatedId(int n) {
        this.mInflatedId = n;
    }

    public void setLayoutInflater(LayoutInflater layoutInflater) {
        this.mInflater = layoutInflater;
    }

    public void setLayoutResource(int n) {
        this.mLayoutResource = n;
    }

    public void setOnInflateListener(OnInflateListener onInflateListener) {
        this.mInflateListener = onInflateListener;
    }

    public void setVisibility(int n) {
        View view = this.mInflatedViewRef;
        if (view != null) {
            if ((view = (View)view.get()) == null) throw new IllegalStateException("setVisibility called on un-referenced view");
            view.setVisibility(n);
            return;
        }
        super.setVisibility(n);
        if (n != 0) {
            if (n != 4) return;
        }
        this.inflate();
    }

    public static interface OnInflateListener {
        public void onInflate(ViewStubCompat var1, View var2);
    }

}

