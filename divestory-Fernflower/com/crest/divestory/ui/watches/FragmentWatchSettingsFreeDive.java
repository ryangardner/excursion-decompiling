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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.crest.divestory.AppBase;
import com.crest.divestory.DataStruct;
import com.crest.divestory.FragmentPage;
import com.crest.divestory.WatchOp;
import com.syntak.library.ByteOp;

public class FragmentWatchSettingsFreeDive extends FragmentPage {
   private static Activity activity;
   private static Context context;

   public static FragmentWatchSettingsFreeDive newInstance(int var0) {
      FragmentWatchSettingsFreeDive var1 = new FragmentWatchSettingsFreeDive();
      var1.page = var0;
      return var1;
   }

   public void init_free_dive_depth_alarm(View var1) {
      Switch var3 = (Switch)var1.findViewById(2131231030);
      boolean var2;
      if (WatchOp.watchSetting_gb.free_dive_depth_alarm == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      var3.setChecked(var2);
      var3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
         public void onCheckedChanged(CompoundButton var1, boolean var2) {
            WatchOp.watchSetting_gb.free_dive_depth_alarm = var2 ^ 1;
            byte var3 = (byte)WatchOp.watchSetting_gb.free_dive_depth_alarm;
            WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)18, new byte[]{var3}, 1);
         }
      });
   }

   public void init_free_dive_depth_alarm_threshold_1(View var1) {
      final TextView var2 = (TextView)var1.findViewById(2131231031);
      Button var3 = (Button)var1.findViewById(2131230872);
      Button var4 = (Button)var1.findViewById(2131230885);
      if (WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_1 < 0) {
         WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_1 = 0;
      }

      if (WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_1 > 99) {
         WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_1 = 99;
      }

      var2.setText(AppBase.get_String_of_Length(WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_1));
      var3.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            if (WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_1 > 0) {
               DataStruct.WatchSetting_GB var3 = WatchOp.watchSetting_gb;
               --var3.free_dive_depth_alarm_threshold_1;
               var2.setText(AppBase.get_String_of_Length(WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_1));
               byte var2x = (byte)WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_1;
               WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)23, new byte[]{var2x}, 1);
            }

         }
      });
      var4.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            if (WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_1 < 99) {
               DataStruct.WatchSetting_GB var3 = WatchOp.watchSetting_gb;
               ++var3.free_dive_depth_alarm_threshold_1;
               var2.setText(AppBase.get_String_of_Length(WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_1));
               byte var2x = (byte)WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_1;
               WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)23, new byte[]{var2x}, 1);
            }

         }
      });
   }

   public void init_free_dive_depth_alarm_threshold_2(View var1) {
      final TextView var2 = (TextView)var1.findViewById(2131231032);
      Button var3 = (Button)var1.findViewById(2131230873);
      Button var4 = (Button)var1.findViewById(2131230886);
      if (WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_2 < 0) {
         WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_2 = 0;
      }

      if (WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_2 > 99) {
         WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_2 = 99;
      }

      var2.setText(AppBase.get_String_of_Length(WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_2));
      var3.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            if (WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_2 > 0) {
               DataStruct.WatchSetting_GB var3 = WatchOp.watchSetting_gb;
               --var3.free_dive_depth_alarm_threshold_2;
               var2.setText(AppBase.get_String_of_Length(WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_2));
               byte var2x = (byte)WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_2;
               WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)24, new byte[]{var2x}, 1);
            }

         }
      });
      var4.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            if (WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_2 < 99) {
               DataStruct.WatchSetting_GB var3 = WatchOp.watchSetting_gb;
               ++var3.free_dive_depth_alarm_threshold_2;
               var2.setText(AppBase.get_String_of_Length(WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_2));
               byte var2x = (byte)WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_2;
               WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)24, new byte[]{var2x}, 1);
            }

         }
      });
   }

   public void init_free_dive_depth_alarm_threshold_3(View var1) {
      final TextView var2 = (TextView)var1.findViewById(2131231033);
      Button var3 = (Button)var1.findViewById(2131230874);
      Button var4 = (Button)var1.findViewById(2131230887);
      if (WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_3 < 0) {
         WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_3 = 0;
      }

      if (WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_3 > 99) {
         WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_3 = 99;
      }

      var2.setText(AppBase.get_String_of_Length(WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_3));
      var3.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            if (WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_3 > 0) {
               DataStruct.WatchSetting_GB var3 = WatchOp.watchSetting_gb;
               --var3.free_dive_depth_alarm_threshold_3;
               var2.setText(AppBase.get_String_of_Length(WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_3));
               byte var2x = (byte)WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_3;
               WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)25, new byte[]{var2x}, 1);
            }

         }
      });
      var4.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            if (WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_3 < 99) {
               DataStruct.WatchSetting_GB var3 = WatchOp.watchSetting_gb;
               ++var3.free_dive_depth_alarm_threshold_3;
               var2.setText(AppBase.get_String_of_Length(WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_3));
               byte var2x = (byte)WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_3;
               WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)25, new byte[]{var2x}, 1);
            }

         }
      });
   }

   public void init_free_dive_surface_time_alarm(View var1) {
      Switch var3 = (Switch)var1.findViewById(2131231034);
      boolean var2;
      if (WatchOp.watchSetting_gb.free_dive_surface_time_alarm == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      var3.setChecked(var2);
      var3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
         public void onCheckedChanged(CompoundButton var1, boolean var2) {
            WatchOp.watchSetting_gb.free_dive_surface_time_alarm = var2 ^ 1;
            byte var3 = (byte)WatchOp.watchSetting_gb.free_dive_surface_time_alarm;
            WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)19, new byte[]{var3}, 1);
         }
      });
   }

   public void init_free_dive_surface_time_alarm_threshold_1(View var1) {
      final TextView var2 = (TextView)var1.findViewById(2131231035);
      Button var3 = (Button)var1.findViewById(2131230875);
      Button var5 = (Button)var1.findViewById(2131230888);
      if (WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_1 < 0) {
         WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_1 = 0;
      }

      if (WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_1 > 60) {
         WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_1 = 60;
      }

      StringBuilder var4 = new StringBuilder();
      var4.append(WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_1);
      var4.append(" min");
      var2.setText(var4.toString());
      var3.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            if (WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_1 > 0) {
               DataStruct.WatchSetting_GB var4 = WatchOp.watchSetting_gb;
               --var4.free_dive_surface_time_alarm_threshold_1;
               TextView var2x = var2;
               StringBuilder var5 = new StringBuilder();
               var5.append(WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_1);
               var5.append(" min");
               var2x.setText(var5.toString());
               byte var3 = (byte)WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_1;
               WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)26, new byte[]{var3}, 1);
            }

         }
      });
      var5.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            if (WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_1 < 60) {
               DataStruct.WatchSetting_GB var4 = WatchOp.watchSetting_gb;
               ++var4.free_dive_surface_time_alarm_threshold_1;
               TextView var2x = var2;
               StringBuilder var5 = new StringBuilder();
               var5.append(WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_1);
               var5.append(" min");
               var2x.setText(var5.toString());
               byte var3 = (byte)WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_1;
               WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)26, new byte[]{var3}, 1);
            }

         }
      });
   }

   public void init_free_dive_surface_time_alarm_threshold_2(View var1) {
      final TextView var2 = (TextView)var1.findViewById(2131231036);
      Button var3 = (Button)var1.findViewById(2131230876);
      Button var5 = (Button)var1.findViewById(2131230889);
      if (WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_2 < 0) {
         WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_2 = 0;
      }

      if (WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_2 > 60) {
         WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_2 = 60;
      }

      StringBuilder var4 = new StringBuilder();
      var4.append(WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_2);
      var4.append(" min");
      var2.setText(var4.toString());
      var3.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            if (WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_2 > 0) {
               DataStruct.WatchSetting_GB var4 = WatchOp.watchSetting_gb;
               --var4.free_dive_surface_time_alarm_threshold_2;
               TextView var2x = var2;
               StringBuilder var5 = new StringBuilder();
               var5.append(WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_2);
               var5.append(" min");
               var2x.setText(var5.toString());
               byte var3 = (byte)WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_2;
               WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)27, new byte[]{var3}, 1);
            }

         }
      });
      var5.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            if (WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_2 < 60) {
               DataStruct.WatchSetting_GB var4 = WatchOp.watchSetting_gb;
               ++var4.free_dive_surface_time_alarm_threshold_2;
               TextView var5 = var2;
               StringBuilder var2x = new StringBuilder();
               var2x.append(WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_2);
               var2x.append(" min");
               var5.setText(var2x.toString());
               byte var3 = (byte)WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_2;
               WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)27, new byte[]{var3}, 1);
            }

         }
      });
   }

   public void init_free_dive_surface_time_alarm_threshold_3(View var1) {
      final TextView var2 = (TextView)var1.findViewById(2131231037);
      Button var3 = (Button)var1.findViewById(2131230877);
      Button var4 = (Button)var1.findViewById(2131230890);
      if (WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_3 < 0) {
         WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_3 = 0;
      }

      if (WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_3 > 60) {
         WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_3 = 60;
      }

      StringBuilder var5 = new StringBuilder();
      var5.append(WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_3);
      var5.append(" min");
      var2.setText(var5.toString());
      var3.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            if (WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_3 > 0) {
               DataStruct.WatchSetting_GB var4 = WatchOp.watchSetting_gb;
               --var4.free_dive_surface_time_alarm_threshold_3;
               TextView var5 = var2;
               StringBuilder var2x = new StringBuilder();
               var2x.append(WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_3);
               var2x.append(" min");
               var5.setText(var2x.toString());
               byte var3 = (byte)WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_3;
               WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)28, new byte[]{var3}, 1);
            }

         }
      });
      var4.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            if (WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_3 < 60) {
               DataStruct.WatchSetting_GB var4 = WatchOp.watchSetting_gb;
               ++var4.free_dive_surface_time_alarm_threshold_3;
               TextView var5 = var2;
               StringBuilder var2x = new StringBuilder();
               var2x.append(WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_3);
               var2x.append(" min");
               var5.setText(var2x.toString());
               byte var3 = (byte)WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_3;
               WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)28, new byte[]{var3}, 1);
            }

         }
      });
   }

   public void init_free_dive_time_alarm(View var1) {
      Switch var3 = (Switch)var1.findViewById(2131231038);
      boolean var2;
      if (WatchOp.watchSetting_gb.free_dive_time_alarm == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      var3.setChecked(var2);
      var3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
         public void onCheckedChanged(CompoundButton var1, boolean var2) {
            WatchOp.watchSetting_gb.free_dive_time_alarm = var2 ^ 1;
            byte var3 = (byte)WatchOp.watchSetting_gb.free_dive_time_alarm;
            WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)17, new byte[]{var3}, 1);
         }
      });
   }

   public void init_free_dive_time_alarm_threshold_1(View var1) {
      final TextView var2 = (TextView)var1.findViewById(2131231039);
      Button var3 = (Button)var1.findViewById(2131230878);
      Button var4 = (Button)var1.findViewById(2131230891);
      if (WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_1 < 0) {
         WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_1 = 0;
      }

      if (WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_1 > 360) {
         WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_1 = 360;
      }

      StringBuilder var5 = new StringBuilder();
      var5.append(WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_1);
      var5.append(" sec");
      var2.setText(var5.toString());
      var3.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            if (WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_1 > 0) {
               DataStruct.WatchSetting_GB var3 = WatchOp.watchSetting_gb;
               --var3.free_dive_time_alarm_threshold_1;
               TextView var4 = var2;
               StringBuilder var2x = new StringBuilder();
               var2x.append(WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_1);
               var2x.append(" sec");
               var4.setText(var2x.toString());
               byte[] var5 = ByteOp.intToUint16(WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_1, WatchOp.byteOrder);
               WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)20, var5, var5.length);
            }

         }
      });
      var4.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            if (WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_1 < 360) {
               DataStruct.WatchSetting_GB var3 = WatchOp.watchSetting_gb;
               ++var3.free_dive_time_alarm_threshold_1;
               TextView var2x = var2;
               StringBuilder var4 = new StringBuilder();
               var4.append(WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_1);
               var4.append(" sec");
               var2x.setText(var4.toString());
               byte[] var5 = ByteOp.intToUint16(WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_1, WatchOp.byteOrder);
               WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)20, var5, var5.length);
            }

         }
      });
   }

   public void init_free_dive_time_alarm_threshold_2(View var1) {
      final TextView var2 = (TextView)var1.findViewById(2131231040);
      Button var3 = (Button)var1.findViewById(2131230879);
      Button var5 = (Button)var1.findViewById(2131230892);
      if (WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_2 < 0) {
         WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_2 = 0;
      }

      if (WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_2 > 360) {
         WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_2 = 360;
      }

      StringBuilder var4 = new StringBuilder();
      var4.append(WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_2);
      var4.append(" sec");
      var2.setText(var4.toString());
      var3.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            if (WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_2 > 0) {
               DataStruct.WatchSetting_GB var3 = WatchOp.watchSetting_gb;
               --var3.free_dive_time_alarm_threshold_2;
               TextView var2x = var2;
               StringBuilder var4 = new StringBuilder();
               var4.append(WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_2);
               var4.append(" sec");
               var2x.setText(var4.toString());
               byte[] var5 = ByteOp.intToUint16(WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_2, WatchOp.byteOrder);
               WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)21, var5, var5.length);
            }

         }
      });
      var5.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            if (WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_2 < 360) {
               DataStruct.WatchSetting_GB var3 = WatchOp.watchSetting_gb;
               ++var3.free_dive_time_alarm_threshold_2;
               TextView var2x = var2;
               StringBuilder var4 = new StringBuilder();
               var4.append(WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_2);
               var4.append(" sec");
               var2x.setText(var4.toString());
               byte[] var5 = ByteOp.intToUint16(WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_2, WatchOp.byteOrder);
               WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)21, var5, var5.length);
            }

         }
      });
   }

   public void init_free_dive_time_alarm_threshold_3(View var1) {
      final TextView var2 = (TextView)var1.findViewById(2131231041);
      Button var3 = (Button)var1.findViewById(2131230880);
      Button var4 = (Button)var1.findViewById(2131230893);
      if (WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_3 < 0) {
         WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_3 = 0;
      }

      if (WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_3 > 360) {
         WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_3 = 360;
      }

      StringBuilder var5 = new StringBuilder();
      var5.append(WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_3);
      var5.append(" sec");
      var2.setText(var5.toString());
      var3.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            if (WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_3 > 0) {
               DataStruct.WatchSetting_GB var3 = WatchOp.watchSetting_gb;
               --var3.free_dive_time_alarm_threshold_3;
               TextView var2x = var2;
               StringBuilder var4 = new StringBuilder();
               var4.append(WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_3);
               var4.append(" sec");
               var2x.setText(var4.toString());
               byte[] var5 = ByteOp.intToUint16(WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_3, WatchOp.byteOrder);
               WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)22, var5, var5.length);
            }

         }
      });
      var4.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            if (WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_3 < 360) {
               DataStruct.WatchSetting_GB var3 = WatchOp.watchSetting_gb;
               ++var3.free_dive_time_alarm_threshold_3;
               TextView var2x = var2;
               StringBuilder var4 = new StringBuilder();
               var4.append(WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_3);
               var4.append(" sec");
               var2x.setText(var4.toString());
               byte[] var5 = ByteOp.intToUint16(WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_3, WatchOp.byteOrder);
               WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)22, var5, var5.length);
            }

         }
      });
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
   }

   public View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
      context = this.getContext();
      View var4 = var1.inflate(2131427400, var2, false);
      this.init_free_dive_time_alarm(var4);
      this.init_free_dive_depth_alarm(var4);
      this.init_free_dive_surface_time_alarm(var4);
      this.init_free_dive_time_alarm_threshold_1(var4);
      this.init_free_dive_time_alarm_threshold_2(var4);
      this.init_free_dive_time_alarm_threshold_3(var4);
      this.init_free_dive_depth_alarm_threshold_1(var4);
      this.init_free_dive_depth_alarm_threshold_2(var4);
      this.init_free_dive_depth_alarm_threshold_3(var4);
      this.init_free_dive_surface_time_alarm_threshold_1(var4);
      this.init_free_dive_surface_time_alarm_threshold_2(var4);
      this.init_free_dive_surface_time_alarm_threshold_3(var4);
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
