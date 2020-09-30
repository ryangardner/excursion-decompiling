package com.crest.divestory.ui.watches;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.crest.divestory.AppBase;
import com.crest.divestory.DataStruct;
import com.crest.divestory.FragmentPage;
import com.crest.divestory.WatchOp;

public class FragmentWatchSettingsScuba extends FragmentPage {
   private static Activity activity;
   private static Context context;

   public static FragmentWatchSettingsScuba newInstance(int var0) {
      FragmentWatchSettingsScuba var1 = new FragmentWatchSettingsScuba();
      var1.page = var0;
      return var1;
   }

   public void init_G_sensor(View var1) {
      Switch var3 = (Switch)var1.findViewById(2131230725);
      boolean var2;
      if (WatchOp.watchSetting_gb.G_sensor == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      var3.setChecked(var2);
      var3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
         public void onCheckedChanged(CompoundButton var1, boolean var2) {
            WatchOp.watchSetting_gb.G_sensor = var2 ^ 1;
            byte var3 = (byte)WatchOp.watchSetting_gb.G_sensor;
            WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)16, new byte[]{var3}, 1);
         }
      });
   }

   public void init_O2_ratio(View var1) {
      final TextView var2 = (TextView)var1.findViewById(2131230728);
      Button var3 = (Button)var1.findViewById(2131230871);
      Button var4 = (Button)var1.findViewById(2131230884);
      if (WatchOp.watchSetting_gb.O2_ratio < 21) {
         WatchOp.watchSetting_gb.O2_ratio = 21;
      }

      if (WatchOp.watchSetting_gb.O2_ratio > 56) {
         WatchOp.watchSetting_gb.O2_ratio = 56;
      }

      StringBuilder var5 = new StringBuilder();
      var5.append(WatchOp.watchSetting_gb.O2_ratio);
      var5.append("%");
      var2.setText(var5.toString());
      var3.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            if (WatchOp.watchSetting_gb.O2_ratio > 21) {
               DataStruct.WatchSetting_GB var4 = WatchOp.watchSetting_gb;
               --var4.O2_ratio;
               TextView var5 = var2;
               StringBuilder var2x = new StringBuilder();
               var2x.append(WatchOp.watchSetting_gb.O2_ratio);
               var2x.append("%");
               var5.setText(var2x.toString());
               byte var3 = (byte)WatchOp.watchSetting_gb.O2_ratio;
               WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)6, new byte[]{var3}, 1);
            }

         }
      });
      var4.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            if (WatchOp.watchSetting_gb.O2_ratio < 56) {
               DataStruct.WatchSetting_GB var4 = WatchOp.watchSetting_gb;
               ++var4.O2_ratio;
               TextView var2x = var2;
               StringBuilder var5 = new StringBuilder();
               var5.append(WatchOp.watchSetting_gb.O2_ratio);
               var5.append("%");
               var2x.setText(var5.toString());
               byte var3 = (byte)WatchOp.watchSetting_gb.O2_ratio;
               WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)6, new byte[]{var3}, 1);
            }

         }
      });
   }

   public void init_PPO2(View var1) {
      RadioGroup var2 = (RadioGroup)var1.findViewById(2131230734);
      RadioButton var3 = (RadioButton)var1.findViewById(2131230729);
      RadioButton var4 = (RadioButton)var1.findViewById(2131230730);
      RadioButton var5 = (RadioButton)var1.findViewById(2131230731);
      RadioButton var6 = (RadioButton)var1.findViewById(2131230732);
      RadioButton var8 = (RadioButton)var1.findViewById(2131230733);
      var2.setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener() {
         public void onCheckedChanged(RadioGroup var1, int var2) {
            byte[] var3 = new byte[1];
            switch(var2) {
            case 2131230729:
               var3[0] = (byte)0;
               break;
            case 2131230730:
               var3[0] = (byte)1;
               break;
            case 2131230731:
               var3[0] = (byte)2;
               break;
            case 2131230732:
               var3[0] = (byte)3;
               break;
            case 2131230733:
               var3[0] = (byte)4;
            }

            WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)7, var3, 1);
         }
      });
      int var7 = WatchOp.watchSetting_gb.PPO2;
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

   public void init_log_sampling_rate(View var1) {
      RadioGroup var2 = (RadioGroup)var1.findViewById(2131231138);
      RadioButton var3 = (RadioButton)var1.findViewById(2131231262);
      RadioButton var4 = (RadioButton)var1.findViewById(2131231263);
      RadioButton var5 = (RadioButton)var1.findViewById(2131231264);
      RadioButton var7 = (RadioButton)var1.findViewById(2131231265);
      var2.setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener() {
         public void onCheckedChanged(RadioGroup var1, int var2) {
            byte[] var3 = new byte[1];
            switch(var2) {
            case 2131231262:
               var3[0] = (byte)0;
               break;
            case 2131231263:
               var3[0] = (byte)1;
               break;
            case 2131231264:
               var3[0] = (byte)2;
               break;
            case 2131231265:
               var3[0] = (byte)3;
            }

            WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)13, var3, 1);
         }
      });
      int var6 = WatchOp.watchSetting_gb.scuba_dive_log_sampling_rate;
      if (var6 != 0) {
         if (var6 != 1) {
            if (var6 != 2) {
               if (var6 == 3) {
                  var7.toggle();
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

   public void init_log_start_depth(View var1) {
      RadioGroup var2 = (RadioGroup)var1.findViewById(2131231139);
      RadioButton var3 = (RadioButton)var1.findViewById(2131230960);
      RadioButton var4 = (RadioButton)var1.findViewById(2131230961);
      RadioButton var5 = (RadioButton)var1.findViewById(2131230962);
      RadioButton var6 = (RadioButton)var1.findViewById(2131230963);
      RadioButton var8 = (RadioButton)var1.findViewById(2131230964);
      if (AppBase.display_unit == AppBase.UNITS.imperial) {
         var3.setText("3.3 ft");
         var4.setText("4.9 ft");
         var5.setText("6.5 ft");
         var6.setText("8.2 ft");
         var8.setText("9.8 ft");
      }

      if (WatchOp.isOldFirmware(WatchOp.watch_connected.firmware_version)) {
         var3.setVisibility(8);
      }

      var2.setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener() {
         public void onCheckedChanged(RadioGroup var1, int var2) {
            byte[] var3 = new byte[1];
            switch(var2) {
            case 2131230960:
               var3[0] = (byte)0;
               break;
            case 2131230961:
               var3[0] = (byte)1;
               break;
            case 2131230962:
               var3[0] = (byte)2;
               break;
            case 2131230963:
               var3[0] = (byte)3;
               break;
            case 2131230964:
               var3[0] = (byte)4;
            }

            WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)14, var3, 1);
         }
      });
      int var7 = WatchOp.watchSetting_gb.scuba_dive_log_start_depth;
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

   public void init_log_stop_time(View var1) {
      RadioGroup var2 = (RadioGroup)var1.findViewById(2131231140);
      RadioButton var3 = (RadioButton)var1.findViewById(2131231336);
      RadioButton var4 = (RadioButton)var1.findViewById(2131231337);
      RadioButton var5 = (RadioButton)var1.findViewById(2131231338);
      RadioButton var7 = (RadioButton)var1.findViewById(2131231339);
      if (WatchOp.isOldFirmware(WatchOp.watch_connected.firmware_version)) {
         var7.setVisibility(8);
      }

      var2.setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener() {
         public void onCheckedChanged(RadioGroup var1, int var2) {
            byte[] var3 = new byte[1];
            switch(var2) {
            case 2131231336:
               var3[0] = (byte)0;
               break;
            case 2131231337:
               var3[0] = (byte)1;
               break;
            case 2131231338:
               var3[0] = (byte)2;
               break;
            case 2131231339:
               var3[0] = (byte)3;
            }

            WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)15, var3, 1);
         }
      });
      int var6 = WatchOp.watchSetting_gb.scuba_dive_log_stop_time;
      if (var6 != 0) {
         if (var6 != 1) {
            if (var6 != 2) {
               if (var6 == 3) {
                  var7.toggle();
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

   public void init_safety_factor(View var1) {
      RadioGroup var2 = (RadioGroup)var1.findViewById(2131231261);
      RadioButton var3 = (RadioButton)var1.findViewById(2131231258);
      RadioButton var4 = (RadioButton)var1.findViewById(2131231259);
      RadioButton var6 = (RadioButton)var1.findViewById(2131231260);
      var2.setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener() {
         public void onCheckedChanged(RadioGroup var1, int var2) {
            byte[] var3 = new byte[1];
            switch(var2) {
            case 2131231258:
               var3[0] = (byte)0;
               break;
            case 2131231259:
               var3[0] = (byte)1;
               break;
            case 2131231260:
               var3[0] = (byte)2;
            }

            WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)8, var3, 1);
         }
      });
      int var5 = WatchOp.watchSetting_gb.safety_factor;
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

   public void init_scuba_depth_alarm(View var1) {
      Switch var3 = (Switch)var1.findViewById(2131231281);
      boolean var2;
      if (WatchOp.watchSetting_gb.scuba_dive_depth_alarm == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      var3.setChecked(var2);
      var3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
         public void onCheckedChanged(CompoundButton var1, boolean var2) {
            WatchOp.watchSetting_gb.scuba_dive_depth_alarm = var2 ^ 1;
            byte var3 = (byte)WatchOp.watchSetting_gb.scuba_dive_depth_alarm;
            WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)9, new byte[]{var3}, 1);
         }
      });
   }

   public void init_scuba_depth_alarm_threshold(View var1) {
      final TextView var2 = (TextView)var1.findViewById(2131231282);
      Button var3 = (Button)var1.findViewById(2131230881);
      Button var4 = (Button)var1.findViewById(2131230894);
      if (WatchOp.watchSetting_gb.scuba_dive_depth_alarm_threshold < 0) {
         WatchOp.watchSetting_gb.scuba_dive_depth_alarm_threshold = 0;
      }

      if (WatchOp.watchSetting_gb.scuba_dive_depth_alarm_threshold > 99) {
         WatchOp.watchSetting_gb.scuba_dive_depth_alarm_threshold = 99;
      }

      var2.setText(AppBase.get_String_of_Length(WatchOp.watchSetting_gb.scuba_dive_depth_alarm_threshold));
      var3.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            if (WatchOp.watchSetting_gb.scuba_dive_depth_alarm_threshold > 0) {
               DataStruct.WatchSetting_GB var3 = WatchOp.watchSetting_gb;
               --var3.scuba_dive_depth_alarm_threshold;
               var2.setText(AppBase.get_String_of_Length(WatchOp.watchSetting_gb.scuba_dive_depth_alarm_threshold));
               byte var2x = (byte)WatchOp.watchSetting_gb.scuba_dive_depth_alarm_threshold;
               WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)11, new byte[]{var2x}, 1);
            }

         }
      });
      var4.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            if (WatchOp.watchSetting_gb.scuba_dive_depth_alarm_threshold < 99) {
               DataStruct.WatchSetting_GB var3 = WatchOp.watchSetting_gb;
               ++var3.scuba_dive_depth_alarm_threshold;
               var2.setText(AppBase.get_String_of_Length(WatchOp.watchSetting_gb.scuba_dive_depth_alarm_threshold));
               byte var2x = (byte)WatchOp.watchSetting_gb.scuba_dive_depth_alarm_threshold;
               WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)11, new byte[]{var2x}, 1);
            }

         }
      });
   }

   public void init_scuba_time_alarm(View var1) {
      Switch var3 = (Switch)var1.findViewById(2131231283);
      boolean var2;
      if (WatchOp.watchSetting_gb.scuba_dive_time_alarm == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      var3.setChecked(var2);
      var3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
         public void onCheckedChanged(CompoundButton var1, boolean var2) {
            WatchOp.watchSetting_gb.scuba_dive_time_alarm = var2 ^ 1;
            byte var3 = (byte)WatchOp.watchSetting_gb.scuba_dive_time_alarm;
            WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)10, new byte[]{var3}, 1);
         }
      });
   }

   public void init_scuba_time_alarm_threshold(View var1) {
      final TextView var2 = (TextView)var1.findViewById(2131231284);
      Button var3 = (Button)var1.findViewById(2131230882);
      Button var4 = (Button)var1.findViewById(2131230895);
      if (WatchOp.watchSetting_gb.scuba_dive_time_alarm_threshold < 0) {
         WatchOp.watchSetting_gb.scuba_dive_time_alarm_threshold = 0;
      }

      if (WatchOp.watchSetting_gb.scuba_dive_time_alarm_threshold > 90) {
         WatchOp.watchSetting_gb.scuba_dive_time_alarm_threshold = 90;
      }

      StringBuilder var5 = new StringBuilder();
      var5.append(WatchOp.watchSetting_gb.scuba_dive_time_alarm_threshold);
      var5.append(" min");
      var2.setText(var5.toString());
      var3.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            if (WatchOp.watchSetting_gb.scuba_dive_time_alarm_threshold > 0) {
               DataStruct.WatchSetting_GB var4 = WatchOp.watchSetting_gb;
               --var4.scuba_dive_time_alarm_threshold;
               TextView var5 = var2;
               StringBuilder var2x = new StringBuilder();
               var2x.append(WatchOp.watchSetting_gb.scuba_dive_time_alarm_threshold);
               var2x.append(" min");
               var5.setText(var2x.toString());
               byte var3 = (byte)WatchOp.watchSetting_gb.scuba_dive_time_alarm_threshold;
               WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)12, new byte[]{var3}, 1);
            }

         }
      });
      var4.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            if (WatchOp.watchSetting_gb.scuba_dive_time_alarm_threshold < 90) {
               DataStruct.WatchSetting_GB var4 = WatchOp.watchSetting_gb;
               ++var4.scuba_dive_time_alarm_threshold;
               TextView var2x = var2;
               StringBuilder var5 = new StringBuilder();
               var5.append(WatchOp.watchSetting_gb.scuba_dive_time_alarm_threshold);
               var5.append(" min");
               var2x.setText(var5.toString());
               byte var3 = (byte)WatchOp.watchSetting_gb.scuba_dive_time_alarm_threshold;
               WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)12, new byte[]{var3}, 1);
            }

         }
      });
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
   }

   public View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
      context = this.getContext();
      View var4 = var1.inflate(2131427401, var2, false);
      this.init_O2_ratio(var4);
      this.init_PPO2(var4);
      this.init_safety_factor(var4);
      this.init_scuba_depth_alarm(var4);
      this.init_scuba_time_alarm(var4);
      this.init_scuba_depth_alarm_threshold(var4);
      this.init_scuba_time_alarm_threshold(var4);
      this.init_log_sampling_rate(var4);
      this.init_log_start_depth(var4);
      this.init_log_stop_time(var4);
      this.init_G_sensor(var4);
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
}
