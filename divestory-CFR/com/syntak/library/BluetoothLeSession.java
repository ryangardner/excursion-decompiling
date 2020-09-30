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
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Build
 *  android.os.Build$VERSION
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
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import com.syntak.library.TimeOp;
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
    BluetoothAdapter.LeScanCallback scanCallback = null;
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

    public BluetoothLeSession(Activity activity, UUID uUID, String string2) {
        this.IDLE = 0;
        this.CHECK_SUPPORTED = 1;
        this.ENABLE = 2;
        this.CHECK_ENABLED = 3;
        this.BE_DISCOVERABLE = 4;
        this.START_DISCOVERY = 5;
        this.SCANNING = 6;
        this.CONNECT = 7;
        this.activity = activity;
        this.uuid_server = uUID;
        this.device_name_server = string2;
        this.flag_server = true;
        this.get_adapter();
        this.message = 1;
    }

    public BluetoothLeSession(Activity activity, UUID uUID, String string2, int n) {
        this.IDLE = 0;
        this.CHECK_SUPPORTED = 1;
        this.ENABLE = 2;
        this.CHECK_ENABLED = 3;
        this.BE_DISCOVERABLE = 4;
        this.START_DISCOVERY = 5;
        this.SCANNING = 6;
        this.CONNECT = 7;
        this.activity = activity;
        this.uuid_server = uUID;
        this.device_name_server = string2;
        if (n > 0) {
            this.time_scanning = n;
        }
        this.flag_server = false;
        this.get_adapter();
        this.message = 1;
    }

    private void broadcastUpdate(String string2) {
        Intent intent = new Intent(string2);
        this.activity.sendBroadcast(intent);
        this.OnActionReceived(string2);
    }

    private void get_adapter() {
        BluetoothManager bluetoothManager = (BluetoothManager)this.activity.getSystemService("bluetooth");
        if (Build.VERSION.SDK_INT >= 18) {
            this.adapter = bluetoothManager.getAdapter();
            return;
        }
        this.adapter = BluetoothAdapter.getDefaultAdapter();
    }

    private void looping() {
        switch (this.message) {
            default: {
                return;
            }
            case 7: {
                this.message = 0;
                this.connect();
                return;
            }
            case 6: {
                if (TimeOp.getNow() <= this.timeout) return;
                this.stopScanning();
                this.message = 0;
                return;
            }
            case 5: {
                this.message = 0;
                this.startScanning();
                return;
            }
            case 4: {
                this.message = 0;
                return;
            }
            case 3: {
                this.checkBluetoothEnabled();
                return;
            }
            case 2: {
                this.message = 0;
                this.enableBluetoothLE();
                return;
            }
            case 1: 
        }
        this.checkBluetoothSupported();
    }

    private void start_timer() {
        TimerTask timerTask2;
        this.timer = null;
        this.timer = new Timer();
        this.timer_task = timerTask2 = new TimerTask(){

            @Override
            public void run() {
                BluetoothLeSession.this.looping();
            }
        };
        this.timer.scheduleAtFixedRate(timerTask2, this.delay_start, (long)this.update_interval);
    }

    public void OnActionReceived(String string2) {
    }

    public void OnBluetoothEnabled(boolean bl) {
    }

    public void OnBluetoothLeScanningStarted() {
    }

    public void OnBluetoothSupported(boolean bl) {
    }

    public void OnDataReceived(byte[] arrby, int n) {
    }

    public void OnError(String string2) {
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

    public void checkBluetoothEnabled() {
        boolean bl;
        this.flag_bluetooth_enabled = bl = this.adapter.isEnabled();
        this.OnBluetoothEnabled(bl);
        if (!this.flag_bluetooth_enabled) return;
        if (this.flag_server) {
            this.message = 7;
            return;
        }
        this.message = 0;
        this.startScanning();
    }

    public void checkBluetoothSupported() {
        if (this.adapter == null) {
            this.flag_bluetooth_supported = false;
            return;
        }
        this.flag_bluetooth_supported = true;
        this.OnBluetoothSupported(true);
        this.checkBluetoothEnabled();
        if (this.flag_bluetooth_enabled) return;
        this.message = 2;
    }

    public void connect() {
        BluetoothGattCallback bluetoothGattCallback;
        this.gattCallback = bluetoothGattCallback = new BluetoothGattCallback(){

            public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
                BluetoothLeSession.this.broadcastUpdate(BluetoothLeSession.ACTION_DATA_AVAILABLE);
                BluetoothLeSession.this.received_characteristic = bluetoothGattCharacteristic;
            }

            public void onCharacteristicRead(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int n) {
                if (n != 0) return;
                BluetoothLeSession.this.broadcastUpdate(BluetoothLeSession.ACTION_DATA_AVAILABLE);
                BluetoothLeSession.this.received_characteristic = bluetoothGattCharacteristic;
            }

            public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int n, int n2) {
                if (n2 == 2) {
                    BluetoothLeSession.this.state = 2;
                    BluetoothLeSession.this.broadcastUpdate(BluetoothLeSession.ACTION_GATT_CONNECTED);
                    BluetoothLeSession.this.bluetoothGatt.discoverServices();
                    return;
                }
                if (n2 != 0) return;
                BluetoothLeSession.this.state = 0;
                BluetoothLeSession.this.broadcastUpdate(BluetoothLeSession.ACTION_GATT_DISCONNECTED);
            }

            public void onServicesDiscovered(BluetoothGatt object, int n) {
                if (n != 0) return;
                BluetoothLeSession.this.broadcastUpdate(BluetoothLeSession.ACTION_GATT_SERVICES_DISCOVERED);
                object = BluetoothLeSession.this;
                ((BluetoothLeSession)object).bluetoothGattServices = ((BluetoothLeSession)object).bluetoothGatt.getServices();
                object = BluetoothLeSession.this;
                ((BluetoothLeSession)object).OnServicesFound(((BluetoothLeSession)object).bluetoothGattServices);
            }
        };
        if (this.flag_server) return;
        this.bluetoothGatt = this.device_server.connectGatt((Context)this.activity, false, bluetoothGattCallback);
    }

    public void enableBluetoothLE() {
        Intent intent = new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE");
        this.activity.startActivity(intent);
        this.message = 3;
    }

    public boolean isBluetoothEnabled() {
        return this.flag_bluetooth_enabled;
    }

    public boolean isBluetoothSupported() {
        return this.flag_bluetooth_supported;
    }

    public void requestCharacteristic(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        if (this.adapter == null) return;
        BluetoothGatt bluetoothGatt = this.bluetoothGatt;
        if (bluetoothGatt == null) {
            return;
        }
        bluetoothGatt.readCharacteristic(bluetoothGattCharacteristic);
    }

    public void start() {
        Thread thread2;
        this.list_devices = new ArrayList();
        if (this.thread != null) return;
        this.thread = thread2 = new Thread(new Runnable(){

            @Override
            public void run() {
                BluetoothLeSession.this.start_timer();
            }
        });
        thread2.setPriority(10);
        this.thread.start();
    }

    public void startScanning() {
        BluetoothAdapter.LeScanCallback leScanCallback;
        this.scanCallback = leScanCallback = new BluetoothAdapter.LeScanCallback(){

            public void onLeScan(BluetoothDevice bluetoothDevice, int n, byte[] arrby) {
                BluetoothLeSession.this.list_devices.add(bluetoothDevice);
                if (BluetoothLeSession.this.flag_server) return;
                if (!bluetoothDevice.getName().equals(BluetoothLeSession.this.device_name_server)) return;
                BluetoothLeSession.this.device_server = bluetoothDevice;
                BluetoothLeSession.this.OnServerDeviceFound(bluetoothDevice);
            }
        };
        this.adapter.startLeScan(leScanCallback);
        this.message = 6;
        this.timeout = TimeOp.getNow() + (long)(this.time_scanning * 1000);
        this.OnBluetoothLeScanningStarted();
    }

    public void stop() {
        Object object = this.adapter;
        if (object != null) {
            object.disable();
        }
        if ((object = this.timer) != null) {
            ((Timer)object).cancel();
            this.timer = null;
        }
        this.timer_task = null;
        this.thread = null;
        object = this.bluetoothGatt;
        if (object == null) return;
        object.close();
        this.bluetoothGatt = null;
    }

    public void stopScanning() {
        this.adapter.stopLeScan(this.scanCallback);
    }

    public void switchNotification(BluetoothGattCharacteristic bluetoothGattCharacteristic, boolean bl) {
        this.bluetoothGatt.setCharacteristicNotification(bluetoothGattCharacteristic, bl);
        this.uuid_peer = !this.flag_server ? this.uuid_server : this.uuid_client;
        bluetoothGattCharacteristic = bluetoothGattCharacteristic.getDescriptor(this.uuid_peer);
        if (bl) {
            bluetoothGattCharacteristic.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        } else {
            bluetoothGattCharacteristic.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
        }
        this.bluetoothGatt.writeDescriptor((BluetoothGattDescriptor)bluetoothGattCharacteristic);
    }

    public void writeCharacteristic(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        if (this.adapter == null) return;
        BluetoothGatt bluetoothGatt = this.bluetoothGatt;
        if (bluetoothGatt == null) {
            return;
        }
        bluetoothGatt.writeCharacteristic(bluetoothGattCharacteristic);
    }

}

