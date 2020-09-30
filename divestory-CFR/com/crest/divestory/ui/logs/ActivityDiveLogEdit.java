/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.database.Cursor
 *  android.graphics.DashPathEffect
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  android.text.Editable
 *  android.text.TextWatcher
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnFocusChangeListener
 *  android.view.View$OnTouchListener
 *  android.view.ViewParent
 *  android.widget.EditText
 *  android.widget.TextView
 */
package com.crest.divestory.ui.logs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.crest.divestory.AppBase;
import com.crest.divestory.DataStruct;
import com.crest.divestory.DbOp;
import com.crest.divestory.WatchOp;
import com.crest.divestory.ui.logs.MyMarkerView;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.ComponentBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BaseDataSet;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.LineRadarDataSet;
import com.github.mikephil.charting.data.LineScatterCandleRadarDataSet;
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
import java.util.List;
import java.util.Locale;

public class ActivityDiveLogEdit
extends AppCompatActivity {
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

    private void fill_chart(final LineChart lineChart, DataStruct.DiveProfileData object, YAxis object2, WatchOp.X_AXIS_UNIT x_AXIS_UNIT) {
        ArrayList<Entry> arrayList = new ArrayList<Entry>();
        float f = 0.0f;
        float f2 = 0.0f;
        for (int i = 0; i < ((DataStruct.DiveProfileData)object).length(); ++i) {
            float f3;
            DataStruct.DiveProfileDatum diveProfileDatum = ((DataStruct.DiveProfileData)object).list.get(i);
            float f4 = diveProfileDatum.time_elpased / 60.0f;
            if (x_AXIS_UNIT == WatchOp.X_AXIS_UNIT.SECOND) {
                f4 = diveProfileDatum.time_elpased;
            }
            float f5 = WatchOp.convertAbsMbarToMeter(diveProfileDatum.depth);
            if (AppBase.display_unit != AppBase.UNITS.metric) {
                f5 = WatchOp.lengthMeter2Foot(f5);
            }
            if (i == 0) {
                f3 = f = f5;
            } else {
                float f6 = f;
                if (f5 > f) {
                    f6 = f5;
                }
                f = f6;
                f3 = f2;
                if (f5 < f2) {
                    f3 = f5;
                    f = f6;
                }
            }
            arrayList.add(new Entry(f4, f5));
            f2 = f3;
        }
        ((AxisBase)object2).setAxisMaximum(f * 1.1f);
        ((AxisBase)object2).setAxisMinimum(f2 * 0.9f);
        if (lineChart.getData() != null && ((LineData)lineChart.getData()).getDataSetCount() > 0) {
            object = (LineDataSet)((LineData)lineChart.getData()).getDataSetByIndex(0);
            ((DataSet)object).setValues(arrayList);
            ((BaseDataSet)object).notifyDataSetChanged();
            ((LineData)lineChart.getData()).notifyDataChanged();
            lineChart.notifyDataSetChanged();
            return;
        }
        object = new LineDataSet(arrayList, "");
        ((BaseDataSet)object).setDrawIcons(false);
        ((BaseDataSet)object).setColor(-1);
        ((LineDataSet)object).setCircleColor(-16711681);
        ((LineRadarDataSet)object).setLineWidth(1.0f);
        ((LineDataSet)object).setCircleRadius(2.0f);
        ((LineDataSet)object).setDrawCircleHole(false);
        ((BaseDataSet)object).setFormLineWidth(1.0f);
        ((BaseDataSet)object).setFormLineDashEffect(new DashPathEffect(new float[]{10.0f, 5.0f}, 0.0f));
        ((BaseDataSet)object).setFormSize(15.0f);
        ((BaseDataSet)object).setDrawValues(false);
        ((BaseDataSet)object).setValueTextSize(9.0f);
        ((LineScatterCandleRadarDataSet)object).enableDashedHighlightLine(10.0f, 5.0f, 0.0f);
        ((LineRadarDataSet)object).setDrawFilled(true);
        ((LineDataSet)object).setFillFormatter(new IFillFormatter(){

            @Override
            public float getFillLinePosition(ILineDataSet iLineDataSet, LineDataProvider lineDataProvider) {
                return lineChart.getAxisLeft().getAxisMinimum();
            }
        });
        if (Utils.getSDKInt() >= 18) {
            ((LineRadarDataSet)object).setFillDrawable(ContextCompat.getDrawable(this.context, 2131165321));
        } else {
            ((LineRadarDataSet)object).setFillColor(-16777216);
        }
        object2 = new ArrayList();
        ((ArrayList)object2).add(object);
        lineChart.setData(new LineData((List<ILineDataSet>)object2));
    }

    private void init_UI() {
        this.setContentView(2131427392);
        this.context = this;
        this.activity = this;
        this.init_action_bar();
        this.diveLog = WatchOp.dive_logs_list.get(this.index);
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
        this.ratingBar = (SmartRatingBar)((Object)this.findViewById(2131231238));
        this.buddy = (EditText)this.findViewById(2131230839);
        this.note = (EditText)this.findViewById(2131231195);
        long l = Math.min(999L, this.diveLog.dive_log_index);
        Object object = this.tv_dive_log_index;
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("#");
        ((StringBuilder)object2).append(String.format(Locale.ENGLISH, "%03d", l));
        object.setText((CharSequence)((StringBuilder)object2).toString());
        int n = (int)this.diveLog.dive_type;
        object2 = n != 0 ? (n != 1 ? (n != 2 ? null : this.getString(2131689521)) : this.getString(2131689645)) : this.getString(2131689798);
        this.dive_type.setText((CharSequence)object2);
        this.date.setText((CharSequence)TimeOp.MsToYearMonthDay(this.diveLog.start_time));
        this.time_start.setText((CharSequence)TimeOp.MsToHourMinute(this.diveLog.start_time));
        this.time_end.setText((CharSequence)TimeOp.MsToHourMinute(this.diveLog.end_time));
        this.duration.setText((CharSequence)TimeOp.get_Minute_Second_String_from_Seconds((int)this.diveLog.duration));
        this.ratingBar.setRatingNum(this.diveLog.rating);
        this.location.setText((CharSequence)this.diveLog.location);
        this.note.setText((CharSequence)this.diveLog.note);
        this.breathing_gas.setText((CharSequence)this.diveLog.breathing_gas);
        this.cylinder_capacity.setText((CharSequence)String.valueOf(this.diveLog.cylinder_capacity));
        this.O2_ratio.setText((CharSequence)String.valueOf(this.diveLog.O2_ratio));
        this.pressure_start.setText((CharSequence)String.valueOf(this.diveLog.pressure_start));
        this.pressure_end.setText((CharSequence)String.valueOf(this.diveLog.pressure_end));
        this.weather.setText((CharSequence)this.diveLog.weather);
        this.wind.setText((CharSequence)this.diveLog.wind);
        this.wave.setText((CharSequence)this.diveLog.wave);
        this.buddy.setText((CharSequence)this.diveLog.buddy);
        float f = WatchOp.convertAbsMbarToMeter(this.diveLog.max_depth);
        float f2 = WatchOp.convertAbsMbarToMeter(this.diveLog.average_depth);
        float f3 = this.diveLog.visibility;
        float f4 = (float)this.diveLog.lowest_water_temperature / 10.0f;
        float f5 = this.diveLog.surface_temperature / 10.0f;
        if (AppBase.display_unit == AppBase.UNITS.metric) {
            this.max_depth_unit.setText(2131689688);
            this.average_depth_unit.setText(2131689688);
            this.visibility_unit.setText(2131689688);
            this.surface_temperature_unit.setText(2131689584);
            this.water_temperature_unit.setText(2131689584);
            this.aux_weight_unit.setText(2131689659);
            this.max_depth.setText((CharSequence)String.format(Locale.ENGLISH, "%.1f", Float.valueOf(f)));
            if (f2 < 0.0f) {
                this.average_depth.setText((CharSequence)"N/A");
            } else {
                this.average_depth.setText((CharSequence)String.format(Locale.ENGLISH, "%.1f", Float.valueOf(f2)));
            }
            this.visibility.setText((CharSequence)String.format(Locale.ENGLISH, "%d", (int)f3));
            this.water_temperature.setText((CharSequence)String.format(Locale.ENGLISH, "%d", (int)f4));
            this.surface_temperature.setText((CharSequence)String.format(Locale.ENGLISH, "%d", (int)f5));
            this.aux_weight.setText((CharSequence)String.format(Locale.ENGLISH, "%d", (int)this.diveLog.aux_weight));
        } else {
            this.max_depth_unit.setText(2131689629);
            this.average_depth_unit.setText(2131689629);
            this.visibility_unit.setText(2131689629);
            this.surface_temperature_unit.setText(2131689585);
            this.water_temperature_unit.setText(2131689585);
            this.aux_weight_unit.setText(2131689766);
            this.max_depth.setText((CharSequence)String.format(Locale.ENGLISH, "%.1f", Float.valueOf(WatchOp.lengthMeter2Foot(f))));
            if (f2 < 0.0f) {
                this.average_depth.setText((CharSequence)"N/A");
            } else {
                this.average_depth.setText((CharSequence)String.format(Locale.ENGLISH, "%.1f", Float.valueOf(WatchOp.lengthMeter2Foot(f2))));
            }
            this.visibility.setText((CharSequence)String.format(Locale.ENGLISH, "%d", (int)WatchOp.lengthMeter2Foot(f3)));
            this.water_temperature.setText((CharSequence)String.format(Locale.ENGLISH, "%d", (int)MathOp.temperatureC2F(f4)));
            this.surface_temperature.setText((CharSequence)String.format(Locale.ENGLISH, "%d", (int)MathOp.temperatureC2F(f5)));
            this.aux_weight.setText((CharSequence)String.format(Locale.ENGLISH, "%d", (int)MathOp.weightKg2Pound(this.diveLog.aux_weight)));
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
        this.ratingBar.setOnRatingBarChangeListener(new SmartRatingBar.OnRatingBarChangeListener(){

            @Override
            public void onRatingChanged(SmartRatingBar smartRatingBar, float f) {
                ActivityDiveLogEdit.this.menuItemSave.setChecked(true);
                ActivityDiveLogEdit.this.menuItemSave.setIcon(2131165342);
            }
        });
        LineChart lineChart = (LineChart)this.findViewById(2131230983);
        if (this.diveLog.profile_data_length > 0L) {
            this.diveProfileData = object2 = new DataStruct.DiveProfileData(AppBase.dbOp.getDiveProfileData(this.diveLog.watch_serial_number, this.diveLog.start_time));
            object = this.diveLog;
            this.init_chart(lineChart, (DataStruct.DiveLog)object, (DataStruct.DiveProfileData)object2, object.start_time);
        } else {
            lineChart.setVisibility(8);
        }
        this.note.setOnTouchListener(new View.OnTouchListener(){

            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!view.hasFocus()) return false;
                view.getParent().requestDisallowInterceptTouchEvent(true);
                if ((motionEvent.getAction() & 255) != 8) {
                    return false;
                }
                view.getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });
    }

    private void init_action_bar() {
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(2131165356);
        AppBase.setTitleAndColor((Activity)this, 2131689611, 2131034179);
    }

    private void init_chart(LineChart lineChart, DataStruct.DiveLog object, DataStruct.DiveProfileData diveProfileData, long l) {
        WatchOp.X_AXIS_UNIT x_AXIS_UNIT = WatchOp.X_AXIS_UNIT.MINUTE;
        lineChart.setBackgroundColor(-16777216);
        lineChart.getDescription().setEnabled(false);
        lineChart.setTouchEnabled(true);
        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener(){

            @Override
            public void onNothingSelected() {
            }

            @Override
            public void onValueSelected(Entry entry, Highlight highlight) {
            }
        });
        lineChart.setDrawGridBackground(true);
        lineChart.setGridBackgroundColor(-12303292);
        if (((DataStruct.DiveLog)object).dive_type == 2L) {
            x_AXIS_UNIT = WatchOp.X_AXIS_UNIT.SECOND;
        }
        object = new MyMarkerView(this.context, 2131427369, l, x_AXIS_UNIT);
        ((MarkerView)object).setChartView(lineChart);
        lineChart.setMarker((IMarker)object);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setPinchZoom(true);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.enableGridDashedLine(10.0f, 10.0f, 0.0f);
        xAxis.setTextColor(-1);
        object = lineChart.getAxisLeft();
        ((YAxis)object).setInverted(true);
        lineChart.getAxisRight().setEnabled(false);
        ((AxisBase)object).enableGridDashedLine(10.0f, 10.0f, 0.0f);
        ((AxisBase)object).setAxisMaximum(200.0f);
        ((AxisBase)object).setAxisMinimum(-50.0f);
        ((ComponentBase)object).setTextColor(-1);
        LimitLine limitLine = new LimitLine(9.0f, "Index 10");
        limitLine.setLineWidth(4.0f);
        limitLine.enableDashedLine(10.0f, 10.0f, 0.0f);
        limitLine.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        limitLine.setTextSize(10.0f);
        limitLine = new LimitLine(-30.0f, "Lower Limit");
        limitLine.setLineWidth(4.0f);
        limitLine.enableDashedLine(10.0f, 10.0f, 0.0f);
        limitLine.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        limitLine.setTextSize(10.0f);
        ((AxisBase)object).setDrawLimitLinesBehindData(true);
        xAxis.setDrawLimitLinesBehindData(true);
        this.fill_chart(lineChart, diveProfileData, (YAxis)object, x_AXIS_UNIT);
        lineChart.animateX(500);
        lineChart.getLegend().setForm(Legend.LegendForm.EMPTY);
    }

    private void init_resume() {
        if (this.handler != null) return;
        this.start_handler();
    }

    private void start_handler() {
        this.handler = new Handler(){

            public void handleMessage(Message object) {
                object = TASK.values()[object.what];
                if (9.$SwitchMap$com$crest$divestory$ui$logs$ActivityDiveLogEdit$TASK[object.ordinal()] != 1) {
                    return;
                }
                AppBase.notifyChangeDiveLogList();
            }
        };
    }

    public void delete() {
        String string2 = WatchOp.dive_logs_list.get((int)this.index).watch_serial_number;
        long l = WatchOp.dive_logs_list.get((int)this.index).dive_log_index;
        l = WatchOp.dive_logs_list.get((int)this.index).start_time;
        AppBase.dbOp.deleteDiveProfileDataByStartTime(string2, l);
        AppBase.dbOp.deleteDiveLogByStartTime(string2, l);
        WatchOp.dive_logs.deleteDiveLogByStartTime(string2, l);
        WatchOp.dive_logs_list.remove(this.index);
    }

    public void exit_activity() {
        this.finish();
    }

    @Override
    public void onBackPressed() {
        this.exit_activity();
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.init_UI();
        this.init_resume();
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.index = this.getIntent().getIntExtra("index", 0);
        this.init_UI();
    }

    public boolean onCreateOptionsMenu(Menu menu2) {
        this.getMenuInflater().inflate(2131492865, menu2);
        this.menuItemSave = menu2.getItem(0);
        this.menuItemFavorite = menu2.getItem(1);
        if (this.diveLog.isFavorite) {
            this.menuItemFavorite.setIcon(2131165422);
            return true;
        }
        this.menuItemFavorite.setIcon(2131165421);
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            default: {
                return super.onOptionsItemSelected(menuItem);
            }
            case 2131230798: {
                if (!menuItem.isChecked()) return super.onOptionsItemSelected(menuItem);
                this.save();
                this.handler.obtainMessage(TASK.NOTIFY_DATASET_CHANGED.ordinal()).sendToTarget();
                return super.onOptionsItemSelected(menuItem);
            }
            case 2131230790: {
                DataStruct.DiveLog diveLog = this.diveLog;
                diveLog.isFavorite ^= true;
                if (this.diveLog.isFavorite) {
                    menuItem.setIcon(2131165422);
                } else {
                    menuItem.setIcon(2131165421);
                }
                this.menuItemSave.setChecked(true);
                this.menuItemSave.setIcon(2131165342);
                this.handler.obtainMessage(TASK.NOTIFY_DATASET_CHANGED.ordinal()).sendToTarget();
                return super.onOptionsItemSelected(menuItem);
            }
            case 2131230788: {
                new ConfirmDialog(this.context, this.getString(2131689587), this.getString(2131689571), this.getString(2131689536)){

                    @Override
                    public void OnConfirmed() {
                        super.OnConfirmed();
                        ActivityDiveLogEdit.this.delete();
                        ActivityDiveLogEdit.this.handler.obtainMessage(TASK.NOTIFY_DATASET_CHANGED.ordinal()).sendToTarget();
                        ActivityDiveLogEdit.this.exit_activity();
                    }
                };
                return super.onOptionsItemSelected(menuItem);
            }
            case 16908332: 
        }
        this.exit_activity();
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.init_resume();
    }

    public void prepareView(final EditText editText, boolean bl) {
        if (bl) {
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener(){

                public void onFocusChange(View view, boolean bl) {
                    if (bl) {
                        editText.setBackgroundColor(ActivityDiveLogEdit.this.getResources().getColor(2131034179));
                        editText.setTextColor(ActivityDiveLogEdit.this.getResources().getColor(2131034159));
                        return;
                    }
                    editText.setBackgroundColor(ActivityDiveLogEdit.this.getResources().getColor(2131034159));
                    editText.setTextColor(ActivityDiveLogEdit.this.getResources().getColor(2131034163));
                }
            });
        }
        editText.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable editable) {
                ActivityDiveLogEdit.this.menuItemSave.setChecked(true);
                ActivityDiveLogEdit.this.menuItemSave.setIcon(2131165342);
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
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
        float f = Float.parseFloat(this.aux_weight.getText().toString());
        float f2 = Float.parseFloat(this.visibility.getText().toString());
        float f3 = Float.parseFloat(this.surface_temperature.getText().toString());
        if (AppBase.display_unit == AppBase.UNITS.metric) {
            this.diveLog.aux_weight = f;
            this.diveLog.visibility = f2;
            this.diveLog.surface_temperature = f3 * 10.0f;
        } else {
            this.diveLog.aux_weight = MathOp.weightPound2Kg(f);
            this.diveLog.visibility = WatchOp.lengthFoot2Meter(f2);
            this.diveLog.surface_temperature = MathOp.temperatureF2C(f3) * 10.0f;
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

    static final class TASK
    extends Enum<TASK> {
        private static final /* synthetic */ TASK[] $VALUES;
        public static final /* enum */ TASK NOTIFY_DATASET_CHANGED;

        static {
            TASK tASK;
            NOTIFY_DATASET_CHANGED = tASK = new TASK();
            $VALUES = new TASK[]{tASK};
        }

        public static TASK valueOf(String string2) {
            return Enum.valueOf(TASK.class, string2);
        }

        public static TASK[] values() {
            return (TASK[])$VALUES.clone();
        }
    }

}

