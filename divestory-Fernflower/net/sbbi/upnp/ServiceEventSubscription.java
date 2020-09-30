package net.sbbi.upnp;

import java.net.InetAddress;
import java.net.URL;

public class ServiceEventSubscription {
   private String SID = null;
   private InetAddress deviceIp = null;
   private int durationTime = 0;
   private String serviceId = null;
   private String serviceType = null;
   private URL serviceURL = null;

   public ServiceEventSubscription(String var1, String var2, URL var3, String var4, InetAddress var5, int var6) {
      this.serviceType = var1;
      this.serviceId = var2;
      this.serviceURL = var3;
      this.SID = var4;
      this.deviceIp = var5;
      this.durationTime = var6;
   }

   public InetAddress getDeviceIp() {
      return this.deviceIp;
   }

   public int getDurationTime() {
      return this.durationTime;
   }

   public String getSID() {
      return this.SID;
   }

   public String getServiceId() {
      return this.serviceId;
   }

   public String getServiceType() {
      return this.serviceType;
   }

   public URL getServiceURL() {
      return this.serviceURL;
   }
}
