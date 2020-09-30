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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.crest.divestory.AppBase;
import com.crest.divestory.DataStruct;
import com.crest.divestory.WatchOp;
import com.syntak.library.UiOp;
import com.syntak.library.time.TimerResetable;
import java.util.UUID;

public class ActivityWatchDiveLogsDownload extends Activity {
   public static DataStruct.DiveLogs new_dive_logs = new DataStruct.DiveLogs();
   public static byte[] profile_data_buffer = null;
   private final int TRIAL_TIMES;
   Button action;
   private Activity activity;
   ImageView cancel;
   private Context context;
   int data_read_address;
   int data_start_address;
   int dive_log_index = 1;
   ActivityWatchDiveLogsDownload.DOWNLOAD_STAGE download_stage;
   ActivityWatchDiveLogsDownload.DOWNLOAD_STATE download_state;
   Handler handler = null;
   boolean isActivityPaused;
   boolean isReceiverStopped;
   int length_to_read = 0;
   int local_log_index = 0;
   int number_of_logs_to_download = 0;
   int number_of_logs_to_read = 0;
   int number_of_logs_to_scan = 0;
   TextView percent;
   int profile_data_length = 0;
   int profile_data_read_length;
   ProgressBar progressBar;
   ActivityWatchDiveLogsDownload.ReceiverResponse receiverResponse;
   ActivityWatchDiveLogsDownload.ReceiverTimeout receiverTimeout;
   TextView status_1;
   TextView status_3;
   LinearLayout status_progress;
   TimerResetable timerResetable;
   int total_profile_data_length = 0;
   int total_profile_data_read_length;
   private int trials;
   private WatchOp.WATCH_REPLY watch_reply;

   public ActivityWatchDiveLogsDownload() {
      this.download_stage = ActivityWatchDiveLogsDownload.DOWNLOAD_STAGE.ENABLE_NOTIFICATION;
      this.download_state = ActivityWatchDiveLogsDownload.DOWNLOAD_STATE.NO_NEW_LOGS;
      this.TRIAL_TIMES = 3;
      this.trials = 0;
      this.isActivityPaused = false;
      this.timerResetable = null;
      this.isReceiverStopped = false;
      this.receiverTimeout = null;
      this.receiverResponse = null;
   }

   // $FF: synthetic method
   static int access$508(ActivityWatchDiveLogsDownload var0) {
      int var1 = var0.trials++;
      return var1;
   }

   private void check_logs_to_scan() {
      int var1 = WatchOp.new_last_dive_log_index;
      this.number_of_logs_to_read = var1;
      if (var1 > 0) {
         TextView var2 = this.status_1;
         StringBuilder var3 = new StringBuilder();
         var3.append(this.getString(2131689680));
         var3.append(this.number_of_logs_to_read);
         var2.setText(var3.toString());
         this.status_1.setText("");
         this.action.setText(2131689792);
         this.download_state = ActivityWatchDiveLogsDownload.DOWNLOAD_STATE.NEW_LOG_AVAILABLE;
      } else {
         this.status_1.setText(2131689744);
         this.action.setText(2131689571);
         this.download_state = ActivityWatchDiveLogsDownload.DOWNLOAD_STATE.NO_NEW_LOGS;
         this.cancel.setVisibility(8);
      }

   }

   private void handle_divelog(DataStruct.DiveLog var1) {
      AppBase.dbOp.insertDiveLog(var1);
      WatchOp.dive_logs.addDiveLog(var1);
      if (var1.start_time > WatchOp.watch_connected.start_time_for_last_downloaded_log) {
         WatchOp.watch_connected.start_time_for_last_downloaded_log = var1.start_time;
         WatchOp.watch_connected.end_time_for_last_downloaded_log = var1.start_time + var1.duration * 1000L;
         AppBase.dbOp.updateMyWatchTimeForLastDownloadedLog(WatchOp.watch_connected.serial_number, var1.start_time, var1.duration * 1000L);
         AppBase.dbOp.updateMyWatchLastDiveLogIndex(WatchOp.watch_connected.serial_number, (long)WatchOp.new_last_dive_log_index);
      }

   }

   private void handle_log_profile_data(DataStruct.DiveLog var1) {
      DataStruct.DiveProfileData var2 = new DataStruct.DiveProfileData(var1, profile_data_buffer, WatchOp.watch_connected.serial_number);
      AppBase.dbOp.insertDiveProfileData(var2);
   }

