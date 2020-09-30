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
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.crest.divestory.AppBase;
import com.crest.divestory.DataStruct;
import com.crest.divestory.WatchOp;
import com.syntak.library.ByteOp;
import com.syntak.library.FileOp;
import com.syntak.library.UiOp;
import com.syntak.library.time.TimerResetable;
import com.syntak.library.ui.EditorAccountPassword;
import java.nio.ByteOrder;
import java.util.UUID;

public class ActivityWatchUpgrade extends Activity {
   private final int TRIAL_TIMES;
   Button action;
   private Activity activity;
   ImageView cancel_upgrade;
   private Context context;
   TextView current_version;
   EditorAccountPassword eap;
   FrameLayout engineer_pad;
   DataStruct.FirmwareFileHeader ffh = null;
   DataStruct.FirmwareHeader fh = null;
   int file_index = 0;
   byte[] firmware_buffer = null;
   int firmware_file_length = 0;
   String firmware_filename;
   String firmware_filepath;
   Handler handler = null;
   ImageView icon_upgrade;
   boolean isActivityPaused;
   boolean isReceiverStopped;
   TextView latest_version;
   int length_received;
   int length_to_send;
   TextView percent;
   ProgressBar progressBar;
   ActivityWatchUpgrade.ReceiverResponse receiverResponse;
   ActivityWatchUpgrade.ReceiverTimeout receiverTimeout;
   TextView status_1;
   LinearLayout status_2;
   TextView status_3;
   LinearLayout status_progress;
   TimerResetable timerResetable;
   private int trials;
   ActivityWatchUpgrade.UPGRADE_STAGE upgrade_stage;
   ActivityWatchUpgrade.UPGRADE_STATE upgrade_state;

   public ActivityWatchUpgrade() {
      this.upgrade_stage = ActivityWatchUpgrade.UPGRADE_STAGE.READ_FIRMWARE;
      this.upgrade_state = ActivityWatchUpgrade.UPGRADE_STATE.ALREADY_LATEST;
      this.TRIAL_TIMES = 3;
      this.trials = 0;
      this.isActivityPaused = false;
      this.timerResetable = null;
      this.isReceiverStopped = false;
      this.firmware_filepath = null;
      this.receiverTimeout = null;
      this.receiverResponse = null;
   }

   // $FF: synthetic method
   static int access$208(ActivityWatchUpgrade var0) {
      int var1 = var0.trials++;
      return var1;
   }

   private void check_firmware_version() {
      LinearLayout var1 = this.status_2;
      boolean var2 = false;
      var1.setVisibility(0);
      this.status_progress.setVisibility(8);
      String var3 = WatchOp.firmwares.get_version(WatchOp.watch_connected.model_name);
      String var4 = WatchOp.watch_connected.firmware_version;
      if (var3.compareTo(var4) > 0) {
         var2 = true;
      }

      if (var2) {
         this.status_1.setText(2131689736);
         this.current_version.setText(var4);
         this.latest_version.setText(var3);
         this.action.setText(2131689850);
         this.upgrade_state = ActivityWatchUpgrade.UPGRADE_STATE.NEWER_AVAILABLE;
      } else {
         this.status_1.setText(2131689626);
         this.current_version.setText(var4);
         this.latest_version.setText(var3);
         this.action.setText(2131689846);
         this.upgrade_state = ActivityWatchUpgrade.UPGRADE_STATE.ALREADY_LATEST;
      }

   }

