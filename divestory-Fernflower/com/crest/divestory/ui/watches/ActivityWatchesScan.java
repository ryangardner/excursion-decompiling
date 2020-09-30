package com.crest.divestory.ui.watches;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.crest.divestory.AppBase;
import com.crest.divestory.DataStruct;
import com.crest.divestory.WatchOp;
import com.syntak.library.UiOp;
import com.syntak.library.time.TimerResetable;
import java.util.HashMap;

public class ActivityWatchesScan extends AppCompatActivity {
   private static boolean isEnablingTried;
   private Activity activity;
   private AdapterWatchesScannedList adapter = null;
   private boolean checkTimeout = false;
   private Context context;
   Handler handler = null;
   private boolean isActivityPaused = false;
   private boolean isBleEnabled = false;
   private ListView listView;
   ProgressBar progress_circle;
   ActivityWatchesScan.ReceiverEnable receiverEnable;
   ActivityWatchesScan.ReceiverScan receiverScan;
   ActivityWatchesScan.ReceiverScanDone receiverScanDone;
   ActivityWatchesScan.ReceiverSupport receiverSupport;
   ActivityWatchesScan.ReceiverTimeout receiverTimeout;
   Button rescan;
   ActivityWatchesScan.SCAN_STAGE scan_stage;
   RelativeLayout scanning;
   TextView status;
   private TextView status_list;
   TimerResetable timerResetable;

   public ActivityWatchesScan() {
      this.scan_stage = ActivityWatchesScan.SCAN_STAGE.CHECK_SUPPORT;
      this.timerResetable = null;
      this.receiverTimeout = null;
      this.receiverSupport = null;
      this.receiverEnable = null;
      this.receiverScan = null;
      this.receiverScanDone = null;
   }

   private void init_UI() {
      this.setContentView(2131427365);
      this.context = this;
      this.activity = this;
      this.init_action_bar();
      RelativeLayout var1 = (RelativeLayout)this.findViewById(2131231270);
      this.scanning = var1;
      var1.setVisibility(0);
      ProgressBar var2 = (ProgressBar)this.findViewById(2131231228);
      this.progress_circle = var2;
      var2.setVisibility(0);
      this.status = (TextView)this.findViewById(2131231328);
      Button var3 = (Button)this.findViewById(2131231243);
      this.rescan = var3;
      var3.setVisibility(8);
      this.status_list = (TextView)this.findViewById(2131231445);
      this.listView = (ListView)this.findViewById(2131231136);
      var3 = (Button)this.findViewById(2131231044);
      WatchOp.open_ble(this.activity);
      this.init_timer();
   }

   private void init_action_bar() {
      this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      this.getSupportActionBar().setHomeAsUpIndicator(2131165356);
      AppBase.setTitleAndColor(this, 2131689794, 2131034179);
   }