   private void init_UI() {
      this.setContentView(2131427361);
      this.context = this;
      this.activity = this;
      this.cancel = (ImageView)this.findViewById(2131230899);
      this.status_1 = (TextView)this.findViewById(2131231329);
      TextView var1 = (TextView)this.findViewById(2131231331);
      this.status_3 = var1;
      var1.setVisibility(8);
      this.action = (Button)this.findViewById(2131230777);
      this.status_progress = (LinearLayout)this.findViewById(2131231333);
      this.percent = (TextView)this.findViewById(2131231216);
      this.progressBar = (ProgressBar)this.findViewById(2131231227);
      this.status_progress.setVisibility(8);
      boolean var2;
      if (WatchOp.watch_connected.firmware_version.compareTo("C01-4C") <= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      WatchOp.force_dive_profile_data_type_ceiling = var2;
      this.check_logs_to_scan();
      this.init_timer();
   }

   private void init_resume() {
      this.isActivityPaused = false;
      if (this.handler == null) {
         this.start_handler();
      }

      this.start_receivers();
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

   private void readDiveLog(byte var1, int var2) {
      this.readDiveLog(WatchOp.watch_connected.mac_address, var1, var2);
   }

   private void readDiveLog(String var1, byte var2, int var3) {
      WatchOp.readDiveLog(var1, var2, var3);
      this.timerResetable.resetTimer(10000).start();
   }

   private void start_handler() {
      this.handler = new Handler() {
         public void handleMessage(Message var1) {
            ActivityWatchDiveLogsDownload.this.download_stage = ActivityWatchDiveLogsDownload.DOWNLOAD_STAGE.values()[var1.what];
            StringBuilder var2;
            int var3;
            TextView var4;
            ActivityWatchDiveLogsDownload var5;
            DataStruct.DiveLog var6;
            switch(null.$SwitchMap$com$crest$divestory$ui$watches$ActivityWatchDiveLogsDownload$DOWNLOAD_STAGE[ActivityWatchDiveLogsDownload.this.download_stage.ordinal()]) {
            case 1:
               WatchOp.bleOp.enable_Notification(WatchOp.watch_connected.mac_address, WatchOp.UUID_SERVICE, WatchOp.UUID_CHARACTERISTIC_SETTING);
               ActivityWatchDiveLogsDownload.this.handler.postDelayed(new Runnable() {
                  public void run() {
                     ActivityWatchDiveLogsDownload.this.download_stage = ActivityWatchDiveLogsDownload.DOWNLOAD_STAGE.SET_MTU;
                     ActivityWatchDiveLogsDownload.this.handler.obtainMessage(ActivityWatchDiveLogsDownload.this.download_stage.ordinal()).sendToTarget();
                  }
               }, 500L);
               break;
            case 2:
               WatchOp.set_MTU(WatchOp.watch_connected.mac_address, 248);
               ActivityWatchDiveLogsDownload.this.handler.postDelayed(new Runnable() {
                  public void run() {
                     ActivityWatchDiveLogsDownload.this.download_state = ActivityWatchDiveLogsDownload.DOWNLOAD_STATE.DOWNLOADING;
                     ActivityWatchDiveLogsDownload.this.download_stage = ActivityWatchDiveLogsDownload.DOWNLOAD_STAGE.READ_DIVE_LOG_FULL_HEADER;
                     ActivityWatchDiveLogsDownload.this.handler.obtainMessage(ActivityWatchDiveLogsDownload.this.download_stage.ordinal()).sendToTarget();
                  }
               }, 500L);
               break;
            case 3:
               ActivityWatchDiveLogsDownload.this.handler.postDelayed(new Runnable() {
                  public void run() {
                     ActivityWatchDiveLogsDownload.this.readDiveLog((byte)2, ActivityWatchDiveLogsDownload.this.dive_log_index);
                  }
               }, 200L);
               break;
            case 4:
               if (WatchOp.dive_logs == null) {
                  WatchOp.dive_logs = new DataStruct.DiveLogs();
               }

               var6 = new DataStruct.DiveLog(WatchOp.watch_connected.serial_number, ActivityWatchDiveLogsDownload.this.watch_reply.data);
               if (var6.start_time > WatchOp.watch_connected.start_time_for_last_downloaded_log) {
                  if (WatchOp.isOldFirmware(var6.firmware_version)) {
                     var6.profile_data_length = 0L;
                  }

                  if (ActivityWatchDiveLogsDownload.new_dive_logs == null) {
                     ActivityWatchDiveLogsDownload.new_dive_logs = new DataStruct.DiveLogs();
                  }

                  ActivityWatchDiveLogsDownload.new_dive_logs.addDiveLog(var6);
                  ActivityWatchDiveLogsDownload var7 = ActivityWatchDiveLogsDownload.this;
                  var7.total_profile_data_length = (int)((long)var7.total_profile_data_length + var6.profile_data_length);
                  var5 = ActivityWatchDiveLogsDownload.this;
                  ++var5.number_of_logs_to_download;
               }

               if (ActivityWatchDiveLogsDownload.this.dive_log_index < ActivityWatchDiveLogsDownload.this.number_of_logs_to_read) {
                  ActivityWatchDiveLogsDownload.this.download_stage = ActivityWatchDiveLogsDownload.DOWNLOAD_STAGE.READ_DIVE_LOG_FULL_HEADER;
                  var5 = ActivityWatchDiveLogsDownload.this;
                  ++var5.dive_log_index;
               } else {
                  ActivityWatchDiveLogsDownload.this.local_log_index = 0;
                  if (ActivityWatchDiveLogsDownload.this.number_of_logs_to_download > 0) {
                     ActivityWatchDiveLogsDownload.this.download_stage = ActivityWatchDiveLogsDownload.DOWNLOAD_STAGE.PREPARE_READ_DIVE_PROFILE_DATA_BLOCK;
                     ActivityWatchDiveLogsDownload.this.status_1.setText(2131689605);
                     ActivityWatchDiveLogsDownload.this.action.setText(2131689537);
                  } else {
                     ActivityWatchDiveLogsDownload.this.download_stage = ActivityWatchDiveLogsDownload.DOWNLOAD_STAGE.COMPLETED;
                  }
               }

               ActivityWatchDiveLogsDownload.this.handler.obtainMessage(ActivityWatchDiveLogsDownload.this.download_stage.ordinal()).sendToTarget();
               var3 = ActivityWatchDiveLogsDownload.this.dive_log_index * 100 / ActivityWatchDiveLogsDownload.this.number_of_logs_to_read;
               ActivityWatchDiveLogsDownload.this.progressBar.setProgress(var3);
               var4 = ActivityWatchDiveLogsDownload.this.percent;
               var2 = new StringBuilder();
               var2.append(var3);
               var2.append("%");
               var4.setText(var2.toString());
               break;
            case 5:
               UiOp.toast_message(ActivityWatchDiveLogsDownload.this.context, 2131689856);
               ActivityWatchDiveLogsDownload.this.exit_activity();
               break;
            case 6:
               var6 = (DataStruct.DiveLog)ActivityWatchDiveLogsDownload.new_dive_logs.list.get(ActivityWatchDiveLogsDownload.this.local_log_index);
               ActivityWatchDiveLogsDownload.this.dive_log_index = (int)var6.dive_log_index;
               ActivityWatchDiveLogsDownload.this.profile_data_length = (int)var6.profile_data_length;
               if (ActivityWatchDiveLogsDownload.this.profile_data_length > 0) {
                  var5 = ActivityWatchDiveLogsDownload.this;
                  var5.length_to_read = var5.profile_data_length;
                  ActivityWatchDiveLogsDownload.this.profile_data_read_length = 0;
                  ActivityWatchDiveLogsDownload.this.data_read_address = 0;
                  ActivityWatchDiveLogsDownload.profile_data_buffer = new byte[ActivityWatchDiveLogsDownload.this.profile_data_length];
                  ActivityWatchDiveLogsDownload.this.handler.obtainMessage(ActivityWatchDiveLogsDownload.DOWNLOAD_STAGE.READ_DIVE_PROFILE_DATA_BLOCK.ordinal()).sendToTarget();
               } else {
                  ActivityWatchDiveLogsDownload.this.handle_divelog(var6);
                  var5 = ActivityWatchDiveLogsDownload.this;
                  ++var5.local_log_index;
                  if (ActivityWatchDiveLogsDownload.this.local_log_index < ActivityWatchDiveLogsDownload.this.number_of_logs_to_download) {
                     ActivityWatchDiveLogsDownload.this.handler.obtainMessage(ActivityWatchDiveLogsDownload.DOWNLOAD_STAGE.PREPARE_READ_DIVE_PROFILE_DATA_BLOCK.ordinal()).sendToTarget();
                  } else {
                     ActivityWatchDiveLogsDownload.this.handler.obtainMessage(ActivityWatchDiveLogsDownload.DOWNLOAD_STAGE.COMPLETED.ordinal()).sendToTarget();
                  }
               }
               break;
            case 7:
               ActivityWatchDiveLogsDownload.this.handler.postDelayed(new Runnable() {
                  public void run() {
                     WatchOp.readDiveProfileData(WatchOp.watch_connected.mac_address, (byte)3, ActivityWatchDiveLogsDownload.this.dive_log_index, (long)ActivityWatchDiveLogsDownload.this.data_read_address);
                  }
               }, 200L);
               break;
            case 8:
               var3 = Math.min(ActivityWatchDiveLogsDownload.this.length_to_read, ActivityWatchDiveLogsDownload.this.watch_reply.data_length);
               System.arraycopy(ActivityWatchDiveLogsDownload.this.watch_reply.data, 0, ActivityWatchDiveLogsDownload.profile_data_buffer, ActivityWatchDiveLogsDownload.this.profile_data_read_length, var3);
               var5 = ActivityWatchDiveLogsDownload.this;
               var5.profile_data_read_length += var3;
               var5 = ActivityWatchDiveLogsDownload.this;
               var5.data_read_address += var3;
               var5 = ActivityWatchDiveLogsDownload.this;
               var5.length_to_read -= var3;
               if (ActivityWatchDiveLogsDownload.this.length_to_read <= 0) {
                  var6 = (DataStruct.DiveLog)ActivityWatchDiveLogsDownload.new_dive_logs.list.get(ActivityWatchDiveLogsDownload.this.local_log_index);
                  ActivityWatchDiveLogsDownload.this.handle_log_profile_data(var6);
                  ActivityWatchDiveLogsDownload.this.handle_divelog(var6);
                  var5 = ActivityWatchDiveLogsDownload.this;
                  ++var5.local_log_index;
                  if (ActivityWatchDiveLogsDownload.this.local_log_index < ActivityWatchDiveLogsDownload.this.number_of_logs_to_download) {
                     ActivityWatchDiveLogsDownload.this.handler.obtainMessage(ActivityWatchDiveLogsDownload.DOWNLOAD_STAGE.PREPARE_READ_DIVE_PROFILE_DATA_BLOCK.ordinal()).sendToTarget();
                  } else {
                     ActivityWatchDiveLogsDownload.this.handler.obtainMessage(ActivityWatchDiveLogsDownload.DOWNLOAD_STAGE.COMPLETED.ordinal()).sendToTarget();
                  }
               } else {
                  ActivityWatchDiveLogsDownload.this.handler.obtainMessage(ActivityWatchDiveLogsDownload.DOWNLOAD_STAGE.READ_DIVE_PROFILE_DATA_BLOCK.ordinal()).sendToTarget();
               }

               var5 = ActivityWatchDiveLogsDownload.this;
               var5.total_profile_data_read_length += var3;
               var3 = ActivityWatchDiveLogsDownload.this.total_profile_data_read_length * 100 / ActivityWatchDiveLogsDownload.this.total_profile_data_length;
               ActivityWatchDiveLogsDownload.this.progressBar.setProgress(var3);
               var4 = ActivityWatchDiveLogsDownload.this.percent;
               var2 = new StringBuilder();
               var2.append(var3);
               var2.append("%");
               var4.setText(var2.toString());
            case 9:
            default:
               break;
            case 10:
               ActivityWatchDiveLogsDownload.this.download_state = ActivityWatchDiveLogsDownload.DOWNLOAD_STATE.DOWNLOAD_DONE;
               ActivityWatchDiveLogsDownload.this.progressBar.setProgress(100);
               var4 = ActivityWatchDiveLogsDownload.this.percent;
               var2 = new StringBuilder();
               var2.append(100);
               var2.append("%");
               var4.setText(var2.toString());
               ActivityWatchDiveLogsDownload.this.status_3.setVisibility(0);
               var4 = ActivityWatchDiveLogsDownload.this.status_3;
               var2 = new StringBuilder();
               var2.append(ActivityWatchDiveLogsDownload.this.getString(2131689600));
               var2.append(" ");
               var2.append(ActivityWatchDiveLogsDownload.this.number_of_logs_to_download);
               var4.setText(var2.toString());
               ActivityWatchDiveLogsDownload.this.action.setText(2131689603);
               ActivityWatchDiveLogsDownload.this.status_progress.setVisibility(8);
               if (ActivityWatchDiveLogsDownload.this.number_of_logs_to_download > 0) {
                  ActivityWatchDiveLogsDownload.this.status_1.setText(2131689606);
               } else {
                  ActivityWatchDiveLogsDownload.this.status_1.setText(2131689791);
               }

               ActivityWatchDiveLogsDownload.this.cancel.setVisibility(8);
               WatchOp.filterDiveLog.clear();
               WatchOp.dive_logs_list = WatchOp.dive_logs.list;
               if (ActivityWatchDiveLogsDownload.this.number_of_logs_to_download > 0) {
                  WatchOp.is_new_divelogs_downloaded = true;
                  AppBase.notifyChangeDiveLogList();
                  AppBase.pagerAdapter.notifyDataSetChanged();
               }
               break;
            case 11:
               UiOp.toast_message(ActivityWatchDiveLogsDownload.this.context, 2131689779);
               ActivityWatchDiveLogsDownload.this.exit_activity();
            }

         }
      };
   }

   private void start_receivers() {
      if (this.receiverTimeout == null) {
         ActivityWatchDiveLogsDownload.ReceiverTimeout var1 = new ActivityWatchDiveLogsDownload.ReceiverTimeout();
         this.receiverTimeout = var1;
         this.activity.registerReceiver(var1, new IntentFilter("ACTION_TIMEOUT"));
      }

      if (this.receiverResponse == null) {
         ActivityWatchDiveLogsDownload.ReceiverResponse var2 = new ActivityWatchDiveLogsDownload.ReceiverResponse();
         this.receiverResponse = var2;
         this.activity.registerReceiver(var2, new IntentFilter("ACTION_DATA_AVAILABLE"));
      }

   }

   private void start_scan() {
      this.download_stage = ActivityWatchDiveLogsDownload.DOWNLOAD_STAGE.ENABLE_NOTIFICATION;
      this.download_state = ActivityWatchDiveLogsDownload.DOWNLOAD_STATE.DOWNLOADING;
      this.status_progress.setVisibility(0);
      this.status_1.setText(2131689790);
      this.action.setText(2131689538);
   }

   private void stop_receivers() {
      if (!this.isReceiverStopped) {
         this.isReceiverStopped = true;
         ActivityWatchDiveLogsDownload.ReceiverTimeout var1 = this.receiverTimeout;
         if (var1 != null) {
            this.activity.unregisterReceiver(var1);
         }

         this.receiverTimeout = null;
         ActivityWatchDiveLogsDownload.ReceiverResponse var2 = this.receiverResponse;
         if (var2 != null) {
            this.activity.unregisterReceiver(var2);
         }

         this.receiverResponse = null;
      }
   }

   public void click_action(View var1) {
      int var2 = null.$SwitchMap$com$crest$divestory$ui$watches$ActivityWatchDiveLogsDownload$DOWNLOAD_STATE[this.download_state.ordinal()];
      if (var2 != 1) {
         if (var2 != 2) {
            if (var2 != 3 && var2 != 4) {
               return;
            }
         } else {
            this.exit_activity();
         }

         this.exit_activity();
      } else {
         this.handler.obtainMessage(ActivityWatchDiveLogsDownload.DOWNLOAD_STAGE.ENABLE_NOTIFICATION.ordinal()).sendToTarget();
         this.status_progress.setVisibility(0);
         this.status_1.setText(2131689790);
         this.action.setText(2131689538);
      }

   }

   public void click_cancel(View var1) {
      this.exit_activity();
   }

   public void exit_activity() {
      new_dive_logs = null;
      this.isActivityPaused = true;
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
      this.stop_receivers();
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
   }

   protected void onResume() {
      super.onResume();
      this.init_resume();
   }

   private static enum DOWNLOAD_STAGE {
      COMPLETED,
      ENABLE_NOTIFICATION,
      PREPARE_READ_DIVE_PROFILE_DATA_BLOCK,
      READ_DIVE_LOG_COMPACT_HEADER,
      READ_DIVE_LOG_COMPACT_HEADER_FAIL,
      READ_DIVE_LOG_COMPACT_HEADER_SUCESS,
      READ_DIVE_LOG_FULL_HEADER,
      READ_DIVE_LOG_FULL_HEADER_FAIL,
      READ_DIVE_LOG_FULL_HEADER_SUCESS,
      READ_DIVE_PROFILE_DATA_BLOCK,
      READ_DIVE_PROFILE_DATA_BLOCK_FAIL,
      READ_DIVE_PROFILE_DATA_BLOCK_SUCESS,
      RESPONSE_TIMEOUT,
      SET_MTU;

      static {
         ActivityWatchDiveLogsDownload.DOWNLOAD_STAGE var0 = new ActivityWatchDiveLogsDownload.DOWNLOAD_STAGE("RESPONSE_TIMEOUT", 13);
         RESPONSE_TIMEOUT = var0;
      }
   }

   private static enum DOWNLOAD_STATE {
      DOWNLOADING,
      DOWNLOAD_DONE,
      NEW_LOG_AVAILABLE,
      NO_NEW_LOGS;

      static {
         ActivityWatchDiveLogsDownload.DOWNLOAD_STATE var0 = new ActivityWatchDiveLogsDownload.DOWNLOAD_STATE("DOWNLOAD_DONE", 3);
         DOWNLOAD_DONE = var0;
      }
   }

   public class ReceiverResponse extends BroadcastReceiver {
      public void onReceive(Context var1, Intent var2) {
         String var3 = var2.getStringExtra("address");
         BluetoothGattCharacteristic var6 = WatchOp.bleOp.get_Characteristic_received(var3);
         ActivityWatchDiveLogsDownload.this.timerResetable.pause();
         if (!ActivityWatchDiveLogsDownload.this.isActivityPaused) {
            UUID var4 = var6.getUuid();
            byte[] var7 = var6.getValue();
            int var5 = null.$SwitchMap$com$crest$divestory$ui$watches$ActivityWatchDiveLogsDownload$DOWNLOAD_STAGE[ActivityWatchDiveLogsDownload.this.download_stage.ordinal()];
            if (var5 != 3) {
               if (var5 == 7) {
                  ActivityWatchDiveLogsDownload.this.watch_reply = WatchOp.handle_received_charteristics(var3, var6);
                  WatchOp.ackDiveLog(var3, (byte)3, ActivityWatchDiveLogsDownload.this.watch_reply.data_length, ActivityWatchDiveLogsDownload.this.watch_reply.data);
                  ActivityWatchDiveLogsDownload.this.handler.obtainMessage(ActivityWatchDiveLogsDownload.DOWNLOAD_STAGE.READ_DIVE_PROFILE_DATA_BLOCK_SUCESS.ordinal()).sendToTarget();
               }
            } else if (var4.equals(WatchOp.UUID_CHARACTERISTIC_SETTING) && var7.length > 4) {
               ActivityWatchDiveLogsDownload.this.trials = 0;
               ActivityWatchDiveLogsDownload.this.watch_reply = WatchOp.handle_received_charteristics(var3, var6);
               ActivityWatchDiveLogsDownload.this.handler.obtainMessage(ActivityWatchDiveLogsDownload.DOWNLOAD_STAGE.READ_DIVE_LOG_FULL_HEADER_SUCESS.ordinal()).sendToTarget();
            } else if (ActivityWatchDiveLogsDownload.this.trials < 3) {
               ActivityWatchDiveLogsDownload.access$508(ActivityWatchDiveLogsDownload.this);
               ActivityWatchDiveLogsDownload.this.handler.obtainMessage(ActivityWatchDiveLogsDownload.DOWNLOAD_STAGE.READ_DIVE_LOG_FULL_HEADER.ordinal()).sendToTarget();
            } else {
               ActivityWatchDiveLogsDownload.this.handler.obtainMessage(ActivityWatchDiveLogsDownload.DOWNLOAD_STAGE.RESPONSE_TIMEOUT.ordinal()).sendToTarget();
            }

         }
      }
   }

   public class ReceiverTimeout extends BroadcastReceiver {
      public void onReceive(Context var1, Intent var2) {
         if (!ActivityWatchDiveLogsDownload.this.isActivityPaused) {
            WatchOp.isTimeout = true;
            UiOp.toast_message(var1, 2131689779);
            ActivityWatchDiveLogsDownload.this.exit_activity();
         }
      }
   }
}
