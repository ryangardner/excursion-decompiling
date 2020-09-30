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
 *  android.content.res.AssetManager
 *  android.content.res.Configuration
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnLongClickListener
 *  android.widget.Button
 *  android.widget.FrameLayout
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
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.crest.divestory.AppBase;
import com.crest.divestory.DataStruct;
import com.crest.divestory.WatchOp;
import com.syntak.library.BleOp;
import com.syntak.library.ByteOp;
import com.syntak.library.FileOp;
import com.syntak.library.UiOp;
import com.syntak.library.time.TimerResetable;
import com.syntak.library.ui.EditorAccountPassword;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;
import java.util.UUID;

public class ActivityWatchUpgrade
extends Activity {
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
    String firmware_filepath = null;
    Handler handler = null;
    ImageView icon_upgrade;
    boolean isActivityPaused = false;
    boolean isReceiverStopped = false;
    TextView latest_version;
    int length_received;
    int length_to_send;
    TextView percent;
    ProgressBar progressBar;
    ReceiverResponse receiverResponse = null;
    ReceiverTimeout receiverTimeout = null;
    TextView status_1;
    LinearLayout status_2;
    TextView status_3;
    LinearLayout status_progress;
    TimerResetable timerResetable = null;
    private int trials = 0;
    UPGRADE_STAGE upgrade_stage = UPGRADE_STAGE.READ_FIRMWARE;
    UPGRADE_STATE upgrade_state = UPGRADE_STATE.ALREADY_LATEST;

    public ActivityWatchUpgrade() {
        this.TRIAL_TIMES = 3;
    }

    static /* synthetic */ int access$208(ActivityWatchUpgrade activityWatchUpgrade) {
        int n = activityWatchUpgrade.trials;
        activityWatchUpgrade.trials = n + 1;
        return n;
    }

    private void check_firmware_version() {
        Object object = this.status_2;
        boolean bl = false;
        object.setVisibility(0);
        this.status_progress.setVisibility(8);
        String string2 = WatchOp.firmwares.get_version(WatchOp.watch_connected.model_name);
        object = WatchOp.watch_connected.firmware_version;
        if (string2.compareTo((String)object) > 0) {
            bl = true;
        }
        if (bl) {
            this.status_1.setText(2131689736);
            this.current_version.setText((CharSequence)object);
            this.latest_version.setText((CharSequence)string2);
            this.action.setText(2131689850);
            this.upgrade_state = UPGRADE_STATE.NEWER_AVAILABLE;
            return;
        }
        this.status_1.setText(2131689626);
        this.current_version.setText((CharSequence)object);
        this.latest_version.setText((CharSequence)string2);
        this.action.setText(2131689846);
        this.upgrade_state = UPGRADE_STATE.ALREADY_LATEST;
    }

    private void init_UI() {
        TextView textView;
        this.setContentView(2131427364);
        this.context = this;
        this.activity = this;
        this.engineer_pad = (FrameLayout)this.findViewById(2131231007);
        this.icon_upgrade = (ImageView)this.findViewById(2131231065);
        this.engineer_pad.setVisibility(8);
        this.icon_upgrade.setOnLongClickListener(new View.OnLongClickListener(){

            public boolean onLongClick(View view) {
                ActivityWatchUpgrade.this.verify_password();
                return false;
            }
        });
        this.cancel_upgrade = (ImageView)this.findViewById(2131230903);
        this.status_1 = (TextView)this.findViewById(2131231329);
        this.status_2 = (LinearLayout)this.findViewById(2131231330);
        this.status_3 = textView = (TextView)this.findViewById(2131231331);
        textView.setVisibility(8);
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
            TimerResetable timerResetable;
            this.timerResetable = timerResetable = new TimerResetable(this, 15000);
            AppBase.timerResetable = timerResetable;
            return;
        }
        this.timerResetable = AppBase.timerResetable;
    }

    private int read_firmware() {
        try {
            InputStream inputStream2;
            byte[] arrby;
            if (this.firmware_filepath != null) {
                arrby = new File(this.firmware_filepath);
                inputStream2 = new FileInputStream((File)arrby);
            } else {
                inputStream2 = this.context.getAssets().open(this.firmware_filename);
            }
            int n = inputStream2.available();
            arrby = new byte[n];
            this.firmware_buffer = arrby;
            inputStream2.read(arrby, 0, n);
            inputStream2.close();
            return n;
        }
        catch (IOException iOException) {
            return 0;
        }
    }

    private void start_handler() {
        this.handler = new Handler(){

            public void handleMessage(Message object) {
                object = UPGRADE_STAGE.values()[((Message)object).what];
                switch (4.$SwitchMap$com$crest$divestory$ui$watches$ActivityWatchUpgrade$UPGRADE_STAGE[((Enum)object).ordinal()]) {
                    default: {
                        return;
                    }
                    case 9: {
                        WatchOp.isTimeout = true;
                        UiOp.toast_message(ActivityWatchUpgrade.this.context, 2131689779);
                        ActivityWatchUpgrade.this.exit_activity();
                        return;
                    }
                    case 8: {
                        ActivityWatchUpgrade.this.progressBar.setProgress(100);
                        object = ActivityWatchUpgrade.this.percent;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(100);
                        stringBuilder.append("%");
                        object.setText((CharSequence)stringBuilder.toString());
                        ActivityWatchUpgrade.this.status_progress.setVisibility(8);
                        ActivityWatchUpgrade.this.status_3.setVisibility(0);
                        ActivityWatchUpgrade.this.status_1.setText(2131689848);
                        ActivityWatchUpgrade.this.action.setText(2131689571);
                        AppBase.isExitSync = true;
                        ActivityWatchUpgrade.this.cancel_upgrade.setVisibility(8);
                        return;
                    }
                    case 7: {
                        int n = ActivityWatchUpgrade.this.length_to_send;
                        int n2 = 244;
                        if (n <= 244) {
                            n2 = ActivityWatchUpgrade.this.length_to_send;
                        }
                        WatchOp.firmware_upgrade_send_data_block(WatchOp.watch_connected.mac_address, ActivityWatchUpgrade.this.firmware_buffer, ActivityWatchUpgrade.this.file_index, n2);
                        ActivityWatchUpgrade.this.timerResetable.resetTimer(15000).start();
                        object = ActivityWatchUpgrade.this;
                        ((ActivityWatchUpgrade)object).length_to_send -= n2;
                        object = ActivityWatchUpgrade.this;
                        ((ActivityWatchUpgrade)object).file_index += n2;
                        n2 = ActivityWatchUpgrade.this.file_index * 100 / ActivityWatchUpgrade.this.firmware_file_length;
                        ActivityWatchUpgrade.this.progressBar.setProgress(n2);
                        TextView textView = ActivityWatchUpgrade.this.percent;
                        object = new StringBuilder();
                        ((StringBuilder)object).append(n2);
                        ((StringBuilder)object).append("%");
                        textView.setText((CharSequence)((StringBuilder)object).toString());
                        return;
                    }
                    case 6: {
                        WatchOp.firmware_upgrade_send_header(WatchOp.watch_connected.mac_address, ActivityWatchUpgrade.this.fh.toByteArray());
                        ActivityWatchUpgrade.this.timerResetable.resetTimer(15000).start();
                        return;
                    }
                    case 5: {
                        UiOp.toast_message(ActivityWatchUpgrade.this.context, 2131689856);
                        ActivityWatchUpgrade.this.exit_activity();
                        return;
                    }
                    case 4: {
                        WatchOp.firmware_upgrade_send_command(WatchOp.watch_connected.mac_address);
                        ActivityWatchUpgrade.this.timerResetable.resetTimer(15000).start();
                        return;
                    }
                    case 3: {
                        WatchOp.set_MTU(WatchOp.watch_connected.mac_address, 248);
                        ActivityWatchUpgrade.this.handler.postDelayed(new Runnable(){

                            @Override
                            public void run() {
                                ActivityWatchUpgrade.this.upgrade_stage = UPGRADE_STAGE.SEND_COMMAND;
                                ActivityWatchUpgrade.this.handler.obtainMessage(ActivityWatchUpgrade.this.upgrade_stage.ordinal()).sendToTarget();
                            }
                        }, 500L);
                        return;
                    }
                    case 2: {
                        object = ActivityWatchUpgrade.this;
                        ((ActivityWatchUpgrade)object).firmware_file_length = ((ActivityWatchUpgrade)((Object)object)).read_firmware();
                        if (ActivityWatchUpgrade.this.firmware_file_length > 0) {
                            ActivityWatchUpgrade.this.ffh = new DataStruct.FirmwareFileHeader(ActivityWatchUpgrade.this.firmware_buffer);
                            ActivityWatchUpgrade.this.fh = new DataStruct.FirmwareHeader(ActivityWatchUpgrade.this.ffh);
                            ActivityWatchUpgrade.this.trials = 0;
                            ActivityWatchUpgrade.this.handler.postDelayed(new Runnable(){

                                @Override
                                public void run() {
                                    ActivityWatchUpgrade.this.upgrade_stage = UPGRADE_STAGE.SET_MTU;
                                    ActivityWatchUpgrade.this.handler.obtainMessage(ActivityWatchUpgrade.this.upgrade_stage.ordinal()).sendToTarget();
                                }
                            }, 500L);
                            return;
                        }
                        UiOp.toast_message(ActivityWatchUpgrade.this.context, 2131689627);
                        ActivityWatchUpgrade.this.exit_activity();
                        return;
                    }
                    case 1: 
                }
                WatchOp.bleOp.enable_Notification(WatchOp.watch_connected.mac_address, WatchOp.UUID_SERVICE, WatchOp.UUID_CHARACTERISTIC_UPGRADE);
                ActivityWatchUpgrade.this.upgrade_stage = UPGRADE_STAGE.READ_FIRMWARE;
                ActivityWatchUpgrade.this.handler.obtainMessage(ActivityWatchUpgrade.this.upgrade_stage.ordinal()).sendToTarget();
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

    private void verify_password() {
        EditorAccountPassword.Info info = new EditorAccountPassword.Info();
        info.mode = 5;
        info.context = this.context;
        info.ancher = this.engineer_pad;
        info.title = this.context.getString(2131689615);
        info.text_hint_password = this.context.getString(2131689615);
        info.warning_fields_empty = this.context.getString(2131689754);
        info.warning_password_wrong = this.context.getString(2131689756);
        info.text_confirm = this.context.getString(2131689571);
        info.text_cancel = this.context.getString(2131689536);
        info.password = "3674";
        EditorAccountPassword editorAccountPassword = this.eap;
        if (editorAccountPassword != null) {
            editorAccountPassword.stop();
            this.eap = null;
        }
        this.eap = new EditorAccountPassword(info){

            @Override
            public void OnCancelled() {
                ActivityWatchUpgrade.this.eap.stop();
                ActivityWatchUpgrade.this.eap = null;
            }

            @Override
            public void OnConfirmed(String string2) {
                ActivityWatchUpgrade.this.engineer_pad.setVisibility(0);
            }
        };
    }

    public void click_action(View view) {
        int n = 4.$SwitchMap$com$crest$divestory$ui$watches$ActivityWatchUpgrade$UPGRADE_STATE[this.upgrade_state.ordinal()];
        if (n != 1 && n != 2) {
            if (n == 3) {
                this.exit_activity();
                return;
            }
            if (n != 4) {
                return;
            }
            WatchOp.isFirmwareUpgraded = true;
            this.exit_activity();
            return;
        }
        this.handler.obtainMessage(UPGRADE_STAGE.ENABLE_NOTIFICATION.ordinal()).sendToTarget();
        this.status_2.setVisibility(8);
        this.status_progress.setVisibility(0);
        this.status_1.setText(2131689851);
        this.action.setText(2131689847);
        this.upgrade_state = UPGRADE_STATE.UPGRADING;
    }

    public void click_cancel(View view) {
        this.exit_activity();
    }

    public void click_hidden_upgrade(View view) {
        this.firmware_filepath = FileOp.get_List_of_Filepath_with_Extension(AppBase.folder_download, "bin").get(0);
        this.handler.obtainMessage(UPGRADE_STAGE.ENABLE_NOTIFICATION.ordinal()).sendToTarget();
        this.status_2.setVisibility(8);
        this.status_progress.setVisibility(0);
        this.status_1.setText(2131689851);
        this.action.setText(2131689847);
        this.upgrade_state = UPGRADE_STATE.UPGRADING;
        view.setEnabled(false);
    }

    public void exit_activity() {
        this.stop_receivers();
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
        this.isActivityPaused = true;
    }

    protected void onResume() {
        super.onResume();
        this.init_resume();
    }

    public class ReceiverResponse
    extends BroadcastReceiver {
        public void onReceive(Context object, Intent arrby) {
            arrby = arrby.getStringExtra("address");
            arrby = WatchOp.bleOp.get_Characteristic_received((String)arrby);
            ActivityWatchUpgrade.this.timerResetable.pause();
            Object object2 = arrby.getUuid();
            arrby = arrby.getValue();
            int n = 4.$SwitchMap$com$crest$divestory$ui$watches$ActivityWatchUpgrade$UPGRADE_STAGE[ActivityWatchUpgrade.this.upgrade_stage.ordinal()];
            boolean bl = true;
            if (n != 4) {
                if (n == 6) {
                    object = ActivityWatchUpgrade.this;
                    object.length_to_send = object.firmware_file_length;
                    ActivityWatchUpgrade.this.upgrade_stage = UPGRADE_STAGE.SEND_DATA_BLOCK;
                    ActivityWatchUpgrade.this.handler.obtainMessage(ActivityWatchUpgrade.this.upgrade_stage.ordinal()).sendToTarget();
                    return;
                }
                if (n != 7) {
                    return;
                }
                if (arrby.length >= 5) {
                    byte by = arrby[4];
                    byte by2 = arrby[3];
                    byte by3 = arrby[2];
                    byte by4 = arrby[1];
                    object2 = ActivityWatchUpgrade.this;
                    ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
                    ((ActivityWatchUpgrade)object2).length_received = (int)ByteOp.get_Long_from_4_bytes_unsigned(new byte[]{by, by2, by3, by4}, byteOrder);
                }
                if (ActivityWatchUpgrade.this.length_to_send > 0) {
                    if (arrby[0] == 2) {
                        ActivityWatchUpgrade.this.upgrade_stage = UPGRADE_STAGE.SEND_DATA_BLOCK;
                        ActivityWatchUpgrade.this.handler.obtainMessage(ActivityWatchUpgrade.this.upgrade_stage.ordinal()).sendToTarget();
                        return;
                    }
                    UiOp.toast_message(object, "Upgrading Fail!", false);
                    ActivityWatchUpgrade.this.exit_activity();
                    return;
                }
                if (arrby[0] == 3) {
                    ActivityWatchUpgrade.this.upgrade_stage = UPGRADE_STAGE.COMPLETED;
                    ActivityWatchUpgrade.this.handler.obtainMessage(ActivityWatchUpgrade.this.upgrade_stage.ordinal()).sendToTarget();
                    return;
                }
                UiOp.toast_message(object, "Upgrade End Fail!", false);
                ActivityWatchUpgrade.this.exit_activity();
                return;
            }
            if (!object2.equals(WatchOp.UUID_CHARACTERISTIC_UPGRADE) || arrby[0] == 0) {
                if (ActivityWatchUpgrade.this.trials < 3) {
                    ActivityWatchUpgrade.access$208(ActivityWatchUpgrade.this);
                    ActivityWatchUpgrade.this.handler.obtainMessage(UPGRADE_STAGE.SEND_COMMAND.ordinal()).sendToTarget();
                } else {
                    ActivityWatchUpgrade.this.handler.obtainMessage(UPGRADE_STAGE.SEND_COMMAND_FAIL.ordinal()).sendToTarget();
                }
                bl = false;
            }
            if (!bl) return;
            ActivityWatchUpgrade.this.trials = 0;
            ActivityWatchUpgrade.this.upgrade_stage = UPGRADE_STAGE.SEND_HEADER;
            ActivityWatchUpgrade.this.handler.obtainMessage(ActivityWatchUpgrade.this.upgrade_stage.ordinal()).sendToTarget();
        }
    }

    public class ReceiverTimeout
    extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            WatchOp.isTimeout = true;
            UiOp.toast_message(context, 2131689779);
            ActivityWatchUpgrade.this.exit_activity();
        }
    }

    private static final class UPGRADE_STAGE
    extends Enum<UPGRADE_STAGE> {
        private static final /* synthetic */ UPGRADE_STAGE[] $VALUES;
        public static final /* enum */ UPGRADE_STAGE COMPLETED;
        public static final /* enum */ UPGRADE_STAGE ENABLE_NOTIFICATION;
        public static final /* enum */ UPGRADE_STAGE READ_FIRMWARE;
        public static final /* enum */ UPGRADE_STAGE RESPONSE_TIMEOUT;
        public static final /* enum */ UPGRADE_STAGE SEND_COMMAND;
        public static final /* enum */ UPGRADE_STAGE SEND_COMMAND_FAIL;
        public static final /* enum */ UPGRADE_STAGE SEND_DATA_BLOCK;
        public static final /* enum */ UPGRADE_STAGE SEND_DATA_BLOCK_FAIL;
        public static final /* enum */ UPGRADE_STAGE SEND_HEADER;
        public static final /* enum */ UPGRADE_STAGE SEND_HEADER_FAIL;
        public static final /* enum */ UPGRADE_STAGE SET_MTU;

        static {
            UPGRADE_STAGE uPGRADE_STAGE;
            ENABLE_NOTIFICATION = new UPGRADE_STAGE();
            READ_FIRMWARE = new UPGRADE_STAGE();
            SET_MTU = new UPGRADE_STAGE();
            SEND_COMMAND = new UPGRADE_STAGE();
            SEND_COMMAND_FAIL = new UPGRADE_STAGE();
            SEND_HEADER = new UPGRADE_STAGE();
            SEND_HEADER_FAIL = new UPGRADE_STAGE();
            SEND_DATA_BLOCK = new UPGRADE_STAGE();
            SEND_DATA_BLOCK_FAIL = new UPGRADE_STAGE();
            COMPLETED = new UPGRADE_STAGE();
            RESPONSE_TIMEOUT = uPGRADE_STAGE = new UPGRADE_STAGE();
            $VALUES = new UPGRADE_STAGE[]{ENABLE_NOTIFICATION, READ_FIRMWARE, SET_MTU, SEND_COMMAND, SEND_COMMAND_FAIL, SEND_HEADER, SEND_HEADER_FAIL, SEND_DATA_BLOCK, SEND_DATA_BLOCK_FAIL, COMPLETED, uPGRADE_STAGE};
        }

        public static UPGRADE_STAGE valueOf(String string2) {
            return Enum.valueOf(UPGRADE_STAGE.class, string2);
        }

        public static UPGRADE_STAGE[] values() {
            return (UPGRADE_STAGE[])$VALUES.clone();
        }
    }

    private static final class UPGRADE_STATE
    extends Enum<UPGRADE_STATE> {
        private static final /* synthetic */ UPGRADE_STATE[] $VALUES;
        public static final /* enum */ UPGRADE_STATE ALREADY_LATEST;
        public static final /* enum */ UPGRADE_STATE NEWER_AVAILABLE;
        public static final /* enum */ UPGRADE_STATE UPGRADE_DONE;
        public static final /* enum */ UPGRADE_STATE UPGRADING;

        static {
            UPGRADE_STATE uPGRADE_STATE;
            ALREADY_LATEST = new UPGRADE_STATE();
            NEWER_AVAILABLE = new UPGRADE_STATE();
            UPGRADING = new UPGRADE_STATE();
            UPGRADE_DONE = uPGRADE_STATE = new UPGRADE_STATE();
            $VALUES = new UPGRADE_STATE[]{ALREADY_LATEST, NEWER_AVAILABLE, UPGRADING, uPGRADE_STATE};
        }

        public static UPGRADE_STATE valueOf(String string2) {
            return Enum.valueOf(UPGRADE_STATE.class, string2);
        }

        public static UPGRADE_STATE[] values() {
            return (UPGRADE_STATE[])$VALUES.clone();
        }
    }

}

