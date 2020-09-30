package net.sbbi.upnp.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import javax.xml.xpath.XPathException;
import net.sbbi.upnp.devices.UPNPDevice;
import net.sbbi.upnp.devices.UPNPRootDevice;
import net.sbbi.upnp.xpath.JXPathContext;
import org.w3c.dom.Node;

public class UPNPService {
   protected URL SCPDURL;
   protected String SCPDURLData;
   protected Map UPNPServiceActions;
   protected Map UPNPServiceStateVariables;
   private String USN;
   protected URL controlURL;
   protected URL eventSubURL;
   private boolean parsedSCPD = false;
   protected String serviceId;
   protected UPNPDevice serviceOwnerDevice;
   protected String serviceType;
   private int specVersionMajor;
   private int specVersionMinor;

   public UPNPService(JXPathContext var1, URL var2, UPNPDevice var3) throws MalformedURLException, XPathException {
      this.serviceOwnerDevice = var3;
      this.serviceType = var1.getString("serviceType");
      this.serviceId = var1.getString("serviceId");
      this.SCPDURL = UPNPRootDevice.getURL(var1.getString("SCPDURL"), var2);
      this.controlURL = UPNPRootDevice.getURL(var1.getString("controlURL"), var2);
      this.eventSubURL = UPNPRootDevice.getURL(var1.getString("eventSubURL"), var2);
      this.USN = var3.getUDN().concat("::").concat(this.serviceType);
   }

   private void lazyInitiate() {
      if (!this.parsedSCPD) {
         synchronized(this){}

         Throwable var10000;
         boolean var10001;
         label144: {
            try {
               if (!this.parsedSCPD) {
                  this.parseSCPD();
               }
            } catch (Throwable var13) {
               var10000 = var13;
               var10001 = false;
               break label144;
            }

            label141:
            try {
               return;
            } catch (Throwable var12) {
               var10000 = var12;
               var10001 = false;
               break label141;
            }
         }

         while(true) {
            Throwable var1 = var10000;

            try {
               throw var1;
            } catch (Throwable var11) {
               var10000 = var11;
               var10001 = false;
               continue;
            }
         }
      }
   }

   private void parseSCPD() {
      // $FF: Couldn't be decompiled
   }

   private void parseServiceStateVariables(JXPathContext var1) throws XPathException {
      JXPathContext var2 = var1.getRelativeContext(var1.getPointer("serviceStateTable"));
      Double var3 = var2.getNumber("count( stateVariable )");
      this.UPNPServiceStateVariables = new HashMap();

      for(int var4 = 1; var4 <= var3.intValue(); ++var4) {
         ServiceStateVariable var5 = new ServiceStateVariable();

         String var15;
         StringBuilder var16;
         try {
            var16 = new StringBuilder("stateVariable[");
            var16.append(var4);
            var16.append("]/@sendEvents");
            var15 = var2.getString(var16.toString());
         } catch (XPathException var14) {
            var15 = "yes";
         }

         var5.parent = this;
         var5.sendEvents = var15.equalsIgnoreCase("no") ^ true;
         var16 = new StringBuilder("stateVariable[");
         var16.append(var4);
         var16.append("]/name");
         var5.name = var2.getString(var16.toString());
         var16 = new StringBuilder("stateVariable[");
         var16.append(var4);
         var16.append("]/dataType");
         var5.dataType = var2.getString(var16.toString());

         try {
            var16 = new StringBuilder("stateVariable[");
            var16.append(var4);
            var16.append("]/defaultValue");
            var5.defaultValue = var2.getString(var16.toString());
         } catch (XPathException var13) {
         }

         Object var6 = null;

         Node var17;
         try {
            var16 = new StringBuilder("stateVariable[");
            var16.append(var4);
            var16.append("]/allowedValueList");
            var17 = var2.getPointer(var16.toString());
         } catch (XPathException var12) {
            var17 = null;
         }

         if (var17 != null) {
            var1 = var2.getRelativeContext(var17);
            Double var7 = var1.getNumber("count( allowedValue )");
            var5.allowedvalues = new HashSet();

            for(int var8 = 1; var8 <= var7.intValue(); ++var8) {
               StringBuilder var9 = new StringBuilder("allowedValue[");
               var9.append(var8);
               var9.append("]");
               String var18 = var1.getString(var9.toString());
               var5.allowedvalues.add(var18);
            }
         }

         try {
            var16 = new StringBuilder("stateVariable[");
            var16.append(var4);
            var16.append("]/allowedValueRange");
            var17 = var2.getPointer(var16.toString());
         } catch (XPathException var11) {
            var17 = (Node)var6;
         }

         if (var17 != null) {
            var16 = new StringBuilder("stateVariable[");
            var16.append(var4);
            var16.append("]/allowedValueRange/minimum");
            var5.minimumRangeValue = var2.getString(var16.toString());
            var16 = new StringBuilder("stateVariable[");
            var16.append(var4);
            var16.append("]/allowedValueRange/maximum");
            var5.maximumRangeValue = var2.getString(var16.toString());

            try {
               var16 = new StringBuilder("stateVariable[");
               var16.append(var4);
               var16.append("]/allowedValueRange/step");
               var5.stepRangeValue = var2.getString(var16.toString());
            } catch (XPathException var10) {
            }
         }

         this.UPNPServiceStateVariables.put(var5.getName(), var5);
      }

   }

   public Iterator getAvailableActionsName() {
      this.lazyInitiate();
      return this.UPNPServiceActions.keySet().iterator();
   }

   public int getAvailableActionsSize() {
      this.lazyInitiate();
      return this.UPNPServiceActions.keySet().size();
   }

   public Iterator getAvailableStateVariableName() {
      this.lazyInitiate();
      return this.UPNPServiceStateVariables.keySet().iterator();
   }

   public int getAvailableStateVariableSize() {
      this.lazyInitiate();
      return this.UPNPServiceStateVariables.keySet().size();
   }

   public URL getControlURL() {
      return this.controlURL;
   }

   public URL getEventSubURL() {
      return this.eventSubURL;
   }

   public String getSCDPData() {
      if (this.SCPDURLData == null) {
         InputStream var1;
         boolean var10001;
         byte[] var2;
         StringBuffer var3;
         try {
            var1 = this.SCPDURL.openConnection().getInputStream();
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
                  this.SCPDURLData = var3.toString();
                  return this.SCPDURLData;
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
         return this.SCPDURLData;
      }
   }

   public URL getSCPDURL() {
      return this.SCPDURL;
   }

   public String getServiceId() {
      return this.serviceId;
   }

   public UPNPDevice getServiceOwnerDevice() {
      return this.serviceOwnerDevice;
   }

   public String getServiceType() {
      return this.serviceType;
   }

   public int getSpecVersionMajor() {
      this.lazyInitiate();
      return this.specVersionMajor;
   }

   public int getSpecVersionMinor() {
      this.lazyInitiate();
      return this.specVersionMinor;
   }

   public ServiceAction getUPNPServiceAction(String var1) {
      this.lazyInitiate();
      return (ServiceAction)this.UPNPServiceActions.get(var1);
   }

   public ServiceStateVariable getUPNPServiceStateVariable(String var1) {
      this.lazyInitiate();
      return (ServiceStateVariable)this.UPNPServiceStateVariables.get(var1);
   }

   public String getUSN() {
      return this.USN;
   }
}
