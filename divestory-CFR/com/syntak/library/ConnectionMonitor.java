/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.net.NetworkInfo
 *  android.net.wifi.ScanResult
 *  android.net.wifi.WifiInfo
 *  android.net.wifi.WifiManager
 *  android.os.Parcelable
 */
package com.syntak.library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import com.syntak.library.NetOp;
import java.util.List;

public class ConnectionMonitor {
    BroadcastReceiver InternetConnectionReceiver = new BroadcastReceiver(){

        public void onReceive(Context object, Intent intent) {
            boolean bl = intent.getBooleanExtra("noConnectivity", false);
            intent.getStringExtra("reason");
            intent.getBooleanExtra("isFailover", false);
            object = (NetworkInfo)intent.getParcelableExtra("networkInfo");
            object = (NetworkInfo)intent.getParcelableExtra("otherNetwork");
            if (!bl) {
                ConnectionMonitor.this.flag_internet_connected = true;
                ConnectionMonitor.this.local_ip = NetOp.getLocalIPV4();
            } else {
                ConnectionMonitor.this.flag_internet_connected = false;
                ConnectionMonitor.this.local_ip = null;
            }
            object = ConnectionMonitor.this;
            ((ConnectionMonitor)object).OnInternetConnectionChanged(((ConnectionMonitor)object).flag_internet_connected);
        }
    };
    String SSID = null;
    BroadcastReceiver WifiConnectionReceiver = new BroadcastReceiver(){

        public void onReceive(Context object, Intent intent) {
            if (1 == ((NetworkInfo)intent.getParcelableExtra("networkInfo")).getType()) {
                object = ConnectionMonitor.this.wifiManager.getConnectionInfo();
                ConnectionMonitor.this.SSID = object.getSSID();
                ConnectionMonitor.this.flag_wifi_connected = true;
            } else {
                ConnectionMonitor.this.flag_wifi_connected = false;
                ConnectionMonitor.this.SSID = null;
            }
            object = ConnectionMonitor.this;
            ((ConnectionMonitor)object).OnWifiConnectionChanged(((ConnectionMonitor)object).flag_wifi_connected);
        }
    };
    final BroadcastReceiver WifiScanReceiver = new BroadcastReceiver(){

        public void onReceive(Context object, Intent intent) {
            if (!intent.getAction().equals("android.net.wifi.SCAN_RESULTS")) return;
            object = ConnectionMonitor.this;
            ((ConnectionMonitor)object).wifi_list = ((ConnectionMonitor)object).wifiManager.getScanResults();
            object = ConnectionMonitor.this;
            ((ConnectionMonitor)object).OnWifiListChanged(((ConnectionMonitor)object).wifi_list);
        }
    };
    Context context;
    boolean flag_internet_connected = false;
    boolean flag_wifi_connected = false;
    String local_ip = null;
    WifiManager wifiManager = null;
    List<ScanResult> wifi_list = null;

    public ConnectionMonitor(Context context) {
        this.context = context;
        context.registerReceiver(this.InternetConnectionReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        context.registerReceiver(this.WifiConnectionReceiver, new IntentFilter("android.net.wifi.STATE_CHANGE"));
        context.registerReceiver(this.WifiScanReceiver, new IntentFilter("android.net.wifi.SCAN_RESULTS"));
        context = (WifiManager)context.getApplicationContext().getSystemService("wifi");
        this.wifiManager = context;
        context.startScan();
    }

    public void OnInternetConnectionChanged(boolean bl) {
    }

    public void OnWifiConnectionChanged(boolean bl) {
    }

    public void OnWifiListChanged(List<ScanResult> list) {
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

