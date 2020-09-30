package net.sbbi.upnp.messages;

import java.util.logging.Logger;
import net.sbbi.upnp.services.ServiceStateVariable;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class StateVariableResponseParser extends DefaultHandler {
   private static final String SOAP_FAULT_EL = "Fault";
   private static final Logger log = Logger.getLogger(StateVariableResponseParser.class.getName());
   private boolean faultResponse = false;
   private UPNPResponseException msgEx;
   private boolean parseStateVar = false;
   private boolean readErrorCode = false;
   private boolean readErrorDescription = false;
   private boolean readFaultCode = false;
   private boolean readFaultString = false;
   private StateVariableResponse result;
   private ServiceStateVariable stateVar;

   protected StateVariableResponseParser(ServiceStateVariable var1) {
      this.stateVar = var1;
   }

   public void characters(char[] var1, int var2, int var3) {
      String var9;
      if (this.parseStateVar) {
         String var4 = this.result.stateVariableValue;
         var9 = new String(var1, var2, var3);
         if (var4 == null) {
            this.result.stateVariableValue = var9;
         } else {
            StateVariableResponse var5 = this.result;
            StringBuilder var10 = new StringBuilder(String.valueOf(var4));
            var10.append(var9);
            var5.stateVariableValue = var10.toString();
         }
      } else if (this.readFaultCode) {
         this.msgEx.faultCode = new String(var1, var2, var3);
         this.readFaultCode = false;
      } else if (this.readFaultString) {
         this.msgEx.faultString = new String(var1, var2, var3);
         this.readFaultString = false;
      } else if (this.readErrorCode) {
         var9 = new String(var1, var2, var3);
         boolean var7 = false;

         label57:
         try {
            var7 = true;
            this.msgEx.detailErrorCode = Integer.parseInt(var9);
            var7 = false;
         } finally {
            if (var7) {
               Logger var11 = log;
               StringBuilder var12 = new StringBuilder("Error during returned error code ");
               var12.append(var9);
               var12.append(" parsing");
               var11.fine(var12.toString());
               break label57;
            }
         }

         this.readErrorCode = false;
      } else if (this.readErrorDescription) {
         this.msgEx.detailErrorDescription = new String(var1, var2, var3);
         this.readErrorDescription = false;
      }

   }

   public void endElement(String var1, String var2, String var3) throws SAXException {
      if (var2.equals("return") || var2.equals("varName")) {
         this.parseStateVar = false;
      }

   }

   protected StateVariableResponse getStateVariableResponse() {
      return this.result;
   }

   protected UPNPResponseException getUPNPResponseException() {
      return this.msgEx;
   }

   public void startElement(String var1, String var2, String var3, Attributes var4) {
      if (this.faultResponse) {
         if (var2.equals("faultcode")) {
            this.readFaultCode = true;
         } else if (var2.equals("faultstring")) {
            this.readFaultString = true;
         } else if (var2.equals("errorCode")) {
            this.readErrorCode = true;
         } else if (var2.equals("errorDescription")) {
            this.readErrorDescription = true;
         }
      } else if (var2.equals("Fault")) {
         this.msgEx = new UPNPResponseException();
         this.faultResponse = true;
      } else if (var2.equals("return") || var2.equals("varName")) {
         this.parseStateVar = true;
         StateVariableResponse var5 = new StateVariableResponse();
         this.result = var5;
         var5.stateVar = this.stateVar;
      }

   }
}
