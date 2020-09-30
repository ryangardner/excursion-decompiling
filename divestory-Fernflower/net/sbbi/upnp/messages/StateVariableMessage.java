package net.sbbi.upnp.messages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import net.sbbi.upnp.services.ServiceStateVariable;
import net.sbbi.upnp.services.UPNPService;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class StateVariableMessage {
   private static final Logger log = Logger.getLogger(StateVariableMessage.class.getName());
   private UPNPService service;
   private ServiceStateVariable serviceStateVar;

   protected StateVariableMessage(UPNPService var1, ServiceStateVariable var2) {
      this.service = var1;
      this.serviceStateVar = var2;
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

         var3.append(new String(var2, 0, var4));
      }
   }

   public StateVariableResponse service() throws IOException, UPNPResponseException {
      StringBuffer var1 = new StringBuffer(256);
      var1.append("<?xml version=\"1.0\"?>\r\n");
      var1.append("<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\"");
      var1.append(" s:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">");
      var1.append("<s:Body>");
      var1.append("<u:QueryStateVariable xmlns:u=\"urn:schemas-upnp-org:control-1-0\">");
      var1.append("<u:varName>");
      var1.append(this.serviceStateVar.getName());
      var1.append("</u:varName>");
      var1.append("</u:QueryStateVariable>");
      var1.append("</s:Body>");
      var1.append("</s:Envelope>");
      Logger var2 = log;
      StringBuilder var3 = new StringBuilder("POST prepared for URL ");
      var3.append(this.service.getControlURL());
      var2.fine(var3.toString());
      URL var23 = new URL(this.service.getControlURL().toString());
      HttpURLConnection var4 = (HttpURLConnection)var23.openConnection();
      var4.setDoInput(true);
      var4.setDoOutput(true);
      var4.setUseCaches(false);
      var4.setRequestMethod("POST");
      HttpURLConnection.setFollowRedirects(false);
      var3 = new StringBuilder(String.valueOf(var23.getHost()));
      var3.append(":");
      var3.append(var23.getPort());
      var4.setRequestProperty("HOST", var3.toString());
      var4.setRequestProperty("SOAPACTION", "\"urn:schemas-upnp-org:control-1-0#QueryStateVariable\"");
      var4.setRequestProperty("CONTENT-TYPE", "text/xml; charset=\"utf-8\"");
      var4.setRequestProperty("CONTENT-LENGTH", Integer.toString(var1.length()));
      OutputStream var5 = var4.getOutputStream();
      var5.write(var1.toString().getBytes());
      var5.flush();
      var4.connect();
      var2 = log;
      var3 = new StringBuilder("executing query :\n");
      var3.append(var1);
      var2.fine(var3.toString());

      InputStream var22;
      try {
         var22 = var4.getInputStream();
      } catch (IOException var18) {
         var22 = var4.getErrorStream();
      }

      UPNPResponseException var29 = null;
      Object var24;
      Object var30;
      if (var22 != null) {
         int var6 = var4.getResponseCode();
         String var31 = this.getResponseBody(var22);
         Logger var7 = log;
         StringBuilder var25 = new StringBuilder("received response :\n");
         var25.append(var31);
         var7.fine(var25.toString());
         SAXParserFactory var35 = SAXParserFactory.newInstance();
         var35.setValidating(false);
         var35.setNamespaceAware(true);
         StateVariableResponseParser var27 = new StateVariableResponseParser(this.serviceStateVar);
         InputSource var33 = new InputSource(new StringReader(var31));

         label153: {
            try {
               var35.newSAXParser().parse(var33, var27);
            } catch (ParserConfigurationException var19) {
               StringBuilder var32 = new StringBuilder("ParserConfigurationException during SAX parser creation, please check your env settings:");
               var32.append(var19.getMessage());
               RuntimeException var28 = new RuntimeException(var32.toString());
               throw var28;
            } catch (SAXException var20) {
               var29 = new UPNPResponseException(899, var20.getMessage());
               break label153;
            } finally {
               try {
                  var22.close();
               } catch (IOException var16) {
               }

            }

            var29 = null;
         }

         if (var29 == null) {
            if (var6 == 200) {
               var30 = var27.getStateVariableResponse();
               var24 = null;
            } else if (var6 == 500) {
               var29 = var27.getUPNPResponseException();
               var24 = null;
               var30 = var24;
            } else {
               StringBuilder var26 = new StringBuilder("Unexpected server HTTP response:");
               var26.append(var6);
               var24 = new IOException(var26.toString());
               var30 = null;
            }
         } else {
            var30 = null;
            var24 = var30;
         }
      } else {
         var30 = null;
         var24 = var30;
      }

      try {
         var5.close();
      } catch (IOException var17) {
      }

      var4.disconnect();
      if (var29 == null) {
         Object var34 = var24;
         if (var30 == null) {
            var34 = var24;
            if (var24 == null) {
               var34 = new IOException("Unable to receive a response from the UPNP device");
            }
         }

         if (var34 == null) {
            return (StateVariableResponse)var30;
         } else {
            throw var34;
         }
      } else {
         throw var29;
      }
   }
}
