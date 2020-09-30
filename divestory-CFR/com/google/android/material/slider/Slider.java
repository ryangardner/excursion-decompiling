/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 */
package com.google.android.material.slider;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.google.android.material.R;
import com.google.android.material.slider.BaseOnChangeListener;
import com.google.android.material.slider.BaseOnSliderTouchListener;
import com.google.android.material.slider.BaseSlider;
import com.google.android.material.slider.LabelFormatter;
import java.util.List;

public class Slider
extends BaseSlider<Slider, OnChangeListener, OnSliderTouchListener> {
    public Slider(Context context) {
        this(context, null);
    }

    public Slider(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.sliderStyle);
    }

    public Slider(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        context = context.obtainStyledAttributes(attributeSet, new int[]{16842788});
        if (context.hasValue(0)) {
            this.setValue(context.getFloat(0, 0.0f));
        }
        context.recycle();
    }

    public float getValue() {
        return this.getValues().get(0).floatValue();
    }

    @Override
    protected boolean pickActiveThumb() {
        if (this.getActiveThumbIndex() != -1) {
            return true;
        }
        this.setActiveThumbIndex(0);
        return true;
    }

    public void setValue(float f) {
        this.setValues(Float.valueOf(f));
    }

    public static interface OnChangeListener
    extends BaseOnChangeListener<Slider> {
    }

    public static interface OnSliderTouchListener
    extends BaseOnSliderTouchListener<Slider> {
    }

}

