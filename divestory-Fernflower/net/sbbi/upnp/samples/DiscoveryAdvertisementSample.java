package net.sbbi.upnp.samples;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import net.sbbi.upnp.DiscoveryAdvertisement;
import net.sbbi.upnp.DiscoveryEventHandler;

public class DiscoveryAdvertisementSample {
   public static final void main(String[] var0) throws IOException {
      DiscoveryAdvertisementSample.AdvHandler var1 = new DiscoveryAdvertisementSample.AdvHandler((DiscoveryAdvertisementSample.AdvHandler)null);
      DiscoveryAdvertisement.getInstance().setDaemon(false);
      System.out.println("Registering EVENT_SSDP_ALIVE event");
      DiscoveryAdvertisement.getInstance().registerEvent(0, "upnp:rootdevice", var1);
      System.out.println("Registering EVENT_SSDP_BYE_BYE event");
      DiscoveryAdvertisement.getInstance().registerEvent(1, "upnp:rootdevice", var1);
      System.out.println("Waiting for incoming events");
   }

   private static class AdvHandler implements DiscoveryEventHandler {
      private AdvHandler() {
      }

      // $FF: synthetic method
      AdvHandler(DiscoveryAdvertisementSample.AdvHandler var1) {
         this();
      }

      public void eventSSDPAlive(String var1, String var2, String var3, String var4, URL var5) {
         PrintStream var6 = System.out;
         StringBuilder var7 = new StringBuilder("Root device at ");
         var7.append(var5);
         var7.append(" plugged in network, advertisement will expire in ");
         var7.append(var4);
         var7.append(" ms");
         var6.println(var7.toString());
      }

      public void eventSSDPByeBye(String var1, String var2, String var3) {
         PrintStream var4 = System.out;
         StringBuilder var5 = new StringBuilder("Bye Bye usn:");
         var5.append(var1);
         var5.append(" udn:");
         var5.append(var2);
         var5.append(" nt:");
         var5.append(var3);
         var4.println(var5.toString());
      }
   }
}
