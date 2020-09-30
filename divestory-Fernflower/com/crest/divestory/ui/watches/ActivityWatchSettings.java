package com.crest.divestory.ui.watches;

import android.app.Activity;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.crest.divestory.AppBase;
import com.crest.divestory.WatchOp;
import com.google.android.material.tabs.TabLayout;
import com.syntak.library.ByteOp;
import com.syntak.library.StringOp;
import com.syntak.library.UiOp;
import com.syntak.library.time.TimerResetable;
import java.nio.ByteOrder;
import java.util.HashMap;

public class ActivityWatchSettings extends AppCompatActivity {
   private Activity activity;
   private Context context;
   Handler handler;
   private boolean isActivityPaused = false;
   boolean isReceiverStopped = false;
   private HashMap<Byte, Boolean> readLogs = new HashMap();
   ActivityWatchSettings.ReceiverResponse receiverResponse = null;
   ActivityWatchSettings.ReceiverTimeout receiverTimeout = null;
   private RelativeLayout sign_processing;
   private TextView status;
   TimerResetable timerResetable = null;
   ViewPager view_pager;
   private WatchOp.WATCH_REPLY watch_reply;

   private void init_UI() {
      this.setContentView(2131427362);
      this.context = this;
      this.activity = this;
      RelativeLayout var1 = (RelativeLayout)this.findViewById(2131231303);
      this.sign_processing = var1;
      var1.setVisibility(0);
      this.status = (TextView)this.findViewById(2131231328);
      this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      AppBase.setTitleAndColor(this, this.getResources().getString(2131689857), 2131034179);
      this.view_pager = (ViewPager)this.findViewById(2131231440);
      TabLayout var2 = (TabLayout)this.findViewById(2131231345);
      var2.setupWithViewPager(this.view_pager, true);
      var2.setTabIndicatorFullWidth(true);
      this.init_timer();
   }

