package net.sbbi.upnp.devices;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import net.sbbi.upnp.services.UPNPService;

public class UPNPDevice {
   private static final Logger log = Logger.getLogger(UPNPDevice.class.getName());
   protected String UDN;
   protected long UPC;
   protected String USN;
   protected List childDevices;
   protected List deviceIcons;
   protected String deviceType;
   protected String friendlyName;
   protected String manufacturer;
   protected URL manufacturerURL;
   protected String modelDescription;
   protected String modelName;
   protected String modelNumber;
   protected String modelURL;
   protected UPNPDevice parent;
   protected URL presentationURL;
   protected String serialNumber;
   protected List services;

   public UPNPDevice getChildDevice(String var1) {
      Logger var2 = log;
      StringBuilder var3 = new StringBuilder("searching for device URI:");
      var3.append(var1);
      var2.fine(var3.toString());
      if (this.getDeviceType().equals(var1)) {
         return this;
      } else {
         List var4 = this.childDevices;
         if (var4 == null) {
            return null;
         } else {
            Iterator var5 = var4.iterator();

            while(var5.hasNext()) {
               UPNPDevice var6 = ((UPNPDevice)var5.next()).getChildDevice(var1);
               if (var6 != null) {
                  return var6;
               }
            }

            return null;
         }
      }
   }

   public List getChildDevices() {
      if (this.childDevices == null) {
         return null;
      } else {
         ArrayList var1 = new ArrayList();
         Iterator var2 = this.childDevices.iterator();

         while(var2.hasNext()) {
            UPNPDevice var3 = (UPNPDevice)var2.next();
            var1.add(var3);
            List var4 = var3.getChildDevices();
            if (var4 != null) {
               var1.addAll(var4);
            }
         }

         return var1;
      }
   }

   public List getDeviceIcons() {
      return this.deviceIcons;
   }

   public String getDeviceType() {
      return this.deviceType;
   }

   public UPNPDevice getDirectParent() {
      return this.parent;
   }

   public String getFriendlyName() {
      return this.friendlyName;
   }

   public String getManufacturer() {
      return this.manufacturer;
   }

   public URL getManufacturerURL() {
      return this.manufacturerURL;
   }

   public String getModelDescription() {
      return this.modelDescription;
   }

   public String getModelName() {
      return this.modelName;
   }

   public String getModelNumber() {
      return this.modelNumber;
   }

   public String getModelURL() {
      return this.modelURL;
   }

   public URL getPresentationURL() {
      return this.presentationURL;
   }

   public String getSerialNumber() {
      return this.serialNumber;
   }

   public UPNPService getService(String var1) {
      if (this.services == null) {
         return null;
      } else {
         Logger var2 = log;
         StringBuilder var3 = new StringBuilder("searching for service URI:");
         var3.append(var1);
         var2.fine(var3.toString());
         Iterator var5 = this.services.iterator();

         while(var5.hasNext()) {
            UPNPService var4 = (UPNPService)var5.next();
            if (var4.getServiceType().equals(var1)) {
               return var4;
            }
         }

         return null;
      }
   }

   public UPNPService getServiceByID(String var1) {
      if (this.services == null) {
         return null;
      } else {
         Logger var2 = log;
         StringBuilder var3 = new StringBuilder("searching for service ID:");
         var3.append(var1);
         var2.fine(var3.toString());
         Iterator var5 = this.services.iterator();

         while(var5.hasNext()) {
            UPNPService var4 = (UPNPService)var5.next();
            if (var4.getServiceId().equals(var1)) {
               return var4;
            }
         }

         return null;
      }
   }

   public List getServices() {
      if (this.services == null) {
         return null;
      } else {
         ArrayList var1 = new ArrayList();
         var1.addAll(this.services);
         return var1;
      }
   }

   public List getServices(String var1) {
      if (this.services == null) {
         return null;
      } else {
         ArrayList var2 = new ArrayList();
         Logger var3 = log;
         StringBuilder var4 = new StringBuilder("searching for services URI:");
         var4.append(var1);
         var3.fine(var4.toString());
         Iterator var5 = this.services.iterator();

         while(var5.hasNext()) {
            UPNPService var6 = (UPNPService)var5.next();
            if (var6.getServiceType().equals(var1)) {
               var2.add(var6);
            }
         }

         if (var2.size() == 0) {
            return null;
         } else {
            return var2;
         }
      }
   }

   public List getTopLevelChildDevices() {
      if (this.childDevices == null) {
         return null;
      } else {
         ArrayList var1 = new ArrayList();
         Iterator var2 = this.childDevices.iterator();

         while(var2.hasNext()) {
            var1.add((UPNPDevice)var2.next());
         }

         return var1;
      }
   }

   public String getUDN() {
      return this.UDN;
   }

   public long getUPC() {
      return this.UPC;
   }

   public String getUSN() {
      return this.USN;
   }

   public boolean isRootDevice() {
      return this instanceof UPNPRootDevice;
   }

   public String toString() {
      return this.getDeviceType();
   }
}
