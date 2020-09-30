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
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Build.VERSION;
import android.util.Log;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class BleOp {
   public static final String ACTION_BLE_ENABLE = "ACTION_BLE_ENABLE";
   public static final String ACTION_BLE_SCAN = "ACTION_BLE_SCAN";
   public static final String ACTION_BLE_SCAN_DONE = "ACTION_BLE_SCAN_DONE";
   public static final String ACTION_BLE_SUPPORT = "ACTION_BLE_SUPPORT";
   public static final String ACTION_DATA_AVAILABLE = "ACTION_DATA_AVAILABLE";
   public static final String ACTION_GATT_CHARACTERISTICS_READ = "ACTION_GATT_CHARACTERISTICS_READ";
   public static final String ACTION_GATT_CONNECTED = "ACTION_GATT_CONNECTED";
   public static final String ACTION_GATT_CONNECTION_CHANGED = "ACTION_GATT_CONNECTION_CHANGED";
   public static final String ACTION_GATT_DISCONNECTED = "ACTION_GATT_DISCONNECTED";
   public static final String ACTION_GATT_SERVICES_DISCOVERED = "ACTION_GATT_SERVICES_DISCOVERED";
   public static final String ACTION_MTU_CHANGED = "ACTION_MTU_CHANGED";
   protected static final UUID CHARACTERISTIC_UPDATE_NOTIFICATION_DESCRIPTOR_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
   public static final String EXTRA_DATA = "EXTRA_DATA";
   public static final int MAX_MTU_SIZE = 512;
   public static final int REQUEST_CODE_PERMISSION_COARSE_LOCATION = 10;
   public static final String TAG = "BleOp";
   public static final String TAG_MAC_ADDRESS = "address";
   public static final String TAG_NAME = "name";
   public static final String TAG_RSSI = "rssi";
   public static final String TAG_SCAN_RECORD = "scan_record";
   public static final String TAG_STATE = "state";
   public static final int TASK_BOND = 80;
   public static final int TASK_CHECK_ENABLED = 20;
   public static final int TASK_CHECK_SUPPORTED = 10;
   public static final int TASK_CONNECTING = 60;
   public static final int TASK_DEBOND = 90;
   public static final int TASK_DISCONNECT = 100;
   public static final int TASK_ENABLING = 30;
   public static final int TASK_NONE = 0;
   public static final int TASK_SEND = 70;
   public static final int TASK_START_SCANNING = 40;
   public static final int TASK_STOP_SCANNING = 50;
   public static final int TIME_ADJUST_REASON_CHANGE_DST = 8;
   public static final int TIME_ADJUST_REASON_CHANGE_TIME_ZONE = 4;
   public static final int TIME_ADJUST_REASON_EXTERNAL_TIME_REFERENCE_UPDATE = 2;
   public static final int TIME_ADJUST_REASON_MANUAL_TIME_UPDATE = 1;
   public static final String UUID_CHARACTERISTIC_CURRENT_TIME = "2A2B";
   public static final String UUID_CHARACTERISTIC_FIRMWARE_REVISION = "2A26";
   public static final String UUID_SERVICE_CURRENT_TIME = "1805";
   public static final String UUID_SERVICE_DEVICE_INFORMATION = "180A";
   private static boolean is_ble_supported = false;
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
   HashMap<String, BluetoothGatt> bluetoothGatts = null;
   private HashMap<String, BluetoothDevice> bonded_devices = null;
   private HashMap<String, BluetoothDevice> connected_devices = null;
   private Context context;
   private BluetoothDevice device_client;
   private String device_name_client = null;
   private String device_name_server = null;
   private BluetoothDevice device_server;
   private BluetoothDevice device_target;
   Runnable discoverServicesRunnable;
   private int first_task = -1;
   private boolean flag_broadcast = false;
   private boolean flag_connecting = false;
   private boolean flag_server = false;
   private BluetoothGattCallback gattCallback = null;
   public boolean isAutoConnect = false;
   public boolean isBleScan = true;
   private boolean isResponsed = false;
   private boolean is_bluetooth_enabled = false;
   private boolean is_bluetooth_supported = false;
   HashMap<String, BluetoothGattCharacteristic> last_received_characteristic = null;
   private List<BluetoothGattCharacteristic> list_characteristics = null;
   private List<BluetoothGattService> list_services = null;
   private int message = 0;
   private BluetoothGattCharacteristic received_characteristic = null;
   private BleOp.ReceiverScanResult receiverScanResult;
   LeScanCallback scanCallback = null;
   BluetoothDevice scanned_device;
   private HashMap<String, BluetoothDevice> scanned_devices = null;
   BleOp.CONNECTION_STATE state;
   private UUID[] target_device_with_service_uuids = null;
   private BleOp.ThreadLooper threadLooper = null;
   private int time_discoverable = 120;
   private int time_scanning = 30;
   private UUID uuid_client = null;
   private UUID uuid_peer = null;
   private UUID uuid_server = null;

   public BleOp(Activity var1, int var2, boolean var3) {
      this.state = BleOp.CONNECTION_STATE.DISCONNECTED;
      this.discoverServicesRunnable = null;
      this.activity = var1;
      this.context = var1;
      if (var2 > 0) {
         this.time_scanning = var2;
      }

      this.flag_broadcast = var3;
      this.flag_server = false;
      if (VERSION.SDK_INT >= 23) {
         var1.requestPermissions(new String[]{"android.permission.ACCESS_COARSE_LOCATION"}, 10);
      }

      this.start();
      this.get_Adapter();
      this.bluetoothGatts = new HashMap();
      this.last_received_characteristic = new HashMap();
   }

   private void broadcast_Device_scanned(String var1, String var2, int var3, byte[] var4) {
      Intent var5 = new Intent("ACTION_BLE_SCAN");
      var5.putExtra("name", var1);
      var5.putExtra("address", var2);
      var5.putExtra("rssi", var3);
      var5.putExtra("scan_record", var4);
      Activity var6 = this.activity;
      if (var6 != null) {
         var6.sendBroadcast(var5);
      }

   }

   private void broadcast_Response(String var1) {
      Intent var2 = new Intent(var1);
      Activity var3 = this.activity;
      if (var3 != null) {
         var3.sendBroadcast(var2);
      }

   }

   private void broadcast_Response(String var1, String var2) {
      Intent var4 = new Intent(var2);
      var4.putExtra("address", var1);
      Activity var3 = this.activity;
      if (var3 != null) {
         var3.sendBroadcast(var4);
      }

   }

   private void broadcast_Response(String var1, String var2, BleOp.CONNECTION_STATE var3) {
      Intent var5 = new Intent(var2);
      var5.putExtra("address", var1);
      var5.putExtra("state", var3);
      Activity var4 = this.activity;
      if (var4 != null) {
         var4.sendBroadcast(var5);
      }

   }

   public static UUID compose_UUID(String var0, String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append(var0);
      var2.append(var1);
      return UUID.fromString(var2.toString());
   }

   private void get_Adapter() {
      BluetoothManager var1 = (BluetoothManager)this.activity.getSystemService("bluetooth");
      if (VERSION.SDK_INT >= 18) {
         this.adapter = var1.getAdapter();
      } else {
         this.adapter = BluetoothAdapter.getDefaultAdapter();
      }

      boolean var2;
      if (this.adapter != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      this.is_bluetooth_supported = var2;
   }

   private BluetoothDevice get_Device(String var1) {
      BluetoothDevice var2 = (BluetoothDevice)this.scanned_devices.get(var1);
      BluetoothDevice var3 = var2;
      if (var2 == null) {
         HashMap var4 = this.bonded_devices;
         var3 = var2;
         if (var4 != null) {
            var3 = (BluetoothDevice)var4.get(var1);
         }
      }

      return var3;
   }

   private String get_DeviceName_from_ScanRecord(byte[] var1) {
      int var2 = 0;

      String var4;
      while(true) {
         if (var2 < var1.length) {
            byte var3 = var1[var2];
            if (var3 != 0) {
               if (var1[var2 + 1] == 9) {
                  var4 = ByteOp.get_String_from_BytesArray(var1, var2 + 2, var3);
                  break;
               }

               var2 += var3 + 1;
               continue;
            }
         }

         var4 = null;
         break;
      }

      return var4;
   }

   public static String get_DeviceOfUUID(UUID var0) {
      return var0.toString().substring(8).toUpperCase();
   }

   public static String get_FunctionOfUUID(UUID var0) {
      return var0.toString().substring(4, 8).toUpperCase();
   }

   public static boolean is_BLE_supported(Context var0) {
      boolean var1 = var0.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le");
      is_ble_supported = var1;
      return var1;
   }

   private void unbond(String var1) {
      BluetoothDevice var3 = this.get_Device(var1);
      if (var3.getBondState() != 10) {
         try {
            var3.getClass().getMethod("removeBond", (Class[])null).invoke(var3, (Object[])null);
         } catch (Exception var2) {
            Log.e("BleOp", var2.getMessage());
         }

      }
   }

   public void OnActionReceived(String var1) {
   }

   public void OnBluetoothDeviceDiscoveryStarted() {
   }

   public void OnBluetoothEnabled(boolean var1) {
   }

   public void OnBluetoothLeScanningStarted() {
   }

   public void OnBluetoothSupported(boolean var1) {
   }

   public void OnCharacteristicChanged(String var1, BluetoothGattCharacteristic var2) {
   }

   public void OnCharacteristicRead(String var1, BluetoothGattCharacteristic var2) {
   }

   public void OnConnectionStateChange(BleOp.CONNECTION_STATE var1) {
   }

   public void OnDataReceived(byte[] var1, int var2) {
   }

   public void OnDeviceFound(BluetoothDevice var1) {
   }

   public void OnDeviceFound(BluetoothDevice var1, int var2, byte[] var3) {
   }

   public void OnError(String var1) {
   }

   public void OnMTUChanged(String var1, int var2, int var3) {
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

   public void bond(String var1) {
      BluetoothDevice var3 = (BluetoothDevice)this.scanned_devices.get(var1);
      int var2 = var3.getBondState();
      if (var2 != 12 && var2 != 11) {
         var3.createBond();
      }

   }

   public void check_Bluetooth_enabled() {
      this.is_bluetooth_enabled = this.adapter.isEnabled();
      this.broadcast_Response("ACTION_BLE_ENABLE");
      this.OnBluetoothEnabled(this.is_bluetooth_enabled);
   }

   public void check_Bluetooth_supported() {
      this.broadcast_Response("ACTION_BLE_SUPPORT");
      this.OnBluetoothSupported(this.is_bluetooth_supported);
   }

   public void connect(String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append("try connecting to ");
      var2.append(var1);
      Log.d("BleOp", var2.toString());
      this.gattCallback = new BluetoothGattCallback() {
         public void onCharacteristicChanged(BluetoothGatt var1, BluetoothGattCharacteristic var2) {
            BleOp.this.isResponsed = true;
            String var3 = var1.getDevice().getAddress();
            BleOp.this.last_received_characteristic.put(var3, var2);
            if (BleOp.this.flag_broadcast) {
               BleOp.this.broadcast_Response(var3, "ACTION_DATA_AVAILABLE");
            }

            BleOp.this.OnCharacteristicChanged(var3, var2);
            Log.d("BleOp", "onCharacteristicChanged");
         }

         public void onCharacteristicRead(BluetoothGatt var1, BluetoothGattCharacteristic var2, int var3) {
            if (var3 == 0) {
               BleOp.this.isResponsed = true;
               String var4 = var1.getDevice().getAddress();
               BleOp.this.last_received_characteristic.put(var4, var2);
               if (BleOp.this.flag_broadcast) {
                  BleOp.this.broadcast_Response(var4, "ACTION_DATA_AVAILABLE");
               }

               BleOp.this.OnCharacteristicRead(var4, var2);
            }

            Log.d("BleOp", "onCharacteristicRead");
         }

         public void onCharacteristicWrite(BluetoothGatt var1, BluetoothGattCharacteristic var2, int var3) {
            StringBuilder var4 = new StringBuilder();
            var4.append("onCharacteristicWrite ");
            var4.append(var3);
            Log.d("BleOp", var4.toString());
         }

         public void onConnectionStateChange(final BluetoothGatt var1, int var2, int var3) {
            BleOp.this.isResponsed = true;
            String var4 = var1.getDevice().getAddress();
            if (var2 == 0) {
               if (var3 == 2) {
                  BleOp.this.state = BleOp.CONNECTION_STATE.CONNECTED;
                  var1.discoverServices();
                  byte var8 = 0;
                  int var5 = var1.getDevice().getBondState();
                  final short var7;
                  if (var5 != 10 && var5 != 12) {
                     var7 = var8;
                     if (var5 == 11) {
                        var7 = 10000;
                        Log.i("BleOp", "waiting for bonding to complete");
                     }
                  } else {
                     var7 = var8;
                     if (VERSION.SDK_INT <= 24) {
                        var7 = var8;
                        if (var5 == 12) {
                           var7 = 1000;
                        }
                     }
                  }

                  BleOp.this.discoverServicesRunnable = new Runnable() {
                     public void run() {
                        Log.d("BleOp", String.format(Locale.ENGLISH, "discovering services of '%s' with delay of %d ms", var1.getDevice().getName(), Integer.valueOf(var7)));
                        if (!var1.discoverServices()) {
                           Log.e("BleOp", "discoverServices failed to start");
                        }

                        BleOp.this.discoverServicesRunnable = null;
                     }
                  };
                  BleOp.this.threadLooper.mHandler.postDelayed(BleOp.this.discoverServicesRunnable, (long)var7);
               } else if (var3 == 0) {
                  BleOp.this.state = BleOp.CONNECTION_STATE.DISCONNECTED;
                  var1.close();
               }
            } else {
               var1.close();
            }

            BleOp var6;
            if (BleOp.this.flag_broadcast) {
               var6 = BleOp.this;
               var6.broadcast_Response(var4, "ACTION_GATT_CONNECTION_CHANGED", var6.state);
            }

            var6 = BleOp.this;
            var6.OnConnectionStateChange(var6.state);
            Log.d("BleOp", "onConnectionStateChange");
         }

         public void onMtuChanged(BluetoothGatt var1, int var2, int var3) {
            BleOp.this.isResponsed = true;
            String var4 = var1.getDevice().getAddress();
            if (BleOp.this.flag_broadcast) {
               BleOp.this.broadcast_Response(var4, "ACTION_MTU_CHANGED");
            }

            BleOp.this.OnMTUChanged(var4, var2, var3);
            StringBuilder var5 = new StringBuilder();
            var5.append("onMtuChanged ");
            var5.append(var2);
            Log.d("BleOp", var5.toString());
         }

         public void onServicesDiscovered(BluetoothGatt var1, int var2) {
            BleOp.this.isResponsed = true;
            String var3 = var1.getDevice().getAddress();
            if (var2 == 0) {
               BleOp.this.list_services = var1.getServices();
               if (BleOp.this.flag_broadcast) {
                  BleOp.this.broadcast_Response(var3, "ACTION_GATT_SERVICES_DISCOVERED");
               }

               BleOp var4 = BleOp.this;
               var4.OnServicesFound(var4.list_services);
            }

            Log.d("BleOp", "onServicesDiscovered");
         }
      };
      BluetoothGatt var3;
      if (VERSION.SDK_INT >= 23) {
         var3 = this.get_Device(var1).connectGatt(this.context, this.isAutoConnect, this.gattCallback, 2);
      } else {
         var3 = this.get_Device(var1).connectGatt(this.context, this.isAutoConnect, this.gattCallback);
      }

      this.bluetoothGatts.put(var1, var3);
   }

   public void disconnect(String var1) {
      ((BluetoothGatt)this.bluetoothGatts.get(var1)).disconnect();
   }

   public void discover_Services(String var1) {
      ((BluetoothGatt)this.bluetoothGatts.get(var1)).discoverServices();
   }

   public void do_Task(int var1) {
      this.threadLooper.mHandler.obtainMessage(var1).sendToTarget();
   }

   public void do_Task(int var1, String var2) {
      this.threadLooper.mHandler.obtainMessage(var1, var2).sendToTarget();
   }

   public void enable_BluetoothLE() {
      Intent var1 = new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE");
      this.activity.startActivityForResult(var1, 30);
      this.message = 3;
   }

   public void enable_Notification(String var1, UUID var2, UUID var3) {
      BluetoothGatt var4 = (BluetoothGatt)this.bluetoothGatts.get(var1);
      BluetoothGattCharacteristic var5 = var4.getService(var2).getCharacteristic(var3);
      var4.setCharacteristicNotification(var5, true);
      Iterator var7 = var5.getDescriptors().iterator();

      while(var7.hasNext()) {
         BluetoothGattDescriptor var6 = (BluetoothGattDescriptor)var7.next();
         var6.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
         var4.writeDescriptor(var6);
      }

   }

   public BluetoothGattCharacteristic get_Characteristic_received(String var1) {
      return (BluetoothGattCharacteristic)this.last_received_characteristic.get(var1);
   }

   public List<BluetoothGattCharacteristic> get_Characteristics() {
      return this.list_characteristics;
   }

   public HashMap<String, BluetoothDevice> get_Devices_bond() {
      this.bonded_devices = new HashMap();
      Iterator var1 = this.adapter.getBondedDevices().iterator();

      while(var1.hasNext()) {
         BluetoothDevice var2 = (BluetoothDevice)var1.next();
         String var3 = var2.getAddress();
         this.bonded_devices.put(var3, var2);
      }

      return this.bonded_devices;
   }

   public HashMap<String, BluetoothDevice> get_Devices_connected() {
      this.connected_devices = new HashMap();
      Iterator var1 = ((BluetoothManager)this.activity.getSystemService("bluetooth")).getConnectedDevices(7).iterator();

      while(var1.hasNext()) {
         BluetoothDevice var2 = (BluetoothDevice)var1.next();
         if (var2.getType() == 2) {
            String var3 = var2.getAddress();
            this.connected_devices.put(var3, var2);
         }
      }

      return this.connected_devices;
   }

   public HashMap<String, BluetoothDevice> get_Devices_scanned() {
      return this.scanned_devices;
   }

   public List<BluetoothGattService> get_Services(String var1) {
      BluetoothGatt var2 = (BluetoothGatt)this.bluetoothGatts.get(var1);
      return var2 == null ? null : var2.getServices();
   }

   public boolean is_Bluetooth_enabled() {
      return this.is_bluetooth_enabled;
   }

   public boolean is_Bluetooth_supported() {
      return this.is_bluetooth_supported;
   }

   public boolean is_Device_bonded(String var1) {
      BluetoothDevice var3 = (BluetoothDevice)this.scanned_devices.get(var1);
      boolean var2 = false;
      if (var3 == null) {
         return false;
      } else {
         if (var3.getBondState() == 12) {
            var2 = true;
         }

         return var2;
      }
   }

   public boolean is_Device_connected(String var1) {
      this.get_Devices_connected();
      boolean var2 = this.connected_devices.containsKey(var1);
      StringBuilder var3;
      if (var2) {
         var3 = new StringBuilder();
         var3.append("connected to ");
         var3.append(var1);
         Log.d("BleOp", var3.toString());
      } else {
         var3 = new StringBuilder();
         var3.append("disconnected from ");
         var3.append(var1);
         Log.d("BleOp", var3.toString());
      }

      return var2;
   }

   public boolean is_Gatt_available(String var1) {
      return this.bluetoothGatts.containsKey(var1);
   }

   public void read_Characteristic(String var1, UUID var2, UUID var3) {
      BluetoothGatt var4 = (BluetoothGatt)this.bluetoothGatts.get(var1);
      BluetoothGattService var5 = var4.getService(var2);
      if (var5 != null) {
         BluetoothGattCharacteristic var6 = var5.getCharacteristic(var3);
         if (var6 != null) {
            var4.readCharacteristic(var6);
         }
      }

   }

   public void setAutoConnect(boolean var1) {
      this.isAutoConnect = var1;
   }

   public void setIsBleScan(boolean var1) {
      this.isBleScan = var1;
   }

   public BleOp setTargetDeviceWithServiceUuids(UUID[] var1) {
      this.target_device_with_service_uuids = var1;
      return this;
   }

   public void set_MTU(String var1, int var2) {
      ((BluetoothGatt)this.bluetoothGatts.get(var1)).requestMtu(var2);
   }

   public void start() {
      if (this.threadLooper == null) {
         this.threadLooper = new BleOp.ThreadLooper() {
         };
      }

      this.threadLooper.start();
   }

   public void start_scanning() {
      this.scanned_devices = new HashMap();
      if (this.isBleScan) {
         LeScanCallback var1 = new LeScanCallback() {
            public void onLeScan(BluetoothDevice var1, int var2, byte[] var3) {
               String var4 = var1.getName();
               String var5 = var4;
               if (var4 == null) {
                  var5 = BleOp.this.get_DeviceName_from_ScanRecord(var3);
               }

               String var6 = var1.getAddress();
               if (var6 != null && !BleOp.this.scanned_devices.containsKey(var6)) {
                  BleOp.this.scanned_devices.put(var1.getAddress(), var1);
                  if (!BleOp.this.flag_server && BleOp.this.device_name_server != null && var1.getName().equals(BleOp.this.device_name_server)) {
                     BleOp.this.device_server = var1;
                     BleOp.this.OnServerDeviceFound(var1);
                  }

                  var4 = var5;
                  if (var5 == null) {
                     var4 = var6;
                  }

                  BleOp.this.broadcast_Device_scanned(var4, var6, var2, var3);
                  BleOp.this.OnDeviceFound(var1, var2, var3);
               }

            }
         };
         this.scanCallback = var1;
         UUID[] var2 = this.target_device_with_service_uuids;
         if (var2 != null && var2.length != 0) {
            this.adapter.startLeScan(var2, var1);
         } else {
            this.adapter.startLeScan(this.scanCallback);
         }

         this.OnBluetoothLeScanningStarted();
      } else {
         if (this.adapter.isDiscovering()) {
            this.adapter.cancelDiscovery();
         }

         this.adapter.startDiscovery();
         this.receiverScanResult = new BleOp.ReceiverScanResult();
         IntentFilter var3 = new IntentFilter("android.bluetooth.device.action.FOUND");
         this.context.registerReceiver(this.receiverScanResult, var3);
      }

      this.message = 6;
      this.threadLooper.mHandler.postDelayed(new Runnable() {
         public void run() {
            if (BleOp.this.threadLooper != null && BleOp.this.threadLooper.mHandler != null) {
               BleOp.this.threadLooper.mHandler.obtainMessage(50).sendToTarget();
            }

            BleOp.this.broadcast_Response("ACTION_BLE_SCAN_DONE");
         }
      }, (long)this.time_scanning);
   }

   public void stop() {
      BluetoothAdapter var1 = this.adapter;
      if (var1 != null) {
         var1.disable();
      }

      HashMap var3 = this.bluetoothGatts;
      if (var3 != null) {
         Iterator var2 = var3.keySet().iterator();

         while(var2.hasNext()) {
            String var4 = (String)var2.next();
            ((BluetoothGatt)this.bluetoothGatts.get(var4)).close();
         }
      }

      this.threadLooper.mHandler = null;
      this.threadLooper = null;
   }

   public void stop_scanning() {
      if (this.isBleScan) {
         this.adapter.stopLeScan(this.scanCallback);
      } else {
         this.adapter.cancelDiscovery();
      }

   }

   public void write_Characteristic(String var1, UUID var2, UUID var3, int var4, int var5) {
      BluetoothGatt var6 = (BluetoothGatt)this.bluetoothGatts.get(var1);
      BluetoothGattService var7 = var6.getService(var2);
      if (var7 != null) {
         BluetoothGattCharacteristic var8 = var7.getCharacteristic(var3);
         if (var8 != null) {
            var8.setValue(var4, var5, 0);
            var6.writeCharacteristic(var8);
         }
      }

   }

   public void write_Characteristic(String var1, UUID var2, UUID var3, byte[] var4) {
      BluetoothGatt var5 = (BluetoothGatt)this.bluetoothGatts.get(var1);
      BluetoothGattService var6 = var5.getService(var2);
      if (var6 != null) {
         BluetoothGattCharacteristic var7 = var6.getCharacteristic(var3);
         if (var7 != null) {
            var7.setValue(var4);
            var5.writeCharacteristic(var7);
         }
      }

   }

   public static enum CONNECTION_STATE {
      CONNECTED,
      CONNECTING,
      DISCONNECTED;

      static {
         BleOp.CONNECTION_STATE var0 = new BleOp.CONNECTION_STATE("CONNECTED", 2);
         CONNECTED = var0;
      }
   }

   private class ReceiverScanResult extends BroadcastReceiver {
      private ReceiverScanResult() {
      }

      // $FF: synthetic method
      ReceiverScanResult(Object var2) {
         this();
      }

      public void onReceive(Context var1, Intent var2) {
         if ("android.bluetooth.device.action.FOUND".equals(var2.getAction())) {
            BluetoothDevice var3 = (BluetoothDevice)var2.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            String var4 = var3.getName();
            String var6 = var3.getAddress();
            String var5 = var4;
            if (var4 == null) {
               var5 = var6;
            }

            BleOp.this.broadcast_Device_scanned(var5, var6, 0, (byte[])null);
            BleOp.this.OnDeviceFound(var3);
         }

      }
   }

   class ThreadLooper extends Thread {
      public Handler mHandler = null;

      public void run() {
         Looper.prepare();
         this.mHandler = new Handler() {
            public void handleMessage(Message var1) {
               int var2 = var1.what;
               if (var2 != 10) {
                  if (var2 != 20) {
                     if (var2 != 30) {
                        if (var2 != 40) {
                           if (var2 != 50) {
                              if (var2 != 60) {
                                 if (var2 != 80) {
                                    if (var2 == 100) {
                                       BleOp.this.disconnect((String)var1.obj);
                                    }
                                 } else {
                                    BleOp.this.bond((String)var1.obj);
                                 }
                              } else {
                                 BleOp.this.connect((String)var1.obj);
                              }
                           } else {
                              BleOp.this.stop_scanning();
                           }
                        } else {
                           BleOp.this.start_scanning();
                        }
                     } else {
                        BleOp.this.enable_BluetoothLE();
                     }
                  } else {
                     BleOp.this.check_Bluetooth_enabled();
                  }
               } else {
                  BleOp.this.check_Bluetooth_supported();
               }

            }
         };
         Looper.loop();
      }
   }
}
