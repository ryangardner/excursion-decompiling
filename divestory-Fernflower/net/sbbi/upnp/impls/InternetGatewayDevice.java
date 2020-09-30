package net.sbbi.upnp.impls;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sbbi.upnp.Discovery;
import net.sbbi.upnp.devices.UPNPDevice;
import net.sbbi.upnp.devices.UPNPRootDevice;
import net.sbbi.upnp.messages.ActionMessage;
import net.sbbi.upnp.messages.ActionResponse;
import net.sbbi.upnp.messages.StateVariableMessage;
import net.sbbi.upnp.messages.StateVariableResponse;
import net.sbbi.upnp.messages.UPNPMessageFactory;
import net.sbbi.upnp.messages.UPNPResponseException;
import net.sbbi.upnp.services.UPNPService;

public class InternetGatewayDevice {
   private static final Logger log = Logger.getLogger(InternetGatewayDevice.class.getName());
   private UPNPRootDevice igd;
   private UPNPMessageFactory msgFactory;

   public InternetGatewayDevice(UPNPRootDevice var1) throws UnsupportedOperationException {
      this(var1, true, true);
   }

   private InternetGatewayDevice(UPNPRootDevice var1, boolean var2, boolean var3) throws UnsupportedOperationException {
      this.igd = var1;
      UPNPDevice var4 = var1.getChildDevice("urn:schemas-upnp-org:device:WANConnectionDevice:1");
      if (var4 != null) {
         UPNPService var5 = var4.getService("urn:schemas-upnp-org:service:WANIPConnection:1");
         UPNPService var7 = var4.getService("urn:schemas-upnp-org:service:WANPPPConnection:1");
         if (var2 && var3 && var5 == null && var7 == null) {
            throw new UnsupportedOperationException("Unable to find any urn:schemas-upnp-org:service:WANIPConnection:1 or urn:schemas-upnp-org:service:WANPPPConnection:1 service");
         } else if (var2 && !var3 && var5 == null) {
            throw new UnsupportedOperationException("Unable to find any urn:schemas-upnp-org:service:WANIPConnection:1 service");
         } else if (!var2 && var3 && var7 == null) {
            throw new UnsupportedOperationException("Unable to find any urn:schemas-upnp-org:service:WANPPPConnection:1 service");
         } else {
            if (var5 != null && var7 == null) {
               this.msgFactory = UPNPMessageFactory.getNewInstance(var5);
            } else if (var7 != null && var5 == null) {
               this.msgFactory = UPNPMessageFactory.getNewInstance(var7);
            } else {
               if (this.testWANInterface(var5)) {
                  this.msgFactory = UPNPMessageFactory.getNewInstance(var5);
               } else if (this.testWANInterface(var7)) {
                  this.msgFactory = UPNPMessageFactory.getNewInstance(var7);
               }

               if (this.msgFactory == null) {
                  log.warning("Unable to detect active WANIPConnection, dfaulting to urn:schemas-upnp-org:service:WANIPConnection:1");
                  this.msgFactory = UPNPMessageFactory.getNewInstance(var5);
               }
            }

         }
      } else {
         StringBuilder var6 = new StringBuilder("device urn:schemas-upnp-org:device:WANConnectionDevice:1 not supported by IGD device ");
         var6.append(var1.getModelName());
         throw new UnsupportedOperationException(var6.toString());
      }
   }

   private void checkPortMappingProtocol(String var1) throws IllegalArgumentException {
      if (var1 == null || !var1.equals("TCP") && !var1.equals("UDP")) {
         throw new IllegalArgumentException("PortMappingProtocol must be either TCP or UDP");
      }
   }

   private void checkPortRange(int var1) throws IllegalArgumentException {
      if (var1 < 1 || var1 > 65535) {
         throw new IllegalArgumentException("Port range must be between 1 and 65535");
      }
   }

   public static InternetGatewayDevice[] getDevices(int var0) throws IOException {
      return lookupDeviceDevices(var0, 4, 3, true, true, (NetworkInterface)null);
   }

