/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.view.KeyEvent
 *  android.view.View
 *  android.widget.Adapter
 *  android.widget.GridView
 *  android.widget.ListAdapter
 */
package com.google.android.material.datepicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import androidx.core.util.Pair;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R;
import com.google.android.material.datepicker.CalendarItemStyle;
import com.google.android.material.datepicker.CalendarStyle;
import com.google.android.material.datepicker.DateSelector;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MonthAdapter;
import com.google.android.material.datepicker.UtcDates;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;

final class MaterialCalendarGridView
extends GridView {
    private final Calendar dayCompute = UtcDates.getUtcCalendar();

    public MaterialCalendarGridView(Context context) {
        this(context, null);
    }

    public MaterialCalendarGridView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MaterialCalendarGridView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        if (MaterialDatePicker.isFullscreen(this.getContext())) {
            this.setNextFocusLeftId(R.id.cancel_button);
            this.setNextFocusRightId(R.id.confirm_button);
        }
        ViewCompat.setAccessibilityDelegate((View)this, new AccessibilityDelegateCompat(){

            @Override
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                accessibilityNodeInfoCompat.setCollectionInfo(null);
            }
        });
    }

    private void gainFocus(int n, Rect rect) {
        if (n == 33) {
            this.setSelection(this.getAdapter().lastPositionInMonth());
            return;
        }
        if (n == 130) {
            this.setSelection(this.getAdapter().firstPositionInMonth());
            return;
        }
        super.onFocusChanged(true, n, rect);
    }

    private static int horizontalMidPoint(View view) {
        return view.getLeft() + view.getWidth() / 2;
    }

    private static boolean skipMonth(Long l, Long l2, Long l3, Long l4) {
        boolean bl;
        boolean bl2 = bl = true;
        if (l == null) return bl2;
        bl2 = bl;
        if (l2 == null) return bl2;
        bl2 = bl;
        if (l3 == null) return bl2;
        if (l4 == null) {
            return bl;
        }
        bl2 = bl;
        if (l3 > l2) return bl2;
        if (l4 >= l) return false;
        return bl;
    }

    public MonthAdapter getAdapter() {
        return (MonthAdapter)super.getAdapter();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.getAdapter().notifyDataSetChanged();
    }

    /*
     * Unable to fully structure code
     */
    protected final void onDraw(Canvas var1_1) {
        super.onDraw(var1_1);
        var2_2 = this.getAdapter();
        var3_3 = var2_2.dateSelector;
        var4_4 = var2_2.calendarStyle;
        var5_5 = var2_2.getItem(var2_2.firstPositionInMonth());
        var6_6 = var2_2.getItem(var2_2.lastPositionInMonth());
        var7_7 = var3_3.getSelectedRanges().iterator();
        block0 : do lbl-1000: // 3 sources:
        {
            var3_3 = this;
            if (var7_7.hasNext() == false) return;
            var8_8 = var7_7.next();
            if (var8_8.first == null || var8_8.second == null) ** GOTO lbl-1000
            var9_9 = (Long)var8_8.first;
            var11_10 = (Long)var8_8.second;
            if (MaterialCalendarGridView.skipMonth(var5_5, var6_6, var9_9, var11_10)) {
                return;
            }
            if (var9_9 < var5_5) {
                var13_11 = var2_2.firstPositionInMonth();
                var14_12 = var2_2.isFirstInRow(var13_11) ? 0 : var3_3.getChildAt(var13_11 - 1).getRight();
            } else {
                var3_3.dayCompute.setTimeInMillis(var9_9);
                var13_11 = var2_2.dayToPosition(var3_3.dayCompute.get(5));
                var14_12 = MaterialCalendarGridView.horizontalMidPoint(var3_3.getChildAt(var13_11));
            }
            if (var11_10 > var6_6) {
                var15_13 = Math.min(var2_2.lastPositionInMonth(), this.getChildCount() - 1);
                var16_14 = var2_2.isLastInRow(var15_13) ? this.getWidth() : var3_3.getChildAt(var15_13).getRight();
            } else {
                var3_3.dayCompute.setTimeInMillis(var11_10);
                var15_13 = var2_2.dayToPosition(var3_3.dayCompute.get(5));
                var16_14 = MaterialCalendarGridView.horizontalMidPoint(var3_3.getChildAt(var15_13));
            }
            var17_15 = (int)var2_2.getItemId(var13_11);
            var18_16 = (int)var2_2.getItemId(var15_13);
            do {
                if (var17_15 > var18_16) continue block0;
                var19_17 = this.getNumColumns() * var17_15;
                var20_18 = this.getNumColumns();
                var3_3 = this.getChildAt(var19_17);
                var21_19 = var3_3.getTop();
                var22_20 = var4_4.day.getTopInset();
                var23_21 = var3_3.getBottom();
                var24_22 = var4_4.day.getBottomInset();
                var25_23 = var19_17 > var13_11 ? 0 : var14_12;
                var20_18 = var15_13 > var20_18 + var19_17 - 1 ? this.getWidth() : var16_14;
                var1_1.drawRect((float)var25_23, (float)(var21_19 + var22_20), (float)var20_18, (float)(var23_21 - var24_22), var4_4.rangeFill);
                ++var17_15;
            } while (true);
            break;
        } while (true);
    }

    protected void onFocusChanged(boolean bl, int n, Rect rect) {
        if (bl) {
            this.gainFocus(n, rect);
            return;
        }
        super.onFocusChanged(false, n, rect);
    }

    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        if (!super.onKeyDown(n, keyEvent)) {
            return false;
        }
        if (this.getSelectedItemPosition() == -1) return true;
        if (this.getSelectedItemPosition() >= this.getAdapter().firstPositionInMonth()) {
            return true;
        }
        if (19 != n) return false;
        this.setSelection(this.getAdapter().firstPositionInMonth());
        return true;
    }

    public final void setAdapter(ListAdapter listAdapter) {
        if (!(listAdapter instanceof MonthAdapter)) throw new IllegalArgumentException(String.format("%1$s must have its Adapter set to a %2$s", MaterialCalendarGridView.class.getCanonicalName(), MonthAdapter.class.getCanonicalName()));
        super.setAdapter(listAdapter);
    }

    public void setSelection(int n) {
        if (n < this.getAdapter().firstPositionInMonth()) {
            super.setSelection(this.getAdapter().firstPositionInMonth());
            return;
        }
        super.setSelection(n);
    }

}

