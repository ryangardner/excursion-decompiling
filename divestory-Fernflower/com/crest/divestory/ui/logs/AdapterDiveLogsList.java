package com.crest.divestory.ui.logs;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.crest.divestory.AppBase;
import com.crest.divestory.DataStruct;
import com.crest.divestory.WatchOp;
import com.syntak.library.MathOp;
import com.syntak.library.TimeOp;
import com.syntak.library.ui.ConfirmDialog;
import com.syntak.library.ui.SlideViewByClick;
import java.util.Locale;

public class AdapterDiveLogsList extends BaseAdapter {
   private Context context;
   private LayoutInflater inflater;
   private String serial_number;

   public AdapterDiveLogsList(Context var1) {
      this.context = var1;
      this.inflater = LayoutInflater.from(var1);
   }

   void delete(String var1, long var2) {
      AppBase.dbOp.deleteDiveProfileDataByStartTime(var1, var2);
      AppBase.dbOp.deleteDiveLogByStartTime(var1, var2);
      WatchOp.dive_logs.deleteDiveLogByStartTime(var1, var2);
      WatchOp.dive_logs.deleteDiveLogInListByStartTime(WatchOp.dive_logs_list, var1, var2);
      this.notifyDataSetChanged();
   }

   void dive_log_edit(Context var1, int var2) {
      Intent var3 = new Intent();
      var3.setClass(var1, ActivityDiveLogEdit.class);
      var3.putExtra("index", var2);
      var1.startActivity(var3);
   }

   public int getCount() {
      return WatchOp.dive_logs_list.size();
   }

   public Object getItem(int var1) {
      return WatchOp.dive_logs_list.get(var1);
   }

   public long getItemId(int var1) {
      return (long)var1;
   }