   private void init_resume() {
      this.isActivityPaused = false;
      this.start_receivers();
      if (this.handler == null) {
         this.start_handler();
         this.handler.postDelayed(new Runnable() {
            public void run() {
               if (ActivityWatchesScan.this.handler != null) {
                  ActivityWatchesScan.this.handler.obtainMessage(ActivityWatchesScan.SCAN_STAGE.CHECK_SUPPORT.ordinal()).sendToTarget();
               }

            }
         }, 1000L);
      }

      this.prepare_listview();
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

   private void prepare_listview() {
      if (WatchOp.devices_scanned == null || WatchOp.isRescanning) {
         WatchOp.devices_scanned = new DataStruct.BleDevices();
         WatchOp.devices_scanned_map = new HashMap();
         AdapterWatchesScannedList var1 = new AdapterWatchesScannedList(this, WatchOp.devices_scanned);
         this.adapter = var1;
         this.listView.setAdapter(var1);
         WatchOp.isRescanning = false;
      }

   }

   private void show_rescan() {
      this.status.setText(2131689855);
      this.rescan.setVisibility(0);
      this.progress_circle.setVisibility(8);
   }

   private void start_handler() {
      this.handler = new Handler() {
         public void handleMessage(Message var1) {
            ActivityWatchesScan.this.scan_stage = ActivityWatchesScan.SCAN_STAGE.values()[var1.what];
            int var2 = null.$SwitchMap$com$crest$divestory$ui$watches$ActivityWatchesScan$SCAN_STAGE[ActivityWatchesScan.this.scan_stage.ordinal()];
            if (var2 != 1) {
               if (var2 != 2) {
                  if (var2 != 3) {
                     if (var2 != 4) {
                        if (var2 != 5) {
                           if (var2 == 7) {
                              AppBase.setTitleAndColor(ActivityWatchesScan.this.activity, 2131689859, 2131034179);
                              if (WatchOp.devices_scanned != null && WatchOp.devices_scanned.length() > 0) {
                                 if (ActivityWatchesScan.this.adapter != null) {
                                    ActivityWatchesScan.this.adapter.notifyDataSetChanged();
                                 }

                                 ActivityWatchesScan.this.status_list.setText(2131689547);
                                 ActivityWatchesScan.this.status_list.setTextColor(ActivityWatchesScan.this.getResources().getColor(2131034179));
                              } else {
                                 ActivityWatchesScan.this.status_list.setText(2131689746);
                              }

                              ActivityWatchesScan.this.scanning.setVisibility(8);
                           }
                        } else {
                           ActivityWatchesScan.this.start_scanning((View)null);
                        }
                     } else {
                        ActivityWatchesScan.this.show_rescan();
                     }
                  } else {
                     WatchOp.bleOp.do_Task(30);
                     ActivityWatchesScan.this.timerResetable.resetTimer(30000).start();
                     ActivityWatchesScan.isEnablingTried = true;
                  }
               } else {
                  WatchOp.bleOp.do_Task(20);
                  ActivityWatchesScan.this.timerResetable.resetTimer(30000).start();
               }
            } else {
               WatchOp.bleOp.do_Task(10);
            }

         }
      };
   }

   private void start_receivers() {
      if (this.receiverTimeout == null) {
         ActivityWatchesScan.ReceiverTimeout var1 = new ActivityWatchesScan.ReceiverTimeout();
         this.receiverTimeout = var1;
         this.activity.registerReceiver(var1, new IntentFilter("ACTION_TIMEOUT"));
      }

      if (this.receiverSupport == null) {
         ActivityWatchesScan.ReceiverSupport var2 = new ActivityWatchesScan.ReceiverSupport();
         this.receiverSupport = var2;
         this.activity.registerReceiver(var2, new IntentFilter("ACTION_BLE_SUPPORT"));
      }

      if (this.receiverEnable == null) {
         ActivityWatchesScan.ReceiverEnable var3 = new ActivityWatchesScan.ReceiverEnable();
         this.receiverEnable = var3;
         this.activity.registerReceiver(var3, new IntentFilter("ACTION_BLE_ENABLE"));
      }

      if (this.receiverScan == null) {
         ActivityWatchesScan.ReceiverScan var4 = new ActivityWatchesScan.ReceiverScan();
         this.receiverScan = var4;
         this.activity.registerReceiver(var4, new IntentFilter("ACTION_BLE_SCAN"));
      }

      if (this.receiverScanDone == null) {
         ActivityWatchesScan.ReceiverScanDone var5 = new ActivityWatchesScan.ReceiverScanDone();
         this.receiverScanDone = var5;
         this.activity.registerReceiver(var5, new IntentFilter("ACTION_BLE_SCAN_DONE"));
      }

   }

   private void stop_receivers() {
      ActivityWatchesScan.ReceiverTimeout var1 = this.receiverTimeout;
      if (var1 != null) {
         this.activity.unregisterReceiver(var1);
         this.receiverTimeout = null;
      }

      ActivityWatchesScan.ReceiverSupport var2 = this.receiverSupport;
      if (var2 != null) {
         this.activity.unregisterReceiver(var2);
         this.receiverSupport = null;
      }

      ActivityWatchesScan.ReceiverEnable var3 = this.receiverEnable;
      if (var3 != null) {
         this.activity.unregisterReceiver(var3);
         this.receiverEnable = null;
      }

      ActivityWatchesScan.ReceiverScan var4 = this.receiverScan;
      if (var4 != null) {
         this.activity.unregisterReceiver(var4);
         this.receiverScan = null;
      }

      ActivityWatchesScan.ReceiverScanDone var5 = this.receiverScanDone;
      if (var5 != null) {
         this.activity.unregisterReceiver(var5);
         this.receiverScanDone = null;
      }

   }

   public void cancel_scanning(View var1) {
      WatchOp.bleOp.stop_scanning();
      this.exit_activity();
   }

   public void exit_activity() {
      this.timerResetable.stop();
      this.timerResetable = null;
      AppBase.timerResetable = null;
      isEnablingTried = false;
      this.handler = null;
      this.checkTimeout = false;
      this.isBleEnabled = false;
      this.finish();
   }

   public void go_next(View var1) {
      Intent var2 = new Intent();
      var2.setClass(this.context, ActivityWatchSync.class);
      var2.putExtra("index", -1);
      this.context.startActivity(var2);
   }

   protected void onActivityResult(int var1, int var2, Intent var3) {
      super.onActivityResult(var1, var2, var3);
      if (var1 == 30) {
         this.timerResetable.pause();
         WatchOp.bleOp.do_Task(20);
      }

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
      WatchOp.devices_scanned_map = null;
      WatchOp.devices_scanned = null;
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
      this.isActivityPaused = false;
      if (!WatchOp.isTimeout && !WatchOp.isSyncDone && !WatchOp.isFirmwareUpgraded) {
         this.init_resume();
      } else {
         WatchOp.isTimeout = false;
         this.exit_activity();
      }

   }

   public void start_scanning(View var1) {
      WatchOp.bleOp.do_Task(40);
      this.timerResetable.resetTimer(10000).start();
      this.status.setText(2131689793);
      this.rescan.setVisibility(8);
      this.progress_circle.setVisibility(0);
   }

   public class ReceiverEnable extends BroadcastReceiver {
      public void onReceive(Context var1, Intent var2) {
         ActivityWatchesScan.this.timerResetable.pause();
         if (!ActivityWatchesScan.this.isActivityPaused) {
            ActivityWatchesScan.this.isBleEnabled = WatchOp.bleOp.is_Bluetooth_enabled();
            if (ActivityWatchesScan.this.isBleEnabled) {
               ActivityWatchesScan.this.timerResetable.pause();
               Log.d("ActivityWatchesScan", "BLE enabled");
               ActivityWatchesScan.this.handler.postDelayed(new Runnable() {
                  public void run() {
                     ActivityWatchesScan.this.handler.obtainMessage(ActivityWatchesScan.SCAN_STAGE.SCANNING.ordinal()).sendToTarget();
                  }
               }, 500L);
            } else if (!ActivityWatchesScan.isEnablingTried) {
               ActivityWatchesScan.this.handler.obtainMessage(ActivityWatchesScan.SCAN_STAGE.ENABLING.ordinal()).sendToTarget();
            } else {
               Log.d("ActivityWatchesScan", "BLE not enabled!");
               UiOp.toast_message(var1, "Bluetooth LE cannot be enabled in this device", false);
               ActivityWatchesScan.this.exit_activity();
            }

         }
      }
   }

   public class ReceiverScan extends BroadcastReceiver {
      public void onReceive(Context var1, Intent var2) {
         ActivityWatchesScan.this.timerResetable.pause();
         if (!ActivityWatchesScan.this.isActivityPaused) {
            String var3 = var2.getStringExtra("name");
            String var5 = var2.getStringExtra("address");
            int var4 = var2.getIntExtra("rssi", 0);
            var2.getByteArrayExtra("scan_record");
            String var6 = WatchOp.checkWatchDeviceName(var3);
            if (var6 != null) {
               if (WatchOp.mac_address_to_scan == null || WatchOp.mac_address_to_scan.equals(var5)) {
                  DataStruct.BleDevice var7 = new DataStruct.BleDevice(var5, var6, var4, false);
                  WatchOp.devices_scanned.add(var7);
                  WatchOp.devices_scanned_map.put(var5, var7);
                  if (WatchOp.mac_address_to_scan != null && WatchOp.mac_address_to_scan.equals(var5)) {
                     ActivityWatchesScan.this.handler.obtainMessage(ActivityWatchesScan.SCAN_STAGE.SCAN_DONE.ordinal()).sendToTarget();
                     ActivityWatchesScan.this.timerResetable.pause();
                  }
               }

            }
         }
      }
   }

   public class ReceiverScanDone extends BroadcastReceiver {
      public void onReceive(Context var1, Intent var2) {
         ActivityWatchesScan.this.timerResetable.pause();
         if (!ActivityWatchesScan.this.isActivityPaused) {
            ActivityWatchesScan.this.handler.obtainMessage(ActivityWatchesScan.SCAN_STAGE.SCAN_DONE.ordinal()).sendToTarget();
         }
      }
   }

   public class ReceiverSupport extends BroadcastReceiver {
      public void onReceive(Context var1, Intent var2) {
         if (!ActivityWatchesScan.this.isActivityPaused) {
            if (WatchOp.bleOp.is_Bluetooth_supported()) {
               Log.d("ActivityWatchesScan", "BLE supported");
               ActivityWatchesScan.this.handler.obtainMessage(ActivityWatchesScan.SCAN_STAGE.CHECK_ENABLE.ordinal()).sendToTarget();
            } else {
               Log.d("ActivityWatchesScan", "BLE not supported!");
               UiOp.toast_message(var1, "Bluetooth LE not supported in this device", false);
               ActivityWatchesScan.this.exit_activity();
            }

         }
      }
   }

   public class ReceiverTimeout extends BroadcastReceiver {
      public void onReceive(Context var1, Intent var2) {
         if (!ActivityWatchesScan.this.isActivityPaused) {
            int var3 = null.$SwitchMap$com$crest$divestory$ui$watches$ActivityWatchesScan$SCAN_STAGE[ActivityWatchesScan.this.scan_stage.ordinal()];
            if (var3 != 2 && var3 != 3) {
               if (var3 == 5) {
                  if (WatchOp.devices_scanned.length() > 0) {
                     ActivityWatchesScan.this.handler.obtainMessage(ActivityWatchesScan.SCAN_STAGE.SCAN_DONE.ordinal()).sendToTarget();
                  } else {
                     ActivityWatchesScan.this.handler.obtainMessage(ActivityWatchesScan.SCAN_STAGE.SHOW_RESCAN.ordinal()).sendToTarget();
                  }
               }
            } else {
               Log.d("ActivityWatchesScan", "BLE not enabled!");
               UiOp.toast_message(var1, "Bluetooth LE cannot be enabled in this device", false);
               ActivityWatchesScan.this.exit_activity();
            }

         }
      }
   }

   private static enum SCAN_STAGE {
      CHECK_ENABLE,
      CHECK_SUPPORT,
      CONNECTING,
      ENABLING,
      SCANNED,
      SCANNING,
      SCAN_DONE,
      SHOW_RESCAN;

      static {
         ActivityWatchesScan.SCAN_STAGE var0 = new ActivityWatchesScan.SCAN_STAGE("SCAN_DONE", 7);
         SCAN_DONE = var0;
      }
   }
}
