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
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  android.view.MenuItem
 *  android.view.View
 *  android.widget.Button
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
import com.crest.divestory.DbOp;
import com.crest.divestory.WatchOp;
import com.crest.divestory.ui.PagerAdapterMain;
import com.syntak.library.BleOp;
import com.syntak.library.UiOp;
import com.syntak.library.time.TimerResetable;
import java.util.ArrayList;
import java.util.UUID;

public class ActivityWatchDiveLogsDownload
extends Activity {
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
    DOWNLOAD_STAGE download_stage = DOWNLOAD_STAGE.ENABLE_NOTIFICATION;
    DOWNLOAD_STATE download_state = DOWNLOAD_STATE.NO_NEW_LOGS;
    Handler handler = null;
    boolean isActivityPaused = false;
    boolean isReceiverStopped = false;
    int length_to_read = 0;
    int local_log_index = 0;
    int number_of_logs_to_download = 0;
    int number_of_logs_to_read = 0;
    int number_of_logs_to_scan = 0;
    TextView percent;
    int profile_data_length = 0;
    int profile_data_read_length;
    ProgressBar progressBar;
    ReceiverResponse receiverResponse = null;
    ReceiverTimeout receiverTimeout = null;
    TextView status_1;
    TextView status_3;
    LinearLayout status_progress;
    TimerResetable timerResetable = null;
    int total_profile_data_length = 0;
    int total_profile_data_read_length;
    private int trials = 0;
    private WatchOp.WATCH_REPLY watch_reply;

    public ActivityWatchDiveLogsDownload() {
        this.TRIAL_TIMES = 3;
    }

    static /* synthetic */ WatchOp.WATCH_REPLY access$100(ActivityWatchDiveLogsDownload activityWatchDiveLogsDownload) {
        return activityWatchDiveLogsDownload.watch_reply;
    }

    static /* synthetic */ int access$508(ActivityWatchDiveLogsDownload activityWatchDiveLogsDownload) {
        int n = activityWatchDiveLogsDownload.trials;
        activityWatchDiveLogsDownload.trials = n + 1;
        return n;
    }

    private void check_logs_to_scan() {
        int n;
        this.number_of_logs_to_read = n = WatchOp.new_last_dive_log_index;
        if (n > 0) {
            TextView textView = this.status_1;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.getString(2131689680));
            stringBuilder.append(this.number_of_logs_to_read);
            textView.setText((CharSequence)stringBuilder.toString());
            this.status_1.setText((CharSequence)"");
            this.action.setText(2131689792);
            this.download_state = DOWNLOAD_STATE.NEW_LOG_AVAILABLE;
            return;
        }
        this.status_1.setText(2131689744);
        this.action.setText(2131689571);
        this.download_state = DOWNLOAD_STATE.NO_NEW_LOGS;
        this.cancel.setVisibility(8);
    }

    private void handle_divelog(DataStruct.DiveLog diveLog) {
        AppBase.dbOp.insertDiveLog(diveLog);
        WatchOp.dive_logs.addDiveLog(diveLog);
        if (diveLog.start_time <= WatchOp.watch_connected.start_time_for_last_downloaded_log) return;
        WatchOp.watch_connected.start_time_for_last_downloaded_log = diveLog.start_time;
        WatchOp.watch_connected.end_time_for_last_downloaded_log = diveLog.start_time + diveLog.duration * 1000L;
        AppBase.dbOp.updateMyWatchTimeForLastDownloadedLog(WatchOp.watch_connected.serial_number, diveLog.start_time, diveLog.duration * 1000L);
        AppBase.dbOp.updateMyWatchLastDiveLogIndex(WatchOp.watch_connected.serial_number, WatchOp.new_last_dive_log_index);
    }

    private void handle_log_profile_data(DataStruct.DiveLog object) {
        object = new DataStruct.DiveProfileData((DataStruct.DiveLog)object, profile_data_buffer, WatchOp.watch_connected.serial_number);
        AppBase.dbOp.insertDiveProfileData((DataStruct.DiveProfileData)object);
    }

    private void init_UI() {
        TextView textView;
        this.setContentView(2131427361);
        this.context = this;
        this.activity = this;
        this.cancel = (ImageView)this.findViewById(2131230899);
        this.status_1 = (TextView)this.findViewById(2131231329);
        this.status_3 = textView = (TextView)this.findViewById(2131231331);
        textView.setVisibility(8);
        this.action = (Button)this.findViewById(2131230777);
        this.status_progress = (LinearLayout)this.findViewById(2131231333);
        this.percent = (TextView)this.findViewById(2131231216);
        this.progressBar = (ProgressBar)this.findViewById(2131231227);
        this.status_progress.setVisibility(8);
        boolean bl = WatchOp.watch_connected.firmware_version.compareTo("C01-4C") <= 0;
        WatchOp.force_dive_profile_data_type_ceiling = bl;
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
            TimerResetable timerResetable;
            this.timerResetable = timerResetable = new TimerResetable(this, 10000);
            AppBase.timerResetable = timerResetable;
            return;
        }
        this.timerResetable = AppBase.timerResetable;
    }

    private void readDiveLog(byte by, int n) {
        this.readDiveLog(WatchOp.watch_connected.mac_address, by, n);
    }

    private void readDiveLog(String string2, byte by, int n) {
        WatchOp.readDiveLog(string2, by, n);
        this.timerResetable.resetTimer(10000).start();
    }

    private void start_handler() {
        this.handler = new Handler(){

            public void handleMessage(Message object) {
                ActivityWatchDiveLogsDownload.this.download_stage = DOWNLOAD_STAGE.values()[object.what];
                switch (2.$SwitchMap$com$crest$divestory$ui$watches$ActivityWatchDiveLogsDownload$DOWNLOAD_STAGE[ActivityWatchDiveLogsDownload.this.download_stage.ordinal()]) {
                    default: {
                        return;
                    }
                    case 11: {
                        UiOp.toast_message(ActivityWatchDiveLogsDownload.this.context, 2131689779);
                        ActivityWatchDiveLogsDownload.this.exit_activity();
                        return;
                    }
                    case 10: {
                        ActivityWatchDiveLogsDownload.this.download_state = DOWNLOAD_STATE.DOWNLOAD_DONE;
                        ActivityWatchDiveLogsDownload.this.progressBar.setProgress(100);
                        object = ActivityWatchDiveLogsDownload.this.percent;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(100);
                        stringBuilder.append("%");
                        object.setText((CharSequence)stringBuilder.toString());
                        ActivityWatchDiveLogsDownload.this.status_3.setVisibility(0);
                        object = ActivityWatchDiveLogsDownload.this.status_3;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(ActivityWatchDiveLogsDownload.this.getString(2131689600));
                        stringBuilder.append(" ");
                        stringBuilder.append(ActivityWatchDiveLogsDownload.this.number_of_logs_to_download);
                        object.setText((CharSequence)stringBuilder.toString());
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
                        if (ActivityWatchDiveLogsDownload.this.number_of_logs_to_download <= 0) return;
                        WatchOp.is_new_divelogs_downloaded = true;
                        AppBase.notifyChangeDiveLogList();
                        AppBase.pagerAdapter.notifyDataSetChanged();
                        return;
                    }
                    case 8: {
                        int n = Math.min(ActivityWatchDiveLogsDownload.this.length_to_read, ActivityWatchDiveLogsDownload.access$100((ActivityWatchDiveLogsDownload)ActivityWatchDiveLogsDownload.this).data_length);
                        System.arraycopy(ActivityWatchDiveLogsDownload.access$100((ActivityWatchDiveLogsDownload)ActivityWatchDiveLogsDownload.this).data, 0, profile_data_buffer, ActivityWatchDiveLogsDownload.this.profile_data_read_length, n);
                        object = ActivityWatchDiveLogsDownload.this;
                        object.profile_data_read_length += n;
                        object = ActivityWatchDiveLogsDownload.this;
                        object.data_read_address += n;
                        object = ActivityWatchDiveLogsDownload.this;
                        object.length_to_read -= n;
                        if (ActivityWatchDiveLogsDownload.this.length_to_read <= 0) {
                            object = ActivityWatchDiveLogsDownload.new_dive_logs.list.get(ActivityWatchDiveLogsDownload.this.local_log_index);
                            ActivityWatchDiveLogsDownload.this.handle_log_profile_data((DataStruct.DiveLog)object);
                            ActivityWatchDiveLogsDownload.this.handle_divelog((DataStruct.DiveLog)object);
                            object = ActivityWatchDiveLogsDownload.this;
                            ++object.local_log_index;
                            if (ActivityWatchDiveLogsDownload.this.local_log_index < ActivityWatchDiveLogsDownload.this.number_of_logs_to_download) {
                                ActivityWatchDiveLogsDownload.this.handler.obtainMessage(DOWNLOAD_STAGE.PREPARE_READ_DIVE_PROFILE_DATA_BLOCK.ordinal()).sendToTarget();
                            } else {
                                ActivityWatchDiveLogsDownload.this.handler.obtainMessage(DOWNLOAD_STAGE.COMPLETED.ordinal()).sendToTarget();
                            }
                        } else {
                            ActivityWatchDiveLogsDownload.this.handler.obtainMessage(DOWNLOAD_STAGE.READ_DIVE_PROFILE_DATA_BLOCK.ordinal()).sendToTarget();
                        }
                        object = ActivityWatchDiveLogsDownload.this;
                        object.total_profile_data_read_length += n;
                        n = ActivityWatchDiveLogsDownload.this.total_profile_data_read_length * 100 / ActivityWatchDiveLogsDownload.this.total_profile_data_length;
                        ActivityWatchDiveLogsDownload.this.progressBar.setProgress(n);
                        object = ActivityWatchDiveLogsDownload.this.percent;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(n);
                        stringBuilder.append("%");
                        object.setText((CharSequence)stringBuilder.toString());
                        return;
                    }
                    case 7: {
                        ActivityWatchDiveLogsDownload.this.handler.postDelayed(new Runnable(){

                            @Override
                            public void run() {
                                WatchOp.readDiveProfileData(WatchOp.watch_connected.mac_address, (byte)3, ActivityWatchDiveLogsDownload.this.dive_log_index, ActivityWatchDiveLogsDownload.this.data_read_address);
                            }
                        }, 200L);
                        return;
                    }
                    case 6: {
                        object = ActivityWatchDiveLogsDownload.new_dive_logs.list.get(ActivityWatchDiveLogsDownload.this.local_log_index);
                        ActivityWatchDiveLogsDownload.this.dive_log_index = (int)object.dive_log_index;
                        ActivityWatchDiveLogsDownload.this.profile_data_length = (int)object.profile_data_length;
                        if (ActivityWatchDiveLogsDownload.this.profile_data_length > 0) {
                            object = ActivityWatchDiveLogsDownload.this;
                            object.length_to_read = object.profile_data_length;
                            ActivityWatchDiveLogsDownload.this.profile_data_read_length = 0;
                            ActivityWatchDiveLogsDownload.this.data_read_address = 0;
                            profile_data_buffer = new byte[ActivityWatchDiveLogsDownload.this.profile_data_length];
                            ActivityWatchDiveLogsDownload.this.handler.obtainMessage(DOWNLOAD_STAGE.READ_DIVE_PROFILE_DATA_BLOCK.ordinal()).sendToTarget();
                            return;
                        }
                        ActivityWatchDiveLogsDownload.this.handle_divelog((DataStruct.DiveLog)object);
                        object = ActivityWatchDiveLogsDownload.this;
                        ++object.local_log_index;
                        if (ActivityWatchDiveLogsDownload.this.local_log_index < ActivityWatchDiveLogsDownload.this.number_of_logs_to_download) {
                            ActivityWatchDiveLogsDownload.this.handler.obtainMessage(DOWNLOAD_STAGE.PREPARE_READ_DIVE_PROFILE_DATA_BLOCK.ordinal()).sendToTarget();
                            return;
                        }
                        ActivityWatchDiveLogsDownload.this.handler.obtainMessage(DOWNLOAD_STAGE.COMPLETED.ordinal()).sendToTarget();
                        return;
                    }
                    case 5: {
                        UiOp.toast_message(ActivityWatchDiveLogsDownload.this.context, 2131689856);
                        ActivityWatchDiveLogsDownload.this.exit_activity();
                        return;
                    }
                    case 4: {
                        Object object2;
                        if (WatchOp.dive_logs == null) {
                            WatchOp.dive_logs = new DataStruct.DiveLogs();
                        }
                        object = new DataStruct.DiveLog(WatchOp.watch_connected.serial_number, ActivityWatchDiveLogsDownload.access$100((ActivityWatchDiveLogsDownload)ActivityWatchDiveLogsDownload.this).data);
                        if (object.start_time > WatchOp.watch_connected.start_time_for_last_downloaded_log) {
                            if (WatchOp.isOldFirmware(object.firmware_version)) {
                                object.profile_data_length = 0L;
                            }
                            if (new_dive_logs == null) {
                                new_dive_logs = new DataStruct.DiveLogs();
                            }
                            new_dive_logs.addDiveLog((DataStruct.DiveLog)object);
                            object2 = ActivityWatchDiveLogsDownload.this;
                            ((ActivityWatchDiveLogsDownload)object2).total_profile_data_length = (int)((long)((ActivityWatchDiveLogsDownload)object2).total_profile_data_length + object.profile_data_length);
                            object = ActivityWatchDiveLogsDownload.this;
                            ++object.number_of_logs_to_download;
                        }
                        if (ActivityWatchDiveLogsDownload.this.dive_log_index < ActivityWatchDiveLogsDownload.this.number_of_logs_to_read) {
                            ActivityWatchDiveLogsDownload.this.download_stage = DOWNLOAD_STAGE.READ_DIVE_LOG_FULL_HEADER;
                            object = ActivityWatchDiveLogsDownload.this;
                            ++object.dive_log_index;
                        } else {
                            ActivityWatchDiveLogsDownload.this.local_log_index = 0;
                            if (ActivityWatchDiveLogsDownload.this.number_of_logs_to_download > 0) {
                                ActivityWatchDiveLogsDownload.this.download_stage = DOWNLOAD_STAGE.PREPARE_READ_DIVE_PROFILE_DATA_BLOCK;
                                ActivityWatchDiveLogsDownload.this.status_1.setText(2131689605);
                                ActivityWatchDiveLogsDownload.this.action.setText(2131689537);
                            } else {
                                ActivityWatchDiveLogsDownload.this.download_stage = DOWNLOAD_STAGE.COMPLETED;
                            }
                        }
                        ActivityWatchDiveLogsDownload.this.handler.obtainMessage(ActivityWatchDiveLogsDownload.this.download_stage.ordinal()).sendToTarget();
                        int n = ActivityWatchDiveLogsDownload.this.dive_log_index * 100 / ActivityWatchDiveLogsDownload.this.number_of_logs_to_read;
                        ActivityWatchDiveLogsDownload.this.progressBar.setProgress(n);
                        object = ActivityWatchDiveLogsDownload.this.percent;
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append(n);
                        ((StringBuilder)object2).append("%");
                        object.setText((CharSequence)((StringBuilder)object2).toString());
                        return;
                    }
                    case 3: {
                        ActivityWatchDiveLogsDownload.this.handler.postDelayed(new Runnable(){

                            @Override
                            public void run() {
                                ActivityWatchDiveLogsDownload.this.readDiveLog((byte)2, ActivityWatchDiveLogsDownload.this.dive_log_index);
                            }
                        }, 200L);
                        return;
                    }
                    case 2: {
                        WatchOp.set_MTU(WatchOp.watch_connected.mac_address, 248);
                        ActivityWatchDiveLogsDownload.this.handler.postDelayed(new Runnable(){

                            @Override
                            public void run() {
                                ActivityWatchDiveLogsDownload.this.download_state = DOWNLOAD_STATE.DOWNLOADING;
                                ActivityWatchDiveLogsDownload.this.download_stage = DOWNLOAD_STAGE.READ_DIVE_LOG_FULL_HEADER;
                                ActivityWatchDiveLogsDownload.this.handler.obtainMessage(ActivityWatchDiveLogsDownload.this.download_stage.ordinal()).sendToTarget();
                            }
                        }, 500L);
                        return;
                    }
                    case 1: 
                }
                WatchOp.bleOp.enable_Notification(WatchOp.watch_connected.mac_address, WatchOp.UUID_SERVICE, WatchOp.UUID_CHARACTERISTIC_SETTING);
                ActivityWatchDiveLogsDownload.this.handler.postDelayed(new Runnable(){

                    @Override
                    public void run() {
                        ActivityWatchDiveLogsDownload.this.download_stage = DOWNLOAD_STAGE.SET_MTU;
                        ActivityWatchDiveLogsDownload.this.handler.obtainMessage(ActivityWatchDiveLogsDownload.this.download_stage.ordinal()).sendToTarget();
                    }
                }, 500L);
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
        if (this.receiverResponse != null) return;
        broadcastReceiver = new ReceiverResponse();
        this.receiverResponse = broadcastReceiver;
        this.activity.registerReceiver(broadcastReceiver, new IntentFilter("ACTION_DATA_AVAILABLE"));
    }

    private void start_scan() {
        this.download_stage = DOWNLOAD_STAGE.ENABLE_NOTIFICATION;
        this.download_state = DOWNLOAD_STATE.DOWNLOADING;
        this.status_progress.setVisibility(0);
        this.status_1.setText(2131689790);
        this.action.setText(2131689538);
    }

    private void stop_receivers() {
        if (this.isReceiverStopped) {
            return;
        }
        this.isReceiverStopped = true;
        BroadcastReceiver broadcastReceiver = this.receiverTimeout;
        if (broadcastReceiver != null) {
            this.activity.unregisterReceiver(broadcastReceiver);
        }
        this.receiverTimeout = null;
        broadcastReceiver = this.receiverResponse;
        if (broadcastReceiver != null) {
            this.activity.unregisterReceiver(broadcastReceiver);
        }
        this.receiverResponse = null;
    }

    public void click_action(View view) {
        int n = 2.$SwitchMap$com$crest$divestory$ui$watches$ActivityWatchDiveLogsDownload$DOWNLOAD_STATE[this.download_state.ordinal()];
        if (n == 1) {
            this.handler.obtainMessage(DOWNLOAD_STAGE.ENABLE_NOTIFICATION.ordinal()).sendToTarget();
            this.status_progress.setVisibility(0);
            this.status_1.setText(2131689790);
            this.action.setText(2131689538);
            return;
        }
        if (n != 2) {
            if (n != 3 && n != 4) {
                return;
            }
        } else {
            this.exit_activity();
        }
        this.exit_activity();
    }

    public void click_cancel(View view) {
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

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.init_UI();
        this.init_resume();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.init_UI();
    }

    public void onDestroy() {
        super.onDestroy();
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

    public void onPause() {
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
        this.init_resume();
    }

    private static final class DOWNLOAD_STAGE
    extends Enum<DOWNLOAD_STAGE> {
        private static final /* synthetic */ DOWNLOAD_STAGE[] $VALUES;
        public static final /* enum */ DOWNLOAD_STAGE COMPLETED;
        public static final /* enum */ DOWNLOAD_STAGE ENABLE_NOTIFICATION;
        public static final /* enum */ DOWNLOAD_STAGE PREPARE_READ_DIVE_PROFILE_DATA_BLOCK;
        public static final /* enum */ DOWNLOAD_STAGE READ_DIVE_LOG_COMPACT_HEADER;
        public static final /* enum */ DOWNLOAD_STAGE READ_DIVE_LOG_COMPACT_HEADER_FAIL;
        public static final /* enum */ DOWNLOAD_STAGE READ_DIVE_LOG_COMPACT_HEADER_SUCESS;
        public static final /* enum */ DOWNLOAD_STAGE READ_DIVE_LOG_FULL_HEADER;
        public static final /* enum */ DOWNLOAD_STAGE READ_DIVE_LOG_FULL_HEADER_FAIL;
        public static final /* enum */ DOWNLOAD_STAGE READ_DIVE_LOG_FULL_HEADER_SUCESS;
        public static final /* enum */ DOWNLOAD_STAGE READ_DIVE_PROFILE_DATA_BLOCK;
        public static final /* enum */ DOWNLOAD_STAGE READ_DIVE_PROFILE_DATA_BLOCK_FAIL;
        public static final /* enum */ DOWNLOAD_STAGE READ_DIVE_PROFILE_DATA_BLOCK_SUCESS;
        public static final /* enum */ DOWNLOAD_STAGE RESPONSE_TIMEOUT;
        public static final /* enum */ DOWNLOAD_STAGE SET_MTU;

        static {
            DOWNLOAD_STAGE dOWNLOAD_STAGE;
            ENABLE_NOTIFICATION = new DOWNLOAD_STAGE();
            SET_MTU = new DOWNLOAD_STAGE();
            READ_DIVE_LOG_COMPACT_HEADER = new DOWNLOAD_STAGE();
            READ_DIVE_LOG_COMPACT_HEADER_SUCESS = new DOWNLOAD_STAGE();
            READ_DIVE_LOG_COMPACT_HEADER_FAIL = new DOWNLOAD_STAGE();
            READ_DIVE_LOG_FULL_HEADER = new DOWNLOAD_STAGE();
            READ_DIVE_LOG_FULL_HEADER_SUCESS = new DOWNLOAD_STAGE();
            READ_DIVE_LOG_FULL_HEADER_FAIL = new DOWNLOAD_STAGE();
            PREPARE_READ_DIVE_PROFILE_DATA_BLOCK = new DOWNLOAD_STAGE();
            READ_DIVE_PROFILE_DATA_BLOCK = new DOWNLOAD_STAGE();
            READ_DIVE_PROFILE_DATA_BLOCK_SUCESS = new DOWNLOAD_STAGE();
            READ_DIVE_PROFILE_DATA_BLOCK_FAIL = new DOWNLOAD_STAGE();
            COMPLETED = new DOWNLOAD_STAGE();
            RESPONSE_TIMEOUT = dOWNLOAD_STAGE = new DOWNLOAD_STAGE();
            $VALUES = new DOWNLOAD_STAGE[]{ENABLE_NOTIFICATION, SET_MTU, READ_DIVE_LOG_COMPACT_HEADER, READ_DIVE_LOG_COMPACT_HEADER_SUCESS, READ_DIVE_LOG_COMPACT_HEADER_FAIL, READ_DIVE_LOG_FULL_HEADER, READ_DIVE_LOG_FULL_HEADER_SUCESS, READ_DIVE_LOG_FULL_HEADER_FAIL, PREPARE_READ_DIVE_PROFILE_DATA_BLOCK, READ_DIVE_PROFILE_DATA_BLOCK, READ_DIVE_PROFILE_DATA_BLOCK_SUCESS, READ_DIVE_PROFILE_DATA_BLOCK_FAIL, COMPLETED, dOWNLOAD_STAGE};
        }

        public static DOWNLOAD_STAGE valueOf(String string2) {
            return Enum.valueOf(DOWNLOAD_STAGE.class, string2);
        }

        public static DOWNLOAD_STAGE[] values() {
            return (DOWNLOAD_STAGE[])$VALUES.clone();
        }
    }

    private static final class DOWNLOAD_STATE
    extends Enum<DOWNLOAD_STATE> {
        private static final /* synthetic */ DOWNLOAD_STATE[] $VALUES;
        public static final /* enum */ DOWNLOAD_STATE DOWNLOADING;
        public static final /* enum */ DOWNLOAD_STATE DOWNLOAD_DONE;
        public static final /* enum */ DOWNLOAD_STATE NEW_LOG_AVAILABLE;
        public static final /* enum */ DOWNLOAD_STATE NO_NEW_LOGS;

        static {
            DOWNLOAD_STATE dOWNLOAD_STATE;
            NO_NEW_LOGS = new DOWNLOAD_STATE();
            NEW_LOG_AVAILABLE = new DOWNLOAD_STATE();
            DOWNLOADING = new DOWNLOAD_STATE();
            DOWNLOAD_DONE = dOWNLOAD_STATE = new DOWNLOAD_STATE();
            $VALUES = new DOWNLOAD_STATE[]{NO_NEW_LOGS, NEW_LOG_AVAILABLE, DOWNLOADING, dOWNLOAD_STATE};
        }

        public static DOWNLOAD_STATE valueOf(String string2) {
            return Enum.valueOf(DOWNLOAD_STATE.class, string2);
        }

        public static DOWNLOAD_STATE[] values() {
            return (DOWNLOAD_STATE[])$VALUES.clone();
        }
    }

    public class ReceiverResponse
    extends BroadcastReceiver {
        public void onReceive(Context context, Intent arrby) {
            String string2 = arrby.getStringExtra("address");
            context = WatchOp.bleOp.get_Characteristic_received(string2);
            ActivityWatchDiveLogsDownload.this.timerResetable.pause();
            if (ActivityWatchDiveLogsDownload.this.isActivityPaused) {
                return;
            }
            UUID uUID = context.getUuid();
            arrby = context.getValue();
            int n = 2.$SwitchMap$com$crest$divestory$ui$watches$ActivityWatchDiveLogsDownload$DOWNLOAD_STAGE[ActivityWatchDiveLogsDownload.this.download_stage.ordinal()];
            if (n != 3) {
                if (n != 7) {
                    return;
                }
                ActivityWatchDiveLogsDownload.this.watch_reply = WatchOp.handle_received_charteristics(string2, (BluetoothGattCharacteristic)context);
                WatchOp.ackDiveLog(string2, (byte)3, ActivityWatchDiveLogsDownload.access$100((ActivityWatchDiveLogsDownload)ActivityWatchDiveLogsDownload.this).data_length, ActivityWatchDiveLogsDownload.access$100((ActivityWatchDiveLogsDownload)ActivityWatchDiveLogsDownload.this).data);
                ActivityWatchDiveLogsDownload.this.handler.obtainMessage(DOWNLOAD_STAGE.READ_DIVE_PROFILE_DATA_BLOCK_SUCESS.ordinal()).sendToTarget();
                return;
            }
            if (uUID.equals(WatchOp.UUID_CHARACTERISTIC_SETTING) && arrby.length > 4) {
                ActivityWatchDiveLogsDownload.this.trials = 0;
                ActivityWatchDiveLogsDownload.this.watch_reply = WatchOp.handle_received_charteristics(string2, (BluetoothGattCharacteristic)context);
                ActivityWatchDiveLogsDownload.this.handler.obtainMessage(DOWNLOAD_STAGE.READ_DIVE_LOG_FULL_HEADER_SUCESS.ordinal()).sendToTarget();
                return;
            }
            if (ActivityWatchDiveLogsDownload.this.trials < 3) {
                ActivityWatchDiveLogsDownload.access$508(ActivityWatchDiveLogsDownload.this);
                ActivityWatchDiveLogsDownload.this.handler.obtainMessage(DOWNLOAD_STAGE.READ_DIVE_LOG_FULL_HEADER.ordinal()).sendToTarget();
                return;
            }
            ActivityWatchDiveLogsDownload.this.handler.obtainMessage(DOWNLOAD_STAGE.RESPONSE_TIMEOUT.ordinal()).sendToTarget();
        }
    }

    public class ReceiverTimeout
    extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            if (ActivityWatchDiveLogsDownload.this.isActivityPaused) {
                return;
            }
            WatchOp.isTimeout = true;
            UiOp.toast_message(context, 2131689779);
            ActivityWatchDiveLogsDownload.this.exit_activity();
        }
    }

}

