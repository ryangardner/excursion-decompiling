/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.view.GestureDetector
 *  android.view.GestureDetector$SimpleOnGestureListener
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnTouchListener
 */
package com.github.mikephil.charting.listener;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class OnDrawLineChartTouchListener
extends GestureDetector.SimpleOnGestureListener
implements View.OnTouchListener {
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}