   public static InternetGatewayDevice[] getDevices(int var0, int var1, int var2, NetworkInterface var3) throws IOException {
      return lookupDeviceDevices(var0, var1, var2, true, true, var3);
   }

   public static InternetGatewayDevice[] getIPDevices(int var0) throws IOException {
      return lookupDeviceDevices(var0, 4, 3, true, false, (NetworkInterface)null);
   }

   public static InternetGatewayDevice[] getPPPDevices(int var0) throws IOException {
      return lookupDeviceDevices(var0, 4, 3, false, true, (NetworkInterface)null);
   }

   private static InternetGatewayDevice[] lookupDeviceDevices(int var0, int var1, int var2, boolean var3, boolean var4, NetworkInterface var5) throws IOException {
      UPNPRootDevice[] var12;
      if (var0 == -1) {
         var12 = Discovery.discover(1500, var1, var2, "urn:schemas-upnp-org:device:InternetGatewayDevice:1", var5);
      } else {
         var12 = Discovery.discover(var0, var1, var2, "urn:schemas-upnp-org:device:InternetGatewayDevice:1", var5);
      }

      InternetGatewayDevice[] var6 = null;
      if (var12 != null) {
         HashSet var7 = new HashSet();
         byte var11 = 0;

         for(var0 = 0; var0 < var12.length; ++var0) {
            try {
               InternetGatewayDevice var13 = new InternetGatewayDevice(var12[var0], var3, var4);
               var7.add(var13);
            } catch (UnsupportedOperationException var10) {
               Logger var8 = log;
               StringBuilder var9 = new StringBuilder("UnsupportedOperationException during discovery ");
               var9.append(var10.getMessage());
               var8.fine(var9.toString());
            }
         }

         if (var7.size() == 0) {
            return null;
         }

         var6 = new InternetGatewayDevice[var7.size()];
         Iterator var14 = var7.iterator();

         for(var0 = var11; var14.hasNext(); ++var0) {
            var6[var0] = (InternetGatewayDevice)var14.next();
         }
      }

      return var6;
   }

   private boolean testWANInterface(UPNPService var1) {
      ActionMessage var5 = UPNPMessageFactory.getNewInstance(var1).getMessage("GetExternalIPAddress");

      String var6;
      label32: {
         try {
            var6 = var5.service().getOutActionArgumentValue("NewExternalIPAddress");
            break label32;
         } catch (UPNPResponseException var3) {
         } catch (IOException var4) {
            log.log(Level.WARNING, "IOException occured during device detection", var4);
         }

         var6 = null;
      }

      if (var6 != null && var6.length() > 0 && !var6.equals("0.0.0.0")) {
         InetAddress var7;
         try {
            var7 = InetAddress.getByName(var6);
         } catch (UnknownHostException var2) {
            return false;
         }

         if (var7 != null) {
            return true;
         }
      }

      return false;
   }

   public boolean addPortMapping(String var1, String var2, int var3, int var4, String var5, int var6, String var7) throws IOException, UPNPResponseException {
      String var8 = var2;
      if (var2 == null) {
         var8 = "";
      }

      this.checkPortMappingProtocol(var7);
      if (var4 != 0) {
         this.checkPortRange(var4);
      }

      this.checkPortRange(var3);
      var2 = var1;
      if (var1 == null) {
         var2 = "";
      }

      if (var6 >= 0) {
         ActionMessage var11 = this.msgFactory.getMessage("AddPortMapping");
         var11.setInputParameter("NewRemoteHost", var8).setInputParameter("NewExternalPort", var4).setInputParameter("NewProtocol", var7).setInputParameter("NewInternalPort", var3).setInputParameter("NewInternalClient", var5).setInputParameter("NewEnabled", true).setInputParameter("NewPortMappingDescription", var2).setInputParameter("NewLeaseDuration", var6);

         try {
            var11.service();
            return true;
         } catch (UPNPResponseException var9) {
            if (var9.getDetailErrorCode() == 718) {
               return false;
            } else {
               throw var9;
            }
         }
      } else {
         StringBuilder var10 = new StringBuilder("Invalid leaseDuration (");
         var10.append(var6);
         var10.append(") value");
         throw new IllegalArgumentException(var10.toString());
      }
   }

