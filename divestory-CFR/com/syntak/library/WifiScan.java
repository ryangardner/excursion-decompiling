/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 *  android.net.wifi.ScanResult
 *  android.net.wifi.WifiConfiguration
 *  android.net.wifi.WifiInfo
 *  android.net.wifi.WifiManager
 */
package com.syntak.library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import com.syntak.library.MathOp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class WifiScan {
    ConnectivityManager connManager;
    Context context;
    HashMap<String, ConnectionInfo> hm = new HashMap();
    BroadcastReceiver receiver = new BroadcastReceiver(){

        public void onReceive(Context object, Intent object2) {
            object = object2.getAction();
            int n = ((String)object).hashCode();
            if (n != -1875733435) {
                if (n != 1878357501) return;
                if (!((String)object).equals("android.net.wifi.SCAN_RESULTS")) return;
                n = 0;
            } else {
                if (!((String)object).equals("android.net.wifi.WIFI_STATE_CHANGED")) return;
                n = 1;
            }
            if (n != 0) {
                if (n != 1) {
                    return;
                }
                WifiScan.this.OnWifiStateChanged();
                return;
            }
            object2 = WifiScan.this.wifiManager.getScanResults();
            object = new ArrayList();
            Iterator iterator2 = object2.iterator();
            do {
                if (!iterator2.hasNext()) {
                    WifiScan.this.OnWifiScanned((ArrayList<ConnectionInfo>)object);
                    return;
                }
                object2 = (ScanResult)iterator2.next();
                ((ArrayList)object).add(new ConnectionInfo(((ScanResult)object2).SSID, ((ScanResult)object2).level, ((ScanResult)object2).frequency));
            } while (true);
        }
    };
    WifiManager wifiManager;

    public WifiScan(Context context) {
        WifiManager wifiManager;
        this.context = context;
        this.connManager = (ConnectivityManager)context.getSystemService("connectivity");
        this.wifiManager = wifiManager = (WifiManager)context.getSystemService("wifi");
        wifiManager.startScan();
        wifiManager = new IntentFilter();
        wifiManager.addAction("android.net.wifi.SCAN_RESULTS");
        wifiManager.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        context.registerReceiver(this.receiver, (IntentFilter)wifiManager);
    }

    public static MathOp.Matrix getProbePosition(WifiApInfo[] object, double[] arrd, double[] arrd2) {
        double d;
        double d2;
        double d3;
        block10 : {
            double d4;
            double d5;
            double d6;
            block14 : {
                block13 : {
                    boolean bl;
                    boolean bl2;
                    block15 : {
                        block11 : {
                            block12 : {
                                block9 : {
                                    int n;
                                    if (((WifiApInfo[])object).length < 3) {
                                        return null;
                                    }
                                    WifiApInfo[] arrwifiApInfo = new WifiApInfo[3];
                                    for (n = 0; n < 3; ++n) {
                                        arrwifiApInfo[n] = new WifiApInfo(null, object[n].x, object[n].y, object[n].z, object[n].d);
                                    }
                                    n = WifiScan.normalizeApInfo(arrwifiApInfo);
                                    if (n < 0) {
                                        return null;
                                    }
                                    d = arrwifiApInfo[0].x;
                                    d3 = arrwifiApInfo[0].y;
                                    d6 = arrwifiApInfo[0].z;
                                    double d7 = arrwifiApInfo[1].x;
                                    double d8 = arrwifiApInfo[1].y;
                                    double d9 = arrwifiApInfo[1].z;
                                    double d10 = arrwifiApInfo[2].x;
                                    double d11 = arrwifiApInfo[2].y;
                                    d4 = arrwifiApInfo[2].z;
                                    d2 = arrwifiApInfo[0].d * arrwifiApInfo[0].d - d * d - d3 * d3 - d6 * d6;
                                    double d12 = arrwifiApInfo[1].d;
                                    double d13 = arrwifiApInfo[1].d;
                                    d5 = (d2 - (arrwifiApInfo[2].d * arrwifiApInfo[2].d - d10 * d10 - d11 * d11 - d4 * d4)) / 2.0;
                                    d5 /= (d10 -= d);
                                    d12 = (d2 - (d12 * d13 - d7 * d7 - d8 * d8 - d9 * d9)) / 2.0;
                                    d13 = d12 / (d7 -= d);
                                    d11 = (d11 - d3) / d10;
                                    d5 = (d5 - d13) / (d11 -= (d8 -= d3) / d7);
                                    d13 = d9 - d6;
                                    d10 = (d13 / d7 - (d4 - d6) / d10) / d11;
                                    d9 = (d12 - d5 * d8) / d7;
                                    d7 = (-d13 - d8 * d10) / d7;
                                    d4 = d7 * d7 + d10 * d10 + 1.0;
                                    d8 = d9 * 2.0;
                                    d12 = d5 * 2.0;
                                    d11 = d8 * d7 - d7 * 2.0 * d + d12 * d10 - d10 * 2.0 * d3 - d6 * 2.0;
                                    d6 = -d11;
                                    d3 = d11 * d11 - 4.0 * d4 * (d9 * d9 + d5 * d5 - d8 * d - d12 * d3 - d2);
                                    d2 = (Math.sqrt(d3) + d6) / 2.0 / d4;
                                    d = (d6 - Math.sqrt(d3)) / 2.0 / d4;
                                    d6 = d7 * d2 + d9;
                                    d4 = d10 * d2 + d5;
                                    d3 = d9 + d7 * d;
                                    d10 = d5 + d10 * d;
                                    if (n != 1) {
                                        if (n != 2) {
                                            d5 = d6;
                                            d6 = d2;
                                            d2 = d3;
                                            d3 = d;
                                        } else {
                                            d5 = d2;
                                            d2 = d;
                                        }
                                        d = d10;
                                    } else {
                                        d5 = d4;
                                        d9 = d;
                                        d4 = d6;
                                        d6 = d2;
                                        d2 = d10;
                                        d = d3;
                                        d3 = d9;
                                    }
                                    if (((WifiApInfo[])object).length <= 3) break block9;
                                    d10 = Math.sqrt(MathOp.square(object[3].x - d5) + MathOp.square(object[3].y - d4) + MathOp.square(object[3].z - d6));
                                    d9 = Math.sqrt(MathOp.square(object[3].x - d2) + MathOp.square(((WifiApInfo)object[3]).y - d) + MathOp.square(((WifiApInfo)object[3]).z - d3));
                                    if (!(Math.abs(d10 - ((WifiApInfo)object[3]).d) < Math.abs(d9 - ((WifiApInfo)object[3]).d))) break block10;
                                    break block11;
                                }
                                if (arrd2 == null) break block12;
                                boolean bl3 = WifiScan.isAbove(d5, d4, d6, arrd2);
                                boolean bl4 = WifiScan.isAbove(d2, d, d3, arrd2);
                                if (!bl3 && bl4) break block11;
                                if (!bl3 || bl4) break block13;
                                break block10;
                            }
                            if (arrd == null) break block14;
                            bl = WifiScan.isBelow(d5, d4, d6, arrd);
                            bl2 = WifiScan.isBelow(d2, d, d3, arrd);
                            if (bl || !bl2) break block15;
                        }
                        d2 = d5;
                        d3 = d6;
                        d = d4;
                        break block10;
                    }
                    if (bl && !bl2) break block10;
                }
                d2 = d = (d3 = 0.0);
                break block10;
            }
            d2 = (d5 + d2) / 2.0;
            d = (d4 + d) / 2.0;
            d3 = (d6 + d3) / 2.0;
        }
        object = new MathOp.Matrix(1, 3);
        ((MathOp.Matrix)object).setValueAt(0, 0, d2);
        ((MathOp.Matrix)object).setValueAt(0, 1, d);
        ((MathOp.Matrix)object).setValueAt(0, 2, d3);
        return object;
    }

    public static double getWifiApDistance(double d, double d2) {
        return Math.pow(10.0, (27.55 - Math.log10(d2) * 20.0 - d) / 20.0);
    }

    public static boolean isAbove(double d, double d2, double d3, double[] arrd) {
        boolean bl = false;
        boolean bl2 = !(d < arrd[0]);
        if (d2 < arrd[1]) {
            bl2 = false;
        }
        if (!(d3 < arrd[2])) return bl2;
        return bl;
    }

    public static boolean isAllPositive(double d, double d2, double d3) {
        boolean bl = false;
        boolean bl2 = !(d < 0.0);
        if (d2 < 0.0) {
            bl2 = false;
        }
        if (!(d3 < 0.0)) return bl2;
        return bl;
    }

    public static boolean isBelow(double d, double d2, double d3, double[] arrd) {
        boolean bl = false;
        boolean bl2 = !(d > arrd[0]);
        if (d2 > arrd[1]) {
            bl2 = false;
        }
        if (!(d3 > arrd[2])) return bl2;
        return bl;
    }

    public static int normalizeApInfo(WifiApInfo[] arrwifiApInfo) {
        int n;
        boolean bl = WifiScan.same2in3(arrwifiApInfo[0].x, arrwifiApInfo[1].x, arrwifiApInfo[2].x);
        boolean bl2 = WifiScan.same2in3(arrwifiApInfo[0].y, arrwifiApInfo[1].y, arrwifiApInfo[2].y);
        boolean bl3 = WifiScan.same2in3(arrwifiApInfo[0].z, arrwifiApInfo[1].z, arrwifiApInfo[2].z);
        if (bl) {
            if (!bl2) {
                for (n = 0; n < 3; ++n) {
                    double d = arrwifiApInfo[n].x;
                    arrwifiApInfo[n].x = arrwifiApInfo[n].y;
                    arrwifiApInfo[n].y = d;
                }
                n = 1;
            } else if (!bl3) {
                for (n = 0; n < 3; ++n) {
                    double d = arrwifiApInfo[n].x;
                    arrwifiApInfo[n].x = arrwifiApInfo[n].z;
                    arrwifiApInfo[n].z = d;
                }
                n = 2;
            } else {
                n = -1;
            }
        } else {
            n = 0;
        }
        if (!WifiScan.same1in3(arrwifiApInfo[0].x, arrwifiApInfo[1].x, arrwifiApInfo[2].x)) return n;
        if (arrwifiApInfo[1].x == arrwifiApInfo[2].x) return n;
        if (arrwifiApInfo[0].x == arrwifiApInfo[1].x) {
            WifiApInfo wifiApInfo = arrwifiApInfo[0];
            arrwifiApInfo[0] = arrwifiApInfo[2];
            arrwifiApInfo[2] = wifiApInfo;
            return n;
        }
        if (arrwifiApInfo[0].x != arrwifiApInfo[2].x) return n;
        WifiApInfo wifiApInfo = arrwifiApInfo[0];
        arrwifiApInfo[0] = arrwifiApInfo[1];
        arrwifiApInfo[1] = wifiApInfo;
        return n;
    }

    public static boolean same1in3(double d, double d2, double d3) {
        boolean bl = false;
        int n = d - d2 == 0.0 ? 1 : 0;
        int n2 = n;
        if (d - d3 == 0.0) {
            n2 = n + 1;
        }
        n = n2;
        if (d2 - d3 == 0.0) {
            n = n2 + 1;
        }
        if (n < 1) return bl;
        return true;
    }

    public static boolean same2in3(double d, double d2, double d3) {
        boolean bl = false;
        int n = d - d2 == 0.0 ? 1 : 0;
        int n2 = n;
        if (d - d3 == 0.0) {
            n2 = n + 1;
        }
        n = n2;
        if (d2 - d3 == 0.0) {
            n = n2 + 1;
        }
        if (n < 2) return bl;
        return true;
    }

    public static void sortConnectionInfoList(ArrayList<ConnectionInfo> arrayList) {
        int n;
        int n2 = arrayList.size();
        int[] arrn = new int[n2];
        int n3 = 0;
        int n4 = 0;
        do {
            n = n3;
            if (n4 >= n2) break;
            arrn[n4] = n4;
            ++n4;
        } while (true);
        while (n < n2 - 1) {
            double d = arrayList.get((int)arrn[n]).SignalLevelDbm;
            for (n3 = n4 = n + 1; n3 < n2; ++n3) {
                if (!(arrayList.get((int)arrn[n3]).SignalLevelDbm > d)) continue;
                int n5 = arrn[n3];
                arrn[n3] = arrn[n];
                arrn[n] = n5;
            }
            n = n4;
        }
    }

    public void OnWifiScanned(ArrayList<ConnectionInfo> arrayList) {
    }

    public void OnWifiStateChanged() {
    }

    public void connect(String charSequence, String string2) {
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\"");
        stringBuilder.append((String)charSequence);
        stringBuilder.append("\"");
        wifiConfiguration.SSID = stringBuilder.toString();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("\"");
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append("\"");
        wifiConfiguration.preSharedKey = ((StringBuilder)charSequence).toString();
        int n = this.wifiManager.addNetwork(wifiConfiguration);
        this.wifiManager.disconnect();
        this.wifiManager.enableNetwork(n, true);
        this.wifiManager.reconnect();
    }

    public String getCurrentSSID() {
        Object object = this.wifiManager.getConnectionInfo();
        if (object == null) return null;
        return object.getSSID();
    }

    public boolean isWifiConnected() {
        return this.connManager.getNetworkInfo(1).isConnected();
    }

    public void scan() {
        this.wifiManager.startScan();
    }

    public void stop() {
        this.context.unregisterReceiver(this.receiver);
        this.hm = null;
    }

    public class ConnectionInfo {
        public double FrequencyMHz;
        public String SSID;
        public double SignalLevelDbm;

        public ConnectionInfo() {
        }

        public ConnectionInfo(String string2, double d, double d2) {
            this.SSID = string2;
            this.SignalLevelDbm = d;
            this.FrequencyMHz = d2;
        }
    }

    public static class WifiApInfo {
        public String SSID;
        public double d;
        public double x;
        public double y;
        public double z;

        public WifiApInfo(String string2, double d, double d2, double d3, double d4) {
            this.SSID = string2;
            this.x = d;
            this.y = d2;
            this.z = d3;
            this.d = d4;
        }
    }

}

