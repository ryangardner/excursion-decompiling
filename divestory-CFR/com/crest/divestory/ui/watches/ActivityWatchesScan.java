/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  android.util.Log
 *  android.view.MenuItem
 *  android.view.View
 *  android.widget.Button
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.ProgressBar
 *  android.widget.RelativeLayout
 *  android.widget.TextView
 */
package com.crest.divestory.ui.watches;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.crest.divestory.AppBase;
import com.crest.divestory.DataStruct;
import com.crest.divestory.WatchOp;
import com.crest.divestory.ui.watches.ActivityWatchSync;
import com.crest.divestory.ui.watches.AdapterWatchesScannedList;
import com.syntak.library.BleOp;
import com.syntak.library.UiOp;
import com.syntak.library.time.TimerResetable;
import java.util.HashMap;

public class ActivityWatchesScan
extends AppCompatActivity {
    private static boolean isEnablingTried = false;
    private Activity activity;
    private AdapterWatchesScannedList adapter = null;
    private boolean checkTimeout = false;
    private Context context;
    Handler handler = null;
    private boolean isActivityPaused = false;
    private boolean isBleEnabled = false;
    private ListView listView;
    ProgressBar progress_circle;
    ReceiverEnable receiverEnable = null;
    ReceiverScan receiverScan = null;
    ReceiverScanDone receiverScanDone = null;
    ReceiverSupport receiverSupport = null;
    ReceiverTimeout receiverTimeout = null;
    Button rescan;
    SCAN_STAGE scan_stage = SCAN_STAGE.CHECK_SUPPORT;
    RelativeLayout scanning;
    TextView status;
    private TextView status_list;
    TimerResetable timerResetable = null;

    private void init_UI() {
        RelativeLayout relativeLayout;
        this.setContentView(2131427365);
        this.context = this;
        this.activity = this;
        this.init_action_bar();
        this.scanning = relativeLayout = (RelativeLayout)this.findViewById(2131231270);
        relativeLayout.setVisibility(0);
        relativeLayout = (ProgressBar)this.findViewById(2131231228);
        this.progress_circle = relativeLayout;
        relativeLayout.setVisibility(0);
        this.status = (TextView)this.findViewById(2131231328);
        relativeLayout = (Button)this.findViewById(2131231243);
        this.rescan = relativeLayout;
        relativeLayout.setVisibility(8);
        this.status_list = (TextView)this.findViewById(2131231445);
        this.listView = (ListView)this.findViewById(2131231136);
        relativeLayout = (Button)this.findViewById(2131231044);
        WatchOp.open_ble(this.activity);
        this.init_timer();
    }

    private void init_action_bar() {
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(2131165356);
        AppBase.setTitleAndColor((Activity)this, 2131689794, 2131034179);
    }

    private void init_resume() {
        this.isActivityPaused = false;
        this.start_receivers();
        if (this.handler == null) {
            this.start_handler();
            this.handler.postDelayed(new Runnable(){

                @Override
                public void run() {
                    if (ActivityWatchesScan.this.handler == null) return;
                    ActivityWatchesScan.this.handler.obtainMessage(SCAN_STAGE.CHECK_SUPPORT.ordinal()).sendToTarget();
                }
            }, 1000L);
        }
        this.prepare_listview();
    }

    private void init_timer() {
        if (AppBase.timerResetable == null) {
            TimerResetable timerResetable;
            this.timerResetable = timerResetable = new TimerResetable(this, 10000);
            AppBase.timerResetable = timerResetable;
            return;
        }
        this.timerResetable = AppBase.timerResetable;
    }

    private void prepare_listview() {
        AdapterWatchesScannedList adapterWatchesScannedList;
        if (WatchOp.devices_scanned != null) {
            if (!WatchOp.isRescanning) return;
        }
        WatchOp.devices_scanned = new DataStruct.BleDevices();
        WatchOp.devices_scanned_map = new HashMap();
        this.adapter = adapterWatchesScannedList = new AdapterWatchesScannedList((Context)this, WatchOp.devices_scanned);
        this.listView.setAdapter((ListAdapter)adapterWatchesScannedList);
        WatchOp.isRescanning = false;
    }

    private void show_rescan() {
        this.status.setText(2131689855);
        this.rescan.setVisibility(0);
        this.progress_circle.setVisibility(8);
    }

    private void start_handler() {
        this.handler = new Handler(){

            public void handleMessage(Message message) {
                ActivityWatchesScan.this.scan_stage = SCAN_STAGE.values()[message.what];
                int n = 4.$SwitchMap$com$crest$divestory$ui$watches$ActivityWatchesScan$SCAN_STAGE[ActivityWatchesScan.this.scan_stage.ordinal()];
                if (n == 1) {
                    WatchOp.bleOp.do_Task(10);
                    return;
                }
                if (n == 2) {
                    WatchOp.bleOp.do_Task(20);
                    ActivityWatchesScan.this.timerResetable.resetTimer(30000).start();
                    return;
                }
                if (n == 3) {
                    WatchOp.bleOp.do_Task(30);
                    ActivityWatchesScan.this.timerResetable.resetTimer(30000).start();
                    isEnablingTried = true;
                    return;
                }
                if (n == 4) {
                    ActivityWatchesScan.this.show_rescan();
                    return;
                }
                if (n == 5) {
                    ActivityWatchesScan.this.start_scanning(null);
                    return;
                }
                if (n != 7) {
                    return;
                }
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
        };
    }

    private void start_receivers() {
        BroadcastReceiver broadcastReceiver;
        if (this.receiverTimeout == null) {
            broadcastReceiver = new ReceiverTimeout();
            this.receiverTimeout = broadcastReceiver;
            this.activity.registerReceiver(broadcastReceiver, new IntentFilter("ACTION_TIMEOUT"));
        }
        if (this.receiverSupport == null) {
            broadcastReceiver = new ReceiverSupport();
            this.receiverSupport = broadcastReceiver;
            this.activity.registerReceiver(broadcastReceiver, new IntentFilter("ACTION_BLE_SUPPORT"));
        }
        if (this.receiverEnable == null) {
            broadcastReceiver = new ReceiverEnable();
            this.receiverEnable = broadcastReceiver;
            this.activity.registerReceiver(broadcastReceiver, new IntentFilter("ACTION_BLE_ENABLE"));
        }
        if (this.receiverScan == null) {
            broadcastReceiver = new ReceiverScan();
            this.receiverScan = broadcastReceiver;
            this.activity.registerReceiver(broadcastReceiver, new IntentFilter("ACTION_BLE_SCAN"));
        }
        if (this.receiverScanDone != null) return;
        broadcastReceiver = new ReceiverScanDone();
        this.receiverScanDone = broadcastReceiver;
        this.activity.registerReceiver(broadcastReceiver, new IntentFilter("ACTION_BLE_SCAN_DONE"));
    }

    private void stop_receivers() {
        BroadcastReceiver broadcastReceiver = this.receiverTimeout;
        if (broadcastReceiver != null) {
            this.activity.unregisterReceiver(broadcastReceiver);
            this.receiverTimeout = null;
        }
        if ((broadcastReceiver = this.receiverSupport) != null) {
            this.activity.unregisterReceiver(broadcastReceiver);
            this.receiverSupport = null;
        }
        if ((broadcastReceiver = this.receiverEnable) != null) {
            this.activity.unregisterReceiver(broadcastReceiver);
            this.receiverEnable = null;
        }
        if ((broadcastReceiver = this.receiverScan) != null) {
            this.activity.unregisterReceiver(broadcastReceiver);
            this.receiverScan = null;
        }
        if ((broadcastReceiver = this.receiverScanDone) == null) return;
        this.activity.unregisterReceiver(broadcastReceiver);
        this.receiverScanDone = null;
    }

    public void cancel_scanning(View view) {
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

    public void go_next(View view) {
        view = new Intent();
        view.setClass(this.context, ActivityWatchSync.class);
        view.putExtra("index", -1);
        this.context.startActivity((Intent)view);
    }

    @Override
    protected void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n != 30) {
            return;
        }
        this.timerResetable.pause();
        WatchOp.bleOp.do_Task(20);
    }

    @Override
    public void onBackPressed() {
        this.exit_activity();
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.init_UI();
        this.init_resume();
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.init_UI();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        WatchOp.devices_scanned_map = null;
        WatchOp.devices_scanned = null;
        this.stop_receivers();
        this.handler = null;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        this.exit_activity();
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onPause() {
        super.onPause();
        this.isActivityPaused = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.isActivityPaused = false;
        if (!(WatchOp.isTimeout || WatchOp.isSyncDone || WatchOp.isFirmwareUpgraded)) {
            this.init_resume();
            return;
        }
        WatchOp.isTimeout = false;
        this.exit_activity();
    }

    public void start_scanning(View view) {
        WatchOp.bleOp.do_Task(40);
        this.timerResetable.resetTimer(10000).start();
        this.status.setText(2131689793);
        this.rescan.setVisibility(8);
        this.progress_circle.setVisibility(0);
    }

    public class ReceiverEnable
    extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            ActivityWatchesScan.this.timerResetable.pause();
            if (ActivityWatchesScan.this.isActivityPaused) {
                return;
            }
            ActivityWatchesScan.this.isBleEnabled = WatchOp.bleOp.is_Bluetooth_enabled();
            if (ActivityWatchesScan.this.isBleEnabled) {
                ActivityWatchesScan.this.timerResetable.pause();
                Log.d((String)"ActivityWatchesScan", (String)"BLE enabled");
                ActivityWatchesScan.this.handler.postDelayed(new Runnable(){

                    @Override
                    public void run() {
                        ActivityWatchesScan.this.handler.obtainMessage(SCAN_STAGE.SCANNING.ordinal()).sendToTarget();
                    }
                }, 500L);
                return;
            }
            if (!isEnablingTried) {
                ActivityWatchesScan.this.handler.obtainMessage(SCAN_STAGE.ENABLING.ordinal()).sendToTarget();
                return;
            }
            Log.d((String)"ActivityWatchesScan", (String)"BLE not enabled!");
            UiOp.toast_message(context, "Bluetooth LE cannot be enabled in this device", false);
            ActivityWatchesScan.this.exit_activity();
        }

    }

    public class ReceiverScan
    extends BroadcastReceiver {
        public void onReceive(Context object, Intent object2) {
            ActivityWatchesScan.this.timerResetable.pause();
            if (ActivityWatchesScan.this.isActivityPaused) {
                return;
            }
            String string2 = object2.getStringExtra("name");
            object = object2.getStringExtra("address");
            int n = object2.getIntExtra("rssi", 0);
            object2.getByteArrayExtra("scan_record");
            object2 = WatchOp.checkWatchDeviceName(string2);
            if (object2 == null) {
                return;
            }
            if (WatchOp.mac_address_to_scan != null) {
                if (!WatchOp.mac_address_to_scan.equals(object)) return;
            }
            object2 = new DataStruct.BleDevice((String)object, (String)object2, n, false);
            WatchOp.devices_scanned.add((DataStruct.BleDevice)object2);
            WatchOp.devices_scanned_map.put((String)object, (DataStruct.BleDevice)object2);
            if (WatchOp.mac_address_to_scan == null) return;
            if (!WatchOp.mac_address_to_scan.equals(object)) return;
            ActivityWatchesScan.this.handler.obtainMessage(SCAN_STAGE.SCAN_DONE.ordinal()).sendToTarget();
            ActivityWatchesScan.this.timerResetable.pause();
        }
    }

    public class ReceiverScanDone
    extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            ActivityWatchesScan.this.timerResetable.pause();
            if (ActivityWatchesScan.this.isActivityPaused) {
                return;
            }
            ActivityWatchesScan.this.handler.obtainMessage(SCAN_STAGE.SCAN_DONE.ordinal()).sendToTarget();
        }
    }

    public class ReceiverSupport
    extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            if (ActivityWatchesScan.this.isActivityPaused) {
                return;
            }
            if (WatchOp.bleOp.is_Bluetooth_supported()) {
                Log.d((String)"ActivityWatchesScan", (String)"BLE supported");
                ActivityWatchesScan.this.handler.obtainMessage(SCAN_STAGE.CHECK_ENABLE.ordinal()).sendToTarget();
                return;
            }
            Log.d((String)"ActivityWatchesScan", (String)"BLE not supported!");
            UiOp.toast_message(context, "Bluetooth LE not supported in this device", false);
            ActivityWatchesScan.this.exit_activity();
        }
    }

    public class ReceiverTimeout
    extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            if (ActivityWatchesScan.this.isActivityPaused) {
                return;
            }
            int n = 4.$SwitchMap$com$crest$divestory$ui$watches$ActivityWatchesScan$SCAN_STAGE[ActivityWatchesScan.this.scan_stage.ordinal()];
            if (n != 2 && n != 3) {
                if (n != 5) {
                    return;
                }
                if (WatchOp.devices_scanned.length() > 0) {
                    ActivityWatchesScan.this.handler.obtainMessage(SCAN_STAGE.SCAN_DONE.ordinal()).sendToTarget();
                    return;
                }
                ActivityWatchesScan.this.handler.obtainMessage(SCAN_STAGE.SHOW_RESCAN.ordinal()).sendToTarget();
                return;
            }
            Log.d((String)"ActivityWatchesScan", (String)"BLE not enabled!");
            UiOp.toast_message(context, "Bluetooth LE cannot be enabled in this device", false);
            ActivityWatchesScan.this.exit_activity();
        }
    }

    private static final class SCAN_STAGE
    extends Enum<SCAN_STAGE> {
        private static final /* synthetic */ SCAN_STAGE[] $VALUES;
        public static final /* enum */ SCAN_STAGE CHECK_ENABLE;
        public static final /* enum */ SCAN_STAGE CHECK_SUPPORT;
        public static final /* enum */ SCAN_STAGE CONNECTING;
        public static final /* enum */ SCAN_STAGE ENABLING;
        public static final /* enum */ SCAN_STAGE SCANNED;
        public static final /* enum */ SCAN_STAGE SCANNING;
        public static final /* enum */ SCAN_STAGE SCAN_DONE;
        public static final /* enum */ SCAN_STAGE SHOW_RESCAN;

        static {
            SCAN_STAGE sCAN_STAGE;
            CHECK_SUPPORT = new SCAN_STAGE();
            CHECK_ENABLE = new SCAN_STAGE();
            ENABLING = new SCAN_STAGE();
            SHOW_RESCAN = new SCAN_STAGE();
            CONNECTING = new SCAN_STAGE();
            SCANNING = new SCAN_STAGE();
            SCANNED = new SCAN_STAGE();
            SCAN_DONE = sCAN_STAGE = new SCAN_STAGE();
            $VALUES = new SCAN_STAGE[]{CHECK_SUPPORT, CHECK_ENABLE, ENABLING, SHOW_RESCAN, CONNECTING, SCANNING, SCANNED, sCAN_STAGE};
        }

        public static SCAN_STAGE valueOf(String string2) {
            return Enum.valueOf(SCAN_STAGE.class, string2);
        }

        public static SCAN_STAGE[] values() {
            return (SCAN_STAGE[])$VALUES.clone();
        }
    }

}

