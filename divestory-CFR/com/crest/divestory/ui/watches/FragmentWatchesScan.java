/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.FragmentManager
 *  android.bluetooth.BluetoothGattCharacteristic
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.TextView
 */
package com.crest.divestory.ui.watches;

import android.app.Activity;
import android.app.FragmentManager;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.crest.divestory.DataStruct;
import com.crest.divestory.WatchOp;
import com.crest.divestory.ui.watches.AdapterWatchesScannedListRecyclerView;
import com.syntak.library.BleOp;
import com.syntak.library.UiOp;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class FragmentWatchesScan
extends Fragment {
    private static final String ARG_ACTION = "action";
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static boolean flag_ble_enable_checked = false;
    private WatchOp.ACTION action;
    private Activity activity;
    private AdapterWatchesScannedListRecyclerView adapter = null;
    private Context context;
    Handler handler = null;
    private int mColumnCount = 1;
    private OnWatchAddFragmentInteractionListener mListener;
    private DataStruct.MyWatch myWatch = null;
    ReceiverEnable receiverEnable = null;
    ReceiverResponse receiverResponse = null;
    ReceiverScan receiverScan = null;
    ReceiverScanDone receiverScanDone = null;
    ReceiverServices receiverServices = null;
    ReceiverSupport receiverSupport = null;
    private TextView warning;
    private WatchOp.WATCH_REPLY watch_reply;

    static /* synthetic */ WatchOp.WATCH_REPLY access$400(FragmentWatchesScan fragmentWatchesScan) {
        return fragmentWatchesScan.watch_reply;
    }

    private void exit_fragment() {
        this.getActivity().getFragmentManager().popBackStack();
    }

    public static FragmentWatchesScan newInstance(WatchOp.ACTION aCTION) {
        FragmentWatchesScan fragmentWatchesScan = new FragmentWatchesScan();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_ACTION, (Serializable)((Object)aCTION));
        fragmentWatchesScan.setArguments(bundle);
        return fragmentWatchesScan;
    }

    private void start_handler() {
        this.handler = new Handler(){

            public void handleMessage(Message object) {
                Object object2 = WatchOp.TASK.values()[object.what];
                int n = 3.$SwitchMap$com$crest$divestory$WatchOp$TASK[object2.ordinal()];
                if (n == 1) {
                    object = new Runnable((String)object.obj){
                        final /* synthetic */ String val$addr;
                        {
                            this.val$addr = string2;
                        }

                        @Override
                        public void run() {
                            WatchOp.readInfo(this.val$addr, (byte)3);
                        }
                    };
                    FragmentWatchesScan.this.handler.postDelayed((Runnable)object, 500L);
                    return;
                }
                if (n != 2) {
                    if (n == 3) {
                        WatchOp.readInfo((String)object.obj, (byte)2);
                        return;
                    }
                    if (n == 4) {
                        FragmentWatchesScan.this.exit_fragment();
                        return;
                    }
                    if (n != 5) {
                        return;
                    }
                    UiOp.toast_message(FragmentWatchesScan.this.context, FragmentWatchesScan.this.getResources().getString(((Integer)object.obj).intValue()), true);
                    return;
                }
                object = (String)object.obj;
                object2 = WatchOp.devices_scanned_map.get((Object)object).serial_number;
                if (WatchOp.watches_map.containsKey(object2)) {
                    WatchOp.devices_scanned.delete((String)object);
                    WatchOp.devices_scanned_map.remove(object);
                    return;
                }
                WatchOp.readInfo((String)object, (byte)1);
            }

        };
    }

    private void start_receivers() {
        BroadcastReceiver broadcastReceiver = new ReceiverSupport();
        this.receiverSupport = broadcastReceiver;
        this.activity.registerReceiver(broadcastReceiver, new IntentFilter("ACTION_BLE_SUPPORT"));
        broadcastReceiver = new ReceiverEnable();
        this.receiverEnable = broadcastReceiver;
        this.activity.registerReceiver(broadcastReceiver, new IntentFilter("ACTION_BLE_ENABLE"));
        broadcastReceiver = new ReceiverScan();
        this.receiverScan = broadcastReceiver;
        this.activity.registerReceiver(broadcastReceiver, new IntentFilter("ACTION_BLE_SCAN"));
        broadcastReceiver = new ReceiverScanDone();
        this.receiverScanDone = broadcastReceiver;
        this.activity.registerReceiver(broadcastReceiver, new IntentFilter("ACTION_BLE_SCAN"));
        broadcastReceiver = new ReceiverServices();
        this.receiverServices = broadcastReceiver;
        this.activity.registerReceiver(broadcastReceiver, new IntentFilter("ACTION_GATT_SERVICES_DISCOVERED"));
        broadcastReceiver = new ReceiverResponse();
        this.receiverResponse = broadcastReceiver;
        this.activity.registerReceiver(broadcastReceiver, new IntentFilter("ACTION_DATA_AVAILABLE"));
    }

    private void stop_receivers() {
        BroadcastReceiver broadcastReceiver = this.receiverSupport;
        if (broadcastReceiver != null) {
            this.activity.unregisterReceiver(broadcastReceiver);
        }
        if ((broadcastReceiver = this.receiverEnable) != null) {
            this.activity.unregisterReceiver(broadcastReceiver);
        }
        if ((broadcastReceiver = this.receiverScan) != null) {
            this.activity.unregisterReceiver(broadcastReceiver);
        }
        if ((broadcastReceiver = this.receiverScanDone) != null) {
            this.activity.unregisterReceiver(broadcastReceiver);
        }
        if ((broadcastReceiver = this.receiverServices) != null) {
            this.activity.unregisterReceiver(broadcastReceiver);
        }
        if ((broadcastReceiver = this.receiverResponse) == null) return;
        this.activity.unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnWatchAddFragmentInteractionListener) {
            this.mListener = (OnWatchAddFragmentInteractionListener)context;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(context.toString());
        stringBuilder.append(" must implement OnListFragmentInteractionListener");
        throw new RuntimeException(stringBuilder.toString());
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (this.getArguments() != null) {
            this.action = (WatchOp.ACTION)((Object)this.getArguments().getSerializable(ARG_ACTION));
        }
        this.activity = this.getActivity();
        this.context = this.getActivity();
        WatchOp.devices_scanned = new DataStruct.BleDevices();
        WatchOp.devices_scanned_map = new HashMap();
        this.start_handler();
        this.start_receivers();
        WatchOp.open_ble(this.activity);
        this.handler.postDelayed(new Runnable(){

            @Override
            public void run() {
                WatchOp.bleOp.do_Task(10);
            }
        }, 500L);
    }

    @Override
    public View onCreateView(LayoutInflater object, ViewGroup viewGroup, Bundle bundle) {
        viewGroup = object.inflate(2131427397, viewGroup, false);
        this.warning = (TextView)viewGroup.findViewById(2131231445);
        if (WatchOp.devices_scanned != null && WatchOp.devices_scanned.length() != 0) {
            this.warning.setText((CharSequence)"");
        } else {
            this.warning.setText(2131689746);
        }
        if (!(viewGroup instanceof RecyclerView)) return viewGroup;
        viewGroup.getContext();
        object = (RecyclerView)viewGroup;
        if (this.adapter == null) {
            this.adapter = new AdapterWatchesScannedListRecyclerView((Context)this.getActivity(), WatchOp.devices_scanned.list, this.mListener);
        }
        ((RecyclerView)object).setAdapter(this.adapter);
        return viewGroup;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        WatchOp.devices_scanned_map = null;
        WatchOp.devices_scanned = null;
        this.stop_receivers();
        this.handler = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    public void setListenToWatchList(OnWatchAddFragmentInteractionListener onWatchAddFragmentInteractionListener) {
        this.mListener = onWatchAddFragmentInteractionListener;
    }

    public static interface OnWatchAddFragmentInteractionListener {
        public void onWatchAddFragmentInteraction(String var1);
    }

    public class ReceiverEnable
    extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            if (WatchOp.bleOp.is_Bluetooth_enabled()) {
                WatchOp.bleOp.do_Task(40);
                return;
            }
            if (!flag_ble_enable_checked) {
                WatchOp.bleOp.do_Task(30);
                flag_ble_enable_checked = true;
                return;
            }
            UiOp.toast_message(FragmentWatchesScan.this.getContext(), "Bluetooth permission must be granted", false);
            FragmentWatchesScan.this.exit_fragment();
        }
    }

    public class ReceiverResponse
    extends BroadcastReceiver {
        public void onReceive(Context context, Intent object) {
            object = object.getStringExtra("address");
            context = WatchOp.bleOp.get_Characteristic_received((String)object);
            FragmentWatchesScan.this.watch_reply = WatchOp.handle_received_charteristics((String)object, (BluetoothGattCharacteristic)context);
            FragmentWatchesScan.this.handler.obtainMessage((int)FragmentWatchesScan.access$400((FragmentWatchesScan)FragmentWatchesScan.this).sub_command, object).sendToTarget();
        }
    }

    public class ReceiverScan
    extends BroadcastReceiver {
        public void onReceive(Context object, Intent object2) {
            String string2 = object2.getStringExtra("name");
            object = object2.getStringExtra("address");
            object2 = new DataStruct.BleDevice((String)object, string2, object2.getIntExtra("rssi", 0), false);
            WatchOp.devices_scanned.add((DataStruct.BleDevice)object2);
            WatchOp.devices_scanned_map.put((String)object, (DataStruct.BleDevice)object2);
            WatchOp.bleOp.do_Task(60, (String)object);
        }
    }

    public class ReceiverScanDone
    extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            if (FragmentWatchesScan.this.adapter == null) return;
            FragmentWatchesScan.this.adapter.notifyDataSetChanged();
        }
    }

    public class ReceiverServices
    extends BroadcastReceiver {
        public void onReceive(Context object, Intent intent) {
            object = intent.getStringExtra("address");
            WatchOp.bleOp.enable_Notification((String)object, WatchOp.UUID_SERVICE, WatchOp.UUID_CHARACTERISTIC_SETTING);
            FragmentWatchesScan.this.handler.obtainMessage(WatchOp.TASK.ASK_SERIAL_NUMBER.ordinal(), object).sendToTarget();
        }
    }

    public class ReceiverSupport
    extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            if (WatchOp.bleOp.is_Bluetooth_supported()) {
                WatchOp.bleOp.do_Task(20);
                return;
            }
            UiOp.toast_message(FragmentWatchesScan.this.getContext(), "Bluetooth LE not supported in this device", false);
            FragmentWatchesScan.this.exit_fragment();
        }
    }

}