   private void init_pager(ViewPager var1) {
      PagerAdapterWatchSettings var2 = new PagerAdapterWatchSettings(this.getSupportFragmentManager());
      var2.set_para(this.context);
      var1.setAdapter(var2);
      var1.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
         public void onPageSelected(int var1) {
            ActivityWatchSettings.this.sync_watch_settings();
         }
      });
   }

   private void init_resume() {
      this.isActivityPaused = false;
      this.start_receivers();
      if (this.handler == null) {
         this.start_handler();
         this.readSetting((byte)1);
      }

   }

   private void init_timer() {
      if (AppBase.timerResetable == null) {
         TimerResetable var1 = new TimerResetable(this, 15000);
         this.timerResetable = var1;
         AppBase.timerResetable = var1;
      } else {
         this.timerResetable = AppBase.timerResetable;
      }

   }

   private void readSetting(byte var1) {
      this.readSetting(WatchOp.watch_connected.mac_address, var1);
   }

   private void readSetting(final String var1, byte var2) {
      this.readLogs.put(var2, false);
      this.handler.postDelayed(new Runnable(var2) {
         // $FF: synthetic field
         final byte val$sub_command;

         {
            this.val$sub_command = (byte)var3;
         }

         public void run() {
            WatchOp.readSetting(var1, this.val$sub_command);
            ActivityWatchSettings.this.timerResetable.resetTimer(10000).start();
         }
      }, 100L);
   }

   private void start_handler() {
      this.handler = new Handler() {
         public void handleMessage(Message var1) {
            byte var2 = (byte)var1.what;
            if (ActivityWatchSettings.this.readLogs.containsKey(var2) && !(Boolean)ActivityWatchSettings.this.readLogs.get(var2)) {
               ActivityWatchSettings.this.readLogs.put(var2, true);
               if (ActivityWatchSettings.this.watch_reply.data_length <= 0) {
                  Context var3 = ActivityWatchSettings.this.context;
                  StringBuilder var5 = new StringBuilder();
                  var5.append("Watch reply=");
                  var5.append(StringOp.getHexFromBytes(ActivityWatchSettings.this.watch_reply.raw_response, false, " "));
                  UiOp.toast_message(var3, var5.toString(), true);
                  return;
               }

               Log.d("ActivityWatchSettings", String.valueOf(var2));
               switch(var2) {
               case -1:
                  UiOp.toast_message(ActivityWatchSettings.this.context, 2131689779);
                  ActivityWatchSettings.this.exit_activity();
               case 0:
               default:
                  break;
               case 1:
                  WatchOp.watchSetting_gb.year = ActivityWatchSettings.this.watch_reply.data[0] + 2000;
                  WatchOp.watchSetting_gb.month = ActivityWatchSettings.this.watch_reply.data[1];
                  WatchOp.watchSetting_gb.day = ActivityWatchSettings.this.watch_reply.data[2];
                  ActivityWatchSettings.this.readSetting((byte)2);
                  break;
               case 2:
                  WatchOp.watchSetting_gb.date_format = ActivityWatchSettings.this.watch_reply.data[0];
                  ActivityWatchSettings.this.readSetting((byte)3);
                  break;
               case 3:
                  WatchOp.watchSetting_gb.hour = ActivityWatchSettings.this.watch_reply.data[0];
                  WatchOp.watchSetting_gb.minute = ActivityWatchSettings.this.watch_reply.data[1];
                  ActivityWatchSettings.this.readSetting((byte)4);
                  break;
               case 4:
                  WatchOp.watchSetting_gb.time_format = ActivityWatchSettings.this.watch_reply.data[0];
                  ActivityWatchSettings.this.readSetting((byte)5);
                  break;
               case 5:
                  WatchOp.watchSetting_gb.auto_dive_type = ActivityWatchSettings.this.watch_reply.data[0];
                  ActivityWatchSettings.this.readSetting((byte)6);
                  break;
               case 6:
                  WatchOp.watchSetting_gb.O2_ratio = ActivityWatchSettings.this.watch_reply.data[0];
                  ActivityWatchSettings.this.readSetting((byte)7);
                  break;
               case 7:
                  WatchOp.watchSetting_gb.PPO2 = ActivityWatchSettings.this.watch_reply.data[0];
                  ActivityWatchSettings.this.readSetting((byte)8);
                  break;
               case 8:
                  WatchOp.watchSetting_gb.safety_factor = ActivityWatchSettings.this.watch_reply.data[0];
                  ActivityWatchSettings.this.readSetting((byte)9);
                  break;
               case 9:
                  WatchOp.watchSetting_gb.scuba_dive_depth_alarm = ActivityWatchSettings.this.watch_reply.data[0];
                  ActivityWatchSettings.this.readSetting((byte)10);
                  break;
               case 10:
                  WatchOp.watchSetting_gb.scuba_dive_time_alarm = ActivityWatchSettings.this.watch_reply.data[0];
                  ActivityWatchSettings.this.readSetting((byte)11);
                  break;
               case 11:
                  WatchOp.watchSetting_gb.scuba_dive_depth_alarm_threshold = ActivityWatchSettings.this.watch_reply.data[0];
                  ActivityWatchSettings.this.readSetting((byte)12);
                  break;
               case 12:
                  WatchOp.watchSetting_gb.scuba_dive_time_alarm_threshold = ActivityWatchSettings.this.watch_reply.data[0];
                  ActivityWatchSettings.this.readSetting((byte)13);
                  break;
               case 13:
                  WatchOp.watchSetting_gb.scuba_dive_log_sampling_rate = ActivityWatchSettings.this.watch_reply.data[0];
                  ActivityWatchSettings.this.readSetting((byte)14);
                  break;
               case 14:
                  WatchOp.watchSetting_gb.scuba_dive_log_start_depth = ActivityWatchSettings.this.watch_reply.data[0];
                  ActivityWatchSettings.this.readSetting((byte)15);
                  break;
               case 15:
                  WatchOp.watchSetting_gb.scuba_dive_log_stop_time = ActivityWatchSettings.this.watch_reply.data[0];
                  ActivityWatchSettings.this.readSetting((byte)16);
                  break;
               case 16:
                  WatchOp.watchSetting_gb.G_sensor = ActivityWatchSettings.this.watch_reply.data[0];
                  ActivityWatchSettings.this.readSetting((byte)17);
                  break;
               case 17:
                  WatchOp.watchSetting_gb.free_dive_time_alarm = ActivityWatchSettings.this.watch_reply.data[0];
                  ActivityWatchSettings.this.readSetting((byte)18);
                  break;
               case 18:
                  WatchOp.watchSetting_gb.free_dive_depth_alarm = ActivityWatchSettings.this.watch_reply.data[0];
                  ActivityWatchSettings.this.readSetting((byte)19);
                  break;
               case 19:
                  WatchOp.watchSetting_gb.free_dive_surface_time_alarm = ActivityWatchSettings.this.watch_reply.data[0];
                  ActivityWatchSettings.this.readSetting((byte)20);
                  break;
               case 20:
                  WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_1 = ByteOp.get_Integer_from_2_bytes_unsigned(ActivityWatchSettings.this.watch_reply.data, ByteOrder.LITTLE_ENDIAN);
                  ActivityWatchSettings.this.readSetting((byte)21);
                  break;
               case 21:
                  WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_2 = ByteOp.get_Integer_from_2_bytes_unsigned(ActivityWatchSettings.this.watch_reply.data, ByteOrder.LITTLE_ENDIAN);
                  ActivityWatchSettings.this.readSetting((byte)22);
                  break;
               case 22:
                  WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_3 = ByteOp.get_Integer_from_2_bytes_unsigned(ActivityWatchSettings.this.watch_reply.data, ByteOrder.LITTLE_ENDIAN);
                  ActivityWatchSettings.this.readSetting((byte)23);
                  break;
               case 23:
                  WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_1 = ActivityWatchSettings.this.watch_reply.data[0];
                  ActivityWatchSettings.this.readSetting((byte)24);
                  break;
               case 24:
                  WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_2 = ActivityWatchSettings.this.watch_reply.data[0];
                  ActivityWatchSettings.this.readSetting((byte)25);
                  break;
               case 25:
                  WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_3 = ActivityWatchSettings.this.watch_reply.data[0];
                  ActivityWatchSettings.this.readSetting((byte)26);
                  break;
               case 26:
                  WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_1 = ActivityWatchSettings.this.watch_reply.data[0];
                  ActivityWatchSettings.this.readSetting((byte)27);
                  break;
               case 27:
                  WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_2 = ActivityWatchSettings.this.watch_reply.data[0];
                  ActivityWatchSettings.this.readSetting((byte)28);
                  break;
               case 28:
                  WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_3 = ActivityWatchSettings.this.watch_reply.data[0];
                  ActivityWatchSettings.this.readSetting((byte)29);
                  break;
               case 29:
                  WatchOp.watchSetting_gb.display_unit = ActivityWatchSettings.this.watch_reply.data[0];
                  ActivityWatchSettings.this.readSetting((byte)30);
                  break;
               case 30:
                  WatchOp.watchSetting_gb.power_saving = ActivityWatchSettings.this.watch_reply.data[0];
                  ActivityWatchSettings.this.readSetting((byte)31);
                  break;
               case 31:
                  WatchOp.watchSetting_gb.UTC_offset = ActivityWatchSettings.this.watch_reply.data[0];
                  ActivityWatchSettings.this.readSetting((byte)32);
                  break;
               case 32:
                  WatchOp.watchSetting_gb.buzzer = ActivityWatchSettings.this.watch_reply.data[0];
                  ActivityWatchSettings.this.readSetting((byte)33);
                  break;
               case 33:
                  WatchOp.watchSetting_gb.backlight = ActivityWatchSettings.this.watch_reply.data[0];
                  ActivityWatchSettings.this.readSetting((byte)34);
                  break;
               case 34:
                  WatchOp.watchSetting_gb.vibrator = ActivityWatchSettings.this.watch_reply.data[0];
                  ActivityWatchSettings.this.sign_processing.setVisibility(8);
                  ActivityWatchSettings var4 = ActivityWatchSettings.this;
                  var4.init_pager(var4.view_pager);
               }
            }

         }
      };
   }

   private void start_receivers() {
      if (this.receiverTimeout == null) {
         ActivityWatchSettings.ReceiverTimeout var1 = new ActivityWatchSettings.ReceiverTimeout();
         this.receiverTimeout = var1;
         this.activity.registerReceiver(var1, new IntentFilter("ACTION_TIMEOUT"));
      }

      if (this.receiverResponse == null) {
         ActivityWatchSettings.ReceiverResponse var2 = new ActivityWatchSettings.ReceiverResponse();
         this.receiverResponse = var2;
         this.activity.registerReceiver(var2, new IntentFilter("ACTION_DATA_AVAILABLE"));
      }

   }

   private void stop_receivers() {
      ActivityWatchSettings.ReceiverTimeout var1 = this.receiverTimeout;
      if (var1 != null) {
         this.activity.unregisterReceiver(var1);
      }

      this.receiverTimeout = null;
      ActivityWatchSettings.ReceiverResponse var2 = this.receiverResponse;
      if (var2 != null) {
         this.activity.unregisterReceiver(var2);
      }

      this.receiverResponse = null;
   }

   private void sync_watch_settings() {
      byte[] var1 = new byte[1];
      WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)39, var1, 1);
   }

   public void exit_activity() {
      this.stop_receivers();
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
      this.init_UI();
   }

   public boolean onCreateOptionsMenu(Menu var1) {
      return true;
   }

   public void onDestroy() {
      super.onDestroy();
   }

   public boolean onOptionsItemSelected(MenuItem var1) {
      if (var1.getItemId() == 16908332) {
         this.exit_activity();
      }

      return super.onOptionsItemSelected(var1);
   }

   public void onPause() {
      super.onPause();
      this.isActivityPaused = true;
      this.sync_watch_settings();
   }

   protected void onResume() {
      super.onResume();
      this.init_resume();
   }

   public class ReceiverResponse extends BroadcastReceiver {
      public void onReceive(Context var1, Intent var2) {
         ActivityWatchSettings.this.timerResetable.pause();
         if (!ActivityWatchSettings.this.isActivityPaused) {
            String var4 = var2.getStringExtra("address");
            BluetoothGattCharacteristic var3 = WatchOp.bleOp.get_Characteristic_received(var4);
            ActivityWatchSettings.this.watch_reply = WatchOp.handle_received_charteristics(var4, var3);
            if (ActivityWatchSettings.this.watch_reply.error == WatchOp.WATCH_REPLY.Error.NO_ERROR) {
               ActivityWatchSettings.this.handler.obtainMessage(ActivityWatchSettings.this.watch_reply.sub_command, var4).sendToTarget();
            } else {
               if (ActivityWatchSettings.this.watch_reply.raw_response != null) {
                  var4 = StringOp.getHexFromBytes(ActivityWatchSettings.this.watch_reply.raw_response, false, " ");
               } else {
                  var4 = "null";
               }

               StringBuilder var5 = new StringBuilder();
               var5.append("Watch response:");
               var5.append(var4);
               UiOp.toast_message(var1, var5.toString(), true);
            }

         }
      }
   }

   public class ReceiverTimeout extends BroadcastReceiver {
      public void onReceive(Context var1, Intent var2) {
         WatchOp.isTimeout = true;
         UiOp.toast_message(var1, 2131689779);
         ActivityWatchSettings.this.exit_activity();
      }
   }
}
