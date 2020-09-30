/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 */
package com.google.android.material.slider;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.google.android.material.R;
import com.google.android.material.slider.BaseOnChangeListener;
import com.google.android.material.slider.BaseOnSliderTouchListener;
import com.google.android.material.slider.BaseSlider;
import com.google.android.material.slider.LabelFormatter;
import java.util.ArrayList;
import java.util.List;

public class RangeSlider
extends BaseSlider<RangeSlider, OnChangeListener, OnSliderTouchListener> {
    public RangeSlider(Context context) {
        this(context, null);
    }

    public RangeSlider(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.sliderStyle);
    }

    public RangeSlider(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        context = context.obtainStyledAttributes(attributeSet, new int[]{R.attr.values});
        if (context.hasValue(0)) {
            n = context.getResourceId(0, 0);
            this.setValues(RangeSlider.convertToFloat(context.getResources().obtainTypedArray(n)));
        }
        context.recycle();
    }

    private static List<Float> convertToFloat(TypedArray typedArray) {
        ArrayList<Float> arrayList = new ArrayList<Float>();
        int n = 0;
        while (n < typedArray.length()) {
            arrayList.add(Float.valueOf(typedArray.getFloat(n, -1.0f)));
            ++n;
        }
        return arrayList;
    }

    @Override
    public List<Float> getValues() {
        return super.getValues();
    }

    @Override
    public void setValues(List<Float> list) {
        super.setValues(list);
    }

    @Override
    public void setValues(Float ... arrfloat) {
        super.setValues(arrfloat);
    }

    public static interface OnChangeListener
    extends BaseOnChangeListener<RangeSlider> {
    }

    public static interface OnSliderTouchListener
    extends BaseOnSliderTouchListener<RangeSlider> {
    }

}

