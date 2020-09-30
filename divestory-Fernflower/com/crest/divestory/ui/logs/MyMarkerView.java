package com.crest.divestory.ui.logs;

import android.content.Context;
import android.widget.TextView;
import com.crest.divestory.AppBase;
import com.crest.divestory.WatchOp;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.syntak.library.TimeOp;
import java.util.Locale;

public class MyMarkerView extends MarkerView {
   Context context;
   private final TextView depth;
   private final TextView time;
   private long time_start_ms = 0L;
   private WatchOp.X_AXIS_UNIT x_axis_unit;

   public MyMarkerView(Context var1, int var2, long var3, WatchOp.X_AXIS_UNIT var5) {
      super(var1, var2);
      this.context = var1;
      this.time_start_ms = var3;
      this.x_axis_unit = var5;
      this.time = (TextView)this.findViewById(2131231403);
      this.depth = (TextView)this.findViewById(2131230959);
   }

   public MPPointF getOffset() {
      return new MPPointF((float)(-(this.getWidth() / 2)), (float)(-this.getHeight()));
   }

   public void refreshContent(Entry var1, Highlight var2) {
      long var3 = (long)var1.getX() * 60L * 1000L + this.time_start_ms;
      if (this.x_axis_unit == WatchOp.X_AXIS_UNIT.SECOND) {
         var3 = (long)var1.getX() * 1000L + this.time_start_ms;
      }

      this.time.setText(TimeOp.MsToHourMinute(var3));
      String var5 = String.format(Locale.ENGLISH, "%.1f ", var1.getY());
      if (AppBase.display_unit == AppBase.UNITS.metric) {
         TextView var6 = this.depth;
         StringBuilder var7 = new StringBuilder();
         var7.append(var5);
         var7.append(this.context.getString(2131689688));
         var6.setText(var7.toString());
      } else {
         TextView var9 = this.depth;
         StringBuilder var8 = new StringBuilder();
         var8.append(var5);
         var8.append(this.context.getString(2131689629));
         var9.setText(var8.toString());
      }

      super.refreshContent(var1, var2);
   }
}
