package net.sbbi.upnp.devices;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.xml.xpath.XPathException;
import net.sbbi.upnp.services.UPNPService;
import net.sbbi.upnp.xpath.JXPathContext;
import org.w3c.dom.Node;

public class UPNPRootDevice extends UPNPDevice {
   private static final Logger log = Logger.getLogger(UPNPRootDevice.class.getName());
   private URL URLBase;
   private long creationTime;
   private URL deviceDefLoc;
   private String deviceDefLocData;
   private String discoveryUDN;
   private String discoveryUSN;
   private int specVersionMajor;
   private int specVersionMinor;
   private long validityTime;
   private String vendorFirmware;

   public UPNPRootDevice(URL param1, String param2) throws IllegalStateException {
      // $FF: Couldn't be decompiled
   }

   public UPNPRootDevice(URL var1, String var2, String var3) throws MalformedURLException, IllegalStateException {
      this(var1, var2);
      this.vendorFirmware = var3;
   }

   public UPNPRootDevice(URL var1, String var2, String var3, String var4, String var5) throws MalformedURLException, IllegalStateException {
      this(var1, var2);
      this.vendorFirmware = var3;
      this.discoveryUSN = var4;
      this.discoveryUDN = var5;
   }

   private void fillUPNPDevice(UPNPDevice var1, UPNPDevice var2, JXPathContext var3, URL var4) throws MalformedURLException, XPathException {
      var1.deviceType = this.getMandatoryData(var3, "deviceType");
      Logger var5 = log;
      StringBuilder var6 = new StringBuilder("parsing device ");
      var6.append(var1.deviceType);
      var5.fine(var6.toString());
      var1.friendlyName = this.getMandatoryData(var3, "friendlyName");
      var1.manufacturer = this.getNonMandatoryData(var3, "manufacturer");
      String var18 = this.getNonMandatoryData(var3, "manufacturerURL");
      if (var18 != null) {
         try {
            URL var15 = new URL(var18);
            var1.manufacturerURL = var15;
         } catch (MalformedURLException var12) {
         }
      }

      try {
         var1.presentationURL = getURL(this.getNonMandatoryData(var3, "presentationURL"), this.URLBase);
      } catch (MalformedURLException var11) {
      }

      var1.modelDescription = this.getNonMandatoryData(var3, "modelDescription");
      var1.modelName = this.getMandatoryData(var3, "modelName");
      var1.modelNumber = this.getNonMandatoryData(var3, "modelNumber");
      var1.modelURL = this.getNonMandatoryData(var3, "modelURL");
      var1.serialNumber = this.getNonMandatoryData(var3, "serialNumber");
      var1.UDN = this.getMandatoryData(var3, "UDN");
      var1.USN = this.UDN.concat("::").concat(this.deviceType);
      var18 = this.getNonMandatoryData(var3, "UPC");
      if (var18 != null) {
         try {
            var1.UPC = Long.parseLong(var18);
         } catch (Exception var10) {
         }
      }

      var1.parent = var2;
      this.fillUPNPServicesList(var1, var3);
      this.fillUPNPDeviceIconsList(var1, var3, this.URLBase);

      Node var13;
      try {
         var13 = var3.getPointer("deviceList");
      } catch (XPathException var9) {
         return;
      }

      var3 = var3.getRelativeContext(var13);
      Double var14 = var3.getNumber("count( device )");
      var1.childDevices = new ArrayList();
      Logger var19 = log;
      StringBuilder var16 = new StringBuilder("child devices count is ");
      var16.append(var14);
      var19.fine(var16.toString());

      for(int var7 = 1; var7 <= var14.intValue(); ++var7) {
         var6 = new StringBuilder("device[");
         var6.append(var7);
         var6.append("]");
         JXPathContext var17 = var3.getRelativeContext(var3.getPointer(var6.toString()));
         UPNPDevice var20 = new UPNPDevice();
         this.fillUPNPDevice(var20, var1, var17, var4);
         Logger var8 = log;
         var16 = new StringBuilder("adding child device ");
         var16.append(var20.getDeviceType());
         var8.fine(var16.toString());
         var1.childDevices.add(var20);
      }

   }

   private void fillUPNPDeviceIconsList(UPNPDevice var1, JXPathContext var2, URL var3) throws MalformedURLException, XPathException {
      Node var4;
      try {
         var4 = var2.getPointer("iconList");
      } catch (XPathException var9) {
         return;
      }

      JXPathContext var11 = var2.getRelativeContext(var4);
      Double var10 = var11.getNumber("count( icon )");
      Logger var5 = log;
      StringBuilder var6 = new StringBuilder("device icons count is ");
      var6.append(var10);
      var5.fine(var6.toString());
      var1.deviceIcons = new ArrayList();

      for(int var7 = 1; var7 <= var10.intValue(); ++var7) {
         DeviceIcon var13 = new DeviceIcon();
         StringBuilder var12 = new StringBuilder("icon[");
         var12.append(var7);
         var12.append("]/mimetype");
         var13.mimeType = var11.getString(var12.toString());
         var12 = new StringBuilder("icon[");
         var12.append(var7);
         var12.append("]/width");
         var13.width = Integer.parseInt(var11.getString(var12.toString()));
         var12 = new StringBuilder("icon[");
         var12.append(var7);
         var12.append("]/height");
         var13.height = Integer.parseInt(var11.getString(var12.toString()));
         var12 = new StringBuilder("icon[");
         var12.append(var7);
         var12.append("]/depth");
         var13.depth = Integer.parseInt(var11.getString(var12.toString()));
         var12 = new StringBuilder("icon[");
         var12.append(var7);
         var12.append("]/url");
         var13.url = getURL(var11.getString(var12.toString()), var3);
         var5 = log;
         StringBuilder var8 = new StringBuilder("icon URL is ");
         var8.append(var13.url);
         var5.fine(var8.toString());
         var1.deviceIcons.add(var13);
      }

   }

