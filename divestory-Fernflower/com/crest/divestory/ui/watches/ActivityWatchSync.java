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
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.crest.divestory.AppBase;
import com.crest.divestory.DataStruct;
import com.crest.divestory.MainActivity;
import com.crest.divestory.WatchOp;
import com.syntak.library.BleOp;
import com.syntak.library.ByteOp;
import com.syntak.library.StringOp;
import com.syntak.library.TimeOp;
import com.syntak.library.UiOp;
import com.syntak.library.time.TimerResetable;

public class ActivityWatchSync extends AppCompatActivity {
   private static final String ARG_ACTION = "action";
   private static final String ARG_ID_WATCH = "id_watch";
   private static final String ARG_RESULT = "result";
   private static final String ARG_SERIAL_NO = "serial_no";
   byte GET_UTC_OFFSET = (byte)-2;
   byte TIMEOUT = (byte)-1;
   private WatchOp.ACTION action;
   private Activity activity;
   private DataStruct.BleDevice bleDevice = null;
   LinearLayout button_download_logs;
   LinearLayout button_upgrade_firmware;
   LinearLayout button_watch_settings;
   private Context context;
   byte current_sub_command = (byte)0;
   boolean flag_check_connection = true;
   Handler handler = null;
   private boolean isActivityPaused = false;
   boolean isConnected = false;
   boolean isReceiverStopped = false;
   boolean isServicesFound = false;
   boolean isUpgradeWarned = false;
   ProgressBar progress_circle;
   ActivityWatchSync.ReceiverConnectionStateChanged receiverConnetionStateChanged = null;
   ActivityWatchSync.ReceiverResponse receiverResponse = null;
   ActivityWatchSync.ReceiverServices receiverServices = null;
   ActivityWatchSync.ReceiverTimeout receiverTimeout = null;
   TextView status;
   ImageView status_watch;
   TextView textFirmwareVersion;
   TextView textHardwareVersion;
   TextView textModelName;
   TextView textSerialNo;
   TimerResetable timerResetable = null;
   int trial_connecting_times = 3;
   private TextView warning;
   private WatchOp.WATCH_REPLY watch_reply;

