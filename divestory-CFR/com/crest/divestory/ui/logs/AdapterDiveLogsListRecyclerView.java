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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.crest.divestory.AppBase;
import com.crest.divestory.DataStruct;
import com.crest.divestory.DbOp;
import com.crest.divestory.WatchOp;
import com.crest.divestory.ui.logs.ActivityDiveLogEdit;
import com.crest.divestory.ui.logs.FragmentDiveLogsList;
import com.syntak.library.MathOp;
import com.syntak.library.TimeOp;
import com.syntak.library.ui.ConfirmDialog;
import com.syntak.library.ui.SlideViewByClick;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdapterDiveLogsListRecyclerView
extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    private final List<DataStruct.DiveLog> diveLogs;
    private float width_button_R;

    public AdapterDiveLogsListRecyclerView(Context context, List<DataStruct.DiveLog> list, FragmentDiveLogsList.OnDiveLogsFragmentInteractionListener onDiveLogsFragmentInteractionListener) {
        this.diveLogs = list;
        this.context = context;
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

    @Override
    public int getItemCount() {
        return this.diveLogs.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int n) {
        final DataStruct.DiveLog diveLog = this.diveLogs.get(n);
        viewHolder.slideViewByClick.reset();
        viewHolder.diveLog = diveLog;
        viewHolder.position = n;
        long l = Math.min(999L, diveLog.dive_log_index);
        Object object = viewHolder.tv_dive_log_index;
        Serializable serializable = new StringBuilder();
        ((StringBuilder)serializable).append("#");
        ((StringBuilder)serializable).append(String.format(Locale.ENGLISH, "%03d", l));
        object.setText((CharSequence)((StringBuilder)serializable).toString());
        viewHolder.date.setText((CharSequence)TimeOp.MsToYearMonthDay(diveLog.start_time));
        viewHolder.time.setText((CharSequence)TimeOp.MsToHourMinute(diveLog.start_time));
        viewHolder.duration.setText((CharSequence)TimeOp.get_Minute_Second_String_from_Seconds((int)diveLog.duration));
        viewHolder.locaiton.setText((CharSequence)diveLog.location);
        viewHolder.favorite.setSelected(diveLog.isFavorite);
        viewHolder.button_L.setSelected(diveLog.isFavorite);
        viewHolder.dive_log_index = diveLog.dive_log_index;
        viewHolder.start_time = diveLog.start_time;
        viewHolder.main_content.setTag(2131231354, (Object)viewHolder);
        float f = WatchOp.convertAbsMbarToMeter((int)diveLog.max_depth);
        float f2 = (float)diveLog.lowest_water_temperature / 10.0f;
        if (AppBase.display_unit == AppBase.UNITS.metric) {
            serializable = viewHolder.max_depth;
            object = Locale.ENGLISH;
            Serializable serializable2 = new StringBuilder();
            serializable2.append("%.1f ");
            serializable2.append(this.context.getString(2131689688));
            serializable.setText((CharSequence)String.format((Locale)object, serializable2.toString(), Float.valueOf(f)));
            object = viewHolder.lowest_temperature;
            serializable2 = Locale.ENGLISH;
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("%d ");
            ((StringBuilder)serializable).append(this.context.getString(2131689584));
            object.setText((CharSequence)String.format((Locale)serializable2, ((StringBuilder)serializable).toString(), (int)f2));
        } else {
            Object object2 = viewHolder.max_depth;
            serializable = Locale.ENGLISH;
            object = new StringBuilder();
            ((StringBuilder)object).append("%.1f ");
            ((StringBuilder)object).append(this.context.getString(2131689629));
            object2.setText((CharSequence)String.format((Locale)serializable, ((StringBuilder)object).toString(), Float.valueOf(WatchOp.lengthMeter2Foot(f))));
            serializable = viewHolder.lowest_temperature;
            object = Locale.ENGLISH;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("%d ");
            ((StringBuilder)object2).append(this.context.getString(2131689585));
            serializable.setText((CharSequence)String.format((Locale)object, ((StringBuilder)object2).toString(), (int)MathOp.temperatureC2F(f2)));
        }
        viewHolder.button_L.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                viewHolder.button_L.setSelected(viewHolder.favorite.isSelected() ^ true);
                viewHolder.favorite.setSelected(viewHolder.button_L.isSelected());
                diveLog.isFavorite = viewHolder.favorite.isSelected();
                AppBase.dbOp.updateDiveLogFavorite(diveLog.watch_serial_number, diveLog.start_time, diveLog.isFavorite);
                AdapterDiveLogsListRecyclerView.this.notifyDataSetChanged();
            }
        });
        viewHolder.favorite.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                viewHolder.favorite.setSelected(viewHolder.favorite.isSelected() ^ true);
                diveLog.isFavorite = viewHolder.favorite.isSelected();
                AppBase.dbOp.updateDiveLogFavorite(diveLog.watch_serial_number, diveLog.start_time, diveLog.isFavorite);
                AdapterDiveLogsListRecyclerView.this.notifyDataSetChanged();
            }
        });
        viewHolder.button_R.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                object = diveLog.watch_serial_number;
                long l = viewHolder.dive_log_index;
                l = viewHolder.start_time;
                new ConfirmDialog(AdapterDiveLogsListRecyclerView.this.context, AdapterDiveLogsListRecyclerView.this.context.getString(2131689587), AdapterDiveLogsListRecyclerView.this.context.getString(2131689571), AdapterDiveLogsListRecyclerView.this.context.getString(2131689536), (String)object, l){
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
                        viewHolder.slideViewByClick.stop();
                        AdapterDiveLogsListRecyclerView.this.delete(this.val$serial_number, this.val$start_time);
                    }
                };
            }

        });
        if (WatchOp.isOldFirmware(diveLog.firmware_version)) {
            viewHolder.no_profile.setVisibility(0);
            return;
        }
        viewHolder.no_profile.setVisibility(8);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int n) {
        return new ViewHolder(LayoutInflater.from((Context)viewGroup.getContext()).inflate(2131427453, viewGroup, false));
    }

    public class ViewHolder
    extends RecyclerView.ViewHolder {
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
        public DataStruct.MyWatch myWatch;
        public TextView no_profile;
        int position;
        public RelativeLayout row_dive_log;
        public SlideViewByClick slideViewByClick;
        long start_time;
        public TextView time;
        public TextView tv_dive_log_index;

        public ViewHolder(View view) {
            super(view);
            this.row_dive_log = (RelativeLayout)view;
            this.main_content = (LinearLayout)view.findViewById(2131231143);
            this.tv_dive_log_index = (TextView)view.findViewById(2131230982);
            this.date = (TextView)view.findViewById(2131230944);
            this.time = (TextView)view.findViewById(2131231403);
            this.lowest_temperature = (TextView)view.findViewById(2131231141);
            this.duration = (TextView)view.findViewById(2131230997);
            this.max_depth = (TextView)view.findViewById(2131231146);
            this.locaiton = (TextView)view.findViewById(2131231137);
            this.favorite = (ImageView)view.findViewById(2131231015);
            this.button_L = (ImageView)view.findViewById(2131230862);
            this.button_R = (ImageView)view.findViewById(2131230863);
            this.no_profile = (TextView)view.findViewById(2131231192);
            this.slideViewByClick = AdapterDiveLogsListRecyclerView.this = new SlideViewByClick((View)this.main_content, (View)this.button_R, SlideViewByClick.SLIDE_DIRECTION.HORIZONTAL, SlideViewByClick.TOWARD.LEAD, (AdapterDiveLogsListRecyclerView)AdapterDiveLogsListRecyclerView.this){
                final /* synthetic */ AdapterDiveLogsListRecyclerView val$this$0;
                {
                    this.val$this$0 = adapterDiveLogsListRecyclerView;
                    super(view, view2, sLIDE_DIRECTION, tOWARD);
                }

                @Override
                public void OnFrontClick(View view) {
                    super.OnFrontClick(view);
                    int n = ((ViewHolder)view.getTag((int)2131231354)).position;
                    AdapterDiveLogsListRecyclerView.this.dive_log_edit(AdapterDiveLogsListRecyclerView.this.context, n);
                }
            };
            ((SlideViewByClick)AdapterDiveLogsListRecyclerView.this).start();
        }

    }

}

