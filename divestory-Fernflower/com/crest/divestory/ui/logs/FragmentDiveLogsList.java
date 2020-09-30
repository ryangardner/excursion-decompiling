package com.crest.divestory.ui.logs;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.crest.divestory.AppBase;
import com.crest.divestory.DataStruct;
import com.crest.divestory.WatchOp;
import com.syntak.library.TimeOp;
import com.syntak.library.UiOp;
import java.util.ArrayList;

public class FragmentDiveLogsList extends Fragment {
   private static final String ARG_FUTURE_ACTION = "future_action";
   static Context context;
   boolean anyFilter = false;
   TextView button_date_end;
   TextView button_date_start;
   Button cancel_filter;
   Button clear_filter;
   TextView date_end;
   TextView date_start;
   WatchOp.ACTION future_action;
   boolean isFilterChanged = false;
   private ListView lv;
   private int mColumnCount = 1;
   private FragmentDiveLogsList.OnDiveLogsFragmentInteractionListener mListener;
   CheckBox radioDateRange;
   CheckBox radioFavorite;
   CheckBox radioFreeDive;
   CheckBox radioGaugeDive;
   CheckBox radioScubaDive;
   RecyclerView recyclerView;
   RelativeLayout screen_filter;
   RelativeLayout screen_list;
   Button start_filter;
   private TextView warning;

   private void do_filter() {
      if (WatchOp.dive_logs.length() > 0) {
         boolean var1 = this.isAnyFilter();
         ArrayList var2 = new ArrayList();

         for(int var3 = 0; var3 < WatchOp.dive_logs.length(); ++var3) {
            DataStruct.DiveLog var4 = WatchOp.dive_logs.get(var3);
            if (!var1 || this.isConformed(var4)) {
               var2.add(var4);
            }
         }

         WatchOp.dive_logs_list = var2;
         AppBase.notifyChangeDiveLogList();
      }
   }

   private void hide_filter_screen() {
      this.screen_filter.setVisibility(8);
      this.screen_list.setVisibility(0);
      RelativeLayout var1 = this.screen_filter;
      var1.setTranslationY((float)(-var1.getHeight()));
   }

   private void init_date_filter() {
      long var1 = TimeOp.getNow();
      int var3 = TimeOp.getYear(var1);
      int var4 = TimeOp.getMonth(var1);
      int var5 = TimeOp.getDay(var1);
      WatchOp.filterDiveLog.date_start = TimeOp.DateTimeToMs(var3, var4, var5, 0, 0);
      WatchOp.filterDiveLog.date_end = TimeOp.DateTimeToMs(var3, var4, var5, 23, 59);
      String var6 = TimeOp.MsToYearMonthDay(var1);
      this.date_start.setText(var6);
      this.date_end.setText(var6);
   }

   private static void init_dive_logs() {
      if (WatchOp.dive_logs != null) {
         WatchOp.dive_logs = null;
      }

      AppBase.open_db(context);
      WatchOp.dive_logs = new DataStruct.DiveLogs(AppBase.dbOp.getDiveLogs());
      WatchOp.dive_logs_list = WatchOp.dive_logs.duplicateDiveLogsList();
   }

   private boolean isAnyFilter() {
      DataStruct.FilterDiveLog var1 = WatchOp.filterDiveLog;
      boolean var2 = false;
      if (var1 == null) {
         return false;
      } else {
         WatchOp.filterDiveLog.date_range = this.radioDateRange.isChecked();
         WatchOp.filterDiveLog.scuba_dive = this.radioScubaDive.isChecked();
         WatchOp.filterDiveLog.free_dive = this.radioFreeDive.isChecked();
         WatchOp.filterDiveLog.gauge_dive = this.radioGaugeDive.isChecked();
         WatchOp.filterDiveLog.favorite = this.radioFavorite.isChecked();
         if (WatchOp.filterDiveLog.date_range || WatchOp.filterDiveLog.scuba_dive || WatchOp.filterDiveLog.free_dive || WatchOp.filterDiveLog.gauge_dive || WatchOp.filterDiveLog.favorite) {
            var2 = true;
         }

         return var2;
      }
   }