   private void fillUPNPServicesList(UPNPDevice var1, JXPathContext var2) throws MalformedURLException, XPathException {
      JXPathContext var3 = var2.getRelativeContext(var2.getPointer("serviceList"));
      Double var4 = var3.getNumber("count( service )");
      Logger var7 = log;
      StringBuilder var5 = new StringBuilder("device services count is ");
      var5.append(var4);
      var7.fine(var5.toString());
      var1.services = new ArrayList();

      for(int var6 = 1; var6 <= var4.intValue(); ++var6) {
         StringBuilder var8 = new StringBuilder("service[");
         var8.append(var6);
         var8.append("]");
         JXPathContext var11 = var3.getRelativeContext(var3.getPointer(var8.toString()));
         URL var9 = this.URLBase;
         if (var9 == null) {
            var9 = this.deviceDefLoc;
         }

         UPNPService var10 = new UPNPService(var11, var9, this);
         var1.services.add(var10);
      }

   }

   private String getMandatoryData(JXPathContext var1, String var2) throws XPathException {
      String var3 = var1.getString(var2);
      if (var3 != null && var3.length() == 0) {
         StringBuilder var4 = new StringBuilder("Mandatory field ");
         var4.append(var2);
         var4.append(" not provided, uncompliant UPNP device !!");
         throw new XPathException(var4.toString());
      } else {
         return var3;
      }
   }

   private String getNonMandatoryData(JXPathContext var1, String var2) {
      Object var3 = null;

      int var4;
      String var7;
      label39: {
         label32: {
            boolean var10001;
            try {
               var7 = var1.getString(var2);
            } catch (XPathException var6) {
               var10001 = false;
               break label32;
            }

            if (var7 == null) {
               return var7;
            }

            try {
               var4 = var7.length();
               break label39;
            } catch (XPathException var5) {
               var10001 = false;
            }
         }

         var7 = (String)var3;
         return var7;
      }

      if (var4 == 0) {
         var7 = (String)var3;
      }

      return var7;
   }

   public static final URL getURL(String var0, URL var1) throws MalformedURLException {
      if (var0 != null && var0.trim().length() != 0) {
         URL var6;
         URL var7;
         try {
            var7 = new URL(var0);
         } catch (MalformedURLException var3) {
            if (var1 != null) {
               String var2 = var0.replace('\\', '/');
               StringBuilder var4;
               if (var2.charAt(0) != '/') {
                  String var5 = var1.toExternalForm();
                  var0 = var5;
                  if (!var5.endsWith("/")) {
                     var4 = new StringBuilder(String.valueOf(var5));
                     var4.append("/");
                     var0 = var4.toString();
                  }

                  var4 = new StringBuilder(String.valueOf(var0));
                  var4.append(var2);
                  var6 = new URL(var4.toString());
               } else {
                  var4 = new StringBuilder(String.valueOf(var1.getProtocol()));
                  var4.append("://");
                  var4.append(var1.getHost());
                  var4.append(":");
                  var4.append(var1.getPort());
                  var4 = new StringBuilder(String.valueOf(var4.toString()));
                  var4.append(var2);
                  var6 = new URL(var4.toString());
               }

               return var6;
            }

            throw var3;
         }

         var6 = var7;
         return var6;
      } else {
         return null;
      }
   }

   public URL getDeviceDefLoc() {
      return this.deviceDefLoc;
   }

   public String getDeviceDefLocData() {
      if (this.deviceDefLocData == null) {
         InputStream var1;
         boolean var10001;
         byte[] var2;
         StringBuffer var3;
         try {
            var1 = this.deviceDefLoc.openConnection().getInputStream();
            var2 = new byte[512];
            var3 = new StringBuffer();
         } catch (IOException var9) {
            var10001 = false;
            return null;
         }

         while(true) {
            int var4;
            try {
               var4 = var1.read(var2);
            } catch (IOException var7) {
               var10001 = false;
               break;
            }

            if (var4 == -1) {
               try {
                  var1.close();
                  this.deviceDefLocData = var3.toString();
                  return this.deviceDefLocData;
               } catch (IOException var6) {
                  var10001 = false;
                  break;
               }
            }

            try {
               String var5 = new String(var2, 0, var4);
               var3.append(var5);
            } catch (IOException var8) {
               var10001 = false;
               break;
            }
         }

         return null;
      } else {
         return this.deviceDefLocData;
      }
   }

   public String getDiscoveryUDN() {
      return this.discoveryUDN;
   }

   public String getDiscoveryUSN() {
      return this.discoveryUSN;
   }

   public int getSpecVersionMajor() {
      return this.specVersionMajor;
   }

   public int getSpecVersionMinor() {
      return this.specVersionMinor;
   }

   public URL getURLBase() {
      return this.URLBase;
   }

   public long getValidityTime() {
      long var1 = System.currentTimeMillis();
      long var3 = this.creationTime;
      return this.validityTime - (var1 - var3);
   }

   public String getVendorFirmware() {
      return this.vendorFirmware;
   }

   public void resetValidityTime(String var1) {
      this.validityTime = (long)(Integer.parseInt(var1) * 1000);
      this.creationTime = System.currentTimeMillis();
   }
}
