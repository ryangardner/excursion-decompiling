package net.sbbi.upnp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiscoveryListener implements Runnable {
   private static final int DEFAULT_TIMEOUT = 250;
   private static boolean MATCH_IP = true;
   private static final Logger log = Logger.getLogger(DiscoveryListener.class.getName());
   private static final DiscoveryListener singleton;
   private final Object REGISTRATION_PROCESS = new Object();
   private boolean daemon = true;
   private boolean inService = false;
   private DatagramPacket input;
   private Map registeredHandlers = new HashMap();
   private MulticastSocket skt;

   static {
      String var0 = System.getProperty("net.sbbi.upnp.ddos.matchip");
      if (var0 != null && var0.equals("false")) {
         MATCH_IP = false;
      }

      singleton = new DiscoveryListener();
   }

   private DiscoveryListener() {
   }

   public static final DiscoveryListener getInstance() {
      return singleton;
   }

   private void listenBroadCast() throws IOException {
      this.skt.receive(this.input);
      InetAddress var1 = this.input.getAddress();
      String var2 = new String(this.input.getData(), this.input.getOffset(), this.input.getLength());

      HttpResponse var64;
      StringBuilder var3;
      Logger var59;
      try {
         var64 = new HttpResponse(var2);
      } catch (IllegalArgumentException var52) {
         var59 = log;
         var3 = new StringBuilder("Skipping uncompliant HTTP message ");
         var3.append(var2);
         var59.fine(var3.toString());
         return;
      }

      Throwable var10000;
      boolean var10001;
      label881: {
         String var4 = var64.getHeader();
         if (var4 != null && var4.startsWith("HTTP/1.1 200 OK") && var64.getHTTPHeaderField("st") != null) {
            var4 = var64.getHTTPHeaderField("location");
            if (var4 == null || var4.trim().length() == 0) {
               log.fine("Skipping SSDP message, missing HTTP header 'location' field");
               return;
            }

            URL var61 = new URL(var4);
            if (MATCH_IP) {
               InetAddress var5 = InetAddress.getByName(var61.getHost());
               if (!var1.equals(var5)) {
                  Logger var68 = log;
                  StringBuilder var63 = new StringBuilder("Discovery message sender IP ");
                  var63.append(var1);
                  var63.append(" does not match device description IP ");
                  var63.append(var5);
                  var63.append(" skipping device, set the net.sbbi.upnp.ddos.matchip system property");
                  var63.append(" to false to avoid this check");
                  var68.warning(var63.toString());
                  return;
               }
            }

            Logger var67 = log;
            StringBuilder var60 = new StringBuilder("Processing ");
            var60.append(var4);
            var60.append(" device description location");
            var67.fine(var60.toString());
            var4 = var64.getHTTPHeaderField("st");
            if (var4 == null || var4.trim().length() == 0) {
               log.fine("Skipping SSDP message, missing HTTP header 'st' field");
               return;
            }

            String var62 = var64.getHTTPHeaderField("usn");
            if (var62 == null || var62.trim().length() == 0) {
               log.fine("Skipping SSDP message, missing HTTP header 'usn' field");
               return;
            }

            String var69 = var64.getHTTPFieldElement("Cache-Control", "max-age");
            if (var69 == null || var69.trim().length() == 0) {
               log.fine("Skipping SSDP message, missing HTTP header 'max-age' field");
               return;
            }

            String var6 = var64.getHTTPHeaderField("server");
            if (var6 == null || var6.trim().length() == 0) {
               log.fine("Skipping SSDP message, missing HTTP header 'server' field");
               return;
            }

            int var7 = var62.indexOf("::");
            String var65;
            if (var7 != -1) {
               var65 = var62.substring(0, var7);
            } else {
               var65 = var62;
            }

            Object var8 = this.REGISTRATION_PROCESS;
            synchronized(var8){}

            Set var9;
            try {
               var9 = (Set)this.registeredHandlers.get(var4);
            } catch (Throwable var57) {
               var10000 = var57;
               var10001 = false;
               break label881;
            }

            if (var9 != null) {
               Iterator var70;
               try {
                  var70 = var9.iterator();
               } catch (Throwable var56) {
                  var10000 = var56;
                  var10001 = false;
                  break label881;
               }

               while(true) {
                  try {
                     if (!var70.hasNext()) {
                        break;
                     }
                  } catch (Throwable var58) {
                     var10000 = var58;
                     var10001 = false;
                     break label881;
                  }

                  try {
                     ((DiscoveryResultsHandler)var70.next()).discoveredDevice(var62, var65, var4, var69, var61, var6);
                  } catch (Throwable var55) {
                     var10000 = var55;
                     var10001 = false;
                     break label881;
                  }
               }
            }

            try {
               ;
            } catch (Throwable var54) {
               var10000 = var54;
               var10001 = false;
               break label881;
            }
         } else {
            var59 = log;
            var3 = new StringBuilder("Skipping uncompliant HTTP message ");
            var3.append(var2);
            var59.fine(var3.toString());
         }

         return;
      }

      while(true) {
         Throwable var66 = var10000;

         try {
            throw var66;
         } catch (Throwable var53) {
            var10000 = var53;
            var10001 = false;
            continue;
         }
      }
   }

   private void startDevicesListenerThread() throws IOException {
      // $FF: Couldn't be decompiled
   }

   private void startMultiCastSocket() throws IOException {
      String var1 = System.getProperty("net.sbbi.upnp.Discovery.bindPort");
      int var2;
      if (var1 != null) {
         var2 = Integer.parseInt(var1);
      } else {
         var2 = 1901;
      }

      MulticastSocket var3 = new MulticastSocket((SocketAddress)null);
      this.skt = var3;
      var3.bind(new InetSocketAddress(InetAddress.getByName("0.0.0.0"), var2));
      this.skt.setTimeToLive(4);
      this.skt.setSoTimeout(250);
      this.skt.joinGroup(InetAddress.getByName("239.255.255.250"));
      this.input = new DatagramPacket(new byte[2048], 2048);
   }

   private void stopDevicesListenerThread() {
      // $FF: Couldn't be decompiled
   }

   public void registerResultsHandler(DiscoveryResultsHandler var1, String var2) throws IOException {
      Object var3 = this.REGISTRATION_PROCESS;
      synchronized(var3){}

      Throwable var10000;
      boolean var10001;
      label265: {
         try {
            if (!this.inService) {
               this.startDevicesListenerThread();
            }
         } catch (Throwable var35) {
            var10000 = var35;
            var10001 = false;
            break label265;
         }

         Set var4;
         try {
            var4 = (Set)this.registeredHandlers.get(var2);
         } catch (Throwable var34) {
            var10000 = var34;
            var10001 = false;
            break label265;
         }

         Object var5 = var4;
         if (var4 == null) {
            try {
               var5 = new HashSet();
               this.registeredHandlers.put(var2, var5);
            } catch (Throwable var33) {
               var10000 = var33;
               var10001 = false;
               break label265;
            }
         }

         label251:
         try {
            ((Set)var5).add(var1);
            return;
         } catch (Throwable var32) {
            var10000 = var32;
            var10001 = false;
            break label251;
         }
      }

      while(true) {
         Throwable var36 = var10000;

         try {
            throw var36;
         } catch (Throwable var31) {
            var10000 = var31;
            var10001 = false;
            continue;
         }
      }
   }

   public void run() {
      if (!Thread.currentThread().getName().equals("DiscoveryListener daemon")) {
         throw new RuntimeException("No right to call this method");
      } else {
         this.inService = true;

         while(this.inService) {
            try {
               this.listenBroadCast();
            } catch (SocketTimeoutException var3) {
            } catch (IOException var4) {
               log.log(Level.SEVERE, "IO Exception during UPNP DiscoveryListener messages listening thread", var4);
            } catch (Exception var5) {
               log.log(Level.SEVERE, "Fatal Error during UPNP DiscoveryListener messages listening thread, thread will exit", var5);
               this.inService = false;
            }
         }

         try {
            this.skt.leaveGroup(InetAddress.getByName("239.255.255.250"));
            this.skt.close();
         } catch (Exception var2) {
         }

      }
   }

   public void setDaemon(boolean var1) {
      this.daemon = var1;
   }

   public void unRegisterResultsHandler(DiscoveryResultsHandler var1, String var2) {
      Object var3 = this.REGISTRATION_PROCESS;
      synchronized(var3){}

      Throwable var10000;
      boolean var10001;
      label277: {
         Set var4;
         try {
            var4 = (Set)this.registeredHandlers.get(var2);
         } catch (Throwable var34) {
            var10000 = var34;
            var10001 = false;
            break label277;
         }

         if (var4 != null) {
            try {
               var4.remove(var1);
               if (var4.size() == 0) {
                  this.registeredHandlers.remove(var2);
               }
            } catch (Throwable var33) {
               var10000 = var33;
               var10001 = false;
               break label277;
            }
         }

         try {
            if (this.registeredHandlers.size() == 0) {
               this.stopDevicesListenerThread();
            }
         } catch (Throwable var32) {
            var10000 = var32;
            var10001 = false;
            break label277;
         }

         label263:
         try {
            return;
         } catch (Throwable var31) {
            var10000 = var31;
            var10001 = false;
            break label263;
         }
      }

      while(true) {
         Throwable var35 = var10000;

         try {
            throw var35;
         } catch (Throwable var30) {
            var10000 = var30;
            var10001 = false;
            continue;
         }
      }
   }
}
