package net.sbbi.upnp.samples;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import net.sbbi.upnp.DiscoveryAdvertisement;
import net.sbbi.upnp.DiscoveryEventHandler;
import net.sbbi.upnp.devices.UPNPRootDevice;

public class MyDiscoveryEventsHandler implements DiscoveryEventHandler {
   private Map devices = new HashMap();

   public static void main(String[] var0) throws IOException {
      DiscoveryAdvertisement var2 = DiscoveryAdvertisement.getInstance();
      MyDiscoveryEventsHandler var1 = new MyDiscoveryEventsHandler();
      var2.setDaemon(false);
      var2.registerEvent(0, "upnp:rootdevice", var1);
   }

   public void eventSSDPAlive(String var1, String var2, String var3, String var4, URL var5) {
      PrintStream var6 = System.out;
      StringBuilder var8 = new StringBuilder("Device ");
      var8.append(var1);
      var8.append(" at ");
      var8.append(var5);
      var8.append(" of type ");
      var8.append(var3);
      var8.append(" alive");
      var6.println(var8.toString());
      if (this.devices.get(var1) == null) {
         PrintStream var9;
         StringBuilder var11;
         try {
            UPNPRootDevice var10 = new UPNPRootDevice(var5, var4);
            this.devices.put(var1, var10);
            var9 = System.out;
            var11 = new StringBuilder("Device ");
            var11.append(var1);
            var11.append(" added");
            var9.println(var11.toString());
         } catch (IllegalStateException var7) {
            var9 = System.err;
            var11 = new StringBuilder("! Error: ");
            var11.append(var7);
            var9.println(var11.toString());
         }
      }

   }

   public void eventSSDPByeBye(String var1, String var2, String var3) {
      if (this.devices.get(var1) != null) {
         this.devices.remove(var1);
         PrintStream var5 = System.out;
         StringBuilder var4 = new StringBuilder("Device ");
         var4.append(var1);
         var4.append(" leaves");
         var5.println(var4.toString());
      }

   }
}