   public boolean deletePortMapping(String var1, int var2, String var3) throws IOException, UPNPResponseException {
      String var4 = var1;
      if (var1 == null) {
         var4 = "";
      }

      this.checkPortMappingProtocol(var3);
      this.checkPortRange(var2);
      ActionMessage var6 = this.msgFactory.getMessage("DeletePortMapping");
      var6.setInputParameter("NewRemoteHost", var4).setInputParameter("NewExternalPort", var2).setInputParameter("NewProtocol", var3);

      try {
         var6.service();
         return true;
      } catch (UPNPResponseException var5) {
         if (var5.getDetailErrorCode() == 714) {
            return false;
         } else {
            throw var5;
         }
      }
   }

   public String getExternalIPAddress() throws UPNPResponseException, IOException {
      return this.msgFactory.getMessage("GetExternalIPAddress").service().getOutActionArgumentValue("NewExternalIPAddress");
   }

   public ActionResponse getGenericPortMappingEntry(int var1) throws IOException, UPNPResponseException {
      ActionMessage var2 = this.msgFactory.getMessage("GetGenericPortMappingEntry");
      var2.setInputParameter("NewPortMappingIndex", var1);

      try {
         ActionResponse var4 = var2.service();
         return var4;
      } catch (UPNPResponseException var3) {
         if (var3.getDetailErrorCode() == 714) {
            return null;
         } else {
            throw var3;
         }
      }
   }

   public UPNPRootDevice getIGDRootDevice() {
      return this.igd;
   }

   public Integer getNatMappingsCount() throws IOException, UPNPResponseException {
      StateVariableMessage var1 = this.msgFactory.getStateVariableMessage("PortMappingNumberOfEntries");

      Integer var4;
      try {
         StateVariableResponse var2 = var1.service();
         var4 = new Integer(var2.getStateVariableValue());
      } catch (UPNPResponseException var3) {
         if (var3.getDetailErrorCode() != 404) {
            throw var3;
         }

         var4 = null;
      }

      return var4;
   }

   public Integer getNatTableSize() throws IOException, UPNPResponseException {
      int var1 = 0;
      int var2 = 0;

      while(true) {
         if (var2 >= 50) {
            var2 = -1;
            break;
         }

         try {
            this.getGenericPortMappingEntry(var2);
            break;
         } catch (UPNPResponseException var6) {
            if (var6.getDetailErrorCode() != 713 && var6.getDetailErrorCode() != 402) {
               throw var6;
            }

            ++var2;
         }
      }

      int var3 = var2;
      if (var2 == -1) {
         return null;
      } else {
         while(true) {
            try {
               this.getGenericPortMappingEntry(var3);
            } catch (UPNPResponseException var5) {
               if (var5.getDetailErrorCode() != 713 && var5.getDetailErrorCode() != 402) {
                  throw var5;
               }

               return new Integer(var1);
            }

            ++var1;
            ++var3;
         }
      }
   }

   public ActionResponse getSpecificPortMappingEntry(String var1, int var2, String var3) throws IOException, UPNPResponseException {
      String var4 = var1;
      if (var1 == null) {
         var4 = "";
      }

      this.checkPortMappingProtocol(var3);
      this.checkPortRange(var2);
      ActionMessage var6 = this.msgFactory.getMessage("GetSpecificPortMappingEntry");
      var6.setInputParameter("NewRemoteHost", var4).setInputParameter("NewExternalPort", var2).setInputParameter("NewProtocol", var3);

      try {
         ActionResponse var7 = var6.service();
         return var7;
      } catch (UPNPResponseException var5) {
         if (var5.getDetailErrorCode() == 714) {
            return null;
         } else {
            throw var5;
         }
      }
   }
}
