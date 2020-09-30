package net.sbbi.upnp.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import net.sbbi.upnp.services.ISO8601Date;
import net.sbbi.upnp.services.ServiceAction;
import net.sbbi.upnp.services.ServiceActionArgument;
import net.sbbi.upnp.services.ServiceStateVariable;
import net.sbbi.upnp.services.UPNPService;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ActionMessage {
   private static final Map XML_ENTITIES;
   private static final Logger log = Logger.getLogger(ActionMessage.class.getName());
   private List inputParameters;
   private UPNPService service;
   private ServiceAction serviceAction;

   static {
      HashMap var0 = new HashMap();
      XML_ENTITIES = var0;
      var0.put('"', "quot");
      XML_ENTITIES.put('&', "amp");
      XML_ENTITIES.put('<', "lt");
      XML_ENTITIES.put('>', "gt");
      XML_ENTITIES.put('\'', "apos");
   }

   protected ActionMessage(UPNPService var1, ServiceAction var2) {
      this.service = var1;
      this.serviceAction = var2;
      if (var2.getInputActionArguments() != null) {
         this.inputParameters = new ArrayList();
      }

   }

   private String getResponseBody(InputStream var1) throws IOException {
      byte[] var2 = new byte[256];
      StringBuffer var3 = new StringBuffer(256);

      while(true) {
         int var4 = var1.read(var2);
         if (var4 == -1) {
            var4 = var3.length();

            while(var3.charAt(var4 - 1) == 0) {
               --var4;
               var3.setLength(var4);
            }

            return var3.toString().trim();
         }

         var3.append(new String(var2, 0, var4, "UTF-8"));
      }
   }

   public void clearInputParameters() {
      this.inputParameters.clear();
   }

   public String escape(String var1) {
      StringBuffer var2 = new StringBuffer(var1.length() * 2);

      for(int var3 = 0; var3 < var1.length(); ++var3) {
         char var4 = var1.charAt(var3);
         String var5 = (String)XML_ENTITIES.get(var4);
         if (var5 == null) {
            if (var4 > 127) {
               var2.append("&#");
               var2.append(var4);
               var2.append(';');
            } else {
               var2.append(var4);
            }
         } else {
            var2.append('&');
            var2.append(var5);
            var2.append(';');
         }
      }

      return var2.toString();
   }

   public List getInputParameterNames() {
      return this.serviceAction.getInputActionArgumentsNames();
   }

   public List getOutputParameterNames() {
      return this.serviceAction.getOutputActionArgumentsNames();
   }

   public ActionResponse service() throws IOException, UPNPResponseException {
      StringBuffer var1 = new StringBuffer(256);
      var1.append("<?xml version=\"1.0\"?>\r\n");
      var1.append("<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\"");
      var1.append(" s:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">");
      var1.append("<s:Body>");
      var1.append("<u:");
      var1.append(this.serviceAction.getName());
      var1.append(" xmlns:u=\"");
      var1.append(this.service.getServiceType());
      var1.append("\">");
      if (this.serviceAction.getInputActionArguments() != null) {
         Iterator var2 = this.inputParameters.iterator();

         while(var2.hasNext()) {
            ActionMessage.InputParamContainer var3 = (ActionMessage.InputParamContainer)var2.next();
            var1.append("<");
            var1.append(var3.name);
            var1.append(">");
            var1.append(var3.value);
            var1.append("</");
            var1.append(var3.name);
            var1.append(">");
         }
      }

      var1.append("</u:");
      var1.append(this.serviceAction.getName());
      var1.append(">");
      var1.append("</s:Body>");
      var1.append("</s:Envelope>");
      Logger var27 = log;
      StringBuilder var23 = new StringBuilder("POST prepared for URL ");
      var23.append(this.service.getControlURL());
      var27.fine(var23.toString());
      URL var29 = new URL(this.service.getControlURL().toString());
      HttpURLConnection var4 = (HttpURLConnection)var29.openConnection();
      var4.setDoInput(true);
      var4.setDoOutput(true);
      var4.setUseCaches(false);
      var4.setRequestMethod("POST");
      HttpURLConnection.setFollowRedirects(false);
      var23 = new StringBuilder(String.valueOf(var29.getHost()));
      var23.append(":");
      var23.append(var29.getPort());
      var4.setRequestProperty("HOST", var23.toString());
      var4.setRequestProperty("CONTENT-TYPE", "text/xml; charset=\"utf-8\"");
      var4.setRequestProperty("CONTENT-LENGTH", Integer.toString(var1.length()));
      StringBuilder var31 = new StringBuilder("\"");
      var31.append(this.service.getServiceType());
      var31.append("#");
      var31.append(this.serviceAction.getName());
      var31.append("\"");
      var4.setRequestProperty("SOAPACTION", var31.toString());
      OutputStream var5 = var4.getOutputStream();
      var5.write(var1.toString().getBytes());
      var5.flush();
      var5.close();
      var4.connect();
      Logger var25 = log;
      var31 = new StringBuilder("executing query :\n");
      var31.append(var1);
      var25.fine(var31.toString());

      InputStream var22;
      try {
         var22 = var4.getInputStream();
      } catch (IOException var18) {
         var22 = var4.getErrorStream();
      }

      UPNPResponseException var28 = null;
      Object var24;
      Object var35;
      if (var22 != null) {
         int var6 = var4.getResponseCode();
         String var30 = this.getResponseBody(var22);
         var27 = log;
         StringBuilder var7 = new StringBuilder("received response :\n");
         var7.append(var30);
         var27.fine(var7.toString());
         SAXParserFactory var37 = SAXParserFactory.newInstance();
         var37.setValidating(false);
         var37.setNamespaceAware(true);
         ActionMessageResponseParser var33 = new ActionMessageResponseParser(this.serviceAction);
         InputSource var32 = new InputSource(new StringReader(var30));

         label169: {
            try {
               var37.newSAXParser().parse(var32, var33);
            } catch (ParserConfigurationException var19) {
               var23 = new StringBuilder("ParserConfigurationException during SAX parser creation, please check your env settings:");
               var23.append(var19.getMessage());
               RuntimeException var34 = new RuntimeException(var23.toString());
               throw var34;
            } catch (SAXException var20) {
               var28 = new UPNPResponseException(899, var20.getMessage());
               break label169;
            } finally {
               try {
                  var22.close();
               } catch (IOException var16) {
               }

            }

            var28 = null;
         }

         if (var28 == null) {
            if (var6 == 200) {
               var35 = var33.getActionResponse();
               var24 = null;
            } else if (var6 == 500) {
               var28 = var33.getUPNPResponseException();
               var24 = null;
               var35 = var24;
            } else {
               StringBuilder var26 = new StringBuilder("Unexpected server HTTP response:");
               var26.append(var6);
               var24 = new IOException(var26.toString());
               var35 = null;
            }
         } else {
            var35 = null;
            var24 = var35;
         }
      } else {
         var35 = null;
         var24 = var35;
      }

      try {
         var5.close();
      } catch (IOException var17) {
      }

      var4.disconnect();
      if (var28 == null) {
         Object var36 = var24;
         if (var35 == null) {
            var36 = var24;
            if (var24 == null) {
               var36 = new IOException("Unable to receive a response from the UPNP device");
            }
         }

         if (var36 == null) {
            return (ActionResponse)var35;
         } else {
            throw var36;
         }
      } else {
         throw var28;
      }
   }

   public ActionMessage setInputParameter(String var1, byte var2) throws IllegalArgumentException {
      return this.setInputParameter(var1, Byte.toString(var2));
   }

   public ActionMessage setInputParameter(String var1, double var2) throws IllegalArgumentException {
      return this.setInputParameter(var1, Double.toString(var2));
   }

   public ActionMessage setInputParameter(String var1, float var2) throws IllegalArgumentException {
      return this.setInputParameter(var1, Float.toString(var2));
   }

   public ActionMessage setInputParameter(String var1, int var2) throws IllegalArgumentException {
      return this.setInputParameter(var1, Integer.toString(var2));
   }

   public ActionMessage setInputParameter(String var1, long var2) throws IllegalArgumentException {
      return this.setInputParameter(var1, Long.toString(var2));
   }

   public ActionMessage setInputParameter(String var1, Object var2) throws IllegalArgumentException {
      if (var2 == null) {
         return this.setInputParameter(var1, "");
      } else if (var2 instanceof Date) {
         return this.setInputParameter(var1, (Date)var2);
      } else if (var2 instanceof Boolean) {
         return this.setInputParameter(var1, (Boolean)var2);
      } else if (var2 instanceof Integer) {
         return this.setInputParameter(var1, (Integer)var2);
      } else if (var2 instanceof Byte) {
         return this.setInputParameter(var1, (Byte)var2);
      } else if (var2 instanceof Short) {
         return this.setInputParameter(var1, (Short)var2);
      } else if (var2 instanceof Float) {
         return this.setInputParameter(var1, (Float)var2);
      } else if (var2 instanceof Double) {
         return this.setInputParameter(var1, (Double)var2);
      } else {
         return var2 instanceof Long ? this.setInputParameter(var1, (Long)var2) : this.setInputParameter(var1, var2.toString());
      }
   }

   public ActionMessage setInputParameter(String var1, String var2) throws IllegalArgumentException {
      if (this.serviceAction.getInputActionArguments() == null) {
         throw new IllegalArgumentException("No input parameters required for this message");
      } else if (this.serviceAction.getInputActionArgument(var1) == null) {
         StringBuilder var5 = new StringBuilder("Wrong input argument name for this action:");
         var5.append(var1);
         var5.append(" available parameters are : ");
         var5.append(this.getInputParameterNames());
         throw new IllegalArgumentException(var5.toString());
      } else {
         var2 = this.escape(var2);
         Iterator var3 = this.inputParameters.iterator();

         ActionMessage.InputParamContainer var4;
         while(var3.hasNext()) {
            var4 = (ActionMessage.InputParamContainer)var3.next();
            if (var4.name.equals(var1)) {
               var4.value = var2;
               return this;
            }
         }

         var4 = new ActionMessage.InputParamContainer((ActionMessage.InputParamContainer)null);
         var4.name = var1;
         var4.value = var2;
         this.inputParameters.add(var4);
         return this;
      }
   }

   public ActionMessage setInputParameter(String var1, Date var2) throws IllegalArgumentException {
      if (this.serviceAction.getInputActionArguments() != null) {
         ServiceActionArgument var3 = this.serviceAction.getInputActionArgument(var1);
         if (var3 != null) {
            ServiceStateVariable var6 = var3.getRelatedStateVariable();
            if (var6.getDataType().equals("time")) {
               return this.setInputParameter(var1, ISO8601Date.getIsoTime(var2));
            } else if (var6.getDataType().equals("time.tz")) {
               return this.setInputParameter(var1, ISO8601Date.getIsoTimeZone(var2));
            } else if (var6.getDataType().equals("date")) {
               return this.setInputParameter(var1, ISO8601Date.getIsoDate(var2));
            } else if (var6.getDataType().equals("dateTime")) {
               return this.setInputParameter(var1, ISO8601Date.getIsoDateTime(var2));
            } else if (var6.getDataType().equals("dateTime.tz")) {
               return this.setInputParameter(var1, ISO8601Date.getIsoDateTimeZone(var2));
            } else {
               StringBuilder var4 = new StringBuilder("Related input state variable ");
               var4.append(var6.getName());
               var4.append(" is not of an date type");
               throw new IllegalArgumentException(var4.toString());
            }
         } else {
            StringBuilder var5 = new StringBuilder("Wrong input argument name for this action:");
            var5.append(var1);
            var5.append(" available parameters are : ");
            var5.append(this.getInputParameterNames());
            throw new IllegalArgumentException(var5.toString());
         }
      } else {
         throw new IllegalArgumentException("No input parameters required for this message");
      }
   }

   public ActionMessage setInputParameter(String var1, short var2) throws IllegalArgumentException {
      return this.setInputParameter(var1, Short.toString(var2));
   }

   public ActionMessage setInputParameter(String var1, boolean var2) throws IllegalArgumentException {
      String var3;
      if (var2) {
         var3 = "1";
      } else {
         var3 = "0";
      }

      return this.setInputParameter(var1, var3);
   }

   private class InputParamContainer {
      private String name;
      private String value;

      private InputParamContainer() {
      }

      // $FF: synthetic method
      InputParamContainer(ActionMessage.InputParamContainer var2) {
         this();
      }
   }
}
