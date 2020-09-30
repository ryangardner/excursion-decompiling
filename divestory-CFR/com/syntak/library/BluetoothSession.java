/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.bluetooth.BluetoothAdapter
 *  android.bluetooth.BluetoothDevice
 *  android.bluetooth.BluetoothServerSocket
 *  android.bluetooth.BluetoothSocket
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.os.Parcelable
 *  android.util.Log
 */
package com.syntak.library;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Parcelable;
import android.util.Log;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class BluetoothSession {
    private final int BE_DISCOVERABLE;
    private final int CHECK_ENABLED;
    private final int CHECK_SUPPORTED;
    private final int CONNECT;
    private final int ENABLE;
    private final int IDLE;
    private final int REQUEST_ENABLE_BT;
    private final int START_DISCOVERY;
    private Activity activity;
    private BluetoothAdapter adapter = null;
    private BroadcastReceiver bcr = null;
    private final int buffer_size;
    BufferedWriter bw;
    int delay_start = 0;
    private BluetoothDevice device_client;
    private String device_name_client = null;
    private String device_name_server = null;
    private BluetoothDevice device_server;
    DataInputStream dis;
    DataOutputStream dos;
    private boolean flag_bluetooth_enabled = false;
    private boolean flag_bluetooth_supported = false;
    boolean flag_receive_data = false;
    boolean flag_receive_data_block = false;
    boolean flag_receive_string = false;
    private boolean flag_server = false;
    InputStream is;
    private ArrayList<BluetoothDevice> list_devices = null;
    private int message = 0;
    OutputStream os;
    int receive_length;
    String receive_string;
    byte[] received_data = null;
    int received_length;
    byte[] send_data;
    String send_string;
    private BluetoothServerSocket server_socket = null;
    private BluetoothSocket socket = null;
    private Thread thread = null;
    private int time_discoverable = 120;
    private Timer timer = null;
    private TimerTask timer_task = null;
    int update_interval = 500;
    private UUID uuid_client = null;
    private UUID uuid_server = null;

    public BluetoothSession(Activity activity, UUID uUID, String string2) {
        this.REQUEST_ENABLE_BT = 1;
        this.IDLE = 0;
        this.CHECK_SUPPORTED = 1;
        this.ENABLE = 2;
        this.CHECK_ENABLED = 3;
        this.BE_DISCOVERABLE = 4;
        this.START_DISCOVERY = 5;
        this.CONNECT = 6;
        this.buffer_size = 4096;
        this.activity = activity;
        this.uuid_server = uUID;
        this.device_name_server = string2;
        this.flag_server = false;
        this.adapter = BluetoothAdapter.getDefaultAdapter();
        this.message = 1;
    }

    public BluetoothSession(Activity activity, UUID uUID, String string2, int n) {
        this.REQUEST_ENABLE_BT = 1;
        this.IDLE = 0;
        this.CHECK_SUPPORTED = 1;
        this.ENABLE = 2;
        this.CHECK_ENABLED = 3;
        this.BE_DISCOVERABLE = 4;
        this.START_DISCOVERY = 5;
        this.CONNECT = 6;
        this.buffer_size = 4096;
        this.activity = activity;
        this.uuid_server = uUID;
        this.device_name_server = string2;
        if (n > 0) {
            this.time_discoverable = n;
        }
        this.flag_server = true;
        this.adapter = BluetoothAdapter.getDefaultAdapter();
        this.message = 1;
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    private void looping() {
        switch (this.message) {
            default: {
                ** break;
            }
            case 6: {
                this.message = 0;
                this.connect();
                ** break;
            }
            case 5: {
                this.message = 0;
                this.startDiscovery();
                ** break;
            }
            case 4: {
                this.message = 0;
                this.beDiscoverable();
                ** break;
            }
            case 3: {
                this.checkBluetoothEnabled();
                ** break;
            }
            case 2: {
                this.message = 0;
                this.enableBluetooth();
                ** break;
            }
            case 1: 
        }
        this.checkBluetoothSupported();
lbl25: // 7 sources:
        var1_1 = this.send_string;
        if (var1_1 != null) {
            this.sendString(var1_1);
        }
        if (this.flag_receive_string) {
            var1_2 = new byte[1024];
            this.received_data = var1_2;
            try {
                this.received_length = this.is.read(var1_2);
                var1_3 = new String(this.received_data, 0, this.received_length);
                this.flag_receive_string = false;
                this.OnStringReceived(var1_3);
            }
            catch (IOException var1_4) {
                Log.d((String)"Bluetooth", (String)var1_4.toString());
            }
        }
        try {
            if (this.flag_receive_data) {
                this.received_length = var2_6 = this.dis.read(this.received_data);
                if (var2_6 > 0) {
                    this.flag_receive_data = false;
                    this.OnDataReceived(this.received_data, var2_6);
                }
            }
            if (this.flag_receive_data_block == false) return;
            var2_6 = this.dis.read(this.received_data, this.received_length, this.receive_length - this.received_length);
            if (var2_6 <= 0) return;
            this.received_length = var2_6 = this.received_length + var2_6;
            if (var2_6 < this.receive_length) return;
            this.flag_receive_data_block = false;
            this.OnDataReceived(this.received_data, var2_6);
            return;
        }
        catch (IOException var1_5) {
            return;
        }
    }

    private void start_timer() {
        TimerTask timerTask2;
        this.timer = null;
        this.timer = new Timer();
        this.timer_task = timerTask2 = new TimerTask(){

            @Override
            public void run() {
                BluetoothSession.this.looping();
            }
        };
        this.timer.scheduleAtFixedRate(timerTask2, this.delay_start, (long)this.update_interval);
    }

    public void OnBluetoothDiscoveryStarted() {
    }

    public void OnBluetoothEnabled(boolean bl) {
    }

    public void OnBluetoothSupported(boolean bl) {
    }

    public void OnDataReceived(byte[] arrby, int n) {
    }

    public void OnError(String string2) {
    }

    public void OnServerDeviceFound(BluetoothDevice bluetoothDevice) {
    }

    public void OnServerDeviceFound(String string2) {
    }

    public void OnSocketReady() {
    }

    public void OnStringReceived(String string2) {
    }

    public void OnStringSent() {
    }

    public void beDiscoverable() {
        Intent intent = new Intent("android.bluetooth.adapter.action.REQUEST_DISCOVERABLE");
        intent.putExtra("android.bluetooth.adapter.extra.DISCOVERABLE_DURATION", this.time_discoverable);
        this.activity.startActivity(intent);
    }

    public void checkBluetoothEnabled() {
        boolean bl;
        this.flag_bluetooth_enabled = bl = this.adapter.isEnabled();
        this.OnBluetoothEnabled(bl);
        if (!this.flag_bluetooth_enabled) return;
        if (this.flag_server) {
            this.message = 6;
            this.beDiscoverable();
            return;
        }
        this.message = 0;
        this.startDiscovery();
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
        new Thread(new Runnable(){

            @Override
            public void run() {
                try {
                    BufferedWriter bufferedWriter;
                    if (BluetoothSession.this.flag_server) {
                        BluetoothSession.this.server_socket = BluetoothSession.this.adapter.listenUsingRfcommWithServiceRecord(BluetoothSession.this.device_name_server, BluetoothSession.this.uuid_server);
                        BluetoothSession.this.socket = BluetoothSession.this.server_socket.accept();
                    } else {
                        BluetoothSession.this.socket = BluetoothSession.this.device_server.createRfcommSocketToServiceRecord(BluetoothSession.this.uuid_server);
                        BluetoothSession.this.socket.connect();
                    }
                    BluetoothSession.this.OnSocketReady();
                    BluetoothSession bluetoothSession = BluetoothSession.this;
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(BluetoothSession.this.socket.getOutputStream());
                    bluetoothSession.bw = bufferedWriter = new BufferedWriter(outputStreamWriter, 4096);
                    BluetoothSession.this.is = BluetoothSession.this.socket.getInputStream();
                    BluetoothSession.this.os = BluetoothSession.this.socket.getOutputStream();
                    BluetoothSession.this.flag_receive_string = true;
                    return;
                }
                catch (IOException iOException) {
                    return;
                }
            }
        }).start();
    }

    public void enableBluetooth() {
        Intent intent = new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE");
        this.activity.startActivity(intent);
        this.message = 3;
    }

    public void enableReceiveData() {
        this.received_length = 0;
        if (this.received_data == null) {
            this.received_data = new byte[4096];
        }
        this.flag_receive_data = true;
    }

    public void enableReceiveDataBlock() {
        this.received_length = 0;
        try {
            this.receive_length = this.dis.readInt();
        }
        catch (IOException iOException) {}
        this.received_data = new byte[this.receive_length];
        this.flag_receive_data_block = true;
    }

    public void enableReceiveString() {
        this.flag_receive_string = true;
    }

    public void handleDiscovery(Intent intent) {
        if (!"android.bluetooth.device.action.FOUND".equals(intent.getAction())) return;
        intent = (BluetoothDevice)intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
        this.list_devices.add((BluetoothDevice)intent);
        if (this.flag_server) return;
        if (!intent.getName().equals(this.device_name_server)) return;
        this.device_server = intent;
        this.adapter.cancelDiscovery();
        this.OnServerDeviceFound(this.device_name_server);
        this.OnServerDeviceFound((BluetoothDevice)intent);
        this.message = 6;
    }

    public boolean isBluetoothEnabled() {
        return this.flag_bluetooth_enabled;
    }

    public boolean isBluetoothSupported() {
        return this.flag_bluetooth_supported;
    }

    public int receiveData(byte[] arrby) {
        DataInputStream dataInputStream = this.dis;
        int n = 0;
        if (dataInputStream == null) {
            return 0;
        }
        try {
            int n2 = dataInputStream.read(arrby, 0, arrby.length);
            return n2;
        }
        catch (IOException iOException) {
            Log.d((String)"receive_data", (String)iOException.toString());
            this.OnError(iOException.toString());
        }
        return n;
    }

    public boolean sendData(byte[] arrby, int n) {
        DataOutputStream dataOutputStream = this.dos;
        if (dataOutputStream == null) {
            return false;
        }
        try {
            dataOutputStream.write(arrby, 0, n);
            return true;
        }
        catch (IOException iOException) {
            Log.d((String)"send_data", (String)iOException.toString());
            this.OnError(iOException.toString());
            return false;
        }
    }

    public boolean sendDataBlock(byte[] arrby, int n) {
        DataOutputStream dataOutputStream = this.dos;
        if (dataOutputStream == null) {
            return false;
        }
        try {
            dataOutputStream.writeInt(n);
            this.dos.write(arrby, 0, n);
            return true;
        }
        catch (IOException iOException) {
            Log.d((String)"receive_data", (String)iOException.toString());
            this.OnError(iOException.toString());
            return false;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public void sendString(String var1_1) {
        if (this.os == null) {
            this.send_string = var1_1;
            return;
        }
        try {
            var1_1 = var1_1.getBytes();
            this.os.write(var1_1);
lbl7: // 2 sources:
            do {
                this.OnStringSent();
                return;
                break;
            } while (true);
        }
        catch (IOException var1_2) {
            ** continue;
        }
    }

    public void start() {
        Thread thread2;
        this.list_devices = new ArrayList();
        if (this.thread != null) return;
        this.thread = thread2 = new Thread(new Runnable(){

            @Override
            public void run() {
                BluetoothSession.this.start_timer();
            }
        });
        thread2.setPriority(10);
        this.thread.start();
    }

    public void startDiscovery() {
        this.bcr = new BroadcastReceiver(){

            public void onReceive(Context context, Intent intent) {
                BluetoothSession.this.handleDiscovery(intent);
            }
        };
        IntentFilter intentFilter = new IntentFilter("android.bluetooth.device.action.FOUND");
        this.activity.registerReceiver(this.bcr, intentFilter);
        intentFilter = new IntentFilter("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
        this.activity.registerReceiver(this.bcr, intentFilter);
        this.adapter.startDiscovery();
        this.OnBluetoothDiscoveryStarted();
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public void stop() {
        var1_1 = this.adapter;
        if (var1_1 != null) {
            var1_1.disable();
        }
        if ((var1_1 = this.timer) != null) {
            var1_1.cancel();
            this.timer = null;
        }
        this.timer_task = null;
        this.thread = null;
        var1_1 = this.socket;
        if (var1_1 != null) {
            try {
                var1_1.close();
lbl14: // 2 sources:
                do {
                    this.socket = null;
                    break;
                } while (true);
            }
            catch (IOException var1_2) {
                ** continue;
            }
        }
        if (this.bcr == null) return;
        LocalBroadcastManager.getInstance((Context)this.activity).unregisterReceiver(this.bcr);
    }

}

