package com.crest.divestory.ui.logs;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.crest.divestory.AppBase;
import com.crest.divestory.DataStruct;
import com.crest.divestory.WatchOp;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.syntak.library.MathOp;
import com.syntak.library.TimeOp;
import com.syntak.library.ui.ConfirmDialog;
import com.syntak.library.ui.SmartRatingBar;
import java.util.ArrayList;
import java.util.Locale;

public class ActivityDiveLogEdit extends AppCompatActivity {
   EditText O2_ratio;
   private Activity activity;
   EditText aux_weight;
   TextView aux_weight_unit;
   TextView average_depth;
   TextView average_depth_unit;
   EditText breathing_gas;
   EditText buddy;
   LineChart chart_dive_profile_data;
   private Context context;
   EditText cylinder_capacity;
   TextView date;
   DataStruct.DiveLog diveLog;
   DataStruct.DiveProfileData diveProfileData;
   TextView dive_type;
   TextView duration;
   Handler handler;
   int index;
   EditText location;
   TextView max_depth;
   TextView max_depth_unit;
   MenuItem menuItemFavorite;
   MenuItem menuItemSave;
   EditText note;
   EditText pressure_end;
   EditText pressure_start;
   SmartRatingBar ratingBar;
   EditText surface_temperature;
   TextView surface_temperature_unit;
   TextView time_end;
   TextView time_start;
   TextView tv_dive_log_index;
   EditText visibility;
   TextView visibility_unit;
   TextView water_temperature;
   TextView water_temperature_unit;
   EditText wave;
   EditText weather;
   EditText wind;

   private void fill_chart(final LineChart var1, DataStruct.DiveProfileData var2, YAxis var3, WatchOp.X_AXIS_UNIT var4) {
      ArrayList var5 = new ArrayList();
      int var6 = 0;
      float var7 = 0.0F;

      float var8;
      float var12;
      for(var8 = 0.0F; var6 < var2.length(); var8 = var12) {
         DataStruct.DiveProfileDatum var9 = (DataStruct.DiveProfileDatum)var2.list.get(var6);
         float var10 = var9.time_elpased / 60.0F;
         if (var4 == WatchOp.X_AXIS_UNIT.SECOND) {
            var10 = var9.time_elpased;
         }

         float var11 = WatchOp.convertAbsMbarToMeter((float)var9.depth);
         if (AppBase.display_unit != AppBase.UNITS.metric) {
            var11 = WatchOp.lengthMeter2Foot(var11);
         }

         if (var6 == 0) {
            var7 = var11;
            var12 = var11;
         } else {
            float var13 = var7;
            if (var11 > var7) {
               var13 = var11;
            }

            var7 = var13;
            var12 = var8;
            if (var11 < var8) {
               var12 = var11;
               var7 = var13;
            }
         }

         var5.add(new Entry(var10, var11));
         ++var6;
      }

      var3.setAxisMaximum(var7 * 1.1F);
      var3.setAxisMinimum(var8 * 0.9F);
      LineDataSet var14;
      if (var1.getData() != null && ((LineData)var1.getData()).getDataSetCount() > 0) {
         var14 = (LineDataSet)((LineData)var1.getData()).getDataSetByIndex(0);
         var14.setValues(var5);
         var14.notifyDataSetChanged();
         ((LineData)var1.getData()).notifyDataChanged();
         var1.notifyDataSetChanged();
      } else {
         var14 = new LineDataSet(var5, "");
         var14.setDrawIcons(false);
         var14.setColor(-1);
         var14.setCircleColor(-16711681);
         var14.setLineWidth(1.0F);
         var14.setCircleRadius(2.0F);
         var14.setDrawCircleHole(false);
         var14.setFormLineWidth(1.0F);
         var14.setFormLineDashEffect(new DashPathEffect(new float[]{10.0F, 5.0F}, 0.0F));
         var14.setFormSize(15.0F);
         var14.setDrawValues(false);
         var14.setValueTextSize(9.0F);
         var14.enableDashedHighlightLine(10.0F, 5.0F, 0.0F);
         var14.setDrawFilled(true);
         var14.setFillFormatter(new IFillFormatter() {
            public float getFillLinePosition(ILineDataSet var1x, LineDataProvider var2) {
               return var1.getAxisLeft().getAxisMinimum();
            }
         });
         if (Utils.getSDKInt() >= 18) {
            var14.setFillDrawable(ContextCompat.getDrawable(this.context, 2131165321));
         } else {
            var14.setFillColor(-16777216);
         }

         ArrayList var15 = new ArrayList();
         var15.add(var14);
         var1.setData(new LineData(var15));
      }

   }

