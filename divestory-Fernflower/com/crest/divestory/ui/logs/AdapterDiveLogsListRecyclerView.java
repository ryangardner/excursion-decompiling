package com.crest.divestory.ui.logs;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.crest.divestory.AppBase;
import com.crest.divestory.DataStruct;
import com.crest.divestory.WatchOp;
import com.syntak.library.MathOp;
import com.syntak.library.TimeOp;
import com.syntak.library.ui.ConfirmDialog;
import com.syntak.library.ui.SlideViewByClick;
import java.util.List;
import java.util.Locale;

public class AdapterDiveLogsListRecyclerView extends RecyclerView.Adapter<AdapterDiveLogsListRecyclerView.ViewHolder> {
   private final Context context;
   private final List<DataStruct.DiveLog> diveLogs;
   private float width_button_R;

   public AdapterDiveLogsListRecyclerView(Context var1, List<DataStruct.DiveLog> var2, FragmentDiveLogsList.OnDiveLogsFragmentInteractionListener var3) {
      this.diveLogs = var2;
      this.context = var1;
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

   public int getItemCount() {
      return this.diveLogs.size();
   }

   public void onBindViewHolder(final AdapterDiveLogsListRecyclerView.ViewHolder var1, int var2) {
      final DataStruct.DiveLog var3 = (DataStruct.DiveLog)this.diveLogs.get(var2);
      var1.slideViewByClick.reset();
      var1.diveLog = var3;
      var1.position = var2;
      long var4 = Math.min(999L, var3.dive_log_index);
      TextView var6 = var1.tv_dive_log_index;
      StringBuilder var7 = new StringBuilder();
      var7.append("#");
      var7.append(String.format(Locale.ENGLISH, "%03d", var4));
      var6.setText(var7.toString());
      var1.date.setText(TimeOp.MsToYearMonthDay(var3.start_time));
      var1.time.setText(TimeOp.MsToHourMinute(var3.start_time));
      var1.duration.setText(TimeOp.get_Minute_Second_String_from_Seconds((int)var3.duration));
      var1.locaiton.setText(var3.location);
      var1.favorite.setSelected(var3.isFavorite);
      var1.button_L.setSelected(var3.isFavorite);
      var1.dive_log_index = var3.dive_log_index;
      var1.start_time = var3.start_time;
      var1.main_content.setTag(2131231354, var1);
      float var8 = WatchOp.convertAbsMbarToMeter((float)((int)var3.max_depth));
      float var9 = (float)var3.lowest_water_temperature / 10.0F;
      StringBuilder var10;
      Locale var11;
      TextView var13;
      if (AppBase.display_unit == AppBase.UNITS.metric) {
         var13 = var1.max_depth;
         var11 = Locale.ENGLISH;
         var10 = new StringBuilder();
         var10.append("%.1f ");
         var10.append(this.context.getString(2131689688));
         var13.setText(String.format(var11, var10.toString(), var8));
         var6 = var1.lowest_temperature;
         Locale var15 = Locale.ENGLISH;
         var7 = new StringBuilder();
         var7.append("%d ");
         var7.append(this.context.getString(2131689584));
         var6.setText(String.format(var15, var7.toString(), (int)var9));
      } else {
         TextView var16 = var1.max_depth;
         Locale var14 = Locale.ENGLISH;
         StringBuilder var12 = new StringBuilder();
         var12.append("%.1f ");
         var12.append(this.context.getString(2131689629));
         var16.setText(String.format(var14, var12.toString(), WatchOp.lengthMeter2Foot(var8)));
         var13 = var1.lowest_temperature;
         var11 = Locale.ENGLISH;
         var10 = new StringBuilder();
         var10.append("%d ");
         var10.append(this.context.getString(2131689585));
         var13.setText(String.format(var11, var10.toString(), (int)MathOp.temperatureC2F(var9)));
      }

      var1.button_L.setOnClickListener(new OnClickListener() {
         public void onClick(View var1x) {
            var1.button_L.setSelected(var1.favorite.isSelected() ^ true);
            var1.favorite.setSelected(var1.button_L.isSelected());
            var3.isFavorite = var1.favorite.isSelected();
            AppBase.dbOp.updateDiveLogFavorite(var3.watch_serial_number, var3.start_time, var3.isFavorite);
            AdapterDiveLogsListRecyclerView.this.notifyDataSetChanged();
         }
      });
      var1.favorite.setOnClickListener(new OnClickListener() {
         public void onClick(View var1x) {
            var1.favorite.setSelected(var1.favorite.isSelected() ^ true);
            var3.isFavorite = var1.favorite.isSelected();
            AppBase.dbOp.updateDiveLogFavorite(var3.watch_serial_number, var3.start_time, var3.isFavorite);
            AdapterDiveLogsListRecyclerView.this.notifyDataSetChanged();
         }
      });
      var1.button_R.setOnClickListener(new OnClickListener() {
         public void onClick(View var1x) {
            final String var4 = var3.watch_serial_number;
            final long var2 = var1.dive_log_index;
            var2 = var1.start_time;
            ConfirmDialog var10001 = new ConfirmDialog(AdapterDiveLogsListRecyclerView.this.context, AdapterDiveLogsListRecyclerView.this.context.getString(2131689587), AdapterDiveLogsListRecyclerView.this.context.getString(2131689571), AdapterDiveLogsListRecyclerView.this.context.getString(2131689536)) {
               public void OnConfirmed() {
                  super.OnConfirmed();
                  var1.slideViewByClick.stop();
                  AdapterDiveLogsListRecyclerView.this.delete(var4, var2);
               }
            };
         }
      });
      if (WatchOp.isOldFirmware(var3.firmware_version)) {
         var1.no_profile.setVisibility(0);
      } else {
         var1.no_profile.setVisibility(8);
      }

   }

   public AdapterDiveLogsListRecyclerView.ViewHolder onCreateViewHolder(ViewGroup var1, int var2) {
      return new AdapterDiveLogsListRecyclerView.ViewHolder(LayoutInflater.from(var1.getContext()).inflate(2131427453, var1, false));
   }

   public class ViewHolder extends RecyclerView.ViewHolder {
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

      public ViewHolder(View var2) {
         super(var2);
         this.row_dive_log = (RelativeLayout)var2;
         this.main_content = (LinearLayout)var2.findViewById(2131231143);
         this.tv_dive_log_index = (TextView)var2.findViewById(2131230982);
         this.date = (TextView)var2.findViewById(2131230944);
         this.time = (TextView)var2.findViewById(2131231403);
         this.lowest_temperature = (TextView)var2.findViewById(2131231141);
         this.duration = (TextView)var2.findViewById(2131230997);
         this.max_depth = (TextView)var2.findViewById(2131231146);
         this.locaiton = (TextView)var2.findViewById(2131231137);
         this.favorite = (ImageView)var2.findViewById(2131231015);
         this.button_L = (ImageView)var2.findViewById(2131230862);
         this.button_R = (ImageView)var2.findViewById(2131230863);
         this.no_profile = (TextView)var2.findViewById(2131231192);
         SlideViewByClick var3 = new SlideViewByClick(this.main_content, this.button_R, SlideViewByClick.SLIDE_DIRECTION.HORIZONTAL, SlideViewByClick.TOWARD.LEAD) {
            public void OnFrontClick(View var1) {
               super.OnFrontClick(var1);
               int var2 = ((AdapterDiveLogsListRecyclerView.ViewHolder)var1.getTag(2131231354)).position;
               AdapterDiveLogsListRecyclerView.this.dive_log_edit(AdapterDiveLogsListRecyclerView.this.context, var2);
            }
         };
         this.slideViewByClick = var3;
         var3.start();
      }
   }
}
