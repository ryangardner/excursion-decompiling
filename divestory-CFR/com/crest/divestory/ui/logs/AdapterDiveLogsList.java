/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.RelativeLayout
 *  android.widget.TextView
 */
package com.crest.divestory.ui.logs;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.crest.divestory.AppBase;
import com.crest.divestory.DataStruct;
import com.crest.divestory.DbOp;
import com.crest.divestory.WatchOp;
import com.crest.divestory.ui.logs.ActivityDiveLogEdit;
import com.syntak.library.MathOp;
import com.syntak.library.TimeOp;
import com.syntak.library.ui.ConfirmDialog;
import com.syntak.library.ui.SlideViewByClick;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterDiveLogsList
extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private String serial_number;

    public AdapterDiveLogsList(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from((Context)context);
    }

    void delete(String string2, long l) {
        AppBase.dbOp.deleteDiveProfileDataByStartTime(string2, l);
        AppBase.dbOp.deleteDiveLogByStartTime(string2, l);
        WatchOp.dive_logs.deleteDiveLogByStartTime(string2, l);
        WatchOp.dive_logs.deleteDiveLogInListByStartTime(WatchOp.dive_logs_list, string2, l);
        this.notifyDataSetChanged();
    }

    void dive_log_edit(Context context, int n) {
        Intent intent = new Intent();
        intent.setClass(context, ActivityDiveLogEdit.class);
        intent.putExtra("index", n);
        context.startActivity(intent);
    }

    public int getCount() {
        return WatchOp.dive_logs_list.size();
    }

    public Object getItem(int n) {
        return WatchOp.dive_logs_list.get(n);
    }

    public long getItemId(int n) {
        return n;
    }

    public View getView(int n, View view, ViewGroup object) {
        DataStruct.DiveLog diveLog;
        if (view == null) {
            object = new ViewHolder();
            view = this.inflater.inflate(2131427453, null);
            object.row_dive_log = (RelativeLayout)view.findViewById(2131231249);
            object.main_content = (LinearLayout)view.findViewById(2131231143);
            object.tv_dive_log_index = (TextView)view.findViewById(2131230982);
            object.date = (TextView)view.findViewById(2131230944);
            object.time = (TextView)view.findViewById(2131231403);
            object.lowest_temperature = (TextView)view.findViewById(2131231141);
            object.duration = (TextView)view.findViewById(2131230997);
            object.max_depth = (TextView)view.findViewById(2131231146);
            object.locaiton = (TextView)view.findViewById(2131231137);
            object.favorite = (ImageView)view.findViewById(2131231015);
            object.button_L = (ImageView)view.findViewById(2131230862);
            object.button_R = (ImageView)view.findViewById(2131230863);
            object.no_profile = (TextView)view.findViewById(2131231192);
            object.main_content.setTag((Object)n);
            view.setTag(object);
            object.slideViewByClick = new SlideViewByClick((View)object.main_content, (View)object.button_R, SlideViewByClick.SLIDE_DIRECTION.HORIZONTAL, SlideViewByClick.TOWARD.LEAD){

                @Override
                public void OnFrontClick(View object) {
                    super.OnFrontClick((View)object);
                    int n = ((ViewHolder)object.getTag((int)2131231354)).position;
                    object = AdapterDiveLogsList.this;
                    ((AdapterDiveLogsList)((Object)object)).dive_log_edit(((AdapterDiveLogsList)((Object)object)).context, n);
                }
            };
            object.slideViewByClick.start();
        } else {
            object = (ViewHolder)view.getTag();
        }
        object.diveLog = diveLog = WatchOp.dive_logs_list.get(n);
        object.position = n;
        long l = Math.min(999L, diveLog.dive_log_index);
        Object object2 = object.tv_dive_log_index;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("#");
        stringBuilder.append(String.format(Locale.ENGLISH, "%03d", l));
        object2.setText((CharSequence)stringBuilder.toString());
        object.date.setText((CharSequence)TimeOp.MsToYearMonthDay(diveLog.start_time));
        object.time.setText((CharSequence)TimeOp.MsToHourMinute(diveLog.start_time));
        object.duration.setText((CharSequence)TimeOp.get_Minute_Second_String_from_Seconds((int)diveLog.duration));
        object.locaiton.setText((CharSequence)diveLog.location);
        object.favorite.setSelected(diveLog.isFavorite);
        object.button_L.setSelected(diveLog.isFavorite);
        object.dive_log_index = diveLog.dive_log_index;
        object.start_time = diveLog.start_time;
        object.main_content.setTag(2131231354, object);
        float f = WatchOp.convertAbsMbarToMeter(diveLog.max_depth);
        float f2 = (float)diveLog.lowest_water_temperature / 10.0f;
        if (AppBase.display_unit == AppBase.UNITS.metric) {
            stringBuilder = object.max_depth;
            Locale locale = Locale.ENGLISH;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("%.1f ");
            ((StringBuilder)object2).append(this.context.getString(2131689688));
            stringBuilder.setText((CharSequence)String.format(locale, ((StringBuilder)object2).toString(), Float.valueOf(f)));
            object2 = object.lowest_temperature;
            locale = Locale.ENGLISH;
            stringBuilder = new StringBuilder();
            stringBuilder.append("%d ");
            stringBuilder.append(this.context.getString(2131689584));
            object2.setText((CharSequence)String.format(locale, stringBuilder.toString(), (int)f2));
        } else {
            stringBuilder = object.max_depth;
            object2 = Locale.ENGLISH;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("%.1f ");
            stringBuilder2.append(this.context.getString(2131689629));
            stringBuilder.setText((CharSequence)String.format((Locale)object2, stringBuilder2.toString(), Float.valueOf(WatchOp.lengthMeter2Foot(f))));
            stringBuilder2 = object.lowest_temperature;
            object2 = Locale.ENGLISH;
            stringBuilder = new StringBuilder();
            stringBuilder.append("%d ");
            stringBuilder.append(this.context.getString(2131689585));
            stringBuilder2.setText((CharSequence)String.format((Locale)object2, stringBuilder.toString(), (int)MathOp.temperatureC2F(f2)));
        }
        object.button_L.setOnClickListener(new View.OnClickListener((ViewHolder)object, diveLog){
            final /* synthetic */ DataStruct.DiveLog val$diveLog;
            final /* synthetic */ ViewHolder val$holder;
            {
                this.val$holder = viewHolder;
                this.val$diveLog = diveLog;
            }

            public void onClick(View view) {
                this.val$holder.button_L.setSelected(this.val$holder.favorite.isSelected() ^ true);
                this.val$holder.favorite.setSelected(this.val$holder.button_L.isSelected());
                this.val$diveLog.isFavorite = this.val$holder.favorite.isSelected();
                AppBase.dbOp.updateDiveLogFavorite(this.val$diveLog.watch_serial_number, this.val$diveLog.start_time, this.val$diveLog.isFavorite);
                AdapterDiveLogsList.this.notifyDataSetChanged();
            }
        });
        object.favorite.setOnClickListener(new View.OnClickListener((ViewHolder)object, diveLog){
            final /* synthetic */ DataStruct.DiveLog val$diveLog;
            final /* synthetic */ ViewHolder val$holder;
            {
                this.val$holder = viewHolder;
                this.val$diveLog = diveLog;
            }

            public void onClick(View view) {
                this.val$holder.favorite.setSelected(this.val$holder.favorite.isSelected() ^ true);
                this.val$diveLog.isFavorite = this.val$holder.favorite.isSelected();
                AppBase.dbOp.updateDiveLogFavorite(this.val$diveLog.watch_serial_number, this.val$diveLog.start_time, this.val$diveLog.isFavorite);
                AdapterDiveLogsList.this.notifyDataSetChanged();
            }
        });
        object.button_R.setOnClickListener(new View.OnClickListener((ViewHolder)object){
            final /* synthetic */ ViewHolder val$holder;
            {
                this.val$holder = viewHolder;
            }

            public void onClick(View object) {
                object = diveLog.watch_serial_number;
                long l = this.val$holder.dive_log_index;
                l = this.val$holder.start_time;
                new ConfirmDialog(AdapterDiveLogsList.this.context, AdapterDiveLogsList.this.context.getString(2131689587), AdapterDiveLogsList.this.context.getString(2131689571), AdapterDiveLogsList.this.context.getString(2131689536), (String)object, l){
                    final /* synthetic */ String val$serial_number;
                    final /* synthetic */ long val$start_time;
                    {
                        this.val$serial_number = string5;
                        this.val$start_time = l;
                        super(context, string2, string3, string4);
                    }

                    @Override
                    public void OnConfirmed() {
                        super.OnConfirmed();
                        val$holder.slideViewByClick.stop();
                        AdapterDiveLogsList.this.delete(this.val$serial_number, this.val$start_time);
                    }
                };
            }

        });
        if (WatchOp.isOldFirmware(diveLog.firmware_version)) {
            object.no_profile.setVisibility(0);
            return view;
        }
        object.no_profile.setVisibility(8);
        return view;
    }

    static class ViewHolder {
        public ImageView button_L;
        public ImageView button_R;
        public TextView date;
        DataStruct.DiveLog diveLog;
        long dive_log_index;
        public TextView duration;
        public ImageView favorite;
        public TextView locaiton;
        public TextView lowest_temperature;
        public LinearLayout main_content;
        public TextView max_depth;
        public TextView no_profile;
        int position;
        public RelativeLayout row_dive_log;
        public SlideViewByClick slideViewByClick;
        long start_time;
        public TextView time;
        public TextView tv_dive_log_index;

        ViewHolder() {
        }
    }

}