   private boolean isConformed(DataStruct.DiveLog var1) {
      boolean var2 = WatchOp.filterDiveLog.date_range;
      boolean var3 = false;
      boolean var4;
      if (!var2 || var1.start_time >= WatchOp.filterDiveLog.date_start && var1.start_time <= WatchOp.filterDiveLog.date_end) {
         var4 = true;
      } else {
         var4 = false;
      }

      boolean var5;
      if (!WatchOp.filterDiveLog.scuba_dive && !WatchOp.filterDiveLog.free_dive && !WatchOp.filterDiveLog.gauge_dive) {
         var5 = false;
      } else {
         var5 = true;
      }

      label69: {
         if (var5) {
            if (WatchOp.filterDiveLog.scuba_dive && var1.dive_type == 0L) {
               var5 = true;
            } else {
               var5 = false;
            }

            boolean var6;
            if (WatchOp.filterDiveLog.free_dive && var1.dive_type == 2L) {
               var6 = true;
            } else {
               var6 = false;
            }

            boolean var7;
            if (WatchOp.filterDiveLog.gauge_dive && var1.dive_type == 1L) {
               var7 = true;
            } else {
               var7 = false;
            }

            if (!var5 && !var6 && !var7) {
               var5 = false;
               break label69;
            }
         }

         var5 = true;
      }

      boolean var8;
      if (WatchOp.filterDiveLog.favorite) {
         var8 = var1.isFavorite;
      } else {
         var8 = true;
      }

      var2 = var3;
      if (var4) {
         var2 = var3;
         if (var5) {
            var2 = var3;
            if (var8) {
               var2 = true;
            }
         }
      }

      return var2;
   }

   public static FragmentDiveLogsList newInstance(WatchOp.ACTION var0) {
      FragmentDiveLogsList var1 = new FragmentDiveLogsList();
      Bundle var2 = new Bundle();
      var2.putSerializable("future_action", var0);
      var1.setArguments(var2);
      return var1;
   }

   public void cancel_filter_screen() {
      UiOp.viewAnimation(this.screen_filter, UiOp.TRANSLATION_AXIS.Y, (float)(-this.screen_filter.getHeight()), 500, UiOp.INTERPOLATION_TYPE.ACCELARATION, false);
      this.screen_list.setVisibility(0);
   }

   public void clear_filter() {
      WatchOp.filterDiveLog.clear();
      this.init_date_filter();
      this.radioDateRange.setChecked(false);
      this.radioScubaDive.setChecked(false);
      this.radioFreeDive.setChecked(false);
      this.radioGaugeDive.setChecked(false);
      this.radioFavorite.setChecked(false);
      this.do_filter();
   }

   public void date_picker_end(View var1) {
      long var2 = WatchOp.filterDiveLog.date_end;
      long var4 = var2;
      if (var2 == 0L) {
         var4 = TimeOp.getNow();
      }

      int var6 = TimeOp.getYear(var4);
      int var7 = TimeOp.getMonth(var4);
      int var8 = TimeOp.getDay(var4);
      (new DatePickerDialog(var1.getContext(), new OnDateSetListener() {
         public void onDateSet(DatePicker var1, int var2, int var3, int var4) {
            StringBuilder var5 = new StringBuilder();
            var5.append(String.valueOf(var2));
            var5.append("/");
            var5.append(String.valueOf(var3 + 1));
            var5.append("/");
            var5.append(String.valueOf(var4));
            String var6 = var5.toString();
            FragmentDiveLogsList.this.date_end.setText(var6);
            WatchOp.filterDiveLog.date_end = TimeOp.DateTimeToMs(var2, var3, var4, 23, 59);
         }
      }, var6, var7, var8)).show();
   }

