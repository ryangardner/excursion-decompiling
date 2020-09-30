package net.sbbi.upnp.messages;

import java.util.logging.Logger;
import net.sbbi.upnp.services.ServiceAction;
import net.sbbi.upnp.services.ServiceActionArgument;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class ActionMessageResponseParser extends DefaultHandler {
   private static final String SOAP_FAULT_EL = "Fault";
   private static final Logger log = Logger.getLogger(ActionMessageResponseParser.class.getName());
   private String bodyElementName;
   private boolean faultResponse = false;
   private UPNPResponseException msgEx;
   private boolean parseOutputParams = false;
   private StringBuffer parsedResultBuilder = new StringBuffer();
   private ServiceActionArgument parsedResultOutArg;
   private boolean readErrorCode = false;
   private boolean readErrorDescription = false;
   private boolean readFaultCode = false;
   private boolean readFaultString = false;
   private ActionResponse result;
   private ServiceAction serviceAction;

   protected ActionMessageResponseParser(ServiceAction var1) {
      this.serviceAction = var1;
      StringBuilder var2 = new StringBuilder(String.valueOf(var1.getName()));
      var2.append("Response");
      this.bodyElementName = var2.toString();
   }

   public void characters(char[] var1, int var2, int var3) {
      if (this.parseOutputParams) {
         if (this.parsedResultOutArg != null) {
            this.parsedResultBuilder.append(var1, var2, var3);
         }
      } else if (this.readFaultCode) {
         this.msgEx.faultCode = new String(var1, var2, var3);
         this.readFaultCode = false;
      } else if (this.readFaultString) {
         this.msgEx.faultString = new String(var1, var2, var3);
         this.readFaultString = false;
      } else if (this.readErrorCode) {
         String var9 = new String(var1, var2, var3);
         boolean var7 = false;

         label55:
         try {
            var7 = true;
            this.msgEx.detailErrorCode = Integer.parseInt(var9);
            var7 = false;
         } finally {
            if (var7) {
               Logger var5 = log;
               StringBuilder var4 = new StringBuilder("Error during returned error code ");
               var4.append(var9);
               var4.append(" parsing");
               var5.fine(var4.toString());
               break label55;
            }
         }

         this.readErrorCode = false;
      } else if (this.readErrorDescription) {
         this.msgEx.detailErrorDescription = new String(var1, var2, var3);
         this.readErrorDescription = false;
      }

   }

   public void endElement(String var1, String var2, String var3) {
      ServiceActionArgument var4 = this.parsedResultOutArg;
      if (var4 != null && var4.getName().equals(var2)) {
         this.result.addResult(this.parsedResultOutArg, this.parsedResultBuilder.toString());
         this.parsedResultOutArg = null;
         this.parsedResultBuilder = new StringBuffer();
      } else if (var2.equals(this.bodyElementName)) {
         this.parseOutputParams = false;
      }

   }

   protected ActionResponse getActionResponse() {
      return this.result;
   }

   protected UPNPResponseException getUPNPResponseException() {
      return this.msgEx;
   }

   public void startElement(String var1, String var2, String var3, Attributes var4) {
      if (this.parseOutputParams) {
         ServiceActionArgument var5 = this.serviceAction.getActionArgument(var2);
         if (var5 != null && var5.getDirection() == "out") {
            this.parsedResultOutArg = var5;
            this.result.addResult(var5, (String)null);
         } else {
            this.parsedResultOutArg = null;
         }
      } else if (this.faultResponse) {
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
      } else if (var2.equals(this.bodyElementName)) {
         this.parseOutputParams = true;
         this.result = new ActionResponse();
      }

   }
}
