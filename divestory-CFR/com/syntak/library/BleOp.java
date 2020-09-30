/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.bluetooth.BluetoothAdapter
 *  android.bluetooth.BluetoothAdapter$LeScanCallback
 *  android.bluetooth.BluetoothDevice
 *  android.bluetooth.BluetoothGatt
 *  android.bluetooth.BluetoothGattCallback
 *  android.bluetooth.BluetoothGattCharacteristic
 *  android.bluetooth.BluetoothGattDescriptor
 *  android.bluetooth.BluetoothGattService
 *  android.bluetooth.BluetoothManager
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.pm.PackageManager
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Parcelable
 *  android.util.Log
 */
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
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import com.syntak.library.ByteOp;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
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
    private final int BE_DISCOVERABLE;
    private final int CHECK_ENABLED;
    private final int CHECK_SUPPORTED;
    private final int CONNECT;
    private final int ENABLE;
    private final int IDLE;
    private final int SCANNING;
    private final int START_DISCOVERY;
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
    Runnable discoverServicesRunnable = null;
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
    private ReceiverScanResult receiverScanResult;
    BluetoothAdapter.LeScanCallback scanCallback = null;
    BluetoothDevice scanned_device;
    private HashMap<String, BluetoothDevice> scanned_devices = null;
    CONNECTION_STATE state = CONNECTION_STATE.DISCONNECTED;
    private UUID[] target_device_with_service_uuids = null;
    private ThreadLooper threadLooper = null;
    private int time_discoverable = 120;
    private int time_scanning = 30;
    private UUID uuid_client = null;
    private UUID uuid_peer = null;
    private UUID uuid_server = null;

    public BleOp(Activity activity, int n, boolean bl) {
        this.IDLE = 0;
        this.CHECK_SUPPORTED = 1;
        this.ENABLE = 2;
        this.CHECK_ENABLED = 3;
        this.BE_DISCOVERABLE = 4;
        this.START_DISCOVERY = 5;
        this.SCANNING = 6;
        this.CONNECT = 7;
        this.activity = activity;
        this.context = activity;
        if (n > 0) {
            this.time_scanning = n;
        }
        this.flag_broadcast = bl;
        this.flag_server = false;
        if (Build.VERSION.SDK_INT >= 23) {
            activity.requestPermissions(new String[]{"android.permission.ACCESS_COARSE_LOCATION"}, 10);
        }
        this.start();
        this.get_Adapter();
        this.bluetoothGatts = new HashMap();
        this.last_received_characteristic = new HashMap();
    }

    private void broadcast_Device_scanned(String string2, String string3, int n, byte[] arrby) {
        Intent intent = new Intent(ACTION_BLE_SCAN);
        intent.putExtra(TAG_NAME, string2);
        intent.putExtra(TAG_MAC_ADDRESS, string3);
        intent.putExtra(TAG_RSSI, n);
        intent.putExtra(TAG_SCAN_RECORD, arrby);
        string2 = this.activity;
        if (string2 == null) return;
        string2.sendBroadcast(intent);
    }

    private void broadcast_Response(String string2) {
        Intent intent = new Intent(string2);
        string2 = this.activity;
        if (string2 == null) return;
        string2.sendBroadcast(intent);
    }

    private void broadcast_Response(String string2, String string3) {
        string3 = new Intent(string3);
        string3.putExtra(TAG_MAC_ADDRESS, string2);
        string2 = this.activity;
        if (string2 == null) return;
        string2.sendBroadcast((Intent)string3);
    }

    private void broadcast_Response(String string2, String string3, CONNECTION_STATE cONNECTION_STATE) {
        string3 = new Intent(string3);
        string3.putExtra(TAG_MAC_ADDRESS, string2);
        string3.putExtra(TAG_STATE, (Serializable)((Object)cONNECTION_STATE));
        string2 = this.activity;
        if (string2 == null) return;
        string2.sendBroadcast((Intent)string3);
    }

    public static UUID compose_UUID(String string2, String string3) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(string3);
        return UUID.fromString(stringBuilder.toString());
    }

    private void get_Adapter() {
        BluetoothManager bluetoothManager = (BluetoothManager)this.activity.getSystemService("bluetooth");
        this.adapter = Build.VERSION.SDK_INT >= 18 ? bluetoothManager.getAdapter() : BluetoothAdapter.getDefaultAdapter();
        boolean bl = this.adapter != null;
        this.is_bluetooth_supported = bl;
    }

    private BluetoothDevice get_Device(String string2) {
        BluetoothDevice bluetoothDevice;
        BluetoothDevice bluetoothDevice2 = bluetoothDevice = this.scanned_devices.get(string2);
        if (bluetoothDevice != null) return bluetoothDevice2;
        HashMap<String, BluetoothDevice> hashMap = this.bonded_devices;
        bluetoothDevice2 = bluetoothDevice;
        if (hashMap == null) return bluetoothDevice2;
        return hashMap.get(string2);
    }

    private String get_DeviceName_from_ScanRecord(byte[] object) {
        int n = 0;
        while (n < ((byte[])object).length) {
            byte by = object[n];
            if (by == 0) return null;
            if (object[n + 1] == 9) {
                return ByteOp.get_String_from_BytesArray(object, n + 2, by);
            }
            n += by + 1;
        }
        return null;
    }

    public static String get_DeviceOfUUID(UUID uUID) {
        return uUID.toString().substring(8).toUpperCase();
    }

    public static String get_FunctionOfUUID(UUID uUID) {
        return uUID.toString().substring(4, 8).toUpperCase();
    }

    public static boolean is_BLE_supported(Context context) {
        boolean bl;
        is_ble_supported = bl = context.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le");
        return bl;
    }

    private void unbond(String string2) {
        if ((string2 = this.get_Device(string2)).getBondState() == 10) {
            return;
        }
        try {
            string2.getClass().getMethod("removeBond", null).invoke(string2, null);
            return;
        }
        catch (Exception exception) {
            Log.e((String)TAG, (String)exception.getMessage());
        }
    }

    public void OnActionReceived(String string2) {
    }

    public void OnBluetoothDeviceDiscoveryStarted() {
    }

    public void OnBluetoothEnabled(boolean bl) {
    }

    public void OnBluetoothLeScanningStarted() {
    }

    public void OnBluetoothSupported(boolean bl) {
    }

    public void OnCharacteristicChanged(String string2, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
    }

    public void OnCharacteristicRead(String string2, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
    }

    public void OnConnectionStateChange(CONNECTION_STATE cONNECTION_STATE) {
    }

    public void OnDataReceived(byte[] arrby, int n) {
    }

    public void OnDeviceFound(BluetoothDevice bluetoothDevice) {
    }

    public void OnDeviceFound(BluetoothDevice bluetoothDevice, int n, byte[] arrby) {
    }

    public void OnError(String string2) {
    }

    public void OnMTUChanged(String string2, int n, int n2) {
    }

    public void OnServerDeviceFound(BluetoothDevice bluetoothDevice) {
    }

    public void OnServicesFound(List<BluetoothGattService> list) {
    }

    public void OnSocketReady() {
    }

    public void OnStringReceived(String string2) {
    }

    public void OnStringSent() {
    }

    public void bond(String string2) {
        int n = (string2 = this.scanned_devices.get(string2)).getBondState();
        if (n == 12) return;
        if (n == 11) {
            return;
        }
        string2.createBond();
    }

    public void check_Bluetooth_enabled() {
        this.is_bluetooth_enabled = this.adapter.isEnabled();
        this.broadcast_Response(ACTION_BLE_ENABLE);
        this.OnBluetoothEnabled(this.is_bluetooth_enabled);
    }

    public void check_Bluetooth_supported() {
        this.broadcast_Response(ACTION_BLE_SUPPORT);
        this.OnBluetoothSupported(this.is_bluetooth_supported);
    }

    public void connect(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("try connecting to ");
        stringBuilder.append(string2);
        Log.d((String)TAG, (String)stringBuilder.toString());
        this.gattCallback = new BluetoothGattCallback(){

            public void onCharacteristicChanged(BluetoothGatt object, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
                BleOp.this.isResponsed = true;
                object = object.getDevice().getAddress();
                BleOp.this.last_received_characteristic.put((String)object, bluetoothGattCharacteristic);
                if (BleOp.this.flag_broadcast) {
                    BleOp.this.broadcast_Response((String)object, BleOp.ACTION_DATA_AVAILABLE);
                }
                BleOp.this.OnCharacteristicChanged((String)object, bluetoothGattCharacteristic);
                Log.d((String)BleOp.TAG, (String)"onCharacteristicChanged");
            }

            public void onCharacteristicRead(BluetoothGatt object, BluetoothGattCharacteristic bluetoothGattCharacteristic, int n) {
                if (n == 0) {
                    BleOp.this.isResponsed = true;
                    object = object.getDevice().getAddress();
                    BleOp.this.last_received_characteristic.put((String)object, bluetoothGattCharacteristic);
                    if (BleOp.this.flag_broadcast) {
                        BleOp.this.broadcast_Response((String)object, BleOp.ACTION_DATA_AVAILABLE);
                    }
                    BleOp.this.OnCharacteristicRead((String)object, bluetoothGattCharacteristic);
                }
                Log.d((String)BleOp.TAG, (String)"onCharacteristicRead");
            }

            public void onCharacteristicWrite(BluetoothGatt object, BluetoothGattCharacteristic bluetoothGattCharacteristic, int n) {
                object = new StringBuilder();
                ((StringBuilder)object).append("onCharacteristicWrite ");
                ((StringBuilder)object).append(n);
                Log.d((String)BleOp.TAG, (String)((StringBuilder)object).toString());
            }

            public void onConnectionStateChange(BluetoothGatt object, int n, int n2) {
                BleOp.this.isResponsed = true;
                String string2 = object.getDevice().getAddress();
                if (n == 0) {
                    if (n2 == 2) {
                        BleOp.this.state = CONNECTION_STATE.CONNECTED;
                        object.discoverServices();
                        n2 = 0;
                        int n3 = object.getDevice().getBondState();
                        if (n3 != 10 && n3 != 12) {
                            n = n2;
                            if (n3 == 11) {
                                n = 10000;
                                Log.i((String)BleOp.TAG, (String)"waiting for bonding to complete");
                            }
                        } else {
                            n = n2;
                            if (Build.VERSION.SDK_INT <= 24) {
                                n = n2;
                                if (n3 == 12) {
                                    n = 1000;
                                }
                            }
                        }
                        BleOp.this.discoverServicesRunnable = new Runnable((BluetoothGatt)object, n){
                            final /* synthetic */ int val$delay;
                            final /* synthetic */ BluetoothGatt val$gatt;
                            {
                                this.val$gatt = bluetoothGatt;
                                this.val$delay = n;
                            }

                            @Override
                            public void run() {
                                Log.d((String)BleOp.TAG, (String)String.format(Locale.ENGLISH, "discovering services of '%s' with delay of %d ms", this.val$gatt.getDevice().getName(), this.val$delay));
                                if (!this.val$gatt.discoverServices()) {
                                    Log.e((String)BleOp.TAG, (String)"discoverServices failed to start");
                                }
                                BleOp.this.discoverServicesRunnable = null;
                            }
                        };
                        BleOp.access$700((BleOp)BleOp.this).mHandler.postDelayed(BleOp.this.discoverServicesRunnable, (long)n);
                    } else if (n2 == 0) {
                        BleOp.this.state = CONNECTION_STATE.DISCONNECTED;
                        object.close();
                    }
                } else {
                    object.close();
                }
                if (BleOp.this.flag_broadcast) {
                    object = BleOp.this;
                    ((BleOp)object).broadcast_Response(string2, BleOp.ACTION_GATT_CONNECTION_CHANGED, ((BleOp)object).state);
                }
                object = BleOp.this;
                ((BleOp)object).OnConnectionStateChange(((BleOp)object).state);
                Log.d((String)BleOp.TAG, (String)"onConnectionStateChange");
            }

            public void onMtuChanged(BluetoothGatt object, int n, int n2) {
                BleOp.this.isResponsed = true;
                object = object.getDevice().getAddress();
                if (BleOp.this.flag_broadcast) {
                    BleOp.this.broadcast_Response((String)object, BleOp.ACTION_MTU_CHANGED);
                }
                BleOp.this.OnMTUChanged((String)object, n, n2);
                object = new StringBuilder();
                ((StringBuilder)object).append("onMtuChanged ");
                ((StringBuilder)object).append(n);
                Log.d((String)BleOp.TAG, (String)((StringBuilder)object).toString());
            }

            public void onServicesDiscovered(BluetoothGatt object, int n) {
                BleOp.this.isResponsed = true;
                String string2 = object.getDevice().getAddress();
                if (n == 0) {
                    BleOp.this.list_services = object.getServices();
                    if (BleOp.this.flag_broadcast) {
                        BleOp.this.broadcast_Response(string2, BleOp.ACTION_GATT_SERVICES_DISCOVERED);
                    }
                    object = BleOp.this;
                    ((BleOp)object).OnServicesFound(((BleOp)object).list_services);
                }
                Log.d((String)BleOp.TAG, (String)"onServicesDiscovered");
            }

        };
        stringBuilder = Build.VERSION.SDK_INT >= 23 ? this.get_Device(string2).connectGatt(this.context, this.isAutoConnect, this.gattCallback, 2) : this.get_Device(string2).connectGatt(this.context, this.isAutoConnect, this.gattCallback);
        this.bluetoothGatts.put(string2, (BluetoothGatt)stringBuilder);
    }

    public void disconnect(String string2) {
        this.bluetoothGatts.get(string2).disconnect();
    }

    public void discover_Services(String string2) {
        this.bluetoothGatts.get(string2).discoverServices();
    }

    public void do_Task(int n) {
        this.threadLooper.mHandler.obtainMessage(n).sendToTarget();
    }

    public void do_Task(int n, String string2) {
        this.threadLooper.mHandler.obtainMessage(n, (Object)string2).sendToTarget();
    }

    public void enable_BluetoothLE() {
        Intent intent = new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE");
        this.activity.startActivityForResult(intent, 30);
        this.message = 3;
    }

    public void enable_Notification(String string2, UUID uUID, UUID object) {
        string2 = this.bluetoothGatts.get(string2);
        uUID = string2.getService(uUID).getCharacteristic((UUID)object);
        string2.setCharacteristicNotification((BluetoothGattCharacteristic)uUID, true);
        object = uUID.getDescriptors().iterator();
        while (object.hasNext()) {
            uUID = (BluetoothGattDescriptor)object.next();
            uUID.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            string2.writeDescriptor((BluetoothGattDescriptor)uUID);
        }
    }

    public BluetoothGattCharacteristic get_Characteristic_received(String string2) {
        return this.last_received_characteristic.get(string2);
    }

    public List<BluetoothGattCharacteristic> get_Characteristics() {
        return this.list_characteristics;
    }

    public HashMap<String, BluetoothDevice> get_Devices_bond() {
        this.bonded_devices = new HashMap();
        Iterator iterator2 = this.adapter.getBondedDevices().iterator();
        while (iterator2.hasNext()) {
            BluetoothDevice bluetoothDevice = (BluetoothDevice)iterator2.next();
            String string2 = bluetoothDevice.getAddress();
            this.bonded_devices.put(string2, bluetoothDevice);
        }
        return this.bonded_devices;
    }

    public HashMap<String, BluetoothDevice> get_Devices_connected() {
        this.connected_devices = new HashMap();
        Iterator iterator2 = ((BluetoothManager)this.activity.getSystemService("bluetooth")).getConnectedDevices(7).iterator();
        while (iterator2.hasNext()) {
            BluetoothDevice bluetoothDevice = (BluetoothDevice)iterator2.next();
            if (bluetoothDevice.getType() != 2) continue;
            String string2 = bluetoothDevice.getAddress();
            this.connected_devices.put(string2, bluetoothDevice);
        }
        return this.connected_devices;
    }

    public HashMap<String, BluetoothDevice> get_Devices_scanned() {
        return this.scanned_devices;
    }

    public List<BluetoothGattService> get_Services(String string2) {
        if ((string2 = this.bluetoothGatts.get(string2)) != null) return string2.getServices();
        return null;
    }

    public boolean is_Bluetooth_enabled() {
        return this.is_bluetooth_enabled;
    }

    public boolean is_Bluetooth_supported() {
        return this.is_bluetooth_supported;
    }

    public boolean is_Device_bonded(String string2) {
        string2 = this.scanned_devices.get(string2);
        boolean bl = false;
        if (string2 == null) {
            return false;
        }
        if (string2.getBondState() != 12) return bl;
        return true;
    }

    public boolean is_Device_connected(String string2) {
        this.get_Devices_connected();
        boolean bl = this.connected_devices.containsKey(string2);
        if (bl) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("connected to ");
            stringBuilder.append(string2);
            Log.d((String)TAG, (String)stringBuilder.toString());
            return bl;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("disconnected from ");
        stringBuilder.append(string2);
        Log.d((String)TAG, (String)stringBuilder.toString());
        return bl;
    }

    public boolean is_Gatt_available(String string2) {
        return this.bluetoothGatts.containsKey(string2);
    }

    public void read_Characteristic(String string2, UUID uUID, UUID uUID2) {
        uUID = (string2 = this.bluetoothGatts.get(string2)).getService(uUID);
        if (uUID == null) return;
        if ((uUID = uUID.getCharacteristic(uUID2)) == null) return;
        string2.readCharacteristic((BluetoothGattCharacteristic)uUID);
    }

    public void setAutoConnect(boolean bl) {
        this.isAutoConnect = bl;
    }

    public void setIsBleScan(boolean bl) {
        this.isBleScan = bl;
    }

    public BleOp setTargetDeviceWithServiceUuids(UUID[] arruUID) {
        this.target_device_with_service_uuids = arruUID;
        return this;
    }

    public void set_MTU(String string2, int n) {
        this.bluetoothGatts.get(string2).requestMtu(n);
    }

    public void start() {
        if (this.threadLooper == null) {
            this.threadLooper = new ThreadLooper(){};
        }
        this.threadLooper.start();
    }

    public void start_scanning() {
        this.scanned_devices = new HashMap();
        if (this.isBleScan) {
            BluetoothAdapter.LeScanCallback leScanCallback;
            this.scanCallback = leScanCallback = new BluetoothAdapter.LeScanCallback(){

                public void onLeScan(BluetoothDevice bluetoothDevice, int n, byte[] arrby) {
                    String string2;
                    String string3;
                    String string4 = string3 = bluetoothDevice.getName();
                    if (string3 == null) {
                        string4 = BleOp.this.get_DeviceName_from_ScanRecord(arrby);
                    }
                    if ((string2 = bluetoothDevice.getAddress()) == null) return;
                    if (BleOp.this.scanned_devices.containsKey(string2)) return;
                    BleOp.this.scanned_devices.put(bluetoothDevice.getAddress(), bluetoothDevice);
                    if (!BleOp.this.flag_server && BleOp.this.device_name_server != null && bluetoothDevice.getName().equals(BleOp.this.device_name_server)) {
                        BleOp.this.device_server = bluetoothDevice;
                        BleOp.this.OnServerDeviceFound(bluetoothDevice);
                    }
                    string3 = string4;
                    if (string4 == null) {
                        string3 = string2;
                    }
                    BleOp.this.broadcast_Device_scanned(string3, string2, n, arrby);
                    BleOp.this.OnDeviceFound(bluetoothDevice, n, arrby);
                }
            };
            UUID[] arruUID = this.target_device_with_service_uuids;
            if (arruUID != null && arruUID.length != 0) {
                this.adapter.startLeScan(arruUID, leScanCallback);
            } else {
                this.adapter.startLeScan(this.scanCallback);
            }
            this.OnBluetoothLeScanningStarted();
        } else {
            if (this.adapter.isDiscovering()) {
                this.adapter.cancelDiscovery();
            }
            this.adapter.startDiscovery();
            this.receiverScanResult = new ReceiverScanResult();
            IntentFilter intentFilter = new IntentFilter("android.bluetooth.device.action.FOUND");
            this.context.registerReceiver((BroadcastReceiver)this.receiverScanResult, intentFilter);
        }
        this.message = 6;
        this.threadLooper.mHandler.postDelayed(new Runnable(){

            @Override
            public void run() {
                if (BleOp.this.threadLooper != null && BleOp.access$700((BleOp)BleOp.this).mHandler != null) {
                    BleOp.access$700((BleOp)BleOp.this).mHandler.obtainMessage(50).sendToTarget();
                }
                BleOp.this.broadcast_Response(BleOp.ACTION_BLE_SCAN_DONE);
            }
        }, (long)this.time_scanning);
    }

    public void stop() {
        Object object2 = this.adapter;
        if (object2 != null) {
            object2.disable();
        }
        if ((object2 = this.bluetoothGatts) != null) {
            for (Object object2 : ((HashMap)object2).keySet()) {
                this.bluetoothGatts.get(object2).close();
            }
        }
        this.threadLooper.mHandler = null;
        this.threadLooper = null;
    }

    public void stop_scanning() {
        if (this.isBleScan) {
            this.adapter.stopLeScan(this.scanCallback);
            return;
        }
        this.adapter.cancelDiscovery();
    }

    public void write_Characteristic(String string2, UUID uUID, UUID uUID2, int n, int n2) {
        uUID = (string2 = this.bluetoothGatts.get(string2)).getService(uUID);
        if (uUID == null) return;
        if ((uUID = uUID.getCharacteristic(uUID2)) == null) return;
        uUID.setValue(n, n2, 0);
        string2.writeCharacteristic((BluetoothGattCharacteristic)uUID);
    }

    public void write_Characteristic(String string2, UUID uUID, UUID uUID2, byte[] arrby) {
        uUID = (string2 = this.bluetoothGatts.get(string2)).getService(uUID);
        if (uUID == null) return;
        if ((uUID = uUID.getCharacteristic(uUID2)) == null) return;
        uUID.setValue(arrby);
        string2.writeCharacteristic((BluetoothGattCharacteristic)uUID);
    }

    public static final class CONNECTION_STATE
    extends Enum<CONNECTION_STATE> {
        private static final /* synthetic */ CONNECTION_STATE[] $VALUES;
        public static final /* enum */ CONNECTION_STATE CONNECTED;
        public static final /* enum */ CONNECTION_STATE CONNECTING;
        public static final /* enum */ CONNECTION_STATE DISCONNECTED;

        static {
            CONNECTION_STATE cONNECTION_STATE;
            DISCONNECTED = new CONNECTION_STATE();
            CONNECTING = new CONNECTION_STATE();
            CONNECTED = cONNECTION_STATE = new CONNECTION_STATE();
            $VALUES = new CONNECTION_STATE[]{DISCONNECTED, CONNECTING, cONNECTION_STATE};
        }

        public static CONNECTION_STATE valueOf(String string2) {
            return Enum.valueOf(CONNECTION_STATE.class, string2);
        }

        public static CONNECTION_STATE[] values() {
            return (CONNECTION_STATE[])$VALUES.clone();
        }
    }

    private class ReceiverScanResult
    extends BroadcastReceiver {
        private ReceiverScanResult() {
        }

        public void onReceive(Context object, Intent object2) {
            if (!"android.bluetooth.device.action.FOUND".equals(object2.getAction())) return;
            BluetoothDevice bluetoothDevice = (BluetoothDevice)object2.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            String string2 = bluetoothDevice.getName();
            object2 = bluetoothDevice.getAddress();
            object = string2;
            if (string2 == null) {
                object = object2;
            }
            BleOp.this.broadcast_Device_scanned((String)object, (String)object2, 0, null);
            BleOp.this.OnDeviceFound(bluetoothDevice);
        }
    }

    class ThreadLooper
    extends Thread {
        public Handler mHandler = null;

        ThreadLooper() {
        }

        @Override
        public void run() {
            Looper.prepare();
            this.mHandler = new Handler(){

                public void handleMessage(Message message) {
                    int n = message.what;
                    if (n == 10) {
                        BleOp.this.check_Bluetooth_supported();
                        return;
                    }
                    if (n == 20) {
                        BleOp.this.check_Bluetooth_enabled();
                        return;
                    }
                    if (n == 30) {
                        BleOp.this.enable_BluetoothLE();
                        return;
                    }
                    if (n == 40) {
                        BleOp.this.start_scanning();
                        return;
                    }
                    if (n == 50) {
                        BleOp.this.stop_scanning();
                        return;
                    }
                    if (n == 60) {
                        BleOp.this.connect((String)message.obj);
                        return;
                    }
                    if (n == 80) {
                        BleOp.this.bond((String)message.obj);
                        return;
                    }
                    if (n != 100) {
                        return;
                    }
                    BleOp.this.disconnect((String)message.obj);
                }
            };
            Looper.loop();
        }

    }

}

