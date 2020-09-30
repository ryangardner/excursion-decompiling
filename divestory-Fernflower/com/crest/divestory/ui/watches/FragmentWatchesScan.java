package com.crest.divestory.ui.watches;

import android.app.Activity;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.crest.divestory.DataStruct;
import com.crest.divestory.WatchOp;
import com.syntak.library.UiOp;
import java.util.HashMap;

public class FragmentWatchesScan extends Fragment {
   private static final String ARG_ACTION = "action";
   private static final String ARG_COLUMN_COUNT = "column-count";
   private static boolean flag_ble_enable_checked;
   private WatchOp.ACTION action;
   private Activity activity;
   private AdapterWatchesScannedListRecyclerView adapter = null;
   private Context context;
   Handler handler = null;
   private int mColumnCount = 1;
   private FragmentWatchesScan.OnWatchAddFragmentInteractionListener mListener;
   private DataStruct.MyWatch myWatch = null;
   FragmentWatchesScan.ReceiverEnable receiverEnable = null;
   FragmentWatchesScan.ReceiverResponse receiverResponse = null;
   FragmentWatchesScan.ReceiverScan receiverScan = null;
   FragmentWatchesScan.ReceiverScanDone receiverScanDone = null;
   FragmentWatchesScan.ReceiverServices receiverServices = null;
   FragmentWatchesScan.ReceiverSupport receiverSupport = null;
   private TextView warning;
   private WatchOp.WATCH_REPLY watch_reply;

   private void exit_fragment() {
      this.getActivity().getFragmentManager().popBackStack();
   }

   public static FragmentWatchesScan newInstance(WatchOp.ACTION var0) {
      FragmentWatchesScan var1 = new FragmentWatchesScan();
      Bundle var2 = new Bundle();
      var2.putSerializable("action", var0);
      var1.setArguments(var2);
      return var1;
   }

   private void start_handler() {
      this.handler = new Handler() {
         public void handleMessage(Message var1) {
            WatchOp.TASK var2 = WatchOp.TASK.values()[var1.what];
            int var3 = null.$SwitchMap$com$crest$divestory$WatchOp$TASK[var2.ordinal()];
            if (var3 != 1) {
               if (var3 != 2) {
                  if (var3 != 3) {
                     if (var3 != 4) {
                        if (var3 == 5) {
                           UiOp.toast_message(FragmentWatchesScan.this.context, FragmentWatchesScan.this.getResources().getString((Integer)var1.obj), true);
                        }
                     } else {
                        FragmentWatchesScan.this.exit_fragment();
                     }
                  } else {
                     WatchOp.readInfo((String)var1.obj, (byte)2);
                  }
               } else {
                  String var4 = (String)var1.obj;
                  String var6 = ((DataStruct.BleDevice)WatchOp.devices_scanned_map.get(var4)).serial_number;
                  if (WatchOp.watches_map.containsKey(var6)) {
                     WatchOp.devices_scanned.delete(var4);
                     WatchOp.devices_scanned_map.remove(var4);
                  } else {
                     WatchOp.readInfo(var4, (byte)1);
                  }
               }
            } else {
               Runnable var5 = new Runnable((String)var1.obj) {
                  // $FF: synthetic field
                  final String val$addr;

                  {
                     this.val$addr = var2;
                  }

                  public void run() {
                     WatchOp.readInfo(this.val$addr, (byte)3);
                  }
               };
               FragmentWatchesScan.this.handler.postDelayed(var5, 500L);
            }

         }
      };
   }

   private void start_receivers() {
      FragmentWatchesScan.ReceiverSupport var1 = new FragmentWatchesScan.ReceiverSupport();
      this.receiverSupport = var1;
      this.activity.registerReceiver(var1, new IntentFilter("ACTION_BLE_SUPPORT"));
      FragmentWatchesScan.ReceiverEnable var2 = new FragmentWatchesScan.ReceiverEnable();
      this.receiverEnable = var2;
      this.activity.registerReceiver(var2, new IntentFilter("ACTION_BLE_ENABLE"));
      FragmentWatchesScan.ReceiverScan var3 = new FragmentWatchesScan.ReceiverScan();
      this.receiverScan = var3;
      this.activity.registerReceiver(var3, new IntentFilter("ACTION_BLE_SCAN"));
      FragmentWatchesScan.ReceiverScanDone var4 = new FragmentWatchesScan.ReceiverScanDone();
      this.receiverScanDone = var4;
      this.activity.registerReceiver(var4, new IntentFilter("ACTION_BLE_SCAN"));
      FragmentWatchesScan.ReceiverServices var5 = new FragmentWatchesScan.ReceiverServices();
      this.receiverServices = var5;
      this.activity.registerReceiver(var5, new IntentFilter("ACTION_GATT_SERVICES_DISCOVERED"));
      FragmentWatchesScan.ReceiverResponse var6 = new FragmentWatchesScan.ReceiverResponse();
      this.receiverResponse = var6;
      this.activity.registerReceiver(var6, new IntentFilter("ACTION_DATA_AVAILABLE"));
   }

   private void stop_receivers() {
      FragmentWatchesScan.ReceiverSupport var1 = this.receiverSupport;
      if (var1 != null) {
         this.activity.unregisterReceiver(var1);
      }

      FragmentWatchesScan.ReceiverEnable var2 = this.receiverEnable;
      if (var2 != null) {
         this.activity.unregisterReceiver(var2);
      }

      FragmentWatchesScan.ReceiverScan var3 = this.receiverScan;
      if (var3 != null) {
         this.activity.unregisterReceiver(var3);
      }

      FragmentWatchesScan.ReceiverScanDone var4 = this.receiverScanDone;
      if (var4 != null) {
         this.activity.unregisterReceiver(var4);
      }

      FragmentWatchesScan.ReceiverServices var5 = this.receiverServices;
      if (var5 != null) {
         this.activity.unregisterReceiver(var5);
      }

      FragmentWatchesScan.ReceiverResponse var6 = this.receiverResponse;
      if (var6 != null) {
         this.activity.unregisterReceiver(var6);
      }

   }

