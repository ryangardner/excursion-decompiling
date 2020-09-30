/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.bluetooth.BluetoothGattCharacteristic
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.res.Configuration
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  android.util.Log
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.ProgressBar
 *  android.widget.TextView
 */
package com.crest.divestory.ui.watches;

import android.app.Activity;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.crest.divestory.AppBase;
import com.crest.divestory.DataStruct;
import com.crest.divestory.DbOp;
import com.crest.divestory.MainActivity;
import com.crest.divestory.WatchOp;
import com.crest.divestory.ui.PagerAdapterMain;
import com.crest.divestory.ui.watches.ActivityWatchDiveLogsDownload;
import com.crest.divestory.ui.watches.ActivityWatchSettings;
import com.crest.divestory.ui.watches.ActivityWatchUpgrade;
import com.crest.divestory.ui.watches.AdapterWatchesListRecyclerView;
import com.crest.divestory.ui.watches.FragmentSyncedWatchesList;
import com.syntak.library.BleOp;
import com.syntak.library.ByteOp;
import com.syntak.library.StringOp;
import com.syntak.library.TimeOp;
import com.syntak.library.UiOp;
import com.syntak.library.time.TimerResetable;
import java.io.Serializable;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.UUID;

public class ActivityWatchSync
extends AppCompatActivity {
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
    byte current_sub_command = (byte)(false ? 1 : 0);
    boolean flag_check_connection = true;
    Handler handler = null;
    private boolean isActivityPaused = false;
    boolean isConnected = false;
    boolean isReceiverStopped = false;
    boolean isServicesFound = false;
    boolean isUpgradeWarned = false;
    ProgressBar progress_circle;
    ReceiverConnectionStateChanged receiverConnetionStateChanged = null;
    ReceiverResponse receiverResponse = null;
    ReceiverServices receiverServices = null;
    ReceiverTimeout receiverTimeout = null;
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

    static /* synthetic */ DataStruct.BleDevice access$000(ActivityWatchSync activityWatchSync) {
        return activityWatchSync.bleDevice;
    }

    static /* synthetic */ WatchOp.WATCH_REPLY access$100(ActivityWatchSync activityWatchSync) {
        return activityWatchSync.watch_reply;
    }

    private void init_UI() {
        ProgressBar progressBar;
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
        this.progress_circle = progressBar = (ProgressBar)this.findViewById(2131231228);
        progressBar.setVisibility(0);
        int n = this.getIntent().getIntExtra("index", -1);
        if (n >= 0) {
            this.bleDevice = WatchOp.devices_scanned.list.get(n);
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
        this.button_download_logs.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                ActivityWatchSync.this.clicked_download_logs(view);
            }
        });
        this.button_upgrade_firmware.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                ActivityWatchSync.this.clicked_upgrade(view);
            }
        });
        this.button_watch_settings.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                ActivityWatchSync.this.clicked_set_watch(view);
            }
        });
        this.init_timer();
        this.start_receivers();
    }

    private void init_action_bar() {
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(2131165356);
        AppBase.setTitleAndColor((Activity)this, 2131689732, 2131034179);
    }

    private void init_resume() {
        if (AppBase.isExitSync) {
            AppBase.isExitSync = false;
            this.exit_activity();
            return;
        }
        this.isActivityPaused = false;
        if (WatchOp.isFirmwareUpgraded) {
            WatchOp.isSyncDone = true;
            this.exit_activity();
            return;
        }
        if (this.handler != null) return;
        this.start_handler();
        this.handler.postDelayed(new Runnable(){

            @Override
            public void run() {
                ActivityWatchSync.this.handler.obtainMessage(0).sendToTarget();
            }
        }, 500L);
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

    private void readInfo(byte by) {
        this.readInfo(WatchOp.watch_connected.mac_address, by);
    }

    private void readInfo(String string2, byte by) {
        this.current_sub_command = by;
        WatchOp.readInfo(string2, by);
        this.timerResetable.resetTimer(10000).start();
    }

    private void start_handler() {
        this.handler = new Handler(){

            public void handleMessage(Message object) {
                byte by = (byte)object.what;
                if (by != 31) {
                    switch (by) {
                        default: {
                            switch (by) {
                                default: {
                                    return;
                                }
                                case 14: {
                                    WatchOp.watch_connected.no_flight_time = ActivityWatchSync.access$100((ActivityWatchSync)ActivityWatchSync.this).data_length == 2 ? ByteOp.uint16ToInt(ActivityWatchSync.access$100((ActivityWatchSync)ActivityWatchSync.this).data, WatchOp.byteOrder) : ActivityWatchSync.access$100((ActivityWatchSync)ActivityWatchSync.this).data[0];
                                    ActivityWatchSync.this.readInfo((byte)13);
                                    return;
                                }
                                case 13: {
                                    WatchOp.watch_connected.no_dive_time = ActivityWatchSync.access$100((ActivityWatchSync)ActivityWatchSync.this).data_length == 2 ? ByteOp.uint16ToInt(ActivityWatchSync.access$100((ActivityWatchSync)ActivityWatchSync.this).data, WatchOp.byteOrder) : ActivityWatchSync.access$100((ActivityWatchSync)ActivityWatchSync.this).data[0];
                                    WatchOp.readSetting(WatchOp.watch_connected.mac_address, (byte)31);
                                    return;
                                }
                                case 12: 
                            }
                            WatchOp.watch_connected.free_dive_max_depth = ByteOp.uint16ToInt(ActivityWatchSync.access$100((ActivityWatchSync)ActivityWatchSync.this).data, WatchOp.byteOrder);
                            ActivityWatchSync.this.readInfo((byte)14);
                            return;
                        }
                        case 10: {
                            WatchOp.watch_connected.free_dive_longest_time = ByteOp.uint16ToInt(ActivityWatchSync.access$100((ActivityWatchSync)ActivityWatchSync.this).data, WatchOp.byteOrder);
                            ActivityWatchSync.this.readInfo((byte)12);
                            return;
                        }
                        case 9: {
                            WatchOp.watch_connected.free_dive_times = ByteOp.uint16ToInt(ActivityWatchSync.access$100((ActivityWatchSync)ActivityWatchSync.this).data, WatchOp.byteOrder);
                            ActivityWatchSync.this.readInfo((byte)10);
                            return;
                        }
                        case 8: {
                            WatchOp.watch_connected.scuba_dive_times = ByteOp.uint16ToInt(ActivityWatchSync.access$100((ActivityWatchSync)ActivityWatchSync.this).data, WatchOp.byteOrder);
                            ActivityWatchSync.this.readInfo((byte)9);
                            return;
                        }
                        case 7: {
                            WatchOp.watch_connected.scuba_dive_max_depth = ByteOp.uint16ToInt(ActivityWatchSync.access$100((ActivityWatchSync)ActivityWatchSync.this).data, WatchOp.byteOrder);
                            ActivityWatchSync.this.readInfo((byte)8);
                            return;
                        }
                        case 6: {
                            WatchOp.watch_connected.scuba_dive_total_time = ByteOp.uint32ToLong(ActivityWatchSync.access$100((ActivityWatchSync)ActivityWatchSync.this).data, WatchOp.byteOrder);
                            ActivityWatchSync.this.readInfo((byte)7);
                            return;
                        }
                        case 5: {
                            if (ActivityWatchSync.access$100((ActivityWatchSync)ActivityWatchSync.this).data != null || ActivityWatchSync.access$100((ActivityWatchSync)ActivityWatchSync.this).data.length > 0) {
                                long l;
                                WatchOp.watch_connected.last_dive_time = l = TimeOp.DateTimeToMs(ActivityWatchSync.access$100((ActivityWatchSync)ActivityWatchSync.this).data[0] + 2000, ActivityWatchSync.access$100((ActivityWatchSync)ActivityWatchSync.this).data[1] - 1, ActivityWatchSync.access$100((ActivityWatchSync)ActivityWatchSync.this).data[2], ActivityWatchSync.access$100((ActivityWatchSync)ActivityWatchSync.this).data[3], ActivityWatchSync.access$100((ActivityWatchSync)ActivityWatchSync.this).data[4]);
                            }
                            ActivityWatchSync.this.readInfo((byte)6);
                            return;
                        }
                        case 4: {
                            WatchOp.new_last_dive_log_index = ByteOp.get_Integer_from_2_bytes_unsigned(ActivityWatchSync.access$100((ActivityWatchSync)ActivityWatchSync.this).data, WatchOp.byteOrder);
                            ActivityWatchSync.this.readInfo((byte)5);
                            return;
                        }
                        case 3: {
                            ActivityWatchSync.this.flag_check_connection = false;
                            object = StringOp.ByteArrayToString(ActivityWatchSync.access$100((ActivityWatchSync)ActivityWatchSync.this).data).substring(0, 12);
                            WatchOp.prefSerialNumber = object;
                            WatchOp.watch_connected.serial_number = object;
                            ActivityWatchSync.this.textSerialNo.setText((CharSequence)WatchOp.watch_connected.serial_number.substring(0, 11));
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
                            return;
                        }
                        case 2: {
                            WatchOp.watch_connected.firmware_version = new String(ActivityWatchSync.access$100((ActivityWatchSync)ActivityWatchSync.this).data, 0, ActivityWatchSync.access$100((ActivityWatchSync)ActivityWatchSync.this).data_length);
                            ActivityWatchSync.this.textFirmwareVersion.setText((CharSequence)WatchOp.watch_connected.firmware_version);
                            ActivityWatchSync.this.readInfo((byte)4);
                            return;
                        }
                        case 1: {
                            WatchOp.watch_connected.hardware_version = new String(ActivityWatchSync.access$100((ActivityWatchSync)ActivityWatchSync.this).data, 0, ActivityWatchSync.access$100((ActivityWatchSync)ActivityWatchSync.this).data_length);
                            object = WatchOp.getModelName(WatchOp.watch_connected.serial_number, WatchOp.watch_connected.hardware_version);
                            WatchOp.watch_connected.model_name = object;
                            WatchOp.watch_connected.model_name_to_show = WatchOp.firmwares.get_model_name_to_show((String)object);
                            ActivityWatchSync.this.textHardwareVersion.setText((CharSequence)WatchOp.watch_connected.hardware_version);
                            ActivityWatchSync.this.readInfo((byte)2);
                            return;
                        }
                        case 0: {
                            WatchOp.bleOp.do_Task(60, ActivityWatchSync.access$000((ActivityWatchSync)ActivityWatchSync.this).mac_address);
                            ActivityWatchSync.this.timerResetable.resetTimer(30000).start();
                            return;
                        }
                        case -1: 
                    }
                    UiOp.toast_message(ActivityWatchSync.this.context, 2131689779);
                    WatchOp.isTimeout = true;
                    ActivityWatchSync.this.exit_activity();
                    return;
                }
                WatchOp.utc_offset = ActivityWatchSync.access$100((ActivityWatchSync)ActivityWatchSync.this).data[0] - 12;
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
                ActivityWatchSync.this.textModelName.setText((CharSequence)WatchOp.watch_connected.model_name_to_show);
                ActivityWatchSync.this.status_watch.setImageDrawable(ActivityWatchSync.this.getDrawable(2131165429));
                ActivityWatchSync.this.status.setText((CharSequence)ActivityWatchSync.this.getString(2131689572));
                if (WatchOp.isSerialNumberValid(WatchOp.watch_connected.serial_number) && !WatchOp.isOldFirmware(WatchOp.watch_connected.firmware_version)) {
                    ActivityWatchSync.this.button_download_logs.setEnabled(true);
                    ActivityWatchSync.this.button_watch_settings.setEnabled(true);
                }
                if (!WatchOp.watch_connected.model_name.equals("Unknown Model")) {
                    ActivityWatchSync.this.button_upgrade_firmware.setEnabled(true);
                }
                ActivityWatchSync.this.stop_receivers();
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
        if (this.receiverServices == null) {
            broadcastReceiver = new ReceiverServices();
            this.receiverServices = broadcastReceiver;
            this.activity.registerReceiver(broadcastReceiver, new IntentFilter("ACTION_GATT_SERVICES_DISCOVERED"));
        }
        if (this.receiverConnetionStateChanged == null) {
            broadcastReceiver = new ReceiverConnectionStateChanged();
            this.receiverConnetionStateChanged = broadcastReceiver;
            this.activity.registerReceiver(broadcastReceiver, new IntentFilter("ACTION_GATT_CONNECTION_CHANGED"));
        }
        if (this.receiverResponse == null) {
            broadcastReceiver = new ReceiverResponse();
            this.receiverResponse = broadcastReceiver;
            this.activity.registerReceiver(broadcastReceiver, new IntentFilter("ACTION_DATA_AVAILABLE"));
        }
        this.isReceiverStopped = false;
    }

    private void stop_receivers() {
        if (this.isReceiverStopped) {
            return;
        }
        this.isReceiverStopped = true;
        try {
            if (this.receiverTimeout != null) {
                this.activity.unregisterReceiver((BroadcastReceiver)this.receiverTimeout);
                this.receiverTimeout = null;
            }
            if (this.receiverServices != null) {
                this.activity.unregisterReceiver((BroadcastReceiver)this.receiverServices);
                this.receiverServices = null;
            }
            if (this.receiverResponse != null) {
                this.activity.unregisterReceiver((BroadcastReceiver)this.receiverResponse);
                this.receiverServices = null;
            }
            if (this.receiverConnetionStateChanged == null) return;
            this.activity.unregisterReceiver((BroadcastReceiver)this.receiverConnetionStateChanged);
            this.receiverConnetionStateChanged = null;
            return;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            illegalArgumentException.printStackTrace();
        }
    }

    private void writeSerialNumber(String arrby) {
        if (arrby == null) {
            return;
        }
        arrby = ByteOp.get_BytesArray_from_String((String)arrby);
        WatchOp.writeFactoryTest(WatchOp.watch_connected.mac_address, (byte)2, arrby, arrby.length);
    }

    public void clicked_download_logs(View view) {
        this.stop_receivers();
        view = new Intent();
        view.setClass((Context)this, ActivityWatchDiveLogsDownload.class);
        this.startActivity((Intent)view);
    }

    public void clicked_set_watch(View view) {
        this.stop_receivers();
        view = new Intent();
        view.setClass((Context)this, ActivityWatchSettings.class);
        this.startActivity((Intent)view);
    }

    public void clicked_upgrade(View view) {
        if (!WatchOp.firmwares.get_activated(WatchOp.watch_connected.model_name)) return;
        this.stop_receivers();
        view = new Intent();
        view.setClass((Context)this, ActivityWatchUpgrade.class);
        this.startActivity((Intent)view);
    }

    public void exit_activity() {
        this.isActivityPaused = true;
        WatchOp.isSyncDone = true;
        this.writeSerialNumber(WatchOp.prefSerialNumber);
        WatchOp.bleOp.do_Task(100, this.bleDevice.mac_address);
        this.stop_receivers();
        this.finish();
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
        this.init_resume();
    }

    public class ReceiverConnectionStateChanged
    extends BroadcastReceiver {
        public void onReceive(Context object, Intent intent) {
            ActivityWatchSync.this.timerResetable.pause();
            if (ActivityWatchSync.this.isActivityPaused) {
                return;
            }
            object = intent.getStringExtra("address");
            if ((BleOp.CONNECTION_STATE)((Object)intent.getSerializableExtra("state")) != BleOp.CONNECTION_STATE.CONNECTED) {
                WatchOp.isTimeout = true;
                ActivityWatchSync.this.exit_activity();
                Log.d((String)"Watch Sync", (String)"dis-connected");
                return;
            }
            ActivityWatchSync.this.isConnected = true;
            if (ActivityWatchSync.this.isServicesFound) {
                WatchOp.bleOp.enable_Notification((String)object, WatchOp.UUID_SERVICE, WatchOp.UUID_CHARACTERISTIC_SETTING);
                ActivityWatchSync.this.handler.postDelayed(new Runnable((String)object){
                    final /* synthetic */ String val$address;
                    {
                        this.val$address = string2;
                    }

                    @Override
                    public void run() {
                        ActivityWatchSync.this.readInfo(this.val$address, (byte)3);
                    }
                }, 500L);
            }
            Log.d((String)"Watch Sync", (String)"Connected");
        }

    }

    public class ReceiverResponse
    extends BroadcastReceiver {
        public void onReceive(Context context, Intent object) {
            ActivityWatchSync.this.timerResetable.pause();
            if (ActivityWatchSync.this.isActivityPaused) {
                return;
            }
            CharSequence charSequence = object.getStringExtra("address");
            object = WatchOp.bleOp.get_Characteristic_received((String)charSequence);
            ActivityWatchSync.this.watch_reply = WatchOp.handle_received_charteristics((String)charSequence, (BluetoothGattCharacteristic)object);
            if (ActivityWatchSync.access$100((ActivityWatchSync)ActivityWatchSync.this).error == WatchOp.WATCH_REPLY.Error.NO_ERROR) {
                ActivityWatchSync.this.handler.obtainMessage((int)ActivityWatchSync.access$100((ActivityWatchSync)ActivityWatchSync.this).sub_command, (Object)charSequence).sendToTarget();
                return;
            }
            object = ActivityWatchSync.access$100((ActivityWatchSync)ActivityWatchSync.this).raw_response != null ? StringOp.getHexFromBytes(ActivityWatchSync.access$100((ActivityWatchSync)ActivityWatchSync.this).raw_response, false, " ") : "null";
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Watch response:");
            ((StringBuilder)charSequence).append((String)object);
            UiOp.toast_message(context, ((StringBuilder)charSequence).toString(), true);
        }
    }

    public class ReceiverServices
    extends BroadcastReceiver {
        public void onReceive(Context object, Intent object2) {
            ActivityWatchSync.this.timerResetable.pause();
            if (ActivityWatchSync.this.isActivityPaused) {
                return;
            }
            ActivityWatchSync.this.isServicesFound = true;
            ActivityWatchSync.this.isConnected = true;
            object = object2.getStringExtra("address");
            WatchOp.bleOp.enable_Notification((String)object, WatchOp.UUID_SERVICE, WatchOp.UUID_CHARACTERISTIC_SETTING);
            object2 = new Runnable(){

                @Override
                public void run() {
                    WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)40, null, 0);
                }
            };
            object = new Runnable(){

                @Override
                public void run() {
                    ActivityWatchSync.this.readInfo((byte)3);
                }
            };
            ActivityWatchSync.this.handler.postDelayed((Runnable)object2, 500L);
            ActivityWatchSync.this.handler.postDelayed((Runnable)object, 1000L);
        }

    }

    public class ReceiverTimeout
    extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            if (ActivityWatchSync.this.isActivityPaused) {
                return;
            }
            WatchOp.isTimeout = true;
            if (ActivityWatchSync.this.current_sub_command != 0) {
                Log.d((String)"ActivityWatchesSync", (String)"Read Info failed!!");
                UiOp.toast_message(context, "Read Info failed!!", false);
                ActivityWatchSync.this.exit_activity();
                return;
            }
            Log.d((String)"ActivityWatchesSync", (String)ActivityWatchSync.this.getString(2131689540));
            UiOp.toast_message(context, 2131689540);
            ActivityWatchSync.this.exit_activity();
        }
    }

}

