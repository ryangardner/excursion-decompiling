package net.sbbi.upnp;

import java.util.HashMap;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class ServiceEventMessageParser extends DefaultHandler {
   private Map changedStateVars = new HashMap();
   private String currentPropName = null;
   private boolean readPropertyName = false;

   protected ServiceEventMessageParser() {
   }

   public void characters(char[] var1, int var2, int var3) {
      String var4 = this.currentPropName;
      if (var4 != null) {
         String var5 = (String)this.changedStateVars.get(var4);
         String var6 = new String(var1, var2, var3);
         if (var5 == null) {
            this.changedStateVars.put(this.currentPropName, var6);
         } else {
            Map var8 = this.changedStateVars;
            var4 = this.currentPropName;
            StringBuilder var7 = new StringBuilder(String.valueOf(var5));
            var7.append(var6);
            var8.put(var4, var7.toString());
         }
      }

   }

   public void endElement(String var1, String var2, String var3) {
      var1 = this.currentPropName;
      if (var1 != null && var2.equals(var1)) {
         this.readPropertyName = false;
         this.currentPropName = null;
      }

   }

   public Map getChangedStateVars() {
      return this.changedStateVars;
   }

   public void startElement(String var1, String var2, String var3, Attributes var4) {
      if (var2.equals("property")) {
         this.readPropertyName = true;
      } else if (this.readPropertyName) {
         this.currentPropName = var2;
      }

   }
}
