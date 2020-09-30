package com.syntak.library;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.content.Intent;
import android.os.Build.VERSION;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class BluetoothLeSession {
   public static final String ACTION_DATA_AVAILABLE = "ACTION_DATA_AVAILABLE";
   public static final String ACTION_GATT_CONNECTED = "ACTION_GATT_CONNECTED";
   public static final String ACTION_GATT_DISCONNECTED = "ACTION_GATT_DISCONNECTED";
   public static final String ACTION_GATT_SERVICES_DISCOVERED = "ACTION_GATT_SERVICES_DISCOVERED";
   public static final String EXTRA_DATA = "EXTRA_DATA";
   private static final int STATE_CONNECTED = 2;
   private static final int STATE_CONNECTING = 1;
   private static final int STATE_DISCONNECTED = 0;
   private final int BE_DISCOVERABLE = 4;
   private final int CHECK_ENABLED = 3;
   private final int CHECK_SUPPORTED = 1;
   private final int CONNECT = 7;
   private final int ENABLE = 2;
   private final int IDLE = 0;
   private final int SCANNING = 6;
   private final int START_DISCOVERY = 5;
   private Activity activity;
   private BluetoothAdapter adapter = null;
   private BluetoothGatt bluetoothGatt;
   private List<BluetoothGattService> bluetoothGattServices;
   int delay_start = 0;
   private BluetoothDevice device_client;
   private String device_name_client = null;
   private String device_name_server = null;
   private BluetoothDevice device_server;
   private boolean flag_bluetooth_enabled = false;
   private boolean flag_bluetooth_supported = false;
   private boolean flag_server = false;
   private BluetoothGattCallback gattCallback = null;
   private ArrayList<BluetoothDevice> list_devices = null;
   private int message = 0;
   private BluetoothGattCharacteristic received_characteristic = null;
   LeScanCallback scanCallback = null;
   int state = 0;
   private Thread thread = null;
   private int time_discoverable = 120;
   private int time_scanning = 10;
   long timeout = 0L;
   private Timer timer = null;
   private TimerTask timer_task = null;
   int update_interval = 500;
   private UUID uuid_client = null;
   private UUID uuid_peer = null;
   private UUID uuid_server = null;

   public BluetoothLeSession(Activity var1, UUID var2, String var3) {
      this.activity = var1;
      this.uuid_server = var2;
      this.device_name_server = var3;
      this.flag_server = true;
      this.get_adapter();
      this.message = 1;
   }

   public BluetoothLeSession(Activity var1, UUID var2, String var3, int var4) {
      this.activity = var1;
      this.uuid_server = var2;
      this.device_name_server = var3;
      if (var4 > 0) {
         this.time_scanning = var4;
      }

      this.flag_server = false;
      this.get_adapter();
      this.message = 1;
   }

   private void broadcastUpdate(String var1) {
      Intent var2 = new Intent(var1);
      this.activity.sendBroadcast(var2);
      this.OnActionReceived(var1);
   }

   private void get_adapter() {
      BluetoothManager var1 = (BluetoothManager)this.activity.getSystemService("bluetooth");
      if (VERSION.SDK_INT >= 18) {
         this.adapter = var1.getAdapter();
      } else {
         this.adapter = BluetoothAdapter.getDefaultAdapter();
      }

   }

   private void looping() {
      switch(this.message) {
      case 1:
         this.checkBluetoothSupported();
         break;
      case 2:
         this.message = 0;
         this.enableBluetoothLE();
         break;
      case 3:
         this.checkBluetoothEnabled();
         break;
      case 4:
         this.message = 0;
         break;
      case 5:
         this.message = 0;
         this.startScanning();
         break;
      case 6:
         if (TimeOp.getNow() > this.timeout) {
            this.stopScanning();
            this.message = 0;
         }
         break;
      case 7:
         this.message = 0;
         this.connect();
      }

   }

   private void start_timer() {
      this.timer = null;
      this.timer = new Timer();
      TimerTask var1 = new TimerTask() {
         public void run() {
            BluetoothLeSession.this.looping();
         }
      };
      this.timer_task = var1;
      this.timer.scheduleAtFixedRate(var1, (long)this.delay_start, (long)this.update_interval);
   }

   public void OnActionReceived(String var1) {
   }

   public void OnBluetoothEnabled(boolean var1) {
   }

   public void OnBluetoothLeScanningStarted() {
   }

   public void OnBluetoothSupported(boolean var1) {
   }

   public void OnDataReceived(byte[] var1, int var2) {
   }

   public void OnError(String var1) {
   }

   public void OnServerDeviceFound(BluetoothDevice var1) {
   }

   public void OnServicesFound(List<BluetoothGattService> var1) {
   }

   public void OnSocketReady() {
   }

   public void OnStringReceived(String var1) {
   }

   public void OnStringSent() {
   }

   public void checkBluetoothEnabled() {
      boolean var1 = this.adapter.isEnabled();
      this.flag_bluetooth_enabled = var1;
      this.OnBluetoothEnabled(var1);
      if (this.flag_bluetooth_enabled) {
         if (this.flag_server) {
            this.message = 7;
         } else {
            this.message = 0;
            this.startScanning();
         }
      }

   }

   public void checkBluetoothSupported() {
      if (this.adapter == null) {
         this.flag_bluetooth_supported = false;
      } else {
         this.flag_bluetooth_supported = true;
         this.OnBluetoothSupported(true);
         this.checkBluetoothEnabled();
         if (!this.flag_bluetooth_enabled) {
            this.message = 2;
         }
      }

   }

   public void connect() {
      BluetoothGattCallback var1 = new BluetoothGattCallback() {
         public void onCharacteristicChanged(BluetoothGatt var1, BluetoothGattCharacteristic var2) {
            BluetoothLeSession.this.broadcastUpdate("ACTION_DATA_AVAILABLE");
            BluetoothLeSession.this.received_characteristic = var2;
         }

         public void onCharacteristicRead(BluetoothGatt var1, BluetoothGattCharacteristic var2, int var3) {
            if (var3 == 0) {
               BluetoothLeSession.this.broadcastUpdate("ACTION_DATA_AVAILABLE");
               BluetoothLeSession.this.received_characteristic = var2;
            }

         }

         public void onConnectionStateChange(BluetoothGatt var1, int var2, int var3) {
            if (var3 == 2) {
               BluetoothLeSession.this.state = 2;
               BluetoothLeSession.this.broadcastUpdate("ACTION_GATT_CONNECTED");
               BluetoothLeSession.this.bluetoothGatt.discoverServices();
            } else if (var3 == 0) {
               BluetoothLeSession.this.state = 0;
               BluetoothLeSession.this.broadcastUpdate("ACTION_GATT_DISCONNECTED");
            }

         }

         public void onServicesDiscovered(BluetoothGatt var1, int var2) {
            if (var2 == 0) {
               BluetoothLeSession.this.broadcastUpdate("ACTION_GATT_SERVICES_DISCOVERED");
               BluetoothLeSession var3 = BluetoothLeSession.this;
               var3.bluetoothGattServices = var3.bluetoothGatt.getServices();
               var3 = BluetoothLeSession.this;
               var3.OnServicesFound(var3.bluetoothGattServices);
            }

         }
      };
      this.gattCallback = var1;
      if (!this.flag_server) {
         this.bluetoothGatt = this.device_server.connectGatt(this.activity, false, var1);
      }

   }

   public void enableBluetoothLE() {
      Intent var1 = new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE");
      this.activity.startActivity(var1);
      this.message = 3;
   }

   public boolean isBluetoothEnabled() {
      return this.flag_bluetooth_enabled;
   }

   public boolean isBluetoothSupported() {
      return this.flag_bluetooth_supported;
   }

   public void requestCharacteristic(BluetoothGattCharacteristic var1) {
      if (this.adapter != null) {
         BluetoothGatt var2 = this.bluetoothGatt;
         if (var2 != null) {
            var2.readCharacteristic(var1);
         }
      }

   }

   public void start() {
      this.list_devices = new ArrayList();
      if (this.thread == null) {
         Thread var1 = new Thread(new Runnable() {
            public void run() {
               BluetoothLeSession.this.start_timer();
            }
         });
         this.thread = var1;
         var1.setPriority(10);
         this.thread.start();
      }

   }

   public void startScanning() {
      LeScanCallback var1 = new LeScanCallback() {
         public void onLeScan(BluetoothDevice var1, int var2, byte[] var3) {
            BluetoothLeSession.this.list_devices.add(var1);
            if (!BluetoothLeSession.this.flag_server && var1.getName().equals(BluetoothLeSession.this.device_name_server)) {
               BluetoothLeSession.this.device_server = var1;
               BluetoothLeSession.this.OnServerDeviceFound(var1);
            }

         }
      };
      this.scanCallback = var1;
      this.adapter.startLeScan(var1);
      this.message = 6;
      this.timeout = TimeOp.getNow() + (long)(this.time_scanning * 1000);
      this.OnBluetoothLeScanningStarted();
   }

   public void stop() {
      BluetoothAdapter var1 = this.adapter;
      if (var1 != null) {
         var1.disable();
      }

      Timer var2 = this.timer;
      if (var2 != null) {
         var2.cancel();
         this.timer = null;
      }

      this.timer_task = null;
      this.thread = null;
      BluetoothGatt var3 = this.bluetoothGatt;
      if (var3 != null) {
         var3.close();
         this.bluetoothGatt = null;
      }

   }

   public void stopScanning() {
      this.adapter.stopLeScan(this.scanCallback);
   }

   public void switchNotification(BluetoothGattCharacteristic var1, boolean var2) {
      this.bluetoothGatt.setCharacteristicNotification(var1, var2);
      if (!this.flag_server) {
         this.uuid_peer = this.uuid_server;
      } else {
         this.uuid_peer = this.uuid_client;
      }

      BluetoothGattDescriptor var3 = var1.getDescriptor(this.uuid_peer);
      if (var2) {
         var3.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
      } else {
         var3.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
      }

      this.bluetoothGatt.writeDescriptor(var3);
   }

   public void writeCharacteristic(BluetoothGattCharacteristic var1) {
      if (this.adapter != null) {
         BluetoothGatt var2 = this.bluetoothGatt;
         if (var2 != null) {
            var2.writeCharacteristic(var1);
         }
      }

   }
}
