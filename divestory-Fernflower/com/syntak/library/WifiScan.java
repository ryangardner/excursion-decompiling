package com.syntak.library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class WifiScan {
   ConnectivityManager connManager;
   Context context;
   HashMap<String, WifiScan.ConnectionInfo> hm = new HashMap();
   BroadcastReceiver receiver = new BroadcastReceiver() {
      public void onReceive(Context var1, Intent var2) {
         byte var9;
         label35: {
            String var5 = var2.getAction();
            int var3 = var5.hashCode();
            if (var3 != -1875733435) {
               if (var3 == 1878357501 && var5.equals("android.net.wifi.SCAN_RESULTS")) {
                  var9 = 0;
                  break label35;
               }
            } else if (var5.equals("android.net.wifi.WIFI_STATE_CHANGED")) {
               var9 = 1;
               break label35;
            }

            var9 = -1;
         }

         if (var9 != 0) {
            if (var9 == 1) {
               WifiScan.this.OnWifiStateChanged();
            }
         } else {
            List var7 = WifiScan.this.wifiManager.getScanResults();
            ArrayList var6 = new ArrayList();
            Iterator var4 = var7.iterator();

            while(var4.hasNext()) {
               ScanResult var8 = (ScanResult)var4.next();
               var6.add(WifiScan.this.new ConnectionInfo(var8.SSID, (double)var8.level, (double)var8.frequency));
            }

            WifiScan.this.OnWifiScanned(var6);
         }

      }
   };
   WifiManager wifiManager;

   public WifiScan(Context var1) {
      this.context = var1;
      this.connManager = (ConnectivityManager)var1.getSystemService("connectivity");
      WifiManager var2 = (WifiManager)var1.getSystemService("wifi");
      this.wifiManager = var2;
      var2.startScan();
      IntentFilter var3 = new IntentFilter();
      var3.addAction("android.net.wifi.SCAN_RESULTS");
      var3.addAction("android.net.wifi.WIFI_STATE_CHANGED");
      var1.registerReceiver(this.receiver, var3);
   }

   public static MathOp.Matrix getProbePosition(WifiScan.WifiApInfo[] var0, double[] var1, double[] var2) {
      if (var0.length < 3) {
         return null;
      } else {
         WifiScan.WifiApInfo[] var3 = new WifiScan.WifiApInfo[3];

         int var4;
         for(var4 = 0; var4 < 3; ++var4) {
            var3[var4] = new WifiScan.WifiApInfo((String)null, var0[var4].x, var0[var4].y, var0[var4].z, var0[var4].d);
         }

         var4 = normalizeApInfo(var3);
         if (var4 < 0) {
            return null;
         } else {
            double var5 = var3[0].x;
            double var7 = var3[0].y;
            double var9 = var3[0].z;
            double var11 = var3[1].x;
            double var13 = var3[1].y;
            double var15 = var3[1].z;
            double var17 = var3[2].x;
            double var19 = var3[2].y;
            double var21 = var3[2].z;
            double var23 = var3[0].d * var3[0].d - var5 * var5 - var7 * var7 - var9 * var9;
            double var25 = var3[1].d;
            double var27 = var3[1].d;
            double var29 = (var23 - (var3[2].d * var3[2].d - var17 * var17 - var19 * var19 - var21 * var21)) / 2.0D;
            var17 -= var5;
            var29 /= var17;
            var25 = (var23 - (var25 * var27 - var11 * var11 - var13 * var13 - var15 * var15)) / 2.0D;
            var11 -= var5;
            var27 = var25 / var11;
            var19 = (var19 - var7) / var17;
            var13 -= var7;
            var19 -= var13 / var11;
            var29 = (var29 - var27) / var19;
            var27 = var15 - var9;
            var17 = (var27 / var11 - (var21 - var9) / var17) / var19;
            var15 = (var25 - var29 * var13) / var11;
            var11 = (-var27 - var13 * var17) / var11;
            var21 = var11 * var11 + var17 * var17 + 1.0D;
            var13 = var15 * 2.0D;
            var25 = var29 * 2.0D;
            var19 = var13 * var11 - var11 * 2.0D * var5 + var25 * var17 - var17 * 2.0D * var7 - var9 * 2.0D;
            var9 = -var19;
            var7 = var19 * var19 - 4.0D * var21 * (var15 * var15 + var29 * var29 - var13 * var5 - var25 * var7 - var23);
            var23 = (Math.sqrt(var7) + var9) / 2.0D / var21;
            var5 = (var9 - Math.sqrt(var7)) / 2.0D / var21;
            var9 = var11 * var23 + var15;
            var21 = var17 * var23 + var29;
            var7 = var15 + var11 * var5;
            var17 = var29 + var17 * var5;
            if (var4 != 1) {
               if (var4 != 2) {
                  var29 = var9;
                  var9 = var23;
                  var23 = var7;
                  var7 = var5;
               } else {
                  var29 = var23;
                  var23 = var5;
               }

               var5 = var17;
            } else {
               var29 = var21;
               var15 = var5;
               var21 = var9;
               var9 = var23;
               var23 = var17;
               var5 = var7;
               var7 = var15;
            }

            label78: {
               if (var0.length > 3) {
                  var17 = Math.sqrt(MathOp.square(var0[3].x - var29) + MathOp.square(var0[3].y - var21) + MathOp.square(var0[3].z - var9));
                  var15 = Math.sqrt(MathOp.square(var0[3].x - var23) + MathOp.square(var0[3].y - var5) + MathOp.square(var0[3].z - var7));
                  if (Math.abs(var17 - var0[3].d) >= Math.abs(var15 - var0[3].d)) {
                     break label78;
                  }
               } else {
                  label77: {
                     boolean var32;
                     boolean var31;
                     if (var2 != null) {
                        var31 = isAbove(var29, var21, var9, var2);
                        var32 = isAbove(var23, var5, var7, var2);
                        if (!var31 && var32) {
                           break label77;
                        }

                        if (var31 && !var32) {
                           break label78;
                        }
                     } else {
                        if (var1 == null) {
                           var23 = (var29 + var23) / 2.0D;
                           var5 = (var21 + var5) / 2.0D;
                           var7 = (var9 + var7) / 2.0D;
                           break label78;
                        }

                        var31 = isBelow(var29, var21, var9, var1);
                        var32 = isBelow(var23, var5, var7, var1);
                        if (!var31 && var32) {
                           break label77;
                        }

                        if (var31 && !var32) {
                           break label78;
                        }
                     }

                     var7 = 0.0D;
                     var5 = var7;
                     var23 = var7;
                     break label78;
                  }
               }

               var23 = var29;
               var7 = var9;
               var5 = var21;
            }

            MathOp.Matrix var33 = new MathOp.Matrix(1, 3);
            var33.setValueAt(0, 0, var23);
            var33.setValueAt(0, 1, var5);
            var33.setValueAt(0, 2, var7);
            return var33;
         }
      }
   }

   public static double getWifiApDistance(double var0, double var2) {
      return Math.pow(10.0D, (27.55D - Math.log10(var2) * 20.0D - var0) / 20.0D);
   }

   public static boolean isAbove(double var0, double var2, double var4, double[] var6) {
      boolean var7 = false;
      boolean var8;
      if (var0 < var6[0]) {
         var8 = false;
      } else {
         var8 = true;
      }

      if (var2 < var6[1]) {
         var8 = false;
      }

      if (var4 < var6[2]) {
         var8 = var7;
      }

      return var8;
   }

   public static boolean isAllPositive(double var0, double var2, double var4) {
      boolean var6 = false;
      boolean var7;
      if (var0 < 0.0D) {
         var7 = false;
      } else {
         var7 = true;
      }

      if (var2 < 0.0D) {
         var7 = false;
      }

      if (var4 < 0.0D) {
         var7 = var6;
      }

      return var7;
   }

   public static boolean isBelow(double var0, double var2, double var4, double[] var6) {
      boolean var7 = false;
      boolean var8;
      if (var0 > var6[0]) {
         var8 = false;
      } else {
         var8 = true;
      }

      if (var2 > var6[1]) {
         var8 = false;
      }

      if (var4 > var6[2]) {
         var8 = var7;
      }

      return var8;
   }

   public static int normalizeApInfo(WifiScan.WifiApInfo[] var0) {
      boolean var1 = same2in3(var0[0].x, var0[1].x, var0[2].x);
      boolean var2 = same2in3(var0[0].y, var0[1].y, var0[2].y);
      boolean var3 = same2in3(var0[0].z, var0[1].z, var0[2].z);
      byte var8;
      if (var1) {
         int var4;
         double var5;
         if (!var2) {
            for(var4 = 0; var4 < 3; ++var4) {
               var5 = var0[var4].x;
               var0[var4].x = var0[var4].y;
               var0[var4].y = var5;
            }

            var8 = 1;
         } else if (!var3) {
            for(var4 = 0; var4 < 3; ++var4) {
               var5 = var0[var4].x;
               var0[var4].x = var0[var4].z;
               var0[var4].z = var5;
            }

            var8 = 2;
         } else {
            var8 = -1;
         }
      } else {
         var8 = 0;
      }

      if (same1in3(var0[0].x, var0[1].x, var0[2].x) && var0[1].x != var0[2].x) {
         WifiScan.WifiApInfo var7;
         if (var0[0].x == var0[1].x) {
            var7 = var0[0];
            var0[0] = var0[2];
            var0[2] = var7;
         } else if (var0[0].x == var0[2].x) {
            var7 = var0[0];
            var0[0] = var0[1];
            var0[1] = var7;
         }
      }

      return var8;
   }

   public static boolean same1in3(double var0, double var2, double var4) {
      boolean var6 = false;
      byte var7;
      if (var0 - var2 == 0.0D) {
         var7 = 1;
      } else {
         var7 = 0;
      }

      int var8 = var7;
      if (var0 - var4 == 0.0D) {
         var8 = var7 + 1;
      }

      int var9 = var8;
      if (var2 - var4 == 0.0D) {
         var9 = var8 + 1;
      }

      if (var9 >= 1) {
         var6 = true;
      }

      return var6;
   }

   public static boolean same2in3(double var0, double var2, double var4) {
      boolean var6 = false;
      byte var7;
      if (var0 - var2 == 0.0D) {
         var7 = 1;
      } else {
         var7 = 0;
      }

      int var8 = var7;
      if (var0 - var4 == 0.0D) {
         var8 = var7 + 1;
      }

      int var9 = var8;
      if (var2 - var4 == 0.0D) {
         var9 = var8 + 1;
      }

      if (var9 >= 2) {
         var6 = true;
      }

      return var6;
   }

   public static void sortConnectionInfoList(ArrayList<WifiScan.ConnectionInfo> var0) {
      int var1 = var0.size();
      int[] var2 = new int[var1];
      byte var3 = 0;
      int var4 = 0;

      while(true) {
         int var5 = var3;
         if (var4 >= var1) {
            while(var5 < var1 - 1) {
               double var6 = ((WifiScan.ConnectionInfo)var0.get(var2[var5])).SignalLevelDbm;
               var4 = var5 + 1;

               for(int var9 = var4; var9 < var1; ++var9) {
                  if (((WifiScan.ConnectionInfo)var0.get(var2[var9])).SignalLevelDbm > var6) {
                     int var8 = var2[var9];
                     var2[var9] = var2[var5];
                     var2[var5] = var8;
                  }
               }

               var5 = var4;
            }

            return;
         }

         var2[var4] = var4++;
      }
   }

   public void OnWifiScanned(ArrayList<WifiScan.ConnectionInfo> var1) {
   }

   public void OnWifiStateChanged() {
   }

   public void connect(String var1, String var2) {
      WifiConfiguration var3 = new WifiConfiguration();
      StringBuilder var4 = new StringBuilder();
      var4.append("\"");
      var4.append(var1);
      var4.append("\"");
      var3.SSID = var4.toString();
      StringBuilder var6 = new StringBuilder();
      var6.append("\"");
      var6.append(var2);
      var6.append("\"");
      var3.preSharedKey = var6.toString();
      int var5 = this.wifiManager.addNetwork(var3);
      this.wifiManager.disconnect();
      this.wifiManager.enableNetwork(var5, true);
      this.wifiManager.reconnect();
   }

   public String getCurrentSSID() {
      WifiInfo var1 = this.wifiManager.getConnectionInfo();
      String var2;
      if (var1 != null) {
         var2 = var1.getSSID();
      } else {
         var2 = null;
      }

      return var2;
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

      public ConnectionInfo(String var2, double var3, double var5) {
         this.SSID = var2;
         this.SignalLevelDbm = var3;
         this.FrequencyMHz = var5;
      }
   }

   public static class WifiApInfo {
      public String SSID;
      public double d;
      public double x;
      public double y;
      public double z;

      public WifiApInfo(String var1, double var2, double var4, double var6, double var8) {
         this.SSID = var1;
         this.x = var2;
         this.y = var4;
         this.z = var6;
         this.d = var8;
      }
   }
}