   private void init_UI() {
      this.setContentView(2131427392);
      this.context = this;
      this.activity = this;
      this.init_action_bar();
      this.diveLog = (DataStruct.DiveLog)WatchOp.dive_logs_list.get(this.index);
      this.tv_dive_log_index = (TextView)this.findViewById(2131230982);
      this.dive_type = (TextView)this.findViewById(2131230984);
      this.date = (TextView)this.findViewById(2131230944);
      this.time_start = (TextView)this.findViewById(2131231410);
      this.time_end = (TextView)this.findViewById(2131231404);
      this.max_depth = (TextView)this.findViewById(2131231146);
      this.max_depth_unit = (TextView)this.findViewById(2131231147);
      this.average_depth = (TextView)this.findViewById(2131230826);
      this.average_depth_unit = (TextView)this.findViewById(2131230827);
      this.duration = (TextView)this.findViewById(2131230997);
      this.surface_temperature = (EditText)this.findViewById(2131231343);
      this.water_temperature = (TextView)this.findViewById(2131231446);
      this.surface_temperature_unit = (TextView)this.findViewById(2131231344);
      this.water_temperature_unit = (TextView)this.findViewById(2131231447);
      this.location = (EditText)this.findViewById(2131231137);
      this.breathing_gas = (EditText)this.findViewById(2131230838);
      this.cylinder_capacity = (EditText)this.findViewById(2131230942);
      this.O2_ratio = (EditText)this.findViewById(2131230728);
      this.pressure_start = (EditText)this.findViewById(2131231226);
      this.pressure_end = (EditText)this.findViewById(2131231225);
      this.aux_weight = (EditText)this.findViewById(2131230824);
      this.aux_weight_unit = (TextView)this.findViewById(2131230825);
      this.visibility = (EditText)this.findViewById(2131231441);
      this.visibility_unit = (TextView)this.findViewById(2131231442);
      this.weather = (EditText)this.findViewById(2131231449);
      this.wind = (EditText)this.findViewById(2131231451);
      this.wave = (EditText)this.findViewById(2131231448);
      this.ratingBar = (SmartRatingBar)this.findViewById(2131231238);
      this.buddy = (EditText)this.findViewById(2131230839);
      this.note = (EditText)this.findViewById(2131231195);
      long var1 = Math.min(999L, this.diveLog.dive_log_index);
      TextView var3 = this.tv_dive_log_index;
      StringBuilder var4 = new StringBuilder();
      var4.append("#");
      var4.append(String.format(Locale.ENGLISH, "%03d", var1));
      var3.setText(var4.toString());
      int var5 = (int)this.diveLog.dive_type;
      String var13;
      if (var5 != 0) {
         if (var5 != 1) {
            if (var5 != 2) {
               var13 = null;
            } else {
               var13 = this.getString(2131689521);
            }
         } else {
            var13 = this.getString(2131689645);
         }
      } else {
         var13 = this.getString(2131689798);
      }

      this.dive_type.setText(var13);
      this.date.setText(TimeOp.MsToYearMonthDay(this.diveLog.start_time));
      this.time_start.setText(TimeOp.MsToHourMinute(this.diveLog.start_time));
      this.time_end.setText(TimeOp.MsToHourMinute(this.diveLog.end_time));
      this.duration.setText(TimeOp.get_Minute_Second_String_from_Seconds((int)this.diveLog.duration));
      this.ratingBar.setRatingNum((float)this.diveLog.rating);
      this.location.setText(this.diveLog.location);
      this.note.setText(this.diveLog.note);
      this.breathing_gas.setText(this.diveLog.breathing_gas);
      this.cylinder_capacity.setText(String.valueOf(this.diveLog.cylinder_capacity));
      this.O2_ratio.setText(String.valueOf(this.diveLog.O2_ratio));
      this.pressure_start.setText(String.valueOf(this.diveLog.pressure_start));
      this.pressure_end.setText(String.valueOf(this.diveLog.pressure_end));
      this.weather.setText(this.diveLog.weather);
      this.wind.setText(this.diveLog.wind);
      this.wave.setText(this.diveLog.wave);
      this.buddy.setText(this.diveLog.buddy);
      float var6 = WatchOp.convertAbsMbarToMeter((float)this.diveLog.max_depth);
      float var7 = WatchOp.convertAbsMbarToMeter((float)this.diveLog.average_depth);
      float var8 = this.diveLog.visibility;
      float var9 = (float)this.diveLog.lowest_water_temperature / 10.0F;
      float var10 = this.diveLog.surface_temperature / 10.0F;
      if (AppBase.display_unit == AppBase.UNITS.metric) {
         this.max_depth_unit.setText(2131689688);
         this.average_depth_unit.setText(2131689688);
         this.visibility_unit.setText(2131689688);
         this.surface_temperature_unit.setText(2131689584);
         this.water_temperature_unit.setText(2131689584);
         this.aux_weight_unit.setText(2131689659);
         this.max_depth.setText(String.format(Locale.ENGLISH, "%.1f", var6));
         if (var7 < 0.0F) {
            this.average_depth.setText("N/A");
         } else {
            this.average_depth.setText(String.format(Locale.ENGLISH, "%.1f", var7));
         }

         this.visibility.setText(String.format(Locale.ENGLISH, "%d", (int)var8));
         this.water_temperature.setText(String.format(Locale.ENGLISH, "%d", (int)var9));
         this.surface_temperature.setText(String.format(Locale.ENGLISH, "%d", (int)var10));
         this.aux_weight.setText(String.format(Locale.ENGLISH, "%d", (int)this.diveLog.aux_weight));
      } else {
         this.max_depth_unit.setText(2131689629);
         this.average_depth_unit.setText(2131689629);
         this.visibility_unit.setText(2131689629);
         this.surface_temperature_unit.setText(2131689585);
         this.water_temperature_unit.setText(2131689585);
         this.aux_weight_unit.setText(2131689766);
         this.max_depth.setText(String.format(Locale.ENGLISH, "%.1f", WatchOp.lengthMeter2Foot(var6)));
         if (var7 < 0.0F) {
            this.average_depth.setText("N/A");
         } else {
            this.average_depth.setText(String.format(Locale.ENGLISH, "%.1f", WatchOp.lengthMeter2Foot(var7)));
         }

         this.visibility.setText(String.format(Locale.ENGLISH, "%d", (int)WatchOp.lengthMeter2Foot(var8)));
         this.water_temperature.setText(String.format(Locale.ENGLISH, "%d", (int)MathOp.temperatureC2F(var9)));
         this.surface_temperature.setText(String.format(Locale.ENGLISH, "%d", (int)MathOp.temperatureC2F(var10)));
         this.aux_weight.setText(String.format(Locale.ENGLISH, "%d", (int)MathOp.weightKg2Pound(this.diveLog.aux_weight)));
      }

      this.prepareView(this.location, false);
      this.prepareView(this.note, false);
      this.prepareView(this.breathing_gas, true);
      this.prepareView(this.cylinder_capacity, true);
      this.prepareView(this.O2_ratio, true);
      this.prepareView(this.pressure_start, true);
      this.prepareView(this.pressure_end, true);
      this.prepareView(this.aux_weight, true);
      this.prepareView(this.visibility, true);
      this.prepareView(this.weather, true);
      this.prepareView(this.wind, true);
      this.prepareView(this.wave, true);
      this.prepareView(this.buddy, true);
      this.prepareView(this.surface_temperature, true);
      this.ratingBar.setOnRatingBarChangeListener(new SmartRatingBar.OnRatingBarChangeListener() {
         public void onRatingChanged(SmartRatingBar var1, float var2) {
            ActivityDiveLogEdit.this.menuItemSave.setChecked(true);
            ActivityDiveLogEdit.this.menuItemSave.setIcon(2131165342);
         }
      });
      LineChart var11 = (LineChart)this.findViewById(2131230983);
      if (this.diveLog.profile_data_length > 0L) {
         DataStruct.DiveProfileData var14 = new DataStruct.DiveProfileData(AppBase.dbOp.getDiveProfileData(this.diveLog.watch_serial_number, this.diveLog.start_time));
         this.diveProfileData = var14;
         DataStruct.DiveLog var12 = this.diveLog;
         this.init_chart(var11, var12, var14, var12.start_time);
      } else {
         var11.setVisibility(8);
      }

      this.note.setOnTouchListener(new OnTouchListener() {
         public boolean onTouch(View var1, MotionEvent var2) {
            if (var1.hasFocus()) {
               var1.getParent().requestDisallowInterceptTouchEvent(true);
               if ((var2.getAction() & 255) == 8) {
                  var1.getParent().requestDisallowInterceptTouchEvent(false);
               }
            }

            return false;
         }
      });
   }

