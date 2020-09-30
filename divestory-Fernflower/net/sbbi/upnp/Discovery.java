package net.sbbi.upnp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;
import net.sbbi.upnp.devices.UPNPRootDevice;

public class Discovery {
   public static final String ALL_DEVICES = "ssdp:all";
   public static final int DEFAULT_MX = 3;
   public static final String DEFAULT_SEARCH = "ssdp:all";
   public static final int DEFAULT_SSDP_SEARCH_PORT = 1901;
   public static final int DEFAULT_TIMEOUT = 1500;
   public static final int DEFAULT_TTL = 4;
   public static final String ROOT_DEVICES = "upnp:rootdevice";
   public static final String SSDP_IP = "239.255.255.250";
   public static final int SSDP_PORT = 1900;
   private static final Logger log = Logger.getLogger(Discovery.class.getName());

   // $FF: synthetic method
   static Logger access$0() {
      return log;
   }

   public static UPNPRootDevice[] discover() throws IOException {
      return discover(1500, 4, 3, "ssdp:all");
   }

   public static UPNPRootDevice[] discover(int var0, int var1, int var2, String var3) throws IOException {
      return discoverDevices(var0, var1, var2, var3, (NetworkInterface)null);
   }

   public static UPNPRootDevice[] discover(int var0, int var1, int var2, String var3, NetworkInterface var4) throws IOException {
      return discoverDevices(var0, var1, var2, var3, var4);
   }

   public static UPNPRootDevice[] discover(int var0, String var1) throws IOException {
      return discover(var0, 4, 3, var1);
   }

   public static UPNPRootDevice[] discover(String var0) throws IOException {
      return discover(1500, 4, 3, var0);
   }

   private static UPNPRootDevice[] discoverDevices(int var0, int var1, int var2, String var3, NetworkInterface var4) throws IOException {
      if (var3 != null && var3.trim().length() != 0) {
         final HashMap var5 = new HashMap();
         DiscoveryResultsHandler var6 = new DiscoveryResultsHandler() {
            public void discoveredDevice(String param1, String param2, String param3, String param4, URL param5, String param6) {
               // $FF: Couldn't be decompiled
            }
         };
         DiscoveryListener.getInstance().registerResultsHandler(var6, var3);
         Enumeration var13;
         if (var4 == null) {
            Enumeration var7 = NetworkInterface.getNetworkInterfaces();

            while(var7.hasMoreElements()) {
               var13 = ((NetworkInterface)var7.nextElement()).getInetAddresses();

               while(var13.hasMoreElements()) {
                  InetAddress var8 = (InetAddress)var13.nextElement();
                  if (var8 instanceof Inet4Address && !var8.isLoopbackAddress()) {
                     sendSearchMessage(var8, var1, var2, var3);
                  }
               }
            }
         } else {
            var13 = var4.getInetAddresses();

            while(var13.hasMoreElements()) {
               InetAddress var15 = (InetAddress)var13.nextElement();
               if (var15 instanceof Inet4Address && !var15.isLoopbackAddress()) {
                  sendSearchMessage(var15, var1, var2, var3);
               }
            }
         }

         long var9 = (long)var0;

         try {
            Thread.sleep(var9);
         } catch (InterruptedException var11) {
         }

         DiscoveryListener.getInstance().unRegisterResultsHandler(var6, var3);
         if (var5.size() == 0) {
            return null;
         } else {
            var0 = 0;
            UPNPRootDevice[] var12 = new UPNPRootDevice[var5.size()];

            for(Iterator var14 = var5.values().iterator(); var14.hasNext(); ++var0) {
               var12[var0] = (UPNPRootDevice)var14.next();
            }

            return var12;
         }
      } else {
         throw new IllegalArgumentException("Illegal searchTarget");
      }
   }

   public static void sendSearchMessage(InetAddress var0, int var1, int var2, String var3) throws IOException {
      String var4 = System.getProperty("net.sbbi.upnp.Discovery.bindPort");
      int var5;
      if (var4 != null) {
         var5 = Integer.parseInt(var4);
      } else {
         var5 = 1901;
      }

      InetSocketAddress var11 = new InetSocketAddress(InetAddress.getByName("239.255.255.250"), 1900);
      MulticastSocket var6 = new MulticastSocket((SocketAddress)null);
      var6.bind(new InetSocketAddress(var0, var5));
      var6.setTimeToLive(var1);
      StringBuffer var7 = new StringBuffer();
      var7.append("M-SEARCH * HTTP/1.1\r\n");
      var7.append("HOST: 239.255.255.250:1900\r\n");
      var7.append("MAN: \"ssdp:discover\"\r\n");
      var7.append("MX: ");
      var7.append(var2);
      var7.append("\r\n");
      var7.append("ST: ");
      var7.append(var3);
      var7.append("\r\n");
      var7.append("\r\n");
      Logger var10 = log;
      StringBuilder var8 = new StringBuilder("Sending discovery message on 239.255.255.250:1900 multicast address form ip ");
      var8.append(var0.getHostAddress());
      var8.append(":\n");
      var8.append(var7.toString());
      var10.fine(var8.toString());
      byte[] var9 = var7.toString().getBytes();
      var6.send(new DatagramPacket(var9, var9.length, var11));
      var6.disconnect();
      var6.close();
   }
}