   public void date_picker_start(View var1) {
      long var2 = WatchOp.filterDiveLog.date_start;
      long var4 = var2;
      if (var2 == 0L) {
         var4 = TimeOp.getNow();
      }

      int var6 = TimeOp.getYear(var4);
      int var7 = TimeOp.getMonth(var4);
      int var8 = TimeOp.getDay(var4);
      (new DatePickerDialog(var1.getContext(), new OnDateSetListener() {
         public void onDateSet(DatePicker var1, int var2, int var3, int var4) {
            StringBuilder var5 = new StringBuilder();
            var5.append(String.valueOf(var2));
            var5.append("/");
            var5.append(String.valueOf(var3 + 1));
            var5.append("/");
            var5.append(String.valueOf(var4));
            String var6 = var5.toString();
            FragmentDiveLogsList.this.date_start.setText(var6);
            WatchOp.filterDiveLog.date_start = TimeOp.DateTimeToMs(var2, var3, var4, 0, 0);
         }
      }, var6, var7, var8)).show();
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      if (this.getArguments() != null) {
         this.future_action = (WatchOp.ACTION)this.getArguments().getSerializable("future_action");
      }

      context = this.getContext();
      init_dive_logs();
   }

   public View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
      View var4 = var1.inflate(2131427394, var2, false);
      this.warning = (TextView)var4.findViewById(2131231445);
      this.lv = (ListView)var4.findViewById(2131231133);
      if (WatchOp.dive_logs_list != null && WatchOp.dive_logs_list.size() > 0) {
         this.warning.setText("");
         AppBase.adapterDiveLogsList = new AdapterDiveLogsList(context);
         this.lv.setAdapter(AppBase.adapterDiveLogsList);
      } else {
         this.warning.setText(2131689746);
      }

      this.screen_list = (RelativeLayout)var4.findViewById(2131231274);
      this.screen_filter = (RelativeLayout)var4.findViewById(2131231273);
      this.hide_filter_screen();
      this.button_date_start = (TextView)var4.findViewById(2131230868);
      this.button_date_end = (TextView)var4.findViewById(2131230867);
      this.date_start = (TextView)var4.findViewById(2131230952);
      this.date_end = (TextView)var4.findViewById(2131230945);
      this.date_start = (TextView)var4.findViewById(2131230952);
      this.date_end = (TextView)var4.findViewById(2131230945);
      this.radioDateRange = (CheckBox)var4.findViewById(2131231233);
      this.radioScubaDive = (CheckBox)var4.findViewById(2131231237);
      this.radioFreeDive = (CheckBox)var4.findViewById(2131231235);
      this.radioGaugeDive = (CheckBox)var4.findViewById(2131231236);
      this.radioFavorite = (CheckBox)var4.findViewById(2131231234);
      this.init_date_filter();
      return var4;
   }

   public void onDestroy() {
      super.onDestroy();
   }

   public void onDestroyView() {
      super.onDestroyView();
   }

   public void onResume() {
      super.onResume();
      if (WatchOp.is_new_divelogs_downloaded) {
         WatchOp.is_new_divelogs_downloaded = false;
         if (AppBase.adapterDiveLogsList != null) {
            init_dive_logs();
            AppBase.adapterDiveLogsList.notifyDataSetChanged();
         }
      }

   }

   public void onSaveInstanceState(Bundle var1) {
      super.onSaveInstanceState(var1);
   }

   public void setListenToWatchList(FragmentDiveLogsList.OnDiveLogsFragmentInteractionListener var1) {
      this.mListener = var1;
   }

   public void show_filter_screen() {
      UiOp.viewAnimation(this.screen_filter, UiOp.TRANSLATION_AXIS.Y, 0.0F, 500, UiOp.INTERPOLATION_TYPE.DECELATION, true);
      this.screen_list.setVisibility(8);
   }

   public void start_filter() {
      this.do_filter();
      this.screen_list.setVisibility(0);
      UiOp.viewAnimation(this.screen_filter, UiOp.TRANSLATION_AXIS.Y, (float)(-this.screen_filter.getHeight()), 500, UiOp.INTERPOLATION_TYPE.ACCELARATION, false);
   }

   public interface OnDiveLogsFragmentInteractionListener {
      void onWatchesFragmentInteraction(String var1);
   }
}
