package net.sbbi.upnp.messages;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.sbbi.upnp.services.ServiceActionArgument;

public class ActionResponse {
   private Map outArguments = new HashMap();
   private Map outArgumentsVals = new HashMap();

   protected ActionResponse() {
   }

   protected void addResult(ServiceActionArgument var1, String var2) {
      this.outArguments.put(var1.getName(), var1);
      this.outArgumentsVals.put(var1.getName(), var2);
   }

   public ServiceActionArgument getOutActionArgument(String var1) {
      return (ServiceActionArgument)this.outArguments.get(var1);
   }

   public Set getOutActionArgumentNames() {
      return this.outArguments.keySet();
   }

   public String getOutActionArgumentValue(String var1) {
      return (String)this.outArgumentsVals.get(var1);
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      Iterator var2 = this.outArguments.keySet().iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         String var4 = (String)this.outArgumentsVals.get(var3);
         var1.append(var3);
         var1.append("=");
         var1.append(var4);
         if (var2.hasNext()) {
            var1.append("\n");
         }
      }

      return var1.toString();
   }
}