   public void onAttach(Context var1) {
      super.onAttach(var1);
      if (var1 instanceof FragmentWatchesScan.OnWatchAddFragmentInteractionListener) {
         this.mListener = (FragmentWatchesScan.OnWatchAddFragmentInteractionListener)var1;
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append(var1.toString());
         var2.append(" must implement OnListFragmentInteractionListener");
         throw new RuntimeException(var2.toString());
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      if (this.getArguments() != null) {
         this.action = (WatchOp.ACTION)this.getArguments().getSerializable("action");
      }

      this.activity = this.getActivity();
      this.context = this.getActivity();
      WatchOp.devices_scanned = new DataStruct.BleDevices();
      WatchOp.devices_scanned_map = new HashMap();
      this.start_handler();
      this.start_receivers();
      WatchOp.open_ble(this.activity);
      this.handler.postDelayed(new Runnable() {
         public void run() {
            WatchOp.bleOp.do_Task(10);
         }
      }, 500L);
   }

   public View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
      View var5 = var1.inflate(2131427397, var2, false);
      this.warning = (TextView)var5.findViewById(2131231445);
      if (WatchOp.devices_scanned != null && WatchOp.devices_scanned.length() != 0) {
         this.warning.setText("");
      } else {
         this.warning.setText(2131689746);
      }

      if (var5 instanceof RecyclerView) {
         var5.getContext();
         RecyclerView var4 = (RecyclerView)var5;
         if (this.adapter == null) {
            this.adapter = new AdapterWatchesScannedListRecyclerView(this.getActivity(), WatchOp.devices_scanned.list, this.mListener);
         }

         var4.setAdapter(this.adapter);
      }

      return var5;
   }

   public void onDestroy() {
      super.onDestroy();
      WatchOp.devices_scanned_map = null;
      WatchOp.devices_scanned = null;
      this.stop_receivers();
      this.handler = null;
   }

   public void onDestroyView() {
      super.onDestroyView();
   }

   public void onDetach() {
      super.onDetach();
      this.mListener = null;
   }

   public void setListenToWatchList(FragmentWatchesScan.OnWatchAddFragmentInteractionListener var1) {
      this.mListener = var1;
   }

   public interface OnWatchAddFragmentInteractionListener {
      void onWatchAddFragmentInteraction(String var1);
   }

   public class ReceiverEnable extends BroadcastReceiver {
      public void onReceive(Context var1, Intent var2) {
         if (WatchOp.bleOp.is_Bluetooth_enabled()) {
            WatchOp.bleOp.do_Task(40);
         } else if (!FragmentWatchesScan.flag_ble_enable_checked) {
            WatchOp.bleOp.do_Task(30);
            FragmentWatchesScan.flag_ble_enable_checked = true;
         } else {
            UiOp.toast_message(FragmentWatchesScan.this.getContext(), "Bluetooth permission must be granted", false);
            FragmentWatchesScan.this.exit_fragment();
         }

      }
   }

   public class ReceiverResponse extends BroadcastReceiver {
      public void onReceive(Context var1, Intent var2) {
         String var4 = var2.getStringExtra("address");
         BluetoothGattCharacteristic var3 = WatchOp.bleOp.get_Characteristic_received(var4);
         FragmentWatchesScan.this.watch_reply = WatchOp.handle_received_charteristics(var4, var3);
         FragmentWatchesScan.this.handler.obtainMessage(FragmentWatchesScan.this.watch_reply.sub_command, var4).sendToTarget();
      }
   }

   public class ReceiverScan extends BroadcastReceiver {
      public void onReceive(Context var1, Intent var2) {
         String var3 = var2.getStringExtra("name");
         String var4 = var2.getStringExtra("address");
         DataStruct.BleDevice var5 = new DataStruct.BleDevice(var4, var3, var2.getIntExtra("rssi", 0), false);
         WatchOp.devices_scanned.add(var5);
         WatchOp.devices_scanned_map.put(var4, var5);
         WatchOp.bleOp.do_Task(60, var4);
      }
   }

   public class ReceiverScanDone extends BroadcastReceiver {
      public void onReceive(Context var1, Intent var2) {
         if (FragmentWatchesScan.this.adapter != null) {
            FragmentWatchesScan.this.adapter.notifyDataSetChanged();
         }

      }
   }

   public class ReceiverServices extends BroadcastReceiver {
      public void onReceive(Context var1, Intent var2) {
         String var3 = var2.getStringExtra("address");
         WatchOp.bleOp.enable_Notification(var3, WatchOp.UUID_SERVICE, WatchOp.UUID_CHARACTERISTIC_SETTING);
         FragmentWatchesScan.this.handler.obtainMessage(WatchOp.TASK.ASK_SERIAL_NUMBER.ordinal(), var3).sendToTarget();
      }
   }

   public class ReceiverSupport extends BroadcastReceiver {
      public void onReceive(Context var1, Intent var2) {
         if (WatchOp.bleOp.is_Bluetooth_supported()) {
            WatchOp.bleOp.do_Task(20);
         } else {
            UiOp.toast_message(FragmentWatchesScan.this.getContext(), "Bluetooth LE not supported in this device", false);
            FragmentWatchesScan.this.exit_fragment();
         }

      }
   }
}
