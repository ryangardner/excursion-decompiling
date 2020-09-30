/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.widget.TextView
 */
package com.crest.divestory.ui.logs;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.crest.divestory.AppBase;
import com.crest.divestory.WatchOp;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.syntak.library.TimeOp;
import java.util.Locale;

public class MyMarkerView
extends MarkerView {
    Context context;
    private final TextView depth;
    private final TextView time;
    private long time_start_ms = 0L;
    private WatchOp.X_AXIS_UNIT x_axis_unit;

    public MyMarkerView(Context context, int n, long l, WatchOp.X_AXIS_UNIT x_AXIS_UNIT) {
        super(context, n);
        this.context = context;
        this.time_start_ms = l;
        this.x_axis_unit = x_AXIS_UNIT;
        this.time = (TextView)this.findViewById(2131231403);
        this.depth = (TextView)this.findViewById(2131230959);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(this.getWidth() / 2), -this.getHeight());
    }

    @Override
    public void refreshContent(Entry entry, Highlight highlight) {
        long l = (long)entry.getX() * 60L * 1000L + this.time_start_ms;
        if (this.x_axis_unit == WatchOp.X_AXIS_UNIT.SECOND) {
            l = (long)entry.getX() * 1000L + this.time_start_ms;
        }
        this.time.setText((CharSequence)TimeOp.MsToHourMinute(l));
        String string2 = String.format(Locale.ENGLISH, "%.1f ", Float.valueOf(entry.getY()));
        if (AppBase.display_unit == AppBase.UNITS.metric) {
            TextView textView = this.depth;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(this.context.getString(2131689688));
            textView.setText((CharSequence)stringBuilder.toString());
        } else {
            TextView textView = this.depth;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(this.context.getString(2131689629));
            textView.setText((CharSequence)stringBuilder.toString());
        }
        super.refreshContent(entry, highlight);
    }
}

