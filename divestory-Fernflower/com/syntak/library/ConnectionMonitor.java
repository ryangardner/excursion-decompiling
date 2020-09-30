package com.syntak.library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import java.util.List;

public class ConnectionMonitor {
   BroadcastReceiver InternetConnectionReceiver = new BroadcastReceiver() {
      public void onReceive(Context var1, Intent var2) {
         boolean var3 = var2.getBooleanExtra("noConnectivity", false);
         var2.getStringExtra("reason");
         var2.getBooleanExtra("isFailover", false);
         NetworkInfo var4 = (NetworkInfo)var2.getParcelableExtra("networkInfo");
         var4 = (NetworkInfo)var2.getParcelableExtra("otherNetwork");
         if (!var3) {
            ConnectionMonitor.this.flag_internet_connected = true;
            ConnectionMonitor.this.local_ip = NetOp.getLocalIPV4();
         } else {
            ConnectionMonitor.this.flag_internet_connected = false;
            ConnectionMonitor.this.local_ip = null;
         }

         ConnectionMonitor var5 = ConnectionMonitor.this;
         var5.OnInternetConnectionChanged(var5.flag_internet_connected);
      }
   };
   String SSID = null;
   BroadcastReceiver WifiConnectionReceiver = new BroadcastReceiver() {
      public void onReceive(Context var1, Intent var2) {
         if (1 == ((NetworkInfo)var2.getParcelableExtra("networkInfo")).getType()) {
            WifiInfo var3 = ConnectionMonitor.this.wifiManager.getConnectionInfo();
            ConnectionMonitor.this.SSID = var3.getSSID();
            ConnectionMonitor.this.flag_wifi_connected = true;
         } else {
            ConnectionMonitor.this.flag_wifi_connected = false;
            ConnectionMonitor.this.SSID = null;
         }

         ConnectionMonitor var4 = ConnectionMonitor.this;
         var4.OnWifiConnectionChanged(var4.flag_wifi_connected);
      }
   };
   final BroadcastReceiver WifiScanReceiver = new BroadcastReceiver() {
      public void onReceive(Context var1, Intent var2) {
         if (var2.getAction().equals("android.net.wifi.SCAN_RESULTS")) {
            ConnectionMonitor var3 = ConnectionMonitor.this;
            var3.wifi_list = var3.wifiManager.getScanResults();
            var3 = ConnectionMonitor.this;
            var3.OnWifiListChanged(var3.wifi_list);
         }

      }
   };
   Context context;
   boolean flag_internet_connected = false;
   boolean flag_wifi_connected = false;
   String local_ip = null;
   WifiManager wifiManager = null;
   List<ScanResult> wifi_list = null;

   public ConnectionMonitor(Context var1) {
      this.context = var1;
      var1.registerReceiver(this.InternetConnectionReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
      var1.registerReceiver(this.WifiConnectionReceiver, new IntentFilter("android.net.wifi.STATE_CHANGE"));
      var1.registerReceiver(this.WifiScanReceiver, new IntentFilter("android.net.wifi.SCAN_RESULTS"));
      WifiManager var2 = (WifiManager)var1.getApplicationContext().getSystemService("wifi");
      this.wifiManager = var2;
      var2.startScan();
   }

   public void OnInternetConnectionChanged(boolean var1) {
   }

   public void OnWifiConnectionChanged(boolean var1) {
   }

   public void OnWifiListChanged(List<ScanResult> var1) {
   }

   public String getLocalIP() {
      return this.local_ip;
   }

   public String getSSID() {
      return this.SSID;
   }

   public void stop() {
      this.context.unregisterReceiver(this.InternetConnectionReceiver);
      this.context.unregisterReceiver(this.WifiConnectionReceiver);
      this.context.unregisterReceiver(this.WifiScanReceiver);
   }
}
