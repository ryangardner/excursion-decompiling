package com.crest.divestory.ui.watches;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.crest.divestory.AppBase;
import com.crest.divestory.DataStruct;
import com.crest.divestory.FragmentPage;
import com.crest.divestory.WatchOp;

public class FragmentWatchSettingsBasic extends FragmentPage {
   private static Activity activity;
   private static Context context;
   TextView date;
   TextView time;

   public static FragmentWatchSettingsBasic newInstance(int var0) {
      FragmentWatchSettingsBasic var1 = new FragmentWatchSettingsBasic();
      var1.page = var0;
      return var1;
   }

   public void date_picker(final View var1) {
      int var2 = WatchOp.watchSetting_gb.year;
      int var3 = WatchOp.watchSetting_gb.month;
      int var4 = WatchOp.watchSetting_gb.day;
      (new DatePickerDialog(var1.getContext(), new OnDateSetListener() {
         public void onDateSet(DatePicker var1x, int var2, int var3, int var4) {
            StringBuilder var8 = new StringBuilder();
            var8.append(String.valueOf(var2));
            var8.append("/");
            ++var3;
            var8.append(String.valueOf(var3));
            var8.append("/");
            var8.append(String.valueOf(var4));
            String var9 = var8.toString();
            ((TextView)var1).setText(var9);
            WatchOp.watchSetting_gb.year = var2;
            WatchOp.watchSetting_gb.month = var3;
            WatchOp.watchSetting_gb.day = var4;
            byte var5 = (byte)(var2 - 2000);
            byte var6 = (byte)var3;
            byte var7 = (byte)var4;
            WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)1, new byte[]{var5, var6, var7}, 3);
         }
      }, var2, var3 - 1, var4)).show();
   }

   public void init_UTC_offset(View var1) {
      final TextView var2 = (TextView)var1.findViewById(2131230742);
      Button var3 = (Button)var1.findViewById(2131230870);
      Button var4 = (Button)var1.findViewById(2131230883);
      var2.setText(String.format("%1$+02d", WatchOp.watchSetting_gb.UTC_offset - 12));
      var3.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            int var2x = WatchOp.watchSetting_gb.UTC_offset - 12;
            if (var2x > -12) {
               DataStruct.WatchSetting_GB var4 = WatchOp.watchSetting_gb;
               --var4.UTC_offset;
               var2.setText(String.format("%1$+02d", var2x - 1));
               byte var3 = (byte)WatchOp.watchSetting_gb.UTC_offset;
               WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)31, new byte[]{var3}, 1);
            }

         }
      });
      var4.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            int var2x = WatchOp.watchSetting_gb.UTC_offset - 12;
            if (var2x < 12) {
               DataStruct.WatchSetting_GB var4 = WatchOp.watchSetting_gb;
               ++var4.UTC_offset;
               var2.setText(String.format("%1$+02d", var2x + 1));
               byte var3 = (byte)WatchOp.watchSetting_gb.UTC_offset;
               WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)31, new byte[]{var3}, 1);
            }

         }
      });
   }

   public void init_auto_run_for(View var1) {
      RadioGroup var2 = (RadioGroup)var1.findViewById(2131230988);
      RadioButton var3 = (RadioButton)var1.findViewById(2131230985);
      RadioButton var4 = (RadioButton)var1.findViewById(2131230986);
      RadioButton var6 = (RadioButton)var1.findViewById(2131230987);
      var2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
         public void onCheckedChanged(RadioGroup var1, int var2) {
            byte[] var3 = new byte[1];
            switch(var2) {
            case 2131230985:
               var3[0] = (byte)0;
               break;
            case 2131230986:
               var3[0] = (byte)1;
               break;
            case 2131230987:
               var3[0] = (byte)2;
            }

            WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)5, var3, 1);
         }
      });
      int var5 = WatchOp.watchSetting_gb.auto_dive_type;
      if (var5 != 0) {
         if (var5 != 1) {
            if (var5 == 2) {
               var6.toggle();
            }
         } else {
            var4.toggle();
         }
      } else {
         var3.toggle();
      }

   }

   public void init_backlight(View var1) {
      RadioGroup var2 = (RadioGroup)var1.findViewById(2131230828);
      RadioButton var3 = (RadioButton)var1.findViewById(2131231123);
      RadioButton var4 = (RadioButton)var1.findViewById(2131231124);
      RadioButton var5 = (RadioButton)var1.findViewById(2131231125);
      RadioButton var6 = (RadioButton)var1.findViewById(2131231126);
      RadioButton var8 = (RadioButton)var1.findViewById(2131231127);
      var2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
         public void onCheckedChanged(RadioGroup var1, int var2) {
            byte[] var3 = new byte[1];
            switch(var2) {
            case 2131231123:
               var3[0] = (byte)0;
               break;
            case 2131231124:
               var3[0] = (byte)1;
               break;
            case 2131231125:
               var3[0] = (byte)2;
               break;
            case 2131231126:
               var3[0] = (byte)3;
               break;
            case 2131231127:
               var3[0] = (byte)4;
            }

            WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)33, var3, 1);
         }
      });
      int var7 = WatchOp.watchSetting_gb.backlight;
      if (var7 != 0) {
         if (var7 != 1) {
            if (var7 != 2) {
               if (var7 != 3) {
                  if (var7 == 4) {
                     var8.toggle();
                  }
               } else {
                  var6.toggle();
               }
            } else {
               var5.toggle();
            }
         } else {
            var4.toggle();
         }
      } else {
         var3.toggle();
      }

   }

   public void init_buzzer(View var1) {
      Switch var3 = (Switch)var1.findViewById(2131230898);
      boolean var2;
      if (WatchOp.watchSetting_gb.buzzer == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      var3.setChecked(var2);
      var3.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
         public void onCheckedChanged(CompoundButton var1, boolean var2) {
            WatchOp.watchSetting_gb.buzzer = var2 ^ 1;
            byte var3 = (byte)WatchOp.watchSetting_gb.buzzer;
            WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)32, new byte[]{var3}, 1);
         }
      });
   }

   public void init_data_format(View var1) {
      RadioGroup var2 = (RadioGroup)var1.findViewById(2131230950);
      RadioButton var3 = (RadioButton)var1.findViewById(2131230947);
      RadioButton var4 = (RadioButton)var1.findViewById(2131230948);
      RadioButton var6 = (RadioButton)var1.findViewById(2131230949);
      var2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
         public void onCheckedChanged(RadioGroup var1, int var2) {
            byte[] var3 = new byte[1];
            switch(var2) {
            case 2131230947:
               var3[0] = (byte)0;
               FragmentWatchSettingsBasic.this.show_date(FragmentWatchSettingsBasic.DATE_FORMAT.YYMMDD);
               break;
            case 2131230948:
               var3[0] = (byte)1;
               FragmentWatchSettingsBasic.this.show_date(FragmentWatchSettingsBasic.DATE_FORMAT.MMDDYY);
               break;
            case 2131230949:
               var3[0] = (byte)2;
               FragmentWatchSettingsBasic.this.show_date(FragmentWatchSettingsBasic.DATE_FORMAT.DDMMYY);
            }

            WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)2, var3, 1);
         }
      });
      int var5 = WatchOp.watchSetting_gb.date_format;
      if (var5 != 0) {
         if (var5 != 1) {
            if (var5 == 2) {
               var6.toggle();
               this.show_date(FragmentWatchSettingsBasic.DATE_FORMAT.DDMMYY);
            }
         } else {
            var4.toggle();
            this.show_date(FragmentWatchSettingsBasic.DATE_FORMAT.MMDDYY);
         }
      } else {
         var3.toggle();
         this.show_date(FragmentWatchSettingsBasic.DATE_FORMAT.YYMMDD);
      }

   }

   public void init_date(View var1) {
      TextView var2 = (TextView)var1.findViewById(2131230944);
      this.date = var2;
      var2.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            FragmentWatchSettingsBasic.this.date_picker(var1);
         }
      });
   }

   public void init_display_unit(View var1) {
      RadioGroup var2 = (RadioGroup)var1.findViewById(2131230979);
      RadioButton var3 = (RadioButton)var1.findViewById(2131230977);
      RadioButton var5 = (RadioButton)var1.findViewById(2131230978);
      var2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
         public void onCheckedChanged(RadioGroup var1, int var2) {
            byte[] var3 = new byte[1];
            switch(var2) {
            case 2131230977:
               var3[0] = (byte)0;
               break;
            case 2131230978:
               var3[0] = (byte)1;
            }

            WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)29, var3, 1);
         }
      });
      int var4 = WatchOp.watchSetting_gb.display_unit;
      if (var4 != 0) {
         if (var4 == 1) {
            var5.toggle();
         }
      } else {
         var3.toggle();
      }

   }

   public void init_power_saving(View var1) {
      RadioGroup var2 = (RadioGroup)var1.findViewById(2131230806);
      RadioButton var3 = (RadioButton)var1.findViewById(2131231152);
      RadioButton var4 = (RadioButton)var1.findViewById(2131231153);
      RadioButton var6 = (RadioButton)var1.findViewById(2131231154);
      var2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
         public void onCheckedChanged(RadioGroup var1, int var2) {
            byte[] var3 = new byte[1];
            switch(var2) {
            case 2131231152:
               var3[0] = (byte)0;
               break;
            case 2131231153:
               var3[0] = (byte)1;
               break;
            case 2131231154:
               var3[0] = (byte)2;
            }

            WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)30, var3, 1);
         }
      });
      int var5 = WatchOp.watchSetting_gb.power_saving;
      if (var5 != 0) {
         if (var5 != 1) {
            if (var5 == 2) {
               var6.toggle();
            }
         } else {
            var4.toggle();
         }
      } else {
         var3.toggle();
      }

   }

   public void init_time(View var1) {
      TextView var2 = (TextView)var1.findViewById(2131231403);
      this.time = var2;
      var2.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            FragmentWatchSettingsBasic.this.time_picker(var1);
         }
      });
   }

   public void init_time_format(View var1) {
      RadioGroup var2 = (RadioGroup)var1.findViewById(2131231408);
      RadioButton var3 = (RadioButton)var1.findViewById(2131231406);
      RadioButton var5 = (RadioButton)var1.findViewById(2131231407);
      var2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
         public void onCheckedChanged(RadioGroup var1, int var2) {
            byte[] var3 = new byte[1];
            switch(var2) {
            case 2131231406:
               var3[0] = (byte)0;
               FragmentWatchSettingsBasic.this.show_time(FragmentWatchSettingsBasic.TIME_FORMAT.H12);
               break;
            case 2131231407:
               var3[0] = (byte)1;
               FragmentWatchSettingsBasic.this.show_time(FragmentWatchSettingsBasic.TIME_FORMAT.H24);
            }

            WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)4, var3, 1);
         }
      });
      int var4 = WatchOp.watchSetting_gb.time_format;
      if (var4 != 0) {
         if (var4 == 1) {
            var5.toggle();
            this.show_time(FragmentWatchSettingsBasic.TIME_FORMAT.H24);
         }
      } else {
         var3.toggle();
         this.show_time(FragmentWatchSettingsBasic.TIME_FORMAT.H12);
      }

   }

   public void init_vibrator(View var1) {
      Switch var3 = (Switch)var1.findViewById(2131231438);
      boolean var2;
      if (WatchOp.watchSetting_gb.vibrator == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      var3.setChecked(var2);
      var3.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
         public void onCheckedChanged(CompoundButton var1, boolean var2) {
            WatchOp.watchSetting_gb.vibrator = var2 ^ 1;
            byte var3 = (byte)WatchOp.watchSetting_gb.vibrator;
            WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)34, new byte[]{var3}, 1);
         }
      });
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
   }

   public View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
      context = this.getContext();
      View var4 = var1.inflate(2131427399, var2, false);
      this.init_date(var4);
      this.init_data_format(var4);
      this.init_time(var4);
      this.init_time_format(var4);
      this.init_auto_run_for(var4);
      this.init_display_unit(var4);
      this.init_power_saving(var4);
      this.init_UTC_offset(var4);
      this.init_buzzer(var4);
      this.init_backlight(var4);
      this.init_vibrator(var4);
      return var4;
   }

   public void onDestroy() {
      super.onDestroy();
   }

   public void onDestroyView() {
      super.onDestroyView();
   }

   public void onSaveInstanceState(Bundle var1) {
      super.onSaveInstanceState(var1);
   }

   public void show_date(FragmentWatchSettingsBasic.DATE_FORMAT var1) {
      int var2 = null.$SwitchMap$com$crest$divestory$ui$watches$FragmentWatchSettingsBasic$DATE_FORMAT[var1.ordinal()];
      if (var2 != 1) {
         if (var2 != 2) {
            if (var2 == 3) {
               this.date.setText(String.format(AppBase.DATE_STRING_FORMAT_MMDDYYYY, WatchOp.watchSetting_gb.day, WatchOp.watchSetting_gb.month, WatchOp.watchSetting_gb.year));
            }
         } else {
            this.date.setText(String.format(AppBase.DATE_STRING_FORMAT_MMDDYYYY, WatchOp.watchSetting_gb.month, WatchOp.watchSetting_gb.day, WatchOp.watchSetting_gb.year));
         }
      } else {
         this.date.setText(String.format(AppBase.DATE_STRING_FORMAT_YYYYMMDD, WatchOp.watchSetting_gb.year, WatchOp.watchSetting_gb.month, WatchOp.watchSetting_gb.day));
      }

   }

   public void show_time(FragmentWatchSettingsBasic.TIME_FORMAT var1) {
      int var2 = null.$SwitchMap$com$crest$divestory$ui$watches$FragmentWatchSettingsBasic$TIME_FORMAT[var1.ordinal()];
      if (var2 != 1) {
         if (var2 == 2) {
            this.time.setText(String.format(AppBase.TIME_STRING_FORMAT, WatchOp.watchSetting_gb.hour, WatchOp.watchSetting_gb.minute));
         }
      } else if (WatchOp.watchSetting_gb.hour < 12) {
         TextView var4 = this.time;
         StringBuilder var3 = new StringBuilder();
         var3.append("A");
         var3.append(String.format(AppBase.TIME_STRING_FORMAT, WatchOp.watchSetting_gb.hour, WatchOp.watchSetting_gb.minute));
         var4.setText(var3.toString());
      } else {
         TextView var6 = this.time;
         StringBuilder var5 = new StringBuilder();
         var5.append("P");
         var5.append(String.format(AppBase.TIME_STRING_FORMAT, WatchOp.watchSetting_gb.hour - 12, WatchOp.watchSetting_gb.minute));
         var6.setText(var5.toString());
      }

   }

   public void time_picker(final View var1) {
      int var2 = WatchOp.watchSetting_gb.hour;
      int var3 = WatchOp.watchSetting_gb.minute;
      (new TimePickerDialog(var1.getContext(), new OnTimeSetListener() {
         public void onTimeSet(TimePicker var1x, int var2, int var3) {
            StringBuilder var6 = new StringBuilder();
            var6.append(String.valueOf(var2));
            var6.append(":");
            var6.append(String.valueOf(var3));
            String var7 = var6.toString();
            ((TextView)var1).setText(var7);
            WatchOp.watchSetting_gb.hour = var2;
            WatchOp.watchSetting_gb.minute = var3;
            byte var4 = (byte)var2;
            byte var5 = (byte)var3;
            WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)3, new byte[]{var4, var5, 0}, 3);
         }
      }, var2, var3, true)).show();
   }

   static enum DATE_FORMAT {
      DDMMYY,
      MMDDYY,
      YYMMDD;

      static {
         FragmentWatchSettingsBasic.DATE_FORMAT var0 = new FragmentWatchSettingsBasic.DATE_FORMAT("DDMMYY", 2);
         DDMMYY = var0;
      }
   }

   static enum TIME_FORMAT {
      H12,
      H24;

      static {
         FragmentWatchSettingsBasic.TIME_FORMAT var0 = new FragmentWatchSettingsBasic.TIME_FORMAT("H24", 1);
         H24 = var0;
      }
   }
}