   private void init_UI() {
      this.setContentView(2131427363);
      this.context = this;
      this.activity = this;
      if (WatchOp.devices_scanned == null || WatchOp.devices_scanned.length() <= 0) {
         this.exit_activity();
      }

      this.init_action_bar();
      this.textModelName = (TextView)this.findViewById(2131231155);
      this.status = (TextView)this.findViewById(2131231328);
      this.status_watch = (ImageView)this.findViewById(2131231334);
      ProgressBar var1 = (ProgressBar)this.findViewById(2131231228);
      this.progress_circle = var1;
      var1.setVisibility(0);
      int var2 = this.getIntent().getIntExtra("index", -1);
      if (var2 >= 0) {
         this.bleDevice = (DataStruct.BleDevice)WatchOp.devices_scanned.list.get(var2);
         WatchOp.watch_connected = new DataStruct.MyWatch();
         WatchOp.watch_connected.ble_device_name = this.bleDevice.name;
         WatchOp.watch_connected.mac_address = this.bleDevice.mac_address;
      } else {
         this.exit_activity();
      }

      this.textSerialNo = (TextView)this.findViewById(2131231383);
      this.textHardwareVersion = (TextView)this.findViewById(2131231382);
      this.textFirmwareVersion = (TextView)this.findViewById(2131231381);
      this.button_download_logs = (LinearLayout)this.findViewById(2131230869);
      this.button_upgrade_firmware = (LinearLayout)this.findViewById(2131230896);
      this.button_watch_settings = (LinearLayout)this.findViewById(2131230897);
      this.button_download_logs.setEnabled(false);
      this.button_upgrade_firmware.setEnabled(false);
      this.button_watch_settings.setEnabled(false);
      this.button_download_logs.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            ActivityWatchSync.this.clicked_download_logs(var1);
         }
      });
      this.button_upgrade_firmware.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            ActivityWatchSync.this.clicked_upgrade(var1);
         }
      });
      this.button_watch_settings.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            ActivityWatchSync.this.clicked_set_watch(var1);
         }
      });
      this.init_timer();
      this.start_receivers();
   }

   private void init_action_bar() {
      this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      this.getSupportActionBar().setHomeAsUpIndicator(2131165356);
      AppBase.setTitleAndColor(this, 2131689732, 2131034179);
   }

   private void init_resume() {
      if (AppBase.isExitSync) {
         AppBase.isExitSync = false;
         this.exit_activity();
      } else {
         this.isActivityPaused = false;
         if (WatchOp.isFirmwareUpgraded) {
            WatchOp.isSyncDone = true;
            this.exit_activity();
         } else if (this.handler == null) {
            this.start_handler();
            this.handler.postDelayed(new Runnable() {
               public void run() {
                  ActivityWatchSync.this.handler.obtainMessage(0).sendToTarget();
               }
            }, 500L);
         }
      }

   }

   private void init_timer() {
      if (AppBase.timerResetable == null) {
         TimerResetable var1 = new TimerResetable(this, 10000);
         this.timerResetable = var1;
         AppBase.timerResetable = var1;
      } else {
         this.timerResetable = AppBase.timerResetable;
      }

   }

   private void readInfo(byte var1) {
      this.readInfo(WatchOp.watch_connected.mac_address, var1);
   }

   private void readInfo(String var1, byte var2) {
      this.current_sub_command = var2;
      WatchOp.readInfo(var1, var2);
      this.timerResetable.resetTimer(10000).start();
   }

   private void start_handler() {
      this.handler = new Handler() {
         public void handleMessage(Message var1) {
            byte var2 = (byte)var1.what;
            if (var2 != 31) {
               String var5;
               switch(var2) {
               case -1:
                  UiOp.toast_message(ActivityWatchSync.this.context, 2131689779);
                  WatchOp.isTimeout = true;
                  ActivityWatchSync.this.exit_activity();
                  break;
               case 0:
                  WatchOp.bleOp.do_Task(60, ActivityWatchSync.this.bleDevice.mac_address);
                  ActivityWatchSync.this.timerResetable.resetTimer(30000).start();
                  break;
               case 1:
                  WatchOp.watch_connected.hardware_version = new String(ActivityWatchSync.this.watch_reply.data, 0, ActivityWatchSync.this.watch_reply.data_length);
                  var5 = WatchOp.getModelName(WatchOp.watch_connected.serial_number, WatchOp.watch_connected.hardware_version);
                  WatchOp.watch_connected.model_name = var5;
                  WatchOp.watch_connected.model_name_to_show = WatchOp.firmwares.get_model_name_to_show(var5);
                  ActivityWatchSync.this.textHardwareVersion.setText(WatchOp.watch_connected.hardware_version);
                  ActivityWatchSync.this.readInfo((byte)2);
                  break;
               case 2:
                  WatchOp.watch_connected.firmware_version = new String(ActivityWatchSync.this.watch_reply.data, 0, ActivityWatchSync.this.watch_reply.data_length);
                  ActivityWatchSync.this.textFirmwareVersion.setText(WatchOp.watch_connected.firmware_version);
                  ActivityWatchSync.this.readInfo((byte)4);
                  break;
               case 3:
                  ActivityWatchSync.this.flag_check_connection = false;
                  var5 = StringOp.ByteArrayToString(ActivityWatchSync.this.watch_reply.data).substring(0, 12);
                  WatchOp.prefSerialNumber = var5;
                  WatchOp.watch_connected.serial_number = var5;
                  ActivityWatchSync.this.textSerialNo.setText(WatchOp.watch_connected.serial_number.substring(0, 11));
                  if (AppBase.dbOp.isMyWatchLogged(WatchOp.watch_connected.serial_number)) {
                     WatchOp.watch_connected.isStored = true;
                     WatchOp.watch_connected.last_dive_log_index = AppBase.dbOp.getMyWatchLastDiveLogIndex(WatchOp.watch_connected.serial_number);
                     WatchOp.watch_connected.start_time_for_last_downloaded_log = AppBase.dbOp.getMyWatchStartTimeForLastDownloadedLog(WatchOp.watch_connected.serial_number);
                     WatchOp.watch_connected.end_time_for_last_downloaded_log = AppBase.dbOp.getMyWatchEndTimeForLastDownloadedLog(WatchOp.watch_connected.serial_number);
                  } else {
                     WatchOp.watch_connected.isStored = false;
                     WatchOp.watch_connected.last_dive_log_index = 0;
                     WatchOp.watch_connected.start_time_for_last_downloaded_log = 0L;
                     WatchOp.watch_connected.end_time_for_last_downloaded_log = 0L;
                  }

                  ActivityWatchSync.this.readInfo((byte)1);
                  break;
               case 4:
                  WatchOp.new_last_dive_log_index = ByteOp.get_Integer_from_2_bytes_unsigned(ActivityWatchSync.this.watch_reply.data, WatchOp.byteOrder);
                  ActivityWatchSync.this.readInfo((byte)5);
                  break;
               case 5:
                  if (ActivityWatchSync.this.watch_reply.data != null || ActivityWatchSync.this.watch_reply.data.length > 0) {
                     long var3 = TimeOp.DateTimeToMs(ActivityWatchSync.this.watch_reply.data[0] + 2000, ActivityWatchSync.this.watch_reply.data[1] - 1, ActivityWatchSync.this.watch_reply.data[2], ActivityWatchSync.this.watch_reply.data[3], ActivityWatchSync.this.watch_reply.data[4]);
                     WatchOp.watch_connected.last_dive_time = var3;
                  }

                  ActivityWatchSync.this.readInfo((byte)6);
                  break;
               case 6:
                  WatchOp.watch_connected.scuba_dive_total_time = ByteOp.uint32ToLong(ActivityWatchSync.this.watch_reply.data, WatchOp.byteOrder);
                  ActivityWatchSync.this.readInfo((byte)7);
                  break;
               case 7:
                  WatchOp.watch_connected.scuba_dive_max_depth = ByteOp.uint16ToInt(ActivityWatchSync.this.watch_reply.data, WatchOp.byteOrder);
                  ActivityWatchSync.this.readInfo((byte)8);
                  break;
               case 8:
                  WatchOp.watch_connected.scuba_dive_times = ByteOp.uint16ToInt(ActivityWatchSync.this.watch_reply.data, WatchOp.byteOrder);
                  ActivityWatchSync.this.readInfo((byte)9);
                  break;
               case 9:
                  WatchOp.watch_connected.free_dive_times = ByteOp.uint16ToInt(ActivityWatchSync.this.watch_reply.data, WatchOp.byteOrder);
                  ActivityWatchSync.this.readInfo((byte)10);
                  break;
               case 10:
                  WatchOp.watch_connected.free_dive_longest_time = ByteOp.uint16ToInt(ActivityWatchSync.this.watch_reply.data, WatchOp.byteOrder);
                  ActivityWatchSync.this.readInfo((byte)12);
                  break;
               default:
                  switch(var2) {
                  case 12:
                     WatchOp.watch_connected.free_dive_max_depth = ByteOp.uint16ToInt(ActivityWatchSync.this.watch_reply.data, WatchOp.byteOrder);
                     ActivityWatchSync.this.readInfo((byte)14);
                     break;
                  case 13:
                     if (ActivityWatchSync.this.watch_reply.data_length == 2) {
                        WatchOp.watch_connected.no_dive_time = ByteOp.uint16ToInt(ActivityWatchSync.this.watch_reply.data, WatchOp.byteOrder);
                     } else {
                        WatchOp.watch_connected.no_dive_time = ActivityWatchSync.this.watch_reply.data[0];
                     }

                     WatchOp.readSetting(WatchOp.watch_connected.mac_address, (byte)31);
                     break;
                  case 14:
                     if (ActivityWatchSync.this.watch_reply.data_length == 2) {
                        WatchOp.watch_connected.no_flight_time = ByteOp.uint16ToInt(ActivityWatchSync.this.watch_reply.data, WatchOp.byteOrder);
                     } else {
                        WatchOp.watch_connected.no_flight_time = ActivityWatchSync.this.watch_reply.data[0];
                     }

                     ActivityWatchSync.this.readInfo((byte)13);
                  }
               }
            } else {
               WatchOp.utc_offset = ActivityWatchSync.this.watch_reply.data[0] - 12;
               if (WatchOp.watch_connected.isStored) {
                  AppBase.dbOp.updateMyWatch(WatchOp.watch_connected);
               } else {
                  WatchOp.watch_connected.isStored = true;
                  AppBase.dbOp.insertMyWatch(WatchOp.watch_connected);
                  if (WatchOp.myWatches == null) {
                     WatchOp.myWatches = new DataStruct.MyWatches();
                  }

                  WatchOp.myWatches.add(WatchOp.watch_connected);
                  if (MainActivity.pagerAdapter != null) {
                     MainActivity.pagerAdapter.notifyDataSetChanged();
                  }

                  if (FragmentSyncedWatchesList.adapter != null) {
                     FragmentSyncedWatchesList.adapter.notifyDataSetChanged();
                  }
               }

               ActivityWatchSync.this.progress_circle.setVisibility(8);
               ActivityWatchSync.this.textModelName.setText(WatchOp.watch_connected.model_name_to_show);
               ActivityWatchSync.this.status_watch.setImageDrawable(ActivityWatchSync.this.getDrawable(2131165429));
               ActivityWatchSync.this.status.setText(ActivityWatchSync.this.getString(2131689572));
               if (WatchOp.isSerialNumberValid(WatchOp.watch_connected.serial_number) && !WatchOp.isOldFirmware(WatchOp.watch_connected.firmware_version)) {
                  ActivityWatchSync.this.button_download_logs.setEnabled(true);
                  ActivityWatchSync.this.button_watch_settings.setEnabled(true);
               }

               if (!WatchOp.watch_connected.model_name.equals("Unknown Model")) {
                  ActivityWatchSync.this.button_upgrade_firmware.setEnabled(true);
               }

               ActivityWatchSync.this.stop_receivers();
            }

         }
      };
   }

   private void start_receivers() {
      if (this.receiverTimeout == null) {
         ActivityWatchSync.ReceiverTimeout var1 = new ActivityWatchSync.ReceiverTimeout();
         this.receiverTimeout = var1;
         this.activity.registerReceiver(var1, new IntentFilter("ACTION_TIMEOUT"));
      }

      if (this.receiverServices == null) {
         ActivityWatchSync.ReceiverServices var2 = new ActivityWatchSync.ReceiverServices();
         this.receiverServices = var2;
         this.activity.registerReceiver(var2, new IntentFilter("ACTION_GATT_SERVICES_DISCOVERED"));
      }

      if (this.receiverConnetionStateChanged == null) {
         ActivityWatchSync.ReceiverConnectionStateChanged var3 = new ActivityWatchSync.ReceiverConnectionStateChanged();
         this.receiverConnetionStateChanged = var3;
         this.activity.registerReceiver(var3, new IntentFilter("ACTION_GATT_CONNECTION_CHANGED"));
      }

      if (this.receiverResponse == null) {
         ActivityWatchSync.ReceiverResponse var4 = new ActivityWatchSync.ReceiverResponse();
         this.receiverResponse = var4;
         this.activity.registerReceiver(var4, new IntentFilter("ACTION_DATA_AVAILABLE"));
      }

      this.isReceiverStopped = false;
   }

   private void stop_receivers() {
      if (!this.isReceiverStopped) {
         this.isReceiverStopped = true;

         try {
            if (this.receiverTimeout != null) {
               this.activity.unregisterReceiver(this.receiverTimeout);
               this.receiverTimeout = null;
            }

            if (this.receiverServices != null) {
               this.activity.unregisterReceiver(this.receiverServices);
               this.receiverServices = null;
            }

            if (this.receiverResponse != null) {
               this.activity.unregisterReceiver(this.receiverResponse);
               this.receiverServices = null;
            }

            if (this.receiverConnetionStateChanged != null) {
               this.activity.unregisterReceiver(this.receiverConnetionStateChanged);
               this.receiverConnetionStateChanged = null;
            }
         } catch (IllegalArgumentException var2) {
            var2.printStackTrace();
         }

      }
   }

   private void writeSerialNumber(String var1) {
      if (var1 != null) {
         byte[] var2 = ByteOp.get_BytesArray_from_String(var1);
         WatchOp.writeFactoryTest(WatchOp.watch_connected.mac_address, (byte)2, var2, var2.length);
      }
   }

   public void clicked_download_logs(View var1) {
      this.stop_receivers();
      Intent var2 = new Intent();
      var2.setClass(this, ActivityWatchDiveLogsDownload.class);
      this.startActivity(var2);
   }

   public void clicked_set_watch(View var1) {
      this.stop_receivers();
      Intent var2 = new Intent();
      var2.setClass(this, ActivityWatchSettings.class);
      this.startActivity(var2);
   }

   public void clicked_upgrade(View var1) {
      if (WatchOp.firmwares.get_activated(WatchOp.watch_connected.model_name)) {
         this.stop_receivers();
         Intent var2 = new Intent();
         var2.setClass(this, ActivityWatchUpgrade.class);
         this.startActivity(var2);
      }

   }

   public void exit_activity() {
      this.isActivityPaused = true;
      WatchOp.isSyncDone = true;
      this.writeSerialNumber(WatchOp.prefSerialNumber);
      WatchOp.bleOp.do_Task(100, this.bleDevice.mac_address);
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

   public void onDestroy() {
      super.onDestroy();
      this.handler = null;
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
   }

   protected void onResume() {
      super.onResume();
      this.isActivityPaused = false;
      this.init_resume();
   }

   public class ReceiverConnectionStateChanged extends BroadcastReceiver {
      public void onReceive(Context var1, Intent var2) {
         ActivityWatchSync.this.timerResetable.pause();
         if (!ActivityWatchSync.this.isActivityPaused) {
            final String var3 = var2.getStringExtra("address");
            if ((BleOp.CONNECTION_STATE)var2.getSerializableExtra("state") == BleOp.CONNECTION_STATE.CONNECTED) {
               ActivityWatchSync.this.isConnected = true;
               if (ActivityWatchSync.this.isServicesFound) {
                  WatchOp.bleOp.enable_Notification(var3, WatchOp.UUID_SERVICE, WatchOp.UUID_CHARACTERISTIC_SETTING);
                  ActivityWatchSync.this.handler.postDelayed(new Runnable() {
                     public void run() {
                        ActivityWatchSync.this.readInfo(var3, (byte)3);
                     }
                  }, 500L);
               }

               Log.d("Watch Sync", "Connected");
            } else {
               WatchOp.isTimeout = true;
               ActivityWatchSync.this.exit_activity();
               Log.d("Watch Sync", "dis-connected");
            }

         }
      }
   }

   public class ReceiverResponse extends BroadcastReceiver {
      public void onReceive(Context var1, Intent var2) {
         ActivityWatchSync.this.timerResetable.pause();
         if (!ActivityWatchSync.this.isActivityPaused) {
            String var3 = var2.getStringExtra("address");
            BluetoothGattCharacteristic var4 = WatchOp.bleOp.get_Characteristic_received(var3);
            ActivityWatchSync.this.watch_reply = WatchOp.handle_received_charteristics(var3, var4);
            if (ActivityWatchSync.this.watch_reply.error == WatchOp.WATCH_REPLY.Error.NO_ERROR) {
               ActivityWatchSync.this.handler.obtainMessage(ActivityWatchSync.this.watch_reply.sub_command, var3).sendToTarget();
            } else {
               String var5;
               if (ActivityWatchSync.this.watch_reply.raw_response != null) {
                  var5 = StringOp.getHexFromBytes(ActivityWatchSync.this.watch_reply.raw_response, false, " ");
               } else {
                  var5 = "null";
               }

               StringBuilder var6 = new StringBuilder();
               var6.append("Watch response:");
               var6.append(var5);
               UiOp.toast_message(var1, var6.toString(), true);
            }

         }
      }
   }

   public class ReceiverServices extends BroadcastReceiver {
      public void onReceive(Context var1, Intent var2) {
         ActivityWatchSync.this.timerResetable.pause();
         if (!ActivityWatchSync.this.isActivityPaused) {
            ActivityWatchSync.this.isServicesFound = true;
            ActivityWatchSync.this.isConnected = true;
            String var3 = var2.getStringExtra("address");
            WatchOp.bleOp.enable_Notification(var3, WatchOp.UUID_SERVICE, WatchOp.UUID_CHARACTERISTIC_SETTING);
            Runnable var5 = new Runnable() {
               public void run() {
                  WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)40, (byte[])null, 0);
               }
            };
            Runnable var4 = new Runnable() {
               public void run() {
                  ActivityWatchSync.this.readInfo((byte)3);
               }
            };
            ActivityWatchSync.this.handler.postDelayed(var5, 500L);
            ActivityWatchSync.this.handler.postDelayed(var4, 1000L);
         }
      }
   }

   public class ReceiverTimeout extends BroadcastReceiver {
      public void onReceive(Context var1, Intent var2) {
         if (!ActivityWatchSync.this.isActivityPaused) {
            WatchOp.isTimeout = true;
            if (ActivityWatchSync.this.current_sub_command != 0) {
               Log.d("ActivityWatchesSync", "Read Info failed!!");
               UiOp.toast_message(var1, "Read Info failed!!", false);
               ActivityWatchSync.this.exit_activity();
            } else {
               Log.d("ActivityWatchesSync", ActivityWatchSync.this.getString(2131689540));
               UiOp.toast_message(var1, 2131689540);
               ActivityWatchSync.this.exit_activity();
            }

         }
      }
   }
}