   private void init_UI() {
      this.setContentView(2131427364);
      this.context = this;
      this.activity = this;
      this.engineer_pad = (FrameLayout)this.findViewById(2131231007);
      this.icon_upgrade = (ImageView)this.findViewById(2131231065);
      this.engineer_pad.setVisibility(8);
      this.icon_upgrade.setOnLongClickListener(new OnLongClickListener() {
         public boolean onLongClick(View var1) {
            ActivityWatchUpgrade.this.verify_password();
            return false;
         }
      });
      this.cancel_upgrade = (ImageView)this.findViewById(2131230903);
      this.status_1 = (TextView)this.findViewById(2131231329);
      this.status_2 = (LinearLayout)this.findViewById(2131231330);
      TextView var1 = (TextView)this.findViewById(2131231331);
      this.status_3 = var1;
      var1.setVisibility(8);
      this.current_version = (TextView)this.findViewById(2131230938);
      this.latest_version = (TextView)this.findViewById(2131231120);
      this.action = (Button)this.findViewById(2131230777);
      this.status_progress = (LinearLayout)this.findViewById(2131231333);
      this.percent = (TextView)this.findViewById(2131231216);
      this.progressBar = (ProgressBar)this.findViewById(2131231227);
      this.status_progress.setVisibility(8);
      this.check_firmware_version();
      this.init_timer();
      WatchOp.isFirmwareUpgraded = false;
      this.firmware_filename = WatchOp.firmwares.get_filename(WatchOp.watch_connected.model_name);
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
         TimerResetable var1 = new TimerResetable(this, 15000);
         this.timerResetable = var1;
         AppBase.timerResetable = var1;
      } else {
         this.timerResetable = AppBase.timerResetable;
      }

   }

   private int read_firmware() {
      // $FF: Couldn't be decompiled
   }

   private void start_handler() {
      this.handler = new Handler() {
         public void handleMessage(Message var1) {
            ActivityWatchUpgrade.UPGRADE_STAGE var5 = ActivityWatchUpgrade.UPGRADE_STAGE.values()[var1.what];
            ActivityWatchUpgrade var7;
            switch(null.$SwitchMap$com$crest$divestory$ui$watches$ActivityWatchUpgrade$UPGRADE_STAGE[var5.ordinal()]) {
            case 1:
               WatchOp.bleOp.enable_Notification(WatchOp.watch_connected.mac_address, WatchOp.UUID_SERVICE, WatchOp.UUID_CHARACTERISTIC_UPGRADE);
               ActivityWatchUpgrade.this.upgrade_stage = ActivityWatchUpgrade.UPGRADE_STAGE.READ_FIRMWARE;
               ActivityWatchUpgrade.this.handler.obtainMessage(ActivityWatchUpgrade.this.upgrade_stage.ordinal()).sendToTarget();
               break;
            case 2:
               var7 = ActivityWatchUpgrade.this;
               var7.firmware_file_length = var7.read_firmware();
               if (ActivityWatchUpgrade.this.firmware_file_length > 0) {
                  ActivityWatchUpgrade.this.ffh = new DataStruct.FirmwareFileHeader(ActivityWatchUpgrade.this.firmware_buffer);
                  ActivityWatchUpgrade.this.fh = new DataStruct.FirmwareHeader(ActivityWatchUpgrade.this.ffh);
                  ActivityWatchUpgrade.this.trials = 0;
                  ActivityWatchUpgrade.this.handler.postDelayed(new Runnable() {
                     public void run() {
                        ActivityWatchUpgrade.this.upgrade_stage = ActivityWatchUpgrade.UPGRADE_STAGE.SET_MTU;
                        ActivityWatchUpgrade.this.handler.obtainMessage(ActivityWatchUpgrade.this.upgrade_stage.ordinal()).sendToTarget();
                     }
                  }, 500L);
               } else {
                  UiOp.toast_message(ActivityWatchUpgrade.this.context, 2131689627);
                  ActivityWatchUpgrade.this.exit_activity();
               }
               break;
            case 3:
               WatchOp.set_MTU(WatchOp.watch_connected.mac_address, 248);
               ActivityWatchUpgrade.this.handler.postDelayed(new Runnable() {
                  public void run() {
                     ActivityWatchUpgrade.this.upgrade_stage = ActivityWatchUpgrade.UPGRADE_STAGE.SEND_COMMAND;
                     ActivityWatchUpgrade.this.handler.obtainMessage(ActivityWatchUpgrade.this.upgrade_stage.ordinal()).sendToTarget();
                  }
               }, 500L);
               break;
            case 4:
               WatchOp.firmware_upgrade_send_command(WatchOp.watch_connected.mac_address);
               ActivityWatchUpgrade.this.timerResetable.resetTimer(15000).start();
               break;
            case 5:
               UiOp.toast_message(ActivityWatchUpgrade.this.context, 2131689856);
               ActivityWatchUpgrade.this.exit_activity();
               break;
            case 6:
               WatchOp.firmware_upgrade_send_header(WatchOp.watch_connected.mac_address, ActivityWatchUpgrade.this.fh.toByteArray());
               ActivityWatchUpgrade.this.timerResetable.resetTimer(15000).start();
               break;
            case 7:
               int var3 = ActivityWatchUpgrade.this.length_to_send;
               int var4 = 244;
               if (var3 <= 244) {
                  var4 = ActivityWatchUpgrade.this.length_to_send;
               }

               WatchOp.firmware_upgrade_send_data_block(WatchOp.watch_connected.mac_address, ActivityWatchUpgrade.this.firmware_buffer, ActivityWatchUpgrade.this.file_index, var4);
               ActivityWatchUpgrade.this.timerResetable.resetTimer(15000).start();
               var7 = ActivityWatchUpgrade.this;
               var7.length_to_send -= var4;
               var7 = ActivityWatchUpgrade.this;
               var7.file_index += var4;
               var4 = ActivityWatchUpgrade.this.file_index * 100 / ActivityWatchUpgrade.this.firmware_file_length;
               ActivityWatchUpgrade.this.progressBar.setProgress(var4);
               TextView var8 = ActivityWatchUpgrade.this.percent;
               StringBuilder var9 = new StringBuilder();
               var9.append(var4);
               var9.append("%");
               var8.setText(var9.toString());
               break;
            case 8:
               ActivityWatchUpgrade.this.progressBar.setProgress(100);
               TextView var6 = ActivityWatchUpgrade.this.percent;
               StringBuilder var2 = new StringBuilder();
               var2.append(100);
               var2.append("%");
               var6.setText(var2.toString());
               ActivityWatchUpgrade.this.status_progress.setVisibility(8);
               ActivityWatchUpgrade.this.status_3.setVisibility(0);
               ActivityWatchUpgrade.this.status_1.setText(2131689848);
               ActivityWatchUpgrade.this.action.setText(2131689571);
               AppBase.isExitSync = true;
               ActivityWatchUpgrade.this.cancel_upgrade.setVisibility(8);
               break;
            case 9:
               WatchOp.isTimeout = true;
               UiOp.toast_message(ActivityWatchUpgrade.this.context, 2131689779);
               ActivityWatchUpgrade.this.exit_activity();
            }

         }
      };
   }

   private void start_receivers() {
      if (this.receiverTimeout == null) {
         ActivityWatchUpgrade.ReceiverTimeout var1 = new ActivityWatchUpgrade.ReceiverTimeout();
         this.receiverTimeout = var1;
         this.activity.registerReceiver(var1, new IntentFilter("ACTION_TIMEOUT"));
      }

      if (this.receiverResponse == null) {
         ActivityWatchUpgrade.ReceiverResponse var2 = new ActivityWatchUpgrade.ReceiverResponse();
         this.receiverResponse = var2;
         this.activity.registerReceiver(var2, new IntentFilter("ACTION_DATA_AVAILABLE"));
      }

   }

   private void stop_receivers() {
      if (!this.isReceiverStopped) {
         this.isReceiverStopped = true;
         ActivityWatchUpgrade.ReceiverTimeout var1 = this.receiverTimeout;
         if (var1 != null) {
            this.activity.unregisterReceiver(var1);
         }

         this.receiverTimeout = null;
         ActivityWatchUpgrade.ReceiverResponse var2 = this.receiverResponse;
         if (var2 != null) {
            this.activity.unregisterReceiver(var2);
         }

         this.receiverResponse = null;
      }
   }

   private void verify_password() {
      EditorAccountPassword.Info var1 = new EditorAccountPassword.Info();
      var1.mode = 5;
      var1.context = this.context;
      var1.ancher = this.engineer_pad;
      var1.title = this.context.getString(2131689615);
      var1.text_hint_password = this.context.getString(2131689615);
      var1.warning_fields_empty = this.context.getString(2131689754);
      var1.warning_password_wrong = this.context.getString(2131689756);
      var1.text_confirm = this.context.getString(2131689571);
      var1.text_cancel = this.context.getString(2131689536);
      var1.password = "3674";
      EditorAccountPassword var2 = this.eap;
      if (var2 != null) {
         var2.stop();
         this.eap = null;
      }

      this.eap = new EditorAccountPassword(var1) {
         public void OnCancelled() {
            ActivityWatchUpgrade.this.eap.stop();
            ActivityWatchUpgrade.this.eap = null;
         }

         public void OnConfirmed(String var1) {
            ActivityWatchUpgrade.this.engineer_pad.setVisibility(0);
         }
      };
   }

   public void click_action(View var1) {
      int var2 = null.$SwitchMap$com$crest$divestory$ui$watches$ActivityWatchUpgrade$UPGRADE_STATE[this.upgrade_state.ordinal()];
      if (var2 != 1 && var2 != 2) {
         if (var2 != 3) {
            if (var2 == 4) {
               WatchOp.isFirmwareUpgraded = true;
               this.exit_activity();
            }
         } else {
            this.exit_activity();
         }
      } else {
         this.handler.obtainMessage(ActivityWatchUpgrade.UPGRADE_STAGE.ENABLE_NOTIFICATION.ordinal()).sendToTarget();
         this.status_2.setVisibility(8);
         this.status_progress.setVisibility(0);
         this.status_1.setText(2131689851);
         this.action.setText(2131689847);
         this.upgrade_state = ActivityWatchUpgrade.UPGRADE_STATE.UPGRADING;
      }

   }

   public void click_cancel(View var1) {
      this.exit_activity();
   }

   public void click_hidden_upgrade(View var1) {
      this.firmware_filepath = (String)FileOp.get_List_of_Filepath_with_Extension(AppBase.folder_download, "bin").get(0);
      this.handler.obtainMessage(ActivityWatchUpgrade.UPGRADE_STAGE.ENABLE_NOTIFICATION.ordinal()).sendToTarget();
      this.status_2.setVisibility(8);
      this.status_progress.setVisibility(0);
      this.status_1.setText(2131689851);
      this.action.setText(2131689847);
      this.upgrade_state = ActivityWatchUpgrade.UPGRADE_STATE.UPGRADING;
      var1.setEnabled(false);
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
      this.isActivityPaused = true;
   }

   protected void onResume() {
      super.onResume();
      this.init_resume();
   }

   public class ReceiverResponse extends BroadcastReceiver {
      public void onReceive(Context var1, Intent var2) {
         String var12 = var2.getStringExtra("address");
         BluetoothGattCharacteristic var13 = WatchOp.bleOp.get_Characteristic_received(var12);
         ActivityWatchUpgrade.this.timerResetable.pause();
         UUID var3 = var13.getUuid();
         byte[] var14 = var13.getValue();
         int var4 = null.$SwitchMap$com$crest$divestory$ui$watches$ActivityWatchUpgrade$UPGRADE_STAGE[ActivityWatchUpgrade.this.upgrade_stage.ordinal()];
         boolean var5 = true;
         if (var4 != 4) {
            if (var4 != 6) {
               if (var4 == 7) {
                  if (var14.length >= 5) {
                     byte var6 = var14[4];
                     byte var7 = var14[3];
                     byte var8 = var14[2];
                     byte var9 = var14[1];
                     ActivityWatchUpgrade var15 = ActivityWatchUpgrade.this;
                     ByteOrder var10 = ByteOrder.LITTLE_ENDIAN;
                     var15.length_received = (int)ByteOp.get_Long_from_4_bytes_unsigned(new byte[]{var6, var7, var8, var9}, var10);
                  }

                  if (ActivityWatchUpgrade.this.length_to_send > 0) {
                     if (var14[0] == 2) {
                        ActivityWatchUpgrade.this.upgrade_stage = ActivityWatchUpgrade.UPGRADE_STAGE.SEND_DATA_BLOCK;
                        ActivityWatchUpgrade.this.handler.obtainMessage(ActivityWatchUpgrade.this.upgrade_stage.ordinal()).sendToTarget();
                     } else {
                        UiOp.toast_message(var1, "Upgrading Fail!", false);
                        ActivityWatchUpgrade.this.exit_activity();
                     }
                  } else if (var14[0] == 3) {
                     ActivityWatchUpgrade.this.upgrade_stage = ActivityWatchUpgrade.UPGRADE_STAGE.COMPLETED;
                     ActivityWatchUpgrade.this.handler.obtainMessage(ActivityWatchUpgrade.this.upgrade_stage.ordinal()).sendToTarget();
                  } else {
                     UiOp.toast_message(var1, "Upgrade End Fail!", false);
                     ActivityWatchUpgrade.this.exit_activity();
                  }
               }
            } else {
               ActivityWatchUpgrade var11 = ActivityWatchUpgrade.this;
               var11.length_to_send = var11.firmware_file_length;
               ActivityWatchUpgrade.this.upgrade_stage = ActivityWatchUpgrade.UPGRADE_STAGE.SEND_DATA_BLOCK;
               ActivityWatchUpgrade.this.handler.obtainMessage(ActivityWatchUpgrade.this.upgrade_stage.ordinal()).sendToTarget();
            }
         } else {
            if (!var3.equals(WatchOp.UUID_CHARACTERISTIC_UPGRADE) || var14[0] == 0) {
               if (ActivityWatchUpgrade.this.trials < 3) {
                  ActivityWatchUpgrade.access$208(ActivityWatchUpgrade.this);
                  ActivityWatchUpgrade.this.handler.obtainMessage(ActivityWatchUpgrade.UPGRADE_STAGE.SEND_COMMAND.ordinal()).sendToTarget();
               } else {
                  ActivityWatchUpgrade.this.handler.obtainMessage(ActivityWatchUpgrade.UPGRADE_STAGE.SEND_COMMAND_FAIL.ordinal()).sendToTarget();
               }

               var5 = false;
            }

            if (var5) {
               ActivityWatchUpgrade.this.trials = 0;
               ActivityWatchUpgrade.this.upgrade_stage = ActivityWatchUpgrade.UPGRADE_STAGE.SEND_HEADER;
               ActivityWatchUpgrade.this.handler.obtainMessage(ActivityWatchUpgrade.this.upgrade_stage.ordinal()).sendToTarget();
            }
         }

      }
   }

   public class ReceiverTimeout extends BroadcastReceiver {
      public void onReceive(Context var1, Intent var2) {
         WatchOp.isTimeout = true;
         UiOp.toast_message(var1, 2131689779);
         ActivityWatchUpgrade.this.exit_activity();
      }
   }

   private static enum UPGRADE_STAGE {
      COMPLETED,
      ENABLE_NOTIFICATION,
      READ_FIRMWARE,
      RESPONSE_TIMEOUT,
      SEND_COMMAND,
      SEND_COMMAND_FAIL,
      SEND_DATA_BLOCK,
      SEND_DATA_BLOCK_FAIL,
      SEND_HEADER,
      SEND_HEADER_FAIL,
      SET_MTU;

      static {
         ActivityWatchUpgrade.UPGRADE_STAGE var0 = new ActivityWatchUpgrade.UPGRADE_STAGE("RESPONSE_TIMEOUT", 10);
         RESPONSE_TIMEOUT = var0;
      }
   }

   private static enum UPGRADE_STATE {
      ALREADY_LATEST,
      NEWER_AVAILABLE,
      UPGRADE_DONE,
      UPGRADING;

      static {
         ActivityWatchUpgrade.UPGRADE_STATE var0 = new ActivityWatchUpgrade.UPGRADE_STATE("UPGRADE_DONE", 3);
         UPGRADE_DONE = var0;
      }
   }
}