   private void init_action_bar() {
      this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      this.getSupportActionBar().setHomeAsUpIndicator(2131165356);
      AppBase.setTitleAndColor(this, 2131689611, 2131034179);
   }

   private void init_chart(LineChart var1, DataStruct.DiveLog var2, DataStruct.DiveProfileData var3, long var4) {
      WatchOp.X_AXIS_UNIT var6 = WatchOp.X_AXIS_UNIT.MINUTE;
      var1.setBackgroundColor(-16777216);
      var1.getDescription().setEnabled(false);
      var1.setTouchEnabled(true);
      var1.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
         public void onNothingSelected() {
         }

         public void onValueSelected(Entry var1, Highlight var2) {
         }
      });
      var1.setDrawGridBackground(true);
      var1.setGridBackgroundColor(-12303292);
      if (var2.dive_type == 2L) {
         var6 = WatchOp.X_AXIS_UNIT.SECOND;
      }

      MyMarkerView var9 = new MyMarkerView(this.context, 2131427369, var4, var6);
      var9.setChartView(var1);
      var1.setMarker(var9);
      var1.setDragEnabled(true);
      var1.setScaleEnabled(true);
      var1.setPinchZoom(true);
      XAxis var7 = var1.getXAxis();
      var7.enableGridDashedLine(10.0F, 10.0F, 0.0F);
      var7.setTextColor(-1);
      YAxis var10 = var1.getAxisLeft();
      var10.setInverted(true);
      var1.getAxisRight().setEnabled(false);
      var10.enableGridDashedLine(10.0F, 10.0F, 0.0F);
      var10.setAxisMaximum(200.0F);
      var10.setAxisMinimum(-50.0F);
      var10.setTextColor(-1);
      LimitLine var8 = new LimitLine(9.0F, "Index 10");
      var8.setLineWidth(4.0F);
      var8.enableDashedLine(10.0F, 10.0F, 0.0F);
      var8.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
      var8.setTextSize(10.0F);
      var8 = new LimitLine(-30.0F, "Lower Limit");
      var8.setLineWidth(4.0F);
      var8.enableDashedLine(10.0F, 10.0F, 0.0F);
      var8.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
      var8.setTextSize(10.0F);
      var10.setDrawLimitLinesBehindData(true);
      var7.setDrawLimitLinesBehindData(true);
      this.fill_chart(var1, var3, var10, var6);
      var1.animateX(500);
      var1.getLegend().setForm(Legend.LegendForm.EMPTY);
   }

   private void init_resume() {
      if (this.handler == null) {
         this.start_handler();
      }

   }

   private void start_handler() {
      this.handler = new Handler() {
         public void handleMessage(Message var1) {
            ActivityDiveLogEdit.TASK var2 = ActivityDiveLogEdit.TASK.values()[var1.what];
            if (null.$SwitchMap$com$crest$divestory$ui$logs$ActivityDiveLogEdit$TASK[var2.ordinal()] == 1) {
               AppBase.notifyChangeDiveLogList();
            }

         }
      };
   }

   public void delete() {
      String var1 = ((DataStruct.DiveLog)WatchOp.dive_logs_list.get(this.index)).watch_serial_number;
      long var2 = ((DataStruct.DiveLog)WatchOp.dive_logs_list.get(this.index)).dive_log_index;
      var2 = ((DataStruct.DiveLog)WatchOp.dive_logs_list.get(this.index)).start_time;
      AppBase.dbOp.deleteDiveProfileDataByStartTime(var1, var2);
      AppBase.dbOp.deleteDiveLogByStartTime(var1, var2);
      WatchOp.dive_logs.deleteDiveLogByStartTime(var1, var2);
      WatchOp.dive_logs_list.remove(this.index);
   }

   public void exit_activity() {
      this.finish();
   }

   public void onBackPressed() {
      this.exit_activity();
   }

   public void onConfigurationChanged(Configuration var1) {
      super.onConfigurationChanged(var1);
      this.init_UI();
      this.init_resume();
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.index = this.getIntent().getIntExtra("index", 0);
      this.init_UI();
   }

   public boolean onCreateOptionsMenu(Menu var1) {
      this.getMenuInflater().inflate(2131492865, var1);
      this.menuItemSave = var1.getItem(0);
      this.menuItemFavorite = var1.getItem(1);
      if (this.diveLog.isFavorite) {
         this.menuItemFavorite.setIcon(2131165422);
      } else {
         this.menuItemFavorite.setIcon(2131165421);
      }

      return true;
   }

   public void onDestroy() {
      super.onDestroy();
   }

   public boolean onOptionsItemSelected(MenuItem var1) {
      switch(var1.getItemId()) {
      case 16908332:
         this.exit_activity();
         break;
      case 2131230788:
         ConfirmDialog var10001 = new ConfirmDialog(this.context, this.getString(2131689587), this.getString(2131689571), this.getString(2131689536)) {
            public void OnConfirmed() {
               super.OnConfirmed();
               ActivityDiveLogEdit.this.delete();
               ActivityDiveLogEdit.this.handler.obtainMessage(ActivityDiveLogEdit.TASK.NOTIFY_DATASET_CHANGED.ordinal()).sendToTarget();
               ActivityDiveLogEdit.this.exit_activity();
            }
         };
         break;
      case 2131230790:
         DataStruct.DiveLog var2 = this.diveLog;
         var2.isFavorite ^= true;
         if (this.diveLog.isFavorite) {
            var1.setIcon(2131165422);
         } else {
            var1.setIcon(2131165421);
         }

         this.menuItemSave.setChecked(true);
         this.menuItemSave.setIcon(2131165342);
         this.handler.obtainMessage(ActivityDiveLogEdit.TASK.NOTIFY_DATASET_CHANGED.ordinal()).sendToTarget();
         break;
      case 2131230798:
         if (var1.isChecked()) {
            this.save();
            this.handler.obtainMessage(ActivityDiveLogEdit.TASK.NOTIFY_DATASET_CHANGED.ordinal()).sendToTarget();
         }
      }

      return super.onOptionsItemSelected(var1);
   }

   protected void onResume() {
      super.onResume();
      this.init_resume();
   }

   public void prepareView(final EditText var1, boolean var2) {
      if (var2) {
         var1.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View var1x, boolean var2) {
               if (var2) {
                  var1.setBackgroundColor(ActivityDiveLogEdit.this.getResources().getColor(2131034179));
                  var1.setTextColor(ActivityDiveLogEdit.this.getResources().getColor(2131034159));
               } else {
                  var1.setBackgroundColor(ActivityDiveLogEdit.this.getResources().getColor(2131034159));
                  var1.setTextColor(ActivityDiveLogEdit.this.getResources().getColor(2131034163));
               }

            }
         });
      }

      var1.addTextChangedListener(new TextWatcher() {
         public void afterTextChanged(Editable var1) {
            ActivityDiveLogEdit.this.menuItemSave.setChecked(true);
            ActivityDiveLogEdit.this.menuItemSave.setIcon(2131165342);
         }

         public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {
         }

         public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
         }
      });
   }

   public void save() {
      this.diveLog.location = this.location.getText().toString();
      this.diveLog.rating = (int)this.ratingBar.getRatingNum();
      this.diveLog.note = this.note.getText().toString();
      this.diveLog.breathing_gas = this.breathing_gas.getText().toString();
      this.diveLog.cylinder_capacity = Float.parseFloat(this.cylinder_capacity.getText().toString());
      this.diveLog.pressure_start = Integer.parseInt(this.pressure_start.getText().toString());
      this.diveLog.pressure_end = Integer.parseInt(this.pressure_end.getText().toString());
      this.diveLog.O2_ratio = Integer.parseInt(this.O2_ratio.getText().toString());
      float var1 = Float.parseFloat(this.aux_weight.getText().toString());
      float var2 = Float.parseFloat(this.visibility.getText().toString());
      float var3 = Float.parseFloat(this.surface_temperature.getText().toString());
      if (AppBase.display_unit == AppBase.UNITS.metric) {
         this.diveLog.aux_weight = var1;
         this.diveLog.visibility = var2;
         this.diveLog.surface_temperature = var3 * 10.0F;
      } else {
         this.diveLog.aux_weight = MathOp.weightPound2Kg(var1);
         this.diveLog.visibility = WatchOp.lengthFoot2Meter(var2);
         this.diveLog.surface_temperature = MathOp.temperatureF2C(var3) * 10.0F;
      }

      this.diveLog.weather = this.weather.getText().toString();
      this.diveLog.wind = this.wind.getText().toString();
      this.diveLog.wave = this.wave.getText().toString();
      this.diveLog.buddy = this.buddy.getText().toString();
      AppBase.dbOp.updateDiveLog(this.diveLog);
      WatchOp.dive_logs.updateDiveLog(this.diveLog);
      WatchOp.dive_logs_list.set(this.index, this.diveLog);
      this.menuItemSave.setChecked(false);
      this.menuItemSave.setIcon(2131165343);
   }

   static enum TASK {
      NOTIFY_DATASET_CHANGED;

      static {
         ActivityDiveLogEdit.TASK var0 = new ActivityDiveLogEdit.TASK("NOTIFY_DATASET_CHANGED", 0);
         NOTIFY_DATASET_CHANGED = var0;
      }
   }
}
