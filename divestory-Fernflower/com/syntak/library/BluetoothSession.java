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
import android.util.Log;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class BluetoothSession {
   private final int BE_DISCOVERABLE = 4;
   private final int CHECK_ENABLED = 3;
   private final int CHECK_SUPPORTED = 1;
   private final int CONNECT = 6;
   private final int ENABLE = 2;
   private final int IDLE = 0;
   private final int REQUEST_ENABLE_BT = 1;
   private final int START_DISCOVERY = 5;
   private Activity activity;
   private BluetoothAdapter adapter = null;
   private BroadcastReceiver bcr = null;
   private final int buffer_size = 4096;
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

   public BluetoothSession(Activity var1, UUID var2, String var3) {
      this.activity = var1;
      this.uuid_server = var2;
      this.device_name_server = var3;
      this.flag_server = false;
      this.adapter = BluetoothAdapter.getDefaultAdapter();
      this.message = 1;
   }

   public BluetoothSession(Activity var1, UUID var2, String var3, int var4) {
      this.activity = var1;
      this.uuid_server = var2;
      this.device_name_server = var3;
      if (var4 > 0) {
         this.time_discoverable = var4;
      }

      this.flag_server = true;
      this.adapter = BluetoothAdapter.getDefaultAdapter();
      this.message = 1;
   }

   // $FF: synthetic method
   static boolean access$000(BluetoothSession var0) {
      return var0.flag_server;
   }

   // $FF: synthetic method
   static BluetoothServerSocket access$100(BluetoothSession var0) {
      return var0.server_socket;
   }

   // $FF: synthetic method
   static BluetoothServerSocket access$102(BluetoothSession var0, BluetoothServerSocket var1) {
      var0.server_socket = var1;
      return var1;
   }

   // $FF: synthetic method
   static String access$200(BluetoothSession var0) {
      return var0.device_name_server;
   }

   // $FF: synthetic method
   static UUID access$300(BluetoothSession var0) {
      return var0.uuid_server;
   }

   // $FF: synthetic method
   static BluetoothAdapter access$400(BluetoothSession var0) {
      return var0.adapter;
   }

   // $FF: synthetic method
   static BluetoothSocket access$500(BluetoothSession var0) {
      return var0.socket;
   }

   // $FF: synthetic method
   static BluetoothSocket access$502(BluetoothSession var0, BluetoothSocket var1) {
      var0.socket = var1;
      return var1;
   }

   // $FF: synthetic method
   static BluetoothDevice access$600(BluetoothSession var0) {
      return var0.device_server;
   }

   private void looping() {
      switch(this.message) {
      case 1:
         this.checkBluetoothSupported();
         break;
      case 2:
         this.message = 0;
         this.enableBluetooth();
         break;
      case 3:
         this.checkBluetoothEnabled();
         break;
      case 4:
         this.message = 0;
         this.beDiscoverable();
         break;
      case 5:
         this.message = 0;
         this.startDiscovery();
         break;
      case 6:
         this.message = 0;
         this.connect();
      }

      String var1 = this.send_string;
      if (var1 != null) {
         this.sendString(var1);
      }

      if (this.flag_receive_string) {
         byte[] var8 = new byte[1024];
         this.received_data = var8;

         try {
            this.received_length = this.is.read(var8);
            var1 = new String(this.received_data, 0, this.received_length);
            this.flag_receive_string = false;
            this.OnStringReceived(var1);
         } catch (IOException var3) {
            Log.d("Bluetooth", var3.toString());
         }
      }

      boolean var10001;
      int var2;
      label64: {
         try {
            if (!this.flag_receive_data) {
               break label64;
            }

            var2 = this.dis.read(this.received_data);
            this.received_length = var2;
         } catch (IOException var7) {
            var10001 = false;
            return;
         }

         if (var2 > 0) {
            try {
               this.flag_receive_data = false;
               this.OnDataReceived(this.received_data, var2);
            } catch (IOException var6) {
               var10001 = false;
               return;
            }
         }
      }

      try {
         if (!this.flag_receive_data_block) {
            return;
         }

         var2 = this.dis.read(this.received_data, this.received_length, this.receive_length - this.received_length);
      } catch (IOException var5) {
         var10001 = false;
         return;
      }

      if (var2 > 0) {
         try {
            var2 += this.received_length;
            this.received_length = var2;
            if (var2 >= this.receive_length) {
               this.flag_receive_data_block = false;
               this.OnDataReceived(this.received_data, var2);
            }
         } catch (IOException var4) {
            var10001 = false;
         }
      }

   }

   private void start_timer() {
      this.timer = null;
      this.timer = new Timer();
      TimerTask var1 = new TimerTask() {
         public void run() {
            BluetoothSession.this.looping();
         }
      };
      this.timer_task = var1;
      this.timer.scheduleAtFixedRate(var1, (long)this.delay_start, (long)this.update_interval);
   }

   public void OnBluetoothDiscoveryStarted() {
   }

   public void OnBluetoothEnabled(boolean var1) {
   }

   public void OnBluetoothSupported(boolean var1) {
   }

   public void OnDataReceived(byte[] var1, int var2) {
   }

   public void OnError(String var1) {
   }

   public void OnServerDeviceFound(BluetoothDevice var1) {
   }

   public void OnServerDeviceFound(String var1) {
   }

   public void OnSocketReady() {
   }

   public void OnStringReceived(String var1) {
   }

   public void OnStringSent() {
   }

   public void beDiscoverable() {
      Intent var1 = new Intent("android.bluetooth.adapter.action.REQUEST_DISCOVERABLE");
      var1.putExtra("android.bluetooth.adapter.extra.DISCOVERABLE_DURATION", this.time_discoverable);
      this.activity.startActivity(var1);
   }

   public void checkBluetoothEnabled() {
      boolean var1 = this.adapter.isEnabled();
      this.flag_bluetooth_enabled = var1;
      this.OnBluetoothEnabled(var1);
      if (this.flag_bluetooth_enabled) {
         if (this.flag_server) {
            this.message = 6;
            this.beDiscoverable();
         } else {
            this.message = 0;
            this.startDiscovery();
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
      (new Thread(new Runnable() {
         public void run() {
            // $FF: Couldn't be decompiled
         }
      })).start();
   }

   public void enableBluetooth() {
      Intent var1 = new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE");
      this.activity.startActivity(var1);
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
      } catch (IOException var2) {
      }

      this.received_data = new byte[this.receive_length];
      this.flag_receive_data_block = true;
   }

   public void enableReceiveString() {
      this.flag_receive_string = true;
   }

   public void handleDiscovery(Intent var1) {
      if ("android.bluetooth.device.action.FOUND".equals(var1.getAction())) {
         BluetoothDevice var2 = (BluetoothDevice)var1.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
         this.list_devices.add(var2);
         if (!this.flag_server && var2.getName().equals(this.device_name_server)) {
            this.device_server = var2;
            this.adapter.cancelDiscovery();
            this.OnServerDeviceFound(this.device_name_server);
            this.OnServerDeviceFound(var2);
            this.message = 6;
         }
      }

   }

   public boolean isBluetoothEnabled() {
      return this.flag_bluetooth_enabled;
   }

   public boolean isBluetoothSupported() {
      return this.flag_bluetooth_supported;
   }

   public int receiveData(byte[] var1) {
      DataInputStream var2 = this.dis;
      int var3 = 0;
      if (var2 == null) {
         return 0;
      } else {
         int var4;
         try {
            var4 = var2.read(var1, 0, var1.length);
         } catch (IOException var5) {
            Log.d("receive_data", var5.toString());
            this.OnError(var5.toString());
            return var3;
         }

         var3 = var4;
         return var3;
      }
   }

   public boolean sendData(byte[] var1, int var2) {
      DataOutputStream var3 = this.dos;
      if (var3 == null) {
         return false;
      } else {
         try {
            var3.write(var1, 0, var2);
            return true;
         } catch (IOException var4) {
            Log.d("send_data", var4.toString());
            this.OnError(var4.toString());
            return false;
         }
      }
   }

   public boolean sendDataBlock(byte[] var1, int var2) {
      DataOutputStream var3 = this.dos;
      if (var3 == null) {
         return false;
      } else {
         try {
            var3.writeInt(var2);
            this.dos.write(var1, 0, var2);
            return true;
         } catch (IOException var4) {
            Log.d("receive_data", var4.toString());
            this.OnError(var4.toString());
            return false;
         }
      }
   }

   public void sendString(String var1) {
      if (this.os != null) {
         try {
            byte[] var3 = var1.getBytes();
            this.os.write(var3);
         } catch (IOException var2) {
         }

         this.OnStringSent();
      } else {
         this.send_string = var1;
      }

   }

   public void start() {
      this.list_devices = new ArrayList();
      if (this.thread == null) {
         Thread var1 = new Thread(new Runnable() {
            public void run() {
               BluetoothSession.this.start_timer();
            }
         });
         this.thread = var1;
         var1.setPriority(10);
         this.thread.start();
      }

   }

   public void startDiscovery() {
      this.bcr = new BroadcastReceiver() {
         public void onReceive(Context var1, Intent var2) {
            BluetoothSession.this.handleDiscovery(var2);
         }
      };
      IntentFilter var1 = new IntentFilter("android.bluetooth.device.action.FOUND");
      this.activity.registerReceiver(this.bcr, var1);
      var1 = new IntentFilter("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
      this.activity.registerReceiver(this.bcr, var1);
      this.adapter.startDiscovery();
      this.OnBluetoothDiscoveryStarted();
   }

   public void stop() {
      BluetoothAdapter var1 = this.adapter;
      if (var1 != null) {
         var1.disable();
      }

      Timer var3 = this.timer;
      if (var3 != null) {
         var3.cancel();
         this.timer = null;
      }

      this.timer_task = null;
      this.thread = null;
      BluetoothSocket var4 = this.socket;
      if (var4 != null) {
         try {
            var4.close();
         } catch (IOException var2) {
         }

         this.socket = null;
      }

      if (this.bcr != null) {
         LocalBroadcastManager.getInstance(this.activity).unregisterReceiver(this.bcr);
      }

   }
}
