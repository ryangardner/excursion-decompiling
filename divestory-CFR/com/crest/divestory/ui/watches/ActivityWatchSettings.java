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
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  android.util.Log
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.view.View
 *  android.widget.RelativeLayout
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
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.crest.divestory.AppBase;
import com.crest.divestory.DataStruct;
import com.crest.divestory.WatchOp;
import com.crest.divestory.ui.watches.PagerAdapterWatchSettings;
import com.google.android.material.tabs.TabLayout;
import com.syntak.library.BleOp;
import com.syntak.library.ByteOp;
import com.syntak.library.StringOp;
import com.syntak.library.UiOp;
import com.syntak.library.time.TimerResetable;
import java.nio.ByteOrder;
import java.util.HashMap;

public class ActivityWatchSettings
extends AppCompatActivity {
    private Activity activity;
    private Context context;
    Handler handler;
    private boolean isActivityPaused = false;
    boolean isReceiverStopped = false;
    private HashMap<Byte, Boolean> readLogs = new HashMap();
    ReceiverResponse receiverResponse = null;
    ReceiverTimeout receiverTimeout = null;
    private RelativeLayout sign_processing;
    private TextView status;
    TimerResetable timerResetable = null;
    ViewPager view_pager;
    private WatchOp.WATCH_REPLY watch_reply;

    static /* synthetic */ WatchOp.WATCH_REPLY access$200(ActivityWatchSettings activityWatchSettings) {
        return activityWatchSettings.watch_reply;
    }

    private void init_UI() {
        this.setContentView(2131427362);
        this.context = this;
        this.activity = this;
        Object object = (RelativeLayout)this.findViewById(2131231303);
        this.sign_processing = object;
        object.setVisibility(0);
        this.status = (TextView)this.findViewById(2131231328);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AppBase.setTitleAndColor((Activity)this, this.getResources().getString(2131689857), 2131034179);
        this.view_pager = (ViewPager)((Object)this.findViewById(2131231440));
        object = (TabLayout)((Object)this.findViewById(2131231345));
        ((TabLayout)((Object)object)).setupWithViewPager(this.view_pager, true);
        ((TabLayout)((Object)object)).setTabIndicatorFullWidth(true);
        this.init_timer();
    }

    private void init_pager(ViewPager viewPager) {
        PagerAdapterWatchSettings pagerAdapterWatchSettings = new PagerAdapterWatchSettings(this.getSupportFragmentManager());
        pagerAdapterWatchSettings.set_para(this.context);
        viewPager.setAdapter(pagerAdapterWatchSettings);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){

            @Override
            public void onPageSelected(int n) {
                ActivityWatchSettings.this.sync_watch_settings();
            }
        });
    }

    private void init_resume() {
        this.isActivityPaused = false;
        this.start_receivers();
        if (this.handler != null) return;
        this.start_handler();
        this.readSetting((byte)1);
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

    private void readSetting(byte by) {
        this.readSetting(WatchOp.watch_connected.mac_address, by);
    }

    private void readSetting(final String string2, final byte by) {
        this.readLogs.put(by, false);
        this.handler.postDelayed(new Runnable(){

            @Override
            public void run() {
                WatchOp.readSetting(string2, by);
                ActivityWatchSettings.this.timerResetable.resetTimer(10000).start();
            }
        }, 100L);
    }

    private void start_handler() {
        this.handler = new Handler(){

            public void handleMessage(Message object) {
                byte by = (byte)((Message)object).what;
                if (!ActivityWatchSettings.this.readLogs.containsKey(by)) return;
                if ((Boolean)ActivityWatchSettings.this.readLogs.get(by) != false) return;
                ActivityWatchSettings.this.readLogs.put(by, true);
                if (ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data_length <= 0) {
                    Context context = ActivityWatchSettings.this.context;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Watch reply=");
                    ((StringBuilder)object).append(StringOp.getHexFromBytes(ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).raw_response, false, " "));
                    UiOp.toast_message(context, ((StringBuilder)object).toString(), true);
                    return;
                }
                Log.d((String)"ActivityWatchSettings", (String)String.valueOf(by));
                switch (by) {
                    default: {
                        return;
                    }
                    case 34: {
                        WatchOp.watchSetting_gb.vibrator = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data[0];
                        ActivityWatchSettings.this.sign_processing.setVisibility(8);
                        object = ActivityWatchSettings.this;
                        ((ActivityWatchSettings)object).init_pager(((ActivityWatchSettings)object).view_pager);
                        return;
                    }
                    case 33: {
                        WatchOp.watchSetting_gb.backlight = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data[0];
                        ActivityWatchSettings.this.readSetting((byte)34);
                        return;
                    }
                    case 32: {
                        WatchOp.watchSetting_gb.buzzer = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data[0];
                        ActivityWatchSettings.this.readSetting((byte)33);
                        return;
                    }
                    case 31: {
                        WatchOp.watchSetting_gb.UTC_offset = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data[0];
                        ActivityWatchSettings.this.readSetting((byte)32);
                        return;
                    }
                    case 30: {
                        WatchOp.watchSetting_gb.power_saving = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data[0];
                        ActivityWatchSettings.this.readSetting((byte)31);
                        return;
                    }
                    case 29: {
                        WatchOp.watchSetting_gb.display_unit = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data[0];
                        ActivityWatchSettings.this.readSetting((byte)30);
                        return;
                    }
                    case 28: {
                        WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_3 = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data[0];
                        ActivityWatchSettings.this.readSetting((byte)29);
                        return;
                    }
                    case 27: {
                        WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_2 = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data[0];
                        ActivityWatchSettings.this.readSetting((byte)28);
                        return;
                    }
                    case 26: {
                        WatchOp.watchSetting_gb.free_dive_surface_time_alarm_threshold_1 = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data[0];
                        ActivityWatchSettings.this.readSetting((byte)27);
                        return;
                    }
                    case 25: {
                        WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_3 = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data[0];
                        ActivityWatchSettings.this.readSetting((byte)26);
                        return;
                    }
                    case 24: {
                        WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_2 = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data[0];
                        ActivityWatchSettings.this.readSetting((byte)25);
                        return;
                    }
                    case 23: {
                        WatchOp.watchSetting_gb.free_dive_depth_alarm_threshold_1 = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data[0];
                        ActivityWatchSettings.this.readSetting((byte)24);
                        return;
                    }
                    case 22: {
                        WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_3 = ByteOp.get_Integer_from_2_bytes_unsigned(ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data, ByteOrder.LITTLE_ENDIAN);
                        ActivityWatchSettings.this.readSetting((byte)23);
                        return;
                    }
                    case 21: {
                        WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_2 = ByteOp.get_Integer_from_2_bytes_unsigned(ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data, ByteOrder.LITTLE_ENDIAN);
                        ActivityWatchSettings.this.readSetting((byte)22);
                        return;
                    }
                    case 20: {
                        WatchOp.watchSetting_gb.free_dive_time_alarm_threshold_1 = ByteOp.get_Integer_from_2_bytes_unsigned(ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data, ByteOrder.LITTLE_ENDIAN);
                        ActivityWatchSettings.this.readSetting((byte)21);
                        return;
                    }
                    case 19: {
                        WatchOp.watchSetting_gb.free_dive_surface_time_alarm = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data[0];
                        ActivityWatchSettings.this.readSetting((byte)20);
                        return;
                    }
                    case 18: {
                        WatchOp.watchSetting_gb.free_dive_depth_alarm = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data[0];
                        ActivityWatchSettings.this.readSetting((byte)19);
                        return;
                    }
                    case 17: {
                        WatchOp.watchSetting_gb.free_dive_time_alarm = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data[0];
                        ActivityWatchSettings.this.readSetting((byte)18);
                        return;
                    }
                    case 16: {
                        WatchOp.watchSetting_gb.G_sensor = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data[0];
                        ActivityWatchSettings.this.readSetting((byte)17);
                        return;
                    }
                    case 15: {
                        WatchOp.watchSetting_gb.scuba_dive_log_stop_time = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data[0];
                        ActivityWatchSettings.this.readSetting((byte)16);
                        return;
                    }
                    case 14: {
                        WatchOp.watchSetting_gb.scuba_dive_log_start_depth = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data[0];
                        ActivityWatchSettings.this.readSetting((byte)15);
                        return;
                    }
                    case 13: {
                        WatchOp.watchSetting_gb.scuba_dive_log_sampling_rate = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data[0];
                        ActivityWatchSettings.this.readSetting((byte)14);
                        return;
                    }
                    case 12: {
                        WatchOp.watchSetting_gb.scuba_dive_time_alarm_threshold = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data[0];
                        ActivityWatchSettings.this.readSetting((byte)13);
                        return;
                    }
                    case 11: {
                        WatchOp.watchSetting_gb.scuba_dive_depth_alarm_threshold = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data[0];
                        ActivityWatchSettings.this.readSetting((byte)12);
                        return;
                    }
                    case 10: {
                        WatchOp.watchSetting_gb.scuba_dive_time_alarm = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data[0];
                        ActivityWatchSettings.this.readSetting((byte)11);
                        return;
                    }
                    case 9: {
                        WatchOp.watchSetting_gb.scuba_dive_depth_alarm = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data[0];
                        ActivityWatchSettings.this.readSetting((byte)10);
                        return;
                    }
                    case 8: {
                        WatchOp.watchSetting_gb.safety_factor = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data[0];
                        ActivityWatchSettings.this.readSetting((byte)9);
                        return;
                    }
                    case 7: {
                        WatchOp.watchSetting_gb.PPO2 = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data[0];
                        ActivityWatchSettings.this.readSetting((byte)8);
                        return;
                    }
                    case 6: {
                        WatchOp.watchSetting_gb.O2_ratio = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data[0];
                        ActivityWatchSettings.this.readSetting((byte)7);
                        return;
                    }
                    case 5: {
                        WatchOp.watchSetting_gb.auto_dive_type = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data[0];
                        ActivityWatchSettings.this.readSetting((byte)6);
                        return;
                    }
                    case 4: {
                        WatchOp.watchSetting_gb.time_format = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data[0];
                        ActivityWatchSettings.this.readSetting((byte)5);
                        return;
                    }
                    case 3: {
                        WatchOp.watchSetting_gb.hour = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data[0];
                        WatchOp.watchSetting_gb.minute = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data[1];
                        ActivityWatchSettings.this.readSetting((byte)4);
                        return;
                    }
                    case 2: {
                        WatchOp.watchSetting_gb.date_format = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data[0];
                        ActivityWatchSettings.this.readSetting((byte)3);
                        return;
                    }
                    case 1: {
                        WatchOp.watchSetting_gb.year = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data[0] + 2000;
                        WatchOp.watchSetting_gb.month = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data[1];
                        WatchOp.watchSetting_gb.day = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).data[2];
                        ActivityWatchSettings.this.readSetting((byte)2);
                        return;
                    }
                    case -1: 
                }
                UiOp.toast_message(ActivityWatchSettings.this.context, 2131689779);
                ActivityWatchSettings.this.exit_activity();
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

    private void sync_watch_settings() {
        byte[] arrby = new byte[1];
        WatchOp.writeSetting(WatchOp.watch_connected.mac_address, (byte)39, arrby, 1);
    }

    public void exit_activity() {
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

    public boolean onCreateOptionsMenu(Menu menu2) {
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
        this.sync_watch_settings();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.init_resume();
    }

    public class ReceiverResponse
    extends BroadcastReceiver {
        public void onReceive(Context context, Intent object) {
            ActivityWatchSettings.this.timerResetable.pause();
            if (ActivityWatchSettings.this.isActivityPaused) {
                return;
            }
            object = object.getStringExtra("address");
            Object object2 = WatchOp.bleOp.get_Characteristic_received((String)object);
            ActivityWatchSettings.this.watch_reply = WatchOp.handle_received_charteristics((String)object, (BluetoothGattCharacteristic)object2);
            if (ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).error == WatchOp.WATCH_REPLY.Error.NO_ERROR) {
                ActivityWatchSettings.this.handler.obtainMessage((int)ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).sub_command, object).sendToTarget();
                return;
            }
            object = ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).raw_response != null ? StringOp.getHexFromBytes(ActivityWatchSettings.access$200((ActivityWatchSettings)ActivityWatchSettings.this).raw_response, false, " ") : "null";
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Watch response:");
            ((StringBuilder)object2).append((String)object);
            UiOp.toast_message(context, ((StringBuilder)object2).toString(), true);
        }
    }

    public class ReceiverTimeout
    extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            WatchOp.isTimeout = true;
            UiOp.toast_message(context, 2131689779);
            ActivityWatchSettings.this.exit_activity();
        }
    }

}