   public View getView(int var1, View var2, ViewGroup var3) {
      final AdapterDiveLogsList.ViewHolder var12;
      if (var2 == null) {
         var12 = new AdapterDiveLogsList.ViewHolder();
         var2 = this.inflater.inflate(2131427453, (ViewGroup)null);
         var12.row_dive_log = (RelativeLayout)var2.findViewById(2131231249);
         var12.main_content = (LinearLayout)var2.findViewById(2131231143);
         var12.tv_dive_log_index = (TextView)var2.findViewById(2131230982);
         var12.date = (TextView)var2.findViewById(2131230944);
         var12.time = (TextView)var2.findViewById(2131231403);
         var12.lowest_temperature = (TextView)var2.findViewById(2131231141);
         var12.duration = (TextView)var2.findViewById(2131230997);
         var12.max_depth = (TextView)var2.findViewById(2131231146);
         var12.locaiton = (TextView)var2.findViewById(2131231137);
         var12.favorite = (ImageView)var2.findViewById(2131231015);
         var12.button_L = (ImageView)var2.findViewById(2131230862);
         var12.button_R = (ImageView)var2.findViewById(2131230863);
         var12.no_profile = (TextView)var2.findViewById(2131231192);
         var12.main_content.setTag(var1);
         var2.setTag(var12);
         var12.slideViewByClick = new SlideViewByClick(var12.main_content, var12.button_R, SlideViewByClick.SLIDE_DIRECTION.HORIZONTAL, SlideViewByClick.TOWARD.LEAD) {
            public void OnFrontClick(View var1) {
               super.OnFrontClick(var1);
               int var2 = ((AdapterDiveLogsList.ViewHolder)var1.getTag(2131231354)).position;
               AdapterDiveLogsList var3 = AdapterDiveLogsList.this;
               var3.dive_log_edit(var3.context, var2);
            }
         };
         var12.slideViewByClick.start();
      } else {
         var12 = (AdapterDiveLogsList.ViewHolder)var2.getTag();
      }

      final DataStruct.DiveLog var4 = (DataStruct.DiveLog)WatchOp.dive_logs_list.get(var1);
      var12.diveLog = var4;
      var12.position = var1;
      long var5 = Math.min(999L, var4.dive_log_index);
      TextView var7 = var12.tv_dive_log_index;
      StringBuilder var8 = new StringBuilder();
      var8.append("#");
      var8.append(String.format(Locale.ENGLISH, "%03d", var5));
      var7.setText(var8.toString());
      var12.date.setText(TimeOp.MsToYearMonthDay(var4.start_time));
      var12.time.setText(TimeOp.MsToHourMinute(var4.start_time));
      var12.duration.setText(TimeOp.get_Minute_Second_String_from_Seconds((int)var4.duration));
      var12.locaiton.setText(var4.location);
      var12.favorite.setSelected(var4.isFavorite);
      var12.button_L.setSelected(var4.isFavorite);
      var12.dive_log_index = var4.dive_log_index;
      var12.start_time = var4.start_time;
      var12.main_content.setTag(2131231354, var12);
      float var9 = WatchOp.convertAbsMbarToMeter((float)var4.max_depth);
      float var10 = (float)var4.lowest_water_temperature / 10.0F;
      TextView var15;
      if (AppBase.display_unit == AppBase.UNITS.metric) {
         var15 = var12.max_depth;
         Locale var11 = Locale.ENGLISH;
         StringBuilder var13 = new StringBuilder();
         var13.append("%.1f ");
         var13.append(this.context.getString(2131689688));
         var15.setText(String.format(var11, var13.toString(), var9));
         var7 = var12.lowest_temperature;
         var11 = Locale.ENGLISH;
         var8 = new StringBuilder();
         var8.append("%d ");
         var8.append(this.context.getString(2131689584));
         var7.setText(String.format(var11, var8.toString(), (int)var10));
      } else {
         var15 = var12.max_depth;
         Locale var14 = Locale.ENGLISH;
         StringBuilder var16 = new StringBuilder();
         var16.append("%.1f ");
         var16.append(this.context.getString(2131689629));
         var15.setText(String.format(var14, var16.toString(), WatchOp.lengthMeter2Foot(var9)));
         TextView var17 = var12.lowest_temperature;
         var14 = Locale.ENGLISH;
         var8 = new StringBuilder();
         var8.append("%d ");
         var8.append(this.context.getString(2131689585));
         var17.setText(String.format(var14, var8.toString(), (int)MathOp.temperatureC2F(var10)));
      }

      var12.button_L.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            var12.button_L.setSelected(var12.favorite.isSelected() ^ true);
            var12.favorite.setSelected(var12.button_L.isSelected());
            var4.isFavorite = var12.favorite.isSelected();
            AppBase.dbOp.updateDiveLogFavorite(var4.watch_serial_number, var4.start_time, var4.isFavorite);
            AdapterDiveLogsList.this.notifyDataSetChanged();
         }
      });
      var12.favorite.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            var12.favorite.setSelected(var12.favorite.isSelected() ^ true);
            var4.isFavorite = var12.favorite.isSelected();
            AppBase.dbOp.updateDiveLogFavorite(var4.watch_serial_number, var4.start_time, var4.isFavorite);
            AdapterDiveLogsList.this.notifyDataSetChanged();
         }
      });
      var12.button_R.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            final String var4x = var4.watch_serial_number;
            final long var2 = var12.dive_log_index;
            var2 = var12.start_time;
            ConfirmDialog var10001 = new ConfirmDialog(AdapterDiveLogsList.this.context, AdapterDiveLogsList.this.context.getString(2131689587), AdapterDiveLogsList.this.context.getString(2131689571), AdapterDiveLogsList.this.context.getString(2131689536)) {
               public void OnConfirmed() {
                  super.OnConfirmed();
                  var12.slideViewByClick.stop();
                  AdapterDiveLogsList.this.delete(var4x, var2);
               }
            };
         }
      });
      if (WatchOp.isOldFirmware(var4.firmware_version)) {
         var12.no_profile.setVisibility(0);
      } else {
         var12.no_profile.setVisibility(8);
      }

      return var2;
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
   }
}
